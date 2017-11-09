package com.safetys.nbyhpc.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.TargetUrlResolverImpl;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import cn.safetys.centerdb.EsUser;
import cn.safetys.constant.SystemConstant;
import cn.safetys.cxf.ws.rest.CasUser;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.persistence.iface.RolePersistenceIface;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.orm.hibernate3.PersistenceHibernateDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType;

/**
 * 
 * @author yangb
 *
 */
@SuppressWarnings("rawtypes")
public class CasAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

	private UserPersistenceIface userPersistenceIface;
	private PersistenceHibernateDao persistenceDao;
	private RolePersistenceIface rolePersistenceIface;
	private boolean forceSynchro = false;

	private boolean enablePasswordAuth = false;

	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setHeader("P3P", "CP=CAO PSA OUR");
		//没有企业用户
		if (requiresAuthentication(request, response)) {
			FkUser user=null;
			HttpSession session = request.getSession();
			CasUser casUser = getCasUser(session);
			EsUser esUser=null;
			if (casUser != null) {
				// 用casusername找
				user = getLocalUserByCasUserName(casUser.getUsername());
			} else {
				esUser = getESUser(session);
				// 企业直接用userName, 当然都可以改成用casUsername,记得改创建企业用户的地方更新cas_user_name字段
				user = getLocalUserByCasUser(esUser);
			}
			if (SystemConstant.P.isDataSeparation()) {
				if(casUser!=null&&!SystemConstant.P.isGovernment()){
					response.setContentType("text/html;charset=UTF-8");
		        	response.getWriter().write("非政府端，无法登录政府用户！");
		        	request.getSession().invalidate();
		        	return;
				}else if(esUser!=null&&!SystemConstant.P.isEnterprise()){
					response.setContentType("text/html;charset=UTF-8");
		        	response.getWriter().write("非企业端，无法登录企业用户！");
		        	request.getSession().invalidate();
				}
			}
			if (this.forceSynchro) {
				if (user == null) {
					if (casUser != null) {
						String roleCodes = RoleType.ROLE_AJJ_LOOK;//查看
						FkUser fkUser = new FkUser();
						FkUserInfo fkUserInfo = new FkUserInfo();
						String casUserName = casUser.getUsername();
						fkUser.setUserName(casUserName);
						fkUser.setUserPass(casUser.getPassword());
						fkUserInfo.setUserPhone(casUser.getPhone());
						fkUserInfo.setFirstArea(Nbyhpc.AREA_CODE);
						fkUserInfo.setFactName(casUser.getName());
						fkUser.setFkUserInfo(fkUserInfo);
						fkUserInfo.setFkUser(fkUser);
						DetachedCriteriaProxy criteria = DetachedCriteriaProxy.forClass(FkRole.class);
						criteria.add(RestrictionsProxy.in("roleCode", roleCodes.split(",")));
						List<FkRole> fkRoles = rolePersistenceIface.loadRoles(criteria);
						Set<FkRole> set = new HashSet<FkRole>(fkRoles);
						fkUser.setFkRoles(set);
						fkUserInfo.setFkUser(fkUser);
						fkUser.setFkUserInfo(fkUserInfo);
						userPersistenceIface.createUser(fkUser);
						persistenceDao.executeSQLUpdate("UPDATE FK_USER SET CAS_USER_NAME='" + casUserName + "' WHERE ID=" + fkUser.getId());
	                    user = getLocalUserByCasUser(casUser);
					} else {
						request.getSession().invalidate();
						response.sendRedirect(request.getContextPath() + "/login_qy.jsp?r=1");
						return;
					}
				}
			}
			if (user != null) {
				if(casUser!=null){
				if ((this.enablePasswordAuth) && (!user.getUserPass().equals(casUser.getPassword()))) {
					return;
				}
				}else{
					if ((this.enablePasswordAuth) && (!user.getUserPass().equals(esUser.getPassword()))) {
						return;
					}
				}
				persistenceDao.evict(user);
			}
			//用setTargetUrl 可能会有线程安全
			buildTargetUrl(request);
			super.doFilterHttp(request, response, chain);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * 默认跳转到系统首页，因此增加这个功能，详见TargetUrlResolverImpl
	 * @param request
	 */
	private void buildTargetUrl(HttpServletRequest request) {
		StringBuilder params = new StringBuilder();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if (!"ticket".equals(name) && !"usertype".equals(name) && !"aw".equals(name)) {
				if (params.length() > 0) {
					params.append("&");
				}
				String value = request.getParameter(name);
				params.append(name).append("=").append(value);
			}
		}
		String url = request.getServletPath() + "?" + params.toString();
		request.setAttribute(TargetUrlResolverImpl.DEFAULT_TARGET_PARAMETER, url);
	}

	private FkUser getLocalUserByCasUser(Object o){
		DetachedCriteriaProxy proxy = DetachedCriteriaProxy.forClass(FkUser.class);
		if (o instanceof CasUser) {
			proxy.add(RestrictionsProxy.eq("userName", ((CasUser) o).getUsername()));
		} else if (o instanceof EsUser) {
			//proxy.add(RestrictionsProxy.eq("userName", ((EsUser) o).getBusiness_reg_num()));
			//修改为等于工商注册号和组织机构代码中的一个就可以了
			proxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("userName", ((EsUser) o).getBusiness_reg_num()), 
					RestrictionsProxy.eq("userName", ((EsUser) o).getOrg_code())));
		}
		List<FkUser> users = userPersistenceIface.loadUsers(proxy);
		return users != null && users.size() > 0 ? users.get(0) : null;
	}
	
	private FkUser getLocalUserByCasUserName(String casUserName){
		List<BigDecimal> l = persistenceDao.executeSQLQuery("SELECT ID FROM FK_USER WHERE IS_DELETED = 0 and CAS_USER_NAME='" + casUserName + "'");
		Long id = 0l;
		if (l.size() > 0) {
			id = l.get(0).longValue();
			return userPersistenceIface.loadUser(id);
		}
		return null;
	}

	private CasUser getCasUser(HttpSession session) {
		Object o = session.getAttribute("casUser");
		return o != null ? (CasUser) o : null;
	}

	private EsUser getESUser(HttpSession session){
		Object o = session.getAttribute("esUser");
		return o != null ? (EsUser) o : null;
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Object o = session.getAttribute("casUser");
		if (o == null) {
			o = session.getAttribute("esUser");
		}
		if ((o != null) && ((o instanceof CasUser|| o instanceof EsUser ))) {
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			if ((authentication != null) && (authentication.getPrincipal() != null)) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CasUser casUser = getCasUser(session);
		FkUser user = null;
		if (casUser != null) {
			user = getLocalUserByCasUserName(getCasUser(session).getUsername());
		} else {
			user = getLocalUserByCasUser(getESUser(session));
		}
		return user.getUserPass();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CasUser casUser = getCasUser(session);
		FkUser user = null;
		if (casUser != null) {
			user = getLocalUserByCasUserName(getCasUser(session).getUsername());
		} else {
			user = getLocalUserByCasUser(getESUser(session));
		}
		return user.getUserName();
	}

	public void setForceSynchro(boolean forceSynchro) {
		this.forceSynchro = forceSynchro;
	}

	public void setEnablePasswordAuth(boolean enablePasswordAuth) {
		this.enablePasswordAuth = enablePasswordAuth;
	}

	public void setPersistenceDao(PersistenceHibernateDao persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public void setRolePersistenceIface(RolePersistenceIface rolePersistenceIface) {
		this.rolePersistenceIface = rolePersistenceIface;
	}

}
