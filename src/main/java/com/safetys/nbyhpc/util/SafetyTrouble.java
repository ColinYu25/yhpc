package com.safetys.nbyhpc.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
//import com.safetys.nbyhpc.domain.model.ZjThreeReport;
//import com.safetys.nbyhpc.domain.model.ZjZhifaReport;

public class SafetyTrouble {

	public final static String[] MONTHS = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };// 填报年月的月份

	public final static int MINE_TYPE = 1;// 矿山类型

	public final static int OTHER_TYPE = 2;// 其他类型

	public final static int THREE_TYPE = 3;// 三非类型

	public final static int ZHIFA_TYPE = 4;// 执法类型

	public final static String MINE = "mine";// 矿山

	public final static String OTHER = "other";// 其他

	public final static String THREE = "THREE";// 三非

	public final static String ZHIFA = "ZHIFA";// 执法

	public final static int NO_REPORT = 0;// 未上报

	public final static int COUNTY_REPORT = 1;// 县级上报

	public final static int CITY_REPORT = 2;// 市级上报
	
	public final static int PROVINCE_REPORT = 3;// 省级上报

	public final static int BACK_OBSTACLE = 100;// 允许退回的障碍，即检查本市级对应的数据是否已经上报，若上报，则不能再退回其下县市区上报的数据。

	/**
	 * 矿山报表增加合计列表
	 */
	public final static Integer WXHXPQY = 45;// 危险化学品企业

	public final static Integer SYWHPSCDW = 46;// 使用危化品的生产单位

	public final static Integer YHBZQY = 47;// 烟花爆竹企业

	public final static Integer COUNTRY_TOTAL = 48;// 国家合计列表

	public final static Integer WXHXYSCCCQY = 49;// （1）危险化学品生产、储存企业和其他化工企业

	public final static Integer YIJINYOUSE = 50;// 冶金、有色企业

	public final static Integer MINE_COUNTRY_TOTAL = 51;// 国家统计矿山报表合计项

	/**
	 * 县级挂牌
	 */
	public final static Integer GUAPAI_LEVEL_COUNTY = 1;

	/**
	 * 市级挂牌
	 */
	public final static Integer GUAPAI_LEVEL_CITY = 2;

	/**
	 * 省级挂牌
	 */
	public final static Integer GUAPAI_LEVEL_PROVINCE = 3;

	/**
	 * 累加到其他行业的行业（国家统计2010）
	 * 
	 */
	public final static Integer[] FOR_OTHER_INDUSTRY = { 27, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44 };

	/**
	 * 打非治违周报表的类型集合
	 */
	public final static List<State> DFZW_TYPE_MAP;

	static {
		DFZW_TYPE_MAP = new ArrayList<State>();
		DFZW_TYPE_MAP.add(new State("01-coal mine", "1、煤矿"));
		DFZW_TYPE_MAP.add(new State("02-non-coal mine", "2、非煤矿山"));
		DFZW_TYPE_MAP.add(new State("03-road trafiic", "3、道路交通"));
		DFZW_TYPE_MAP.add(new State("04-water transportation", "4、水上交通"));
		DFZW_TYPE_MAP.add(new State("05-construction", "5、建设施工"));
		DFZW_TYPE_MAP.add(new State("06-fire control", "6、消  防"));
		DFZW_TYPE_MAP.add(new State("07-dangerous chemical", "7、危险化学品"));
		DFZW_TYPE_MAP.add(new State("08-fireworks and crackers", "8、烟花爆竹"));
		DFZW_TYPE_MAP.add(new State("09-industrial goods", "9、民爆物品"));
		DFZW_TYPE_MAP.add(new State("10-metallurgy", "10、冶金"));
		DFZW_TYPE_MAP.add(new State("11-other", "11、其他"));
	}

	/**
	 * 打非治违（处罚）周报表的类型集合
	 */
	public final static List<State> DFZW_PUNISH_TYPE_MAP;

	static {
		DFZW_PUNISH_TYPE_MAP = new ArrayList<State>();
		DFZW_PUNISH_TYPE_MAP.add(new State("01-coal mine", "1、煤矿"));
		DFZW_PUNISH_TYPE_MAP.add(new State("02-non-coal mine", "2、非煤矿山"));
		DFZW_PUNISH_TYPE_MAP.add(new State("03-road trafiic", "3、道路交通"));
		DFZW_PUNISH_TYPE_MAP.add(new State("04-water transportation", "4、水上交通"));
		DFZW_PUNISH_TYPE_MAP.add(new State("05-construction", "5、建设施工"));
		DFZW_PUNISH_TYPE_MAP.add(new State("06-fire control", "6、消  防"));
		DFZW_PUNISH_TYPE_MAP.add(new State("07-dangerous chemical", "7、危险化学品"));
		DFZW_PUNISH_TYPE_MAP.add(new State("08-fireworks and crackers", "8、烟花爆竹"));
		DFZW_PUNISH_TYPE_MAP.add(new State("09-industrial goods", "9、民爆物品"));
		DFZW_PUNISH_TYPE_MAP.add(new State("10-metallurgy", "10、冶金"));
		DFZW_PUNISH_TYPE_MAP.add(new State("11-other", "11、其他"));
	}

	/**
	 * 获取月份
	 * 
	 * @return
	 */
	public final static List<SafetyTroubleUtil> getMonths() {
		List<SafetyTroubleUtil> months = new ArrayList<SafetyTroubleUtil>();
		// for (int i = 0; i < MONTHS.length; i++) {
		// SafetyTroubleUtil month = new SafetyTroubleUtil();
		// month.setMonth(MONTHS[i]);
		// months.add(month);
		// }
		for (int i = MONTHS.length - 1; i >= 0; i--) {
			SafetyTroubleUtil month = new SafetyTroubleUtil();
			month.setMonth(MONTHS[i]);
			months.add(month);
		}
		return months;
	}

	public final static List<SafetyTroubleUtil> getMonthsByData(List list, String nowYear) {
		List<SafetyTroubleUtil> months = getMonths();
		// Calendar cal = Calendar.getInstance();
		// String nowYear = cal.get(Calendar.YEAR)+"";
		String reportMonth = "";
		for (int j = 0; j < months.size(); j++) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof ZjMineReport) {
					reportMonth = ((ZjMineReport) list.get(i)).getReportMonth();
					if (reportMonth != null) {
						if (reportMonth.split("-")[1].equals(months.get(j).getMonth())
								&& reportMonth.split("-")[0].equals(nowYear)) {
							months.get(j).setChoose(false);
						}
					}
				} 
//				else if (list.get(i) instanceof ZjThreeReport) {
//					reportMonth = ((ZjThreeReport) list.get(i)).getReportMonth();
//					reportMonth = ((ZjThreeReport) list.get(i)).getReportMonth();
//					if (reportMonth != null) {
//						if (reportMonth.split("-")[1].equals(months.get(j).getMonth())
//								&& reportMonth.split("-")[0].equals(nowYear)) {
//							months.get(j).setChoose(false);
//						}
//					}
				//} 
//			else if (list.get(i) instanceof ZjZhifaReport) {
//					reportMonth = ((ZjZhifaReport) list.get(i)).getReportMonth();
//					reportMonth = ((ZjZhifaReport) list.get(i)).getReportMonth();
//					if (reportMonth != null) {
//						if (reportMonth.split("-")[1].equals(months.get(j).getMonth())
//								&& reportMonth.split("-")[0].equals(nowYear)) {
//							months.get(j).setChoose(false);
//						}
//					}
//				}
			}
		}
		return months;
	}

	/**
	 * 获取当前年月
	 * 
	 * @return
	 */
	public final static String getNowReportMonth() {
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		int nowMonth = cal.get(Calendar.MONTH) + 1;
		if (nowMonth < 10) {
			return nowYear + "-0" + nowMonth;
		}
		return nowYear + "-" + nowMonth;
	}

	/**
	 * 获取年份集合
	 * 
	 * @return
	 */
	public final static String[] getYaers() {
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		String[] years = new String[(nowYear - 2008 + 1)];
		for (int i = 0; i <= nowYear - 2008; i++) {
			// years[i]=2008+i+"";
			years[i] = nowYear - i + "";// 倒序
		}
		return years;
	}

	/**
	 * 根据矿山或其他获取对应的行业序号
	 * 
	 * @param tableType
	 * @return
	 */
	public final static int[] getType(String tableType) {
		int[] mineTrades = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		// 矿山 行业
		int[] otherTrades =
				{ 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
						43, 44 };
		// int [] otherTrades = {18, 19, 20, 23, 24, 25, 26, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
		// 43, 44};
		// 其他 行业
		return MINE.equals(tableType) ? mineTrades : otherTrades;
	}

	/**
	 * 根据矿山或其他获取对应的行业序号
	 * 
	 * @param tableType
	 * @return
	 */
	public final static int[] getType(int type) {
		return (type == MINE_TYPE) ? getType(MINE) : getType(OTHER);
	}

	/**
	 * 根据用户获取应该上报的状态
	 * 
	 * @param fkUser
	 * @return
	 */
	public final static Map getState(FkUser fkUser, int mineState) {
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		boolean flag = false;
		int state = SafetyTrouble.NO_REPORT;
		if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())) {
			// if (mineState == SafetyTrouble.COUNTY_REPORT) {//操作用户为市级用户
			// flag = true;
			// state = SafetyTrouble.CITY_REPORT;
			// }
			if (mineState == SafetyTrouble.NO_REPORT) {
				flag = true;
				state = SafetyTrouble.PROVINCE_REPORT;
			}
		}else if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
			// if (mineState == SafetyTrouble.COUNTY_REPORT) {//操作用户为市级用户
			// flag = true;
			// state = SafetyTrouble.CITY_REPORT;
			// }
			if (mineState == SafetyTrouble.NO_REPORT) {
				flag = true;
				state = SafetyTrouble.CITY_REPORT;
			}
			System.out.println("-----------------------"+state+"-----createDfzw-"+flag+"--------------------------------------");
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, fkUser.getFkRoles())
				|| RoleType1.isRoleByDepic(RoleType1.ADMIN, fkUser.getFkRoles())) {
			if (mineState == SafetyTrouble.NO_REPORT) {// 操作用户为县级用户，上报的数据状态必须为未上报
				flag = true;
				state = SafetyTrouble.COUNTY_REPORT;
			}
		} else {

		}
		map.put("allow", flag);
		map.put("state", state);
		return map;
	}

	/**
	 * 查看是否用权限对数据进行退回
	 * 
	 * @param nowUser
	 * @param state
	 * @return
	 */
	public static boolean allowBack(FkUser fkUser, int mineState) {
		boolean flag = false;
		if (RoleType1.isRoleByDepic(RoleType1.CITY, fkUser.getFkRoles())) {
			// if (mineState == SafetyTrouble.COUNTY_REPORT) {//操作用户为市级用户
			// flag = true;
			// state = SafetyTrouble.CITY_REPORT;
			// }
			if (mineState == SafetyTrouble.COUNTY_REPORT) {
				flag = true;
			}
		} else if (RoleType1.isRoleByDepic(RoleType1.COUNTY, fkUser.getFkRoles())
				|| RoleType1.isRoleByDepic(RoleType1.ADMIN, fkUser.getFkRoles())) {
			// if (mineState == SafetyTrouble.NO_REPORT) {//操作用户为县级用户，上报的数据状态必须为未上报
			// flag = true;
			// }
			return false;
		} else if (RoleType1.isRoleByDepic(RoleType1.PROVINCE, fkUser.getFkRoles())) {

			if (mineState == SafetyTrouble.CITY_REPORT) {// 省级操作用户
				flag = true;
			}
		}
		return flag;
	}

	@SuppressWarnings("unused")
	/**
	 * 查看对应的市区，其对应的县市区是否已全部录入
	 * @param areas
	 * @param data
	 * @return
	 * @throws ApplicationAccessException 
	 */
	public boolean allowCityReport(Long areaId, List data) throws ApplicationAccessException {
		// List<FkArea> areas=loadChildAreas(areaId);
		boolean isAllow = true;// 是否允许
		// boolean isNotContinue = false;//是否不再继续循环
		// ZjMineReport mine = new ZjMineReport();
		// ZjThreeReport three = new ZjThreeReport();
		// Long countyId = 0L;
		// if (areas.size() > 0 && data.size() <= 0) {
		// return false;
		// }
		// for (FkArea area : areas) {
		//			
		// for (int i=0; i<data.size(); i++) {
		// if (data.get(i) instanceof ZjMineReport) {//矿山表
		// mine = ((ZjMineReport)data.get(i));
		// if (mine.getUserId() != null) {//获取操作用户对应的区域
		// countyId = mine.getUserId().getFkUserInfo().getThirdArea();
		// } else {//数据操作用户为null
		// continue;//跳到下一次循环
		// }
		// } else if (data.get(i) instanceof ZjThreeReport) {//三非表
		// three = ((ZjThreeReport)data.get(i));
		// if (three.getUserId() != null) {
		// countyId = three.getUserId().getFkUserInfo().getThirdArea();
		// } else {
		// continue;
		// }
		// }
		// if (area.getId().equals(countyId)) {
		// break;
		// } else if (i == data.size() - 1) {
		// return false;
		// }
		// }
		// }
		return isAllow;
	}

	/**
	 * 根据父行业获取所有的子行业的序号
	 * 
	 * @param fatherCode
	 * @return
	 */
	public static Integer[] getTradeTypesByFatherCode(Integer fatherCode) {
		Integer[] wxhxp = { 7, 8, 9, 10, 11, 12, 13 };
		Integer[] syhwpscdw = { 10, 11, 12 };
		Integer[] yhbzqy = { 14, 15, 16 };
		// Integer [] countryTotal = {18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34};
		Integer[] countryTotal = { 18, 19, 20, 23, 24, 25, 26, 28, 29, 30, 31, 32, 33, 34 };
		Integer[] wxhxyscccqy = { 7, 9, 10, 11, 12 };
		Integer[] mineCountryTotal = { 1, 2, 3, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		
		Integer[] ks_js_2012 = { 1,2 };// 2012年金属非金属矿山
		Integer[] wh_2012 = { 7, 8, 9, 10, 11, 12 };// 2012年危化品合计
		Integer[] syhwpscdw_2012 = { 7, 9, 10, 11, 12 };// 2012年生产、存储等危化品
		Integer[] yhbzqy_2012 = { 14, 15, 16 };// 2012年烟花爆竹
		Integer[] yejin_2012 = { 3, 4 };// 2012年冶金、有色金属
		Integer[] ks_qt_2012 = { 5, 6, 17 };// 2012年矿山报表其他
		Integer[] ks_hj_2012 = { 1,2,7,8,9,10,11,12,14,15,16,3,4,5,6,17 };// 2012年矿山报表其他
		Integer[] jt_dl_2012 = { 18,13 };// 2012年交通运输表、道路交通
		Integer[] jt_qt_2012 = { 33,27,38,39,40,41,42,34 };// 2012年交通运输表、其他
		Integer[] jt_hj_2012 = { 18,13,33,40,42,34,19,20,21,22,23,30,28,29,26,32,25,24,31 };// 2012年交通运输表、合计
		if (SafetyTrouble.WXHXPQY.equals(fatherCode))
			return wxhxp;
		else if (SafetyTrouble.SYWHPSCDW.equals(fatherCode))
			return syhwpscdw;
		else if (SafetyTrouble.YHBZQY.equals(fatherCode))
			return yhbzqy;
		else if (SafetyTrouble.COUNTRY_TOTAL.equals(fatherCode))
			return countryTotal;
		else if (SafetyTrouble.WXHXYSCCCQY.equals(fatherCode))
			return wxhxyscccqy;
		else if (SafetyTrouble.MINE_COUNTRY_TOTAL.equals(fatherCode))
			return mineCountryTotal;
		else if (fatherCode.intValue() == 101)
			return ks_js_2012;
		else if (fatherCode.intValue() == 103)
			return wh_2012;
		else if (fatherCode.intValue() == 104)
			return syhwpscdw_2012;
		else if (fatherCode.intValue() == 105)
			return yhbzqy_2012;
		else if (fatherCode.intValue() == 106)
			return yejin_2012;
		else if (fatherCode.intValue() == 107)
			return ks_qt_2012;
		else if (fatherCode.intValue() == 108)
			return ks_hj_2012;
		else if (fatherCode.intValue() == 109)
			return jt_dl_2012;
		else if (fatherCode.intValue() == 110)
			return jt_qt_2012;
		else if (fatherCode.intValue() == 111)
			return jt_hj_2012;
		return null;
	}
}
