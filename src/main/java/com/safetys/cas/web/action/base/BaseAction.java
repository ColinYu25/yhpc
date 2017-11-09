package com.safetys.cas.web.action.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.framework.web.model.ResponseMessage;

public class BaseAction extends ActionSupport implements Preparable {

	private static String themePath = "default";//

	private static String contextPath;
	
	protected ResponseMessage responseMessage = new ResponseMessage();

	public void prepare() throws Exception {

	}

	public String getContextPath() {
		if (contextPath == null) {
			contextPath = getRequest().getContextPath();
		}
		return contextPath;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected UserDetailWrapper getUserDetail() {
		SecurityContext context=SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		UserDetailWrapper user=(UserDetailWrapper)authentication.getPrincipal();
		return user;
	}
	
	protected Long getUserId() {
		return new Long(getUserDetail().getUserId());
	}
	
	protected boolean isAdminUser(){
		return "admin".equals(getUserDetail().getUsername());
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
	
	


}
