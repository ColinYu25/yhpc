package com.safetys.nbyhpc.web.action.mobile;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.Cookie;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.android.ClientAuthKey;
import com.safetys.nbyhpc.facade.iface.android.ClientAuthKeyFacadeIface;
import com.safetys.nbyhpc.mobile.util.AuthKeyGenerator;
import com.safetys.nbyhpc.web.action.mobile.MBaseAction;
import com.safetys.nbyhpc.web.action.mobile.vo.JsonResult;

/**
 * 安卓后台
 * @author yangb
 *
 */
public abstract class AndroidBaseAction extends MBaseAction {

	private static final long serialVersionUID = 4507078898902798000L;

	/**
	 * 客户端密钥Cookie name。
	 */
	protected String SAFETYS_CLIENT_AUTH_COOKIE_NAME = "SAFETYS_CLIENT_AUTH_KEY_NBYHPC";

	/**
	 * 客户端重登反馈，即登录失败。
	 */
	protected String M_RELOGIN = "_safetys_mobile_login_invalid";
	/**
	 * 密钥非法。
	 */
	protected String M_KEY_INVALID = "_safetys_mobile_key_invalid";
	/**
	 * 客户端访问服务器后台异常反馈。
	 */
	protected String M_EXCEPTION = "_safetys_mobile_server_exception";
	
	/**
	 * 业务操作提示，非异常
	 */
	protected String M_OPERATION = "_safetys_mobile_operation_hint";
	/**
	 * 返回给客户端的自定义标识信息。<br/>
	 * 可以为{@link M_RELOGIN}、{@link M_KEY_INVALID}、{@link M_EXCEPTION}、 {@link M_OPERATION}。
	 */
	protected String identify = M_OPERATION;
	
	protected JsonResult jsonResult = new JsonResult();

	protected Pagination pagination;// 分页对象
	
	/**
	 * 验证服务
	 */
	protected ClientAuthKeyFacadeIface clientAuthKeyFacadeIface;
	
	protected UserFacadeIface userFacadeIface;
	
	//初始化日志级别和密钥有效期限
	static {
		try {
			Properties prop = PropertiesLoaderUtils.loadAllProperties("mobile.properties");
			ClientAuthKey.ACTION_TIME_OUT = Long.valueOf(prop.getProperty("KEY.TIMEOUT", "2592000000"));//默认30天
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证密钥是否可用，所有对外开放业务服务理应携带密钥（登录无密钥），验证完后更新最后验证时间。
	 */
	protected boolean isAuthKeyValidate() {
		String key = getAuthKey();
		ClientAuthKey tKey = clientAuthKeyFacadeIface.findByKey(key);
		if (tKey != null) {
			Date now = new Date();
			if (ClientAuthKey.ACTIVE.equals(tKey.getStatus())) {
				if (!(now.getTime() > tKey.getActionDate().getTime() 
						&& (now.getTime() - tKey.getActionDate().getTime()) < ClientAuthKey.ACTION_TIME_OUT)) {
					tKey.setStatus(ClientAuthKey.DEAD);
				}
			}
			tKey.setActionDate(now);
			clientAuthKeyFacadeIface.update(tKey);
		}
		if (tKey == null) {
			jsonResult.setSuccess(false);
			jsonResult.setMsg("密钥验证失败，请重新登录");
			jsonResult.setIdentify(M_KEY_INVALID);
		} else if (tKey.getStatus().equals(ClientAuthKey.DEAD)) {
			// 是否有最新的密钥（别处登录）
			ClientAuthKey newKey = clientAuthKeyFacadeIface.findByUser(getUserId());
			if (newKey != null && !newKey.getAuthKey().equals(key)) {
				jsonResult.setMsg("您的帐号正在另一台手机上使用，请重新登录！");
			} else {
				jsonResult.setMsg("密钥验证失败，请重新登录");
			}
			jsonResult.setIdentify(M_KEY_INVALID);
			jsonResult.setSuccess(false);
		}
		return jsonResult.isSuccess();
	}

	/**
	 * 从Cookie获取客户端 提交 的验证密钥，客户端 HttpClientUtil 自动提交
	 */
	protected String getAuthKey() {
		String key = null;
		try {
			Cookie[] cookies = getRequest().getCookies();
			for (Cookie c : cookies) {
				if (c.getName().equals(SAFETYS_CLIENT_AUTH_COOKIE_NAME)) {
					key = c.getValue();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	protected Long getUserId() {
		String key = this.getAuthKey();
		return AuthKeyGenerator.extractId(key);
	}
	
	protected String getUserCode(){
		String key = this.getAuthKey();
		return AuthKeyGenerator.extractCode(key);
	}

	/***
	 * 手机后台没有走spring security
	 * 这里的用户名和密码是假的
	 */
	@Override
	public UserDetailWrapper getUserDetail() {
		try {
			FkUser user = userFacadeIface.loadUser(getUserId());
			FkUserInfo fkUserInfo = user.getFkUserInfo();
			UserDetailWrapper userDetail = new UserDetailWrapper(getUserId().intValue(), "notnull", "notnull", true, true, true, true, fkUserInfo.getFirstArea(), fkUserInfo.getSecondArea(), fkUserInfo.getThirdArea(), fkUserInfo.getFouthArea(), fkUserInfo.getFifthArea(), null, null, getUserCode());
			return userDetail;
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getIdentify() {
		return identify;
	}

	public JsonResult getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(JsonResult jsonResult) {
		this.jsonResult = jsonResult;
	}

	public void setClientAuthKeyFacadeIface(ClientAuthKeyFacadeIface clientAuthKeyFacadeIface) {
		this.clientAuthKeyFacadeIface = clientAuthKeyFacadeIface;
	}

	public Pagination getPagination() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}
	
}
