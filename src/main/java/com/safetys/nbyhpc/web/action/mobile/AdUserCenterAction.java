package com.safetys.nbyhpc.web.action.mobile;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.framework.security.spring.encoding.PasswordEncoderIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.mobile.vo.UserVo;

/**
 * 用户中心
 * @author yangb
 *
 */
public class AdUserCenterAction extends AndroidBaseAction {

	private static final long serialVersionUID = -4904190176253315441L;

	private UserVo userVo;
	
	private UserFacadeIface userFacadeIface;
	
	private PasswordEncoderIface passwordEncoderIface;
	
//	public String getInfo() {
//		try {
//			if (isAuthKeyValidate()) {
//				FkUser user = userFacadeIface.loadUser(getUserId());
//				userVo = new UserVo();
//				Map<String, Object> data = new HashMap<String, Object>();
//				data.put("userInfo", userVo);
//				jsonResult.setData(data);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			jsonResult.setMsg("服务请求失败");
//			jsonResult.setSuccess(false);
//			jsonResult.setIdentify(M_EXCEPTION);
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 这里的联系电话，在登录时候提供了
	 * params: userVo.userPhone
	 * @return
	 */
	public String updateUserInfo() {
		try {
			if (isAuthKeyValidate()) {
				FkUser user = userFacadeIface.loadUser(getUserId());
				FkUserInfo userInfo = user.getFkUserInfo();
				if (StringUtils.hasText(userVo.getUserPhone())) {
					userInfo.setUserPhone(userVo.getUserPhone());
				}
				userFacadeIface.updatePerson(user, userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	/**
	 * params : userVo.password userVo.oldPassword
	 * @return
	 */
	public String updatePassword() {
		try {
			if (isAuthKeyValidate()) {
				FkUser user = userFacadeIface.loadUser(getUserId());
				String oldPassword = passwordEncoderIface.encodePassword(userVo.getOldPassword());
				if (!user.getUserPass().equals(oldPassword)) {
					jsonResult.setMsg("原密码不正确!");
	    			jsonResult.setSuccess(false);
	    			jsonResult.setIdentify(M_OPERATION);
	    			return SUCCESS;
				}
				FkUserInfo userInfo = user.getFkUserInfo();
				String password = userVo.getPassword();
				if ("qiye".equals(userInfo.getUserIndustry())) {
		    		if (!Pattern.compile(Nbyhpc.PASSWORD_REGEX).matcher(password).find()) {
		    			jsonResult.setMsg("密码设置为：1、必须要有大小写的字母； 2、必须要有数字； 3、8-16位密码； 例如：Abc123456");
		    			jsonResult.setSuccess(false);
		    			jsonResult.setIdentify(M_OPERATION);
		    			return SUCCESS;
					}
				}
				user.setUserPass(password);
				userFacadeIface.updatePassword(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public void setPasswordEncoderIface(PasswordEncoderIface passwordEncoderIface) {
		this.passwordEncoderIface = passwordEncoderIface;
	}
	
}
