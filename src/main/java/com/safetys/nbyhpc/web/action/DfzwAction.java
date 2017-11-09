/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;
import com.safetys.nbyhpc.domain.model.ZjDfzwReportDetail;
import com.safetys.nbyhpc.domain.model.ZjIdea;
import com.safetys.nbyhpc.facade.iface.Axis2FacadeIface;
import com.safetys.nbyhpc.facade.iface.DfzwFacadeIface;
import com.safetys.nbyhpc.facade.iface.IdeaFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.State;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

//。。
/**
 * 打非治违周报表Action
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public class DfzwAction extends DaAppAction {

	private static final long serialVersionUID = -806308569550257407L;

	private DfzwFacadeIface dfzwFacadeIface;

	private UserFacadeIface userFacadeIface;

	private IdeaFacadeIface ideaFacadeIface;

	private MineFacadeIface mineFacadeIface;// 矿山接口

	private Axis2FacadeIface axis2FacadeIface;

	private ZjDfzwReport entity;

	public final static String PRINT = "print";

	private Pagination pagination;

	private List<ZjDfzwReport> entities;

	private List<ZjDfzwReportDetail> details;

	private List<FkArea> areas;// 区域集合（浙江省所有地级市）

	private boolean allowCreate;// 是否允许用户进行创建的操作

	private String roleDepic;

	public int isAllReport = 0;
	
	//分四个集合分别保存四级区域代码，这样在页面显示区域的时候，每级区域显示，只循环当前级别区域的数据。可以提高效率。
	//如果都放在一个集合里面，查询一次，但是在页面上显示的时候，显示每级区域的时候，都要循环所有的区域，会降低效率。
	//所以此处采用四个区域保存
	private List<FkArea> firstAreas;
	private List<FkArea> secondAreas;
	private List<FkArea> thirdAreas;
	private List<FkArea> fourthAreas;
	//当前用户是否含有安委办查看的角色，默认是无
	private String hasAjjLookRole="0";
	
	//当前用户是否是安委会用户，默认0为否，1为是
	private String anweiUserFlag="0";
	//是否是安委会退回到各部门的，默认0为否，1为是
	private String anweiBackFlag="0";
	
	
	

	/**
	 * 创建页面初始化
	 * 
	 * @return
	 */
	public String createInit() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setUser(fkUser);
			loadNewDate();
			entity = null;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String createInitForCity() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setUser(fkUser);
			loadNewDateForCity();
			entity = null;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void loadNewDate() throws Exception {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		entities = dfzwFacadeIface.load(getEntity(), pagination);
		if (entities != null && entities.size() > 0) {
			Long dfzwId = entities.get(0).getId();
			details = dfzwFacadeIface.loadDetails(dfzwId, null);
		}
	}

	/**
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void loadNewDateForCity() throws Exception {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		entities = dfzwFacadeIface.loadForCity(getEntity(), pagination);
		if (entities != null && entities.size() > 0) {
			Long dfzwId = entities.get(0).getId();
			details = dfzwFacadeIface.loadDetails(dfzwId, null);
		}
	}

	public String create() {
		try {
			FkUser fkUser = new FkUser();
			fkUser.setId(getUserId());
			getEntity().setUser(fkUser);// 设置当前用户为操作用户
			entity.setState(0);
			// 这里需要处理roleDepic不存在的现象
			FkUser user = userFacadeIface.loadUser(getUserId());
			String roleDepic = RoleType1.roleByDepic(user);
			entity.setRoleDepic(roleDepic);
			System.out.println("----------------------------createDfzw-" + roleDepic + "--------------------------------------");
			dfzwFacadeIface.create(entity, details);
			Boolean isAnweiCheck = entity.isAnweiCheck();
			if (entity.isProvince()) {// 设置本部门下级上报的不能退回
				ZjDfzwReport dfzwreport = new ZjDfzwReport();
				dfzwreport.setUser(user);
				dfzwreport.setProvince(false);
				dfzwreport.setQueryReportMonth(true);
				dfzwreport.setAnweiCheck(isAnweiCheck);
				dfzwreport.setReportDateBegin(entity.getReportDateBegin());
				dfzwreport.setReportDateEnd(entity.getReportDateEnd());
				List<ZjDfzwReport> entitys = dfzwFacadeIface.load(dfzwreport, null);
				for (ZjDfzwReport dfzw : entitys) {
					dfzw.setNotAllowedroolBack(true);
					dfzwFacadeIface.update(dfzw);
				}
				List<ZjDfzwReport> entitys2 = dfzwFacadeIface.loadForCitySelf(dfzwreport, null);
				for (ZjDfzwReport dfzw : entitys2) {
					dfzw.setState(3);
					dfzwFacadeIface.update(dfzw);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (entity.isReport()) {// 查看市级统计列表
			setUrl(getContextPath() + "/redirectServlet");
		} else if (entity.isProvince()) {// 查看省级统计列表
			setUrl("loadProvinceReports.xhtml");
		} else {// 查看县级统计列表
			setUrl("list.xhtml");
		}
		setMessageKey("create.success");
		return SUCCESS;
	}

	public String createForCity() {
		try {
			FkUser fkUser = new FkUser();
			fkUser.setId(getUserId());
			getEntity().setUser(fkUser);// 设置当前用户为操作用户
			entity.setState(0);
			// 这里需要处理roleDepic不存在的现象
			String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, userFacadeIface.loadUser(getUserId()).getFkRoles());
			entity.setRoleDepic(roleDepic);
			dfzwFacadeIface.create(entity, details);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl("listForCity.xhtml");
		setMessageKey("create.success");
		return SUCCESS;
	}

	public String list() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			getEntity().setUser(fkUser);
		    System.out.println("查询条件地区编码为："+entity.getSecondArea());
			entities = dfzwFacadeIface.load(entity, pagination);
			int isReport = 0;
			roleDepic = RoleType1.roleByDepic(fkUser);
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {// 如果登陆用户是县级用户
				anweiUserFlag="0";
				System.out.println("县级用户");
				List<ZjDfzwReport> reports = dfzwFacadeIface.loadByCity(fkUser);
				for (ZjDfzwReport dfzw : entities) {

					isReport = 0;
					// System.out.println("dfzw: " + dfzw.getReportDateEnd());
					// System.out.println("dfzw: " + dfzw.getRoleDepic());

					isReport = dfzwFacadeIface.isReport(dfzw.getRoleDepic(), dfzw.getReportDateEnd());
					if (isReport == 1) {
						dfzw.setReport(true);
					}

					// 原有
					for (int i = 0; i < reports.size(); i++) {

						if (dfzw.getReportType() != null) {
							if (dfzw.getReportType().intValue() == 1) {
								if (dfzw.getReportDateBegin().equals(reports.get(i).getReportDateBegin()) && reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
									dfzw.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
								}
							} else {
								if (dfzw.getReportDateEnd().equals(reports.get(i).getReportDateEnd()) && reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
									dfzw.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
								}
							}
						}
					}

				}
			}else if(entity.isProvince()&&RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())&&"anwei".equals(roleDepic)){
				//各部门上报查看时，当前用户为宁波市级用户，且所属部门为安委会时。如果安委会没有上报到省局，则可以让他退回各部门上报的数据。
				anweiUserFlag="1";
				//List<ZjDfzwReport> reports = dfzwFacadeIface.loadByCity(fkUser);
				//查询所有安委办上报的打非治违信息
				List<ZjDfzwReport> reports  = dfzwFacadeIface.loadCityReport(entity,null);
				
				System.out.println("reports.size="+reports.size());
				for (ZjDfzwReport dfzw : entities) {
					//设置初始值为都能退回
					dfzw.setAnweiBackState("1");
					// 原有
					for (int i = 0; i < reports.size(); i++) {
						if (dfzw.getReportType() != null) {
								//首先先判断是否存在结束日期相同的打非治违信息
								if(dfzw.getReportDateEnd().equals(reports.get(i).getReportDateEnd())&& reports.get(i).getState() > SafetyTrouble.NO_REPORT){
									//存在且上报状态为已上报状态，则不能退回
									dfzw.setAnweiBackState("0");
									continue;
								}
						}
					}
				}

			}

			for (ZjDfzwReport dfzw : entities)
				dfzw.setNowUser(fkUser);
			
			//根据层级结构获得宁波市区域集合
			//areas = mineFacadeIface.loadAreasByGradePath("%/0/1/3020%");
			Long ararCode =new Long(330200000000L);
			
			//this.firstAreas=mineFacadeIface.loadAreasByGrade(ararCode, "1");
			this.secondAreas=mineFacadeIface.loadAreasByGrade(ararCode, "2");
			//this.thirdAreas=mineFacadeIface.loadAreasByGrade(ararCode, "3");
			//this.fourthAreas=mineFacadeIface.loadAreasByGrade(ararCode, "4");
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String listForCity() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			getEntity().setUser(fkUser);
			entities = dfzwFacadeIface.loadForCity(entity, pagination);
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {// 如果登陆用户是市级用户
				List<ZjDfzwReport> reports = dfzwFacadeIface.loadByCity(fkUser);
				for (ZjDfzwReport dfzw : entities) {
					for (int i = 0; i < reports.size(); i++) {
						if (dfzw.getReportType().intValue() == 1) {
							if (dfzw.getReportDateBegin().equals(reports.get(i).getReportDateBegin()) && reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
								dfzw.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
							}
						} else {
							if (dfzw.getReportDateEnd().equals(reports.get(i).getReportDateEnd()) && reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
								dfzw.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
							}
						}
					}
				}
			}
			roleDepic = RoleType1.roleByDepic(fkUser);
			for (ZjDfzwReport dfzw : entities)
				dfzw.setNowUser(fkUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 根据对应ID获取其他的详细信息
	 * 
	 * @return
	 */
	public String load() {
		try {
			entity = dfzwFacadeIface.load(entity.getId());
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwFacadeIface.loadDetails(entity.getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadForCity() {
		try {
			entity = dfzwFacadeIface.load(entity.getId());
			if (entity == null) {
				setUrl("listForCity.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwFacadeIface.loadDetails(entity.getId(), null);
		} catch (Exception e) {
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
	public String update() {
		try {
			dfzwFacadeIface.update(entity, details);
			// dfzwDetailFacadeIface.deleteZhifaReportDetailsByZhifa(entity
			// .getId());
			// dfzwDetailFacadeIface.createZhifaReportDetails(details,
			// entity.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (entity.isReport()) {
			setUrl(getContextPath() + "/redirectServlet");
		} else if (entity.isProvince()) {// 查看省级统计列表
			setUrl("loadProvinceReports.xhtml");
		} else {
			setUrl("list.xhtml");
		}
		setMessageKey("update.success");
		return SUCCESS;
	}

	public String updateForCity() {
		try {
			dfzwFacadeIface.update(entity, details);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl("listForCity.xhtml");
		setMessageKey("update.success");
		return SUCCESS;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		try {
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			dfzwFacadeIface.deleteDetails(entity.getId());
			dfzwFacadeIface.delete(entity);

			FkUser fkUser = new FkUser();
			fkUser.setId(getUserId());
			getEntity().setUser(fkUser);// 设置当前用户为操作用户
			if (entity.isProvince()) {// 设置本部门下级上报的不能退回
				ZjDfzwReport dfzwReport = new ZjDfzwReport();
				entity = dfzwFacadeIface.load(entity.getId());
				FkUser user = userFacadeIface.loadUser(getUserId());
				dfzwReport.setUser(user);
				dfzwReport.setProvince(false);
				dfzwReport.setQueryReportMonth(true);
				dfzwReport.setReportDateBegin(entity.getReportDateBegin());
				dfzwReport.setReportDateEnd(entity.getReportDateEnd());
				List<ZjDfzwReport> entitys = dfzwFacadeIface.load(dfzwReport, null);
				for (ZjDfzwReport dfzw : entitys) {
					dfzw.setNotAllowedroolBack(false);
					dfzwFacadeIface.update(dfzw);
				}
				List<ZjDfzwReport> entitys2 = dfzwFacadeIface.loadForCitySelf(dfzwReport, null);
				for (ZjDfzwReport dfzw : entitys2) {
					dfzw.setState(0);
					dfzwFacadeIface.update(dfzw);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (entity.isReport()) {
			setUrl(getContextPath() + "/redirectServlet");
		} else {
			setUrl("list.xhtml");
		}
		setMessageKey("delete.success");
		return SUCCESS;
	}

	public String deleteForCity() {
		try {
			if (entity == null) {
				setUrl("listForCity.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			dfzwFacadeIface.deleteDetails(entity.getId());
			dfzwFacadeIface.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl("listForCity.xhtml");
		setMessageKey("delete.success");
		return SUCCESS;
	}

	/**
	 * 上报数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String doneReport() {
		try {
			if (entity == null) {
				setUrl("../dfzw/list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			ZjDfzwReport dfzw = dfzwFacadeIface.load(entity.getId());
			String url = "../dfzw/list.xhtml";
			if (dfzw == null) {
				setUrl("../dfzw/list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			} else if (dfzw.getState() > SafetyTrouble.COUNTY_REPORT) {
				setUrl(url);
				setMessageKey("report.save.already");
				return MESSAGE;
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			Map map = SafetyTrouble.getState(fkUser, dfzw.getState());
			if (!(Boolean) map.get("allow")) {
				setUrl(url);
				if (dfzw.getState() == SafetyTrouble.NO_REPORT) {
					setMessageKey("report.save.fail.noReport");
				} else if (dfzw.getState() == SafetyTrouble.CITY_REPORT) {
					setMessageKey("report.save.fail.cityReport");
				} else if (dfzw.getState() == SafetyTrouble.COUNTY_REPORT) {
					setMessageKey("report.save.fail.countyReport");
				}
				return MESSAGE;
			}
			dfzw.setState(Integer.parseInt(map.get("state").toString()));
			dfzw.setModifyTime(new Date());
			dfzwFacadeIface.update(dfzw);
			// 市安委上报同时发送数据到省隐患排查
			if (dfzw.getState() == SafetyTrouble.PROVINCE_REPORT) {
				String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
				if ("anwei".equals(roleDepic)) {
					axis2FacadeIface.sendDataDfzwByOMElement(dfzw, getUserId());
				}
			}
			// List<ZjDfzwReport> reports = dfzwFacadeIface.loadByCity(fkUser);
			// for (ZjDfzwReport report : reports) {
			// if (dfzw.getReportDateBegin().after(report.getReportDateBegin()))
			// {
			// if (report.getState() == SafetyTrouble.NO_REPORT) {//
			// 如果没有上报，则记录当前时间为上报时间
			// report.setModifyTime(new Date());
			// report
			// .setState(RoleType1.isRoleByDepic(RoleType1.CITY,
			// fkUser.getFkRoles()) ? SafetyTrouble.CITY_REPORT
			// : SafetyTrouble.COUNTY_REPORT);
			// dfzwFacadeIface.update(report);
			// ZjIdea idea = new ZjIdea(userFacadeIface.loadUser(getUserId()),
			// "批量上报", 3L, report.getId());
			// ideaFacadeIface.createIdea(idea);
			// }
			// }
			// }
			if (!entity.isReport()) {
				setUrl(url);
			} else {
				setUrl(getContextPath() + "/redirectServlet");
				setMessageKey("report.save.success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 页面查看
	 * 
	 * @return
	 */
	public String view() {
		try {
			entity = dfzwFacadeIface.load(entity.getId());
			entity.setView(true);
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwFacadeIface.loadDetails(entity.getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String doneCityReportInit() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setUser(fkUser);
			entity.setQueryReportMonth(true);

			
			//此处需要对安监局查看角色用户进行特殊处理，当用户为查看角色时，只让他查询安委办的记录，其他角色保持不变
			boolean isAjjLookRole=RoleType1.isRoleByDepic(RoleType1.AJJLOOK, fkUser.getFkRoles());
			if(isAjjLookRole){
				hasAjjLookRole="1";
				//是查看角色时,此处改成只查询安委办的汇总记录
				entities = dfzwFacadeIface.loadCityReport(entity, pagination);
			}else{
				hasAjjLookRole="0";
				//其他角色时，查询条件不变
				entities = dfzwFacadeIface.load(entity, pagination);
			}
			int isReport = 0;
			for (ZjDfzwReport dfzw : entities) {
				dfzw.setNowUser(fkUser);
				isReport = 0;
				System.out.println("dfzw: " + dfzw.getReportDateEnd());
				System.out.println("dfzw: " + dfzw.getRoleDepic());
				if (!dfzw.getRoleDepic().equals("anwei")) {
					isReport = dfzwFacadeIface.isReport("anwei", dfzw.getReportDateEnd());
					if (isReport == 1) {
						dfzw.setReport(true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDetailsByDfzw() {
		boolean isPrint = getEntity().isPrint();
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setUser(fkUser);
			Boolean isProvince = entity.isProvince();
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwFacadeIface.load(entity.getId());
			}
			entity.setProvince(isProvince);
			details = dfzwFacadeIface.loadDetailsByDfzw(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (isPrint) {
			return PRINT;
		}
		if (isPrint) {
			return PRINT;
		}
		return SUCCESS;
	}

	/**
	 * 加载市级显示数据
	 * 
	 * @return
	 */
	public String loadCityReportInit() {
		boolean isPrint = getEntity().isPrint();
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setUser(fkUser);
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwFacadeIface.load(entity.getId());
			}
			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
			if (!dfzwFacadeIface.is_all_reported("county", getUserDetail().getSecondArea(), entity)) {
				setMessageKey("county.is.not.all.report");
				return MESSAGE;
			}
			details = dfzwFacadeIface.loadDetailsByUser(entity);
			if (entity.getId() == -1L) {// 第一次加载，之后创建
				// entity = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (isPrint) {
			return PRINT;
		}
		return SUCCESS;
	}

	public String doneCityReport() {
		if (entity == null) {
			return SUCCESS;
		}
		try {
			entity = dfzwFacadeIface.load(entity.getId());
			entities = dfzwFacadeIface.load(entity, null);
			// if (true) {
			doneReport();
			// } else {
			// setUrl("doneCityReportInit.xhtml");
			// setMessageKey("report.save.fail.cityReport.noAllReport");
			// return MESSAGE;
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl("loadProvinceReports.xhtml?mineReport.type=5");
		return SUCCESS;
	}

	/**
	 * 查看省级统计列表
	 * 
	 * @return
	 */
	public String loadProvinceReports() {
		System.out.println("&&&&&&&&&&&&&&&&&&&");
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setProvince(true);
			doneCityReportInit();
			roleDepic = RoleType1.roleByDepic(fkUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查看省级统计
	 * 
	 * @return
	 */
	public String loadProvinceReport() {
		boolean isPrint = false;
		try {
			isPrint = getEntity().isPrint();
			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
			// if (!isPrint && !isPass) {
			// if (!dfzwFacadeIface.is_all_reported("city", getUserDetail()
			// .getFirstArea(), entity)) {
			// setMessageKey("city.is.not.all.report");
			// return MESSAGE;
			// }
			// }
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwFacadeIface.load(entity.getId());
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setQueryReportMonth(true);
			entity.setUser(fkUser);
			entity.setProvince(true);
			details = dfzwFacadeIface.loadDetailsForProvince(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (isPrint) {
			return PRINT;
		}
		return SUCCESS;
	}

	/**
	 * 各个市级部门汇总新增初始化填报数据
	 * 
	 * @return
	 */
	public String loadProvinceReportForCity() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setQueryReportMonth(true);
			entity.setUser(fkUser);
			details = dfzwFacadeIface.loadDetailsForCityInit(entity);

			isAllReport = dfzwFacadeIface.getIsAllReport();
			entity.setProvince(true);

			Calendar c = Calendar.getInstance();
			c.setTime(entity.getReportDateBegin());
			int day1 = c.get(Calendar.DAY_OF_MONTH);
			c.setTime(entity.getReportDateEnd());
			int day2 = c.get(Calendar.DAY_OF_MONTH);
			if (day1 == 1 && day2 > 20) {
				entity.setReportType(2);
			} else {
				entity.setReportType(1);
			}
		} catch (Exception e) {
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
	public String print() {
		try {
			entity = dfzwFacadeIface.load(entity.getId());
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwFacadeIface.loadDetails(entity.getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 退回方法
	 * 
	 * @return
	 */
	public String rollback() {
		if (entity == null) {
			setUrl("../dfzw/list.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		System.out.println("-------------");
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			ZjDfzwReport dfzw = dfzwFacadeIface.load(entity.getId());
			if (dfzw == null) {
				setUrl("../dfzw/list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
				List<ZjDfzwReport> reports = dfzwFacadeIface.loadByCity(fkUser);
				for (ZjDfzwReport report : reports)
					if (report.getReportDateBegin().equals(dfzw.getReportDateBegin()))
						if (report.getState() > SafetyTrouble.NO_REPORT) {
							setUrl("");
							setMessageKey("rollback.fail.city.cityIsReport");
							return MESSAGE;
						}
			}
			List<ZjDfzwReport> countys = dfzwFacadeIface.loadByCity(dfzw.getUser());
			for (ZjDfzwReport report : countys) {
				if (report.getState() == ((RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) ? SafetyTrouble.COUNTY_REPORT : SafetyTrouble.CITY_REPORT)) {
					// System.out.println("report.getReportDateBegin(): "+report.getReportDateBegin());
					// System.out.println("dfzw.getReportDateBegin(): "+dfzw.getReportDateBegin());
					if (report.getReportDateBegin() != null && dfzw.getReportDateBegin() != null) {
						if (dfzw.getReportDateBegin().before(report.getReportDateBegin())) {
							setUrl("");
							setMessageKey(RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles()) ? "rollback.fail.city.countyAfterReport" : "rollback.fail.province.cityAfterReport");
							return MESSAGE;
						}
					}
				}
			}
			dfzw.setModifyTime(new Date());
			String content = "";
			
			if("0".equals(anweiBackFlag)){
				if (dfzw.getState() == SafetyTrouble.CITY_REPORT) {
					dfzw.setState(0);
					content = "省级退回";
				} else {
					dfzw.setState(dfzw.getState() - 1);
					content = "市级退回";
				}
			}else{
				//为1时表示从安委会退回，state设置为0，其他条件不变化
				dfzw.setState(0);
				content = "省级退回";
				
			}
			
			dfzwFacadeIface.update(dfzw);
			ZjIdea idea = new ZjIdea(userFacadeIface.loadUser(getUserId()), content, 3l, dfzw.getId());
			ideaFacadeIface.createIdea(idea);
			setUrl(getContextPath() + "/redirectServlet");
			setMessageKey("rollback.save.success");
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	
	
	/**
	 * 上报总局
	 * 
	 * @return
	 */
	public String loadCountryReports2010() {
		try {
			getEntity().setProvince(true);
			doneCityReportInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 国家统计查看
	 * 
	 * @return
	 */
	public String loadCountry() {
		boolean isPrint = false;
		try {
			isPrint = getEntity().isPrint();
			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
			// if (!dfzwFacadeIface.is_all_reported("city", getUserDetail()
			// .getFirstArea(), entity)) {
			// setMessageKey("city.is.not.all.report");
			// return MESSAGE;
			// }
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwFacadeIface.load(entity.getId());
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setQueryReportMonth(true);
			entity.setUser(fkUser);
			details = dfzwFacadeIface.loadDetailsForProvince(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		if (isPrint) {
			return PRINT;
		}
		return SUCCESS;
	}

	public String loadCountyReport() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(18);
			}
			getEntity().setUser(fkUser);
			Pagination temp = new Pagination();
			temp.setPageSize(100);
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
				areas = mineFacadeIface.loadAreas(0L, temp);
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
				Long area_id = entity.getUser().getFkUserInfo().getSecondArea();
				if (area_id != null && !"".equals(area_id)) {
					areas = dfzwFacadeIface.loadCityAreas(area_id);
				}
			}
			entities = dfzwFacadeIface.loadCountyReports(entity, pagination);
			for (ZjDfzwReport dfzw : entities)
				dfzw.setNowUser(fkUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 验证用户是否可以保存，ajax调用
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @return
	 */
	public String checkAllowCreate() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setUser(fkUser);
			allowCreate = dfzwFacadeIface.checkAllowCreate(getEntity());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String checkAllowCreate1() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setUser(fkUser);
			if (entity.isAnweiCheck()) {
				allowCreate = dfzwFacadeIface.checkAllowCreate2(getEntity());
			} else {
				allowCreate = dfzwFacadeIface.checkAllowCreate1(getEntity());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String checkAllowCreate3() {
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			getEntity().setUser(fkUser);
			allowCreate = dfzwFacadeIface.checkAllowCreate3(getEntity());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public List<State> getTypes() {
		return SafetyTrouble.DFZW_TYPE_MAP;
	}

	public ZjDfzwReport getEntity() {
		if (entity == null) {
			entity = new ZjDfzwReport();
		}
		return entity;
	}

	public void setEntity(ZjDfzwReport entity) {
		this.entity = entity;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<ZjDfzwReport> getEntities() {
		return entities;
	}

	public void setEntities(List<ZjDfzwReport> entities) {
		this.entities = entities;
	}

	public List<ZjDfzwReportDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ZjDfzwReportDetail> details) {
		this.details = details;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

	public void setDfzwFacadeIface(DfzwFacadeIface dfzwFacadeIface) {
		this.dfzwFacadeIface = dfzwFacadeIface;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public void setIdeaFacadeIface(IdeaFacadeIface ideaFacadeIface) {
		this.ideaFacadeIface = ideaFacadeIface;
	}

	public void setAxis2FacadeIface(Axis2FacadeIface axis2FacadeIface) {
		this.axis2FacadeIface = axis2FacadeIface;
	}

	public boolean isAllowCreate() {
		return allowCreate;
	}

	public void setAllowCreate(boolean allowCreate) {
		this.allowCreate = allowCreate;
	}

	public void setMineFacadeIface(MineFacadeIface mineFacadeIface) {
		this.mineFacadeIface = mineFacadeIface;
	}

	public String getRoleDepic() {
		return roleDepic;
	}

	public void setRoleDepic(String roleDepic) {
		this.roleDepic = roleDepic;
	}

	public int getIsAllReport() {
		return isAllReport;
	}

	public void setIsAllReport(int isAllReport) {
		this.isAllReport = isAllReport;
	}

	/**
	 * @return the firstAreas
	 */
	public List<FkArea> getFirstAreas() {
		return firstAreas;
	}

	/**
	 * @param firstAreas the firstAreas to set
	 */
	public void setFirstAreas(List<FkArea> firstAreas) {
		this.firstAreas = firstAreas;
	}

	/**
	 * @return the secondAreas
	 */
	public List<FkArea> getSecondAreas() {
		return secondAreas;
	}

	/**
	 * @param secondAreas the secondAreas to set
	 */
	public void setSecondAreas(List<FkArea> secondAreas) {
		this.secondAreas = secondAreas;
	}

	/**
	 * @return the thirdAreas
	 */
	public List<FkArea> getThirdAreas() {
		return thirdAreas;
	}

	/**
	 * @param thirdAreas the thirdAreas to set
	 */
	public void setThirdAreas(List<FkArea> thirdAreas) {
		this.thirdAreas = thirdAreas;
	}

	/**
	 * @return the fourthArea
	 */
	public List<FkArea> getFourthAreas() {
		return fourthAreas;
	}

	/**
	 * @param fourthAreas the fourthAreas to set
	 */
	public void setFourthAreas(List<FkArea> fourthAreas) {
		this.fourthAreas = fourthAreas;
	}

	/**
	 * @return the hasAjjLookRole
	 */
	public String getHasAjjLookRole() {
		return hasAjjLookRole;
	}

	/**
	 * @param hasAjjLookRole the hasAjjLookRole to set
	 */
	public void setHasAjjLookRole(String hasAjjLookRole) {
		this.hasAjjLookRole = hasAjjLookRole;
	}

	/**
	 * @return the anweiBackFlag
	 */
	public String getAnweiBackFlag() {
		return anweiBackFlag;
	}

	/**
	 * @param anweiBackFlag the anweiBackFlag to set
	 */
	public void setAnweiBackFlag(String anweiBackFlag) {
		this.anweiBackFlag = anweiBackFlag;
	}

	/**
	 * @return the anweiUserFlag
	 */
	public String getAnweiUserFlag() {
		return anweiUserFlag;
	}

	/**
	 * @param anweiUserFlag the anweiUserFlag to set
	 */
	public void setAnweiUserFlag(String anweiUserFlag) {
		this.anweiUserFlag = anweiUserFlag;
	}

}
