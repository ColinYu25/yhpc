/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.MatchMode;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bjsxt.hibernate.MemCached;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.ProjectionsProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyHis;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDanger1;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.model.DaItemDanger1;
import com.safetys.nbyhpc.domain.model.DaItemHis;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.DaNomalDanger1;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaTrouble;
import com.safetys.nbyhpc.domain.model.FkAreaHis;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.ActualizeTableNoticePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyHisPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.Danger1PersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerGorverPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.FkAreaHisPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.NomalDanger1PersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.NomalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PubCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TCachePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.ConnectionFactory;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.MonthQueryHelper;
import com.safetys.nbyhpc.util.Nbyhpc;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @date 2011-05-26 排查质量统计企业查看列表加入月份条件 @author lvx
 * @author lvx
 * @date 2012-2-29 zhoux
 *       在季度统计中，增加时间条件，使得在注册时间在所查询季度时间之后的企业不显示在统计列表中，修改地方有zhoux标识
 */
public class StatisticFacadeImpl implements StatisticFacadeIface {
	private static String XMLURL = null;

	private static List<Long> areaCodeList = null;
	private static List<Long> areaCodeZhjgList = null;
	private CompanyPersistenceIface companyPersistenceIface;
	private TCachePersistenceIface tcachePersistenceIface;
	private AreaPersistenceIface areaPersistenceIface;
	private DangerPersistenceIface dangerPersistenceIface;
	private Danger1PersistenceIface danger1PersistenceIface;
	private InputStream inputStream;

	private File tempFile;

	private Writer out;

	private String realPath;

	private String filePath;

	private String fileName;

	private NomalDangerPersistenceIface nomalDangerPersistenceIface;

	private NomalDanger1PersistenceIface nomalDanger1PersistenceIface;

	private ItemDangerPersistenceIface itemDangerPersistenceIface;

	private ItemPersistenceIface itemPersistenceIface;

	private ActualizeTableNoticePersistenceIface actualTableNoticePersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private RollcallCompanyPersistenceIface rollcallCompanyPersistenceIface;

	private DangerGorverPersistenceIface dangerGorverPersistenceIface;

	private CompanyHisPersistenceIface companyHisPersistenceIface;

	private FkAreaHisPersistenceIface fkAreaHisPersistenceIface;

	private PubCompanyPersistenceIface pubCompanyPersistenceIface;

	private Logger log = Logger.getLogger(this.getClass());

	private static ConnectionFactory cFactory = new ConnectionFactory();

	public void setNomalDangerPersistenceIface(NomalDangerPersistenceIface nomalDangerPersistenceIface) {
		this.nomalDangerPersistenceIface = nomalDangerPersistenceIface;
	}

	public void setDangerPersistenceIface(DangerPersistenceIface dangerPersistenceIface) {
		this.dangerPersistenceIface = dangerPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public Long createActualizeProject(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		return actualTableNoticePersistenceIface.createDaActualTableNotice(daActualTableNotice);
	}

	/**
	 * lisl(提取重大隐患最新的治理时间)
	 * 
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaDangerGorver> loadDangerGorversOfOne(DaDanger daDanger) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDangerGorver.class, "ddg");
		if (daDanger != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("ddg.daDanger.id", daDanger.getId()));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dangerGorverPersistenceIface.loadDangerGorvers(detachedCriteriaProxy, null);
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
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}

	/**
	 * 查询区域集合给首页统计用
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas1(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}

	/**
	 * 查询区域集合给首页统计用(工程项目的区域分组，特别注意这里不能随便修改，如果要修改，必须要验证修改后的工程项目的统计是否正确)
	 * 
	 */
	public List<FkArea> loadAreasByGroupNum(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ") order by group_num,sort_num"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}

	/**
	 * 查询区域对象,如果从查询不到的话,先通过xml匹配,然后再查
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public FkArea loadAreaByAreaCodeZhjg(Long areaCode) throws ApplicationAccessException {
		FkArea area = loadArea(areaCode);
		if (area == null) {
			if (areaCodeList == null && areaCodeZhjgList == null) {
				loadAreasFromXML();
			}
			// 综合监管平台的code收不到地区
			// 先转成隐患排查的code,然后再去查找地区
			int index = 0;
			for (int i = 0; i < areaCodeZhjgList.size(); i++) {
				if (areaCode.equals(areaCodeZhjgList.get(i))) {
					index = i;
					break;
				}
			}
			Long newAreaCode = areaCodeList.get(index);
			return loadArea(newAreaCode);
		}
		return area;
	}

	/**
	 * 通过area_code读取综合监管平台的area_code
	 */
	public Long loadAreaCodeZhjgByAreaCode(Long areaCode) {
		Long newAreaCode = 0L;
		int index = -1;
		if (areaCodeList == null && areaCodeZhjgList == null) {
			loadAreasFromXML();
		}
		for (int i = 0; i < areaCodeList.size(); i++) {
			if (areaCode.equals(areaCodeList.get(i))) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			newAreaCode = areaCodeZhjgList.get(index);
			return newAreaCode;
		} else
			return areaCode;
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

		if (areas != null && areas.size() == 1) {
			return areas.get(0);
		}

		return null;
	}

	/**
	 * 查询区域对象1
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public FkArea loadArea(Long areaCode, Boolean reFresh) throws ApplicationAccessException {
		// liulj 加入MemCached缓存设置

		MemCached cache = MemCached.getInstance();
		if ((reFresh == null || reFresh != true) && cache.get("nbyhpc_loadArea_" + areaCode) != null) {
//			System.out.println("缓存区域: " + "nbyhpc_loadArea_" + areaCode);
			return (FkArea) cache.get("nbyhpc_loadArea_" + areaCode);

		} else {

			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
			List<FkArea> areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);

			if (areas != null && areas.size() == 1) {
				cache.set("nbyhpc_loadArea_" + areaCode, areas.get(0), new Date(Nbyhpc.EXPIRATION_TIME));
				return areas.get(0);
			}

		}

		return null;
	}

	/**
	 * 累计排查进度统计
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadPaiChaOfCompany(FkArea area, Statistic st, int current_quarter, String secondStatistic) throws ApplicationAccessException {
		String cacheName = "";
		String cacheNum = "0";
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sqlCondition = "";
		String sqlTimeCondition = "";// zhoux增加时间条件：企业创建时间在查询季度统计的时间之后
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		// add by huangjl
		// String zjTypeSql =
		// "  and dci.par_da_ind_id in(select id from da_industry_parameter par where par.zj_type is not null ) ";
		String zjTypeSql = "  and dci.par_da_ind_id in(select id from da_industry_parameter par ) ";
		int backup_date = 0; // 历史表查询的日期
		if (st != null) {
			if (st.getMonth() != 0) { // 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else if (st.getQuarter() != 0) { // 季报
				// System.out.println("季报");
				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}

					} else if (st.getYear() == 2013) {

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";

						if (st.getQuarter() == 4) {
							backup_date = 201312;
						} else {
							backup_date = 201311;
						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else if (st.getQuarter() < current_quarter) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (st.getQuarter() == 4) {
						backup_date = Integer.parseInt(st.getYear() + "12");

					} else if (st.getQuarter() == 3) {
						backup_date = Integer.parseInt(st.getYear() + "09");

					} else if (st.getQuarter() == 2) {
						backup_date = Integer.parseInt(st.getYear() + "06");

					} else if (st.getQuarter() == 1) {
						backup_date = Integer.parseInt(st.getYear() + "03");

					}

				} else {
					// System.out.println("动态表");
				}

				if ((current_quarter == st.getQuarter()) && (st.getMonth() == 0)) {
					st.setMonth(month);
				}

			} else { // 年报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = Integer.parseInt(st.getYear() + "12");

					} else if (st.getYear() == 2013) {

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";

						backup_date = 201312;
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else {
					// System.out.println("动态表");
				}
			}

			MonthQueryHelper helper = new MonthQueryHelper(st.getYear(), st.getQuarter(), st.getMonth());

			sqlCondition = " and create_time between to_date('" + DateUtils.date2Str(helper.getBeginDate(), null) + "','yyyy-MM-dd') and to_date('" + DateUtils.date2Str(helper.getEndDate(), null) + "','yyyy-MM-dd')";
			// sqlTimeCondition = " and create_time <= to_date('" +
			// DateUtils.date2Str(helper.getEndDate(), null) +
			// "','yyyy-MM-dd')";// zhoux设置创建时间限制

			// modify by huangjl 如果月份为空的话，则取当前月份。
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
			// add by huangjl
			int nowY = nowCalendar.get(Calendar.YEAR);
			int newM = nowCalendar.get(Calendar.MONTH) + 1;
			int nowQ = 0;
			// 再判断季度是否相等
			if (newM == 1 || newM == 2 || newM == 3) {
				nowQ = 1;
			} else if (newM == 4 || newM == 5 || newM == 6) {
				nowQ = 2;
			} else if (newM == 7 || newM == 8 | newM == 9) {
				nowQ = 3;
			} else {
				nowQ = 4;
			}
			if(st.getQuarter()==0&&st.getMonth()==0){
				//查询全年的记录时，企业小于当年年份12月录入的企业
				sqlTimeCondition = " and create_time < to_date('" + st.getYear() + "-12-01','yyyy-MM-dd')";
			}else{
				// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
				if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0) && st.getMonth() == 0) {

					sqlTimeCondition = " and create_time < to_date('" + DateUtils.date2Str(nowCalendar.getTime(), null) + "','yyyy-MM-dd')";// liul
				} else {
					sqlTimeCondition = " and create_time < to_date('" + DateUtils.date2Str(helper.getNowDate(), null) + "','yyyy-MM-dd')";// liulj//
																																			// 过滤当月添加的数据
				}
				
			}
			
			

			if (st.getYear() == year && month == st.getMonth()) {
				cacheNum = "0";
			} else {
				if (st.getMonth() == 0) {
					int q = st.getQuarter() + 12;
					cacheNum = "" + st.getYear() + q;
				} else {
					cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
				}
			}

		}

		// liulj 加入MemCached缓存设置
		cacheName = "nbyhpc_loadPaiChaOfCompany_" + area.getAreaCode() + cacheNum;
		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存:" + cacheName);
		} else {

			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			String areaType = areaRate == 4 ? "third_area" : "second_area";
			String sql = "select area_code,sum(w) total,sum(r) inhere,sum(g) numbers,sum(gr),sum(wz),sum(rz),sum(gz),sum(grz),sum(zero) zero from ( " + " select fa.area_code,count(distinct(dci.par_da_com_id)) w, 0 r,0 g,0 gr,0 wz, 0 rz,0 gz,0 grz,0 zero  from  ("
			// 高 危 企 业a
					+ " select area_code from " + fk_area + "  where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join (select par_da_com_id from " + da_company_industry_rel + "  where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.WEIHUA + ")) dci on dci.par_da_com_id = dp.par_da_com_id " + " group by fa.area_code   " + " union "
					// 人员密集场所b
					+ " select fa.area_code,0 w,count(distinct(dci.par_da_com_id)) r,0 g,0 gr,0 wz, 0 rz,0 gz,0 grz,0 zero  from  " + " (select area_code from " + fk_area + "  where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) fa  " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join (select par_da_com_id from " + da_company_industry_rel + "  where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + "par_da_ind_id in (" + Nbyhpc.RENYUANMIJI + ")) dci on dci.par_da_com_id = dp.par_da_com_id " + " group by fa.area_code   " + " union "
					// 所有规模以上企业c
					+ " select fa.area_code,0 w,0 r,count(distinct(dci.par_da_com_id)) g,0 gr,0 wz, 0 rz,0 gz,0 grz,0 zero  from  " + " (select area_code from " + fk_area + "  where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa  " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1 and is_enterprise=1) dp on dp.par_da_com_id =da.id " + " left join  " + da_company_industry_rel + " dci on dci.par_da_com_id = dp.par_da_com_id " + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  dci.backup_date=" + backup_date + "   " : "") + " group by fa.area_code " + " union "
					// 高危企业和人员密集场所中的规模以上企业d
					+ " select fa.area_code,0 w,0 r, 0 g,count(distinct(dci.par_da_com_id)) gr,0 wz, 0 rz,0 gz,0 grz,0 zero  from  " + " (select area_code from " + fk_area + "   where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1 and is_enterprise=1) dp on dp.par_da_com_id =da.id  " + " left join (select par_da_com_id from " + da_company_industry_rel + "  where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + " and  " : "") + " par_da_ind_id in (" + Nbyhpc.WEIHUA + "," + Nbyhpc.RENYUANMIJI + ")) dci on dci.par_da_com_id = dp.par_da_com_id " + " group by fa.area_code   " + " union  "
					// qiyeaa
					+ " select fa.area_code,0 w, 0 r,0 g,0 gr,count(distinct(dn.par_da_com_id)) wz, 0 rz,0 gz,0 grz,0 zero  from  " + " (select area_code from " + fk_area + "   where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area  from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join " + da_company_industry_rel + " dci  on dci.par_da_com_id =  dp.par_da_com_id " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 and user_id in " + "(select id from fk_user_info where is_deleted=0 and user_industry='qiye') " + sqlCondition + ") dn on dn.par_da_com_id=dci.par_da_com_id  " + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  dci.backup_date=" + backup_date + "   " : "") + " group by fa.area_code  " + " union "
					// 所有企业bb
					+ " select fa.area_code,0 w, 0 r,0 g,0 gr,0 wz, count(distinct(dci.par_da_com_id)) rz,0 gz,0 grz,0 zero  from  " + " (select area_code from " + fk_area + "   where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area  from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join " + da_company_industry_rel + " dci  on dci.par_da_com_id =  dp.par_da_com_id  where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  dci.backup_date=" + backup_date + "  and " : "") + "dci.par_da_com_id is not null " + zjTypeSql + "" + " group by fa.area_code   " + " union  "
					// 未排查单位数cc
					+ " select fa.area_code,0 w, 0 r,0 g,0 gr,0 wz, 0 rz,count(distinct(dn.par_da_com_id))+count(distinct(dn.pid)) gz,0 grz,0 zero  from  " + " (select area_code from " + fk_area + "   where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area  from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join " + da_company_industry_rel + " dci  on dci.par_da_com_id =  dp.par_da_com_id " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlCondition + ") dn on dn.par_da_com_id=dci.par_da_com_id   "

					/**
					 * 新加入上报重大隐患数liu
					 */

					+ " left join (select par_da_com_id as pid from da_danger where is_deleted=0 " + sqlCondition + "and   par_da_com_id not  in      (select par_da_com_id      from da_normal_danger    where is_deleted = 0" + "and user_id in     (select id   from fk_user_info   where is_deleted = 0    and user_industry = 'qiye')" + sqlCondition + ") " + " ) dn on pid=dci.par_da_com_id   "

					//

					+ " " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  dci.backup_date=" + backup_date + "   " : "") + " group by fa.area_code  "
					/**
					 * + " union " +
					 * " select fa.area_code,0 w,0 r,0 g,0 gr,0 wz, 0 rz,0 gz,count(distinct(dn.par_da_com_id)) grz,0 zero from  "
					 * +
					 * " (select area_code from fk_area  where is_deleted=0 and father_id = (select id from fk_area where  area_code= "
					 * + area.getAreaCode()+")) fa   " +
					 * " left join (select id,second_area,third_area,fouth_area from da_company where is_deleted=0 ) da on da."
					 * +areaType+" = fa.area_code " +
					 * " left join (select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm=1 and is_enterprise=1) dp on dp.par_da_com_id =da.id  "
					 * +
					 * " left join (select par_da_com_id from da_company_industry_rel  where par_da_ind_id in ("
					 * +Nbyhpc.WEIHUA+","+Nbyhpc.RENYUANMIJI+
					 * ")) dci on dci.par_da_com_id = dp.par_da_com_id " +
					 * " left join (select par_da_com_id from da_normal_danger where is_deleted=0 "
					 * +sqlCondition+
					 * ") dn on dn.par_da_com_id=dci.par_da_com_id  " +
					 * " group by fa.area_code   "
					 */
					+ " union "
					// 零隐患dd
					+ " select fa.area_code,0 w, 0 r,0 g,0 gr,0 wz, 0 rz,0 gz,0 grz,count(distinct(dn.par_da_com_id)) zero from  " + " (select area_code from " + fk_area + "  where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area  from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join  " + da_company_industry_rel + " dci on dci.par_da_com_id = dp.par_da_com_id " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0  " + sqlCondition + " group by par_da_com_id having sum(is_danger)<1) dn on dn.par_da_com_id=dci.par_da_com_id  " + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  dci.backup_date=" + backup_date + "   " : "") + " group by fa.area_code   " + " ) value group by area_code";

//			System.out.println("月报sql:" + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);
			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setAnum(res.getInt(2));
					statistic.setBnum(res.getInt(3));
					statistic.setCnum(res.getInt(4));
					statistic.setDnum(res.getInt(5));

					statistic.setAanum(res.getInt(6));
					statistic.setBbnum(res.getInt(7));
					statistic.setCcnum(res.getInt(8));
					statistic.setDdnum(res.getInt(9));
					statistic.setZero(res.getInt(10));
					statistics.add(statistic);
				}
				res.getStatement().close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
			ResultSet rs = null;
			try {
				if (c.next()) { // 已存在,更新
					PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各地月报" + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
					rs = pState.executeQuery();
				} else { // 新建
					PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各地月报" + area.getAreaCode() + cacheNum + "',sysdate)");
					rs = pState.executeQuery();
				}
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (backup_date == 0) { // 动态的 设置过期时间
				cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
			} else { // 永不过期
				cache.add(cacheName, statistics);
			}

		}

		return statistics;
	}

	public List<Statistic> loadQuarterOfCompany(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		// String sqlCondition = "";
		String sqlTimeCondition = "";// zhoux增加时间条件：企业创建时间在查询季度统计的时间之后
		String cacheName = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String cacheNum = "0";
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String BACKUP_DATE = ""; // 历史表查询的日期
		if (st != null) {

			// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
			if (st.getYear() != year || current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																				// ,则取历史表
				// 2013_11开始备份

				if (st.getQuarter() == 0) { // 全年

					if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																		// 2013.11当年取动态
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						BACKUP_DATE = "201311";
					} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																				// 2013(包括)之后取那年的12月份
																				// yyyy-12
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						BACKUP_DATE = st.getYear() + "12";
					}

				} else { // 季度

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
																															// 未做备份,则取最早备份日期2013_11
						BACKUP_DATE = "201311";
					} else if (st.getQuarter() == 4) {
						BACKUP_DATE = st.getYear() + "12";

					} else if (st.getQuarter() == 3) {
						BACKUP_DATE = st.getYear() + "09";

					} else if (st.getQuarter() == 2) {
						BACKUP_DATE = st.getYear() + "06";

					} else if (st.getQuarter() == 1) {
						BACKUP_DATE = st.getYear() + "03";

					}
				}
			}

			MonthQueryHelper helper = new MonthQueryHelper(st.getYear(), st.getQuarter(), st.getMonth());
			// sqlCondition = " and create_time between to_date('" +
			// DateUtils.date2Str(helper.getBeginDate(), null) +
			// "','yyyy-MM-dd') and to_date('"
			// + DateUtils.date2Str(helper.getEndDate(), null) +
			// "','yyyy-MM-dd')";

			if (da_company.equals("da_company_his")) {
				sqlTimeCondition += " and backup_date=" + BACKUP_DATE + "  ";
			}

			// modify by huangjl 如果月份为空的话，则取当前月份。
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
			// add by huangjl
			int nowY = nowCalendar.get(Calendar.YEAR);
			int newM = nowCalendar.get(Calendar.MONTH) + 1;
			int nowQ = 0;
			// 再判断季度是否相等
			if (newM == 1 || newM == 2 || newM == 3) {
				nowQ = 1;
			} else if (newM == 4 || newM == 5 || newM == 6) {
				nowQ = 2;
			} else if (newM == 7 || newM == 8 | newM == 9) {
				nowQ = 3;
			} else {
				nowQ = 4;
			}
			// 如果当前年份和季度都相等的话，则过滤到当年月的数据;或者年度相等，季度为0，也要过滤掉
			if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0)) {
				sqlTimeCondition += " and create_time < to_date('" + DateUtils.date2Str(nowCalendar.getTime(), null) + "','yyyy-MM-dd')";// zhoux设置创建时间限制
			} else {
				sqlTimeCondition += " and create_time < to_date('" + DateUtils.date2Str(helper.getNowDate(), null) + "','yyyy-MM-dd')";// zhoux设置创建时间限制
			}

			if (st.getYear() == year && current_quarter == st.getQuarter()) {
				cacheNum = "0";
			} else {
				int q = st.getQuarter() + 12;
				cacheNum = "" + st.getYear() + q;
			}

		}

		// liulj 加入MemCached缓存设置

		cacheName = "nbyhpc_loadQuarter_" + st.getRemark() + area.getAreaCode() + cacheNum;
		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存" + cacheName);
		} else {

			// add by huangjl
			// String zjTypeSql =
			// "  and dci.par_da_ind_id in(select id from da_industry_parameter par where par.zj_type is not null ) ";
			String zjTypeSql = "  and dci.par_da_ind_id in(select id from da_industry_parameter par ) ";
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			String areaType = areaRate == 4 ? "third_area" : "second_area";
			String sql = "select area_code,sum(w) total,sum(r) inhere,sum(g) numbers,sum(gr),sum(wz),sum(rz) from ( " + " select fa.area_code,count(distinct(dci.par_da_com_id)) w, 0 r,0 g,0 gr,0 wz, 0 rz  from  ("
			// 高 危 企 业a
					+ " select area_code from " + fk_area + "  where is_deleted=0 " + (fk_area.equals("fk_area_his") ? " and backup_date=" + BACKUP_DATE + " " : "") + " and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + BACKUP_DATE + " and " : "") + " area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + " where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + BACKUP_DATE + " and  " : "") + " is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join (select par_da_com_id from " + da_company_industry_rel + "  where  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.WEIHUA + ")) dci on dci.par_da_com_id = dp.par_da_com_id " + " group by fa.area_code   " + " union "
					// 人员密集场所b
					+ " select fa.area_code,0 w,count(distinct(dci.par_da_com_id)) r,0 g,0 gr,0 wz, 0 rz  from  " + " (select area_code from " + fk_area + "   where is_deleted=0  " + (fk_area.equals("fk_area_his") ? " and backup_date=" + BACKUP_DATE + " " : "") + "  and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa  " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + "  where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + BACKUP_DATE + " and  " : "") + "  is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join (select par_da_com_id from " + da_company_industry_rel + "  where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.RENYUANMIJI + ")) dci on dci.par_da_com_id = dp.par_da_com_id " + " group by fa.area_code   " + " union "
					// 所有规模以上企业c
					+ " select fa.area_code,0 w,0 r,count(distinct(dci.par_da_com_id)) g,0 gr,0 wz, 0 rz  from  " + " (select area_code from " + fk_area + "  where is_deleted=0 " + (fk_area.equals("fk_area_his") ? " and backup_date=" + BACKUP_DATE + " " : "") + " and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa  " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + "  where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + BACKUP_DATE + " and  " : "") + "  is_deleted=0 and is_affirm=1 and is_enterprise=1) dp on dp.par_da_com_id =da.id " + " left join  " + da_company_industry_rel + " dci on dci.par_da_com_id = dp.par_da_com_id " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where dci.backup_date=" + BACKUP_DATE + "  " : "") + "" + " group by fa.area_code " + " union "
					// 高危企业和人员密集场所中的规模以上企业d
					+ " select fa.area_code,0 w,0 r, 0 g,count(distinct(dci.par_da_com_id)) gr,0 wz, 0 rz  from  " + " (select area_code from " + fk_area + "  where is_deleted=0 " + (fk_area.equals("fk_area_his") ? " and backup_date=" + BACKUP_DATE + " " : "") + "  and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area from " + da_company + " where is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + "  where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + BACKUP_DATE + " and  " : "") + "  is_deleted=0 and is_affirm=1 and is_enterprise=1) dp on dp.par_da_com_id =da.id  " + " left join (select par_da_com_id from " + da_company_industry_rel + "  where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + "par_da_ind_id in (" + Nbyhpc.WEIHUA + "," + Nbyhpc.RENYUANMIJI + ")) dci on dci.par_da_com_id = dp.par_da_com_id " + " group by fa.area_code   " + " union  "

					// 所有企业bb
					+ " select fa.area_code,0 w, 0 r,0 g,0 gr,count(distinct(rep.company_id)) wz, count(distinct(dci.par_da_com_id)) rz  from  " + " (select area_code from " + fk_area + "  where is_deleted=0 " + (fk_area.equals("fk_area_his") ? " and backup_date=" + BACKUP_DATE + " " : "") + "  and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa   " + " left join (select id,second_area,third_area,fouth_area  from " + da_company + " where is_deleted=0 " + sqlTimeCondition + ") da on da." + areaType + " = fa.area_code  "// zhoux设置创建时间限制，增加代码为"+sqlTimeCondition+"
					+ " left join (select par_da_com_id from " + da_company_pass + "  where " + (da_company_pass.equals("da_company_pass_his") ? "  backup_date=" + BACKUP_DATE + " and  " : "") + " is_deleted=0 and is_affirm=1) dp on dp.par_da_com_id =da.id  " + " left join " + da_company_industry_rel + " dci  on dci.par_da_com_id =  dp.par_da_com_id  " + " left join (select company_id from da_company_quarter_report where year = " + st.getYear() + " and quarter = " + st.getQuarter() + ") rep on  rep.company_id = da.id where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  dci.backup_date=" + BACKUP_DATE + "  and " : "") + " dci.par_da_com_id is not null " + zjTypeSql + " " + " group by fa.area_code   "

					+ " ) value group by area_code";

			// System.out.println("季度统计sql: " + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);
			// ResultSet res=null;
			// try {
			// res =
			// cFactory.createConnection().prepareStatement(sql).executeQuery();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setAnum(res.getInt(2));
					statistic.setBnum(res.getInt(3));
					statistic.setCnum(res.getInt(4));
					statistic.setDnum(res.getInt(5));

					statistic.setReportedNum(res.getInt(6));
					statistic.setBbnum(res.getInt(7));
					statistics.add(statistic);
				}

				if (BACKUP_DATE.equals("")) { // 动态的 2000*60*60=2小时过期 ,现为 //
												// 1000=1秒
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.set(cacheName, statistics);
				}
				res.getStatement().close();
				res.close();

				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
				ResultSet rs = null;
				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各地季报" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各地季报" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.getStatement().close();
					rs.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return statistics;
	}

	/**
	 * 累计排查进度统计企业列表
	 * 
	 * @param company
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadPaiChaOfCompanyList(DaCompany company, Pagination pagination, FkArea area, int current_quarter, String statisicLevel, UserDetailWrapper userDetail) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);

		String da_company = "da_company";
		String fk_area = "fk_area";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		int backup_date = 0; // 历史表查询的日期
		// int year = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		if (company != null) {

			if (company.getMonth() != 0) { // 月报

				if (company.getYear() != year1) {

					if (company.getYear() > year1) {

						// System.out.println("动态表");

					} else if (company.getYear() > 2013) {
//						System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (company.getMonth() <= 9) {
							backup_date = Integer.parseInt(company.getYear() + "0" + company.getMonth());
						} else {
							backup_date = Integer.parseInt(company.getYear() + "" + company.getMonth());
						}

					} else if (company.getYear() == 2013) {

						if (company.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else if (company.getMonth() < month1) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (company.getMonth() <= 9) {
						backup_date = Integer.parseInt(company.getYear() + "0" + company.getMonth());
					} else {
						backup_date = Integer.parseInt(company.getYear() + "" + company.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != company.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份
					if (company.getQuarter() == 0) { // 全年

						if (company.getYear() != year1 && company.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						} else if (company.getYear() != year1 && company.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = Integer.parseInt(company.getYear() + "12");
						}

					} else { // 季度

						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						fk_area = "fk_area_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (company.getYear() < 2013 || (company.getYear() == 2013 && company.getQuarter() <= 3 && company.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (company.getQuarter() == 4) {
							backup_date = Integer.parseInt(company.getYear() + "12");

						} else if (company.getQuarter() == 3) {
							backup_date = Integer.parseInt(company.getYear() + "09");

						} else if (company.getQuarter() == 2) {
							backup_date = Integer.parseInt(company.getYear() + "06");

						} else if (company.getQuarter() == 1) {
							backup_date = Integer.parseInt(company.getYear() + "03");

						}
					}
				}
			}
		}

		DetachedCriteriaProxy detachedCriteriaProxy = null;
		if (da_company.equals("da_company_his")) {

			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyHis.class, "da");
			detachedCriteriaProxy.createCriteria("daCompanyPassHis", "dcp");

			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.backup_date=" + backup_date + "   and dcp1_.backup_date=" + backup_date + " "));

		} else {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "da");
			detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		}
		String areaSql = "";
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
				areaSql = " and c.second_area=" + area.getAreaCode() + "";

			} else if (areaRate == 5) {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
				areaSql = " and c.third_area=" + area.getAreaCode() + "";

			} else if (areaRate == 6) {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.fouthArea", area.getAreaCode()));
				areaSql = " and c.fouth_area=" + area.getAreaCode() + "";

			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				areaSql = " and c.first_area=" + area.getAreaCode() + "";
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from " + fk_area + " a where  " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + ")"));
			}
		}

		if (company != null) {
			String sqlCondition = "";
			String sqlTimeCondition = "";// zhoux增加时间条件：企业创建时间在查询季度统计的时间之后
			int month = company.getMonth();
			int year = company.getYear();
			int nextYear = year + 1;
			int nextMonth = month + 1;
			if (company.getMonth() != 0) {
				if (month != 12) {
					sqlCondition = " and create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
				} else {
					sqlCondition = " and create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
				sqlTimeCondition = "this_.create_time < to_date('" + year + "-" + month + "-01','yyyy-MM-dd')";// zhoux设置创建时间限制
			} else {
				// add by huangjl
				// modify by huangjl 如果月份为空的话，则取当前月份。
				Calendar nowCalendar = Calendar.getInstance();
				nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
				// add by huangjl
				int nowY = nowCalendar.get(Calendar.YEAR);
				int newM = nowCalendar.get(Calendar.MONTH) + 1;
				int nowQ = 0;
				// 再判断季度是否相等
				if (newM == 1 || newM == 2 || newM == 3) {
					nowQ = 1;
				} else if (newM == 4 || newM == 5 || newM == 6) {
					nowQ = 2;
				} else if (newM == 7 || newM == 8 | newM == 9) {
					nowQ = 3;
				} else {
					nowQ = 4;
				}
				// modify by huangjl 如果当前年份和季度都相等的话，则过滤到当年月的数据;或者年度相等，季度为0，也要过滤掉
				if (nowY == company.getYear() && (nowQ == company.getQuarter() || company.getQuarter() == 0)) {
					sqlCondition = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + year + "-04-01','yyyy-MM-dd')";
					sqlTimeCondition = "this_.create_time < to_date('" + year + "-" + newM + "-01','yyyy-MM-dd')";// zhoux设置创建时间限制
				} else {
					if (company.getQuarter() == 1) {
						sqlCondition = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + year + "-04-01','yyyy-MM-dd')";
						sqlTimeCondition = "this_.create_time < to_date('" + year + "-03-01','yyyy-MM-dd')";// zhoux设置创建时间限制
					} else if (company.getQuarter() == 2) {
						sqlCondition = " and create_time between to_date('" + year + "-04-01','yyyy-MM-dd') and to_date('" + year + "-07-01','yyyy-MM-dd')";
						sqlTimeCondition = "this_.create_time < to_date('" + year + "-06-01','yyyy-MM-dd')";// zhoux设置创建时间限制
					} else if (company.getQuarter() == 3) {
						sqlCondition = " and create_time between to_date('" + year + "-07-01','yyyy-MM-dd') and to_date('" + year + "-10-01','yyyy-MM-dd')";
						sqlTimeCondition = "this_.create_time < to_date('" + year + "-09-01','yyyy-MM-dd')";// zhoux设置创建时间限制
					} else if (company.getQuarter() == 4) {
						sqlCondition = " and create_time between to_date('" + year + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
						sqlTimeCondition = "this_.create_time < to_date('" + year + "-12-01','yyyy-MM-dd')";// zhoux设置创建时间限制
					} else if (company.getQuarter() == 0) {
						sqlCondition = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
						sqlTimeCondition = "this_.create_time < to_date('" + year + "-12-01','yyyy-MM-dd')";// zhoux设置创建时间限制
					}else {
						sqlCondition = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
						sqlTimeCondition = "this_.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd')";// zhoux设置创建时间条件
					}
				}

			}
			if (sqlTimeCondition != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlTimeCondition));// zhoux:sql语句添加创建时间限制
			}
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("da.companyName", company.getCompanyName(), MatchMode.ANYWHERE));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists (select par_da_com_id from da_company_industry_rel where   this_.id= par_da_com_id  and  par_da_ind_id in (" + trade + "))"));
				}
			}

			if (company.getSecondArea() != null && company.getSecondArea() == 330285000000l) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.SECOND_AREA is null or this_.SECOND_AREA=0)"));
				areaSql = " and (c.second_area is null or c.second_area=0)";
			} else if (company.getThirdArea() != null && company.getThirdArea() == 100000000000l) {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", company.getSecondArea()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.third_area is null or this_.third_area=0)"));
				areaSql = "and c.second_area=" + company.getSecondArea() + "  and (c.third_area is null or c.third_area=0)";
			} else if (company.getFouthArea() != null && company.getFouthArea() == 200000000000l) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", company.getThirdArea()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.fouth_area is null or this_.fouth_area=0)"));
				areaSql = "and c.third_area=" + company.getThirdArea() + "    and (c.fouth_area is null or c.fouth_area=0)";
			}

			if (userDetail != null) {
				String userIndustry = userDetail.getUserIndustry();
				if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
					DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
					if (industryParamenter != null) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where  this_.id=dcir.par_da_com_id  and  dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
					} else {
						return null;
					}
				}
			}
			if (company.getCompanyTrade() != null && !"".equals(company.getCompanyTrade().trim())) {
				if ("w".equals(company.getCompanyTrade().trim())) {// 高危企业（各地季度）
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "  where  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.WEIHUA + "))"));
				} else if ("r".equals(company.getCompanyTrade().trim())) {// 人口密集场所（各地季度）
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "  where  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.RENYUANMIJI + "))"));
				} else if ("g".equals(company.getCompanyTrade().trim())) {// 规模以上企业（各地季度）
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.enterprise", true));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_com_id from " + da_company_industry_rel + "  where  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.WEIHUA + "," + Nbyhpc.RENYUANMIJI + "))"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  where backup_date=" + backup_date + "   " : "") + ")"));
				} else if ("h".equals(company.getCompanyTrade().trim())) {// 所有企业（各地季度）
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  where backup_date=" + backup_date + "   " : "") + "      )"));
				} else if ("c".equals(company.getCompanyTrade().trim())) {// 季度未录入企业数（各地季度）
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "   " : "") + ")"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (" + "select n.c_id from (" + "select par_da_com_id as c_id from da_normal_danger where is_deleted=0 " + sqlCondition + ")" + " n left join " + da_company + " c on n.c_id=c.id where c.is_deleted=0   " + (da_company.equals("da_company_his") ? " and  c.backup_date=" + backup_date + "  " : "") + "   and n.c_id is not null " + areaSql + ")"));
					// liu加入 上报数据中加入重大隐患
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (" + "select n.c_id from (" + "select par_da_com_id as c_id from da_danger where is_deleted=0 " + sqlCondition + ")" + " n left join " + da_company + " c on n.c_id=c.id where c.is_deleted=0  " + (da_company.equals("da_company_his") ? " and c.backup_date=" + backup_date + "  " : "") + "  and n.c_id is not null " + areaSql + ")"));
				} else if ("q".equals(company.getCompanyTrade().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where backup_date=" + backup_date + "   " : "") + ")"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 and user_id in " + " (select id from fk_user_info where is_deleted=0 and user_industry='qiye') " + sqlCondition + ")"));
				} else if ("z".equals(company.getCompanyTrade().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "   " : "") + ")"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0  " + sqlCondition + " group by par_da_com_id having sum(is_danger)<1)"));
				} else if ("t".equals(company.getCompanyTrade().trim())) {// 其他企业（各地季度）
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.enterprise", false));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_com_id from " + da_company_industry_rel + "  where  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id in (" + Nbyhpc.WEIHUA + "," + Nbyhpc.RENYUANMIJI + "))"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where backup_date=" + backup_date + "   " : "") + ")"));
				} else if ("wq".equals(company.getCompanyTrade().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select company_id from da_company_quarter_report where quarter = " + company.getQuarter() + " and year = " + company.getYear() + ")"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "    " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "   " : "") + ")"));
				}
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));

		if (company.getOrderProperty() != null) {
			String orderProperty = company.getOrderProperty();
			detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
		}

		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));

		// add by huangjl
		if (statisicLevel != null && "2".equals(statisicLevel)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.third_area is not null and this_.third_area!=0"));
		}
		if (da_company.equals("da_company_his")) {
			List<DaCompanyHis> companies = companyHisPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			return decorationStatistic1(companies, company, backup_date);
		} else {
			List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			return decorationStatistic(companies, company);
		}
	}


	private String getTemplate() throws Exception {
		Configuration freemarker_cfg = null;
		freemarker_cfg = new Configuration();
		// 定义freemarker模板目录
		freemarker_cfg.setDirectoryForTemplateLoading(new File(realPath));
		// // 获取模板
		Template template = freemarker_cfg.getTemplate("template/" + filePath, "UTF-8");
		// 得到临时文件
		Properties p = System.getProperties();
		String tempPath = p.getProperty("java.io.tmpdir");
		String separator = p.getProperty("file.separator");
		tempFile = new File(tempPath + separator + fileName);
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8"));
		return template.toString();
	}

	/**
	 * 查询条件一般隐患列表 lisl
	 */
	public List<DaNomalDanger> loadNomalDangers(DaCompany company, Pagination pagination, String flag) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		String sqlParam = "";
		int month = company.getMonth();
		int year = company.getYear();
		int nextMonth = month + 1;
		int nextYear = year + 1;
		if (month != 0) {
			if (month != 12) {
				sqlParam = "this_.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
			} else {
				sqlParam = "this_.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
		} else {
			if (company.getQuarter() == 1) {
				sqlParam = "this_.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + year + "-04-01','yyyy-MM-dd')";
			} else if (company.getQuarter() == 2) {
				sqlParam = "this_.create_time between to_date('" + year + "-04-01','yyyy-MM-dd') and to_date('" + year + "-07-01','yyyy-MM-dd')";
			} else if (company.getQuarter() == 3) {
				sqlParam = "this_.create_time between to_date('" + year + "-07-01','yyyy-MM-dd') and to_date('" + year + "-10-01','yyyy-MM-dd')";
			} else if (company.getQuarter() == 4) {
				sqlParam = "this_.create_time between to_date('" + year + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlParam = "this_.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
		Long com_user_id = loadCompanyUser(company);
		if (!"".equals(flag)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id !=0 and this_.user_id is not null and this_.user_id !=" + com_user_id + ""));
		}
		return nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
	}

	/**
	 * 获取企业的用户的ID
	 */
	public Long loadCompanyUser(DaCompany company) throws ApplicationAccessException {
		String sql = "select dcp.com_user_id as com_user_id from da_company_pass dcp where dcp.is_deleted=0 and dcp.par_da_com_id=" + company.getId() + "";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		Long com_user_id = null;
		try {
			while (res.next()) {
				com_user_id = res.getLong(1);
				break;
			}
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return com_user_id;
	}

	/**
	 * 查询条件重大隐患列表 lisl
	 */
	public List<DaDanger> loadDangers(DaCompany company, Pagination pagination, String flag) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.daCompanyPass.id", company.getId()));
		String sqlParam = "";
		int month = company.getMonth();
		int year = company.getYear();
		int nextMonth = month + 1;
		int nextYear = year + 1;
		if (month != 0) {
			if (month != 12) {
				sqlParam = "this_.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
			} else {
				sqlParam = "this_.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
		} else {
			if (company.getQuarter() == 1) {
				sqlParam = "this_.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + year + "-04-01','yyyy-MM-dd')";
			} else if (company.getQuarter() == 2) {
				sqlParam = "this_.create_time between to_date('" + year + "-04-01','yyyy-MM-dd') and to_date('" + year + "-07-01','yyyy-MM-dd')";
			} else if (company.getQuarter() == 3) {
				sqlParam = "this_.create_time between to_date('" + year + "-07-01','yyyy-MM-dd') and to_date('" + year + "-10-01','yyyy-MM-dd')";
			} else if (company.getQuarter() == 4) {
				sqlParam = "this_.create_time between to_date('" + year + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlParam = "this_.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
		Long com_user_id = loadCompanyUser(company);
		if (!"".equals(flag)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id !=0 and this_.user_id is not null and this_.user_id !=" + com_user_id + ""));
		}
		return dangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}

	/**
	 * 查询重大隐患挂牌列表
	 * 
	 * @param danger
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaRollcallCompany> loadRollcallCompanies(DaDanger danger, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallCompany.class, "drc");
		detachedCriteriaProxy.createCriteria("daDanger", "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.createCriteria("daCompany", "dc");

		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));

		if (danger != null) {
			if (danger.getId() != null && !"".equals(danger.getId())) {
				if (danger.getId() != -1) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.id", danger.getId()));
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return rollcallCompanyPersistenceIface.loadRollcallCompanies(detachedCriteriaProxy, pagination);
	}

	public List<Statistic> loadMassAll(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String cacheName = "";
		String cacheNum = "0";
		area = loadArea_his(area.getAreaCode(), st, current_quarter);
		if (area != null) {
			int areaRate = area.getGradePath().split("/").length - 1;
			String areaType = areaRate == 4 ? "third_area" : "second_area";
			String sqlCondition = "";
			String companyStartTimeSql = "";
			@SuppressWarnings("unused")
			String IndType = "  depiction = '" + st.getRemark() + "' and type=1";
			String qiyeTypeSql = " and user_id in (select id from fk_user_info where is_deleted=0 and user_industry='qiye')";
			int beginMonth = 1;
			int endMonth = 12;
			int endYear = st.getYear();
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String fk_area = "fk_area";
			String da_company = "da_company";
			String da_company_pass = "da_company_pass";
			String da_company_industry_rel = "da_company_industry_rel";
			String da_industry_parameter = "da_industry_parameter";
			int backup_date = 0; // 历史表查询的日期

			if (st != null) {

				if (st.getMonth() != 0) { // 月报

					// 月报

					if (st.getYear() != year) {

						if (st.getYear() > year) {

							// System.out.println("动态表");

						} else if (st.getYear() > 2013) {
							// System.out.println("年份大于2013,不等于当前年份");
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							if (st.getMonth() <= 9) {
								backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
							} else {
								backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
							}

						} else if (st.getYear() == 2013) {

							if (st.getMonth() > 11) {
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201312;
							} else {

								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201311;

							}
						} else {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201311;
						}

					} else if (st.getMonth() < month) {
						// System.out.println("年份相等, 月份小于当前月份");

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else {
						// System.out.println("年份相等, 月份大于等于当前月份");
						// System.out.println("动态表");

					}

				} else {

					// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
					if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份

						if (st.getQuarter() == 0) { // 全年

							if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																				// 2013.11当年取动态
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201311;
							} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																						// 2013(包括)之后取那年的12月份
																						// yyyy-12
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = Integer.parseInt(st.getYear() + "12");
							}

						} else { // 季度

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
																																	// 未做备份,则取最早备份日期2013_11
								backup_date = 201311;
							} else if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");

							}
						}
					}
				}

				// modify by huangjl 如果月份为空的话，则取当前月份。
				Calendar nowCalendar = Calendar.getInstance();
				nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
				// add by huangjl
				int nowY = nowCalendar.get(Calendar.YEAR);
				int newM = nowCalendar.get(Calendar.MONTH) + 1;
				int nowQ = 0;
				// 再判断季度是否相等
				if (newM == 1 || newM == 2 || newM == 3) {
					nowQ = 1;
				} else if (newM == 4 || newM == 5 || newM == 6) {
					nowQ = 2;
				} else if (newM == 7 || newM == 8 | newM == 9) {
					nowQ = 3;
				} else {
					nowQ = 4;
				}

				int nextYear = st.getYear() + 1;
				if (st.getQuarter() == 0) {
					// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
					if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0) && st.getMonth() == 0) {
						companyStartTimeSql = " and dc.create_time < to_date('" + st.getYear() + "-" + newM + "-01','yyyy-MM-dd')";
					} else {
						companyStartTimeSql = " and dc.create_time < to_date('" + year + "-" + month + "-01','yyyy-MM-dd')";
					}

					sqlCondition = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + year + "-" + month + "-01','yyyy-MM-dd')";
				} else {
					if (st.getMonth() > 0) {
						beginMonth = st.getMonth();
						endMonth = st.getMonth() + 1;
					} else {
						beginMonth = st.getQuarter() * 3 - 2;
						endMonth = st.getQuarter() * 3 + 1;
					}

					if (endMonth >= 12) {
						endYear++;
						endMonth = 1;
					}

					MonthQueryHelper helper = new MonthQueryHelper(st);
					// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
					if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0) && st.getMonth() == 0) {
						companyStartTimeSql = " and dc.create_time < to_date('" + DateUtils.date2Str(nowCalendar.getTime(), null) + "','yyyy-MM-dd')";
					} else {
						companyStartTimeSql = " and dc.create_time < to_date('" + DateUtils.date2Str(helper.getNowDate(), null) + "','yyyy-MM-dd')";
					}

					sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + beginMonth + "-01','yyyy-MM-dd') and to_date('" + endYear + "-" + endMonth + "-01','yyyy-MM-dd')";
				}

				if (st.getIsSonType() == 1)
					IndType = " grade_path like (select grade_path||'%' from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "depiction = '" + st.getRemark() + "' and type=1)";

				if (st.getYear() == year && month == st.getMonth()) {
					cacheNum = "0";
				} else {
					if (st.getMonth() == 0) {
						int q = st.getQuarter() + 12;
						cacheNum = "" + st.getYear() + q;
					} else {
						cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
					}
				}

			}

			// liulj 加入MemCached缓存设置
			cacheName = "nbyhpc_loadMassAll_" + area.getAreaCode() + cacheNum;
			MemCached cache = MemCached.getInstance();
			if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
				statistics = (List<Statistic>) cache.get(cacheName);
//				System.out.println("读取缓存:" + cacheName);
			} else {

				// add by huangjl
				// " + (da_industry_parameter.equals("da_industry_parameter_his") ? "
				// and backup_date=" + backup_date + " " : "") + " )
				String zjTypeSql = " 1=1 ";
				// System.out.println("zjTypeSql:" + zjTypeSql);
				String sql = "  select count(distinct(da.id)) inhere,count(distinct(dn.par_da_com_id))num,count(distinct(z.par_da_com_id))zero, " + " count(distinct(dnqy.par_da_com_id))dnqy,map.area_code from (select fa.area_code from " + fk_area + " fa  " + " where " + (fk_area.equals("fk_area_his") ? " fa.backup_date=" + backup_date + "  and " : "") + " fa.is_deleted=0 and fa.father_id=(select id from " + fk_area + " where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) map   " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc left join " + da_company_pass + " dcp    " + " on dcp.par_da_com_id = dc.id  where " + (da_company.equals("da_company_his") ? "  dc.backup_date=" + backup_date + "  and " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? "  dcp.backup_date=" + backup_date + "  and " : "") + "dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 "
						+ companyStartTimeSql + " and dc.second_area != 0  " + " and dc.id in (select par_da_com_id from " + da_company_industry_rel + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "  and   " + zjTypeSql + "    " : " where  " + zjTypeSql + "   ") + " )) da  on da." + areaType + " = map.area_code   " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlCondition + " union   select par_da_com_id from da_danger where is_deleted=0  " + sqlCondition + ") dn on dn.par_da_com_id=da.id  " + " left join (select par_da_com_id from da_normal_danger  where is_deleted=0 " + sqlCondition + " group by par_da_com_id having sum(is_danger)<1) z on z.par_da_com_id = da.id  " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + qiyeTypeSql + " " + sqlCondition + " union   select par_da_com_id from da_danger where is_deleted=0 " + qiyeTypeSql + " " + sqlCondition
						+ ") dnqy on dnqy.par_da_com_id=da.id " + " group by map.area_code";

				// System.out.println("排查质量sql11:" + sql);
				ResultSet res = companyPersistenceIface.findBySql(sql);
				// ResultSet res=null;
				// try {
				// res =
				// cFactory.createConnection().prepareStatement(sql).executeQuery();
				// } catch (SQLException e1) {
				// e1.printStackTrace();
				// }
				try {
					while (res.next()) {
						statistic = new Statistic();
						statistic.setInhere(res.getInt(1));
						statistic.setNumber(res.getInt(2));
						statistic.setZero(res.getInt(3));
						statistic.setQnum(res.getInt(4));
						statistic.setAreaCode(res.getLong(5));
						statistics.add(statistic);
					}
					res.getStatement().close();
					res.close();

					ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
					ResultSet rs = null;

					try {
						if (c.next()) { // 已存在,更新
							PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各地排查" + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
							rs = pState.executeQuery();
						} else { // 新建
							PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各地排查" + area.getAreaCode() + cacheNum + "',sysdate)");
							rs = pState.executeQuery();
						}
						rs.getStatement().close();
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (backup_date == 0) { // 动态的 设置过期时间
						cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
					} else { // 永不过期
						cache.add(cacheName, statistics);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return statistics;
	}

	public List<Statistic> loadMassByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea_his(area.getAreaCode(), st, current_quarter);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		if (area != null) {
			int areaRate = area.getGradePath().split("/").length - 1;
			String areaType = areaRate == 4 ? "third_area" : "second_area";
			String sqlCondition = "";
			String companyStartTimeSql = "";
			@SuppressWarnings("unused")
			String IndType = "  depiction = '" + st.getRemark() + "' and type=1";
			String qiyeTypeSql = " and user_id in (select id from fk_user_info where is_deleted=0 and user_industry='qiye')";
			int beginMonth = 1;
			int endMonth = 12;
			int endMonth1 = 12;
			int endYear = st.getYear();
			String cacheName = "";
			// add by huangjl
			// 查询过滤掉未定义的行业
			String undefinedSql = " and (code is null or  code  not like '%_undefined') ";
			String cacheNum = "0";
			String fk_area = "fk_area";
			String da_company = "da_company";
			String da_company_pass = "da_company_pass";
			String da_company_industry_rel = "da_company_industry_rel";
			String da_industry_parameter = "da_industry_parameter";
			int backup_date = 0; // 历史表查询的日期

			// System.out.println("st.getQuarter(): " + st.getQuarter());
			if (st != null) {

				if (st.getMonth() != 0) { // 月报

					if (st.getYear() != year) {

						if (st.getYear() > year) {

							// System.out.println("动态表");

						} else if (st.getYear() > 2013) {
							// System.out.println("年份大于2013,不等于当前年份");
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							if (st.getMonth() <= 9) {
								backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
							} else {
								backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
							}

						} else if (st.getYear() == 2013) {

							if (st.getMonth() > 11) {
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201312;
							} else {

								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201311;

							}
						} else {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201311;
						}

					} else if (st.getMonth() < month) {
						// System.out.println("年份相等, 月份小于当前月份");

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";

						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else {
						// System.out.println("年份相等, 月份大于等于当前月份");
						// System.out.println("动态表");

					}

				} else {

					// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
					if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份

						if (st.getQuarter() == 0) { // 全年

							if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																				// 2013.11当年取动态
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201311;
							} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																						// 2013(包括)之后取那年的12月份
																						// yyyy-12
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = Integer.parseInt(st.getYear() + "12");
							}

						} else { // 季度

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
																																	// 未做备份,则取最早备份日期2013_11
								backup_date = 201311;
							} else if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");

							}
						}
					}
				}

				int nextYear = st.getYear() + 1;
				// modify by huangjl 如果月份为空的话，则取当前月份。
				Calendar nowCalendar = Calendar.getInstance();
				nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
				// add by huangjl
				int nowY = nowCalendar.get(Calendar.YEAR);
				int newM = nowCalendar.get(Calendar.MONTH) + 1;
				int nowQ = 0;
				// 再判断季度是否相等
				if (newM == 1 || newM == 2 || newM == 3) {
					nowQ = 1;
				} else if (newM == 4 || newM == 5 || newM == 6) {
					nowQ = 2;
				} else if (newM == 7 || newM == 8 | newM == 9) {
					nowQ = 3;
				} else {
					nowQ = 4;
				}
				if (st.getQuarter() == 0) {
					if (st.getMonth() > 0) {
						beginMonth = st.getMonth();
						endMonth = st.getMonth() + 1;
						// add by huangjl 12月的
						if (st.getMonth() != 12) {
							nextYear = st.getYear();// 查询当年的某个月份
						}
					} else {
						beginMonth = 1;
						endMonth = 1;
					}
					if (endMonth >= 12) {
						endYear++;
						endMonth = 1;
					}

					// System.out.println("nextYear1: " + nextYear);
					// System.out.println("endMonth1: " + endMonth);
					// MonthQueryHelper helper = new MonthQueryHelper(st);
					// System.out.println("查询时间："+DateUtils.date2Str(helper.getBeginDate(),
					// null));
					// companyStartTimeSql = " and dc.create_time < to_date('" +
					// DateUtils.date2Str(helper.getBeginDate(), null) +
					// "','yyyy-MM-dd')";

					// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
					if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0) && st.getMonth() == 0) {
						companyStartTimeSql = " and dc.create_time < to_date('" + st.getYear() + "-" + newM + "-01','yyyy-MM-dd')";
					} else {
						companyStartTimeSql = " and dc.create_time < to_date('" + nextYear + "-" + endMonth + "-01','yyyy-MM-dd')";

					}

					sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + beginMonth + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-" + endMonth + "-01','yyyy-MM-dd')";
				} else {
					if (st.getMonth() > 0) {
						beginMonth = st.getMonth();
						endMonth = st.getMonth() + 1;
						endMonth1 = st.getMonth();
					} else {
						beginMonth = st.getQuarter() * 3 - 2;
						endMonth = st.getQuarter() * 3 + 1;
						endMonth1 = st.getQuarter() * 3;
					}

					if (endMonth >= 12) {
						endYear++;
						endMonth = 1;
						endMonth1 = 12;
					}

					// liulj
					// MonthQueryHelper helper = new MonthQueryHelper(st);
					// System.out.println("查询时间：" +
					// DateUtils.date2Str(helper.getNowDate(), null));
					// companyStartTimeSql = " and dc.create_time < to_date('" +
					// DateUtils.date2Str(helper.getNowDate(), null) +
					// "','yyyy-MM-dd')";

					// edit by huangjl
					// 如果是第四季度的话，并且st.getMonth() >
					// 0表示按月查询，如果st.getMonth()==12的话，则年度加一。st.getMonth()
					// =0，表示不按月查询，年度加一
					int newYear = st.getYear();
					if (4 == st.getQuarter()) {
						if (st.getMonth() > 0) {
							if (st.getMonth() == 12) {
								newYear = newYear + 1;
							}
						} else {
							newYear = newYear + 1;
						}
					}

					// System.out.println("nextYear2: " + nextYear);
					// System.out.println("endMonth2: " + endMonth);

					// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
					if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0) && st.getMonth() == 0) {
						companyStartTimeSql = " and dc.create_time < to_date('" + st.getYear() + "-" + newM + "-01','yyyy-MM-dd')";
					} else {
						companyStartTimeSql = " and dc.create_time < to_date('" + st.getYear() + "-" + endMonth1 + "-01','yyyy-MM-dd')";
					}

					sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + beginMonth + "-01','yyyy-MM-dd') and to_date('" + endYear + "-" + endMonth + "-01','yyyy-MM-dd')";
				}

				if (st.getIsSonType() == 1)
					IndType = " grade_path like (select grade_path||'%' from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "depiction = '" + st.getRemark() + "' and type=1 " + undefinedSql + ")";

//				System.out.println("st.getMonth(): " + st.getMonth());

				if (st.getYear() == year && month == st.getMonth()) {
					cacheNum = "0";
				} else if (st.getYear() == year && current_quarter == st.getQuarter()) {
					cacheNum = "0";
				} else {
					if (st.getMonth() == 0) {
						int q = st.getQuarter() + 12;
						cacheNum = "" + st.getYear() + q;
					} else {
						cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
					}
				}

			}

			cacheName = "nbyhpc_loadMassByIndustry_" + st.getRemark() + area.getAreaCode() + cacheNum;
			ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
			ResultSet rs = null;
			MemCached cache = MemCached.getInstance();
			if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
				statistics = (List<Statistic>) cache.get(cacheName);
//				System.out.println("读取缓存:" + cacheName);
			} else {

				String relSql = "" + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + " " : "") + " ";
				String sql = " select count(distinct(da.id)) inhere,count(distinct(dn.par_da_com_id))num,count(distinct(z.par_da_com_id))zero, " + " count(distinct(dnqy.par_da_com_id))dnqy,map.area_code,map.dip_id,map.name,   " + " (select count(1) from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + undefinedSql + " and grade_path like  " + " (select grade_path||'%' from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "  id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonCount,   " + " (select 1 from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in (map.dip_id)) sonNumber1,   "
						+ " (select 1 from  " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " rownum=1 and is_deleted=0  " + undefinedSql + " and par_da_ind_id in (select id from   " + " " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonNumber2  from  " + " (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa    " + " full join (select id,name,grade_path from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + undefinedSql + " and    " + " grade_path like (select grade_path||'%' from " + da_industry_parameter + " where "
						+ (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " depiction = '" + st.getRemark() + "' and type=1 " + undefinedSql + ")) dip on 1!=2  " + " where " + (fk_area.equals("fk_area_his") ? "  fa.backup_date=" + backup_date + "  and " : "") + "   fa.is_deleted=0 and fa.father_id=(select id from " + fk_area + " where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "area_code= " + area.getAreaCode() + ")) map    " + " left join (select * from " + da_company_industry_rel + relSql + ") dcir on dcir.par_da_ind_id = map.dip_id   " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc left join " + da_company_pass + " dcp    " + " on dcp.par_da_com_id = dc.id  where " + (da_company.equals("da_company_his") ? "  dc.backup_date=" + backup_date + "  and " : "") + "  "
						+ (da_company_pass.equals("da_company_pass_his") ? "  dcp.backup_date=" + backup_date + "  and " : "") + "dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + companyStartTimeSql + " and dc.second_area != 0) da  " + " on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id   " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0  " + sqlCondition + " " + " union   select par_da_com_id from da_danger where is_deleted=0  " + sqlCondition + ") dn on dn.par_da_com_id=da.id  " + " left join (select par_da_com_id from da_normal_danger  where is_deleted=0  " + sqlCondition + "  group by par_da_com_id having sum(is_danger)<1) z on z.par_da_com_id = da.id  " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + qiyeTypeSql + " " + sqlCondition + " union   select par_da_com_id from da_danger where is_deleted=0 " + qiyeTypeSql + " " + sqlCondition + ") dnqy on dnqy.par_da_com_id=da.id " + "  "
						// +
						// (da_company_industry_rel.equals("da_company_industry_rel_his")
						// ? "  where  dcir.backup_date=" + backup_date + " " :
						// "")
						+ "     group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
//				System.out.println("sql1111=" + sql);
				ResultSet res = companyPersistenceIface.findBySql(sql);
				// ResultSet res=null;
				// try {
				// res =
				// cFactory.createConnection().prepareStatement(sql).executeQuery();
				// } catch (SQLException e1) {
				// e1.printStackTrace();
				// }

				try {
					while (res.next()) {
						statistic = new Statistic();
						statistic.setInhere(res.getInt(1));
						statistic.setNumber(res.getInt(2));
						statistic.setZero(res.getInt(3));
						statistic.setQnum(res.getInt(4));
						statistic.setAreaCode(res.getLong(5));
						statistic.setIndustryId(res.getInt(6));
						statistic.setEnumName(res.getString(7));
						statistic.setSonCount(res.getInt(8));
						statistic.setSonNumber1(res.getInt(9));
						statistic.setSonNumber2(res.getInt(10));
						statistics.add(statistic);
					}
					res.getStatement().close();
					res.close();

					try {
						if (c.next()) { // 已存在,更新
							PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='部门排查质量" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
							rs = pState.executeQuery();
						} else { // 新建
							PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','部门排查质量" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
							rs = pState.executeQuery();
						}
						rs.getStatement().close();
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					if (backup_date == 0) { // 动态的 设置过期时间
						cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
					} else { // 永不过期
						cache.add(cacheName, statistics);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return statistics;
	}

	/**
	 * 排查质量统计企业查看列表加入月份条件
	 * 
	 * @author lvx
	 * @date 2011-05-26
	 * @param company
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadCompanyMassByIndustryList(DaCompany company, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = null;
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		int backup_date = 0; // 历史表查询的日期
		if (company != null) {

			if (company.getMonth() != 0) { // 月报

				if (company.getYear() != year1) {

					if (company.getYear() > year1) {

						// System.out.println("动态表");

					} else if (company.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						if (company.getMonth() <= 9) {
							backup_date = Integer.parseInt(company.getYear() + "0" + company.getMonth());
						} else {
							backup_date = Integer.parseInt(company.getYear() + "" + company.getMonth());
						}

					} else if (company.getYear() == 2013) {

						if (company.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						backup_date = 201311;
					}
				} else if (company.getMonth() < month1) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					da_industry_parameter = "da_industry_parameter_his";

					if (company.getMonth() <= 9) {
						backup_date = Integer.parseInt(company.getYear() + "0" + company.getMonth());
					} else {
						backup_date = Integer.parseInt(company.getYear() + "" + company.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != company.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份

					if (company.getQuarter() == 0) { // 全年

						if (company.getYear() != year1 && company.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						} else if (company.getYear() != year1 && company.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = Integer.parseInt(company.getYear() + "12");
						}

					} else { // 季度

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (company.getYear() < 2013 || (company.getYear() == 2013 && company.getQuarter() <= 3 && company.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (company.getQuarter() == 4) {
							backup_date = Integer.parseInt(company.getYear() + "12");

						} else if (company.getQuarter() == 3) {
							backup_date = Integer.parseInt(company.getYear() + "09");

						} else if (company.getQuarter() == 2) {
							backup_date = Integer.parseInt(company.getYear() + "06");

						} else if (company.getQuarter() == 1) {
							backup_date = Integer.parseInt(company.getYear() + "03");

						}
					}
				}
			}
		}

		if (fk_area.equals("fk_area_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyHis.class, "da");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.backup_date =" + backup_date));
			detachedCriteriaProxy.createCriteria("daCompanyPassHis", "dcp");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("dcp1_.backup_date =" + backup_date));
		} else {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "da");
			detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		}

		String areaSQL = "";
		if (area != null && area.getAreaCode() != 0L) {
			Statistic statistic = new Statistic();
			statistic.setYear(company.getYear());
			statistic.setQuarter(company.getQuarter());
			statistic.setMonth(company.getMonth());
			area = loadArea_his(area.getAreaCode(), statistic, current_quarter);
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
				areaSQL = " and second_area=" + area.getAreaCode() + "";
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
				areaSQL = " and third_area=" + area.getAreaCode() + "";
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				areaSQL = " and first_area=" + area.getAreaCode() + "";
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from " + fk_area + " a where  " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + "a.father_id=" + area.getId() + ")"));
			}
		}
		if (company != null) {

			String sqlParam = "";
			String companyStartTimeSql = "";
			int year = company.getYear();
			int month = company.getMonth();
			int nextYear = year + 1;
			int nextMonth = month + 1;
			String comTypeSql = "";
			String qiyeTypeSql = "";
			// companyStartTimeSql = " this_.create_time < to_date('"
			// + (company.getYear() ) +
			// "-"+company.getMonth()+"-01','yyyy-MM-dd')";
			if (month != 0) {
				if (month != 12) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-" + company.getMonth() + "-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-" + month + "-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-" + nextMonth + "-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-" + company.getMonth() + "-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			} else {
				if (company.getQuarter() == 1) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-03-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-04-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 2) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-06-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-07-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 3) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-09-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-10-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 4) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-12-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " this_.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			}

			// System.out.println("company.getIndustryId: " +
			// company.getIndustryId());
			if (company.getIndustryId() != 0) {
				comTypeSql = " where  1=1  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " and backup_date=" + backup_date + "   " : "") + " and par_da_ind_id = " + company.getIndustryId();
			} else {
				comTypeSql = " where  1=1  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " and backup_date=" + backup_date + "   " : "") + "  ";

			}
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("da.companyName", company.getCompanyName(), MatchMode.ANYWHERE));
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + " " + comTypeSql + " )"));

			if (company.getCompanyTrade() != null && !"".equals(company.getCompanyTrade().trim())) {
				if ("q".equals(company.getCompanyTrade().trim()) || "d".equals(company.getCompanyTrade().trim())) {
					qiyeTypeSql = " and user_id in (select id from fk_user_info where is_deleted=0 and user_industry='qiye')";
				}
				if ("y".equals(company.getCompanyTrade().trim()) || "q".equals(company.getCompanyTrade().trim())) {// 已报数、自报数
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 " + qiyeTypeSql + sqlParam + " union select par_da_com_id from da_danger  where is_deleted=0 " + qiyeTypeSql + sqlParam + ")"));
				}
				if ("d".equals(company.getCompanyTrade().trim())) {// 代报数
					String sql0 = "this_.id not in (select dnd.par_da_com_id from (select par_da_com_id from da_normal_danger where is_deleted=0 " + qiyeTypeSql + sqlParam + ") dnd left join (select id from " + da_company + " where  " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + areaSQL + ") da on da.id=dnd.par_da_com_id where da.id is not null" + " union select dd.par_da_com_id from (select par_da_com_id from da_danger where is_deleted=0 " + qiyeTypeSql + sqlParam + ") dd left join (select id from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + areaSQL + ") da on da.id=dd.par_da_com_id where da.id is not null)";

					String sql1 = "this_.id in (select dnd.par_da_com_id from (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlParam + ") dnd left join (select id from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + areaSQL + ") da on da.id=dnd.par_da_com_id where da.id is not null" + " union select dd.par_da_com_id from (select par_da_com_id from da_danger where is_deleted=0 " + sqlParam + ") dd left join (select id from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 " + areaSQL + ") da on da.id=dd.par_da_com_id where da.id is not null)";

					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql0));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql1));
				}
				if ("z".equals(company.getCompanyTrade().trim())) {// 零隐患数
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0  " + sqlParam + " group by par_da_com_id having sum(is_danger)<1)"));
				}
				if ("w".equals(company.getCompanyTrade().trim())) {// 未报数
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dnd.par_da_com_id from (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlParam + ") dnd left join (select id from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + areaSQL + ") da on da.id=dnd.par_da_com_id where da.id is not null" + " union select dd.par_da_com_id from (select par_da_com_id from da_danger where is_deleted=0 " + sqlParam + ") dd left join (select id from " + da_company + " where " + (da_company.equals("da_company_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + areaSQL + ") da on da.id=dd.par_da_com_id where da.id is not null)"));
				}
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(companyStartTimeSql));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		// System.out.println("*********************************");
		if (fk_area.equals("fk_area_his")) {
			List<DaCompanyHis> companies = companyHisPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			// System.out.println("*********************************" +
			// companies.size());
			return decorationStatistic1(companies, company, backup_date);
		} else {
			List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			return decorationStatistic(companies, company);
		}
	}

	// public List<DaCompany> loadCompanyMassByIndustryList(DaCompany company,
	// Pagination pagination,FkArea area)throws ApplicationAccessException {
	// DetachedCriteriaProxy detachedCriteriaProxy =
	// DetachedCriteriaProxy.forClass(DaCompany.class, "da");
	// detachedCriteriaProxy.createCriteria("daCompanyPass","dcp");
	// if (area != null && area.getAreaCode() != 0L) {
	// area = loadArea(area.getAreaCode());
	// int areaRate = area.getGradePath().split("/").length - 1;
	// if (areaRate == 4) {
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea",
	// area.getAreaCode()));
	// } else if (areaRate == 5) {
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea",
	// area.getAreaCode()));
	// }else{
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea",
	// area.getAreaCode()));
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id="+area.getId()+")"));
	// }
	// }
	// if (company != null) {
	// String sqlParam = "";
	// String companyStartTimeSql = "";
	// int nextYear = company.getYear()+1;
	// String qiyeTypeSql = "";
	// String comTypeSql = "";
	// int nextMonth = company.getMonth()+1;
	// if(company.getMonth() != 0){
	// companyStartTimeSql =
	// " this_.create_time < to_date('"+company.getYear()+"-"+nextMonth+"-01','yyyy-MM-dd')";
	// sqlParam =
	// " and create_time between to_date('"+company.getYear()+"-"+company.getMonth()+"-01','yyyy-MM-dd') and to_date('"+company.getYear()+"-"+nextMonth+"-01','yyyy-MM-dd')";
	// }else{
	// if(company.getQuarter() == 1){
	// companyStartTimeSql =
	// " this_.create_time < to_date('"+company.getYear()+"-04-01','yyyy-MM-dd')";
	// sqlParam =
	// " and create_time between to_date('"+company.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+company.getYear()+"-04-01','yyyy-MM-dd')";
	// }else if(company.getQuarter() == 2){
	// companyStartTimeSql =
	// " this_.create_time < to_date('"+company.getYear()+"-07-01','yyyy-MM-dd')";
	// sqlParam =
	// " and create_time between to_date('"+company.getYear()+"-04-01','yyyy-MM-dd') and to_date('"+company.getYear()+"-07-01','yyyy-MM-dd')";
	// }else if(company.getQuarter() == 3){
	// companyStartTimeSql =
	// " this_.create_time < to_date('"+company.getYear()+"-10-01','yyyy-MM-dd')";
	// sqlParam =
	// " and create_time between to_date('"+company.getYear()+"-07-01','yyyy-MM-dd') and to_date('"+company.getYear()+"-10-01','yyyy-MM-dd')";
	// }else if(company.getQuarter() == 4){
	// companyStartTimeSql =
	// " this_.create_time < to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// sqlParam =
	// " and create_time between to_date('"+company.getYear()+"-10-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// }else{
	// companyStartTimeSql =
	// " this_.create_time < to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// sqlParam =
	// " and create_time between to_date('"+company.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// }
	// }
	// if(company.getCompanyTrade() != null &&
	// !"".equals(company.getCompanyTrade().trim())){
	// if("q".equals(company.getCompanyTrade().trim())){
	// qiyeTypeSql =
	// " and user_id in (select id from fk_user_info where is_deleted=0 and user_industry='qiye')";
	// }
	// if("y".equals(company.getCompanyTrade().trim()) ||
	// "q".equals(company.getCompanyTrade().trim())){
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id  in (select par_da_com_id from da_normal_danger where is_deleted=0 "+qiyeTypeSql+sqlParam
	// +" union select par_da_com_id from da_danger  where is_deleted=0 "+qiyeTypeSql+sqlParam+")"));
	// }
	// if("z".equals(company.getCompanyTrade().trim())){
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0  "
	// +sqlParam+ " group by par_da_com_id having sum(is_danger)<1)"));
	// }
	// }
	// if(company.getIndustryId() != 0){
	// comTypeSql =" where par_da_ind_id = "+company.getIndustryId();
	// }
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel "+comTypeSql+")"));
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(companyStartTimeSql));
	// }
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
	// detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
	// return companyPersistenceIface.loadCompanies(detachedCriteriaProxy,
	// pagination);
	// }

	/**
	 * 行业统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadCompanyByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		String cacheNum = "0";
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode(), st.getReFresh());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sqlCondition = "";
		String companyStartTimeSql = "";
		String IndType = " depiction = '" + st.getRemark() + "' and type=1";
		String cacheName = "";
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);

		String da_company = "da_company";
		String fk_area = "fk_area";
		String da_company_pass = "da_company_pass";
		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		int backup_date = 0; // 历史表查询的日期
		int month1 = cal.get(Calendar.MONTH) + 1;
		// System.out.println("month1: " + month1);
		// System.out.println("st.getMonth(): " + st.getMonth());
		// add by huangjl
		// 查询过滤掉未定义的行业
		String undefinedSql = " and (code is null or  code  not like '%_undefined') ";
		// String undefinedSql="";
		if (st != null) {

			if (st.getMonth() != 0) { // 月报

				if (st.getYear() != year1) {

					if (st.getYear() > year1) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}
						da_industry_parameter = "da_industry_parameter_his";
					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201312;
							da_industry_parameter = "da_industry_parameter_his";
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
							da_industry_parameter = "da_industry_parameter_his";

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
						da_industry_parameter = "da_industry_parameter_his";
					}

				} else if (st.getMonth() < month1) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}
					da_industry_parameter = "da_industry_parameter_his";
				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
															// ,则取历史表
															// 2013_11开始备份

					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year1 && st.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							da_company = "da_company_his";
							da_industry_parameter = "da_industry_parameter_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						} else if (st.getYear() != year1 && st.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							da_company = "da_company_his";
							da_industry_parameter = "da_industry_parameter_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度

						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						fk_area = "fk_area_his";
						da_industry_parameter = "da_industry_parameter_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}
					}
				}

				if ((current_quarter == st.getQuarter()) && (st.getMonth() == 0)) {
					st.setMonth(month1);
				}
			}

			//MonthQueryHelper helper = new MonthQueryHelper(st);
			// System.out.println("查询时间: " +
			// DateUtils.date2Str(helper.getNowDate(), null));

			
			// modify by huangjl 如果月份为空的话，则取当前月份。
			MonthQueryHelper helper = new MonthQueryHelper(st.getYear(), st.getQuarter(), st.getMonth());
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
			// add by huangjl
			int nowY = nowCalendar.get(Calendar.YEAR);
			int newM = nowCalendar.get(Calendar.MONTH) + 1;
			int nowQ = 0;
			// 再判断季度是否相等
			if (newM == 1 || newM == 2 || newM == 3) {
				nowQ = 1;
			} else if (newM == 4 || newM == 5 || newM == 6) {
				nowQ = 2;
			} else if (newM == 7 || newM == 8 | newM == 9) {
				nowQ = 3;
			} else {
				nowQ = 4;
			}
			
			
			if(st.getQuarter()==0&&st.getMonth()==0){
				//查询全年的记录时，企业小于当年年份12月录入的企业
				companyStartTimeSql = " and dc.create_time < to_date('" + st.getYear() + "-12-01','yyyy-MM-dd')";
			}else{
				//查询不是全年的记录时
				// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
				if (st != null && nowY == st.getYear() && (nowQ == st.getQuarter() || st.getQuarter() == 0) && st.getMonth() == 0) {

					companyStartTimeSql = " and dc.create_time < to_date('" + DateUtils.date2Str(nowCalendar.getTime(), null) + "','yyyy-MM-dd')";// liul
				} else {
					companyStartTimeSql = " and dc.create_time < to_date('" + DateUtils.date2Str(helper.getNowDate(), null) + "','yyyy-MM-dd')";// liulj//
																																			// 过滤当月添加的数据
				}
			}
			
			
			//companyStartTimeSql = " and dc.create_time < to_date('" + st.getYear() + "-" + (st.getMonth() != 0 ? st.getMonth() : 1) + "-01','yyyy-MM-dd')";
			
			
			sqlCondition = " and create_time between to_date('" + helper.getBeginDate(null) + "','yyyy-MM-dd') and to_date('" + helper.getEndDate(null) + "','yyyy-MM-dd')";
			if (st.getIsSonType() == 1) {
				IndType = " grade_path like (select grade_path||'%' from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " depiction = '" + st.getRemark() + "' and type=1 " + undefinedSql + ")";
			}

			if (st.getYear() == year1 && month1 == st.getMonth()) {
				cacheNum = "0";
			} else {
				if (st.getMonth() == 0) {
					int q = st.getQuarter() + 12;
					cacheNum = "" + st.getYear() + q;
				} else {
					cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
				}
			}
		}
		// liulj 加入MemCached缓存设置

		cacheName = "nbyhpc_loadCompanyByIndustry_" + st.getRemark() + area.getAreaCode() + cacheNum;

		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存:" + cacheName);
		} else {

			String sql = "select count(distinct(da.id)) inhere,count(distinct(dn.par_da_com_id)) num,map.area_code,map.dip_id,map.name, " + " (select count(1) from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + undefinedSql + " and grade_path like (select grade_path||'%' from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonCount, " + " (select 1 from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in (map.dip_id)) sonNumber1,  " + " (select 1 from " + da_industry_parameter + " where "
					+ (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in (select id from  " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonNumber2 " + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa  " + " full join (select id,name,grade_path from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 " + undefinedSql + " and  " + IndType + ") dip on 1!=2   " + " where " + (fk_area.equals("fk_area_his") ? "  fa.backup_date=" + backup_date + "  and " : "") + " fa.is_deleted=0 and fa.father_id=(select id from " + fk_area + " where "
					+ (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) map  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc left join " + da_company_pass + " dcp  " + " on dcp.par_da_com_id = dc.id  where " + (da_company.equals("da_company_his") ? "  dc.backup_date=" + backup_date + "  and " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? "  dcp.backup_date=" + backup_date + "  and " : "") + " dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + companyStartTimeSql + " and dc.second_area != 0)  " + " da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlCondition + " union  " + " select par_da_com_id from da_danger where is_deleted=0 " + sqlCondition
					+ ") dn on dn.par_da_com_id=da.id " + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  where   dcir.backup_date=" + backup_date + " " : "") + "  group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
//			System.out.println("行业sql: " + sql);

			ResultSet res = companyPersistenceIface.findBySql(sql);
			// ResultSet res=null;
			// try {
			// res =
			// cFactory.createConnection().prepareStatement(sql).executeQuery();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setInhere(res.getInt(1));
					statistic.setNumber(res.getInt(2));
					statistic.setAreaCode(res.getLong(3));
					statistic.setIndustryId(res.getInt(4));
					statistic.setEnumName(res.getString(5));
					statistic.setSonCount(res.getInt(6));
					statistic.setSonNumber1(res.getInt(7));
					statistic.setSonNumber2(res.getInt(8));
					statistics.add(statistic);
				}
				res.getStatement().close();
				res.close();

				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
				ResultSet rs = null;
				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各部门月报" + st.getRemark() + "" + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各部门月报" + st.getRemark() + "" + area.getAreaCode() + cacheNum + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (backup_date == 0) { // 动态的 设置过期时间
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.add(cacheName, statistics);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statistics;
	}

	public List<Statistic> loadQuarterByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode(), st.getReFresh());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String companyStartTimeSql = "";
		String cacheNum = "0";
		String IndType = " and depiction = '" + st.getRemark() + "' and type=1";
		String BACKUP_DATE = ""; // 历史表查询的日期
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String cacheName = "";
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		// 查询过滤掉未定义的行业
		String undefinedSql = " and (code is null or  code  not like '%_undefined') ";
		if (st != null) {

			// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
			if (st.getYear() != year || current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																				// ,则取历史表
				// 2013_11开始备份
				if (st.getQuarter() == 0) { // 全年

					if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																		// 2013.11当年取动态
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						BACKUP_DATE = "201311";
					} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																				// 2013(包括)之后取那年的12月份
																				// yyyy-12
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						BACKUP_DATE = st.getYear() + "12";
					}

				} else { // 季度

					// System.out.println("季度");
					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					da_industry_parameter = "da_industry_parameter_his";
					if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
						BACKUP_DATE = "201311";
					} else if (st.getQuarter() == 4) {
						BACKUP_DATE = st.getYear() + "12";

					} else if (st.getQuarter() == 3) {
						BACKUP_DATE = st.getYear() + "09";

					} else if (st.getQuarter() == 2) {
						BACKUP_DATE = st.getYear() + "06";

					} else if (st.getQuarter() == 1) {
						BACKUP_DATE = st.getYear() + "03";

					}
				}
			}

			MonthQueryHelper helper = new MonthQueryHelper(st);

			companyStartTimeSql = " and dc.create_time < to_date('" + DateUtils.date2Str(helper.getNowDate(), null) + "','yyyy-MM-dd')";

			if (st.getIsSonType() == 1)
				IndType = " and grade_path like (select grade_path||'%' from da_industry_parameter where depiction = '" + st.getRemark() + "' and type=1 " + undefinedSql + ")";

			if (st.getYear() == year && current_quarter == st.getQuarter()) {
				cacheNum = "0";
			} else {
				int q = st.getQuarter() + 12;
				cacheNum = "" + st.getYear() + q;
			}
		}

		// liulj 加入MemCached缓存设置
		cacheName = "nbyhpc_loadQuarterByIndustry_" + st.getRemark() + area.getAreaCode() + cacheNum;
		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存:" + cacheName);
		} else {

			String sql = "select count(distinct(da.id)) inhere,count(distinct(rep.company_id)) num,map.area_code,map.dip_id,map.name, " + " (select count(1) from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " is_deleted=0 " + undefinedSql + " and grade_path like (select grade_path||'%' from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " id=map.dip_id and is_deleted=0  " + undefinedSql + " )) sonCount, " + " (select 1 from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + "rownum=1 and is_deleted=0  " + undefinedSql + " and par_da_ind_id in (map.dip_id)) sonNumber1,  " + " (select 1 from " + da_industry_parameter + " where "
					+ (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in (select id from  " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " par_da_ind_id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonNumber2 " + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa  " + " full join (select id,name,grade_path from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " is_deleted=0 " + undefinedSql + "  " + IndType + ") dip on 1!=2   " + " where fa.is_deleted=0 and   " + (fk_area.equals("fk_area_his") ? "  fa.backup_date=" + BACKUP_DATE + "  and" : "") + " fa.father_id=(select id from " + fk_area + " where "
					+ (fk_area.equals("fk_area_his") ? "  backup_date=" + BACKUP_DATE + "  and " : "") + " area_code= " + area.getAreaCode() + ")) map  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc left join " + da_company_pass + " dcp  " + " on dcp.par_da_com_id = dc.id  where " + (da_company.equals("da_company_his") ? "  dc.backup_date=" + BACKUP_DATE + "  and" : "") + " dc.is_deleted=0 and  " + (da_company_pass.equals("da_company_pass_his") ? "  dcp.backup_date=" + BACKUP_DATE + "  and" : "") + " dcp.is_deleted=0 and dcp.is_affirm=1 " + companyStartTimeSql + " and dc.second_area != 0)  " + " da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id " + " left join (select company_id from da_company_quarter_report " + " where year = " + st.getYear() + " and quarter = " + st.getQuarter() + ") rep on  rep.company_id = da.id" + "     "
					+ (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where dcir.backup_date=" + BACKUP_DATE + "  " : "") + "      group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
//			System.out.println("部门季度:" + sql);

			ResultSet res = companyPersistenceIface.findBySql(sql);
			// ResultSet res=null;
			// try {
			// res =
			// cFactory.createConnection().prepareStatement(sql).executeQuery();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }

			ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
			ResultSet rs = null;

			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setInhere(res.getInt(1));
					statistic.setNumber(res.getInt(2));
					statistic.setAreaCode(res.getLong(3));
					statistic.setIndustryId(res.getInt(4));
					statistic.setEnumName(res.getString(5));
					statistic.setSonCount(res.getInt(6));
					statistic.setSonNumber1(res.getInt(7));
					statistic.setSonNumber2(res.getInt(8));
					statistics.add(statistic);
				}
				if (BACKUP_DATE.equals("")) { // 动态的 2000*60*60=2小时过期
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.set(cacheName, statistics);
				}
				res.getStatement().close();
				res.close();
				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各部门季报" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各部门季报" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.getStatement().close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statistics;
	}

	/**
	 * 安监其他行业统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadCompanyByIndustryOfOther(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode(), st.getReFresh());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String companyStartTimeSql = "";
		String cacheNum = "0";
		String sqlCondition = "";
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		String da_company = "da_company";
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		int backup_date = 0; // 历史表查询的日期
		String cacheName = "";
		if (st != null) {

			if (st.getMonth() != 0) { // 月报

				// 月报

				if (st.getYear() != year1) {

					if (st.getYear() > year1) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
															// ,则取历史表
															// 2013_11开始备份

					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year1 && st.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201311;
						} else if (st.getYear() != year1 && st.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度

						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						fk_area = "fk_area_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}
					}
				}
			}
			//企业创建时间
			String companyCreateDate = null;
			if (st.getMonth() != 0) {
				companyCreateDate = st.getYear() + "-" + st.getMonth() + "-01";
			} else {
				if (st.getQuarter() == 4) {
					companyCreateDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 3) {
					companyCreateDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 2) {
					companyCreateDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 1) {
					companyCreateDate = st.getYear() + "-01-01";
				} else {
					int nextYear = st.getYear() + 1;
					companyCreateDate = nextYear + "-01-01";
				}
			}
			MonthQueryHelper helper = new MonthQueryHelper(st);
			companyStartTimeSql = " and dc.create_time < to_date('" + companyCreateDate + "','yyyy-MM-dd')";
			sqlCondition = " and create_time between to_date('" + helper.getBeginDate(null) + "','yyyy-MM-dd') and to_date('" + helper.getEndDate(null) + "','yyyy-MM-dd')";
			// System.out.println("sqlCondition: " + sqlCondition);
			// System.out.println("companyStartTimeSql:" + companyStartTimeSql);

			if (st.getYear() == year1 && month == st.getMonth()) {
				cacheNum = "0";
			} else {
				if (st.getMonth() == 0) {
					int q = st.getQuarter() + 12;
					cacheNum = "" + st.getYear() + q;
				} else {
					cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
				}
			}
		}

		// liulj 加入MemCached缓存设置

		cacheName = "nbyhpc_loadCompanyByIndustry_" + st.getRemark() + area.getAreaCode() + cacheNum;
		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存:" + cacheName);
		} else {

			String sql = "select count(distinct(da.id)) inhere,count(distinct(dn.par_da_com_id)) num,map.area_code,map.dip_id,map.name" + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa  " + " full join (select id,name,grade_path from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and depiction = '" + st.getRemark() + "' and type=1) dip on 1!=2   " + " where " + (fk_area.equals("fk_area_his") ? "  fa.backup_date=" + backup_date + "  and " : "") + "fa.is_deleted=0 and fa.father_id=(select id from " + fk_area + " where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) map  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company
					+ " dc left join " + da_company_pass + " dcp  " + " on dcp.par_da_com_id = dc.id  where " + (da_company.equals("da_company_his") ? "  dc.backup_date=" + backup_date + "  and " : "") + "  " + (da_company_pass.equals("da_company_pass_his") ? "  dcp.backup_date=" + backup_date + "  and " : "") + "   dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + companyStartTimeSql + " and dc.second_area != 0)  " + " da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlCondition + " union  " + " select par_da_com_id from da_danger where is_deleted=0 " + sqlCondition + ") dn on dn.par_da_com_id=da.id " + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  dcir.backup_date=" + backup_date + "   " : "") + "  group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
//			System.out.println("安监其他行业统计sql:  " + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);
			// ResultSet res=null;
			// try {
			// res =
			// cFactory.createConnection().prepareStatement(sql).executeQuery();
			// } catch (SQLException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setInhere(res.getInt(1));
					statistic.setNumber(res.getInt(2));
					statistic.setAreaCode(res.getLong(3));
					statistic.setIndustryId(res.getInt(4));
					statistic.setEnumName(res.getString(5));
					statistics.add(statistic);
				}

				res.getStatement().close();
				res.close();

				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
				ResultSet rs = null;

				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各部门月报" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各部门月报" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.getStatement().close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (backup_date == 0) { // 动态的 设置过期时间
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.add(cacheName, statistics);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statistics;
	}

	public List<Statistic> loadQuarterByIndustryOfAjOther(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode(), st.getReFresh());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String companyStartTimeSql = "";
		int backup_date = 0; // 历史表查询的日期
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String cacheNum = "0";
		String cacheName = "";
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";

		if (st != null) {

			// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
			if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致 ,则取历史表
														// 2013_11开始备份
				if (st.getQuarter() == 0) { // 全年

					if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																		// 2013.11当年取动态
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						backup_date = 201311;
					} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																				// 2013(包括)之后取那年的12月份
																				// yyyy-12
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						backup_date = Integer.parseInt(st.getYear() + "12");
					}

				} else { // 季度
					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					da_industry_parameter = "da_industry_parameter_his";
					if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
						backup_date = 201311;
					} else if (st.getQuarter() == 4) {
						backup_date = Integer.parseInt(st.getYear() + "12");

					} else if (st.getQuarter() == 3) {
						backup_date = Integer.parseInt(st.getYear() + "09");

					} else if (st.getQuarter() == 2) {
						backup_date = Integer.parseInt(st.getYear() + "06");

					} else if (st.getQuarter() == 1) {
						backup_date = Integer.parseInt(st.getYear() + "03");

					}
				}
			}

			//MonthQueryHelper helper = new MonthQueryHelper(st);
			//companyStartTimeSql = " and dc.create_time < to_date('" + helper.getEndDate(null) + "','yyyy-MM-dd')";
			int month = st.getMonth();
			int nextYear = st.getYear() + 1;
			if (st.getMonth() != 0) {
				if (month != 12) {
					companyStartTimeSql = " and dc.create_time <to_date('" + st.getYear() + "-" + month + "-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " and dc.create_time <to_date('" + st.getYear() + "-12-01','yyyy-MM-dd')";
				}
			} else {
				if (st.getQuarter() == 1) {
					companyStartTimeSql = " and dc.create_time <to_date('" + st.getYear() + "-03-01','yyyy-MM-dd')";
				} else if (st.getQuarter() == 2) {
					companyStartTimeSql = " and dc.create_time <to_date('" + st.getYear() + "-06-01','yyyy-MM-dd')";
				} else if (st.getQuarter() == 3) {
					companyStartTimeSql = " and dc.create_time <to_date('" + st.getYear() + "-09-01','yyyy-MM-dd')";
				} else if (st.getQuarter() == 4) {
					companyStartTimeSql = " and dc.create_time <to_date('" + st.getYear() + "-12-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " and dc.create_time <to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			}
			
			// log.info("companyStartTimeSql:" + companyStartTimeSql);

			if (st.getYear() == year && current_quarter == st.getQuarter()) {
				cacheNum = "0";
			} else {
				int q = st.getQuarter() + 12;
				cacheNum = "" + st.getYear() + q;
			}
		}

		// liulj 加入MemCached缓存设置

		cacheName = "nbyhpc_loadQuarterByIndustry_" + st.getRemark() + area.getAreaCode() + cacheNum;
		ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
		ResultSet rs = null;

		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存:" + cacheName);
		} else {

			String sql = "select count(distinct(da.id)) inhere,count(distinct(rep.company_id)) num,map.area_code,map.dip_id,map.name" + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa  " + " full join (select id,name,grade_path from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "is_deleted=0 and depiction = '" + st.getRemark() + "' and type=1) dip on 1!=2   " + " where   " + (fk_area.equals("fk_area_his") ? "  fa.backup_date=" + backup_date + "  and " : "") + "     fa.is_deleted=0 and fa.father_id=(select id from " + fk_area + " where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) map  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company
					+ " dc left join " + da_company_pass + " dcp  " + " on dcp.par_da_com_id = dc.id  where " + (da_company.equals("da_company_his") ? "  dc.backup_date=" + backup_date + "  and " : "") + "  " + (da_company_pass.equals("da_company_pass_his") ? "  dcp.backup_date=" + backup_date + "  and " : "") + " dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + companyStartTimeSql + " and dc.second_area != 0)  " + " da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id " + " left join (select company_id from da_company_quarter_report " + " where year = " + st.getYear() + " and quarter = " + st.getQuarter() + ") rep on  rep.company_id = da.id" + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  where   dcir.backup_date=" + backup_date + "   " : "") + "   group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";

			// System.out.println("安监其他行业: " + sql);

			ResultSet res = companyPersistenceIface.findBySql(sql);
			// ResultSet res=null;
			// try {
			// res =
			// cFactory.createConnection().prepareStatement(sql).executeQuery();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setInhere(res.getInt(1));
					statistic.setNumber(res.getInt(2));
					statistic.setAreaCode(res.getLong(3));
					statistic.setIndustryId(res.getInt(4));
					statistic.setEnumName(res.getString(5));
					statistics.add(statistic);
				}

				if (backup_date == 0) { // 动态的 2000*60*60=2小时过期
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.set(cacheName, statistics);
				}

				res.getStatement().close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (c.next()) { // 已存在,更新
					PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各部门季报" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
					rs = pState.executeQuery();
				} else { // 新建
					PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各部门季报" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
					rs = pState.executeQuery();
				}
				rs.getStatement().close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return statistics;
	}

	/**
	 * 行业统计企业列表
	 * 
	 * @param company
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompanyByIndustryList(DaCompany company, Pagination pagination, FkArea area) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "da");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
			}
		}
		if (company != null) {
			String sqlParam = "";
			String sqlType = "";
			String companyStartTimeSql = "";
			int month = company.getMonth();
			int year = company.getYear();
			int nextYear = year + 1;
			int nextMonth = month + 1;
			if (company.getMonth() != 0) {
				if (month != 12) {
					companyStartTimeSql = " this_.create_time < to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + year + "-" + company.getMonth() + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " this_.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			} else {
				if (company.getQuarter() == 1) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-04-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-04-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 2) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-07-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-07-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 3) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-10-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-10-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 4) {
					companyStartTimeSql = " this_.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " this_.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			}
			if (company.getCompanyTrade() != null && !"".equals(company.getCompanyTrade().trim())) {
				if ("w".equals(company.getCompanyTrade().trim())) {
					sqlType = " and par_da_com_id not in (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlParam + ") " + " and par_da_com_id not in (select par_da_com_id from da_danger  where is_deleted=0 " + sqlParam + ")";
				}
				if ("q".equals(company.getCompanyTrade().trim())) {
					sqlType = " and par_da_com_id not in (select company_id from da_company_quarter_report where quarter = " + company.getQuarter() + " and year = " + company.getYear() + ") ";
				}
			}
			if (company.getIndustryId() != 0) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id = " + company.getIndustryId() + sqlType + ")"));
			}
			// if(company.getCompanyTrade() != null &&
			// !"".equals(company.getCompanyTrade().trim())){
			// if("w".equals(company.getCompanyTrade().trim())){
			// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("and par_da_com_id not in (select par_da_com_id from da_normal_danger where is_deleted=0 "+sqlParam+")"));
			// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("and par_da_com_id not in (select par_da_com_id from da_danger  where is_deleted=0 "+sqlParam+")"));
			// }
			// }
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(companyStartTimeSql));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	/**
	 * 行业统计企业列表
	 * 
	 * @author lisl
	 * @param company
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadCompanyByIndustryList1(DaCompany company, Pagination pagination, FkArea area, int current_quarter, UserDetailWrapper userDetail) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		String da_company = "da_company";
		String fk_area = "fk_area";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		int backup_date = 0; // 历史表查询的日期
		if (company != null) {

			if (company.getMonth() != 0) { // 月报

				// 月报

				if (company.getYear() != year1) {

					if (company.getYear() > year1) {

						// System.out.println("动态表");

					} else if (company.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (company.getMonth() <= 9) {
							backup_date = Integer.parseInt(company.getYear() + "0" + company.getMonth());
						} else {
							backup_date = Integer.parseInt(company.getYear() + "" + company.getMonth());
						}

					} else if (company.getYear() == 2013) {

						if (company.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else if (company.getMonth() < month1) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (company.getMonth() <= 9) {
						backup_date = Integer.parseInt(company.getYear() + "0" + company.getMonth());
					} else {
						backup_date = Integer.parseInt(company.getYear() + "" + company.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != company.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份

					if (company.getQuarter() == 0) { // 全年

						if (company.getYear() != year1 && company.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						} else if (company.getYear() != year1 && company.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = Integer.parseInt(company.getYear() + "12");
						}

					} else { // 季度

						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						fk_area = "fk_area_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (company.getYear() < 2013 || (company.getYear() == 2013 && company.getQuarter() <= 3 && company.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (company.getQuarter() == 4) {
							backup_date = Integer.parseInt(company.getYear() + "12");

						} else if (company.getQuarter() == 3) {
							backup_date = Integer.parseInt(company.getYear() + "09");

						} else if (company.getQuarter() == 2) {
							backup_date = Integer.parseInt(company.getYear() + "06");

						} else if (company.getQuarter() == 1) {
							backup_date = Integer.parseInt(company.getYear() + "03");

						}
					}
				}
			}
		}
		DetachedCriteriaProxy detachedCriteriaProxy = null;
		if (da_company.equals("da_company_his")) {

			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyHis.class, "da");
			detachedCriteriaProxy.createCriteria("daCompanyPassHis", "dcp");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.backup_date=" + backup_date + "   and dcp1_.backup_date=" + backup_date + " "));
		} else {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "da");
			detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		}
		if (area != null && area.getAreaCode() != 0L) {
			if (fk_area.equals("fk_area_his")) {
				area = loadArea_his(area.getAreaCode(), backup_date);
			} else {
				area = loadArea(area.getAreaCode());
			}
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
			}else if (areaRate == 6) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.fouthArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists (select a.area_code from " + fk_area + " a where  this_.SECOND_AREA= a.area_code and     " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + ")"));
			}
		}
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("da.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			String sqlParam = "";
			String sqlType = "";
			String companyStartTimeSql = "";
			int year = company.getYear();
			int month = company.getMonth();
			int nextYear = company.getYear() + 1;
			int nextMonth = company.getMonth() + 1;
			if (company.getMonth() != 0) {
				if (month != 12) {
					companyStartTimeSql = " this_.create_time < to_date('" + year + "-" + month + "-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " this_.create_time < to_date('" + year + "-12-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			} else {
				if (company.getQuarter() == 1) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-03-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-04-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 2) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-06-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-07-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 3) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-09-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + company.getYear() + "-10-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 4) {
					companyStartTimeSql = " this_.create_time < to_date('" + company.getYear() + "-12-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				} else {
					companyStartTimeSql = " this_.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					sqlParam = " and create_time between to_date('" + company.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			}

			if (company.getSecondArea() != null && company.getSecondArea() == 330285000000l) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.SECOND_AREA is null or this_.SECOND_AREA=0)"));
			} else if (company.getThirdArea() != null && company.getThirdArea() == 100000000000l) {

				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", company.getSecondArea()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.third_area is null or this_.third_area=0)"));
			} else if (company.getFouthArea() != null && company.getFouthArea() == 200000000000l) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", company.getThirdArea()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.fouth_area is null or this_.fouth_area=0)"));
			}
			if (company.getCompanyTrade() != null && !"".equals(company.getCompanyTrade().trim())) {
				if ("w".equals(company.getCompanyTrade().trim())) {
					sqlType = " and par_da_com_id not in (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlParam + ") " + " and par_da_com_id not in (select par_da_com_id from da_danger  where is_deleted=0 " + sqlParam + ")";
				}
				if ("q".equals(company.getCompanyTrade().trim())) {
					sqlType = " and par_da_com_id not in (select company_id from da_company_quarter_report where quarter = " + company.getQuarter() + " and year = " + company.getYear() + ") ";
				}
			}

			if (userDetail != null) {
				String userIndustry = userDetail.getUserIndustry();
				if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
					DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
					company.setIndustryId(industryParamenter.getId().intValue());
				}
			}

			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						company.setIndustryId(Integer.parseInt(trade));
						break;
					}
				}
			}

			if (company.getIndustryId() != 0) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from " + da_company_industry_rel + "  where  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id = " + company.getIndustryId() + sqlType + ")"));
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(companyStartTimeSql));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));

		if (company.getOrderProperty() != null) {
			String orderProperty = company.getOrderProperty();
//			System.out.println("orderProperty: " + orderProperty);
			detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
		}

		// detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		if (da_company.equals("da_company_his")) {
			List<DaCompanyHis> companies = companyHisPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			return decorationStatistic1(companies, company, backup_date);
		} else {
			List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			return decorationStatistic(companies, company);
		}
	}

	/**
	 * 封装查询出的企业隐患信息
	 * 
	 * @author lisl
	 * @param companies
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> decorationStatistic(List<DaCompany> companies, DaCompany company) throws ApplicationAccessException {
		String companyIdies = "";

		for (DaCompany com : companies) {
			companyIdies = companyIdies + com.getId().toString() + ",";
		}

		List<Statistic> statisticDetails = new ArrayList<Statistic>();
		Statistic statistic = null;
		if (!companyIdies.equals("")) {
			String sqlParam = "";
			int year = company.getYear();
			int month = company.getMonth();
			int nextYear = year + 1;
			int nextMonth = month + 1;
			if (month != 0) {
				if (month != 12)
					sqlParam = " and t.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
				else
					sqlParam = " and t.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				if (company.getQuarter() == 1) {
					sqlParam = " and t.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + year + "-04-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 2) {
					sqlParam = " and t.create_time between to_date('" + year + "-04-01','yyyy-MM-dd') and to_date('" + year + "-07-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 3) {
					sqlParam = " and t.create_time between to_date('" + year + "-07-01','yyyy-MM-dd') and to_date('" + year + "-10-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 4) {
					sqlParam = " and t.create_time between to_date('" + year + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				} else {
					sqlParam = " and t.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			}

			String sql = "select c.id,c.company_name,count(distinct dnd.id),count(distinct dd.id),count(distinct cqr.id),count(distinct dnd_1.id)+count(distinct dd_1.id),c.safety_management_person,c.safety_management_person_phone,c.address" + " from "
			// --连接企业表（统计企业数目）
					+ " (select t.id as id,t.company_name as company_name ,t.safety_management_person,t.safety_management_person_phone,t.address from da_company t where t.is_deleted=0 and t.id in (" + companyIdies.substring(0, companyIdies.length() - 1) + ")) c"
					// --连接一般隐患表（统计一般隐患数目）
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_normal_danger t where t.is_deleted=0" + sqlParam + ") dnd on c.id=dnd.par_da_com_id"
					// --连接重大隐患表（统计重大隐患数目）
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_danger t where t.is_deleted=0" + sqlParam + ") dd on c.id=dd.par_da_com_id"
					// --连接企业季度上报表 （查询企业是否上报）
					+ " left join (select t.id as id,t.company_id as company_id from da_company_quarter_report t where t.year='" + company.getYear() + "' and t.quarter='" + company.getQuarter() + "' ) cqr on cqr.company_id=c.id"
					// --连接一般隐患表（统计一般隐患数目）(监管部门检查发现隐患数)
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_normal_danger t left join da_company_pass dcp on dcp.par_da_com_id=t.par_da_com_id where t.is_deleted=0 and t.user_id !=0 and t.user_id is not null and t.user_id!=dcp.com_user_id" + sqlParam + ") dnd_1 on c.id=dnd_1.par_da_com_id"
					// --连接重大隐患表（统计重大隐患数目）(监管部门检查发现隐患数)
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_danger t left join da_company_pass dcp on dcp.par_da_com_id=t.par_da_com_id where t.is_deleted=0 and t.user_id !=0 and t.user_id is not null and t.user_id!=dcp.com_user_id" + sqlParam + ") dd_1 on c.id=dd_1.par_da_com_id"
					// --按企业分组
					+ " group by c.address,c.id,c.company_name,c.safety_management_person,c.safety_management_person_phone";
			// System.out.println("-------sql---shun=" + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);

			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setCompanyId(res.getLong(1));
					statistic.setCompanyName(res.getString(2));
					statistic.setAnum(res.getInt(3));// 标记一般事故隐患数
					statistic.setBnum(res.getInt(4));// 标记重大事故隐患数
					statistic.setCnum(res.getInt(5));// 标记季报统计报表报送情况的状态
					statistic.setDnum(res.getInt(6));// 标记监管部门检查发现隐患数

					statistic.setSafetyMngPerson(res.getString(7));
					statistic.setSafetyMngPersonPhone(res.getString(8));
					statistic.setAddress(res.getString(9));
					statisticDetails.add(statistic);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statisticDetails;
	}

	public List<Statistic> loadTroubleByRollcall(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String cacheNum = "0";
		area = loadArea_his(area.getAreaCode(), st, current_quarter);
		if (area != null) {
			int areaRate = area.getGradePath().split("/").length - 1;
			String areaType = areaRate == 4 ? "third_area" : "second_area";
			String sqlCondition = "";
			String sqlConditionD = "";
			String IndType = "";
			Calendar cal = Calendar.getInstance();
			int year1 = cal.get(Calendar.YEAR);
			String fk_area = "fk_area";
			String da_company = "da_company";
			String da_company_pass = "da_company_pass";
			String da_company_industry_rel = "da_company_industry_rel";
			int month1 = cal.get(Calendar.MONTH) + 1;
			String da_company_industry_rell = "da_company_industry_rell";
			String da_industry_parameter = "da_industry_parameter";
			int backup_date = 0; // 历史表查询的日期
			String comcreatesql = "";
			int nextYear = 0;
			String cacheName = "";
			if (st != null) {
				nextYear = st.getYear() + 1;
				if (st.getMonth() != 0) { // 月报

					if (st.getYear() != year1) {

						if (st.getYear() > year1) {

							// System.out.println("动态表");

						} else if (st.getYear() > 2013) {
							// System.out.println("年份大于2013,不等于当前年份");
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							if (st.getMonth() <= 9) {
								backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
							} else {
								backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
							}

						} else if (st.getYear() == 2013) {

							if (st.getMonth() > 11) {
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								backup_date = 201312;
							} else {

								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								backup_date = 201311;

							}
						} else {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						}

					} else if (st.getMonth() < month1) {
						// System.out.println("年份相等, 月份小于当前月份");

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";

						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else {
						// System.out.println("年份相等, 月份大于等于当前月份");
						// System.out.println("动态表");

					}

				} else {

					// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
					if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份

						if (st.getQuarter() == 0) { // 全年

							if (st.getYear() != year1 && st.getYear() < 2013) { // 往年:2013年之前都取
								// 2013.11当年取动态
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								fk_area = "fk_area_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201311;
							} else if (st.getYear() != year1 && st.getYear() >= 2013) { // 往年
								// 2013(包括)之后取那年的12月份
								// yyyy-12
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								fk_area = "fk_area_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = Integer.parseInt(st.getYear() + "12");
							}

						} else { // 季度

							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
								// 未做备份,则取最早备份日期2013_11
								backup_date = 201311;
							} else if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");
							}
						}
					}
				}

				if (st.getYear() == year1 && month1 == st.getMonth()) {
					cacheNum = "0";
				} else {
					cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
				}

				// liulj 加入MemCached缓存设置
				cacheName = "nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_" + st.getRemark() + "_rollcall_" + area.getAreaCode() + cacheNum;
				MemCached cache = MemCached.getInstance();
				if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
					statistics = (List<Statistic>) cache.get(cacheName);
//					System.out.println("读取缓存:" + cacheName);
				} else {

					if (st.getIsSonType() == 0)
						IndType = " where ";

					if (st.getEnd_month() == 12) {
						sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					} else {
						sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" + ((st.getEnd_month() + 1)) + "-01','yyyy-MM-dd')";
					}

					// 判断是否当前年份，如果是当前年份的话，要过滤到当前月份。
					Calendar calendar = Calendar.getInstance();
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH) + 1;
					int nextmonth = 0;
					String query_time = "";
					if (st.getEnd_month() != 12) {
						nextmonth = st.getEnd_month() + 1;
						query_time = "and drc.create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" + nextmonth + "-01','yyyy-MM-dd')";
					} else {
						query_time = "and drc.create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					}

					// if (year == st.getYear()) {
					// comcreatesql = " and dc.create_time<to_date('" +
					// st.getYear()
					// + "-" + month + "-01','yyyy-MM-dd') ";
					// } else {
					comcreatesql = " and dc.create_time<to_date('" + st.getYear() + "-" + (st.getEnd_month()) + "-01','yyyy-MM-dd') ";
					// }

					IndType = " left join " + da_industry_parameter + " dip on dip.id = dcir.par_da_ind_id  where  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  dip.backup_date=" + backup_date + "  and " : "") + " dip.is_deleted=0  and dip.type=1 and ";
					if (st.getRemark() != null) {
						IndType = IndType + "   dip.depiction = '" + st.getRemark() + "'  and ";
					}

					String sql = "select count(distinct(dd.id)) inhere,count(distinct(ddg.par_da_dan_id)) num, " + " map.area_code,map.enum_code,map.enum_name " + " from (select fa.area_code,fe.enum_code,fe.enum_name,fe.grade_path from " + fk_area + " fa  " + " full join (select enum_name,enum_code,grade_path from fk_enum where is_deleted=0 and  " + " father_id = (select id from fk_enum where enum_code='rollcallCompany')) fe on 1!=2   " + " where " + (fk_area.equals("fk_area_his") ? "  fa.backup_date=" + backup_date + "  and " : "") + " fa.is_deleted=0 and fa.father_id=(select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) map  " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc  " + " left join " + da_company_pass
							+ " dcp on dcp.par_da_com_id = dc.id  "
							+ " left join "
							// modify by huangjl
							// 添加da_company_industry_rell查询已经删除的企业的挂牌隐患
							// + da_company_industry_rel
							+ " (select par_da_com_id,par_da_ind_id from " + da_company_industry_rel + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "   " : "") + " union select par_da_com_id,par_da_ind_id from " + da_company_industry_rell + " ) "

							+ " dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0    " + comcreatesql + "  " + " and dcp.is_deleted=0 " + (da_company.equals("da_company_his") ? " and dc.backup_date=" + backup_date + "   " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? " and dcp.backup_date=" + backup_date + "   " : "") + "and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = map.area_code  " + " left join (select d.id,d.par_da_com_id,drc.levels from da_danger d " + " left join da_rollcall_company drc on drc.par_da_dan_id = d.id " + " where d.is_deleted=0 and drc.is_deleted=0  " + query_time + "   and d.id is not null " + sqlConditionD + ") " + " dd on dd.par_da_com_id = da.id and dd.levels = map.enum_code " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlCondition + ") ddg on ddg.par_da_dan_id = dd.id  " + " group by map.area_code,map.enum_code,map.enum_name,map.grade_path"
							+ " order by map.area_code,map.grade_path";

					// System.out.println("挂牌重大隐患整治统计sql: " + sql);
					ResultSet res = companyPersistenceIface.findBySql(sql);
					// ResultSet res=null;
					// try {
					// res =
					// cFactory.createConnection().prepareStatement(sql).executeQuery();
					// } catch (SQLException e1) {
					// e1.printStackTrace();
					// }
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
						res.getStatement().close();
						res.close();

						ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
						ResultSet rs = null;

						try {
							if (c.next()) { // 已存在,更新
								PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各地隐患治理2" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
								rs = pState.executeQuery();
							} else { // 新建
								PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各地隐患治理2" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
								rs = pState.executeQuery();
							}
							rs.getStatement().close();
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}

						if (backup_date == 0) { // 动态的 2000*60*60=2小时过期
							cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
						} else { // 永不过期
							cache.add(cacheName, statistics);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}
		}
		return statistics;
	}

	/**
	 * 挂牌重大隐患整治统计
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadTroubleByRollcallColligation(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sqlCondition = "";
		String sqlConditionD = "";
		String IndType = " left join da_industry_parameter dip on dip.id = dcir.par_da_ind_id  where  dip.is_deleted=0  and dip.type=1 and ";
		if (st.getRemark() != null) {
			IndType = IndType + "   dip.depiction = '" + st.getRemark() + "'  and ";
		}
		if (st != null) {
			if (st.getIsSonType() == 0)
				IndType = " where ";
			int nextYear = st.getYear() + 1;
			sqlCondition = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			sqlConditionD = " and drc.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}

		String sql = "select count(distinct(dd.id)) inhere,count(distinct(ddg.par_da_dan_id)) num, " + " map.area_code,map.enum_code,map.enum_name " + " from (select fa.area_code,fe.enum_code,fe.enum_name,fe.grade_path from fk_area fa  " + " full join (select enum_name,enum_code,grade_path from fk_enum where is_deleted=0 and  " + " father_id = (select id from fk_enum where enum_code='rollcallCompany')) fe on 1!=2   " + " where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map  " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  " + " left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0  " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = map.area_code  " + " left join (select d.id,d.par_da_com_id,drc.levels from da_danger d "
				+ " left join da_rollcall_company drc on drc.par_da_dan_id = d.id " + " where d.is_deleted=0 and drc.is_deleted=0 and d.id is not null " + sqlConditionD + ") " + " dd on dd.par_da_com_id = da.id and dd.levels = map.enum_code " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlCondition + ") ddg on ddg.par_da_dan_id = dd.id  " + " group by map.area_code,map.enum_code,map.enum_name,map.grade_path" + " order by map.area_code,map.grade_path";
		// System.out.println("挂牌重大隐患整治统计sql: " + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
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
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<Statistic> loadTroubleByNomalAndHidden(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		area = loadArea_his(area.getAreaCode(), st, current_quarter);
		String cacheNum = "0";
		if (area != null) {
			int areaRate = area.getGradePath().split("/").length - 1;
			String areaType = areaRate == 4 ? "third_area" : "second_area";
			String sqlCondition = "";
			String IndType = "";

			String fk_area = "fk_area";
			String da_company = "da_company";
			String da_company_pass = "da_company_pass";
			String da_company_industry_rel = "da_company_industry_rel";
			String da_industry_parameter = "da_industry_parameter";
			int backup_date = 0; // 历史表查询的日期
			String comcreatesql = "";
			String cacheName = "";
			if (st != null) {
				if (st.getMonth() != 0) { // 月报
					if (st.getYear() != year1) {
						if (st.getYear() > year1) {

							// System.out.println("动态表");

						} else if (st.getYear() > 2013) {
							// System.out.println("年份大于2013,不等于当前年份");
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							if (st.getMonth() <= 9) {
								backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
							} else {
								backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
							}

						} else if (st.getYear() == 2013) {

							if (st.getMonth() > 11) {
								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								backup_date = 201312;
							} else {

								fk_area = "fk_area_his";
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								backup_date = 201311;

							}
						} else {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						}

					} else if (st.getMonth() < month) {
						// System.out.println("年份相等, 月份小于当前月份");

						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";

						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else {
						// System.out.println("年份相等, 月份大于等于当前月份");
						// System.out.println("动态表");

					}

				} else {

					// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
					if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																// ,则取历史表
																// 2013_11开始备份

						if (st.getQuarter() == 0) { // 全年

							if (st.getYear() != year1 && st.getYear() < 2013) { // 往年:2013年之前都取
								// 2013.11当年取动态
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								fk_area = "fk_area_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = 201311;
							} else if (st.getYear() != year1 && st.getYear() >= 2013) { // 往年
								// 2013(包括)之后取那年的12月份
								// yyyy-12
								da_company = "da_company_his";
								da_company_pass = "da_company_pass_his";
								fk_area = "fk_area_his";
								da_company_industry_rel = "da_company_industry_rel_his";
								da_industry_parameter = "da_industry_parameter_his";
								backup_date = Integer.parseInt(st.getYear() + "12");
							}

						} else { // 季度

							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
								// 未做备份,则取最早备份日期2013_11
								backup_date = 201311;
							} else if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");
							}
						}
					}
				}

				if (st.getYear() == year1 && month == st.getMonth()) {
					cacheNum = "0";
				} else {
					cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth() + ((st.getBeg_month() < 10) ? "0" : "") + st.getBeg_month() + ((st.getEnd_month() < 10) ? "0" : "") + st.getEnd_month();
				}

				// liulj 加入MemCached缓存设置 loadTroubleByNomalAndHiddenAndRollcall
				cacheName = "nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_" + st.getRemark() + "_normal_" + area.getAreaCode() + cacheNum;
				MemCached cache = MemCached.getInstance();
				if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
					statistics = (List<Statistic>) cache.get(cacheName);
//					System.out.println("读取缓存:" + cacheName);
				} else {

					if (st.getIsSonType() == 0)
						IndType = " where ";
					int nextYear = st.getYear() + 1;
					if (st.getEnd_month() == 12) {
						sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";

					} else {
						sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" + ((st.getEnd_month() + 1)) + "-01','yyyy-MM-dd')";
					}

					// 判断是否当前年份，如果是当前年份的话，要过滤到当前月份。
					Calendar calendar = Calendar.getInstance();
					int year = calendar.get(Calendar.YEAR);
					if (year == st.getYear()) {
						comcreatesql = " and dc.create_time<to_date('" + st.getYear() + "-" + month + "-01','yyyy-MM-dd') ";
					} else {
						comcreatesql = " and dc.create_time<to_date('" + st.getYear() + "-" + (st.getEnd_month()) + "-01','yyyy-MM-dd') ";
					}

					IndType = " left join " + da_industry_parameter + " dip on dip.id = dcir.par_da_ind_id  where  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  dip.backup_date=" + backup_date + "  and " : "") + " dip.is_deleted=0  and dip.type=1 and ";
					if (st.getRemark() != null) {
						IndType = IndType + "   dip.depiction = '" + st.getRemark() + "'  and ";
					}

					String sql = "select area_code,sum(anum) anum,sum(bnum) bnum,sum(aanum) aanum,sum(bbnum) bbnum,sum(ccnum) ccnum from " + " (select fa.area_code,count(distinct(dnd.id)) anum,count(distinct(dnd_g.id)) bnum,0 aanum,0 bbnum,0 ccnum from  " + " (select area_code from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ")) fa " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc  " + " left join " + da_company_pass + " dcp on dcp.par_da_com_id = dc.id  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0   " + comcreatesql + " " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null  "
							+ (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + " "
							+ (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and dcir.backup_date=" + backup_date + "   " : "")

							// add by huangjl 添加
							// da_company_industry_rell----------start
							+ " union select dc.id,dc.second_area,dc.third_area from " + da_company + " dc  " + " left join " + da_company_pass + " dcp on dcp.par_da_com_id = dc.id  " + " left join " + " da_company_industry_rell " + " dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0   " + comcreatesql + " " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null  " + (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "")
							+ " "
							// add by huangjl 添加
							// da_company_industry_rell----------end
							+ ") da on da." + areaType + " = fa.area_code  " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlCondition + ") dnd on dnd.par_da_com_id = da.id " + " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and " + " is_danger=1 and is_repaired=1 " + sqlCondition + ") dnd_g on dnd_g.id=dnd.id " + " group by fa.area_code " + " union " + " select fa.area_code,0 anum,0 bnum,count(distinct(dd.id)) aanum,count(distinct(ddg.par_da_dan_id)) bbnum,count(distinct(dd_f.id)) ccnum from  " + " (select area_code from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + "  area_code= " + area.getAreaCode() + ")) fa " + " left join (select dc.id,dc.second_area,dc.third_area from "
							+ da_company + " dc  " + " left join " + da_company_pass + " dcp on dcp.par_da_com_id = dc.id  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0   " + comcreatesql + " " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null  " + (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + " " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and dcir.backup_date=" + backup_date + "   " : "")

							// add by huangjl 添加
							// da_company_industry_rell----------start
							+ " union select dc.id,dc.second_area,dc.third_area from " + da_company + " dc  " + " left join " + da_company_pass + " dcp on dcp.par_da_com_id = dc.id  " + " left join " + " da_company_industry_rell " + " dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0   " + comcreatesql + "" + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null  " + (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + " " + (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + " "
							// add by huangjl 添加
							// da_company_industry_rell----------end

							+ ") da on da." + areaType + " = fa.area_code  " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlCondition + ") dd on dd.par_da_com_id = da.id " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlCondition + ") ddg on ddg.par_da_dan_id = dd.id  " + " left join (select id from da_danger where is_deleted=0 and finish_date < to_date('" + mDateTime + "','yyyy-MM-dd') " + " and id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0) " + sqlCondition + ") dd_f on dd_f.id = dd.id " + " and dd.id is not null group by fa.area_code) value group by value.area_code";

//					System.out.println("==========" + sql);
					ResultSet res = companyPersistenceIface.findBySql(sql);
					// ResultSet res=null;
					// try {
					// res =
					// cFactory.createConnection().prepareStatement(sql).executeQuery();
					// } catch (SQLException e1) {
					// e1.printStackTrace();
					// }
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
						res.getStatement().close();
						res.close();

						if (backup_date == 0) { // 动态的 设置过期时间
							cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
						} else { // 永不过期
							cache.add(cacheName, statistics);
						}

						ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
						ResultSet rs = null;

						try {
							if (c.next()) { // 已存在,更新
								PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各地隐患治理1" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
								rs = pState.executeQuery();
							} else { // 新建
								PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各地隐患治理1" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
								rs = pState.executeQuery();
							}
							rs.getStatement().close();
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return statistics;
	}

	/**
	 * 一般隐患、重大隐患整治统计
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadTroubleByNomalAndHiddenColligation(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sqlCondition = "";
		String IndType = " left join da_industry_parameter dip on dip.id = dcir.par_da_ind_id  where  dip.is_deleted=0  and dip.type=1 and ";
		if (st.getRemark() != null) {
			IndType = IndType + "   dip.depiction = '" + st.getRemark() + "'  and ";
		}
		if (st != null) {
			if (st.getIsSonType() == 0)
				IndType = " where ";
			int nextYear = st.getYear() + 1;
			// if(st.getQuarter() == 1){
			// sqlCondition =
			// " and create_time between to_date('"+st.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+st.getYear()+"-04-01','yyyy-MM-dd')";
			// }else if(st.getQuarter() == 2){
			// sqlCondition =
			// " and create_time between to_date('"+st.getYear()+"-04-01','yyyy-MM-dd') and to_date('"+st.getYear()+"-07-01','yyyy-MM-dd')";
			// }else if(st.getQuarter() == 3){
			// sqlCondition =
			// " and create_time between to_date('"+st.getYear()+"-07-01','yyyy-MM-dd') and to_date('"+st.getYear()+"-10-01','yyyy-MM-dd')";
			// }else if(st.getQuarter() == 4){
			// sqlCondition =
			// " and create_time between to_date('"+st.getYear()+"-10-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
			// }
			// sqlCondition =
			// " and create_time between to_date('"+st.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
			// if (st.getEnd_month() == 12) {
			// sqlCondition = " and create_time between to_date('" +
			// st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month()
			// : "0" + st.getBeg_month())
			// + "-01','yyyy-MM-dd') and to_date('" + nextYear +
			// "-01-01','yyyy-MM-dd')";
			// } else {
			// sqlCondition = " and create_time between to_date('" +
			// st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month()
			// : "0" + st.getBeg_month())
			// + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" +
			// ((st.getEnd_month() + 1) >= 10 ? (st.getEnd_month() + 1) : "0" +
			// (st.getEnd_month() + 1))
			// + "-01','yyyy-MM-dd')";
			// }
			sqlCondition = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		String sql = "select area_code,sum(anum) anum,sum(bnum) bnum,sum(aanum) aanum,sum(bbnum) bbnum,sum(ccnum) ccnum from " + " (select fa.area_code,count(distinct(dnd.id)) anum,count(distinct(dnd_g.id)) bnum,0 aanum,0 bbnum,0 ccnum from  " + " (select area_code from fk_area where is_deleted=0 and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  " + " left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0  " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = fa.area_code  " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlCondition + ") dnd on dnd.par_da_com_id = da.id "
				+ " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and " + " is_danger=1 and is_repaired=1 " + sqlCondition + ") dnd_g on dnd_g.id=dnd.id " + " group by fa.area_code " + " union " + " select fa.area_code,0 anum,0 bnum,count(distinct(dd.id)) aanum,count(distinct(ddg.par_da_dan_id)) bbnum,count(distinct(dd_f.id)) ccnum from  " + " (select area_code from fk_area where is_deleted=0 and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  " + " left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0  " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = fa.area_code  " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlCondition
				+ ") dd on dd.par_da_com_id = da.id " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlCondition + ") ddg on ddg.par_da_dan_id = dd.id  " + " left join (select id from da_danger where is_deleted=0 and finish_date < to_date('" + mDateTime + "','yyyy-MM-dd') " + " and id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0) " + sqlCondition + ") dd_f on dd_f.id = dd.id " + " and dd.id is not null group by fa.area_code) value group by value.area_code";
		// System.out.println("一般隐患、重大隐患整治统计sql:" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
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
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 一般隐患、重大隐患整治统计(Json)
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadTroubleByNomalAndHiddenJson(FkArea area, Statistic st) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sqlCondition = "";
		String IndType = " left join da_industry_parameter dip on dip.id = dcir.par_da_ind_id  where  dip.is_deleted=0  and dip.type=1 and ";
		if (st.getRemark() != null) {
			IndType = IndType + "   dip.depiction = '" + st.getRemark() + "'  and ";
		}
		if (st != null) {
			if (st.getIsSonType() == 0)
				IndType = " where ";
			int nextYear = st.getYear() + 1;
			if (st.getEnd_month() == 12) {
				sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" + ((st.getEnd_month() + 1) >= 10 ? (st.getEnd_month() + 1) : "0" + (st.getEnd_month() + 1)) + "-01','yyyy-MM-dd')";
			}
		}
		String sql = "select area_code,sum(anum) anum,sum(bnum) bnum,sum(aanum) aanum,sum(bbnum) bbnum,sum(ccnum) ccnum from " + " (select fa.area_code,count(distinct(dnd.id)) anum,count(distinct(dnd_g.id)) bnum,0 aanum,0 bbnum,0 ccnum from  " + " (select area_code from fk_area where is_deleted=0 and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  " + " left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0  " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = fa.area_code  " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlCondition + ") dnd on dnd.par_da_com_id = da.id "
				+ " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and " + " is_danger=1 and is_repaired=1 " + sqlCondition + ") dnd_g on dnd_g.id=dnd.id " + " group by fa.area_code " + " union " + " select fa.area_code,0 anum,0 bnum,count(distinct(dd.id)) aanum,count(distinct(ddg.par_da_dan_id)) bbnum,count(distinct(dd_f.id)) ccnum from  " + " (select area_code from fk_area where is_deleted=0 and father_id = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) fa " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  " + " left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0  " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = fa.area_code  " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlCondition
				+ ") dd on dd.par_da_com_id = da.id " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlCondition + ") ddg on ddg.par_da_dan_id = dd.id  " + " left join (select id from da_danger where is_deleted=0 and finish_date < to_date('" + mDateTime + "','yyyy-MM-dd') " + " and id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0) " + sqlCondition + ") dd_f on dd_f.id = dd.id " + " and dd.id is not null group by fa.area_code) value group by value.area_code";
		// System.out.println("一般隐患、重大隐患整治统计sql:" + sql);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultSet res = companyPersistenceIface.findBySql(sql);
			while (res.next()) {
				map.put("areaCode", res.getLong(1));
				map.put("area_" + res.getLong(1) + "_n_num", res.getInt(2));
				map.put("area_" + res.getLong(1) + "_n_w_num", res.getInt(3));
				map.put("area_" + res.getLong(1) + "_d_num", res.getInt(4));
				map.put("area_" + res.getLong(1) + "_d_w_num", res.getInt(5));
				map.put("area_" + res.getLong(1) + "_p_num", res.getInt(6));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 挂牌重大隐患整治统计(Json)
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadTroubleByRollcallJson(FkArea area, Statistic st) throws ApplicationAccessException {
		Map<String, Object> oMap = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sqlCondition = "";
		String sqlConditionD = "";
		String IndType = " left join da_industry_parameter dip on dip.id = dcir.par_da_ind_id  where  dip.is_deleted=0  and dip.type=1 and ";
		if (st.getRemark() != null) {
			IndType = IndType + "   dip.depiction = '" + st.getRemark() + "'  and ";
		}

		if (st != null) {
			if (st.getIsSonType() == 0)
				IndType = " where ";
			int nextYear = st.getYear() + 1;
			if (st.getEnd_month() == 12) {
				sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlCondition = " and create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" + ((st.getEnd_month() + 1) >= 10 ? (st.getEnd_month() + 1) : "0" + (st.getEnd_month() + 1)) + "-01','yyyy-MM-dd')";
			}
			if (st.getEnd_month() == 12) {
				sqlConditionD = " and drc.create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlConditionD = " and drc.create_time between to_date('" + st.getYear() + "-" + (st.getBeg_month() >= 10 ? st.getBeg_month() : "0" + st.getBeg_month()) + "-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-" + ((st.getEnd_month() + 1) >= 10 ? (st.getEnd_month() + 1) : "0" + (st.getEnd_month() + 1)) + "-01','yyyy-MM-dd')";
			}
		}

		String sql = "select count(distinct(dd.id)) inhere,count(distinct(ddg.par_da_dan_id)) num, " + " map.area_code,map.enum_code,map.enum_name " + " from (select fa.area_code,fe.enum_code,fe.enum_name,fe.grade_path from fk_area fa  " + " full join (select enum_name,enum_code,grade_path from fk_enum where is_deleted=0 and  " + " father_id = (select id from fk_enum where enum_code='rollcallCompany')) fe on 1!=2   " + " where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map  " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  " + " left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id " + IndType + " dc.is_deleted=0  " + " and dcp.is_deleted=0 and dcp.is_affirm=1 and dcir.par_da_com_id is not null) da on da." + areaType + " = map.area_code  " + " left join (select d.id,d.par_da_com_id,drc.levels from da_danger d "
				+ " left join da_rollcall_company drc on drc.par_da_dan_id = d.id " + " where d.is_deleted=0 and drc.is_deleted=0 and d.id is not null " + sqlConditionD + ") " + " dd on dd.par_da_com_id = da.id and dd.levels = map.enum_code " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlCondition + ") ddg on ddg.par_da_dan_id = dd.id  " + " group by map.area_code,map.enum_code,map.enum_name,map.grade_path" + " order by map.enum_code";
		// System.out.println("挂牌重大隐患整治统计sql: " + sql);
		int i = 1;
		int t_sum = 0;
		int t_sum_1 = 0;
		String type = "";
		Map<String, Object> map = null;
		try {
			ResultSet res = companyPersistenceIface.findBySql(sql);
			while (res.next()) {
				if (!type.equals((res.getString(4) != null ? res.getString(4) : ""))) {
					if (i != 1) {
						map.put("t_sum", t_sum);
						map.put("t_sum_1", t_sum_1);
						listMap.add(map);
					}
					i++;
					t_sum = 0;
					type = res.getString(4) != null ? res.getString(4) : "";
					map = new HashMap<String, Object>();
					map.put("type", type);
					map.put("typeName", res.getString(5));
				}
				t_sum += res.getInt(1);
				t_sum_1 += res.getInt(2);
				map.put("area_" + res.getLong(3) + "_0", res.getInt(1));
				map.put("area_" + res.getLong(3) + "_1", res.getInt(2));
			}
			res.close();
			map.put("t_sum", t_sum);
			map.put("t_sum_1", t_sum_1);
			listMap.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		oMap.put("mapList", listMap);
		return oMap;
	}

	/**
	 * 一般隐患列表
	 * 
	 * @param company
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaNomalDanger> loadNomalTroubleByTypeList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = null;
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		String fk_area = "fk_area";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		int backup_date = 0; // 历史表查询的日期
		int month1 = cal.get(Calendar.MONTH) + 1;
		int month = 0;
		String comcreatesql = "";
		String sql0 = "";
		if (statistic != null) {

			if (statistic.getMonth() != 0) { // 月报
				month = statistic.getMonth() + 1;

				if (statistic.getYear() != year1) {

					if (statistic.getYear() > year1) {

						// System.out.println("动态表");

					} else if (statistic.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						if (statistic.getMonth() <= 9) {
							backup_date = Integer.parseInt(statistic.getYear() + "0" + statistic.getMonth());
						} else {
							backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
						}

					} else if (statistic.getYear() == 2013) {

						if (statistic.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						backup_date = 201311;
					}

				} else if (statistic.getMonth() < month1) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					da_industry_parameter = "da_industry_parameter_his";

					if (statistic.getMonth() <= 9) {
						backup_date = Integer.parseInt(statistic.getYear() + "0" + statistic.getMonth());
					} else {
						backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != statistic.getQuarter()) { // 当前时间与查询时不一致
																	// ,则取历史表
																	// 2013_11开始备份

					if (statistic.getQuarter() == 0) { // 全年

						if (statistic.getYear() != year1 && statistic.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = 201311;
						} else if (statistic.getYear() != year1 && statistic.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							fk_area = "fk_area_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							da_industry_parameter = "da_industry_parameter_his";
							backup_date = Integer.parseInt(statistic.getYear() + "12");
						}

					} else { // 季度

						fk_area = "fk_area_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						da_industry_parameter = "da_industry_parameter_his";
						if (statistic.getYear() < 2013 || (statistic.getYear() == 2013 && statistic.getQuarter() <= 3 && statistic.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (statistic.getQuarter() == 4) {
							backup_date = Integer.parseInt(statistic.getYear() + "12");

						} else if (statistic.getQuarter() == 3) {
							backup_date = Integer.parseInt(statistic.getYear() + "09");

						} else if (statistic.getQuarter() == 2) {
							backup_date = Integer.parseInt(statistic.getYear() + "06");

						} else if (statistic.getQuarter() == 1) {
							backup_date = Integer.parseInt(statistic.getYear() + "03");

						}
					}
				}
			}
		}

		if (fk_area.equals("fk_area_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger1.class, "dnd");
			detachedCriteriaProxy.createCriteria("daCompanyPassHis", "dcp");
			detachedCriteriaProxy.createCriteria("daCompanyHis", "da");

			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("dcp1_.backup_date =" + backup_date));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("da2_.backup_date =" + backup_date + "  "));
		} else {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
			detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "da");
		}
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea_his(area.getAreaCode(), statistic, current_quarter);
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				sql0 += " and da2_.SECOND_AREA=" + area.getAreaCode();
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
				sql0 += " and da2_.SECOND_AREA=" + area.getAreaCode();
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("SECOND_AREA in (select a.area_code from " + fk_area + " a where  " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + ")"));
				sql0 += " and da2_.first_area=" + area.getAreaCode();
				sql0 += " and da2_.SECOND_AREA in (select a.area_code from " + fk_area + " a where  " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + ")";
			}
		}
		if (statistic != null) {

			if (statistic.getCompanyName() != null && !"".equals(statistic.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("da.companyName", statistic.getCompanyName().trim(), MatchMode.ANYWHERE));
				sql0 += " and da2_.company_name  like '%" + statistic.getCompanyName().trim() + "%'";
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
			} else if (statistic.getEnd_month() == 12) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-" + 01 + "-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-" + (statistic.getEnd_month() + 1) + "-01','yyyy-MM-dd')";
			}

			// 判断是否当前年份，如果是当前年份的话，要过滤到当前月份。
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int nowMonth = calendar.get(Calendar.MONTH) + 1;
			if (year == statistic.getYear()) {
				sqlParam += " and da2_.create_time<to_date('" + statistic.getYear() + "-" + nowMonth + "-01','yyyy-MM-dd') ";
			} else {
				sqlParam += " and da2_.create_time<to_date('" + statistic.getYear() + "-" + (statistic.getEnd_month()) + "-01','yyyy-MM-dd') ";
			}

			sql0 += " and " + sqlParam;
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
			if (statistic.getRemark() != null && !"".equals(statistic.getRemark().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + " " + " where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "    backup_date=" + backup_date + " and   " : "") + "par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "depiction='" + statistic.getRemark() + "' and type=1)) or  this_.par_da_com_id in (select par_da_com_id from da_company_industry_rell" + " where par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "depiction='" + statistic.getRemark() + "' and type=1))  )"));
				sql0 += " and  (this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + " " + " where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "    backup_date=" + backup_date + " and   " : "") + "par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "depiction='" + statistic.getRemark() + "' and type=1)) or  this_.par_da_com_id in (select par_da_com_id from da_company_industry_rell" + " where par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + "depiction='" + statistic.getRemark() + "' and type=1))  ) ";
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where   backup_date=" + backup_date + "   " : "") + ")   or  this_.par_da_com_id in (select par_da_com_id from da_company_industry_rell  ) )"));
				sql0 += " and (this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where   backup_date=" + backup_date + "   " : "") + ")   or  this_.par_da_com_id in (select par_da_com_id from da_company_industry_rell  ) )";
			}
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
			}
		}
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" da2_.create_time<to_date('"
		// + statistic.getYear() + "-" + (statistic.getEnd_month()) +
		// "-01','yyyy-MM-dd')"));

		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.id"));

		if (fk_area.equals("fk_area_his")) {
			List<DaNomalDanger1> daNomalDanger1 = new ArrayList<DaNomalDanger1>();
			Map<String, Object> map = null;
			String sql = " select  this_.description,this_.create_time,this_.type,this_.completed_date,da2_.company_name,this_.user_id,this_.is_repaired,dcp1_.com_user_id  from DA_NORMAL_DANGER  this_,DA_COMPANY_PASS_his   dcp1_, DA_COMPANY_his  da2_  where this_.PAR_DA_COM_ID = dcp1_.PAR_DA_COM_ID   and dcp1_.PAR_DA_COM_ID = da2_.ID  and this_.IS_DELETED =0  and  dcp1_.backup_date=" + backup_date + "  and dcp1_.IS_DELETED=0  and da2_.IS_DELETED =0  and  da2_.backup_date=" + backup_date + "  " + sql0 + " and this_.IS_DANGER =1 and dcp1_.IS_AFFIRM = 1 order by da2_.ID desc, this_.ID desc ";
			// System.out.println("sql: " + sql);
			String sql1 = " select  count(*)  from DA_NORMAL_DANGER  this_,DA_COMPANY_PASS_his   dcp1_, DA_COMPANY_his  da2_  where this_.PAR_DA_COM_ID = dcp1_.PAR_DA_COM_ID   and dcp1_.PAR_DA_COM_ID = da2_.ID  and this_.IS_DELETED =0  and  dcp1_.backup_date=" + backup_date + "  and dcp1_.IS_DELETED=0  and da2_.IS_DELETED =0  and  da2_.backup_date=" + backup_date + "  " + sql0 + " and this_.IS_DANGER =1 and dcp1_.IS_AFFIRM = 1 order by da2_.ID desc, this_.ID desc";
			List<DaNomalDanger> daNomalDanger = new ArrayList<DaNomalDanger>();
			try {
				ResultSet res = danger1PersistenceIface.findPageBySQL(sql);
				ResultSet res1 = danger1PersistenceIface.findPageBySQL(sql1);
				int startNum = pagination.getItemCount();// 页面实现开始数
				int endNum = startNum + pagination.getPageSize();// 页面显示结束数
				int totalNum = 0;// 累计查询结果总数
				int x = 0;
				while (res.next()) {
					if (totalNum >= startNum && totalNum < endNum) {// 只取页面显示的记录
						map = new HashMap<String, Object>();
						x++;
						DaNomalDanger dn = new DaNomalDanger();
						dn.setDescription(res.getString(1));
						dn.setCreateTime(res.getString(2) != null ? res.getDate(2) : null);
						dn.setType(Integer.parseInt(res.getString(3)));
						dn.setCompletedDate(res.getString(4) != null ? res.getDate(4) : null);
						DaCompanyPass dcp = new DaCompanyPass();
						DaCompany dc = new DaCompany();
						dc.setCompanyName(res.getString(5));
						dcp.setComUserId(res.getLong(8));
						dcp.setDaCompany(dc);
						dn.setDaCompanyPass(dcp);
						dn.setUserId(res.getLong(6));
						dn.setRepaired(res.getString(7).equals("1") ? true : false);
						daNomalDanger.add(dn);
					}
					totalNum++;
					if (totalNum == endNum) {
						break;
					}
				}
				res.close();
				while (res1.next()) {// 结合分页实现
					totalNum = res1.getInt(1);
				}
				res1.close();
				pagination.setTotalCount(totalNum);// 更新查询结果总数
			} catch (Exception e) {
				e.printStackTrace();
			}

			return daNomalDanger;
		} else {
			return nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
		}
	}

	/**
	 * 重大隐患列表
	 * 
	 * @param company
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaDanger> loadDangerTroubleByTypeList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		int year1 = cal.get(Calendar.YEAR);
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		int backup_date = 0; // 历史表查询的日期
		String sql0 = "";
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		if (statistic != null) {

			if (statistic.getMonth() != 0) { // 月报

				if (statistic.getYear() != year1) {

					if (statistic.getYear() > year1) {

						// System.out.println("动态表");

					} else if (statistic.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (statistic.getMonth() <= 9) {
							backup_date = Integer.parseInt(statistic.getYear() + "0" + statistic.getMonth());
						} else {
							backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
						}

					} else if (statistic.getYear() == 2013) {

						if (statistic.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						backup_date = 201311;
					}

				} else if (statistic.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";

					if (statistic.getMonth() <= 9) {
						backup_date = Integer.parseInt(statistic.getYear() + "0" + statistic.getMonth());
					} else {
						backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != statistic.getQuarter()) { // 当前时间与查询时不一致
																	// ,则取历史表
																	// 2013_11开始备份

					if (statistic.getQuarter() == 0) { // 全年

						if (statistic.getYear() != year1 && statistic.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = 201311;
						} else if (statistic.getYear() != year1 && statistic.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							fk_area = "fk_area_his";
							da_company = "da_company_his";
							da_company_pass = "da_company_pass_his";
							da_company_industry_rel = "da_company_industry_rel_his";
							backup_date = Integer.parseInt(statistic.getYear() + "12");
						}

					} else { // 季度
						fk_area = "fk_area_his";
						da_company = "da_company_his";
						da_company_pass = "da_company_pass_his";
						da_company_industry_rel = "da_company_industry_rel_his";
						if (statistic.getYear() < 2013 || (statistic.getYear() == 2013 && statistic.getQuarter() <= 3 && statistic.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (statistic.getQuarter() == 4) {
							backup_date = Integer.parseInt(statistic.getYear() + "12");

						} else if (statistic.getQuarter() == 3) {
							backup_date = Integer.parseInt(statistic.getYear() + "09");

						} else if (statistic.getQuarter() == 2) {
							backup_date = Integer.parseInt(statistic.getYear() + "06");

						} else if (statistic.getQuarter() == 1) {
							backup_date = Integer.parseInt(statistic.getYear() + "03");

						}
					}
				}
			}
		}

//		System.out.println("backup_date: " + backup_date);
		if (fk_area.equals("fk_area_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger1.class, "dd");
			detachedCriteriaProxy.createCriteria("daCompanyPassHis", "dcp").createCriteria("daCompanyHis", "da");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("da2_.backup_date=" + backup_date + "   and  dcp1_.backup_date=" + backup_date + "  "));

		} else {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
			detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "da");
		}
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea_his(area.getAreaCode(), statistic, current_quarter);
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
				sql0 += " and da2_.SECOND_AREA=" + area.getAreaCode();
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
				sql0 += " and da2_.third_area=" + area.getAreaCode();
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("da2_.SECOND_AREA in (select a.area_code from " + fk_area + " a where  " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + ")"));
				sql0 += " and da2_.first_area=" + area.getAreaCode();
				sql0 += " and da2_.SECOND_AREA in (select a.area_code from " + fk_area + " a where  " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + ")";

			}
		}
		if (statistic != null) {
			if (statistic.getCompanyName() != null && !"".equals(statistic.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("da.companyName", statistic.getCompanyName().trim(), MatchMode.ANYWHERE));
				sql0 += " and da2_.company_name like  '%" + statistic.getCompanyName().trim() + "%'";
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

			if (statistic.getEnd_month() == 12) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-" + (statistic.getEnd_month() + 1) + "-01','yyyy-MM-dd')";
			}
			sql0 += " and " + sqlParam;

			if (statistic.getRemark() != null && !"".equals(statistic.getRemark().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + " " + " where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " depiction='" + statistic.getRemark() + "' and type=1)   union  select par_da_com_id from da_company_industry_rell where  par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " depiction='" + statistic.getRemark() + "' and type=1))"));
				sql0 += " and this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + " " + " where " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "  backup_date=" + backup_date + "  and " : "") + " par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " depiction='" + statistic.getRemark() + "' and type=1)   union  select par_da_com_id from da_company_industry_rell where  par_da_ind_id = (select id from " + da_industry_parameter + "  where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + backup_date + "  and " : "") + " depiction='" + statistic.getRemark() + "' and type=1))";
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "  " : "") + "  union  select par_da_com_id from da_company_industry_rell   )"));
				sql0 += " and this_.par_da_com_id in (select par_da_com_id from " + da_company_industry_rel + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? " where  backup_date=" + backup_date + "  " : "") + "  union  select par_da_com_id from da_company_industry_rell   )";
			}
			if (statistic.getIsRollcall() != null && !"".equals(statistic.getIsRollcall().trim())) {
				if ("0".equals(statistic.getIsRollcall().trim())) {
					if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
						if ("0".equals(statistic.getIsGorver().trim())) { // 未整改
							detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)"));
							sql0 += " and   g.id is null ";
						} else if ("2".equals(statistic.getIsGorver().trim())) {
							detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date < to_date('" + mDateTime + "','yyyy-MM-dd') and this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)"));
							sql0 += " and this_.finish_date < to_date('" + mDateTime + "','yyyy-MM-dd') and g.id is null ";
						}
					}
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
				} else if (statistic.getEnumCode() != null && !"".equals(statistic.getEnumCode().trim()) && "1".equals(statistic.getIsRollcall().trim())) {
					sqlParam = " and create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_dan_id from da_rollcall_company where is_deleted=0 and levels = '" + statistic.getEnumCode() + "' " + sqlParam + ")"));
					sql0 += " and this_.id in (select par_da_dan_id from da_rollcall_company where is_deleted=0 and levels = '" + statistic.getEnumCode() + "' " + sqlParam + ")";
					if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)"));
						// sql0 +=
						// " and this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)";

					}
				}
			}
		}

		// 判断是否当前年份，如果是当前年份的话，要过滤到当前月份。
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		// if (year == statistic.getYear()) {
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" da2_.create_time<to_date('"
		// + statistic.getYear() + "-" + nowMonth + "-01','yyyy-MM-dd')"));
		// sql0 += " and  da2_.create_time<to_date('" + statistic.getYear() +
		// "-" + nowMonth + "-01','yyyy-MM-dd')";
		// } else {
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" da2_.create_time<to_date('" + statistic.getYear() + "-" + (statistic.getEnd_month()) + "-01','yyyy-MM-dd')"));
		sql0 += " and  da2_.create_time<to_date('" + statistic.getYear() + "-" + (statistic.getEnd_month()) + "-01','yyyy-MM-dd')";
		// }

		String create_time = "";
		int nextmonth = 0;
		int nextyear = statistic.getYear() + 1;
		if (statistic.getEnd_month() != 12) {
			nextmonth = statistic.getEnd_month() + 1;
			create_time = " create_time between   to_date('" + statistic.getYear() + "-01-01', 'yyyy-MM-dd') and  to_date('" + statistic.getYear() + "-" + nextmonth + "-01', 'yyyy-MM-dd')";
		} else {
			create_time = " create_time between   to_date('" + statistic.getYear() + "-01-01', 'yyyy-MM-dd') and  to_date('" + nextyear + "-01-01', 'yyyy-MM-dd')";
		}

		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		if (fk_area.equals("fk_area_his")) {
			List<DaDanger> daDanger = new ArrayList<DaDanger>();
			Map<String, Object> map = null;
			String sql = " select  this_.id,da2_.company_name,this_.danger_no,this_.danger_add,this_.finish_date,da2_.id , g.finish_date,r.id ,this_.first_area  from  DA_COMPANY_PASS_his dcp1_, DA_COMPANY_his da2_,DA_DANGER this_   left join (select finish_date,par_da_dan_id,id from  da_danger_gorver   where is_deleted = 0 and " + create_time + ")  g  on  this_.id=g.par_da_dan_id  left join  (select id,par_da_dan_id from  da_rollcall_company  where is_deleted=0 and " + create_time + ")  r  on  this_.id=r.par_da_dan_id    where this_.PAR_DA_COM_ID=dcp1_.PAR_DA_COM_ID  and  dcp1_.backup_date=" + backup_date + "  and  da2_.backup_date=" + backup_date + "   and dcp1_.PAR_DA_COM_ID=da2_.ID   and this_.IS_DELETED=0   and dcp1_.IS_DELETED=0   and da2_.IS_DELETED=0   " + sql0 + "    and dcp1_.IS_AFFIRM=1   order by   da2_.ID desc,this_.ID desc ";
			// System.out.println("sql: " + sql);
			String sql1 = " select  count(*)  from  DA_COMPANY_PASS_his dcp1_, DA_COMPANY_his da2_,DA_DANGER this_   left join (select finish_date,par_da_dan_id,id from  da_danger_gorver   where is_deleted = 0 and  " + create_time + ")  g  on  this_.id=g.par_da_dan_id  left join  (select id,par_da_dan_id from  da_rollcall_company  where is_deleted=0 and " + create_time + ")  r  on  this_.id=r.par_da_dan_id    where this_.PAR_DA_COM_ID=dcp1_.PAR_DA_COM_ID  and  dcp1_.backup_date=" + backup_date + "  and  da2_.backup_date=" + backup_date + "   and dcp1_.PAR_DA_COM_ID=da2_.ID   and this_.IS_DELETED=0   and dcp1_.IS_DELETED=0   and da2_.IS_DELETED=0   " + sql0 + "    and dcp1_.IS_AFFIRM=1   ";

			try {
				ResultSet res = danger1PersistenceIface.findPageBySQL(sql);
				ResultSet res1 = danger1PersistenceIface.findPageBySQL(sql1);
				int startNum = pagination.getItemCount();// 页面实现开始数
				int endNum = startNum + pagination.getPageSize();// 页面显示结束数
				int totalNum = 0;// 累计查询结果总数
				int x = 0;
				while (res.next()) {
					if (totalNum >= startNum && totalNum < endNum) {// 只取页面显示的记录
						map = new HashMap<String, Object>();
						x++;
						DaDanger dn = new DaDanger();
						dn.setId(res.getLong(1));
						dn.setDangerNo(res.getString(3));
						dn.setDangerAdd(res.getString(4));
						dn.setFinishDate(res.getString(5) != null ? res.getDate(5) : null);
						DaCompanyPass dcp = new DaCompanyPass();
						DaCompany dc = new DaCompany();
						dc.setId(res.getLong(6));
						dc.setCompanyName(res.getString(2));

						if (res.getString(8) != null) {
							dc.setAffrim(1); // 已挂牌
						}
						if (res.getString(7) != null) {
							dc.setCompanyCode(res.getString(7) != null ? res.getString(7).substring(0, 10) : null); // 存放已治理时间
						}

						if (res.getString(7) != null) {
							Set<DaDangerGorver> daDangerGorvers = new HashSet<DaDangerGorver>(1);
							DaDangerGorver daDangerGorver = new DaDangerGorver();
							daDangerGorver.setFinishDate(res.getDate(7));
							daDangerGorvers.add(daDangerGorver);
							dn.setDaDangerGorvers(daDangerGorvers); // 存放已治理时间
						}
						if (res.getString(5) != null) {
							String currTime = year + "-" + nowMonth + "-" + day;
							Date dt1 = formatter.parse(currTime);
							Date dt2 = formatter.parse(res.getString(5).substring(0, 10));
							long l = dt1.getTime() - dt2.getTime();
							long i = l / (1000 * 60 * 60 * 24);

							if (i > 0) {
								dc.setFlag("1");// 超期
							}

						}

						dcp.setDaCompany(dc);
						dn.setDaCompanyPass(dcp);

						daDanger.add(dn);
					}
					totalNum++;
					if (totalNum == endNum) {
						break;
					}
				}
				res.close();
				while (res1.next()) {// 结合分页实现
					totalNum = res1.getInt(1);
				}
				res1.close();
				pagination.setTotalCount(totalNum);// 更新查询结果总数
			} catch (Exception e) {
				e.printStackTrace();
			}

			return daDanger;
		} else {
			List<DaDanger> daDanger = dangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
			// for (int i = 0; i < daDanger.size(); i++) {
			// System.out.println("sfhhhhh:" +
			// daDanger.get(0).getDaDangerGorvers());
			// }

			return daDanger;
		}
	}

	/**
	 * 打非统计
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadThreeIllegal(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sqlCondition = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getQuarter() == 1) {
				sqlCondition = " and i.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-04-01','yyyy-MM-dd')";
			} else if (st.getQuarter() == 2) {
				sqlCondition = " and i.create_time between to_date('" + st.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-07-01','yyyy-MM-dd')";
			} else if (st.getQuarter() == 3) {
				sqlCondition = " and i.create_time between to_date('" + st.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + st.getYear() + "-10-01','yyyy-MM-dd')";
			} else if (st.getQuarter() == 4) {
				sqlCondition = " and i.create_time between to_date('" + st.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
		}
		String sql = "select map.name,map.enum_name,sum(value.illegal_build_getting),sum(value.illegal_build_candeled),sum(value.illegal_build_canceling), " + " sum(value.illegal_produce_getting),sum(value.illegal_produce_canceled),sum(value.illegal_produce_canceling), " + " sum(value.illegal_trade_getting),sum(value.illegal_trade_calceled),sum(value.illegal_trade_canceling) from " + " (select dip.id,dip.name,fe.enum_name,fe.enum_code,dip.grade_path dipgp,fe.grade_path fegp from " + " (select id,name,grade_path,depiction from da_industry_parameter where is_deleted = 0 and type = 4) dip " + " left join (select enum_name,enum_code,grade_path from fk_enum where is_deleted=0 and  " + " father_id = (select id from fk_enum where enum_code='threeIllegal')) fe on dip.depiction like '%'||fe.enum_code||'%') map " + " left join (select i.par_da_ind_id,i.illegal_build_getting,i.illegal_build_candeled,i.illegal_build_canceling, "
				+ " i.illegal_produce_getting,i.illegal_produce_canceled,i.illegal_produce_canceling,f.user_industry, " + " i.illegal_trade_getting,i.illegal_trade_calceled,i.illegal_trade_canceling from da_three_illegal i  " + " left join fk_user_info f on i.user_id = f.id where i.is_deleted=0 and f.is_deleted=0 " + sqlCondition + ") value " + " on map.id = value.par_da_ind_id and map.enum_code = value.user_industry  " + " group by map.name,map.enum_name,map.dipgp,map.fegp order by map.dipgp,map.fegp";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setName(res.getString(1));
				statistic.setEnumName(res.getString(2));
				statistic.setAnum(res.getInt(3));
				statistic.setBnum(res.getInt(4));
				statistic.setCnum(res.getInt(5));
				statistic.setAanum(res.getInt(6));
				statistic.setBbnum(res.getInt(7));
				statistic.setCcnum(res.getInt(8));
				statistic.setAaanum(res.getInt(9));
				statistic.setBbbnum(res.getInt(10));
				statistic.setCccnum(res.getInt(11));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 重大隐患类型统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadDangerTypeByIndustry(FkArea area, String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sql = "select count(distinct(d.id)) inhere,count(distinct(ddg.par_da_dan_id)) num,map.area_code,map.dip_id,map.name, " + " (select count(1) from da_industry_parameter where is_deleted = 0 and grade_path like (select grade_path||'%' from da_industry_parameter where id=map.dip_id)) sonCount, " + " (select 1 from da_industry_parameter where is_deleted = 0 and rownum=1 and par_da_ind_id in (map.dip_id)) sonNumber1,  " + " (select 1 from da_industry_parameter where is_deleted = 0 and rownum=1 and par_da_ind_id in (select id from  da_industry_parameter where par_da_ind_id=map.dip_id)) sonNumber2 " + " from (select fa.area_code,fa.area_name,dip.id dip_id,dip.grade_path,dip.name from fk_area fa  " + " full join (select id,name,grade_path from da_industry_parameter where is_deleted=0 and  " + " grade_path like (select grade_path||'%' from da_industry_parameter where depiction = '" + remark + "' and type=3)) dip on 1!=2 "
				+ " where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map  " + " left join da_danger_type_rel ddtr on ddtr.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc left join da_company_pass dcp " + " on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1) da on da." + areaType + " = map.area_code " + " inner join da_danger d on d.par_da_com_id = da.id and d.is_deleted = 0 and d.id = ddtr.par_da_dan_id " + " left join da_danger_gorver ddg on ddg.par_da_dan_id = d.id and ddg.is_deleted = 0 " + " group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setAreaCode(res.getLong(3));
				statistic.setIndustryId(res.getInt(4));
				statistic.setEnumName(res.getString(5));
				statistic.setSonCount(res.getInt(6));
				statistic.setSonNumber1(res.getInt(7));
				statistic.setSonNumber2(res.getInt(8));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return switchList(statistics, area.getAreaCode(), Nbyhpc.TROUBLE_TYPE);
	}

	private List<Statistic> switchList(List<Statistic> statistics, Long areaCode, Integer ind) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("areaCode"));
		List<FkArea> areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", ind));
		detachedCriteriaProxy.setProjection(ProjectionsProxy.projectionList().add(ProjectionsProxy.property("dip.id")).add(ProjectionsProxy.property("dip.name")).add(ProjectionsProxy.sqlProjection("(select count(1) from da_industry_parameter where " + " grade_path like (select grade_path||'%' from da_industry_parameter where id=this_.id)) sonCount", new String[] { "sonCount" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("(select 1 from da_industry_parameter where rownum=1 and par_da_ind_id " + "=this_.id) sonNumber1", new String[] { "sonNumber1" }, new Type[] { (new IntegerType()) })).add(ProjectionsProxy.sqlProjection("(select 1 from da_industry_parameter where rownum=1 and " + "par_da_ind_id in (select id from  da_industry_parameter where par_da_ind_id=this_.id)) sonNumber2", new String[] { "sonNumber2" }, new Type[] { (new IntegerType()) })));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("dip.gradePath"));
		List<DaIndustryParameter> types = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
		List<Statistic> tmp = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Object type = new Object();
		Object[] os = new Object[5];
		if (types != null && types.size() > 0) {
			for (int i = 0; i < areas.size(); i++) {
				for (int j = 0; j < types.size(); j++) {
					for (int k = 0; k < statistics.size(); k++) {
						type = (Object) types.get(j);
						os = (Object[]) type;
						if (areas.get(i).getAreaCode().equals(statistics.get(k).getAreaCode()) && os[0].toString().equals(statistics.get(k).getIndustryId().toString())) {
							tmp.add(statistics.get(k));
							break;
						} else if (k == statistics.size() - 1) {
							statistic = new Statistic();
							statistic.setAreaCode(areas.get(i).getAreaCode());
							statistic.setIndustryId(Integer.parseInt(os[0].toString()));
							statistic.setInhere(0);
							statistic.setNumber(0);
							statistic.setEnumName(os[1].toString());
							statistic.setSonCount(Integer.parseInt(os[2].toString()));
							statistic.setSonNumber1(os[3] != null ? 1 : 0);
							statistic.setSonNumber2(os[4] != null ? 1 : 0);
							tmp.add(statistic);
						}
					}
				}
			}
		}
		return tmp;
	}

	/**
	 * 重大隐患类型统计列表
	 * 
	 * @param statistic
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaDanger> loadDangerTypeByIndustryList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException {
		List<DaDanger> dangers = new ArrayList<DaDanger>();
		List<DaDanger> dangersByindustry = new ArrayList<DaDanger>();
		List<DaDanger> dangersList = new ArrayList<DaDanger>();
		List<DaIndustryParameter> industrys = new ArrayList<DaIndustryParameter>();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "da");
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
			}
		}
		if (statistic != null) {
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
				if ("0".equals(statistic.getIsGorver().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)"));
				}
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		dangers = dangerPersistenceIface.loadDangers(detachedCriteriaProxy, null);

		detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		if (statistic.getIndustryId() != 0) {
			DaIndustryParameter industry = new DaIndustryParameter();
			industry = tradeTypePersistenceIface.loadTradeType(new DaIndustryParameter((long) (statistic.getIndustryId())));
			Integer len = industry.getGradePath().split("/").length - 1;
			if (len == 2) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.id", (long) statistic.getIndustryId()));
			} else if (len == 1) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.daIndustryParameter.id", (long) statistic.getIndustryId()));
			}
		}
		industrys = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
		if (dangers != null && dangers.size() > 0) {
			for (DaDanger d : dangers) {
				for (DaIndustryParameter i : industrys) {
					dangersByindustry = loadDangersByIndustry(i);
					for (DaDanger r : dangersByindustry) {
						if ((long) d.getId() == (long) r.getId()) {
							dangersList.add(d);
						}
					}
				}
			}
		}
		/**
		 * 分页
		 */
		List<DaDanger> dangerList = new ArrayList<DaDanger>();
		int itemCount = 0;
		long afterCount = 0;
		pagination.setTotalCount(dangersList.size());
		if (pagination.getItemCount() == 0) {
			itemCount = 0;
			if (pagination.getPageSize() > pagination.getTotalCount()) {
				afterCount = pagination.getTotalCount();
			} else {
				afterCount = pagination.getPageSize();
			}
		} else if (pagination.getItemCount() > 0) {
			itemCount = pagination.getPageSize() * (pagination.getCurrentPage() - 1);
			if (pagination.getTotalCount() < pagination.getCurrentPage() * pagination.getPageSize()) {
				afterCount = pagination.getTotalCount();
			} else {
				afterCount = pagination.getCurrentPage() * pagination.getPageSize();
			}
		}
		for (int i = itemCount; i < afterCount; i++) {
			dangerList.add(dangersList.get(i));
		}
		return dangerList;
	}

	private List<DaDanger> loadDangersByIndustry(DaIndustryParameter i) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_dan_id from da_danger_type_rel where par_da_ind_id=" + i.getId() + ")"));
		return dangerPersistenceIface.loadDangers(detachedCriteriaProxy, null);
	}

	/**
	 * 项目排查进度统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadItemByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		Integer type = st.getType();
		String sqlParam = "";
		String createParam = "";
		String cacheName = "";
		int backup_date = 0; // 历史表查询的日期
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		String da_item = "da_item";
		String cacheNum = "0";
		if (st != null) {
			if (st.getMonth() != 0) { // 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_item = "da_item_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致 ,则取历史表
															// 2013_11开始备份
					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																			// 2013.11当年取动态
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																					// 2013(包括)之后取那年的12月份
																					// yyyy-12
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
							backup_date = 201311;
						} else if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}
					}
				}
			}

			MonthQueryHelper helper = new MonthQueryHelper(st.getYear(), st.getQuarter(), st.getMonth());
			sqlParam = " and create_time between to_date('" + DateUtils.date2Str(helper.getBeginDate(), null) + "','yyyy-MM-dd') " + "and to_date('" + DateUtils.date2Str(helper.getEndDate(), null) + "','yyyy-MM-dd')";
			createParam = " and di.create_time<to_date('" + DateUtils.date2Str(helper.getEndDate(), null) + "','yyyy-MM-dd') ";
			// log.info("sqlParam: " + sqlParam);

			if (st.getYear() == year && month == st.getMonth()) {
				cacheNum = "0";
			} else if (st.getYear() == year && current_quarter == st.getQuarter()) {
				cacheNum = "0";
			} else {
				if (st.getMonth() == 0) {
					int q = st.getQuarter() + 12;
					cacheNum = "" + st.getYear() + q;
				} else {
					cacheNum = st.getYear() + ((st.getMonth() < 10) ? "0" : "") + st.getMonth();
				}
			}
		}

		// liulj 加入MemCached缓存设置

		cacheName = "nbyhpc_loadCompanyByIndustry_jw" + st.getRemark() + area.getAreaCode() + cacheNum;
		
		//huangjl 加入二级区域为空的链接
		String noAreaSql=" union select 0 from dual ";
		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存:" + cacheName);
		} else {

			String sql = "select fa.area_code, sum(inhere) inhere, sum(num) num,sum(finish) finish ,sum(zero) zero  " + " from (select area_code from " + fk_area + " where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ") "+noAreaSql+") fa " + " left join (select di." + areaType + ",count(distinct(di.id)) inhere,0 num,0 zero,0 finish, 'i' type  " + " from " + da_item + " di where " + (da_item.equals("da_item_his") ? "  di.backup_date=" + backup_date + "  and " : "") + " di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 " + createParam + " group by di." + areaType + " union  " + " select di." + areaType + ",0 inhere,count(distinct(di.id)) num,0 zero ,0 finish,'n' type  " + " from " + da_item
					+ " di left join (select par_da_ite_id from da_item_season_report where is_deleted=0 " + sqlParam + ") disr  " + " on disr.par_da_ite_id = di.id where " + (da_item.equals("da_item_his") ? "  di.backup_date=" + backup_date + "  and " : "") + " di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 " + createParam + " " + " and disr.par_da_ite_id is not null group by di." + areaType + " union  " + " select di." + areaType + ",0 inhere,0 num,count(distinct(di.id)) zero ,0 finish,'z'type  " + " from  " + da_item + " di left join (select par_da_ite_id,ordinary_danger_finding from da_item_season_report  " + " where is_deleted=0" + sqlParam + ") disr on disr.par_da_ite_id = di.id left join (select par_da_ite_id from da_item_danger  " + " where is_deleted=0" + sqlParam + ") did on did.par_da_ite_id=di.id where " + (da_item.equals("da_item_his") ? "  di.backup_date=" + backup_date + "  and " : "") + " di.type = " + type
					+ " and di.iscompleted='0' and di.is_deleted=0  " + createParam + " " + " and disr.par_da_ite_id is not null and disr.ordinary_danger_finding=0 and did.par_da_ite_id is null  " + " group by di." + areaType + " union  " + " select di." + areaType + ",0 inhere,0 num,0 zero, count(distinct(di.id)) finish,'f'type  " + " from " + da_item + " di where " + (da_item.equals("da_item_his") ? "  di.backup_date=" + backup_date + "  and " : "") + "di.type = " + type + " and di.iscompleted='1' and di.is_deleted=0  " + createParam + " " + " group by di." + areaType + ") value on value." + areaType + " = fa.area_code group by fa.area_code";
//			System.out.println("排查进度sql: " + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);
			// ResultSet res=null;
			// try {
			// res =
			// cFactory.createConnection().prepareStatement(sql).executeQuery();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setInhere(res.getInt(2));
					statistic.setNumber(res.getInt(3));
					statistic.setAnum(res.getInt(4));
					statistic.setBnum(res.getInt(5));
					statistics.add(statistic);
				}
				res.getStatement().close();
				res.close();

				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
				ResultSet rs = null;

				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各部门月报" + st.getRemark() + area.getAreaCode() + cacheNum + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各部门月报" + st.getRemark() + area.getAreaCode() + cacheNum + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.getStatement().close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (backup_date == 0) { // 动态的 设置过期时间
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.set(cacheName, statistics);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statistics;
	}

	/**
	 * 项目一般隐患、重大隐患统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadItemTroubleByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		Integer type = st.getType();
		String sqlParam = "";
		String yearMonth = "";

		int backup_date = 0; // 历史表查询的日期
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String fk_area = "fk_area";
		String da_item = "da_item";

		if (st != null) {

			if (st.getMonth() != 0) { // 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_item = "da_item_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {
				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致 ,则取历史表
															// 2013_11开始备份
					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																			// 2013.11当年取动态
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																					// 2013(包括)之后取那年的12月份
																					// yyyy-12
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
							backup_date = 201311;
						} else if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}
					}
				}
			}

			MonthQueryHelper helper = new MonthQueryHelper(st);
			sqlParam = " and create_time between to_date('" + helper.getBeginDate(null) + "','yyyy-MM-dd') and to_date('" + helper.getEndDate(null) + "','yyyy-MM-dd')";
		}
		if (st.getQuarter() > 0) {
			yearMonth = "and type ='" + st.getYear() + st.getQuarter() + "'";
		}
		
		//huangjl 加入二级区域为空的链接
		String noAreaSql=" union select 0 from dual ";
		
		String sql = "select fa.area_code,sum(odf) odf,sum(odfg) odfg,sum(ytdCount) ytdCount,sum(ytdgCount) ytdgCount  " + " from (select area_code from " + fk_area + " where " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " is_deleted=0 and father_id = (select id from " + fk_area + " where  " + (fk_area.equals("fk_area_his") ? "  backup_date=" + backup_date + "  and " : "") + " area_code= " + area.getAreaCode() + ") "+noAreaSql+") fa  " + " left join (select di." + areaType + ",sum(ytqb.ordinary_danger_finding) odf,sum(ytqb.ordinary_danger_not_govern) odfg,  " + " 0 ytdCount ,0 ytdgCount,'yiban' type from " + da_item + " di left join (select * from da_item_season_report where is_deleted=0 " + yearMonth + sqlParam + ") ytqb on  " + " ytqb.par_da_ite_id = di.id where " + (da_item.equals("da_item_his") ? "  di.backup_date=" + backup_date + "  and " : "") + " di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 and ytqb.par_da_ite_id is not null  "
				+ " group by di." + areaType + " union   " + " select di." + areaType + ",0 odf,0 odfg,count(ytd.id) ytdCount,count(ytdg.par_da_it_id) ytdgCount ,'zhongda' type  " + " from " + da_item + " di left join (select * from da_item_danger where is_deleted=0 " + sqlParam + ") ytd on ytd.par_da_ite_id=di.id  " + " left join (select par_da_it_id from da_item_danger_gover where is_deleted=0) ytdg on ytdg.par_da_it_id=ytd.id where  " + (da_item.equals("da_item_his") ? "  di.backup_date=" + backup_date + "  and " : "") + " di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0  " + " group by di." + areaType + ") list on list." + areaType + " = fa.area_code group by fa.area_code";

//		System.out.println("隐患qsql: " + sql);

		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistic.setAnum(res.getInt(4));
				statistic.setBnum(res.getInt(5));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 项目排查进度统计列表
	 * 
	 * @param statistic
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaItem> loadItemByIndustryList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = null;

		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		// System.out.println("当年:" + year1);

		String da_item = "da_item";
		String fk_area = "fk_area";
		int month = cal.get(Calendar.MONTH) + 1;
		int backup_date = 0; // 历史表查询的日期
		if (statistic != null) {

			if (statistic.getMonth() != 0) { // 月报

				// int month = cal.get(Calendar.MONTH);
				// da_item = "da_item_his";
				// fk_area = "fk_area_his";
				// if (statistic.getYear() < 2013 || (statistic.getYear() ==
				// 2013 && statistic.getMonth() <= 11 && statistic.getMonth() >=
				// 1)) { // 往月
				//
				// backup_date = 201311;
				// } else {
				// backup_date = Integer.parseInt(statistic.getYear() + "" +
				// statistic.getMonth());
				// }
				// 月报

				if (statistic.getYear() != year1) {

					if (statistic.getYear() > year1) {

						// System.out.println("动态表");

					} else if (statistic.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						if (statistic.getMonth() <= 9) {
							backup_date = Integer.parseInt(statistic.getYear() + "0" + statistic.getMonth());
						} else {
							backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
						}

					} else if (statistic.getYear() == 2013) {

						if (statistic.getMonth() > 11) {
							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							da_item = "da_item_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						da_item = "da_item_his";
						backup_date = 201311;
					}

				} else if (statistic.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";
					da_item = "da_item_his";

					if (statistic.getMonth() <= 9) {
						backup_date = Integer.parseInt(statistic.getYear() + "0" + statistic.getMonth());
					} else {
						backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != statistic.getQuarter()) { // 当前时间与查询时不一致
																	// ,则取历史表
																	// 2013_11开始备份

					if (statistic.getQuarter() == 0) { // 全年

						if (statistic.getYear() != year1 && statistic.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							da_item = "da_item_his";
							fk_area = "fk_area_his";
							backup_date = 201311;
						} else if (statistic.getYear() != year1 && statistic.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							da_item = "da_item_his";
							fk_area = "fk_area_his";
							backup_date = Integer.parseInt(statistic.getYear() + "12");
						}

					} else { // 季度

						da_item = "da_item_his";
						fk_area = "fk_area_his";
						if (statistic.getYear() < 2013 || (statistic.getYear() == 2013 && statistic.getQuarter() <= 3 && statistic.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (statistic.getQuarter() == 4) {
							backup_date = Integer.parseInt(statistic.getYear() + "12");

						} else if (statistic.getQuarter() == 3) {
							backup_date = Integer.parseInt(statistic.getYear() + "09");

						} else if (statistic.getQuarter() == 2) {
							backup_date = Integer.parseInt(statistic.getYear() + "06");

						} else if (statistic.getQuarter() == 1) {
							backup_date = Integer.parseInt(statistic.getYear() + "03");

						}
					}
				}
			}
		}

		if (da_item.equals("da_item_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaItemHis.class, "di");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.backup_date=" + backup_date + "  "));
		} else {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaItem.class, "di");
		}

		String sqlParam = "";
		String createParam = "";
		// if (statistic != null) {
		// int nextYear = statistic.getYear() + 1;
		// if (statistic.getQuarter() == 1) {
		// sqlParam = " and create_time between to_date('" + statistic.getYear()
		// + "-01-01','yyyy-MM-dd') and to_date('" + statistic.getYear() +
		// "-04-01','yyyy-MM-dd')";
		// createParam = " and this_.create_time<to_date('" +
		// statistic.getYear() + "-04-01','yyyy-MM-dd') ";
		// } else if (statistic.getQuarter() == 2) {
		// sqlParam = " and create_time between to_date('" + statistic.getYear()
		// + "-04-01','yyyy-MM-dd') and to_date('" + statistic.getYear() +
		// "-07-01','yyyy-MM-dd')";
		// createParam = " and this_.create_time<to_date('" +
		// statistic.getYear() + "-07-01','yyyy-MM-dd') ";
		// } else if (statistic.getQuarter() == 3) {
		// sqlParam = " and create_time between to_date('" + statistic.getYear()
		// + "-07-01','yyyy-MM-dd') and to_date('" + statistic.getYear() +
		// "-10-01','yyyy-MM-dd')";
		// createParam = " and this_.create_time<to_date('" +
		// statistic.getYear() + "-10-01','yyyy-MM-dd') ";
		// } else if (statistic.getQuarter() == 4) {
		// sqlParam = " and create_time between to_date('" + statistic.getYear()
		// + "-10-01','yyyy-MM-dd') and to_date('" + nextYear +
		// "-01-01','yyyy-MM-dd')";
		// createParam = " and this_.create_time<to_date('" + nextYear +
		// "-01-01','yyyy-MM-dd') ";
		// } else {
		// sqlParam = " and create_time between to_date('" + statistic.getYear()
		// + "-01-01','yyyy-MM-dd') and to_date('" + nextYear +
		// "-01-01','yyyy-MM-dd')";
		// createParam = " and this_.create_time<to_date('" + nextYear +
		// "-01-01','yyyy-MM-dd') ";
		// }
		// }

		if (statistic != null) {
			MonthQueryHelper helper = new MonthQueryHelper(statistic.getYear(), statistic.getQuarter(), statistic.getMonth());
			sqlParam = " and create_time between to_date('" + DateUtils.date2Str(helper.getBeginDate(), null) + "','yyyy-MM-dd') " + "and to_date('" + DateUtils.date2Str(helper.getEndDate(), null) + "','yyyy-MM-dd')";
			createParam = " and this_.create_time<to_date('" + DateUtils.date2Str(helper.getEndDate(), null) + "','yyyy-MM-dd') ";
			// log.info("sqlParam: " + sqlParam);
		}

		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea", area.getAreaCode()));
			} else {
//				if ("jianwei".equals(statistic.getRemark())) {
//					//禅道#17710 住建报表修改
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea", Nbyhpc.AREA_CODE));
//					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from " + fk_area + " a where " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() +" )"));
//				} else {
					String noAreaSql="  union select 0 from dual ";
					detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea", Nbyhpc.AREA_CODE));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from " + fk_area + " a where " + (fk_area.equals("fk_area_his") ? "  a.backup_date=" + backup_date + "  and " : "") + " a.father_id=" + area.getId() + " "+noAreaSql+" )"));
//				}
			}
		}else if(area != null && area.getAreaCode() == 0L){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea",0L));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea", Nbyhpc.AREA_CODE));
		}
		if (statistic != null) {
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
				if ("0".equals(statistic.getIsGorver().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_ite_id from da_item_season_report where is_deleted=0" + sqlParam + ")"));
				}
				if ("2".equals(statistic.getIsGorver().trim())) {
					// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted",
					// "1"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.iscompleted=1 " + createParam + ""));
				} else {
					// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted",
					// "0"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.iscompleted=0 " + createParam + ""));
				}
			} else {
				// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted",
				// "0"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.iscompleted=0 " + createParam + ""));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", statistic.getIndustryId()));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("di.id"));

		// itemHisPersistenceIface

		return itemPersistenceIface.loadItems(detachedCriteriaProxy, pagination);
	}

	// /**
	// * 项目排查进度统计列表
	// * @param statistic
	// * @param pagination
	// * @param area
	// * @return
	// * @throws ApplicationAccessException
	// */
	// public List<Statistic> loadItemByIndustryList(Statistic statistic,
	// Pagination pagination,FkArea area)throws ApplicationAccessException {
	// DetachedCriteriaProxy detachedCriteriaProxy =
	// DetachedCriteriaProxy.forClass(DaItem.class, "di");
	// String sqlParam = "";
	// String createParam = "";
	// if(statistic != null){
	// int nextYear = statistic.getYear()+1;
	// if(statistic.getQuarter() == 1){
	// sqlParam =
	// " and create_time between to_date('"+statistic.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-04-01','yyyy-MM-dd')";
	// createParam =
	// " and this_.create_time<to_date('"+statistic.getYear()+"-04-01','yyyy-MM-dd') ";
	// }else if(statistic.getQuarter() == 2){
	// sqlParam =
	// " and create_time between to_date('"+statistic.getYear()+"-04-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-07-01','yyyy-MM-dd')";
	// createParam =
	// " and this_.create_time<to_date('"+statistic.getYear()+"-07-01','yyyy-MM-dd') ";
	// }else if(statistic.getQuarter() == 3){
	// sqlParam =
	// " and create_time between to_date('"+statistic.getYear()+"-07-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-10-01','yyyy-MM-dd')";
	// createParam =
	// " and this_.create_time<to_date('"+statistic.getYear()+"-10-01','yyyy-MM-dd') ";
	// }else if(statistic.getQuarter() == 4){
	// sqlParam =
	// " and create_time between to_date('"+statistic.getYear()+"-10-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// createParam =
	// " and this_.create_time<to_date('"+nextYear+"-01-01','yyyy-MM-dd') ";
	// }else{
	// sqlParam =
	// " and create_time between to_date('"+statistic.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// createParam =
	// " and this_.create_time<to_date('"+nextYear+"-01-01','yyyy-MM-dd') ";
	// }
	// }
	// if (area != null && area.getAreaCode() != 0L) {
	// area = loadArea(area.getAreaCode());
	// int areaRate = area.getGradePath().split("/").length - 1;
	// if (areaRate == 4) {
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea",
	// area.getAreaCode()));
	// } else if (areaRate == 5) {
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea",
	// area.getAreaCode()));
	// }else{
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",
	// Nbyhpc.AREA_CODE));
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id="+area.getId()+")"));
	// }
	// }
	// if (statistic != null) {
	// if(statistic.getIsGorver() != null &&
	// !"".equals(statistic.getIsGorver().trim())){
	// if("0".equals(statistic.getIsGorver().trim())){
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_ite_id from da_item_season_report where is_deleted=0"+sqlParam+")"));
	// }
	// if("2".equals(statistic.getIsGorver().trim())){
	// //detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted", "1"));
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.iscompleted=1 "+createParam+""));
	// }else{
	// //detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted", "0"));
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.iscompleted=0 "+createParam+""));
	// }
	// }else{
	// //detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted", "0"));
	// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.iscompleted=0 "+createParam+""));
	// }
	// }
	// detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type",
	// statistic.getIndustryId()));
	// detachedCriteriaProxy.addOrder(OrderProxy.desc("di.id"));
	//
	// List<DaItem> items =
	// itemPersistenceIface.loadItems(detachedCriteriaProxy, pagination);
	//
	// return decorationItems(items, statistic);
	// }
	//
	// /**
	// * 封装查询出的企业隐患信息
	// * @author lisl
	// * @param companies
	// * @param company
	// * @return
	// * @throws ApplicationAccessException
	// */
	// public List<Statistic> decorationItems(List<DaItem> items,Statistic
	// statistic)throws ApplicationAccessException {
	// String itemIdes = "";
	//
	// for(DaItem item : items){
	// itemIdes = itemIdes + item.getId().toString() + ",";
	// }
	//
	// List<Statistic> statisticDetails = new ArrayList<Statistic>();
	// Statistic statistic_0 = null;
	// if(!itemIdes.equals("")){
	// String sqlParam = "";
	// if(statistic.getMonth() != 0){
	// int nextMonth = statistic.getMonth()+1;
	// sqlParam =
	// " and t.create_time between to_date('"+statistic.getYear()+"-"+statistic.getMonth()+"-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-"+nextMonth+"-01','yyyy-MM-dd')";
	// }else{
	// int nextYear = statistic.getYear()+1;
	// if(statistic.getQuarter() == 1){
	// sqlParam =
	// " and t.create_time between to_date('"+statistic.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-04-01','yyyy-MM-dd')";
	// }else if(statistic.getQuarter() == 2){
	// sqlParam =
	// " and t.create_time between to_date('"+statistic.getYear()+"-04-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-07-01','yyyy-MM-dd')";
	// }else if(statistic.getQuarter() == 3){
	// sqlParam =
	// " and t.create_time between to_date('"+statistic.getYear()+"-07-01','yyyy-MM-dd') and to_date('"+statistic.getYear()+"-10-01','yyyy-MM-dd')";
	// }else if(statistic.getQuarter() == 4){
	// sqlParam =
	// " and t.create_time between to_date('"+statistic.getYear()+"-10-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// }else{
	// sqlParam =
	// " and t.create_time between to_date('"+statistic.getYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')";
	// }
	// }
	//
	// String sql =
	// "select c.id,c.itemname,sum(dnd.ordinary_danger_finding),count(distinct dd.id)"
	// +" from "
	// //--连接工程表（统计工程数目）
	// +" (select t.id as id,t.itemname as itemname from da_item t where t.is_deleted=0 and t.id in ("+itemIdes.substring(0,
	// itemIdes.length()-1)+")) c"
	// //--连接一般隐患表（统计一般隐患数目）
	// +" left join (select t.id as id,t.par_da_ite_id as par_da_ite_id,t.ordinary_danger_finding as ordinary_danger_finding from da_item_season_report t where t.is_deleted=0"+sqlParam+") dnd on c.id=dnd.par_da_ite_id"
	// //--连接重大隐患表（统计重大隐患数目）
	// +" left join (select t.id as id,t.par_da_ite_id as par_da_ite_id from da_item_danger t where t.is_deleted=0"+sqlParam+") dd on c.id=dd.par_da_ite_id"
	// //--连接工程季度上报表 （查询工程是否上报）
	// //
	// +" left join (select t.id as id,t.company_id as company_id from da_company_quarter_report t where t.year='"+statistic.getYear()+"' and t.quarter='"+statistic.getQuarter()+"' ) cqr on cqr.company_id=c.id"
	// //--连接一般隐患表（统计一般隐患数目）(监管部门检查发现隐患数)
	// //+" left join (select t.id as id,t.par_da_ite_id as par_da_ite_id from da_item_season_report t left join da_company_pass dcp on dcp.par_da_com_id=t.par_da_com_id where t.is_deleted=0 and t.user_id !=0 and t.user_id is not null and t.user_id!=dcp.com_user_id"+sqlParam+") dnd_1 on c.id=dnd_1.par_da_com_id"
	// //--连接重大隐患表（统计重大隐患数目）(监管部门检查发现隐患数)
	// //
	// +" left join (select t.id as id,t.par_da_ite_id as par_da_ite_id from da_item_danger t left join da_company_pass dcp on dcp.par_da_com_id=t.par_da_com_id where t.is_deleted=0 and t.user_id !=0 and t.user_id is not null and t.user_id!=dcp.com_user_id"+sqlParam+") dd_1 on c.id=dd_1.par_da_com_id"
	// //--按工程分组
	// +" group by c.id,c.itemname";
	// System.out.println("-----shun--sql---shun="+sql);
	// ResultSet res = companyPersistenceIface.findBySql(sql);
	//
	// try {
	// while (res.next()) {
	// statistic_0 = new Statistic();
	// statistic_0.setCompanyId(res.getLong(1));
	// statistic_0.setCompanyName(res.getString(2));
	// statistic_0.setAnum(res.getInt(3));//标记一般事故隐患数
	// statistic_0.setBnum(res.getInt(4));//标记重大事故隐患数
	// //statistic_0.setCnum(res.getInt(5));//标记季报统计报表报送情况的状态
	// //statistic_0.setDnum(res.getInt(6));//标记监管部门检查发现隐患数
	//
	// statisticDetails.add(statistic_0);
	// }
	// } catch (SQLException e) {
	// //e.printStackTrace();
	// }
	// }
	// return statisticDetails;
	// }

	/**
	 * 项目重大隐患统计列表
	 * 
	 * @param statistic
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaItemDanger> loadItemTroubleByIndustryList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException {
		String sqlParam = "";
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);
		// System.out.println("当年:" + year1);
		DetachedCriteriaProxy detachedCriteriaProxy = null;
		String da_item = "da_item";
		String fk_area = "fk_area";
		int backup_date = 0; // 历史表查询的日期
		if (statistic != null) {

			if (statistic.getMonth() != 0) { // 月报

				int month = cal.get(Calendar.MONTH);

				if (statistic.getYear() < 2013 || (statistic.getYear() == 2013 && statistic.getMonth() <= 11 && statistic.getMonth() >= 1)) { // 往月

					backup_date = 201311;
				} else {
					backup_date = Integer.parseInt(statistic.getYear() + "" + statistic.getMonth());
				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != statistic.getQuarter()) { // 当前时间与查询时不一致
																	// ,则取历史表
																	// 2013_11开始备份

					if (statistic.getQuarter() == 0) { // 全年

						if (statistic.getYear() != year1 && statistic.getYear() < 2013) { // 往年:2013年之前都取
							// 2013.11当年取动态
							da_item = "da_item_his";
							fk_area = "fk_area_his";
							backup_date = 201311;
						} else if (statistic.getYear() != year1 && statistic.getYear() >= 2013) { // 往年
							// 2013(包括)之后取那年的12月份
							// yyyy-12
							da_item = "da_item_his";
							fk_area = "fk_area_his";
							backup_date = Integer.parseInt(statistic.getYear() + "12");
						}

					} else { // 季度

						da_item = "da_item_his";
						fk_area = "fk_area_his";
						if (statistic.getYear() < 2013 || (statistic.getYear() == 2013 && statistic.getQuarter() <= 3 && statistic.getQuarter() >= 1)) { // 2013年第三季度之前
							// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (statistic.getQuarter() == 4) {
							backup_date = Integer.parseInt(statistic.getYear() + "12");

						} else if (statistic.getQuarter() == 3) {
							backup_date = Integer.parseInt(statistic.getYear() + "09");

						} else if (statistic.getQuarter() == 2) {
							backup_date = Integer.parseInt(statistic.getYear() + "06");

						} else if (statistic.getQuarter() == 1) {
							backup_date = Integer.parseInt(statistic.getYear() + "03");

						}
					}
				}
			}

			int nextYear = statistic.getYear() + 1;
			if (statistic.getQuarter() == 1) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-04-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 2) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-04-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-07-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 3) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-07-01','yyyy-MM-dd') and to_date('" + statistic.getYear() + "-10-01','yyyy-MM-dd')";
			} else if (statistic.getQuarter() == 4) {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			}
		}

		if (da_item.equals("da_item_his")) {

			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaItemDanger1.class, "did");
			detachedCriteriaProxy.createCriteria("daItemHis", "di");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("di1_.backup_date=" + backup_date + "   "));

		} else {

			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaItemDanger.class, "did");
			detachedCriteriaProxy.createCriteria("daItem", "di");
		}

		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea", Nbyhpc.AREA_CODE));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
			}
		}
		if (statistic != null) {
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
				if ("0".equals(statistic.getIsGorver().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_it_id from da_item_danger_gover where is_deleted=0)"));
				}
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("di.iscompleted", "0"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", statistic.getIndustryId()));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("di.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("did.id"));
		return itemDangerPersistenceIface.loadItemDangers(detachedCriteriaProxy, pagination);
	}

	/**
	 * 一般隐患类型统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadNomalDangerTypeByIndustry(FkArea area, String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sql = " select count(distinct(dnd.id)) inhere,count(distinct(gorver.id)) num,map.area_code,map.dip_id, " + "  map.name, (select count(1) from da_industry_parameter where is_deleted = 0 and grade_path like " + " (select grade_path||'%' from da_industry_parameter where is_deleted = 0 and id=map.dip_id)) sonCount, " + " (select 1 from da_industry_parameter where is_deleted = 0 and rownum=1 and par_da_ind_id in (map.dip_id)) sonNumber1, " + " (select 1 from da_industry_parameter where is_deleted = 0 and rownum=1 and par_da_ind_id in " + " (select id from da_industry_parameter where par_da_ind_id=map.dip_id)) sonNumber2 " + " from (select fa.area_code,fa.area_name,dip.id dip_id,dip.grade_path,dip.name from fk_area fa " + " full join (select id,name,grade_path from da_industry_parameter where " + " is_deleted=0 and grade_path like (select grade_path||'%' from " + " da_industry_parameter where depiction = '" + remark + "' and type=8)) dip on 1!=2 "
				+ " where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map " + " left join (select dc.id,dc.second_area,dc.third_area from " + " da_company dc left join da_company_pass dcp  on dcp.par_da_com_id = dc.id " + " where dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1) da on " + " da." + areaType + " = map.area_code " + " left join da_normal_danger dnd on dnd.type = map.dip_id and dnd.par_da_com_id = da.id " + " left join (select id from da_normal_danger where is_deleted = 0  and is_danger=1 and " + " is_repaired=1) gorver on gorver.id = dnd.id " + " where dnd.is_danger=1 and dnd.is_deleted = 0 group by map.area_code,map.dip_id,map.name,map.grade_path " + " order by map.area_code,map.grade_path";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setAreaCode(res.getLong(3));
				statistic.setIndustryId(res.getInt(4));
				statistic.setEnumName(res.getString(5));
				statistic.setSonCount(res.getInt(6));
				statistic.setSonNumber1(res.getInt(7));
				statistic.setSonNumber2(res.getInt(8));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return switchList(statistics, area.getAreaCode(), Nbyhpc.QUARTER_REPORT_TYPE);
	}

	public List<DaNomalDanger> loadNomalDangerTypeByIndustryList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "da");
		DaIndustryParameter industry = new DaIndustryParameter();
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("da2_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
			}
		}
		if (statistic != null) {
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
				if ("0".equals(statistic.getIsGorver().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
				}
			}
			if (statistic.getIndustryId() != 0) {
				industry = tradeTypePersistenceIface.loadTradeType(new DaIndustryParameter((long) (statistic.getIndustryId())));
				Integer len = industry.getGradePath().split("/").length - 1;
				if (len == 2) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.industry.id", (long) statistic.getIndustryId()));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.type in (select id from da_industry_parameter where par_da_ind_id=" + statistic.getIndustryId() + ")"));
				}
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.id"));
		return nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
	}

	/**
	 * 其他季报统计
	 * 
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadStatisticsOfSeasonReportOther(Statistic st, int current_quarter) throws ApplicationAccessException {
		String areaType = "";
		String sqlParam = "";
		String cacheName = "";
		String cacheNum = "0";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		String da_industry_parameter = "da_industry_parameter";
		String BACKUP_DATE = ""; // 历史表查询的日期

		if (st != null) {

			// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
			if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致 ,则取历史表
														// 2013_11开始备份

				if (st.getQuarter() == 0) { // 全年

					if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																		// 2013.11当年取动态
						da_industry_parameter = "da_industry_parameter_his";
						BACKUP_DATE = "201311";
					} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																				// 2013(包括)之后取那年的12月份
																				// yyyy-12
						da_industry_parameter = "da_industry_parameter_his";
						BACKUP_DATE = st.getYear() + "12";
					}

				} else { // 季度

					da_industry_parameter = "da_industry_parameter_his";
					if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
																															// 未做备份,则取最早备份日期2013_10
						BACKUP_DATE = "201311";
					} else if (st.getQuarter() == 4) {
						BACKUP_DATE = st.getYear() + "12";

					} else if (st.getQuarter() == 3) {
						BACKUP_DATE = st.getYear() + "09";

					} else if (st.getQuarter() == 2) {
						BACKUP_DATE = st.getYear() + "06";

					} else if (st.getQuarter() == 1) {
						BACKUP_DATE = st.getYear() + "03";

					}
				}
			}

			MonthQueryHelper helper = new MonthQueryHelper(st);
			sqlParam = " and create_time between to_date('" + helper.getBeginDate(null) + "','yyyy-MM-dd') and to_date('" + helper.getEndDate(null) + "','yyyy-MM-dd')";
			if (st.getAreaCode() != 0L)
				areaType = "and o.second_area=" + st.getAreaCode();

			if (st.getYear() == year && current_quarter == st.getQuarter()) {
				cacheNum = "0";
			} else {
				int q = st.getQuarter() + 12;
				cacheNum = "" + st.getYear() + q;
			}
		}

		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();

		// liulj 加入MemCached缓存设置

		MemCached cache = MemCached.getInstance();
		cacheName = "nbyhpc_loadQuarterByIndustry_" + st.getAreaCode() + st.getRemark() + cacheNum;
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			statistics = (List<Statistic>) cache.get(cacheName);
//			System.out.println("读取缓存" + cacheName);
		} else {

			String sql = "select i.name,sum(o.company_num),sum(o.troub_num),sum(o.troub_clean_num), " + " sum(o.big_troub_num),sum(o.big_troub_clean_num),sum(o.target),sum(o.goods), " + " sum(o.resources),sum(o.finish_date),sum(o.safety_method),sum(o.govern_money) from " + " (select id,name from " + da_industry_parameter + " where " + (da_industry_parameter.equals("da_industry_parameter_his") ? "  backup_date=" + BACKUP_DATE + " and " : "") + " type=7 and depiction='" + st.getRemark() + "' and is_deleted=0) i  " + " left join  da_season_report_other o on o.par_da_ind_id=i.id and o.is_deleted=0 " + areaType + " " + sqlParam + " group by i.id,i.name order by i.id";
//			System.out.println("sql: " + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);
			ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
			ResultSet rs = null;
			// System.out.println("其他季报统计:" + sql);

			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setEnumName(res.getString(1));
					statistic.setCompanyNum(res.getInt(2));
					statistic.setTroubNum(res.getInt(3));
					statistic.setTroubCleanNum(res.getInt(4));
					statistic.setBigTroubNum(res.getInt(5));
					statistic.setBigTroubCleanNum(res.getInt(6));
					statistic.setTarget(res.getInt(7));
					statistic.setGoods(res.getInt(8));
					statistic.setResource(res.getInt(9));
					statistic.setFinishDate(res.getInt(10));
					statistic.setSafetyMethod(res.getInt(11));
					statistic.setGovernMoney(res.getDouble(12));
					statistics.add(statistic);
				}

				if (BACKUP_DATE.equals("")) { // 动态的 2000*60*60=2小时过期
					cache.set(cacheName, statistics, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.set(cacheName, statistics);
				}

				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='各部门季报" + st.getRemark() + cacheNum + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','各部门季报" + st.getRemark() + cacheNum + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.getStatement().close();
					rs.close();
					res.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return statistics;
	}

	/**
	 * 分级评估统计
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadCompanyByCompanyLevel(FkArea area) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sql = "select count(distinct(da.id)) inhere,map.area_code,map.enum_code,map.enum_name  " + " from (select fa.area_code,fe.enum_code,fe.enum_name,fe.grade_path from fk_area fa   " + " full join (select enum_name,enum_code,grade_path from fk_enum where is_deleted=0  " + " and  father_id = (select id from fk_enum where enum_code='company_level'))  " + " fe on 1!=2 where fa.is_deleted=0 and fa.father_id=(select id from fk_area  " + " where area_code= " + area.getAreaCode() + ")) map left join (select dc.id,dc.second_area," + " dc.third_area,company_level from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 and  " + " dcp.is_deleted=0 and dcp.is_affirm=1) da on da." + areaType + " = map.area_code and da.company_level = map.enum_code " + " group by map.area_code,map.enum_code,map.enum_name,map.grade_path order by  " + " map.area_code,map.grade_path";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setAreaCode(res.getLong(2));
				statistic.setEnumCode(res.getString(3));
				statistic.setEnumName(res.getString(4));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 分级评估统计列表
	 * 
	 * @param statistic
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompanyByCompanyLevelList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "da");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
			}
		}
		if (statistic != null) {
			if (statistic.getEnumCode() != null && !"".equals(statistic.getEnumCode().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.companyLevel", statistic.getEnumCode()));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	/**
	 * xml一般隐患统计图sql
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadNormalDangerByIndustryAndAreaSql(FkArea area, String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		String sqlType = "";
		if (!remark.equals("anwei")) {
			sqlType = " and grade_path like (select grade_path||'%' from da_industry_parameter where depiction = '" + remark + "' and type=1)";
		} else {
			sqlType = " and type=1";
		}
		String areaType = "second_area";
		if (area.getAreaCode() != Nbyhpc.AREA_CODE) {
			areaType = "third_area";
		}
		int nowYear = 2009;
		String sqlCondition = "";
		nowYear = cal.get(Calendar.YEAR);
		int nextYear = nowYear + 1;
		sqlCondition = " and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sql = " select map.area_code,count(distinct(dnd.id)) dnd,count(distinct(dnd_g.id)) dnd_g from " + " (select fa.area_code,dip.id dip_id from fk_area fa full join (select id,name, " + " grade_path from da_industry_parameter where is_deleted=0 " + sqlType + ") dip on 1!=2  " + " where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map  " + " left join da_company_industry_rel dcir on dcir.par_da_ind_id = map.dip_id  " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 and dcp.is_deleted=0  " + " and dcp.is_affirm=1) da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id  " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlCondition + ") dnd on dnd.par_da_com_id = da.id "
				+ " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 and " + " is_repaired=1 " + sqlCondition + ") dnd_g on dnd_g.id=dnd.id " + " group by map.area_code";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;

	}

	/**
	 * 创建一般隐患统计图xml
	 * 
	 * @param area
	 * @param remark
	 * @param filePath
	 * @throws ApplicationAccessException
	 */
	public void loadNormalDangerByIndustryAndArea(FkArea area, String remark, String param) throws ApplicationAccessException {
		if (area == null) {
			area = new FkArea();
			area.setAreaCode(Nbyhpc.AREA_CODE);
		}
		Calendar cal = Calendar.getInstance();
		String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_nbyhpc_" + remark + "_normal_danger.xml";
		// System.out.println("filePath路径: " + filePath);
		List<FkArea> areas = new ArrayList<FkArea>();
		List<Statistic> statistics = new ArrayList<Statistic>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0'?>");
		buffer.append("<graph useRoundEdges='0' decimals='0' baseFont='宋体' formatNumberScale='0'  showValues='0'  baseFontSize='14' hovercapbg='DEDEBE' hovercapborder='889E6D' ");
		buffer.append("rotateNames='0' yAxisMaxValue='110' numdivlines='10' divLineColor='CCCCCC' divLineAlpha='80' ");
		buffer.append("decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'");
		buffer.append("AlternateHGridColor='CCCCCC' xAxisName='' yAxisName=''  caption='宁波市安全生产隐患排查一般隐患整改率（%）统计图" + param + "'>");
		areas = loadAreas(area.getAreaCode());
		statistics = loadNormalDangerByIndustryAndAreaSql(area, remark);
		buffer.append("<categories font='宋体' fontSize='12' fontColor='000000'>");
		if (areas != null) {
			for (int i = 0; i < areas.size(); i++) {
				buffer.append("<category name='" + areas.get(i).getAreaName() + "'/>");
			}
		}
		buffer.append("</categories>");
		buffer.append("<dataset  color='97CFFD' showValues='0'>");
		int ag = 0;
		if (areas != null && statistics != null) {
			for (int i = 0; i < areas.size(); i++) {
				// buffer.append("<set color='97CFFD' label='"+
				// areas.get(i).getAreaName()+"'");
				for (int j = 0; j < statistics.size(); j++) {
					if (areas.get(i).getAreaCode() == statistics.get(j).getAreaCode()) {
						if (statistics.get(j).getInhere() == 0)
							buffer.append("<set value='0'/>");
						else {
							buffer.append("<set value='" + 100f * statistics.get(j).getNumber() / statistics.get(j).getInhere() + "' />");
							ag += 100f * statistics.get(j).getNumber() / statistics.get(j).getInhere();
						}

					}
				}
			}
		}
		buffer.append("</dataset>");
		buffer.append("<dataset  color='C9198D' showValues='0'>");
		if (areas != null && statistics != null) {
			for (int i = 0; i < areas.size(); i++) {
				for (int j = 0; j < statistics.size(); j++) {
					if (areas.get(i).getAreaCode() == statistics.get(j).getAreaCode())
						buffer.append("<set value='0' />");
				}
			}
		}
		buffer.append("</dataset>");
		buffer.append("<trendLines><line startValue='" + ag / areas.size() + "' color='FF0000' displayvalue='平均整改率" + ag / areas.size() + "%   ' valueOnRight='0' /></trendLines>");
		buffer.append("</graph>");
		writeToFile(filePath, buffer);
	}

	/**
	 * 
	 * xml重大隐患统计图sql
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadDangerByIndustryAndAreaSql(FkArea area, String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		// System.out.println("remark：" + remark);
		Statistic statistic = new Statistic();
		String sqlType = "";
		if (!remark.equals("anwei")) {
			sqlType = " and grade_path like (select grade_path||'%' from da_industry_parameter where depiction = '" + remark + "' and type=1)";
		} else {
			sqlType = " and type=1";
		}
		String areaType = "second_area";
		if (area.getAreaCode() != Nbyhpc.AREA_CODE) {
			areaType = "third_area";
		}
		int nowYear = 2009;
		String sqlCondition = "";
		Calendar cal = Calendar.getInstance();
		nowYear = cal.get(Calendar.YEAR);
		int nextYear = nowYear + 1;
		sqlCondition = " and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sql = "select map.area_code,count(distinct(dd.id)) dd ,count(distinct(ddg.par_da_dan_id)) ddg from " + " (select fa.area_code,dip.id dip_id from fk_area fa full join (select id,name, " + " grade_path from da_industry_parameter where is_deleted=0 " + sqlType + ") dip on 1!=2  " + " where fa.is_deleted=0 and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map  " + " left join da_company_industry_rel dcir on dcir.par_da_ind_id = map.dip_id  " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc  " + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 and dcp.is_deleted=0  " + " and dcp.is_affirm=1) da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id   " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlCondition + ") dd on dd.par_da_com_id = da.id " + " left join (select id,par_da_dan_id from da_danger_gorver where is_deleted=0 "
				+ sqlCondition + ") ddg on ddg.par_da_dan_id=dd.id  " + " group by map.area_code";

		// System.out.println("sql：" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 创建重大隐患统计图xml
	 * 
	 * @param area
	 * @param remark
	 * @param filePath
	 * @throws ApplicationAccessException
	 */
	public void loadDangerByIndustryAndArea(FkArea area, String remark, String param) throws ApplicationAccessException {
		if (area == null) {
			area = new FkArea();
			area.setAreaCode(Nbyhpc.AREA_CODE);
		}
//		System.out.println("生成xml");
		Calendar cal = Calendar.getInstance();
		String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_nbyhpc_" + remark + "_major_danger.xml";
		List<FkArea> areas = new ArrayList<FkArea>();
		List<Statistic> statistics = new ArrayList<Statistic>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0'?>");
		buffer.append("<graph baseFont='宋体' formatNumberScale='0'  showValues='0'  baseFontSize='14' hovercapbg='DEDEBE' hovercapborder='889E6D' ");
		buffer.append("rotateNames='0' yAxisMaxValue='110' numdivlines='10' divLineColor='CCCCCC' divLineAlpha='80' ");
		buffer.append("decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'");
		buffer.append("AlternateHGridColor='CCCCCC'  caption='宁波市安全生产隐患排查重大隐患整改率（%）统计图" + param + "'>");
		areas = loadAreas(area.getAreaCode());
		statistics = loadDangerByIndustryAndAreaSql(area, remark);
		buffer.append("<categories font='宋体' fontSize='12' fontColor='000000'>");
		if (areas != null) {
			for (int i = 0; i < areas.size(); i++) {
				buffer.append("<category name='" + areas.get(i).getAreaName() + "'/>");
			}
		}
		buffer.append("</categories>");
		buffer.append("<dataset  color='97CFFD' showValues='0'>");
		int ag = 0;
		if (areas != null && statistics != null) {
			for (int i = 0; i < areas.size(); i++) {
				// buffer.append("<set label='"+
				// areas.get(i).getAreaName()+"'");
				for (int j = 0; j < statistics.size(); j++) {
					if (areas.get(i).getAreaCode() == statistics.get(j).getAreaCode()) {
						if (statistics.get(j).getInhere() == 0)
							buffer.append("<set value='0'/>");
						else {
							buffer.append("<set value='" + 100f * statistics.get(j).getNumber() / statistics.get(j).getInhere() + "' />");
							ag += 100f * statistics.get(j).getNumber() / statistics.get(j).getInhere();
						}

					}
				}
			}
		}
		buffer.append("</dataset>");
		buffer.append("<dataset  color='C9198D' showValues='0'>");
		if (areas != null && statistics != null) {
			for (int i = 0; i < areas.size(); i++) {
				for (int j = 0; j < statistics.size(); j++) {
					if (areas.get(i).getAreaCode() == statistics.get(j).getAreaCode())
						buffer.append("<set value='0' />");
				}
			}
		}
		buffer.append("</dataset>");
		buffer.append("<trendLines><line startValue='" + ag / areas.size() + "' color='FF0000' displayvalue='平均整改率" + ag / areas.size() + "%   ' valueOnRight='0' /></trendLines>");
		buffer.append("</graph>");

		// System.out.println("filePath: " + filePath);
		writeToFile(filePath, buffer);
	}

	/**
	 * 创建一般隐患统计图xml(隐患分类统计图)
	 * 
	 * @param area
	 * @param remark
	 * @param filePath
	 * @throws ApplicationAccessException
	 */
	public void loadNormalDangerByIndustryAndArea1(String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		String hql = "from FkArea t  where  t.id=3020 or t.fatherId=3020 order by id ";
		List<FkArea> areas0 = areaPersistenceIface.loadAreasByHql(hql);
		for (FkArea a : areas0) {

			Calendar cal = Calendar.getInstance();
			String filePath0 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_" + a.getAreaCode() + "_" + remark + "_all_normal_danger0.xml";
			String filePath1 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_" + a.getAreaCode() + "_" + remark + "_all_normal_danger1.xml";
			String filePath2 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_" + a.getAreaCode() + "_" + remark + "_normal_danger.xml";

			StringBuffer buffer0 = new StringBuffer(); // 总的报表_柱状图
			StringBuffer buffer1 = new StringBuffer(); // 总的报表_饼图

			buffer0.append("<?xml version='1.0' ?>");
			buffer0.append("<chart caption='' plotSpacePercent='80' formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");
			buffer1.append("<?xml version='1.0' ?>");
			buffer1.append("<chart caption=''  showValues='0'  decimals='0'    formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");

			statistics = loadNormalDangerByIndustryAndAreaSql1(a, remark, 0);
			buffer0.append("<categories>");
			buffer0.append("<category label='人' />");
			buffer0.append("<category label='物' />");
			buffer0.append("<category label='管理' />");
			buffer0.append("</categories>");
			buffer0.append("<dataset color='AFD8F8' showValues='0'>");
			if (statistics.size() > 0) {
				for (int s = 0; s < statistics.size(); s++) {

					if (s == 0) {
						if (statistics.get(s).getType() == 1327) {
							buffer0.append("<set value='" + statistics.get(s).getNumber() + "'  color='AFD8F8'  />");
							buffer1.append("<set label='人' value='" + statistics.get(s).getNumber() + "' />");

						} else {
							buffer0.append("<set value='0'  color='AFD8F8'  />");
							buffer1.append("<set label='人' value='0' />");
						}
					}
					if (s == 1) {

						if (statistics.get(s).getType() == 1332) {
							buffer0.append("<set value='" + statistics.get(s).getNumber() + "'  color='F6BD0F'  />");
							buffer1.append("<set label='物' value='" + statistics.get(s).getNumber() + "' />");
						} else {
							buffer0.append("<set value='0'  color='F6BD0F'  />");
							buffer1.append("<set label='物' value='0' />");
						}

					}
					if (s == 2) {
						if (statistics.get(s).getType() == 1344) {
							buffer0.append("<set value='" + statistics.get(s).getNumber() + "'  color='8BBA00'  />");
							buffer1.append("<set label='管理' value='" + statistics.get(s).getNumber() + "' />");
						} else {
							buffer0.append("<set value='0'  color='8BBA00'  />");
							buffer1.append("<set label='管理' value='0' />");
						}
					}
				}
			}
			buffer0.append("</dataset>");
			buffer0.append("</chart>");
			writeToFile(filePath0, buffer0);

			buffer1.append("</chart>");
			writeToFile(filePath1, buffer1);

			// //柱状_其下报表
			StringBuffer buffer2 = new StringBuffer();
			StringBuffer buffer2_0 = new StringBuffer();
			StringBuffer buffer2_1 = new StringBuffer();
			StringBuffer buffer2_2 = new StringBuffer();
			StringBuffer buffer2_3 = new StringBuffer();
			buffer2.append("<?xml version='1.0' ?>");
			buffer2.append("<chart caption=''   showValues='0'  decimals='0'  formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");
			statistics = loadNormalDangerByIndustryAndAreaSql1(a, remark, 1);
			buffer2_0.append("<categories>");
			buffer2_1.append("<dataset seriesName='人' color='AFD8F8' showValues='0'>");
			buffer2_2.append("<dataset seriesName='物' color='F6BD0F' showValues='0'>");
			buffer2_3.append("<dataset seriesName='管理' color='8BBA00' showValues='0'>");
			String aname1 = "";
			String aname2 = "";
			if (statistics.size() > 0) {
				for (int s = 0; s < statistics.size(); s++) {
					aname1 = statistics.get(s).getName();
					buffer2_0.append("<category label='" + statistics.get(s).getName() + "' />");

					if (statistics.get(s).getType() == 1327) {
						buffer2_1.append("<set value='" + statistics.get(s).getNumber() + "' />");
						s++;
						if (s == statistics.size()) { // 末个
							buffer2_2.append("<set value='0' />");
							buffer2_3.append("<set value='0' />");
						} else {
							aname2 = statistics.get(s).getName();
							if (!aname1.equals(aname2)) {
								buffer2_2.append("<set value='0' />");
								buffer2_3.append("<set value='0' />");
								s--;
							} else {
								if (statistics.get(s).getType() == 1332) {
									buffer2_2.append("<set value='" + statistics.get(s).getNumber() + "' />");
									s++;
									if (s == statistics.size()) { // 末个
										buffer2_3.append("<set value='0' />");
									} else {
										aname2 = statistics.get(s).getName();
										if (!aname1.equals(aname2)) {
											buffer2_3.append("<set value='0' />");
											s--;
										} else {
											buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
										}
									}
								} else if (statistics.get(s).getType() == 1344) {
									buffer2_2.append("<set value='0' />");
									buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
								}
							}
						}
					} else if (statistics.get(s).getType() == 1332) {
						buffer2_1.append("<set value='0' />");
						buffer2_2.append("<set value='" + statistics.get(s).getNumber() + "' />");

						s++;
						if (s == statistics.size()) { // 末个
							buffer2_3.append("<set value='0' />");
						} else {
							aname2 = statistics.get(s).getName();
							if (!aname1.equals(aname2)) {
								buffer2_3.append("<set value='0' />");
								s--;
							} else {
								buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
							}
						}

					} else if (statistics.get(s).getType() == 1344) {
						buffer2_1.append("<set value='0' />");
						buffer2_2.append("<set value='0' />");
						buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
					}
				}
			}

			buffer2_0.append("</categories>");
			buffer2_1.append("</dataset>");
			buffer2_2.append("</dataset>");
			buffer2_3.append("</dataset>");
			buffer2.append(buffer2_0);
			buffer2.append(buffer2_1);
			buffer2.append(buffer2_2);
			buffer2.append(buffer2_3);
			buffer2.append("</chart>");
			writeToFile(filePath2, buffer2);
		}
	}

	/**
	 * 生成一般隐患柱状图和饼图XML
	 * 柱状图为 近三年的数据
	 * 饼状图为当年数据
	 */
	@Override
	public void loadNormalDangerByIndustryAndArea2(String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		String hql = "from FkArea t  where  t.areaCode=" + Nbyhpc.AREA_CODE + " or t.fatherId=(select id from FkArea where areaCode = " + Nbyhpc.AREA_CODE + ") order by id ";
		List<FkArea> areaList = areaPersistenceIface.loadAreasByHql(hql);
		for (FkArea a : areaList) {
			Calendar cal = Calendar.getInstance();
			int nowYear = cal.get(Calendar.YEAR);
			int preYear = nowYear - 1;
			int firstYear = nowYear - 2;
			String filePath0 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + nowYear + "_" + a.getAreaCode() + "_" + remark + "_all_normal_danger0.xml";
			String filePath1 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + nowYear + "_" + a.getAreaCode() + "_" + remark + "_all_normal_danger1.xml";
			String filePath2 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + nowYear + "_" + a.getAreaCode() + "_" + remark + "_normal_danger.xml";

			StringBuffer buffer0 = new StringBuffer(); // 总的报表_柱状图
			StringBuffer buffer1 = new StringBuffer(); // 总的报表_饼图

			buffer0.append("<?xml version='1.0' ?>");
			buffer0.append("<chart caption='' plotSpacePercent='50' formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");
			buffer1.append("<?xml version='1.0' ?>");
			buffer1.append("<chart caption=''  showValues='0'  decimals='0'    formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");

			statistics = loadNormalDangerByIndustryAndAreaSql2(a, remark, 0);
			buffer0.append("<categories>");
			buffer0.append("<category label='人' />");
			buffer0.append("<category label='物' />");
			buffer0.append("<category label='管理' />");
			buffer0.append("</categories>");
			StringBuffer dataset1 = new StringBuffer("<dataset color='1F82C8' seriesName='" + firstYear + "' showValues='0'>");
			StringBuffer dataset2 = new StringBuffer("<dataset color='EBB306' seriesName='" + preYear + "' showValues='0'>");
			StringBuffer dataset3 = new StringBuffer("<dataset color='47BA31' seriesName='" + nowYear + "' showValues='0'>");
			if (statistics.size() > 0) {
				int renNum = 0;
				int wuNum = 0;
				int guanLiNum = 0;
				for (int s = 0; s < statistics.size(); s++) {
					Statistic statistic = statistics.get(s);
					if (firstYear == statistic.getYear()) {
						dataset1.append("<set value='" + statistic.getNumber() + "'/>");
					} else if (preYear == statistic.getYear()) {
						dataset2.append("<set value='" + statistic.getNumber() + "'/>");
					} else if (nowYear == statistic.getYear()) {
						dataset3.append("<set value='" + statistic.getNumber() + "'/>");
						//饼状图值获取当年的值
						if (statistic.getType() == 1327) {
							renNum += statistic.getNumber();
						} else if (statistic.getType() == 1332) {
							wuNum += statistic.getNumber();
						} else if (statistic.getType() == 1344) {
							guanLiNum += statistic.getNumber();
						}
					}
				}
				buffer1.append("<set label='人' value='" + renNum + "' />");
				buffer1.append("<set label='物' value='" + wuNum + "' />");
				buffer1.append("<set label='管理' value='" + guanLiNum + "' />");
			}
			dataset1.append("</dataset>");
			dataset2.append("</dataset>");
			dataset3.append("</dataset>");
			buffer0.append(dataset1).append(dataset2).append(dataset3).append("</chart>");
			writeToFile(filePath0, buffer0);

			buffer1.append("</chart>");
			writeToFile(filePath1, buffer1);

			// //柱状_其下报表
			StringBuffer buffer2 = new StringBuffer();
			StringBuffer buffer2_0 = new StringBuffer();
			StringBuffer buffer2_1 = new StringBuffer();
			StringBuffer buffer2_2 = new StringBuffer();
			StringBuffer buffer2_3 = new StringBuffer();
			buffer2.append("<?xml version='1.0' ?>");
			buffer2.append("<chart caption=''   showValues='0'  decimals='0'  formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");
			statistics = loadNormalDangerByIndustryAndAreaSql1(a, remark, 1);
			buffer2_0.append("<categories>");
			buffer2_1.append("<dataset seriesName='人' color='AFD8F8' showValues='0'>");
			buffer2_2.append("<dataset seriesName='物' color='F6BD0F' showValues='0'>");
			buffer2_3.append("<dataset seriesName='管理' color='8BBA00' showValues='0'>");
			String aname1 = "";
			String aname2 = "";
			if (statistics.size() > 0) {
				for (int s = 0; s < statistics.size(); s++) {
					aname1 = statistics.get(s).getName();
					buffer2_0.append("<category label='" + statistics.get(s).getName() + "' />");

					if (statistics.get(s).getType() == 1327) {
						buffer2_1.append("<set value='" + statistics.get(s).getNumber() + "' />");
						s++;
						if (s == statistics.size()) { // 末个
							buffer2_2.append("<set value='0' />");
							buffer2_3.append("<set value='0' />");
						} else {
							aname2 = statistics.get(s).getName();
							if (!aname1.equals(aname2)) {
								buffer2_2.append("<set value='0' />");
								buffer2_3.append("<set value='0' />");
								s--;
							} else {
								if (statistics.get(s).getType() == 1332) {
									buffer2_2.append("<set value='" + statistics.get(s).getNumber() + "' />");
									s++;
									if (s == statistics.size()) { // 末个
										buffer2_3.append("<set value='0' />");
									} else {
										aname2 = statistics.get(s).getName();
										if (!aname1.equals(aname2)) {
											buffer2_3.append("<set value='0' />");
											s--;
										} else {
											buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
										}
									}
								} else if (statistics.get(s).getType() == 1344) {
									buffer2_2.append("<set value='0' />");
									buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
								}
							}
						}
					} else if (statistics.get(s).getType() == 1332) {
						buffer2_1.append("<set value='0' />");
						buffer2_2.append("<set value='" + statistics.get(s).getNumber() + "' />");

						s++;
						if (s == statistics.size()) { // 末个
							buffer2_3.append("<set value='0' />");
						} else {
							aname2 = statistics.get(s).getName();
							if (!aname1.equals(aname2)) {
								buffer2_3.append("<set value='0' />");
								s--;
							} else {
								buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
							}
						}

					} else if (statistics.get(s).getType() == 1344) {
						buffer2_1.append("<set value='0' />");
						buffer2_2.append("<set value='0' />");
						buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
					}
				}
			}

			buffer2_0.append("</categories>");
			buffer2_1.append("</dataset>");
			buffer2_2.append("</dataset>");
			buffer2_3.append("</dataset>");
			buffer2.append(buffer2_0);
			buffer2.append(buffer2_1);
			buffer2.append(buffer2_2);
			buffer2.append(buffer2_3);
			buffer2.append("</chart>");
			writeToFile(filePath2, buffer2);
		}
	}

	/**
	 * xml一般隐患统计图sql
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadNormalDangerByIndustryAndAreaSql1(FkArea a, String remark, int n) throws ApplicationAccessException {

		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		String areaType = "";
		String areaType1 = "";
		Long areaCode = a.getAreaCode();
		if (areaCode == 330200000000l) {
			areaType = "first_area";
			areaType1 = "second_area";
		} else {
			areaType = "second_area";
			areaType1 = "third_area";
		}
		int nowYear = 2009;
		nowYear = cal.get(Calendar.YEAR);
		int nextYear = nowYear + 1;
		String sqlCondition = " and this_.create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sqlCondition1 = " and da2_.create_time<to_date('" + nowYear + "-" + (cal.get(Calendar.MONTH) + 1) + "-01','yyyy-MM-dd')";

		String sql = "";
		sql += " select " + ((n == 0) ? " 0 " : " f.area_code") + " ,d.type,d.c " + ((n == 0) ? ",0" : " ,f.area_name") + " from  " + ((n == 0) ? "DA_INDUSTRY_PARAMETER t" : "fk_area f") + "    left join (";
		sql += " select da2_." + ((n == 0) ? areaType : areaType1) + " as area,this_.type,count(*) as c  from  DA_NORMAL_DANGER this_,DA_COMPANY_PASS dcp1_,DA_COMPANY da2_  where";
		sql += " this_.PAR_DA_COM_ID=dcp1_.PAR_DA_COM_ID   and dcp1_.PAR_DA_COM_ID=da2_.ID   and this_.IS_DELETED=0   and dcp1_.IS_DELETED=0 and da2_.IS_DELETED=0 ";
		sql += " and da2_." + areaType + "=" + areaCode + " ";
		if (n == 1) {
			sql += "and da2_." + ((n == 0) ? areaType : areaType1) + " in (select a.area_code  from fk_area a  where a.father_id  =" + a.getId() + ")  ";
		}
		sql += sqlCondition + sqlCondition1;

		if (remark.equals("anwei")) {
			sql += " and ( this_.par_da_com_id in ( select  par_da_com_id  from da_company_industry_rel)  or  this_.par_da_com_id in ( select par_da_com_id  from da_company_industry_rell  ) )  and this_.IS_DANGER=1 and dcp1_.IS_AFFIRM=1";
		} else {
			sql += " and ( this_.par_da_com_id in ( select  par_da_com_id  from da_company_industry_rel where   par_da_ind_id = (select  id  from da_industry_parameter  where depiction='" + remark + "'   and type=1 ))  or  this_.par_da_com_id in ( select par_da_com_id  from da_company_industry_rell where   par_da_ind_id = (select  id  from da_industry_parameter  where depiction='" + remark + "'   and type=1 ) ) )  and this_.IS_DANGER=1 and dcp1_.IS_AFFIRM=1";
		}
		sql += " group  by   this_.type ,da2_." + ((n == 0) ? areaType : areaType1) + ") d on  " + ((n == 0) ? "d.type = t.id    where t.par_da_ind_id = 440967" : "f.area_code=d.area  where  f.father_id = " + a.getId() + "") + "    order by " + ((n == 0) ? " t.id " : " f.sort_num,f.area_code,d.type") + " ";
//		System.out.println("sql: " + n + ":" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				if (n == 1 && res.getString(2) == null) { // 3个都无,添加3个0
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(1327);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistics.add(statistic);
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(1332);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistics.add(statistic);
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(1344);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistics.add(statistic);
				} else {
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(res.getInt(2));
					statistic.setNumber(res.getInt(3));
					statistic.setName(res.getString(4));
					statistics.add(statistic);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;

	}

	/**
	 * xml一般隐患统计图sql
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	private List<Statistic> loadNormalDangerByIndustryAndAreaSql2(FkArea a, String remark, int n) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		String areaType = "";
		String areaType1 = "";
		Long areaCode = a.getAreaCode();
		if (areaCode == 330200000000l) {
			areaType = "first_area";
			areaType1 = "second_area";
		} else {
			areaType = "second_area";
			areaType1 = "third_area";
		}
		int nowYear = 2009;
		nowYear = cal.get(Calendar.YEAR);
		int nextYear = nowYear + 1;
		int last3Year = nowYear - 2;
		String sqlCondition = " and this_.create_time between to_date('" + last3Year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sqlCondition1 = " and da2_.create_time<to_date('" + nowYear + "-" + (cal.get(Calendar.MONTH) + 1) + "-01','yyyy-MM-dd')";

		String sql = "";
		sql += " select " + ((n == 0) ? " 0 " : " f.area_code") + " ,d.type,d.c " + ((n == 0) ? ",0" : " ,f.area_name") + ",  years from  " + ((n == 0) ? "DA_INDUSTRY_PARAMETER t" : "fk_area f") + "    left join (";
		sql += " select da2_." + ((n == 0) ? areaType : areaType1) + " as area,this_.type,count(*) as c, to_char(this_.create_time, 'yyyy') years  from  DA_NORMAL_DANGER this_,DA_COMPANY_PASS dcp1_,DA_COMPANY da2_  where";
		sql += " this_.PAR_DA_COM_ID=dcp1_.PAR_DA_COM_ID   and dcp1_.PAR_DA_COM_ID=da2_.ID   and this_.IS_DELETED=0   and dcp1_.IS_DELETED=0 and da2_.IS_DELETED=0 ";
		sql += " and da2_." + areaType + "=" + areaCode + " ";
		if (n == 1) {
			sql += "and da2_." + ((n == 0) ? areaType : areaType1) + " in (select a.area_code  from fk_area a  where a.father_id  =" + a.getId() + ")  ";
		}
		sql += sqlCondition + sqlCondition1;

		if (remark.equals("anwei")) {
			sql += " and ( this_.par_da_com_id in ( select  par_da_com_id  from da_company_industry_rel)  or  this_.par_da_com_id in ( select par_da_com_id  from da_company_industry_rell  ) )  and this_.IS_DANGER=1 and dcp1_.IS_AFFIRM=1";
		} else {
			sql += " and ( this_.par_da_com_id in ( select  par_da_com_id  from da_company_industry_rel where   par_da_ind_id = (select  id  from da_industry_parameter  where depiction='" + remark + "'   and type=1 ))  or  this_.par_da_com_id in ( select par_da_com_id  from da_company_industry_rell where   par_da_ind_id = (select  id  from da_industry_parameter  where depiction='" + remark + "'   and type=1 ) ) )  and this_.IS_DANGER=1 and dcp1_.IS_AFFIRM=1";
		}
		sql += " group  by   this_.type ,da2_." + ((n == 0) ? areaType : areaType1) + ",to_char(this_.create_time, 'yyyy') ) d on  " + ((n == 0) ? "d.type = t.id    where t.par_da_ind_id = 440967" : "f.area_code=d.area  where  f.father_id = " + a.getId() + "") + "    order by d.years," + ((n == 0) ? " t.id " : " f.sort_num,f.area_code,d.type") + " ";
//		System.out.println("sql: " + n + ":" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				if (n == 1 && res.getString(2) == null) { // 3个都无,添加3个0
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(1327);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistic.setYear(res.getInt(5));
					statistics.add(statistic);
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(1332);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistic.setYear(res.getInt(5));
					statistics.add(statistic);
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(1344);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistic.setYear(res.getInt(5));
					statistics.add(statistic);
				} else {
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(res.getInt(2));
					statistic.setNumber(res.getInt(3));
					statistic.setName(res.getString(4));
					statistic.setYear(res.getInt(5));
					statistics.add(statistic);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;

	}
	
	/**
	 * 创建重大隐患统计图xml(隐患分类统计图)
	 * 
	 * @param area
	 * @param remark
	 * @param filePath
	 * @throws ApplicationAccessException
	 */
	public void loadDangerByIndustryAndArea1(String remark) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		String hql = "from FkArea t  where  t.id=3020 or t.fatherId=3020 order by id ";
		// String hql = "from FkArea t  where  t.id=3028  ";
		List<FkArea> areas0 = areaPersistenceIface.loadAreasByHql(hql);
		for (FkArea a : areas0) {

			Calendar cal = Calendar.getInstance();
			String filePath0 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_" + a.getAreaCode() + "_" + remark + "_all_da_danger0.xml";
			String filePath1 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_" + a.getAreaCode() + "_" + remark + "_all_da_danger1.xml";
			String filePath2 = ConfigUtil.getPropertyValue("xml.release.dir") + File.separator + cal.get(Calendar.YEAR) + "_" + a.getAreaCode() + "_" + remark + "_da_danger.xml";

			StringBuffer buffer0 = new StringBuffer(); // 总的报表_柱状图
			StringBuffer buffer1 = new StringBuffer(); // 总的报表_饼图

			buffer0.append("<?xml version='1.0' ?>");
			buffer0.append("<chart caption='' plotSpacePercent='80' formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");
			buffer1.append("<?xml version='1.0' ?>");
			buffer1.append("<chart caption=''  showValues='0'  decimals='0'  formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");

			statistics = loadDangerByIndustryAndAreaSql1(a, remark, 0);
			buffer0.append("<categories>");
			buffer0.append("<category label='人' />");
			buffer0.append("<category label='物' />");
			buffer0.append("<category label='管理' />");
			buffer0.append("</categories>");
			buffer0.append("<dataset color='AFD8F8' showValues='0'>");
			if (statistics.size() > 0) {
				for (int s = 0; s < statistics.size(); s++) {

					if (s == 0) {
						if (statistics.get(s).getType() == 472033) {
							buffer0.append("<set value='" + statistics.get(s).getNumber() + "'  color='AFD8F8'  />");
							buffer1.append("<set label='人' value='" + statistics.get(s).getNumber() + "' />");
						} else {
							buffer0.append("<set value='0'  color='AFD8F8'  />");
							buffer1.append("<set label='人' value='0' />");
						}

					}
					if (s == 1) {

						if (statistics.get(s).getType() == 472034) {
							buffer0.append("<set value='" + statistics.get(s).getNumber() + "'  color='F6BD0F'  />");
							buffer1.append("<set label='物' value='" + statistics.get(s).getNumber() + "' />");
						} else {
							buffer0.append("<set value='0'  color='F6BD0F'  />");
							buffer1.append("<set label='物' value='0' />");
						}
					}
					if (s == 2) {
						if (statistics.get(s).getType() == 472035) {
							buffer0.append("<set value='" + statistics.get(s).getNumber() + "'  color='8BBA00'  />");
							buffer1.append("<set label='管理' value='" + statistics.get(s).getNumber() + "' />");
						} else {
							buffer0.append("<set value='0'  color='8BBA00'  />");
							buffer1.append("<set label='管理' value='0' />");
						}
					}
				}
			}
			buffer0.append("</dataset>");
			buffer0.append("</chart>");
			writeToFile(filePath0, buffer0);

			buffer1.append("</chart>");
			writeToFile(filePath1, buffer1);

			// //柱状_其下报表
			StringBuffer buffer2 = new StringBuffer();
			StringBuffer buffer2_0 = new StringBuffer();
			StringBuffer buffer2_1 = new StringBuffer();
			StringBuffer buffer2_2 = new StringBuffer();
			StringBuffer buffer2_3 = new StringBuffer();
			buffer2.append("<?xml version='1.0' ?>");
			buffer2.append("<chart caption=''   showValues='0'  decimals='0'  formatNumberScale='0'  baseFontSize='12' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0'  divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30'AlternateHGridColor='CCCCCC'>");
			statistics = loadDangerByIndustryAndAreaSql1(a, remark, 1);
			buffer2_0.append("<categories>");
			buffer2_1.append("<dataset seriesName='人' color='AFD8F8' showValues='0'>");
			buffer2_2.append("<dataset seriesName='物' color='F6BD0F' showValues='0'>");
			buffer2_3.append("<dataset seriesName='管理' color='8BBA00' showValues='0'>");
			String aname1 = "";
			String aname2 = "";
			if (statistics.size() > 0) {
				for (int s = 0; s < statistics.size(); s++) {
					aname1 = statistics.get(s).getName();
					buffer2_0.append("<category label='" + statistics.get(s).getName() + "' />");

					if (statistics.get(s).getType() == 472033) {
						buffer2_1.append("<set value='" + statistics.get(s).getNumber() + "' />");

						s++;
						if (s == statistics.size()) { // 末个
							buffer2_2.append("<set value='0' />");
							buffer2_3.append("<set value='0' />");
						} else {
							aname2 = statistics.get(s).getName();
							if (!aname1.equals(aname2)) {
								buffer2_2.append("<set value='0' />");
								buffer2_3.append("<set value='0' />");
								s--;
							} else {
								if (statistics.get(s).getType() == 472034) {
									buffer2_2.append("<set value='" + statistics.get(s).getNumber() + "' />");
									s++;
									if (s == statistics.size()) { // 末个
										buffer2_3.append("<set value='0' />");
									} else {
										aname2 = statistics.get(s).getName();
										if (!aname1.equals(aname2)) {
											buffer2_3.append("<set value='0' />");
											s--;
										} else {
											buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
										}
									}
								} else if (statistics.get(s).getType() == 472035) {
									buffer2_2.append("<set value='0' />");
									buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
								}

							}
						}

					} else if (statistics.get(s).getType() == 472034) {
						buffer2_1.append("<set value='0' />");
						buffer2_2.append("<set value='" + statistics.get(s).getNumber() + "' />");

						s++;
						if (s == statistics.size()) { // 末个
							buffer2_3.append("<set value='0' />");
						} else {
							aname2 = statistics.get(s).getName();
							if (!aname1.equals(aname2)) {
								buffer2_3.append("<set value='0' />");
								s--;
							} else {
								buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
							}
						}

					} else if (statistics.get(s).getType() == 472035) {
						buffer2_1.append("<set value='0' />");
						buffer2_2.append("<set value='0' />");
						buffer2_3.append("<set value='" + statistics.get(s).getNumber() + "' />");
					}

				}
			}

			buffer2_0.append("</categories>");
			buffer2_1.append("</dataset>");
			buffer2_2.append("</dataset>");
			buffer2_3.append("</dataset>");
			buffer2.append(buffer2_0);
			buffer2.append(buffer2_1);
			buffer2.append(buffer2_2);
			buffer2.append(buffer2_3);
			buffer2.append("</chart>");
			writeToFile(filePath2, buffer2);
		}
	}

	/**
	 * xml重大隐患统计图sql
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadDangerByIndustryAndAreaSql1(FkArea a, String remark, int n) throws ApplicationAccessException {

		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		String areaType = "";
		String areaType1 = "";
		Long areaCode = a.getAreaCode();
		if (areaCode == 330200000000l) {
			areaType = "first_area";
			areaType1 = "second_area";
		} else {
			areaType = "second_area";
			areaType1 = "third_area";
		}
		int nowYear = 2009;
		nowYear = cal.get(Calendar.YEAR);
		int nextYear = nowYear + 1;
		String sqlCondition = " and this_.create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sqlCondition1 = " and da2_.create_time<to_date('" + nowYear + "-" + (cal.get(Calendar.MONTH) + 1) + "-01','yyyy-MM-dd')";

		String sql = "";
		sql += " select  " + ((n == 0) ? " 0 " : " f.area_code") + " ,d.type,d.c " + ((n == 0) ? ",0" : " ,f.area_name") + "  from  " + ((n == 0) ? "DA_INDUSTRY_PARAMETER t" : "fk_area f") + "    left join (";
		sql += " select da2_." + ((n == 0) ? areaType : areaType1) + " as area,t.par_da_ind_id as type,count(*) as c  from  DA_DANGER this_,DA_COMPANY_PASS dcp1_,DA_COMPANY da2_, DA_DANGER_TYPE_REL t  where";
		sql += " this_.PAR_DA_COM_ID=dcp1_.PAR_DA_COM_ID   and dcp1_.PAR_DA_COM_ID=da2_.ID   and this_.IS_DELETED=0   and dcp1_.IS_DELETED=0 and da2_.IS_DELETED=0 and t.par_da_dan_id = this_.id ";
		sql += " and da2_." + areaType + "=" + areaCode + " ";
		if (n == 1) {
			sql += "and da2_." + ((n == 0) ? areaType : areaType1) + " in (select a.area_code  from fk_area a  where a.father_id  =" + a.getId() + ")  ";
		}
		sql += sqlCondition + sqlCondition1;

		if (remark.equals("anwei")) {
			sql += " and ( this_.par_da_com_id in ( select  par_da_com_id  from da_company_industry_rel)  or  this_.par_da_com_id in ( select par_da_com_id  from da_company_industry_rell  ) )  and dcp1_.IS_AFFIRM=1";
		} else {
			sql += " and ( this_.par_da_com_id in ( select  par_da_com_id  from da_company_industry_rel where   par_da_ind_id = (select  id  from da_industry_parameter  where depiction='" + remark + "'   and type=1 ))  or  this_.par_da_com_id in ( select par_da_com_id  from da_company_industry_rell where   par_da_ind_id = (select  id  from da_industry_parameter  where depiction='" + remark + "'   and type=1 ) ) )   and dcp1_.IS_AFFIRM=1";
		}
		sql += " group  by   t.par_da_ind_id ,da2_." + ((n == 0) ? areaType : areaType1) + ") d on  " + ((n == 0) ? "d.type = t.id    where t.par_da_ind_id = 437105 and t.is_deleted = 0" : "f.area_code=d.area  where  f.father_id = " + a.getId() + "") + "    order by  " + ((n == 0) ? " t.id " : " f.sort_num,f.area_code,d.type") + " ";
//		System.out.println("ssql: " + n + ":" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				if (n == 1 && res.getString(2) == null) { // 3个都无,添加3个0
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(472033);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistics.add(statistic);
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(472034);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistics.add(statistic);
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(472035);
					statistic.setNumber(0);
					statistic.setName(res.getString(4));
					statistics.add(statistic);
				} else {
					statistic = new Statistic();
					statistic.setAreaCode(res.getLong(1));
					statistic.setType(res.getInt(2));
					statistic.setNumber(res.getInt(3));
					statistic.setName(res.getString(4));
					statistics.add(statistic);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;

	}

	private void writeToFile(String filePath, StringBuffer buffer) {
		try {
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);
			out.write(buffer.toString().getBytes("GBK"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// （年度）
	// （其他）企业其他行业（有色、冶金、纺织、民爆器材生产、船舶修造、电力生产、电子（电器）、造纸、印染、其他）
	// （危化）企业安监危化（危险化学品、生产储存、经营带储存、使用剧毒品、加油站、使用危险化学品构成重大危险源）
	// 已审核企业
	// 一年的一般和重大隐患数，一年的零隐患数，本年度本季度统计数据

	// (季度)
	// （其他）企业其他行业（有色、冶金、纺织、民爆器材生产、船舶修造、电力生产、电子（电器）、造纸、印染、其他）
	// （危化）企业安监危化（危险化学品、生产储存、经营带储存、使用剧毒品、加油站、使用危险化学品构成重大危险源）
	// 已审核企业
	// 一个季度的一般和重大隐患数，一个季度的零隐患数，本年度本季度统计数据
	public List<Statistic> loadReportByInd(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		// System.out.println("current_quarter: " + current_quarter);
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = null;
		String fatherId = null;

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		String backup_date = ""; // 历史表查询的日期

		// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
		if (st.getYear() != year || current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																			// ,则取历史表
			// 2013_11开始备份
			if (st.getQuarter() == 0) { // 全年

				if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																	// 2013.11当年取动态
					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					da_industry_parameter = "da_industry_parameter_his";
					backup_date = "201311";
				} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																			// 2013(包括)之后取那年的12月份
																			// yyyy-12
					fk_area = "fk_area_his";
					da_company = "da_company_his";
					da_company_pass = "da_company_pass_his";
					da_company_industry_rel = "da_company_industry_rel_his";
					da_industry_parameter = "da_industry_parameter_his";
					backup_date = st.getYear() + "12";
				}

			} else { // 季度

				// System.out.println("季度");
				fk_area = "fk_area_his";
				da_company = "da_company_his";
				da_company_pass = "da_company_pass_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_industry_parameter = "da_industry_parameter_his";
				if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
					backup_date = "201311";
				} else if (st.getQuarter() == 4) {
					backup_date = st.getYear() + "12";

				} else if (st.getQuarter() == 3) {
					backup_date = st.getYear() + "09";

				} else if (st.getQuarter() == 2) {
					backup_date = st.getYear() + "06";

				} else if (st.getQuarter() == 1) {
					backup_date = st.getYear() + "03";

				}
			}
		}

		// System.out.println("st.getYear(): " + st.getYear());
		// System.out.println("st.getMonth: " + st.getMonth());
		// System.out.println("month: " + month);
		// System.out.println("year: " + year);
		// System.out.println("st.getQuarter(): " + st.getQuarter());

		if (330218000000l == area.getAreaCode() || 330219000000l == area.getAreaCode() || 330215000000l == area.getAreaCode()) {
			areaType = "second_area";
			fatherId = "id";
		} else {
			areaType = areaRate == 4 ? "third_area" : "second_area";
			fatherId = "father_id";
		}
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-03-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-06-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-9-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-12-01";
				}
			}
		}
		// 查询过滤掉未定义的行业
		String undefinedSql = " and (code is null or  code  not like '%_undefined') ";
		String sqlCondition = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByCom = "and dc.create_time < to_date('" + endDate + "','yyyy-MM-dd')";
		String depictionSql = "";
		if (st.getRemark3() != null && !"".equals(st.getRemark3())&&st.getRemark2() != null && !"".equals(st.getRemark2())) {
			depictionSql = " ( id in (select id from " + da_industry_parameter + " t2 where  " + (da_industry_parameter.equals("da_industry_parameter_his") ? " t2.backup_date=" + backup_date + " and  " : "") + " t2.depiction in ('" + st.getRemark() + "','" + st.getRemark2() + "','" + st.getRemark3() + "') )   or    par_da_ind_id in (select id from " + da_industry_parameter + " t2 where " + (da_industry_parameter.equals("da_industry_parameter_his") ? " t2.backup_date=" + backup_date + " and  " : "") + " t2.depiction in ('" + st.getRemark() + "','" + st.getRemark2() + "','" + st.getRemark3() + "') ) )";
		} else{
			if (st.getRemark2() != null && !"".equals(st.getRemark2())) {
				depictionSql = " ( id in (select id from " + da_industry_parameter + " t2 where  " + (da_industry_parameter.equals("da_industry_parameter_his") ? " t2.backup_date=" + backup_date + " and  " : "") + " t2.depiction in ('" + st.getRemark() + "','" + st.getRemark2() + "') )   or    par_da_ind_id in (select id from " + da_industry_parameter + " t2 where " + (da_industry_parameter.equals("da_industry_parameter_his") ? " t2.backup_date=" + backup_date + " and  " : "") + " t2.depiction in ('" + st.getRemark() + "','" + st.getRemark2() + "') ) )";
			} else {
				depictionSql = "  ( id in (select id from " + da_industry_parameter + " t2 where " + (da_industry_parameter.equals("da_industry_parameter_his") ? " t2.backup_date=" + backup_date + " and  " : "") + " t2.depiction in ('" + st.getRemark() + "') )  or   par_da_ind_id in (select id from " + da_industry_parameter + " t2 where " + (da_industry_parameter.equals("da_industry_parameter_his") ? " t2.backup_date=" + backup_date + " and  " : "") + " t2.depiction in ('" + st.getRemark() + "') ) )";
			}
		} 

//		System.out.println("backup_date: " + backup_date);

		String sql = "select count(distinct(da.id)) inhere,count(distinct(rep.company_id))num,0,map.area_code,map.dip_id,map.name, count(distinct(rep.company_id)) reported " + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa    " + " full join (select id,name,grade_path from "
				+ da_industry_parameter
				+ " where is_deleted=0  "
				+ undefinedSql
				+ (da_industry_parameter.equals("da_industry_parameter_his") ? "and backup_date=" + backup_date + "   " : "")
				+ " and    "

				// + " (grade_path like (select grade_path||'%' from " +
				// da_industry_parameter + " where  depiction = '" +
				// st.getRemark() + "'   " +
				// (da_industry_parameter.equals("da_industry_parameter_his") ?
				// "and backup_date=" + backup_date + "   " : "") +
				// " and type=1 "+undefinedSql+")"

				+ depictionSql

				+ ") dip on 1!=2  " + " where fa.is_deleted=0 " + (fk_area.equals("fk_area_his") ? "and fa.backup_date=" + backup_date + "   " : "") + " and fa." + fatherId + "=(select id from " + fk_area + " where area_code= " + area.getAreaCode() + "  " + (fk_area.equals("fk_area_his") ? "and backup_date=" + backup_date + "   " : "") + "  )) map   " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_ind_id = map.dip_id   " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc left join " + da_company_pass + " dcp    " + " on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + " and dcp.is_deleted=0 and dcp.is_affirm=1 " + sqlTypeByCom + ")   " + " da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id   "
				+ " left join (select company_id from da_company_quarter_report " + " where year = " + st.getYear() + " and quarter = " + st.getQuarter() + ") rep on  rep.company_id = da.id" + "   " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "where dcir.backup_date=" + backup_date + "   " : " where dcir.par_da_ind_id is not null ") + "    group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";

		// System.out.println("depictionSql: " + depictionSql);

//		System.out.println("========汇总sql: " + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setZero(res.getInt(3));
				statistic.setAreaCode(res.getLong(4));
				statistic.setIndustryId(res.getInt(5));
				statistic.setEnumName(res.getString(6));
				statistic.setReportedNum(res.getInt(7));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<Statistic> loadReportByItem(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = null;
		String fatherId = null;
		if (330218000000l == area.getAreaCode() || 330219000000l == area.getAreaCode() || 330215000000l == area.getAreaCode()) {
			areaType = "second_area";
			fatherId = "id";
		} else {
			areaType = areaRate == 4 ? "third_area" : "second_area";
			fatherId = "father_id";
		}
		Integer type = st.getType();
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-01-01";
				}
			}
		}
		String noAreaSql = "jianwei".equals(st.getRemark())?  " union select 0 from dual " : "";
		String sqlParam = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String createParam = " and di.create_time < to_date('" + endDate + "','yyyy-MM-dd')";
		String sql = "select fa.area_code, sum(inhere) inhere, sum(num) num,sum(finish) finish ,sum(zero) zero  " + " from (select area_code from fk_area where is_deleted=0 and " + fatherId + " = (select id from fk_area where  area_code= " + area.getAreaCode() + ") " + noAreaSql + " ) fa " + " left join (select di." + areaType + ",count(distinct(di.id)) inhere,0 num,0 zero,0 finish, 'i' type  " + " from da_item di where di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 " + createParam + " group by di." + areaType + " union  " + " select di." + areaType + ",0 inhere,count(distinct(di.id)) num,0 zero ,0 finish,'n' type  " + " from da_item di left join (select par_da_ite_id from da_item_season_report where is_deleted=0 " + sqlParam + ") disr  " + " on disr.par_da_ite_id = di.id where di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 " + createParam + " " + " and disr.par_da_ite_id is not null group by di." + areaType + " union  " + " select di." + areaType
				+ ",0 inhere,0 num,count(distinct(di.id)) zero ,0 finish,'z'type  " + " from  da_item di left join (select par_da_ite_id,ordinary_danger_finding from da_item_season_report  " + " where is_deleted=0" + sqlParam + ") disr on disr.par_da_ite_id = di.id left join (select par_da_ite_id from da_item_danger  " + " where is_deleted=0" + sqlParam + ") did on did.par_da_ite_id=di.id where di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 " + createParam + " " + " and disr.par_da_ite_id is not null and disr.ordinary_danger_finding=0 and did.par_da_ite_id is null  " + " group by di." + areaType + " union  " + " select di." + areaType + ",0 inhere,0 num,0 zero, count(distinct(di.id)) finish,'f'type  " + " from da_item di where di.type = " + type + " and di.iscompleted='1' and di.is_deleted=0 " + createParam + " " + " group by di." + areaType + ") value on value." + areaType + " = fa.area_code group by fa.area_code";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistic.setAnum(res.getInt(4));
				statistic.setBnum(res.getInt(5));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 项目一般隐患、重大隐患统计
	 * 
	 * @param area
	 * @param remark
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadReportByItemTrouble(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = null;
		String fatherId = null;
		if (330218000000l == area.getAreaCode() || 330219000000l == area.getAreaCode() || 330215000000l == area.getAreaCode()) {
			areaType = "second_area";
			fatherId = "id";
		} else {
			areaType = areaRate == 4 ? "third_area" : "second_area";
			fatherId = "father_id";
		}
		Integer type = st.getType();
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-01-01";
				}
			}
		}
		String noAreaSql = "jianwei".equals(st.getRemark())?  " union select 0 from dual " : "";
		String sqlParam = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sql = "select fa.area_code,sum(odf) odf,sum(odfg) odfg,sum(ytdCount) ytdCount,sum(ytdgCount) ytdgCount  " + " from (select area_code from fk_area where is_deleted=0 and " + fatherId + " = (select id from fk_area where  area_code= " + area.getAreaCode() + ") " + noAreaSql + " ) fa  " + " left join (select di." + areaType + ",sum(distinct(ytqb.ordinary_danger_finding)) odf,sum(distinct(ytqb.ordinary_danger_not_govern)) odfg,  " + " 0 ytdCount ,0 ytdgCount,'yiban' type from da_item di left join (select * from da_item_season_report where is_deleted=0 " + sqlParam + ") ytqb on  " + " ytqb.par_da_ite_id = di.id where di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0 and ytqb.par_da_ite_id is not null  " + " group by di." + areaType + " union   " + " select di." + areaType + ",0 odf,0 odfg,count(distinct(ytd.id)) ytdCount,count(distinct(ytdg.par_da_it_id)) ytdgCount ,'zhongda' type  " + " from da_item di left join (select * from da_item_danger where is_deleted=0 " + sqlParam
				+ ") ytd on ytd.par_da_ite_id=di.id  " + " left join da_item_danger_gover ytdg on ytdg.par_da_it_id=ytd.id where di.type = " + type + " and di.iscompleted='0' and di.is_deleted=0  " + " and ytd.is_deleted=0 and ytdg.is_deleted=0 and ytd.par_da_ite_id is not null and ytdg.par_da_it_id is not null and ytd.id is not null " + " group by di." + areaType + ") list on list." + areaType + " = fa.area_code group by fa.area_code";
		// System.out.println("sql=" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistic.setAnum(res.getInt(4));
				statistic.setBnum(res.getInt(5));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<Statistic> loadReportByAjOther(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = null;
		String fatherId = null;

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_industry_parameter = "da_industry_parameter";
		int backup_date = 0; // 历史表查询的日期

		if (st.getQuarter() == 0) { // 全年
			if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																// 2013.11当年取动态
				fk_area = "fk_area_his";
				da_company = "da_company_his";
				da_company_pass = "da_company_pass_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_industry_parameter = "da_industry_parameter_his";
				backup_date = 201311;
			} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																		// 2013(包括)之后取那年的12月份
																		// yyyy-12
				fk_area = "fk_area_his";
				da_company = "da_company_his";
				da_company_pass = "da_company_pass_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_industry_parameter = "da_industry_parameter_his";
				backup_date = Integer.parseInt(st.getYear() + "12");
			}
		} else { // 季度
			if (st.getYear() != year || (st.getYear() == year && st.getMonth() != 0 && st.getMonth() <= month)) {
				fk_area = "fk_area_his";
				da_company = "da_company_his";
				da_company_pass = "da_company_pass_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_industry_parameter = "da_industry_parameter_his";
				if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
																														// 未做备份,则取最早备份日期2013_11
					backup_date = 201311;
				} else if (st.getQuarter() == 4) {
					backup_date = Integer.parseInt(st.getYear() + "12");
				} else if (st.getQuarter() == 3) {
					backup_date = Integer.parseInt(st.getYear() + "09");
				} else if (st.getQuarter() == 2) {
					backup_date = Integer.parseInt(st.getYear() + "06");
				} else if (st.getQuarter() == 1) {
					backup_date = Integer.parseInt(st.getYear() + "03");
				}
			}
		}

		if (330218000000l == area.getAreaCode() || 330219000000l == area.getAreaCode() || 330215000000l == area.getAreaCode()) {
			areaType = "second_area";
			fatherId = "id";
		} else {
			areaType = areaRate == 4 ? "third_area" : "second_area";
			fatherId = "father_id";
		}
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-01-01";
				}
			}
		}

		String depictionSql = "";

		if (st.getRemark2() == null && st.getRemark3() == null) {
			depictionSql = "  and depiction in ('" + st.getRemark() + "')";
		} else {
			depictionSql += "  and depiction in ('" + st.getRemark() + "' ";
			if (st.getRemark2() != null && !"".equals(st.getRemark2())) {
				depictionSql += " ,'" + st.getRemark2() + "' ";
			}

			if (st.getRemark3() != null && !"".equals(st.getRemark3())) {
				depictionSql += " ,'" + st.getRemark3() + "'";
			}
			depictionSql += ")";
		}

		String fatherSql="";
		if(st.getFatherId()!=null&&st.getFatherId()>0){
			fatherSql=" and PAR_DA_IND_ID="+st.getFatherId()+" ";
		}
		// 查询过滤掉未定义的行业
		String undefinedSql = " and (code is null or  code  not like '%_undefined') ";
		String sqlCondition = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sql = "select count(distinct(da.id)) inhere,count(distinct(dn.par_da_com_id)) num,count(distinct(z.par_da_com_id))zero,map.area_code,map.dip_id,map.name, count(distinct(rep.company_id)) reported" + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from " + fk_area + " fa  "

		+ " full join (select id,name,grade_path from " + da_industry_parameter + " where is_deleted=0 " + undefinedSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and backup_date=" + backup_date + "   " : "") + " " + depictionSql + " "+fatherSql+"  and type=1) dip on 1!=2   " + " where fa.is_deleted=0 " + (fk_area.equals("fk_area_his") ? "and fa.backup_date=" + backup_date + "   " : "") + " and fa." + fatherId + "=(select id from " + fk_area + " where area_code= " + area.getAreaCode() + "  " + (fk_area.equals("fk_area_his") ? "and backup_date=" + backup_date + "   " : "") + ")) map  " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from " + da_company + " dc left join " + da_company_pass + " dcp  " + " on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 " + (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + " "
				+ (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + "and dcp.is_deleted=0 and dcp.is_affirm=1)  " + " da on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id   " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and dcir.backup_date=" + backup_date + "   " : "") + "          " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0 " + sqlCondition + " union  " + " select par_da_com_id from da_danger where is_deleted=0 " + sqlCondition + ") dn on dn.par_da_com_id=da.id " + " left join (select par_da_com_id from da_normal_danger where is_deleted=0  " + sqlCondition + " group by par_da_com_id having sum(is_danger)<1) z on z.par_da_com_id = da.id " + " left join (select company_id from da_company_quarter_report " + " where year = " + st.getYear() + " and quarter = " + st.getQuarter() + ") rep on  rep.company_id = da.id "
				+ " group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";

		// log.info("sql:" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setZero(res.getInt(3));
				statistic.setAreaCode(res.getLong(4));
				statistic.setIndustryId(res.getInt(5));
				statistic.setEnumName(res.getString(6));
				statistic.setReportedNum(res.getInt(7));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<Statistic> loadCompanyLevelPoint(FkArea area) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = areaRate == 4 ? "third_area" : "second_area";
		String sql = " select map.area_code,map.enum_code,map.enum_name,count(distinct(da.id))" + " from (select fa.area_code,fe.enum_code,fe.enum_name from fk_area fa " + " full join fk_enum fe on 1!=2 where fa.is_deleted=0 and fe.is_deleted=0" + " and fe.father_id=(select id from fk_enum where enum_code= 'point_abcd')" + " and fa.father_id=(select id from fk_area where area_code= " + area.getAreaCode() + ")) map" + " left join (select dc.id,dc.second_area,dc.third_area,dc.company_level from da_company dc" + " left join da_company_pass dcp on dcp.par_da_com_id = dc.id where " + " dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1) da " + " on da.company_level = map.enum_code and da." + areaType + "=map.area_code" + " group by map.area_code,map.enum_code,map.enum_name order by map.area_code,map.enum_code";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setAreaCode(res.getLong(1));
				statistic.setEnumCode(res.getString(2));
				statistic.setEnumName(res.getString(3));
				statistic.setInhere(res.getInt(4));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public Statistic loadReportByCompany(DaCompany company, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String startDate = "";
		String reportDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-01-01";
				}
			}
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		if (mDateTime.compareTo(endDate) > 0)
			mDateTime = endDate;
		String sqlType = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByCom = " and dc.create_time <= to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByDangerGorver = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sql = " select  m.* ,y.report_date from ( select map.company_name,map.reg_address,sum(map.dnd),sum(map.dndg),sum(map.dd)," + " sum(map.ddg),sum(map.target) target,sum(map.goods) goods,sum(map.resources) resources," + " sum(map.finish) finish,sum(map.method) method,sum(map.money) money, map.second_areaName, " + " map.third_areaName,map.fact_name,map.user_phone,map.user_mobile, map.id,map.address  from (" + " select dc.company_name,dc.reg_address,count(distinct(dnd.id)) dnd,count(distinct" + " (dndg.id)) dndg,0 dd, 0 ddg,0 target,0 goods,0 resources, 0 finish,0 method,0 money, " + " (select fa.area_name from fk_area fa where fa.area_code = dc.second_area and fa.area_code != 0) second_areaName," + " (select fa.area_name from fk_area fa where fa.area_code = dc.third_area and fa.area_code != 0) third_areaName," + " u.fact_name,u.user_phone,u.user_mobile, dc.id,dc.address  from da_company dc left join da_company_pass dcp "
				+ " on dcp.par_da_com_id = dc.id left join fk_user_info u on u.id = dcp.com_user_id" + " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and is_danger=1 "
				+ sqlType
				+ ") dnd on dnd.par_da_com_id = dcp.par_da_com_id "
				+ " left join (select id from da_normal_danger where is_deleted=0  and is_danger=1 "
				+ sqlType
				+ " and (is_repaired=1 or is_danger=0)) dndg on dndg.id=dnd.id  ";
		
		        if(company!=null){
		        	if(company.getId()!=null&&company.getId()>0){
		        		sql+=  " where dc.id="+ company.getId();
		        	}else if(company.getUuid()!=null && !"".equals(company.getUuid())){
		        		sql+=  "where dc.uuid='"+ company.getUuid()+"'";
		        	}
		        }
				sql+= " and dcp.is_affirm=1 and u.is_deleted=0 and dcp.is_deleted=0 and dc.is_deleted=0 "
				+ sqlTypeByCom
				+ " group by dc.id,dc.company_name,dc.reg_address,dc.second_area,dc.third_area,u.fact_name,u.user_phone,u.user_mobile,dc.address"

				+ " union"
				+ " select dc.company_name,dc.reg_address,0 dnd,0 dndg,count(distinct(dd.id)) dd,"
				+ " count(distinct(ddg.id)) ddg,sum(ddg.target) target,sum(ddg.goods) "
				+ " goods,sum(ddg.resources) resources, count(ddg.finish_date) finish,sum(ddg.safety_method) "
				+ " method,sum(ddg.govern_money) money, (select fa.area_name from fk_area fa where "
				+ " fa.area_code = dc.second_area and fa.area_code != 0) second_areaName, (select fa.area_name from fk_area fa where "
				+ " fa.area_code = dc.third_area and fa.area_code != 0) third_areaName,u.fact_name,u.user_phone,u.user_mobile, dc.id,dc.address "
				+ " from da_company dc left join da_company_pass dcp on dcp.par_da_com_id = dc.id "
				+ " left join fk_user_info u on u.id = dcp.com_user_id"
				+ " left join (select distinct id,par_da_com_id from da_danger where is_deleted=0 "
				+ sqlType
				+ " ) dd on dd.par_da_com_id = dcp.par_da_com_id "
				+ " left join ( select distinct d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money  "
				+ " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0  "
				+ sqlTypeByDangerGorver
				+ " ) g on g.par_da_dan_id=d.id where d.is_deleted=0 and g.par_da_dan_id is null and d.id is  "
				+ " not null) ddg on ddg.id = dd.id " ;
				
				 if(company!=null){
			        	if(company.getId()!=null&&company.getId()>0){
			        		sql+=  "where dc.id="+ company.getId();
			        	}else if(company.getUuid()!=null && !"".equals(company.getUuid())){
			        		sql+=  "where dc.uuid='"+ company.getUuid()+"'";
			        	}
			    }
				
				sql+=" and dcp.is_affirm=1 and u.is_deleted=0 and "
				+ " dcp.is_deleted=0  and dc.is_deleted=0  "
				+ sqlTypeByCom
				+ " group by "
				+ " dc.id,dc.company_name,dc.reg_address,dc.second_area,dc.third_area,u.fact_name,u.user_phone,u.user_mobile,dc.address) map"
				+ " group by map.company_name,map.reg_address, map.second_areaName, map.third_areaName,map.fact_name,map.user_phone,map.user_mobile, map.id,map.address  ) m    left join (select  dt.report_date,dt.company_id from DA_COMPANY_QUARTER_REPORT dt where  dt.year="
				+ st.getYear()
				+ "  and  dt.quarter="
				+ st.getQuarter()
				+ ") y on y.company_id=m.id";
		 log.info("sql111111: " + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setCompanyName(res.getString(1));
				statistic.setAddress(res.getString(2));
				statistic.setTroubNum(res.getInt(3));
				statistic.setTroubCleanNum(res.getInt(4));
				statistic.setBigTroubNum(res.getInt(5));
				statistic.setPlanBigTroubNum(res.getInt(6));
				statistic.setTarget(res.getInt(7));
				statistic.setGoods(res.getInt(8));
				statistic.setResource(res.getInt(9));
				statistic.setFinishDate(res.getInt(10));
				statistic.setSafetyMethod(res.getInt(11));
				statistic.setGovernMoney(res.getDouble(12));
				statistic.setAreaName((res.getString(13) == null ? "" : res.getString(13)) + (res.getString(14) == null ? "" : res.getString(14)));
				statistic.setLinkMan(res.getString(15));
				statistic.setLinkTel(res.getString(16));
				statistic.setLinkMobile(res.getString(17));
				statistic.setCompanyId(res.getLong(18));
				statistic.setBussnissAddress(res.getString(19));
				reportDate = res.getString(20) == null ? "" : res.getString(20).substring(0, 10);

				if (!reportDate.equals("")) {
					String ab[] = reportDate.split("-");

					reportDate = ab[0] + "年" + ab[1] + "月" + ab[2] + "日";
				}
				statistic.setReportDate(reportDate);
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (statistics != null && statistics.size() > 0) {
			return statistics.get(0);
		} else {
			return null;
		}

	}

	public List<Statistic> loadNomalDangerByIndustry(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = null;
		String fatherId = null;
		if (330218000000l == area.getAreaCode() || 330219000000l == area.getAreaCode() || 330215000000l == area.getAreaCode()) {
			areaType = "second_area";
			fatherId = "id";
		} else {
			areaType = areaRate == 4 ? "third_area" : "second_area";
			fatherId = "father_id";
		}
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}

		// 查询过滤掉未定义的行业
		String undefinedSql = " and (code is null or  code  not like '%_undefined') ";

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		// 过滤掉当月添加的企业
		String createTimeSql = " and dc.create_time<to_date('" + year + "-" + month + "-1', 'yyyy-MM-dd') ";

		String sql = " select count(distinct(dnd.id)) inhere,count(distinct(dnd_g.id)) num,map.area_code,map.dip_id,map.name, " + " (select count(1) from da_industry_parameter where is_deleted=0 " + undefinedSql + " and grade_path like  " + " (select grade_path||'%' from da_industry_parameter where id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonCount,  " + " (select 1 from da_industry_parameter where rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in (map.dip_id)) sonNumber1,  " + " (select 1 from da_industry_parameter where rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in  " + " (select id from  da_industry_parameter where par_da_ind_id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonNumber2 " + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from fk_area fa  " + " full join (select id,name,grade_path from da_industry_parameter where is_deleted=0 " + undefinedSql + "  and  "
				+ " grade_path like (select grade_path||'%' from da_industry_parameter where depiction = '" + st.getRemark() + "' and type=1 " + undefinedSql + " )) dip on 1!=2 " + " where fa.is_deleted=0 and fa." + fatherId + " = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) map  " + " left join da_company_industry_rel dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc left join da_company_pass dcp   " + " on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + createTimeSql + " )  da  " + " on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id  " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlType + ")" + " dnd on dnd.par_da_com_id = da.id " + " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and is_danger=1 and is_repaired=1 " + sqlType
				+ ") dnd_g on dnd_g.id=dnd.id  " + " group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		// System.out.println("sql=" + sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setAreaCode(res.getLong(3));
				statistic.setIndustryId(res.getInt(4));
				statistic.setEnumName(res.getString(5));
				statistic.setSonCount(res.getInt(6));
				statistic.setSonNumber1(res.getInt(7));
				statistic.setSonNumber2(res.getInt(8));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 根据用户获得用户的抄告隐患
	 */
	public List<Statistic> loadTroubleUpAndDownByUser(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " and dt_t.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		String sql = "select count(distinct(dt.id)),sum(dt.is_back),sum(dt.is_result),area_code from ( " + "select area_code,id from fk_area where father_id = (select id from fk_area where area_code=" + area.getAreaCode() + ")) fa " + "left join (select " + "dt_t.second_area,dt_t.id ,dd_d.is_back,dd_d.is_result " + "from da_trouble  dt_t , da_dept dd_d where dt_t.is_deleted=0  and dd_d.is_deleted=0 " + "and dd_d.dept_id is null and find_dept_trade='" + st.getRemark() + "' " + "and dd_d.trouble_id = dt_t.id " + "and dt_t.third_area=0 " + "and dd_d.trouble_copy_type=1 " + sqlType + ") dt on dt.second_area = fa.area_code group by area_code ";

		String sql1 = "select area_code,count(distinct(map.dtId)),count(distinct(nb.trouble_id)),count(distinct(nr.trouble_id))" + "from (" + "select area_code,id " + "from fk_area " + "where father_id = (select id from fk_area where area_code=" + area.getAreaCode() + ")) fa " + "left join ( " + "select dt_t.id dtId,dd_d.trouble_id,dd_d.id deptId,dt_t.second_area " + "from da_trouble dt_t left join da_dept dd_d on dd_d.trouble_id = dt_t.id " + "where dt_t.is_deleted=0 " + "and dd_d.dept_id is null " + sqlType + " and dt_t.third_area=0 " +
		// " and (dd_d.trouble_copy_type=5 or dd_d.trouble_copy_type=4 )"
		// +
				"and (dd_d.mostly_company = 'anjian' or dd_d.copy_company like '%anjian%')" + ") map on fa.area_code = map.second_area left join (" + "select dd.trouble_id,dd.id deptId from da_dept dd " +
				// "where dd.is_back=1 " +
				"where dd.trouble_copy_type=4 " + ") nb on nb.deptId = map.deptId " + "left join (select dd.trouble_id,dd.id deptId from da_dept dd " +
				// "where dd.is_result=1 " +
				"where dd.trouble_copy_type=5 " + ") nr on nr.deptId = map.deptId group by area_code";

		String sql2 = "select count(distinct(dt.id)),sum(dt.is_back),sum(dt.is_result),area_code from ( " + "select area_code,id from fk_area where area_code=" + Nbyhpc.AREA_CODE + ") fa " + "left join (select " + "dt_t.first_area,dt_t.id ,dd_d.is_back,dd_d.is_result " + "from da_trouble dt_t, da_dept dd_d where dt_t.is_deleted=0  and dd_d.is_deleted=0 " + "and dd_d.dept_id is null and dd_d.find_dept_trade='" + st.getRemark() + "' " + "and dd_d.trouble_id = dt_t.id " + "and dt_t.second_area =0" + "and dd_d.trouble_copy_type=1 " + sqlType + ") dt on dt.first_area = fa.area_code group by area_code ";

		String sql3 = "select area_code,count(distinct(map.dtId)),count(distinct(nb.trouble_id)),count(distinct(nr.trouble_id))" + "from (" + "select area_code,id " + "from fk_area " + "where area_code=" + area.getAreaCode() + ") fa " + "left join ( " + "select dt_t.id dtId,dd_d.trouble_id,dd_d.id deptId,dt_t.first_area " + "from da_trouble dt_t left join da_dept dd_d on dd_d.trouble_id = dt_t.id " + "where dt_t.is_deleted=0 " + "and dd_d.dept_id is null " + sqlType + " and dt_t.second_area=0 " +
		// " and (dd_d.trouble_copy_type=5 or dd_d.trouble_copy_type=4 )"
		// +
				"and (dd_d.mostly_company = 'anjian' or dd_d.copy_company like '%anjian%')" + ") map on fa.area_code = map.first_area left join (" + "select dd.trouble_id,dd.id deptId from da_dept dd " +
				// "where dd.is_back=1 " +
				"where dd.trouble_copy_type=4 " + ") nb on nb.deptId = map.deptId " + "left join (select dd.trouble_id,dd.id deptId from da_dept dd " +
				// "where dd.is_result=1 " +
				"where dd.trouble_copy_type=5 " + ") nr on nr.deptId = map.deptId group by area_code";

		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setTotal(res.getInt(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistic.setAreaCode(res.getLong(4));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res = null;
		res = companyPersistenceIface.findBySql(sql2);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setTotal(res.getInt(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistic.setAreaCode(res.getLong(4));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		res = null;
		res = companyPersistenceIface.findBySql(sql1);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setTotal(res.getInt(2));
				statistic.setInhere(res.getInt(3));
				statistic.setNumber(res.getInt(4));
				statistic.setAreaCode(res.getLong(1));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res = null;
		res = companyPersistenceIface.findBySql(sql3);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setTotal(res.getInt(2));
				statistic.setInhere(res.getInt(3));
				statistic.setNumber(res.getInt(4));
				statistic.setAreaCode(res.getLong(1));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return statistics;
	}

	/**
	 * 根据用户获得用户的抄告隐患
	 */
	public List<Statistic> loadTroubleUpAndDownByUserThird(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();

		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " and dt_t.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		String sql = "select count(distinct(dt.id)),sum(dt.is_back),sum(dt.is_result),area_code from ( " + "select area_code,id from fk_area where father_id = (select id from fk_area where area_code=" + area.getAreaCode() + ")) fa " + "left join (select dt_t.third_area,dt_t.id ,dd_d.is_back,dd_d.is_result " + "from da_trouble  dt_t , da_dept dd_d where dt_t.is_deleted=0  and dd_d.is_deleted=0 " + "and dd_d.dept_id is null and dd_d.find_dept_trade='" + st.getRemark() + "' " + "and dd_d.trouble_id = dt_t.id " + "and dd_d.trouble_copy_type=1 " + sqlType + ") dt on dt.third_area = fa.area_code group by area_code ";

		String sql1 = "select area_code,count(distinct(map.dtId)),count(distinct(nb.trouble_id)),count(distinct(nr.trouble_id))" + "from (" + "select area_code,id " + "from fk_area " + "where father_id = (select id from fk_area where area_code=" + area.getAreaCode() + ")) fa " + "left join ( " + "select dt_t.id dtId,dd_d.trouble_id,dd_d.id deptId,dt_t.third_area " + "from da_trouble dt_t left join da_dept dd_d on dd_d.trouble_id = dt_t.id " + "where dt_t.is_deleted=0 " + "and dd_d.dept_id is null " + sqlType +
		// " and (dd_d.trouble_copy_type=5 or dd_d.trouble_copy_type=4 )"
		// +
				"and (dd_d.mostly_company = 'anjian' or dd_d.copy_company like '%anjian%')" + ") map on fa.area_code = map.third_area left join (" + "select dd.trouble_id,dd.id deptId from da_dept dd " +
				// "where dd.is_back=1 " +
				"where dd.trouble_copy_type=4" + ") nb on nb.deptId = map.deptId " + "left join (select dd.trouble_id,dd.id deptId from da_dept dd " +
				// "where dd.is_result=1 " +
				"where dd.trouble_copy_type=5 " + ") nr on nr.deptId = map.deptId group by area_code";

		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				Statistic statistic = new Statistic();
				statistic = new Statistic();
				statistic.setTotal(res.getInt(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistic.setAreaCode(res.getLong(4));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res = null;
		res = companyPersistenceIface.findBySql(sql1);
		try {
			while (res.next()) {
				Statistic statistic = new Statistic();
				statistic = new Statistic();
				statistic.setTotal(res.getInt(2));
				statistic.setInhere(res.getInt(3));
				statistic.setNumber(res.getInt(4));
				statistic.setAreaCode(res.getLong(1));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<DaDept> loadTroubleDownByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaDept> depts = new ArrayList<DaDept>();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " dt.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		String sql = "select * from " + "( select " + " dd.id,dd.find_dept_name,dd.mostly_company,dd.submit_time,dd.is_back,dd.is_result," + " dt.trouble_company_name,dt.trouble_no,rownum rn " + " from DA_DEPT dd, DA_TROUBLE dt where" + " dd.TROUBLE_ID=dt.ID  " + " and dd.IS_DELETED=0 ";
		if (userDetail.getFirstArea() != null) {
			sql += " and dt.first_area=" + userDetail.getFirstArea();
		}
		if (userDetail.getSecondArea() != null) {
			sql += " and dt.second_area=" + userDetail.getSecondArea();
		}
		if (userDetail.getThirdArea() != null) {
			sql += " and dt.third_area=" + userDetail.getThirdArea();
		}
		sql += " and dt.IS_DELETED=0 " + " and dd.FIND_DEPT_TRADE= '" + st.getRemark() + "' " + " and dd.DEPT_ID is null and dd.TROUBLE_COPY_TYPE= " + dept.getTroubleCopyType() + "" + " and " + sqlType + " and rownum < " + pagination.getPageSize() * (pagination.getItemCount() + 1) + " )" + " where rn  >=" + (pagination.getItemCount()) * pagination.getPageSize();
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DaDept tmpDept = new DaDept();
				tmpDept.setId(res.getLong(1));
				tmpDept.setFindDeptName(res.getString(2));
				tmpDept.setMostlyCompany(res.getString(3));
				tmpDept.setSubmitTime(res.getDate(4));
				tmpDept.setBack(res.getBoolean(5));
				tmpDept.setResult(res.getBoolean(6));
				DaTrouble dt = new DaTrouble();
				dt.setTroubleCompanyName(res.getString(7));
				dt.setTroubleNo(res.getString(8));
				tmpDept.setDaTrouble(dt);
				tmpDept.setType(String.valueOf(dept.getTroubleCopyType()));
				depts.add(tmpDept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	/**
	 * 获得已抄告未回复列表
	 */
	public List<DaDept> loadTroubleDownNoBackByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaDept> depts = new ArrayList<DaDept>();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " dt.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		int back;
		if (dept.getBack())
			back = 1;
		else
			back = 0;
		String sql = "select * from " + "( select " + " dd.id,dd.find_dept_name,dd.mostly_company,dd.submit_time,dd.is_back,dd.is_result," + " dt.trouble_company_name,dt.trouble_no,rownum rn " + " from DA_DEPT dd, DA_TROUBLE dt where" + " dd.TROUBLE_ID=dt.ID  " + " and dd.IS_DELETED=0 ";
		if (userDetail.getFirstArea() != null) {
			sql += " and dt.first_area=" + userDetail.getFirstArea();
		}
		if (userDetail.getSecondArea() != null) {
			sql += " and dt.second_area=" + userDetail.getSecondArea();
		}
		if (userDetail.getThirdArea() != null) {
			sql += " and dt.third_area=" + userDetail.getThirdArea();
		}
		sql += " and dt.IS_DELETED=0 " + " and dd.FIND_DEPT_TRADE= '" + st.getRemark() + "' " + " and dd.DEPT_ID is null and dd.IS_BACK= " + back + " and dd.TROUBLE_COPY_TYPE= " + dept.getTroubleCopyType() + "" + " and " + sqlType + " and rownum < " + pagination.getPageSize() * (pagination.getItemCount() + 1) + " )" + " where rn  >=" + (pagination.getItemCount()) * pagination.getPageSize();
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DaDept tmpDept = new DaDept();
				tmpDept.setId(res.getLong(1));
				tmpDept.setFindDeptName(res.getString(2));
				tmpDept.setMostlyCompany(res.getString(3));
				tmpDept.setSubmitTime(res.getDate(4));
				tmpDept.setBack(res.getBoolean(5));
				tmpDept.setResult(res.getBoolean(6));
				DaTrouble dt = new DaTrouble();
				dt.setTroubleCompanyName(res.getString(7));
				dt.setTroubleNo(res.getString(8));
				tmpDept.setDaTrouble(dt);
				tmpDept.setType(String.valueOf(dept.getTroubleCopyType()));
				depts.add(tmpDept);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	/**
	 * 获得已抄告未反馈列表
	 */
	public List<DaDept> loadTroubleDownNoResultByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaDept> depts = new ArrayList<DaDept>();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " dt.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		int result;
		if (dept.getResult())
			result = 1;
		else
			result = 0;
		String sql = "select * from " + "( select " + " dd.id,dd.find_dept_name,dd.mostly_company,dd.submit_time,dd.is_back,dd.is_result," + " dt.trouble_company_name,dt.trouble_no,rownum rn " + " from DA_DEPT dd, DA_TROUBLE dt where" + " dd.TROUBLE_ID=dt.ID  " + " and dd.IS_DELETED=0 ";
		if (userDetail.getFirstArea() != null) {
			sql += " and dt.first_area=" + userDetail.getFirstArea();
		}
		if (userDetail.getSecondArea() != null) {
			sql += " and dt.second_area=" + userDetail.getSecondArea();
		}
		if (userDetail.getThirdArea() != null) {
			sql += " and dt.third_area=" + userDetail.getThirdArea();
		}
		sql += " and dt.IS_DELETED=0 " + " and dd.FIND_DEPT_TRADE= '" + st.getRemark() + "' " + " and dd.DEPT_ID is null and dd.IS_RESULT=" + result + " and dd.TROUBLE_COPY_TYPE= " + dept.getTroubleCopyType() + "" + " and " + sqlType + " and rownum < " + pagination.getPageSize() * (pagination.getItemCount() + 1) + " )" + " where rn  >=" + (pagination.getItemCount()) * pagination.getPageSize();
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DaDept tmpDept = new DaDept();
				tmpDept.setId(res.getLong(1));
				tmpDept.setFindDeptName(res.getString(2));
				tmpDept.setMostlyCompany(res.getString(3));
				tmpDept.setSubmitTime(res.getDate(4));
				tmpDept.setBack(res.getBoolean(5));
				tmpDept.setResult(res.getBoolean(6));
				DaTrouble dt = new DaTrouble();
				dt.setTroubleCompanyName(res.getString(7));
				dt.setTroubleNo(res.getString(8));
				tmpDept.setDaTrouble(dt);
				tmpDept.setType(String.valueOf(dept.getTroubleCopyType()));
				depts.add(tmpDept);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	public List<DaDept> loadTroubleUpByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaDept> depts = new ArrayList<DaDept>();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " dt.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		String sql = "select * from " + "( select " + " dd.id,dd.find_dept_name,dd.mostly_company,dd.submit_time,dd.is_back,dd.is_result," + " dt.trouble_company_name,dt.trouble_no,rownum rn " + " from DA_DEPT dd, DA_TROUBLE dt where" + " dd.TROUBLE_ID=dt.ID  " + " and dd.IS_DELETED=0 ";
		if (userDetail.getFirstArea() != null) {
			sql += " and dt.first_area=" + userDetail.getFirstArea();
		}
		if (userDetail.getSecondArea() != null) {
			sql += " and dt.second_area=" + userDetail.getSecondArea();
		}
		if (userDetail.getThirdArea() != null) {
			sql += " and dt.third_area=" + userDetail.getThirdArea();
		}
		sql += " and dt.IS_DELETED=0 " +
		// " and dd.FIND_DEPT_TRADE= '" + st.getRemark() + "' " +
				" and dd.DEPT_ID is null " + // " and dd.TROUBLE_COPY_TYPE= " +
												// dept.getTroubleCopyType()+
												// "" +
				" and (dd.mostly_company = 'anjian' or dd.copy_company like '%anjian%') " + " and " + sqlType + " and rownum < " + pagination.getPageSize() * (pagination.getItemCount() + 1) + " )" + " where rn  >=" + (pagination.getItemCount()) * pagination.getPageSize();
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DaDept tmpDept = new DaDept();
				tmpDept.setId(res.getLong(1));
				tmpDept.setFindDeptName(res.getString(2));
				tmpDept.setMostlyCompany(res.getString(3));
				tmpDept.setSubmitTime(res.getDate(4));
				tmpDept.setBack(res.getBoolean(5));
				tmpDept.setResult(res.getBoolean(6));
				DaTrouble dt = new DaTrouble();
				dt.setTroubleCompanyName(res.getString(7));
				dt.setTroubleNo(res.getString(8));
				tmpDept.setDaTrouble(dt);
				tmpDept.setType(String.valueOf(dept.getTroubleCopyType()));
				depts.add(tmpDept);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	public List<DaDept> loadTroubleUpNoBackByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaDept> depts = new ArrayList<DaDept>();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " dt.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		int back;
		if (dept.getBack())
			back = 1;
		else
			back = 0;
		String sql = "select * from " + "( select " + " dd.id,dd.find_dept_name,dd.mostly_company,dd.submit_time,dd.is_back,dd.is_result," + " dt.trouble_company_name,dt.trouble_no,rownum rn " + " from DA_DEPT dd, DA_TROUBLE dt where" + " dd.TROUBLE_ID=dt.ID  " + " and dd.IS_DELETED=0 ";
		if (userDetail.getFirstArea() != null) {
			sql += " and dt.first_area=" + userDetail.getFirstArea();
		}
		if (userDetail.getSecondArea() != null) {
			sql += " and dt.second_area=" + userDetail.getSecondArea();
		}
		if (userDetail.getThirdArea() != null) {
			sql += " and dt.third_area=" + userDetail.getThirdArea();
		}
		sql += " and dt.IS_DELETED=0 " +
		// " and dd.FIND_DEPT_TRADE= '" + st.getRemark() + "' " +
				" and dd.DEPT_ID is null and dd.IS_BACK=" + back + // " and dd.TROUBLE_COPY_TYPE= "
																	// +
																	// dept.getTroubleCopyType()+
																	// "" +
				" and (dd.mostly_company = 'anjian' or dd.copy_company like '%anjian%') " + " and " + sqlType + " and rownum < " + pagination.getPageSize() * (pagination.getItemCount() + 1) + " )" + " where rn  >=" + (pagination.getItemCount()) * pagination.getPageSize();
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DaDept tmpDept = new DaDept();
				tmpDept.setId(res.getLong(1));
				tmpDept.setFindDeptName(res.getString(2));
				tmpDept.setMostlyCompany(res.getString(3));
				tmpDept.setSubmitTime(res.getDate(4));
				tmpDept.setBack(res.getBoolean(5));
				tmpDept.setResult(res.getBoolean(6));
				DaTrouble dt = new DaTrouble();
				dt.setTroubleCompanyName(res.getString(7));
				dt.setTroubleNo(res.getString(8));
				tmpDept.setDaTrouble(dt);
				tmpDept.setType(String.valueOf(dept.getTroubleCopyType()));
				depts.add(tmpDept);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	public List<DaDept> loadTroubleUpNoResultByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaDept> depts = new ArrayList<DaDept>();
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " dt.create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		int result;
		if (dept.getResult())
			result = 1;
		else
			result = 0;
		String sql = "select * from " + "( select " + " dd.id,dd.find_dept_name,dd.mostly_company,dd.submit_time,dd.is_back,dd.is_result," + " dt.trouble_company_name,dt.trouble_no,rownum rn " + " from DA_DEPT dd, DA_TROUBLE dt where" + " dd.TROUBLE_ID=dt.ID  " + " and dd.IS_DELETED=0 ";
		if (userDetail.getFirstArea() != null) {
			sql += " and dt.first_area=" + userDetail.getFirstArea();
		}
		if (userDetail.getSecondArea() != null) {
			sql += " and dt.second_area=" + userDetail.getSecondArea();
		}
		if (userDetail.getThirdArea() != null) {
			sql += " and dt.third_area=" + userDetail.getThirdArea();
		}
		sql += " and dt.IS_DELETED=0 " +
		// " and dd.FIND_DEPT_TRADE= '" + st.getRemark() + "' " +
				" and dd.DEPT_ID is null and dd.is_result=" + result + // " and dd.TROUBLE_COPY_TYPE= "
																		// +
																		// dept.getTroubleCopyType()+
																		// "" +
				" and (dd.mostly_company = 'anjian' or dd.copy_company like '%anjian%') " + " and " + sqlType + " and rownum < " + pagination.getPageSize() * (pagination.getItemCount() + 1) + " )" + " where rn  >=" + (pagination.getItemCount()) * pagination.getPageSize();
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DaDept tmpDept = new DaDept();
				tmpDept.setId(res.getLong(1));
				tmpDept.setFindDeptName(res.getString(2));
				tmpDept.setMostlyCompany(res.getString(3));
				tmpDept.setSubmitTime(res.getDate(4));
				tmpDept.setBack(res.getBoolean(5));
				tmpDept.setResult(res.getBoolean(6));
				DaTrouble dt = new DaTrouble();
				dt.setTroubleCompanyName(res.getString(7));
				dt.setTroubleNo(res.getString(8));
				tmpDept.setDaTrouble(dt);
				tmpDept.setType(String.valueOf(dept.getTroubleCopyType()));
				depts.add(tmpDept);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	public List<DaCompany> loadNomalDangerByIndustryList(FkArea area, Statistic statistic, Pagination pagination) throws ApplicationAccessException {
		List<DaCompany> companys = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
			}
		}
		if (statistic != null) {
			StringBuilder sql = new StringBuilder();
			int nextYear = statistic.getYear() + 1;
			sql.append(" this_.id in (select par_da_com_id from da_normal_danger where is_deleted=0 and is_danger = 1 " + " and create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd') ");
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
				sql.append(" and is_repaired = 0 )");
			} else {
				sql.append("  )");
			}
			if (sql.toString() != null && !"".equals(sql.toString())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql.toString()));
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + statistic.getIndustryId() + ")"));
		}
		// add by huangjl
		Calendar nowCalendar = Calendar.getInstance();
		// add by huangjl
		int nowY = nowCalendar.get(Calendar.YEAR);
		int newM = nowCalendar.get(Calendar.MONTH) + 1;
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time<to_date('" + nowY + "-" + newM + "-1','yyyy-MM-dd') "));

		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		for (DaCompany com : companys) {
			List<DaNomalDanger> normalDangers = loadNomalDangersOfNew(com, statistic);
			if (normalDangers != null)
				com.setNotRepairCount(normalDangers.size());
			else
				com.setNotRepairCount(0);
		}
		return companys;

	}

	private List<DaNomalDanger> loadNomalDangersOfNew(DaCompany company, Statistic statistic) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		int nextYear = statistic.getYear() + 1;
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", company.getId()));
		return nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
	}

	public List<DaNomalDanger> loadNomalDangersOfStatistic(Statistic statistic, Pagination pagination) throws ApplicationAccessException {
		List<DaNomalDanger> normalDangers = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaNomalDanger.class, "dnd");
		int nextYear = statistic.getYear() + 1;
		if (statistic != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.companyPassId", statistic.getCompanyId()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim()) && "0".equals(statistic.getIsGorver().trim())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.repaired", false));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') " + " and to_date('" + nextYear + "-01-01','yyyy-MM-dd')"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dnd.id"));
		normalDangers = nomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, pagination);
		return normalDangers;
	}

	public List<Statistic> loadDangerByIndustry(FkArea area, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		area = loadArea(area.getAreaCode());
		int areaRate = area.getGradePath().split("/").length - 1;
		String areaType = null;
		String fatherId = null;
		if (330218000000l == area.getAreaCode() || 330219000000l == area.getAreaCode() || 330215000000l == area.getAreaCode()) {
			areaType = "second_area";
			fatherId = "id";
		} else {
			areaType = areaRate == 4 ? "third_area" : "second_area";
			fatherId = "father_id";
		}
		String sqlType = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			sqlType = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());

		// add by huangjl
		int nowY = cal.get(Calendar.YEAR);
		int newM = cal.get(Calendar.MONTH) + 1;
		// 查询过滤掉未定义的行业
		String undefinedSql = " and (code is null or  code  not like '%_undefined') ";
		// add by huangjl
		String companySql = " and dc.create_time<to_date('" + nowY + "-" + newM + "-01', 'yyyy-MM-dd') ";

		String sql = " select count(distinct(dd.id)) inhere,count(distinct(ddg.par_da_dan_id)) num,map.area_code,map.dip_id,map.name, " + " (select count(1) from da_industry_parameter where is_deleted=0 " + undefinedSql + " and grade_path like  " + " (select grade_path||'%' from da_industry_parameter where id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonCount,  " + " (select 1 from da_industry_parameter where rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in (map.dip_id)) sonNumber1,  " + " (select 1 from da_industry_parameter where rownum=1 and is_deleted=0 " + undefinedSql + " and par_da_ind_id in  " + " (select id from  da_industry_parameter where par_da_ind_id=map.dip_id and is_deleted=0 " + undefinedSql + ")) sonNumber2,count(distinct(dd_f.id)) " + " from (select fa.area_code,dip.id dip_id,dip.grade_path,dip.name from fk_area fa  " + " full join (select id,name,grade_path from da_industry_parameter where is_deleted=0 " + undefinedSql + " and  "
				+ " grade_path like (select grade_path||'%' from da_industry_parameter where depiction = '" + st.getRemark() + "' and type=1 " + undefinedSql + ")) dip on 1!=2 " + " where fa.is_deleted=0 and fa." + fatherId + " = (select id from fk_area where  area_code= " + area.getAreaCode() + ")) map  " + " left join da_company_industry_rel dcir on dcir.par_da_ind_id = map.dip_id " + " left join (select dc.id,dc.second_area,dc.third_area from da_company dc left join da_company_pass dcp   " + " on dcp.par_da_com_id = dc.id  where dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + companySql + ")  da  " + " on da." + areaType + " = map.area_code and da.id = dcir.par_da_com_id  "

				+ " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlType + ") dd on dd.par_da_com_id = da.id  left join (select id,par_da_dan_id " + " from da_danger_gorver where is_deleted=0  " + sqlType + ") ddg on ddg.par_da_dan_id = dd.id   " + " left join (select id from da_danger where is_deleted=0 and finish_date < to_date" + " ('" + mDateTime + "','yyyy-MM-dd')  and id not in (select par_da_dan_id from da_danger_gorver where " + " is_deleted=0) " + sqlType + " ) dd_f on dd_f.id = dd.id  "

				+ " group by map.area_code,map.dip_id,map.name,map.grade_path order by map.area_code,map.grade_path";
//		 System.out.println("sql------" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setInhere(res.getInt(1));
				statistic.setNumber(res.getInt(2));
				statistic.setAreaCode(res.getLong(3));
				statistic.setIndustryId(res.getInt(4));
				statistic.setEnumName(res.getString(5));
				statistic.setSonCount(res.getInt(6));
				statistic.setSonNumber1(res.getInt(7));
				statistic.setSonNumber2(res.getInt(8));
				statistic.setZero(res.getInt(9));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	public List<DaDanger> loadDangerByIndustryList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDanger.class, "dd");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp").createCriteria("daCompany", "da");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		if (area != null && area.getAreaCode() != 0L) {
			area = loadArea(area.getAreaCode());
			int areaRate = area.getGradePath().split("/").length - 1;
			if (areaRate == 4) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.secondArea", area.getAreaCode()));
			} else if (areaRate == 5) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.thirdArea", area.getAreaCode()));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("da.firstArea", area.getAreaCode()));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("da2_.SECOND_AREA in (select a.area_code from fk_area a where a.father_id=" + area.getId() + ")"));
			}
		}
		if (statistic != null) {
			String sqlParam = "";
			int nextYear = statistic.getYear() + 1;
			sqlParam = " this_.create_time between to_date('" + statistic.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-04-01','yyyy-MM-dd')";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sqlParam));
			if (statistic.getIsGorver() != null && !"".equals(statistic.getIsGorver().trim())) {
				if ("0".equals(statistic.getIsGorver().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)"));
				} else if ("2".equals(statistic.getIsGorver().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date < to_date('" + mDateTime + "','yyyy-MM-dd') and this_.id not in (select par_da_dan_id from da_danger_gorver where is_deleted=0)"));
				}
			}
		}
		// add by huangjl
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.add(Calendar.MONTH, 1);
		// add by huangjl
		int nowY = nowCalendar.get(Calendar.YEAR);
		int newM = nowCalendar.get(Calendar.MONTH) + 1;
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time<to_date('" + nowY + "-" + newM + "-1','yyyy-MM-dd') "));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + statistic.getIndustryId() + ")"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("da.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));

		return dangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}

	public List<DaIndustryParameter> loadIndustrysForDanger() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id = (select id from da_industry_parameter where depiction = '" + Nbyhpc.DANGER_TYPE + "')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.TROUBLE_TYPE));
		// detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public DaDanger loadDangerOfStatistic(DaDanger danger) throws ApplicationAccessException {
		return dangerPersistenceIface.loadDanger(danger);
	}

	public List<DaIndustryParameter> loadCompanyTypes() {
		List<DaIndustryParameter> list = new ArrayList<DaIndustryParameter>();
		String sql = "select id,name from da_industry_parameter " + "where par_da_ind_id is null and is_deleted = 0 and type = 1";
		try {
			ResultSet res = companyPersistenceIface.findBySql(sql);
			while (res.next()) {
				DaIndustryParameter dp = new DaIndustryParameter();
				dp.setId(res.getLong(1));
				dp.setName(res.getString(2));
				list.add(dp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 生产经营性单位数按区域和行业类型统计（考虑到企业有存在选择多个不同的行业的情况,合计和其他需要重新统计，保持页面统计数据与查看详情一致）
	 * 
	 * @return
	 */
	public Map<String, Object> loadCompaniesByAreaAndType(List<FkArea> areas, FkArea area, String areaLevel, List<DaIndustryParameter> types) {
		Map<String, Object> oMap = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> tdataMap = new HashMap<String, Object>();
		areaLevel = areaLevel.equals("firstArea") ? "second_area" : "third_area";
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select area_code, type, c_num, type_name from ( ");
			sql.append("    select a.area_code as area_code, 'alltype' as type, '合计' as type_name, sum(dc.c_num) as c_num from ( ");
			sql.append("          select id, area_code from fk_area ");
			sql.append("          where is_deleted=0 and father_id = (select id from fk_area where area_code='" + area.getAreaCode() + "') or area_code='" + area.getAreaCode() + "' ");
			sql.append("    ) a left join ( ");
			sql.append("          select count(c.id) as c_num, c.area_code from ( ");
			sql.append("             select t.id, case when (t." + areaLevel + " is null or t." + areaLevel + "=0) then " + area.getAreaCode() + " else t." + areaLevel + " end as area_code from da_company t where t.is_deleted=0 ");
			sql.append(" " + (areaLevel.equals("third_area") ? "  and t.second_area=" + area.getAreaCode() + " " : "") + " ");
			sql.append("          ) c  group by c.area_code ");
			sql.append("    ) dc on a.area_code = dc.area_code group by a.area_code ");

			sql.append("    union ");

			sql.append("select a.area_code as area_code, 'qit' as type, '其他' as type_name, sum(dc.c_num) as c_num from ( ");
			sql.append("         select id, area_code from fk_area ");
			sql.append("         where is_deleted=0 and father_id = (select id from fk_area where area_code='" + area.getAreaCode() + "') or area_code='" + area.getAreaCode() + "' ");
			sql.append(" ) a left join ( ");
			sql.append("   select count(c.id) as c_num, c.area_code from ( ");
			sql.append("         select t.id, case when (t." + areaLevel + " is null or t." + areaLevel + "=0) then " + area.getAreaCode() + " else ");
			sql.append("         t." + areaLevel + " end as area_code from da_company t ");
			sql.append("         where t.is_deleted=0 " + (areaLevel.equals("third_area") ? "  and t.second_area=" + area.getAreaCode() + " " : "") + " and t.id not in ( ");
			sql.append("            select c.id from (select t.id from da_company t where t.is_deleted=0) c ");
			sql.append("            left join da_company_industry_rel i on c.id=i.par_da_com_id where ");
			sql.append("            i.par_da_ind_id in (select id from da_industry_parameter where par_da_ind_id is null and is_deleted = 0 and type = 1) ");
			sql.append("         ) ");
			sql.append("    ) c  group by c.area_code ");
			sql.append(") dc on a.area_code = dc.area_code group by a.area_code ");

			sql.append("    union ");

			sql.append("    select a.area_code, to_char(dc.par_da_ind_id) as type, (select name from da_industry_parameter where id=dc.par_da_ind_id) as type_name, dc.c_num as c_num  from ( ");
			sql.append("          select id, area_code from fk_area ");
			sql.append("          where is_deleted=0 and father_id = (select id from fk_area where area_code='" + area.getAreaCode() + "') or area_code='" + area.getAreaCode() + "' ");
			sql.append("    ) a left join ( ");
			sql.append("          select count(c.id) as c_num, c.area_code, i.par_da_ind_id from ( ");
			sql.append("             select t.id, case when (t." + areaLevel + " is null or t." + areaLevel + "=0) then " + area.getAreaCode() + " else t." + areaLevel + " end as area_code from da_company t where t.is_deleted=0 ");
			sql.append(" " + (areaLevel.equals("third_area") ? "  and t.second_area=" + area.getAreaCode() + " " : "") + " ");
			sql.append("          ) c ");
			sql.append("          left join da_company_industry_rel i on c.id=i.par_da_com_id where ");
			sql.append("          i.par_da_ind_id in (select id from da_industry_parameter where par_da_ind_id is null and is_deleted = 0 and type = 1) ");
			sql.append("          group by c.area_code, i.par_da_ind_id ");
			sql.append("    ) dc on a.area_code = dc.area_code ");
			sql.append(") order by type, area_code ");
			// System.out.println("生产经营性单位数按区域和行业类型统计sql=" + sql.toString());
			ResultSet res = companyPersistenceIface.findBySql(sql.toString());
			int i = 1;
			int row_sum = 0;
			String type = "";
			Map<String, Object> map = null;
			while (res.next()) {
				if (!type.equals((res.getString(2) != null ? res.getString(2) : "null"))) {
					if (i != 1) {
						map.put("row_sum", row_sum);
						tdataMap.put(type, map);
					}
					i++;
					row_sum = 0;
					type = res.getString(2) != null ? res.getString(2) : "null";
					map = new HashMap<String, Object>();
					map.put("type", type);
					map.put("typeName", res.getString(4));
				}
				row_sum += res.getInt(3);
				map.put("area_" + res.getLong(1), res.getInt(3));
			}
			res.close();
			map.put("row_sum", row_sum);
			tdataMap.put(type, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String key = "";
		Map<String, Object> map = null;
		for (DaIndustryParameter t : types) {
			key = "" + t.getId();
			if (tdataMap != null && null != tdataMap.get(key)) {
				listMap.add((Map<String, Object>) tdataMap.get(key));
			} else {
				map = new HashMap<String, Object>();
				map.put("type", key);
				map.put("typeName", t.getName());
				listMap.add(map);
			}
		}
		listMap.add((Map<String, Object>) tdataMap.get("qit"));
		listMap.add((Map<String, Object>) tdataMap.get("alltype"));
		oMap.put("mapList", listMap);
		return oMap;
	}

	/**
	 * 生成经营性单位详情查询
	 */
	public List<DaCompany> loadCompanysForColligationShowDetails(DaCompany company, String type, String areaLevel, FkArea area, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		List<DaCompany> companys;
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
		// Nbyhpc.AREA_CODE));
		if (areaLevel != null && !areaLevel.equals("")) {
			if ("firstArea".equals(areaLevel)) {
				// detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
				// company.getFirstArea()));
				// detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
				// Nbyhpc.AREA_CODE));
			}
			if ("secondArea".equals(areaLevel)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", area.getAreaCode()));
			}
			if ("thirdArea".equals(areaLevel)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", area.getAreaCode()));
			}
		} else {
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
			// Nbyhpc.AREA_CODE));
		}
		if (null != company) {
			if (null != company.getCompanyName()) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName(), MatchMode.ANYWHERE));
			}
		}
		if (type != null && !type.equals("") && !type.equals("alltype")) {
			if (type.equals("qit")) {
				String sql = "this_.id not in ( " + "select c.id " + "from (select t.id " + "from da_company t where t.is_deleted=0) c " + "left join da_company_industry_rel i on c.id=i.par_da_com_id where " + "i.par_da_ind_id in (select id from da_industry_parameter where par_da_ind_id is null and is_deleted = 0 and type = 1)" + ")";
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql));
			} else {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select di.par_da_com_id  from  da_company_industry_rel di where  di.par_da_ind_id = " + type + " ) "));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		return companys;
	}

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}

	@SuppressWarnings("deprecation")
	private void initXMLURL() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRealPath("/");
		XMLURL = url + "resources\\default\\xml\\area_map.xml";
	}

	private synchronized void loadAreasFromXML() {
		areaCodeList = new ArrayList<Long>();
		areaCodeZhjgList = new ArrayList<Long>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			// 从XMLURL的地址中读取XML
			initXMLURL();
			Document doc = db.parse(new File(XMLURL));
			Element elmtInfo = doc.getDocumentElement();
			NodeList nodes = elmtInfo.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node result = nodes.item(i);
				// 判断节点是否是area
				if (result.getNodeType() == Node.ELEMENT_NODE && result.getNodeName().equals("AREA")) {
					String area_code = null;
					String area_code_zhjg = null;
					NodeList ns = result.getChildNodes();
					for (int j = 0; j < ns.getLength(); j++) {
						Node record = ns.item(j);
						// area_code作为value
						// area_code_zhjg作为key
						if (record.getNodeType() == Node.ELEMENT_NODE && record.getNodeName().toLowerCase().equals("area_code_zhjg")) {
							area_code_zhjg = record.getTextContent();
						}
						if (record.getNodeType() == Node.ELEMENT_NODE && record.getNodeName().toLowerCase().equals("area_code")) {
							area_code = record.getTextContent();
						}
						if (area_code != null && area_code_zhjg != null) {
							areaCodeList.add(new Long(area_code));
							areaCodeZhjgList.add(new Long(area_code_zhjg));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询区域集合(查询历史数据 )
	 * 
	 * @author:liulj
	 * @time:2013-11-5
	 */
	public List<FkArea> loadAreas_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		int backup_date = 0; // 历史表查询的日期
		if (st != null) {

			if (st.getMonth() != 0) { // 月报
				// fk_area = "fk_area_his";
				// int month = cal.get(Calendar.MONTH);
				//
				// if (st.getYear() < 2013 || (st.getYear() == 2013 &&
				// st.getMonth()
				// <= 11 && st.getMonth() >= 1)) { // 往月
				//
				// backup_date = 201311;
				// } else {
				// if (st.getMonth() < 10) {
				// backup_date = Integer.parseInt(st.getYear() + "0" +
				// st.getMonth());
				// } else {
				// backup_date = Integer.parseInt(st.getYear() + "" +
				// st.getMonth());
				// }
				// }

				// 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (st.getYear() != year || current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																					// ,则取历史表
					// 2013_11开始备份

					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																			// 2013.11当年取动态
							fk_area = "fk_area_his";
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																					// 2013(包括)之后取那年的12月份
																					// yyyy-12
							fk_area = "fk_area_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度

						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
							fk_area = "fk_area_his"; // 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) {
							fk_area = "fk_area_his";
							if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");

							}
						} else if (st.getQuarter() < current_quarter) {
							// System.out.println("年份相等, 月份小于当前月份");

							fk_area = "fk_area_his";
							if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");

							}

						}
					}
				}
			}
		}
		// System.out.println("area_backup_date: " + backup_date);

		DetachedCriteriaProxy detachedCriteriaProxy = null;

		if (fk_area.equals("fk_area")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
			return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		} else if (fk_area.equals("fk_area_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkAreaHis.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("backupDate", backup_date));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area_his where backup_date=" + backup_date + "  and area_code=" + areaCode + ")"));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
			return (List<FkArea>) fkAreaHisPersistenceIface.loadAreas(detachedCriteriaProxy);
		}

		// detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class,
		// "fa");
		// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code="
		// + areaCode + ")"));
		// detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		return null;
	}

	/**
	 * 查询区域对象(加入查询历史数据)
	 * 
	 * @author:liulj
	 * @time:2013-12-5
	 */
	public FkArea loadArea_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		int backup_date = 0; // 历史表查询的日期
		if (st != null) {
			if (st.getMonth() != 0) { // 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {
				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (st.getYear() != year || current_quarter != st.getQuarter()) { // 当前时间与查询时不一致
																					// ,则取历史表
					// 2013_11开始备份

					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																			// 2013.11当年取动态
							fk_area = "fk_area_his";
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																					// 2013(包括)之后取那年的12月份
																					// yyyy-12
							fk_area = "fk_area_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度
						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
							fk_area = "fk_area_his"; // 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) {
							fk_area = "fk_area_his";
							if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");

							}

						} else if (st.getQuarter() < current_quarter) {
							// System.out.println("年份相等, 月份小于当前月份");

							fk_area = "fk_area_his";
							if (st.getQuarter() == 4) {
								backup_date = Integer.parseInt(st.getYear() + "12");

							} else if (st.getQuarter() == 3) {
								backup_date = Integer.parseInt(st.getYear() + "09");

							} else if (st.getQuarter() == 2) {
								backup_date = Integer.parseInt(st.getYear() + "06");

							} else if (st.getQuarter() == 1) {
								backup_date = Integer.parseInt(st.getYear() + "03");

							}
						}
					}
				}

			}
		}

		DetachedCriteriaProxy detachedCriteriaProxy = null;
		List<FkArea> areas = null;
		List<FkAreaHis> areas_his = null;
		if (fk_area.equals("fk_area")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
			areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		} else if (fk_area.equals("fk_area_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkAreaHis.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("backupDate", backup_date));
			detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
			areas_his = fkAreaHisPersistenceIface.loadAreas(detachedCriteriaProxy);
		}
		if (areas_his != null && areas_his.size() == 1) {
			FkArea area = new FkArea();
			area.setId(areas_his.get(0).getId());
			area.setAreaName(areas_his.get(0).getAreaName());
			area.setAreaCode(areas_his.get(0).getAreaCode());
			area.setFatherId(areas_his.get(0).getFatherId());
			area.setGradePath(areas_his.get(0).getGradePath());
			area.setGroupNum(areas_his.get(0).getGroupNum());

			return area;
		}
		if (areas != null && areas.size() == 1) {
			return areas.get(0);
		}
		return null;
	}

	/**
	 * 查询区域对象(加入查询历史数据)
	 * 
	 */
	public FkArea loadArea_his(Long areaCode, int backupDate) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkAreaHis.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("backupDate", backupDate));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
		List<FkAreaHis> areas_his = fkAreaHisPersistenceIface.loadAreas(detachedCriteriaProxy);
		if (areas_his != null && areas_his.size() == 1) {
			FkArea area = new FkArea();
			area.setId(areas_his.get(0).getId());
			area.setAreaName(areas_his.get(0).getAreaName());
			area.setAreaCode(areas_his.get(0).getAreaCode());
			area.setFatherId(areas_his.get(0).getFatherId());
			area.setGradePath(areas_his.get(0).getGradePath());
			area.setGroupNum(areas_his.get(0).getGroupNum());
			return area;
		}
		return null;
	}
	
	/**
	 * 查询区域集合给首页统计用(加入查询历史数据)
	 * 
	 * liulj
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas1_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		int backup_date = 0; // 历史表查询的日期
		if (st != null) {
			if (st.getMonth() != 0) { // 月报

				// 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					if (st.getMonth() < 10) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}
				}

			}

			// else {
			// // liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
			// if (st.getYear() != year || current_quarter != st.getQuarter()) {
			// // 当前时间与查询时不一致
			// // ,则取历史表
			// // 2013_11开始备份
			//
			// if (st.getQuarter() == 0) { // 全年
			//
			// if (st.getYear() != year && st.getYear() < 2013) { //
			// 往年:2013年之前都取
			// // 2013.11当年取动态
			// fk_area = "fk_area_his";
			// backup_date = 201311;
			// } else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
			// // 2013(包括)之后取那年的12月份
			// // yyyy-12
			// fk_area = "fk_area_his";
			// backup_date = Integer.parseInt(st.getYear() + "12");
			// }
			//
			// }
			//
			// else { // 季度
			//
			// fk_area = "fk_area_his";
			// if (st.getYear() < 2013
			// || (st.getYear() == 2013
			// && st.getQuarter() <= 3 && st
			// .getQuarter() >= 1)) { // 2013年第三季度之前
			// // 未做备份,则取最早备份日期2013_11
			// backup_date = 201311;
			// } else if (st.getYear() != year && st.getYear() >= 2013) {
			//
			// if (st.getQuarter() == 4) {
			// backup_date = Integer.parseInt(st.getYear()
			// + "12");
			//
			// } else if (st.getQuarter() == 3) {
			// backup_date = Integer.parseInt(st.getYear()
			// + "09");
			//
			// } else if (st.getQuarter() == 2) {
			// backup_date = Integer.parseInt(st.getYear()
			// + "06");
			//
			// } else if (st.getQuarter() == 1) {
			// backup_date = Integer.parseInt(st.getYear()
			// + "03");
			//
			// }
			// }
			// }
			// }
			// }

			else if (st.getQuarter() != 0) { // 季报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}

					} else if (st.getYear() == 2013) {

						fk_area = "fk_area_his";

						if (st.getQuarter() == 4) {
							backup_date = 201312;
						} else {
							backup_date = 201311;
						}
					} else {
						fk_area = "fk_area_his";
						backup_date = 201311;
					}

				} else if (st.getQuarter() < current_quarter) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";

					if (st.getQuarter() == 4) {
						backup_date = Integer.parseInt(st.getYear() + "12");

					} else if (st.getQuarter() == 3) {
						backup_date = Integer.parseInt(st.getYear() + "09");

					} else if (st.getQuarter() == 2) {
						backup_date = Integer.parseInt(st.getYear() + "06");

					} else if (st.getQuarter() == 1) {
						backup_date = Integer.parseInt(st.getYear() + "03");

					}

				} else {
					// System.out.println("动态表");
				}

			} else { // 年报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						backup_date = Integer.parseInt(st.getYear() + "12");

					} else if (st.getYear() == 2013) {

						fk_area = "fk_area_his";

						backup_date = 201312;
					} else {
						fk_area = "fk_area_his";
						backup_date = 201311;
					}

				} else {
					// System.out.println("动态表");
				}
			}

		}

		// System.out.println("fk_area88: " + fk_area);
		DetachedCriteriaProxy detachedCriteriaProxy = null;

		if (fk_area.equals("fk_area")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
			return areaPersistenceIface.loadAreas(detachedCriteriaProxy);

		} else if (fk_area.equals("fk_area_his")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkAreaHis.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("backupDate", backup_date));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area_his where area_code=" + areaCode + " and  BACKUP_DATE=" + backup_date + ")"));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
			fkAreaHisPersistenceIface.loadAreas(detachedCriteriaProxy);
			return fkAreaHisPersistenceIface.loadAreas(detachedCriteriaProxy);
		}

		return null;
	}

	/**
	 * 查询区域集合给首页统计用(工程项目的区域分组，特别注意这里不能随便修改，如果要修改，必须要验证修改后的工程项目的统计是否正确) 历史数据
	 * 
	 * @author:liulj
	 * @time:2013-11-5
	 */
	public List<FkArea> loadAreasByGroupNum_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String fk_area = "fk_area";
		int backup_date = 0; // 历史表查询的日期
		if (st != null) {

			if (st.getMonth() != 0) { // 月报
				// fk_area = "fk_area_his";
				// int month = cal.get(Calendar.MONTH);
				//
				// if (st.getYear() < 2013
				// || (st.getYear() == 2013 && st.getMonth() <= 11 && st
				// .getMonth() >= 1)) { // 往月
				//
				// backup_date = 201311;
				// } else {
				// backup_date = Integer.parseInt(st.getYear() + ""
				// + st.getMonth());
				// }

				// 月报

				if (st.getYear() != year) {

					if (st.getYear() > year) {

						// System.out.println("动态表");

					} else if (st.getYear() > 2013) {
						// System.out.println("年份大于2013,不等于当前年份");
						fk_area = "fk_area_his";
						if (st.getMonth() <= 9) {
							backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
						} else {
							backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
						}

					} else if (st.getYear() == 2013) {

						if (st.getMonth() > 11) {
							fk_area = "fk_area_his";
							backup_date = 201312;
						} else {

							fk_area = "fk_area_his";
							backup_date = 201311;

						}
					} else {
						fk_area = "fk_area_his";
						backup_date = 201311;
					}

				} else if (st.getMonth() < month) {
					// System.out.println("年份相等, 月份小于当前月份");

					fk_area = "fk_area_his";

					if (st.getMonth() <= 9) {
						backup_date = Integer.parseInt(st.getYear() + "0" + st.getMonth());
					} else {
						backup_date = Integer.parseInt(st.getYear() + "" + st.getMonth());
					}

				} else {
					// System.out.println("年份相等, 月份大于等于当前月份");
					// System.out.println("动态表");

				}

			} else {

				// liulj 增加报表固化 当前时间与查询时间对比 区分查动态表还是历史表
				if (current_quarter != st.getQuarter()) { // 当前时间与查询时不一致 ,则取历史表
															// 2013_11开始备份

					if (st.getQuarter() == 0) { // 全年

						if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
																			// 2013.11当年取动态
							fk_area = "fk_area_his";
							backup_date = 201311;
						} else if (st.getYear() != year && st.getYear() >= 2013) { // 往年
																					// 2013(包括)之后取那年的12月份
																					// yyyy-12
							fk_area = "fk_area_his";
							backup_date = Integer.parseInt(st.getYear() + "12");
						}

					} else { // 季度

						fk_area = "fk_area_his";
						if (st.getYear() < 2013 || (st.getYear() == 2013 && st.getQuarter() <= 3 && st.getQuarter() >= 1)) { // 2013年第三季度之前
																																// 未做备份,则取最早备份日期2013_11
							backup_date = 201311;
						} else if (st.getQuarter() == 4) {
							backup_date = Integer.parseInt(st.getYear() + "12");

						} else if (st.getQuarter() == 3) {
							backup_date = Integer.parseInt(st.getYear() + "09");

						} else if (st.getQuarter() == 2) {
							backup_date = Integer.parseInt(st.getYear() + "06");

						} else if (st.getQuarter() == 1) {
							backup_date = Integer.parseInt(st.getYear() + "03");

						}
					}
				}
			}
		}

		DetachedCriteriaProxy detachedCriteriaProxy = null;

		if (fk_area.equals("fk_area")) {
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ") order by group_num,sort_num"));
			return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		} else if (fk_area.equals("fk_area_his")) {

			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkAreaHis.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("backupDate", backup_date));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area_his where backup_date=" + backup_date + " and area_code=" + areaCode + ") order by group_num,sort_num"));
			return (List<FkArea>) fkAreaHisPersistenceIface.loadAreas(detachedCriteriaProxy);
		}
		return null;
	}

	public Statistic loadReportByCompanyId(Long companyId, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-01-01";
				}
			}
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		if (mDateTime.compareTo(endDate) > 0)
			mDateTime = endDate;
		String sqlType = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByCom = " and dc.create_time <= to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByDangerGorver = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sql = " select map.company_name,map.reg_address,sum(map.dnd),sum(map.dndg),sum(map.dd)," + " sum(map.ddg),sum(map.target) target,sum(map.goods) goods,sum(map.resources) resources," + " sum(map.finish) finish,sum(map.method) method,sum(map.money) money, map.second_areaName, " + " map.third_areaName,map.fact_name,map.user_phone,map.user_mobile, map.id  from (" + " select dc.company_name,dc.reg_address,count(distinct(dnd.id)) dnd,count(distinct" + " (dndg.id)) dndg,0 dd, 0 ddg,0 target,0 goods,0 resources, 0 finish,0 method,0 money, " + " (select fa.area_name from fk_area fa where fa.area_code = dc.second_area and fa.area_code != 0) second_areaName," + " (select fa.area_name from fk_area fa where fa.area_code = dc.third_area and fa.area_code != 0) third_areaName," + " u.fact_name,u.user_phone,u.user_mobile, dc.id  from da_company dc left join da_company_pass dcp " + " on dcp.par_da_com_id = dc.id left join fk_user_info u on u.id = dcp.com_user_id"
				+ " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and is_danger=1 "
				+ sqlType
				+ ") dnd on dnd.par_da_com_id = dcp.par_da_com_id "
				+ " left join (select id from da_normal_danger where is_deleted=0  and is_danger=1 "
				+ sqlType
				+ " and (is_repaired=1 or is_danger=0)) dndg on dndg.id=dnd.id  "
				+ " where dcp.par_da_com_id="
				+ companyId
				+ " and dcp.is_affirm=1 and u.is_deleted=0 and dcp.is_deleted=0 and dc.is_deleted=0 "
				+ sqlTypeByCom
				+ " group by dc.id,dc.company_name,dc.reg_address,dc.second_area,dc.third_area,u.fact_name,u.user_phone,u.user_mobile"

				+ " union"
				+ " select dc.company_name,dc.reg_address,0 dnd,0 dndg,count(distinct(dd.id)) dd,"
				+ " count(distinct(ddg.id)) ddg,sum(ddg.target) target,sum(ddg.goods) "
				+ " goods,sum(ddg.resources) resources, count(ddg.finish_date) finish,sum(ddg.safety_method) "
				+ " method,sum(ddg.govern_money) money, (select fa.area_name from fk_area fa where "
				+ " fa.area_code = dc.second_area and fa.area_code != 0) second_areaName, (select fa.area_name from fk_area fa where "
				+ " fa.area_code = dc.third_area and fa.area_code != 0) third_areaName,u.fact_name,u.user_phone,u.user_mobile, dc.id "
				+ " from da_company dc left join da_company_pass dcp on dcp.par_da_com_id = dc.id "
				+ " left join fk_user_info u on u.id = dcp.com_user_id"
				+ " left join (select distinct id,par_da_com_id from da_danger where is_deleted=0 "
				+ sqlType
				+ " ) dd on dd.par_da_com_id = dcp.par_da_com_id "
				+ " left join ( select distinct d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money  "
				+ " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0  " + sqlTypeByDangerGorver + " ) g on g.par_da_dan_id=d.id where d.is_deleted=0 and g.par_da_dan_id is null and d.id is  " + " not null) ddg on ddg.id = dd.id where dcp.par_da_com_id=" + companyId + " and dcp.is_affirm=1 and u.is_deleted=0 and " + " dcp.is_deleted=0  and dc.is_deleted=0  " + sqlTypeByCom + " group by " + " dc.id,dc.company_name,dc.reg_address,dc.second_area,dc.third_area,u.fact_name,u.user_phone,u.user_mobile) map" + " group by map.company_name,map.reg_address, map.second_areaName, map.third_areaName,map.fact_name,map.user_phone,map.user_mobile, map.id ";

		// System.out.println("企业季报sql:" + sql);

		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setCompanyName(res.getString(1));
				statistic.setAddress(res.getString(2));
				statistic.setTroubNum(res.getInt(3));
				statistic.setTroubCleanNum(res.getInt(4));
				statistic.setBigTroubNum(res.getInt(5));
				statistic.setPlanBigTroubNum(res.getInt(6));
				statistic.setTarget(res.getInt(7));
				statistic.setGoods(res.getInt(8));
				statistic.setResource(res.getInt(9));
				statistic.setFinishDate(res.getInt(10));
				statistic.setSafetyMethod(res.getInt(11));
				statistic.setGovernMoney(res.getDouble(12));
				statistic.setAreaName((res.getString(13) == null ? "" : res.getString(13)) + (res.getString(14) == null ? "" : res.getString(14)));
				statistic.setLinkMan(res.getString(15));
				statistic.setLinkTel(res.getString(16));
				statistic.setLinkMobile(res.getString(17));
				statistic.setCompanyId(res.getLong(18));
				statistics.add(statistic);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (statistics != null && statistics.size() > 0) {
			return statistics.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 封装查询出的企业隐患信息(查询历史数据)
	 * 
	 * @author:liulj
	 * @time:2013-11-6
	 */
	public List<Statistic> decorationStatistic1(List<DaCompanyHis> companies, DaCompany company, int backup_date) throws ApplicationAccessException {
		String companyIdies = "";
		for (DaCompanyHis com : companies) {
			companyIdies = companyIdies + com.getId().toString() + ",";
		}

		List<Statistic> statisticDetails = new ArrayList<Statistic>();
		Statistic statistic = null;
		if (!companyIdies.equals("")) {
			String sqlParam = "";
			int year = company.getYear();
			int month = company.getMonth();
			int nextYear = year + 1;
			int nextMonth = month + 1;
			if (month != 0) {
				if (month != 12)
					sqlParam = " and t.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + year + "-" + nextMonth + "-01','yyyy-MM-dd')";
				else
					sqlParam = " and t.create_time between to_date('" + year + "-" + month + "-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			} else {
				if (company.getQuarter() == 1) {
					sqlParam = " and t.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + year + "-04-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 2) {
					sqlParam = " and t.create_time between to_date('" + year + "-04-01','yyyy-MM-dd') and to_date('" + year + "-07-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 3) {
					sqlParam = " and t.create_time between to_date('" + year + "-07-01','yyyy-MM-dd') and to_date('" + year + "-10-01','yyyy-MM-dd')";
				} else if (company.getQuarter() == 4) {
					sqlParam = " and t.create_time between to_date('" + year + "-10-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				} else {
					sqlParam = " and t.create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
				}
			}

			String sql = "select c.id,c.company_name,count(distinct dnd.id),count(distinct dd.id),count(distinct cqr.id),count(distinct dnd_1.id)+count(distinct dd_1.id),c.safety_management_person,c.safety_management_person_phone,c.address" + " from "
			// --连接企业表（统计企业数目）
					+ " (select t.id as id,t.company_name as company_name,t.safety_management_person,t.safety_management_person_phone,t.address from da_company_his t where  backup_date=" + backup_date + "  and  t.is_deleted=0 and t.id in (" + companyIdies.substring(0, companyIdies.length() - 1) + ")) c"
			// --连接一般隐患表（统计一般隐患数目）
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_normal_danger t where t.is_deleted=0" + sqlParam + ") dnd on c.id=dnd.par_da_com_id"
					// --连接重大隐患表（统计重大隐患数目）
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_danger t where t.is_deleted=0" + sqlParam + ") dd on c.id=dd.par_da_com_id"
					// --连接企业季度上报表 （查询企业是否上报）
					+ " left join (select t.id as id,t.company_id as company_id from da_company_quarter_report t where t.year='" + company.getYear() + "' and t.quarter='" + company.getQuarter() + "' ) cqr on cqr.company_id=c.id"
					// --连接一般隐患表（统计一般隐患数目）(监管部门检查发现隐患数)
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_normal_danger t left join da_company_pass_his dcp on dcp.par_da_com_id=t.par_da_com_id where dcp.backup_date=" + backup_date + "  and  t.is_deleted=0 and t.user_id !=0 and t.user_id is not null and t.user_id!=dcp.com_user_id" + sqlParam + ") dnd_1 on c.id=dnd_1.par_da_com_id"
					// --连接重大隐患表（统计重大隐患数目）(监管部门检查发现隐患数)
					+ " left join (select t.id as id,t.par_da_com_id as par_da_com_id from da_danger t left join da_company_pass_his dcp on dcp.par_da_com_id=t.par_da_com_id where dcp.backup_date=" + backup_date + "  and t.is_deleted=0 and t.user_id !=0 and t.user_id is not null and t.user_id!=dcp.com_user_id" + sqlParam + ") dd_1 on c.id=dd_1.par_da_com_id"
					// --按企业分组
					+ " group by c.id,c.company_name,c.safety_management_person,c.safety_management_person_phone,c.address";
			// System.out.println("-------sql---shun=" + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);

			try {
				while (res.next()) {
					statistic = new Statistic();
					statistic.setCompanyId(res.getLong(1));
					statistic.setCompanyName(res.getString(2));
					statistic.setAnum(res.getInt(3));// 标记一般事故隐患数
					statistic.setBnum(res.getInt(4));// 标记重大事故隐患数
					statistic.setCnum(res.getInt(5));// 标记季报统计报表报送情况的状态
					statistic.setDnum(res.getInt(6));// 标记监管部门检查发现隐患数
					statistic.setSafetyMngPerson(res.getString(7));
					statistic.setSafetyMngPersonPhone(res.getString(8));
					statistic.setAddress(res.getString(9));
					statisticDetails.add(statistic);
				}
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statisticDetails;
	}

	public void setItemDangerPersistenceIface(ItemDangerPersistenceIface itemDangerPersistenceIface) {
		this.itemDangerPersistenceIface = itemDangerPersistenceIface;
	}

	public void setItemPersistenceIface(ItemPersistenceIface itemPersistenceIface) {
		this.itemPersistenceIface = itemPersistenceIface;
	}

	public void setActualTableNoticePersistenceIface(ActualizeTableNoticePersistenceIface actualTableNoticePersistenceIface) {
		this.actualTableNoticePersistenceIface = actualTableNoticePersistenceIface;
	}

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setRollcallCompanyPersistenceIface(RollcallCompanyPersistenceIface rollcallCompanyPersistenceIface) {
		this.rollcallCompanyPersistenceIface = rollcallCompanyPersistenceIface;
	}

	public void setDangerGorverPersistenceIface(DangerGorverPersistenceIface dangerGorverPersistenceIface) {
		this.dangerGorverPersistenceIface = dangerGorverPersistenceIface;
	}

	public Danger1PersistenceIface getDanger1PersistenceIface() {
		return danger1PersistenceIface;
	}

	public void setDanger1PersistenceIface(Danger1PersistenceIface danger1PersistenceIface) {
		this.danger1PersistenceIface = danger1PersistenceIface;
	}

	public NomalDanger1PersistenceIface getNomalDanger1PersistenceIface() {
		return nomalDanger1PersistenceIface;
	}

	public void setNomalDanger1PersistenceIface(NomalDanger1PersistenceIface nomalDanger1PersistenceIface) {
		this.nomalDanger1PersistenceIface = nomalDanger1PersistenceIface;
	}

	public CompanyHisPersistenceIface getCompanyHisPersistenceIface() {
		return companyHisPersistenceIface;
	}

	public void setCompanyHisPersistenceIface(CompanyHisPersistenceIface companyHisPersistenceIface) {
		this.companyHisPersistenceIface = companyHisPersistenceIface;
	}

	public FkAreaHisPersistenceIface getFkAreaHisPersistenceIface() {
		return fkAreaHisPersistenceIface;
	}

	public void setFkAreaHisPersistenceIface(FkAreaHisPersistenceIface fkAreaHisPersistenceIface) {
		this.fkAreaHisPersistenceIface = fkAreaHisPersistenceIface;
	}

	public NomalDangerPersistenceIface getNomalDangerPersistenceIface() {
		return nomalDangerPersistenceIface;
	}

	public DangerGorverPersistenceIface getDangerGorverPersistenceIface() {
		return dangerGorverPersistenceIface;
	}

	public DangerPersistenceIface getDangerPersistenceIface() {
		return dangerPersistenceIface;
	}

	public TCachePersistenceIface getTcachePersistenceIface() {
		return tcachePersistenceIface;
	}

	public void setTcachePersistenceIface(TCachePersistenceIface tcachePersistenceIface) {
		this.tcachePersistenceIface = tcachePersistenceIface;
	}

	public PubCompanyPersistenceIface getPubCompanyPersistenceIface() {
		return pubCompanyPersistenceIface;
	}

	public void setPubCompanyPersistenceIface(PubCompanyPersistenceIface pubCompanyPersistenceIface) {
		this.pubCompanyPersistenceIface = pubCompanyPersistenceIface;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File getTempFile() {
		return tempFile;
	}

	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}

	public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
