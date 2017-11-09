/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.impl;

import java.util.Calendar;
import java.util.List;

import com.bjsxt.hibernate.MemCached;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.ZjSend;
import com.safetys.nbyhpc.domain.persistence.iface.SendPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.Axis2FacadeIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.StaticCacheFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @date 2011-05-26 排查质量统计企业查看列表加入月份条件 @author lvx
 * @author lvx
 * @date 2012-2-29 zhoux
 *       在季度统计中，增加时间条件，使得在注册时间在所查询季度时间之后的企业不显示在统计列表中，修改地方有zhoux标识
 */
public class StaticCacheFacadeImpl implements StaticCacheFacadeIface {

	private AreaPersistenceIface areaPersistenceIface; 

	private StatisticFacadeIface statisticFacadeIface;

	private CompanyFacadeIface companyFacadeIface;

	private Axis2FacadeIface axis2FacadeIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private SendPersistenceIface sendPersistenceIface;

	public void loadCacheHis(String[] cname) throws ApplicationAccessException {
		MemCached cache = MemCached.getInstance();
		String cacheName = "";
		String name = "";
		String name1 = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int quarter = 0;
		int current_quarter = mToq(month);
		FkArea area = new FkArea();
		Statistic statistic = new Statistic();
		String hql = "from FkArea t  where  t.id=3020 or t.fatherId=3020 order by id ";
		List<FkArea> areas = areaPersistenceIface.loadAreasByHql(hql);
		for (FkArea a : areas) {
			cache.delete("nbyhpc_loadArea_" + a.getAreaCode()); // 区域缓存每天清空
		}
		// cacheName
		for (int i = 0; i < cname.length; i++) {
			name1 = cname[i];
			statistic = new Statistic();
			if (!name1.equals("nbyhpc_loadPaiChaOfCompany_") && !name1.equals("nbyhpc_loadQuarter_null") && !name1.equals("nbyhpc_loadMassAll_") && !name1.equals("nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_null")) {
				// department
				for (int j = 0; j < Nbyhpc.department.length; j++) {
					String d = Nbyhpc.department[j];
					statistic.setRemark(d);

					name = name1 + d;
					// area
					for (FkArea a : areas) {

						if ((d.equals("anjian") || d.equals("jianwei") || d.equals("xiaofang") || d.equals("zhijian") || d.equals("renfang") || d.equals("nongji") || d.equals("lishe")) && a.getAreaCode().toString().trim().equals("330200000000")) {
							statistic.setIsSonType(0);
						} else {
							statistic.setIsSonType(1);
						}

						cacheName = "";
						cacheName = name + a.getAreaCode();
						area.setAreaCode(a.getAreaCode());

						// month
						for (int y = 2014; y <= year; y++) {
							statistic.setYear(y);
							statistic.setBeg_month(1);
							cacheName = name + a.getAreaCode() + y;
							for (int m = 1; m <= 12; m++) {

								if (y < year || (y == year && m <= month)) {
									statistic.setMonth(m);
									statistic.setEnd_month(m);
									quarter = mToq(m);
									statistic.setQuarter(quarter);
									cacheName = name + a.getAreaCode() + y + (m < 10 ? "0" + m : m);

									if (y == year && (m == month  ||  (quarter+1)==current_quarter   )) { 
										System.out.println("当月或季或同年上季动态生成");
										statistic.setReFresh(true);
										loadDtCacheHis(cacheName, area, statistic, current_quarter);
									}else if ((y+1) == year && (current_quarter==1 ||  quarter==4  )) {
										System.out.println("不同年上季动态生成");
										statistic.setReFresh(true);
										loadDtCacheHis(cacheName, area, statistic, current_quarter);
									} else {

										statistic.setReFresh(false);
										Object chis = cache.get(cacheName);
										if (chis != null) {
											cache.set(cacheName, chis);
											System.out.println("cache重置");
										} else {
											System.out.println("动态生成");
											loadDtCacheHis(cacheName, area, statistic, current_quarter);
										}

									}
								}
							}

						}
					}
				}
			} else {
				name = name1;
				for (FkArea a : areas) {
					if (!a.getAreaCode().toString().trim().equals("330200000000")) {
						statistic.setIsSonType(1);
					} else {
						statistic.setIsSonType(0);
					}

					cacheName = "";
					cacheName = name + a.getAreaCode();
					area.setAreaCode(a.getAreaCode());
					// month
					for (int y = 2014; y <= year; y++) {
						statistic.setYear(y);
						statistic.setBeg_month(1);
						cacheName = name + a.getAreaCode() + y;
						for (int m = 1; m <= 12; m++) {
							if (y < year || (y == year && m <= month)) {
								statistic.setMonth(m);
								statistic.setEnd_month(m);
								quarter = mToq(m);
								statistic.setQuarter(quarter);

								cacheName = name + a.getAreaCode() + y + (m < 10 ? "0" + m : m);
								if (y == year && (m == month  ||  (quarter+1)==current_quarter   )) { 
									System.out.println("当月或季或同年上季动态生成");
									statistic.setReFresh(true);
									loadDtCacheHis(cacheName, area, statistic, current_quarter);
								}else if ((y+1) == year && (current_quarter==1 ||  quarter==4  )) {
									System.out.println("不同年上季动态生成");
									statistic.setReFresh(true);
									loadDtCacheHis(cacheName, area, statistic, current_quarter);
								} else {
									statistic.setReFresh(false);
									Object chis = cache.get(cacheName);
									if (chis != null) {
										cache.set(cacheName, chis);
										System.out.println("cache重置");
									} else {
										System.out.println("动态生成");
										loadDtCacheHis(cacheName, area, statistic, current_quarter);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void loadDtCacheHis(String cacheName, FkArea area, Statistic statistic, int quarter) throws ApplicationAccessException {

		if (cacheName.indexOf("nbyhpc_loadPaiChaOfCompany_") >= 0) { // 各地月报
			System.out.println("各地月报");
			statisticFacadeIface.loadPaiChaOfCompany(area, statistic, quarter, "");

		} else if (cacheName.indexOf("nbyhpc_loadCompanyByIndustry_") >= 0) { //
			// 各部门月报
			System.out.println("各部门月报" + statistic.getRemark());

			if (!statistic.getRemark().equals("xiaofang") && !statistic.getRemark().equals("zhijian") && !statistic.getRemark().equals("renfang") && !statistic.getRemark().equals("nongji") && !statistic.getRemark().equals("lishe")) {

				if (statistic.getRemark().equals("anjian_other")) {

					statisticFacadeIface.loadCompanyByIndustryOfOther(area, statistic, quarter);
				} else if (statistic.getRemark().equals("jianwei")) { // 住建各部门月报

					statisticFacadeIface.loadItemByIndustry(area, statistic, quarter);
				} else if (statistic.getRemark().equals("shuili") || statistic.getRemark().equals("jiaotong") || statistic.getRemark().equals("chengguan")) { // 住建各部门月报
					statisticFacadeIface.loadItemByIndustry(area, statistic, quarter);
					statisticFacadeIface.loadCompanyByIndustry(area, statistic, quarter);
				} else {
					statisticFacadeIface.loadCompanyByIndustry(area, statistic, quarter);
				}
			}

		} else if (cacheName.indexOf("nbyhpc_loadQuarter_null") >= 0) { // 各地季报
			System.out.println("各地季报");
			statisticFacadeIface.loadQuarterOfCompany(area, statistic, quarter);

		} else if (cacheName.indexOf("nbyhpc_loadQuarterByIndustry_") >= 0) {
			// 各部门季报
			System.out.println("各部门季报");

			if (statistic.getRemark().equals("anjian_other")) {
				statisticFacadeIface.loadQuarterByIndustryOfAjOther(area, statistic, quarter);
			} else if (statistic.getRemark().equals("lishe") || statistic.getRemark().equals("nongji") || statistic.getRemark().equals("renfang") || statistic.getRemark().equals("zhijian") || statistic.getRemark().equals("xiaofang")) {
				statisticFacadeIface.loadStatisticsOfSeasonReportOther(statistic, quarter);
			} else {

				if ("jianwei".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.CONSTRUCTION_PROJECT);
				}
				statisticFacadeIface.loadQuarterByIndustry(area, statistic, quarter);
			}

		} else if (cacheName.indexOf("nbyhpc_loadMassAll_") >= 0) { // 各地排查质量
			System.out.println("各地排查质量");
			statisticFacadeIface.loadMassAll(area, statistic, quarter);

		} else if (cacheName.indexOf("nbyhpc_loadMassByIndustry_") >= 0) { // 各部门排查质量

			if (!statistic.getRemark().equals("anjian_whp") && !statistic.getRemark().equals("anjian_fire") && !statistic.getRemark().equals("anjian_mine") && !statistic.getRemark().equals("anjian_machine") && !statistic.getRemark().equals("anjian_other") && !statistic.getRemark().equals("xiaofang") && !statistic.getRemark().equals("zhijian") && !statistic.getRemark().equals("renfang") && !statistic.getRemark().equals("nongji") && !statistic.getRemark().equals("lishe")) {
				System.out.println("各部门排查质量");
				statisticFacadeIface.loadMassByIndustry(area, statistic, quarter);
			}

		} else if (cacheName.indexOf("nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_null") >= 0) { // 各地隐患治理
			System.out.println("各地隐患治理");
			statisticFacadeIface.loadTroubleByNomalAndHidden(area, statistic, quarter);
			statisticFacadeIface.loadTroubleByRollcall(area, statistic, quarter);

		} else if (cacheName.indexOf("nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_") >= 0 && cacheName.indexOf("nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_null") < 0) { // 各部门隐患治理
			if (!statistic.getRemark().equals("anjian_whp") && !statistic.getRemark().equals("anjian_fire") && !statistic.getRemark().equals("anjian_mine") && !statistic.getRemark().equals("anjian_machine") && !statistic.getRemark().equals("anjian_other") && !statistic.getRemark().equals("xiaofang") && !statistic.getRemark().equals("zhijian") && !statistic.getRemark().equals("renfang") && !statistic.getRemark().equals("nongji") && !statistic.getRemark().equals("lishe")) {
				System.out.println("各部门隐患治理");
				statisticFacadeIface.loadTroubleByNomalAndHidden(area, statistic, quarter);
				statisticFacadeIface.loadTroubleByRollcall(area, statistic, quarter);
			}
		}
	}

	/**
	 * 列表中查询条件下拉框中行业的数据加载,及列表查询中的行业内容 liulj
	 */
	public void loadCacheTradeTypes() throws ApplicationAccessException {
		MemCached cache = MemCached.getInstance();
		String cacheName = "";
		for (int k = 0; k < Nbyhpc.hy.length; k++) {
			for (int i = 0; i < Nbyhpc.industry.length; i++) {
				cacheName = Nbyhpc.hy[k] + "_" + Nbyhpc.industry[i];
				cache.delete(cacheName);

				if (Nbyhpc.hy[k].equals("loadTradeTypesForCompany")) {

					companyFacadeIface.loadTradeTypesForCompany1(Nbyhpc.industry[i]);

				} else if (Nbyhpc.hy[k].equals("loadTradeType")) {

					tradeTypePersistenceIface.loadTradeType(Nbyhpc.industry[i], Nbyhpc.COMPANY_TRADE);

				}

			}
		}
	}

	/**
	 * 省局报表 liulj
	 */
	public void loadAxis2Client() throws ApplicationAccessException {
		MemCached cache = MemCached.getInstance();
		String cacheName = "";
		Statistic statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		for (int k = 0; k < Nbyhpc.axis.length; k++) {
			statistic = new Statistic();
			statistic.setAreaCode(0l);
			statistic.setThirdCode(0l);
			for (int y = 2013; y <= year; y++) {
				statistic.setYear(y);
				if (Nbyhpc.axis[k].equals("nbyhpc_AxisMine_") || Nbyhpc.axis[k].equals("nbyhpc_AxisOther_")) { // 年报

					cacheName = Nbyhpc.axis[k] + y;
					if (y == year) { // 当年最新数据每天动态获取
						System.out.println("当年动态生成");
						statistic.setReFresh(true);
						loadDtCacheAxis(cacheName, statistic);
					} else {
						statistic.setReFresh(false);
						Object chis = cache.get(cacheName);
						if (chis != null) {
							cache.set(cacheName, chis);
							System.out.println("cache重置");
						} else {
							System.out.println("动态获取");
							loadDtCacheAxis(cacheName, statistic);
						}
					}

				} else { // 月报

					for (int m = 1; m <= 12; m++) {
						if (y < year || (y == year && m <= month)) {
							statistic.setMonth(m);
							statistic.setYearMonth(y + "-" + (m < 10 ? "0" + m : m));
							cacheName = Nbyhpc.axis[k] + y + (m < 10 ? "0" + m : m);

							if (y == year && m == month) { // 当月最新数据每天动态获取
								System.out.println("当年动态生成");
								statistic.setReFresh(true);
								loadDtCacheAxis(cacheName, statistic);
							} else {
								statistic.setReFresh(false);
								Object chis = cache.get(cacheName);
								if (chis != null) {
									cache.set(cacheName, chis);
									System.out.println("cache重置");
								} else {
									System.out.println("动态获取");
									loadDtCacheAxis(cacheName, statistic);
								}
							}
						}
					}
				}
			}
		}
	}

	public void loadDtCacheAxis(String cacheName, Statistic statistic) throws ApplicationAccessException {
		if (cacheName.indexOf("nbyhpc_AxisMine_") >= 0) {
			axis2FacadeIface.loadStatisticMineByParam2(statistic);
		} else if (cacheName.indexOf("nbyhpc_AxisOther_") >= 0) {
			axis2FacadeIface.loadStatisticOtherByParam2(statistic);
		} else if (cacheName.indexOf("nbyhpc_Axis2Mine_") >= 0) {
			statistic.setType(1);
			axis2FacadeIface.loadStatisticForAxis2MineByParam2(statistic);
		} else if (cacheName.indexOf("nbyhpc_Axis2Other_") >= 0) {
			statistic.setType(2);
			axis2FacadeIface.loadStatisticForAxis2OtherByParam2(statistic);
		}

	}

	public void sendData(int type) throws ApplicationAccessException {
		Calendar cal = Calendar.getInstance();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjSend.class, "ddg");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("type", type));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("setTime", cal.getTime()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSend", 0)); // 未发送的
		detachedCriteriaProxy.add(RestrictionsProxy.eq("userId", 430371l));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		List<ZjSend> send = sendPersistenceIface.loadSends(detachedCriteriaProxy, null);
		for (ZjSend s : send) {
			Statistic statistic = new Statistic();
			statistic.setYearMonth(s.getReportMonth());
			statistic.setType(type);
			//当前月份发送当前月份数据时候，强制生成da_statistic,因为如果是当前月份的话，默认不生成，
			String setTime = DateUtils.date2Str(s.getSetTime(), "yyyy-MM");
			boolean needDaStatistic = s.getReportMonth().equals(setTime);
			statistic.setNeedDaStatistic(needDaStatistic);
			try {
				if(axis2FacadeIface.sendDataOfMineOrOtherByOMElement(statistic, 430371l)){
					s.setIsSend(1);
					sendPersistenceIface.uptSend(s);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 省局发送之前先重新生成缓存
	 * 
	 **/
	public void reCreateCache(int type) throws ApplicationAccessException {
		
		
		Calendar cal = Calendar.getInstance();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjSend.class, "ddg");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("type", type));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("setTime", cal.getTime()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSend", 0)); // 未发送的
		detachedCriteriaProxy.add(RestrictionsProxy.eq("userId", 430371l));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		List<ZjSend> send = sendPersistenceIface.loadSends(detachedCriteriaProxy, null);
		for (ZjSend s : send) {
			
			Statistic statistic = new Statistic();
			statistic.setYearMonth(s.getReportMonth());
			statistic.setAreaCode(0);
			statistic.setType(type);
			//当前月份发送当前月份数据时候，强制生成da_statistic,因为如果是当前月份的话，默认不生成，
			String setTime = DateUtils.date2Str(s.getSetTime(), "yyyy-MM");
			boolean currentMonth = s.getReportMonth().equals(setTime);
				
			Integer year = Integer.parseInt(s.getReportMonth().split("-")[0]);
			Integer month = Integer.parseInt(s.getReportMonth().split("-")[1]);
			String cacheNum  = year + ((month < 10) ? "0" : "") + month;
			
			if(type==1){
				//调用刷新方法之前，先删除数据库保存的查询结果
				String cacheName  = "nbyhpc_Axis2Mine_" + cacheNum;
				axis2FacadeIface.deleteStatistic(cacheName, String.valueOf(type));
				
				//调用刷新方法之前，先删除MemCached中的缓存数据
				MemCached cache = MemCached.getInstance();
				cache.delete(cacheName);
				
				if (!currentMonth) {//当前月份不需要在这里生成DAStatistic
					axis2FacadeIface.loadStatisticForAxis2MineByParam2(statistic);
				}
				//调用方法刷新
			}else{
				//调用刷新方法之前，先删除数据库保存的查询结果
				String cacheName  = "nbyhpc_Axis2Other_" + cacheNum;
				axis2FacadeIface.deleteStatistic(cacheName, String.valueOf(type));
				
				//调用刷新方法之前，先删除MemCached中的缓存数据
				MemCached cache = MemCached.getInstance();
				cache.delete(cacheName);
				
				//调用方法刷新
				if (!currentMonth) {//当前月份不需要在这里生成DAStatistic
					axis2FacadeIface.loadStatisticForAxis2OtherByParam2(statistic);
				}
			}
			
			
		}
	}

	private int mToq(int month) {
		int quarter = 0;
		if (month == 1 || month == 2 || month == 3) {
			quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			quarter = 4;
		}
		return quarter;
	}

	public AreaPersistenceIface getAreaPersistenceIface() {
		return areaPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public StatisticFacadeIface getStatisticFacadeIface() {
		return statisticFacadeIface;
	}

	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
	}

	public CompanyFacadeIface getCompanyFacadeIface() {
		return companyFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public TradeTypePersistenceIface getTradeTypePersistenceIface() {
		return tradeTypePersistenceIface;
	}

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public Axis2FacadeIface getAxis2FacadeIface() {
		return axis2FacadeIface;
	}

	public void setAxis2FacadeIface(Axis2FacadeIface axis2FacadeIface) {
		this.axis2FacadeIface = axis2FacadeIface;
	}

	public SendPersistenceIface getSendPersistenceIface() {
		return sendPersistenceIface;
	}

	public void setSendPersistenceIface(SendPersistenceIface sendPersistenceIface) {
		this.sendPersistenceIface = sendPersistenceIface;
	}

}
