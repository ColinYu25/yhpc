package com.safetys.framework.security.spring.providers;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * 源码更改：
 *     考虑了生产环境和开发环境，默认为用户每次登录时，都会从数据库中重新加载数据，并更新缓存中的数据.可通过userLoginCacheReload控制，默认为true.
 * 源代码:
 *     官方显然只考虑生产环境下，用户权限不经常变更或不需要实时反映的情况，当用户退出或关闭浏览器重新登录时，如果缓存中存在，系统只从缓存中读取用户信息。
 *     （解释：用户退出或关闭浏览器，这只对会话起了作用（会话消失，不能登录），但不能对缓存起作用，缓存中数据依然存在，只有当缓存中数据超时时，数据才删除。）
 * 
 * @author:seak.lv
 * @MSN:seak82@hotmail.com
 * @Email:<a href="mailto:seaklv@sina.com">seaklv@sina.com</a>
 * @created:22010-09-01
 * @modified:xxxx-xx-xx
 */
public class DaoAuthenticationProvider extends org.springframework.security.providers.dao.DaoAuthenticationProvider{
	
	private boolean userLoginCacheReload=true;
    public Authentication authenticate(Authentication authentication)
	throws AuthenticationException { Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
            messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
            "Only UsernamePasswordAuthenticationToken is supported"));

    // Determine username
    String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

    boolean cacheWasUsed = true;
    UserDetails user=null;
    
    //begin     (author:seak.lv date:2010-09-01) 
    //解决了用户退出或关闭浏览器，重新登录后，用户信息和权限资源未变的问题，现考虑了生产环境和开发环境，默认为用户每次登录时，都会从数据库中重新加载数据，并更新缓存中的数据
    if(!userLoginCacheReload){
    	user = this.getUserCache().getUserFromCache(username);
    }
    //end
    
    if (user == null) {
        cacheWasUsed = false;

        try {
            user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
        } catch (UsernameNotFoundException notFound) {
            if (hideUserNotFoundExceptions) {
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            } else {
                throw notFound;
            }
        }

        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
    }

    try {
        getPreAuthenticationChecks().check(user);
        additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
    } catch (AuthenticationException exception) {
        if (cacheWasUsed) {
            // There was a problem, so try again after checking
            // we're using latest data (i.e. not from the cache)
            cacheWasUsed = false;
            user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            getPreAuthenticationChecks().check(user);
            additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
        } else {
            throw exception;
        }
    }

    getPreAuthenticationChecks().check(user);

    if (!cacheWasUsed) {
        this.getUserCache().putUserInCache(user);
    }

    Object principalToReturn = user;

    if (isForcePrincipalAsString()) {
        principalToReturn = user.getUsername();
    }

    return createSuccessAuthentication(principalToReturn, authentication, user);
}
	public boolean isUserLoginCacheReload() {
		return userLoginCacheReload;
	}
	public void setUserLoginCacheReload(boolean userLoginCacheReload) {
		this.userLoginCacheReload = userLoginCacheReload;
	}

}
