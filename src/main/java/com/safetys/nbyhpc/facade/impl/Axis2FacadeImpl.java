package com.safetys.nbyhpc.facade.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.log4j.Logger;

import com.bjsxt.hibernate.MemCached;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.domain.model.DaStatistic;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.model.ZjDanger;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.model.ZjReportParam;
import com.safetys.nbyhpc.domain.model.ZjSend;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ReportParamPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.SendPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.StatisticPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TCachePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ZjDangerPersistenceIface;
import com.safetys.nbyhpc.facade.iface.Axis2FacadeIface;
import com.safetys.nbyhpc.facade.iface.BigTroubleFacadeIface;
import com.safetys.nbyhpc.facade.iface.DfzwFacadeIface;
import com.safetys.nbyhpc.facade.iface.DfzwPunishFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineDetailFacadeIface;
import com.safetys.nbyhpc.facade.iface.MineFacadeIface;
import com.safetys.nbyhpc.util.ConnectionFactory;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.axis2.model.Danger;
import com.safetys.nbyhpc.web.axis2.model.DfzwPunish;
import com.safetys.nbyhpc.web.axis2.model.DfzwPunishDetail;
import com.safetys.nbyhpc.web.axis2.model.DfzwReport;
import com.safetys.nbyhpc.web.axis2.model.DfzwReportDetail;
import com.safetys.nbyhpc.web.axis2.model.NomalDanger;
import com.safetys.nbyhpc.web.axis2.model.Param;
import com.safetys.nbyhpc.web.axis2.model.ThreeIllegal;
import com.safetys.nbyhpc.web.axis2.model.ThreeParam;

public class Axis2FacadeImpl implements Axis2FacadeIface { 

	// targetEPR指定打包的Service（.aar文件）在容器中的物理位置
	// 测试合并添加
	private static EndpointReference targetEPR;

	private CompanyPersistenceIface companyPersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;

	private ReportParamPersistenceIface reportParamPersistenceIface;

	private ZjDangerPersistenceIface zjDangerPersistenceIface;

	private MineFacadeIface mineFacadeIface;

	private MineDetailFacadeIface mineDetailFacadeIface;

	private BigTroubleFacadeIface bigTroubleFacadeIface;

	private SendPersistenceIface sendPersistenceIface;

	private DfzwFacadeIface dfzwFacadeIface;

	private DfzwPunishFacadeIface dfzwPunishFacadeIface;

	private TCachePersistenceIface tcachePersistenceIface;
	
	private StatisticPersistenceIface statisticPersistenceIface;

	Logger log = Logger.getLogger(this.getClass());

	private static ConnectionFactory cFactory = new ConnectionFactory();

	public void setReportParamPersistenceIface(ReportParamPersistenceIface reportParamPersistenceIface) {
		this.reportParamPersistenceIface = reportParamPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	private void createSend(ZjSend send) throws ApplicationAccessException {
		sendPersistenceIface.createSend(send);
	}

	public ZjSend loadSend(Statistic statistic) throws ApplicationAccessException {
		List<ZjSend> sends = new ArrayList<ZjSend>();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjSend.class, "zs");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zs.type", statistic.getType()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("zs.reportMonth", statistic.getYearMonth()));
		sends = sendPersistenceIface.loadSends(detachedCriteriaProxy, null);
		if (sends != null && sends.size() >= 1) {
			return sends.get(0);
		} else {
			return null;
		}
	}

	private List<Danger> bigTroublesTojDangers(List<ZjBigTrouble> bigTroubles) throws ApplicationAccessException {
		List<Danger> dangers = new ArrayList<Danger>();
		Danger danger = null;
		for (ZjBigTrouble d : bigTroubles) {
			danger = new Danger();
			danger.setTradeType(d.getTradeType());
			danger.setTableType(d.getTableType());
			danger.setCompanyName(d.getCompanyName());
			danger.setAddress(d.getAddress());
			danger.setContent(d.getContent());
			danger.setTargetTask(d.getTargetTask());
			danger.setWorker(d.getWorker());
			danger.setSafetyMethod(d.getSafetyMethod());
			danger.setGoods(d.getGoods());
			danger.setGovernDate(d.getGovernDate());
			danger.setStateTime(d.getStateTime());
			danger.setState(d.getState());
			danger.setId(d.getId());
			dangers.add(danger);
		}
		return dangers;
	}

	private List<NomalDanger> mineReportDetailsTojNomalDangers(List<ZjMineReportDetail> mineReportDetails) throws ApplicationAccessException {
		List<NomalDanger> nomalDangers = new ArrayList<NomalDanger>();
		NomalDanger nomalDanger = null;
		for (ZjMineReportDetail m : mineReportDetails) {
			nomalDanger = new NomalDanger();
			// nomalDanger.setTableType(m.get);
			nomalDanger.setType(m.getType());
			nomalDanger.setCompany(m.getCompany());
			nomalDanger.setGeneralDanger(m.getGeneralDanger());
			nomalDanger.setGeneralDangerGovern(m.getGeneralDangerGovern());
			nomalDanger.setPlanMoney(m.getPlanMoney());
			nomalDangers.add(nomalDanger);
		}
		return nomalDangers;
	}

	private void createZjDanger(List<Danger> dangers) throws ApplicationAccessException {
		ZjDanger zjDanger;
		for (Danger d : dangers) {
			zjDanger = new ZjDanger();
			zjDanger.setDangerId(d.getId());
			zjDanger.setState(d.getState());
			zjDangerPersistenceIface.createZjDanger(zjDanger);
		}
	}

	private void writeToFile(String filePath, OMElement method) {
		try {
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);
			out.write(method.toString().getBytes("utf-8"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			// throw new
			// RuntimeException("Error occured while Writing to xml file!");
		}
	}

	public Boolean sendDataOfMineOrOtherByOMElement(Statistic statistic, Long userId) {
		targetEPR = new EndpointReference(ConfigUtil.getPropertyValue("local.axis2.targetEPR"));
		// 创建request SOAP包
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// OMNamespace指定此SOAP文档名称空间
		OMNamespace om = fac.createOMNamespace("http://www.zjsafety.gov.cn/", "client");
		// 创建元素，并指定其在om指代的名称空间中,元素名必须跟services.xml重大operation的name相同
		OMElement method = fac.createOMElement("SafetyTrouble", om);
		OMElement root;
		OMElement paramOmElement;
		OMElement nomalDangerOmElement;
		OMElement dangerOmElement;
		List<Param> paramList = new ArrayList<Param>();
		List<FkArea> areas = new ArrayList<FkArea>();
		List<NomalDanger> nomalDangers = new ArrayList<NomalDanger>();
		List<Danger> dangers = new ArrayList<Danger>();
		List<Danger> dangerAll = new ArrayList<Danger>();
		List<NomalDanger> nomalDangerAll = new ArrayList<NomalDanger>();
		Param param = new Param();
		try {
			areas = loadAreas(Nbyhpc.AREA_CODE);
			int i = 0;
			for (FkArea a : areas) {
				i++;
				System.out.println("arar=" + a.getAreaName());
				root = fac.createOMElement("root", null);
				paramList = new ArrayList<Param>();
				param = loadArea(a.getAreaCode());
				param.setReportMonth(statistic.getYearMonth());
				param.setType(statistic.getType());
				paramList.add(param);
				paramOmElement = BeanUtil.getOMElement(new QName("params"), paramList.toArray(), new QName("param"), false, null);
				statistic.setAreaCode(a.getAreaCode());
				if (statistic.getType() == 1) {
					nomalDangers = loadMineByParam(statistic);
					// 添加一个参数，1表示煤矿、金属非金属矿山等工矿企业安全生产事故隐患排查治理情况月报表；0表示其他重点
					dangers = loadDangerByParamy(statistic, "1");
				} else {
					nomalDangers = loadOtherByParam(statistic);
					dangers = loadDangerByParamy(statistic, "2");
				}
				nomalDangerOmElement = BeanUtil.getOMElement(new QName("ndangers"), nomalDangers.toArray(), new QName("ndanger"), false, null);

				System.out.println("重大隐患个数: " + dangers.size());
				dangerOmElement = BeanUtil.getOMElement(new QName("dangers"), dangers.toArray(), new QName("danger"), false, null);
				root.addChild(paramOmElement);
				root.addChild(nomalDangerOmElement);
				root.addChild(dangerOmElement);
				method.addChild(root);
				nomalDangerAll.addAll(nomalDangers);
				dangerAll.addAll(dangers);
			}
			//如果是当月发送当月的报表
			if (Boolean.TRUE.equals(statistic.getNeedDaStatistic())) {
				buildDaStatisticMap(nomalDangerAll, dangerAll, statistic);
			}
			root = fac.createOMElement("root", null);
			paramList = new ArrayList<Param>();
			param = loadArea(Nbyhpc.AREA_CODE);
			param.setReportMonth(statistic.getYearMonth());
			param.setType(statistic.getType());
			paramList.add(param);
			paramOmElement = BeanUtil.getOMElement(new QName("citys"), paramList.toArray(), new QName("city"), false, null);
			root.addChild(paramOmElement);
			method.addChild(root);
			String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + "\\mine_" + statistic.getType() + ".xml";
			writeToFile(filePath, method);
			Options options = new Options();
			options.setTo(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			// 发出request SOAP
			// 同时将得到的远端由SafetyTrouble方法返回的信息保存到result
			// 通过services.xml能准确找到SafetyTrouble方法所在的文件
			sender.sendReceive(method);
			createZjDanger(dangerAll);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void buildDaStatisticMap(List<NomalDanger> nomalDangers, List<Danger> dangers, Statistic statistic) throws SQLException {
		String nowMonth = statistic.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		Map<Integer, Statistic> sMap = new HashMap<Integer, Statistic>();
		buildNormalDangerMap(nomalDangers, sMap);
		buildDangerMap(dangers, sMap, statistic);
		Map<String, Object> map = new HashMap<String, Object>();
		String cacheNum = year + ((month < 10) ? "0" : "") + month;
		String cacheName = statistic.getType() == 1 ? "nbyhpc_Axis2Mine_" + cacheNum : "nbyhpc_Axis2Other_" + cacheNum;
		Iterator<Entry<Integer, Statistic>> it = sMap.entrySet().iterator();
		Statistic total = new Statistic();
		total.setEnumName("total");
		Integer companyNum = 0;
		Integer troubNum = 0;
		Integer troubCleanNum = 0;
		Double governMoney = 0.0;
		Double normalGovernMoney = 0.0;
		Integer allCompanyNum = 0;
		Integer bigTroubNum = 0;
		Integer bigTroubCleanNum = 0;
		Integer target = 0;
		Integer goods = 0;
		Integer resource = 0;
		Integer finishDate = 0;
		Integer safetyMethod = 0;
		Integer wdw = 0;
		Integer proviceRcNum = 0;
		Integer cityRcNum = 0;
		Integer qtRcNum = 0;
		if (statistic.getType() == 1) {//参照loadStatisticForAxis2MineByParam2
			while (it.hasNext()) {
				Entry<Integer, Statistic> entity = it.next();
				Integer key = entity.getKey();
				Statistic sta = (Statistic) entity.getValue();
				sta.setNormalGovernMoney(new BigDecimal(sta.getNormalGovernMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				sta.setGovernMoney(new BigDecimal(sta.getGovernMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				map.put("s_" + key, sta);
				if (key != 13) {
					companyNum = add(companyNum, sta.getCompanyNum());
					troubNum = add(troubNum, sta.getTroubNum());
					troubCleanNum = add(troubCleanNum, sta.getTroubCleanNum());
					governMoney = add(governMoney, sta.getGovernMoney());
					normalGovernMoney = add(normalGovernMoney, sta.getNormalGovernMoney());
					allCompanyNum = add(allCompanyNum, sta.getAllCompanyNum());
					bigTroubNum = add(bigTroubNum, sta.getBigTroubNum());
					bigTroubCleanNum = add(bigTroubCleanNum, sta.getBigTroubCleanNum());
					target = add(target, sta.getTarget());
					goods = add(goods, sta.getGoods());
					resource = add(resource, sta.getResource());
					finishDate = add(finishDate, sta.getFinishDate());
					safetyMethod = add(safetyMethod, sta.getSafetyMethod());
					wdw = add(wdw, sta.getDdng5Num());
					proviceRcNum = add(proviceRcNum, sta.getProviceRcNum());
					cityRcNum = add(cityRcNum, sta.getCityRcNum());
					qtRcNum = add(qtRcNum, sta.getQtRcNum());
				}
			}
		} else {// =2 参照loadStatisticForAxis2OtherByParam2
			Integer companyNum15 = 0;
			Integer troubNum15 = 0;
			Integer troubCleanNum15 = 0;
			Double governMoney15 = 0.0;
			Double normalGovernMoney15 = 0.0;
			Integer allCompanyNum15 = 0;
			Integer bigTroubNum15 = 0;
			Integer bigTroubCleanNum15 = 0;
			Integer target15 = 0;
			Integer goods15 = 0;
			Integer resource15 = 0;
			Integer finishDate15 = 0;
			Integer safetyMethod15 = 0;
			Integer wdw15 = 0;
			Integer proviceRcNum15 = 0;
			Integer cityRcNum15 = 0;
			Integer qtRcNum15 = 0;
			Statistic s15 = new Statistic();
			while (it.hasNext()) {
				Entry<Integer, Statistic> entity = it.next();
				Integer key = entity.getKey();
				Statistic sta = (Statistic) entity.getValue();
				sta.setNormalGovernMoney(new BigDecimal(sta.getNormalGovernMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				sta.setGovernMoney(new BigDecimal(sta.getGovernMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				map.put("s_" + key, sta);
				companyNum = add(companyNum, sta.getCompanyNum());
				troubNum = add(troubNum, sta.getTroubNum());
				troubCleanNum = add(troubCleanNum, sta.getTroubCleanNum());
				governMoney = add(governMoney, sta.getGovernMoney());
				normalGovernMoney = add(normalGovernMoney, sta.getNormalGovernMoney());
				allCompanyNum = add(allCompanyNum, sta.getAllCompanyNum());
				bigTroubNum = add(bigTroubNum, sta.getBigTroubNum());
				bigTroubCleanNum = add(bigTroubCleanNum, sta.getBigTroubCleanNum());
				target = add(target, sta.getTarget());
				goods = add(goods, sta.getGoods());
				resource = add(resource, sta.getResource());
				finishDate = add(finishDate, sta.getFinishDate());
				safetyMethod = add(safetyMethod, sta.getSafetyMethod());
				wdw = add(wdw, sta.getDdng5Num());
				proviceRcNum = add(proviceRcNum, sta.getProviceRcNum());
				cityRcNum = add(cityRcNum, sta.getCityRcNum());
				qtRcNum = add(qtRcNum, sta.getQtRcNum());
				if (key == 33 || key == 27 || key == 38 || key == 39 || key == 40 || key == 41 || key == 42 || key == 34 || key == 35 || key == 36 || key == 37 || key == 43 || key == 44) {
					companyNum15 = add(companyNum15, sta.getCompanyNum());
					troubNum15 = add(troubNum15, sta.getTroubNum());
					troubCleanNum15 = add(troubCleanNum15, sta.getTroubCleanNum());
					governMoney15 = add(governMoney15, sta.getGovernMoney());
					normalGovernMoney15 = add(normalGovernMoney15, sta.getNormalGovernMoney());
					allCompanyNum15 = add(allCompanyNum15, sta.getAllCompanyNum());
					bigTroubNum15 = add(bigTroubNum15, sta.getBigTroubNum());
					bigTroubCleanNum15 = add(bigTroubCleanNum15, sta.getBigTroubCleanNum());
					target15 = add(target15, sta.getTarget());
					goods15 = add(goods15, sta.getGoods());
					resource15 = add(resource15, sta.getResource());
					finishDate15 = add(finishDate15, sta.getFinishDate());
					safetyMethod15 = add(safetyMethod15, sta.getSafetyMethod());
					wdw15 = add(wdw15, sta.getDdng5Num());
					proviceRcNum15 = add(proviceRcNum15, sta.getProviceRcNum());
					cityRcNum15 = add(cityRcNum15, sta.getCityRcNum());
					qtRcNum15 = add(qtRcNum15, sta.getQtRcNum());
				}
			}
			s15.setCompanyNum(companyNum15);
			s15.setTroubNum(troubNum15);
			s15.setTroubCleanNum(troubCleanNum15);
			s15.setGovernMoney(governMoney15);
			s15.setNormalGovernMoney(normalGovernMoney15);
			s15.setAllCompanyNum(allCompanyNum15);
			s15.setBigTroubNum(bigTroubNum15);
			s15.setBigTroubCleanNum(bigTroubCleanNum15);
			s15.setTarget(target15);
			s15.setGoods(goods15);
			s15.setResource(resource15);
			s15.setFinishDate(finishDate15);
			s15.setSafetyMethod(safetyMethod15);
			s15.setDdng5Num(wdw15);
			s15.setProviceRcNum(proviceRcNum15);
			s15.setCityRcNum(cityRcNum15);
			s15.setQtRcNum(qtRcNum15);
			map.put("s_15", s15);
			//TODO 13这个貌似没有行业和省局的这个关联，硬编码
			Statistic s13 = new Statistic(); 
			s13.setEnumName(getZjIndustryName(13));
			s13.setCompanyNum(0);
			s13.setTroubNum(0);
			s13.setTroubCleanNum(0);
			s13.setGovernMoney(0.0);
			s13.setNormalGovernMoney(0.0);
			s13.setAllCompanyNum(0);
			s13.setBigTroubNum(0);
			s13.setBigTroubCleanNum(0);
			s13.setTarget(0);
			s13.setGoods(0);
			s13.setResource(0);
			s13.setFinishDate(0);
			s13.setSafetyMethod(0);
			s13.setDdng5Num(0);
			s13.setProviceRcNum(0);
			s13.setCityRcNum(0);
			s13.setQtRcNum(0);
			map.put("s_13", s13);
		}
		total.setCompanyNum(companyNum);
		total.setTroubNum(troubNum);
		total.setTroubCleanNum(troubCleanNum);
		total.setGovernMoney(governMoney);
		total.setNormalGovernMoney(normalGovernMoney);
		total.setAllCompanyNum(allCompanyNum);
		total.setBigTroubNum(bigTroubNum);
		total.setBigTroubCleanNum(bigTroubCleanNum);
		total.setTarget(target);
		total.setGoods(goods);
		total.setResource(resource);
		total.setFinishDate(finishDate);
		total.setSafetyMethod(safetyMethod);
		total.setDdng5Num(wdw);
		total.setProviceRcNum(proviceRcNum);
		total.setCityRcNum(cityRcNum);
		total.setQtRcNum(qtRcNum);
		map.put("s_total", total);
		if (statistic.getType() == 1) {
			createDaStatistic4Mine(cacheName, cacheNum, map);
		} else {//2
			createDaStatistic4Other(cacheName, cacheNum, map);
		}
	}

	private Integer add(Integer...values) {
		int result = 0;
		for (Integer v : values) {
			if (v == null) v = 0;
			result += v;
		}
		return result;
	}
	
	private double add(Double...values) {
		BigDecimal result = new BigDecimal("0");
		for (Double v : values) {
			if (v == null) v = 0.0;
			BigDecimal vb = new BigDecimal(Double.toString(v));
			result = result.add(vb);
		}
		return result.doubleValue();
	}
	
	private void buildNormalDangerMap(List<NomalDanger> nomalDangers, Map<Integer, Statistic> sMap) {
		for (NomalDanger nomalDanger : nomalDangers) {
			Integer type = nomalDanger.getType();
			Statistic statistic = sMap.get(type);
			if (statistic == null) {
				statistic = new Statistic();
				statistic.setEnumName(getZjIndustryName(type));
				statistic.setDdng5Num(0);//防止值为空
				statistic.setProviceRcNum(0);
				statistic.setCityRcNum(0);
				statistic.setQtRcNum(0);
			}
			statistic.setCompanyNum(add(statistic.getCompanyNum(), nomalDanger.getCompany()));
			statistic.setTroubNum(add(statistic.getTroubNum(), nomalDanger.getGeneralDanger()));
			statistic.setTroubCleanNum(add(statistic.getTroubCleanNum(), nomalDanger.getGeneralDangerGovern()));

			statistic.setGovernMoney(add(statistic.getGovernMoney(), nomalDanger.getGovernMoney()));
			statistic.setNormalGovernMoney(add(statistic.getNormalGovernMoney(), nomalDanger.getNormalGovernMoney()));
			statistic.setAllCompanyNum(add(statistic.getAllCompanyNum(), nomalDanger.getShouldTroubleshooting()));

			sMap.put(type, statistic);
		}
	}

	private void buildDangerMap(List<Danger> dangers, Map<Integer, Statistic> sMap, Statistic st) {
		for (Danger danger : dangers) {
			Integer type = danger.getTradeType();
			Statistic statistic = sMap.get(type);
			if (statistic == null) {
				statistic = new Statistic();
				statistic.setEnumName(getZjIndustryName(type));
				statistic.setDdng5Num(0);//防止值为空
				statistic.setProviceRcNum(0);
				statistic.setCityRcNum(0);
				statistic.setQtRcNum(0);
			}
			statistic.setBigTroubNum(add(statistic.getBigTroubNum(), 1));
			
			Integer bigTroubCleanNum = statistic.getBigTroubCleanNum() == null ? 0 : statistic.getBigTroubCleanNum();
			//未整改，这边的数字和省局的统计数字是相反的，省局统计的是STATE=1
			//这里da_statistic里的值是显示已整改 bigtroubNum - bigTroubCleanNum，因此保存的是未整改的值
			if (Integer.valueOf(0).equals(danger.getState())) {
				bigTroubCleanNum++;
			}
			statistic.setBigTroubCleanNum(bigTroubCleanNum);
			
			Integer target = statistic.getTarget() == null ? 0 : statistic.getTarget();
			if (Integer.valueOf(0).equals(danger.getState())) {
				target = add(target, danger.getTargetTask()); 
			}
			statistic.setTarget(target);
			
			Integer goods = statistic.getGoods() == null ? 0 : statistic.getGoods();
			if (Integer.valueOf(0).equals(danger.getState())) {
				goods = add(goods, danger.getGoods()); 
			}
			statistic.setGoods(goods);
			
			Integer resource = statistic.getResource() == null ? 0 : statistic.getResource();
			if (Integer.valueOf(0).equals(danger.getState())) {
				resource = add(resource, danger.getWorker()); 
			}
			statistic.setResource(resource);
			
			Integer finishDate = statistic.getFinishDate() == null ? 0 : statistic.getFinishDate();
			if (Integer.valueOf(0).equals(danger.getState())) {
				finishDate = add(finishDate, danger.getGovernDate()); 
			}
			statistic.setFinishDate(finishDate);
			
			Integer safetyMethod = statistic.getSafetyMethod() == null ? 0 : statistic.getSafetyMethod();
			if (Integer.valueOf(0).equals(danger.getState())) {
				safetyMethod = add(safetyMethod, danger.getSafetyMethod()); 
			}
			statistic.setSafetyMethod(safetyMethod);

			Integer wdw = statistic.getDdng5Num() == null ? 0 : statistic.getDdng5Num();
			if (Integer.valueOf(0).equals(danger.getState()) && Integer.valueOf(1).equals(danger.getSafetyMethod())
					&& Integer.valueOf(1).equals(danger.getGovernDate()) && Integer.valueOf(1).equals(danger.getWorker())
					&& Integer.valueOf(1).equals(danger.getGoods()) && Integer.valueOf(1).equals(danger.getTargetTask())) {
				wdw++;
			}
			statistic.setDdng5Num(wdw);
			
			if (Integer.valueOf(3).equals(danger.getGuapaiLevel())) {
				Integer proviceRcNum = statistic.getProviceRcNum() == null ? 0 : statistic.getProviceRcNum();
				proviceRcNum++;
				statistic.setProviceRcNum(proviceRcNum);
			} else if (Integer.valueOf(2).equals(danger.getGuapaiLevel())) {
				Integer cityRcNum = statistic.getCityRcNum() == null ? 0 : statistic.getCityRcNum();
				cityRcNum++;
				statistic.setCityRcNum(cityRcNum);
			} else if (Integer.valueOf(1).equals(danger.getGuapaiLevel())) {
				Integer qtRcNum = statistic.getQtRcNum() == null ? 0 : statistic.getQtRcNum();
				qtRcNum++;
				statistic.setQtRcNum(qtRcNum);
			}
			sMap.put(type, statistic);
		}
	}

	private String getZjIndustryName(Integer type) {
		ResultSet rs = null;
		try {
			PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.name from zj_industry t where t.zj_id = '" + type + "'");
			rs = pState.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	/**
	 * 向省隐患排查传送数据（new）
	 * 
	 * @param statistic
	 * @param userId
	 * @return
	 */
	public Boolean sendDataOfMineOrOtherByOMElement1(Statistic statistic, Long userId) {
		targetEPR = new EndpointReference(ConfigUtil.getPropertyValue("local.axis2.targetEPR"));
		// 创建request SOAP包
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// OMNamespace指定此SOAP文档名称空间
		OMNamespace om = fac.createOMNamespace("http://www.zjsafety.gov.cn/", "client");
		// 创建元素，并指定其在om指代的名称空间中,元素名必须跟services.xml重大operation的name相同
		OMElement method = fac.createOMElement("SafetyTrouble", om);
		OMElement root;
		OMElement paramOmElement;
		OMElement nomalDangerOmElement;
		OMElement dangerOmElement;
		List<Param> paramList = null;
		List<FkArea> areas = null;
		List<NomalDanger> nomalDangers = null;
		List<Danger> dangers = null;
		List<Danger> dangerAll = new ArrayList<Danger>();
		String reportTime = statistic.getYearMonth();
		Integer type = statistic.getType();
		try {
			areas = loadAreas(Nbyhpc.AREA_CODE);
			Long areaCode = 0l;
			for (FkArea a : areas) {
				areaCode = a.getAreaCode();
				ZjMineReport mine = mineFacadeIface.loadMineReports(areaCode, reportTime, type);
				if (mine != null) {
					paramList = this.paramListInit(areaCode, reportTime, type, mine);
				} else {
					continue;
				}

				root = fac.createOMElement("root", null);

				paramOmElement = BeanUtil.getOMElement(new QName("params"), paramList.toArray(), new QName("param"), false, null);

				nomalDangers = this.mineReportDetailsTojNomalDangers(mineDetailFacadeIface.loadMineReportDetailsByMine(mine.getId(), null));
				nomalDangerOmElement = BeanUtil.getOMElement(new QName("ndangers"), nomalDangers.toArray(), new QName("ndanger"), false, null);

				dangers = this.bigTroublesTojDangers(bigTroubleFacadeIface.loadBigTroubles(areaCode, type));
				dangerOmElement = BeanUtil.getOMElement(new QName("dangers"), dangers.toArray(), new QName("danger"), false, null);

				root.addChild(paramOmElement);
				root.addChild(nomalDangerOmElement);
				root.addChild(dangerOmElement);
				method.addChild(root);

				dangerAll.addAll(dangers);
			}

			root = fac.createOMElement("root", null);
			ZjMineReport mine = mineFacadeIface.loadMineReports1(Nbyhpc.AREA_CODE, reportTime, type);
			if (mine != null) {
				paramList = this.paramListInit(Nbyhpc.AREA_CODE, reportTime, type, mine);
			} else {
				return false;
			}
			paramOmElement = BeanUtil.getOMElement(new QName("citys"), paramList.toArray(), new QName("city"), false, null);
			root.addChild(paramOmElement);
			method.addChild(root);

			Options options = new Options();
			options.setTo(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			// 发出request SOAP
			// 同时将得到的远端由SafetyTrouble方法返回的信息保存到result
			// 通过services.xml能准确找到SafetyTrouble方法所在的文件
			sender.sendReceive(method);
			createZjDanger(dangerAll);
			ZjSend send = new ZjSend();
			send.setReportMonth(reportTime);
			send.setType(type);
			send.setUserId(userId);
			createSend(send);
			String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + "\\mine_new_" + type + ".xml";
			writeToFile(filePath, method);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 执法治违
	 * 
	 * @param statistic
	 * @param userId
	 * @return
	 */
	public String sendDataDfzwByOMElement(ZjDfzwReport entity, Long userId) {
		targetEPR = new EndpointReference(ConfigUtil.getPropertyValue("local.axis2.targetEPR"));
		// 创建request SOAP包
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// OMNamespace指定此SOAP文档名称空间
		OMNamespace om = fac.createOMNamespace("http://www.zjsafety.gov.cn/", "client");
		// 创建元素，并指定其在om指代的名称空间中,元素名必须跟services.xml重大operation的name相同
		OMElement method = fac.createOMElement("SafetyTrouble", om);
		OMElement root;
		OMElement dfzwOmElement;
		OMElement dfzwDetailsOmElement;
		DfzwReport dfzw = null;
		List<DfzwReport> dfzwList = null;
		List<DfzwReportDetail> dfzwDetails = null;
		String reportDateBegin = "";
		String reportDateEnd = "";
		if (entity.getReportDateEnd().getTime() - entity.getReportDateBegin().getTime() > 1000 * 60 * 60 * 24 * 10) {
			reportDateBegin = DateUtils.date2Str(entity.getReportDateBegin(), "yyyy-MM-dd");
			reportDateEnd = DateUtils.date2Str(entity.getReportDateEnd(), "yyyy-MM-dd");
		} else {
			reportDateBegin = DateUtils.date2Str(DateUtils.getDateAddDay(entity.getReportDateBegin(), 2), "yyyy-MM-dd");
			reportDateEnd = DateUtils.date2Str(DateUtils.getDateAddDay(entity.getReportDateEnd(), 2), "yyyy-MM-dd");
		}
		try {
			List<FkArea> areas = loadAreas(Nbyhpc.AREA_CODE);
			for (FkArea a : areas) {
				root = fac.createOMElement("root", null);
				dfzwList = new ArrayList<DfzwReport>();
				dfzw = this.loadAreaForDfzw(a.getAreaCode());
				dfzw.setReportDateBeginStr(reportDateBegin);
				dfzw.setReportDateEndStr(reportDateEnd);
				// dfzw.setAreaCode(a.getAreaCode());
				dfzw.setState(1);
				dfzwList.add(dfzw);
				dfzwOmElement = BeanUtil.getOMElement(new QName("dfzws"), dfzwList.toArray(), new QName("dfzw"), false, null);
				root.addChild(dfzwOmElement);
				dfzwDetails = dfzwFacadeIface.loadDetailsForAxis2(a.getAreaCode(), entity);
				dfzwDetailsOmElement = BeanUtil.getOMElement(new QName("dfzwDetails"), dfzwDetails.toArray(), new QName("dfzwDetail"), false, null);
				root.addChild(dfzwDetailsOmElement);
				method.addChild(root);
			}
			root = fac.createOMElement("root", null);
			dfzwList = new ArrayList<DfzwReport>();
			dfzw = loadAreaForDfzw(Nbyhpc.AREA_CODE);
			dfzw.setReportDateBeginStr(reportDateBegin);
			dfzw.setReportDateEndStr(reportDateEnd);
			// dfzw.setAreaCode(Nbyhpc.AREA_CODE);
			dfzw.setChargeMan(entity.getChargeMan());
			dfzw.setState(2);
			dfzwList.add(dfzw);
			dfzwOmElement = BeanUtil.getOMElement(new QName("dcitys"), dfzwList.toArray(), new QName("dcity"), false, null);
			root.addChild(dfzwOmElement);
			method.addChild(root);
			Options options = new Options();
			options.setTo(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			// 发出request SOAP
			// 同时将得到的远端由SafetyTrouble方法返回的信息保存到result
			// 通过services.xml能准确找到SafetyTrouble方法所在的文件
			sender.sendReceive(method);
			ZjSend send = new ZjSend();
			send.setType(5);
			send.setUserId(userId);
			createSend(send);
			String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + "\\dfzw_5.xml";
			writeToFile(filePath, method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执法治违处罚
	 * 
	 * @param statistic
	 * @param userId
	 * @return
	 */
	public String sendDataDfzwPunishByOMElement(ZjDfzwPunish entity, Long userId) {
		targetEPR = new EndpointReference(ConfigUtil.getPropertyValue("local.axis2.targetEPR"));
		// 创建request SOAP包
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// OMNamespace指定此SOAP文档名称空间
		OMNamespace om = fac.createOMNamespace("http://www.zjsafety.gov.cn/", "client");
		// 创建元素，并指定其在om指代的名称空间中,元素名必须跟services.xml重大operation的name相同
		OMElement method = fac.createOMElement("SafetyTrouble", om);
		OMElement root;
		OMElement dfzwPunishOmElement;
		OMElement nomalDangerOmElement;
		List<DfzwPunish> dfzwPunishList = new ArrayList<DfzwPunish>();
		List<FkArea> areas = new ArrayList<FkArea>();
		List<DfzwPunishDetail> dfzwDetails = new ArrayList<DfzwPunishDetail>();
		DfzwPunish dfzwPunish = new DfzwPunish();
		String reportDateBegin = "";
		String reportDateEnd = "";
		if (entity.getReportDateEnd().getTime() - entity.getReportDateBegin().getTime() > 1000 * 60 * 60 * 24 * 10) {
			reportDateBegin = DateUtils.date2Str(entity.getReportDateBegin(), "yyyy-MM-dd");
			reportDateEnd = DateUtils.date2Str(entity.getReportDateEnd(), "yyyy-MM-dd");
		} else {
			reportDateBegin = DateUtils.date2Str(DateUtils.getDateAddDay(entity.getReportDateBegin(), 2), "yyyy-MM-dd");
			reportDateEnd = DateUtils.date2Str(DateUtils.getDateAddDay(entity.getReportDateEnd(), 2), "yyyy-MM-dd");
		}
		try {
			areas = loadAreas(Nbyhpc.AREA_CODE);
			for (FkArea a : areas) {
				root = fac.createOMElement("root", null);
				dfzwPunishList = new ArrayList<DfzwPunish>();
				dfzwPunish = this.loadAreaForDfzwPunish(a.getAreaCode());
				dfzwPunish.setReportDateBeginStr(reportDateBegin);
				dfzwPunish.setReportDateEndStr(reportDateEnd);
				// dfzwPunish.setAreaCode(a.getAreaCode());
				dfzwPunish.setState(1);
				dfzwPunishList.add(dfzwPunish);
				dfzwPunishOmElement = BeanUtil.getOMElement(new QName("dpfzws"), dfzwPunishList.toArray(), new QName("dpfzw"), false, null);
				root.addChild(dfzwPunishOmElement);
				dfzwDetails = dfzwPunishFacadeIface.loadDetailsForAxis2(a.getAreaCode(), entity);
				nomalDangerOmElement = BeanUtil.getOMElement(new QName("dpfzwDetails"), dfzwDetails.toArray(), new QName("dpfzwDetail"), false, null);
				root.addChild(nomalDangerOmElement);
				method.addChild(root);
			}
			root = fac.createOMElement("root", null);
			dfzwPunishList = new ArrayList<DfzwPunish>();
			dfzwPunish = loadAreaForDfzwPunish(Nbyhpc.AREA_CODE);
			dfzwPunish.setReportDateBeginStr(reportDateBegin);
			dfzwPunish.setReportDateEndStr(reportDateEnd);
			// dfzwPunish.setAreaCode(Nbyhpc.AREA_CODE);
			dfzwPunish.setChargeMan(entity.getChargeMan());
			dfzwPunish.setState(2);
			dfzwPunishList.add(dfzwPunish);
			dfzwPunishOmElement = BeanUtil.getOMElement(new QName("dpcitys"), dfzwPunishList.toArray(), new QName("dpcity"), false, null);
			root.addChild(dfzwPunishOmElement);
			method.addChild(root);
			Options options = new Options();
			options.setTo(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			// 发出request SOAP
			// 同时将得到的远端由SafetyTrouble方法返回的信息保存到result
			// 通过services.xml能准确找到SafetyTrouble方法所在的文件
			sender.sendReceive(method);
			ZjSend send = new ZjSend();
			send.setType(6);
			send.setUserId(userId);
			createSend(send);
			String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + "\\dfzwPunish_6.xml";
			writeToFile(filePath, method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// public List<DfzwReportDetail> loadDfzwReportDetailsForAxis2(Long
	// areaCode, ZjDfzwReport entity) throws Exception {
	// List<DfzwReportDetail> list =
	// dfzwFacadeIface.loadDetailsForAxis2(areaCode, entity);
	// for(){
	//
	// }
	// return null;
	// }
	//
	// public List<DfzwPunishDetail> loadDfzwPunishDetailsForAxis2(Long
	// areaCode, ZjDfzwPunish entity) throws Exception {
	// return null;
	// }

	public List<Param> paramListInit(Long areaCode, String reportTime, Integer type, ZjMineReport mine) {
		List<Param> paramList = new ArrayList<Param>();
		Param param = new Param();
		param.setAreaCode(areaCode);
		param.setChargeMan(mine.getChargeMan());
		param.setFillMan(mine.getFillMan());
		param.setTel(mine.getTel());
		param.setReportMonth(reportTime);
		param.setType(type);
		paramList.add(param);
		return paramList;
	}

	public String sendDataOfIllegalByOMElement(Statistic statistic, Long userId) {
		targetEPR = new EndpointReference(ConfigUtil.getPropertyValue("local.axis2.targetEPR"));
		// 创建request SOAP包
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// OMNamespace指定此SOAP文档名称空间
		OMNamespace om = fac.createOMNamespace("http://www.zjsafety.gov.cn/", "client");
		// 创建元素，并指定其在om指代的名称空间中,元素名必须跟services.xml重大operation的name相同
		OMElement method = fac.createOMElement("SafetyTrouble", om);
		OMElement root;
		OMElement threeParamOmElement;
		OMElement threeIllegalOmElement;
		List<ThreeParam> threeParams = new ArrayList<ThreeParam>();
		List<FkArea> areas = new ArrayList<FkArea>();
		List<ThreeIllegal> threeIllegals = new ArrayList<ThreeIllegal>();
		ThreeParam threeParam = new ThreeParam();
		try {
			areas = loadAreas(Nbyhpc.AREA_CODE);
			for (FkArea a : areas) {
				root = fac.createOMElement("root", null);
				threeParams = new ArrayList<ThreeParam>();
				threeParam = loadAreaForThreeIllegal(a.getAreaCode());
				// System.out.println("statistic.getYearMonth(): "+statistic.getYearMonth());

				threeParam.setReportMonth(statistic.getYearMonth());
				threeParams.add(threeParam);
				threeParamOmElement = BeanUtil.getOMElement(new QName("tparams"), threeParams.toArray(), new QName("tparam"), false, null);
				statistic.setAreaCode(a.getAreaCode());
				threeIllegals = loadThreeIllegalByParam(statistic);
				threeIllegalOmElement = BeanUtil.getOMElement(new QName("illegals"), threeIllegals.toArray(), new QName("illegal"), false, null);
				root.addChild(threeParamOmElement);
				root.addChild(threeIllegalOmElement);
				method.addChild(root);
			}
			root = fac.createOMElement("root", null);
			threeParams = new ArrayList<ThreeParam>();
			threeParam = loadAreaForThreeIllegal(Nbyhpc.AREA_CODE);
			threeParam.setReportMonth(statistic.getYearMonth());
			threeParams.add(threeParam);
			threeParamOmElement = BeanUtil.getOMElement(new QName("tcitys"), threeParams.toArray(), new QName("tcity"), false, null);
			root.addChild(threeParamOmElement);
			method.addChild(root);
			Options options = new Options();
			options.setTo(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			// 发出request SOAP
			// 同时将得到的远端由SafetyTrouble方法返回的信息保存到result
			// 通过services.xml能准确找到SafetyTrouble方法所在的文件
			sender.sendReceive(method);
			ZjSend send = new ZjSend();
			send.setReportMonth(statistic.getYearMonth());
			send.setType(statistic.getType());
			send.setUserId(userId);
			createSend(send);
			String filePath = ConfigUtil.getPropertyValue("xml.release.dir") + "\\illegal.xml";
			writeToFile(filePath, method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加参数
	 * 
	 * @param reportParams
	 * @throws ApplicationAccessException
	 */
	public void createReportParam(List<ZjReportParam> reportParams, Long userId) throws ApplicationAccessException {
		for (ZjReportParam reportParam : reportParams) {
			reportParam.setUserId(userId);
			reportParamPersistenceIface.createReportParam(reportParam);
		}
	}

	/**
	 * 删除所有参数
	 * 
	 * @throws ApplicationAccessException
	 */
	private void deleteReportParam() throws ApplicationAccessException {
		List<ZjReportParam> reportParams = loadReportParams();
		for (ZjReportParam reportParam : reportParams) {
			reportParamPersistenceIface.deleteReportParam(reportParam);
		}
	}

	/**
	 * 查询所有参数
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjReportParam> loadReportParams() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(ZjReportParam.class, "zrp");
		return reportParamPersistenceIface.loadReportParams(detachedCriteriaProxy, null);
	}

	/**
	 * 修改参数
	 * 
	 * @param params
	 * @throws ApplicationAccessException
	 */
	public void updateReportParam(List<ZjReportParam> params, Long userId) throws ApplicationAccessException {
		deleteReportParam();
		createReportParam(params, userId);
	}

	/**
	 * 为参数列表查询区域
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreasForReport() throws ApplicationAccessException {
		List<FkArea> areas = new ArrayList<FkArea>();
		FkArea area;
		String sql = "select fa.area_code,fa.area_name from zj_area za left join " + " fk_area fa on fa.area_code=za.area_code order by fa.sort_num ";
		// System.out.println("区域sql: "+sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				area = new FkArea();
				area.setAreaCode(res.getLong(1));
				area.setAreaName(res.getString(2));
				areas.add(area);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return areas;
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
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")  or  this_.id=3020"));
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
	public Param loadArea(Long areaCode) throws ApplicationAccessException {
		List<Param> params = new ArrayList<Param>();
		String sql = "select a.zj_area_code,p.charge_man,p.fill_man,p.tel from zj_report_param p " + " left join zj_area a on a.area_code=p.area_code where p.area_code=" + areaCode;
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				Param param = new Param();
				param.setAreaCode(res.getLong(1));
				param.setChargeMan(res.getString(2));
				param.setFillMan(res.getString(3));
				param.setTel(res.getString(4));
				params.add(param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (params != null && params.size() == 1) {
			return params.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询区域对象
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DfzwReport loadAreaForDfzw(Long areaCode) throws ApplicationAccessException {
		List<DfzwReport> params = new ArrayList<DfzwReport>();
		String sql = "select a.zj_area_code,p.charge_man,p.fill_man,p.tel from zj_report_param p " + " left join zj_area a on a.area_code=p.area_code where p.area_code=" + areaCode;
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DfzwReport param = new DfzwReport();
				param.setAreaCode(res.getLong(1));
				param.setChargeMan(res.getString(2));
				param.setFillMan(res.getString(3));
				param.setTel(res.getString(4));
				params.add(param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (params != null && params.size() == 1) {
			return params.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询区域对象
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DfzwPunish loadAreaForDfzwPunish(Long areaCode) throws ApplicationAccessException {
		List<DfzwPunish> params = new ArrayList<DfzwPunish>();
		String sql = "select a.zj_area_code,p.charge_man,p.fill_man,p.tel from zj_report_param p " + " left join zj_area a on a.area_code=p.area_code where p.area_code=" + areaCode;
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				DfzwPunish param = new DfzwPunish();
				param.setAreaCode(res.getLong(1));
				param.setChargeMan(res.getString(2));
				param.setFillMan(res.getString(3));
				param.setTel(res.getString(4));
				params.add(param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (params != null && params.size() == 1) {
			return params.get(0);
		} else {
			return null;
		}
	}

	public ThreeParam loadAreaForThreeIllegal(Long areaCode) throws ApplicationAccessException {
		List<ThreeParam> threeParams = new ArrayList<ThreeParam>();
		String sql = "select a.zj_area_code,p.charge_man,p.fill_man,p.tel from zj_report_param p " + " left join zj_area a on a.area_code=p.area_code where p.area_code=" + areaCode;
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				ThreeParam threeParam = new ThreeParam();
				threeParam.setAreaCode(res.getLong(1));
				threeParam.setChargeMan(res.getString(2));
				threeParam.setFillMan(res.getString(3));
				threeParam.setTel(res.getString(4));
				threeParams.add(threeParam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (threeParams != null && threeParams.size() == 1) {
			return threeParams.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 查询重大隐患报送数据
	 * 
	 * @param param
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Danger> loadDangerByParamy(Statistic st, String sendType) throws ApplicationAccessException {
		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		int backup_date = 0; // 历史表查询的日期

		List<Danger> dangers = new ArrayList<Danger>();
		Long areaCode = st.getAreaCode();
		Integer type = st.getType();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		// if (!nowMonth.equals(defaultMonth)) {
		mDateTime = nextMonthTime;
		// }

		int year0 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		if (year < 2013 || (year == 2013 && month <= 11 && month >= 1)) { // 往月
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			backup_date = 201311;
		} else if (year > year0 || (year0 == year && month >= month1)) { // 在当前日期之后
			da_company = "da_company";
			da_company_industry_rel = "da_company_industry_rel";
			da_industry_parameter = "da_industry_parameter";
			da_company_pass = "da_company_pass";
		} else {
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			if (month < 10) {
				backup_date = Integer.parseInt(year + "0" + month);
			} else {
				backup_date = Integer.parseInt(year + "" + month);
			}
		}

		// add by huangjl 对企业添加过滤掉当前月份的限制条件
		String createTime = year + "-" + month + "-01";
		String createTimeSql = " and dc.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

		// add by huangjl 过滤掉其他行业中的电力生产
		String qcDlsqSql1 = " ";
		String qcDlsqSql2 = " ";
		if ("1".equals(sendType)) {
			String da_company_industry_relSql = " " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + " ";
			qcDlsqSql1 = " and  not exists (select par_da_com_id from  " + da_company_industry_rel + "  rel where par_da_ind_id=1438 " + da_company_industry_relSql + " and  dc.id=par_da_com_id ) ";
			qcDlsqSql2 = " and  not exists (select par_da_com_id from  da_company_industry_rell  where par_da_ind_id=1438 and  dc.id=par_da_com_id ) ";
		}

		String startTime1 = year + "-01-01";
		// String oldDangerSql=" (dd.is_deleted = 0 and create_time <to_date('"
		// + startTime1 +
		// "', 'yyyy-MM-dd') and (dd.id not in (select par_da_dan_id from  da_danger_gorver)  or dd.id in (select par_da_dan_id from  da_danger_gorver gor where to_char(gor.create_time, 'yyyy')>to_char(dd.create_time, 'yyyy') and  to_char(gor.create_time, 'yyyy')='"+year+"'))) ";
		int pYear = year - 1;
		String oldDangerSql = " (dd.is_deleted = 0 and to_char(dd.create_time,'yyyy')='" + pYear + "' and ( not exists (select par_da_dan_id from  da_danger_gorver where  dd.id=par_da_dan_id)  or  exists (select par_da_dan_id from  da_danger_gorver gor where to_char(gor.create_time, 'yyyy')>to_char(dd.create_time, 'yyyy') and dd.id=par_da_dan_id and  to_char(gor.create_time, 'yyyy')='" + year + "'))) ";

		String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
		String sqlDanger = " and dd.create_time between to_date('" + startTime1 + "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlGorver = " and create_time < to_date('" + mDateTime + "','yyyy-MM-dd') ";
		String sql1 = "select zi.zj_id,zi.type,dc.company_name,dc.reg_address,dd.description,dd.target, " + " dd.resources,dd.safety_method,dd.goods,count(dd.finish_date), " + " (select ddg.create_time from da_danger_gorver ddg where ddg.is_deleted=0 " + sqlGorver + " and ddg.par_da_dan_id =dd.id) stateTime ,dd.id,dc.id  as  companyId" + " from da_danger dd left join " + da_company_pass + " dcp on dcp.par_da_com_id=dd.par_da_com_id " + " left join " + da_company + " dc on dc.id= dcp.par_da_com_id " + " left join " + da_company_industry_rel + " dcir on dcir.par_da_com_id=dc.id  " + " left join " + da_industry_parameter + " dip on dip.id = dcir.par_da_ind_id " + " left join zj_industry zi on zi.id=dip.zj_type " + "  " + " where dc.is_deleted=0  " + createTimeSql + "  and dcp.is_deleted=0 and dcp.is_affirm=1 " + " and dip.is_deleted=0  and dip.zj_type is not null " + (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + "  "
				+ (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and dcir.backup_date=" + backup_date + "   " : "") + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and dip.backup_date=" + backup_date + "   " : "") + " and dc.second_area=" + areaCode + " and zi.type= " + type + qcDlsqSql1 + " and ((dd.is_deleted=0  and dd.id is not null" + sqlDanger + ") or " + oldDangerSql + " ) group by dd.id,zi.zj_id,zi.type,dc.company_name,dc.reg_address,dd.description, " + " dd.target,dd.resources,dd.safety_method,dc.id,dd.goods";

		// liulj 包含已删除行业的企业的隐患

		String sql2 = "select zi.zj_id,zi.type,dc.company_name,dc.reg_address,dd.description,dd.target, " + " dd.resources,dd.safety_method,dd.goods,count(dd.finish_date), " + " (select ddg.create_time from da_danger_gorver ddg where ddg.is_deleted=0 " + sqlGorver + " and ddg.par_da_dan_id =dd.id) stateTime ,dd.id,dc.id  as  companyId" + " from da_danger dd left join " + da_company_pass + " dcp on dcp.par_da_com_id=dd.par_da_com_id " + " left join " + da_company + "  dc on dc.id= dcp.par_da_com_id " + " left join da_company_industry_rell dcir on dcir.par_da_com_id=dc.id  " + " left join " + da_industry_parameter + " dip on dip.id = dcir.par_da_ind_id " + " left join zj_industry zi on zi.id=dip.zj_type " + "  " + " where dc.is_deleted=0 " + createTimeSql + " and dcp.is_deleted=0 and dcp.is_affirm=1  " + (da_company_pass.equals("da_company_pass_his") ? "and dcp.backup_date=" + backup_date + "   " : "") + "  "
				+ (da_company.equals("da_company_his") ? "and dc.backup_date=" + backup_date + "   " : "") + "    " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and dip.backup_date=" + backup_date + " " : "") + "  and dip.is_deleted=0  and dip.zj_type is not null " + " and dc.second_area=" + areaCode + " and zi.type= " + type + qcDlsqSql2 + " and ((dd.is_deleted=0  and dd.id is not null" + sqlDanger + ") or  " + oldDangerSql + " ) group by dd.id,zi.zj_id,zi.type,dc.company_name,dc.reg_address,dd.description, " + " dd.target,dd.resources,dd.safety_method,dc.id,dd.goods";

		// liulj
		String sql = "select  c.*,(case x1.levels  when 'city_city'  then  2   when 'county_county'  then  1   when 'province_city'  then  3 end) as guapai_level  from   (" + sql1 + "   )  c  left join  (select  * from da_rollcall_company  ) x1  on c.id = x1.par_da_dan_id ";

		sql += " union " + "select  c.*,(case x1.levels  when 'city_city'  then  2   when 'county_county'  then  1   when 'province_city'  then  3 end) as guapai_level  from   (" + sql2 + "   )  c  left join  (select  * from da_rollcall_company  ) x1  on c.id = x1.par_da_dan_id ";

		System.out.println("--big trouble:" + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				Danger danger = new Danger();
				danger.setTradeType(res.getInt(1));
				danger.setTableType(res.getInt(2));
				danger.setCompanyName(res.getString(3));
				danger.setAddress(res.getString(4));
				danger.setContent(res.getString(5));

				// 五到位
				danger.setTargetTask(res.getInt(6));
				danger.setWorker(res.getInt(7));
				danger.setSafetyMethod(res.getInt(8));
				danger.setGoods(res.getInt(9));
				danger.setGovernDate(res.getInt(10));

				// liulj
				if (res.getString(14) != null) {
					danger.setGuapaiLevel(res.getInt(14));
				}

				if (res.getString(11) != null) {
					danger.setStateTime(res.getString(11).substring(0, 7));
				} else {
					danger.setStateTime(res.getString(11));
				}
				if (res.getString(11) != null) {
					danger.setState(1);
				} else {
					danger.setState(0);
				}
				danger.setId(res.getLong(12));
				danger.setNbDangerId(res.getLong(12));
				danger.setRemark(res.getString(13));
				dangers.add(danger);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dangers;
	}

	/**
	 * 查询重大隐患报送数据
	 * 
	 * @param param
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Danger> loadDangerCreateBigTrouble(Statistic st) throws ApplicationAccessException {
		List<Danger> dangers = new ArrayList<Danger>();
		Long areaCode = st.getAreaCode();
		Integer type = st.getType();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}
		String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
		String sqlDanger = " and dd.create_time between to_date('" + startTime + "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlGorver = " and create_time < to_date('" + mDateTime + "','yyyy-MM-dd') ";
		String sql = "select zi.zj_id,zi.type,dc.company_name,dc.reg_address,dd.description,dd.target, " + " dd.resources,dd.safety_method,dd.goods,count(dd.finish_date), " + " (select ddg.create_time from da_danger_gorver ddg where ddg.is_deleted=0 " + sqlGorver + " and ddg.par_da_dan_id =dd.id) stateTime ,dd.id" + " from da_danger dd left join da_company_pass dcp on dcp.par_da_com_id=dd.par_da_com_id " + " left join da_company dc on dc.id= dcp.par_da_com_id " + " left join da_company_industry_rel dcir on dcir.par_da_com_id=dc.id  " + " left join da_industry_parameter dip on dip.id = dcir.par_da_ind_id " + " left join zj_industry zi on zi.id=dip.zj_type " + " left join zj_danger zd on zd.danger_id=dd.id" + " where dc.is_deleted=0 and dcp.is_deleted=0 and dcp.is_affirm=1 " + " and dip.is_deleted=0  and dip.zj_type is not null and dd.is_deleted=0 " + " and dd.id not in (select t.id from zj_big_trouble t ) "// zj_big_trouble表的ID跟重大隐患的ID一样
				+ "  and dd.id is not null" + " and dc.second_area=" + areaCode + " and zi.type= " + type + sqlDanger + " group by dd.id,zi.zj_id,zi.type,dc.company_name,dc.reg_address,dd.description, " + " dd.target,dd.resources,dd.safety_method,dd.goods";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		// System.out.println("----------------------------sql------------------------------------------------------"
		// + sql);
		try {
			while (res.next()) {
				ZjBigTrouble danger = new ZjBigTrouble();
				danger.setTradeType(res.getInt(1));
				danger.setTableType(res.getInt(2));
				danger.setCompanyName(res.getString(3));
				danger.setAddress(res.getString(4));
				danger.setContent(res.getString(5));
				danger.setTargetTask(res.getInt(6));
				danger.setWorker(res.getInt(7));
				danger.setSafetyMethod(res.getInt(8));
				danger.setGoods(res.getInt(9));
				danger.setGovernDate(res.getInt(10));
				if (res.getString(11) != null) {
					danger.setStateTime(res.getString(11).substring(0, 7));
				} else {
					danger.setStateTime(res.getString(11));
				}
				if (res.getString(11) != null) {
					danger.setState(1);
				} else {
					danger.setState(0);
				}
				danger.setId(res.getLong(12));
				bigTroubleFacadeIface.createBigTrouble(danger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dangers;
	}

	/**
	 * BigTrouble数据初始化
	 * 
	 * @param param
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Danger> createBigTroubleInit() throws ApplicationAccessException {
		List<Danger> dangers = new ArrayList<Danger>();
		String sql = "select zi.zj_id, zi.type, dc.company_name,   dc.reg_address, dd.description,  dd.target, dd.resources,  dd.safety_method, dd.goods,   count(dd.finish_date), (select ddg.create_time from da_danger_gorver ddg where ddg.is_deleted = 0 and create_time < to_date('2012-07-15', 'yyyy-MM-dd') and ddg.par_da_dan_id = dd.id) stateTime,   dd.id,dd.user_id  from da_danger dd left join da_company_pass dcp on dcp.par_da_com_id = dd.par_da_com_id left join da_company dc on dc.id = dcp.par_da_com_id  left join da_company_industry_rel dcir on dcir.par_da_com_id = dc.id left join da_industry_parameter dip on dip.id = dcir.par_da_ind_id left join zj_industry zi on zi.id = dip.zj_type where dc.is_deleted = 0 and dcp.is_deleted = 0 and dip.is_deleted = 0 and dd.is_deleted = 0  and dd.id in (select t.danger_id from zj_danger t) group by dd.id, zi.zj_id, zi.type, dc.company_name,dc.reg_address,dd.description, dd.target, dd.resources, dd.safety_method, dd.goods,dd.user_id";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				ZjBigTrouble danger = new ZjBigTrouble();
				danger.setTradeType(res.getInt(1));
				danger.setTableType(res.getInt(2));
				danger.setCompanyName(res.getString(3));
				danger.setAddress(res.getString(4));
				danger.setContent(res.getString(5));
				danger.setTargetTask(res.getInt(6));
				danger.setWorker(res.getInt(7));
				danger.setSafetyMethod(res.getInt(8));
				danger.setGoods(res.getInt(9));
				danger.setGovernDate(res.getInt(10));
				if (res.getString(11) != null) {
					danger.setStateTime(res.getString(11).substring(0, 7));
				} else {
					danger.setStateTime(res.getString(11));
				}
				if (res.getString(11) != null) {
					danger.setState(1);
				} else {
					danger.setState(0);
				}
				danger.setReportMonth(danger.getStateTime());
				danger.setId(res.getLong(12));
				FkUser user = new FkUser();
				user.setId(res.getLong(13));
				danger.setUserId(user);
				bigTroubleFacadeIface.createBigTrouble(danger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dangers;
	}

	/**
	 * 查询矿山等行业的报送数据
	 * 
	 * @param param
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<NomalDanger> loadMineByParam(Statistic st) throws ApplicationAccessException {

		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		int backup_date = 0; // 历史表查询的日期

		List<NomalDanger> nomalDangers = new ArrayList<NomalDanger>();
		Long areaCode = st.getAreaCode();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		int year0 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		if (year < 2013 || (year == 2013 && month <= 11 && month >= 1)) { // 往月
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			backup_date = 201311;
		} else if (year > year0 || (year0 == year && month >= month1)) { // 在当前日期之后
			da_company = "da_company";
			da_company_industry_rel = "da_company_industry_rel";
			da_industry_parameter = "da_industry_parameter";
			da_company_pass = "da_company_pass";
		} else {
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			if (month < 10) {
				backup_date = Integer.parseInt(year + "0" + month);
			} else {
				backup_date = Integer.parseInt(year + "" + month);
			}
		}

		System.out.println("backup_date: " + backup_date);

		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}

		// add by huangjl 对企业添加过滤掉当前月份的限制条件
		String createTime = year + "-" + month + "-01";
		String createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

		String da_company_industry_relSql = " " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + " ";
		// add by huangjl 过滤掉其他行业中的电力生产
		String qcDlsqSql1 = " and  not exists (select par_da_com_id from  " + da_company_industry_rel + "  where par_da_ind_id=1438 " + da_company_industry_relSql + "  and  c.id=par_da_com_id ) ";
		String qcDlsqSql2 = " and  not exists (select par_da_com_id from  da_company_industry_rell  where par_da_ind_id=1438   and  c.id=par_da_com_id  ) ";

		String da_company_passSql = " " + (da_company_pass.equals("da_company_pass_his") ? "and pass.backup_date=" + backup_date + "   " : "") + " ";
		// add by huangjl
		// 添加当前年份以前的重大隐患----重大隐患创建时间小于当前年份的;--重大隐患治理为空的，或者重大隐患治理的创建年份大于重大隐患的创建时间，并且等于当前年份。
		// String
		// oldDangerSql="  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
		// + year +
		// "-01-01','yyyy-MM-dd')  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='"
		// + year + "')) ";
		// String
		// oldDangerSql1="  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
		// + year +
		// "-01-01','yyyy-MM-dd')  and  (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

		int pYear = year - 1;
		String oldDangerSql = "  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='" + year + "')) ";
		String oldDangerSql1 = "  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and  (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and  not exists  (select dag.par_da_dan_id   from da_danger_gorver dag where  danger.id=dag.par_da_dan_id ) ";

		String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
		String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		// String sqlDanger = " and create_time between to_date('" + startTime +
		// "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";

		String sqlDanger = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		// String sqlGorver = " and create_time < to_date('" + mDateTime +
		// "','yyyy-MM-dd') ";
		String sqlGorver = sqlParam;
		String sql = "select zi.type,zi.zj_id,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val,sum(money) money, sum(all_com_val) all_com_val,sum(normalMoney) " + " from (select id,zj_id,name,type from zj_industry where type=" + Nbyhpc.MINE_REPORT + " ) zi left join ("

		+ " select com.zjtype,0 all_com_val,0 com_val," + " 0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.resources) resources,count(dd_ng.finish_date) finish,  sum " + " (dd_ng.safety_method) method,sum(dd_ng.govern_money) money,1 type,0 normalMoney  from " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
				+ " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + " " + qcDlsqSql1 + "  union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
				+ " left join "
				+ da_company
				+ " c on c.id=rel.par_da_com_id where ind.is_deleted=0 "
				+ createTimeSql
				+ " "
				+ (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "")
				+ "  "
				+ (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
				+ " and ind.zj_type is not null "
				+ " and ind.type="
				+ Nbyhpc.COMPANY_TRADE
				+ " and c.is_deleted=0 and c.second_area="
				+ areaCode
				+ " "
				+ qcDlsqSql2
				+ ") com "

				+ " left join (select id,par_da_com_id from da_danger where is_deleted=0 "
				+ sqlDanger
				+ oldDangerSql
				+ ") dd on dd.par_da_com_id = com.id "
				+ " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date, g.money as govern_money from  "
				+ " da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
				+ sqlGorver
				+ ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and  "
				+ " g.par_da_dan_id is not null and d.id is not null) dd_ng on dd_ng.id=dd.id"
				// add by huangjl 添加一般隐患落实资金
				// + " left join (select normal.govern_money, " +
				// "         normal.id, " + "         normal.par_da_com_id " +
				// "    from da_normal_danger normal " +
				// "    where normal.is_deleted = 0 " +
				// "     and normal.is_danger = 1 " +
				// "   and normal.is_repaired = 1 " + sqlParam + ") nor_ng " +
				// "  on nor_ng.par_da_com_id = com.id "

				+ " group by com.zjtype  "

				// add by huangjl 添加一般隐患落实资金
				+ " union select com.zjtype,0 all_com_val,0 com_val," + " 0 dnd_val,0 dndg_val, 0 dd_val, 0 ddng_val,0 target,  0 goods,0 resources,0 finish,  0 method,0 money,1 type,sum(nor_ng.govern_money) normalMoney  from " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + " " + qcDlsqSql1
				+ "  union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + " " + qcDlsqSql2
				+ ") com "
				// add by huangjl 添加一般隐患落实资金
				+ " left join (select normal.govern_money, " + "         normal.id, " + "         normal.par_da_com_id " + "    from da_normal_danger normal " + "    where normal.is_deleted = 0 " + "     and normal.is_danger = 1 " + "   and normal.is_repaired = 1 " + sqlParam + ") nor_ng " + "  on nor_ng.par_da_com_id = com.id "

				+ " group by com.zjtype  "

				+ " union " + " select com.zjtype,0 all_com_val,count (distinct(d.par_da_com_id)) com_val,count " + " (distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val, 0 dd_val," + " 0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type, 0 normalMoney  from  " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
				+ " and c.is_deleted=0 and c.second_area=" + areaCode + " " + qcDlsqSql1 + "  union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + " " + qcDlsqSql2 + ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + ") dnd " + " on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where  "
				+ " is_danger=1 and is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id  " + " = dnd.id  left join ((select d.id,d.par_da_com_id from da_danger d where d.is_deleted=0  " + sqlParam + oldDangerSql + "  " + ")  union  select  n.id,n.par_da_com_id from da_normal_danger n where n.is_deleted=0  " + sqlParam + ") d on  " + " d.par_da_com_id=com.id    group by com.zjtype "

				+ " union " + " select com.zjtype,count(distinct(com.id)) all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val," + " 0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type , 0 normalMoney from  " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id "
				// modify by huangjl 添加da_company_pass pass
				+ " left join " + da_company_pass + " pass  on pass.par_da_com_id = rel.par_da_com_id "

				+ " left join " + da_company + " c on c.id=pass.par_da_com_id where ind.is_deleted=0  " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " " + da_company_passSql + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and pass.is_deleted = 0  and pass.is_affirm = 1 and c.second_area=" + areaCode + " " + qcDlsqSql1 + ") com group by com.zjtype "

				+ " ) val on zi.id=val.zjtype group by zi.zj_id,zi.type order by zi.zj_id";

		System.out.println("normal  danger: " + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				NomalDanger nomalDanger = new NomalDanger();
				nomalDanger.setTableType(res.getInt(1));
				nomalDanger.setType(res.getInt(2));
				nomalDanger.setCompany(res.getInt(3));
				nomalDanger.setGeneralDanger(res.getInt(4));
				nomalDanger.setGeneralDangerGovern(res.getInt(5));
				// double类型在类里面进行四舍五入
				Double governMoney = res.getBigDecimal(6) == null ? 0 : res.getBigDecimal(6).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				Double normalGovernMoney = res.getBigDecimal(8) == null ? 0 : res.getBigDecimal(8).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				nomalDanger.setNormalGovernMoney(res.getBigDecimal(6) == null ? 0 : res.getBigDecimal(8).doubleValue());
				nomalDanger.setGovernMoney(res.getBigDecimal(8) == null ? 0 : res.getBigDecimal(6).doubleValue());
				nomalDanger.setPlanMoney(governMoney + normalGovernMoney);

				nomalDanger.setShouldTroubleshooting(res.getInt(7));
				nomalDangers.add(nomalDanger);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nomalDangers;
	}

	/**
	 * 查询其他行业的报送数据
	 * 
	 * @param param
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<NomalDanger> loadOtherByParam(Statistic st) throws ApplicationAccessException {
		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_item = "da_item";
		int backup_date = 0; // 历史表查询的日期

		List<NomalDanger> nomalDangers = new ArrayList<NomalDanger>();
		Long areaCode = st.getAreaCode();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		int year0 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		if (year < 2013 || (year == 2013 && month <= 11 && month >= 1)) { // 往月
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			da_item = "da_item_his";
			backup_date = 201311;
		} else if (year > year0 || (year0 == year && month >= month1)) { // 在当前日期之后
			da_company = "da_company";
			da_company_industry_rel = "da_company_industry_rel";
			da_industry_parameter = "da_industry_parameter";
			da_company_pass = "da_company_pass";
			da_item = "da_item";
		} else {
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			da_item = "da_item_his";
			if (month < 10) {
				backup_date = Integer.parseInt(year + "0" + month);
			} else {
				backup_date = Integer.parseInt(year + "" + month);
			}
		}

		System.out.println("backup_date: " + backup_date);
		String da_company_passSql = " " + (da_company_pass.equals("da_company_pass_his") ? "and pass.backup_date=" + backup_date + "   " : "") + " ";
		String da_itemSql = " " + (da_item.equals("da_item_his") ? "and di.backup_date=" + backup_date + "   " : "") + " ";

		// add by huangjl 对企业添加过滤掉当前月份的限制条件
		String createTime = year + "-" + month + "-01";
		String createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

		// add by huangjl
		// 添加当前年份以前的重大隐患----重大隐患创建时间小于当前年份的;--重大隐患治理为空的，或者重大隐患治理的创建年份大于重大隐患的创建时间，并且等于当前年份。
		// String
		// oldDangerSql="  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
		// + year +
		// "-01-01','yyyy-MM-dd')  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='"
		// + year + "')) ";
		// String
		// oldDangerSql1="  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
		// + year +
		// "-01-01','yyyy-MM-dd')   and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag)";

		int pYear = year - 1;
		String oldDangerSql = "  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='" + year + "')) ";
		String oldDangerSql1 = "  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'    and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag)";

		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}
		String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
		String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		// String sqlDanger = " and create_time between to_date('" + startTime +
		// "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlDanger = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		// String sqlGorver = " and create_time < to_date('" + mDateTime +
		// "','yyyy-MM-dd') ";

		String sqlGorver = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sql = " select zi.type,zi.zj_id,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val,sum(money) money, sum(all_com_val) all_com_val,  sum(normalMoney) normalMoney" + " from (select id,zj_id,name,type from zj_industry where type=" + Nbyhpc.OTHER_REPORT + "  ) zi left join ("

		+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target, sum(dd_ng.goods) goods,sum " + " (dd_ng.resources) resources,count(dd_ng.finish_date) finish,  sum " + " (dd_ng.safety_method) method,0 money,1 type,0 normalMoney from " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type="
				+ Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + ") com " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id "
				+ " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money "
				+ " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
				+ sqlGorver
				+ ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and g.par_da_dan_id is not null "
				+ " and d.id is not null) dd_ng on dd_ng.id=dd.id group by com.zjtype  "

				// 新加的 统计累加资金
				+ " union   select   com.zjtype, 0 all_com_val, 0 com_val, 0 dnd_val,   0 dndg_val,  0 dd_val, 0 ddng_val,  0 target,  0 goods,  0 resources,  0 finish,  0 method, sum(dd_ng.govern_money) money,1 type,0 normalMoney  from (select c.id, ind.zj_type zjtype  from " + da_industry_parameter + " ind    left join " + da_company_industry_rel + " rel       on rel.par_da_ind_id = ind.id   left join " + da_company + " c on c.id = rel.par_da_com_id  where ind.is_deleted = 0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null  and ind.type = 1  and c.is_deleted = 0    and   c.second_area=" + areaCode
				+ " union  select c.id, ind.zj_type zjtype  from " + da_industry_parameter + " ind    left join da_company_industry_rell rel       on rel.par_da_ind_id = ind.id   left join " + da_company + " c on c.id = rel.par_da_com_id  where ind.is_deleted = 0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null  and ind.type = 1  and c.is_deleted = 0    and   c.second_area=" + areaCode

				+ ") com    left join (select id, par_da_com_id from da_danger where is_deleted = 0  " + sqlParam + oldDangerSql + ") dd   on dd.par_da_com_id = com.id left join (select d.id, d.target, d.goods, d.resources, d.safety_method, d.finish_date, g.money as govern_money " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
				+ sqlParam
				+ ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and g.par_da_dan_id is not null "
				+ " and d.id is not null) dd_ng on dd_ng.id=dd.id group by com.zjtype  "

				// 新加的 统计累加资金
				+ " union   select   com.zjtype, 0 all_com_val, 0 com_val, 0 dnd_val,   0 dndg_val,  0 dd_val, 0 ddng_val,  0 target,  0 goods,  0 resources,  0 finish,  0 method, 0 money,1 type,sum(nor_ng.govern_money) normalMoney  from (select c.id, ind.zj_type zjtype  from " + da_industry_parameter + " ind    left join " + da_company_industry_rel + " rel       on rel.par_da_ind_id = ind.id   left join " + da_company + " c on c.id = rel.par_da_com_id  where ind.is_deleted = 0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null  and ind.type = 1  and c.is_deleted = 0    and   c.second_area=" + areaCode
				+ " union  select c.id, ind.zj_type zjtype  from " + da_industry_parameter + " ind    left join da_company_industry_rell rel       on rel.par_da_ind_id = ind.id   left join " + da_company + " c on c.id = rel.par_da_com_id  where ind.is_deleted = 0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null  and ind.type = 1  and c.is_deleted = 0    and   c.second_area="
				+ areaCode
				+ ") com   "
				// add by huangjl 添加一般隐患落实资金
				+ " left join (select normal.govern_money, " + "         normal.id, " + "         normal.par_da_com_id " + "    from da_normal_danger normal " + "    where normal.is_deleted = 0 " + "     and normal.is_danger = 1 " + "   and normal.is_repaired = 1 " + sqlParam + ") nor_ng " + "  on nor_ng.par_da_com_id = com.id "

				+ " group by com.zjtype   "

				+ " union   " + " select com.zjtype,0 all_com_val, count(distinct(d.par_da_com_id)) com_val,count " + " (distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val,0 dd_val," + " 0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type,0 normalMoney  from " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE
				+ " and c.is_deleted=0 and c.second_area=" + areaCode + " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 and c.second_area=" + areaCode + ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + ") dnd " + " on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id = dnd.id  left join ((select  "
				+ " d.id,d.par_da_com_id from da_danger d where d.is_deleted=0 " + sqlParam + oldDangerSql + " ) union  select  n.id,n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + ") d on  " + " d.par_da_com_id=com.id  group by com.zjtype "

				+ " union   " + " select com.zjtype, count (distinct(com.id)) all_com_val, 0 com_val, 0 dnd_val, 0 dndg_val,0 dd_val," + " 0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type,0 normalMoney  from " + " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind "

				+ " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id "

				// modify by huangjl 添加da_company_pass pass
				+ " left join " + da_company_pass + " pass  on pass.par_da_com_id = rel.par_da_com_id "

				+ " left join " + da_company + " c on c.id=pass.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " " + da_company_passSql + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0    and pass.is_deleted = 0  and pass.is_affirm = 1  and c.second_area=" + areaCode + ") com group by com.zjtype  "

				+ " union " + " select ind.zjtype,sum (dsro.company_num) all_com_val,sum (dsro.company_num) com_val,sum (dsro.troub_num) dnd_val, " + " sum(dsro.troub_clean_num) dndg_val,  0 dd_val,0 ddng_val,0 target,0 goods,0 resources," + " 0 finish,0 method,0 money,3 type,0 normalMoney from (select i.id,i.zj_type zjtype from " + da_industry_parameter + " i " + " where i.is_deleted=0 " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and i.backup_date=" + backup_date + "   " : "") + " and i.zj_type is not null and i.type=" + Nbyhpc.QUARTER_REPORT_TRADE_OTHER + ") ind  " + " left join da_season_report_other dsro on dsro.par_da_ind_id=ind.id where dsro.is_deleted=0 and dsro.second_area=" + areaCode + " " + sqlParam + " group by ind.zjtype  "

				+ " union " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype, 0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.worker) resources,count(dd_ng.finish_date) finish,  " + " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,4 type,0 normalMoney  " + " from (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0  and di.second_area=" + areaCode + da_itemSql + ") com " + " left join (select id,par_da_ite_id from da_item_danger where is_deleted=0 " + sqlDanger + ") dd on dd.par_da_ite_id = com.id   " + " left join ( select d.id,d.target,d.goods,d.worker,d.safety_method,d.finish_date,g.money as govern_money from  " + " da_item_danger d left join (select par_da_it_id,money from da_item_danger_gover where is_deleted=0 " + sqlGorver + ") g on g.par_da_it_id=d.id where d.is_deleted=0 and  "
				+ " g.par_da_it_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id  "

				+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype, 0 all_com_val,count (distinct(d.par_da_ite_id)) com_val,sum(disr.ordinary_danger_finding) dnd_val," + " (sum(disr.ordinary_danger_finding)-sum(disr.ordinary_danger_not_govern)) dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type,0 normalMoney  from   " + " (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0  and di.second_area=" + areaCode + da_itemSql + ") com  " + " left join (select id,par_da_ite_id,ordinary_danger_finding,ordinary_danger_not_govern " + " from da_item_season_report where is_deleted=0 " + sqlParam + ") disr on disr.par_da_ite_id = com.id  " + " left join (select d.par_da_ite_id from da_item_danger d where d.is_deleted=0 " + sqlParam + " union  select n.par_da_ite_id from da_item_season_report n where n.is_deleted=0 " + sqlParam + ") d " + " on d.par_da_ite_id=com.id "

				+ " union " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype, count(distinct(com.id)) all_com_val, 0 com_val, 0 dnd_val, 0 dndg_val,0 dd_val,0 ddng_val," + " 0 target, 0 goods, 0 resources, 0 finish, 0 method, 0 money, 5 type,0 normalMoney from " + " (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 and di.second_area=" + areaCode + da_itemSql + ") com  "

				+ " ) val on zi.id=val.zjtype group by zi.zj_id,zi.type order by zi.zj_id";

		System.out.println("一般隐患sql: " + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				NomalDanger nomalDanger = new NomalDanger();
				nomalDanger.setTableType(res.getInt(1));
				nomalDanger.setType(res.getInt(2));
				nomalDanger.setCompany(res.getInt(3));
				nomalDanger.setGeneralDanger(res.getInt(4));
				nomalDanger.setGeneralDangerGovern(res.getInt(5));
				// double类型在类里面进行四舍五入
				Double governMoney = res.getBigDecimal(6) == null ? 0 : res.getBigDecimal(6).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				Double normalGovernMoney = res.getBigDecimal(8) == null ? 0 : res.getBigDecimal(8).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				nomalDanger.setGovernMoney(res.getBigDecimal(6) == null ? 0 : res.getBigDecimal(6).doubleValue());
				nomalDanger.setNormalGovernMoney(res.getBigDecimal(8) == null ? 0 : res.getBigDecimal(8).doubleValue());
				nomalDanger.setPlanMoney(governMoney + normalGovernMoney);

				nomalDanger.setShouldTroubleshooting(res.getInt(7));
				nomalDangers.add(nomalDanger);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nomalDangers;
	}

	/**
	 * 查询打击三非的报送数据
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ThreeIllegal> loadThreeIllegalByParam(Statistic st) throws ApplicationAccessException {
		List<ThreeIllegal> threeIllegals = new ArrayList<ThreeIllegal>();
		ThreeIllegal threeIllegal;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}
		String sqlArea = " and second_area=" + st.getAreaCode() + "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
		}
		String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sql = " select zi.zj_id,sum(dti.illegal_build_getting),sum(dti.illegal_build_candeled)," + " sum(dti.illegal_produce_getting), sum(dti.illegal_produce_canceled)," + " sum(dti.illegal_trade_getting),sum(dti.illegal_trade_calceled)" + " from zj_industry zi left join da_industry_parameter dip on dip.zj_type=zi.id " + " left join (select * from da_three_illegal where is_deleted=0 " + sqlParam + sqlArea + ") dti on dti.par_da_ind_id = dip.id " + " where dip.is_deleted =0 and zi.type=3 and dip.type=4 and zi.id is not null" + " group by zi.zj_id,zi.name,zi.sort_num order by zi.sort_num";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				threeIllegal = new ThreeIllegal();
				threeIllegal.setType(res.getInt(1));
				threeIllegal.setIllegalBuildGetting(res.getInt(2));
				threeIllegal.setIllegalBuildCandeled(res.getInt(3));
				threeIllegal.setIllegalProduceGetting(res.getInt(4));
				threeIllegal.setIllegalProduceCanceled(res.getInt(5));
				threeIllegal.setIllegalTradeGetting(res.getInt(6));
				threeIllegal.setIllegalTradeCalceled(res.getInt(7));
				threeIllegals.add(threeIllegal);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return threeIllegals;
	}

	/**
	 * 矿山等行业的报送数据汇总统计
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadStatisticForAxis2MineByParam(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}
		String sqlArea = " and second_area=" + st.getAreaCode() + "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
		}
		String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
		String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlDanger = " and create_time between to_date('" + startTime + "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlGorver = " and create_time < to_date('" + mDateTime + "','yyyy-MM-dd') ";
		String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, " + " sum(resources) resources,sum(finish) finish,sum(method) method,sum(money) money " + " from (select id,name,sort_num from zj_industry where type=" + Nbyhpc.MINE_REPORT + " ) zi  " + " left join (select com.zjtype,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val, " + " count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods, " + " sum(dd_ng.resources) resources,count(dd_ng.finish_date) finish," + " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,1 type from " + " (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id " + " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type="
				+ Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlDanger + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where d.is_deleted=0   " + " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id group by com.zjtype  " + " union   " + " select com.zjtype,count (distinct(d.par_da_com_id)) com_val,count(distinct(dnd.id)) dnd_val, " + " count(distinct(dnd_g.id)) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish, " + " 0 method,0 money,2 type  from (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id "
				+ " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + " ) dnd on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where  " + " is_danger=1 and is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id = dnd.id   " + " left join (select d.par_da_com_id from da_danger d where d.is_deleted=0 " + sqlParam + " union  select  n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + " ) d on  " + " d.par_da_com_id=com.id group by com.zjtype ) val on zi.id=val.zjtype " + " group by zi.sort_num,zi.name order by zi.sort_num";
		System.out.println("sql:  " + sql);
		ResultSet res = companyPersistenceIface.findBySql(sql);
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
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	// types.add(new State(100, "1.煤矿企业"));// 原表格中不含有
	// types.add(new State(101, "2.金属非金属矿山企业"));// 包含金属非金属(type=1)、尾矿库(type=2)
	// types.add(new State(102, "3.石油天然气开采企业"));// 原表格中不含有
	// types.add(new State(103, "4.危险化学品企业"));//
	// 排除危险化学品--道路运输企业与单位(type=13)，type为7、8、9、10、11、12的总和
	// types.add(new State(104, "  (1)生产、储存和其他化工企业"));// 包含7、9、10、11、12
	// types.add(new State(8, "  (2)经营企业和单位"));
	// types.add(new State(105, "5.烟花爆竹企业"));// 包含14、15、16
	// types.add(new State(106, "6.冶金、有色企业"));// 包含3、4
	// types.add(new State(107, "7.其他企业"));// 包含5、6、17
	//
	// types.add(new State(109, "1.道路运输企业"));// 包含交通运输行业中的“1.道路运输企业”(type=18)
	// // 以及 矿山行业中的“6.危险化学品企业”--“(5).道路运输企业和单位”(type=13)
	// types.add(new State(19, "2.公路养护施工企业"));
	// types.add(new State(20, "3.水上运输企业"));
	// types.add(new State(21, "4.铁路运输企业"));
	// types.add(new State(22, "5.航空公司"));
	// types.add(new State(23, "6.机场和油料企业"));
	// types.add(new State(30, "7.建筑施工企业"));
	// types.add(new State(28, "8.学校"));
	// types.add(new State(29, "9.商场、市场等人员密集场所"));
	// types.add(new State(26, "10.水库"));
	// types.add(new State(32, "11.电力企业"));
	// types.add(new State(25, "12.农机行业"));
	// types.add(new State(24, "13.渔业企业"));
	// types.add(new State(31, "14.民爆器材生产企业"));
	// types.add(new State(110, "15.其他企业单位"));//
	// 包含交通运输行业中的“15.机械制造行业”(type=33)、16.地铁施工（按项目部统计）(type=27)
	// //20.城市公共交通(type=38)、21.燃气(type=39)、 22.旅游行业(type=40)、 23.铁路(type=41)、
	// 24.医院(type=42)、
	// // “27.其他单位”(type=34)、17.道路交通事故多发点段(type=35)、18.道路交通安全设施(type=36)、
	// //19.临水临崖危险路段(type=37)、25.“三合一”场所(type=43)、26.出租房(type=44)
	/**
	 * 矿山等行业的报送数据汇总统计(new)
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticForAxis2MineByParam2(Statistic st) throws ApplicationAccessException {
		Statistic statistic;
		String cacheName = "";
		String cacheNum = "0";
		Calendar cal = Calendar.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}

		int backup_date = 0; // 历史表查询的日期
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company_industry_rell = "da_company_industry_rell";
		String da_industry_parameter = "da_industry_parameter";

		if (year < 2013 || (year == 2013 && month <= 11 && month >= 1)) { // 往月
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			backup_date = 201311;
		} else if (year > year1 || (year1 == year && month >= month1)) { // 在当前日期之后
			da_company = "da_company";
			da_company_industry_rel = "da_company_industry_rel";
			da_industry_parameter = "da_industry_parameter";
			da_company_pass = "da_company_pass";
		} else {
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			if (month < 10) {
				backup_date = Integer.parseInt(year + "0" + month);
			} else {
				backup_date = Integer.parseInt(year + "" + month);
			}
		}

		// System.out.println("backup_date: " + backup_date);

		// liulj 加入MemCached缓存设置
//		if (year1 == year && month == month1) {
//			cacheNum = "0";
//		} else {
			cacheNum = year + ((month < 10) ? "0" : "") + month;
//		}
		cacheName = "nbyhpc_Axis2Mine_" + cacheNum;
		MemCached cache = MemCached.getInstance();
		
		
		boolean catchDateExist=false;
		//如果缓存不存在的化，就先查找緩存备份表中是否存在数据，存在的话，取缓存备份表数据
		ResultSet stat = tcachePersistenceIface.findBySql("select  id from DA_STATISTIC  where S_TYPE='1' and IS_DELETED=0  and SENDMONTH=" + cacheNum + " ");
		try {
			if (stat.next()) { // 已存在,取缓存数据
				catchDateExist=true;
			}
			stat.getStatement().close();
			stat.close();
			cFactory.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			map = (Map<String, Object>) cache.get(cacheName);
			System.out.println("读取缓存: " + cacheName);
		} else if(catchDateExist){
			System.out.println("读取缓存数据库表: " + cacheName);
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaStatistic.class, "t");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" IS_DELETED=0 and S_TYPE='1' and SENDMONTH="+cacheNum+" "));// 此处一部分一部分的执行。
			List<DaStatistic> daStatistics = statisticPersistenceIface.loadStatistics(detachedCriteriaProxy, null);
			System.out.println("daStatistic.size() : " + daStatistics.size());
			if (daStatistics!=null&&daStatistics.size() > 0) {
				for (DaStatistic daStatistic : daStatistics) {
					statistic = new Statistic();
					statistic.setEnumName(daStatistic.getEnumName());
					statistic.setCompanyNum(daStatistic.getCompanyNum());
					statistic.setTroubNum(daStatistic.getTroubNum());
					statistic.setTroubCleanNum(daStatistic.getTroubCleanNum());
					statistic.setBigTroubNum(daStatistic.getBigTroubNum());
					statistic.setBigTroubCleanNum(daStatistic.getBigTroubCleanNum());
					statistic.setTarget(daStatistic.getTarget());
					statistic.setGoods(daStatistic.getGoods());
					statistic.setResource(daStatistic.getsResource());
					statistic.setFinishDate(daStatistic.getFinishDate());
					statistic.setSafetyMethod(daStatistic.getSafetyMethod());
					statistic.setDdng5Num(daStatistic.getDdng5Num());
					statistic.setProviceRcNum(daStatistic.getProvicercNum());
					statistic.setCityRcNum(daStatistic.getCityrcNum());
					statistic.setAllCompanyNum(daStatistic.getAllcompanyNum());
					statistic.setQtRcNum(daStatistic.getQtrcNum());
					statistic.setGovernMoney(daStatistic.getGovernMoney());
					statistic.setNormalGovernMoney(daStatistic.getNormalgovernMoney());
					map.put(daStatistic.getsId(), statistic);
				}
			}
			//更新缓存记录信息
			this.updateCache("省局矿山_年表",cacheName, backup_date);
			
//			if (cacheNum.equals("0")) { // 动态的 设置过期时间
//				cache.set(cacheName, map, new Date(Nbyhpc.EXPIRATION_TIME));
//			} else { // 永不过期
				cache.add(cacheName, map);
//			}
			
		}else {
			// add by huangjl 固化后参数的sql
			String da_companySql = " " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " ";
			String da_company_industry_relSql = " " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + " ";
			String da_industry_parameterSql = " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + " ";
			String da_company_passSql = " " + (da_company_pass.equals("da_company_pass_his") ? "and pass.backup_date=" + backup_date + "   " : "") + " ";
			// add by huangjl 对企业添加过滤掉当前月份的限制条件
			String createTime = year + "-" + month + "-01";
			String createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

			String qcDlsqSql1 = " and  not exists (select par_da_com_id from  " + da_company_industry_rel + "  where par_da_ind_id=1438 " + da_company_industry_relSql + "  and c.id= par_da_com_id ) ";

			String qcDlsqSql2 = " and  not exists (select par_da_com_id from  da_company_industry_rell  where par_da_ind_id=1438 and c.id= par_da_com_id ) ";

			// add by huangjl
			// 添加当前年份以前的重大隐患----重大隐患创建时间小于当前年份的;--重大隐患治理为空的，或者重大隐患治理的创建年份大于重大隐患的创建时间，并且等于当前年份。
			// String
			// oldDangerSql="  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + year +
			// "-01-01','yyyy-MM-dd')  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='"
			// + year + "')) ";
			// String
			// oldDangerSql1="  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + year +
			// "-01-01','yyyy-MM-dd')    and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			int pYear = year - 1;
			String oldDangerSql = "  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='" + year + "')) ";
			String oldDangerSql1 = "  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'     and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and  not exists  (select dag.par_da_dan_id   from da_danger_gorver dag where  danger.id=dag.par_da_dan_id ) ";
			String sqlArea = "";
			if (st.getAreaCode() == 0L) {
				sqlArea = " and second_area!=0";
			} else {
				if (st.getThirdCode() == null || st.getThirdCode() == 0L) {
					sqlArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
				} else {
					sqlArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
				}
			}

			Integer nextYear = year + 1;
			String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");

			String creatimeCompany = " and c.create_time < to_date('" + year + "-" + month + "-01','yyyy-MM-dd') ";

			String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
			String sqlDanger = sqlParam;

			// String sqlDanger = " and create_time between to_date('" +
			// startTime +
			// "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
			// System.out.println("sqlParam:"+sqlParam);
			// System.out.println("sqlDanger:"+sqlDanger);
			// String sqlGorver = " and create_time < to_date('" + mDateTime +
			// "','yyyy-MM-dd') ";

			// 将时间统一修改
			// String sqlGorver = " and create_time between to_date('" + year +
			// "-01-01','yyyy-MM-dd') and to_date('" + mDateTime +
			// "','yyyy-MM-dd')";
			// String sqlGorver1 = " and create_time  between to_date('" + year
			// + "-01-01','yyyy-MM-dd') and to_date('" + nextYear +
			// "-01-01','yyyy-MM-dd')";
			String sqlGorver = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
			String sqlGorver1 = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";

			String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, " + " sum(resources) resources,sum(finish) finish,sum(method) method,sum(money) money " + " , sum(dd_ng5_num), sum(p_rc_num), sum(ct_rc_num), zi.id,sum(all_com_val) all_com_val, sum(qt_rc_num), sum(normalMoney)  " + " from (select id,name,sort_num from zj_industry where type=" + Nbyhpc.MINE_REPORT + " ) zi  " + " left join (select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val, " + " count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods, " + " sum(dd_ng.resources) resources,count(dd_ng.finish_date) finish,"
					+ " sum(dd_ng.safety_method) method,0 money,1 type, count(distinct(dd_ng5.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join  " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + sqlDanger + oldDangerSql + "" + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver1 + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0  or (d.is_deleted=1 and d.flag=1)) " + " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					+ " select d.id from da_danger d  "// 连接5落实
					+ "  where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  and d.id is not null " + "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) " + " ) dd_ng5 on dd_ng.id=dd_ng5.id "
					+ " group by com.zjtype  "

					// liulj 2014.2.13

					/**
					 * +
					 * " union  select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val1, "
					 * +
					 * " count(distinct(dd_ng.id)) ddng_val1,sum(dd_ng.target) target1,  sum(dd_ng.goods) goods1, "
					 * +
					 * " sum(dd_ng.resources) resources1,count(dd_ng.finish_date) finish1,"
					 * +
					 * " sum(dd_ng.safety_method) method1,0 money,1 type, count(distinct(dd_ng5.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num ,0 dd_val, 0 ddng_val,0 target, 0 goods, 0 resources,0 finish,0 method from "
					 * +
					 * " (select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					 * +
					 * " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					 * +
					 * " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					 * + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					 * " and c.is_deleted=0 " + sqlArea + ") com " +
					 * " left join (select id,par_da_com_id from da_danger where is_deleted=0 "
					 * + sqlDanger + "" + ") dd on dd.par_da_com_id = com.id  "
					 * +
					 * " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    "
					 * +
					 * " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					 * + sqlGorver1 +
					 * ") g on g.par_da_dan_id=d.id where d.is_deleted=0   " +
					 * " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					 * + " select d.id from da_danger d  "// 连接5落实 +
					 * "  where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  and d.id is not null "
					 * +
					 * "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) "
					 * + " ) dd_ng5 on dd_ng.id=dd_ng5.id " +
					 * " group by com.zjtype  "
					 **/
					// modify by huangjl
					+ " union " + " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  0 dd_val, " + " 0 ddng_val,0 target,  0 goods, " + " 0 resources,0 finish," + " 0 method,sum(dd_ng.govern_money) money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1, 0 normalMoney from "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell

					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + sqlDanger + oldDangerSql + "" + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date, g.money as govern_money    "
					+ " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
					+ sqlGorver1
					+ ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  "
					+ " and d.id is not null) dd_ng on dd_ng.id=dd.id "
					// +"left join ( "
					// + " select d.id from da_danger d  "// 连接5落实
					// + "  where d.is_deleted=0  and d.id is not null " +
					// "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) "
					// + " ) dd_ng5 on dd_ng.id=dd_ng5.id "

					+ " group by com.zjtype  "

					// add by huangjl 添加一般隐患落实资金
					+ " union " + " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  0 dd_val, " + " 0 ddng_val,0 target,  0 goods, " + " 0 resources,0 finish," + " 0 method,0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1, sum(nor_ng.govern_money) normalMoney from "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell

					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com "

					+ " left join (select normal.govern_money, " + "         normal.id, " + "         normal.par_da_com_id " + "    from da_normal_danger normal " + "    where normal.is_deleted = 0 " + "     and normal.is_danger = 1 " + "   and normal.is_repaired = 1 " + sqlParam + ") nor_ng " + "  on nor_ng.par_da_com_id = com.id "

					+ " group by com.zjtype  "

					+ " union   "
					// 省级挂牌
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, count(distinct(dd_rc.id)) p_rc_num, 0 ct_rc_num,0 qt_rc_num ,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null" + "     and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell
					+ "  union  select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join da_company_industry_rell " + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "    and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql2

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))   " + "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.par_da_dan_id from da_danger d left join(" + "        select par_da_dan_id from da_rollcall_company where is_deleted=0 and levels = 'province_city' " + "     ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null" + " ) dd_rc on dd_rc.id=dd_ng.id" + " group by com.zjtype"
					// and g.par_da_dan_id is null
					+ " union   "
					// 地市级挂牌
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, count(distinct(dd_rc.id)) ct_rc_num,0 qt_rc_num,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  (" + "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "    rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ "    and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell
					+ "   union  select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "    and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql2

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  " + "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'city_city' " + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id" + " group by com.zjtype"
					// and g.par_da_dan_id is null
					+ " union   "
					// 其他级挂牌(省局没有乡镇挂牌，因为不统计在内)
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num,0 ct_rc_num, count(distinct(dd_rc.id)) qt_rc_num ,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join " + da_company_industry_rel + "" + "     rel on rel.par_da_ind_id=ind.id  left join  " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "     and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell
					+ "  union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join  " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "    and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql2

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0  or (d.is_deleted=1 and d.flag=1)) " + "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'county_county' " + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id" + " group by com.zjtype"
					// and g.par_da_dan_id is null
					+ " union   "

					+ " select com.zjtype,0 all_com_val,count (distinct(d.par_da_com_id)) com_val,count(distinct(dnd.id)) dnd_val, " + " count(distinct(dnd_g.id)) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish, " + " 0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num,0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join  " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join  " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + " ) dnd on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where  " + " is_danger=1 and is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id = dnd.id   " + " left join ((select d.id,d.par_da_com_id from da_danger d where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) " + sqlParam + oldDangerSql + ") union  select  n.id,n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + " ) d on  " + " d.par_da_com_id=com.id group by com.zjtype "

					+ " union   "
					// 应排查企业数
					+ " select com.zjtype,count (distinct(com.id)) all_com_val,0 com_val, 0 dnd_val, " + " 0 dndg_val,0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish, " + " 0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from ("

					+ "select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id "
					// add by huangjl 添加da_company_pass pass条件pass.is_deleted =
					// 0 and pass.is_affirm = 1
					+ " left join  " + da_company_pass + " pass  on pass.par_da_com_id = rel.par_da_com_id " + " left join  " + da_company + "  c on c.id=pass.par_da_com_id where ind.is_deleted=0  " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " " + da_company_passSql + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1 + "  and pass.is_deleted = 0 and pass.is_affirm = 1 "
					// modify by huangjl 添加da_company_industry_rell
					// +" union select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					// +
					// " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					// +
					// " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					// + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					// " and c.is_deleted=0 " + sqlArea

					+ ") com group by com.zjtype "

					+ ") val on zi.id=val.zjtype " + " group by zi.sort_num,zi.name, zi.id ";// order
																								// by
																								// zi.sort_num
			System.out.println("send sql:  " + sql);

			try {
				ResultSet res = companyPersistenceIface.findBySql(sql);
				Statistic s = new Statistic();
				s.setEnumName("total");
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

					statistic.setDdng5Num(res.getInt(13));
					statistic.setProviceRcNum(res.getInt(14));
					statistic.setCityRcNum(res.getInt(15));
					statistic.setAllCompanyNum(res.getInt(17));
					statistic.setQtRcNum(res.getInt(18));

					// double类型在类里面进行四舍五入
					Double governMoney = res.getBigDecimal(12) == null ? 0 : res.getBigDecimal(12).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double normalGovernMoney = res.getBigDecimal(19) == null ? 0 : res.getBigDecimal(19).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

					statistic.setGovernMoney(governMoney);
					statistic.setNormalGovernMoney(normalGovernMoney);

					// statistics.add(statistic);
					map.put("s_" + res.getInt(16) + "", statistic);
					if (res.getInt(16) != 13) {
						s.setCompanyNum(s.getCompanyNum() == null ? res.getInt(2) : (s.getCompanyNum() + res.getInt(2)));
						s.setTroubNum(s.getTroubNum() == null ? res.getInt(3) : (s.getTroubNum() + res.getInt(3)));
						s.setTroubCleanNum(s.getTroubCleanNum() == null ? res.getInt(4) : (s.getTroubCleanNum() + res.getInt(4)));
						s.setBigTroubNum(s.getBigTroubNum() == null ? res.getInt(5) : (s.getBigTroubNum() + res.getInt(5)));
						s.setBigTroubCleanNum(s.getBigTroubCleanNum() == null ? res.getInt(6) : (s.getBigTroubCleanNum() + res.getInt(6)));
						s.setTarget(s.getTarget() == null ? res.getInt(7) : (s.getTarget() + res.getInt(7)));
						s.setGoods(s.getGoods() == null ? res.getInt(8) : (s.getGoods() + res.getInt(8)));
						s.setResource(s.getResource() == null ? res.getInt(9) : (s.getResource() + res.getInt(9)));
						s.setFinishDate(s.getFinishDate() == null ? res.getInt(10) : (s.getFinishDate() + res.getInt(10)));
						s.setSafetyMethod(s.getSafetyMethod() == null ? res.getInt(11) : (s.getSafetyMethod() + res.getInt(11)));
						s.setGovernMoney(s.getGovernMoney() == null ? governMoney : (s.getGovernMoney() + governMoney));
						s.setDdng5Num(s.getDdng5Num() == null ? res.getInt(13) : (s.getDdng5Num() + res.getInt(13)));
						s.setProviceRcNum(s.getProviceRcNum() == null ? res.getInt(14) : (s.getProviceRcNum() + res.getInt(14)));
						s.setCityRcNum(s.getCityRcNum() == null ? res.getInt(15) : (s.getCityRcNum() + res.getInt(15)));
						s.setAllCompanyNum(s.getAllCompanyNum() == null ? res.getInt(17) : (s.getAllCompanyNum() + res.getInt(17)));
						s.setQtRcNum(s.getQtRcNum() == null ? res.getInt(18) : (s.getQtRcNum() + res.getInt(18)));
						s.setNormalGovernMoney(s.getNormalGovernMoney() == null ? normalGovernMoney : (s.getNormalGovernMoney() + normalGovernMoney));
					}
				}
				map.put("s_total", s);

//				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
//				ResultSet rs = null;
//
//				try {
//					if (c.next()) { // 已存在,更新
//						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='省局矿山_年表" + backup_date + "' where  id=" + c.getLong(1));
//						rs = pState.executeQuery();
//					} else { // 新建
//						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','省局矿山_年表" + backup_date + "',sysdate)");
//						rs = pState.executeQuery();
//					}
//					rs.close();
//					rs.getStatement().close();
//					cFactory.releaseConnection();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				//更新缓存记录信息
				this.updateCache("省局矿山_年表",cacheName, backup_date);
				System.out.println("cacheName:" + cacheName);
//				if (cacheNum.equals("0")) { // 动态的 设置过期时间
				if (year1 == year && month == month1) {
					cache.set(cacheName, map, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.add(cacheName, map);
					//在缓存永远不过期的情况下，将缓存内容保存到数据库备份
					createDaStatistic4Mine(cacheName, cacheNum, map);
				}

				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return map;
	}

	private void createDaStatistic4Mine(String cacheName, String cacheNum, Map<String, Object> map) throws SQLException {
		if(map!=null){
			ResultSet rs = null;
			Iterator<Entry<String, Object>> it=map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Object> entity=it.next();
				String key=entity.getKey();
				Statistic sta=(Statistic) entity.getValue();
				
				String insertSql="insert  into da_statistic " +
						"(ID,SID,ENUM_NAME,COMPANY_NUM,TROUB_NUM,TROUB_CLEAN_NUM,BIG_TROUB_NUM," +
						"BIG_TROUB_CLEAN_NUM,TARGET,GOODS,S_RESOURCE,FINISH_DATE,SAFETY_METHOD," +
						"DDNG5_NUM,PROVICERC_NUM,CITYRC_NUM,ALLCOMPANY_NUM,QTRC_NUM,GOVERN_MONEY," +
						"NORMALGOVERN_MONEY,S_TYPE,SENDMONTH,CATCHNAME,IS_DELETED,CREATE_TIME,MODIFY_TIME)" +
						" values(hibernate_sequence.nextval,'"+key+"','"+sta.getEnumName()+"',"+sta.getCompanyNum()+","+sta.getTroubNum()+","+sta.getTroubCleanNum()+
						","+sta.getBigTroubNum()+","+sta.getBigTroubCleanNum()+","+sta.getTarget()+","+sta.getGoods()+""+
						","+sta.getResource()+","+sta.getFinishDate()+","+sta.getSafetyMethod()+","+sta.getDdng5Num()+"" +
						","+sta.getProviceRcNum()+","+sta.getCityRcNum()+","+sta.getAllCompanyNum()+","+sta.getQtRcNum()+"" +
						","+sta.getGovernMoney()+","+sta.getNormalGovernMoney()+",'1'" +
						","+Integer.valueOf(cacheNum)+",'"+cacheName+"',0,sysdate,sysdate)";
				
				System.err.println("insertSql="+insertSql);
				PreparedStatement pState = cFactory.createConnection().prepareStatement(insertSql);
				rs = pState.executeQuery();
			}
			rs.getStatement().close();
			rs.close();
			cFactory.releaseConnection();
			
		}
	}
	
	
	/**
	 * 更新缓存记录信息
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException 
	 * @throws ApplicationAccessException
	 */
	public void updateCache(String contentTitle,String cacheName,Integer backup_date) throws ApplicationAccessException{
		ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
		ResultSet rs = null;

		try {
			if (c.next()) { // 已存在,更新
				System.out.println("更新缓存表信息！");
				PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='"+contentTitle+"" + backup_date + "' where  id=" + c.getLong(1));
				rs = pState.executeQuery();
			} else { // 新建
				System.out.println("新增缓存表信息！");
				PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','"+contentTitle+"" + backup_date + "',sysdate)");
				rs = pState.executeQuery();
			}
			rs.close();
			rs.getStatement().close();
			cFactory.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据缓存名称和缓存类型删除数据库中的缓存数据
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException 
	 * @throws ApplicationAccessException
	 */
	public void deleteStatistic(String catchName,String type){
		
		ResultSet rs = null;

		try {
			
			System.out.println("删除缓存表信息！");
			String deleteSql="update  DA_STATISTIC set IS_DELETED=1  where S_TYPE='"+type+"' and IS_DELETED=0  and CATCHNAME='" + catchName + "'";
			System.err.println("insertSql="+deleteSql);
			PreparedStatement pState = cFactory.createConnection().prepareStatement(deleteSql);
			rs = pState.executeQuery();
			
			rs.close();
			rs.getStatement().close();
			cFactory.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 其他行业报送数据汇总统计
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadStatisticForAxis2OtherByParam(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}
		String sqlArea = " and second_area=" + st.getAreaCode() + "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
		}
		String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
		String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlDanger = " and create_time between to_date('" + startTime + "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sqlGorver = " and create_time < to_date('" + mDateTime + "','yyyy-MM-dd') ";
		String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods,  sum(resources) " + " resources,sum(finish) finish,sum(method) method,sum(money) money  " + " from (select id,name,sort_num from zj_industry where type=" + Nbyhpc.OTHER_REPORT + " or  id=13 ) zi  " + " left join (select com.zjtype,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.resources) resources,count(dd_ng.finish_date) finish,sum " + " (dd_ng.safety_method) method,sum(dd_ng.govern_money) money,1 type  from " + " (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id " + " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type="
				+ Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlDanger + ") dd on dd.par_da_com_id = com.id " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money from  " + " da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and  " + " g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id group by com.zjtype  "

				+ " union   " + " select com.zjtype,count (distinct(d.par_da_com_id)) com_val,count (distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val," + " 0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type  from  " + " (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id " + " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + ") dnd " + " on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id  = dnd.id  " + " left join (select d.par_da_com_id from da_danger d where d.is_deleted=0  " + sqlParam
				+ "  union  select  n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + ") d on d.par_da_com_id=com.id  group by com.zjtype  " + " union " + " select ind.zjtype,sum (dsro.company_num) com_val,sum (dsro.troub_num) dnd_val, " + " sum(dsro.troub_clean_num) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods," + " 0 resources,0 finish,0 method,0 money,3 type from (select i.id,i.zj_type zjtype from " + " da_industry_parameter i where i.is_deleted=0 and i.zj_type is not null and i.type=" + Nbyhpc.QUARTER_REPORT_TRADE_OTHER + ") ind  " + " left join da_season_report_other dsro on dsro.par_da_ind_id=ind.id where dsro.is_deleted=0 " + sqlParam + " " + sqlArea + " group by ind.zjtype  "

				+ " union " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype ,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.worker) resources,count(dd_ng.finish_date) finish,  " + " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,4 type  " + " from (select di.id from  da_item di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + sqlArea + ") com " + " left join (select id,par_da_ite_id from da_item_danger where is_deleted=0 " + sqlDanger + ") dd on dd.par_da_ite_id = com.id   " + " left join ( select d.id,d.target,d.goods,d.worker,d.safety_method,d.finish_date,d.govern_money from  " + " da_item_danger d left join (select par_da_it_id from da_item_danger_gover where is_deleted=0 " + sqlGorver + ") g on g.par_da_it_id=d.id where d.is_deleted=0 and  " + " g.par_da_it_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id  "

				+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,count (distinct(d.par_da_ite_id)) com_val,sum(disr.ordinary_danger_finding) dnd_val," + " (sum(disr.ordinary_danger_finding)-sum(disr.ordinary_danger_not_govern)) dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type  from   " + " (select di.id from  da_item di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + sqlArea + ") com  " + " left join (select id,par_da_ite_id,ordinary_danger_finding,ordinary_danger_not_govern " + " from da_item_season_report where is_deleted=0 " + sqlParam + ") disr on disr.par_da_ite_id = com.id  " + " left join (select d.par_da_ite_id from da_item_danger d where d.is_deleted=0 " + sqlParam + " union  select n.par_da_ite_id from da_item_season_report n where n.is_deleted=0 " + sqlParam + ") d " + " on d.par_da_ite_id=com.id ) val on zi.id=val.zjtype group by zi.name,zi.sort_num order by zi.sort_num";
		ResultSet res = companyPersistenceIface.findBySql(sql);
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
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 其他行业报送数据汇总统计（new）
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticForAxis2OtherByParam2(Statistic st) throws ApplicationAccessException {
		String cacheName = "";
		String cacheNum = "0";
		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_item = "da_item";
		int backup_date = 0; // 历史表查询的日期
		Statistic statistic;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}

		String sqlArea = "";
		String jArea = "";

		if (year < 2013 || (year == 2013 && month <= 11 && month >= 1)) { // 往月
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			da_item = "da_item_his";
			backup_date = 201311;
		} else if (year > year1 || (year1 == year && month >= month1)) { // 在当前日期之后
			da_company = "da_company";
			da_company_industry_rel = "da_company_industry_rel";
			da_industry_parameter = "da_industry_parameter";
			da_company_pass = "da_company_pass";
			da_item = "da_item";
		} else {
			da_company = "da_company_his";
			da_company_industry_rel = "da_company_industry_rel_his";
			da_industry_parameter = "da_industry_parameter_his";
			da_company_pass = "da_company_pass_his";
			da_item = "da_item_his";
			if (month < 10) {
				backup_date = Integer.parseInt(year + "0" + month);
			} else {
				backup_date = Integer.parseInt(year + "" + month);
			}
		}

		// liulj 加入MemCached缓存设置
//		if (year1 == year && month == month1) {
//			cacheNum = "0";
//		} else {
			cacheNum = year + ((month < 10) ? "0" : "") + month;
//		}
		cacheName = "nbyhpc_Axis2Other_" + cacheNum;
		MemCached cache = MemCached.getInstance();

		boolean catchDateExist=false;
		//如果缓存不存在的化，就先查找緩存备份表中是否存在数据，存在的话，取缓存备份表数据
		ResultSet stat = tcachePersistenceIface.findBySql("select  id from DA_STATISTIC  where S_TYPE='2' and IS_DELETED=0  and SENDMONTH=" + cacheNum + " ");
		try {
			if (stat.next()) { // 已存在,取缓存数据
				catchDateExist=true;
			}
			stat.getStatement().close();
			stat.close();
			cFactory.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			map = (Map<String, Object>) cache.get(cacheName);
			System.out.println("读取缓存:  " + cacheName);
		}else if(catchDateExist){
			System.out.println("读取缓存数据库表: " + cacheName);
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaStatistic.class, "t");
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" IS_DELETED=0 and S_TYPE='2' and SENDMONTH="+cacheNum+" "));
			List<DaStatistic> daStatistics = statisticPersistenceIface.loadStatistics(detachedCriteriaProxy, null);
			System.out.println("daStatistic.size() : " + daStatistics.size());
			if (daStatistics!=null&&daStatistics.size() > 0) {
				for (DaStatistic daStatistic : daStatistics) {
					statistic = new Statistic();
					statistic.setEnumName(daStatistic.getEnumName());
					statistic.setCompanyNum(daStatistic.getCompanyNum());
					statistic.setTroubNum(daStatistic.getTroubNum());
					statistic.setTroubCleanNum(daStatistic.getTroubCleanNum());
					statistic.setBigTroubNum(daStatistic.getBigTroubNum());
					statistic.setBigTroubCleanNum(daStatistic.getBigTroubCleanNum());
					statistic.setTarget(daStatistic.getTarget());
					statistic.setGoods(daStatistic.getGoods());
					statistic.setResource(daStatistic.getsResource());
					statistic.setFinishDate(daStatistic.getFinishDate());
					statistic.setSafetyMethod(daStatistic.getSafetyMethod());
					statistic.setDdng5Num(daStatistic.getDdng5Num());
					statistic.setProviceRcNum(daStatistic.getProvicercNum());
					statistic.setCityRcNum(daStatistic.getCityrcNum());
					statistic.setAllCompanyNum(daStatistic.getAllcompanyNum());
					statistic.setQtRcNum(daStatistic.getQtrcNum());
					statistic.setGovernMoney(daStatistic.getGovernMoney());
					statistic.setNormalGovernMoney(daStatistic.getNormalgovernMoney());
					map.put(daStatistic.getsId(), statistic);
				}
			}
			//更新缓存记录信息
			this.updateCache("省局qita_yue表",cacheName, backup_date);
			
//			if (cacheNum.equals("0")) { // 动态的 设置过期时间
//				cache.set(cacheName, map, new Date(Nbyhpc.EXPIRATION_TIME));
//			} else { // 永不过期
				cache.add(cacheName, map);
//			}
			
		} else {

			if (st.getAreaCode() == 0L) {
				sqlArea = " and second_area!=0";
				jArea = " ";
			} else {
				if (st.getThirdCode() == null || st.getThirdCode() == 0L) {
					sqlArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
					jArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
				} else {
					sqlArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
					jArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
				}
			}
			String da_company_passSql = " " + (da_company_pass.equals("da_company_pass_his") ? "and pass.backup_date=" + backup_date + "   " : "") + " ";
			String da_itemSql = " " + (da_item.equals("da_item_his") ? "and di.backup_date=" + backup_date + "   " : "") + " ";
			// add by huangjl 对企业添加过滤掉当前月份的限制条件
			String createTime = year + "-" + month + "-01";
			String createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

			// add by huangjl
			// 添加当前年份以前的重大隐患----重大隐患创建时间小于当前年份的;--重大隐患治理为空的，或者重大隐患治理的创建年份大于重大隐患的创建时间，并且等于当前年份。
			// String
			// oldDangerSql="  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + year +
			// "-01-01','yyyy-MM-dd')  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='"
			// + year + "')) ";
			// String
			// oldDangerSql1="  union  select danger.id, danger.par_da_com_id from da_danger danger where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + year +
			// "-01-01','yyyy-MM-dd')    and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			int pYear = year - 1;
			String oldDangerSql = "  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='" + year + "')) ";
			String oldDangerSql1 = "  union  select danger.id, danger.par_da_com_id from da_danger danger where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'     and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			Integer nextYear = year + 1;
			String startTime = ConfigUtil.getPropertyValue("local.axis2.startTime");
			String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
			// String sqlDanger = " and create_time between to_date('" +
			// startTime +
			// "','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
			String sqlDanger = sqlParam;
			// String sqlGorver = " and create_time < to_date('" + mDateTime +
			// "','yyyy-MM-dd') ";

			// String sqlGorver = " and create_time between to_date('" + year +
			// "-01-01','yyyy-MM-dd') and to_date('" + mDateTime +
			// "','yyyy-MM-dd')";
			// String sqlGorver1 = " and create_time between to_date('" + year +
			// "-01-01','yyyy-MM-dd') and to_date('" + nextYear +
			// "-01-01','yyyy-MM-dd') ";
			String sqlGorver = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
			String sqlGorver1 = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";

			String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, sum(resources) " + " resources,sum(finish) finish,sum(method) method,sum(money) money  " + " , sum(dd_ng5_num), sum(p_rc_num), sum(ct_rc_num), sum(qt_rc_num), zi.id,sum(all_com_val) all_com_val, sum(normalMoney) normalMoney  " + " from (select id,name,sort_num from zj_industry where ( (type=" + Nbyhpc.OTHER_REPORT + " and id!=60) or  id=13 ) ) zi  " + " left join (select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.resources) resources,count(dd_ng.finish_date) finish,sum " + " (dd_ng.safety_method) method,0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from "

			+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type="
					+ Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea

					+ ") com "
					+ " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) "
					+ sqlDanger
					+ oldDangerSql
					+ ") dd on dd.par_da_com_id = com.id "
					+ " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money from  "
					+ " da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
					+ sqlGorver1
					+ ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and  "
					+ " g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					+ " select d.id from da_danger d  left join( "// 连接5落实
					+ " select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					+ sqlGorver1
					+ " ) g on g.par_da_dan_id=d.id   where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null "
					+ "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) "
					+ " ) dd_ng5 on dd_ng.id=dd_ng5.id "
					+ " group by com.zjtype  "

					// liulj 增加 已上报的企业 被删除行业 在统计表也将累计(未上报 当月的不包含)
					/**
					 * +
					 * "union  select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val1,count "
					 * +
					 * " (distinct(dd_ng.id)) ddng_val1,sum(dd_ng.target) target1,  sum(dd_ng.goods) goods1,sum "
					 * +
					 * " (dd_ng.resources) resources1,count(dd_ng.finish_date) finish1,sum "
					 * +
					 * " (dd_ng.safety_method) method1,0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 dd_val, 0 ddng_val,0 target, 0 goods,0 resources, 0 finish,0 method from "
					 * +
					 * " (select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					 * 
					 * +
					 * " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					 * +
					 * " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					 * + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					 * " and c.is_deleted=0 " + sqlArea + ") com " +
					 * " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) "
					 * + sqlDanger + ") dd on dd.par_da_com_id = com.id " +
					 * " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money from  "
					 * +
					 * " da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					 * + sqlGorver1 +
					 * ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and  "
					 * +
					 * " g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					 * + " select d.id from da_danger d  left join( "// 连接5落实 +
					 * " select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					 * + sqlGorver1 +
					 * " ) g on g.par_da_dan_id=d.id   where d.is_deleted=0 and g.par_da_dan_id is not null and d.id is not null "
					 * +
					 * "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) "
					 * + " ) dd_ng5 on dd_ng.id=dd_ng5.id " +
					 * " group by com.zjtype  "
					 **/

					// modify by huangjl 由于使用left join
					// 会丢掉已整改的企业信息，所有使用union链接，且去掉了d.target=1 and d.goods=1 and
					// d.resources=1 and d.safety_method=1 and d.finish_date is
					// not
					// null
					+ " union select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  0 dd_val,0 ddng_val,0 target,  0 goods, 0 resources,0 finish, 0 method,sum(dd_ng.govern_money) money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1, 0 normalMoney from "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money from  " + " da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlGorver1 + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and  "
					+ " d.id is not null) dd_ng on dd_ng.id=dd.id "
					+ " group by com.zjtype  "

					// add by huangjl 添加一般隐患累计资金统计
					+ " union select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  0 dd_val,0 ddng_val,0 target,  0 goods, 0 resources,0 finish, 0 method,0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1, sum(nor_ng.govern_money) normalMoney from "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select normal.govern_money, " + "          normal.id, " + "          normal.par_da_com_id " + "    from da_normal_danger normal " + "   where normal.is_deleted = 0 " + "      and normal.is_danger = 1 " + "      and normal.is_repaired = 1 " + sqlParam + "  ) nor_ng "
					+ " on nor_ng.par_da_com_id = com.id "

					+ " group by com.zjtype  "

					// modify by huangjl 使用union链接来单独查询达到五到位要求的重大隐患

					+ " union   " + "select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val," + " 0 ddng_val,0 target,  0 goods," + " 0 resources,0 finish, 0 method," + " 0 money,1 type, count(distinct(dd.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + " " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select da.id,da.par_da_com_id from da_danger da where  da.id not in (select dag.par_da_dan_id from da_danger_gorver  dag) and (da.is_deleted=0 or (da.is_deleted=1 and da.flag=1)) " + sqlGorver + " and (da.target=1 and da.goods=1 and da.resources=1 and da.safety_method=1 and da.finish_date is not null) " + oldDangerSql1
					+ " ) dd on dd.par_da_com_id = com.id  group by com.zjtype"

					+ " union   "
					// 省级挂牌 and g.par_da_dan_id is null
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, count(distinct(dd_rc.id)) p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney  from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " and ind.zj_type is not null"
					+ "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					// modify by huangjl 添加da_company_industry_rell
					+ "  union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "" + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + " " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "   and ind.type=1 and c.is_deleted=0  and second_area!=0"

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  " + "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.par_da_dan_id from da_danger d left join(" + "        select par_da_dan_id from da_rollcall_company where is_deleted=0 and levels = 'province_city' " + "     ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null" + " ) dd_rc on dd_rc.id=dd_ng.id"
					+ " group by com.zjtype"

					+ " union   "
					// 地市级挂牌 and g.par_da_dan_id is null
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, count(distinct(dd_rc.id)) ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join " + da_company_industry_rel + "  " + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " and ind.zj_type is not null"
					+ "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					// modify by huangjl 添加da_company_industry_rell
					+ "   union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "     and ind.type=1 and c.is_deleted=0  and second_area!=0"

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))   " + "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'city_city'" + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id"
					+ " group by com.zjtype"
					
					+ " union   "
					// 其他级挂牌 and g.par_da_dan_id is null(省局没有乡镇挂牌，因此不统计在内)
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, count(distinct(dd_rc.id)) qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join " + da_company_industry_rel + "  " + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " and ind.zj_type is not null"
					+ "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					// modify by huangjl 添加da_company_industry_rell
					+ "   union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "     and ind.type=1 and c.is_deleted=0  and second_area!=0"

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlDanger + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlGorver + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))   " + "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'county_county' " + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id"
					+ " group by com.zjtype"

					+ " union   " + " select com.zjtype,0 all_com_val,count (distinct(d.par_da_com_id)) com_val,count (distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val," + " 0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from  "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind " + " left join  " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + ") dnd " + " on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id  = dnd.id  " + " left join ((select d.id,d.par_da_com_id from da_danger d where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  " + sqlParam + oldDangerSql + " ) union  select n.id, n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + ") d on d.par_da_com_id=com.id  group by com.zjtype  "

					+ " union   " + " select com.zjtype,count (distinct(com.id)) all_com_val,0 com_val,0 dnd_val,0 dndg_val," + " 0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from  "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel
					+ " rel on rel.par_da_ind_id=ind.id "
					// add by huangjl 添加da_company_pass pass
					+ " left join " + da_company_pass + " pass on pass.par_da_com_id = rel.par_da_com_id "
					+ " left join  "
					+ da_company
					+ "  c on c.id=pass.par_da_com_id where ind.is_deleted=0 "
					+ createTimeSql
					+ "  and pass.is_deleted = 0 and pass.is_affirm = 1 "
					+ (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "")
					+ "  "
					+ (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "")
					+ "  "
					+ (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " "
					+ da_company_passSql
					+ " and ind.zj_type is not null "
					+ " and ind.type="
					+ Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					// +
					// " union select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					// +
					// " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					// +
					// " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					// + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					// " and c.is_deleted=0 " + sqlArea

					+ ") com group by com.zjtype  "

					+ " union " + " select ind.zjtype,sum(dsro.company_num) all_com_val,sum(dsro.company_num) com_val,sum (dsro.troub_num) dnd_val, " + " sum(dsro.troub_clean_num) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods," + " 0 resources,0 finish,0 method,0 money,3 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from (select i.id,i.zj_type zjtype from " + " " + da_industry_parameter + " i where i.is_deleted=0 and i.zj_type is not null and i.type=" + Nbyhpc.QUARTER_REPORT_TRADE_OTHER + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and i.backup_date=" + backup_date + "   " : "") + ") ind  " + " left join da_season_report_other dsro on dsro.par_da_ind_id=ind.id where dsro.is_deleted=0 and  dsro.second_area != 0  " + sqlParam + " " + jArea + " group by ind.zjtype  "

					+ " union " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.worker) resources,count(dd_ng.finish_date) finish,  " + " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,4 type, count(distinct(dd_ng5.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney " + " from (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + da_itemSql + sqlArea + ") com " + " left join (select id,par_da_ite_id from da_item_danger where is_deleted=0 " + sqlDanger + ") dd on dd.par_da_ite_id = com.id   " + " left join ( select d.id,d.target,d.goods,d.worker,d.safety_method,d.finish_date,g.money as govern_money from  "
					+ " da_item_danger d left join (select par_da_it_id,money from da_item_danger_gover where is_deleted=0 " + sqlGorver + ") g on g.par_da_it_id=d.id where d.is_deleted=0 and  " + " g.par_da_it_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					+ " select d.id from da_danger d left join( "// 连接5落实
					+ " select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlGorver + " ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null " + "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) " + " ) dd_ng5 on dd_ng.id=dd_ng5.id "

					+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,0 all_com_val,count (distinct(d.par_da_ite_id)) com_val,sum(disr.ordinary_danger_finding) dnd_val," + " (sum(disr.ordinary_danger_finding)-sum(disr.ordinary_danger_not_govern)) dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from   " + " (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + da_itemSql + sqlArea + ") com  " + " left join (select id,par_da_ite_id,ordinary_danger_finding,ordinary_danger_not_govern " + " from da_item_season_report where is_deleted=0 " + sqlParam + ") disr on disr.par_da_ite_id = com.id  " + " left join (select d.par_da_ite_id from da_item_danger d where d.is_deleted=0 " + sqlParam
					+ " union  select n.par_da_ite_id from da_item_season_report n where n.is_deleted=0 " + sqlParam + ") d " + " on d.par_da_ite_id=com.id "

					+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,count (distinct(com.id)) all_com_val,0 com_val,0 dnd_val, 0 dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1, 0 ddng_val1,0 target1, 0 goods1,0 resources1, 0 finish1,0 method1,0 normalMoney from   " + " (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + da_itemSql + sqlArea + ") com  "

					+ ") val on zi.id=val.zjtype group by zi.name,zi.id ";// order
																			// by
																			// zi.sort_num\
			System.out.println("sql:  " + sql);

			int id = 0;
			try {
				ResultSet res = companyPersistenceIface.findBySql(sql);
				Statistic s = new Statistic();
				Statistic s15 = new Statistic();
				s.setEnumName("total");
				while (res.next()) {
					statistic = new Statistic();
					id = res.getInt(17);
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
					// statistic.setGovernMoney(res.getDouble(12));
					statistic.setDdng5Num(res.getInt(13));
					statistic.setProviceRcNum(res.getInt(14));
					statistic.setCityRcNum(res.getInt(15));
					statistic.setQtRcNum(res.getInt(16));
					
					statistic.setAllCompanyNum(res.getInt(18));
					// statistic.setNormalGovernMoney(res.getDouble(18));

					// double类型在类里面进行四舍五入
					Double governMoney = res.getBigDecimal(12) == null ? 0 : res.getBigDecimal(12).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double normalGovernMoney = res.getBigDecimal(19) == null ? 0 : res.getBigDecimal(19).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

					statistic.setGovernMoney(governMoney);
					statistic.setNormalGovernMoney(normalGovernMoney);

					// statistics.add(statistic);
					map.put("s_" + id + "", statistic);

					s.setCompanyNum(s.getCompanyNum() == null ? res.getInt(2) : (s.getCompanyNum() + res.getInt(2)));
					s.setTroubNum(s.getTroubNum() == null ? res.getInt(3) : (s.getTroubNum() + res.getInt(3)));
					s.setTroubCleanNum(s.getTroubCleanNum() == null ? res.getInt(4) : (s.getTroubCleanNum() + res.getInt(4)));
					s.setBigTroubNum(s.getBigTroubNum() == null ? res.getInt(5) : (s.getBigTroubNum() + res.getInt(5)));
					s.setBigTroubCleanNum(s.getBigTroubCleanNum() == null ? res.getInt(6) : (s.getBigTroubCleanNum() + res.getInt(6)));
					s.setTarget(s.getTarget() == null ? res.getInt(7) : (s.getTarget() + res.getInt(7)));
					s.setGoods(s.getGoods() == null ? res.getInt(8) : (s.getGoods() + res.getInt(8)));
					s.setResource(s.getResource() == null ? res.getInt(9) : (s.getResource() + res.getInt(9)));
					s.setFinishDate(s.getFinishDate() == null ? res.getInt(10) : (s.getFinishDate() + res.getInt(10)));
					s.setSafetyMethod(s.getSafetyMethod() == null ? res.getInt(11) : (s.getSafetyMethod() + res.getInt(11)));
					s.setGovernMoney(s.getGovernMoney() == null ? governMoney : (s.getGovernMoney() + governMoney));
					s.setDdng5Num(s.getDdng5Num() == null ? res.getInt(13) : (s.getDdng5Num() + res.getInt(13)));
					s.setProviceRcNum(s.getProviceRcNum() == null ? res.getInt(14) : (s.getProviceRcNum() + res.getInt(14)));
					s.setCityRcNum(s.getCityRcNum() == null ? res.getInt(15) : (s.getCityRcNum() + res.getInt(15)));
					s.setQtRcNum(s.getQtRcNum() == null ? res.getInt(16) : (s.getQtRcNum() + res.getInt(16)));
					s.setAllCompanyNum(s.getAllCompanyNum() == null ? res.getInt(18) : (s.getAllCompanyNum() + res.getInt(18)));
					s.setNormalGovernMoney(s.getNormalGovernMoney() == null ? normalGovernMoney : (s.getNormalGovernMoney() + normalGovernMoney));
					if (id == 33 || id == 27 || id == 38 || id == 39 || id == 40 || id == 41 || id == 42 || id == 34 || id == 35 || id == 36 || id == 37 || id == 43 || id == 44) {
						s15.setCompanyNum(s15.getCompanyNum() == null ? res.getInt(2) : (s15.getCompanyNum() + res.getInt(2)));
						s15.setTroubNum(s15.getTroubNum() == null ? res.getInt(3) : (s15.getTroubNum() + res.getInt(3)));
						s15.setTroubCleanNum(s15.getTroubCleanNum() == null ? res.getInt(4) : (s15.getTroubCleanNum() + res.getInt(4)));
						s15.setBigTroubNum(s15.getBigTroubNum() == null ? res.getInt(5) : (s15.getBigTroubNum() + res.getInt(5)));
						s15.setBigTroubCleanNum(s15.getBigTroubCleanNum() == null ? res.getInt(6) : (s15.getBigTroubCleanNum() + res.getInt(6)));
						s15.setTarget(s15.getTarget() == null ? res.getInt(7) : (s15.getTarget() + res.getInt(7)));
						s15.setGoods(s15.getGoods() == null ? res.getInt(8) : (s15.getGoods() + res.getInt(8)));
						s15.setResource(s15.getResource() == null ? res.getInt(9) : (s15.getResource() + res.getInt(9)));
						s15.setFinishDate(s15.getFinishDate() == null ? res.getInt(10) : (s15.getFinishDate() + res.getInt(10)));
						s15.setSafetyMethod(s15.getSafetyMethod() == null ? res.getInt(11) : (s15.getSafetyMethod() + res.getInt(11)));
						s15.setGovernMoney(s15.getGovernMoney() == null ? governMoney : (s15.getGovernMoney() + governMoney));
						s15.setDdng5Num(s15.getDdng5Num() == null ? res.getInt(13) : (s15.getDdng5Num() + res.getInt(13)));
						s15.setProviceRcNum(s15.getProviceRcNum() == null ? res.getInt(14) : (s15.getProviceRcNum() + res.getInt(14)));
						s15.setCityRcNum(s15.getCityRcNum() == null ? res.getInt(15) : (s15.getCityRcNum() + res.getInt(15)));
						s15.setQtRcNum(s15.getQtRcNum() == null ? res.getInt(16) : (s15.getQtRcNum() + res.getInt(16)));
						s15.setAllCompanyNum(s15.getAllCompanyNum() == null ? res.getInt(18) : (s15.getAllCompanyNum() + res.getInt(18)));
						s15.setNormalGovernMoney(s15.getNormalGovernMoney() == null ? normalGovernMoney : (s15.getNormalGovernMoney() + normalGovernMoney));
					}

				}
				map.put("s_total", s);
				map.put("s_15", s15);
				res.close();

//				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
//				ResultSet rs = null;
//				try {
//					if (c.next()) { // 已存在,更新
//						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='省局qita_yue表" + backup_date + "' where  id=" + c.getLong(1));
//						rs = pState.executeQuery();
//					} else { // 新建
//						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','省局qita_yue表" + backup_date + "',sysdate)");
//						rs = pState.executeQuery();
//					}
//					rs.close();
//					rs.getStatement().close();
//					cFactory.releaseConnection();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				
				//更新缓存记录信息
				this.updateCache("省局qita_yue表",cacheName, backup_date);
//				if (cacheNum.equals("0")) { // 24小时过期
				if (year1 == year && month == month1) {
					cache.set(cacheName, map, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.add(cacheName, map);
					//在缓存永远不过期的情况下，将缓存内容保存到数据库备份
					createDaStatistic4Other(cacheName, cacheNum, map);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	private void createDaStatistic4Other(String cacheName, String cacheNum, Map<String, Object> map) throws SQLException {
		if(map!=null){
			ResultSet rs = null;
			Iterator<Entry<String, Object>> it=map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Object> entity=it.next();
				String key=entity.getKey();
				Statistic sta=(Statistic) entity.getValue();
				
				String insertSql="insert  into da_statistic " +
						"(ID,SID,ENUM_NAME,COMPANY_NUM,TROUB_NUM,TROUB_CLEAN_NUM,BIG_TROUB_NUM," +
						"BIG_TROUB_CLEAN_NUM,TARGET,GOODS,S_RESOURCE,FINISH_DATE,SAFETY_METHOD," +
						"DDNG5_NUM,PROVICERC_NUM,CITYRC_NUM,ALLCOMPANY_NUM,QTRC_NUM,GOVERN_MONEY," +
						"NORMALGOVERN_MONEY,S_TYPE,SENDMONTH,CATCHNAME,IS_DELETED,CREATE_TIME,MODIFY_TIME)" +
						" values(hibernate_sequence.nextval,'"+key+"','"+sta.getEnumName()+"',"+sta.getCompanyNum()+","+sta.getTroubNum()+","+sta.getTroubCleanNum()+
						","+sta.getBigTroubNum()+","+sta.getBigTroubCleanNum()+","+sta.getTarget()+","+sta.getGoods()+""+
						","+sta.getResource()+","+sta.getFinishDate()+","+sta.getSafetyMethod()+","+sta.getDdng5Num()+"" +
						","+sta.getProviceRcNum()+","+sta.getCityRcNum()+","+sta.getAllCompanyNum()+","+sta.getQtRcNum()+"" +
						","+sta.getGovernMoney()+","+sta.getNormalGovernMoney()+",'2'" +
						","+Integer.valueOf(cacheNum)+",'"+cacheName+"',0,sysdate,sysdate)";
				
				System.err.println("insertSql="+insertSql);
				PreparedStatement pState = cFactory.createConnection().prepareStatement(insertSql);
				rs = pState.executeQuery();
			}
			rs.getStatement().close();
			rs.close();
			cFactory.releaseConnection();
			
		}
	}

	/**
	 * 打击三非报送数据汇总统计
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadStatisticForAxis2ThreeIllegal(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		String defaultMonth = formater.format(cal.getTime());
		String nowMonth = st.getYearMonth();
		Integer year = Integer.parseInt(nowMonth.split("-")[0]);
		Integer month = Integer.parseInt(nowMonth.split("-")[1]);
		GregorianCalendar gre = new GregorianCalendar(year, month - 1, 1);
		gre.add(Calendar.MONTH, 1);
		String nextMonthTime = formatter.format(gre.getTime());
		if (!nowMonth.equals(defaultMonth)) {
			mDateTime = nextMonthTime;
		}
		String sqlArea = " and second_area=" + st.getAreaCode() + "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
		}
		String sqlParam = " and create_time between to_date('" + year + "-01-01','yyyy-MM-dd') and to_date('" + mDateTime + "','yyyy-MM-dd')";
		String sql = " select zi.zj_id,zi.name, sum(dti.illegal_build_getting),sum(dti.illegal_build_candeled)," + " sum(dti.illegal_build_canceling),  sum(dti.illegal_produce_getting)," + " sum(dti.illegal_produce_canceled),sum(dti.illegal_produce_canceling)," + " sum(dti.illegal_trade_getting),sum(dti.illegal_trade_calceled),sum(dti.illegal_trade_canceling)" + " from zj_industry zi left join da_industry_parameter dip on dip.zj_type=zi.id " + " left join (select * from da_three_illegal where is_deleted=0 " + sqlParam + sqlArea + ") dti on dti.par_da_ind_id = dip.id " + " where dip.is_deleted =0 and zi.type=3 and dip.type=4 and zi.id is not null" + " group by zi.zj_id,zi.name,zi.sort_num order by zi.sort_num";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setType(res.getInt(1));
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
	 * 查询矿山等行业的统计数据
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadStatisticMineByParam(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Integer nowYear = st.getYear();
		Integer nextYear = nowYear + 1;
		String sqlArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
		}
		String sqlParam = " and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, " + " sum(resources) resources,sum(finish) finish,sum(method) method,sum(money) money " + " from (select id,name,sort_num from zj_industry where type=" + Nbyhpc.MINE_REPORT + " ) zi  " + " left join (select com.zjtype,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val, " + " count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods, " + " sum(dd_ng.resources) resources,count(dd_ng.finish_date) finish,sum(dd_ng.safety_method) method, " + " sum(dd_ng.govern_money) money,1 type from (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id " + " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
				+ " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlParam + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where d.is_deleted=0   " + " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id group by com.zjtype  " + " union   " + " select com.zjtype,count (distinct(d.par_da_com_id)) com_val,count(distinct(dnd.id)) dnd_val, " + " count(distinct(dnd_g.id)) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish, " + " 0 method,0 money,2 type  from (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id "
				+ " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + " ) dnd on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id = dnd.id   " + " left join (select d.par_da_com_id from da_danger d where d.is_deleted=0 " + sqlParam + " union  select  n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + " ) d on  " + " d.par_da_com_id=com.id group by com.zjtype ) val on zi.id=val.zjtype " + " group by zi.sort_num,zi.name order by zi.sort_num";
		ResultSet res = companyPersistenceIface.findBySql(sql);
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
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 查询矿山等行业的统计数据(new)
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticMineByParam2(Statistic st) throws ApplicationAccessException {

		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company_industry_rell = "da_company_industry_rell";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		int backup_date = 0; // 历史表查询的日期
		Statistic statistic;
		String cacheName = "";
		int nowYear = st.getYear();
		int cacheNum = 0;
		// System.out.println("查询nowYear: " + nowYear);
		String creatimeCompany = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		Map<String, Object> map = new HashMap<String, Object>();
		// System.out.println("当前year: " + year);
		if (nowYear != year) {
			cacheNum = nowYear;
			if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
				// 2013.11当年取动态
				da_industry_parameter = "da_industry_parameter_his";
				da_company = "da_company_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_company_pass = "da_company_pass_his";
				backup_date = 201311;
			} else if (st.getYear() > year && st.getYear() >= 2013) { // 往年
				// 2013(包括)之后取那年的12月份
				// yyyy-12
				da_industry_parameter = "da_industry_parameter";
				da_company_industry_rel = "da_company_industry_rel";
				da_company = "da_company";
				da_company_pass = "da_company_pass";
			} else {
				da_industry_parameter = "da_industry_parameter_his";
				da_company = "da_company_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_company_pass = "da_company_pass_his";
				backup_date = Integer.parseInt(st.getYear() + "12");
			}
		}

		// add by huangjl 对企业添加过滤掉当前月份的限制条件
		String createTimeSql = "";
		int month = 0;
		if (nowYear == year) {
			// 是当前年份，得到当前月份
			month = cal.get(Calendar.MONTH) + 1;
			String createTime = nowYear + "-" + month + "-01";
			createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

		} else {
			// 不是当前年份的话，去掉12月份的
			String createTime = nowYear + "-12-01";
			createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

		}
		// String createTime=nowYear+"-12-01";
		// String
		// createTimeSql=" and c.create_time <to_date('"+createTime+"','yyyy-MM-dd') ";

		String da_company_industry_relSql = " " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + " ";
		;

		String qcDlsqSql1 = " and  not exists (select par_da_com_id from  " + da_company_industry_rel + "  where c.id=par_da_com_id  and   par_da_ind_id=1438 " + da_company_industry_relSql + ") ";

		String qcDlsqSql2 = " and  not exists (select par_da_com_id from  da_company_industry_rell  where par_da_ind_id=1438 and  c.id=par_da_com_id) ";

		// System.out.println("backup_date: " + backup_date);

		// liulj 加入MemCached缓存设置
		cacheName = "nbyhpc_AxisMine_" + cacheNum;
		System.out.println("cacheName: " + cacheName);
		MemCached cache = MemCached.getInstance();
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			map = (Map<String, Object>) cache.get(cacheName);
			System.out.println("读取缓存: " + cacheName);
		} else {

			Integer nextYear = nowYear + 1;
			String sqlArea = "";
			if (st.getAreaCode() == 0L) {
				sqlArea = " and second_area!=0";
			} else {
				if (st.getThirdCode() == null || st.getThirdCode() == 0L) {
					sqlArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
				} else {
					sqlArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
				}
			}

			if (nowYear == year) {
				creatimeCompany = " and c.create_time < to_date('" + year + "-" + month + "-01','yyyy-MM-dd') ";
			} else {
				creatimeCompany = " and c.create_time < to_date('" + nextYear + "-01-01','yyyy-MM-dd') ";
			}
			String da_company_passSql = " " + (da_company_pass.equals("da_company_pass_his") ? "and pass.backup_date=" + backup_date + "   " : "") + " ";
			// 添加当前年份以前的重大隐患----重大隐患创建时间小于当前年份的;--重大隐患治理为空的，或者重大隐患治理的创建年份大于重大隐患的创建时间，并且等于当前年份。
			// String
			// oldDangerSql="  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + nowYear +
			// "-01-01','yyyy-MM-dd')  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='"
			// + nowYear + "')) ";
			// String
			// oldDangerSql1="  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + nowYear +
			// "-01-01','yyyy-MM-dd')   and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			int pYear = nowYear - 1;
			String oldDangerSql = "  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='" + nowYear + "')) ";
			String oldDangerSql1 = "  union  select danger.id, danger.par_da_com_id from da_danger danger  where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'    and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			String sqlParam = " and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";

			String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, " + " sum(resources) resources,sum(finish) finish,sum(method) method,sum(money) money " + " , sum(dd_ng5_num), sum(p_rc_num), sum(ct_rc_num), zi.id,sum(all_com_val) all_com_val, sum(qt_rc_num),sum(normalMoney) from (select id,name,sort_num from zj_industry where type=" + Nbyhpc.MINE_REPORT + " ) zi  " + " left join (select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val, " + " count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods, " + " sum(dd_ng.resources) resources,count(dd_ng.finish_date) finish,sum(dd_ng.safety_method) method, " + " 0 money,1 type, count(distinct(dd_ng5.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from ("

			+ "select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0  " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1

			// add by huangjl --添加da_company_industry_rell
					+ " union " + "select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "    and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted = 1 and flag=1)) " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) " + " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id " + " left join ( " + " select d.id from da_danger d "// 连接5落实
					+ "  where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and d.id is not null " + "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 ) " // and
																																														// d.finish_date
					+ " ) dd_ng5 on dd_ng.id=dd_ng5.id " + " group by com.zjtype  "

					// add by huangjl 累计落实隐患治理资金单独拿出来统计，用union单独来链接
					+ " union   " + "select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,0 dd_val, " + " 0 ddng_val,0 target,  0 goods, " + " 0 resources,0 finish,0 method, " + " sum(dd_ng.govern_money) money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "  and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1

					// add by huangjl --添加da_company_industry_rell
					+ " union " + " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + "   " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))   " + " and  d.id is not null) dd_ng on dd_ng.id=dd.id "

					+ " group by com.zjtype  "

					// add by huangjl 添加一般隐患治理资金的统计
					+ " union   " + "select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val,0 dd_val, " + " 0 ddng_val,0 target,  0 goods, " + " 0 resources,0 finish,0 method, " + " 0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, sum(nor_ng.govern_money) normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "  and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1

					// add by huangjl --添加da_company_industry_rell
					+ " union " + " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + "   " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select normal.govern_money,normal.id,normal.par_da_com_id from da_normal_danger normal where " + "         normal.is_deleted=0 and normal.is_danger=1 and normal.is_repaired=1  " + sqlParam + "        ) nor_ng " + " on nor_ng.par_da_com_id=com.id "

					+ " group by com.zjtype  "

					+ " union   "
					// 省级挂牌
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, count(distinct(dd_rc.id)) p_rc_num, 0 ct_rc_num,0 qt_rc_num ,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from  (" + "     "

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null" + "   " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "  and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql1

					// add by huangjl --添加da_company_industry_rell
					+ " union  select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0  " + createTimeSql + " and ind.zj_type is not null" + "     and ind.type=1 and c.is_deleted=0  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and second_area!=0 " + qcDlsqSql2

					+ " )" + " com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  "
					// and g.par_da_dan_id is null
					+ "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.par_da_dan_id from da_danger d left join(" + "        select par_da_dan_id from da_rollcall_company where is_deleted=0 and levels = 'province_city' " + "     ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null" + " ) dd_rc on dd_rc.id=dd_ng.id" + " group by com.zjtype"

					+ " union   "
					// 地市级挂牌 city
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, count(distinct(dd_rc.id)) ct_rc_num,0 qt_rc_num ,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from  (" + "     "

					+ "select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null" + "     and ind.type=1 " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and c.is_deleted=0  and second_area!=0 " + qcDlsqSql1

					// add by huangjl --添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rell + "" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null" + "    " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "  and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql2

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))   "
					// and g.par_da_dan_id is null

					+ "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'city_city' " + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id" + " group by com.zjtype"
					// 其他挂牌(省局没有乡级挂牌，因此不统计在内)
					+ " union   " + " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,count(distinct(dd_rc.id)) qt_rc_num,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null" + "   " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "  and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql1
					// add by huangjl --添加da_company_industry_rell
					+ "  union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + "  ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join  " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null" + "    " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=1 and c.is_deleted=0  and second_area!=0 " + qcDlsqSql2

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money    " + " from da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  "
					// and g.par_da_dan_id is null

					+ "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'county_county' " + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id" + " group by com.zjtype"

					+ " union   "

					+ " select com.zjtype,0 all_com_val,count (distinct(d.par_da_com_id)) com_val,count(distinct(dnd.id)) dnd_val, " + " count(distinct(dnd_g.id)) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish, " + " 0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num ,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1
					// add by huangjl --添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql2

					+ ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + " ) dnd on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id = dnd.id   "

					+ " left join ((select d.id, d.par_da_com_id from da_danger d where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) " + sqlParam + oldDangerSql + ") union  select n.id,  n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + " ) d on  " + " d.par_da_com_id=com.id group by com.zjtype "

					+ " union   "
					// 应排查企业数
					+ " select com.zjtype, count (distinct(com.id)) all_com_val,0 com_val,0 dnd_val, " + " 0 dndg_val,0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish, " + " 0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 qt_rc_num,0 dd_val1, 0 ddng_val1, 0 target1, 0 goods1, 0 resources1, 0 finish1, 0 method1, 0 normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id "
					// add by huangjl --添加da_company_pass
					// pass条件pass.is_deleted=0
					// and pass.is_affirm=1
					+ " left join " + da_company_pass + " pass on pass.par_da_com_id=rel.par_da_com_id "

					+ " left join " + da_company + " c on c.id= pass.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " " + da_company_passSql + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + qcDlsqSql1 + " and pass.is_deleted=0 and pass.is_affirm=1 "
					// add by huangjl --添加da_company_industry_rell
					// +" union select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					// +
					// " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					// +
					// " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					// + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					// " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + " ) dnd on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id = dnd.id   " + " left join ((select d.id,d.par_da_com_id from da_danger d where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) " + sqlParam + oldDangerSql + ")"

					+ " union  select n.id, n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + " ) d on  " + " d.par_da_com_id=com.id group by com.zjtype "

					+ ") val on zi.id=val.zjtype " + " group by zi.sort_num,zi.name, zi.id";
			System.out.println("sql111=" + sql);
			// log.info(sql);

			try {
				ResultSet res = companyPersistenceIface.findBySql(sql);
				Statistic s = new Statistic();
				s.setEnumName("total");
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
					// statistic.setGovernMoney(res.getDouble(12));
					statistic.setDdng5Num(res.getInt(13));
					statistic.setProviceRcNum(res.getInt(14));
					statistic.setCityRcNum(res.getInt(15));
					statistic.setAllCompanyNum(res.getInt(17));
					statistic.setQtRcNum(res.getInt(18));
					// statistic.setNormalGovernMoney(res.getDouble(19));

					// double类型在类里面进行四舍五入
					Double governMoney = res.getBigDecimal(12) == null ? 0 : res.getBigDecimal(12).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double normalGovernMoney = res.getBigDecimal(19) == null ? 0 : res.getBigDecimal(19).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

					statistic.setGovernMoney(governMoney);
					statistic.setNormalGovernMoney(normalGovernMoney);

					// statistics.add(statistic);
					map.put("s_" + res.getInt(16) + "", statistic);
					if (res.getInt(16) != 13) {
						s.setCompanyNum(s.getCompanyNum() == null ? res.getInt(2) : (s.getCompanyNum() + res.getInt(2)));
						s.setTroubNum(s.getTroubNum() == null ? res.getInt(3) : (s.getTroubNum() + res.getInt(3)));
						s.setTroubCleanNum(s.getTroubCleanNum() == null ? res.getInt(4) : (s.getTroubCleanNum() + res.getInt(4)));
						s.setBigTroubNum(s.getBigTroubNum() == null ? res.getInt(5) : (s.getBigTroubNum() + res.getInt(5)));
						s.setBigTroubCleanNum(s.getBigTroubCleanNum() == null ? res.getInt(6) : (s.getBigTroubCleanNum() + res.getInt(6)));
						s.setTarget(s.getTarget() == null ? res.getInt(7) : (s.getTarget() + res.getInt(7)));
						s.setGoods(s.getGoods() == null ? res.getInt(8) : (s.getGoods() + res.getInt(8)));
						s.setResource(s.getResource() == null ? res.getInt(9) : (s.getResource() + res.getInt(9)));
						s.setFinishDate(s.getFinishDate() == null ? res.getInt(10) : (s.getFinishDate() + res.getInt(10)));
						s.setSafetyMethod(s.getSafetyMethod() == null ? res.getInt(11) : (s.getSafetyMethod() + res.getInt(11)));
						s.setGovernMoney(s.getGovernMoney() == null ? governMoney : (s.getGovernMoney() + governMoney));
						s.setDdng5Num(s.getDdng5Num() == null ? res.getInt(13) : (s.getDdng5Num() + res.getInt(13)));
						s.setProviceRcNum(s.getProviceRcNum() == null ? res.getInt(14) : (s.getProviceRcNum() + res.getInt(14)));
						s.setCityRcNum(s.getCityRcNum() == null ? res.getInt(15) : (s.getCityRcNum() + res.getInt(15)));
						s.setAllCompanyNum(s.getAllCompanyNum() == null ? res.getInt(17) : (s.getAllCompanyNum() + res.getInt(17)));
						s.setQtRcNum(s.getQtRcNum() == null ? res.getInt(18) : (s.getQtRcNum() + res.getInt(18)));
						s.setNormalGovernMoney(s.getNormalGovernMoney() == null ? normalGovernMoney : (s.getNormalGovernMoney() + normalGovernMoney));
					}
				}
				map.put("s_total", s);
				res.close();

				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
				ResultSet rs = null;

				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='省局矿山_年表" + backup_date + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','省局矿山_年表" + backup_date + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.close();
					rs.getStatement().close();
					cFactory.releaseConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("cacheName: " + cacheName);
				if (cacheNum == 0) {
					cache.set(cacheName, map, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.add(cacheName, map);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	/**
	 * 查询其他行业的统计数据
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadStatisticOtherByParam(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Integer nowYear = st.getYear();
		Integer nextYear = nowYear + 1;
		String sqlArea = "";
		String jArea = "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
			jArea = " ";
		} else {
			if (st.getThirdCode() == 0L) {
				sqlArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
				jArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
			} else {
				sqlArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
				jArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
			}
		}
		String sqlParam = "and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods,  sum(resources) " + " resources,sum(finish) finish,sum(method) method,sum(money) money  " + " from (select id,name,sort_num from zj_industry where type=" + Nbyhpc.OTHER_REPORT + " ) zi  " + " left join (select com.zjtype,0 com_val,0 dnd_val,0 dndg_val,  count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.resources) resources,count(dd_ng.finish_date) finish, sum (dd_ng.safety_method) method," + " sum(dd_ng.govern_money) money,1 type  from (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id " + " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type="
				+ Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_danger where is_deleted=0 " + sqlParam + ") dd on dd.par_da_com_id = com.id " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money from  " + " da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and  " + " g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id group by com.zjtype  "

				+ " union   " + " select com.zjtype,count (distinct(d.par_da_com_id)) com_val,count " + " (distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val,  0 dd_val,0 ddng_val,0 target,0  " + " goods,0 resources,0 finish,0 method,0 money,2 type  from  " + " (select c.id ,ind.zj_type zjtype from da_industry_parameter ind " + " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id " + " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea + ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + ") dnd " + " on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id  = dnd.id  " + " left join (select d.par_da_com_id from da_danger d where d.is_deleted=0  " + sqlParam
				+ "  union  select  n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + ") d on d.par_da_com_id=com.id  group by com.zjtype  " + " union " + " select ind.zjtype,sum (dsro.company_num) com_val,sum (dsro.troub_num) dnd_val, " + " sum(dsro.troub_clean_num) dndg_val,0 dd_val,0 ddng_val,0 target,0 goods," + " 0 resources,0 finish,0 method,0 money,3 type from (select i.id,i.zj_type zjtype from " + " da_industry_parameter i where i.is_deleted=0 and i.zj_type is not null and i.type=" + Nbyhpc.QUARTER_REPORT_TRADE_OTHER + ") ind  " + " left join da_season_report_other dsro on dsro.par_da_ind_id=ind.id where dsro.is_deleted=0 " + sqlParam + " " + jArea + " group by ind.zjtype  "

				+ " union " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype ,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.worker) resources,count(dd_ng.finish_date) finish,  " + " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,4 type  " + " from (select di.id from  da_item di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + sqlArea + ") com " + " left join (select id,par_da_ite_id from  da_item_danger where is_deleted=0 " + sqlParam + ") dd on dd.par_da_ite_id = com.id   " + " left join ( select d.id,d.target,d.goods,d.worker,d.safety_method,d.finish_date,d.govern_money from  " + " da_item_danger d left join (select par_da_it_id from da_item_danger_gover where is_deleted=0 " + sqlParam + ") g on g.par_da_it_id=d.id where d.is_deleted=0 and  " + " g.par_da_it_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id  "

				+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,count (distinct(d.par_da_ite_id)) com_val,sum(disr.ordinary_danger_finding) dnd_val," + " (sum(disr.ordinary_danger_finding)-sum(disr.ordinary_danger_not_govern)) dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type  from   " + " (select di.id from  da_item di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + sqlArea + ") com  " + " left join (select id,par_da_ite_id,ordinary_danger_finding,ordinary_danger_not_govern " + " from da_item_season_report where is_deleted=0 " + sqlParam + ") disr on disr.par_da_ite_id = com.id  " + " left join (select d.par_da_ite_id from da_item_danger d where d.is_deleted=0 " + sqlParam + " union  select n.par_da_ite_id from da_item_season_report n where n.is_deleted=0 " + sqlParam + ") d " + " on d.par_da_ite_id=com.id ) val on zi.id=val.zjtype group by zi.name,zi.sort_num order by zi.sort_num";
		ResultSet res = companyPersistenceIface.findBySql(sql);
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
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}

	/**
	 * 查询其他行业的统计数据(new)
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticOtherByParam2(Statistic st) throws ApplicationAccessException {
		int cacheNum = 0;
		String cacheName = "";
		String da_industry_parameter = "da_industry_parameter";
		String da_company_industry_rel = "da_company_industry_rel";
		String da_company_industry_rell = "da_company_industry_rell";
		String da_company = "da_company";
		String da_company_pass = "da_company_pass";
		String da_item = "da_item";
		int backup_date = 0; // 历史表查询的日期
		Statistic statistic;
		Integer nowYear = st.getYear();
		Integer nextYear = nowYear + 1;
		String sqlArea = "";
		String jArea = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		Map<String, Object> map = new HashMap<String, Object>();
		if (nowYear != year) {
			cacheNum = nowYear;
			if (st.getYear() != year && st.getYear() < 2013) { // 往年:2013年之前都取
				// 2013.11当年取动态
				da_industry_parameter = "da_industry_parameter_his";
				da_company = "da_company_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_company_pass = "da_company_pass_his";
				da_item = "da_item_his";
				backup_date = 201311;
			} else if (st.getYear() > year && st.getYear() >= 2013) { // 往年
				// 2013(包括)之后取那年的12月份
				// yyyy-12
				da_industry_parameter = "da_industry_parameter";
				da_company_industry_rel = "da_company_industry_rel";
				da_company = "da_company";
				da_company_pass = "da_company_pass";
				da_item = "da_item";
			} else {
				da_industry_parameter = "da_industry_parameter_his";
				da_company = "da_company_his";
				da_company_industry_rel = "da_company_industry_rel_his";
				da_company_pass = "da_company_pass_his";
				da_item = "da_item_his";
				backup_date = Integer.parseInt(st.getYear() + "12");
			}
		}

		// System.out.println("backup_date: " + backup_date);

		// liulj 加入MemCached缓存设置
		cacheName = "nbyhpc_AxisOther_" + cacheNum;
		MemCached cache = MemCached.getInstance();

		// System.out.println("测试: "+ cache.get("test"));
		if ((st.getReFresh() == null || st.getReFresh() != true) && cache.get(cacheName) != null) {
			map = (Map<String, Object>) cache.get(cacheName);
			System.out.println("读取缓存 :" + cacheName);
		} else {

			if (st.getAreaCode() == 0L) {
				sqlArea = " and second_area!=0";
				jArea = " ";
			} else {
				if (st.getThirdCode() == null || st.getThirdCode() == 0L) {
					sqlArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
					jArea = " and second_area=" + st.getAreaCode();// +" and third_area="+st.getThirdCode();
				} else {
					sqlArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
					jArea = " and second_area=" + st.getAreaCode() + " and third_area=" + st.getThirdCode();
				}
			}

			// add by huangjl 对企业添加过滤掉当前月份的限制条件
			String createTimeSql = "";
			if (nowYear == year) {
				// 是当前年份，得到当前月份
				int month = cal.get(Calendar.MONTH) + 1;
				String createTime = nowYear + "-" + month + "-01";
				createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

			} else {
				// 不是当前年份的话，去掉12月份的
				String createTime = nowYear + "-12-01";
				createTimeSql = " and c.create_time <to_date('" + createTime + "','yyyy-MM-dd') ";

			}
			// 添加当前年份以前的重大隐患----重大隐患创建时间小于当前年份的;--重大隐患治理为空的，或者重大隐患治理的创建年份大于重大隐患的创建时间，并且等于当前年份。
			// String
			// oldDangerSql="  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + nowYear +
			// "-01-01','yyyy-MM-dd')  and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='"
			// + nowYear + "')) ";
			// String
			// oldDangerSql1="  union  select danger.id, danger.par_da_com_id from da_danger danger where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and danger.create_time <to_date('"
			// + nowYear +
			// "-01-01','yyyy-MM-dd')   and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			int pYear = nowYear - 1;
			String oldDangerSql = "  union  select danger.id, danger.par_da_com_id from da_danger danger left join da_danger_gorver gorver on danger.id=gorver.par_da_dan_id where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and (gorver.par_da_dan_id is null or ( to_char(gorver.create_time,'yyyy')>to_char(danger.create_time,'yyyy')  and to_char(gorver.create_time,'yyyy')='" + nowYear + "')) ";
			String oldDangerSql1 = "  union  select danger.id, danger.par_da_com_id from da_danger danger where (danger.is_deleted = 0 or (danger.is_deleted = 1 and danger.flag = 1))  and to_char(danger.create_time,'yyyy') ='" + pYear + "'   and (danger.target = 1 and danger.goods = 1 and  danger.resources = 1 and danger.safety_method = 1 and  danger.finish_date is not null)  and danger.id not in  (select dag.par_da_dan_id   from da_danger_gorver dag) ";

			String sqlParam = "and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
			// mine
			// String sql =
			// " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val,"
			// +
			// " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods,  sum(resources) "
			// +
			// " resources,sum(finish) finish,sum(method) method,sum(money) money  "
			// +
			// " , sum(dd_ng5_num), sum(p_rc_num), sum(ct_rc_num), zi.id, sum(all_com_val) all_com_val "
			// + " from (select id,name,sort_num from zj_industry where (type="
			// +
			// Nbyhpc.OTHER_REPORT + "  ) ) zi  " +
			// " left join (select com.zjtype, 0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,"
			// +
			// " count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,"
			// +
			// " sum(dd_ng.resources) resources,count(dd_ng.finish_date) finish, sum (dd_ng.safety_method) method,"
			// +
			// " sum(dd_ng.govern_money) money,1 type, count(distinct(dd_ng5.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num from (select c.id ,ind.zj_type zjtype from da_industry_parameter ind "

			String da_company_passSql = " " + (da_company_pass.equals("da_company_pass_his") ? "and pass.backup_date=" + backup_date + "   " : "") + " ";
			String da_itemSql = " " + (da_item.equals("da_item_his") ? "and di.backup_date=" + backup_date + "   " : "") + " ";

			String sql = " select zi.name,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val," + " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods,  sum(resources) " + " resources,sum(finish) finish,sum(method) method,sum(money) money  " + " , sum(dd_ng5_num), sum(p_rc_num), sum(ct_rc_num), sum(qt_rc_num), zi.id, sum(all_com_val) all_com_val,  sum(normalMoney) normalMoney " + " from (select id,name,sort_num from zj_industry where ( (type=" + Nbyhpc.OTHER_REPORT + " and id!=60)or id=13 ) ) zi  " + " left join (select com.zjtype, 0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val," + " count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods," + " sum(dd_ng.resources) resources,count(dd_ng.finish_date) finish, sum (dd_ng.safety_method) method," + " 0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from ("

			+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  "
					+ (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ "  and ind.zj_type is not null "
					+ " and ind.type="
					+ Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea

					+ ") com "
					+ " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) "
					+ sqlParam
					+ oldDangerSql
					+ ") dd on dd.par_da_com_id = com.id "
					+ " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money from  "
					+ " da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
					+ sqlParam
					+ ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and  "
					+ " g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					+ " select d.id from da_danger d left join( "// 连接5落实
					+ " select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					+ sqlParam
					+ " ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null "
					+ "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) "
					+ " ) dd_ng5 on dd_ng.id=dd_ng5.id "
					+ " group by com.zjtype  "

					// liulj 增加已上报的企业 被删除了行业的重大隐患数
					/**
					 * +
					 * " union select com.zjtype, 0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val1,"
					 * +
					 * " count(distinct(dd_ng.id)) ddng_val1,sum(dd_ng.target) target1,  sum(dd_ng.goods) goods1,"
					 * +
					 * " sum(dd_ng.resources) resources1,count(dd_ng.finish_date) finish1, sum (dd_ng.safety_method) method1,"
					 * +
					 * " 0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num,0 dd_val,0 ddng_val,0 target,0 goods, 0 resources,0 finish,0 method from (select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					 * +
					 * " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					 * +
					 * " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					 * + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					 * " and c.is_deleted=0 " + sqlArea + ") com " +
					 * " left join (select id,par_da_com_id from da_danger where is_deleted=0 "
					 * + sqlParam + ") dd on dd.par_da_com_id = com.id " +
					 * " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money from  "
					 * +
					 * " da_danger d left join (select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					 * + sqlParam +
					 * ") g on g.par_da_dan_id=d.id where d.is_deleted=0 and  "
					 * +
					 * " g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					 * + " select d.id from da_danger d left join( "// 连接5落实 +
					 * " select par_da_dan_id from da_danger_gorver where is_deleted=0 "
					 * + sqlParam +
					 * " ) g on g.par_da_dan_id=d.id where d.is_deleted=0 and g.par_da_dan_id is not null and d.id is not null "
					 * +
					 * "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) "
					 * + " ) dd_ng5 on dd_ng.id=dd_ng5.id " +
					 * " group by com.zjtype  "
					 **/

					// modify by huangjl 由于使用left join
					// 会丢掉已整改的企业信息，所有使用union链接来单独查询钱数，且去掉了d.target=1 and
					// d.goods=1
					// and d.resources=1 and d.safety_method=1 and d.finish_date
					// is
					// not null

					+ " union   " + "select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val," + " 0 ddng_val,0 target,  0 goods," + " 0 resources,0 finish, 0 method," + " sum(dd_ng.govern_money) money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money from  " + " da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlParam + ") g on g.par_da_dan_id=d.id "
					+ "where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and  d.id is not null ) dd_ng on dd_ng.id=dd.id "
					+ "group by com.zjtype"

					// add by huangjl 添加一般隐患累计资金查询
					+ " union   " + "select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val," + " 0 ddng_val,0 target,  0 goods," + " 0 resources,0 finish, 0 method," + " 0 money,1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1, sum(nor_ng.govern_money) normalMoney from ("

					+ " select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select normal.govern_money,normal.id,normal.par_da_com_id from da_normal_danger normal where " + " normal.is_deleted=0 and normal.is_danger=1 and normal.is_repaired=1  " + sqlParam + "         ) nor_ng "
					+ " on nor_ng.par_da_com_id=com.id "

					+ "group by com.zjtype"

					// modify by huangjl 使用union链接来单独查询达到五到位要求的重大隐患

					+ " union   " + "select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val," + " 0 ddng_val,0 target,  0 goods," + " 0 resources,0 finish, 0 method," + " 0 money,1 type, count(distinct(dd.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from ("

					+ "select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select da.id,da.par_da_com_id from da_danger da where da.id not in (select dag.par_da_dan_id from da_danger_gorver  dag) and (da.is_deleted=0 or (da.is_deleted=1 or da.flag=1)) " + sqlParam + " and (da.target=1 and da.goods=1 and da.resources=1 and da.safety_method=1 and da.finish_date is not null) " + oldDangerSql1
					+ " ) dd on dd.par_da_com_id = com.id  group by com.zjtype"

					+ " union   "
					// 省级挂牌
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, count(distinct(dd_rc.id)) p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  ("

					+ "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " and ind.zj_type is not null"
					+ "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					// modify by huangjl 添加da_company_industry_rell
					+ "   union  select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rell + "     rel on rel.par_da_ind_id=ind.id  left join  " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + "   and ind.type=1 and c.is_deleted=0  and second_area!=0"

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 "
					+ sqlParam
					+ ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))   "
					// and g.par_da_dan_id is null
					+ " and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.par_da_dan_id from da_danger d left join(" + "        select par_da_dan_id from da_rollcall_company where is_deleted=0 and levels = 'province_city' " + "     ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null" + " ) dd_rc on dd_rc.id=dd_ng.id"
					+ " group by com.zjtype"

					+ " union   "
					// 地市级挂牌
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, count(distinct(dd_rc.id)) ct_rc_num, 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  (" + "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "    rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " and ind.zj_type is not null"
					+ "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					// modify by huangjl 添加da_company_industry_rell
					+ "  union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null" + "     and ind.type=1 and c.is_deleted=0  and second_area!=0"

					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlParam
					+ ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  "
					// and g.par_da_dan_id is null

					+ "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'city_city'" + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id" + " group by com.zjtype"

					+ " union   "
					// 其他级挂牌(省局没有乡级挂牌，因此不统计在内)
					+ " select com.zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, 0 dd_val,  0 ddng_val, 0 target," + " 0 goods, 0 resources, 0 finish, 0 method," + " 0 money, 1 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, count(distinct(dd_rc.id)) qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  (" + "     select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join " + da_company_industry_rel + "    rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " and ind.zj_type is not null"
					+ "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					// modify by huangjl 添加da_company_industry_rell
					+ "  union   select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind  left join da_company_industry_rell" + "     rel on rel.par_da_ind_id=ind.id  left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "   " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null" + "     and ind.type=1 and c.is_deleted=0  and second_area!=0"
					
					+ " ) com  left join (" + "     select id,par_da_com_id from da_danger where (is_deleted=0 or (is_deleted=1 and flag=1)) " + "     " + sqlParam + oldDangerSql + ") dd on dd.par_da_com_id = com.id  " + " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,g.money as govern_money    " + " from da_danger d left join (select par_da_dan_id,money from da_danger_gorver where is_deleted=0 " + sqlParam
					+ ") g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  "
					// and g.par_da_dan_id is null
					
					+ "  and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( " + "     select d.id, g.g_id from da_danger d left join(" + "        select par_da_dan_id as g_id from da_rollcall_company where is_deleted=0 and levels = 'county_county' " + "     ) g on g.g_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.g_id is not null and d.id is not null " + " ) dd_rc on dd_ng.id=dd_rc.id" + " group by com.zjtype"


					+ " union   " + " select com.zjtype,0 all_com_val,count(distinct(d.par_da_com_id)) com_val,count " + " (distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val,  0 dd_val,0 ddng_val,0 target,0  " + " goods,0 resources,0 finish,0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join " + da_company_industry_rel + " rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + " c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "  " + (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "") + "  " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.zj_type is not null " + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					+ " union select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id " + " left join " + da_company + "  c on c.id=rel.par_da_com_id where ind.is_deleted=0 " + createTimeSql + " and ind.zj_type is not null " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "") + "    " + (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "") + " and ind.type=" + Nbyhpc.COMPANY_TRADE + " and c.is_deleted=0 " + sqlArea

					+ ") com " + " left join (select id,par_da_com_id from da_normal_danger where is_danger=1 and is_deleted=0 " + sqlParam + ") dnd " + " on dnd.par_da_com_id = com.id  left join (select id from da_normal_danger where is_danger=1 and " + " is_deleted = 0 and is_repaired=1) dnd_g on dnd_g.id  = dnd.id  " + " left join ((select d.id,d.par_da_com_id from da_danger d where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1))  " + sqlParam + oldDangerSql + ")  union  select n.id, n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " + sqlParam + ") d on d.par_da_com_id=com.id  group by com.zjtype  "

					+ " union   " + " select com.zjtype, count(distinct(com.id)) all_com_val,0 com_val, 0 dnd_val, 0 dndg_val,  0 dd_val,0 ddng_val,0 target,0  " + " goods,0 resources,0 finish,0 method,0 money,2 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from  "

					+ " (select c.id ,ind.zj_type zjtype from " + da_industry_parameter + " ind " + " left join  " + da_company_industry_rel
					+ " rel on rel.par_da_ind_id=ind.id "
					// modify by huangjl 添加da_company_pass pass
					+ " left join " + da_company_pass
					+ " pass  on pass.par_da_com_id = rel.par_da_com_id "

					+ " left join "
					+ da_company
					+ "  c on c.id=pass.par_da_com_id where ind.is_deleted=0 "
					+ createTimeSql
					+ " "
					+ (da_industry_parameter.equals("da_industry_parameter_his") ? "and ind.backup_date=" + backup_date + "   " : "")
					+ "  "
					+ (da_company_industry_rel.equals("da_company_industry_rel_his") ? "and rel.backup_date=" + backup_date + "   " : "")
					+ "  "
					+ (da_company.equals("da_company_his") ? "and c.backup_date=" + backup_date + "   " : "")
					+ " "
					+ da_company_passSql
					+ " and ind.zj_type is not null "
					+ " and ind.type="
					+ Nbyhpc.COMPANY_TRADE
					+ " and c.is_deleted=0  and pass.is_deleted = 0  and pass.is_affirm = 1 "
					+ sqlArea
					// modify by huangjl 添加da_company_industry_rell
					// +
					// " union select c.id ,ind.zj_type zjtype from da_industry_parameter ind "
					// +
					// " left join da_company_industry_rell rel on rel.par_da_ind_id=ind.id "
					// +
					// " left join da_company c on c.id=rel.par_da_com_id where ind.is_deleted=0 and ind.zj_type is not null "
					// + " and ind.type=" + Nbyhpc.COMPANY_TRADE +
					// " and c.is_deleted=0 " + sqlArea

					+ ") com group by com.zjtype  "

					+ " union "// 部门行业填报应报数和已报数相同
					+ " select ind.zjtype, sum(dsro.company_num) all_com_val, sum (dsro.company_num) com_val,sum (dsro.troub_num) dnd_val, " + " sum(dsro.troub_clean_num) dndg_val,0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney,0 dd_val,0 ddng_val,0 target,0 goods," + " 0 resources,0 finish,0 method,0 money,3 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num from (select i.id,i.zj_type zjtype from  " + da_industry_parameter + " i where i.is_deleted=0 and i.zj_type is not null and i.type=" + Nbyhpc.QUARTER_REPORT_TRADE_OTHER + "  " + (da_industry_parameter.equals("da_industry_parameter_his") ? "and i.backup_date=" + backup_date + "   " : "") + " ) ind  " + " left join da_season_report_other dsro on dsro.par_da_ind_id=ind.id where dsro.is_deleted=0  and dsro.second_area != 0 " + sqlParam + " " + jArea + " group by ind.zjtype  "

					+ " union " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,0 all_com_val,0 com_val,0 dnd_val,0 dndg_val, count(distinct(dd.id)) dd_val,count " + " (distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target,  sum(dd_ng.goods) goods,sum " + " (dd_ng.worker) resources,count(dd_ng.finish_date) finish,  " + " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,4 type, count(distinct(dd_ng5.id)) dd_ng5_num, 0 p_rc_num, 0 ct_rc_num , 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney " + " from (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + da_itemSql + sqlArea + ") com " + " left join (select id,par_da_ite_id from  da_item_danger where is_deleted=0 " + sqlParam + ") dd on dd.par_da_ite_id = com.id   " + " left join ( select d.id,d.target,d.goods,d.worker,d.safety_method,d.finish_date," + "g.money as govern_money from  "
					+ " da_item_danger d left join (select par_da_it_id,money from da_item_danger_gover where is_deleted=0 " + sqlParam + ") g on g.par_da_it_id=d.id where d.is_deleted=0 and  " + " g.par_da_it_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id left join ( "
					+ " select d.id from da_danger d left join( "// 连接5落实
					+ " select par_da_dan_id from da_danger_gorver where is_deleted=0 " + sqlParam + " ) g on g.par_da_dan_id=d.id where (d.is_deleted=0 or (d.is_deleted=1 and d.flag=1)) and g.par_da_dan_id is not null and d.id is not null " + "  and (d.target=1 and d.goods=1 and d.resources=1 and d.safety_method=1 and d.finish_date is not null) " + " ) dd_ng5 on dd_ng.id=dd_ng5.id "

					+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype,0 all_com_val,count(distinct(d.par_da_ite_id)) com_val,sum(disr.ordinary_danger_finding) dnd_val," + " (sum(disr.ordinary_danger_finding)-sum(disr.ordinary_danger_not_govern)) dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from   " + " (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + da_itemSql + sqlArea + ") com  " + " left join (select id,par_da_ite_id,ordinary_danger_finding,ordinary_danger_not_govern " + " from da_item_season_report where is_deleted=0 " + sqlParam + ") disr on disr.par_da_ite_id = com.id  " + " left join (select d.par_da_ite_id from da_item_danger d where d.is_deleted=0 " + sqlParam
					+ " union  select n.par_da_ite_id from da_item_season_report n where n.is_deleted=0 " + sqlParam + ") d " + " on d.par_da_ite_id=com.id "

					+ " union   " + " select " + Nbyhpc.HOUSE_ITEM_TRADE + " zjtype, count(distinct(com.id)) all_com_val,0 com_val, 0 dnd_val, 0 dndg_val,0 dd_val,0 ddng_val," + " 0 target,0 goods,0 resources,0 finish,0 method,0 money,5 type, 0 dd_ng5_num, 0 p_rc_num, 0 ct_rc_num, 0 qt_rc_num, 0 dd_val1,0 ddng_val1,0 target1,0 goods1, 0 resources1,0 finish1,0 method1,0 normalMoney from   " + " (select di.id from  " + da_item + " di where di.is_deleted=0 and di.type=1 and di.iscompleted=0 " + da_itemSql + sqlArea + ") com  "

					+ ") val on zi.id=val.zjtype group by zi.name,zi.id ";// order
																			// by
																			// zi.sort_num
			System.out.println("其他行业sql:  " + sql);

			try {
				ResultSet res = companyPersistenceIface.findBySql(sql);
				Statistic s = new Statistic();
				Statistic s15 = new Statistic();
				s.setEnumName("total");
				while (res.next()) {
					statistic = new Statistic();
					int id = res.getInt(17);
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
					// statistic.setGovernMoney(res.getDouble(12));
					statistic.setDdng5Num(res.getInt(13));
					statistic.setProviceRcNum(res.getInt(14));
					statistic.setCityRcNum(res.getInt(15));
					statistic.setQtRcNum(res.getInt(16));
					statistic.setAllCompanyNum(res.getInt(18));
					// statistic.setNormalGovernMoney(res.getDouble(18));

					// double类型在类里面进行四舍五入
					Double governMoney = res.getBigDecimal(12) == null ? 0 : res.getBigDecimal(12).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double normalGovernMoney = res.getBigDecimal(19) == null ? 0 : res.getBigDecimal(19).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

					statistic.setGovernMoney(governMoney);
					statistic.setNormalGovernMoney(normalGovernMoney);

					// statistics.add(statistic);
					map.put("s_" + id + "", statistic);

					s.setCompanyNum(s.getCompanyNum() == null ? res.getInt(2) : (s.getCompanyNum() + res.getInt(2)));
					s.setTroubNum(s.getTroubNum() == null ? res.getInt(3) : (s.getTroubNum() + res.getInt(3)));
					s.setTroubCleanNum(s.getTroubCleanNum() == null ? res.getInt(4) : (s.getTroubCleanNum() + res.getInt(4)));
					s.setBigTroubNum(s.getBigTroubNum() == null ? res.getInt(5) : (s.getBigTroubNum() + res.getInt(5)));
					s.setBigTroubCleanNum(s.getBigTroubCleanNum() == null ? res.getInt(6) : (s.getBigTroubCleanNum() + res.getInt(6)));
					s.setTarget(s.getTarget() == null ? res.getInt(7) : (s.getTarget() + res.getInt(7)));
					s.setGoods(s.getGoods() == null ? res.getInt(8) : (s.getGoods() + res.getInt(8)));
					s.setResource(s.getResource() == null ? res.getInt(9) : (s.getResource() + res.getInt(9)));
					s.setFinishDate(s.getFinishDate() == null ? res.getInt(10) : (s.getFinishDate() + res.getInt(10)));
					s.setSafetyMethod(s.getSafetyMethod() == null ? res.getInt(11) : (s.getSafetyMethod() + res.getInt(11)));
					s.setGovernMoney(s.getGovernMoney() == null ? governMoney : (s.getGovernMoney() + governMoney));
					s.setDdng5Num(s.getDdng5Num() == null ? res.getInt(13) : (s.getDdng5Num() + res.getInt(13)));
					s.setProviceRcNum(s.getProviceRcNum() == null ? res.getInt(14) : (s.getProviceRcNum() + res.getInt(14)));
					s.setCityRcNum(s.getCityRcNum() == null ? res.getInt(15) : (s.getCityRcNum() + res.getInt(15)));
					s.setQtRcNum(s.getQtRcNum() == null ? res.getInt(16) : (s.getQtRcNum() + res.getInt(16)));
					s.setAllCompanyNum(s.getAllCompanyNum() == null ? res.getInt(18) : (s.getAllCompanyNum() + res.getInt(18)));
					s.setNormalGovernMoney(s.getNormalGovernMoney() == null ? normalGovernMoney : (s.getNormalGovernMoney() + normalGovernMoney));
					if (id == 27 || id == 38 || id == 39 || id == 40 || id == 41 || id == 42 || id == 34 || id == 35 || id == 36 || id == 37 || id == 43 || id == 44) {
						s15.setCompanyNum(s15.getCompanyNum() == null ? res.getInt(2) : (s15.getCompanyNum() + res.getInt(2)));
						s15.setTroubNum(s15.getTroubNum() == null ? res.getInt(3) : (s15.getTroubNum() + res.getInt(3)));
						s15.setTroubCleanNum(s15.getTroubCleanNum() == null ? res.getInt(4) : (s15.getTroubCleanNum() + res.getInt(4)));
						s15.setBigTroubNum(s15.getBigTroubNum() == null ? res.getInt(5) : (s15.getBigTroubNum() + res.getInt(5)));
						s15.setBigTroubCleanNum(s15.getBigTroubCleanNum() == null ? res.getInt(6) : (s15.getBigTroubCleanNum() + res.getInt(6)));
						s15.setTarget(s15.getTarget() == null ? res.getInt(7) : (s15.getTarget() + res.getInt(7)));
						s15.setGoods(s15.getGoods() == null ? res.getInt(8) : (s15.getGoods() + res.getInt(8)));
						s15.setResource(s15.getResource() == null ? res.getInt(9) : (s15.getResource() + res.getInt(9)));
						s15.setFinishDate(s15.getFinishDate() == null ? res.getInt(10) : (s15.getFinishDate() + res.getInt(10)));
						s15.setSafetyMethod(s15.getSafetyMethod() == null ? res.getInt(11) : (s15.getSafetyMethod() + res.getInt(11)));
						s15.setGovernMoney(s15.getGovernMoney() == null ? governMoney : (s15.getGovernMoney() + governMoney));
						s15.setDdng5Num(s15.getDdng5Num() == null ? res.getInt(13) : (s15.getDdng5Num() + res.getInt(13)));
						s15.setProviceRcNum(s15.getProviceRcNum() == null ? res.getInt(14) : (s15.getProviceRcNum() + res.getInt(14)));
						s15.setCityRcNum(s15.getCityRcNum() == null ? res.getInt(15) : (s15.getCityRcNum() + res.getInt(15)));
						s15.setQtRcNum(s15.getQtRcNum() == null ? res.getInt(16) : (s15.getQtRcNum() + res.getInt(16)));
						s15.setAllCompanyNum(s15.getAllCompanyNum() == null ? res.getInt(18) : (s15.getAllCompanyNum() + res.getInt(18)));
						s15.setNormalGovernMoney(s15.getNormalGovernMoney() == null ? normalGovernMoney : (s15.getNormalGovernMoney() + normalGovernMoney));
					}
				}
				map.put("s_total", s);
				map.put("s_15", s15);
				res.close();

				ResultSet c = tcachePersistenceIface.findBySql("select  id,uptdate from T_CACHES  where name='" + cacheName + "' ");
				ResultSet rs = null;

				try {
					if (c.next()) { // 已存在,更新
						PreparedStatement pState = cFactory.createConnection().prepareStatement("update  T_CACHES t   set   t.uptdate=sysdate,content='省局qita_年表" + backup_date + "' where  id=" + c.getLong(1));
						rs = pState.executeQuery();
					} else { // 新建
						PreparedStatement pState = cFactory.createConnection().prepareStatement("insert  into T_CACHES (name,content,Uptdate)  values ('" + cacheName + "','省局qita_年表" + backup_date + "',sysdate)");
						rs = pState.executeQuery();
					}
					rs.close();
					rs.getStatement().close();
					cFactory.releaseConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (cacheNum == 0) { // 动态的 2000*60*60=2小时过期
					cache.set(cacheName, map, new Date(Nbyhpc.EXPIRATION_TIME));
				} else { // 永不过期
					cache.set(cacheName, map);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	/**
	 * 打击三非报表
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadThreeIllegal(Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Integer nowYear = st.getYear();
		Integer nextYear = nowYear + 1;
		String sqlArea = " and second_area=" + st.getAreaCode() + "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and second_area!=0";
		}
		String sqlParam = " and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sql = " select zi.zj_id,zi.name, sum(dti.illegal_build_getting),sum(dti.illegal_build_candeled)," + " sum(dti.illegal_build_canceling),  sum(dti.illegal_produce_getting)," + " sum(dti.illegal_produce_canceled),sum(dti.illegal_produce_canceling)," + " sum(dti.illegal_trade_getting),sum(dti.illegal_trade_calceled),sum(dti.illegal_trade_canceling)" + " from zj_industry zi left join da_industry_parameter dip on dip.zj_type=zi.id " + " left join (select * from da_three_illegal where is_deleted=0 " + sqlParam + sqlArea + ") dti on dti.par_da_ind_id = dip.id " + " where dip.is_deleted =0 and zi.type=3 and dip.type=4 and zi.id is not null" + " group by zi.zj_id,zi.name,zi.sort_num order by zi.sort_num";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setType(res.getInt(1));
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

	public void setSendPersistenceIface(SendPersistenceIface sendPersistenceIface) {
		this.sendPersistenceIface = sendPersistenceIface;
	}

	public void setZjDangerPersistenceIface(ZjDangerPersistenceIface zjDangerPersistenceIface) {
		this.zjDangerPersistenceIface = zjDangerPersistenceIface;
	}

	public void setBigTroubleFacadeIface(BigTroubleFacadeIface bigTroubleFacadeIface) {
		this.bigTroubleFacadeIface = bigTroubleFacadeIface;
	}

	public void setMineFacadeIface(MineFacadeIface mineFacadeIface) {
		this.mineFacadeIface = mineFacadeIface;
	}

	public void setMineDetailFacadeIface(MineDetailFacadeIface mineDetailFacadeIface) {
		this.mineDetailFacadeIface = mineDetailFacadeIface;
	}

	public void setDfzwFacadeIface(DfzwFacadeIface dfzwFacadeIface) {
		this.dfzwFacadeIface = dfzwFacadeIface;
	}

	public void setDfzwPunishFacadeIface(DfzwPunishFacadeIface dfzwPunishFacadeIface) {
		this.dfzwPunishFacadeIface = dfzwPunishFacadeIface;
	}

	public TCachePersistenceIface getTcachePersistenceIface() {
		return tcachePersistenceIface;
	}

	public void setTcachePersistenceIface(TCachePersistenceIface tcachePersistenceIface) {
		this.tcachePersistenceIface = tcachePersistenceIface;
	}

	/**
	 * 打击三非报表
	 * 
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadThreeIllegal(Statistic st, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic;
		Integer nowYear = st.getYear();
		Integer nextYear = nowYear + 1;
		String sqlArea = " and second_area=" + st.getAreaCode() + "";
		if (st.getAreaCode() == 0L) {
			sqlArea = " and first_area=" + userDetail.getFirstArea() + "";
		}
		String sqlParam = " and create_time between to_date('" + nowYear + "-01-01','yyyy-MM-dd') and to_date('" + nextYear + "-01-01','yyyy-MM-dd')";
		String sql = "select dip.id,dip.name, sum(dti.num_1_1),sum(dti.num_1_2), sum(dti.num_1_3), " + " sum(dti.num_2_1),sum(dti.num_2_2), sum(dti.num_2_3)," + " sum(dti.num_3_1),sum(dti.num_3_2), sum(dti.num_3_3)" + " from (select t.id as id,t.name as name from da_industry_parameter t where t.is_deleted =0 and t.type=4 and t.depiction like '%" + userDetail.getUserIndustry() + "%') " + " dip left join (" + " select par_da_ind_id,illegal_build_getting as num_1_1,illegal_build_canceling as num_1_2,illegal_build_candeled as num_1_3," + " illegal_produce_getting as num_2_1,illegal_produce_canceling as num_2_2,illegal_produce_canceled as num_2_3," + " illegal_trade_getting as num_3_1,illegal_trade_canceling as num_3_2,illegal_trade_calceled as num_3_3" + " from da_three_illegal t where is_deleted=0 " + sqlParam + " " + sqlArea + "" + ") dti" + " on dti.par_da_ind_id = dip.id " + " group by dip.id,dip.name ";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setType(res.getInt(1));
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
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return statistics;
	}

	public void saveSendTime(Statistic statistic) {

		try {
			ZjSend send = new ZjSend();
			send.setReportMonth(statistic.getYearMonth());
			send.setType(statistic.getType());
			send.setIsSend(0);
			send.setSetTime(statistic.getSetTime());
			send.setUserId(430371l);
			createSend(send);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the statisticPersistenceIface
	 */
	public StatisticPersistenceIface getStatisticPersistenceIface() {
		return statisticPersistenceIface;
	}

	/**
	 * @param statisticPersistenceIface the statisticPersistenceIface to set
	 */
	public void setStatisticPersistenceIface(
			StatisticPersistenceIface statisticPersistenceIface) {
		this.statisticPersistenceIface = statisticPersistenceIface;
	}
}
