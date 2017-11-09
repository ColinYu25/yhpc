package com.safetys.nbyhpc.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;

public class CASFilter extends edu.yale.its.tp.cas.client.filter.CASFilter {

    protected final Log log = LogFactory.getLog(getClass());

    protected static final String USERTYPE_PARAM = "usertype"; 
    protected static final String AW_PARAM = "aw";
    /**
     * 白名单
     */
    private String[] whiteListURLs = null;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final String[] NULL_STRING_ARRAY = new String[0];

    private final String URL_SPLIT_PATTERN = "[, ;\r\n]";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        String whiteListURLStr = filterConfig.getServletContext().getInitParameter("whiteListURL");
        whiteListURLs = strToArray(whiteListURLStr);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = ((HttpServletRequest)req);
        HttpServletResponse response = (HttpServletResponse) res;
        if (!isWhiteURL(request.getServletPath()) && !hasLogin(request)) {
        	String usertype = (String) request.getSession().getAttribute("usertype");
			String aw = (String) request.getSession().getAttribute("aw");
			Cookie cookieUsertype = new Cookie(USERTYPE_PARAM, usertype);
			cookieUsertype.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookieUsertype);
			Cookie cookieAw = new Cookie(AW_PARAM, aw);
			cookieAw.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookieAw);
            super.doFilter(req, res, chain);
        } else {
            chain.doFilter(req, res);
        }

    }

    protected boolean hasLogin(HttpServletRequest request) {
        Object o = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        SecurityContext context = (SecurityContext)(o != null ? o : SecurityContextHolder.getContext());
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            Object p = authentication != null ? authentication.getPrincipal() : null;
            if (p != null && p instanceof UserDetailWrapper) {
                log.debug("request has login");
                return true;
            }
        }
        log.debug("request has not login");
        return false;
    }

    protected String[] strToArray(String urlStr) {
        if (urlStr == null) {
            return NULL_STRING_ARRAY;
        }
        String[] urlArray = urlStr.split(URL_SPLIT_PATTERN);

        List<String> urlList = new ArrayList<String>();

        for (String url : urlArray) {
            url = url.trim();
            if (url.length() == 0) {
                continue;
            }

            urlList.add(url);
        }

        return urlList.toArray(NULL_STRING_ARRAY);
    }

	protected boolean isWhiteURL(String currentURL) {
		return matchUrl(whiteListURLs, currentURL);
	}

    protected boolean matchUrl(String[] urls,String currentURL){
    	for (String u : urls) {
            if (pathMatcher.match(u, currentURL)) {
                log.debug("url filter : white url list matches : " + u + " match " + currentURL + " continue");
                return true;
            }
            log.debug("url filter : white url list not matches : " + u + " match " + currentURL);
        }
        return false;
    }
    
    protected boolean matchUniquUrl(String url, String currentURL) {
    	return pathMatcher.match(url, currentURL);
    }
}
