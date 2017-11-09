/*
 * Copyright (c) 2002-2012 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerGorverPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeLinePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeNomalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.facade.iface.PipeStatisticFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * 管道统计
 * @date 2012-12-6 
 * @author lisl
 *       
 */
public class PipeStatisticFacadeImpl implements PipeStatisticFacadeIface{
	
	private CompanyPersistenceIface companyPersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;

	private PipeDangerPersistenceIface pipeDangerPersistenceIface;

	private PipeNomalDangerPersistenceIface pipeNomalDangerPersistenceIface;

	private PipeRollcallCompanyPersistenceIface pipeRollcallCompanyPersistenceIface;

	private PipeDangerGorverPersistenceIface pipeDangerGorverPersistenceIface;
	
	private PipeLinePersistenceIface pipeLinePersistenceIface;
	
	private BasePersistenceImpl persistenceImpl;
	
	private String monthSql;
	
	private String quarterSql;
	
	private String massSql;
	
	private String dangerSql;
	
	private String nomalDangerSql;
	
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 管道一般隐患统计
	 */
	public Map<String, Object> loadNomalDanger(Statistic st) throws ApplicationAccessException {
		int year = st.getYear();
		//统计参数
		FkArea area = loadArea(st.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		st.setAreaName(area.getAreaName());
		String areaType = areaRate == 4 ? "third_area" : "second_area"; st.setAreaType(areaType);
		//变量初始化
		String areaSql1 = " case when (t." + areaType + " is null or t." + areaType + "<=0) then " + st.getAreaCode() + " else t." + areaType + " end ";
		String areaSql2 = "";
		if(areaType.equals("third_area")){
			areaSql2 = " and t.second_area=" + st.getAreaCode();
		}
		String pipeType = "";
		if(st.getType()!=null && !"".equals(st.getType()) && -1!=st.getType()){
			pipeType += " and i.type='" + st.getType() + "'";
		}
		
		//绑定坏境变量
		String sql = nomalDangerSql.replaceAll(":areaCode", String.valueOf(st.getAreaCode()))
							 .replaceAll(":dangerType", "pipe_nomal_danger_type")
							 .replaceAll(":year", String.valueOf(year))
							 .replaceAll(":pipeType", pipeType)
				             .replaceAll(":areaSql1", areaSql1)
							 .replaceAll(":areaSql2", areaSql2);
		//log.info("monthSql: " + sql);
		Map<String, Object> mapTotal = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> map = null;
			ResultSet res = persistenceImpl.findBySql(sql);
			String type = "", areaCol = "", areaCol_y = "", tName = ""; Long areaCode = 0l;
			int length = 0;
			while (res.next()) {
				if(!type.equals(res.getString(1))){
					if(length >0)
						mapList.add(map);
					map = new HashMap<String, Object>();
					type = res.getString(1);
					tName = res.getString(2);
					map.put("s_type", type);
					map.put("s_tName", tName);
				}
				areaCode = res.getLong(3);
				areaCol = "s_" + res.getLong(3) + "_t";
				areaCol_y = "s_" + res.getLong(3) + "_y";
				map.put("s_" + areaCode + "_t", res.getInt(4));
				map.put("s_" + areaCode + "_y", res.getInt(5));
				mapTotal.put(areaCol, (null!=mapTotal.get(areaCol)? (Integer) mapTotal.get(areaCol) : 0) + res.getInt(4));
				mapTotal.put(areaCol_y, (null!=mapTotal.get(areaCol_y)? (Integer) mapTotal.get(areaCol_y) : 0) + res.getInt(5));
				length ++;
			}
			if(length >0)
				mapList.add(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mapTotal.put("mapList", mapList);
		return mapTotal;
	}
	
	/**
	 * 管道一般隐患统计详情
	 */
	public List<DaPipeNomalDanger> loadNomalDangerDetail(Statistic st, Pagination page) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		
		if(null!=st.getdType() && !"".equals(st.getdType())){
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.type = " +
					"(select id from da_industry_parameter where is_deleted=0 and type=10 and type =10 and name='"+st.getdType()+"')"));
		}
		
		if(null!=st.getRepaired() && "1".equals(st.getRepaired())){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
		}
		
		if(null!=st.getType() && st.getType()>-1){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", st.getType()));
		}
		
		if(null!=st.getAreaType() && !"".equals(st.getAreaType())){
			Long secondArea = st.getSecondArea();Long areaCode = st.getAreaCode();
			if(null!=st.getAreaType() && "third_area".equals(st.getAreaType())){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.secondArea", secondArea));
				if(null!=areaCode && areaCode > 0l){
					if(areaCode - Nbyhpc.AREA_CODE != 0l){
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.thirdArea", areaCode));
					}else{
						detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dnd.thirdArea", 0l), RestrictionsProxy.isNull("dnd.thirdArea")));
					}
				}
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.firstArea", Nbyhpc.AREA_CODE));
				if(null!=areaCode && areaCode > 0l){
					if(areaCode - Nbyhpc.AREA_CODE != 0l){
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.secondArea", areaCode));
					}else{
						detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dnd.secondArea", 0l), RestrictionsProxy.isNull("dnd.secondArea")));
					}
				}
			}
		}
		
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
				+ st.getYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + (st.getYear() + 1)
				+ "-01-01','yyyy-MM-dd')"));
		
		detachedCriteriaProxy.addOrder(Order.desc("pl.id"));
		
		return pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, page);
	}
	
	/**
	 * 管道重大隐患统计
	 */
	public Map<String, Object> loadDanger(Statistic st) throws ApplicationAccessException {
		int year = st.getYear();
		//统计参数
		FkArea area = loadArea(st.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		st.setAreaName(area.getAreaName());
		String areaType = areaRate == 4 ? "third_area" : "second_area"; st.setAreaType(areaType);
		//变量初始化
		String areaSql1 = " case when (t." + areaType + " is null or t." + areaType + "<=0) then " + st.getAreaCode() + " else t." + areaType + " end ";
		String areaSql2 = "";
		if(areaType.equals("third_area")){
			areaSql2 = " and t.second_area=" + st.getAreaCode();
		}
		String pipeType = "";
		if(st.getType()!=null && !"".equals(st.getType()) && -1!=st.getType()){
			pipeType += " and i.type='" + st.getType() + "'";
		}
		
		//绑定坏境变量
		String sql = dangerSql.replaceAll(":areaCode", String.valueOf(st.getAreaCode()))
							 .replaceAll(":dangerType", "pipe_danger_type")
							 .replaceAll(":year", String.valueOf(year))
							 .replaceAll(":pipeType", pipeType)
				             .replaceAll(":areaSql1", areaSql1)
							 .replaceAll(":areaSql2", areaSql2);
		//log.info("monthSql: " + sql);
		Map<String, Object> mapTotal = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> map = null;
			ResultSet res = persistenceImpl.findBySql(sql);
			String type = "", areaCol = "", areaCol_y = "", tName = ""; Long areaCode = 0l;
			int length = 0;
			while (res.next()) {
				if(!type.equals(res.getString(1))){
					if(length >0)
						mapList.add(map);
					map = new HashMap<String, Object>();
					type = res.getString(1);
					tName = res.getString(2);
					map.put("s_type", type);
					map.put("s_tName", tName);
				}
				areaCode = res.getLong(3);
				areaCol = "s_" + res.getLong(3) + "_t";
				areaCol_y = "s_" + res.getLong(3) + "_y";
				map.put("s_" + areaCode + "_t", res.getInt(4));
				map.put("s_" + areaCode + "_y", res.getInt(5));
				mapTotal.put(areaCol, (null!=mapTotal.get(areaCol)? (Integer) mapTotal.get(areaCol) : 0) + res.getInt(4));
				mapTotal.put(areaCol_y, (null!=mapTotal.get(areaCol_y)? (Integer) mapTotal.get(areaCol_y) : 0) + res.getInt(5));
				length ++;
			}
			if(length >0)
				mapList.add(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mapTotal.put("mapList", mapList);
		return mapTotal;
	}
	
	/**
	 * 管道重大隐患统计详情
	 */
	public List<DaPipeDanger> loadDangerDetail(Statistic st, Pagination page) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDanger.class, "dd");
		detachedCriteriaProxy.createAlias("dd.pipeLine", "pl");
		
		if(null!=st.getdType() && !"".equals(st.getdType())){
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in " +
					" ( select par_da_pipe_dan_id from da_pipe_danger_type_rel where par_da_ind_id = (select id from da_industry_parameter where is_deleted=0 and type= 11 and name='"+st.getdType()+"'))"));
		}
		
		if(null!=st.getRepaired() && "1".equals(st.getRepaired())){
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in " +
					" ( select par_da_pipe_dan_id from da_pipe_danger_gorver where is_deleted=0 )"));
		}
		
		if(null!=st.getType() && st.getType()>-1){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", st.getType()));
		}
		
		if(null!=st.getAreaType() && !"".equals(st.getAreaType())){
			Long secondArea = st.getSecondArea();Long areaCode = st.getAreaCode();
			if(null!=st.getAreaType() && "third_area".equals(st.getAreaType())){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.secondArea", secondArea));
				if(null!=areaCode && areaCode > 0l){
					if(areaCode - Nbyhpc.AREA_CODE != 0l){
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.thirdArea", areaCode));
					}else{
						detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dd.thirdArea", 0l), RestrictionsProxy.isNull("dd.thirdArea")));
					}
				}
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.firstArea", Nbyhpc.AREA_CODE));
				if(null!=areaCode && areaCode > 0l){
					if(areaCode - Nbyhpc.AREA_CODE != 0l){
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.secondArea", areaCode));
					}else{
						detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dd.secondArea", 0l), RestrictionsProxy.isNull("dd.secondArea")));
					}
				}
			}
		}
		
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"
				+ st.getYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + (st.getYear() + 1)
				+ "-01-01','yyyy-MM-dd')"));
		
		detachedCriteriaProxy.addOrder(Order.desc("pl.id"));
		
		return pipeDangerPersistenceIface.loadDangers(detachedCriteriaProxy, page);
	}
	
	/**
	 * 月报统计
	 */
	public Map<String, Object> loadMonth(Statistic st) throws ApplicationAccessException {
		Map<String, Object> dateMap = this.getStartAndEndDate(st);
		String startDate = (String) dateMap.get("startDate");
		String endDate = (String) dateMap.get("endDate");
		//统计参数
		FkArea area = loadArea(st.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		st.setAreaName(area.getAreaName());
		String areaType = areaRate == 4 ? "third_area" : "second_area"; st.setAreaType(areaType);
		//变量初始化
		String dateSql = " and to_char(t.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(t.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
		String areaType_0 = " case when (tc." + areaType + " is null or tc." + areaType + "<=0) then " + st.getAreaCode() + " else tc." + areaType + " end ";
		String secondAreaSql = " ";
		if(areaType.equals("third_area")){
			secondAreaSql = " and tc.second_area=" + st.getAreaCode();
		}
		//绑定坏境变量
		String sql = monthSql.replaceAll(":areaCode", String.valueOf(st.getAreaCode()))
				              .replaceAll(":nbAreaCode", String.valueOf(Nbyhpc.AREA_CODE))
							  .replaceAll(":secondAreaSql", secondAreaSql)
				              .replaceAll(":dateSql", dateSql)
							  .replaceAll(":areaType", areaType_0);
		//log.info("monthSql: " + sql);
		Map<String, Object> tmap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = null;
			ResultSet res = persistenceImpl.findBySql(sql);
			String type = ""; Long areaCode = 0l;
			int length = 0;
			while (res.next()) {
				if(!type.equals(res.getString(3))){
					if(length >0)
						 tmap.put("returnMap" + type, map);
					map = new HashMap<String, Object>();
					type = res.getString(3);
				}
				areaCode = res.getLong(2);
				map.put("s_" + type + "_" + areaCode + "_q", res.getInt(4));
				map.put("s_" + type + "_" + areaCode + "_g", res.getInt(5));
				map.put("s_" + type + "_" + areaCode + "_y", res.getInt(6));
				length ++;
			}
			if(length >0)
				 tmap.put("returnMap" + type, map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmap;
	}
	
	/**
	 * 查询开始和结束时间处理
	 * @param st
	 * @return
	 */
	public Map<String, Object> getStartAndEndDate(Statistic st) {
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if(st.getMonth()>0){
				int nextMonth = st.getMonth() + 1;
				startDate = st.getYear() + "-" + (st.getMonth()>9 ? st.getMonth() : "0" + st.getMonth()) + "-01";
				if(st.getMonth()!=12)
					endDate = st.getYear() + "-" + (nextMonth>9 ? nextMonth : "0" + nextMonth) + "-01";
				else
					endDate = nextYear + "-01-01";
			}else{
				if(st.getQuarter()>0){
					if(st.getQuarter()==1){
						startDate = st.getYear() + "-01-01";
						endDate = st.getYear() + "-04-01";
					}
					if(st.getQuarter()==2){
						startDate = st.getYear() + "-04-01";
						endDate = st.getYear() + "-07-01";
					}
					if(st.getQuarter()==3){
						startDate = st.getYear() + "-07-01";
						endDate = st.getYear() + "-10-01";
					}
					if(st.getQuarter()==4){
						startDate = st.getYear() + "-10-01";
						endDate = nextYear + "-01-01";
					}
				}else{
					startDate = st.getYear() + "-01-01";
					endDate = nextYear + "-01-01";
				}
			}
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return map;
	}
	
	/**
	 * 根据管道类型、区域加载企业信息
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadCompanyListByArea(Statistic st, Boolean flag, Pagination page) throws Exception {
		String lineType = "";
		Long areaCode = 0l;
		Long secondArea = 0l;
		if(null != st){
			if(null != st.getLineType()){
				lineType = st.getLineType();
			}
			areaCode = st.getAreaCode();
			if(null != st.getSecondArea()){
				secondArea = st.getSecondArea();
			}
		}
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "c");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("c.firstArea", Nbyhpc.AREA_CODE));
		if(secondArea > 0l){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea", secondArea));
			if(null!=areaCode && areaCode>0){
				if(areaCode - secondArea != 0l){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("c.thirdArea", areaCode));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("c.thirdArea", 0l), RestrictionsProxy.isNull("c.thirdArea")));
				}
			}
		}else{
			if(null!=areaCode && areaCode>0){
				if(areaCode - Nbyhpc.AREA_CODE != 0l){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea", areaCode));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("c.secondArea", 0l), RestrictionsProxy.isNull("c.secondArea")));
				}
			}
		}
		String typeSql = "";
		if(null!=lineType && !"".equals(lineType)){
			if("20".equals(lineType)){
				typeSql = " and (p.type = 0 or p.type = 2) ";
			}else{
				typeSql = " and p.type = " + lineType;
			}
		}
		String sql =" select distinct(pc.cid) from ("
				   +"   select p.company_id as pcid from da_pipeline_info p where p.is_deleted=0 " + typeSql + " group by p.company_id"
				   +" ) p left join ("  
				   +"   select pc2.id, pc2.company_id as cid, pc2.cq_company_id as qcid from da_pipeline_companyinfo pc2 where pc2.is_deleted=0"
				   +" ) pc on pc.id=p.pcid and pc.cid is not null";
		
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql + ")"));	
		//查询未报单位数
		if(!flag){
			String dateSql = ((st.getQuarter()>0) ? " year=" + st.getYear() + " and quarter=" + st.getQuarter() : " year=" + st.getYear() + " and quarter!=0");
			String sql2 =" select distinct company_id from da_pipe_company_quarter_report where " + dateSql + " group by company_id";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (" + sql2 + ")"));	
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("c.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, page);
	}
	
	/**
	 * 根据区域、管道类型，加载管道列表
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipLineByArea(Statistic st, Pagination page) throws Exception {
		String lineType = "";
		String paramType = "";
		Long areaCode = 0l;
		Long secondArea = 0l;
		if(null != st){
			if(null != st.getLineType()){
				lineType = st.getLineType();
			}
			if(null != st.getParamType()){
				paramType = st.getParamType();
			}
			areaCode = st.getAreaCode();
			if(null != st.getSecondArea()){
				secondArea = st.getSecondArea();
			}
		}
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dpli");
		detachedCriteriaProxy.createAlias("daPipelineCompanyinfo", "dplci").createAlias("dplci.company", "c");
		if (null != lineType && !"".equals(lineType)){
			if(!"20".equals(lineType)){//特别主意报送情况统计中详情这里不能共用
//				System.out.println("lineType: "+lineType);
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", Integer.valueOf(lineType)));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dpli.type", 2), RestrictionsProxy.eq("dpli.type", 0)));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("c.firstArea", Nbyhpc.AREA_CODE));
		if(secondArea > 0l){
//			System.out.println("secondArea: "+secondArea);
			detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea", secondArea));
			if(null!=areaCode && areaCode>0){
				if(areaCode - secondArea != 0l){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("c.thirdArea", areaCode));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("c.thirdArea", 0l), RestrictionsProxy.isNull("c.thirdArea")));
				}
			}
		}else{
			if(null!=areaCode && areaCode>0){
				if(areaCode - Nbyhpc.AREA_CODE != 0l){
//					System.out.println("areaCode: "+areaCode);
					detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea", areaCode));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("c.secondArea", 0l), RestrictionsProxy.isNull("c.secondArea")));
				}
			}
		}
		//查询未报管道数
		if("1".equals(paramType)){
			Map<String, Object> dateMap = this.getStartAndEndDate(st);
			String startDate = (String) dateMap.get("startDate");
			String endDate = (String) dateMap.get("endDate");
			String dateSql = " and to_char(n.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(n.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
			String sql =" select distinct n.par_da_pipe_id from da_pipe_normal_danger n " 
					+"   where n.is_deleted=0 " + dateSql + " and n.par_da_pipe_id is not null";
//			System.out.println("sql查询未报管道数"+sql);
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (" + sql + ")"));
		}
		
		//查询已报管道数
		if("2".equals(paramType)){
			Map<String, Object> dateMap = this.getStartAndEndDate(st);
			String startDate = (String) dateMap.get("startDate");
			String endDate = (String) dateMap.get("endDate");
			String dateSql = " and to_char(t.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(t.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
			String sql = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 " + dateSql + " group by t.par_da_pipe_id ";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql + ")"));
		}
				
		//查询自报管道数
		if("3".equals(paramType)){
			Map<String, Object> dateMap = this.getStartAndEndDate(st);
			String startDate = (String) dateMap.get("startDate");
			String endDate = (String) dateMap.get("endDate");
			String dateSql = " and to_char(t.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(t.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
			String sql = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 " + dateSql + " group by t.par_da_pipe_id ";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql + ")"));
			String sql2 = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 and t.user_id=(select cp.com_user_id from da_company_pass cp where cp.par_da_com_id=(select pipec.company_id from da_pipeline_companyinfo pipec where pipec.id=(select pipe.company_id from da_pipeline_info pipe where pipe.id=t.par_da_pipe_id))) " + dateSql + " group by t.par_da_pipe_id ";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql2 + ")"));
		}
		
		//查询代报管道数
		if("4".equals(paramType)){
			Map<String, Object> dateMap = this.getStartAndEndDate(st);
			String startDate = (String) dateMap.get("startDate");
			String endDate = (String) dateMap.get("endDate");
			String dateSql = " and to_char(t.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(t.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
			String sql = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 " + dateSql + " group by t.par_da_pipe_id ";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql + ")"));
			String sql2 = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 and t.user_id=(select cp.com_user_id from da_company_pass cp where cp.par_da_com_id=(select pipec.company_id from da_pipeline_companyinfo pipec where pipec.id=(select pipe.company_id from da_pipeline_info pipe where pipe.id=t.par_da_pipe_id))) " + dateSql + " group by t.par_da_pipe_id ";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (" + sql2 + ")"));
		}
		
		//查询零隐患报管道数
		if("5".equals(paramType)){
			Map<String, Object> dateMap = this.getStartAndEndDate(st);
			String startDate = (String) dateMap.get("startDate");
			String endDate = (String) dateMap.get("endDate");
			String dateSql = " and to_char(t.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(t.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
			String sql = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 " + dateSql + " group by t.par_da_pipe_id ";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql + ")"));
			//--总隐患管道数
			String sql2 = " select t.par_da_pipe_id from da_pipe_normal_danger t where t.is_deleted=0 " + dateSql + " and t.is_danger=1 group by t.par_da_pipe_id";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (" + sql2 + ")"));
		}
				
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dpli.id"));
		return pipeLinePersistenceIface.loadPipeLines(detachedCriteriaProxy, page);
	}
	
	/**
	 * 季报统计
	 */
	public Map<String, Object> loadQuarter(Statistic st) throws ApplicationAccessException {
		//统计参数
		FkArea area = loadArea(st.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		st.setAreaName(area.getAreaName());
		String areaType = areaRate == 4 ? "third_area" : "second_area"; st.setAreaType(areaType);
		//变量初始化
		String dateSql = ((st.getQuarter()>0) ? " year=" + st.getYear() + " and quarter=" + st.getQuarter() : " year=" + st.getYear() + " and quarter!=0");
		String areaType_0 = " case when (tc." + areaType + " is null or tc." + areaType + "<=0) then " + st.getAreaCode() + " else tc." + areaType + " end";
		String secondAreaSql = " ";
		if(areaType.equals("third_area")){
			secondAreaSql = " and tc.second_area=" + st.getAreaCode();
		}
		//绑定坏境变量
		String sql = quarterSql.replaceAll(":areaCode", String.valueOf(st.getAreaCode()))
							   .replaceAll(":nbAreaCode", String.valueOf(Nbyhpc.AREA_CODE))
							   .replaceAll(":secondAreaSql", secondAreaSql)
							   .replaceAll(":dateSql", dateSql)
							   .replaceAll(":areaType", areaType_0);
		log.info("quarterSql: " + sql);
		Map<String, Object> tmap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = null;
			ResultSet res = persistenceImpl.findBySql(sql);
			String type = ""; Long areaCode = 0l;
			int length = 0;
			while (res.next()) {
				if(!type.equals(res.getString(3))){
					if(length >0)
						 tmap.put("returnMap" + type, map);
					map = new HashMap<String, Object>();
					type = res.getString(3);
				}
				areaCode = res.getLong(2);
				map.put("s_" + type + "_" + areaCode + "_q", res.getInt(4));
				map.put("s_" + type + "_" + areaCode + "_y", res.getInt(5));
				map.put("s_" + type + "_" + areaCode + "_g", res.getInt(6));
				length ++;
			}
			if(length >0)
				 tmap.put("returnMap" + type, map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmap;
	}
	
	/**
	 * 排查情况统计
	 */
	public Map<String, Object> loadMass(Statistic st) throws ApplicationAccessException {
		Map<String, Object> dateMap = this.getStartAndEndDate(st);
		String startDate = (String) dateMap.get("startDate");
		String endDate = (String) dateMap.get("endDate");
		//统计参数
		FkArea area = loadArea(st.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		st.setAreaName(area.getAreaName());
		String areaType = areaRate == 4 ? "third_area" : "second_area"; st.setAreaType(areaType);
		//变量初始化
		String dateSql = " and to_char(t.create_time, 'yyyy-MM-dd')>='" + startDate + "' and to_char(t.create_time, 'yyyy-MM-dd')<'" + endDate + "'";
		String areaType_0 = " case when (tc." + areaType + " is null or tc." + areaType + "<=0) then " + st.getAreaCode() + " else tc." + areaType + " end";
		String secondAreaSql = " ";
		if(areaType.equals("third_area")){
			secondAreaSql = " and tc.second_area=" + st.getAreaCode();
		}
		String typeSql = "";
		String lineType = "100";
		if(st!=null && st.getRemark()!=null && !"".equals(st.getRemark())){
			if("fagai".equals(st.getRemark())){
				typeSql = " and t.type=1 ";lineType = "1";
			}else if("chengguan".equals(st.getRemark())){
				typeSql = " and (t.type=2 or t.type=0) ";lineType = "20";
			}else if("anjian".equals(st.getRemark())){
				typeSql = " and t.type=3 ";lineType = "3";
			}else if("jiaotong".equals(st.getRemark())){
				typeSql = " and t.type=4 ";lineType = "4";
			}
		}
		//绑定坏境变量
		String sql = massSql.replaceAll(":areaCode", String.valueOf(st.getAreaCode()))
							.replaceAll(":nbAreaCode", String.valueOf(Nbyhpc.AREA_CODE))
							.replaceAll(":secondAreaSql", secondAreaSql)
							.replaceAll(":areaType", areaType_0)
							.replaceAll(":dateSql", dateSql)
							.replaceAll(":typeSql", typeSql)
							.replaceAll(":lineType", lineType);
		//log.info("massSql: " + sql);
		Map<String, Object> tmap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = null;
			ResultSet res = persistenceImpl.findBySql(sql);
			String type = ""; Long areaCode = 0l;
			int length = 0;
			while (res.next()) {
				if(!type.equals(res.getString(3))){
					if(length >0)
						 tmap.put("returnMap" + type, map);
					map = new HashMap<String, Object>();
					type = res.getString(3);
				}
				areaCode = res.getLong(2);
				map.put("s_" + type + "_" + areaCode + "_q", res.getInt(4));
				map.put("s_" + type + "_" + areaCode + "_g", res.getInt(5));
				map.put("s_" + type + "_" + areaCode + "_y", res.getInt(6));
				map.put("s_" + type + "_" + areaCode + "_f", res.getInt(7));
				map.put("s_" + type + "_" + areaCode + "_z", res.getInt(8));
				length ++;
			}
			if(length >0)
				 tmap.put("returnMap" + type, map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmap;
	}
	
	/**
	 * 查询区域集合(适合管道季报和月报、报送情况统计) 查询包含本级区域和子区域
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas2(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		List<FkArea> list = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	    if(null == list){
	    	list = new ArrayList<FkArea>();
	    }
	    FkArea a = new FkArea();a.setAreaCode(areaCode); a.setAreaName("其他");
	    list.add(a);
		return list;
	}
	
	/**
	 * 查询区域集合
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		if(330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode){
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id = (select id from fk_area where area_code=" + areaCode + ")"));
		}else{
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}
	
	/**
	 * 查询区域对象
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public FkArea loadArea(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
		List<FkArea> areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		if (areas != null && areas.size() == 1) { return areas.get(0); }
		return null;
	}

	
	public List<Statistic> loadTroubleByNomalAndHidden(FkArea area, Statistic st)
			throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		cal.set(Calendar.YEAR, st.getYear());
		cal.set(Calendar.MONTH, st.getBeg_month()-1);
		String bgTime = sdf.format(cal.getTime());
		cal.set(Calendar.MONTH, st.getEnd_month());
		String endTime = sdf.format(cal.getTime());
		String type_sql = "";
		if(st.getType()!=null){
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(st.getType().equals(2)){
				type_sql=" and ((dpi.type=0 and dpi.line_type = 1) or dpi.type=2)";
			}else{
				type_sql=" and dpi.type="+st.getType();
			}
		}
//		CASE WHEN  dpnd.").append(areaType).append(" = 0 or dpnd.").append(areaType).append(" is NULL  THEN 330214000000  ELSE dpnd.").append(areaType).append(" END AS ").append(areaType).append("
//		CASE WHEN  dpd.").append(areaType).append(" = 0 or dpd.").append(areaType).append(" is NULL  THEN 330214000000  ELSE dpd.").append(areaType).append(" END AS ").append(areaType).append("
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String areaType2 = "";
		if(areaType.equals("third_area")){
			areaType2="second_area";
		}else{
			areaType2="third_area";
		}
		StringBuilder pipe_sql = new StringBuilder();
		pipe_sql.append("select area_code,sum(anum) anum,sum(bnum) bnum,sum(aanum) aanum,sum(bbnum) bbnum,sum(ccnum) ccnum from  ")
			.append(" (select fa.area_code,count(distinct(dnd.id)) anum,count(distinct(dnd_g.id)) bnum,0 aanum,0 bbnum,0 ccnum ")
			.append(" from   (select area_code from fk_area where is_deleted=0 and father_id = (select id from fk_area where  area_code= ").append(area.getAreaCode()).append(") or area_code = ").append(area.getAreaCode()).append(") fa  ")
			.append(" left join (select dpnd.id,dpnd.PAR_DA_PIPE_ID,CASE WHEN  (dpnd.").append(areaType).append(" = 0 or dpnd.").append(areaType).append(" is NULL) AND dpnd.").append(areaType2).append(" = ").append(area.getAreaCode()).append("   THEN ").append(area.getAreaCode()).append("  ELSE dpnd.").append(areaType).append(" END AS ").append(areaType).append(" from da_pipe_normal_danger dpnd LEFT JOIN DA_PIPELINE_INFO  dpi ON dpi.id = dpnd.PAR_DA_PIPE_ID where  dpnd.is_danger=1 ").append(type_sql).append(" and dpnd.is_deleted=0 AND dpi.is_deleted=0  and dpnd.create_time between to_date('").append(bgTime).append("','yyyy-MM') and to_date('").append(endTime).append("','yyyy-MM')) dnd on dnd.").append(areaType).append(" = fa.area_code")
			.append(" left join (select id,PAR_DA_PIPE_ID from da_pipe_normal_danger where is_deleted=0 and  is_danger=1 and is_repaired=1  and create_time between to_date('").append(bgTime).append("','yyyy-MM') and to_date('").append(endTime).append("','yyyy-MM')) dnd_g on dnd_g.id=dnd.id ")
			.append(" group by fa.area_code")
			.append(" union  ")
			.append(" select fa.area_code,0 anum,0 bnum,count(distinct(dd.id)) aanum,count(distinct(ddg.PAR_DA_PIPE_DAN_ID)) bbnum,count(distinct(dd_f.id)) ccnum ")
			.append(" from   (select area_code from fk_area where is_deleted=0 and father_id = (select id from fk_area where  area_code= ").append(area.getAreaCode()).append(") or area_code = ").append(area.getAreaCode()).append(") fa  ")
			.append(" left join (select dpd.id,dpd.PAR_DA_PIPE_ID,CASE WHEN  (dpd.").append(areaType).append(" = 0 or dpd.").append(areaType).append(" is NULL)  AND dpd.").append(areaType2).append(" = ").append(area.getAreaCode()).append(" THEN ").append(area.getAreaCode()).append("  ELSE dpd.").append(areaType).append(" END AS ").append(areaType).append(" from da_pipe_danger dpd LEFT JOIN DA_PIPELINE_INFO dpi ON dpi.id=dpd.PAR_DA_PIPE_ID where dpi.is_deleted = 0 ").append(type_sql).append(" and dpd.is_deleted=0  and dpd.create_time between to_date('").append(bgTime).append("','yyyy-MM') and to_date('").append(endTime).append("','yyyy-MM')) dd on dd.").append(areaType).append(" = fa.area_code ")
			.append(" left join (select id,PAR_DA_PIPE_DAN_ID from da_pipe_danger_gorver where is_deleted=0  and create_time between to_date('").append(bgTime).append("','yyyy-MM') and to_date('").append(endTime).append("','yyyy-MM')) ddg on ddg.PAR_DA_PIPE_DAN_ID = dd.id    ")
			.append(" left join (select id from da_pipe_danger where is_deleted=0 and finish_date < to_date('").append(mDateTime).append("','yyyy-MM-dd')  and id not in (select PAR_DA_PIPE_DAN_ID from da_pipe_danger_gorver where is_deleted=0)  and create_time between to_date('").append(bgTime).append("','yyyy-MM') and to_date('").append(endTime).append("','yyyy-MM')) dd_f on dd_f.id = dd.id  and dd.id is not null")
			.append(" group by fa.area_code) value ")
			.append(" group by value.area_code");
		ResultSet res = companyPersistenceIface.findBySql(pipe_sql.toString());
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setAnum(res.getInt(2));
				statistic.setBnum(res.getInt(3));
				statistic.setAanum(res.getInt(4));
				statistic.setBbnum(res.getInt(5));
				statistic.setCcnum(res.getInt(6));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<Statistic> loadTroubleByRollcall(FkArea area, Statistic st)
			throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		cal.set(Calendar.YEAR, st.getYear());
		cal.set(Calendar.MONTH, st.getBeg_month()-1);
		String bgTime = sdf.format(cal.getTime());
		cal.set(Calendar.MONTH, st.getEnd_month());
		String endTime = sdf.format(cal.getTime());
		String type_sql = "";
		if(st.getType()!=null){
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(st.getType().equals(2)){
				type_sql=" and ((dpi.type=0 and dpi.line_type = 1) or dpi.type=2)";
			}else{
				type_sql=" and dpi.type="+st.getType();
			}
		}
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String areaType2 = "";
		if(areaType.equals("third_area")){
			areaType2="second_area";
		}else{
			areaType2="third_area";
		}
		StringBuilder pipe_sql = new StringBuilder();
//		CASE WHEN dpd.").append(areaType).append(" = 0 OR dpd.").append(areaType).append(" IS NULL THEN ").append(area.getAreaCode()).append("	ELSE dpd.").append(areaType).append(" END AS ").append(areaType).append(",
		pipe_sql.append("select count(distinct(dd.id)) inhere,count(distinct(ddg.id)) num,  map.area_code,map.enum_code,map.enum_name  ")
				.append(" from (select fa.area_code,fe.enum_code,fe.enum_name,fe.grade_path  from fk_area fa   ")
				.append(" full join ( select enum_name,enum_code,grade_path from fk_enum where is_deleted=0 and   father_id = (select id from fk_enum where enum_code='rollcallCompany')) fe on 1!=2    where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= ").append(area.getAreaCode()).append(") or area_code = ").append(area.getAreaCode()).append(") map   ")
				.append(" left join (SELECT dprc.id,dpd.id as dpdid,CASE WHEN (dpd.").append(areaType).append(" = 0 OR dpd.").append(areaType).append(" IS NULL) AND dpd.").append(areaType2).append(" = ").append(area.getAreaCode()).append(" THEN ").append(area.getAreaCode()).append("	ELSE dpd.").append(areaType).append(" END AS ").append(areaType).append(",DPRC.LEVELS FROM DA_PIPE_ROLLCALL_COMPANY  dprc " +
						"LEFT JOIN DA_PIPE_DANGER dpd ON DPRC.PAR_DA_PIPE_DAN_ID = DPD.id " +
						"LEFT JOIN DA_PIPELINE_INFO dpi ON dpi.id = DPD.PAR_DA_PIPE_ID")
				.append(" WHERE DPRC.IS_DELETED =0 AND DPD.IS_DELETED = 0 ").append(type_sql).append(" AND DPRC.create_time BETWEEN TO_DATE('").append(bgTime).append("','yyyy-MM') AND TO_DATE('").append(endTime).append("','yyyy-MM'))  dd on  dd.").append(areaType).append(" = map.area_code   AND dd.LEVELS = map.enum_code")
				.append(" left join (select id,PAR_DA_PIPE_DAN_ID from DA_PIPE_DANGER_GORVER where is_deleted=0  and create_time between to_date('").append(bgTime).append("','yyyy-MM') and to_date('").append(endTime).append("','yyyy-MM')) ddg on ddg.PAR_DA_PIPE_DAN_ID = dd.dpdid   ")
				.append(" group by map.area_code,map.enum_code,map.enum_name,map.grade_path order by map.area_code,map.grade_path"); 
		
		ResultSet res = companyPersistenceIface.findBySql(pipe_sql.toString());
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setAreaCode(res.getLong(3));
				statistic.setEnumCode(res.getString(4));
				statistic.setEnumName(res.getString(5));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}
	
	public List<DaPipeDanger> loadDangerTroubleByTypeList(Statistic statistic,
			Pagination pagination, FkArea area)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDanger.class, "dpd");
		detachedCriteriaProxy.createCriteria("dpd.pipeLine", "pl").createAlias("pl.daPipelineCompanyinfo", "dc").createAlias("dc.company", "c").createAlias("c.daCompanyPass", "dcp");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		if (area != null && area.getAreaCode() != 0L) {
			int areaRate = area.getGradePath().split("/").length - 1;
			if(statistic.getParamType()!=null&&statistic.getParamType().equals("other")){
				if (areaRate == 4) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpd.secondArea", area.getAreaCode()));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.third_AREA =0 or this_.third_AREA is null)"));
				} else if (areaRate == 5) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpd.thirdArea", area.getAreaCode()));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.fouth_AREA =0 or this_.fouth_AREA is null)"));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpd.firstArea", area.getAreaCode()));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.second_AREA =0 or this_.second_AREA is null)"));
				}
			}else{
				if (areaRate == 4) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpd.secondArea", area.getAreaCode()));
				} else if (areaRate == 5) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpd.thirdArea", area.getAreaCode()));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpd.firstArea", area.getAreaCode()));
					//detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("dpd1_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
				}
			}
		}
		if (statistic != null) {
			if (statistic.getCompanyName() != null && !"".equals(statistic.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("c.companyName", statistic.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (statistic.getPipeName() != null && !"".equals(statistic.getPipeName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("pl.name", statistic.getPipeName().trim(), MatchMode.ANYWHERE));
			}
			String sqlParam = "";
			String sqlParam_enumCode = "";
			int nextYear = statistic.getYear() + 1;
			if (statistic.getQuarter() == 1) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-04-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 2) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-07-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 3) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-10-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 4) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
			sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			if(statistic.getBeg_month()>0&&statistic.getEnd_month()>0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				cal.set(Calendar.YEAR, statistic.getYear());
				cal.set(Calendar.MONTH, statistic.getBeg_month()-1);
				String bgTime = sdf.format(cal.getTime());
				cal.set(Calendar.MONTH, statistic.getEnd_month());
				String endTime = sdf.format(cal.getTime());
				sqlParam = " this_.create_time between to_date('" + bgTime + "','yyyy-MM') and to_date('" + endTime + "','yyyy-MM')";
				sqlParam_enumCode = " create_time between to_date('" + bgTime + "','yyyy-MM') and to_date('" + endTime + "','yyyy-MM') ";
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
			String type_sql = "";
			if(statistic.getType()!=null){
				if(statistic.getType().equals(2)){
					type_sql=" ((pl1_.type=0 and pl1_.line_type = 1) or pl1_.type=2)";
				}else{
					type_sql=" pl1_.type="+statistic.getType();
				}
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(type_sql));
			}
			if (statistic.getIsRollcall() != null && !"".equals(statistic.getIsRollcall().trim())) {
				if ("0".equals(statistic.getIsRollcall().trim())) {
					if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
						if ("0".equals(statistic.getIsGorver().trim())) {
							detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select PAR_DA_PIPE_DAN_ID from da_pipe_danger_gorver where is_deleted=0)"));
						}  else if ("2".equals(statistic.getIsGorver().trim())) {
							detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date < to_date('" + mDateTime
							        + "','yyyy-MM-dd') and this_.id not in (select PAR_DA_PIPE_DAN_ID from da_pipe_danger_gorver where is_deleted=0)"));
						}
					}
				} else if (statistic.getEnumCode() != null && !"".equals(statistic.getEnumCode().trim()) && "1".equals(statistic.getIsRollcall().trim())) {
//					sqlParam = " and create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select PAR_DA_PIPE_DAN_ID from da_pipe_rollcall_company where is_deleted=0 and levels = '"
					        + statistic.getEnumCode() + "' and " + sqlParam_enumCode + ")"));
					if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select PAR_DA_PIPE_DAN_ID from da_pipe_danger_gorver where is_deleted=0)"));
					}
				}
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("c.id"));
		return pipeDangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}
	
	public List<DaPipeNomalDanger> loadNomalTroubleByTypeList(
			Statistic statistic, Pagination pagination, FkArea area)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dpnd");
		detachedCriteriaProxy.createCriteria("dpnd.pipeLine", "pl").createAlias("pl.daPipelineCompanyinfo", "dc").createAlias("dc.company", "c");
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if(statistic.getParamType()!=null&&statistic.getParamType().equals("other")){
				if (areaRate == 4) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.secondArea", area.getAreaCode()));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.third_AREA =0 or this_.third_AREA is null)"));
				} else if (areaRate == 5) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.thirdArea", area.getAreaCode()));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.fouth_AREA =0 or this_.fouth_AREA is null)"));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.firstArea", area.getAreaCode()));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.second_AREA =0 or this_.second_AREA is null)"));
				}
			}else{
				if (areaRate == 4) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.secondArea", area.getAreaCode()));
				} else if (areaRate == 5) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.thirdArea", area.getAreaCode()));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.firstArea", area.getAreaCode()));
					//detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
					//System.out.println("一般区域: "+"SECOND_AREA in (select a.area_code from fk_area a where a.father_id="+area.getId()+")");
				}
			}
		}
		if (statistic != null) {
			if (statistic.getCompanyName() != null && !"".equals(statistic.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("c.companyName", statistic.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (statistic.getPipeName() != null && !"".equals(statistic.getPipeName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("pl.name", statistic.getPipeName().trim(), MatchMode.ANYWHERE));
			}
			String sqlParam = "";
			int nextYear = statistic.getYear() + 1;
			if (statistic.getQuarter() == 1) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-04-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 2) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-07-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 3) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-10-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 4) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
			sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			if(statistic.getBeg_month()>0&&statistic.getEnd_month()>0){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				cal.set(Calendar.YEAR, statistic.getYear());
				cal.set(Calendar.MONTH, statistic.getBeg_month()-1);
				String bgTime = sdf.format(cal.getTime());
				cal.set(Calendar.MONTH, statistic.getEnd_month());
				String endTime = sdf.format(cal.getTime());
				sqlParam = " this_.create_time between to_date('" + bgTime + "','yyyy-MM') and to_date('" + endTime + "','yyyy-MM')";
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
			String type_sql = "";
			if(statistic.getType()!=null){
				if(statistic.getType().equals(2)){
					type_sql=" ((pl1_.type=0 and pl1_.line_type = 1) or pl1_.type=2)";
				}else{
					type_sql=" pl1_.type="+statistic.getType();
				}
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(type_sql));
			}
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.repaired", false));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dpnd.danger", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("c.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dpnd.id"));
		return pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
	}
	
	
	/**
	 * 提取重大隐患最新的治理时间
	 * 
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeDangerGorver> loadDangerGorversOfOne(DaPipeDanger daPipeDanger) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDangerGorver.class, "ddg");
		if (daPipeDanger != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("ddg.daPipeDanger.id", daPipeDanger.getId()));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return pipeDangerGorverPersistenceIface.loadDangerGorvers(detachedCriteriaProxy, null);
	}
	
	/**
	 * 查询重大隐患挂牌列表
	 * 
	 * @param danger
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeRollcallCompany> loadRollcallCompanies(DaPipeDanger danger, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallCompany.class, "drc");
		detachedCriteriaProxy.createAlias("drc.daPipeDanger", "dd").createAlias("dd.pipeLine", "pl").createAlias("pl.daPipelineCompanyinfo", "di").createAlias("di.company", "dc").createAlias("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (danger != null) {
			if (danger.getId() != null && !"".equals(danger.getId())) {
				if (danger.getId() != -1) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.id", danger.getId()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}
	
	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setPipeDangerPersistenceIface(
			PipeDangerPersistenceIface pipeDangerPersistenceIface) {
		this.pipeDangerPersistenceIface = pipeDangerPersistenceIface;
	}

	public void setPipeNomalDangerPersistenceIface(
			PipeNomalDangerPersistenceIface pipeNomalDangerPersistenceIface) {
		this.pipeNomalDangerPersistenceIface = pipeNomalDangerPersistenceIface;
	}

	public void setPipeRollcallCompanyPersistenceIface(
			PipeRollcallCompanyPersistenceIface pipeRollcallCompanyPersistenceIface) {
		this.pipeRollcallCompanyPersistenceIface = pipeRollcallCompanyPersistenceIface;
	}

	public void setPipeDangerGorverPersistenceIface(
			PipeDangerGorverPersistenceIface pipeDangerGorverPersistenceIface) {
		this.pipeDangerGorverPersistenceIface = pipeDangerGorverPersistenceIface;
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public String getMonthSql() {
		return monthSql;
	}

	public void setMonthSql(String monthSql) {
		this.monthSql = monthSql;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getQuarterSql() {
		return quarterSql;
	}

	public void setQuarterSql(String quarterSql) {
		this.quarterSql = quarterSql;
	}

	public String getMassSql() {
		return massSql;
	}

	public void setMassSql(String massSql) {
		this.massSql = massSql;
	}

	public String getDangerSql() {
		return dangerSql;
	}

	public void setDangerSql(String dangerSql) {
		this.dangerSql = dangerSql;
	}

	public void setPipeLinePersistenceIface(
			PipeLinePersistenceIface pipeLinePersistenceIface) {
		this.pipeLinePersistenceIface = pipeLinePersistenceIface;
	}

	public String getNomalDangerSql() {
		return nomalDangerSql;
	}

	public void setNomalDangerSql(String nomalDangerSql) {
		this.nomalDangerSql = nomalDangerSql;
	}
	
	
}
