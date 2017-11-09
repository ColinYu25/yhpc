package cn.safetys.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import cn.safetys.annotation.Field_Method_Parameter_Annotation;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.safetys.framework.web.action.base.BaseAppAction;

public class MyInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 23242425252525L;

	@Field_Method_Parameter_Annotation(describ = "struts拦截器")
	public String intercept(ActionInvocation arg0) {

		Logger logger = Logger.getLogger("SYSTEM");
		BaseAppAction user = new BaseAppAction();
		MDC.put("username", user.getUserDetail().getUsername());
		MDC.put("realusername", user.getUserDetail().getFactName());
		HttpServletRequest request = ServletActionContext.getRequest();
		String remoteAddr = request.getRemoteAddr();
		MDC.put("ip", remoteAddr);
		MDC.put("method", request.getServletPath());

		try {
			logger.info("");
			return arg0.invoke();
		} catch (Exception e) {

			logger.error(e);
			e.printStackTrace();
			return  "error";
		}
	}

}
