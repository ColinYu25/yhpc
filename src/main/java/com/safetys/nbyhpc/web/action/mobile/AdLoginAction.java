package com.safetys.nbyhpc.web.action.mobile;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.facade.iface.CasUserFacadeIface;
import com.safetys.framework.security.spring.encoding.PasswordEncoderIface;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.android.ClientAuthKey;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.mobile.util.AuthKeyGenerator;
import com.safetys.nbyhpc.util.RoleType;


public class AdLoginAction extends AndroidBaseAction {

	private static final long serialVersionUID = 3607874719944141906L;
	private CasUserFacadeIface casUserFacadeIface;
	private PasswordEncoderIface passwordEncoderIface;
	private CompanyFacadeIface companyFacadeIface;
	
	private String username;
	private String password;
	private String type;
	
	/**
	 * 登录验证服务<br/>
	 * POST params: username, password,type
	 * http://localhost:8080/nbyhpc/app/login.mobile?username=330200400042819&password=123456&type=1
	 * type: 1 企业 2政府
	 */
	public String login() {
		try {
			if (!"1".equals(type) && !"2".equals(type)) {
				jsonResult.setMsg("用户类型不能为空");
				jsonResult.setSuccess(false);
				jsonResult.setIdentify(M_RELOGIN);
				return SUCCESS;
			}
			if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
				FkUser user = casUserFacadeIface.loadUserByUserName(username);
				if (user != null) {
					if (user.getUserPass().equals(passwordEncoderIface.encodePassword(password))) {
						FkUserInfo fkUserInfo = user.getFkUserInfo();
						String userIndustry = fkUserInfo.getUserIndustry();
						if (StringUtils.isBlank(userIndustry)) {
							jsonResult.setMsg("用户账号信息不完整，请联系客服!");
							jsonResult.setSuccess(false);
							jsonResult.setIdentify(M_RELOGIN);
							return SUCCESS;
						}
						Map<String, Object> map = new HashMap<String, Object>();
						Set<FkRole> roles = user.getFkRoles();
						boolean isCompanyUser = false;
						for (FkRole fkRole : roles) {
							if (fkRole.getRoleCode().equals(RoleType.ROLE_COMPANY)) {
								isCompanyUser = true;
							}
						}
						if (isCompanyUser) {
							if (!"1".equals(type)) {
								jsonResult.setMsg("企业用户不能登录政府端");
								jsonResult.setSuccess(false);
								jsonResult.setIdentify(M_RELOGIN);
								return SUCCESS;
							}
							DaCompany company = companyFacadeIface.loadCompanyByUserId(user.getId());
							map.put("companyId", company.getId());
							if (!(company != null && company.getHzTradeTypes() != null && company.getHzTradeTypes().size() > 0)) {
								jsonResult.setMsg("此用户所对应的企业信息已被删除");
								jsonResult.setSuccess(true);
								jsonResult.setIdentify(M_RELOGIN);
								return SUCCESS;
							}
							map.put("monthReport", "请注意：每月1至25日为当月隐患排查上报时间");
							Calendar cal = Calendar.getInstance();
							int month = cal.get(Calendar.MONTH) + 1;
							int day = cal.get(Calendar.DAY_OF_MONTH);
							boolean exists = false;
							if ((month == 1 && day >= 25) || (month == 2 && day <= 15)
								|| (month == 4 && day >= 25) || (month == 5 && day <= 15)
								|| (month == 7 && day >= 25) || (month == 8 && day <= 15)
								|| (month == 10 && day >= 25) || (month == 11 && day <= 15)) {
								exists = companyFacadeIface.existUnReportPreQuarter(company.getId());
							}
							String seasonReport = exists ? "" : "每季度当月25至下月15为季报打印时间";
							map.put("seasonReport", seasonReport);
						} else {
							if (!"2".equals(type)) {
								jsonResult.setMsg("政府用户不能登录企业端");
								jsonResult.setSuccess(false);
								jsonResult.setIdentify(M_RELOGIN);
								return SUCCESS;
							}
						}
						Long userId = user.getId();
						// 生成密钥
						String key = AuthKeyGenerator.generate(userId, userIndustry);
						ClientAuthKey tKey = new ClientAuthKey(userId, key);
						// 屏蔽原密钥，保存新密钥
						clientAuthKeyFacadeIface.saveAfterClear(tKey);
						map.put("userName", user.getUserName());
						map.put("factName", fkUserInfo.getFactName());
						map.put("userCompany", fkUserInfo.getUserCompany());
						map.put("userPhone", fkUserInfo.getUserPhone());
						jsonResult.setData(map);
						jsonResult.setMsg("登录成功");
						jsonResult.setIdentify(key);
					} else {
						jsonResult.setMsg("登录验证失败，密码错误");
						jsonResult.setSuccess(false);
						jsonResult.setIdentify(M_RELOGIN);
					}
				} else {
					jsonResult.setMsg("登录验证失败，账号不存在");
					jsonResult.setSuccess(false);
					jsonResult.setIdentify(M_RELOGIN);
				}
			} else {
				jsonResult.setMsg("用户名和密码不能为空");
				jsonResult.setSuccess(false);
				jsonResult.setIdentify(M_RELOGIN);
			}
		} catch (Exception e) {
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPasswordEncoderIface(PasswordEncoderIface passwordEncoderIface) {
		this.passwordEncoderIface = passwordEncoderIface;
	}

	public CasUserFacadeIface getCasUserFacadeIface() {
		return casUserFacadeIface;
	}

	public void setCasUserFacadeIface(CasUserFacadeIface casUserFacadeIface) {
		this.casUserFacadeIface = casUserFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

}
