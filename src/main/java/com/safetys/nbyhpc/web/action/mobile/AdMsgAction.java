package com.safetys.nbyhpc.web.action.mobile;

import java.util.HashMap;
import java.util.Map;

import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.android.ClientChangeLog;
import com.safetys.nbyhpc.facade.iface.android.ClientChangeLogFacadeIface;
import com.safetys.nbyhpc.util.OsType;

/**
 * 
 * @author yangb
 *
 */
public class AdMsgAction extends AndroidBaseAction {

	private static final long serialVersionUID = 9103554625432755174L;

	private String os;
	
	private String type;
	
	private ClientChangeLogFacadeIface clientChangeLogFacadeIface;
	
	private DaCompany company;
	
	/**
	 * params os=android / ios
	 * type= es / gov
	 * @return
	 */
	public String getVersion() {
		try {
			ClientChangeLog clientChangeLog = clientChangeLogFacadeIface.loadLastedClientChangeLog(os, type);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("versionCode", clientChangeLog.getVersionCode());
			data.put("versionNum", clientChangeLog.getVersionNum());
			if (OsType.ANDROID.getCode().endsWith(os)) {
				data.put("fileRealPath", getBaseURL() + getContextPath() + clientChangeLog.getFileInfo());
			}
			jsonResult.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setClientChangeLogFacadeIface(ClientChangeLogFacadeIface clientChangeLogFacadeIface) {
		this.clientChangeLogFacadeIface = clientChangeLogFacadeIface;
	}
	
}
