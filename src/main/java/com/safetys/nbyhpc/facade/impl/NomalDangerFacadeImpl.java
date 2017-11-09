package com.safetys.nbyhpc.facade.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.util.StringUtils;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.NomalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.Nbyhpc;

public class NomalDangerFacadeImpl implements NomalDangerFacadeIface {

	private NomalDangerPersistenceIface nomalDangerPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private CompanyPersistenceIface companyPersistenceIface;

	private CompanyPassPersistenceIface companyPassPersistenceIface;

	private UserPersistenceIface userPersistenceIface;
	
	private DaDangerImageFacadeIface daDangerImageFacadeIface;

	private PersistenceDao<DaNomalDanger> persistenceDao;

	public List<DaNomalDanger> loadNomalDangers(DaCompany company, UserDetailWrapper userDetail, Pagination pagination, DaNomalDanger nomalDanger, int otherTradeDanger) throws ApplicationAccessException {
		return _loadNomalDangers(null, company, userDetail, pagination, otherTradeDanger, nomalDanger);
	}

	public List<DaNomalDanger> loadNomalDangers1(DaCompany company, UserDetailWrapper userDetail, Pagination pagination, DaNomalDanger nomalDanger, int otherTradeDanger) throws ApplicationAccessException {
		return _loadNomalDangers1(null, company, userDetail, pagination, otherTradeDanger, nomalDanger);
	}

	public List<DaNomalDanger> loadNomalDangers(DaBag bag, UserDetailWrapper userDetail, Pagination pagination, DaNomalDanger nomalDanger, int otherTradeDanger) throws ApplicationAccessException {
		return _loadNomalDangers(bag, null, userDetail, pagination, otherTradeDanger, nomalDanger);
	}

	private List<DaNomalDanger> loadNomalDangers(DaCompany company, DaNomalDanger nomalDanger, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		int nextYear = nomalDanger.getNowYear() + 1;
		if (nomalDanger.getRepair() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.sqlRestriction("this_.completed_date < to_date('" + mDateTime + "','yyyy-MM-dd')"), RestrictionsProxy.eq("dnd.danger", false)));
		} else if (nomalDanger.getRepair() == 0) {
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.isNull("completedDate"), RestrictionsProxy.sqlRestriction("this_.completed_date >= to_date('" + mDateTime + "','yyyy-MM-dd')")));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		// if (userDetail.getUserIndustry() != null &&
		// !"".equals(userDetail.getUserIndustry()) &&
		// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry like '"+userDetail.getUserIndustry()+"' )"));
		// }
		return nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
	}

	public DaNomalDanger loadNomalDanger(DaNomalDanger nomaldanger) throws ApplicationAccessException {
		return nomalDangerPersistenceIface.loadNomalDanger(nomaldanger);
	}

	public DaNomalDanger loadLinkManByBefore(DaCompany company) throws ApplicationAccessException {
		DaNomalDanger normalDanger = new DaNomalDanger();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		List<DaNomalDanger> normalDangers = nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
		if (normalDangers.size() > 0) {
			normalDanger.setLinkMan(normalDangers.get(0).getLinkMan());
			normalDanger.setLinkTel(normalDangers.get(0).getLinkTel());
			normalDanger.setLinkMobile(normalDangers.get(0).getLinkMobile());
			return normalDanger;
		} else
			return null;
	}

	public void createNomalDanger(DaNomalDanger nomalDanger, DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException {
		_createNormalDanger(nomalDanger, company, null, userDetail);
	}

	public void createNomalDanger(DaNomalDanger nomalDanger, DaBag bag, UserDetailWrapper userDetail) throws ApplicationAccessException {

		_createNormalDanger(nomalDanger, null, bag, userDetail);
	}

	public void updateNomalDanger(DaNomalDanger nomalDanger) throws ApplicationAccessException {
		DaNomalDanger nd = nomalDangerPersistenceIface.loadNomalDanger(nomalDanger);
		// System.out.println(nd.isRepaired());
		// if(!nd.isRepaired()){
		// if(nomalDanger.isRepaired()){
		// Calendar cal = Calendar.getInstance();
		// nomalDanger.setCompletedDate(cal.getTime());
		// }
		// }

		// add by huangjl
		if (nomalDanger.isRepaired()) {
			nomalDanger.setCompletedDate(new Date());
		} else {
			nomalDanger.setCompletedDate(null);
		}

		nomalDanger.setIsSynchro(1); // 更新同步状态
		nomalDanger.setDanger(true);
		nomalDanger.setCreateTime(nd.getCreateTime());
		nomalDangerPersistenceIface.mergeNomalDanger(nomalDanger);
	}

	public void deleteNormalDanger(String nomalDangerIds) throws ApplicationAccessException {
		String hql = "update DaNomalDanger set IS_DELETED=1,is_synchro=1 where id in (" + nomalDangerIds + ")";
		nomalDangerPersistenceIface.executeUpdate(hql);
	}
	
	public void deleteNormalDanger1(DaNomalDanger nomalDanger) throws ApplicationAccessException {
		String hql = "delete from DaNomalDanger where  flag ='"+nomalDanger.getFlag()+"'";
		nomalDangerPersistenceIface.executeUpdate(hql);
	}

	private List<DaNomalDanger> loadNomalDangersOfNew(DaCompany company, DaNomalDanger nomalDanger, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		int nextYear = nomalDanger.getNowYear() + 1;

		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		if (nomalDanger.getRepair() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", true));
		} else if (nomalDanger.getRepair() == 0) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));

		return nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
	}

	public List<DaCompany> loadCompanysByIsRepairNew(DaCompany company, DaNomalDanger nomalDanger, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		List<DaCompany> companys = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		int nextYear = nomalDanger.getNowYear() + 1;

		float governMoney1 = nomalDanger.getGovernMoney1();
		float governMoney2 = nomalDanger.getGovernMoney2();

		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				}
			}
		}
		StringBuilder sql = new StringBuilder();
		if (!"".equals(nomalDanger.getRepair())) {
			sql.append(" this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 and is_danger = 1 " + " and create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')");
			// 一般隐患根据填写人所在行业的查询条件
			// if (userDetail.getUserIndustry() != null &&
			// !"".equals(userDetail.getUserIndustry()) &&
			// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			// sql.append(" and user_id in (select id from fk_user_info where user_industry like '"+userDetail.getUserIndustry()+"' )");
			// }
			if (nomalDanger.getRepair() == 0) {
				sql.append(" and is_repaired = 0 ");
			} else if (nomalDanger.getRepair() == 1) {
				sql.append(" and is_repaired = 1 ");
			}
			if (governMoney1 != 0.0) {

				sql.append(" and  govern_money>=" + governMoney1 + "   ");
			}
			if (governMoney2 != 0.0) {
				sql.append(" and  govern_money<=" + governMoney2 + " ");
			}

			sql.append(")");

		}

		if (sql.toString() != null && !"".equals(sql.toString())) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql.toString()));
		}
		if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.createTime"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		for (DaCompany com : companys) {
			List<DaNomalDanger> normalDangers = loadNomalDangersOfNew(com, nomalDanger, userDetail);
			if (normalDangers != null)
				com.setNotRepairCount(normalDangers.size());
			else
				com.setNotRepairCount(0);
		}
		return companys;

	}

	/**
	 * 查询行业的企业的隐患数
	 * 
	 * @param company
	 * @param nomalDanger
	 * @param userDetail
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	/**
	 * public List<DaCompany> loadCompanysByInd(DaCompany company,
	 * UserDetailWrapper userDetail,Pagination pagination) throws
	 * ApplicationAccessException{ List<DaCompany> companys=null;
	 * DetachedCriteriaProxy
	 * detachedCriteriaProxy=DetachedCriteriaProxy.forClass
	 * (DaCompany.class,"dc");
	 * detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm",true)); if
	 * (userDetail.getFifthArea() != null &&
	 * !userDetail.getFifthArea().equals(0L)) {
	 * detachedCriteriaProxy.add(RestrictionsProxy
	 * .eq("dc.fifthArea",userDetail.getFifthArea())); } else if
	 * (userDetail.getFouthArea() != null &&
	 * !userDetail.getFouthArea().equals(0L)) {
	 * detachedCriteriaProxy.add(RestrictionsProxy
	 * .eq("dc.fouthArea",userDetail.getFouthArea())); } else if
	 * (userDetail.getThirdArea() != null &&
	 * !userDetail.getThirdArea().equals(0L)) {
	 * detachedCriteriaProxy.add(RestrictionsProxy
	 * .eq("dc.thirdArea",userDetail.getThirdArea())); } else if
	 * (userDetail.getSecondArea() != null &&
	 * !userDetail.getSecondArea().equals(0L)) {
	 * detachedCriteriaProxy.add(RestrictionsProxy
	 * .eq("dc.secondArea",userDetail.getSecondArea())); } else if
	 * (userDetail.getFirstArea() != null &&
	 * !userDetail.getFirstArea().equals(0L)) {
	 * detachedCriteriaProxy.add(RestrictionsProxy
	 * .eq("dc.firstArea",userDetail.getFirstArea())); } if(company!=null){ if
	 * (company.getCompanyName() != null &&
	 * !"".equals(company.getCompanyName().trim())) {
	 * detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName",
	 * company.getCompanyName().trim(),MatchMode.ANYWHERE)); } if
	 * (company.getFirstArea() != null && company.getFirstArea() != 0L) {
	 * detachedCriteriaProxy
	 * .add(RestrictionsProxy.eq("dc.firstArea",company.getFirstArea())); if
	 * (company.getSecondArea() != null && company.getSecondArea() != 0L) {
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",
	 * company.getSecondArea())); if (company.getThirdArea() != null &&
	 * company.getThirdArea() != 0L) {
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",
	 * company.getThirdArea())); if (company.getFouthArea() != null &&
	 * company.getFouthArea() != 0L) {
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",
	 * company.getFouthArea())); if (company.getFifthArea() != null &&
	 * company.getFifthArea() != 0L) {
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",
	 * company.getFifthArea())); } } } } } if (company.getTradeTypes() != null
	 * && !"".equals(company.getTradeTypes())) { String trade = ""; for (int i =
	 * company.getTradeTypes().split(",").length - 1; i >= 0; i--) { if
	 * (!"".equals(company.getTradeTypes().split(",")[i].trim())) { trade =
	 * company.getTradeTypes().split(",")[i].trim(); break; } } if
	 * (!"".equals(trade)) {
	 * detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(
	 * "this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("
	 * +trade+"))")); } } if(company.getEndTime() != null &&
	 * !"".equals(company.getEndTime())){
	 * detachedCriteriaProxy.add(RestrictionsProxy
	 * .sqlRestriction("this_.create_time <= to_date("
	 * +company.getEndTime()+",'yyyy-MM-dd')")); }
	 * 
	 * } if (userDetail.getUserIndustry() != null &&
	 * !"".equals(userDetail.getUserIndustry()) &&
	 * !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
	 * DaIndustryParameter industryParamenter =
	 * tradeTypePersistenceIface.loadTradeType
	 * (userDetail.getUserIndustry(),Nbyhpc.COMPANY_TRADE);
	 * if(industryParamenter != null){
	 * detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(
	 * "this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
	 * + industryParamenter.getId() + ")")); }else{ return null; } }
	 * detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
	 * companys=companyPersistenceIface.loadCompanies(detachedCriteriaProxy,
	 * pagination); for(DaCompany com : companys){ List<DaNomalDanger>
	 * normalDangers=loadNomalDangerSizes(com); if(normalDangers != null)
	 * com.setNotRepairCount(normalDangers.size()); else
	 * com.setNotRepairCount(0); } return companys;
	 * 
	 * }
	 * 
	 * 
	 * private List<DaNomalDanger> loadNomalDangerSizes(DaCompany company)throws
	 * ApplicationAccessException { DetachedCriteriaProxy detachedCriteriaProxy
	 * = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd"); int
	 * nextYear = nomalDanger.getNowYear()+1;
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger",true));
	 * if(nomalDanger.getRepair()==1){
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", true));
	 * }else if(nomalDanger.getRepair()==0){
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired",false)); }
	 * detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(
	 * "this_.create_time between to_date('"
	 * +nomalDanger.getNowYear()+"-01-01','yyyy-MM-dd') " +
	 * " and to_date('"+nextYear+"-01-01','yyyy-MM-dd')"));
	 * detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId",
	 * company.getId())); return
	 * nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy,
	 * null); }
	 */

	public List<DaCompany> loadCompanysByIsRepair(DaCompany company, DaNomalDanger nomalDanger, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		List<DaCompany> companys = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		int nextYear = nomalDanger.getNowYear() + 1;
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
		StringBuilder sql = new StringBuilder();
		if (!"".equals(nomalDanger.getRepair())) {
			sql.append(" this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 " + " and create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')");
			// 一般隐患根据填写人所在行业的查询条件
			// if (userDetail.getUserIndustry() != null &&
			// !"".equals(userDetail.getUserIndustry()) &&
			// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			// sql.append(" and user_id in (select id from fk_user_info where user_industry like '"+userDetail.getUserIndustry()+"' )");
			// }
			if (nomalDanger.getRepair() == 0) {
				sql.append(" and (completed_date is null or completed_date >= to_date('" + mDateTime + "','yyyy-MM-dd'))");
				sql.append(" and is_danger = 1)");
			} else if (nomalDanger.getRepair() == 1) {
				sql.append(" and (completed_date < to_date('" + mDateTime + "','yyyy-MM-dd') or is_danger=0))");
			}
		}
		if (sql.toString() != null && !"".equals(sql.toString())) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql.toString()));
		}
		if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		for (DaCompany com : companys) {
			List<DaNomalDanger> normalDangers = loadNomalDangers(com, nomalDanger, userDetail);
			if (normalDangers != null)
				com.setNotRepairCount(normalDangers.size());
			else
				com.setNotRepairCount(0);
		}
		return companys;

	}

	public void setNomalDangerPersistenceIface(NomalDangerPersistenceIface nomalDangerPersistenceIface) {
		this.nomalDangerPersistenceIface = nomalDangerPersistenceIface;
	}

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	/**
	 * 添加隐患(按明细保存)
	 */
	public void createMoreNomalDanger(DaNomalDanger nomalDanger, DaCompany company, UserDetailWrapper userDetail, List<DaNomalDanger> nomalDangers) throws ApplicationAccessException {
		if (nomalDangers != null && nomalDangers.size() > 0) {
			for (DaNomalDanger nd : nomalDangers) {
//				System.out.println("company.getId()：" + company.getId());
				// 设置默认为有隐患 add by huangjl 2014-1-9
				nd.setDanger(true);
				nd.setCompanyPassId(company.getId());
				nd.setUserId((long) userDetail.getUserId());
				nd.setLinkMan(nomalDanger.getLinkMan());
				nd.setLinkMobile(nomalDanger.getLinkMobile());
				nd.setLinkTel(nomalDanger.getLinkTel());
				nd.setDanger(true);
				nd.setIsSynchro(1); // 更新同步状态
				if (nd.isRepaired()) {
					Calendar cal = Calendar.getInstance();
					nd.setCompletedDate(cal.getTime());
				}
				if (nd.getType() == 0)
					nd.setType(Nbyhpc.NOMAL_DANGER_TYPE);
				nomalDangerPersistenceIface.createNomalDanger(nd);
			}

		}
	}

	/**
	 * 添加隐患(按个数保存)
	 */
	public void createCountNomalDanger(DaNomalDanger nomalDanger, DaCompany company, UserDetailWrapper userDetail, int yhcount) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		Date  cretime=cal.getTime();
		
		java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
		String flag = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DATE) + time.getHours() + time.getMinutes() + time.getSeconds();
		for (int i = 0; i < yhcount; i++) {
			DaNomalDanger nd = new DaNomalDanger();
			nd.setDanger(true);
			nd.setCompanyPassId(company.getId());
			nd.setUserId((long) userDetail.getUserId());
			nd.setLinkMan(nomalDanger.getLinkMan());
			nd.setLinkMobile(nomalDanger.getLinkMobile());
			nd.setLinkTel(nomalDanger.getLinkTel());
			nd.setIsSynchro(1); // 更新同步状态
			nd.setRepaired(true);
			nd.setType(1327); // 人的不安全行为
			nd.setSecondType(1584181); // 从业人员的不安全行为
			nd.setCompletedDate(cretime);
			nd.setCreateTime(cretime);
			nd.setModifyTime(cretime);
			nd.setGovernMoney(0);
			nd.setAuto(1);
			if (nd.getType() == 0)
				nd.setType(Nbyhpc.NOMAL_DANGER_TYPE);
			nd.setDescription("其他已治理隐患");
			nd.setFlag(flag); // 同组批量添加标志
			nomalDangerPersistenceIface.createNomalDanger(nd);
		}
	}

	private void _createNormalDanger(DaNomalDanger nomalDanger, DaCompany company, DaBag bag, UserDetailWrapper userDetail) throws ApplicationAccessException {
		if (company != null)
			nomalDanger.setCompanyPassId(company.getId());
		if (bag != null)
			nomalDanger.setBagId(bag.getId());
		nomalDanger.setUserId((long) userDetail.getUserId());
		nomalDanger.setIsSynchro(1);
		nomalDangerPersistenceIface.createNomalDanger(nomalDanger);
	}

	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId", (long) userDetail.getUserId()));
		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
	}

	/**
	 * 本单位一般隐患列表(按明细)
	 * 
	 * @param bag
	 * @param company
	 * @param userDetail
	 * @param pagination
	 * @param otherTradeDanger
	 * @param repair
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaNomalDanger> _loadNomalDangers(DaBag bag, DaCompany company, UserDetailWrapper userDetail, Pagination pagination, int otherTradeDanger, DaNomalDanger nomalDanger) throws ApplicationAccessException {
		List<DaNomalDanger> normalDangers = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		int nextYear = nomalDanger.getNowYear() + 1;
		if (company != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		}
		if(nomalDanger!=null&&"1".equals(nomalDanger.getJbFlag())){
			String startDate = "";
			String endDate = "";
			int nYear = nomalDanger.getJbYear()+ 1;
			if (nomalDanger.getIsAllYear() == 1) {
				startDate = nomalDanger.getJbYear() + "-01-01";
				endDate = nYear + "-01-01";
			} else {
				if (nomalDanger.getJbQuarter() == 1) {
					startDate = nomalDanger.getJbYear() + "-01-01";
					endDate =  nomalDanger.getJbYear() + "-04-01";
				} else if (nomalDanger.getJbQuarter() == 2) {
					startDate =  nomalDanger.getJbYear() + "-04-01";
					endDate =  nomalDanger.getJbYear() + "-07-01";
				} else if (nomalDanger.getJbQuarter() == 3) {
					startDate =  nomalDanger.getJbYear() + "-07-01";
					endDate =  nomalDanger.getJbYear() + "-10-01";
				} else if (nomalDanger.getJbQuarter() == 4) {
					startDate =  nomalDanger.getJbYear() + "-10-01";
					endDate = nYear + "-01-01";
				}
			}
			
			
			
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
			if("2".equals(nomalDanger.getRepairState())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" +startDate + "','yyyy-MM-dd') " + " and to_date('" + endDate + "','yyyy-MM-dd')"));
			}else if("0".equals(nomalDanger.getRepairState())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" +startDate + "','yyyy-MM-dd') " + " and to_date('" + endDate + "','yyyy-MM-dd') and  this_.is_repaired = 0  "));
			}
			
		}else{
			if (nomalDanger.getRepair() == 1) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", true));
			} else if (nomalDanger.getRepair() == 0) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
			}
//			System.out.println("------=====" + Nbyhpc.USER_INDUSTRY_COMPANY);
			if (!Nbyhpc.USER_INDUSTRY_COMPANY.equals(userDetail.getUserIndustry())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));
			}
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.auto", 0));
		}
		
		// if (userDetail.getUserIndustry() != null &&
		// !"".equals(userDetail.getUserIndustry()) &&
		// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
		//
		// if(otherTradeDanger==1){
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id not in (select id from fk_user_info where user_industry ='"+userDetail.getUserIndustry()+"')"));
		// }else{
		// if(Nbyhpc.USER_INDUSTRY_COMPANY.equals(userDetail.getUserIndustry())){
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.userId",
		// (long)userDetail.getUserId()));
		// }else{
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry ='"+userDetail.getUserIndustry()+"')"));
		// }
		// }
		// }
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.createTime"));
		normalDangers = nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
		if (normalDangers.size() > 0 && otherTradeDanger == 1) {
			for (DaNomalDanger nd : normalDangers) {
				FkUser user = userPersistenceIface.loadUser(nd.getUserId());
				DaIndustryParameter indusrtry = tradeTypePersistenceIface.loadTradeType(user.getFkUserInfo().getUserIndustry(), Nbyhpc.COMPANY_TRADE);
				if (indusrtry != null) {
					nd.setIndustryName(indusrtry.getName());
				}
			}
		}
		return normalDangers;
	}

	/**
	 * 本单位一般隐患列表(按个数)
	 * 
	 * @param bag
	 * @param company
	 * @param userDetail
	 * @param pagination
	 * @param otherTradeDanger
	 * @param repair
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaNomalDanger> _loadNomalDangers1(DaBag bag, DaCompany company, UserDetailWrapper userDetail, Pagination pagination, int otherTradeDanger, DaNomalDanger nomalDanger) throws ApplicationAccessException {
		List<DaNomalDanger> normalDangers = new ArrayList<DaNomalDanger>();
		String sql = "select to_char(t.create_time, 'yyyy-MM-dd'), count(*) , completed_date,flag from DA_NORMAL_DANGER t where   t.par_da_com_id=" + company.getId() + "  and  t.is_deleted=0  and   t.user_id=" + userDetail.getUserId() + "  and  t.auto=1 group by to_char(t.create_time, 'yyyy-MM-dd'), flag,completed_date  order  by  t.flag  desc";
//		System.out.println("sql: "+sql);
		ResultSet rs = nomalDangerPersistenceIface.findBySql(sql);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int year1 = 0;
		int month1 = 0;
//		System.out.println("year:" + year + " month: " + month);
		try {
			while (rs.next()) {
				DaNomalDanger dn = new DaNomalDanger();
				dn.setYhcount(rs.getInt(2));
				dn.setCreateTime(rs.getDate(1));
				dn.setCompletedDate(rs.getDate(3));
				dn.setFlag(rs.getString(4));
				if (rs.getDate(1) != null) {
					year1 = Integer.parseInt(rs.getDate(1).toString().substring(0, 4));
					month1 = Integer.parseInt(rs.getDate(1).toString().substring(6, 7));
				}
//				System.out.println("year1:" + year1 + " month1: " + month1);
				if (year1 == year && month1 == month) {
					dn.setSysFlag("1"); // 是否当月
				} else {
					dn.setSysFlag("0"); // 是否当月
				}
				normalDangers.add(dn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return normalDangers;
	}
	
	/**
	 * 当月一般隐患条数
	 */
	public int  loadnowYhCount(DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException {
		int count=0; 
		Calendar  cal=Calendar.getInstance();
		int year1=cal.get(Calendar.YEAR);
		int month1=cal.get(Calendar.MONTH)+1;
		int year2=0;
		int month2=0;
		if (month1==12){
			year2=year1+1;
			month2=1;
		}else{
			year2=year1;
			month2=month1+1;
		}
		
		String sql = "select count(*) from DA_NORMAL_DANGER  t  where  t.par_da_com_id = "+company.getId()+""+
					 "and t.create_time> = ( to_date('"+year1+"-"+month1+"-1','yyyy-MM-dd'))   and t.create_time < ( to_date('"+year2+"-"+month2+"-1','yyyy-MM-dd'))"+
					 "and  t.user_id="+userDetail.getUserId()+"  and  t.is_deleted=0  and   t.auto=0";
//		System.out.println("sql:"+sql);
		ResultSet rel= nomalDangerPersistenceIface.findBySql(sql);
		try {
			if (rel.next()){
				count=rel.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  count;
	}

	@Override
	public List<DaNomalDanger> loadNomalDangers(DaCompany company, Pagination pagination, DaNomalDanger nomalDanger, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
		hql.append(" from DaNomalDanger dnd where dnd.deleted = false and dnd.danger = true and dnd.auto = 0 ");
		Date dateNow = new Date();
		String currentMonthDay = DateUtils.date2Str(dateNow, "yyyy-MM") + "-01";
		String nextMonthDay = DateUtils.date2Str(DateUtils.plusMonth(dateNow, 1), "yyyy-MM") + "-01";
		hql.append(" and createTime between to_date('").append(currentMonthDay).append("','yyyy-MM-dd') ")
		   .append(" and to_date('").append(nextMonthDay).append("','yyyy-MM-dd') ");
		hql.append(" and dnd.companyPassId = ? ");
		params.add(company.getId());
		if (nomalDanger.getRepair() == 1) {
			hql.append(" and dnd.repaired = true ");
		} else if (nomalDanger.getRepair() == 0) {
			hql.append(" and dnd.repaired = false ");
		}
		if (StringUtils.hasText(nomalDanger.getDescription())) {
			hql.append(" and dnd.description like ? ");
			params.add("%" + nomalDanger.getDescription() +"%");
		}
		
		hql.append(" order by dnd.createTime desc ");
		//有一个List<DaNomalDanger> loadNomalDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
		//注释里的方法有BUG， 如果不传totalCount 会报错， 传了，那么总数不会改变了
		return nomalDangerPersistenceIface.loadNomalDangers(hql.toString(), pagination, params.toArray());
	}

	@Override
	public void saveNormalDanger(DaNomalDanger daNomalDanger, Long userId, DaCompany company, List<File> upFiles, String deletedFileIds) throws Exception {
		//TODO 这里目前只考虑是企业用户增加修改隐患
		daNomalDanger.setLinkMan(company.getSafetyMngPerson());
		daNomalDanger.setLinkTel(company.getSafetyMngPersonPhone());
		daNomalDanger.setUserId(userId);
		daNomalDanger.setCompanyPassId(company.getId());
		daNomalDanger.setDanger(true);
		daNomalDanger.setIsSynchro(1);
		if (daNomalDanger.isRepaired()) {
			Calendar cal = Calendar.getInstance();
			daNomalDanger.setCompletedDate(cal.getTime());
		} else {
			daNomalDanger.setCompletedDate(null);
		}
		if (daNomalDanger.getId() > 0) {
			nomalDangerPersistenceIface.updateNomalDanger(daNomalDanger);
		} else {
			daNomalDanger.setFromApp(true);
			nomalDangerPersistenceIface.createNomalDanger(daNomalDanger);
		}
		daDangerImageFacadeIface.createByApp(daNomalDanger, upFiles);
		if (StringUtils.hasText(deletedFileIds)) {
			String[] ids = deletedFileIds.split(",");
			for (String id : ids) {
				DaDangerImage image = daDangerImageFacadeIface.findById(Long.valueOf(id));
				daDangerImageFacadeIface.delete(image);
			}
		}
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setCompanyPassPersistenceIface(CompanyPassPersistenceIface companyPassPersistenceIface) {
		this.companyPassPersistenceIface = companyPassPersistenceIface;
	}

	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	/**
	 * 修改录入时间超过7天的未治理隐患治理状态为已治理
	 * 
	 * @param days
	 */
	public void updateNomalDangerRepaired(int days) throws ApplicationAccessException {
		String hql = "update DaNomalDanger set repaired=1,completedDate=SYSDATE,isSynchro=1 where repaired=0 and deleted=0  and danger=1  and createTime+" + days + "<=SYSDATE";
		nomalDangerPersistenceIface.executeUpdate(hql);
		// 取得所有未治理的隐患数
		/**
		 * Date newDate=new Date(); String hql=
		 * "from DaNomalDanger where repaired=0 and danger=1 and deleted=0";
		 * 
		 * List<DaNomalDanger>
		 * daNomalDangers=persistenceDao.executeHQLQuery(hql);
		 * if(daNomalDangers!=null&&daNomalDangers.size()>0){ for (DaNomalDanger
		 * daNomalDanger : daNomalDangers) { //如果创建时间加上7天，还小于当前时间的话，则说明不超过七天
		 * Date createTime=daNomalDanger.getCreateTime();
		 * if(!(createTime.getTime()+7*24*3600*1000<newDate.getTime())){
		 * System.out.println("治理一般隐患id="+daNomalDanger.getId());
		 * daNomalDanger.setRepaired(true);
		 * daNomalDanger.setCompletedDate(newDate);
		 * nomalDangerPersistenceIface.updateNomalDanger(daNomalDanger); }
		 * 
		 * }
		 * 
		 * }
		 ***/
	}

	public PersistenceDao<DaNomalDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaNomalDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public void setDaDangerImageFacadeIface(DaDangerImageFacadeIface daDangerImageFacadeIface) {
		this.daDangerImageFacadeIface = daDangerImageFacadeIface;
	}

}
