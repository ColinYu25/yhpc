package com.safetys.nbyhpc.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cn.safetys.cas.UserTypeEnum;
import cn.safetys.cas.context.CasSystemContext;

/**
 * 
 * @author yangb
 *
 */
public class LocalCasFilter extends CASFilter {
	
    private String LOGIN_OPTION;
    
    private String SSO_CAS_PATH;
    private String SSO_AW_PATH;
    private String SSO_ES_PATH;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        SSO_CAS_PATH = filterConfig.getInitParameter("sso_cas_logout");
        SSO_AW_PATH = filterConfig.getInitParameter("sso_aw_logout");
        SSO_ES_PATH = filterConfig.getInitParameter("sso_es_logout");
        LOGIN_OPTION = filterConfig.getInitParameter("login_option");
    }
	
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
    	HttpServletRequest request = (HttpServletRequest) req;
    	if (needCasValidate(request.getServletPath())) {
			HttpServletResponse response = (HttpServletResponse) res;
			boolean isLogin = hasLogin(request);
			String usertype = request.getParameter("usertype");
			String aw = request.getParameter("aw") != null ? request.getParameter("aw") : "false";
			if (StringUtils.isNotBlank(usertype)) {
				// 当前登录状态 且 是单点登录 且 单点登录类型发生了改变
				if (isLogin && usertypeChanage(request)) {
					request.getSession().invalidate();
					request.getSession().setAttribute("usertype", usertype);
					request.getSession().setAttribute("aw", aw);
					setLogoutPath(request.getSession(), usertype, aw);
				} else if (!isLogin && usertypeChanage(request)) {
					request.getSession().setAttribute("usertype", usertype);
					request.getSession().setAttribute("aw", aw);
					setLogoutPath(request.getSession(), usertype, aw);
				}
			} else if (!matchUniquUrl(LOGIN_OPTION, request.getServletPath())) {
				if (!isLogin && StringUtils.isBlank((String) request.getSession().getAttribute("usertype"))) {
					//获得cookie
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if (USERTYPE_PARAM.equals(cookie.getName())) {
								usertype = cookie.getValue();
							}
							if (AW_PARAM.equals(cookie.getName())) {
								aw = cookie.getValue();
							}
						}
					}
					if (StringUtils.isBlank(usertype)) {
						// 跳转选择登录页面
						response.sendRedirect(request.getContextPath() + LOGIN_OPTION);
						return;
					}
					request.getSession().setAttribute("usertype", usertype);
					request.getSession().setAttribute("aw", aw);
					setLogoutPath(request.getSession(), usertype, aw);
				}
			}
		}
		super.doFilter(req, res, chain);
    }
    
    @Override
    protected void redirectToCAS(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String usertype = request.getParameter("usertype");
		String aw = request.getParameter("aw");
		if (StringUtils.isBlank(usertype)) {
			usertype = (String) request.getSession().getAttribute("usertype");
			Object v = request.getSession().getAttribute("aw");
			aw = v != null ? v.toString() : "false";
		}
		if (usertype == null) {
			usertype = UserTypeEnum.CAS.getKey();
		}
		CasSystemContext.setUsertype(usertype);
		CasSystemContext.setAw(Boolean.valueOf(aw));
		super.redirectToCAS(request, response);
	}
    
    /**
     * 白名单和无权限的不需要进行CAS验证
     * @param currentURL
     * @return
     */
    protected boolean needCasValidate(String currentURL) {
        return !isWhiteURL(currentURL);
	}
    
    /**
     * CAS登录类型是否发生变更
     * @param request
     * @return
     */
	private boolean usertypeChanage(HttpServletRequest request) {
		String usertype = request.getParameter("usertype");
		String aw = (request.getParameter("aw") != null ? request.getParameter("aw") : "false");
		String s_usertype = (String) request.getSession().getAttribute("usertype");
		String s_aw = (String) request.getSession().getAttribute("aw");

		if (StringUtils.isBlank(usertype) && StringUtils.isNotBlank(s_usertype)) {
			return false;
		} else if (StringUtils.isNotBlank(usertype) && StringUtils.isNotBlank(s_usertype) && StringUtils.equals(usertype, s_usertype)) {
			return StringUtils.equals(usertype, UserTypeEnum.ES.getKey()) ? false : !StringUtils.equals(aw, s_aw);
		} else if (StringUtils.isBlank(usertype) && StringUtils.isBlank(s_usertype)) {
			return false;
		}
		return true;
	}
    
	private void setLogoutPath(HttpSession session, String usertype, String aw) {
		try {
			if (StringUtils.equals(UserTypeEnum.ES.getKey(), usertype)) {
				session.setAttribute("sso_logout", URLEncoder.encode(SSO_ES_PATH, "UTF-8"));
			} else if (StringUtils.equals(UserTypeEnum.CAS.getKey(), usertype) && StringUtils.equals("true", aw)) {
				session.setAttribute("sso_logout", URLEncoder.encode(SSO_AW_PATH, "UTF-8"));
			} else if (StringUtils.equals(UserTypeEnum.CAS.getKey(), usertype) && StringUtils.equals("false", aw)) {
				session.setAttribute("sso_logout",URLEncoder.encode(SSO_CAS_PATH, "UTF-8") );
			} else {
				session.setAttribute("sso_logout", null);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
