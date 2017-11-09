package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.domain.persistence.iface.EnumPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.PipeRollcallCompanyFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @(#) RollcallCompanyFacadeImpl.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public class PipeRollcallCompanyFacadeImpl implements PipeRollcallCompanyFacadeIface {

	private PipeRollcallCompanyPersistenceIface pipeRollcallCompanyPersistenceIface;
	
	private EnumPersistenceIface enumPersistenceIface;
	
	private AreaPersistenceIface areaPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private BasePersistenceImpl persistenceImpl;
	
	private PipeDangerPersistenceIface pipeDangerPersistenceIface;

	public Long createRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return pipeRollcallCompanyPersistenceIface.createRollcallCompany(rollcallCompany);
	}

	public DaPipeRollcallCompany loadRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompany(rollcallCompany);
	}
	
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException{
		return pipeDangerPersistenceIface.loadDanger(danger);
	}
	
	public DaPipelineCompanyInfo loadCompany(DaPipelineCompanyInfo pipeLine) throws ApplicationAccessException {
		return null;
	}

	public List<DaPipeRollcallCompany> loadRollcallCompanies(Long dangerId) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallCompany.class, "drc");
		if (dangerId != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("drc.daPipeDanger.id", dangerId));
		}
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, null);
	}
	
	 public List<FkEnum> loadEnums(UserDetailWrapper userDetail)throws ApplicationAccessException{
	    DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkEnum.class,"fe");
	    if(userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0L) {
			detachedCriteriaProxy.add(RestrictionsProxy.like("fe.enumCode","town", MatchMode.ANYWHERE));
		}else if(userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0L) {
			detachedCriteriaProxy.add(RestrictionsProxy.like("fe.enumCode","county", MatchMode.ANYWHERE));
		}else if(userDetail.getFirstArea() != null && userDetail.getFirstArea() != 0L) {
	    	detachedCriteriaProxy.add(RestrictionsProxy.like("fe.enumCode","city", MatchMode.ANYWHERE));
		}
	    detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.father_id in(select id from fk_enum where is_deleted=0 and enum_code = 'rollcallCompany')"));
	    return enumPersistenceIface.loadEnums(detachedCriteriaProxy);
	 }
	 
	 public List<FkArea> loadAreas(UserDetailWrapper userDetail)throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		if(userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0L) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id = (select id from fk_area where area_code=" + userDetail.getThirdArea() + ")"));
		}else if(userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0L) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + userDetail.getSecondArea() + ")"));
		}else if(userDetail.getFirstArea() != null && userDetail.getFirstArea() != 0L) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + userDetail.getFirstArea() + ")"));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		}
	
	public List<DaPipeRollcallCompany> loadRollcallCompaniesOfLevel(DaPipeRollcallCompany rollcallCompany,DaCompany company,DaPipelineCompanyInfo pipeLine, Pagination pagination,UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("daPipeDanger", "dd").createAlias("dd.pipeLine", "pl").createAlias("pl.daPipelineCompanyinfo", "dc").createAlias("dc.company", "c").createAlias("c.daCompanyPass", "dcp");
		if(null!=company){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.id", company.getId()));
		}else{//安监、城管、交通、发改委
			String userIndustry = userDetail.getUserIndustry();
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(null != userIndustry){
//				if("anjian".equals(userIndustry)){
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", 3));
//				}
				if("jiaotong".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", 4));
				}
				if("chengguan".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("pl.type", 0), RestrictionsProxy.eq("pl.type", 2)));
				}
				if("fagai".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", 1));
				}
			}
		}
//		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if(rollcallCompany != null){
			if(rollcallCompany.getLevel() != null && !"".equals(rollcallCompany.getLevel().trim())){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("drc.level",rollcallCompany.getLevel()));
			}else{
				if(userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.like("drc.level","town", MatchMode.ANYWHERE));
				}else if(userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.like("drc.level","county", MatchMode.ANYWHERE));
				}else if(userDetail.getFirstArea() != null && userDetail.getFirstArea() != 0L) {
			    	detachedCriteriaProxy.add(RestrictionsProxy.like("drc.level","city", MatchMode.ANYWHERE));
				}
			}
			if (rollcallCompany.getDaPipeDanger() != null) {
				if(rollcallCompany.getDaPipeDanger().getDangerAdd() != null && !"".equals(rollcallCompany.getDaPipeDanger().getDangerAdd().trim())){
					detachedCriteriaProxy.add(RestrictionsProxy.like("dd.dangerAdd", rollcallCompany.getDaPipeDanger().getDangerAdd().trim(), MatchMode.ANYWHERE));
				}
			}
			int nextYear = rollcallCompany.getNowYear()+1;
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"+rollcallCompany.getNowYear()+"-01-01','yyyy-MM-dd') " 
					+ " and to_date('"+nextYear+"-01-01','yyyy-MM-dd')"));
		}else{
			if(userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("drc.level","town", MatchMode.ANYWHERE));
			}else if(userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("drc.level","county", MatchMode.ANYWHERE));
			}else if(userDetail.getFirstArea() != null && userDetail.getFirstArea() != 0L) {
		    	detachedCriteriaProxy.add(RestrictionsProxy.like("drc.level","city", MatchMode.ANYWHERE));
			}
		}
		if(pipeLine!=null&&pipeLine.getCompany()!=null){
			DaCompany com = pipeLine.getCompany();
			if(com!= null){
				if(com.getCompanyName() != null && !"".equals(com.getCompanyName().trim())){
					detachedCriteriaProxy.add(RestrictionsProxy.like("c.companyName", com.getCompanyName().trim(), MatchMode.ANYWHERE));
				}
				if(com.getFirstArea() != null && com.getFirstArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("c.firstArea", com.getFirstArea()));
					if(com.getSecondArea() != null && com.getSecondArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea", com.getSecondArea()));
						if(com.getThirdArea() != null && com.getThirdArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("c.thirdArea", com.getThirdArea()));
							if(com.getFouthArea() != null && com.getFouthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("c.fouthArea", com.getFouthArea()));
								if(com.getFifthArea() != null && com.getFifthArea() != 0L) {
									detachedCriteriaProxy.add(RestrictionsProxy.eq("c.fifthArea", com.getFifthArea()));
								}
							}
						}
					}
				}
			}
		}
		/* 取消用户区域限制
		 * if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.firstArea",userDetail.getFirstArea()));
		}*/
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<DaPipeRollcallCompany> loadRollcallCompaniesByCompany(DaPipelineCompanyInfo pipeLine, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("DaPipeDanger", "dd");
//		if(company!= null){
//			if(company.getId() != null){
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.DaPipelineCompanyInfoPass.id", company.getId()));
//			}
//		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("drc.modifyTime"));
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}
	
//	public List<DaPipelineCompanyInfoPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException {
//		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineCompanyInfoPass.class, "dcp");
//		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long)userDetail.getUserId()));
//		//return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
//		return null;
//	}
	
	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type",Nbyhpc.COMPANY_TRADE));
		if (userDetail != null) {
			String userIndustry = userDetail.getUserIndustry();
			if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("depiction",userIndustry));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id is null"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,null);
	}
	
	public List<DaPipelineCompanyInfo> loadCompanysByNotice(DaPipelineCompanyInfo pipeLine,UserDetailWrapper userDetail,Pagination pagination) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaPipelineCompanyInfo.class,"dc");
		detachedCriteriaProxy.createCriteria("dc.DaPipelineCompanyInfoPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm",true));
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_danger where is_deleted=0 and id in (select par_da_dan_id from da_rollcall_company where is_deleted=0 and is_notice=1))"));
//		if(company!=null){
//			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
//				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
//			}
//			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",company.getFirstArea()));
//				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
//					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
//						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
//						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
//							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
//							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
//								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
//							}
//						}
//					}
//				}
//			}
//		}
		if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(),Nbyhpc.COMPANY_TRADE);
			if(industryParamenter != null){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
									+ industryParamenter.getId() + ")"));
			}else{
				return null;
			}
		}
		//return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		return null;
	}
	
	public List<DaPipeRollcallCompany> loadRollcallCompaniesOfLevel(DaPipelineCompanyInfo pipeLine,Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("DaPipeDanger", "dd");
		detachedCriteriaProxy.createCriteria("DaPipelineCompanyInfoPass", "dcp");
		detachedCriteriaProxy.createCriteria("DaPipelineCompanyInfo", "dc");
//		if(company!= null){
//			if(company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())){
//				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
//			}
//			if(company.getFirstArea() != null && company.getFirstArea() != 0L) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
//				if(company.getSecondArea() != null && company.getSecondArea() != 0L) {
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
//					if(company.getThirdArea() != null && company.getThirdArea() != 0L) {
//						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
//						if(company.getFouthArea() != null && company.getFouthArea() != 0L) {
//							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
//							if(company.getFifthArea() != null && company.getFifthArea() != 0L) {
//								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
//							}
//						}
//					}
//				}
//			}
//		}
		if (userDetail.getFifthArea() != null
				&& !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",
					userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null
				&& !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",
					userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null
				&& !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",
					userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null
				&& !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",
					userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null
				&& !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
					userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userDetail.getUserIndustry())
				&& !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface
					.loadTradeType(userDetail.getUserIndustry(),
							Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy
						.add(RestrictionsProxy
								.sqlRestriction("dcp2_.par_da_com_id in "
										+ "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
										+ industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}

	public void updateRollcallCompany(DaPipeRollcallCompany rollcallCompany)
			throws ApplicationAccessException {
		DaPipeRollcallCompany oldRollcallCompany = loadRollcallCompany(rollcallCompany);
		rollcallCompany.setCreateTime(oldRollcallCompany.getCreateTime());
		rollcallCompany.setNotice(oldRollcallCompany.isNotice());
		rollcallCompany.setFinishTime(oldRollcallCompany.getFinishTime());
		rollcallCompany.setPlanTime(oldRollcallCompany.getPlanTime());
		rollcallCompany.setWordOne(oldRollcallCompany.getWordOne());
		rollcallCompany.setWordYear(oldRollcallCompany.getWordYear());
		rollcallCompany.setWordTwo(oldRollcallCompany.getWordTwo());
		rollcallCompany.setSignatory(oldRollcallCompany.getSignatory());
		rollcallCompany.setRollcallUnitTime(oldRollcallCompany.getRollcallUnitTime());
		rollcallCompany.setSendoffMan(oldRollcallCompany.getSendoffMan());
		rollcallCompany.setSigninMan(oldRollcallCompany.getSigninMan());
		rollcallCompany.setSigninTime(oldRollcallCompany.getSigninTime());
		pipeRollcallCompanyPersistenceIface.mergeRollcallCompany(rollcallCompany);
	}
	
	
	public void updateRollcallCompanyForNotice(DaPipeRollcallCompany rollcallCompany)
			throws ApplicationAccessException {
		DaPipeRollcallCompany oldRollcallCompany = loadRollcallCompany(rollcallCompany);
		rollcallCompany.setCreateTime(oldRollcallCompany.getCreateTime());
		rollcallCompany.setUserId(oldRollcallCompany.getUserId());
		rollcallCompany.setLevel(oldRollcallCompany.getLevel());
		rollcallCompany.setGovment(oldRollcallCompany.getGovment());
		rollcallCompany.setDepartment(oldRollcallCompany.getDepartment());
		rollcallCompany.setCompleteTime(oldRollcallCompany.getCompleteTime());
		pipeRollcallCompanyPersistenceIface.mergeRollcallCompany(rollcallCompany);
	}


	public void deleteRollcallCompany(String id)throws ApplicationAccessException {
		for (int i = 0; i < id.split(",").length; i++) {
			pipeRollcallCompanyPersistenceIface.deleteRollcallCompany(new DaPipeRollcallCompany(Long.parseLong(id)));
		}
	}

	public void setEnumPersistenceIface(EnumPersistenceIface enumPersistenceIface) {
		this.enumPersistenceIface = enumPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public void setPipeRollcallCompanyPersistenceIface(
			PipeRollcallCompanyPersistenceIface pipeRollcallCompanyPersistenceIface) {
		this.pipeRollcallCompanyPersistenceIface = pipeRollcallCompanyPersistenceIface;
	}

	public void setPipeDangerPersistenceIface(
			PipeDangerPersistenceIface pipeDangerPersistenceIface) {
		this.pipeDangerPersistenceIface = pipeDangerPersistenceIface;
	}
	
}
