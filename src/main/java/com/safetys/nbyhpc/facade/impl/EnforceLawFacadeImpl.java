package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaZhifaReport;
import com.safetys.nbyhpc.domain.model.DaZhifaReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.EnforceLawPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.EnforceLawFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class EnforceLawFacadeImpl implements EnforceLawFacadeIface {

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private EnforceLawPersistenceIface enforceLawPersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;

	public List<DaIndustryParameter> loadTradeTypes(UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type",
				Nbyhpc.SAFE_ENFORCE_LAW));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.depiction",
				userDetail.getUserIndustry()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,
				null);
	}

	public void createEnforceLaw(DaZhifaReport daZhifaReport,
			List<DaZhifaReportDetail> daZhifaReportDetails,
			UserDetailWrapper userDetail) throws ApplicationAccessException {
		FkArea area = loadArea(userDetail);
		daZhifaReport.setAreaCode(area.getAreaCode());
		daZhifaReport.setAreaName(area.getAreaName());
		for (DaZhifaReportDetail dd : daZhifaReportDetails) {
			dd.setDaZhifaReport(daZhifaReport);
			dd.setUserId(daZhifaReport.getUserId());
		}
		daZhifaReport.setDaZhifaReportDetails(new HashSet<DaZhifaReportDetail>(
				daZhifaReportDetails));
		enforceLawPersistenceIface.createEnforceLaw(daZhifaReport);
	}

	public List<DaZhifaReport> loadEnforceLaws(UserDetailWrapper userDetail,
			Pagination pagination, DaZhifaReport daZhifaReport)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaZhifaReport.class, "dzr");
		detachedCriteriaProxy
				.add(RestrictionsProxy
						.sqlRestriction("this_.id in ( select distinct(zhifa_id)  from da_zhifa_report_detail d "
								+ "where d.industry_parameter_id in (select id from Da_Industry_Parameter t where t.type ="
								+ Nbyhpc.SAFE_ENFORCE_LAW
								+ " and t.is_deleted = 0 and t.depiction = '"
								+ userDetail.getUserIndustry() + "'))"));
		if (0L != userDetail.getSecondArea()) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode",
					userDetail.getSecondArea()));
		}
		if (daZhifaReport != null) {
			if (daZhifaReport.getAreaCode() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode",
						daZhifaReport.getAreaCode()));
			}
			if (daZhifaReport.getUnit() != null
					&& !"".equals(daZhifaReport.getUnit().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("unit",
						daZhifaReport.getUnit()));
			}
			if (daZhifaReport.getWrittenMonth() != null
					&& !"".equals(daZhifaReport.getWrittenMonth())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("writtenMonth",
						daZhifaReport.getWrittenMonth()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dzr.writtenDate"));
		return enforceLawPersistenceIface.loadEnforceLaws(
				detachedCriteriaProxy, pagination);
	}

	public void updateEnforceLaw(DaZhifaReport daZhifaReport,
			List<DaZhifaReportDetail> daZhifaReportDetails,
			UserDetailWrapper userDetail) throws ApplicationAccessException {
		FkArea area = loadArea(userDetail);
		daZhifaReport.setAreaCode(area.getAreaCode());
		daZhifaReport.setAreaName(area.getAreaName());
		for (DaZhifaReportDetail dd : daZhifaReportDetails) {
			dd.setDaZhifaReport(daZhifaReport);
			dd.setUserId(daZhifaReport.getUserId());
		}
		daZhifaReport.setDaZhifaReportDetails(new HashSet<DaZhifaReportDetail>(
				daZhifaReportDetails));
		enforceLawPersistenceIface.updateEnforceLaw(daZhifaReport);

	}

	public void deleteEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException {
		daZhifaReport = loadEnforceLaw(daZhifaReport);
		daZhifaReport.setDeleted(true);
		Set<DaZhifaReportDetail> set = new HashSet<DaZhifaReportDetail>();
		for (Iterator it = daZhifaReport.getDaZhifaReportDetails().iterator(); it
				.hasNext();) {
			DaZhifaReportDetail dd = (DaZhifaReportDetail) it.next();
			dd.setDaZhifaReport(daZhifaReport);
			dd.setDeleted(true);
			set.add(dd);
		}
		daZhifaReport.setDaZhifaReportDetails(set);
		enforceLawPersistenceIface.updateEnforceLaw(daZhifaReport);

	}

	public FkArea loadArea(UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		Long areaCode;
		if (userDetail.getSecondArea() != 0L) {
			if (userDetail.getThirdArea() != 0L) {
				areaCode = userDetail.getThirdArea();
			} else {
				areaCode = userDetail.getSecondArea();
			}
		} else {
			areaCode = userDetail.getFirstArea();
		}
		String hql = "FROM FkArea WHERE deleted = 0 and areaCode = " + areaCode;
		List<FkArea> areas = areaPersistenceIface.loadAreasByHql(hql);
		return areas.size() > 0 ? areas.get(0) : null;
	}

	public List<FkArea> loadAreas(UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		long areaCode = 0L;
		if (userDetail.getSecondArea() != 0L) {
			if (userDetail.getThirdArea() != 0L) {
				areaCode = userDetail.getThirdArea();
			} else {
				areaCode = userDetail.getSecondArea();
			}
		} else {
			areaCode = userDetail.getFirstArea();
		}
		StringBuffer sb = new StringBuffer();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(FkArea.class, "fa");
		if (areaCode == Nbyhpc.AREA_CODE) {
			sb
					.append(" this_.father_id = (select id from fk_area where area_code="
							+ areaCode + ")");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sb
					.toString()));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy
					.eq("areaCode", areaCode));
		}

		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}

	public List<DaZhifaReportDetail> loadStatistics(UserDetailWrapper userDetail, DaZhifaReport daZhifaReport)
			throws ApplicationAccessException {
		String sql = "select name,total,data1,data2,data3,data4,data5,data6,data7,data8,data9,data10 from (select zrd.industry_parameter_id industry_parameter_id,sum(zrd.total) total,"
				+ "sum(zrd.data1) data1,sum(zrd.data2) data2,sum(zrd.data3) data3,sum(zrd.data4) data4,"
				+ "sum(zrd.data5) data5,sum(zrd.data6) data6,sum(zrd.data7) data7,sum(zrd.data8) data8,"
				+ "sum(zrd.data9) data9,sum(zrd.data10) data10 from da_zhifa_report_detail zrd "
				+ "where zrd.is_deleted = 0 group by zrd.industry_parameter_id) t1,(select id, name "
				+ "from da_industry_parameter dip where dip.type = "+Nbyhpc.SAFE_ENFORCE_LAW+" and dip.depiction = '"+userDetail.getUserIndustry()+"' "
				+ "and dip.is_deleted = 0) t2 where t1.industry_parameter_id = t2.id "
				+ "order by t1.industry_parameter_id";
		ResultSet rs = enforceLawPersistenceIface.findBySql(sql);
		List<DaZhifaReportDetail> daZhifaReportDetails = new ArrayList<DaZhifaReportDetail>();
		try {
			while (rs.next()) {
				DaZhifaReportDetail dd = new DaZhifaReportDetail();
				dd.setName(rs.getString(1));
				dd.setTotal(rs.getInt(2));
				dd.setData1(rs.getInt(3));
				dd.setData2(rs.getInt(4));
				dd.setData3(rs.getInt(5));
				dd.setData4(rs.getInt(6));
				dd.setData5(rs.getInt(7));
				dd.setData6(rs.getInt(8));
				dd.setData7(rs.getInt(9));
				dd.setData8(rs.getInt(10));
				dd.setData9(rs.getInt(11));
				dd.setData10(rs.getInt(12));
				daZhifaReportDetails.add(dd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return daZhifaReportDetails;
	}

	public DaZhifaReport loadEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException {
		return enforceLawPersistenceIface.loadEnforceLaw(daZhifaReport);
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setEnforceLawPersistenceIface(
			EnforceLawPersistenceIface enforceLawPersistenceIface) {
		this.enforceLawPersistenceIface = enforceLawPersistenceIface;
	}

	public void setAreaPersistenceIface(
			AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

}
