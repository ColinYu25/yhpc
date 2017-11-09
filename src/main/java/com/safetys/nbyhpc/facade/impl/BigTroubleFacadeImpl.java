package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.ProjectionsProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjStatistic;
import com.safetys.nbyhpc.domain.persistence.iface.BigTroublePersistenceIface;
import com.safetys.nbyhpc.facade.iface.BigTroubleFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;

public class BigTroubleFacadeImpl implements BigTroubleFacadeIface {

	private BigTroublePersistenceIface bigTroublePersistenceIface;

	private MineFacadeIface mineFacadeIface;

	public void createBigTrouble(ZjBigTrouble bigTrouble) {
		bigTroublePersistenceIface.createBigTrouble(bigTrouble);
	}

	public List<ZjBigTrouble> loadBigTroubles(Long areaCode, Integer type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class, "lbt");
		detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("lbt.userId.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.tradeType", type));// 显示列表类型（矿山、其他）
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", Nbyhpc.AREA_CODE));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", areaCode));
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("fui.thirdArea"));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select t.danger_id from zj_danger t )"));
		return bigTroublePersistenceIface.loadBigTroubles(detachedCriteriaProxy, null);
	}

	@SuppressWarnings("deprecation")
	public List<ZjBigTrouble> loadBigTroubles(ZjBigTrouble bigTrouble, Integer mineType, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class, "lbt");
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.property("id")).add(ProjectionsProxy.property("reportMonth")).add(ProjectionsProxy.property("companyName")).add(ProjectionsProxy.property("address")).add(ProjectionsProxy.property("content")).add(ProjectionsProxy.property("state")).add(ProjectionsProxy.sqlProjection("(SELECT state FROM ZJ_MINE_REPORT WHERE user_id=this_.user_id and report_month=this_.report_month and rownum=1 and is_deleted=0) as chargeMan ", new String[] { "chargeMan"

		}, new Type[] { (new StringType()) })).add(ProjectionsProxy.property("tradeType")).add

		(ProjectionsProxy.property("stateTime")));
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId

			().getFkUserInfo().getFirstArea()));// 设置省级用户显示列表
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("((select state from ZJ_MINE_REPORT " + "where user_id=this_.user_id and report_month=this_.report_month and type=this_.table_type and rownum=1) in(1) " + "or (select state from ZJ_MINE_REPORT " + "where report_month<=this_.report_month and type=this_.table_type" + " and state=1 and rownum =1 ) is not null)"));// 查询状态是否是上报，或者是否对应的矿山记录已上报
		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq

			("fui.firstArea", bigTrouble.getUserId().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq

			("fui.secondArea", bigTrouble.getUserId().getFkUserInfo().getSecondArea())), RestrictionsProxy.eq

			("lbt.userId", bigTrouble.getUserId())));// 设置市级用户显示列表
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId().getFkUserInfo

			().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", bigTrouble.getUserId().getFkUserInfo

			().getSecondArea())), RestrictionsProxy.eq("fui.thirdArea", bigTrouble.getUserId().getFkUserInfo

			().getThirdArea())), RestrictionsProxy.eq("lbt.userId", bigTrouble.getUserId())));// 设置县级用户显示列表
		}
		if (bigTrouble.getState() != -1) {
			if (bigTrouble.getState() == 1) {// 已销号页面
				// 注销时间小于等于查看时间并且状态是已注销
				detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", bigTrouble.getState()), RestrictionsProxy.le

				("lbt.stateTime", bigTrouble.getReportMonth())));
			} else if (bigTrouble.getState() == 0) {// 未销号页面
				// 状态是未注销或注销时间大于查看时间
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("lbt.state", bigTrouble.getState()),

				RestrictionsProxy.gt("lbt.stateTime", bigTrouble.getReportMonth())));
			}
		}
		if (!"109".equals(bigTrouble.getTradeType())) {// 如果是行业号109的，不再限制表格类型的条件，因为行业109代表是交通运输表中的“道路交通”行业
			// 应客户要求，该行业的数据需要包含矿山表中的行业13（危化品--道路运输企业）
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.tableType", bigTrouble.getTableType()));
		}
		if (bigTrouble.getTradeType() != 0) {
			if (bigTrouble.getTradeType() >= SafetyTrouble.WXHXPQY) {
				// if (mineType != null && !(mineType == 5 &&
				// bigTrouble.getTradeType().equals
				//
				// (SafetyTrouble.COUNTRY_TOTAL)))
				detachedCriteriaProxy.add(RestrictionsProxy.in("lbt.tradeType", SafetyTrouble.getTradeTypesByFatherCode(bigTrouble.getTradeType())));
			} else {
				// if (bigTrouble.getTradeType() == (6 - 1)
				// || bigTrouble.getTradeType() == (27 - 1)) {
				// detachedCriteriaProxy.add(RestrictionsProxy.eq(
				// "lbt.tradeType", bigTrouble.getTradeType()));
				// } else {
				if (mineType != null && mineType == 5 && bigTrouble.getTradeType() == 34) {
					detachedCriteriaProxy.add(RestrictionsProxy.in("lbt.tradeType",

					SafetyTrouble.FOR_OTHER_INDUSTRY));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.tradeType",

					bigTrouble.getTradeType()));
				}
				// }
			}
		}
		if ("2008-06".equals(bigTrouble.getReportMonth())) {
			detachedCriteriaProxy.add(RestrictionsProxy.ge("lbt.reportMonth", "2008-04"));
		}

		/**
		 * 过滤已销号并且上报时间不是今年的重大隐患 dijj修改于2010.02.25
		 */
		String thisYear = new Date().getYear() + 1900 + "";
		String thisYearMonth = (Integer.parseInt(thisYear) - 1) + "-12";
		if (thisYear.equals(bigTrouble.getReportMonth().split("-")[0])) {// 当年
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(// 上报时间不是今年，但销号时间是今年的也要显示
					RestrictionsProxy.eq("lbt.state", 1), RestrictionsProxy.gt("lbt.stateTime",

					thisYearMonth)), RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.gt("lbt.reportMonth",

			thisYear), RestrictionsProxy.le("lbt.reportMonth", bigTrouble.getReportMonth

			())), RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 0), RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth())))));
		} else {// 当前年以前的时间
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 1), RestrictionsProxy.gt("lbt.stateTime",

			(Integer.parseInt(bigTrouble.getReportMonth().split("-")[0]) - 1) + "-12")), RestrictionsProxy.and(RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth()), RestrictionsProxy.le("lbt.reportMonth",

			bigTrouble.getReportMonth()))), RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 0), RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth()))));
		}

		/**
		 * 新增搜索条件
		 * 
		 * @author lih
		 * @since 2012-5-29
		 */
		if (bigTrouble.isWdw()) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.targetTask", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.worker", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.safetyMethod", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.goods", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.governDate", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.state", 0));
		}
		if (bigTrouble.isGuapai()) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.isNotNull("lbt.guapaiLevel"), RestrictionsProxy.gt("lbt.guapaiLevel", 0)));
			if (bigTrouble.getGuapaiLevel() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.guapaiLevel",

				bigTrouble.getGuapaiLevel()));
			}
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.state", 0));
		}
		/**
		 * 
		 */
		detachedCriteriaProxy.addOrder(OrderProxy.asc("tradeType"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("lbt.modifyTime"));
		List troubles = bigTroublePersistenceIface.loadBigTroubles(detachedCriteriaProxy, null);
		List<ZjBigTrouble> troubles_2 = new ArrayList<ZjBigTrouble>();
		/**
		 * 分页
		 */
		int itemCount = 0;
		int afterCount = 0;
		pagination.setTotalCount(troubles.size());
		if (pagination.getItemCount() == 0) {
			itemCount = 0;
			if (pagination.getPageSize() > pagination.getTotalCount()) {
				afterCount = Integer.valueOf(String.valueOf(pagination.getTotalCount()));
			} else {
				afterCount = pagination.getPageSize();
			}
		} else if (pagination.getItemCount() > 0) {
			itemCount = pagination.getPageSize() * (pagination.getCurrentPage() - 1);
			if (pagination.getTotalCount() < pagination.getCurrentPage() * pagination.getPageSize()) {
				afterCount = Integer.valueOf(String.valueOf(pagination.getTotalCount()));
			} else {
				afterCount = pagination.getCurrentPage() * pagination.getPageSize();
			}
		}
		for (int i = itemCount; i < afterCount; i++) {
			Object[] trouble = (Object[]) troubles.get(i);
			ZjBigTrouble trouble_2 = new ZjBigTrouble();
			if (trouble[0] != null) {
				trouble_2.setId(new Long(trouble[0].toString()));
			}
			if (trouble[1] != null) {
				trouble_2.setReportMonth(trouble[1].toString());
			}
			if (trouble[2] != null) {
				trouble_2.setCompanyName(trouble[2].toString());
			}
			if (trouble[3] != null) {
				trouble_2.setAddress(trouble[3].toString());
			}
			if (trouble[4] != null) {
				trouble_2.setContent(trouble[4].toString());
			}
			if (trouble[5] != null) {
				trouble_2.setState(Integer.parseInt(trouble[5].toString()));
			}
			if (trouble[6] != null) {
				trouble_2.setChargeMan(trouble[6].toString());
			}
			if (trouble[7] != null) {
				trouble_2.setTradeType(Integer.parseInt(trouble[7].toString()));
			}
			if (trouble[8] != null) {
				trouble_2.setStateTime(trouble[8].toString());
			}
			troubles_2.add(trouble_2);
		}
		return troubles_2;
	}

	@SuppressWarnings("deprecation")
	public List<ZjBigTrouble> loadBigTroubles_excel(ZjBigTrouble bigTrouble, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class, "lbt");
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.property("id")).add(ProjectionsProxy.property("reportMonth")).add(ProjectionsProxy.property("companyName")).add(ProjectionsProxy.property("address")).add(ProjectionsProxy.property("content")).add(ProjectionsProxy.property("state")).add(ProjectionsProxy.sqlProjection("(SELECT area_name FROM fk_area WHERE area_code=(select third_area from fk_user_info where id=this_.user_id)) as chargeMan ", new String[] { "chargeMan"

		}, new Type[] { (new StringType()) })).add(ProjectionsProxy.property("tradeType")).add

		(ProjectionsProxy.property("targetTask")).add(ProjectionsProxy.property("goods")).add(ProjectionsProxy.property

		("worker")).add(ProjectionsProxy.property("governDate")).add

		(ProjectionsProxy.property("safetyMethod")));
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId

			().getFkUserInfo().getFirstArea()));// 设置省级用户显示列表
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("((select state from ZJ_MINE_REPORT " + "where user_id=this_.user_id and report_month=this_.report_month and type=this_.table_type and rownum=1) in(1) " + "or (select state from ZJ_MINE_REPORT " + "where report_month<=this_.report_month and type=this_.table_type" + " and state=1 and rownum =1 ) is not null)"));// 查询状态是否是上报，或者是否对应的矿山记录已上报
		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq

			("fui.firstArea", bigTrouble.getUserId().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq

			("fui.secondArea", bigTrouble.getUserId().getFkUserInfo().getSecondArea())), RestrictionsProxy.eq

			("lbt.userId", bigTrouble.getUserId())));// 设置市级用户显示列表
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId().getFkUserInfo

			().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", bigTrouble.getUserId().getFkUserInfo

			().getSecondArea())), RestrictionsProxy.eq("fui.thirdArea", bigTrouble.getUserId().getFkUserInfo

			().getThirdArea())), RestrictionsProxy.eq("lbt.userId", bigTrouble.getUserId())));// 设置县级用户显示列表
		}
		if (bigTrouble.getState() != -1) {
			if (bigTrouble.getState() == 1) {// 已销号页面
				// 注销时间小于等于查看时间并且状态是已注销
				detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", bigTrouble.getState()), RestrictionsProxy.le

				("lbt.stateTime", bigTrouble.getReportMonth())));
			} else if (bigTrouble.getState() == 0) {// 未销号页面
				// 状态是未注销或注销时间大于查看时间
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("lbt.state", bigTrouble.getState()),

				RestrictionsProxy.gt("lbt.stateTime", bigTrouble.getReportMonth())));
			}
		}

		if (!"109".equals(bigTrouble.getTradeType())) {// 如果是行业号109的，不再限制表格类型的条件，因为行业109代表是交通运输表中的“道路交通”行业
			// 应客户要求，该行业的数据需要包含矿山表中的行业13（危化品--道路运输企业）
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.tableType", bigTrouble.getTableType()));
		}

		if (bigTrouble.getTradeType() != 0) {
			if (bigTrouble.getTradeType() >= SafetyTrouble.WXHXPQY) {
				detachedCriteriaProxy.add(RestrictionsProxy.in("lbt.tradeType", SafetyTrouble.getTradeTypesByFatherCode(bigTrouble.getTradeType())));
			} else {
				// if (bigTrouble.getTradeType() == (6 - 1)
				// || bigTrouble.getTradeType() == (27 - 1)) {
				// detachedCriteriaProxy.add(RestrictionsProxy.eq(
				// "lbt.tradeType", bigTrouble.getTradeType()));
				// } else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.tradeType", bigTrouble.getTradeType

				()));
				// }
			}
		}
		if ("2008-06".equals(bigTrouble.getReportMonth())) {
			detachedCriteriaProxy.add(RestrictionsProxy.ge("lbt.reportMonth", "2008-04"));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.le("lbt.reportMonth",
		// bigTrouble.getReportMonth()));
		/**
		 * 过滤已销号并且上报时间不是今年的重大隐患 dijj修改于2010.02.25
		 */
		String thisYear = new Date().getYear() + 1900 + "";
		String thisYearMonth = (Integer.parseInt(thisYear) - 1) + "-12";
		if (thisYear.equals(bigTrouble.getReportMonth().split("-")[0])) {
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(// 上报时间不是今年，但销号时间是今年的也要显示
					RestrictionsProxy.eq("lbt.state", 1), RestrictionsProxy.gt("lbt.stateTime",

					thisYearMonth)), RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.gt("lbt.reportMonth",

			thisYear), RestrictionsProxy.le("lbt.reportMonth", bigTrouble.getReportMonth

			())), RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 0), RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth())))));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 1), RestrictionsProxy.gt("lbt.stateTime",

			(Integer.parseInt(bigTrouble.getReportMonth().split("-")[0]) - 1) + "-12")), RestrictionsProxy.and(RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth()), RestrictionsProxy.le("lbt.reportMonth",

			bigTrouble.getReportMonth()))), RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 0), RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth()))));
		}

		/**
		 * 新增搜索条件
		 * 
		 * @author lih
		 * @since 2012-5-29
		 */
		if (bigTrouble.isWdw()) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.targetTask", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.worker", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.safetyMethod", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.goods", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.governDate", 1));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.state", 0));
		}
		if (bigTrouble.isGuapai()) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.isNotNull("lbt.guapaiLevel"), RestrictionsProxy.gt("lbt.guapaiLevel", 0)));
			if (bigTrouble.getGuapaiLevel() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.guapaiLevel",

				bigTrouble.getGuapaiLevel()));
			}
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.state", 0));
		}

		detachedCriteriaProxy.addOrder(OrderProxy.asc("tradeType"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("lbt.modifyTime"));
		List<ZjBigTrouble> troubles = bigTroublePersistenceIface.loadBigTroubles(detachedCriteriaProxy, null);
		List<ZjBigTrouble> troubles_2 = new ArrayList<ZjBigTrouble>();
		Iterator iterator = troubles.iterator();
		while (iterator.hasNext()) {
			Object[] trouble = (Object[]) iterator.next();
			ZjBigTrouble trouble_2 = new ZjBigTrouble();
			if (trouble[0] != null) {
				trouble_2.setId(new Long(trouble[0].toString()));
			}
			if (trouble[1] != null) {
				trouble_2.setReportMonth(trouble[1].toString());
			}
			if (trouble[2] != null) {
				trouble_2.setCompanyName(trouble[2].toString());
			}
			if (trouble[3] != null) {
				trouble_2.setAddress(trouble[3].toString());
			}
			if (trouble[4] != null) {
				trouble_2.setContent(trouble[4].toString());
			}
			if (trouble[5] != null) {
				trouble_2.setState(Integer.parseInt(trouble[5].toString()));
			}
			if (trouble[6] != null) {
				trouble_2.setChargeMan(trouble[6].toString());
			}
			if (trouble[7] != null) {
				trouble_2.setTradeType(Integer.parseInt(trouble[7].toString()));
			}
			if (trouble[8] != null) {
				trouble_2.setTargetTask(Integer.parseInt(trouble[8].toString()));
			}
			if (trouble[9] != null) {
				trouble_2.setGoods(Integer.parseInt(trouble[9].toString()));
			}
			if (trouble[10] != null) {
				trouble_2.setWorker(Integer.parseInt(trouble[10].toString()));
			}
			if (trouble[11] != null) {
				trouble_2.setGovernDate(Integer.parseInt(trouble[11].toString()));
			}
			if (trouble[12] != null) {
				trouble_2.setSafetyMethod(Integer.parseInt(trouble[12].toString()));
			}
			troubles_2.add(trouble_2);
		}
		return troubles_2;
	}

	public List<ZjBigTrouble> loadAllBigTroubles(ZjBigTrouble bigTrouble, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class);
		if (bigTrouble.getCompanyName() != null && !"".equals(bigTrouble.getCompanyName())) {
			detachedCriteriaProxy.add(RestrictionsProxy.like("companyName", bigTrouble.getCompanyName(), MatchMode.ANYWHERE));
		}
		return bigTroublePersistenceIface.loadBigTroubles(detachedCriteriaProxy, pagination);
	}

	public void deleteBigTrouble(ZjBigTrouble bigTrouble, FkUser fkUser) throws ApplicationAccessException {
		bigTroublePersistenceIface.deleteBigTrouble(bigTrouble, fkUser);
	}

	public void updateBigTrouble(ZjBigTrouble bigTrouble) throws ApplicationAccessException {
		ZjBigTrouble trouble = bigTroublePersistenceIface.loadBigTrouble(bigTrouble.getId());
		trouble.setContent(bigTrouble.getContent());
		trouble.setRemark(bigTrouble.getRemark());
		trouble.setCompanyName(bigTrouble.getCompanyName());
		trouble.setAddress(bigTrouble.getAddress());
		trouble.setGoods(bigTrouble.getGoods());
		trouble.setGovernDate(bigTrouble.getGovernDate());
		trouble.setWorker(bigTrouble.getWorker());
		trouble.setTargetTask(bigTrouble.getTargetTask());
		trouble.setSafetyMethod(bigTrouble.getSafetyMethod());
		trouble.setGuapaiLevel(bigTrouble.getGuapaiLevel());
		trouble.setModifyTime(bigTrouble.getModifyTime());
		bigTroublePersistenceIface.updateBigTrouble(trouble);
	}

	public void updateBigTroubleRemark(ZjBigTrouble bigTrouble) throws ApplicationAccessException {
		ZjBigTrouble trouble = bigTroublePersistenceIface.loadBigTrouble(bigTrouble.getId());
		trouble.setRemark(bigTrouble.getRemark());
		trouble.setTargetTask(bigTrouble.getTargetTask());
		trouble.setGoods(bigTrouble.getGoods());
		trouble.setWorker(bigTrouble.getWorker());
		trouble.setGovernDate(bigTrouble.getGovernDate());
		trouble.setSafetyMethod(bigTrouble.getSafetyMethod());
		trouble.setGuapaiLevel(bigTrouble.getGuapaiLevel());
		bigTroublePersistenceIface.updateBigTrouble(trouble);
	}

	public void updateBigTroubleTime(ZjBigTrouble bigTrouble) throws ApplicationAccessException {
		ZjBigTrouble trouble = bigTroublePersistenceIface.loadBigTrouble(bigTrouble.getId());
		trouble.setStateTime("2008-07");
		bigTroublePersistenceIface.updateBigTrouble(trouble);
	}

	public ZjBigTrouble loadBigTrouble(long id) throws ApplicationAccessException {
		return bigTroublePersistenceIface.loadBigTrouble(id);
	}

	public void updateBigTroubleOut(ZjBigTrouble bigTrouble) throws ApplicationAccessException {
		ZjBigTrouble temp = bigTroublePersistenceIface.loadBigTrouble(bigTrouble.getId());
		if (bigTrouble.getState() == 0) {
			temp.setState(1);// 销号
			temp.setStateTime(bigTrouble.getReportMonth());
			temp.setModifyTime(new Date());
		} else if (bigTrouble.getState() == 1) {
			temp.setState(0);// 反销号
			temp.setStateTime("");
			temp.setModifyTime(new Date());
		}
		bigTroublePersistenceIface.updateBigTroubleOut(temp);
	}

	/**
	 * 为矿山和其他获取对应的重大隐患数据
	 */
	@SuppressWarnings("deprecation")
	public List<ZjStatistic> loadStatisticForMine(ZjMineReport mineReport) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class, "zbt");
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.countDistinct("id")).add(ProjectionsProxy.sqlProjection("SUM( " + "(SELECT (" + "case when this_.report_month <=report_month then state else 0 end" + ") FROM Zj_Big_Trouble " + "WHERE state_Time<='" + mineReport.getReportMonth() + "' AND id=this_.id)) as state ", new String[] { "state" }, new Type[] { (new

		IntegerType()) })

		).add(ProjectionsProxy.sqlProjection("SUM( (SELECT target_task from zj_big_trouble where " + "id=this_.id and state=0) ) as targetTask ", new String[] { "targetTask" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT goods from zj_big_trouble where " + "id=this_.id and state=0) ) as goods ",

		new String[] { "goods" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT worker from zj_big_trouble where " + "id=this_.id and state=0) ) as worker ",

		new String[] { "worker" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT govern_date from zj_big_trouble where " + "id=this_.id and state=0) ) as governDate ", new String[] { "governDate" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT safety_method from zj_big_trouble where " + "id=this_.id and state=0) ) as safetyMethod ", new String[] { "safetyMethod" }, new Type[]

		{ (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when ( " + " state=0 and safety_method=1 and govern_date=1 and worker=1 and goods=1 and target_task=1) then 1 else 0 end ) as wdw ", new String[] { "wdw" }, new

		Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when( " + " guapai_level is not null and guapai_level>0) then 1 else 0 end ) as guapaiTotal ", new String[] {

		"guapaiTotal" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when ( " + "  guapai_level =3) then 1 else 0 end ) as provinceGuapai ", new String[] { "provinceGuapai" }, new

		Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when ( " + "  guapai_level =2) then 1 else 0 end ) as cityGuapai ", new String[] { "cityGuapai" }, new

		Type[] { (new IntegerType()) })).add

		(ProjectionsProxy.groupProperty("tradeType")));
		if (mineReport != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("tableType", mineReport.getType()));
			if (mineReport.getReportMonth() != null) {
				if ("2008-06".equals(mineReport.getReportMonth())) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("reportMonth", "2008-04"));
				}
				// detachedCriteriaProxy.add(RestrictionsProxy.le("reportMonth",
				// mineReport.getReportMonth()));
				/**
				 * 过滤已销号并且上报时间不是今年的重大隐患 dijj修改于2010.02.25
				 */
				String thisYear = new Date().getYear() + 1900 + "";
				String thisYearMonth = (Integer.parseInt(thisYear) - 1) + "-12";
				if (thisYear.equals(mineReport.getReportMonth().split("-")[0])) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(// 上报时间不是今年，但销号时间是今年的也要显示
							RestrictionsProxy.eq("zbt.state", 1),

							RestrictionsProxy.gt("zbt.stateTime", thisYearMonth)),

					RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.gt("zbt.reportMonth", thisYear),

					RestrictionsProxy.le("zbt.reportMonth",

					mineReport.getReportMonth())), RestrictionsProxy.and(RestrictionsProxy.eq("zbt.state", 0),

					RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth())))));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and

					(RestrictionsProxy.and(// 上报时间不是今年，但销号时间是今年的也要显示
							RestrictionsProxy.eq("zbt.state", 1), RestrictionsProxy.gt

							("zbt.stateTime", (Integer.parseInt(mineReport.getReportMonth().split("-")

							[0]) - 1) + "-12")), RestrictionsProxy.and

					(RestrictionsProxy.le("reportMonth", mineReport.getReportMonth()), RestrictionsProxy.le("reportMonth",

					mineReport.getReportMonth()))), RestrictionsProxy.and(RestrictionsProxy.eq("zbt.state", 0),

					RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth()))));
				}
			}
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport.getUserId().getFkRoles())) {
				return getReportTemp(mineReport);
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zbt.userId", "fu").createCriteria

				("zbt.userId.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", mineReport.getUserId().getFkUserInfo().getFirstArea()),

				RestrictionsProxy.eq("fui.secondArea", mineReport.getUserId().getFkUserInfo().getSecondArea())),

				RestrictionsProxy.or(RestrictionsProxy.eq("zbt.userId", mineReport.getUserId()),

				RestrictionsProxy.sqlRestriction("fu1_.id=(SELECT id FROM fk_user_info WHERE" + " second_area = '" + mineReport.getUserId().getFkUserInfo

				().getSecondArea() + "' and third_area is null and rownum=1)"))));// 设置市级用户
			} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, mineReport.getUserId().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zbt.userId", "fu").createCriteria

				("zbt.userId.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea",

				mineReport.getUserId().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq

				("fui.secondArea", mineReport.getUserId().getFkUserInfo().getSecondArea())),

				RestrictionsProxy.eq("fui.thirdArea", mineReport.getUserId().getFkUserInfo().getThirdArea())),

				RestrictionsProxy.eq("zbt.userId", mineReport.getUserId())));// 设置县级用户
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.asc("tradeType"));
		List<ZjBigTrouble> troubles = bigTroublePersistenceIface.loadBigTroublesForStatistic

		(detachedCriteriaProxy);
		Iterator iterator = troubles.iterator();
		List<ZjStatistic> statistics = new ArrayList<ZjStatistic>();
		String MineOrOther = (mineReport.getType() == SafetyTrouble.MINE_TYPE || mineReport.getType() == 6) ?

		SafetyTrouble.MINE : SafetyTrouble.OTHER;
		while (iterator.hasNext()) {
			Object[] trouble = (Object[]) iterator.next();
			ZjStatistic statistic = new ZjStatistic();
			if (trouble[0] != null) {
				statistic.setBigTrouble(Integer.parseInt(trouble[0].toString()));
			}
			if (trouble[1] != null) {
				statistic.setBigTroubleGovern(Integer.parseInt(trouble[1].toString()));
			}
			if (trouble[2] != null) {
				statistic.setTargetTask(Integer.parseInt(trouble[2].toString()));
			}
			if (trouble[3] != null) {
				statistic.setGoods(Integer.parseInt(trouble[3].toString()));
			}
			if (trouble[4] != null) {
				statistic.setWorker(Integer.parseInt(trouble[4].toString()));
			}
			if (trouble[5] != null) {
				statistic.setGovernDate(Integer.parseInt(trouble[5].toString()));
			}
			if (trouble[6] != null) {
				statistic.setSafetyMethod(Integer.parseInt(trouble[6].toString()));
			}
			if (trouble[7] != null) {
				statistic.setWdw(Integer.parseInt(trouble[7].toString()));
			}
			if (trouble[8] != null) {
				statistic.setGuapaiTotal(Integer.parseInt(trouble[8].toString()));
			}
			if (trouble[9] != null) {
				statistic.setProvinceGuapai(Integer.parseInt(trouble[9].toString()));
			}
			if (trouble[10] != null) {
				statistic.setCityGuapai(Integer.parseInt(trouble[10].toString()));
			}

			if (trouble[11] != null) {
				statistic.setType(Integer.parseInt(trouble[11].toString()));
			}
			statistic.setMineOrOther(MineOrOther);
			statistics.add(statistic);
		}
		List<ZjStatistic> ss = switchStatisticList(statistics, MineOrOther, mineReport);
		return ss;
	}

	private List<ZjStatistic> getReportTemp(ZjMineReport mineReport) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class, "zbt");
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.countDistinct("id")).add(ProjectionsProxy.sqlProjection("SUM( " + "(SELECT (case when this_.report_month <=report_month then state else 0 end) " + "FROM Zj_Big_Trouble WHERE state_Time<='" +

		mineReport.getReportMonth() + "' AND id=this_.id)) as state ", new String[] { "state" },

		new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT target_task from zj_big_trouble where " + "id=this_.id and state=0) ) as targetTask ", new String[] { "targetTask" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT goods from zj_big_trouble where " + "id=this_.id and state=0) ) as goods ",

		new String[] { "goods" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT worker from zj_big_trouble where " + "id=this_.id and state=0) ) as worker ",

		new String[] { "worker" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT govern_date from zj_big_trouble where " + "id=this_.id and state=0) ) as governDate ", new String[] { "governDate" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( (SELECT safety_method from zj_big_trouble where " + "id=this_.id and state=0) ) as safetyMethod ", new String[] { "safetyMethod" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when( " + " state=0 and  safety_method=1 and govern_date=1 and worker=1 and goods=1 and target_task=1) then 1 else 0 end ) as wdw ", new String[] { "wdw" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when( " + " guapai_level is not null and guapai_level>0) then 1 else 0 end ) as guapaiTotal ", new String[] { "guapaiTotal" }, new Type[] { (new IntegerType()) }))
				.add(ProjectionsProxy.sqlProjection("SUM( case when ( " + "  guapai_level =3) then 1 else 0 end ) as provinceGuapai ", new String[] { "provinceGuapai" }, new

				Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("SUM( case when ( " + "  guapai_level =2) then 1 else 0 end ) as cityGuapai ", new String[] { "cityGuapai" }, new

				Type[] { (new IntegerType()) })).add(ProjectionsProxy.groupProperty

				("tradeType")));
		detachedCriteriaProxy.createCriteria("zbt.userId", "fu").createCriteria("zbt.userId.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", mineReport.getUserId().getFkUserInfo().getFirstArea()));// 设置省级用户显示列表
		// if ("2008-06".equals(mineReport.getReportMonth())) {
		// detachedCriteriaProxy.add(RestrictionsProxy.ge("zbt.reportMonth",
		// "2008-04"));
		// }
		// detachedCriteriaProxy.add(RestrictionsProxy.le("zbt.reportMonth",
		// mineReport.getReportMonth()));
		// detachedCriteriaProxy
		// .add(RestrictionsProxy
		// .sqlRestriction("((select state from ZJ_MINE_REPORT "
		// +
		// "where user_id=this_.user_id and report_month=this_.report_month and type=this_.table_type and rownum=1) in(1) "
		// + "or (select state from ZJ_MINE_REPORT "
		// + "where report_month<=this_.report_month and type=this_.table_type"
		// + " and state=1 and rownum =1 ) is not null)"));//
		// 查询状态是否是上报，或者是否对应的矿山记录已上报
		if (mineReport.getReportMonth() != null) {
			if ("2008-06".equals(mineReport.getReportMonth())) {
				detachedCriteriaProxy.add(RestrictionsProxy.ge("zbt.reportMonth", "2008-04"));
			}
			String thisYear = new Date().getYear() + 1900 + "";
			String thisYearMonth = Integer.parseInt(thisYear) - 1 + "-12";
			if (thisYear.equals(mineReport.getReportMonth().split("-")[0])) {
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq("zbt.state", Integer.valueOf(1)), RestrictionsProxy.gt("zbt.stateTime", thisYearMonth)), RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.gt("zbt.reportMonth", thisYear), RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth())), RestrictionsProxy.and(RestrictionsProxy.eq("zbt.state", Integer.valueOf(0)), RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth())))));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("zbt.state", Integer.valueOf(1)), RestrictionsProxy.gt("zbt.stateTime", Integer.parseInt(mineReport.getReportMonth().split("-")[0]) - 1 + "-12")), RestrictionsProxy.and(RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth()), RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth()))), RestrictionsProxy.and(RestrictionsProxy.eq("zbt.state", Integer.valueOf(0)), RestrictionsProxy.le("zbt.reportMonth", mineReport.getReportMonth()))));
			}

		}

		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("((select state from ZJ_MINE_REPORT where user_id=this_.user_id and report_month=this_.report_month and type=this_.table_type and rownum=1) in(1) or (select state from ZJ_MINE_REPORT where report_month<=this_.report_month and type=this_.table_type and state=1 and rownum =1 ) is not null)"));

		detachedCriteriaProxy.addOrder(OrderProxy.asc("tradeType"));
		List<ZjBigTrouble> troubles = bigTroublePersistenceIface.loadBigTroublesForStatistic

		(detachedCriteriaProxy);
		Iterator iterator = troubles.iterator();
		List<ZjStatistic> statistics = new ArrayList<ZjStatistic>();
		String MineOrOther = (mineReport.getType() == SafetyTrouble.MINE_TYPE || mineReport.getType() == 6) ?

		SafetyTrouble.MINE : SafetyTrouble.OTHER;
		while (iterator.hasNext()) {
			Object[] trouble = (Object[]) iterator.next();
			ZjStatistic statistic = new ZjStatistic();
			if (trouble[0] != null) {
				statistic.setBigTrouble(Integer.parseInt(trouble[0].toString()));
			}
			if (trouble[1] != null) {
				statistic.setBigTroubleGovern(Integer.parseInt(trouble[1].toString()));
			}
			if (trouble[2] != null) {
				statistic.setTargetTask(Integer.parseInt(trouble[2].toString()));
			}
			if (trouble[3] != null) {
				statistic.setGoods(Integer.parseInt(trouble[3].toString()));
			}
			if (trouble[4] != null) {
				statistic.setWorker(Integer.parseInt(trouble[4].toString()));
			}
			if (trouble[5] != null) {
				statistic.setGovernDate(Integer.parseInt(trouble[5].toString()));
			}
			if (trouble[6] != null) {
				statistic.setSafetyMethod(Integer.parseInt(trouble[6].toString()));
			}
			if (trouble[7] != null) {
				statistic.setWdw(Integer.parseInt(trouble[7].toString()));
			}
			if (trouble[8] != null) {
				statistic.setGuapaiTotal(Integer.parseInt(trouble[8].toString()));
			}
			if (trouble[9] != null) {
				statistic.setProvinceGuapai(Integer.parseInt(trouble[9].toString()));
			}
			if (trouble[10] != null) {
				statistic.setCityGuapai(Integer.parseInt(trouble[10].toString()));
			}

			if (trouble[11] != null) {
				statistic.setType(Integer.parseInt(trouble[11].toString()));
			}
			statistic.setMineOrOther(MineOrOther);
			statistics.add(statistic);
		}
		return switchStatisticList(statistics, MineOrOther, mineReport);
	}

	/**
	 * 私有方法,为获取重大隐患的统计数据做修正、补全、增加合计
	 * 
	 * @param statistics
	 * @param mineOrOther
	 * @return
	 */
	private List<ZjStatistic> switchStatisticList(List<ZjStatistic> statistics, String mineOrOther, ZjMineReport mineReport) {
		List<ZjStatistic> temps = new ArrayList<ZjStatistic>();
		ZjStatistic temp = new ZjStatistic();
		int bigTrouble = 0;
		int bigTroubleGovern = 0;
		int bigTroubleNotGovern = 0;
		int targetTask = 0;
		int goods = 0;
		int worker = 0;
		int governDate = 0;
		int safetyMethod = 0;
		int wdw = 0;
		int guapaiTotal = 0;
		int provinceGuapai = 0;
		int cityGuapai = 0;
		int[] trades = SafetyTrouble.getType(mineOrOther);
		if (statistics.size() == 0) {
			for (int i = 0; i < trades.length; i++) {
				temp = new ZjStatistic();
				temp.setMineOrOther(mineOrOther);
				temp.setType(trades[i]);
				temps.add(temp);
			}
		} else {
			for (int i = 0; i < trades.length; i++) {
				for (int j = 0; j < statistics.size(); j++) {
					if (statistics.get(j).getType() == trades[i]) {
						bigTrouble += statistics.get(j).getBigTrouble();
						bigTroubleGovern += statistics.get(j).getBigTroubleGovern();
						targetTask += statistics.get(j).getTargetTask();
						goods += statistics.get(j).getGoods();
						worker += statistics.get(j).getWorker();
						governDate += statistics.get(j).getGovernDate();
						safetyMethod += statistics.get(j).getSafetyMethod();
						wdw += statistics.get(j).getWdw();
						guapaiTotal += statistics.get(j).getGuapaiTotal();
						provinceGuapai += statistics.get(j).getProvinceGuapai();
						cityGuapai += statistics.get(j).getCityGuapai();
						// if (trades[i] == 6 || trades[i] == 27) {// 判断子项
						// if (j - 1 >= 0
						// && (statistics.get(j - 1).getType() == (6 - 1) ||
						// statistics
						// .get(j - 1).getType() == (27 - 1))) {
						// statistics.get(j - 1).setBigTrouble(
						// statistics.get(j).getBigTrouble()
						// + statistics.get(j - 1)
						// .getBigTrouble());
						// statistics.get(j - 1).setBigTroubleGovern(
						// statistics.get(j).getBigTroubleGovern()
						// + statistics.get(j - 1)
						// .getBigTroubleGovern());
						// statistics.get(j - 1).setTargetTask(
						// statistics.get(j).getTargetTask()
						// + statistics.get(j - 1)
						// .getTargetTask());
						// statistics.get(j - 1).setGoods(
						// statistics.get(j).getGoods()
						// + statistics.get(j - 1)
						// .getGoods());
						// statistics.get(j - 1).setWorker(
						// statistics.get(j).getWorker()
						// + statistics.get(j - 1)
						// .getWorker());
						// statistics.get(j - 1).setGovernDate(
						// statistics.get(j).getGovernDate()
						// + statistics.get(j - 1)
						// .getGovernDate());
						// statistics.get(j - 1).setSafetyMethod(
						// statistics.get(j).getSafetyMethod()
						// + statistics.get(j - 1)
						// .getSafetyMethod());
						// } else {
						// temps.get(temps.size() - 1).setBigTrouble(
						// statistics.get(j).getBigTrouble());
						// temps.get(temps.size() - 1)
						// .setBigTroubleGovern(
						// statistics.get(j)
						// .getBigTroubleGovern());
						// temps.get(temps.size() - 1).setTargetTask(
						// statistics.get(j).getTargetTask());
						// temps.get(temps.size() - 1).setGoods(
						// statistics.get(j).getGoods());
						// temps.get(temps.size() - 1).setWorker(
						// statistics.get(j).getWorker());
						// temps.get(temps.size() - 1).setGovernDate(
						// statistics.get(j).getGovernDate());
						// temps.get(temps.size() - 1).setSafetyMethod(
						// statistics.get(j).getSafetyMethod());
						// }
						// temps.add(statistics.get(j));
						// break;
						// } else {
						temp = statistics.get(j);
						temps.add(temp);
						break;
						// }
					} else if (j == statistics.size() - 1) {
						temp = new ZjStatistic();
						temp.setMineOrOther(mineOrOther);
						temp.setType(trades[i]);
						temps.add(temp);
					}
				}
			}
		}
		temp = new ZjStatistic();
		temp.setMineOrOther(mineOrOther);
		temp.setType(0);
		temp.setBigTrouble(bigTrouble);
		temp.setBigTroubleGovern(bigTroubleGovern);
		temp.setBigTroubleNotGovern(bigTroubleNotGovern);
		temp.setTargetTask(targetTask);
		temp.setGoods(goods);
		temp.setWorker(worker);
		temp.setGovernDate(governDate);
		temp.setSafetyMethod(safetyMethod);
		temp.setWdw(wdw);
		temp.setGuapaiTotal(guapaiTotal);
		temp.setProvinceGuapai(provinceGuapai);
		temp.setCityGuapai(cityGuapai);
		temps.add(temp);
		if (mineReport.getType() == 5) {
			for (int i = 0; i < temps.size(); i++) {
				for (int j = 0; j < SafetyTrouble.FOR_OTHER_INDUSTRY.length; j++) {
					if (temps.get(i).getType() == SafetyTrouble.FOR_OTHER_INDUSTRY[j] && temps.get(16).getType() != SafetyTrouble.FOR_OTHER_INDUSTRY[j])

					{
						temps.get(16).setBigTrouble(temps.get(16).getBigTrouble() + temps.get

						(i).getBigTrouble());
						temps.get(16).setBigTroubleGovern(temps.get(16).getBigTroubleGovern() + temps.get

						(i).getBigTroubleGovern());
						temps.get(16).setBigTroubleNotGovern(temps.get(16).getBigTroubleNotGovern() + temps.get

						(i).getBigTroubleNotGovern());
						temps.get(16).setTargetTask(temps.get(16).getTargetTask() + temps.get

						(i).getTargetTask());
						temps.get(16).setGoods(temps.get(16).getGoods() + temps.get(i).getGoods());
						temps.get(16).setWorker(temps.get(16).getWorker() + temps.get(i).getWorker

						());
						temps.get(16).setGovernDate(temps.get(16).getGovernDate() + temps.get

						(i).getGovernDate());
						temps.get(16).setSafetyMethod(temps.get(16).getSafetyMethod() + temps.get

						(i).getSafetyMethod());
						temps.get(16).setWdw(temps.get(16).getWdw() + temps.get(i).getWdw());
						temps.get(16).setGuapaiTotal(temps.get(16).getGuapaiTotal() + temps.get

						(i).getGuapaiTotal());
						temps.get(16).setProvinceGuapai(temps.get(16).getProvinceGuapai() + temps.get

						(i).getProvinceGuapai());
						temps.get(16).setCityGuapai(temps.get(16).getCityGuapai() + temps.get

						(i).getCityGuapai());
					}
				}
			}
		}
		return temps;
	}

	public List<ZjStatistic> getStatistic(List<ZjMineReport> mineReports, int[] types) throws ApplicationAccessException {
		List<ZjStatistic> statistics = new ArrayList<ZjStatistic>();
		List<List<ZjStatistic>> statisticsTotal = new ArrayList<List<ZjStatistic>>();
		for (ZjMineReport mine : mineReports) {
			statisticsTotal.add(loadStatisticForMine(mine));
		}
		for (int i = 0; i < types.length; i++) {
			ZjStatistic detail = new ZjStatistic(types[i], 0, 0, 0, 0, 0, 0, 0);
			statistics.add(detail);
		}
		statistics.add(new ZjStatistic(0, 0, 0, 0, 0, 0, 0, 0));
		for (int i = 0; i < statisticsTotal.size(); i++) {// 循环到下一级
			for (ZjStatistic statistic : statisticsTotal.get(i)) {// 循环到行业
				for (int j = 0; j < statistics.size(); j++) {// 循环对比行业
					ZjStatistic stati = statistics.get(j);
					if (stati.getType() == statistic.getType()) {// 对比
						stati.addStatistic(statistic);// 累加
					}
				}
			}
		}
		return statistics;
	}

	public boolean checkBigTroubleHaveNoChooseGuapaiLevel(UserDetailWrapper userDetail, ZjMineReport mineReport) throws ApplicationAccessException {
		ZjBigTrouble bigTrouble = new ZjBigTrouble();
		// mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
		bigTrouble.setReportMonth(mineReport.getReportMonth());
		bigTrouble.setTableType(mineReport.getType());
		bigTrouble.setUserId(mineReport.getUserId());
		List<ZjBigTrouble> troubles = loadBigTroubleHaveNoChooseGuapaiLevel(userDetail, bigTrouble, null);
		if (troubles != null && troubles.size() > 0) {
			return false;
		}
		return true;
	}

	public List<ZjBigTrouble> loadBigTroubleHaveNoChooseGuapaiLevel(UserDetailWrapper userDetail, ZjBigTrouble bigTrouble, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjBigTrouble.class, "lbt");
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.property("id")).add(ProjectionsProxy.property("reportMonth")).add(ProjectionsProxy.property("companyName")).add(ProjectionsProxy.property("address")).add(ProjectionsProxy.property("content")).add(ProjectionsProxy.property("state")).add(ProjectionsProxy.sqlProjection("(SELECT state FROM ZJ_MINE_REPORT WHERE user_id=this_.user_id and report_month=this_.report_month and rownum=1 and is_deleted=0) as chargeMan ", new String[] { "chargeMan"

		}, new Type[] { (new StringType()) })).add(ProjectionsProxy.property("tradeType")).add

		(ProjectionsProxy.property("stateTime")));
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId

			().getFkUserInfo().getFirstArea()));// 设置省级用户显示列表
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("((select state from ZJ_MINE_REPORT " + "where user_id=this_.user_id and report_month=this_.report_month and type=this_.table_type and rownum=1) in(1) " + "or (select state from ZJ_MINE_REPORT " + "where report_month<=this_.report_month and type=this_.table_type" + " and state=1 and rownum =1 ) is not null)"));// 查询状态是否是上报，或者是否对应的矿山记录已上报
		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq

			("fui.firstArea", bigTrouble.getUserId().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq

			("fui.secondArea", bigTrouble.getUserId().getFkUserInfo().getSecondArea())), RestrictionsProxy.eq

			("lbt.userId", bigTrouble.getUserId())));// 设置市级用户显示列表
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, bigTrouble.getUserId().getFkRoles())) {
			detachedCriteriaProxy.createCriteria("lbt.userId", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", bigTrouble.getUserId().getFkUserInfo

			().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", bigTrouble.getUserId().getFkUserInfo

			().getSecondArea())), RestrictionsProxy.eq("fui.thirdArea", bigTrouble.getUserId().getFkUserInfo

			().getThirdArea())), RestrictionsProxy.eq("lbt.userId", bigTrouble.getUserId())));// 设置县级用户显示列表
		}
		if ("2008-06".equals(bigTrouble.getReportMonth())) {
			detachedCriteriaProxy.add(RestrictionsProxy.ge("lbt.reportMonth", "2008-04"));
		}

		/**
		 * 过滤已销号并且上报时间不是今年的重大隐患 dijj修改于2010.02.25
		 */
		String thisYear = new Date().getYear() + 1900 + "";
		String thisYearMonth = (Integer.parseInt(thisYear) - 1) + "-12";
		if (thisYear.equals(bigTrouble.getReportMonth().split("-")[0])) {// 当年
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(// 上报时间不是今年，但销号时间是今年的也要显示
					RestrictionsProxy.eq("lbt.state", 1), RestrictionsProxy.gt("lbt.stateTime",

					thisYearMonth)), RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.gt("lbt.reportMonth",

			thisYear), RestrictionsProxy.le("lbt.reportMonth", bigTrouble.getReportMonth

			())), RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 0), RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth())))));
		} else {// 当前年以前的时间
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 1), RestrictionsProxy.gt("lbt.stateTime",

			(Integer.parseInt(bigTrouble.getReportMonth().split("-")[0]) - 1) + "-12")), RestrictionsProxy.and(RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth()), RestrictionsProxy.le("lbt.reportMonth",

			bigTrouble.getReportMonth()))), RestrictionsProxy.and(RestrictionsProxy.eq("lbt.state", 0), RestrictionsProxy.le

			("lbt.reportMonth", bigTrouble.getReportMonth()))));
		}

		detachedCriteriaProxy.add(RestrictionsProxy.eq("lbt.guapaiLevel", 0));

		detachedCriteriaProxy.addOrder(OrderProxy.asc("tradeType"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("lbt.modifyTime"));
		List troubles = bigTroublePersistenceIface.loadBigTroubles(detachedCriteriaProxy, null);
		List<ZjBigTrouble> troubles_2 = new ArrayList<ZjBigTrouble>();
		if (pagination == null) {
			return troubles;
		}
		/**
		 * 分页
		 */
		int itemCount = 0;
		int afterCount = 0;
		pagination.setTotalCount(troubles.size());
		if (pagination.getItemCount() == 0) {
			itemCount = 0;
			if (pagination.getPageSize() > pagination.getTotalCount()) {
				afterCount = Integer.valueOf(String.valueOf(pagination.getTotalCount()));
			} else {
				afterCount = pagination.getPageSize();
			}
		} else if (pagination.getItemCount() > 0) {
			itemCount = pagination.getPageSize() * (pagination.getCurrentPage() - 1);
			if (pagination.getTotalCount() < pagination.getCurrentPage() * pagination.getPageSize()) {
				afterCount = Integer.valueOf(String.valueOf(pagination.getTotalCount()));
			} else {
				afterCount = pagination.getCurrentPage() * pagination.getPageSize();
			}
		}
		for (int i = itemCount; i < afterCount; i++) {
			Object[] trouble = (Object[]) troubles.get(i);
			ZjBigTrouble trouble_2 = new ZjBigTrouble();
			if (trouble[0] != null) {
				trouble_2.setId(new Long(trouble[0].toString()));
			}
			if (trouble[1] != null) {
				trouble_2.setReportMonth(trouble[1].toString());
			}
			if (trouble[2] != null) {
				trouble_2.setCompanyName(trouble[2].toString());
			}
			if (trouble[3] != null) {
				trouble_2.setAddress(trouble[3].toString());
			}
			if (trouble[4] != null) {
				trouble_2.setContent(trouble[4].toString());
			}
			if (trouble[5] != null) {
				trouble_2.setState(Integer.parseInt(trouble[5].toString()));
			}
			if (trouble[6] != null) {
				trouble_2.setChargeMan(trouble[6].toString());
			}
			if (trouble[7] != null) {
				trouble_2.setTradeType(Integer.parseInt(trouble[7].toString()));
			}
			if (trouble[8] != null) {
				trouble_2.setStateTime(trouble[8].toString());
			}
			troubles_2.add(trouble_2);
		}
		return troubles_2;
	}

	public ZjMineReport getMineUserId(long mineId) throws ApplicationAccessException {
		return mineFacadeIface.loadMineReport(mineId);
	}

	public void setMineFacadeIface(MineFacadeIface mineFacadeIface) {
		this.mineFacadeIface = mineFacadeIface;
	}

	public void setBigTroublePersistenceIface(BigTroublePersistenceIface bigTroublePersistenceIface) {
		this.bigTroublePersistenceIface = bigTroublePersistenceIface;
	}
}
