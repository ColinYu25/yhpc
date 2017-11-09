/*
 * @(#) MaterialFacadeImpl.java        
 * Date 2008-10-29                                           
 * Copyright (c) 2008 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.tool.hbm2x.StringUtils;
import org.springframework.security.GrantedAuthority;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.MapMarker;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.MapPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.MapFacadeIface;
//import com.safetys.nbyhpc.util.AppUtils;
//import com.safetys.nbyhpc.util.UserAreaWrapper;
public class MapFacadeImpl implements MapFacadeIface {

	private MapPersistenceIface mapPersistenceIface;

	private CompanyPersistenceIface companyPersistenceIface;
	
	private CompanyPassPersistenceIface companyPassPersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;
	
	public FkArea loadArea(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
		List<FkArea> areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		if (areas != null && areas.size() == 1) {
			return areas.get(0);
		}
		return null;
	}

	public FkArea loadAreaById(Long id) throws ApplicationAccessException {
		return areaPersistenceIface.loadArea(id);
	}

	public List<DaCompany> loadCompanys(DaCompany company, Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		List<DaCompany> companies = new ArrayList<DaCompany>();
		String sql = "select fa.sort_num,dc.id,dc.company_name,dc.reg_address from da_company dc left join fk_area fa on dc.second_area = fa.area_code";
		String sql2 = "select count(dc.id) from da_company dc";
		String queryCondition = " where dc.is_deleted = 0"; //查询条件
		String queryCondition2 = " and fa.is_deleted = 0";
//		String orderCondition = " order by fa.sort_num,dc.id"; //排序规则(按区域排序)
		String orderCondition = " order by dc.id"; //排序规则
		if (company != null) {
			if (!StringUtils.isEmpty(company.getCompanyName())) { //查询条件：企业名称
				queryCondition += " and dc.company_name like '%" + company.getCompanyName().trim() + "%'";
			}
			if (!StringUtils.isEmpty(company.getRegAddress())){ //查询条件：注册地址
				queryCondition += " and dc.reg_address like '%" + company.getRegAddress().trim() + "%'";
			}
			if (company.getSecondArea() != null && company.getSecondArea() != 0L) { //查询条件：县区
				queryCondition += " and dc.second_area =" + company.getSecondArea();
			}
			if (company.getThirdArea() != null && company.getThirdArea() != 0L) { //查询条件：乡镇
				queryCondition += " and dc.third_area =" + company.getThirdArea();
			}
			if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
				queryCondition += " and dc.fouth_area =" + company.getFouthArea();
			}	
			
			if(company.getOff()!=null){
				if(company.getOff()!=1){
					queryCondition += " and dc.id not in(select par_da_com_id from da_company_logout where is_deleted=0 and type=1)";
				}else{
					queryCondition += " and dc.id in(select par_da_com_id from da_company_logout where is_deleted=0 and type="+company.getOff()+")";
				}
			}
			if(company.getAffrim()!=null){
				if(company.getAffrim()!=1){
					queryCondition += " and dc.id not in(select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm =1)";
				}else{
					queryCondition += " and dc.id in((select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm="+company.getAffrim()+"))";
				}
			}
			
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					queryCondition += " and dc.id in(select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))";
				}
			}
		}
		if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			queryCondition += " and dc.fouth_area =" + userDetail.getFouthArea();
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			queryCondition += " and dc.third_area =" + userDetail.getThirdArea();
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			queryCondition += " and dc.second_area =" + userDetail.getSecondArea();
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			queryCondition += " and dc.first_area =" + userDetail.getFirstArea();
		}
		ResultSet rs = companyPersistenceIface.findBySql(sql + queryCondition + queryCondition2 + orderCondition);
		if (rs != null) {
			int startNum = pagination.getItemCount();//页面实现开始数
			int endNum = startNum + pagination.getPageSize();//页面显示结束数
			int totalNum = 0;//累计查询结果总数
			try {
				while (rs.next()) {
					if(totalNum>=startNum && totalNum<endNum){//只取页面显示的记录
						DaCompany daCompany = new DaCompany(rs.getLong(2),rs.getString(3),rs.getString(4));
						companies.add(daCompany);
					}
					totalNum++;
					if(totalNum == endNum){
						break;
					}
				}
				rs.close();
				ResultSet rs2 = companyPersistenceIface.findBySql(sql2 + queryCondition);
//				System.out.println(sql2 + queryCondition);
				if (rs2 != null) {
					while (rs2.next()) {
						totalNum = rs2.getInt(1); //获得总数
					}
				}
				rs2.close();
				pagination.setTotalCount(totalNum);//更新查询结果总数
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return companies;
	}

	/**
	 * @description 标记
	 * @param wkt
	 * @param phMarkerEquipment
	 * @param userId
	 * @return
	 * @throws ApplicationAccessException
	 */
	public MapMarker createMarker(MapMarker mapMarker, Long userId) throws ApplicationAccessException {
		return mapPersistenceIface.creatMapMarker(mapMarker);
	}

	/**
	 * @description 所有地图上显示的标记
	 */
	public List<MapMarker> loadMapMarkers(Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(MapMarker.class,"mm");
		return mapPersistenceIface.loadMapMarkers(detachedCriteriaProxy,pagination);
	}

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}


	/**
	 * @description 根据类型查出对应标记
	 */
	public String loadPhMapMarkerByType(String markerType,Pagination pagination) throws ApplicationAccessException {
		String marker_ids = "";
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(MapMarker.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("markerType", markerType));
		List<MapMarker> list = mapPersistenceIface.loadMapMarkers(detachedCriteriaProxy,pagination);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				marker_ids += list.get(i).getMarkerId() + ",";
			}
			marker_ids = marker_ids.substring(0, marker_ids.length() - 1);
		}
		return marker_ids;
	}

	public boolean vlidateUserRoles(UserDetailWrapper userDetail,String roleCode)
			throws ApplicationAccessException {
		GrantedAuthority[] authorities = userDetail.getAuthorities();
		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equals(roleCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 加载单个标记
	 */
	public MapMarker loadMapMarker(String markerType, Long markerId, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(MapMarker.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("markerId", markerId));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("markerType", markerType));
		List<MapMarker> list = mapPersistenceIface.loadMapMarkers(detachedCriteriaProxy,pagination);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据查询条件显示地图上的标记
	 */
	public List<MapMarker> loadMapMarkers(DaCompany company, MapMarker mapMarker) {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(MapMarker.class, "mm");
		if (mapMarker != null) {
			if (!StringUtils.isEmpty(String.valueOf(mapMarker.getMarkerId()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("mm.markerId", mapMarker.getMarkerId()));
			}
			if (!StringUtils.isEmpty(mapMarker.getMarkerType())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("mm.markerType", mapMarker.getMarkerType()));
			}
		}
		if (company != null) {
			if (!StringUtils.isEmpty(company.getCompanyName())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("mm.markerName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select id from DA_COMPANY dc where dc.reg_address like '%" + company.getRegAddress().trim() + "%')"));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select id from DA_COMPANY dc where dc.first_area ='" + company.getFirstArea() + "')"));
				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select id from DA_COMPANY dc where dc.second_area ='" + company.getSecondArea() + "')"));
					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select id from DA_COMPANY dc where dc.third_area ='" + company.getThirdArea() + "')"));
						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select id from DA_COMPANY dc where dc.fouth_area ='" + company.getFouthArea() + "')"));
							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select id from DA_COMPANY dc where dc.fifth_area ='" + company.getFifthArea() + "')"));
							}
						}
					}
				}
			}
			if(company.getOff()!=null){
				if(company.getOff()!=1){
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id not in (select par_da_com_id from da_company_logout where is_deleted=0 and type=1)"));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select par_da_com_id from da_company_logout where is_deleted=0 and type="+company.getOff()+")"));
				}
			}
			if(company.getAffrim()!=null){
				if(company.getAffrim()!=1){
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id not in (select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm =1)"));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm="+company.getAffrim()+")"));
				}
			}
			
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
				}
			}
		}
		return mapPersistenceIface.loadMapMarkers(detachedCriteriaProxy, null);
	}


	public void deleteMarker(MapMarker mapMarker) throws ApplicationAccessException {
		String sql = "update map_marker set is_deleted=1 where marker_id=" + mapMarker.getId() + " and marker_type='"
				+ mapMarker.getMarkerType() + "'";
		mapPersistenceIface.executeSQLUpdate(sql);
	}
	
	public List<DaCompany> loadCompaniesByUserId(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.userId",(long)userDetail.getUserId()));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}

	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long)userDetail.getUserId()));
		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
	}
	
	public List<DaCompany> loadCompanysForQuickSearch(DaCompany company,
			Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (company != null) {
				if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
				}
				if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
				}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.deleted", false));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	public List<DaCompany> loadUnmarkerCompanies(List<DaCompany> companies,
			Pagination pagination) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (companies != null && companies.size() > 0) {
			String ids = "";
			for (DaCompany company:companies) {
				ids += company.getId() + ",";
			}
			ids = ids.substring(0, ids.length() - 1);
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in(" + ids + ")"));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select marker_id from map_marker where is_deleted = 0)"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.deleted", false));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<MapMarker> loadMapMarkers(List<DaCompany> companies,
			Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(MapMarker.class,"mm");
		if (companies != null && companies.size() > 0) {
			String ids = "";
			for (DaCompany company:companies) {
				ids += company.getId() + ",";
			}
			ids = ids.substring(0, ids.length() - 1);
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.marker_Id in(" + ids + ")"));
		}
		return mapPersistenceIface.loadMapMarkers(detachedCriteriaProxy,pagination);
	}
	
	public List<DaCompany> loadInitCompanys(DaCompany company,
			Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		List<DaCompany> companies = new ArrayList<DaCompany>();
		String sql = "select fa.sort_num,dc.id,dc.company_name,dc.reg_address from da_company dc left join fk_area fa on dc.second_area = fa.area_code";
			   sql+= " where dc.is_deleted = 0 and fa.is_deleted = 0 and company_name in(select xk_company_name from xk_yh_company) order by dc.id";
		ResultSet rs = companyPersistenceIface.findBySql(sql);
		if (rs != null) {
			try {
				int startNum = pagination.getItemCount();//页面实现开始数
				int endNum = startNum + pagination.getPageSize();//页面显示结束数
				int totalNum = 0;//累计查询结果总数
				while (rs.next()) {
					if(totalNum>=startNum && totalNum<endNum){//只取页面显示的记录
						company = new DaCompany(rs.getLong(2),rs.getString(3),rs.getString(4));
						companies.add(company);
					}
					totalNum++;
				}
				rs.close();
				pagination.setTotalCount(totalNum);//更新查询结果总数
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return companies;
	}
	
	public List<DaCompany> loadMapForGsCompanies(DaCompany company,
			Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		List<DaCompany> companies = new ArrayList<DaCompany>();
		String sql = "select fa.sort_num, dc.id, dc.company_name, dc.reg_address from da_company dc";
			   sql += " left join t_core_area fa on dc.second_area = fa.area_code";
			   sql += " and dc.id in (select id from t_core_company where is_gather_data = 1)";
			   String sql2 = "select count(dc.id) from da_company dc";
			   String queryCondition = " where dc.is_deleted = 0 and dc.id in (select id from t_core_company where is_gather_data = 1)"; //查询条件
			   String queryCondition2 = " and fa.is_deleted = 0";
			   String orderCondition = " order by dc.id"; //排序规则
				if (company != null) {
					if (!StringUtils.isEmpty(company.getCompanyName())) { //查询条件：企业名称
						queryCondition += " and dc.company_name like '%" + company.getCompanyName().trim() + "%'";
					}
					if (!StringUtils.isEmpty(company.getRegAddress())){ //查询条件：注册地址
						queryCondition += " and dc.reg_address like '%" + company.getRegAddress().trim() + "%'";
					}
					if (company.getSecondArea() != null && company.getSecondArea() != 0L) { //查询条件：县区
						queryCondition += " and dc.second_area =" + company.getSecondArea();
					}
					if (company.getThirdArea() != null && company.getThirdArea() != 0L) { //查询条件：乡镇
						queryCondition += " and dc.third_area =" + company.getThirdArea();
					}
					if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
						queryCondition += " and dc.fouth_area =" + company.getFouthArea();
					}	
					
					if(company.getOff()!=null){
						if(company.getOff()!=1){
							queryCondition += " and dc.id not in(select par_da_com_id from da_company_logout where is_deleted=0 and type=1)";
						}else{
							queryCondition += " and dc.id in(select par_da_com_id from da_company_logout where is_deleted=0 and type="+company.getOff()+")";
						}
					}
					if(company.getAffrim()!=null){
						if(company.getAffrim()!=1){
							queryCondition += " and dc.id not in(select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm =1)";
						}else{
							queryCondition += " and dc.id in((select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm="+company.getAffrim()+"))";
						}
					}
					
					if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
						String trade = "";
						for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
							if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
								trade = company.getTradeTypes().split(",")[i].trim();
								break;
							}
						}
						if (!"".equals(trade)) {
							queryCondition += " and dc.id in(select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))";
						}
					}
				}
				if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
					queryCondition += " and dc.fouth_area =" + userDetail.getFouthArea();
				} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
					queryCondition += " and dc.third_area =" + userDetail.getThirdArea();
				} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
					queryCondition += " and dc.second_area =" + userDetail.getSecondArea();
				} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
					queryCondition += " and dc.first_area =" + userDetail.getFirstArea();
				}
				ResultSet rs = companyPersistenceIface.findBySql(sql + queryCondition + queryCondition2 + orderCondition);
				if (rs != null) {
					int startNum = pagination.getItemCount();//页面实现开始数
					int endNum = startNum + pagination.getPageSize();//页面显示结束数
					int totalNum = 0;//累计查询结果总数
					try {
						while (rs.next()) {
							if(totalNum>=startNum && totalNum<endNum){//只取页面显示的记录
								DaCompany daCompany = new DaCompany(rs.getLong(2),rs.getString(3),rs.getString(4));
								companies.add(daCompany);
							}
							totalNum++;
							if(totalNum == endNum){
								break;
							}
						}
						rs.close();
						ResultSet rs2 = companyPersistenceIface.findBySql(sql2 + queryCondition);
//						System.out.println(sql2 + queryCondition);
						if (rs2 != null) {
							while (rs2.next()) {
								totalNum = rs2.getInt(1); //获得总数
							}
						}
						rs2.close();
						pagination.setTotalCount(totalNum);//更新查询结果总数
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return companies;
	}
	
	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setMapPersistenceIface(MapPersistenceIface mapPersistenceIface) {
		this.mapPersistenceIface = mapPersistenceIface;
	}

	public void setCompanyPassPersistenceIface(CompanyPassPersistenceIface companyPassPersistenceIface) {
		this.companyPassPersistenceIface = companyPassPersistenceIface;
	}

	public DaIndustryParameter loadTradeTypeById(DaIndustryParameter daIndustryParameter)
			throws ApplicationAccessException {
		return tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
	}

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

}
