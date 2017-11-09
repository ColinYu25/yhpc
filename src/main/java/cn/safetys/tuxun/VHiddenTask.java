package cn.safetys.tuxun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.safetys.ansheng.service.bean.DataEx_GREAT_HIDDEN_DANGER_ServiceBean;
import cn.safetys.ansheng.service.bean.DataEx_SMALL_HIDDEN_DANGER_ServiceBean;
import cn.safetys.constant.SystemConstant;
import cn.safetys.tuxun.request.great_hidden_danger.ExchangeItems;
import cn.safetys.tuxun.response.ResponseDataE;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.SynErrorIds;
import com.safetys.nbyhpc.domain.model.SyncNo;
import com.safetys.nbyhpc.domain.model.VDangerEx;
import com.safetys.nbyhpc.domain.model.VNormalDangerEx;
import com.safetys.nbyhpc.facade.iface.HiddenIface;
public class VHiddenTask {
	
	private static Logger log = Logger.getLogger(VHiddenTask.class);
	private HiddenIface hiddenIface;
	private DataEx_SMALL_HIDDEN_DANGER_ServiceBean dataEx_SMALL_HIDDEN_DANGER_Service;
	private DataEx_GREAT_HIDDEN_DANGER_ServiceBean dataEx_GREAT_HIDDEN_DANGER_Service;
	private static final String   NORMALDANGERKEY = "NORMALDANGER";
	private static final String   DANGERKEY = "DANGER";
	
	//每次查询的要同步的同步失败隐患数量
	private static final Long   ERRORCOUNT = new Long(50);
	
	//每次查询的要同步的隐患数量
	private static final Long   COUNT = new Long(200);
	
	private SystemConstant systemConstant;
	/**
	 * @return the hiddenIface
	 */
	public HiddenIface getHiddenIface() {
		return hiddenIface;
	}

	/**
	 * @param hiddenIface
	 *            the hiddenIface to set
	 */
	public void setHiddenIface(HiddenIface hiddenIface) {
		this.hiddenIface = hiddenIface;
	}

	public void execute() throws Exception {
		if(systemConstant.isDataSeparation()&&!systemConstant.isGovernment()){
			return ;
		}
		// 重大隐患交换开始
		//doDangerEx();
		// 一般隐患交换开始
		//doVNormalDangerEx();
		
	}

	public void doVNormalDangerEx() throws Exception {
        log.info("一般隐患同步到省局开始。。。。。。");
		try {
			//获得要同步的隐患信息，先同步那些执行失败的记录。
			log.info("一般隐患同步失败的记录同步开始。。。。。。。");
			List<SynErrorIds> synErrorIdsList = hiddenIface.getSynErrorIdsByVersionId(NORMALDANGERKEY, ERRORCOUNT);
			if(synErrorIdsList!=null&&synErrorIdsList.size()>0){
				for (SynErrorIds synErrorIds : synErrorIdsList) {
					//根据versionid和dangerId查询隐患信息。查询不到的话，说明versionId已经发生变化，设置这条失败记录为删除状态。查询到的话，重新同步
					VNormalDangerEx vNormalDangerEx=hiddenIface.getVNormalDangerEx(synErrorIds.getDangerVersionId(), synErrorIds.getDangerId());
					if(vNormalDangerEx!=null){
						//根据versionid和dangerId查询到隐患信息，说明这条隐患信息距离上次同步失败没有发生过变化，需要重新同步
						if(vNormalDangerEx.getIsDeleted().longValue()==0){
							//没有删除，则需要更新记录
							ResponseDataE responseDataE =this.vNormalDangerEx(vNormalDangerEx);
							//同步成功的话，删除这条同步失败记录，下次不再同步；同步失败的话，则删除状态不发生变化下次再次同步。
							if(responseDataE.getInfo().isOk()){
								synErrorIds.setDeleted(true);
								//修改同步到省局状态，方便同步删除的数据是否要发送到省局
								if("0".equals(vNormalDangerEx.getExFlag())){
									hiddenIface.updateExFlag(vNormalDangerEx.getId(), "normalDanger");
								}
								//由于这些都是失败记录，所以同步号表SyncNo不需要更改版本记录。
							}
						}else{
							//已经同步过去的隐患信息，被删除后，需要调用删除方法同步删除省级系统的这条隐患记录。
							if("1".equals(vNormalDangerEx.getExFlag())){
								ResponseDataE responseDataE = dataEx_SMALL_HIDDEN_DANGER_Service.delete_small_hidden_danger_info(vNormalDangerEx.getOutid(), null);//参数为outid
								if(responseDataE.getInfo().isOk()){
									//同步成功的话，删除这条记录
									synErrorIds.setDeleted(true);
								}
							}else{
								//如果这条记录一开始就没有同步过去的话，则也不需要再次删除，直接删除这条同步失败记录。
								synErrorIds.setDeleted(true);
							}
						}
					}else{
						//根据versionid和dangerId查询不到隐患信息，则说版本号已经发生变化，这条失败的记录不需要再次同步，故删除
						synErrorIds.setDeleted(true);
					}
					//修改这条同步失败记录是否删除。
					hiddenIface.saveOrUpdateSynErrorIds(synErrorIds);
				}
			}
			log.info("一般隐患同步失败的记录同步结束。。。。。。。");
			
			//处理完同步失败的数据，再处理正常数据。
			Long synLastNo=0L;
			//获得一般隐患上次的同步号
			SyncNo syncNo=hiddenIface.getSyncNoByKey(NORMALDANGERKEY);
			if(syncNo!=null){
				synLastNo=syncNo.getSyncLastNo();
			}else{
				//首次同步没有的话，向数据库中添加一条初始话记录
				syncNo=new SyncNo();
				syncNo.setCreateTime(new Date());
				syncNo.setModifyTime(new Date());
				syncNo.setDeleted(false);
				syncNo.setSyncKey(NORMALDANGERKEY);
				syncNo.setSyncLastNo(0L);
			}
			//获得要同步的隐患信息，第一个参数是最后一次同步成功同步号，第二个参数需要同步的条数
			List<VNormalDangerEx> vNormalDangerExList = hiddenIface
					.getVNormalDangerExListByVersionId(synLastNo,COUNT);
			log.info("要同步到省局的一般隐患条数为："+vNormalDangerExList.size());
			log.info("要同步到省局的一般隐患条数为："+vNormalDangerExList.size());
			int i=0;
            for (VNormalDangerEx vNormalDangerEx : vNormalDangerExList) {
            	//删除状态为未删除，更新记录。
				if(vNormalDangerEx.getIsDeleted().longValue()==0){
					ResponseDataE responseDataE =this.vNormalDangerEx(vNormalDangerEx);
					log.info("一般隐患修改，隐患id："+vNormalDangerEx.getId());
					log.info(responseDataE.getInfo().getMessage());
					log.info(responseDataE.getInfo().isOk());
					if(!responseDataE.getInfo().isOk()){
						log.info("一般隐患同步失败，向同步失败表中插入记录");
						//同步失败，向同步失败表中插入记录，下次同步的时候，先同步这些记录。
						//插入失败记录前，先查询是否已经存在
						SynErrorIds synErr=hiddenIface.getSynErrorIdsByVersionId(vNormalDangerEx.getId(), vNormalDangerEx.getVersionId());
						//不存在的情况下，插入失败记录
						if(synErr==null){
							SynErrorIds synErrorIds=new SynErrorIds();
							synErrorIds.setCreateTime(new Date());
							synErrorIds.setDangerId(vNormalDangerEx.getId());
							synErrorIds.setDangerVersionId(vNormalDangerEx.getVersionId());
							synErrorIds.setDeleted(false);
							synErrorIds.setDoFlag("0");
							synErrorIds.setSyncKey(NORMALDANGERKEY);
							hiddenIface.saveOrUpdateSynErrorIds(synErrorIds);
						}
						
						//执行失败，直接跳出
						//break;
					}else{
						i++;
						syncNo.setSyncLastNo(vNormalDangerEx.getVersionId());
						syncNo.setModifyTime(new Date());
						//修改同步到省局状态，方便同步删除的数据是否要发送到省局
						if("0".equals(vNormalDangerEx.getExFlag())){
							hiddenIface.updateExFlag(vNormalDangerEx.getId(), "normalDanger");
						}
						
					}
				}else{
					
					//删除状态为删除，更新省级隐患信息为删除状态。分两种情况，一种是删除前已经同步到省局的数据，需要删除；另一种为删除前没有同步到省级，
					//则这样的数据根本就不需要同步到省局
					
					//先判断这条数据省局是否已经同步过去了，已经同步过去了，则要设置为删除状态，如果没有同步过去，则删除的数据根本就不用同步过去
					if("1".equals(vNormalDangerEx.getExFlag())){
						//已经同步过去了，则要设置为删除状态
						ResponseDataE responseDataE = dataEx_SMALL_HIDDEN_DANGER_Service.delete_small_hidden_danger_info(vNormalDangerEx.getOutid(), null);//参数为outid
						log.info("一般隐患删除，隐患id："+vNormalDangerEx.getId());
						log.info(responseDataE.getInfo().isOk());
						log.info(responseDataE.getInfo().getMessage());
						if(!responseDataE.getInfo().isOk()){
							log.info("一般隐患同步失败，向同步失败表中插入记录");
							//同步失败，向同步失败表中插入记录，下次同步的时候，先同步这些记录。
							//插入失败记录前，先查询是否已经存在
							SynErrorIds synErr=hiddenIface.getSynErrorIdsByVersionId(vNormalDangerEx.getId(), vNormalDangerEx.getVersionId());
							//不存在的情况下，插入失败记录
							if(synErr==null){
								SynErrorIds synErrorIds=new SynErrorIds();
								synErrorIds.setCreateTime(new Date());
								synErrorIds.setDangerId(vNormalDangerEx.getId());
								synErrorIds.setDangerVersionId(vNormalDangerEx.getVersionId());
								synErrorIds.setDeleted(false);
								synErrorIds.setDoFlag("1");
								synErrorIds.setSyncKey(NORMALDANGERKEY);
								hiddenIface.saveOrUpdateSynErrorIds(synErrorIds);
							}
							
							//执行失败，直接跳出
							//break;
						}else{
							i++;
							syncNo.setSyncLastNo(vNormalDangerEx.getVersionId());
							syncNo.setModifyTime(new Date());
						}
					}else{
						//没有同步过去，修改编号，下次不再检测
						i++;
						syncNo.setSyncLastNo(vNormalDangerEx.getVersionId());
						syncNo.setModifyTime(new Date());
					}
					
				}
				//50条数据更新一下同步号表
				if(i==50){
					hiddenIface.saveSyncNo(syncNo);
					i=0;
				}
				
			}
            //最后更新同步号表
        	hiddenIface.saveSyncNo(syncNo);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		log.info("一般隐患同步到省局结束。。。。。。");
	}
	
	public ResponseDataE vNormalDangerEx(VNormalDangerEx vNormalDangerEx){
		//新增或者修改隐患信息
		Properties properties = new Properties();
		properties.put("CO_NAME", vNormalDangerEx.getCoName());//企业名称
		properties.put("HIDDEN_DANGER_INFO_SOURCES", vNormalDangerEx.getHiddenDangerInfoSources());//隐患来源 5.1.	隐患来源代码（CODE_HIDDEN_DANGER_INFO_SOURCES）
        properties.put("HIDDEN_DANGER_CATEGORY", vNormalDangerEx.getHiddenDangerCategory());//隐患类别  5.2.	隐患类别代码（CODE_HIDDEN_DANGER_CATEGORY）   参考:《安全生产监督管理信息隐患排查治理数据规范》
		//properties.put("REGULATION_CLASSIFICATION", getNotNullObj(vNormalDangerEx.getRegulationClassification()));//监管分类   参照《安全生产监督管理信息生产经营单位基础数据规范》 5.2.5   监管分类
		properties.put("INVESTIGATION_DATE", getNotNullDate(vNormalDangerEx.getInvestigationDate()));//排查日期
		properties.put("HIDDEN_DANGER_PLACE",getReplaceStr(vNormalDangerEx.getHiddenDangerPlace(),"无",150));//隐患地点
		//properties.put("HIDDEN_DANGER_AREA", getNotNullObj(vNormalDangerEx.getHiddenDangerArea()));//隐患部位
		properties.put("HIDDEN_DANGER_DESCRIPTION", getReplaceStr(vNormalDangerEx.getHiddenDangerDescription(),"无",100));//隐患描述
		properties.put("RESPONSIBLE_ORG", vNormalDangerEx.getResponsibleOrg());//责任单位
		properties.put("RECTIFICATION_RESPONSIBLE",getReplaceStr(vNormalDangerEx.getRectificationResponsible(),"无",25));//整改责任人
		//properties.put("RECTIFICATION_LIMIT_DATE", getNotNullObj(vNormalDangerEx.getRectificationLimitDate()));//整改期限
		properties.put("RECTIFICATION_FINISH_TIME", getNotNullDate(vNormalDangerEx.getRectificationFinishTime()));//整改完成日期
		properties.put("RECTIFICATION_TYPE",vNormalDangerEx.getRectificationType());//整改类型   参照第四部分     5.3.	整改类型代码（CODE_RECTIFICATION_TYPE）
		properties.put("RECTIFICATION_SITUATION", vNormalDangerEx.getRectificationSituation());//整改情况  	整改情况代码（CODE_RECTIFICATION_SITUATION）
		//properties.put("RECTIFICATION_MEASURES", getNotNullObj(vNormalDangerEx.getRectificationMeasures()));//整改措施
		//properties.put("CHECK_DATE", getNotNullObj(vNormalDangerEx.getCheckDate()));//核查日期
		//properties.put("CHECK_ORG",getNotNullObj(vNormalDangerEx.getCheckOrg()));//核查单位
		//properties.put("CHECK_SITUATION", getNotNullObj(vNormalDangerEx.getCheckSituation()));//核查情况
		properties.put("CONFIRM_TIME", getNotNullDate(vNormalDangerEx.getConfirmTime()));//确认时间	
		//properties.put("INSPECTOR", getNotNullObj(vNormalDangerEx.getInspector()));//检查人员
        //properties.put("OUTID", vNormalDangerEx.getOutid());//隐患编号
		//properties.put("LONGITUDE", getNotNullObj(vNormalDangerEx.getLongitude()));//经度
        //properties.put("LATITUDE", getNotNullObj(vNormalDangerEx.getLatitude()));//纬度
        //properties.put("REPAIR_UNIT", getNotNullObj(vNormalDangerEx.getRepairUnit()));//隐患整改责任部门
        //properties.put("CHECK_MAN", getNotNullObj(vNormalDangerEx.getCheckMan()));//排查人员
       
		ResponseDataE responseDataE = null;
		try {
			responseDataE = dataEx_SMALL_HIDDEN_DANGER_Service.edit_small_hidden_danger_info(vNormalDangerEx.getOutid(), properties, null);
		} catch (Exception e) {
			log.info("接口返回错误信息为："+responseDataE.getInfo().getMessage());
			log.info("同步错误信息为："+e.getMessage());
			e.printStackTrace();
		} //参数为outid
	    return responseDataE;
	}
	
	public void doDangerEx() throws Exception {
		log.info("重大隐患同步到省局开始。。。。。。");
		try {
			//获得要同步的隐患信息，先同步那些执行失败的记录。
			log.info("重大隐患同步失败的记录同步开始。。。。。。。");
			List<SynErrorIds> synErrorIdsList = hiddenIface.getSynErrorIdsByVersionId(DANGERKEY, ERRORCOUNT);
			if(synErrorIdsList!=null&&synErrorIdsList.size()>0){
				log.info("重大隐患同步失败的记录有"+synErrorIdsList.size()+"条！");
				for (SynErrorIds synErrorIds : synErrorIdsList) {
					//根据versionid和dangerId查询隐患信息。查询不到的话，说明versionId已经发生变化，设置这条失败记录为删除状态。查询到的话，重新同步
					VDangerEx vDangerEx=hiddenIface.getVDangerEx(synErrorIds.getDangerVersionId(), synErrorIds.getDangerId());
					if(vDangerEx!=null){
						//根据versionid和dangerId查询到隐患信息，说明这条隐患信息距离上次同步失败没有发生过变化，需要重新同步
						if(vDangerEx.getIsDeleted().longValue()==0){
							//没有删除，则需要更新记录
							ResponseDataE responseDataE =this.vDangerEx(vDangerEx);
							log.info("同步结果为："+responseDataE.getInfo().isOk());
							//同步成功的话，删除这条同步失败记录，下次不再同步；同步失败的话，则删除状态不发生变化下次再次同步。
							if(responseDataE.getInfo().isOk()){
								log.info("重大隐患同步成功！");
								synErrorIds.setDeleted(true);
								//修改同步到省局状态，方便同步删除的数据是否要发送到省局
								if("0".equals(vDangerEx.getExFlag())){
									hiddenIface.updateExFlag(vDangerEx.getId(), "danger");
								}
								//由于这些都是失败记录，所以同步号表SyncNo不需要更改版本记录。
							}
						}else{
							//已经同步过去的隐患信息，被删除后，需要调用删除方法同步删除省级系统的这条隐患记录。
							if("1".equals(vDangerEx.getExFlag())){
								ResponseDataE responseDataE = dataEx_SMALL_HIDDEN_DANGER_Service.delete_small_hidden_danger_info(vDangerEx.getOutid(), null);//参数为outid
								log.info("同步结果为："+responseDataE.getInfo().isOk());
								if(responseDataE.getInfo().isOk()){
									log.info("重大隐患同步成功！");
									//同步成功的话，删除这条记录
									synErrorIds.setDeleted(true);
								}
							}else{
								//如果这条记录一开始就没有同步过去的话，则也不需要再次删除，直接删除这条同步失败记录。
								synErrorIds.setDeleted(true);
							}
						}
					}else{
						//根据versionid和dangerId查询不到隐患信息，则说版本号已经发生变化，这条失败的记录不需要再次同步，故删除
						synErrorIds.setDeleted(true);
					}
					//修改这条同步失败记录是否删除。
					hiddenIface.saveOrUpdateSynErrorIds(synErrorIds);
				}
			}
			log.info("重大隐患同步失败的记录同步结束。。。。。。。");
			Long synLastNo=0L;
			//首先获得同步号
			SyncNo syncNo=hiddenIface.getSyncNoByKey(DANGERKEY);
			if(syncNo!=null){
				synLastNo=syncNo.getSyncLastNo();
			}else{
				//首次同步没有的话，向数据库中添加一条
				syncNo=new SyncNo();
				syncNo.setCreateTime(new Date());
				syncNo.setModifyTime(new Date());
				syncNo.setDeleted(false);
				syncNo.setSyncKey(DANGERKEY);
				syncNo.setSyncLastNo(0L);
			}
			//获得要同步重大隐患信息
			List<VDangerEx> vDangerExList = hiddenIface
					.getVDangerExListByVersionId(synLastNo,COUNT);
			log.info("要同步到省局的重大隐患条数为："+vDangerExList.size());
			log.info("要同步到省局的重大隐患条数为："+vDangerExList.size());
			int i=0;
            for (VDangerEx vDangerEx : vDangerExList) {
				if(vDangerEx.getIsDeleted().longValue()==0){
					 try {
						//edit_great_hidden_danger_info(vDangerEx.getOutid(), properties, null);//参数为隐患编号
						ResponseDataE responseDataE = this.vDangerEx(vDangerEx);
						log.info("重大隐患修改，隐患id："+vDangerEx.getId());
						log.info(responseDataE.getInfo().getMessage());
						log.info(responseDataE.getInfo().isOk());
						if(!responseDataE.getInfo().isOk()){
							log.info("重大隐患同步失败，向同步失败表中插入记录");
							//同步失败，向同步失败表中插入记录，下次同步的时候，先同步这些记录。
							//插入失败记录前，先查询是否已经存在
							SynErrorIds synErr=hiddenIface.getSynErrorIdsByVersionId(vDangerEx.getId(), vDangerEx.getVersionId());
							//不存在的情况下，插入失败记录
							if(synErr==null){
								SynErrorIds synErrorIds=new SynErrorIds();
								synErrorIds.setCreateTime(new Date());
								synErrorIds.setDangerId(vDangerEx.getId());
								synErrorIds.setDangerVersionId(vDangerEx.getVersionId());
								synErrorIds.setDeleted(false);
								synErrorIds.setDoFlag("0");
								synErrorIds.setSyncKey(DANGERKEY);
								hiddenIface.saveOrUpdateSynErrorIds(synErrorIds);
							}
							
							//执行失败，直接跳出
							//break;
						}else{
							log.info("重大隐患同步成功！");
							i++;
							syncNo.setSyncLastNo(vDangerEx.getVersionId());
							syncNo.setModifyTime(new Date());
							 //修改同步到省局状态，方便同步删除的数据是否要发送到省局
							if("0".equals(vDangerEx.getExFlag())){
							    hiddenIface.updateExFlag(vDangerEx.getId(), "danger");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					
				}else{
					//删除状态为删除，更新省级隐患信息为删除状态。分两种情况，一种是删除前已经同步到省局的数据，需要删除；另一种为删除前没有同步到省级，
					//则这样的数据根本就不需要同步到省局
					
					//先判断这条数据省局是否已经同步过去了，已经同步过去了，则要设置为删除状态，如果没有同步过去，则删除的数据根本就不用同步过去
					if("1".equals(vDangerEx.getExFlag())){
						//已经同步过去了，则要设置为删除状态
						ResponseDataE responseDataE = dataEx_GREAT_HIDDEN_DANGER_Service.delete_great_hidden_danger_info(vDangerEx.getOutid(), null);
						log.info("重大隐患删除，隐患id："+vDangerEx.getId());
						log.info(responseDataE.getInfo().isOk());
						log.info(responseDataE.getInfo().getMessage());
						if(!responseDataE.getInfo().isOk()){
							log.info("重大隐患同步失败，向同步失败表中插入记录");
							//插入失败记录前，先查询是否已经存在
							SynErrorIds synErr=hiddenIface.getSynErrorIdsByVersionId(vDangerEx.getId(), vDangerEx.getVersionId());
							//不存在的情况下，插入失败记录
							if(synErr==null){
								//同步失败，向同步失败表中插入记录，下次同步的时候，先同步这些记录。
								SynErrorIds synErrorIds=new SynErrorIds();
								synErrorIds.setCreateTime(new Date());
								synErrorIds.setDangerId(vDangerEx.getId());
								synErrorIds.setDangerVersionId(vDangerEx.getVersionId());
								synErrorIds.setDeleted(false);
								synErrorIds.setDoFlag("1");
								synErrorIds.setSyncKey(DANGERKEY);
								hiddenIface.saveOrUpdateSynErrorIds(synErrorIds);
							}
						
							//执行失败，直接跳出
							//break;
						}else{
							log.info("重大隐患同步成功！");
							i++;
							syncNo.setSyncLastNo(vDangerEx.getVersionId());
							syncNo.setModifyTime(new Date());
					       
						}
					}else{
						//没有同步过去，修改编号，下次不再检测
						i++;
						syncNo.setSyncLastNo(vDangerEx.getVersionId());
						syncNo.setModifyTime(new Date());
					}
					
				}
				//50条数据更新一下同步号表
				if(i==50){
					hiddenIface.saveSyncNo(syncNo);
					i=0;
				}
				
			}
         
            //最后更新同步号表
        	hiddenIface.saveSyncNo(syncNo);
			
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			
		}
		log.info("重大隐患同步到省局结束。。。。。。");
	}
	
	public ResponseDataE vDangerEx(VDangerEx vDangerEx) throws Exception{
		//新增或者修改隐患信息
		Properties properties = new Properties();
		properties.put("CO_NAME", vDangerEx.getCoName());
		properties.put("HIDDEN_DANGER_INFO_SOURCES", vDangerEx.getHiddenDangerInfoSources());//参考第四部分 代码集 	隐患来源代码（CODE_HIDDEN_DANGER_INFO_SOURCES）
		properties.put("HIDDEN_DANGER_CATEGORY", vDangerEx.getHiddenDangerCategory());//参考第四部分 代码集 5.2.	隐患类别代码（CODE_HIDDEN_DANGER_CATEGORY）  参考:《安全生产监督管理信息隐患排查治理数据规范》
		//properties.put("REGULATION_CLASSIFICATION",getNotNullObj(vDangerEx.getRegulationClassification()));//参照《安全生产监督管理信息生产经营单位基础数据规范》  5.2.5   监管分类 	
		properties.put("INVESTIGATION_DATE", getNotNullDate(vDangerEx.getInvestigationDate()));//时间格式 8位	
		properties.put("HIDDEN_DANGER_PLACE", getReplaceStr(vDangerEx.getHiddenDangerPlace(),"无",150));//字符串长度300
		//properties.put("HIDDEN_DANGER_AREA",getNotNullObj(vDangerEx.getHiddenDangerArea()));//字符串长度300
		properties.put("HIDDEN_DANGER_DESCRIPTION", getReplaceStr(vDangerEx.getHiddenDangerDescription(),"无",1000)); // 字符串 长度2000
		properties.put("RESPONSIBLE_ORG", vDangerEx.getResponsibleOrg());//字符串
		properties.put("RESPONSIBLE_RESPONSIBLE", vDangerEx.getResponsibleResponsible());//字符串
		properties.put("RECTIFICATION_LIMIT_DATE", getNotNullDate(vDangerEx.getRectificationLimitDate()));//时间格式 8位
		properties.put("RECTIFICATION_TYPE", vDangerEx.getRectificationType());//  参照第四部分     5.3.	整改类型代码（CODE_RECTIFICATION_TYPE）
		properties.put("RECTIFICATION_SITUATION", vDangerEx.getRectificationSituation());//参照第四部分 5.4.	整改情况代码（CODE_RECTIFICATION_SITUATION）	
		properties.put("RECTIFICATION_MEASURES", getNotNullObj(vDangerEx.getRectificationMeasures(),1000));//字符串 长度2000	
		properties.put("CHECK_DATE", getNotNullDate(vDangerEx.getCheckDate()));//时间格式 8位
		properties.put("CHECK_ORG", getNotNullObj(vDangerEx.getCheckOrg(),50));//字符串长度100	
		properties.put("CHECK_SITUATION", getNotNullObj(vDangerEx.getCheckSituation(),1000));//字符串 长度2000
		properties.put("LISTED_SUPERVISORY_SITUATION", getNotNullObj(vDangerEx.getListedSupervisorySituation(),0));//是否        1：是；0：否 
		properties.put("LISTED_SUPERVISORY_DATE", getNotNullDate(vDangerEx.getListedSupervisoryDate()));//时间格式8位	
		properties.put("LISTED_SUPERVISORY_LEVEL", getNotNullObj(vDangerEx.getListedSupervisoryLevel(),0));// 参考第四部分  5.5.	挂牌督办级别代码（CODE_LISTED_SUPERVISORY_LEVEL）
		properties.put("LISTED_SUPERVISORY_ORG", getNotNullObj(vDangerEx.getListedSupervisoryOrg(),50));//字符串 长度 100		
		properties.put("LISTED_SUPERVISORY_NUMBER", getNotNullObj(vDangerEx.getListedSupervisoryNumber(),50));//挂牌督办文号  字符串长度100	
		//properties.put("HIDDEN_DANGER_REPORT", getNotNullObj(vDangerEx.getHiddenDangerReport()));//隐患报告摘要   字符串长度100	
		properties.put("IMPROVEMENT_TARGET_SITU", getNotNullObj(vDangerEx.getImprovementTargetSitu(),0));//  整改目标到位情况      1：是；0：否	
		//properties.put("IMPROVEMENT_DATE",getNotNullObj(vDangerEx.getImprovementDate()));// 整改目标到位日期      8位	
		properties.put("IMPROVEMENT_RES_SITU", getNotNullObj(vDangerEx.getImprovementResSitu(),0));//  整改责任到位情况   1：是；0：否 	
		//properties.put("IMPROVEMENT_RESP_DATE", getNotNullObj(vDangerEx.getImprovementRespDate()));// 整改责任到位日期 
		properties.put("CONTROL_MEASURES_SITU", getNotNullObj(vDangerEx.getControlMeasuresSitu(),0));//治理措施到位情况  1：是；0：否 
		//properties.put("CONTROL_MEASURES_DATE", getNotNullObj(vDangerEx.getControlMeasuresDate()));//治理措施到位日期 
		properties.put("RECTIFICATION_MONEY_SITU", getNotNullObj(vDangerEx.getRectificationMoneySitu(),0));//整改资金到位情况   1：是；0：否
		//properties.put("RECTIFICATION_MONEY_DATE",getNotNullObj(vDangerEx.getRectificationMoneyDate()));// 整改资金到位日期
		properties.put("RECTIFICATION_TIME_SITU", vDangerEx.getRectificationTimeSitu());//整改时限到位情况   1：是；0：否
		//properties.put("RECTIFICATION_TIME_DATE", getNotNullObj(vDangerEx.getRectificationTimeDate()));//整改时限到位日期 
		properties.put("CONTINGENCY_PLAN_SITU", getNotNullObj(vDangerEx.getContingencyPlanSitu(),0));//应急预案到位情况 1：是；0：否
		//properties.put("CONTINGENCY_PLAN_DATE", getNotNullObj(vDangerEx.getContingencyPlanDate()));//应急预案到位日期
		properties.put("CONTROL_PLAN_SITU", vDangerEx.getControlPlanSitu());//纳入治理计划情况   1：是；0：否 
		//properties.put("CONTROL_PLAN_DATE", getNotNullObj(vDangerEx.getControlPlanDate()));//纳入治理计划日期
		properties.put("RECTIFICATION_MONEY",getNotNullObj(vDangerEx.getRectificationMoney(),0));//整改资金    数字
		properties.put("RECTIFICATION_FINISH_TIME", getNotNullDate(vDangerEx.getRectificationFinishTime()));//整改完成日期 
		properties.put("CANCEL_DATE", getNotNullDate(vDangerEx.getCancelDate()));//销号日期
		properties.put("CONFIRM_TIME", getNotNullDate(vDangerEx.getConfirmTime()));//确认时间 	
		//properties.put("INSPECTOR",getNotNullObj(vDangerEx.getInspector()));//检查人员
        //properties.put("OUTID", vDangerEx.getOutid());//隐患编号
		//properties.put("LONGITUDE", getNotNullObj(vDangerEx.getLongitude()));//经度
		//properties.put("LATITUDE", getNotNullObj(vDangerEx.getLatitude()));//纬度
		//properties.put("REPAIR_UNIT", getNotNullObj(vDangerEx.getRepairUnit()));//隐患整改责任部门  字符串
		ResponseDataE responseDataE = dataEx_GREAT_HIDDEN_DANGER_Service.edit_great_hidden_danger_info(vDangerEx.getOutid(), properties, null);//参数为隐患编号
	    return responseDataE;
	}
	
	//返回不为null的指定长度的字符
	public String getNotNullObj(Object obj,int length){
		if(obj==null){
			return "";
		}else{
			//当length为0的时候不进行截取
			if(length>0){
				//截取到符合要求的长度
				if(obj.toString().length()>length){
					return obj.toString().substring(0, length);
				}else{
					return obj.toString();
				}
			}else{
				return obj.toString();
			}
			
		}
	}
	
	//将只包含特殊字符的字段，转换成要替换的字符
	public String getReplaceStr(String obj,String replaceStr,int length){
		//当要判断的字符不为空的时候才需要判断
		if(obj!=null&&!"".equals(obj)){
			//去掉以下字符
			//\n 回车
		    //\t 水平制表符
		    //\s 空格
		    //\r 换行
			String strTemp="start"+obj.replace("\\s", "").replace("\n", "").replace("\t", "").replace("\r", "").trim()+"end";

			//如果strTemp等于startend，则说明这个字符串要替换
			if("startend".equals(strTemp)){
				return replaceStr;
			}else{
				//返回去掉了所有的换行，回车，空格，水平制表符的字符,含有这些字符的字符串传过去会报错。
				String str1= obj.replaceAll("\\s", "").replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").trim();
				//截取到符合要求的长度
				if(str1.length()>length){
					return str1.substring(0, length);
				}else{
					return str1;
				}
				
			}
		}else{
		    //为空的话直接返回要替换的字符串
			return replaceStr;
		}
	}
	

	
	//返回不为null的日期类型
	public String getNotNullDate(Date obj){
		if(obj==null){
			return "";
		}else{
			SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMdd");
			return fmt.format(obj);
		}
	}
	
	
	

	/**
	 * @return the dataEx_SMALL_HIDDEN_DANGER_Service
	 */
	public DataEx_SMALL_HIDDEN_DANGER_ServiceBean getDataEx_SMALL_HIDDEN_DANGER_Service() {
		return dataEx_SMALL_HIDDEN_DANGER_Service;
	}

	/**
	 * @param dataExSMALLHIDDENDANGERService the dataEx_SMALL_HIDDEN_DANGER_Service to set
	 */
	public void setDataEx_SMALL_HIDDEN_DANGER_Service(
			DataEx_SMALL_HIDDEN_DANGER_ServiceBean dataExSMALLHIDDENDANGERService) {
		dataEx_SMALL_HIDDEN_DANGER_Service = dataExSMALLHIDDENDANGERService;
	}

	/**
	 * @return the dataEx_GREAT_HIDDEN_DANGER_Service
	 */
	public DataEx_GREAT_HIDDEN_DANGER_ServiceBean getDataEx_GREAT_HIDDEN_DANGER_Service() {
		return dataEx_GREAT_HIDDEN_DANGER_Service;
	}

	/**
	 * @param dataExGREATHIDDENDANGERService the dataEx_GREAT_HIDDEN_DANGER_Service to set
	 */
	public void setDataEx_GREAT_HIDDEN_DANGER_Service(
			DataEx_GREAT_HIDDEN_DANGER_ServiceBean dataExGREATHIDDENDANGERService) {
		dataEx_GREAT_HIDDEN_DANGER_Service = dataExGREATHIDDENDANGERService;
	}
	
 public ResponseDataE edit_great_hidden_danger_info(String OUTID, Properties object, Map<String, Class<?>> alias)
    throws Exception
  {
    ExchangeItems exchangeItems = new ExchangeItems();
    Iterator it = object.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      Object key = entry.getKey();
      Object value = entry.getValue();
      java.lang.reflect.Method setMethod = ExchangeItems.class.getMethod("set" + key, new Class[] { String.class });
      log.info("反射："+key);
      setMethod.invoke(exchangeItems, new Object[] { value });
    }
     return null;
  }

public void setSystemConstant(SystemConstant systemConstant) {
	this.systemConstant = systemConstant;
}




}
