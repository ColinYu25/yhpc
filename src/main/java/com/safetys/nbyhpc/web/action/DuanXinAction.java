package com.safetys.nbyhpc.web.action;

import org.springframework.util.StringUtils;

import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.facade.iface.SmsInfoFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * 短信
 * @author yangb
 *
 */
public class DuanXinAction  extends DaAppAction {

	private static final long serialVersionUID = -1138835942958146505L;

	private String phone;
	
	private String message;
	
	private String result;
	
	private SmsInfoFacadeIface smsInfoFacadeIface;
	
	public String show() {
		return SUCCESS;
	}
	
	public String sendMonth() {
		smsInfoFacadeIface.save4SendToCompanyByMonth();
		return SUCCESS;
	}
	
	public String sendQuarter() {
		smsInfoFacadeIface.save4SendToCompanyByQuarter();
		return SUCCESS;
	}
	
	public String send() {
		if (!StringUtils.hasText(message)) {
			message = ConfigUtil.getPropertyValue("SMS_COMPANY_MSG_MONTH");
		}
		result = smsInfoFacadeIface.send(phone, message);
		return SUCCESS;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setSmsInfoFacadeIface(SmsInfoFacadeIface smsInfoFacadeIface) {
		this.smsInfoFacadeIface = smsInfoFacadeIface;
	}
	
}
