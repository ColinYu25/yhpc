package com.safetys.nbyhpc.facade.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.persistence.iface.PipeLinePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeNomalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.PipeNomalDangerFacadeIface;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.util.Nbyhpc;

public class PipeNomalDangerFacadeImpl implements PipeNomalDangerFacadeIface {

	private PipeNomalDangerPersistenceIface pipeNomalDangerPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;
	
	private PipeLinePersistenceIface pipeLinePersistenceIface;

	private UserPersistenceIface userPersistenceIface;
	
	private BasePersistenceImpl persistenceImpl;

	public List<DaPipeNomalDanger> loadNomalDangers(DaPipelineInfo pipeLine, DaPipeNomalDanger nomalDanger, UserDetailWrapper userDetail, Pagination pagination
			) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.id", pipeLine.getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));//去掉无隐患纪录
		if(null != nomalDanger){
			if (nomalDanger.getRepair() == 1) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", true));
			}  
			if (nomalDanger.getRepair() == 0) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
			}
		}
		//		if (!Nbyhpc.USER_INDUSTRY_COMPANY.equals(userDetail.getUserIndustry())) {
//			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
//					+ nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear
//					+ "-01-01','yyyy-MM-dd')"));
//		}
//		if (userDetail.getUserIndustry() != null &&
//				!"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
//			detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.id"));
//		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.createTime"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.id"));
		return pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
	}

	private List<DaPipeNomalDanger> loadNomalDangers(DaPipelineInfo pipeLine, DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.id", pipeLine.getId()));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		int nextYear = nomalDanger.getNowYear() + 1;
		if (nomalDanger.getRepair() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy
					.sqlRestriction("this_.completed_date < to_date('" + mDateTime + "','yyyy-MM-dd')"),
					RestrictionsProxy.eq("dnd.danger", false)));
		} else if (nomalDanger.getRepair() == 0) {
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.isNull("completedDate"), RestrictionsProxy
					.sqlRestriction("this_.completed_date >= to_date('" + mDateTime + "','yyyy-MM-dd')")));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
				+ nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear
				+ "-01-01','yyyy-MM-dd')"));
		//detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		// if (userDetail.getUserIndustry() != null &&
		// !"".equals(userDetail.getUserIndustry()) &&
		// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry like '"+userDetail.getUserIndustry()+"' )"));
		// }
		return pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
	}

	public DaPipeNomalDanger loadNomalDanger(DaPipeNomalDanger nomaldanger) throws ApplicationAccessException {
		return pipeNomalDangerPersistenceIface.loadNomalDanger(nomaldanger);
	}

	public DaPipeNomalDanger loadLinkManByBefore(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		DaPipeNomalDanger normalDanger = new DaPipeNomalDanger();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.id", pipeLine.getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		List<DaPipeNomalDanger> normalDangers = pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
		if (normalDangers.size() > 0) {
			normalDanger.setLinkMan(normalDangers.get(0).getLinkMan());
			normalDanger.setLinkTel(normalDangers.get(0).getLinkTel());
			normalDanger.setLinkMobile(normalDangers.get(0).getLinkMobile());
			return normalDanger;
		} else
			return null;
	}

	public void createNomalDanger(DaPipeNomalDanger nomalDanger, DaPipelineInfo pipeLine, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		_createNormalDanger(nomalDanger, pipeLine, userDetail);
	}

	public void createNomalDanger(DaPipeNomalDanger nomalDanger, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		_createNormalDanger(nomalDanger, null, userDetail);
	}

	public void updateNomalDanger(DaPipeNomalDanger nomalDanger) throws ApplicationAccessException {
		DaPipeNomalDanger nd = pipeNomalDangerPersistenceIface.loadNomalDanger(nomalDanger);
		 if(!nd.isRepaired()){
			 if(nomalDanger.isRepaired()){
				 Calendar cal = Calendar.getInstance();
				 nomalDanger.setCompletedDate(cal.getTime());
			 }
		 }
		nomalDanger.setCreateTime(nd.getCreateTime());
		pipeNomalDangerPersistenceIface.mergeNomalDanger(nomalDanger);
	}
	
	public List<DaPipeNomalDanger> loadNormalDangers(String nomalDangerIds) throws ApplicationAccessException {
		String hql = "from DaPipeNomalDanger where IS_DELETED=0 and id in (" + nomalDangerIds + ")";
		return persistenceImpl.find(hql, null, null);
	}

	public void deleteNormalDanger(String nomalDangerIds) throws ApplicationAccessException {
		String hql = "update DaPipeNomalDanger set IS_DELETED=1 where id in (" + nomalDangerIds + ")";
		pipeNomalDangerPersistenceIface.executeUpdate(hql);
	}

	private List<DaPipeNomalDanger> loadNomalDangersOfNew(DaPipelineInfo pipeLine, DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		int nextYear = nomalDanger.getNowYear() + 1;
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		if (nomalDanger.getRepair() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", true));
		} else if (nomalDanger.getRepair() == 0) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
				+ nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear
				+ "-01-01','yyyy-MM-dd')"));
		//detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		return pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
	}

	public List<DaPipelineInfo> loadCompanysByIsRepairNew(DaPipelineInfo pipeLine, DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		List<DaPipelineInfo> companys = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dc");
		int nextYear = nomalDanger.getNowYear() + 1;
		detachedCriteriaProxy.createCriteria("dc.DaPipelineInfoPass", "dcp");
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
//		if (company != null) {
//			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
//				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),
//						MatchMode.ANYWHERE));
//			}
//			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
//			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
//				String trade = "";
//				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
//					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
//						trade = company.getTradeTypes().split(",")[i].trim();
//						break;
//					}
//				}
//				if (!"".equals(trade)) {
//					detachedCriteriaProxy
//							.add(RestrictionsProxy
//									.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("
//											+ trade + "))"));
//				}
//			}
//		}
		StringBuilder sql = new StringBuilder();
		if (!"".equals(nomalDanger.getRepair())) {
			sql.append(" this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 and is_danger = 1 "
					+ " and create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') "
					+ " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')");
			// 一般隐患根据填写人所在行业的查询条件
			// if (userDetail.getUserIndustry() != null &&
			// !"".equals(userDetail.getUserIndustry()) &&
			// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			// sql.append(" and user_id in (select id from fk_user_info where user_industry like '"+userDetail.getUserIndustry()+"' )");
			// }
			if (nomalDanger.getRepair() == 0) {
				sql.append(" and is_repaired = 0 )");
			} else if (nomalDanger.getRepair() == 1) {
				sql.append(" and is_repaired = 1 )");
			}
		}
//		System.out.println("sql: "+sql);
		if (sql.toString() != null && !"".equals(sql.toString())) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql.toString()));
		}
		if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry())
				&& !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail
					.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy
						.add(RestrictionsProxy
								.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
										+ industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
//		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
//		for (DaPipelineInfo com : companys) {
//			List<DaPipeNomalDanger> normalDangers = loadNomalDangersOfNew(com, nomalDanger, userDetail);
//			if (normalDangers != null)
//				com.setNotRepairCount(normalDangers.size());
//			else
//				com.setNotRepairCount(0);
//		}
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
	 * public List<DaPipelineInfo> loadCompanysByInd(DaPipelineInfo pipeLine,
	 * UserDetailWrapper userDetail,Pagination pagination) throws
	 * ApplicationAccessException{ List<DaPipelineInfo> companys=null;
	 * DetachedCriteriaProxy
	 * detachedCriteriaProxy=DetachedCriteriaProxy.forClass
	 * (DaPipelineInfo.class,"dc");
	 * detachedCriteriaProxy.createCriteria("dc.DaPipelineInfoPass", "dcp");
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
	 * detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("
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
	 * detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
	 * + industryParamenter.getId() + ")")); }else{ return null; } }
	 * detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
	 * companys=companyPersistenceIface.loadCompanies(detachedCriteriaProxy,
	 * pagination); for(DaPipelineInfo com : companys){ List<DaPipeNomalDanger>
	 * normalDangers=loadNomalDangerSizes(com); if(normalDangers != null)
	 * com.setNotRepairCount(normalDangers.size()); else
	 * com.setNotRepairCount(0); } return companys;
	 * 
	 * }
	 * 
	 * 
	 * private List<DaPipeNomalDanger> loadNomalDangerSizes(DaPipelineInfo pipeLine)throws
	 * ApplicationAccessException { DetachedCriteriaProxy detachedCriteriaProxy
	 * = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd"); int
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
	 * pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy,
	 * null); }
	 */

	public List<DaPipelineInfo> loadCompanysByIsRepair(DaPipelineInfo pipeLine, DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		List<DaPipelineInfo> companys = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dc");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		int nextYear = nomalDanger.getNowYear() + 1;
		detachedCriteriaProxy.createCriteria("dc.DaPipelineInfoPass", "dcp");
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
//		if (company != null) {
//			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
//				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),
//						MatchMode.ANYWHERE));
//			}
//			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
		StringBuilder sql = new StringBuilder();
		if (!"".equals(nomalDanger.getRepair())) {
			sql.append(" this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 "
					+ " and create_time between to_date('" + nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') "
					+ " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')");
			// 一般隐患根据填写人所在行业的查询条件
			// if (userDetail.getUserIndustry() != null &&
			// !"".equals(userDetail.getUserIndustry()) &&
			// !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			// sql.append(" and user_id in (select id from fk_user_info where user_industry like '"+userDetail.getUserIndustry()+"' )");
			// }
			if (nomalDanger.getRepair() == 0) {
				sql.append(" and (completed_date is null or completed_date >= to_date('" + mDateTime
						+ "','yyyy-MM-dd'))");
				sql.append(" and is_danger = 1)");
			} else if (nomalDanger.getRepair() == 1) {
				sql.append(" and (completed_date < to_date('" + mDateTime + "','yyyy-MM-dd') or is_danger=0))");
			}
		}
		if (sql.toString() != null && !"".equals(sql.toString())) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql.toString()));
		}
		if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry())
				&& !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail
					.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy
						.add(RestrictionsProxy
								.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id ="
										+ industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
//		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
//		for (DaPipelineInfo com : companys) {
//			List<DaPipeNomalDanger> normalDangers = loadNomalDangers(com, nomalDanger, userDetail);
//			if (normalDangers != null)
//				com.setNotRepairCount(normalDangers.size());
//			else
//				com.setNotRepairCount(0);
//		}
		return companys;

	}
	
	/**
	 * 一般隐患治理（已经治理和未治理管道隐患查询）
	 * @param entity
	 * @param com
	 * @param flag（true已治理，false未治理）
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipeLinesUnGorverOrGorver(DaPipelineInfo entity, DaCompany com, Boolean flag, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dpli");
		detachedCriteriaProxy.createAlias("daPipelineCompanyinfo", "dplci").createAlias("dplci.company", "dc");
		if(null!=com){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.id", com.getId()));
		}else{//安监、城管、交通、发改委
			String userIndustry = userDetail.getUserIndustry();
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(null != userIndustry){
//				if("anjian".equals(userIndustry)){
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 3));
//				}
				if("jiaotong".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 4));
				}
				if("chengguan".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dpli.type", 0), RestrictionsProxy.eq("dpli.type", 2)));
				}
				if("fagai".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 1));
				}
			}
		}
		if (entity != null) {
			//管道名称
			if(null!=entity.getName() && !"".equals(entity.getName().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.name", entity.getName().trim(), MatchMode.ANYWHERE));
			//管道起点
			if(null!=entity.getStartPoint() && !"".equals(entity.getStartPoint().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.startPoint", entity.getStartPoint().trim(), MatchMode.ANYWHERE));
			//管道止点
			if(null!=entity.getEndPoint() && !"".equals(entity.getEndPoint().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.endPoint", entity.getEndPoint().trim(), MatchMode.ANYWHERE));
			//管道材质
			if(null!=entity.getMaterial() && !"".equals(entity.getMaterial().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.material", entity.getMaterial().trim(), MatchMode.ANYWHERE));
			//传输介质
			if(null!=entity.getMedium() && !"".equals(entity.getMedium().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.medium", entity.getMedium().trim(), MatchMode.ANYWHERE));
			//管道种类
			if(entity.getType()>-1)
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", entity.getType()));
			//铺设方式
			if(entity.getBuildType()>0)//0查询全部
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.buildType", entity.getBuildType()));
		}
		if (entity != null && null!=entity.getDaPipelineCompanyinfo() && null!=entity.getDaPipelineCompanyinfo().getCompany()) {
			DaCompany company = entity.getDaPipelineCompanyinfo().getCompany();
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(),MatchMode.ANYWHERE));
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
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy
							.add(RestrictionsProxy
									.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("
											+ trade + "))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		StringBuilder sql = new StringBuilder();
		Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
		sql.append(" this_.id in (select par_da_pipe_id from da_pipe_normal_danger where is_deleted=0 and is_danger=1"
				+ " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') "
				+ " and to_date('" + (year + 1) + "-01-01','yyyy-MM-dd')");
		if (!flag) {//未治理
			sql.append(" and is_repaired = 0 )");
		}
		if (flag) {//已治理
			sql.append(" and is_repaired = 1 )");
		}
		
//	System.out.println("已治理sql: "+sql);	
		if (sql.toString() != null && !"".equals(sql.toString())) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql.toString()));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		List<DaPipelineInfo> pipeLines = pipeLinePersistenceIface.loadPipeLines(detachedCriteriaProxy, pagination);
		for (DaPipelineInfo pipeLine : pipeLines) {
			List<DaPipeNomalDanger> normalDangers = loadNomalDangersByPipeLine(pipeLine, flag);
			int size = (normalDangers == null) ? 0 : normalDangers.size();
			if(flag)
				pipeLine.setRepairCount(size);
			else
				pipeLine.setUnRepairCount(size);
		}
		return pipeLines;
	}
	
	/**
	 * 查询每个管道的目标隐患数据
	 * @param pipeLine
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	private List<DaPipeNomalDanger> loadNomalDangersByPipeLine(DaPipelineInfo pipeLine, Boolean flag) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.id", pipeLine.getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", flag));
//		Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
//				+ year + "-01-01','yyyy-MM-dd') " + " and to_date('" + (year+1) + "-01-01','yyyy-MM-dd')"));
		List<DaPipeNomalDanger> normalDangers = pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
		return normalDangers;
	}

	/**
	 * 批量保存一般隐患
	 */
	public void createNomalDangers(DaPipeNomalDanger n, DaPipelineInfo pipeLine, UserDetailWrapper userDetail,
			List<DaPipeNomalDanger> nomalDangers) throws ApplicationAccessException {
		if (nomalDangers != null && nomalDangers.size() > 0) {
			for (DaPipeNomalDanger nd : nomalDangers) {
				if(null != nd){
					nd.setDanger(true);//去掉有无隐患  默认全部为有隐患
					nd.setPipeLine(pipeLine);
					nd.setUserId((long) userDetail.getUserId());
					nd.setLinkMan(n.getLinkMan());
					nd.setLinkMobile(n.getLinkMobile());
					nd.setLinkTel(n.getLinkTel());
					nd.setFillDate(new Date());
					nd.setChargePerson(n.getChargePerson());
					if (nd.isRepaired()){
						Calendar cal = Calendar.getInstance();
						nd.setCompletedDate(cal.getTime());
					}
					if (!nd.isDanger()){
						nd.setRepaired(true);
					}
					if (null!=nd.getType() && nd.getType() == 0)
						nd.setType(Nbyhpc.PIPE_NOMAL_DANGER_TYPE);
					pipeNomalDangerPersistenceIface.mergeNomalDanger(nd);
				}
			}
		}
	}
	
	/**
	 * 批量保存一般隐患(企业批量添加无隐患)
	 */
	public void addWuNomalDangers(DaCompany c, String ids, UserDetailWrapper userDetail) throws ApplicationAccessException {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if(null != id && !"".equals(id.trim())){
				DaPipeNomalDanger nd = new DaPipeNomalDanger();
				DaPipelineInfo pl = new DaPipelineInfo();
				pl.setId(Long.valueOf(id.trim()));
//				System.out.println("pl.getId()"+pl.getId());
//				System.out.println("company.id: "+pl.getCompany().getId());
				nd.setPipeLine(pipeLinePersistenceIface.loadPipeLine(pl));
//				System.out.println("用户id: "+userDetail.getUserId());
				nd.setUserId((long) userDetail.getUserId());
				nd.setLinkMan(c.getFddelegate());
				nd.setLinkMobile(c.getPhoneCode());
				nd.setLinkTel(c.getPhoneCode());
				nd.setFillDate(new Date());
				nd.setChargePerson(c.getFddelegate());
//					if (nd.isRepaired()){
//						Calendar cal = Calendar.getInstance();
//						nd.setCompletedDate(cal.getTime());
//					}
				if (!nd.isDanger()){
					nd.setRepaired(true);
				}
//				if (null!=nd.getType() && nd.getType() == 0)
//					nd.setType(Nbyhpc.PIPE_NOMAL_DANGER_TYPE);
				pipeNomalDangerPersistenceIface.createNomalDanger(nd);
			}
		}
	}
	
	/**
	 * 保存一般隐患
	 * @param nomalDanger
	 * @param pipeLine
	 * @param userDetail
	 * @throws ApplicationAccessException
	 */
	private void _createNormalDanger(DaPipeNomalDanger nomalDanger, DaPipelineInfo pipeLine,
			UserDetailWrapper userDetail) throws ApplicationAccessException {
		nomalDanger.setPipeLine(pipeLine);
		nomalDanger.setUserId((long) userDetail.getUserId());
		pipeNomalDangerPersistenceIface.createNomalDanger(nomalDanger);
	}

	/**
	 * 本单位一般隐患列表
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
	public List<DaPipeNomalDanger> _loadNomalDangers(DaPipelineInfo pipeLine, DaPipeNomalDanger nomalDanger, UserDetailWrapper userDetail,
			Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		int nextYear = nomalDanger.getNowYear() + 1;
		if (nomalDanger.getRepair() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", true));
		} else if (nomalDanger.getRepair() == 0) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
		}
		if (!Nbyhpc.USER_INDUSTRY_COMPANY.equals(userDetail.getUserIndustry())) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
					+ nomalDanger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear
					+ "-01-01','yyyy-MM-dd')"));
		}
		return pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
	}

	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public void setPipeNomalDangerPersistenceIface(
			PipeNomalDangerPersistenceIface pipeNomalDangerPersistenceIface) {
		this.pipeNomalDangerPersistenceIface = pipeNomalDangerPersistenceIface;
	}

	public void setPipeLinePersistenceIface(
			PipeLinePersistenceIface pipeLinePersistenceIface) {
		this.pipeLinePersistenceIface = pipeLinePersistenceIface;
	}

}
