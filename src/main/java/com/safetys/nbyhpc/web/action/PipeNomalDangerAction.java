package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeNomalDangerFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) PipeNomalDangerAction.java
 * @date 2012-11-16
 * @author lisl
 * @copyright (c) 2012 Safetys.cn All rights reserved.
 * 
 */
public class PipeNomalDangerAction extends DaAppAction {
	
	/**
	 * 管道一般隐患
	 */
	private static final long serialVersionUID = -7534375276276098212L;

	private PipeNomalDangerFacadeIface pipeNomalDangerFacadeIface;// 管道隐患隐患

	private List<DaPipelineCompanyInfo> pipelineCompanyInfos;// 企业管道信息集合
	
	private DaPipelineCompanyInfo pipelineCompanyInfo;// 企业管道信息

	private List<DaPipeNomalDanger> nomalDangers;// 管道一般隐患集合
	
	private DaPipeNomalDanger nomalDanger;// 管道一般隐患
	
	private DaPipelineInfo entity;// 管道
	
	private List result; // 查询结果集

	private Pagination pagination;// 分页对象
	
	private String ids;//ID字符串
	
	private Long entityId;
	
	private PipeLineFacadeIface pipeLineFacadeIface;
	
	private DaCompany company; // 企业
	
	/**
	 * 管道列表（一般隐患录入）
	 * @return
	 * @throws Exception
	 */
	public String loadPipeLines() {
		try {
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				result = pipeLineFacadeIface.loadAllPipeLines(entity, company, "nomalDanger", getPagination(), this.getUserDetail());
			} else {
				result = pipeLineFacadeIface.loadAllPipeLines(entity, null, "nomalDanger", getPagination(), this.getUserDetail());
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 管道列表（未治理）
	 * @return
	 * @throws Exception
	 */
	public String loadPipeLinesUnGorver() {
		try {
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				result = pipeNomalDangerFacadeIface.loadPipeLinesUnGorverOrGorver(entity, company, false, getPagination(), this.getUserDetail());
			} else {
				result = pipeNomalDangerFacadeIface.loadPipeLinesUnGorverOrGorver(entity, null, false,getPagination(), this.getUserDetail());
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 管道列表（已治理）
	 * @return
	 * @throws Exception
	 */
	public String loadPipeLinesGorver() {
		try {
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				result = pipeNomalDangerFacadeIface.loadPipeLinesUnGorverOrGorver(entity, company, true, getPagination(), this.getUserDetail());
			} else {
				result = pipeNomalDangerFacadeIface.loadPipeLinesUnGorverOrGorver(entity, null, true, getPagination(), this.getUserDetail());
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 企业、部门一般隐患列表
	 * @return
	 */
	public String loadNomalDangers(){
		try {
			if(entity == null){
				return MESSAGE_TO_BACK;
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			if(null == entity){
				return MESSAGE_TO_BACK;
			}
			result = pipeNomalDangerFacadeIface.loadNomalDangers(entity, nomalDanger, getUserDetail(), getPagination());
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_TO_BACK;
		}
		return SUCCESS;
	}
	
	/**
	 * 一般隐患已治理列表
	 * @return
	 */
	public String loadNomalDangersGorver(){
		try {
			if(entity == null){
				return MESSAGE_TO_BACK;
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			if(null == entity){
				return MESSAGE_TO_BACK;
			}
			if(nomalDanger == null){
				nomalDanger = new DaPipeNomalDanger();
			}
			nomalDanger.setRepair(1);
			result = pipeNomalDangerFacadeIface.loadNomalDangers(entity, nomalDanger, getUserDetail(), getPagination());
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_TO_BACK;
		}
		return SUCCESS;
	}
	
	/**
	 * 一般隐患列表(已治理，未治理)
	 * @return
	 */
	public String loadNomalDangersUnGorver(){
		try {
			if(entity == null){
				return MESSAGE_TO_BACK;
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			if(null == entity){
				return MESSAGE_TO_BACK;
			}
			if(nomalDanger == null){
				nomalDanger = new DaPipeNomalDanger();
			}
			nomalDanger.setRepair(0);
			result = pipeNomalDangerFacadeIface.loadNomalDangers(entity, nomalDanger, getUserDetail(), getPagination());
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_TO_BACK;
		}
		return SUCCESS;
	}
	
	/**
	 * 一般隐患添加初始化（批量添加）
	 * @return
	 */
	public String createNomalDangerInit(){
		try {
			if(entity == null){
				return MESSAGE_TO_BACK;
			}
			if(nomalDanger == null){
				nomalDanger = new DaPipeNomalDanger();
				Calendar cal = Calendar.getInstance();
		        nomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			if(null != ids){
				result = pipeNomalDangerFacadeIface.loadNormalDangers(ids);
			}
			if(null == entity){
				return MESSAGE_TO_BACK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 一般隐患修改初始化（批量修改）
	 * @return
	 */
	public String editNomalDangerInit(){
		try {
			if(entityId == null){
				return MESSAGE_TO_BACK;
			}
			entity = pipeLineFacadeIface.findPipelineById(entityId);
			Calendar cal = Calendar.getInstance();
			int thisM = cal.get(Calendar.MONTH);
			int thisY = cal.get(Calendar.YEAR);
			int thisUid = getUserDetail().getUserId();
			if(null != ids){
				result = pipeNomalDangerFacadeIface.loadNormalDangers(ids);
				for(Object n0 : result){
					DaPipeNomalDanger n = (DaPipeNomalDanger) n0;
					Long userId = n.getUserId();
					if(userId!=thisUid){//不是本人
						n.setDisable(true);
					}else{
						if(n.isRepaired()){//已治理隐患
							Date d = n.getCreateTime();
							Calendar cal2 = Calendar.getInstance();
							cal2.setTime(d);
							int thisTm = cal2.get(Calendar.MONTH);
							int  thisTy = cal2.get(Calendar.YEAR);
							if(thisY != thisTy || thisM != thisTm) {//不是本月+本年
								n.setDisable(true);
							}
						}
					}
				}
			}
			if(nomalDanger == null){
				nomalDanger = new DaPipeNomalDanger();
		        nomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			if(null == entity){
				return MESSAGE_TO_BACK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加或者修改一般隐患
	 */
	public String createNomalDanger(){
		try {
			if(entity!=null && nomalDangers!=null){
				pipeNomalDangerFacadeIface.createNomalDangers(nomalDanger, entity, getUserDetail(), nomalDangers);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateNomalDanger(){		
		return null;
	}
	
	/**
	 * 批量删除一般隐患
	 * @return
	 */
	public String deleteNomalDanger(){
		try {		
			pipeNomalDangerFacadeIface.deleteNormalDanger(ids);
			return SUCCESS;
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 批量添加一般隐患无隐患
	 * @return
	 */
	public String addWuNomalDangers(){
		try {		
			if(company == null){
				company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
			}
			pipeNomalDangerFacadeIface.addWuNomalDangers(company, ids, getUserDetail());
//			return "byCompany";
			this.setMessageKey("批量添加无隐患成功!");
			this.getRequest().setAttribute("url", "loadPipeLines.xhtml");
			return MESSAGE_TO_REDIRECT;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public Pagination getPagination() {
		if (pagination == null){
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<DaPipeNomalDanger> getDaPipeNomalDangers() {
		return nomalDangers;
	}

	public void setDaPipeNomalDangers(List<DaPipeNomalDanger> nomalDangers) {
		this.nomalDangers = nomalDangers;
	}

	public DaPipeNomalDanger getDaPipeNomalDanger() {
		return nomalDanger;
	}

	public void setDaPipeNomalDanger(DaPipeNomalDanger nomalDanger) {
		this.nomalDanger = nomalDanger;
	}

	public List<DaPipeNomalDanger> getNomalDangers() {
		return nomalDangers;
	}

	public void setNomalDangers(List<DaPipeNomalDanger> nomalDangers) {
		this.nomalDangers = nomalDangers;
	}

	public List<DaPipelineCompanyInfo> getPipelineCompanyInfos() {
		return pipelineCompanyInfos;
	}

	public void setPipelineCompanyInfos(
			List<DaPipelineCompanyInfo> pipelineCompanyInfos) {
		this.pipelineCompanyInfos = pipelineCompanyInfos;
	}
	
	public DaPipeNomalDanger getNomalDanger() {
		return nomalDanger;
	}

	public void setNomalDanger(DaPipeNomalDanger nomalDanger) {
		this.nomalDanger = nomalDanger;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public PipeLineFacadeIface getPipeLineFacadeIface() {
		return pipeLineFacadeIface;
	}

	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public DaPipelineCompanyInfo getPipelineCompanyInfo() {
		return pipelineCompanyInfo;
	}

	public void setPipelineCompanyInfo(DaPipelineCompanyInfo pipelineCompanyInfo) {
		this.pipelineCompanyInfo = pipelineCompanyInfo;
	}

	public PipeNomalDangerFacadeIface getPipeNomalDangerFacadeIface() {
		return pipeNomalDangerFacadeIface;
	}

	public void setPipeNomalDangerFacadeIface(
			PipeNomalDangerFacadeIface pipeNomalDangerFacadeIface) {
		this.pipeNomalDangerFacadeIface = pipeNomalDangerFacadeIface;
	}

	public DaPipelineInfo getEntity() {
		return entity;
	}

	public void setEntity(DaPipelineInfo entity) {
		this.entity = entity;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	
}
