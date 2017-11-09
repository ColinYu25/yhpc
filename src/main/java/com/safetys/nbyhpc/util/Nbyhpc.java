package com.safetys.nbyhpc.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;

/**
 * @(#) Nbyhpc.java
 * @date 2009-07-28
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public class Nbyhpc { 

	/**
	 * 中心库同步
	 */
	public static String TOKEN = "#NBYH#2013#";
	
	/**
	 * 企业行业
	 */
	public static final Integer COMPANY_TRADE = 1;// 企业行业

	public static final Integer TROUBLE_TRADE = 2;// 工程项目重大隐患类型

	public static final Integer TROUBLE_TYPE = 3;// 企业重大隐患类型

	public static final Integer THREE_ILLEGAL_TRADE = 4;// 打非类型

	public static final Integer STATISTIC_REPORT_TRADE = 5;// 统计上报行业

	public static final Integer QUARTER_REPORT_TRADE = 6;// 企业季报行业

	public static final Integer PIPE_LINE_TYPE = 11;// 企业重大隐患类型

	/**
	 * 行业部门季报行业
	 */
	public static final Integer QUARTER_REPORT_TRADE_OTHER = 7;// 行业部门季报行业

	public static final Integer QUARTER_REPORT_TYPE = 8;// 企业一般隐患类型

	public static final Integer CONSTRUCTION_PROJECT = 1;// 建委工程项目

	public static final Integer TRAFFIC_PROJECT = 2;// 交通工程项目

	public static final Integer WATER_PROJECT = 3;// 水利工程项目

	public static final Integer CITY_PROJECT = 4;// 市政工程项目

	public static final String USER_INDUSTRY_AJJ = "anwei";

	public static final String DANGER_TYPE = "danger_type";

	public static final String PIPE_DANGER_TYPE = "pipe_danger_type";

	public static final String USER_INDUSTRY_COMPANY = "qiye";

	public static final String USER_INDUSTRY_ANJIAN = "anjian";

	public static final String CHAOGAO_DEPT = "trouble_dept";

	public static final Integer COMPANY_ZHUXIAO = 1;// 企业已注销或暂定经营

	public static final Integer COMPANY_WUCHANGSUO = 2;// 企业无经营场所

	public final static String DANGER_NUMBER = "NB";// 用于生产隐患编号

	public final static String GD_DANGER_NUMBER = "GD";// 用于生产隐患编号

	public final static Long AREA_CODE = 330200000000L;// 顶级区域编码
	public final static String AREA_NAME = "宁波市";//区域名字

	public final static String WEIHUA = "1400,1390,1401,1404,1405,430466";

	public final static String RENYUANMIJI = "1425,1430,1406,1433,1411";

	public final static Integer ACTUALIZE = 1;// 工作动态

	public final static Integer TABLE = 2;// 表格下载

	public final static Integer NOTICE = 3;// 通知通告

	public final static Integer LOW = 4;// 法规标准

	public final static Integer MEETING_RECORD = 1;

	public final static String ROLE = "ROLE_TRAFFIC_QC";

	public final static Long QC_AREA[] = { 330201L, 330202L, 330203L, 330215L };

	public final static Integer GOVERNANCE_REPORT_STATISTIC = 1; // 矿山、危化、烟花爆竹、冶金等行业和领域安全生产隐患排查治理情况报表
																	// 指定行业

	public final static Integer GOVERNANCE_REPORT_STATISTIC_OTHER = 3; // 矿山、危化、烟花爆竹、冶金等行业和领域安全生产隐患排查治理情况报表
																		// 其他行业

	public final static Integer KEY_INDUSTRIES_REPROT_STATISTIC = 2; // 其他重点行业领域安全生产隐患排查治理情况统计表
																		// 指定行业

	public final static Integer KEY_INDUSTRIES_REPROT_STATISTIC_OTHER = 4; // 其他重点行业领域安全生产隐患排查治理情况统计表
																			// 其他行业

	/**
	 * 矿山、危化、烟花爆竹、冶金等行业和领域安全生产隐患排查治理情况报表行业
	 */
	public final static Integer MINE_REPORT = 1;

	/**
	 * 其他重点行业领域安全生产隐患排查治理情况统计表行业
	 */
	public final static Integer OTHER_REPORT = 2;

	/**
	 * 房屋建筑工程行业
	 */
	public final static Integer HOUSE_ITEM_TRADE = 30;

	public static final Integer SAFE_ENFORCE_LAW = 10; // 安全生产执法行业类型

	public static final Integer NOMAL_DANGER_TYPE = 1332;// 企业行业

	public static final Integer PIPE_NOMAL_DANGER_TYPE = 1010007;// 管道行业

	// 文件不存在
	public final static String NO_FILE = "file.upload.noFile";

	// add by huangjl 2014-1-8
	public final static String SYS_HAZARD_SOURCE = "SYS_HAZARD_SOURCE";// 隐患来源代码，保存在枚举表Fk_enum中
	public final static String NEW_NOMAL_DANGER_TYPE = "nomal_danger_type";// 一般隐患类型编码，保存在表DA_INDUSTRY_PARAMETER中
	public final static String ECONOMYKIND = "economyKind_new";// 经济类型，保存在枚举表Fk_enum中
	public final static String TRADETYPE = "tradeType_new";// 行业分类，保存在枚举表Fk_enum中
	public final static String NATIONAL_ECONOMIC = "NATIONAL_ECONOMIC";// 国民经济分类，保存在枚举表Fk_enum中
	public final static String _UNDEFINED = "_undefined";// 未定义行业编码后缀

	public final static String COMPANYSCALE = "companyScale";// 企业规模，保存在枚举表Fk_enum中

	public final static String HIGH_RISK_WORK = "HIGH_RISK_WORK";// 高风险作业，保存在枚举表Fk_enum中

	public static final String PASSWORD_REGEX = "^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";
	
	// 二级区域编码

	// 保税区
	public final static String BS_AREACODE = "330215000000";

	// 石化区
	public final static String SH_AREACODE = "330218000000";

	// 物流区
	public final static String WL_AREACODE = "330219000000";
	// memcached 过期时间 expiration_time 2h 2000 * 60 * 60
	public final static long EXPIRATION_TIME = 1000 * 60 * 60 * 24; 

	// 是否需要修改用户名为工商注册号
	public final static boolean ISMODIFYUSERNAME = true;
	// 重置密码
	public final static String PASSWORD = "Abc123456";

	public final static String department[] = { "anjian", "anjian_whp", "anjian_fire", "anjian_mine", "anjian_machine", "anjian_other", "jingxin", "jiaoyu", "xiaofang", "minzong", "jianwei", "chengguan", "jiaotong", "shuili", "maoyi", "wenguang", "weisheng", "haiyang", "lvyou", "zhijian", "renfang", "nongji", "dianli", "lishe" }; //
	// 月报
	public final static String Cname3[] = { "nbyhpc_loadPaiChaOfCompany_", "nbyhpc_loadCompanyByIndustry_" };
	// 季报
	public final static String Cname4[] = { "nbyhpc_loadQuarter_null", "nbyhpc_loadQuarterByIndustry_" };
	// 排查质量
	public final static String Cname5[] = { "nbyhpc_loadMassAll_", "nbyhpc_loadMassByIndustry_"}; 
	// 隐患治理
	public final static String Cname6[] = { "nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_null", "nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_" }; 

	public final static String industry[] = { "anwei", "anjian", "jingxin", "jiaoyu", "jiaojing", "xiaofang", "minzong", "zhujian", "chengguan", "jiaotong", "shuili", "maoyi", "wenguang", "weisheng", "haiyang", "lvyou", "zhijian", "renfang", "nongji", "haishi", "dianli", "jichang" }; // 行业

	public final static String hy[] = { "loadTradeTypesForCompany", "loadTradeType" };

	public final static String axis[] = { "nbyhpc_AxisMine_", "nbyhpc_Axis2Mine_", "nbyhpc_AxisOther_", "nbyhpc_Axis2Other_" };

	public final static Long noSecondAreaCode=0L;
	public final static String noSecondAreaName="本级";
	/**
	 * 根据2个日期获取时间差
	 * 
	 * @param date
	 * @param governDate
	 * @return
	 */
	public final static int getTimeOut(Date beforeDate, Date afterDate) {
		if (beforeDate == null || afterDate == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(beforeDate);
		GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DATE));
		cal.setTime(afterDate);
		GregorianCalendar g2 = new GregorianCalendar(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DATE));
		return FileUpload.getDays(g1, g2);
	}

	public final static Date getTimeOutDate(Date oldDate, int timeOut) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DATE));
		return FileUpload.getTimeOutDate(g1, timeOut);
	}

	public final static Date[] getSeasonTime(int year, int quarter) throws ApplicationAccessException {
		Date startTime;
		Date endTime;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		switch (quarter) {
		case 1:
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			startTime = cal.getTime();
			cal.set(Calendar.MONTH, 3);
			endTime = cal.getTime();
			break;
		case 2:
			cal.set(Calendar.MONTH, 3);
			cal.set(Calendar.DATE, 1);
			startTime = cal.getTime();
			cal.set(Calendar.MONTH, 6);
			endTime = cal.getTime();
			break;
		case 3:
			cal.set(Calendar.MONTH, 6);
			cal.set(Calendar.DATE, 1);
			startTime = cal.getTime();
			cal.set(Calendar.MONTH, 9);
			endTime = cal.getTime();
			break;
		case 4:
			cal.set(Calendar.MONTH, 9);
			cal.set(Calendar.DATE, 1);
			startTime = cal.getTime();
			cal.set(Calendar.YEAR, year + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			endTime = cal.getTime();
			break;
		default:
			throw new ApplicationAccessException("季报类型错误");
		}
		return new Date[] { startTime, endTime };
	}

	public final static String checkCompany(DaCompany company) {
		if (company != null) {
			// 先查看二级区域有没有，没有的话直接返回info界面
			if (company.getSecondArea() != null && company.getSecondArea().longValue() > 0) {
				// 二级区域有的情况下还要判断三级区域是否有，保税区，石化区，物流区三个区域没有三级区域，不用判断
				if (Nbyhpc.BS_AREACODE.equals(company.getSecondArea().toString()) || Nbyhpc.SH_AREACODE.equals(company.getSecondArea().toString()) || Nbyhpc.WL_AREACODE.equals(company.getSecondArea().toString())) {
					// 二级区域为保税区，石化区，物流区三个区域时，不做操作
					return null;
				} else {
					if (company.getThirdArea() != null && company.getThirdArea().longValue() > 0) {
						// 三级区域存在，不做操作
						return null;
					} else {
						return "info";
					}
				}
			} else {
				return "info";
			}
		} else {
			return "info";
		}
	}
}
