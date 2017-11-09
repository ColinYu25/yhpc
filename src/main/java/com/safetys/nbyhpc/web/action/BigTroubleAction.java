package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.facade.iface.BigTroubleFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class BigTroubleAction extends DaAppAction {

	private static final long serialVersionUID = 5241392654492908378L;

	private UserFacadeIface userFacadeIface;// 用户接口（用于调用用户模块相关方法）

	private String[] years;// 显示年月的年份

	private String[] months = SafetyTrouble.MONTHS;// 显示年月的月份

	private ZjBigTrouble bigTrouble;

	private List<ZjBigTrouble> bigTroubles;

	private BigTroubleFacadeIface bigTroubleFacadeIface;

	private MineFacadeIface mineFacadeIface;

	private Pagination pagination;// 分页参数

	private Integer tableType;// 表分类

	private Integer tradeType;// 行业分类

	private String reportMonth;// 时间

	private Integer state;// 是否销号0:未销号 1已销号-1全部

	private Integer troubleType;// 隐患类型

	private Integer reportState;// 上报类型

	private Integer mineType;// 统计上报2010

	private Integer mineId;

	private String tableName;

	public String loadBigTrouble() {
		long id = bigTrouble.getId();
		years = SafetyTrouble.getYaers();
		try {
			if (id > 0) {// 修改
				Integer tt = bigTrouble.getTroubleType();
				Integer ttb = bigTrouble.getReportState();
				String month = bigTrouble.getReportMonth2();
				String state = bigTrouble.getReportState2();
				bigTrouble = bigTroubleFacadeIface.loadBigTrouble(id);
				bigTrouble.setTroubleType(tt);
				bigTrouble.setReportState(ttb);
				bigTrouble.setReportMonth2(month);
				bigTrouble.setReportState2(state);
			} else {// 新增
				bigTrouble.setReportMonth(bigTrouble.getReportMonth());
				bigTrouble.setTableType(bigTrouble.getTableType());
				bigTrouble.setTradeType(bigTrouble.getTradeType());
				bigTrouble.setTroubleType(bigTrouble.getTroubleType());
				bigTrouble.setReportState(bigTrouble.getReportState());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改重大隐患
	 * 
	 * @return
	 */
	public String updateBigTrouble() {
		try {
			if (bigTrouble.getReportMonth2() != null && !"".equals(bigTrouble.getReportMonth2())) {
				bigTrouble.setReportMonth(bigTrouble.getReportMonth2());
			}
			bigTroubleFacadeIface.updateBigTrouble(bigTrouble);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMessageKey("update.success");
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	/**
	 * 修改重大隐患的备注
	 * 
	 * @return
	 */
	public String updateBigTroubleRemark() {
		try {
			if (bigTrouble.getReportMonth2() != null && !"".equals(bigTrouble.getReportMonth2())) {
				bigTrouble.setReportMonth(bigTrouble.getReportMonth2());
			}
			bigTroubleFacadeIface.updateBigTroubleRemark(bigTrouble);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMessageKey("update.success");
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	/**
	 * 销号或反销号
	 * 
	 * @return
	 */
	public String updateBigTroubleOut() {
		try {
			bigTrouble.setState(state);
			bigTrouble.setReportMonth(bigTrouble.getReportMonth());
			bigTroubleFacadeIface.updateBigTroubleOut(bigTrouble);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMessageKey("destroy.success");
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	public String loadBigTroubles() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		FkUser fkUser = new FkUser();
		if (bigTrouble == null) {
			bigTrouble = new ZjBigTrouble();
		}
		/**
		 * 根据隐患类型显示不同数据
		 */
		if (troubleType == 1) {//全部隐患
			state = -1;
		}
		if (troubleType == 2) {//已销号
			state = 1;
		} else if (troubleType == 3) {//列入治理（即 未治理）
			state = 0;
		} else if (troubleType == 4) {//五到位
			state = 0;
			bigTrouble.setWdw(true);
		} else if (troubleType == 5) {//挂牌督办
			state = -1;
			bigTrouble.setGuapai(true);
		} else if (troubleType == 6) {//省级挂牌
			state = -1;
			bigTrouble.setGuapai(true);
			bigTrouble.setGuapaiLevel(3);
		} else if (troubleType == 7) {//市级挂牌
			state = -1;
			bigTrouble.setGuapai(true);
			bigTrouble.setGuapaiLevel(2);
		}
		try {
			fkUser = userFacadeIface.loadUser(getUserId());
			if (mineId == null || mineId == 0 || mineId == -1) {

			} else {
				long userId = bigTroubleFacadeIface.getMineUserId(mineId).getUserId().getId();
				fkUser = userFacadeIface.loadUser(userId);
			}
			bigTrouble.setTableType(tableType);
			bigTrouble.setTradeType(tradeType);
			bigTrouble.setReportMonth(reportMonth);
			bigTrouble.setState(state);
			bigTrouble.setUserId(fkUser);
			bigTroubles = bigTroubleFacadeIface.loadBigTroubles(bigTrouble, mineType, pagination);
			if (RoleType1.isRoleByDepic(RoleType1.COUNTY, userFacadeIface.loadUser(getUserId()).getFkRoles())) {
				bigTrouble.setIsCounty("county");
			} else {

			}
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 重大隐患打印列表
	 * 
	 * @return
	 */
	public String loadBigTroubles_excel() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		FkUser fkUser = new FkUser();
		try {
			if (mineId == null || mineId == 0 || mineId == -1) {
				fkUser = userFacadeIface.loadUser(getUserId());
			} else {
				long userId = bigTroubleFacadeIface.getMineUserId(mineId).getUserId().getId();
				fkUser = userFacadeIface.loadUser(userId);
			}
			if (bigTrouble == null) {
				bigTrouble = new ZjBigTrouble();
			}
			bigTrouble.setTableType(tableType);
			bigTrouble.setTradeType(tradeType);
			bigTrouble.setReportMonth(reportMonth);
			bigTrouble.setState(state);
			bigTrouble.setUserId(fkUser);
			bigTroubles = bigTroubleFacadeIface.loadBigTroubles_excel(bigTrouble, pagination);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 显示所有重大隐患
	 * 
	 * @return
	 */
	public String loadAllBigTroubles() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		if (bigTrouble == null) {
			bigTrouble = new ZjBigTrouble();
			pagination.setPageSize(18);
		}
		try {
			bigTroubles = bigTroubleFacadeIface.loadAllBigTroubles(bigTrouble, pagination);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改重大隐患销号时间
	 * 
	 * @return
	 */
	public String updateBigTroubleStateTime() {
		try {
			bigTroubleFacadeIface.updateBigTroubleTime(bigTrouble);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMessageKey("update.success");
		setUrl("loadAllBigTroubles.xhtml");
		return SUCCESS;
	}

	public String createBigTrouble() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			bigTrouble.setUserId(fkUser);
			bigTrouble.setState(0);
			bigTrouble.setStateTime(" ");
			bigTrouble.setChargeMan(" ");
			bigTroubleFacadeIface.createBigTrouble(bigTrouble);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setMessageKey("create.success");
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	public String deleteBigTrouble() {
		try {
			if (bigTrouble == null)
				return SUCCESS;
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			bigTrouble = bigTroubleFacadeIface.loadBigTrouble(bigTrouble.getId());
			ZjMineReport tmpMine = mineFacadeIface.loadMineReportByBigTrouble(bigTrouble);
			if (tmpMine == null) {
				// setUrl("");
				// setMessageKey("noData");
				// return MESSAGE;
			} else if (tmpMine.getState() > SafetyTrouble.NO_REPORT) {
				setUrl("");
				setMessageKey("bigTrouble.delete.fail.mineIsReport");
				return MESSAGE;
			}
			bigTroubleFacadeIface.deleteBigTrouble(bigTrouble, fkUser);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setMessageKey("delete.success");
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}
	
	/**
	 * 查询将要上报报表中含有的未选择挂牌督办级别的重大隐患
	 * @author lih
	 * @since 2012-6-29
	 * @return
	 */
	public String loadNoChooseGuapaiLevel() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		if (bigTrouble == null) {
			bigTrouble = new ZjBigTrouble();
		}
		try {
			FkUser fkUser = new FkUser();
			fkUser = userFacadeIface.loadUser(getUserId());
			if (mineId == null || mineId == 0 || mineId == -1) {

			} else {
				long userId = bigTroubleFacadeIface.getMineUserId(mineId).getUserId().getId();
				fkUser = userFacadeIface.loadUser(userId);
			}
			bigTrouble.setTableType(tableType);
			bigTrouble.setReportMonth(reportMonth);
			bigTrouble.setUserId(fkUser);
			bigTroubles = bigTroubleFacadeIface.loadBigTroubleHaveNoChooseGuapaiLevel(getUserDetail(), bigTrouble,pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	

	public ZjBigTrouble getBigTrouble() {
		return bigTrouble;
	}

	public void setBigTrouble(ZjBigTrouble bigTrouble) {
		this.bigTrouble = bigTrouble;
	}

	public List<ZjBigTrouble> getBigTroubles() {
		return bigTroubles;
	}

	public void setBigTroubles(List<ZjBigTrouble> bigTroubles) {
		this.bigTroubles = bigTroubles;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTableType() {
		return tableType;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getTroubleType() {
		return troubleType;
	}

	public void setTroubleType(Integer troubleType) {
		this.troubleType = troubleType;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public Integer getReportState() {
		return reportState;
	}

	public void setReportState(Integer reportState) {
		this.reportState = reportState;
	}

	public Integer getMineId() {
		return mineId;
	}

	public void setMineId(Integer mineId) {
		this.mineId = mineId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public MineFacadeIface getMineFacadeIface() {
		return mineFacadeIface;
	}

	public void setMineFacadeIface(MineFacadeIface mineFacadeIface) {
		this.mineFacadeIface = mineFacadeIface;
	}

	public Integer getMineType() {
		return mineType;
	}

	public void setMineType(Integer mineType) {
		this.mineType = mineType;
	}
}
