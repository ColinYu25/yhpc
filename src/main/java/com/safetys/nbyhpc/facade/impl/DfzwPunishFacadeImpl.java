package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.ProjectionsProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunishDetail;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwPunishDetailPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwPunishPersistenceIface;
import com.safetys.nbyhpc.facade.iface.DfzwPunishFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.State;
import com.safetys.nbyhpc.web.axis2.model.DfzwPunishDetail;

public class DfzwPunishFacadeImpl implements DfzwPunishFacadeIface {

	public int isAllReport = 0; // 已上报个数 0未上报完 1已上报完

	private DfzwPunishPersistenceIface dfzwPunishPersistenceIface;

	private DfzwPunishDetailPersistenceIface dfzwPunishDetailPersistenceIface; 

	private AreaPersistenceIface areaPersistenceIface;

	public void create(ZjDfzwPunish entity, List<ZjDfzwPunishDetail> details) throws Exception {
		dfzwPunishPersistenceIface.create(entity);
		if (details != null && details.size() > 0) {
			for (ZjDfzwPunishDetail detail : details) {
				detail.setDfzwPunish(entity);
				fullZeroForDetail(detail);
				if (detail.getType().toString().trim().equals("01-coal mine")){
					detail.setCheckTeam(0);
					detail.setCheckPerson(0);
					detail.setCompanyNum(0);
					detail.setWarn(0);
					detail.setOrderRectify(0);
					detail.setConfiscate(0);
					detail.setStopProduct(0);
					detail.setClose(0);
					detail.setTempDetain(0);
					detail.setAdministrativeDetain(0);
					detail.setCriminalResponsibility(0);
					detail.setPenalty(0f);
				}
				dfzwPunishDetailPersistenceIface.create(detail);
			}
		}
	}

	public void update(ZjDfzwPunish entity, List<ZjDfzwPunishDetail> details) throws Exception {
		ZjDfzwPunish _entity = load(entity.getId());
		entity.setState(_entity.getState());
		entity.setModifyTime(_entity.getModifyTime());
		deleteDetails(_entity.getId());
		if (details != null && details.size() > 0) {
			for (ZjDfzwPunishDetail detail : details) {
				detail.setDfzwPunish(_entity);
				fullZeroForDetail(detail);
				
				if (detail.getType().toString().trim().equals("01-coal mine")){
					detail.setCheckTeam(0);
					detail.setCheckPerson(0);
					detail.setCompanyNum(0);
					detail.setWarn(0);
					detail.setOrderRectify(0);
					detail.setConfiscate(0);
					detail.setStopProduct(0);
					detail.setClose(0);
					detail.setTempDetain(0);
					detail.setAdministrativeDetain(0);
					detail.setCriminalResponsibility(0);
					detail.setPenalty(0f);
				}
				dfzwPunishDetailPersistenceIface.create(detail);
			}
		}
		dfzwPunishPersistenceIface.merge(entity);
	}

	/**
	 * 对子表的数字字段补充默认值0
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @param detail
	 */
	private void fullZeroForDetail(ZjDfzwPunishDetail detail) {
		if (detail == null) {
			detail = new ZjDfzwPunishDetail();
		}
		detail.setCheckTeam(detail.getCheckTeam() == null ? 0 : detail.getCheckTeam());
		detail.setCheckPerson(detail.getCheckPerson() == null ? 0 : detail.getCheckPerson());
		detail.setCompanyNum(detail.getCompanyNum() == null ? 0 : detail.getCompanyNum());
		detail.setWarn(detail.getWarn() == null ? 0 : detail.getWarn());
		detail.setOrderRectify(detail.getOrderRectify() == null ? 0 : detail.getOrderRectify());
		detail.setConfiscate(detail.getConfiscate() == null ? 0 : detail.getConfiscate());
		detail.setStopProduct(detail.getStopProduct() == null ? 0 : detail.getStopProduct());
		detail.setTempDetain(detail.getTempDetain() == null ? 0 : detail.getTempDetain());
		detail.setClose(detail.getClose() == null ? 0 : detail.getClose());
		detail.setAdministrativeDetain(detail.getAdministrativeDetain() == null ? 0 : detail.getAdministrativeDetain());
		detail.setCriminalResponsibility(detail.getCriminalResponsibility() == null ? 0 : detail.getCriminalResponsibility());
		detail.setPenalty(detail.getPenalty() == null ? 0 : detail.getPenalty());
	}

	public List<ZjDfzwPunish> loadByUser(ZjDfzwPunish entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()));
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea())).add(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea())).add(RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0L)));
			} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea())).add(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea())).add(RestrictionsProxy.eq("fui.thirdArea", entity.getUser().getFkUserInfo().getThirdArea()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("zdp.modifyTime"));
		return dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
	}

	public List<ZjDfzwPunish> load(ZjDfzwPunish entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		System.out.println("-------------------------------------ZjDfzwPunish---------------------------------------------------");
		if (entity != null) {
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
				System.out.println("PROVINCE");
				if (entity.isProvince()) {// 查看省级统计列表,不显示市级和县级
					System.out.println("=####");
					if (entity.isAnweiCheck()) {// 市级安委汇总
						detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
						String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
						detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.eq("zdp.roleDepic", roleDepic)));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
								RestrictionsProxy.eq("zdp.state", SafetyTrouble.PROVINCE_REPORT)));
					} else {
						detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
						String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
								RestrictionsProxy.or(RestrictionsProxy.eq("zdp.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdp.state", SafetyTrouble.PROVINCE_REPORT))));
					}
				} else {System.out.println("====");
					
					detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
					String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
					if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录,市级部门汇总
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
								RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT)));
					} else {
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
								RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT)));
					}
				}
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
				System.out.println("CITY");
				detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
				String roleDepic = RoleType1.roleDepic(RoleType1.CITY, entity.getUser().getFkRoles());
				if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录
					detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级各个部门只能查看自己填报的上报或者未上报状态的记录
							RestrictionsProxy.or(RestrictionsProxy.eq("zdp.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT))));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
							RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT)));
				}
			}

			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
			}

			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
			}
			
			if (entity.getSecondArea()!=null&&entity.getSecondArea()>0) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getSecondArea()));
			}

			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		return dfzwPunishPersistenceIface.load(detachedCriteriaProxy, pagination);
	}
	public List<ZjDfzwPunish> loadCityReport(ZjDfzwPunish entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		System.out.println("-------------------------------------ZjDfzwPunish---------------------------------------------------");
		if (entity != null) {
//			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
//				System.out.println("PROVINCE");
//				if (entity.isProvince()) {// 查看省级统计列表,不显示市级和县级
//					System.out.println("=####");
//					if (entity.isAnweiCheck()) {// 市级安委汇总
//						detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
//						String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
//						detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.eq("zdp.roleDepic", roleDepic)));
//						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
//								RestrictionsProxy.eq("zdp.state", SafetyTrouble.PROVINCE_REPORT)));
//					} else {
						detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
						//String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
						//此处默认只查询安委办的打非治违处罚汇总单
						String roleDepic = "anwei";
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
								RestrictionsProxy.or(RestrictionsProxy.eq("zdp.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdp.state", SafetyTrouble.PROVINCE_REPORT))));
//					}
//				} else {System.out.println("====");
//					
//					detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
//					String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
//					if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录,市级部门汇总
//						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
//						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
//								RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT)));
//					} else {
//						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
//								RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT)));
//					}
//				}
//			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
//				System.out.println("CITY");
//				detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
//				String roleDepic = RoleType1.roleDepic(RoleType1.CITY, entity.getUser().getFkRoles());
//				if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
//					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级各个部门只能查看自己填报的上报或者未上报状态的记录
//							RestrictionsProxy.or(RestrictionsProxy.eq("zdp.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT))));
//				} else {
//					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
//							RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT)));
//				}
//			}

			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
			}

			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
			}
			
			if (entity.getSecondArea()!=null&&entity.getSecondArea()>0) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getSecondArea()));
			}

			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dfzwPunishPersistenceIface.load(detachedCriteriaProxy, pagination);
	}
	
	/**
	 * 市级填报(各个部门只能查看各个市级部门填报的数据)
	 */
	public List<ZjDfzwPunish> loadForCity(ZjDfzwPunish entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", true));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
			}
			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
			}

			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dfzwPunishPersistenceIface.load(detachedCriteriaProxy, pagination);
	}

	/**
	 * 根据用户区域获取记录集合
	 * 
	 * @author lihu
	 * 
	 * @param fkUser
	 * @param type
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjDfzwPunish> loadByCity(FkUser fkUser) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", fkUser.getFkUserInfo().getFirstArea()));

		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add((RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", fkUser.getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", fkUser.getFkUserInfo().getSecondArea()))));
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("fui.thirdArea", 0L), RestrictionsProxy.isNull("fui.thirdArea")));
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", fkUser.getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", fkUser.getFkUserInfo().getSecondArea())), RestrictionsProxy.eq("fui.thirdArea", fkUser.getFkUserInfo().getThirdArea())));
		}
		List<ZjDfzwPunish> reports = dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
		return reports;
	}

	/**
	 * 
	 * @Description:获取上级部门上报情况
	 * @author:liulj
	 * @time:2014-5-14
	 */
	public int isReport(String roleDepic, Date reportDate) throws Exception {
		int isReport = 0;
		String beginDate = "";
		System.out.println("roleDepic: " + roleDepic);
		if (reportDate != null) {
			String[] ab = reportDate.toString().split("-");
			beginDate = ab[0] + "-" + ab[1] + "-01";
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdr");
			if (roleDepic.equals("anwei")) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.report_date_end  between  to_date('" + beginDate + "','yyyy-MM-dd') and  to_date('" + reportDate + "','yyyy-MM-dd')  "));

			} else {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.state", 3));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.report_date_end  between  to_date('" + beginDate + "','yyyy-MM-dd') and  to_date('" + reportDate + "','yyyy-MM-dd')  "));

			}
			List<ZjDfzwPunish> details = dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
			if (details.size() > 0) {
				isReport = 1;
			}
		}
		return isReport;
	}

	public List<ZjDfzwPunishDetail> loadDetails(Long id, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunishDetail.class, "zdpd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdpd.dfzwPunish.id", id));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdpd.type"));// 排序
		List<ZjDfzwPunishDetail> details = dfzwPunishDetailPersistenceIface.load(detachedCriteriaProxy, pagination);
		return details;
	}

	public void deleteDetails(Long id) throws Exception {
		List<ZjDfzwPunishDetail> details = loadDetails(id, null);
		for (ZjDfzwPunishDetail detail : details) {
			deleteDetail(detail);
		}
	}

	public void update(ZjDfzwPunish entity) throws Exception {
		ZjDfzwPunish _entity = load(entity.getId());
		entity.setState(_entity.getState());
		entity.setModifyTime(_entity.getModifyTime());
		dfzwPunishPersistenceIface.merge(entity);
	}

	/**
	 * @depiction:判断下一级的区域用户是否已经都上报
	 * @param areaType
	 *            判断哪一级是否上报
	 * @param areaType
	 *            当前用户的行政区划代码
	 */
	public boolean is_all_reported(String areaType, Long areaCode, ZjDfzwPunish entity) {
		/**
		 * 周报表不需要强制要求下级区域全部上报，本级用户才能打开汇总报表（因时间较短）
		 */
		// String hql = "";
		// if ("county".equals(areaType)) {
		// hql =
		// "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
		// + areaCode
		// +
		// ") and area_code not in (select thirdArea from FkUserInfo where id in (select userId from ZjDfzwPunish where state=1 and deleted=0 and reportMonth='"
		// + entity.getReportMonth() + "') and deleted=0  and secondArea=" +
		// areaCode + ")";
		// } else if ("city".equals(areaType)) {
		// hql =
		// "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
		// + areaCode
		// +
		// ") and area_code not in (select secondArea from FkUserInfo where id in (select userId from ZjDfzwPunish where state=2 and deleted=0 and reportMonth='"
		// + entity.getReportMonth() + "') and deleted=0  and firstArea=" +
		// areaCode + ")";
		// }
		// List<FkArea> fkAreas = areaPersistenceIface.loadAreasByHql(hql);
		// if (fkAreas != null && fkAreas.size() > 0) {
		// return false;
		// }
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<ZjDfzwPunishDetail> loadDetailsByUser(ZjDfzwPunish entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunishDetail.class, "zdpd");
		detachedCriteriaProxy.createCriteria("zdpd.dfzwPunish", "zdp").createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.sum("zdpd.checkTeam")).add(ProjectionsProxy.sum("zdpd.checkPerson")).add(ProjectionsProxy.sum("zdpd.companyNum")).add(ProjectionsProxy.sum("zdpd.warn")).add(ProjectionsProxy.sum("zdpd.orderRectify")).add(ProjectionsProxy.sum("zdpd.confiscate")).add(ProjectionsProxy.sum("zdpd.stopProduct")).add(ProjectionsProxy.sum("zdpd.tempDetain")).add(ProjectionsProxy.sum("zdpd.close")).add(ProjectionsProxy.sum("zdpd.administrativeDetain")).add(ProjectionsProxy.sum("zdpd.criminalResponsibility")).add(ProjectionsProxy.sum("zdpd.penalty")).add(ProjectionsProxy.groupProperty("zdpd.type")));
		if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {// 设置市级用户显示列表
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdp.state", SafetyTrouble.COUNTY_REPORT), RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()))));
		} else if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {

		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdpd.type"));// 排序
		List<ZjDfzwPunishDetail> objects = dfzwPunishDetailPersistenceIface.load(detachedCriteriaProxy, null);
		Iterator iterator = objects.iterator();
		List<ZjDfzwPunishDetail> details = new ArrayList<ZjDfzwPunishDetail>();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ZjDfzwPunishDetail detail = new ZjDfzwPunishDetail((obj[12].toString()), Integer.parseInt((obj[0].toString())), Integer.parseInt((obj[1].toString())), Integer.parseInt((obj[2].toString())), Integer.parseInt((obj[3].toString())), Integer.parseInt((obj[4].toString())), Integer.parseInt((obj[5].toString())), Integer.parseInt((obj[6].toString())), Integer.parseInt((obj[7].toString())), Integer.parseInt((obj[8].toString())), Integer.parseInt((obj[9].toString())), Integer.parseInt((obj[10].toString())), Float.parseFloat((obj[11].toString())));
			details.add(detail);
		}
		if (details.size() == 0) {
			for (State state : SafetyTrouble.DFZW_PUNISH_TYPE_MAP) {
				ZjDfzwPunishDetail detail = new ZjDfzwPunishDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0f);
				details.add(detail);
			}
		}
		return details;
	}

	public List<DfzwPunishDetail> loadDetailsForAxis2(Long areaCode, ZjDfzwPunish entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunishDetail.class, "zdpd");
		detachedCriteriaProxy.createCriteria("zdpd.dfzwPunish", "zdp").createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表

		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.sum("zdpd.checkTeam")).add(ProjectionsProxy.sum("zdpd.checkPerson")).add(ProjectionsProxy.sum("zdpd.companyNum")).add(ProjectionsProxy.sum("zdpd.warn")).add(ProjectionsProxy.sum("zdpd.orderRectify")).add(ProjectionsProxy.sum("zdpd.confiscate")).add(ProjectionsProxy.sum("zdpd.stopProduct")).add(ProjectionsProxy.sum("zdpd.tempDetain")).add(ProjectionsProxy.sum("zdpd.close")).add(ProjectionsProxy.sum("zdpd.administrativeDetain")).add(ProjectionsProxy.sum("zdpd.criminalResponsibility")).add(ProjectionsProxy.sum("zdpd.penalty")).add(ProjectionsProxy.groupProperty("zdpd.type")));

		// detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdp.state",
		// SafetyTrouble.CITY_REPORT),
		// RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea",
		// Nbyhpc.AREA_CODE),
		// RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea",
		// areaCode),
		// RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"),RestrictionsProxy.eq("fui.thirdArea",
		// 0l)))
		// )));
		System.out.println("areaCode: " + areaCode);

		if (areaCode == 330200000000l) { // 市级数据
			System.out.println("市级数据");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.state", SafetyTrouble.PROVINCE_REPORT));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", true));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" fui3_.second_area  is null"));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.notAllowedroolBack", false));

		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdp.state", SafetyTrouble.CITY_REPORT), RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", Nbyhpc.AREA_CODE), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", areaCode), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l))))));

			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.notAllowedroolBack", true));// 必须是市级部门确认上报的数据
		}

		// liulj 发送之前先判断其上级部门是否已上报,若没上报,则不 发送(如县级或本级已上报,但市级未报的不发送)
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists (select * from  zj_dfzw_punish zdr2_  where zdr2_.state = 3   and zdr2_.is_city_create = 0  and  zdr2_.role_depic=zdp1_.role_depic  and zdr2_.REPORT_DATE_BEGIN = to_date('" + entity.getReportDateBegin() + "', 'yyyy-MM-dd')   and zdr2_.REPORT_DATE_END = to_date('" + entity.getReportDateEnd() + "', 'yyyy-MM-dd') )"));

		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdpd.type"));// 排序
		List<ZjDfzwPunishDetail> objects = dfzwPunishDetailPersistenceIface.load(detachedCriteriaProxy, null);
		Iterator iterator = objects.iterator();
		List<DfzwPunishDetail> details = new ArrayList<DfzwPunishDetail>();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			DfzwPunishDetail detail = new DfzwPunishDetail((obj[12].toString()), Integer.parseInt((obj[0].toString())), Integer.parseInt((obj[1].toString())), Integer.parseInt((obj[2].toString())), Integer.parseInt((obj[3].toString())), Integer.parseInt((obj[4].toString())), Integer.parseInt((obj[5].toString())), Integer.parseInt((obj[6].toString())), Integer.parseInt((obj[7].toString())), Integer.parseInt((obj[8].toString())), Integer.parseInt((obj[9].toString())), Integer.parseInt((obj[10].toString())), Double.parseDouble((obj[11].toString())));
			details.add(detail);
		}
		if (details.size() == 0) {
			for (State state : SafetyTrouble.DFZW_PUNISH_TYPE_MAP) {
				DfzwPunishDetail detail = new DfzwPunishDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0d);
				details.add(detail);
			}
		}
		return details;
	}

	public List<ZjDfzwPunishDetail> loadDetailsByDfzwPunish(ZjDfzwPunish entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunishDetail.class, "zdpd");
		detachedCriteriaProxy.createCriteria("zdpd.dfzwPunish", "zdp").createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.id", entity.getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdpd.type"));// 排序
		List<ZjDfzwPunishDetail> details = dfzwPunishDetailPersistenceIface.load(detachedCriteriaProxy, null);
		if (details.size() == 0) {
			for (State state : SafetyTrouble.DFZW_PUNISH_TYPE_MAP) {
				ZjDfzwPunishDetail detail = new ZjDfzwPunishDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0f);
				details.add(detail);
			}
		}
		return details;
	}

	public List<ZjDfzwPunishDetail> loadDetailsByDfzwPunish1(ZjDfzwPunish entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunishDetail.class, "zdpd");
		detachedCriteriaProxy.createCriteria("zdpd.dfzwPunish", "zdp").createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.id", entity.getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdpd.type"));// 排序
		List<ZjDfzwPunishDetail> details = dfzwPunishDetailPersistenceIface.load(detachedCriteriaProxy, null);
		return details;
	}

	public List<ZjDfzwPunishDetail> loadDetailsForProvince(ZjDfzwPunish entity) throws Exception {
		List<ZjDfzwPunish> entitys = load(entity, null);
		List<ZjDfzwPunishDetail> details = new ArrayList<ZjDfzwPunishDetail>();
		List<List<ZjDfzwPunishDetail>> detailsTotal = new ArrayList<List<ZjDfzwPunishDetail>>();
		for (ZjDfzwPunish punish : entitys) {
			List<ZjDfzwPunishDetail> list_t = this.loadDetailsByDfzwPunish1(punish);
			if (null != list_t && list_t.size() > 0) {
				detailsTotal.add(list_t);
			}
		}
		for (State state : SafetyTrouble.DFZW_PUNISH_TYPE_MAP) {
			ZjDfzwPunishDetail detail = new ZjDfzwPunishDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0f);
			details.add(detail);
		}
		for (int i = 0; i < detailsTotal.size(); i++) {// 循环到市级
			for (ZjDfzwPunishDetail detail : detailsTotal.get(i)) {// 循环到行业
				for (ZjDfzwPunishDetail detai : details) {// 循环对比行业
					if (detai.getType().equals(detail.getType())) {// 对比
						detai.addDfzwPunishDetail(detail.getCheckTeam(), detail.getCheckPerson(), detail.getCompanyNum(), detail.getWarn(), detail.getOrderRectify(), detail.getConfiscate(), detail.getStopProduct(), detail.getTempDetain(), detail.getClose(), detail.getAdministrativeDetain(), detail.getCriminalResponsibility(), detail.getPenalty());// 累加
					}
				}
			}
		}
		return details;
	}

	public List<ZjDfzwPunishDetail> loadDetailsForCityInit(ZjDfzwPunish entity) throws Exception {
		List<ZjDfzwPunish> entitys = load(entity, null);
		List<ZjDfzwPunish> entitys2 = loadForCitySelf(entity, null);// 获取本周本部门填报的数据
		if (null != entitys2 && entitys2.size() > 0) {
			if (null != entitys && entitys.size() > 0) { 
			} else {
				entitys = new ArrayList<ZjDfzwPunish>();
			}
			entitys.add(entitys2.get(0));
		}
		List<ZjDfzwPunishDetail> details = new ArrayList<ZjDfzwPunishDetail>();
		List<List<ZjDfzwPunishDetail>> detailsTotal = new ArrayList<List<ZjDfzwPunishDetail>>();
		for (ZjDfzwPunish punish : entitys) {
			List<ZjDfzwPunishDetail> list_t = this.loadDetailsByDfzwPunish1(punish);
			if (null != list_t && list_t.size() > 0) {
				detailsTotal.add(list_t);
			}
		}
		for (State state : SafetyTrouble.DFZW_PUNISH_TYPE_MAP) {
			ZjDfzwPunishDetail detail = new ZjDfzwPunishDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0f);
			details.add(detail);
		}

		// liulj 判断角色 anwei 或市级用户
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());

		if (roleDepic.equals("anwei") && detailsTotal.size() < 19) { // 19个部门
			isAllReport = 0;
		} else if (!roleDepic.equals("anwei") && detailsTotal.size() < 18) { // 18区域
			isAllReport = 1;
		} else {
			isAllReport = 2;
		}

		for (int i = 0; i < detailsTotal.size(); i++) {// 循环到市级
			for (ZjDfzwPunishDetail detail : detailsTotal.get(i)) {// 循环到行业
				for (ZjDfzwPunishDetail detai : details) {// 循环对比行业
					if (detai.getType().equals(detail.getType())) {// 对比
						detai.addDfzwPunishDetail(detail.getCheckTeam(), detail.getCheckPerson(), detail.getCompanyNum(), detail.getWarn(), detail.getOrderRectify(), detail.getConfiscate(), detail.getStopProduct(), detail.getTempDetain(), detail.getClose(), detail.getAdministrativeDetain(), detail.getCriminalResponsibility(), detail.getPenalty());// 累加
					}
				}
			}
		}
		return details;
	}

	public List<ZjDfzwPunish> loadForCitySelf(ZjDfzwPunish entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", true));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
			}
			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dfzwPunishPersistenceIface.load(detachedCriteriaProxy, pagination);
	}

	/**
	 * 市级汇总之后需要设置县级上报的市级不能操作退回
	 */
	public void loadDetailsSetRollback(ZjDfzwPunish entity) throws Exception {
		List<ZjDfzwPunish> entitys = load(entity, null);
		for (ZjDfzwPunish punish : entitys) {
			punish.setNotAllowedroolBack(true);
			dfzwPunishPersistenceIface.update(punish);
		}
	}

	/**
	 * 根据区域代码查询区域名称
	 * 
	 * @param fatherAreaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<FkArea> loadCityAreas(Long cityAreaCode) throws ApplicationAccessException {
		String hql = "SELECT areaCode,areaName FROM FkArea fk WHERE areaCode=" + cityAreaCode;
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

	public List<ZjDfzwPunish> loadCountyReports(ZjDfzwPunish entity, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		List<FkArea> areas = null;
		if (entity != null) {
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("zdp.user.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdp.state", SafetyTrouble.COUNTY_REPORT), RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea())));// 设置省级用户显示列表
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("zdp.user.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq("zdp.state", SafetyTrouble.COUNTY_REPORT), RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea())), RestrictionsProxy.eq("zdp.user", entity.getUser())));// 设置市级用户显示列表
				detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("fui.thirdArea"));// 过滤市级用户
			}
			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdp.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdp.reportDateBegin", entity.getReportDateEnd()));
				}
			}
			Long negativeOne = -1L;
			if (entity.getSecondArea() != null && !negativeOne.equals(entity.getSecondArea())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getSecondArea()));
			}
		}

		detachedCriteriaProxy.addOrder(OrderProxy.desc("zdp.modifyTime"));// 根据修改时间排序
		return switchCountyReportList(dfzwPunishPersistenceIface.load(detachedCriteriaProxy, pagination), areas);
	}

	/**
	 * 根据父区域的区域代码查询子区域集合
	 * 
	 * @param fatherAreaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadChildAreas(Long fatherAreaCode) throws ApplicationAccessException {
		String hql = "SELECT userCompany FROM FkUserInfo WHERE thirdArea in (SELECT areaCode FROM FkArea fk WHERE fk.fatherId = (SELECT id FROM FkArea WHERE areaCode=" + fatherAreaCode + ") AND deleted=0) AND deleted=0 AND secondArea=" + fatherAreaCode;
		return areaPersistenceIface.loadAreasByHql(hql);
	}

	private List<ZjDfzwPunish> switchCountyReportList(List<ZjDfzwPunish> entities, List<FkArea> areas) {
		if (areas == null || areas.size() == 0) {
			return entities;
		}
		String[] existCompany = new String[areas.size()];
		ZjDfzwPunish punish = new ZjDfzwPunish();
		FkUser fu = new FkUser();
		FkUserInfo fui = new FkUserInfo();
		int k = 0;
		try {
			if (entities.size() == 0) {
				for (int j = 0; j < areas.size(); j++) {
					punish = new ZjDfzwPunish();
					fui = new FkUserInfo();
					fu = new FkUser();
					fui.setUserCompany(String.valueOf((areas.get(j))));
					fu.setFkUserInfo(fui);
					punish.setUser(fu);
					// TODO AA
					// punish.setReportMonth("");
					entities.add(punish);
				}
			} else {
				for (int j = 0; j < areas.size(); j++) {
					for (int i = 0; i < entities.size(); i++) {
						if (entities.get(i).getUser().getFkUserInfo().getUserCompany() != null && areas.get(j) != null) {
							if (entities.get(i).getUser().getFkUserInfo().getUserCompany().equals(areas.get(j))) {
								break;
							} else {
								if (existCompany[0] == null) {
									punish = new ZjDfzwPunish();
									fui = new FkUserInfo();
									fu = new FkUser();
									fui.setUserCompany(String.valueOf((areas.get(j))));
									fu.setFkUserInfo(fui);
									punish.setUser(fu);
									// punish.setReportMonth("");
									entities.add(punish);
									existCompany[k] = String.valueOf((areas.get(j)));
									k++;
								} else {
									for (int l = 0; l < existCompany.length; l++) {
										if (existCompany[l] != null) {
											if (!existCompany[l].equals(String.valueOf((areas.get(j)))) && existCompany[l + 1] == null) {
												punish = new ZjDfzwPunish();
												fui = new FkUserInfo();
												fu = new FkUser();
												fui.setUserCompany(String.valueOf((areas.get(j))));
												fu.setFkUserInfo(fui);
												punish.setUser(fu);
												// punish.setReportMonth("");
												entities.add(punish);
												existCompany[k] = String.valueOf((areas.get(j)));
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
			for (int i = 0; i < entities.size(); i++) {
				for (int j = 0; j < entities.size(); j++) {
					if (entities.get(i).getUser().getFkUserInfo().getUserCompany() != null) {
						if (entities.get(i).getUser().getFkUserInfo().getUserCompany().equals(entities.get(j).getUser().getFkUserInfo().getUserCompany()) && i != j) {
							if (i > j) {
								entities.remove(i);
							} else {
								entities.remove(j);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entities;
	}

	public boolean checkAllowCreate(ZjDfzwPunish entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		String roleLevel = RoleType1.roleLevelByUser(entity.getUser());
		if (roleLevel != null && "PROVINCE".equals(roleLevel)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l)));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", RoleType1.roleDepic(RoleType1.CITY, entity.getUser().getFkRoles())));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)));
		}

		if (entity.getReportType() != null && entity.getReportType().intValue() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd",
		// entity.getReportDateEnd()));
		List<ZjDfzwPunish> entities = dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
		if (entities != null && entities.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 市级各个部门汇总验证
	 * 
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreate1(ZjDfzwPunish entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
		if (entity.getReportType() != null && entity.getReportType().intValue() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd",
		// entity.getReportDateEnd()));
		List<ZjDfzwPunish> entities = dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
		if (entities != null && entities.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 市级安委汇总验证
	 * 
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreate2(ZjDfzwPunish entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", false));
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
		if (entity.getReportType() != null && entity.getReportType().intValue() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd",
		// entity.getReportDateEnd()));
		List<ZjDfzwPunish> entities = dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
		if (entities != null && entities.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 市级安委汇总验证
	 * 
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreat3(ZjDfzwPunish entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwPunish.class, "zdp");
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.cityCreate", true));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdp.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
			if (entity.getReportType() != null && entity.getReportType().intValue() == 1) {
				if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateBegin", entity.getReportDateBegin()));
				}
			} else {
				if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("zdp.reportDateEnd", entity.getReportDateEnd()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		List<ZjDfzwPunish> entities = dfzwPunishPersistenceIface.load(detachedCriteriaProxy, null);
		if (entities != null && entities.size() > 0) {
			return false;
		}
		return true;
	}

	public void delete(ZjDfzwPunish entity) throws Exception {
		dfzwPunishPersistenceIface.delete(entity);
	}

	public void deleteDetail(ZjDfzwPunishDetail detail) throws Exception {
		dfzwPunishDetailPersistenceIface.deleteTrue(detail);
	}

	public ZjDfzwPunish load(long id) throws ApplicationAccessException {
		return dfzwPunishPersistenceIface.load(id);
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setDfzwPunishPersistenceIface(DfzwPunishPersistenceIface dfzwPunishPersistenceIface) {
		this.dfzwPunishPersistenceIface = dfzwPunishPersistenceIface;
	}

	public void setDfzwPunishDetailPersistenceIface(DfzwPunishDetailPersistenceIface dfzwPunishDetailPersistenceIface) {
		this.dfzwPunishDetailPersistenceIface = dfzwPunishDetailPersistenceIface;
	}

	public int getIsAllReport() {
		return isAllReport;
	}

	public void setIsAllReport(int isAllReport) {
		this.isAllReport = isAllReport;
	}

}
