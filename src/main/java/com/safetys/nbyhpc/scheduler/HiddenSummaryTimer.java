package com.safetys.nbyhpc.scheduler;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import cn.safetys.center.db.cxf.WsLoader;
import cn.safetys.center.db.cxf.lawyhinfo.ILAWYHINFO;
import cn.safetys.center.db.cxf.rcjc.IRCJC;
import cn.safetys.center.db.cxf.yh.wzlzd.IYHWZLZD;
import cn.safetys.center.db.cxf.yh.xx.IYHXX;
import cn.safetys.constant.SystemConstant;

import com.safetys.nbyhpc.domain.model.ETLSynInfo;
import com.safetys.nbyhpc.domain.persistence.iface.ETLSynInfoPersistenceIface;
import com.safetys.nbyhpc.facade.iface.HiddenSummaryFacadeIface;
import com.safetys.nbyhpc.util.HiddenTable;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * 中心库 标准化、网格化、执法、隐患同步到隐患汇总表
 * @author yangb
 *
 */
public class HiddenSummaryTimer {
	
	private static Logger logger = Logger.getLogger(HiddenSummaryTimer.class);

	private static final String SYN_DATA = "data";
	private static final String SYN_NO = "SYN_NO";
	
	private SystemConstant systemConstant;
	
	private HiddenSummaryFacadeIface hiddenSummaryFacadeIface;
	
	private ETLSynInfoPersistenceIface etlSynInfoPersistenceIface;
	
	public void receive() {
		if (systemConstant.isDataSeparation() && !systemConstant.isGovernment()) {
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("=============执法隐患信息同步开始===============");
				receiveLawYhInfo();
				logger.info("=============执法隐患信息同步结束===============");
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("=============网格化标准化隐患同步开始===============");
				receiveDailyCheck();
				logger.info("=============网格化标准化隐患同步开始===============");
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("=============隐患系统一般隐患同步开始===============");
				receiveHiddenInfo();
				logger.info("=============隐患系统一般隐患同步开始===============");
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("=============隐患系统重大隐患同步开始===============");
				receiveHiddenUnSerious();
				logger.info("=============隐患系统重大隐患同步开始===============");
			}
		}).start();
	}
	
	public void receiveLawYhInfo() {
		String fromTable = HiddenTable.T_LAW_YH_INFO.getCode();
		ETLSynInfo synInfo = etlSynInfoPersistenceIface.getSynNo(fromTable);
		Long synNo = synInfo.getSynNo();
		int i = 0;
		JSONObject dataJson = null;
		do {
			String jsonStr = WsLoader.getInterface(ILAWYHINFO.class).getNextINCInfo(Nbyhpc.TOKEN, synNo);
			JSONObject json = convertToJSON(jsonStr);
			dataJson = getDataJSON(json);
			if (dataJson != null && !dataJson.isEmpty()) {
				try {
					hiddenSummaryFacadeIface.updateFromCenterDB4Xzzf(dataJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.hasText(dataJson.getString(SYN_NO))) {
					synNo = getNextSynNo(dataJson);
				} else {
					//没有下一个增量的就直接保存
					i = 50;
				}
			}
			if (i++ == 50 || dataJson == null || dataJson.isEmpty()) {
				synInfo.setSynNo(synNo);
				etlSynInfoPersistenceIface.update(synInfo);
				i = 0;
			}
		} while (dataJson != null && !dataJson.isEmpty());
	}
	
	private void receiveDailyCheck() {
		String fromTable = HiddenTable.T_DAILY_CHECK.getCode();
		ETLSynInfo synInfo = etlSynInfoPersistenceIface.getSynNo(fromTable);
		Long synNo = synInfo.getSynNo();
		int i = 0;
		JSONObject dataJson = null;
		do {
			String jsonStr = WsLoader.getInterface(IRCJC.class).getNextINCInfo(Nbyhpc.TOKEN, synNo);
			JSONObject json = convertToJSON(jsonStr);
			dataJson = getDataJSON(json);
			if (dataJson != null && !dataJson.isEmpty()) {
				try {
					hiddenSummaryFacadeIface.updateFromCenterDB(fromTable, dataJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.hasText(dataJson.getString(SYN_NO))) {
					synNo = getNextSynNo(dataJson);
				} else {
					//没有下一个增量的就直接保存
					i = 50;
				}
			}
			if (i++ == 50 || dataJson == null || dataJson.isEmpty()) {
				synInfo.setSynNo(synNo);
				etlSynInfoPersistenceIface.update(synInfo);
				i = 0;
			}
		} while (dataJson != null && !dataJson.isEmpty());
	}
	
	private void receiveHiddenInfo() {
		String fromTable = HiddenTable.T_HIDDEN_INFO.getCode();
		ETLSynInfo synInfo = etlSynInfoPersistenceIface.getSynNo(fromTable);
		Long synNo = synInfo.getSynNo();
		int i = 0;
		JSONObject dataJson = null;
		do {
			String jsonStr = WsLoader.getInterface(IYHXX.class).getNextINCInfo(Nbyhpc.TOKEN, synNo);
			JSONObject json = convertToJSON(jsonStr);
			dataJson = getDataJSON(json);
			if (dataJson != null && !dataJson.isEmpty()) {
				try {
					hiddenSummaryFacadeIface.updateFromCenterDB(fromTable, dataJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.hasText(dataJson.getString(SYN_NO))) {
					synNo = getNextSynNo(dataJson);
				} else {
					//没有下一个增量的就直接保存
					i = 50;
				}
			}
			if (i++ == 50 || dataJson == null || dataJson.isEmpty()) {
				synInfo.setSynNo(synNo);
				etlSynInfoPersistenceIface.update(synInfo);
				i = 0;
			}
		} while (dataJson != null && !dataJson.isEmpty());
	}
	
	private void receiveHiddenUnSerious() {
		String fromTable = HiddenTable.T_HIDDEN_UN_SERIOUS.getCode();
		ETLSynInfo synInfo = etlSynInfoPersistenceIface.getSynNo(fromTable);
		Long synNo = synInfo.getSynNo();
		int i = 0;
		JSONObject dataJson = null;
		do {
			String jsonStr = WsLoader.getInterface(IYHWZLZD.class).getNextINCInfo(Nbyhpc.TOKEN, synNo);
			JSONObject json = convertToJSON(jsonStr);
			dataJson = getDataJSON(json);
			if (dataJson != null && !dataJson.isEmpty()) {
				try {
					hiddenSummaryFacadeIface.updateFromCenterDB(fromTable, dataJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.hasText(dataJson.getString(SYN_NO))) {
					synNo = getNextSynNo(dataJson);
				} else {
					//没有下一个增量的就直接保存
					i = 50;
				}
			}
			if (i++ == 50 || dataJson == null || dataJson.isEmpty()) {
				synInfo.setSynNo(synNo);
				etlSynInfoPersistenceIface.update(synInfo);
				i = 0;
			}
		} while (dataJson != null && !dataJson.isEmpty());
	}

	private Long getNextSynNo(JSONObject json) {
		//json.getLong 这个方法有BUG
		return Long.valueOf(json.getString(SYN_NO));
	}
	
	private JSONObject getDataJSON(JSONObject jsonObject) {
		if (jsonObject != null && jsonObject.has(SYN_DATA)) {
			return jsonObject.getJSONObject(SYN_DATA);
		}
		return null;
	}
	
	private JSONObject convertToJSON(String jsonString) {
		if (StringUtils.hasText(jsonString)) {
			return JSONObject.fromObject(jsonString);
		}
		return new JSONObject();
	}
	
	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}

	public void setHiddenSummaryFacadeIface(HiddenSummaryFacadeIface hiddenSummaryFacadeIface) {
		this.hiddenSummaryFacadeIface = hiddenSummaryFacadeIface;
	}

	public void setEtlSynInfoPersistenceIface(ETLSynInfoPersistenceIface etlSynInfoPersistenceIface) {
		this.etlSynInfoPersistenceIface = etlSynInfoPersistenceIface;
	}
	
}
