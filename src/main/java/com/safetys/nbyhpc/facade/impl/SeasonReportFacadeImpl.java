package com.safetys.nbyhpc.facade.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaSeasonReport;
import com.safetys.nbyhpc.domain.model.DaSeasonReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.BagPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.SeasonReportDetailPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.SeasonReportPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.SeasonReportFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType;

public class SeasonReportFacadeImpl implements SeasonReportFacadeIface {

	private SeasonReportPersistenceIface seasonReportPersistenceIface;

	private SeasonReportDetailPersistenceIface seasonReportDetailPersistenceIface;

	private UserPersistenceIface userPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private CompanyPersistenceIface companyPersistenceIface;

	private CompanyPassPersistenceIface companyPassPersistenceIface;

	private BagPersistenceIface bagPersistenceIface;

	public Long createSeasonReport(DaSeasonReport seasonReport) throws ApplicationAccessException {
		DaIndustryParameter industry = new DaIndustryParameter();
		industry.setId(new Long(seasonReport.getIndustryId()));
		industry = tradeTypePersistenceIface.loadTradeType(industry);
		seasonReport.getDaIndustryParameters().add(industry);
		while ((industry = industry.getDaIndustryParameter()) != null) {
			seasonReport.getDaIndustryParameters().add(industry);
		}
		seasonReport.setDaIndustryParameter(industry);
		Long id = seasonReportPersistenceIface.createSeasonReport(seasonReport);
		return id;
	}

	public void createSeasonReport(DaSeasonReport seasonReport, List<DaSeasonReportDetail> daSeasonReportDetails) throws ApplicationAccessException {
		seasonReportPersistenceIface.createSeasonReport(seasonReport);

		try {
			for (DaSeasonReportDetail seasonReportDetail : daSeasonReportDetails) {
				seasonReportDetail.setUserId(seasonReport.getUserId());
				seasonReportDetail.setDaSeasonReport(seasonReport);
				seasonReportDetailPersistenceIface.createSeasonReportDetail(seasonReportDetail);
				// seasonReport.getDaSeasonReportDetails().add(seasonReportDetail);
			}
			// seasonReportPersistenceIface.updateSeasonReport(seasonReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSeasonReport(String ids) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			seasonReportPersistenceIface.deleteSeasonReport(new DaSeasonReport(Long.parseLong(ids.split(",")[i].trim())));
		}
	}

	public DaSeasonReport loadSeasonReport(DaSeasonReport seasonReport) throws ApplicationAccessException {
		return seasonReportPersistenceIface.loadSeasonReport(seasonReport);
	}

	public List<DaSeasonReport> loadSeasonReports(DaSeasonReport seasonReport, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaSeasonReport.class, "htt");
		if (seasonReport != null) {

		}
		Set<FkRole> roles = userPersistenceIface.loadUser((long) userDetail.getUserId()).getFkRoles();
		boolean isAdmin = RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles) || RoleType.isRoleByCode(RoleType.MANAGER, roles);
		if (!isAdmin) {
			String depics = RoleType.getRoleDepicStr(roles);
			String gradeWhere = "";
			String[] depicList = depics.split(",");
			for (int i = 0; i < depicList.length; i++) {
				gradeWhere += " or grade_path like (select grade_path||'/%' from hz_trade_type where depiction='" + depicList[i] + "') ";
			}
			depics = "'" + depics.replace(",", "','") + "'";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.depiction in (" + depics + ") " + gradeWhere));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return seasonReportPersistenceIface.loadSeasonReports(detachedCriteriaProxy, pagination);
	}

	public List<DaSeasonReport> loadSeasonReports(Integer[] compIds, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaSeasonReport.class, "dsp");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.in("dcp.daCompany.id", (Object[]) compIds));
		String sqlWhere = "this_.PAR_DA_IND_ID in(select id from DA_INDUSTRY_PARAMETER where type=8)";
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlWhere));
		return seasonReportPersistenceIface.loadSeasonReports(detachedCriteriaProxy, pagination);
	}

	public void updateSeasonReport(DaSeasonReport seasonReport) throws ApplicationAccessException {
		for (DaSeasonReportDetail dsrd : seasonReport.getSeasonReportDetails()) {
			dsrd.setDaSeasonReport(seasonReport);
			dsrd.setDaSeasonReportId(seasonReport.getId());
			dsrd.setUserId(seasonReport.getUserId());
			seasonReportDetailPersistenceIface.updateSeasonReportDetail(dsrd);
		}
		seasonReport.setModifyTime(new Date());
		seasonReportPersistenceIface.updateSeasonReport(seasonReport);
	}

	public List<DaIndustryParameter> loadTradeTypesForSeasonReport() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("dip.daIndustryParameter"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.QUARTER_REPORT_TYPE));
		// detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public List<DaCompany> companiseAffirmToSeasonIsExist(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (company != null) {

			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			// if (company.getDaCompanyPass() != null) {
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.enterprise",
			// company.getDaCompanyPass().isEnterprise()));
			// }
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
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}

		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id  in  ( select par_da_com_id from da_company_industry_rel  ) "));

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
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	public List<DaCompanyPass> companiesByAffirm(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.createCriteria("daCompany", "dc");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.address", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getDaCompanyPass() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.enterprise", company.getDaCompanyPass().isEnterprise()));
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
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
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
		if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, pagination);
	}

	public List<DaBag> bagsToSeasonIsExist(DaBag bagInit, Pagination pagination, int year, int quarter, UserDetailWrapper userDetail, int isQuarter) throws ApplicationAccessException {
		List<DaBag> bags;
		bags = loadBags(bagInit, pagination, userDetail, year, quarter, isQuarter);
		return bags;
	}

	public DaSeasonReport loadSeasonReport(DaCompany company, int year, int seasonNumber) throws ApplicationAccessException {
		return _loadSeasonReport(company, null, year, seasonNumber);
	}

	public DaSeasonReport loadSeasonReport(DaBag bag, int year, int seasonNumber) throws ApplicationAccessException {
		return _loadSeasonReport(null, bag, year, seasonNumber);
	}

	/**
	 * 鏁版嵁鎸佷箙鎺ュ彛娉ㄥ叆
	 * 
	 * @param tradeTypePersistenceIface
	 */
	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setSeasonReportPersistenceIface(SeasonReportPersistenceIface seasonReportPersistenceIface) {
		this.seasonReportPersistenceIface = seasonReportPersistenceIface;
	}

	public void setSeasonReportDetailPersistenceIface(SeasonReportDetailPersistenceIface seasonReportDetailPersistenceIface) {
		this.seasonReportDetailPersistenceIface = seasonReportDetailPersistenceIface;
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setBagPersistenceIface(BagPersistenceIface bagPersistenceIface) {
		this.bagPersistenceIface = bagPersistenceIface;
	}

	/**
	 * 鍔犺浇閫氳繃瀹℃牳鐨勯潪鎵撳寘浼佷笟闆嗗悎锛屽苟涓庤涓氬叧鑱?
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @param dates
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompaniesAffirm(DaCompany company, Pagination pagination, UserDetailWrapper userDetail, int year, int quarter, int isQuarter) throws ApplicationAccessException {
		Date[] dates = null;
		if (quarter != 0) {
			dates = Nbyhpc.getSeasonTime(year, quarter);
		}
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));

		if (dates != null) {
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
		}
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}

			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.address", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(), MatchMode.ANYWHERE));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(trade + " in (select PAR_DA_IND_ID from da_company_industry_rel where id=this_.id)"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
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
		} else {

		}
		String sqlWhere = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		switch (isQuarter) {
		case 1:
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("dcp.daSeasonReports"));
			break;
		case 2:
			dates = Nbyhpc.getSeasonTime(year, 1);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("dcp.daSeasonReports"));
			break;
		case 3:
			dates = Nbyhpc.getSeasonTime(year, 2);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("dcp.daSeasonReports"));
			break;
		case 4:
			dates = Nbyhpc.getSeasonTime(year, 3);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("dcp.daSeasonReports"));
			break;
		case 5:
			dates = Nbyhpc.getSeasonTime(year, 4);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("dcp.daSeasonReports"));
			break;
		case 6:
			detachedCriteriaProxy.add(RestrictionsProxy.isNull("dcp.daSeasonReports"));
			// sqlWhere="this_.id in(select PAR_DA_COM_ID from DA_COMPANY_PASS where PAR_DA_COM_ID not in (select PAR_DA_COM_ID from da_season_report where par_da_com_id is not null))";
			// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlWhere));
			break;
		case 7:
			dates = Nbyhpc.getSeasonTime(year, 1);
//			System.out.println(sdf.format(dates[0]));
//			System.out.println(sdf.format(dates[1]));
			sqlWhere = "this_.id in(select PAR_DA_COM_ID from DA_COMPANY_PASS where PAR_DA_COM_ID not in (select PAR_DA_COM_ID from da_season_report where par_da_com_id is not null and create_Time between to_date('" + sdf.format(dates[0]) + "'   ,'dd/mm/yyyy hh24:mi:ss') and to_date('" + sdf.format(dates[1]) + "'   ,'dd/mm/yyyy hh24:mi:ss')))";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlWhere));
			break;
		case 8:
			dates = Nbyhpc.getSeasonTime(year, 2);
			sqlWhere = "this_.id in(select PAR_DA_COM_ID from DA_COMPANY_PASS where PAR_DA_COM_ID not in (select PAR_DA_COM_ID from da_season_report where par_da_com_id is not null and create_Time between to_date('" + sdf.format(dates[0]) + "'   ,'dd/mm/yyyy hh24:mi:ss') and to_date('" + sdf.format(dates[1]) + "'   ,'dd/mm/yyyy hh24:mi:ss')))";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlWhere));
			break;
		case 9:
			dates = Nbyhpc.getSeasonTime(year, 3);
			sqlWhere = "this_.id in(select PAR_DA_COM_ID from DA_COMPANY_PASS where PAR_DA_COM_ID not in (select PAR_DA_COM_ID from da_season_report where par_da_com_id is not null))";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlWhere));
			break;
		case 10:
			dates = Nbyhpc.getSeasonTime(year, 4);
			sqlWhere = "this_.id in(select PAR_DA_COM_ID from DA_COMPANY_PASS where PAR_DA_COM_ID not in (select PAR_DA_COM_ID from da_season_report where par_da_com_id is not null))";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlWhere));
			break;

		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.createTime"));
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			String depicWhere = "'" + userIndustry + "' in (select depiction from da_industry_parameter dip where dip.id in (select par_da_ind_id from da_company_industry_rel dcir where par_da_com_id=this_.id))";
			;
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(depicWhere));
		}
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);

	}

	/**
	 * 鍔犺浇閫氳繃瀹℃牳鐨勬墦鍖呬紒涓氶泦鍚?
	 * 
	 * @param bag
	 * @param pagination
	 * @param userDetail
	 * @param dates
	 * @return
	 * @throws ApplicationAccessException
	 */
	private List<DaBag> loadBags(DaBag bag, Pagination pagination, UserDetailWrapper userDetail, int year, int quarter, int isQuarter) throws ApplicationAccessException {
		Date[] dates = null;
		if (quarter != 0) {
			dates = Nbyhpc.getSeasonTime(year, quarter);
		}
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaBag.class, "db");
		if (bag != null) {
			if (bag.getName() != null && !"".equals(bag.getName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.name", bag.getName().trim(), MatchMode.ANYWHERE));
			}
			if (bag.getRegAddress() != null && !"".equals(bag.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.regAddress", bag.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (bag.getFddelegate() != null && !"".equals(bag.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.fddelegate", bag.getFddelegate().trim(), MatchMode.ANYWHERE));
			}
			if (bag.getFirstArea() != null && bag.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("db.firstArea", bag.getFirstArea()));
				if (bag.getSecondArea() != null && bag.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("db.secondArea", bag.getSecondArea()));
					if (bag.getThirdArea() != null && bag.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("db.thirdArea", bag.getThirdArea()));
						if (bag.getFouthArea() != null && bag.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fouthArea", bag.getFouthArea()));
							if (bag.getFifthArea() != null && bag.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fifthArea", bag.getFifthArea()));
							}
						}
					}
				}
			}
			if (bag.getOrderProperty() != null) {
				String orderProperty = bag.getOrderProperty();
				detachedCriteriaProxy.addOrder(bag.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.firstArea", userDetail.getFirstArea()));
		} else {

		}
		if (dates != null) {
			detachedCriteriaProxy.createCriteria("db.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
		}
		switch (isQuarter) {
		case 1:
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("db.daSeasonReports"));
			break;
		case 2:
			dates = Nbyhpc.getSeasonTime(year, 1);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("db.daSeasonReports"));
			break;
		case 3:
			dates = Nbyhpc.getSeasonTime(year, 2);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("db.daSeasonReports"));
			break;
		case 4:
			dates = Nbyhpc.getSeasonTime(year, 3);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("db.daSeasonReports"));
			break;
		case 5:
			dates = Nbyhpc.getSeasonTime(year, 4);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("db.daSeasonReports"));
			break;
		case 6:
			detachedCriteriaProxy.add(RestrictionsProxy.isNull("db.daSeasonReports"));
			break;
		case 7:
			dates = Nbyhpc.getSeasonTime(year, 1);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNull("db.daSeasonReports"));
			break;
		case 8:
			dates = Nbyhpc.getSeasonTime(year, 2);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNull("db.daSeasonReports"));
			break;
		case 9:
			dates = Nbyhpc.getSeasonTime(year, 3);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNull("db.daSeasonReports"));
			break;
		case 10:
			dates = Nbyhpc.getSeasonTime(year, 4);
			detachedCriteriaProxy.createCriteria("dcp.daSeasonReports", "dsr");
			detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			detachedCriteriaProxy.add(RestrictionsProxy.isNull("db.daSeasonReports"));
			break;

		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("db.createTime"));
		List<DaBag> bags = bagPersistenceIface.loadBags(detachedCriteriaProxy, pagination);
		return bags;
	}

	/**
	 * 鍔犺浇瀛ｆ姤瀹炰綋锛屾牴鎹紒涓氬拰瀛ｅ害鎼滅储
	 * 
	 * @param company
	 *            闈炴墦鍖呬紒涓?
	 * @param bag
	 *            鍖呬紒涓?
	 * @param year
	 *            瀛ｆ姤骞翠唤
	 * @param seasonNumber
	 *            瀛ｅ害
	 * @return
	 * @throws ApplicationAccessException
	 */
	private DaSeasonReport _loadSeasonReport(DaCompany company, DaBag bag, int year, int seasonNumber) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		Date[] dates = Nbyhpc.getSeasonTime(year, seasonNumber);
		DaSeasonReport seasonReport = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaSeasonReport.class, "dsr");
		if (company != null) {
			detachedCriteriaProxy.createCriteria("dsr.daCompanyPass", "dcp");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.daCompany.id", company.getId()));
		} else if (bag != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dsr.bagId", bag.getId()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));

		List<DaSeasonReport> dsrs = seasonReportPersistenceIface.loadSeasonReports(detachedCriteriaProxy, null);
		if (dsrs.size() > 0) {
			seasonReport = dsrs.get(0);
		}
		while ((seasonNumber -= 1) > 0 && seasonReport == null) {
			dates = Nbyhpc.getSeasonTime(year, seasonNumber);
			DetachedCriteriaProxy detachedCriteria = DetachedCriteriaProxy.forClass(DaSeasonReport.class, "dsr");
			if (company != null) {
				detachedCriteria.createCriteria("dsr.daCompanyPass", "dcp");
				detachedCriteria.add(RestrictionsProxy.eq("dcp.daCompany.id", company.getId()));
			} else if (bag != null) {
				detachedCriteria.add(RestrictionsProxy.eq("dsr.bagId", bag.getId()));
			}
			detachedCriteria.add(RestrictionsProxy.between("dsr.createTime", dates[0], dates[1]));
			List<DaSeasonReport> seasonReports = seasonReportPersistenceIface.loadSeasonReports(detachedCriteria, null);
			if (seasonReports.size() > 0) {
				seasonReport = new DaSeasonReport();
				seasonReport.setOrdinaryDangerFindingBefore(seasonReports.get(0).getOrdinaryDangerFinding());
				seasonReport.setOrdinaryDangerNotGovernBefore(seasonReports.get(0).getOrdinaryDangerNotGovern());
			}
		}
		return seasonReport;
	}

	public List<DaSeasonReport> loadSeasonReportsIsGorver(DaSeasonReport seasonReport, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCompanyPassPersistenceIface(CompanyPassPersistenceIface companyPassPersistenceIface) {
		this.companyPassPersistenceIface = companyPassPersistenceIface;
	}

}
