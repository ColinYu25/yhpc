package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.ProjectionsProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.MineDetailPersistenceIface;
import com.safetys.nbyhpc.facade.iface.MineDetailFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;

public class MineDetailFacadeImpl implements MineDetailFacadeIface {

	private MineDetailPersistenceIface mineDetailPersistenceIface;

	private MineFacadeIface mineFacadeIface;

	public long createMineReportDetail(ZjMineReportDetail mineReportDetail) throws ApplicationAccessException {
		return mineDetailPersistenceIface.createMineReportDetail(mineReportDetail);
	}

	public void createMineReportDetails(List<ZjMineReportDetail> mineReportDetails, long id)
			throws ApplicationAccessException {
		if (mineReportDetails != null && mineReportDetails.size() != 0) {
			ZjMineReport mineReport = new ZjMineReport();
			mineReport.setId(id);
			for (ZjMineReportDetail detail : mineReportDetails) {
				if (detail != null) {
					detail.setZjMineReport(mineReport);
					createMineReportDetail(detail);
				}
			}
		}
	}

	public void deleteMineReportDetail(ZjMineReportDetail mineReportDetail) throws ApplicationAccessException {
		mineDetailPersistenceIface.deleteMineReportDetail(mineReportDetail);
	}

	public void deleteMineReportDetailsByMine(Long id) throws ApplicationAccessException {
		List<ZjMineReportDetail> details = loadMineReportDetailsByMine(id, null);
		for (ZjMineReportDetail detail : details) {
			deleteMineReportDetail(detail);
		}
	}

	public ZjMineReportDetail loadMineReportDetail(long id) throws ApplicationAccessException {
		return mineDetailPersistenceIface.loadMineReportDetail(id);
	}

	public List<ZjMineReportDetail> loadMineReportDetails(ZjMineReportDetail mineReportDetail, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjMineReportDetail.class, "zmrd");
		return mineDetailPersistenceIface.loadMineReportDetails(detachedCriteriaProxy, pagination);
	}

	public List<ZjMineReportDetail> loadMineReportDetailsByMine(Long id, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjMineReportDetail.class, "zmrd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmrd.zjMineReport.id", id));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("type"));
		List<ZjMineReportDetail> details =
				mineDetailPersistenceIface.loadMineReportDetails(detachedCriteriaProxy, pagination);
		if (details.size() == 16) {
			details.add(5, null);
		} else if (details.size() == 26) {
			details.add(9, null);
		}
		return details;
	}

	public List<ZjMineReportDetail> loadMineReportDetailsByUser(ZjMineReport mineReport)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjMineReportDetail.class, "zmrd");
		detachedCriteriaProxy.createCriteria("zmrd.zjMineReport", "zmr").createCriteria("zmr.userId", "fu")
				.createCriteria("zmr.userId.fkUserInfo", "fui");// 创建链接表
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(
				ProjectionsProxy.sum("zmrd.shouldTroubleshooting")).add(ProjectionsProxy.sum("zmrd.company")).add(
				ProjectionsProxy.sum("zmrd.generalDanger")).add(ProjectionsProxy.sum("zmrd.generalDangerGovern")).add(
				ProjectionsProxy.sum("zmrd.planMoney")).add(ProjectionsProxy.groupProperty("zmrd.type")));// 设置查询字段
		if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport.getUserId().getFkRoles())) {
			detachedCriteriaProxy.add(
					RestrictionsProxy.and(
							RestrictionsProxy.eq("zmr.state",	SafetyTrouble.CITY_REPORT),
							RestrictionsProxy.and(
									RestrictionsProxy.eq("fui.firstArea", mineReport.getUserId().getFkUserInfo().getFirstArea()),	
									RestrictionsProxy.and(
											RestrictionsProxy.eq("fui.secondArea", mineReport.getUserId().getFkUserInfo().getSecondArea()),
											RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"),
													RestrictionsProxy.eq("fui.thirdArea", 0l))
									)
									
							)
					)
			);// 设置市级用户显示列表
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.reportMonth", mineReport.getReportMonth())).add(
				RestrictionsProxy.eq("zmr.type", mineReport.getType()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zmrd.type"));// 排序
		List<ZjMineReportDetail> objects =
				mineDetailPersistenceIface.loadMineReportDetails(detachedCriteriaProxy, null);
		Iterator iterator = objects.iterator();
		List<ZjMineReportDetail> details = new ArrayList<ZjMineReportDetail>();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ZjMineReportDetail detail =
					new ZjMineReportDetail(Integer.parseInt((obj[0].toString())),
							Integer.parseInt((obj[1].toString())), Integer.parseInt((obj[2].toString())), Integer
									.parseInt((obj[3].toString())), Double.parseDouble((obj[4].toString())), Integer
									.parseInt((obj[5].toString())));
			details.add(detail);
		}
		if (details.size() == 0) {
			int[] types = SafetyTrouble.getType(mineReport.getType());
			for (int i = 0; i < types.length; i++) {
				ZjMineReportDetail detail = new ZjMineReportDetail(0, 0, 0, 0, 0.0, types[i]);
				details.add(detail);
			}
		}
		if (details.size() == 16) {
			details.add(5, null);
		} else if (details.size() == 26) {
			details.add(9, null);
		}
		return details;
	}

	public List<ZjMineReportDetail> loadMineReportDetailsForProvince(ZjMineReport mineReport)
			throws ApplicationAccessException {
		List<ZjMineReport> mineReports =
				mineFacadeIface.loadMineReports(mineReport, null, mineReport.getType() == 5
						|| mineReport.getType() == 6);
		List<ZjMineReportDetail> details = new ArrayList<ZjMineReportDetail>();
		List<List<ZjMineReportDetail>> detailsTotal = new ArrayList<List<ZjMineReportDetail>>();
		for (ZjMineReport mine : mineReports) {
			detailsTotal.add(loadMineReportDetailsByUser(mine));
		}
		int[] types = SafetyTrouble.getType(mineReport.getType() == 6 ? SafetyTrouble.MINE_TYPE : mineReport.getType());
		for (int i = 0; i < types.length; i++) {
			ZjMineReportDetail detail = new ZjMineReportDetail(0, 0, 0, 0, 0.0, types[i]);
			details.add(detail);
		}
		for (int i = 0; i < detailsTotal.size(); i++) {// 循环到市级
			for (ZjMineReportDetail detail : detailsTotal.get(i)) {// 循环到行业
				for (ZjMineReportDetail detai : details) {// 循环对比行业
					if (detai != null && detail != null) {
						if (detai.getType() == detail.getType()) {// 对比
							detai.addMineDetail(detail.getShouldTroubleshooting(), detail.getCompany(), detail
									.getGeneralDanger(), detail.getGeneralDangerGovern(), detail.getPlanMoney());// 累加
						}
					}
				}
			}
		}
		/**
		 * @author lihu
		 * @date 2010-05-04
		 */
		if (mineReport.getType() == 5) {// 国家统计2010(交通运输)
			for (int i = 0; i < details.size(); i++) {
				for (int j = 0; j < SafetyTrouble.FOR_OTHER_INDUSTRY.length; j++) {
					if (details.get(i).getType() == SafetyTrouble.FOR_OTHER_INDUSTRY[j]
							&& details.get(16).getType() != SafetyTrouble.FOR_OTHER_INDUSTRY[j]) {
						details.get(16).addMineDetail(details.get(i).getShouldTroubleshooting(),
								details.get(i).getCompany(), details.get(i).getGeneralDanger(),
								details.get(i).getGeneralDangerGovern(), details.get(i).getPlanMoney());
					}
				}
			}

		}
		if (details.size() == 16) {
			details.add(5, null);
		} else if (details.size() == 26) {
			details.add(9, null);
		}
		return details;
	}

	public void doneCityReport(ZjMineReport mineReport) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjMineReportDetail.class, "zmrd");
		detachedCriteriaProxy.createCriteria("zmrd.zjMineReport", "zmr").createCriteria("zmr.userId", "fu")
				.createCriteria("zmr.userId.fkUserInfo", "fui");// 创建链接表
		if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport.getUserId().getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zmr.state",
					SafetyTrouble.COUNTY_REPORT), RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea",
					mineReport.getUserId().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea",
					mineReport.getUserId().getFkUserInfo().getSecondArea()))));// 设置市级用户显示列表
		} else if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport.getUserId().getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy
					.eq("zmr.state", SafetyTrouble.CITY_REPORT), RestrictionsProxy.eq("fui.firstArea", mineReport
					.getUserId().getFkUserInfo().getFirstArea())));// 设置市级用户显示列表
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.reportMonth", mineReport.getReportMonth())).add(
				RestrictionsProxy.eq("zmr.type", mineReport.getType()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zmrd.type"));// 排序
	}

	public void updateMineReportDetail(ZjMineReportDetail mineReportDetail) throws ApplicationAccessException {
		mineDetailPersistenceIface.updateMineReportDetail(mineReportDetail);
	}

	public void setMineDetailPersistenceIface(MineDetailPersistenceIface mineDetailPersistenceIface) {
		this.mineDetailPersistenceIface = mineDetailPersistenceIface;
	}

	public void setMineFacadeIface(MineFacadeIface mineFacadeIface) {
		this.mineFacadeIface = mineFacadeIface;
	}
}
