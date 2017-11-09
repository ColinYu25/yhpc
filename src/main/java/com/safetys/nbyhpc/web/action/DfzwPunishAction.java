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
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunishDetail;
import com.safetys.nbyhpc.domain.model.ZjIdea;
import com.safetys.nbyhpc.facade.iface.Axis2FacadeIface;
import com.safetys.nbyhpc.facade.iface.DfzwPunishFacadeIface;
import com.safetys.nbyhpc.facade.iface.IdeaFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.State;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * 打非治违处罚周报表Action
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public class DfzwPunishAction extends DaAppAction {

	private static final long serialVersionUID = -806308569550257407L;

	private DfzwPunishFacadeIface dfzwPunishFacadeIface;

	private UserFacadeIface userFacadeIface;

	private IdeaFacadeIface ideaFacadeIface;

	private MineFacadeIface mineFacadeIface;// 矿山接口

	private Axis2FacadeIface axis2FacadeIface;

	public final static String PRINT = "print";

	private ZjDfzwPunish entity;

	private Pagination pagination;

	private List<ZjDfzwPunish> entities;

	private List<ZjDfzwPunishDetail> details;

	private List<FkArea> areas;// 区域集合（浙江省所有地级市）

	private boolean allowCreate;// 是否允许用户进行创建的操作

	private String roleDepic;

	public int isAllReport = 0;
	private List<FkArea> secondAreas;
	
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

	/**
	 * 创建页面初始化(市级部门填报)
	 * 
	 * @return
	 */
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
		entities = dfzwPunishFacadeIface.load(getEntity(), pagination);
		if (entities != null && entities.size() > 0) {
			Long punishId = entities.get(0).getId();
			details = dfzwPunishFacadeIface.loadDetails(punishId, null);
		}
	}

	public void loadNewDateForCity() throws Exception {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(18);
		}
		entities = dfzwPunishFacadeIface.loadForCity(getEntity(), pagination);
		if (entities != null && entities.size() > 0) {
			Long punishId = entities.get(0).getId();
			details = dfzwPunishFacadeIface.loadDetails(punishId, null);
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
			System.out.println("----------------------------createDfzw-"
					+ roleDepic + "--------------------------------------");
			dfzwPunishFacadeIface.create(entity, details);
			Boolean isAnweiCheck = entity.isAnweiCheck();
			if (entity.isProvince()) {// 设置本部门下级上报的不能退回
				ZjDfzwPunish dfzwpunish = new ZjDfzwPunish();
				// FkUser user = userFacadeIface.loadUser(getUserId());
				dfzwpunish.setUser(user);
				dfzwpunish.setProvince(false);
				dfzwpunish.setAnweiCheck(isAnweiCheck);
				dfzwpunish.setReportDateBegin(entity.getReportDateBegin());
				dfzwpunish.setReportDateEnd(entity.getReportDateEnd());
				List<ZjDfzwPunish> entitys = dfzwPunishFacadeIface.load(
						dfzwpunish, null);
				for (ZjDfzwPunish punish : entitys) {
					punish.setNotAllowedroolBack(true);
					dfzwPunishFacadeIface.update(punish);
				}
				List<ZjDfzwPunish> entitys2 = dfzwPunishFacadeIface
						.loadForCitySelf(dfzwpunish, null);
				for (ZjDfzwPunish punish : entitys2) {
					punish.setState(3);
					dfzwPunishFacadeIface.update(punish);
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
			String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE,
					userFacadeIface.loadUser(getUserId()).getFkRoles());
			entity.setRoleDepic(roleDepic);
			dfzwPunishFacadeIface.create(entity, details);
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
			entities = dfzwPunishFacadeIface.load(entity, pagination);

			int isReport = 0;
			roleDepic = RoleType1.roleByDepic(fkUser);
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {// 如果登陆用户是县级用户
				List<ZjDfzwPunish> reports = dfzwPunishFacadeIface
						.loadByCity(fkUser);
				for (ZjDfzwPunish punish : entities) {

					isReport = 0;

					isReport = dfzwPunishFacadeIface.isReport(
							punish.getRoleDepic(), punish.getReportDateEnd());
					if (isReport == 1) {
						punish.setReport(true);
					}

					// 原有
					for (int i = 0; i < reports.size(); i++) {
						if (punish.getReportType() != null) {

							if (punish.getReportType().intValue() == 1) {
								if (punish.getReportDateBegin().equals(
										reports.get(i).getReportDateBegin())
										&& reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
									punish.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
								}
							} else {
								if (punish.getReportDateEnd().equals(
										reports.get(i).getReportDateEnd())
										&& reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
									punish.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
								}
							}

						}

					}
				}
			}else if(entity.isProvince()&&RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())&&"anwei".equals(roleDepic)){
				//各部门上报查看时，当前用户为宁波市级用户，且所属部门为安委会时。如果安委会没有上报到省局，则可以让他退回各部门上报的数据。
				anweiUserFlag="1";
				//查询所有安委办上报的打非治违信息
				List<ZjDfzwPunish> reports  = dfzwPunishFacadeIface.loadCityReport(entity,null);
				
				System.out.println("reports.size="+reports.size());
				for (ZjDfzwPunish punish : entities) {
					//设置初始值为都能退回
					punish.setAnweiBackState("1");
					// 原有
					for (int i = 0; i < reports.size(); i++) {
						if (punish.getReportType() != null) {
								//首先先判断是否存在结束日期相同的打非治违信息
								if(punish.getReportDateEnd().equals(reports.get(i).getReportDateEnd())&& reports.get(i).getState() > SafetyTrouble.NO_REPORT){
									//存在且上报状态为已上报状态，则不能退回
									punish.setAnweiBackState("0");
									continue;
								}
						}
					}
				}

			}
			//查询宁波下的二级区域
			Long ararCode =new Long(330200000000L);
			this.secondAreas=mineFacadeIface.loadAreasByGrade(ararCode, "2");
			
			roleDepic = RoleType1.roleByDepic(fkUser);
			for (ZjDfzwPunish punish : entities)
				punish.setNowUser(fkUser);
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
			entities = dfzwPunishFacadeIface.loadForCity(entity, pagination);
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {// 如果登陆用户是市级用户
				List<ZjDfzwPunish> reports = dfzwPunishFacadeIface
						.loadByCity(fkUser);
				for (ZjDfzwPunish punish : entities) {
					for (int i = 0; i < reports.size(); i++) {
						if (punish.getReportType().intValue() == 1) {
							if (punish.getReportDateBegin().equals(
									reports.get(i).getReportDateBegin())
									&& reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
								punish.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
							}
						} else {
							if (punish.getReportDateEnd().equals(
									reports.get(i).getReportDateEnd())
									&& reports.get(i).getState() > SafetyTrouble.NO_REPORT) {
								punish.setBackObstacle(SafetyTrouble.BACK_OBSTACLE);
							}
						}
					}
				}
			}
			roleDepic = RoleType1.roleByDepic(fkUser);
			for (ZjDfzwPunish punish : entities)
				punish.setNowUser(fkUser);
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
			entity = dfzwPunishFacadeIface.load(entity.getId());
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwPunishFacadeIface.loadDetails(entity.getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadForCity() {
		try {
			entity = dfzwPunishFacadeIface.load(entity.getId());
			if (entity == null) {
				setUrl("listForCity.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwPunishFacadeIface.loadDetails(entity.getId(), null);
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
			dfzwPunishFacadeIface.update(getEntity(), details);
			// punishDetailFacadeIface.deleteZhifaReportDetailsByZhifa(entity
			// .getId());
			// punishDetailFacadeIface.createZhifaReportDetails(details,
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

	/**
	 * 修改其他详细信息
	 * 
	 * @return
	 */
	public String updateForCity() {
		try {
			dfzwPunishFacadeIface.update(getEntity(), details);
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
			dfzwPunishFacadeIface.deleteDetails(entity.getId());
			dfzwPunishFacadeIface.delete(entity);

			FkUser fkUser = new FkUser();
			fkUser.setId(getUserId());
			getEntity().setUser(fkUser);// 设置当前用户为操作用户
			if (entity.isProvince()) {// 设置本部门下级上报的不能退回
				entity = dfzwPunishFacadeIface.load(entity.getId());
				ZjDfzwPunish dfzwpunish = new ZjDfzwPunish();
				FkUser user = userFacadeIface.loadUser(getUserId());
				dfzwpunish.setUser(user);
				dfzwpunish.setProvince(false);
				dfzwpunish.setReportDateBegin(entity.getReportDateBegin());
				dfzwpunish.setReportDateEnd(entity.getReportDateEnd());
				List<ZjDfzwPunish> entitys = dfzwPunishFacadeIface.load(
						dfzwpunish, null);
				for (ZjDfzwPunish punish : entitys) {
					punish.setNotAllowedroolBack(false);
					dfzwPunishFacadeIface.update(punish);
				}
				List<ZjDfzwPunish> entitys2 = dfzwPunishFacadeIface
						.loadForCitySelf(dfzwpunish, null);
				for (ZjDfzwPunish punish : entitys2) {
					punish.setState(0);
					dfzwPunishFacadeIface.update(punish);
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
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			dfzwPunishFacadeIface.deleteDetails(entity.getId());
			dfzwPunishFacadeIface.delete(entity);
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
				setUrl("../punish/list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			ZjDfzwPunish punish = dfzwPunishFacadeIface.load(entity.getId());
			String url = "../punish/list.xhtml";
			if (punish == null) {
				setUrl("../punish/list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			} else if (punish.getState() > SafetyTrouble.COUNTY_REPORT) {
				setUrl(url);
				setMessageKey("report.save.already");
				return MESSAGE;
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			Map map = SafetyTrouble.getState(fkUser, punish.getState());
			if (!(Boolean) map.get("allow")) {
				setUrl(url);
				if (punish.getState() == SafetyTrouble.NO_REPORT) {
					setMessageKey("report.save.fail.noReport");
				} else if (punish.getState() == SafetyTrouble.CITY_REPORT) {
					setMessageKey("report.save.fail.cityReport");
				} else if (punish.getState() == SafetyTrouble.COUNTY_REPORT) {
					setMessageKey("report.save.fail.countyReport");
				}
				return MESSAGE;
			}
			punish.setState(Integer.parseInt(map.get("state").toString()));
			punish.setModifyTime(new Date());
			dfzwPunishFacadeIface.update(punish);
			// 市安委上报同时发送数据到省隐患排查
			if (punish.getState() == SafetyTrouble.PROVINCE_REPORT) {
				String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE,
						entity.getUser().getFkRoles());
				if ("anwei".equals(roleDepic)) {
					axis2FacadeIface.sendDataDfzwPunishByOMElement(punish,
							getUserId());
				}
			}

			// List<ZjDfzwPunish> reports =
			// dfzwPunishFacadeIface.loadByCity(fkUser);
			// for (ZjDfzwPunish report : reports) {
			// if
			// (punish.getReportDateBegin().after(report.getReportDateBegin()))
			// {
			// if (report.getState() == SafetyTrouble.NO_REPORT) {//
			// 如果没有上报，则记录当前时间为上报时间
			// report.setModifyTime(new Date());
			// report
			// .setState(RoleType1.isRoleByDepic(RoleType1.CITY,
			// fkUser.getFkRoles()) ? SafetyTrouble.CITY_REPORT
			// : SafetyTrouble.COUNTY_REPORT);
			// dfzwPunishFacadeIface.update(report);
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
			entity = dfzwPunishFacadeIface.load(entity.getId());
			entity.setView(true);
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwPunishFacadeIface.loadDetails(entity.getId(), null);
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
				entities = dfzwPunishFacadeIface.loadCityReport(entity, pagination);
			}else{
				hasAjjLookRole="0";
				//其他角色时，查询条件不变
				entities = dfzwPunishFacadeIface.load(entity, pagination);
			}
			
			int isReport = 0;
			for (ZjDfzwPunish punish : entities) {
				punish.setNowUser(fkUser);
				isReport = 0;
				if (!punish.getRoleDepic().equals("anwei")) {
					isReport = dfzwPunishFacadeIface.isReport("anwei", punish.getReportDateEnd());
					System.out.println("isReport: "+isReport);
					if (isReport == 1) {
						punish.setReport(true);
					}
				}
			}
		} catch (Exception e) {
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
		boolean isPrint = getEntity().isPrint();
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setUser(fkUser);
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwPunishFacadeIface.load(entity.getId());
			}

			/*
			 * @author seak.lv @depiction:判断下一级的区域用户是否已经都上报
			 */
			if (!dfzwPunishFacadeIface.is_all_reported("county",
					getUserDetail().getSecondArea(), entity)) {
				setMessageKey("county.is.not.all.report");
				return MESSAGE;
			}
			details = dfzwPunishFacadeIface.loadDetailsByUser(entity);
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
			entity = dfzwPunishFacadeIface.load(entity.getId());
			entities = dfzwPunishFacadeIface.load(entity, null);
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
		setUrl("loadProvinceReports.xhtml?mineReport.type=6");
		return SUCCESS;
	}

	/**
	 * 查看省级统计列表
	 * 
	 * @return
	 */
	public String loadProvinceReports() {
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

	public String loadDetailsByDfzw() {
		boolean isPrint = getEntity().isPrint();
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setUser(fkUser);
			Boolean isProvince = entity.isProvince();
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwPunishFacadeIface.load(entity.getId());
			}
			entity.setProvince(isProvince);
			details = dfzwPunishFacadeIface.loadDetailsByDfzwPunish(entity);
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
			// if (!punishFacadeIface.is_all_reported("city", getUserDetail()
			// .getFirstArea(), entity)) {
			// setMessageKey("city.is.not.all.report");
			// return MESSAGE;
			// }
			// }
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwPunishFacadeIface.load(entity.getId());
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setQueryReportMonth(true);
			entity.setUser(fkUser);
			entity.setProvince(true);
			details = dfzwPunishFacadeIface.loadDetailsForProvince(entity);
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
			details = dfzwPunishFacadeIface.loadDetailsForCityInit(entity);

			isAllReport = dfzwPunishFacadeIface.getIsAllReport();
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
			entity = dfzwPunishFacadeIface.load(entity.getId());
			if (entity == null) {
				setUrl("list.xhtml");
				setMessageKey("noData");
				return MESSAGE;// 转向到提示页面
			}
			details = dfzwPunishFacadeIface.loadDetails(entity.getId(), null);
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
			setUrl("../punish/list.xhtml");
			setMessageKey("noData");
			return MESSAGE;
		}
		try {
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			ZjDfzwPunish punish = dfzwPunishFacadeIface.load(entity.getId());
			if (punish == null) {
				setUrl("../punish/list.xhtml");
				setMessageKey("noData");
				return MESSAGE;
			}
			if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
				List<ZjDfzwPunish> reports = dfzwPunishFacadeIface
						.loadByCity(fkUser);
				for (ZjDfzwPunish report : reports)
					if (report.getReportDateEnd().equals(
							punish.getReportDateEnd()))
						if (report.getState() > SafetyTrouble.NO_REPORT) {
							setUrl("");
							setMessageKey("rollback.fail.city.cityIsReport");
							return MESSAGE;
						}
			}
			List<ZjDfzwPunish> countys = dfzwPunishFacadeIface
					.loadByCity(punish.getUser());
			for (ZjDfzwPunish report : countys) {
				if (report.getState() == ((RoleType1.isRoleByDepic(
						RoleType1.CITY, fkUser.getFkRoles())) ? SafetyTrouble.COUNTY_REPORT
						: SafetyTrouble.CITY_REPORT)) {
					//add by huangjl 此处添加非空判断
					if (report.getReportDateEnd() != null && punish.getReportDateEnd() != null) {
						if (punish.getReportDateEnd().before(
								report.getReportDateEnd())) {
							setUrl("");
							setMessageKey(RoleType1.isRoleByDepic(RoleType1.CITY,
									fkUser.getFkRoles()) ? "rollback.fail.city.countyAfterReport"
									: "rollback.fail.province.cityAfterReport");
							return MESSAGE;
						}
					}
					
				}
			}
			punish.setModifyTime(new Date());
			String content = "";
			
			if("0".equals(anweiBackFlag)){
				if (punish.getState() == SafetyTrouble.CITY_REPORT) {
					punish.setState(0);
					content = "省级退回";
				} else {
					punish.setState(punish.getState() - 1);
					content = "市级退回";
				}
			}else{
				//为1时表示从安委会退回，state设置为0，其他条件不变化
				punish.setState(0);
				content = "省级退回";
				
			}
			
			
			dfzwPunishFacadeIface.update(punish);
			ZjIdea idea = new ZjIdea(userFacadeIface.loadUser(getUserId()),
					content, 3l, punish.getId());
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
			// if (!punishFacadeIface.is_all_reported("city", getUserDetail()
			// .getFirstArea(), entity)) {
			// setMessageKey("city.is.not.all.report");
			// return MESSAGE;
			// }
			if (entity.getId() != -1L) {// 第二次加载，之后修改
				entity = dfzwPunishFacadeIface.load(entity.getId());
			}
			FkUser fkUser = userFacadeIface.loadUser(getUserId());
			entity.setQueryReportMonth(true);
			entity.setUser(fkUser);
			details = dfzwPunishFacadeIface.loadDetailsForProvince(entity);
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
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser()
					.getFkRoles())) {
				areas = mineFacadeIface.loadAreas(0L, temp);
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser()
					.getFkRoles())) {
				Long area_id = entity.getUser().getFkUserInfo().getSecondArea();
				if (area_id != null && !"".equals(area_id)) {
					areas = dfzwPunishFacadeIface.loadCityAreas(area_id);
				}
			}
			entities = dfzwPunishFacadeIface.loadCountyReports(entity,
					pagination);
			for (ZjDfzwPunish punish : entities)
				punish.setNowUser(fkUser);
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
			allowCreate = dfzwPunishFacadeIface.checkAllowCreate(getEntity());
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
				allowCreate = dfzwPunishFacadeIface
						.checkAllowCreate2(getEntity());
			} else {
				allowCreate = dfzwPunishFacadeIface
						.checkAllowCreate1(getEntity());
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
			allowCreate = dfzwPunishFacadeIface.checkAllowCreat3(getEntity());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public List<State> getTypes() {
		return SafetyTrouble.DFZW_PUNISH_TYPE_MAP;
	}

	public ZjDfzwPunish getEntity() {
		if (entity == null) {
			entity = new ZjDfzwPunish();
		}
		return entity;
	}

	public void setEntity(ZjDfzwPunish entity) {
		this.entity = entity;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<ZjDfzwPunish> getEntities() {
		return entities;
	}

	public void setEntities(List<ZjDfzwPunish> entities) {
		this.entities = entities;
	}

	public List<ZjDfzwPunishDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ZjDfzwPunishDetail> details) {
		this.details = details;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

	public void setDfzwPunishFacadeIface(
			DfzwPunishFacadeIface dfzwPunishFacadeIface) {
		this.dfzwPunishFacadeIface = dfzwPunishFacadeIface;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public void setIdeaFacadeIface(IdeaFacadeIface ideaFacadeIface) {
		this.ideaFacadeIface = ideaFacadeIface;
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

	public void setAxis2FacadeIface(Axis2FacadeIface axis2FacadeIface) {
		this.axis2FacadeIface = axis2FacadeIface;
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

}
