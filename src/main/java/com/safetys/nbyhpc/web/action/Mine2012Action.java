package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.model.ZjStatistic;
import com.safetys.nbyhpc.facade.iface.Mine2012FacadeIface;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.State;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * 2012年新表格样式，此Action仅含有查看页面。 添加、修改、删除、列表、上报、退回等其他功能依然存放在原有Action（MineAction）
 * 
 * author lih
 * 
 * @since 2012-6-18
 * @version 1.0.0
 */
public class Mine2012Action extends DaAppAction {

	private static final long serialVersionUID = -853352139576447566L;
	
	public final static String PRINT = "print";

	private UserFacadeIface userFacadeIface;

	private Mine2012FacadeIface mine2012FacadeIface;// 矿山接口

	private ZjMineReport mineReport;// 矿山记录

	private List<ZjMineReport> mineReports;// 矿山记录集合

	private List<ZjMineReportDetail> mineReportDetails;// 矿山详细记录集合

	private List<ZjStatistic> statistics;// 统计对应区域和填报月份的重大隐患数据

	private boolean pass = false;// 判断省级汇总是否通过

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
		boolean isPrint = mineReport.isPrint();
		try {
			mineReport = mine2012FacadeIface.loadMineReport(mineReport.getId());
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
			mineReportDetails = mine2012FacadeIface.loadMineReportDetailsByMine(mineReport, null);
			if (mineReportDetails !=null && mineReportDetails.size()==0) {
				if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport.getUserId().getFkRoles())) {
					setUrl("../mine/loadCityReportInit2012.xhtml?mineReport.id="+mineReport.getId()+"&mineReport.type=1&mineReport.reportMonth="+mineReport.getReportMonth()+"&mineReport.print="+isPrint);
				} else if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport.getUserId().getFkRoles())) {
					setUrl("../mine/loadProvinceReport2012.xhtml?mineReport.id="+mineReport.getId()+"&mineReport.type=1&mineReport.reportMonth="+mineReport.getReportMonth()+"&mineReport.print="+isPrint);
				}
				return MESSAGE;
			}
			statistics = mine2012FacadeIface.loadStatisticForMine(mineReport);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return isPrint ? PRINT : SUCCESS;
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
		boolean isPrint = mineReport.isPrint();
		try {
			mineReport = mine2012FacadeIface.loadMineReport(mineReport.getId());
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
			mineReportDetails = mine2012FacadeIface.loadMineReportDetailsByMine(mineReport, null);
			if (mineReportDetails !=null && mineReportDetails.size()==0) {
				if (RoleType1.isRoleByDepic(RoleType1.CITY, mineReport.getUserId().getFkRoles())) {
					setUrl("../mine/loadCityReportInit2012.xhtml?mineReport.id="+mineReport.getId()+"&mineReport.type=1&mineReport.reportMonth="+mineReport.getReportMonth()+"&mineReport.print="+isPrint);
				} else if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, mineReport.getUserId().getFkRoles())) {
					setUrl("../mine/loadProvinceReport2012.xhtml?mineReport.id="+mineReport.getId()+"&mineReport.type=1&mineReport.reportMonth="+mineReport.getReportMonth()+"&mineReport.print="+isPrint);
				}
				return MESSAGE;
			}
			statistics = mine2012FacadeIface.loadStatisticForMine(mineReport);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return isPrint ? PRINT : SUCCESS;
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
			if (mineReport.getId() != -1L) {// 第二次加载，之后修改
				mineReport = mine2012FacadeIface.loadMineReport(mineReport.getId());
			}
			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
//			if (!mine2012FacadeIface.is_all_reported("county", getUserDetail().getSecondArea(), mineReport)) {
//				setMessageKey("city.is.not.all.report");
//				return MESSAGE;
//			}
			mineReportDetails = mine2012FacadeIface.loadMineReportDetailsByUser(mineReport);
			if (mineReport.getReportMonth() != null && !"".equals(mineReport.getReportMonth())) {
				mineReport.setYear(mineReport.getReportMonth().split("-")[0]);
				mineReport.setMonth(mineReport.getReportMonth().split("-")[1]);
			}
			statistics = mine2012FacadeIface.loadStatisticForMine(mineReport);
			if (mineReport.getType() != null) {
				returns = (mineReport.getType() == SafetyTrouble.MINE_TYPE) ? SafetyTrouble.MINE : SafetyTrouble.OTHER;
			}
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
				mineReport = mine2012FacadeIface.loadMineReport(mineReport.getId());
			} else if (!mine2012FacadeIface.is_all_reported("city", getUserDetail().getFirstArea(), mineReport)
					&& !pass) {
				setMessageKey("city.is.not.all.report");
				return MESSAGE;
			}
			mineReport.setQueryReportMonth(true);
			mineReportDetails = mine2012FacadeIface.loadMineReportDetailsForProvince(mineReport);
			statistics = mine2012FacadeIface.loadStatisticForMine(mineReport);
			if (mineReport.getType() != null) {
				returns = (mineReport.getType() == SafetyTrouble.MINE_TYPE) ? SafetyTrouble.MINE : SafetyTrouble.OTHER;
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
				mineReport = mine2012FacadeIface.loadMineReport(mineReport.getId());
			}
			mineReport.setQueryReportMonth(true);
			if (mineReport.getType() != 5 && mineReport.getType() != 6)
				mineReport.setType(2);
			mineReportDetails = mine2012FacadeIface.loadMineReportDetailsForProvince(mineReport);
			statistics = mine2012FacadeIface.loadStatisticForMine(mineReport);
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
	 * 获取矿山行业的行业集合，用于页面循环显示
	 * 
	 * @author lih
	 * @since 2012-6-18
	 * @return
	 */
	public List<State> getMineType() {
		return mine2012FacadeIface.getMineTypeMap();
	}

	/**
	 * 获取其他行业的行业集合，用于页面循环显示
	 * 
	 * @author lih
	 * @since 2012-6-18
	 * @return
	 */
	public List<State> getOtherType() {
		return mine2012FacadeIface.getOtherTypeMap();
	}

	/**
	 * setter or getter
	 */
	public ZjMineReport getMineReport() {
		return mineReport;
	}

	public void setMineReport(ZjMineReport mineReport) {
		this.mineReport = mineReport;
	}

	public List<ZjMineReportDetail> getMineReportDetails() {
		return mineReportDetails;
	}

	public void setMineReportDetails(List<ZjMineReportDetail> mineReportDetails) {
		this.mineReportDetails = mineReportDetails;
	}

	public List<ZjMineReport> getMineReports() {
		return mineReports;
	}

	public void setMineReports(List<ZjMineReport> mineReports) {
		this.mineReports = mineReports;
	}

	public List<ZjStatistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<ZjStatistic> statistics) {
		this.statistics = statistics;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	
	public void setMine2012FacadeIface(Mine2012FacadeIface mine2012FacadeIface) {
		this.mine2012FacadeIface = mine2012FacadeIface;
	}

	
	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}
}