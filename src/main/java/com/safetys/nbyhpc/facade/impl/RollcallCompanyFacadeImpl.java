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
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.RollcallCompanyFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @(#) RollcallCompanyFacadeImpl.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public class RollcallCompanyFacadeImpl implements RollcallCompanyFacadeIface {

	private RollcallCompanyPersistenceIface rollcallCompanyPersistenceIface;
	
	private EnumPersistenceIface enumPersistenceIface;
	
	private AreaPersistenceIface areaPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;
	
	private CompanyPersistenceIface companyPersistenceIface;
	
	private CompanyPassPersistenceIface companyPassPersistenceIface;
	
	private DangerPersistenceIface dangerPersistenceIface;

	public Long createRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return rollcallCompanyPersistenceIface.createRollcallCompany(rollcallCompany);
	}

	public DaRollcallCompany loadRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return rollcallCompanyPersistenceIface.loadRollcallCompany(rollcallCompany);
	}
	
	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException{
		return dangerPersistenceIface.loadDanger(danger);
	}
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}

	public List<DaRollcallCompany> loadRollcallCompanies(Long dangerId) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallCompany.class, "drc");
		if (dangerId != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("drc.daDanger.id", dangerId));
		}
		return rollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, null);
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
	
	public List<DaRollcallCompany> loadRollcallCompaniesOfLevel(DaRollcallCompany rollcallCompany,DaCompany company, Pagination pagination,UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("daDanger", "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.createCriteria("daCompany", "dc");

		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
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
			if (rollcallCompany.getDaDanger() != null) {
				if(rollcallCompany.getDaDanger().getDangerAdd() != null && !"".equals(rollcallCompany.getDaDanger().getDangerAdd().trim())){
					detachedCriteriaProxy.add(RestrictionsProxy.like("dd.dangerAdd", rollcallCompany.getDaDanger().getDangerAdd().trim(), MatchMode.ANYWHERE));
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
		if(company!= null){
			if(company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())){
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if(company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
				if(company.getSecondArea() != null && company.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
					if(company.getThirdArea() != null && company.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
						if(company.getFouthArea() != null && company.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
							if(company.getFifthArea() != null && company.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
							}
						}
					}
				}
			}
		}
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
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(),Nbyhpc.COMPANY_TRADE);
			if(industryParamenter != null){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("dcp2_.par_da_com_id in "
					+ "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
					+ industryParamenter.getId() + ")"));
			}else{
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
//		System.out.println("========================");
		return rollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<DaRollcallCompany> loadRollcallCompaniesByCompany(DaCompany company, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("daDanger", "dd");
		if(company!= null){
			if(company.getId() != null){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.daCompanyPass.id", company.getId()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("drc.modifyTime"));
		return rollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long)userDetail.getUserId()));
		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
	}
	
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
	
	public List<DaCompany> loadCompanysByNotice(DaCompany company,UserDetailWrapper userDetail,Pagination pagination) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaCompany.class,"dc");
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
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
		if(company!=null){
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",company.getFirstArea()));
				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
							}
						}
					}
				}
			}
		}
		if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(),Nbyhpc.COMPANY_TRADE);
			if(industryParamenter != null){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
									+ industryParamenter.getId() + ")"));
			}else{
				return null;
			}
		}
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<DaRollcallCompany> loadRollcallCompaniesOfLevel(DaCompany company,Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("daDanger", "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.createCriteria("daCompany", "dc");
		if(company!= null){
			if(company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())){
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if(company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
				if(company.getSecondArea() != null && company.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
					if(company.getThirdArea() != null && company.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
						if(company.getFouthArea() != null && company.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
							if(company.getFifthArea() != null && company.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
							}
						}
					}
				}
			}
		}
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
		return rollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}

	public void updateRollcallCompany(DaRollcallCompany rollcallCompany)
			throws ApplicationAccessException {
		DaRollcallCompany oldRollcallCompany = loadRollcallCompany(rollcallCompany);
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
		rollcallCompany.setIsSynchro(1);
		rollcallCompanyPersistenceIface.mergeRollcallCompany(rollcallCompany);
	}
	
	
	public void updateRollcallCompanyForNotice(DaRollcallCompany rollcallCompany)
			throws ApplicationAccessException {
		DaRollcallCompany oldRollcallCompany = loadRollcallCompany(rollcallCompany);
		rollcallCompany.setCreateTime(oldRollcallCompany.getCreateTime());
		rollcallCompany.setUserId(oldRollcallCompany.getUserId());
		rollcallCompany.setLevel(oldRollcallCompany.getLevel());
		rollcallCompany.setGovment(oldRollcallCompany.getGovment());
		rollcallCompany.setDepartment(oldRollcallCompany.getDepartment());
		rollcallCompany.setCompleteTime(oldRollcallCompany.getCompleteTime());
		rollcallCompanyPersistenceIface.mergeRollcallCompany(rollcallCompany);
	}


	public void deleteRollcallCompany(String id)throws ApplicationAccessException {
		for (int i = 0; i < id.split(",").length; i++) {
			rollcallCompanyPersistenceIface.deleteRollcallCompany(new DaRollcallCompany(Long.parseLong(id)));
		}
	}
	
	public void setRollcallCompanyPersistenceIface(
			RollcallCompanyPersistenceIface rollcallCompanyPersistenceIface) {
		this.rollcallCompanyPersistenceIface = rollcallCompanyPersistenceIface;
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

	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setCompanyPassPersistenceIface(
			CompanyPassPersistenceIface companyPassPersistenceIface) {
		this.companyPassPersistenceIface = companyPassPersistenceIface;
	}

	public void setDangerPersistenceIface(
			DangerPersistenceIface dangerPersistenceIface) {
		this.dangerPersistenceIface = dangerPersistenceIface;
	}
}
