package com.safetys.nbyhpc.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import cn.safetys.sms.SMSServiceClient;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.FkAreaFacadeIface;
import com.safetys.nbyhpc.facade.iface.SmsInfoFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.vo.CompanyVo;

/**
 * 短信信息
 * @author yangb
 *
 */
public class SmsInfoFacadeImpl implements SmsInfoFacadeIface {

	private static Logger log = Logger.getLogger(SmsInfoFacadeImpl.class);
	
	private String SMS_IP_KEY;
	
	private CompanyFacadeIface companyFacadeIface;
	
	private FkAreaFacadeIface fkAreaFacadeIface;
	
	public void save4SendToCompanyByMonth() {
		if (isAllowSend()) {
			String message = ConfigUtil.getPropertyValue("SMS_COMPANY_MSG_MONTH");
			log.info("==========开始发送短信给本月未填写隐患情况的企业=============");
			List<FkArea> areas = fkAreaFacadeIface.loadChildrens(Nbyhpc.AREA_CODE);
			for (FkArea fkArea : areas) {
				sendByArea(message, fkArea);
			}
			log.info("==========完成发送短信给本月未填写隐患情况的企业=============");
		}
	}

	private void sendByArea(String message, FkArea fkArea) {
		List<CompanyVo> companyVos = companyFacadeIface.findUnReportCompany4CurrentMonth(fkArea.getAreaCode());
		Set<String> phoneSet = new HashSet<String>();
		for (CompanyVo companyVo : companyVos) {
			String safetyPersonPhone = companyVo.getSafetyMngPersonPhone();
			if (StringUtils.hasText(safetyPersonPhone)) {
				if (phoneSet.contains(safetyPersonPhone)) {
					continue;
				}
				phoneSet.add(safetyPersonPhone);
				send(safetyPersonPhone, message);
			}
		}
	}

	public void sendByArea4Particial(List<Long> areaCodes, boolean include) {
		if (isAllowSend()) {
			String message = ConfigUtil.getPropertyValue("SMS_COMPANY_MSG_MONTH");
			if (include) {
				for (int i = 0; i < areaCodes.size(); i++) {
					FkArea fkArea = fkAreaFacadeIface.findByCode(areaCodes.get(i));
					if (fkArea != null) {
						sendByArea(message, fkArea);
					}
				}
			} else {
				List<FkArea> areas = fkAreaFacadeIface.loadChildrens(Nbyhpc.AREA_CODE);
				for (FkArea fkArea : areas) {
					//不包含
					if (areaCodes.contains(fkArea.getAreaCode())) {
						continue;
					}
					sendByArea(message, fkArea);
				}
			}
		}
	}

	public void save4SendToCompanyByQuarter() {
		if (isAllowSend()) {
			String message = ConfigUtil.getPropertyValue("SMS_COMPANY_MSG_QUARTER");
			log.info("==========开始发送短信给上一个季度未上报隐患季报的企业=============");
			List<FkArea> areas = fkAreaFacadeIface.loadChildrens(Nbyhpc.AREA_CODE);
			Set<String> phoneSet = new HashSet<String>();
			for (FkArea fkArea : areas) {
				List<CompanyVo> companyVos = companyFacadeIface.findUnReportCompany4PreQuarter(fkArea.getAreaCode());
				for (CompanyVo companyVo : companyVos) {
					String safetyPersonPhone = companyVo.getSafetyMngPersonPhone();
					if (StringUtils.hasText(safetyPersonPhone)) {
						if (phoneSet.contains(safetyPersonPhone)) {
							continue;
						}
						phoneSet.add(safetyPersonPhone);
						send(safetyPersonPhone, message);
					}
				}
			}
			log.info("==========完成发送短信给上一个季度未上报隐患季报的企业=============");
		}
	}

	public String send(String phone, String message) {
		if (StringUtils.hasText(phone)) {
			phone = phone.trim();
			if (phone.length() == 11 && phone.indexOf("-") < 0 && StringUtils.hasText(message)) {
				return SMSServiceClient.sendSms(SMS_IP_KEY, message, phone);
			}
		}
		return "";
	}
	
	private boolean isAllowSend() {
		return "true".equals(ConfigUtil.getPropertyValue("SMS_KEY"));
	}

	public String getSMS_IP_KEY() {
		return SMS_IP_KEY;
	}

	public void setSMS_IP_KEY(String sMS_IP_KEY) {
		SMS_IP_KEY = sMS_IP_KEY;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}
	
	public void setFkAreaFacadeIface(FkAreaFacadeIface fkAreaFacadeIface) {
		this.fkAreaFacadeIface = fkAreaFacadeIface;
	}
	
}
