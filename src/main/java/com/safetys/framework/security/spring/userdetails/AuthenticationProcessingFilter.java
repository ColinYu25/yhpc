/*
 * @(#) AuthenticationProcessingFilter.java        
 * Date 2008-12-6                                           
 * Copyright (c) 2008 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.framework.security.spring.userdetails;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @author:seak.lv
 * @MSN:seak82@hotmail.com
 * @Email:<a href="mailto:seaklv@sina.com">seaklv@sina.com</a>
 * @created:2008-12-6
 * @modified:xxxx-xx-xx
 */
public class AuthenticationProcessingFilter extends
		org.springframework.security.ui.webapp.AuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_CHECK_NUMBER = "j_checknumber";
	private String checknumberParameter = SPRING_SECURITY_FORM_CHECK_NUMBER;
	private String checknumberFailureUrl;

	@Override
	public void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (requiresAuthentication(request, response)) {// request url is equal
														// to
														// '/security_check.xhtml'
			request.getSession().removeAttribute("easyPassword");
			String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
			String loginType = request.getParameter("loginType");
    		if ("qy".equals(loginType) && !Pattern.compile(Nbyhpc.PASSWORD_REGEX).matcher(password).find()) {
    			request.getSession().setAttribute("easyPassword", "true");
			}
			String checknumber = obtainChecknumber(request);
			if (checknumber == null) {
				checknumber = "";
			}
			HttpSession session = request.getSession(false);
			if (session != null) {
				Object obj = session.getAttribute("checkNumber");
				if (obj != null) {
					// 如果是从隐患系统单独登录的，则验证吗一定不为空。则一定要验证通过了，才能进入。
					session.removeAttribute("checkNumber");
					if (checknumber.equals(obj.toString())) {
						super.doFilterHttp(request, response, chain);
						return;
					}
				} else {
					// 如果不存在验证吗，则说明是从统一登录过来的，不需要验证
					super.doFilterHttp(request, response, chain);
					return;
				}
			}
			sendRedirect(request, response, checknumberFailureUrl);
			return;

		}
		chain.doFilter(request, response);
	}

	protected String obtainChecknumber(HttpServletRequest request) {
		return request.getParameter(checknumberParameter);
	}

	public void setChecknumberParameter(String checknumberParameter) {
		this.checknumberParameter = checknumberParameter;
	}

	public void setChecknumberFailureUrl(String checknumberFailureUrl) {
		this.checknumberFailureUrl = checknumberFailureUrl;
	}
}
