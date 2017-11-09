package com.safetys.nbyhpc.domain.persistence.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.ETLRcjc;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLRcjcInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public class ETLRcjcInfoPersistenceImpl implements ETLRcjcInfoPersistenceIface {

	private PersistenceDao<DaNomalDanger> persistenceDao;

	private CompanyPersistenceIface companyPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	public void save(ETLRcjc entity) throws Exception {

		DaNomalDanger nomalDanger = null;
		// 同步部署后的当月日期开始,之前的过滤

		String[] start_time = ConfigUtil.getPropertyValue("rcjc_start_time")
				.split("-");
		long year = Long.parseLong(start_time[0]);
		long month = Long.parseLong(start_time[1]);
		if (entity.getHIDDEN_INPUT_TIME() != null
				&& !"".equals(entity.getHIDDEN_INPUT_TIME())) {

			String[] df2 = new SimpleDateFormat("yyyy-MM").format(
					entity.getHIDDEN_INPUT_TIME()).split("-");
			System.out.println("HIDDEN_INPUT_TIME="
					+ new SimpleDateFormat("yyyy-MM").format(entity
							.getHIDDEN_INPUT_TIME()));
			long year1 = Long.parseLong(df2[0]);
			long month1 = Long.parseLong(df2[1]);

			if (year1 > year || (year1 == year && month1 >= month)) {
				if (entity.getCOMPANY_NAME() != null
						&& entity.getASSEMBLEID() != null) { // 企业是否在隐患中存在

					DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
							.forClass(DaCompany.class);
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"companyName", entity.getCOMPANY_NAME()));

					DaCompany res1 = companyPersistenceIface
							.loadCompanyInfo(detachedCriteriaProxy);
					if (res1 != null
							&& res1.getDaCompanyPass().getComUserId() != null) {

						//定义是否同步字段，默认为不同步
						boolean isSyn=false;
						// 首先先通过同步标志判断是否是隐患，是的话，还要判断是否是街道和标准话，目前暂时只同步标准话和街道的隐患信息
						if (entity.getASSEMBLEID() != null
								&& !"".equals(entity.getASSEMBLEID())) {
					
							String[] assembleIds=entity.getASSEMBLEID().split("_");

                            if(assembleIds!=null&&assembleIds.length>=3&&!"".equals(assembleIds[2].trim())){
                            	//当是隐患的时候，判断是否是乡镇街道或者标准话，是的话，才同步
                            	if("XZJD".equals(assembleIds[0].toUpperCase())||"BZH".equals(assembleIds[0].toUpperCase())){
                            		isSyn=true;
                            	}else{
                            		isSyn=false;
                            	}
                            }else{
                            	isSyn=false;
                            }
							
						}else{
							isSyn=false;
						}
						
						
						if(isSyn){
							// 首先先通过同步标志查找系统中是否已经存在，存在的话，修改；不存在的话，添加
							
							String hql = "from DaNomalDanger da where da.sysFlag='"
										+ entity.getASSEMBLEID() + "'";
							List<DaNomalDanger> daNomalDangerList = persistenceDao
										.executeHQLQuery(hql);
							if (daNomalDangerList.size() > 0) {
									nomalDanger = daNomalDangerList.get(0);
							}
							
							if (nomalDanger != null) {
								// 存在一般隐患的情况下，修改隐患信息
								System.out.println("存在一般隐患,修改隐患信息。sysFlag="
										+ entity.getASSEMBLEID());
								nomalDanger.setCompanyPassId(res1.getId());
								nomalDanger.setLinkMan(res1.getFddelegate());
								nomalDanger.setLinkMobile(res1.getPhoneCode());
								nomalDanger.setLinkTel(res1.getPhoneCode());

								nomalDanger.setUserId(res1.getDaCompanyPass()
										.getComUserId());
								
								if(entity.getIS_DELETED()!=null){
									boolean isDeleted=entity.getIS_DELETED().longValue()==0?false:true;
									nomalDanger.setDeleted(isDeleted);
								}else{
									nomalDanger.setDeleted(false);
								}
								
								nomalDanger.setDanger(true);
								nomalDanger.setHazardSourceCode(entity
										.getHIDDEN_SOURCE());
								nomalDanger.setDescription(entity
										.getCHECK_MAJOR_ACCIDENT());

								if(entity.getHIDDEN_TYPE()!=null&&!"".equals( entity.getHIDDEN_TYPE())){
									DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy
									.forClass(DaIndustryParameter.class);
									detachedCriteriaProxy1.add(RestrictionsProxy.eq(
											"code", entity.getHIDDEN_TYPE()));
									detachedCriteriaProxy1.add(RestrictionsProxy.eq(
											"type", Nbyhpc.QUARTER_REPORT_TYPE));
									
									detachedCriteriaProxy1.add(RestrictionsProxy.eq(
											"deleted", false));
									List<DaIndustryParameter> res = tradeTypePersistenceIface
											.loadTradeTypes(detachedCriteriaProxy1,
													null);
									if (res.size() > 0) {
										//判断查询的隐患类型是否是一级代码人：001，是的话，默认二级代码为从业人员操作行为：B08
										if("001".equals(res.get(0).getCode())){
											//1327为一级代码人的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setType(1327);
											//1584181为二级代码从业人员操作行为的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setSecondType(1584181);
										}else if("002".equals(res.get(0).getCode())){
											//判断查询的隐患类型是否是一级代码物：002，是的话，默认二级代码为场所环境—物：B07
											//1332为一级代码物的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setType(1332);
											//1584197为二级代码场所环境的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setSecondType(1584197);
										}else if("003".equals(res.get(0).getCode())){
											//判断查询的隐患类型是否是一级代码管理：003是的话，默认二级代码为其他现场管理：B99
											//1344为一级代码管理的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setType(1344);
											//1584184为二级代码其他现场管理的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setSecondType(1584184);
										}else{
											//如果上面三种情况都不是的话，则说明查询的二级编码，则可以根据二级类型的父节点设置隐患类型。
											nomalDanger.setSecondType(Integer.parseInt(""
													+ res.get(0).getId()));
											nomalDanger.setType(Integer.parseInt(""
													+ res.get(0).getDaIndustryParameter()
															.getId()));
										}
										
										
									}
							
								}

								nomalDanger.setCreateTime(entity
										.getHIDDEN_INPUT_TIME());
								nomalDanger.setModifyTime(entity
										.getHIDDEN_INPUT_TIME());

								if (entity.getREC_FINISH_TIME() != null) {
									nomalDanger.setRepaired(true);
									nomalDanger.setCompletedDate(entity
											.getREC_FINISH_TIME());
								}else{
									nomalDanger.setRepaired(false);
									nomalDanger.setCompletedDate(null);
								}
								nomalDanger.setGovernMoney(0f);
								nomalDanger.setSysFlag(entity.getASSEMBLEID()); // 同步标志
								//设置同步状态为未同步，再次同步到中心库企业隐患表中
								nomalDanger.setIsSynchro(Integer.valueOf(1));
								persistenceDao.update(nomalDanger);
							} else {
								// 不存在一般隐患的情况下，添加隐患信息
								System.out.println("不存在一般隐患,添加隐患信息。");
								nomalDanger = new DaNomalDanger();
								nomalDanger.setCompanyPassId(res1.getId());
								nomalDanger.setLinkMan(res1.getFddelegate());
								nomalDanger.setLinkMobile(res1.getPhoneCode());
								nomalDanger.setLinkTel(res1.getPhoneCode());

								nomalDanger.setUserId(res1.getDaCompanyPass()
										.getComUserId());

								if(entity.getIS_DELETED()!=null){
									boolean isDeleted=entity.getIS_DELETED().longValue()==0?false:true;
									nomalDanger.setDeleted(isDeleted);
								}else{
									nomalDanger.setDeleted(false);
								}
								nomalDanger.setDanger(true);
								nomalDanger.setHazardSourceCode(entity
										.getHIDDEN_SOURCE());
								nomalDanger.setDescription(entity
										.getCHECK_MAJOR_ACCIDENT());

								if(entity.getHIDDEN_TYPE()!=null&&!"".equals( entity.getHIDDEN_TYPE())){
									DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy
									.forClass(DaIndustryParameter.class);
									detachedCriteriaProxy1.add(RestrictionsProxy.eq(
											"code", entity.getHIDDEN_TYPE()));
									detachedCriteriaProxy1.add(RestrictionsProxy.eq(
											"type", Nbyhpc.QUARTER_REPORT_TYPE));
									
									detachedCriteriaProxy1.add(RestrictionsProxy.eq(
											"deleted", false));
									List<DaIndustryParameter> res = tradeTypePersistenceIface
											.loadTradeTypes(detachedCriteriaProxy1,
													null);
									if (res.size() > 0) {
										//判断查询的隐患类型是否是一级代码人：001，是的话，默认二级代码为从业人员操作行为：B08
										if("001".equals(res.get(0).getCode())){
											//1327为一级代码人的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setType(1327);
											//1584181为二级代码从业人员操作行为的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setSecondType(1584181);
										}else if("002".equals(res.get(0).getCode())){
											//判断查询的隐患类型是否是一级代码物：002，是的话，默认二级代码为场所环境—物：B07
											//1332为一级代码物的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setType(1332);
											//1584197为二级代码场所环境的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setSecondType(1584197);
										}else if("003".equals(res.get(0).getCode())){
											//判断查询的隐患类型是否是一级代码管理：003是的话，默认二级代码为其他现场管理：B99
											//1344为一级代码管理的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setType(1344);
											//1584184为二级代码其他现场管理的id，由于隐患类型一般是不会变化的，id不会轻易改变，故此处暂时写死，以后需要的话，可以根据code查询
											nomalDanger.setSecondType(1584184);
										}else{
											//如果上面三种情况都不是的话，则说明查询的二级编码，则可以根据二级类型的父节点设置隐患类型。
											nomalDanger.setSecondType(Integer.parseInt(""
													+ res.get(0).getId()));
											nomalDanger.setType(Integer.parseInt(""
													+ res.get(0).getDaIndustryParameter()
															.getId()));
										}
										
										
									}
							
								}
								nomalDanger.setCreateTime(entity
										.getHIDDEN_INPUT_TIME());
								nomalDanger.setModifyTime(entity
										.getHIDDEN_INPUT_TIME());
								if (entity.getREC_FINISH_TIME() != null) {
									nomalDanger.setRepaired(true);
									nomalDanger.setCompletedDate(entity
											.getREC_FINISH_TIME());
								}else{
									nomalDanger.setRepaired(false);
									nomalDanger.setCompletedDate(null);
								}
								nomalDanger.setGovernMoney(0f);
								nomalDanger.setSysFlag(entity.getASSEMBLEID()); // 同步标志
								//设置同步状态为未同步，再次同步到中心库企业隐患表中
								nomalDanger.setIsSynchro(Integer.valueOf(1));
								persistenceDao.save(nomalDanger);
							}
						}
					}
				}
			}
		}

	}

	
	public static void main(String[] args) {

		String[] start_time = "2014-05".split("-");
		long year = Long.parseLong(start_time[0]);
		long month = Long.parseLong(start_time[1]);
		System.out.println("year=" + year);
		System.out.println("month=" + month);
		Date d = new Date();

		String[] df2 = new SimpleDateFormat("yyyy-MM").format(d).split("-");
		System.out.println("date=" + new SimpleDateFormat("yyyy-MM").format(d));
		long year1 = Long.parseLong(df2[0]);
		long month1 = Long.parseLong(df2[1]);
		System.out.println("year1=" + year1);
		System.out.println("month1=" + month1);

		if (year1 > year || (year1 == year && month1 >= month)) {
			System.out.println("ok!");
		}

		

	}

	public PersistenceDao<DaNomalDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaNomalDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public TradeTypePersistenceIface getTradeTypePersistenceIface() {
		return tradeTypePersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

}
