package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.persistence.iface.MinePersistenceIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;

public class MineFacadeImpl implements MineFacadeIface {

	private MinePersistenceIface minePersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;

	public long createMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException {
		mineReport.setReportMonth(mineReport.getYear() + "-"
				+ mineReport.getMonth());
		return minePersistenceIface.createMineReport(mineReport);
	}

	public void updateMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException {
		ZjMineReport mine = loadMineReport(mineReport.getId());
		if (mineReport.getYear() != null && mineReport.getMonth() != null) {
			mineReport.setReportMonth(mineReport.getYear() + "-"
					+ mineReport.getMonth());
		}
		mineReport.setCreateTime(mine.getCreateTime());
		minePersistenceIface.mergeMineReport(mineReport);
	}

	public void deleteMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException {
		minePersistenceIface.deleteMineReport(mineReport);
	}

	public ZjMineReport loadMineReport(long id)
			throws ApplicationAccessException {
		return minePersistenceIface.loadMineReport(id);
	}
	
	public List<ZjMineReport> loadMineReports(ZjMineReport mineReport,
			Pagination pagination) throws ApplicationAccessException {
		return loadMineReports(mineReport, pagination,false);
	}
	
	/**
	 * 获取各个县级填报的数据
	 * @param areaCode
	 * @param reportTime
	 * @param type
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ZjMineReport loadMineReports(Long areaCode, String reportTime, Integer type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjMineReport.class, "zmr");
		detachedCriteriaProxy.createCriteria("zmr.userId", "fu").createCriteria("zmr.userId.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.type", type));// 显示列表类型（矿山、其他）
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.reportMonth", reportTime));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", Nbyhpc.AREA_CODE));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", areaCode));
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("fui.thirdArea"));
		List<ZjMineReport> list = minePersistenceIface.loadMineReports(detachedCriteriaProxy, null);
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取市级填报的数据
	 * @param areaCode
	 * @param reportTime
	 * @param type
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ZjMineReport loadMineReports1(Long areaCode, String reportTime, Integer type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjMineReport.class, "zmr");
		detachedCriteriaProxy.createCriteria("zmr.userId", "fu").createCriteria("zmr.userId.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.type", type));// 显示列表类型（矿山、其他）
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.reportMonth", reportTime));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", Nbyhpc.AREA_CODE));
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("fui.secondArea"));
		List<ZjMineReport> list = minePersistenceIface.loadMineReports(detachedCriteriaProxy, null);
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<ZjMineReport> loadMineReports(ZjMineReport mineReport,
			Pagination pagination,boolean country) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(ZjMineReport.class, "zmr");
		if (mineReport != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.type",
					mineReport.getType()));// 显示列表类型（矿山、其他）
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
						.createCriteria("zmr.userId.fkUserInfo", "fui");
				if (mineReport.isProvince()) {// 查看省级统计列表,不显示市级和县级
					detachedCriteriaProxy.add(RestrictionsProxy.or(
							RestrictionsProxy.and(RestrictionsProxy
									.isNull("fui.secondArea"),
									RestrictionsProxy.isNull("fui.thirdArea")),
							RestrictionsProxy.eq("zmr.userId", mineReport
									.getUserId())));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.and(
							RestrictionsProxy.eq("zmr.state",
									SafetyTrouble.CITY_REPORT),
							RestrictionsProxy
									.eq("fui.firstArea", mineReport.getUserId()
											.getFkUserInfo().getFirstArea())));// 设置省级用户显示列表
				}
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
						.createCriteria("zmr.userId.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(
						RestrictionsProxy.and(RestrictionsProxy.eq("zmr.state",
								SafetyTrouble.COUNTY_REPORT), RestrictionsProxy
								.and(RestrictionsProxy.eq("fui.firstArea",
										mineReport.getUserId().getFkUserInfo()
												.getFirstArea()),
										RestrictionsProxy.eq("fui.secondArea",
												mineReport.getUserId()
														.getFkUserInfo()
														.getSecondArea()))),
						RestrictionsProxy.eq("zmr.userId", mineReport.getUserId())));// 设置市级用户显示列表
			} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
						.createCriteria("zmr.userId.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(
						RestrictionsProxy.and(RestrictionsProxy.ge("zmr.state",
								SafetyTrouble.COUNTY_REPORT), RestrictionsProxy
								.and(RestrictionsProxy.and(RestrictionsProxy
										.eq("fui.firstArea", mineReport
												.getUserId().getFkUserInfo()
												.getFirstArea()),
										RestrictionsProxy.eq("fui.secondArea",
												mineReport.getUserId()
														.getFkUserInfo()
														.getSecondArea())),
										RestrictionsProxy.eq("fui.thirdArea",
												mineReport.getUserId()
														.getFkUserInfo()
														.getThirdArea()))),
						RestrictionsProxy.eq("zmr.userId", mineReport
								.getUserId())));// 设置县级用户显示列表
			} else if (RoleType1.isRoleByDepic(RoleType1.ADMIN, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.or(
						RestrictionsProxy.eq("zmr.state",
								SafetyTrouble.COUNTY_REPORT), RestrictionsProxy
								.eq("zmr.userId", mineReport.getUserId())));// 设置管理员列表
			}
			if (mineReport.isQueryReportMonth()) {// 市级上报列表
				if (mineReport.getReportMonth() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"zmr.reportMonth", mineReport.getReportMonth()));
				}
				if (!mineReport.isProvince()) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(
							RestrictionsProxy.isNull("fui.thirdArea"),
							RestrictionsProxy.eq("fui.thirdArea", 0L)));
				}
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport
					.getUserId().getFkRoles())) {// 矿山列表
//				detachedCriteriaProxy.add(RestrictionsProxy
//						.not(RestrictionsProxy.eq("zmr.userId", mineReport
//								.getUserId())));
				//设置同级用户看到同级填报的数据
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea",mineReport.getUserId().getFkUserInfo().getFirstArea()));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea",mineReport.getUserId().getFkUserInfo().getSecondArea()));
			}
			if ((mineReport.getYear() != null && !"0".equals(mineReport
					.getYear()))
					&& (mineReport.getMonth() == null || "0".equals(mineReport
							.getMonth()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"zmr.reportMonth", mineReport.getYear() + "-",
						MatchMode.START));
			} else if ((mineReport.getYear() == null || "0".equals(mineReport
					.getYear()))
					&& (mineReport.getMonth() != null && !"0".equals(mineReport
							.getMonth()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"zmr.reportMonth", "-" + mineReport.getMonth(),
						MatchMode.END));
			} else if ((mineReport.getYear() != null && !"0".equals(mineReport
					.getYear()))
					&& (mineReport.getMonth() != null && !"0".equals(mineReport
							.getMonth()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq(
						"zmr.reportMonth", mineReport.getYear() + "-"
								+ mineReport.getMonth()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("zmr.modifyTime"));// 根据修改时间排序
		List<ZjMineReport> zmr = minePersistenceIface.loadMineReports(detachedCriteriaProxy,
				pagination);
		if (mineReport.getType() == 5 && zmr.size() == 0 && country) {
			mineReport.setType(2);
			zmr = loadMineReports(mineReport, pagination);
			mineReport.setType(5);
		}
		if (mineReport.getType() == 6 && zmr.size() == 0 && country) {
			mineReport.setType(1);
			zmr = loadMineReports(mineReport, pagination);
			mineReport.setType(6);
		}
		return zmr;
	}

	public List<ZjMineReport> loadMineReportsByUser(ZjMineReport mineReport)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(ZjMineReport.class, "zmr");
		if (mineReport != null) {
			detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
					.createCriteria("zmr.userId.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.type",
					mineReport.getType()));
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea",
						mineReport.getUserId().getFkUserInfo().getFirstArea()));
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy
						.isNull("fui.secondArea"),RestrictionsProxy.eq("fui.secondArea",0L)));
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy
						.isNull("fui.thirdArea"),RestrictionsProxy.eq("fui.thirdArea",0L)));
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.add(
						RestrictionsProxy.eq("fui.firstArea", mineReport
								.getUserId().getFkUserInfo().getFirstArea()))
						.add(
								RestrictionsProxy.eq("fui.secondArea",
										mineReport.getUserId().getFkUserInfo()
												.getSecondArea())).add(
								RestrictionsProxy.or(RestrictionsProxy
										.isNull("fui.thirdArea"),
										RestrictionsProxy.eq("fui.thirdArea",
												0L)));
			} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.add(
						RestrictionsProxy.eq("fui.firstArea", mineReport
								.getUserId().getFkUserInfo().getFirstArea()))
						.add(
								RestrictionsProxy.eq("fui.secondArea",
										mineReport.getUserId().getFkUserInfo()
												.getSecondArea())).add(
								RestrictionsProxy.eq("fui.thirdArea",
										mineReport.getUserId().getFkUserInfo()
												.getThirdArea()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("zmr.modifyTime"));
		return minePersistenceIface
				.loadMineReports(detachedCriteriaProxy, null);
	}

	public List<ZjMineReport> loadCountyReports(ZjMineReport mineReport,
			Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(ZjMineReport.class, "zmr");
		List<FkArea> areas = null;
		if (mineReport != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.type",
					mineReport.getType()));// 显示列表类型（矿山、其他）
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
						.createCriteria("zmr.userId.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.and(
						RestrictionsProxy.eq("zmr.state",
								SafetyTrouble.COUNTY_REPORT), RestrictionsProxy
								.eq("fui.firstArea", mineReport.getUserId()
										.getFkUserInfo().getFirstArea())));// 设置省级用户显示列表
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport
					.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
						.createCriteria("zmr.userId.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(
						RestrictionsProxy.and(RestrictionsProxy.eq("zmr.state",
								SafetyTrouble.COUNTY_REPORT), RestrictionsProxy
								.eq("fui.secondArea", mineReport.getUserId()
										.getFkUserInfo().getSecondArea())),
						RestrictionsProxy.eq("zmr.userId", mineReport
								.getUserId())));// 设置市级用户显示列表
				detachedCriteriaProxy.add(RestrictionsProxy
						.isNotNull("fui.thirdArea"));// 过滤市级用户
			}
			if ((mineReport.getYear() != null && !"0".equals(mineReport
					.getYear()))
					&& (mineReport.getMonth() == null || "0".equals(mineReport
							.getMonth()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"zmr.reportMonth", mineReport.getYear() + "-",
						MatchMode.START));
			} else if ((mineReport.getYear() == null || "0".equals(mineReport
					.getYear()))
					&& (mineReport.getMonth() != null && !"0".equals(mineReport
							.getMonth()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"zmr.reportMonth", "-" + mineReport.getMonth(),
						MatchMode.END));
			} else if ((mineReport.getYear() != null && !"0".equals(mineReport
					.getYear()))
					&& (mineReport.getMonth() != null && !"0".equals(mineReport
							.getMonth()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq(
						"zmr.reportMonth", mineReport.getYear() + "-"
								+ mineReport.getMonth()));
			}
			Long negativeOne = -1L;
			if (mineReport.getSecondArea() != null
					&& !negativeOne.equals(mineReport.getSecondArea())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq(
						"fui.secondArea", mineReport.getSecondArea()));
				if ((mineReport.getYear() != null && !"0".equals(mineReport
						.getYear()))
						&& (mineReport.getMonth() != null && !"0"
								.equals(mineReport.getMonth()))) {
					areas = loadChildAreas(mineReport.getSecondArea());
					pagination.setTotalCount(areas.size());
					if (areas.size() > 18) {
						pagination.setPageSize(areas.size());
					}
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("zmr.reportMonth"));// 根据修改时间排序
		return switchCountyReportList(minePersistenceIface.loadMineReports(
				detachedCriteriaProxy, pagination), areas, mineReport
				.getReportMonth());
	}

	/**
	 * @author seak.lv
	 * @modified:2008-9-30
	 * @depiction:判断下一级的区域用户是否已经都上报
	 * @param areaType
	 *            判断哪一级是否上报
	 * @param areaType
	 *            当前用户的行政区划代码
	 */
	public boolean is_all_reported(String areaType, Long areaCode,
			ZjMineReport mineReport) {
		String hql = "";
		if ("county".equals(areaType)) {
			if (mineReport.getType() == SafetyTrouble.THREE_TYPE) {
				hql = "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
						+ areaCode
						+ ") and area_code not in (select thirdArea from FkUserInfo where id in (select userId from ZjThreeReport where state=1 and deleted=0 and reportMonth='"
						+ mineReport.getReportMonth()
						+ "') and deleted=0  and secondArea=" + areaCode + ")";
			} else {
				hql = "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
						+ areaCode
						+ ") and area_code not in (select thirdArea from FkUserInfo where id in (select userId from ZjMineReport where state=1 and deleted=0 and type="
						+ mineReport.getType().intValue()
						+ " and reportMonth='"
						+ mineReport.getReportMonth()
						+ "') and deleted=0  and secondArea=" + areaCode + ")";
			}
		} else if ("city".equals(areaType)) {
			if (mineReport.getType() == SafetyTrouble.THREE_TYPE) {
				hql = "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
						+ areaCode
						+ ") and area_code not in (select secondArea from FkUserInfo where id in (select userId from ZjThreeReport where state=2 and deleted=0 and reportMonth='"
						+ mineReport.getReportMonth()
						+ "') and deleted=0  and firstArea=" + areaCode + ")";
			} else {
				hql = "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
						+ areaCode
						+ ") and area_code not in (select secondArea from FkUserInfo where id in (select userId from ZjMineReport where state=2 and deleted=0  and type="
						+ mineReport.getType().intValue()
						+ "  and reportMonth='"
						+ mineReport.getReportMonth()
						+ "') and deleted=0  and firstArea=" + areaCode + ")";
			}
		}
		//验证各县市级用户是否上报数据时，屏蔽市本级的验证
		hql += " and a.areaCode not in (330330,330201,330301,330493,330599,330699,330701,330801,330901,331001,331101,330219)";//330219物流园区
		List<FkArea> fkAreas = areaPersistenceIface.loadAreasByHql(hql);
		if (fkAreas != null && fkAreas.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 根据父区域的区域代码查询子区域集合
	 * 
	 * @param fatherAreaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadChildAreas(Long fatherAreaCode)
			throws ApplicationAccessException {
		String hql = "SELECT userCompany FROM FkUserInfo WHERE thirdArea in (SELECT areaCode FROM FkArea fk WHERE fk.fatherId = (SELECT id FROM FkArea WHERE areaCode="
				+ fatherAreaCode
				+ ") AND deleted=0) AND deleted=0 AND secondArea="
				+ fatherAreaCode;
		//验证各县市级用户是否上报数据时，屏蔽市本级的验证
		hql += " and thirdArea not in (330330,330201,330301,330493,330599,330699,330701,330801,330901,331001,331101)";
		return areaPersistenceIface.loadAreasByHql(hql);
	}

	private List<ZjMineReport> switchCountyReportList(
			List<ZjMineReport> mineReports, List<FkArea> areas,
			String reportMonth) {
		if (areas == null || areas.size() == 0) {
			return mineReports;
		}
		String[] existCompany = new String[areas.size()];
		ZjMineReport mine = new ZjMineReport();
		FkUser fu = new FkUser();
		FkUserInfo fui = new FkUserInfo();
		int k = 0;
		try {
			if (mineReports.size() == 0) {
				for (int j = 0; j < areas.size(); j++) {
					mine = new ZjMineReport();
					fui = new FkUserInfo();
					fu = new FkUser();
					fui.setUserCompany(String.valueOf((areas.get(j))));
					fu.setFkUserInfo(fui);
					mine.setUserId(fu);
					mine.setReportMonth("");
					mineReports.add(mine);
				}
			} else {
				for (int j = 0; j < areas.size(); j++) {
					for (int i = 0; i < mineReports.size(); i++) {
						if (mineReports.get(i).getUserId().getFkUserInfo()
								.getUserCompany() != null
								&& areas.get(j) != null) {
							if (mineReports.get(i).getUserId().getFkUserInfo()
									.getUserCompany().equals(areas.get(j))) {
								break;
							} else {
								if (existCompany[0] == null) {
									mine = new ZjMineReport();
									fui = new FkUserInfo();
									fu = new FkUser();
									fui.setUserCompany(String.valueOf((areas
											.get(j))));
									fu.setFkUserInfo(fui);
									mine.setUserId(fu);
									mine.setReportMonth("");
									mineReports.add(mine);
									existCompany[k] = String.valueOf((areas
											.get(j)));
									k++;
								} else {
									for (int l = 0; l < existCompany.length; l++) {
										if (existCompany[l] != null) {
											if (!existCompany[l].equals(String
													.valueOf((areas.get(j))))
													&& existCompany[l + 1] == null) {
												mine = new ZjMineReport();
												fui = new FkUserInfo();
												fu = new FkUser();
												fui
														.setUserCompany(String
																.valueOf((areas
																		.get(j))));
												fu.setFkUserInfo(fui);
												mine.setUserId(fu);
												mine.setReportMonth("");
												mineReports.add(mine);
												existCompany[k] = String
														.valueOf((areas.get(j)));
												k++;
												break;
											}
										} else {
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < mineReports.size(); i++) {
				for (int j = 0; j < mineReports.size(); j++) {
					if (mineReports.get(i).getUserId().getFkUserInfo()
							.getUserCompany() != null) {
						if (mineReports.get(i).getUserId().getFkUserInfo()
								.getUserCompany().equals(
										mineReports.get(j).getUserId()
												.getFkUserInfo()
												.getUserCompany())
								&& i != j) {
							if (i > j) {
								mineReports.remove(i);
							} else {
								mineReports.remove(j);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mineReports;
	}
	
	 public List<FkArea> loadAreas(Long fatherId, Pagination pagination) throws ApplicationAccessException {
		    DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class);
		    detachedCriteriaProxy.add(RestrictionsProxy.eq("fatherId", fatherId));
		    return this.areaPersistenceIface.loadAreas(detachedCriteriaProxy, pagination);
		  }

	 /**
		 * 根据gradePath查询符合要求的区域
		 * 
		 * @param gradePath
		 *        层级结构
		 * @return
		 * @throws ApplicationAccessException
		 */
	 public  List<FkArea> loadAreasByGradePath(String gradePath) throws ApplicationAccessException{
		  DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class);
		  detachedCriteriaProxy.add(RestrictionsProxy.like("gradePath", gradePath));
		  return this.areaPersistenceIface.loadAreas(detachedCriteriaProxy);
     }
	 
	 /**
		 * 根据区域代码和级别显示这个地区不同区域级别的所有区域
		 * 
		 * @param areaCode
		 *        地区代码
		 * @param grade
		 *        区域级别
		 * @return
		 * @throws ApplicationAccessException
		 */
	 public  List<FkArea> loadAreasByGrade(Long areaCode,String grade) throws ApplicationAccessException{
		  DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class);
		  if("1".equals(grade)){
			  //查询areaCode地区一级区域信息
			  detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
		  }else if("2".equals(grade)){
			  //查询areaCode地区二级区域信息
			  detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("father_id=(select t.id from fk_area t where t.area_code="+areaCode+")"));
		  }else if("3".equals(grade)){
			//查询areaCode地区三级区域信息
			  detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("father_id in (select t.id from fk_area t where  t.father_id=(select t2.id from fk_area t2 where t2.area_code="+areaCode+"))"));
		  }else{
			//查询areaCode地区四级区域信息
			  detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("father_id in (select t.id from fk_area t where  t.father_id in (select t1.id from fk_area t1 where  t1.father_id=(select t2.id from fk_area t2 where t2.area_code="+areaCode+")))"));
		  }
		  return this.areaPersistenceIface.loadAreas(detachedCriteriaProxy);
     }
	 
	/**
	 * 根据区域代码查询区域名称
	 * 
	 * @param fatherAreaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadCityAreas(Long cityAreaCode)
			throws ApplicationAccessException {
		String hql = "SELECT areaCode,areaName FROM FkArea fk WHERE areaCode="
				+ cityAreaCode;
		List<FkArea> areaList = new ArrayList<FkArea>();
		List<FkArea> areas = new ArrayList<FkArea>();
		areaList = areaPersistenceIface.loadAreasByHql(hql);
		Iterator iterator = areaList.iterator();
		while (iterator.hasNext()) {
			Object[] area = (Object[]) iterator.next();
			FkArea fkArea = new FkArea();
			fkArea.setAreaCode(new Long(area[0].toString()));
			fkArea.setAreaName(area[1].toString());
			areas.add(fkArea);
		}
		return areas;
	}

	public List<ZjMineReport> loadMineReportByCityAndMonth(FkUser fkUser, Integer type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(ZjMineReport.class, "zmr");
		detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
				.createCriteria("zmr.userId.fkUserInfo", "fui");

		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea",
					fkUser.getFkUserInfo().getFirstArea()));
			
		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add((RestrictionsProxy.and(
					RestrictionsProxy.eq("fui.firstArea", fkUser
							.getFkUserInfo().getFirstArea()), RestrictionsProxy
							.eq("fui.secondArea", fkUser.getFkUserInfo()
									.getSecondArea()))));
			detachedCriteriaProxy.add(RestrictionsProxy.or(
					RestrictionsProxy.eq("fui.thirdArea", 0L), 
					RestrictionsProxy.isNull("fui.thirdArea")));
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy
					.and(RestrictionsProxy.eq("fui.firstArea", fkUser
							.getFkUserInfo().getFirstArea()), RestrictionsProxy
							.eq("fui.secondArea", fkUser.getFkUserInfo()
									.getSecondArea())), RestrictionsProxy.eq(
					"fui.thirdArea", fkUser.getFkUserInfo().getThirdArea())));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.type",
				type));
		List<ZjMineReport> reports = minePersistenceIface.loadMineReports(detachedCriteriaProxy, null);
		return reports;
	}
	
	public ZjMineReport loadMineReportByBigTrouble(ZjBigTrouble bigTrouble) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
			.forClass(ZjMineReport.class, "zmr");
		detachedCriteriaProxy.createCriteria("zmr.userId", "fu")
				.createCriteria("zmr.userId.fkUserInfo", "fui");
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea",
					bigTrouble.getUserId().getFkUserInfo().getFirstArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.or(
					RestrictionsProxy.eq("fui.secondArea", 0L), 
					RestrictionsProxy.isNull("fui.secondArea")));
			detachedCriteriaProxy.add(RestrictionsProxy.or(
					RestrictionsProxy.eq("fui.thirdArea", 0L), 
					RestrictionsProxy.isNull("fui.thirdArea")));
		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.add((RestrictionsProxy.and(
					RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId()
							.getFkUserInfo().getFirstArea()), RestrictionsProxy
							.eq("fui.secondArea", bigTrouble.getUserId().getFkUserInfo()
									.getSecondArea()))));
			detachedCriteriaProxy.add(RestrictionsProxy.or(
					RestrictionsProxy.eq("fui.thirdArea", 0L), 
					RestrictionsProxy.isNull("fui.thirdArea")));
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy
					.and(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId()
							.getFkUserInfo().getFirstArea()), RestrictionsProxy
							.eq("fui.secondArea", bigTrouble.getUserId().getFkUserInfo()
									.getSecondArea())), RestrictionsProxy.eq(
					"fui.thirdArea", bigTrouble.getUserId().getFkUserInfo().getThirdArea())));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zmr.reportMonth", bigTrouble.getReportMonth()))
			.add(RestrictionsProxy.eq("zmr.type", bigTrouble.getTableType()));
		List<ZjMineReport> reports = minePersistenceIface.loadMineReports(detachedCriteriaProxy, null);
		if (reports.size() == 1)
			return reports.get(0);
		else 
			return null;
	}

	public void setMinePersistenceIface(
			MinePersistenceIface minePersistenceIface) {
		this.minePersistenceIface = minePersistenceIface;
	}

	public void setAreaPersistenceIface(
			AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}
}
