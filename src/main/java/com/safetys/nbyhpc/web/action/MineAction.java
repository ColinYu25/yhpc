package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.AreaFacadeIface;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.ZjIdea;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.model.ZjStatistic;
import com.safetys.nbyhpc.facade.iface.Axis2FacadeIface;
import com.safetys.nbyhpc.facade.iface.BigTroubleFacadeIface;
import com.safetys.nbyhpc.facade.iface.IdeaFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineDetailFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.SafetyTroubleUtil;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#)MineAction.java Date 2008-9-16 Copyright (c) 2008 Safetys.cn.
 * @author lihu
 * 
 */

public class MineAction extends DaAppAction {

	private static final long serialVersionUID = -5725310183862333322L;
	
	public final static String PRINT = "print";

	private UserFacadeIface userFacadeIface;// 用户接口（用于调用用户模块相关方法）

	private MineFacadeIface mineFacadeIface;// 矿山接口

	private MineDetailFacadeIface mineDetailFacadeIface;// 矿山详细接口

	private BigTroubleFacadeIface bigTroubleFacadeIface;// 重大隐患接口
	
	private Axis2FacadeIface axis2FacadeIface;

	private IdeaFacadeIface ideaFacadeIface;// 台帐接口

	private AreaFacadeIface areaFacadeIface;

	private ZjMineReport mineReport;// 矿山记录

	private List<ZjMineReport> mineReports;// 矿山记录集合

	private List<ZjMineReportDetail> mineReportDetails;// 矿山详细记录集合

	private List<ZjStatistic> statistics;// 统计对应区域和填报月份的重大隐患数据

	private List<FkArea> areas;// 区域集合（浙江省所有地级市）

	private Pagination pagination;// 分页参数

	private String fillUnit;// 显示填报单位

	private String[] years = SafetyTrouble.getYaers();// 显示年月的年份

	private List<SafetyTroubleUtil> months = SafetyTrouble.getMonths();// 显示年月的月份

	private String mineOrOther;// 判断列表及部分链接如何表示值，即判断点击的列表是矿山列表或者其他列表

	private String reportMonth;// 设置初始化录入页面显示填报年月的初始值

	private String fillDate;

	private boolean pass = false;// 判断省级汇总是否通过

	/**
	 * 矿山
	 */

	/**
	 * 加载对应用户或角色的矿山列表
	 * 
	 * @return
	 */
	public String loadMines() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			if (mineReport == null) {// 判断相关对象是否为空，如果为空则重新定义，防止后面程序空指针
				mineReport = new ZjMineReport();
				reportMonth = SafetyTrouble.getNowReportMonth();
			}
			mineReport.setType(SafetyTrouble.MINE_TYPE);// 设置搜索条件，本方法用于显示矿山列表
			mineReport.setUserId(fkUser);
			mineReports = mineFacadeIface.loadMineReports(mineReport, pagination);
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {// 如果登陆用户是市级用户
				Integer type = mineReport.getType();
				List<ZjMineReport> reports = mineFacadeIface.loadMineReportByCityAndMonth(fkUser, type);

				for (ZjMineReport mine : mineReports) {
					for (int i = 0; i < reports.size(); i++) {
						if (mine.getType().equals(reports.get(i).getType())
								&& mine.getReportMonth().equals(reports.get(i).getReportMonth())
								&& reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
							mine.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
						}
					}
				}
			}
			for (ZjMineReport mine : mineReports)
				mine.setNowUser(fkUser);
			mineOrOther = SafetyTrouble.MINE;// 设置列表中显示的链接都应是与矿山想关联的，设置SafetyTrouble.OTHER时关联到其他模块
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 创建矿山页面初始化
	 * 
	 * @return
	 */
	public String createMineInit() {
		try {
			
			Statistic statistic = new Statistic();
			if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
				statistic.setYearMonth(formater.format(cal.getTime()));
			}
			statistic.setType(1);
			statistic.setAreaCode(getUserDetail().getSecondArea());
			axis2FacadeIface.loadDangerCreateBigTrouble(statistic);//同步重大隐患表数据
			
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			fillUnit = fkUser.getFkUserInfo().getUserCompany();// 设置页面显示填报单位
			if (mineReport == null) {
				mineReport = new ZjMineReport();
			}
			mineReport.setUserId(fkUser);
			mineReport.setType(SafetyTrouble.MINE_TYPE);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
			reportMonth = mineReport.getReportMonth();
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);

			loadNewDate();
			mineReport = null;
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 创建相应的矿山报表
	 * 
	 * @return
	 */
	public String createMine() {
		try {
			FkUser fkUser = new FkUser();
			fkUser.setId(getUserId());
			mineReport.setUserId(fkUser);// 设置当前用户为操作用户
			long id = mineFacadeIface.createMineReport(mineReport);
			mineDetailFacadeIface.createMineReportDetails(mineReportDetails, id);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		if (mineReport.getType() == 5 || mineReport.getType() == 6) {
			setUrl("loadCountryReports2010.xhtml?mineReport.type=" + mineReport.getType());
		} else if (mineReport.isReport()) {
			String url =
					"doneCityReportInit.xhtml?mineReport.type=" + mineReport.getType() + "&mineReport.reportMonth="
							+ mineReport.getReportMonth();
			setUrl(url);
		} else if (mineReport.isProvince()) {
			setUrl("loadProvinceReports.xhtml?mineReport.type=" + mineReport.getType());
		} else if (mineReport.isCountry()) {
			setUrl("loadCountryReports.xhtml?mineReport.type=" + mineReport.getType());
		} else {
			setUrl((mineReport.getType() == SafetyTrouble.MINE_TYPE) ? "loadMines.xhtml" : "../other/loadOthers.xhtml");
		}
		setMessageKey("create.success");
		return SUCCESS;
	}

	/**
	 * 根据对应ID获取其他的详细信息
	 * 
	 * @return
	 */
	public String loadMine() {
		if (mineReport == null) {
			setUrl("loadMines.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		try {
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mineReport == null) {// 判断对象是否为空，如果为空则不必获取对应序号的矿山，直接提示用户无数据，并返回列表帮助其选择记录
				setUrl("loadMines.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			} else if (mineReport.getState() == SafetyTrouble.NO_REPORT) {// 如果用户违法操作查看未上报并且不是本用户录入的数据，则返回提示页面
				long userId = mineReport.getUserId().getId();
				if (userId != getUserId()) {
					setMessageKey("noData");
					setUrl("loadOthers.xhtml");
					return MESSAGE;
				}
			}
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsByMine(mineReport.getId(), null);
			fillUnit = mineReport.getUserId().getFkUserInfo().getUserCompany();
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改其他详细信息
	 * 
	 * @return
	 */
	public String updateMine() {
		try {
			mineFacadeIface.updateMineReport(mineReport);
			mineDetailFacadeIface.deleteMineReportDetailsByMine(mineReport.getId());
			mineDetailFacadeIface.createMineReportDetails(mineReportDetails, mineReport.getId());
			if (mineReport.getType() == 5 || mineReport.getType() == 6) {
				setUrl("loadCountryReports2010.xhtml?mineReport.type=" + mineReport.getType());
			} else if (mineReport.isProvince()) {
				setUrl("loadProvinceReports.xhtml?mineReport.type=" + mineReport.getType());
			} else if (mineReport.isCountry()) {
				setUrl("loadCountryReports.xhtml?mineReport.type=" + mineReport.getType());
			} else if (!mineReport.isReport()) {
				setUrl("loadProvinceReports.xhtml?mineReport.type=1");
			} else {
				if (mineReport.getYear() != null && mineReport.getMonth() != null)
					mineReport.setReportMonth(mineReport.getYear() + "-" + mineReport.getMonth());
				setUrl(getContextPath() + "/redirectServlet");
				setMessageKey("report.save.success");
			}
			setMessageKey("update.success");
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 进入其他对应的打印页面
	 * 
	 * @return
	 */
	public String printMine() {
		if (mineReport == null) {
			setUrl("loadMines.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		try {
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mineReport == null) {// 判断对象是否为空，如果为空则不必获取对应序号的矿山，直接提示用户无数据，并返回列表帮助其选择记录
				setUrl("loadMines.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			} else if (mineReport.getState() == SafetyTrouble.NO_REPORT) {// 如果用户违法操作查看未上报并且不是本用户录入的数据，则返回提示页面
				long userId = mineReport.getUserId().getId();
				if (userId != getUserId()) {
					setMessageKey("noData");
					setUrl("loadOthers.xhtml");
					return MESSAGE;
				}
			}
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsByMine(mineReport.getId(), null);
			fillUnit = mineReport.getUserId().getFkUserInfo().getUserCompany();
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 删除对应的矿山数据
	 * 
	 * @return
	 */
	public String deleteMine() {
		try {
			if (mineReport == null) {
				setUrl("loadMines.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			boolean isReport = mineReport.isReport();
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mineReport.getState() > SafetyTrouble.NO_REPORT) {
				setUrl("loadMines.xhtml");
				setMessageKey("report.delete.already");
				return MESSAGE;
			}
			mineDetailFacadeIface.deleteMineReportDetailsByMine(mineReport.getId());
			mineFacadeIface.deleteMineReport(mineReport);
			if (mineReport.getType() == 5 || mineReport.getType() == 6) {// 统计2010
				setUrl("loadCountryReports2010.xhtml?mineReport.type=" + mineReport.getType());
			}
			if (!isReport) {
				setUrl("loadMines.xhtml");
			} else {
				if (mineReport.getYear() != null && mineReport.getMonth() != null)
					mineReport.setReportMonth(mineReport.getYear() + "-" + mineReport.getMonth());
				setUrl(getContextPath() + "/redirectServlet");
				setMessageKey("report.save.success");
			}
			setMessageKey("delete.success");
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 其他
	 */

	/**
	 * 加载对应用户或角色的其他列表
	 * 
	 * @return
	 */
	public String loadOthers() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			if (mineReport == null) {
				mineReport = new ZjMineReport();
				reportMonth = SafetyTrouble.getNowReportMonth();
			}
			mineReport.setType(SafetyTrouble.OTHER_TYPE);
			mineReport.setUserId(fkUser);
			mineReports = mineFacadeIface.loadMineReports(mineReport, pagination);
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {// 如果登陆用户是市级用户
				Integer type = mineReport.getType();
				List<ZjMineReport> reports = mineFacadeIface.loadMineReportByCityAndMonth(fkUser, type);

				for (ZjMineReport mine : mineReports) {
					for (int i = 0; i < reports.size(); i++) {
						if (mine.getType().equals(reports.get(i).getType())
								&& mine.getReportMonth().equals(reports.get(i).getReportMonth())
								&& reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
							mine.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
						}
					}
				}
			}
			for (ZjMineReport mine : mineReports)
				mine.setNowUser(fkUser);
			mineOrOther = SafetyTrouble.OTHER;
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 创建其他页面初始化
	 * 
	 * @return
	 */
	public String createOtherInit() {
		try {

			Statistic statistic = new Statistic();
			if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
				statistic.setYearMonth(formater.format(cal.getTime()));
			}
			statistic.setType(2);
			statistic.setAreaCode(getUserDetail().getSecondArea());
			axis2FacadeIface.loadDangerCreateBigTrouble(statistic);//同步重大隐患表数据
			
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			fillUnit = fkUser.getFkUserInfo().getUserCompany();// 设置页面显示填报单位
			if (mineReport == null) {
				mineReport = new ZjMineReport();
			}
			mineReport.setUserId(fkUser);
			mineReport.setType(SafetyTrouble.OTHER_TYPE);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
			reportMonth = mineReport.getReportMonth();
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);

			loadNewDate();
			mineReport = null;
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * BigTrouble数据初始化
	 * 
	 * @return
	 */
	public String createBigTroubleInit() {
		try {
			axis2FacadeIface.createBigTroubleInit();//数据初始化
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}


	/**
	 * 创建相应的其他报表
	 * 
	 * @return
	 */
	public String createOther() {
		try {
			FkUser fkUser = new FkUser();
			fkUser.setId(getUserId());
			mineReport.setUserId(fkUser);
			long id = mineFacadeIface.createMineReport(mineReport);
			mineDetailFacadeIface.createMineReportDetails(mineReportDetails, id);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl("loadOthers.xhtml");
		setMessageKey("create.success");
		return SUCCESS;
	}

	/**
	 * 根据对应ID获取其他的详细信息
	 * 
	 * @return
	 */
	public String loadOther() {
		if (mineReport == null) {
			setUrl("loadOthers.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		try {
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mineReport == null) {
				setUrl("loadOthers.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			} else if (mineReport.getState() == SafetyTrouble.NO_REPORT) {
				long userId = mineReport.getUserId().getId();
				if (userId != getUserId()) {
					setMessageKey("noData");
					setUrl("loadOthers.xhtml");
					return MESSAGE;
				}
			}
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsByMine(mineReport.getId(), null);
			fillUnit = mineReport.getUserId().getFkUserInfo().getUserCompany();
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改其他详细信息
	 * 
	 * @return
	 */
	public String updateOther() {
		try {
			mineFacadeIface.updateMineReport(mineReport);
			mineDetailFacadeIface.deleteMineReportDetailsByMine(mineReport.getId());
			mineDetailFacadeIface.createMineReportDetails(mineReportDetails, mineReport.getId());
			setUrl("loadOthers.xhtml");
			setMessageKey("update.success");
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 进入其他对应的打印页面
	 * 
	 * @return
	 */
	public String printOther() {
		if (mineReport == null) {
			setUrl("loadOthers.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		try {
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mineReport == null) {
				setUrl("loadOthers.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			} else if (mineReport.getState() == SafetyTrouble.NO_REPORT) {
				long userId = mineReport.getUserId().getId();
				if (userId != getUserId()) {
					setMessageKey("noData");
					setUrl("loadOthers.xhtml");
					return MESSAGE;
				}
			}
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsByMine(mineReport.getId(), null);
			fillUnit = mineReport.getUserId().getFkUserInfo().getUserCompany();
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 删除对应的其他数据
	 * 
	 * @return
	 */
	public String deleteOther() {
		try {
			if (mineReport == null) {
				setUrl("loadOthers.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mineReport.getState() > SafetyTrouble.NO_REPORT) {
				setUrl("loadOthers.xhtml");
				setMessageKey("report.delete.already");
				return MESSAGE;
			}
			mineDetailFacadeIface.deleteMineReportDetailsByMine(mineReport.getId());
			mineFacadeIface.deleteMineReport(mineReport);
			setUrl("loadOthers.xhtml");
			setMessageKey("delete.success");
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 上报数据
	 * 
	 * @return
	 */
	public String doneReport() {
		try {
			if (mineReport == null) {
				setUrl("../mine/loadMines.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			ZjMineReport mine = mineFacadeIface.loadMineReport(mineReport.getId());
			String url =
					(mine.getType() == SafetyTrouble.MINE_TYPE) ? "../mine/loadMines.xhtml"
							: "../other/loadOthers.xhtml";
			if (mine == null) {
				setUrl("../mine/loadMines.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			} else if (mine.getState() > SafetyTrouble.COUNTY_REPORT) {
				setUrl(url);
				setMessageKey("report.save.already");
				return MESSAGE;
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			Map map = SafetyTrouble.getState(fkUser, mine.getState());
			if (!(Boolean) map.get("allow")) {
				setUrl(url);
				if (mine.getState() == SafetyTrouble.NO_REPORT) {
					setMessageKey("report.save.fail.noReport");
				} else if (mine.getState() == SafetyTrouble.CITY_REPORT) {
					setMessageKey("report.save.fail.cityReport");
				} else if (mine.getState() == SafetyTrouble.COUNTY_REPORT) {
					setMessageKey("report.save.fail.countyReport");
				}
				return MESSAGE;
			}
			/**
			 * 增加验证该县区、当前年份对应报表的重大隐患中是否存在：未选择挂牌督办级别的，如果存在，则弹出提示，不允许上报
			 * 并且，跳转到重大隐患列表（仅显示未选择挂牌督办级别的），在该列表中可以对重大隐患进行修改挂牌督办级别的操作。
			 * 
			 * @author lih
			 * @date 2012-06-29
			 * 
			 */
			if (!bigTroubleFacadeIface.checkBigTroubleHaveNoChooseGuapaiLevel(getUserDetail(), mine)) {
				setMessageKey("county.is.have.nochoose.guapaiLevel");
				url =
						"../bigTrouble/loadNoChooseGuapaiLevel.xhtml?reportMonth=" + mine.getReportMonth()
								+ "&tableType=" + mine.getType();
				setUrl(url);
				return MESSAGE;
			}

			mine.setState(Integer.parseInt(map.get("state").toString()));
			mine.setModifyTime(new Date());
			mineFacadeIface.updateMineReport(mine);
			List<ZjMineReport> reports = mineFacadeIface.loadMineReportByCityAndMonth(fkUser, mine.getType());
			for (ZjMineReport report : reports) {// 上报报表时，检查之前是否有未进行上报的，如果存在，则批量上报
				if (mine.getReportMonth().split("-")[0].equals(report.getReportMonth().split("-")[0]))
					if (Integer.parseInt(mine.getReportMonth().split("-")[0]) >= Integer.parseInt(report
							.getReportMonth().split("-")[1])
							&& Integer.parseInt(mine.getReportMonth().split("-")[1]) > Integer.parseInt(report
									.getReportMonth().split("-")[1])) {
						if (report.getState().equals(SafetyTrouble.NO_REPORT)) {// 如果没有上报，则记录当前时间为上报时间
							report.setModifyTime(new Date());
							report
									.setState(RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles()) ? SafetyTrouble.CITY_REPORT
											: SafetyTrouble.COUNTY_REPORT);
							mineFacadeIface.updateMineReport(report);
							ZjIdea idea =
									new ZjIdea(userFacadeIface.loadUser(getUserId()), "批量上报", (long) report.getType(),
											report.getId());
							ideaFacadeIface.createIdea(idea);
						}
					}
			}
			if (!mineReport.isReport()) {
				setUrl(url);
			} else {
				setUrl(getContextPath() + "/redirectServlet");
				setMessageKey("report.save.success");
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 市级上报初始化
	 * 
	 * @return
	 */
	public String doneCityReportInit() {
		try {
			if (mineReport == null) {
				mineReport = new ZjMineReport();
				reportMonth = SafetyTrouble.getNowReportMonth();
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			mineReport.setUserId(fkUser);
			mineReport.setQueryReportMonth(true);
			if (mineReport.getType() == null) {
				mineReport.setType(SafetyTrouble.MINE_TYPE);
			}
			reportMonth = mineReport.getYear() + "-" + mineReport.getMonth();
			mineReport.setReportMonth(null);
			mineReports = mineFacadeIface.loadMineReports(mineReport, pagination);
			for (ZjMineReport mine : mineReports)
				mine.setNowUser(fkUser);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载市级显示数据
	 * 
	 * @return
	 */
	public String loadCityReportInit() {
		String returns = SUCCESS;
		boolean isPrint = false;
		try {
			if (mineReport == null) {
				return SUCCESS;
			}
			isPrint = mineReport.isPrint();
			String year = mineReport.getYear();
			String month = mineReport.getMonth();
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			mineReport.setUserId(fkUser);
			if (mineReport.getId() != -1L) {// 第二次加载，之后修改
				mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			}
			if (mineReport.getId() == -1L) {// 第一次加载，之后创建
				if (year != null && (month == null || "0".equals(month))) {
					reportMonth = SafetyTrouble.getNowReportMonth();
				} else if (!"0".equals(month) && year != null) {
					reportMonth = year + "-" + month;
				}
				mineReport.setReportMonth(reportMonth);
			}
			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
//			if (!mineFacadeIface.is_all_reported("county", getUserDetail().getSecondArea(), mineReport)) {
//				setMessageKey("county.is.not.all.report");
//				return MESSAGE;
//			}
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsByUser(mineReport);
			if (mineReport.getReportMonth() != null && !"".equals(mineReport.getReportMonth())) {
				mineReport.setYear(mineReport.getReportMonth().split("-")[0]);
				mineReport.setMonth(mineReport.getReportMonth().split("-")[1]);
			}
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
			fillUnit = mineReport.getUserId().getFkUserInfo().getUserCompany();
			if (mineReport.getType() != null) {
				returns = (mineReport.getType() == SafetyTrouble.MINE_TYPE) ? SafetyTrouble.MINE : SafetyTrouble.OTHER;
			}
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);
			if (mineReport.getId() == -1L) {// 第一次加载，之后创建
				mineReport = null;
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		if (isPrint) {
			if (SafetyTrouble.MINE.equals(returns)) {
				return PRINT + "1";
			} else if (SafetyTrouble.OTHER.equals(returns)) {
				return PRINT + "2";
			}
		}
		return returns;
	}
	
	/**
	 * 省级上报
	 * 
	 * @return
	 */
	public String doneProvinceReport() {
		if (mineReport == null) {
			return SUCCESS;
		}
		try {
			ZjMineReport mine = mineFacadeIface.loadMineReport(mineReport.getId());
			String url = "../mine/loadProvinceReports.xhtml?mineReport.type=1";
			setUrl(url);
			if (mine == null) {
				setMessageKey("noData");
				return MESSAGE;
			} else if (mine.getState() > SafetyTrouble.CITY_REPORT) {
				setMessageKey("report.save.already");
				return MESSAGE;
			}
			
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			Map map = SafetyTrouble.getState(fkUser, mine.getState());
			if (!(Boolean) map.get("allow")) {
				setUrl(url);
				if (mine.getState() == SafetyTrouble.NO_REPORT) {
					setMessageKey("report.save.fail.noReport");
				} else if (mine.getState() == SafetyTrouble.CITY_REPORT) {
					setMessageKey("report.save.fail.cityReport");
				} else if (mine.getState() == SafetyTrouble.COUNTY_REPORT) {
					setMessageKey("report.save.fail.countyReport");
				} else if (mine.getState() == SafetyTrouble.PROVINCE_REPORT) {
					setMessageKey("report.save.fail.provinceReport");
				}
				return MESSAGE;
			}
			
			Integer type = mine!=null?mine.getType():null;
			if(null != type){
				if(sendDataOfMineByOMElement(type)){
					mine.setState(Integer.parseInt(map.get("state").toString()));
					mine.setModifyTime(new Date());
					mineFacadeIface.updateMineReport(mine);
					setMessageKey("report.save.success");
				}else{
					setMessageKey("report.save.fail");
					return MESSAGE;
				}
			}else{
				setMessageKey("report.save.fail");
				return MESSAGE;
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}
	
	/**
	 * AXIS向省隐患排查发送数据
	 * @param type
	 * @return
	 */
	public Boolean sendDataOfMineByOMElement(Integer type) {
		Statistic statistic = new Statistic();
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(type);
		try {
			if(axis2FacadeIface.sendDataOfMineOrOtherByOMElement1(statistic,getUserId())){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 市级上报
	 * 
	 * @return
	 */
	public String doneCityReport() {
		if (mineReport == null) {
			return SUCCESS;
		}
		try {
			mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			mineReport.setYear(mineReport.getReportMonth().split("-")[0]);
			mineReport.setMonth(mineReport.getReportMonth().split("-")[1]);
			mineReports = mineFacadeIface.loadMineReports(mineReport, null);
			if (true) {
				doneReport();
			} else {
				setUrl("doneCityReportInit.xhtml");
				setMessageKey("report.save.fail.cityReport.noAllReport");
				return MESSAGE;
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	/**
	 * 查看省级统计
	 * 
	 * @return
	 */
	public String loadProvinceReport() {
		String returns = SUCCESS;
		boolean isPrint = false;
		try {
			if (mineReport == null
					|| (mineReport.getType() != SafetyTrouble.MINE_TYPE && mineReport.getType() != SafetyTrouble.OTHER_TYPE)
					|| (mineReport.getReportMonth() == null || "".equals(mineReport.getReportMonth()))) {
				mineReport = new ZjMineReport();
				return SUCCESS;
			}
			isPrint = mineReport.isPrint();
			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
			if (mineReport.getId() != -1L) {// 第二次加载，之后修改
				mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			} else if (!mineFacadeIface.is_all_reported("city", getUserDetail().getFirstArea(), mineReport) && !pass) {
				 setMessageKey("county.is.not.all.report");
				 return MESSAGE;
			 }
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			fillUnit = fkUser.getFkUserInfo().getUserCompany();
			mineReport.setQueryReportMonth(true);
			mineReport.setUserId(fkUser);
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsForProvince(mineReport);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
			if (mineReport.getType() != null) {
				returns = (mineReport.getType() == SafetyTrouble.MINE_TYPE) ? SafetyTrouble.MINE : SafetyTrouble.OTHER;
			}
			months =
					SafetyTrouble.getMonthsByData(mineFacadeIface.loadMineReportsByUser(mineReport), mineReport
							.getReportMonth().split("-")[0]);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		if (isPrint) {
			if (SafetyTrouble.MINE.equals(returns)) {
				return PRINT + "1";
			} else if (SafetyTrouble.OTHER.equals(returns)) {
				return PRINT + "2";
			}
		}
		return returns;
	}

	public String loadProvinceReports() {
		if (mineReport == null) {
			mineReport = new ZjMineReport();
		}
		mineReport.setProvince(true);
		doneCityReportInit();
		return SUCCESS;
	}

	public String loadCountryReports() {
		if (mineReport == null) {
			mineReport = new ZjMineReport();
		}
		mineReport.setProvince(true);
		doneCityReportInit();
		return SUCCESS;
	}

	public String loadCountryReports2010() {
		if (mineReport == null) {// type值为5时，代表交通运输等重点行业2010；type值为6时，代表矿山等危化行业和领域2010
			mineReport = new ZjMineReport();
			mineReport.setType(5);
		}
		mineReport.setProvince(true);
		doneCityReportInit();
		return SUCCESS;
		// mineReport.setType(1);
		// return loadProvinceReports();
	}

	public String loadCountry2010() {
		String forward = loadCountry();
		return forward;
	}

	/**
	 * 国家统计查看
	 * 
	 * @return
	 */
	public String loadCountry() {
		String returns = SUCCESS;
		boolean isPrint = false;
		try {
			if (mineReport == null
					|| (mineReport.getType() != 3 && mineReport.getType() != 5 && mineReport.getType() != 6)
					|| (mineReport.getReportMonth() == null || "".equals(mineReport.getReportMonth()))) {
				mineReport = new ZjMineReport();
				return SUCCESS;
			}
			isPrint = mineReport.isPrint();
			if (mineReport.getId() != -1L) {// 第二次加载，之后修改
				mineReport = mineFacadeIface.loadMineReport(mineReport.getId());
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			fillUnit = fkUser.getFkUserInfo().getUserCompany();
			mineReport.setQueryReportMonth(true);
			mineReport.setUserId(fkUser);
			if (mineReport.getType() != 5 && mineReport.getType() != 6)// 国家统计2010
				mineReport.setType(2);
			mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsForProvince(mineReport);
			statistics = bigTroubleFacadeIface.loadStatisticForMine(mineReport);
			if (mineReport.getType() != null) {
				returns = (mineReport.getType() == SafetyTrouble.MINE_TYPE) ? SafetyTrouble.MINE : SafetyTrouble.OTHER;
				if (mineReport.getType() == 5) {
					returns = SafetyTrouble.OTHER;
				} else if (mineReport.getType() == 6) {
					returns = SafetyTrouble.MINE;
				}
			}
			if (isPrint) {
				if (SafetyTrouble.MINE.equals(returns)) {
					return PRINT + "1";
				} else if (SafetyTrouble.OTHER.equals(returns)) {
					if (mineReport.getType() == 5) {
						return PRINT + "3";
					}
					return PRINT + "2";
				}
			}
			// months = SafetyTrouble.getMonthsByData(mineFacadeIface
			// .loadMineReportsByUser(mineReport));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return returns;
	}

	/**
	 * 退回方法
	 * 
	 * @return
	 */
	public String rollback() {
		if (mineReport == null) {
			setUrl("../mine/loadMines.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			ZjMineReport mine = mineFacadeIface.loadMineReport(mineReport.getId());
			if (mine == null) {
				setUrl("../mine/loadMines.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
				List<ZjMineReport> reports = mineFacadeIface.loadMineReportByCityAndMonth(fkUser, mine.getType());
				for (ZjMineReport report : reports)
					if (report.getReportMonth().equals(mine.getReportMonth()))
						if (report.getState() > SafetyTrouble.NO_REPORT) {
							setUrl("");
							setMessageKey("rollback.fail.city.cityIsReport");
							return MESSAGE;
						}
			}
			List<ZjMineReport> countys = mineFacadeIface.loadMineReportByCityAndMonth(mine.getUserId(), mine.getType());
			for (ZjMineReport report : countys) {
				if (report.getState() == ((RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) ? SafetyTrouble.COUNTY_REPORT
						: SafetyTrouble.CITY_REPORT)) {
					if (Integer.parseInt(mine.getReportMonth().split("-")[0]) < Integer.parseInt(report
							.getReportMonth().split("-")[0])) {
						setUrl("");
						setMessageKey(RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles()) ? "rollback.fail.city.countyAfterReport"
								: "rollback.fail.province.cityAfterReport");
						return MESSAGE;
					} else if (Integer.parseInt(mine.getReportMonth().split("-")[0]) == Integer.parseInt(report
							.getReportMonth().split("-")[0])
							&& Integer.parseInt(mine.getReportMonth().split("-")[1]) < Integer.parseInt(report
									.getReportMonth().split("-")[1])) {
						setUrl("");
						setMessageKey(RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles()) ? "rollback.fail.city.countyAfterReport"
								: "rollback.fail.province.cityAfterReport");
						return MESSAGE;
					}
				}
			}
			mine.setModifyTime(new Date());
			String content = "";
			if (mine.getState() == SafetyTrouble.CITY_REPORT) {
				mine.setState(0);
				content = "省级退回";
			} else {
				mine.setState(mine.getState() - 1);
				content = "市级退回";
			}
			mineFacadeIface.updateMineReport(mine);
			ZjIdea idea =
					new ZjIdea(userFacadeIface.loadUser(getUserId()), content, (long) mine.getType(), mine.getId());
			ideaFacadeIface.createIdea(idea);
			setUrl(getContextPath() + "/redirectServlet");
			setMessageKey("rollback.save.success");
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCountyReport() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			if (mineReport == null || mineReport.getType() == null) {
				setUrl("loadCountyReport.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			mineReport.setUserId(fkUser);
			Pagination temp = new Pagination();
			temp.setPageSize(100);
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport.getUserId().getFkRoles())) {
			 areas = mineFacadeIface.loadAreas(0L, temp);
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport.getUserId().getFkRoles())) {
				Long area_id = mineReport.getUserId().getFkUserInfo().getSecondArea();
				if (area_id != null && !"".equals(area_id)) {
					areas = mineFacadeIface.loadCityAreas(area_id);
				}
			}
			mineReports = mineFacadeIface.loadCountyReports(mineReport, pagination);
			for (ZjMineReport mine : mineReports)
				mine.setNowUser(fkUser);
			mineOrOther = (mineReport.getType() == SafetyTrouble.MINE_TYPE) ? SafetyTrouble.MINE : SafetyTrouble.OTHER;// 设置列表中显示的链接都应是与矿山想关联的，设置SafetyTrouble.OTHER时关联到其他模块
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String printCountry() {
		return loadCountry();
	}

	/**
	 * dijj修改于2009年7月23日,添加时显示最近一次添加的数据
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void loadNewDate() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		try{
			mineReports = mineFacadeIface.loadMineReports(mineReport, pagination);
			if (mineReports != null && mineReports.size() > 0) {
				Long mineId = mineReports.get(0).getId();
				mineReportDetails = mineDetailFacadeIface.loadMineReportDetailsByMine(mineId, null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 本Action类的 getter和 setter方法
	 * 
	 * @return
	 */
	public String getFillUnit() {
		return fillUnit;
	}

	public void setFillUnit(String fillUnit) {
		this.fillUnit = fillUnit;
	}

	public ZjMineReport getMineReport() {
		return mineReport;
	}

	public void setMineReport(ZjMineReport mineReport) {
		this.mineReport = mineReport;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public List<ZjMineReportDetail> getMineReportDetails() {
		return mineReportDetails;
	}

	public void setMineReportDetails(List<ZjMineReportDetail> mineReportDetails) {
		this.mineReportDetails = mineReportDetails;
	}

	public void setMineFacadeIface(MineFacadeIface mineFacadeIface) {
		this.mineFacadeIface = mineFacadeIface;
	}

	public void setMineDetailFacadeIface(MineDetailFacadeIface mineDetailFacadeIface) {
		this.mineDetailFacadeIface = mineDetailFacadeIface;
	}

	public List<SafetyTroubleUtil> getMonths() {
		return months;
	}

	public void setMonths(List<SafetyTroubleUtil> months) {
		this.months = months;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}

	public List<ZjMineReport> getMineReports() {
		return mineReports;
	}

	public void setMineReports(List<ZjMineReport> mineReports) {
		this.mineReports = mineReports;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getMineOrOther() {
		return mineOrOther;
	}

	public void setMineOrOther(String mineOrOther) {
		this.mineOrOther = mineOrOther;
	}

	public List<ZjStatistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<ZjStatistic> statistics) {
		this.statistics = statistics;
	}

	public void setBigTroubleFacadeIface(BigTroubleFacadeIface bigTroubleFacadeIface) {
		this.bigTroubleFacadeIface = bigTroubleFacadeIface;
	}

	public String getReportMonth() {
		return reportMonth;
	}

	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

	public void setIdeaFacadeIface(IdeaFacadeIface ideaFacadeIface) {
		this.ideaFacadeIface = ideaFacadeIface;
	}

	public String getFillDate() {
		return fillDate;
	}

	public void setFillDate(String fillDate) {
		if (mineReport == null) {
			mineReport = new ZjMineReport();
		}
		if (!"".equals(fillDate)) {
			mineReport.setFillDate(fillDate);
		}
		this.fillDate = fillDate;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

	public void setAreaFacadeIface(AreaFacadeIface areaFacadeIface) {
		this.areaFacadeIface = areaFacadeIface;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public void setAxis2FacadeIface(Axis2FacadeIface axis2FacadeIface) {
		this.axis2FacadeIface = axis2FacadeIface;
	}
	
}