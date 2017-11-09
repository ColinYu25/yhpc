package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.tool.hbm2x.StringUtils;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PubCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.facade.iface.DangerFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class DangerFacadeImpl implements DangerFacadeIface {

	private DangerPersistenceIface dangerPersistenceIface;

	private CompanyPersistenceIface companyPersistenceIface;

	private CompanyPassPersistenceIface companyPassPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;
	private PubCompanyPersistenceIface pubCompanyPersistenceIface;
	
	private DaDangerImageFacadeIface daDangerImageFacadeIface;

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void createDanger(DaDanger danger) throws ApplicationAccessException {
//		System.out.println("--------------------");
//		System.out.println("no: " + getHiddenNum(danger));
		danger.setDangerNo(getHiddenNum(danger));
		danger.setIsSynchro(1);// 更新同步状态
		dangerPersistenceIface.createDanger(danger);
		daDangerImageFacadeIface.createByDanger(danger);
	}

	public List<DaIndustryParameter> loadIndustrysForDanger() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id = (select id from da_industry_parameter where depiction = '" + Nbyhpc.DANGER_TYPE + "')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.TROUBLE_TYPE));
		// detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId", (long) userDetail.getUserId()));
		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
	}
	
	public List<DaCompany> loadCompanyIdByNum(String num) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "da");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.setup_number='"+num+"'   or   this_.regnum='"+num+"' )"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}

	public void deleteDangers(String ids) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			dangerPersistenceIface.deleteDanger(new DaDanger(Long.parseLong(ids.split(",")[i].trim())));
		}
	}

	public void deleteDanger(DaDanger danger) throws ApplicationAccessException {
		dangerPersistenceIface.deleteDanger(danger);
	}

	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException {
		return dangerPersistenceIface.loadDanger(danger);
	}

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}

	public List<DaCompany> loadCompanyByRegNum(DaCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (company != null) {
			if (!StringUtils.isEmpty(company.getRegNum())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.regNum", company.getRegNum()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}

	public List<DaDanger> loadDangers(DaDanger danger, DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		if (company != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.daCompanyPass.id", company.getId()));
		}
		if(danger!=null&&"1".equals(danger.getJbFlag())){
			
			String startDate = "";
			String endDate = "";
			int nYear = danger.getJbYear()+ 1;
			
			if (danger.getIsAllYear() == 1) {
				startDate = danger.getJbYear() + "-01-01";
				endDate = nYear + "-01-01";
			} else {
				if (danger.getJbQuarter() == 1) {
					startDate = danger.getJbYear() + "-01-01";
					endDate =  danger.getJbYear() + "-04-01";
				} else if (danger.getJbQuarter() == 2) {
					startDate =  danger.getJbYear() + "-04-01";
					endDate =  danger.getJbYear() + "-07-01";
				} else if (danger.getJbQuarter() == 3) {
					startDate =  danger.getJbYear() + "-07-01";
					endDate =  danger.getJbYear() + "-10-01";
				} else if (danger.getJbQuarter() == 4) {
					startDate =  danger.getJbYear() + "-10-01";
					endDate = nYear + "-01-01";
				}
			}
			
			if("2".equals(danger.getRepairState())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" +startDate + "','yyyy-MM-dd') " + " and to_date('" + endDate + "','yyyy-MM-dd')"));
			}else if("0".equals(danger.getRepairState())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" +startDate + "','yyyy-MM-dd') " + " and to_date('" + endDate + "','yyyy-MM-dd') and  this_.id not in (select par_da_dan_id  from da_danger_gorver where is_deleted = 0 and create_time  between to_date('" + danger.getJbYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + endDate + "','yyyy-MM-dd'))  "));
			}
		}else{
			if (danger != null) {
				if ("0".equals(danger.getIsGorver())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)"));
				} else if ("1".equals(danger.getIsGorver())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)"));
				} else if ("2".equals(danger.getIsGorver())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <to_date('" + computeDate("30") + "','yyyy-MM-dd')"));
				}
			}
		}
		
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return dangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}

	public DaDanger loadDangeres(DaDanger danger) throws ApplicationAccessException {
		return dangerPersistenceIface.loadDanger(danger);
	}

	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.COMPANY_TRADE));
		if (userDetail != null) {
			// Set<FkRole> roles = userPersistenceIface.loadUser((long)
			// userDetail.getUserId()).getFkRoles();
			// if (RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles)) {
			//
			// } else {
			String userIndustry = userDetail.getUserIndustry();
			if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("depiction", userIndustry));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id is null"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sort"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	/**
	 * 修改次方法时，同事修改loadDangers4count方法
	 */
	public List<DaDanger> loadDangersOfCompanies(DaDanger danger, DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "dc");
		String sql = "";
		float governMoney1 = danger.getGovernMoney1();
		float governMoney2 = danger.getGovernMoney2();

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
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			// String depicWhere = "'"+ userIndustry
			// +
			// "' in (select depiction from da_industry_parameter dip where dip.id in (select par_da_ind_id from da_company_industry_rel dcir where par_da_com_id=this_.id))";
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("dcp1_.par_da_com_id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (danger != null) {
			if (danger.getDangerAdd() != null && !"".equals(danger.getDangerAdd().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.dangerAdd", danger.getDangerAdd().trim(), MatchMode.ANYWHERE));
			}
			if (danger.getLinkMan() != null && !"".equals(danger.getLinkMan().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.linkMan", danger.getLinkMan().trim(), MatchMode.ANYWHERE));
			}

			if (governMoney1 != 0.0) {

				sql += " and  govern_money>=" + governMoney1 + "   ";
			}
			if (governMoney2 != 0.0) {
				sql += " and  govern_money<=" + governMoney2 + "  ";
			}

			if ("0".equals(danger.getIsGorver())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0 " + sql + ")"));
			} else if ("1".equals(danger.getIsGorver())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0 " + sql + " )"));
			} else if ("2".equals(danger.getIsGorver())) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0 " + sql + " )"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <to_date('" + computeDate("30") + "','yyyy-MM-dd')"));
				if (danger.getStartTime() != null && !"".equals(danger.getStartTime()) && danger.getEndTime() != null && !"".equals(danger.getEndTime())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date >=to_date('" + formatter.format(danger.getStartTime()) + "','yyyy-MM-dd')"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <=to_date('" + formatter.format(danger.getEndTime()) + "','yyyy-MM-dd')"));
				} else {
					if (danger.getStartTime() != null && !"".equals(danger.getStartTime())) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date >=to_date('" + formatter.format(danger.getStartTime()) + "','yyyy-MM-dd')"));
					} else if (danger.getEndTime() != null && !"".equals(danger.getEndTime())) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <=to_date('" + formatter.format(danger.getEndTime()) + "','yyyy-MM-dd')"));
					}
				}
			}

			if ("0".equals(danger.getIsRollcall())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)"));
			} else if ("1".equals(danger.getIsRollcall())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)"));
			}
			int nextYear = danger.getNowYear() + 1;
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + danger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));

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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return dangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}

	/**
	 * 修改此方法，同时修改loadDangersOfCompanies方法
	 */
	public long loadDangers4Count(DaDanger danger, DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "dc");
		String sql = "";
		float governMoney1 = danger.getGovernMoney1();
		float governMoney2 = danger.getGovernMoney2();

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
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			// String depicWhere = "'"+ userIndustry
			// +
			// "' in (select depiction from da_industry_parameter dip where dip.id in (select par_da_ind_id from da_company_industry_rel dcir where par_da_com_id=this_.id))";
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("dcp1_.par_da_com_id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return 0;
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (danger != null) {
			if (danger.getDangerAdd() != null && !"".equals(danger.getDangerAdd().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.dangerAdd", danger.getDangerAdd().trim(), MatchMode.ANYWHERE));
			}
			if (danger.getLinkMan() != null && !"".equals(danger.getLinkMan().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.linkMan", danger.getLinkMan().trim(), MatchMode.ANYWHERE));
			}

			if (governMoney1 != 0.0) {

				sql += " and  govern_money>=" + governMoney1 + "   ";
			}
			if (governMoney2 != 0.0) {
				sql += " and  govern_money<=" + governMoney2 + "  ";
			}

			if ("0".equals(danger.getIsGorver())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0 " + sql + ")"));
			} else if ("1".equals(danger.getIsGorver())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0 " + sql + " )"));
			} else if ("2".equals(danger.getIsGorver())) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0 " + sql + " )"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <to_date('" + computeDate("30") + "','yyyy-MM-dd')"));
				if (danger.getStartTime() != null && !"".equals(danger.getStartTime()) && danger.getEndTime() != null && !"".equals(danger.getEndTime())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date >=to_date('" + formatter.format(danger.getStartTime()) + "','yyyy-MM-dd')"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <=to_date('" + formatter.format(danger.getEndTime()) + "','yyyy-MM-dd')"));
				} else {
					if (danger.getStartTime() != null && !"".equals(danger.getStartTime())) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date >=to_date('" + formatter.format(danger.getStartTime()) + "','yyyy-MM-dd')"));
					} else if (danger.getEndTime() != null && !"".equals(danger.getEndTime())) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <=to_date('" + formatter.format(danger.getEndTime()) + "','yyyy-MM-dd')"));
					}
				}
			}

			if ("0".equals(danger.getIsRollcall())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)"));
			} else if ("1".equals(danger.getIsRollcall())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)"));
			}
			int nextYear = danger.getNowYear() + 1;
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + danger.getNowYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));

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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				}
			}
		}
		detachedCriteriaProxy.setProjection(Projections.rowCount());
		
		return dangerPersistenceIface.countCriteria(detachedCriteriaProxy);
	}

	public List<Statistic> loadDangersOfCompaniesBySql(DaDanger danger, DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		List<Statistic> statistics2 = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sql = "select dc.company_name,dd.danger_no,dd.danger_add,dd.link_man,dd.link_tel,drc.id,dd.id,dd.par_da_com_id" + " from da_danger dd left join da_company_pass dcp on dcp.par_da_com_id=dd.par_da_com_id " + " left join da_company dc on dc.id=dd.par_da_com_id left join da_rollcall_company drc " + " on drc.par_da_dan_id=dd.id and drc.is_deleted=0 where dd.is_deleted=0 and dcp.is_affirm=1 " + " and dcp.is_deleted=0 and dc.is_deleted=0";
		if (danger != null) {
			if ("0".equals(danger.getIsGorver())) {
				sql += " and dd.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)";
			} else if ("1".equals(danger.getIsGorver())) {
				sql += " and dd.id in (select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)";
			}
			if ("0".equals(danger.getIsRollcall())) {
				sql += " and dd.id not in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)";
			} else if ("1".equals(danger.getIsRollcall())) {
				sql += " and dd.id in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)";
			}
			if (danger.getDangerAdd() != null && !"".equals(danger.getDangerAdd().trim())) {
				sql += " and dd.danger_add like '%" + danger.getDangerAdd() + "%'";
			}
			if (danger.getLinkMan() != null && !"".equals(danger.getLinkMan().trim())) {
				sql += " and dd.link_man like '%" + danger.getLinkMan() + "%'";
			}
		}
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				sql += " and dc.company_name like '%" + company.getCompanyName() + "%'";
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				sql += " and dc.first_area = " + company.getFirstArea();
				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
					sql += " and dc.second_area = " + company.getSecondArea();
					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
						sql += " and dc.third_area = " + company.getThirdArea();
						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
							sql += " and dc.fouth_area = " + company.getFouthArea();
							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
								sql += " and dc.fifth_area = " + company.getFifthArea();
							}
						}
					}
				}
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			sql += " and dc.fifth_area = " + userDetail.getFifthArea();
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			sql += " and dc.fouth_area = " + userDetail.getFouthArea();
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			sql += " and dc.third_area = " + userDetail.getThirdArea();
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			sql += " and dc.second_area = " + userDetail.getSecondArea();
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			sql += " and dc.first_area = " + userDetail.getFirstArea();
		}
		sql += " order by dc.id desc ,dc.company_name ";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setCompanyName(res.getString(1));
				statistic.setDangerNo(res.getString(2));
				statistic.setDangerAdd(res.getString(3));
				statistic.setLinkMan(res.getString(4));
				statistic.setLinkTel(res.getString(5));
				statistic.setIsRollcall(res.getString(6));
				statistic.setId(res.getLong(7));
				statistic.setCompanyId(res.getLong(8));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/**
		 * 分页
		 */
		int itemCount = 0;
		int afterCount = 0;
		pagination.setTotalCount(statistics.size());
		if (pagination.getItemCount() == 0) {
			itemCount = 0;
			if (pagination.getPageSize() > pagination.getTotalCount()) {
				afterCount = 10;
			} else {
				afterCount = pagination.getPageSize();
			}
		} else if (pagination.getItemCount() > 0) {
			itemCount = pagination.getPageSize() * (pagination.getCurrentPage() - 1);
			if (pagination.getTotalCount() < pagination.getCurrentPage() * pagination.getPageSize()) {
				afterCount = 10;
			} else {
				afterCount = pagination.getCurrentPage() * pagination.getPageSize();
			}
		}
		for (int i = itemCount; i < afterCount; i++) {
			statistic = new Statistic();
			statistic.setCompanyName(statistics.get(i).getCompanyName());
			statistic.setDangerNo(statistics.get(i).getDangerNo());
			statistic.setDangerAdd(statistics.get(i).getDangerAdd());
			statistic.setLinkMan(statistics.get(i).getLinkMan());
			statistic.setLinkTel(statistics.get(i).getLinkTel());
			statistic.setIsRollcall(statistics.get(i).getIsRollcall());
			statistic.setId(statistics.get(i).getId());
			statistic.setCompanyId(statistics.get(i).getCompanyId());
			statistics2.add(statistic);
		}
		return statistics2;
	}

	/**
	 * 日期处理模块(将日期加上某些天或减去天数)返回字符串
	 * 
	 * @param param
	 * @return
	 */
	private String computeDate(String param) {
		int strTo;
		try {
			strTo = Integer.parseInt(param);
		} catch (Exception e) {
			e.printStackTrace();
			strTo = 0;
		}
		Calendar strDate = Calendar.getInstance(); // java.util包
		strDate.add(Calendar.DATE, strTo); // 日期减 如果不够减会将月变动
		String resultDate = strDate.get(Calendar.YEAR) + "-" + String.valueOf(strDate.get(Calendar.MONTH) + 1) + "-" + strDate.get(Calendar.DATE);// 生成(年-月-日)字符串
		return resultDate;
	}

	private List<DaDanger> loadDangerForNum(DaDanger danger) throws ApplicationAccessException {
		List<DaDanger> dangers = new ArrayList<DaDanger>();
		DaDanger sdanger;
		String sql = "select danger_no from (select dd.danger_no from da_danger dd where " + " dd.par_da_com_id = " + danger.getDaCompanyPass().getId() + " order by dd.id desc) d where rownum=1";

		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				sdanger = new DaDanger();
				sdanger.setDangerNo(res.getString(1));
				dangers.add(sdanger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dangers;
	}

	private String getHiddenNum(DaDanger danger) throws ApplicationAccessException {
//		System.out.println("getNo====================");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date now = new Date();
		String year = formatter.format(now);
		String hiddenNumber = "";
		List<DaDanger> dangers = loadDangerForNum(danger);
		if (dangers.size() > 0) {
			String hiddenNum = dangers.get(0).getDangerNo();
			if (hiddenNum != null && !"".equals(hiddenNum)) {
				String lastNum = hiddenNum.split("-")[0];
				if (lastNum.equals(Nbyhpc.DANGER_NUMBER + year)) {
					int newNo = Integer.parseInt(hiddenNum.split("-")[1]) + 1;
					if (String.valueOf(newNo).length() == 1) {
						hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-0" + newNo;
					} else if (String.valueOf(newNo).length() == 2) {
						hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-" + newNo;
					}
				} else {
					hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-01";
				}
			} else {
				hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-01";
			}
		} else {
			hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-01";
		}
		return hiddenNumber;
	}

	public void updateDanger(DaDanger danger) throws ApplicationAccessException {
		DaDanger oldDanger = loadDanger(danger);
		danger.setCreateTime(oldDanger.getCreateTime());
		danger.setIsSynchro(1);// 更新同步状态
		dangerPersistenceIface.mergeDanger(danger);
		daDangerImageFacadeIface.createByDanger(danger);
	}

	public long loadCompanyQuarterlyAccount(DaCompany company) throws ApplicationAccessException {
		long count = 0;
		String sql = "select count(*)  from da_company_quarter_report t where t.company_id=" + company.getId() + " and t.quarter= to_char(sysdate, 'q')";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				count = res.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 修改录入时间超过7天的未治理重大隐患治理状态为已治理
	 * 
	 * @param days
	 * @throws SQLException
	 */
	public void updateDangerRepaired(int days) throws ApplicationAccessException {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String temp_str = sdf.format(dt);
		// 查询有无未治理的重大隐患
		ResultSet res_bigdouble = companyPersistenceIface.findBySql("select t.id,t.danger_add,t.link_man,t.link_tel,t.link_mobile,t.govern_money,t.fill_date,t.fill_man,t.charge_person,t.user_id  from da_danger t where t.id not in (select  d.par_da_dan_id   from da_danger_gorver d  where d.is_deleted=0)   and  t.is_deleted=0  and t.create_time+7<=sysdate ");
		Long mid = 0l;
		try {
			while (res_bigdouble.next()) {
				ResultSet rr = companyPersistenceIface.findBySql("select id  from  da_danger_gorver  order  by  id desc");
				if (rr.next()) {
					mid = rr.getLong(1);
				}
				mid++;
				String mobile = res_bigdouble.getString(5);
				if (mobile == null)
					mobile = "";
				// 重大隐患未治理变已治理
				String s1 = "insert  into    da_danger_gorver(id,par_da_dan_id,danger_add,gorver_content,finish_date,money, user_id,link_man,link_tel,link_mobile,charge_person,fill_date,fill_man,IS_DELETED,MODIFY_TIME,CREATE_TIME,IS_EVALUATE,IS_EVALUATE_EXPERT,IS_EVALUATE_GOVERNMENT)  values(" + mid + "," + res_bigdouble.getInt(1) + ",'" + res_bigdouble.getString(2) + "','无',to_date('" + temp_str + "','yyyy-MM-dd')," + res_bigdouble.getString(6) + "," + res_bigdouble.getString(10) + ",'" + res_bigdouble.getString(3) + "','" + res_bigdouble.getString(4) + "','" + mobile + "','" + res_bigdouble.getString(9) + "',to_date('" + res_bigdouble.getString(7).substring(0, 11) + "','yyyy-MM-dd'),'" + res_bigdouble.getString(8) + "',0,sysdate,sysdate,1,0,0)";
				pubCompanyPersistenceIface.executeSQLUpdate(s1);

			}
		} catch (SQLException e) {
//			System.out.println("重大隐患7天以后变为已治理出错！");
			e.printStackTrace();
		}
	}

	public void setDangerPersistenceIface(DangerPersistenceIface dangerPersistenceIface) {
		this.dangerPersistenceIface = dangerPersistenceIface;
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setCompanyPassPersistenceIface(CompanyPassPersistenceIface companyPassPersistenceIface) {
		this.companyPassPersistenceIface = companyPassPersistenceIface;
	}

	public PubCompanyPersistenceIface getPubCompanyPersistenceIface() {
		return pubCompanyPersistenceIface;
	}

	public void setPubCompanyPersistenceIface(PubCompanyPersistenceIface pubCompanyPersistenceIface) {
		this.pubCompanyPersistenceIface = pubCompanyPersistenceIface;
	}

	public void setDaDangerImageFacadeIface(DaDangerImageFacadeIface daDangerImageFacadeIface) {
		this.daDangerImageFacadeIface = daDangerImageFacadeIface;
	}
	
}
