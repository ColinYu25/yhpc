package com.safetys.web.cas.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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

    /**
     * 白名单
     */
    private String[] whiteListURLs = null;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final String[] NULL_STRING_ARRAY = new String[0];

    private final String URL_SPLIT_PATTERN = "[, ;\r\n]"; 

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        super.init(filterConfig);
        String whiteListURLStr = filterConfig.getServletContext().getInitParameter("whiteListURL");
        whiteListURLs = strToArray(whiteListURLStr);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
//    	System.out.println("====================");
        HttpServletRequest request = ((HttpServletRequest)req);
//        System.out.println("request.getServletPath(): "+request.getServletPath());
        if (!isWhiteURL(request.getServletPath()) && !hasLogin(request)) {
//        	System.out.println("chain1: "+chain);
            super.doFilter(req, res, chain);
        } else {
//        	System.out.println("chain2: "+chain);
            chain.doFilter(req, res);
        }

    }

    protected boolean hasLogin(HttpServletRequest request) {
        // HttpSession session = request.getSession();
      
        Object o=request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        SecurityContext context =(SecurityContext)(o!=null?o: SecurityContextHolder.getContext());
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            Object p = authentication != null ? authentication.getPrincipal() : null;
            if (p != null && p instanceof UserDetailWrapper) {
                // if (session != null && session.getAttribute("login") != null)
                // {
                log.debug("request has login");
                return true;
                // }
            }
        }
        log.debug("request has not login");
        return false;
    }

    private String[] strToArray(String urlStr) {
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
        for (String whiteURL : whiteListURLs) {
            if (pathMatcher.match(whiteURL, currentURL)) {
                log.debug("url filter : white url list matches : " + whiteURL + " match " + currentURL + " continue");
                return true;
            }
            log.debug("url filter : white url list not matches : " + whiteURL + " match " + currentURL);
        }
        return false;
    }

}
