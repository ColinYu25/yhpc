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
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;
import com.safetys.nbyhpc.domain.model.ZjDfzwReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwDetailPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwPersistenceIface;
import com.safetys.nbyhpc.facade.iface.DfzwFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType1;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.State;
import com.safetys.nbyhpc.web.axis2.model.DfzwReportDetail;

public class DfzwFacadeImpl implements DfzwFacadeIface {

	public int isAllReport = 0; // 已上报个数 0未上报完 1已上报完

	private DfzwPersistenceIface dfzwPersistenceIface;

	private DfzwDetailPersistenceIface dfzwDetailPersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;

	public void create(ZjDfzwReport entity, List<ZjDfzwReportDetail> details) throws Exception {
		dfzwPersistenceIface.create(entity);
		if (details != null && details.size() > 0) {
			for (ZjDfzwReportDetail detail : details) {
				detail.setDfzwReport(entity);
				fullZeroForDetail(detail);
				if (detail.getType().toString().trim().equals("01-coal mine")){
					detail.setWuzheng(0);
					detail.setGuanbi(0);
					detail.setTingchan(0);
					detail.setManbao(0);
					detail.setJubuzhixing(0);
					detail.setFeifayonggong(0);
					detail.setZuoyeguicheng(0);
					detail.setGongyi(0);
					detail.setZhidubujianquan(0);
					detail.setZhongda(0);
					detail.setYingji(0);
					detail.setQita(0);
					detail.setXincailiao(0);
				}
				dfzwDetailPersistenceIface.create(detail);
			}
		}
	}

	// 。。
	public void update(ZjDfzwReport entity, List<ZjDfzwReportDetail> details) throws Exception {
		ZjDfzwReport _entity = load(entity.getId());
		entity.setState(_entity.getState());
		entity.setModifyTime(_entity.getModifyTime());
		deleteDetails(_entity.getId());
		if (details != null && details.size() > 0) {
			for (ZjDfzwReportDetail detail : details) {
				detail.setDfzwReport(_entity);
				fullZeroForDetail(detail);
				if (detail.getType().toString().trim().equals("01-coal mine")){
					detail.setWuzheng(0);
					detail.setGuanbi(0);
					detail.setTingchan(0);
					detail.setManbao(0);
					detail.setJubuzhixing(0);
					detail.setFeifayonggong(0);
					detail.setZuoyeguicheng(0);
					detail.setGongyi(0);
					detail.setZhidubujianquan(0);
					detail.setZhongda(0);
					detail.setYingji(0);
					detail.setQita(0);
					detail.setXincailiao(0);
				}
				dfzwDetailPersistenceIface.create(detail);
			}
		}
		dfzwPersistenceIface.merge(entity);
	}

	/**
	 * 对子表的数字字段补充默认值0
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @param detail
	 */
	private void fullZeroForDetail(ZjDfzwReportDetail detail) {
		if (detail == null) {
			detail = new ZjDfzwReportDetail();
		}
		detail.setWuzheng(detail.getWuzheng() == null ? 0 : detail.getWuzheng());
		detail.setGuanbi(detail.getGuanbi() == null ? 0 : detail.getGuanbi());
		detail.setTingchan(detail.getTingchan() == null ? 0 : detail.getTingchan());
		detail.setManbao(detail.getManbao() == null ? 0 : detail.getManbao());
		detail.setJubuzhixing(detail.getJubuzhixing() == null ? 0 : detail.getJubuzhixing());
		detail.setFeifayonggong(detail.getFeifayonggong() == null ? 0 : detail.getFeifayonggong());
		detail.setZuoyeguicheng(detail.getZuoyeguicheng() == null ? 0 : detail.getZuoyeguicheng());
		detail.setGongyi(detail.getGongyi() == null ? 0 : detail.getGongyi());
		detail.setZhidubujianquan(detail.getZhidubujianquan() == null ? 0 : detail.getZhidubujianquan());
		detail.setZhongda(detail.getZhongda() == null ? 0 : detail.getZhongda());
		detail.setYingji(detail.getYingji() == null ? 0 : detail.getYingji());
		detail.setXincailiao(detail.getXincailiao() == null ? 0 : detail.getXincailiao());
		detail.setQita(detail.getQita() == null ? 0 : detail.getQita());
	}

	public List<ZjDfzwReport> loadByUser(ZjDfzwReport entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()));
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea())).add(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea())).add(RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0L)));
			} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea())).add(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea())).add(RestrictionsProxy.eq("fui.thirdArea", entity.getUser().getFkUserInfo().getThirdArea()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("zdr.modifyTime"));
		return dfzwPersistenceIface.load(detachedCriteriaProxy, null);
	}

	public List<ZjDfzwReport> load(ZjDfzwReport entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		if (entity != null) {
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {

				if (entity.isProvince() == true) {// 查看省级统计列表,不显示市级和县级

					if (entity.isAnweiCheck()) {
						detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
						String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
						detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.eq("zdr.roleDepic", roleDepic)));// anwei
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
								RestrictionsProxy.eq("zdr.state", SafetyTrouble.PROVINCE_REPORT)));
					} else {
						detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
						String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
								RestrictionsProxy.or(RestrictionsProxy.eq("zdr.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdr.state", SafetyTrouble.PROVINCE_REPORT))));
					}
				} else {

					detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
					String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());

					if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
								RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT)));
					} else {
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
								RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT)));
					}
				}
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {

				detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
				String roleDepic = RoleType1.roleDepic(RoleType1.CITY, entity.getUser().getFkRoles());
				if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录
					detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级各个部门只能查看自己填报的上报或者未上报状态的记录
							RestrictionsProxy.or(RestrictionsProxy.eq("zdr.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT))));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
							RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT)));
				}
			}

			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
			}

			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
			}
			
			if (entity.getSecondArea()!=null&&entity.getSecondArea()>0) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getSecondArea()));
			}

			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd()));
				}
			}
		}
		
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		return dfzwPersistenceIface.load(detachedCriteriaProxy, pagination);
	}

	public List<ZjDfzwReport> loadCityReport(ZjDfzwReport entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		if (entity != null) {
//			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {

//				if (entity.isProvince() == true) {// 查看省级统计列表,不显示市级和县级

//					if (entity.isAnweiCheck()) {
//						detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
//						String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
//						detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.eq("zdr.roleDepic", roleDepic)));// anwei
//						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
//								RestrictionsProxy.eq("zdr.state", SafetyTrouble.PROVINCE_REPORT)));
//					} else {
						detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
						//String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
						//此处默认只查询安委办的打非治违汇总
						String roleDepic = "anwei";
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))),// 市级各个部门只能查看自己填报的上报或者未上报状态的记录
								RestrictionsProxy.or(RestrictionsProxy.eq("zdr.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdr.state", SafetyTrouble.PROVINCE_REPORT))));
//					}
//				} else {
//
//					detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
//					String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
//
//					if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录
//						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
//						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
//								RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT)));
//					} else {
//						detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.isNotNull("fui.secondArea"), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
//								RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT)));
//					}
//				}
//			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
//
//				detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
//				String roleDepic = RoleType1.roleDepic(RoleType1.CITY, entity.getUser().getFkRoles());
//				if (!"anwei".equals(roleDepic)) {// 安委可以看到所有其它部门上报的记录，其它部门只能看到本部门的记录
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
//					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级各个部门只能查看自己填报的上报或者未上报状态的记录
//							RestrictionsProxy.or(RestrictionsProxy.eq("zdr.state", SafetyTrouble.NO_REPORT), RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT))));
//				} else {
//					detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)))),// 县级安委只能查看所有本县级各个部门上报状态的记录
//							RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT)));
//				}
//			}

			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
			}

			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
			}
			
			if (entity.getSecondArea()!=null&&entity.getSecondArea()>0) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getSecondArea()));
			}

			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		return dfzwPersistenceIface.load(detachedCriteriaProxy, pagination);
	}
	

	
	/**
	 * 市级填报(各个部门只能查看各个市级部门填报的数据)
	 */
	public List<ZjDfzwReport> loadForCity(ZjDfzwReport entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", true));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
			}
			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
			}

			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dfzwPersistenceIface.load(detachedCriteriaProxy, pagination);
	}

	/**
	 * 市级汇总之后需要设置县级上报的市级不能操作退回
	 */
	public void loadDetailsSetRollback(ZjDfzwReport entity) throws Exception {
		List<ZjDfzwReport> entitys = load(entity, null);
		for (ZjDfzwReport punish : entitys) {
			punish.setNotAllowedroolBack(true);
			dfzwPersistenceIface.update(punish);
		}
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
	public List<ZjDfzwReport> loadByCity(FkUser fkUser) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", fkUser.getFkUserInfo().getFirstArea()));

		} else if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add((RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", fkUser.getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", fkUser.getFkUserInfo().getSecondArea()))));
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("fui.thirdArea", 0L), RestrictionsProxy.isNull("fui.thirdArea")));
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, fkUser.getFkRoles())) {
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", fkUser.getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", fkUser.getFkUserInfo().getSecondArea())), RestrictionsProxy.eq("fui.thirdArea", fkUser.getFkUserInfo().getThirdArea())));
		}
		List<ZjDfzwReport> reports = dfzwPersistenceIface.load(detachedCriteriaProxy, null);
		return reports;
	}

	public List<ZjDfzwReportDetail> loadDetails(Long id, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReportDetail.class, "zdrd");
		System.out.println("id: " + id);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdrd.dfzwReport.id", id));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdrd.type"));// 排序
		List<ZjDfzwReportDetail> details = dfzwDetailPersistenceIface.load(detachedCriteriaProxy, pagination);
		return details;
	}

	public void deleteDetails(Long id) throws Exception {
		List<ZjDfzwReportDetail> details = loadDetails(id, null);
		for (ZjDfzwReportDetail detail : details) {
			deleteDetail(detail);
		}
	}

	public void update(ZjDfzwReport entity) throws Exception {
		ZjDfzwReport _entity = load(entity.getId());
		entity.setState(_entity.getState());
		entity.setModifyTime(_entity.getModifyTime());
		dfzwPersistenceIface.merge(entity);
	}

	/**
	 * @depiction:判断下一级的区域用户是否已经都上报
	 * @param areaType
	 *            判断哪一级是否上报
	 * @param areaType
	 *            当前用户的行政区划代码
	 */
	public boolean is_all_reported(String areaType, Long areaCode, ZjDfzwReport entity) {
		/**
		 * 周报表不需要强制要求下级区域全部上报，本级用户才能打开汇总报表（因时间较短）
		 */
		// String hql = "";
		// if ("county".equals(areaType)) {
		// hql =
		// "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
		// + areaCode
		// +
		// ") and area_code not in (select thirdArea from FkUserInfo where id in (select userId from ZjDfzwReport where state=1 and deleted=0 and reportMonth='"
		// + entity.getReportMonth() + "') and deleted=0  and secondArea=" +
		// areaCode + ")";
		// } else if ("city".equals(areaType)) {
		// hql =
		// "select id from FkArea a where a.deleted=0 and a.fatherId = (select id from FkArea where area_code="
		// + areaCode
		// +
		// ") and area_code not in (select secondArea from FkUserInfo where id in (select userId from ZjDfzwReport where state=2 and deleted=0 and reportMonth='"
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
	public List<ZjDfzwReportDetail> loadDetailsByUser(ZjDfzwReport entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReportDetail.class, "zdrd");
		detachedCriteriaProxy.createCriteria("zdrd.dfzwReport", "zdr").createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.sum("zdrd.wuzheng")).add(ProjectionsProxy.sum("zdrd.guanbi")).add(ProjectionsProxy.sum("zdrd.tingchan")).add(ProjectionsProxy.sum("zdrd.manbao")).add(ProjectionsProxy.sum("zdrd.jubuzhixing")).add(ProjectionsProxy.sum("zdrd.feifayonggong")).add(ProjectionsProxy.sum("zdrd.zuoyeguicheng")).add(ProjectionsProxy.sum("zdrd.gongyi")).add(ProjectionsProxy.sum("zdrd.zhidubujianquan")).add(ProjectionsProxy.sum("zdrd.zhongda")).add(ProjectionsProxy.sum("zdrd.yingji")).add(ProjectionsProxy.sum("zdrd.xincailiao")).add(ProjectionsProxy.sum("zdrd.qita")).add(ProjectionsProxy.groupProperty("zdrd.type")));
		if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {// 设置市级用户显示列表
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT), RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()))));
		} else if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {

		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdrd.type"));// 排序
		List<ZjDfzwReportDetail> objects = dfzwDetailPersistenceIface.load(detachedCriteriaProxy, null);
		Iterator iterator = objects.iterator();
		List<ZjDfzwReportDetail> details = new ArrayList<ZjDfzwReportDetail>();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ZjDfzwReportDetail detail = new ZjDfzwReportDetail((obj[13].toString()), Integer.parseInt((obj[0].toString())), Integer.parseInt((obj[1].toString())), Integer.parseInt((obj[2].toString())), Integer.parseInt((obj[3].toString())), Integer.parseInt((obj[4].toString())), Integer.parseInt((obj[5].toString())), Integer.parseInt((obj[6].toString())), Integer.parseInt((obj[7].toString())), Integer.parseInt((obj[8].toString())), Integer.parseInt((obj[9].toString())), Integer.parseInt((obj[10].toString())), Integer.parseInt((obj[11].toString())), Integer.parseInt((obj[12].toString())));
			details.add(detail);
		}
		if (details.size() == 0) {
			for (State state : SafetyTrouble.DFZW_TYPE_MAP) {
				ZjDfzwReportDetail detail = new ZjDfzwReportDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				details.add(detail);
			}
		}
		return details;
	}

	public List<DfzwReportDetail> loadDetailsForAxis2(Long areaCode, ZjDfzwReport entity) throws Exception {
		List<DfzwReportDetail> details = new ArrayList<DfzwReportDetail>();
		System.out.println("areaCode: " + areaCode);
		// liulj 发送之前先判断其上级部门是否已上报,若没上报,则不 发送(如县级或本级已上报,但市级未报的不发送)
		// System.out.println("zdr.reportDateBegin: " +
		// entity.getReportDateBegin());
		// System.out.println("zdr.reportDateEnd: " +
		// entity.getReportDateEnd());
		//
		// int isReport = 0;
		//
		// isReport = isReport(entity.getRoleDepic(),
		// entity.getReportDateEnd());
		//
		// System.out.println("--RoleDepic--"+entity.getRoleDepic()+"--isReport: "+isReport);
		// if (isReport == 1) {

		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReportDetail.class, "zdrd");
		detachedCriteriaProxy.createCriteria("zdrd.dfzwReport", "zdr").createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表

		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.sum("zdrd.wuzheng")).add(ProjectionsProxy.sum("zdrd.guanbi")).add(ProjectionsProxy.sum("zdrd.tingchan")).add(ProjectionsProxy.sum("zdrd.manbao")).add(ProjectionsProxy.sum("zdrd.jubuzhixing")).add(ProjectionsProxy.sum("zdrd.feifayonggong")).add(ProjectionsProxy.sum("zdrd.zuoyeguicheng")).add(ProjectionsProxy.sum("zdrd.gongyi")).add(ProjectionsProxy.sum("zdrd.zhidubujianquan")).add(ProjectionsProxy.sum("zdrd.zhongda")).add(ProjectionsProxy.sum("zdrd.yingji")).add(ProjectionsProxy.sum("zdrd.xincailiao")).add(ProjectionsProxy.sum("zdrd.qita")).add(ProjectionsProxy.groupProperty("zdrd.type")));

		if (areaCode == 330200000000l) { // 市级数据
			System.out.println("市级数据");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.state", SafetyTrouble.PROVINCE_REPORT));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", true));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" fui3_.second_area  is null"));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.notAllowedroolBack", false));

		} else {
			System.out.println("其他数据");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));

			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdr.state", SafetyTrouble.CITY_REPORT), RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", Nbyhpc.AREA_CODE), RestrictionsProxy.and(RestrictionsProxy.eq("fui.secondArea", areaCode), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l))))));

			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.notAllowedroolBack", true));// 必须是市级部门确认上报的数据
		}

		// liulj 发送之前先判断其上级部门是否已上报,若没上报,则不 发送(如县级或本级已上报,但市级未报的不发送)
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists (select * from  ZJ_DFZW_REPORT zdr2_  where zdr2_.state = 3   and zdr2_.is_city_create = 0  and  zdr2_.role_depic=zdr1_.role_depic  and zdr2_.REPORT_DATE_BEGIN = to_date('" + entity.getReportDateBegin() + "', 'yyyy-MM-dd')   and zdr2_.REPORT_DATE_END = to_date('" + entity.getReportDateEnd() + "', 'yyyy-MM-dd') )"));

		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdrd.type"));// 排序
		List<ZjDfzwReportDetail> objects = dfzwDetailPersistenceIface.load(detachedCriteriaProxy, null);
		Iterator iterator = objects.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			DfzwReportDetail detail = new DfzwReportDetail((obj[13].toString()), Integer.parseInt((obj[0].toString())), Integer.parseInt((obj[1].toString())), Integer.parseInt((obj[2].toString())), Integer.parseInt((obj[3].toString())), Integer.parseInt((obj[4].toString())), Integer.parseInt((obj[5].toString())), Integer.parseInt((obj[6].toString())), Integer.parseInt((obj[7].toString())), Integer.parseInt((obj[8].toString())), Integer.parseInt((obj[9].toString())), Integer.parseInt((obj[10].toString())), Integer.parseInt((obj[11].toString())), Integer.parseInt((obj[12].toString())));
			details.add(detail);
		}
		if (details.size() == 0) {
			for (State state : SafetyTrouble.DFZW_TYPE_MAP) {
				DfzwReportDetail detail = new DfzwReportDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				details.add(detail);
			}
		}
		// }
		System.out.println("details.size(): " + details.size());
		return details;
	}

	public List<ZjDfzwReportDetail> loadDetailsByDfzw(ZjDfzwReport entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReportDetail.class, "zdrd");
		detachedCriteriaProxy.createCriteria("zdrd.dfzwReport", "zdr").createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.id", entity.getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdrd.type"));// 排序
		List<ZjDfzwReportDetail> details = dfzwDetailPersistenceIface.load(detachedCriteriaProxy, null);
		if (details.size() == 0) {
			for (State state : SafetyTrouble.DFZW_TYPE_MAP) {
				ZjDfzwReportDetail detail = new ZjDfzwReportDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				details.add(detail);
			}
		}
		return details;
	}

	public List<ZjDfzwReportDetail> loadDetailsByDfzw1(ZjDfzwReport entity) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReportDetail.class, "zdrd");
		detachedCriteriaProxy.createCriteria("zdrd.dfzwReport", "zdr").createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");// 创建连接表
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.id", entity.getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("zdrd.type"));// 排序

		System.out.println("zdr.id: " + entity.getId());
		List<ZjDfzwReportDetail> details = dfzwDetailPersistenceIface.load(detachedCriteriaProxy, null);
		return details;
	}

	public List<ZjDfzwReportDetail> loadDetailsForProvince(ZjDfzwReport entity) throws Exception {
		List<ZjDfzwReport> entitys = load(entity, null);
		List<ZjDfzwReportDetail> details = new ArrayList<ZjDfzwReportDetail>();
		List<List<ZjDfzwReportDetail>> detailsTotal = new ArrayList<List<ZjDfzwReportDetail>>();
		for (ZjDfzwReport dfzw : entitys) {
			List<ZjDfzwReportDetail> list_t = this.loadDetailsByDfzw1(dfzw);
			if (null != list_t && list_t.size() > 0) {
				detailsTotal.add(list_t);
			}
		}
		for (State state : SafetyTrouble.DFZW_TYPE_MAP) {
			ZjDfzwReportDetail detail = new ZjDfzwReportDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			details.add(detail);
		}
		for (int i = 0; i < detailsTotal.size(); i++) {// 循环到市级
			for (ZjDfzwReportDetail detail : detailsTotal.get(i)) {// 循环到行业
				for (ZjDfzwReportDetail detai : details) {// 循环对比行业
					if (detai.getType().equals(detail.getType())) {// 对比
						detai.addDfzwReportDetail(detail.getWuzheng(), detail.getGuanbi(), detail.getTingchan(), detail.getManbao(), detail.getJubuzhixing(), detail.getFeifayonggong(), detail.getZuoyeguicheng(), detail.getGongyi(), detail.getZhidubujianquan(), detail.getZhongda(), detail.getYingji(), detail.getXincailiao(), detail.getQita());// 累加
					}
				}
			}
		}
		return details;
	}

	public List<ZjDfzwReportDetail> loadDetailsForCityInit(ZjDfzwReport entity) throws Exception {
		List<ZjDfzwReport> entitys = load(entity, null);
		List<ZjDfzwReport> entitys2 = loadForCitySelf(entity, null);// 获取本周本部门填报的数据
		if (null != entitys2 && entitys2.size() > 0) {
			if (null != entitys && entitys.size() > 0) {
			} else {
				entitys = new ArrayList<ZjDfzwReport>();
			}
			entitys.add(entitys2.get(0));
		}
		List<ZjDfzwReportDetail> details = new ArrayList<ZjDfzwReportDetail>();
		List<List<ZjDfzwReportDetail>> detailsTotal = new ArrayList<List<ZjDfzwReportDetail>>();
		for (ZjDfzwReport dfzw : entitys) {
			List<ZjDfzwReportDetail> list_t = this.loadDetailsByDfzw1(dfzw);
			if (null != list_t && list_t.size() > 0) {
				detailsTotal.add(list_t);
			}
		}
		for (State state : SafetyTrouble.DFZW_TYPE_MAP) {
			ZjDfzwReportDetail detail = new ZjDfzwReportDetail(state.getKey(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
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
			for (ZjDfzwReportDetail detail : detailsTotal.get(i)) {// 循环到行业
				for (ZjDfzwReportDetail detai : details) {// 循环对比行业
					if (detai.getType().equals(detail.getType())) {// 对比
						detai.addDfzwReportDetail(detail.getWuzheng(), detail.getGuanbi(), detail.getTingchan(), detail.getManbao(), detail.getJubuzhixing(), detail.getFeifayonggong(), detail.getZuoyeguicheng(), detail.getGongyi(), detail.getZhidubujianquan(), detail.getZhongda(), detail.getYingji(), detail.getXincailiao(), detail.getQita());// 累加
					}
				}
			}
		}
		return details;
	}

	/**
	 * 市级填报(各个部门只能查看各个市级部门填报的数据)
	 */
	public List<ZjDfzwReport> loadForCitySelf(ZjDfzwReport entity, Pagination pagination) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		System.out.println("roleDepic: " + roleDepic);
		System.out.println("cityCreate: " + 1);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", true));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
			if (entity.getReportDateBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
			}
			if (entity.getReportDateEnd() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dfzwPersistenceIface.load(detachedCriteriaProxy, pagination);
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

	public List<ZjDfzwReport> loadCountyReports(ZjDfzwReport entity, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		List<FkArea> areas = null;
		if (entity != null) {
			if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("zdr.user.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("zdr.state", SafetyTrouble.COUNTY_REPORT), RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea())));// 设置省级用户显示列表
			} else if (RoleType1.isRoleByDepic(RoleType1.CITY, entity.getUser().getFkRoles())) {
				detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("zdr.user.fkUserInfo", "fui");
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.and(RestrictionsProxy.eq("zdr.state", SafetyTrouble.COUNTY_REPORT), RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea())), RestrictionsProxy.eq("zdr.user", entity.getUser())));// 设置市级用户显示列表
				detachedCriteriaProxy.add(RestrictionsProxy.isNotNull("fui.thirdArea"));// 过滤市级用户
			}
			if (entity.getReportDateBegin() != null || entity.getReportDateEnd() != null) {
				if (entity.getReportDateBegin() != null && entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()), RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd())));
				} else if (entity.getReportDateBegin() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.ge("zdr.reportDateEnd", entity.getReportDateBegin()));
				} else if (entity.getReportDateEnd() != null) {
					detachedCriteriaProxy.add(RestrictionsProxy.le("zdr.reportDateBegin", entity.getReportDateEnd()));
				}
			}
			Long negativeOne = -1L;
			if (entity.getSecondArea() != null && !negativeOne.equals(entity.getSecondArea())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getSecondArea()));
			}
		}

		detachedCriteriaProxy.addOrder(OrderProxy.desc("zdr.modifyTime"));// 根据修改时间排序
		return switchCountyReportList(dfzwPersistenceIface.load(detachedCriteriaProxy, pagination), areas);
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

	private List<ZjDfzwReport> switchCountyReportList(List<ZjDfzwReport> entities, List<FkArea> areas) {
		if (areas == null || areas.size() == 0) {
			return entities;
		}
		String[] existCompany = new String[areas.size()];
		ZjDfzwReport dfzw = new ZjDfzwReport();
		FkUser fu = new FkUser();
		FkUserInfo fui = new FkUserInfo();
		int k = 0;
		try {
			if (entities.size() == 0) {
				for (int j = 0; j < areas.size(); j++) {
					dfzw = new ZjDfzwReport();
					fui = new FkUserInfo();
					fu = new FkUser();
					fui.setUserCompany(String.valueOf((areas.get(j))));
					fu.setFkUserInfo(fui);
					dfzw.setUser(fu);
					entities.add(dfzw);
				}
			} else {
				for (int j = 0; j < areas.size(); j++) {
					for (int i = 0; i < entities.size(); i++) {
						if (entities.get(i).getUser().getFkUserInfo().getUserCompany() != null && areas.get(j) != null) {
							if (entities.get(i).getUser().getFkUserInfo().getUserCompany().equals(areas.get(j))) {
								break;
							} else {
								if (existCompany[0] == null) {
									dfzw = new ZjDfzwReport();
									fui = new FkUserInfo();
									fu = new FkUser();
									fui.setUserCompany(String.valueOf((areas.get(j))));
									fu.setFkUserInfo(fui);
									dfzw.setUser(fu);
									entities.add(dfzw);
									existCompany[k] = String.valueOf((areas.get(j)));
									k++;
								} else {
									for (int l = 0; l < existCompany.length; l++) {
										if (existCompany[l] != null) {
											if (!existCompany[l].equals(String.valueOf((areas.get(j)))) && existCompany[l + 1] == null) {
												dfzw = new ZjDfzwReport();
												fui = new FkUserInfo();
												fu = new FkUser();
												fui.setUserCompany(String.valueOf((areas.get(j))));
												fu.setFkUserInfo(fui);
												dfzw.setUser(fu);
												// dfzw.setReportMonth("");
												entities.add(dfzw);
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

	public boolean checkAllowCreate(ZjDfzwReport entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		String roleLevel = RoleType1.roleLevelByUser(entity.getUser());
		if (roleLevel != null && "PROVINCE".equals(roleLevel)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles())));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l)));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", RoleType1.roleDepic(RoleType1.CITY, entity.getUser().getFkRoles())));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea", entity.getUser().getFkUserInfo().getSecondArea()));
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.isNull("fui.thirdArea"), RestrictionsProxy.eq("fui.thirdArea", 0l)));
		}
		if (entity.getReportType() != null && entity.getReportType() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd",
		// entity.getReportDateEnd()));
		List<ZjDfzwReport> entities = dfzwPersistenceIface.load(detachedCriteriaProxy, null);
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
	public boolean checkAllowCreate1(ZjDfzwReport entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
		if (entity.getReportType() != null && entity.getReportType().intValue() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd",
		// entity.getReportDateEnd()));
		List<ZjDfzwReport> entities = dfzwPersistenceIface.load(detachedCriteriaProxy, null);
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
	public boolean checkAllowCreate2(ZjDfzwReport entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
		if (entity.getReportType() != null && entity.getReportType().intValue() == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd",
		// entity.getReportDateEnd()));
		List<ZjDfzwReport> entities = dfzwPersistenceIface.load(detachedCriteriaProxy, null);
		if (entities != null && entities.size() > 0) {
			return false;
		}
		return true;
	}

	public boolean checkAllowCreate3(ZjDfzwReport entity) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
		String roleDepic = RoleType1.roleDepic(RoleType1.PROVINCE, entity.getUser().getFkRoles());
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", true));
		if (entity != null) {
			detachedCriteriaProxy.createCriteria("zdr.user", "fu").createCriteria("fu.fkUserInfo", "fui");
			detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.eq("fui.firstArea", entity.getUser().getFkUserInfo().getFirstArea()), RestrictionsProxy.or(RestrictionsProxy.isNull("fui.secondArea"), RestrictionsProxy.eq("fui.secondArea", 0l))));
			if (entity.getReportType() != null) {
				if (entity.getReportType().intValue() == 1) {
					if (entity.getReportDateBegin() != null) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateBegin", entity.getReportDateBegin()));
					}
				} else if (entity.getReportType().intValue() == 2) {
					if (entity.getReportDateEnd() != null) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd", entity.getReportDateEnd()));
					}
				}
			}
			// if (entity.getReportDateEnd() != null) {
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.reportDateEnd",
			// entity.getReportDateEnd()));
			// }
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		List<ZjDfzwReport> entities = dfzwPersistenceIface.load(detachedCriteriaProxy, null);
		if (entities != null && entities.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Description:获取上级部门上报情况
	 * @author:liulj
	 * @time:2014-5-14
	 */
	public int isReport(String roleDepic, Date reportDate) throws Exception {
		int isReport = 0;
		System.out.println("结束日期: " + reportDate);
		String beginDate = "";
		if (reportDate != null) {
			String[] ab = reportDate.toString().split("-");
			System.out.println("ab[0]: " + ab[0]);
			beginDate = ab[0] + "-" + ab[1] + "-01";
			System.out.println("开始日期: " + beginDate);
			System.out.println("roleDepic: " + roleDepic);
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjDfzwReport.class, "zdr");
			if (roleDepic.equals("anwei")) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.report_date_end  between  to_date('" + beginDate + "','yyyy-MM-dd') and  to_date('" + reportDate + "','yyyy-MM-dd')  "));

			} else {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.state", 3));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.roleDepic", roleDepic));
				detachedCriteriaProxy.add(RestrictionsProxy.eq("zdr.cityCreate", false));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.report_date_end  between  to_date('" + beginDate + "','yyyy-MM-dd') and  to_date('" + reportDate + "','yyyy-MM-dd')  "));

			}
			List<ZjDfzwReportDetail> details = dfzwDetailPersistenceIface.load(detachedCriteriaProxy, null);
			System.out.println("-------------");
			if (details.size() > 0) {
				isReport = 1;
			}
		}
		return isReport;
	}

	public void delete(ZjDfzwReport entity) throws Exception {
		dfzwPersistenceIface.delete(entity);
	}

	public void deleteDetail(ZjDfzwReportDetail detail) throws Exception {
		dfzwDetailPersistenceIface.deleteTrue(detail);
	}

	public ZjDfzwReport load(long id) throws ApplicationAccessException {
		return dfzwPersistenceIface.load(id);
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setDfzwPersistenceIface(DfzwPersistenceIface dfzwPersistenceIface) {
		this.dfzwPersistenceIface = dfzwPersistenceIface;
	}

	public void setDfzwDetailPersistenceIface(DfzwDetailPersistenceIface dfzwDetailPersistenceIface) {
		this.dfzwDetailPersistenceIface = dfzwDetailPersistenceIface;
	}

	public int getIsAllReport() {
		return isAllReport;
	}

	public void setIsAllReport(int isAllReport) {
		this.isAllReport = isAllReport;
	}

}
