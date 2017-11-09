package com.safetys.nbyhpc.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

public class SocketsFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = -5951099819168082963L;
	private static org.apache.commons.logging.Log logWriter =
		   LogFactory.getLog(SocketsFilter.class.getName());
	private FilterConfig config; 

	public SocketsFilter(){
		super();
	}

	public void init(FilterConfig config) throws ServletException {
		 this.config=config;  
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		   request.setCharacterEncoding("utf-8");
		   response.setContentType("text/html;charset=utf-8");
		   HttpServletRequest request_ = (HttpServletRequest) request;
//		   String url = request_.getParameter("url");
//		   if (StringUtils.isEmpty(url)) {
//		    logWriter.fatal(" there is no url for this request");
//		   } else {
//		    logWriter.info(" url is " + url);
//		   }
		   HttpServletResponse res = (HttpServletResponse) response;
		   //iframe跨域访问
		   res.setHeader("P3P","CP=CAO PSA OUR");
		   if (chain != null)
		    chain.doFilter(request, response);
	}
	
	public void destroy() {
		this.config = null;
	}

}
