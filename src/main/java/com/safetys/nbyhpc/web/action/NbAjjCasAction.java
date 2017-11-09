package com.safetys.nbyhpc.web.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.safetys.framework.cas.client.util.HttpUtil;
import com.safetys.framework.cas.client.validation.CasProxyValidator;
import com.safetys.framework.cas.client.xmlobject.Role;
import com.safetys.framework.cas.client.xmlobject.Roles;
import com.safetys.framework.cas.client.xmlobject.User;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkSite;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.facade.iface.NbAjjCasFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class NbAjjCasAction extends DaAppAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768213960637985654L;

	private FkUser fkUser;

	private List<FkUser> fkUsers;

	private List<FkRole> fkRoles;

	private FkUserInfo fkUserInfo;

	private NbAjjCasFacadeIface nbAjjCasFacadeIface;

	private Pagination pagination;

	private boolean saveCreate;

	private Long[] ids;

	private Long[] roleIds;

	public static final String encode = ConfigUtil
			.getPropertyValue("cas.client.encode");

	public static final String contentType = ConfigUtil
			.getPropertyValue("cas.client.contentType");

	public static final String serviceUrl = ConfigUtil
			.getPropertyValue("cas.service.url");

	public void createUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reback = null;
		try {
			reback = CasProxyValidator.validate(request, serviceUrl, encode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (reback != null) {
			User user = new User();
			if (fkUser == null)
				fkUser = new FkUser();
			if (fkUserInfo == null)
				fkUserInfo = new FkUserInfo();
			roleIds = new Long[1];
			HttpServletResponse response = ServletActionContext.getResponse();
			try {

				Object object = HttpUtil.bindObject(User.class, request
						.getInputStream(), encode);
				if (object != null) {
					if (object instanceof User) {
						user = (User) object;
						fkUser.setUserName(user.getUserName());
						fkUser.setUserPass(user.getUserPass());
						fkUserInfo.setFactName(user.getRealName());
						fkUserInfo.setUserPhone(user.getUserPhone());
						fkUserInfo.setUserEmail(user.getUserEmail());
						fkUserInfo.setUserCompany(user.getUserCompany());
						fkUserInfo.setIdCard(user.getIdCard());
						fkUserInfo.setBornDate(user.getBornDate());
						fkUserInfo.setFirstArea(Nbyhpc.AREA_CODE);
						fkUserInfo.setSecondArea(0L);
						fkUserInfo.setThirdArea(0L);
						fkUserInfo.setUserIndustry(Nbyhpc.USER_INDUSTRY_ANJIAN);
						roleIds[0] = Long.parseLong(user.getUserRole());
					}
					fkUsers = nbAjjCasFacadeIface.checkDuplicateUser(fkUser
							.getUserName());// 检查用户名是否重复
					if (fkUsers != null && fkUsers.size() > 0) {
						user.setMessageCode(3);
					} else {
						nbAjjCasFacadeIface.createUser(fkUser, fkUserInfo,
								roleIds);
						user.setMessageCode(1);
					}
				} else {
					user.setMessageCode(2);
				}
			} catch (ApplicationAccessException ae) {
				if (log.isDebugEnabled()) {
					log.debug(ae.getOurMessage());
				}
				user.setMessageCode(2);
				ae.printStackTrace();
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(e.getMessage());
				}
				user.setMessageCode(2);
				e.printStackTrace();
			}
			HttpUtil.responseMessage(response, encode, user.getMessageCode());
		}
	}

	public void deleteUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reback = null;
		try {
			reback = CasProxyValidator.validate(request, serviceUrl, encode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (reback != null) {
			User user = new User();
			if (fkUser == null)
				fkUser = new FkUser();
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				Object object = HttpUtil.bindObject(User.class, request
						.getInputStream(), encode);
				if (object != null) {
					if (object instanceof User) {
						user = (User) object;
						fkUser.setUserName(user.getUserName());
					}
					nbAjjCasFacadeIface.deleteUser(fkUser.getUserName());
					user.setMessageCode(1);
				} else {
					user.setMessageCode(2);
				}
			} catch (ApplicationAccessException ae) {
				if (log.isDebugEnabled()) {
					log.debug(ae.getOurStackTrace());
				}
				addActionError(ae.getOurMessage());
				user.setMessageCode(2);
			} catch (Exception e) {
				e.printStackTrace();
				user.setMessageCode(2);
			}
			HttpUtil.responseMessage(response, encode, user.getMessageCode());
		}
	}

	public void updateUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reback = null;
		try {
			reback = CasProxyValidator.validate(request, serviceUrl, encode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (reback != null) {
			User user = new User();
			if (fkUser == null)
				fkUser = new FkUser();
			if (fkUserInfo == null)
				fkUserInfo = new FkUserInfo();
			roleIds = new Long[1];
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				Object object = HttpUtil.bindObject(User.class, request
						.getInputStream(), encode);
				if (object != null) {
					if (object instanceof User) {
						user = (User) object;
						fkUser.setUserName(user.getUserName());
						fkUser.setUserPass(user.getUserPass());
						fkUserInfo.setFactName(user.getRealName());
						fkUserInfo.setUserPhone(user.getUserPhone());
						fkUserInfo.setUserEmail(user.getUserEmail());
						fkUserInfo.setUserCompany(user.getUserCompany());
						fkUserInfo.setIdCard(user.getIdCard());
						fkUserInfo.setBornDate(user.getBornDate());
						fkUserInfo.setFirstArea(Nbyhpc.AREA_CODE);
						fkUserInfo.setSecondArea(0L);
						fkUserInfo.setThirdArea(0L);
						fkUserInfo.setUserIndustry(Nbyhpc.USER_INDUSTRY_ANJIAN);
						roleIds[0] = Long.parseLong(user.getUserRole());
					}
					nbAjjCasFacadeIface.mergeUser(fkUser, fkUserInfo, roleIds);
					user.setMessageCode(1);
				} else {
					user.setMessageCode(1);
				}
			} catch (ApplicationAccessException ae) {
				if (log.isDebugEnabled()) {
					log.debug(ae.getOurStackTrace());
				}
				addActionError(ae.getOurMessage());
				ae.printStackTrace();
				user.setMessageCode(2);
			} catch (Exception e) {
				e.printStackTrace();
				user.setMessageCode(2);
			}
			HttpUtil.responseMessage(response, encode, user.getMessageCode());
		}
	}

	public void updatePassword() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reback = null;
		try {
			reback = CasProxyValidator.validate(request, serviceUrl, encode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (reback != null) {
			User user = new User();
			if (fkUser == null)
				fkUser = new FkUser();
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				Object object = HttpUtil.bindObject(User.class, request
						.getInputStream(), encode);
				if (object != null) {
					if (object instanceof User) {
						user = (User) object;
						fkUser.setUserName(user.getUserName());
						fkUser.setUserPass(user.getNewUserPass());
					}
					nbAjjCasFacadeIface.updatePassword(fkUser);
					user.setMessageCode(1);
				} else {
					user.setMessageCode(2);
				}
			} catch (ApplicationAccessException ae) {
				if (log.isDebugEnabled()) {
					log.debug(ae.getOurStackTrace());
				}
				addActionError(ae.getOurMessage());
				user.setMessageCode(2);
			} catch (Exception e) {
				e.printStackTrace();
				user.setMessageCode(2);
			}
			HttpUtil.responseMessage(response, encode, user.getMessageCode());
		}
	}

	public void loadRoles() {
//		System.out.println("-------隐患登录");
		HttpServletRequest request = ServletActionContext.getRequest();
		String reback = null;
		try {
			reback = CasProxyValidator.validate(request, serviceUrl, encode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (reback != null) {
			Roles roles = new Roles();
			Set<Role> roleSet = new HashSet<Role>();
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				fkRoles = nbAjjCasFacadeIface.loadRoles();
				if (fkRoles != null && fkRoles.size() > 0) {
					for (FkRole fr : fkRoles) {
						Role role = new Role();
						role.setRoleCode(fr.getId().toString());
						role.setRoleName(fr.getRoleName());
						roleSet.add(role);
					}
				}
				roles.setRole(roleSet);
				request.setCharacterEncoding("gbk");
				response.setContentType(contentType);
				HttpUtil.responseXML(Roles.class, roles, response, encode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FkUser getFkUser() {
		return fkUser;
	}

	public void setFkUser(FkUser fkUser) {
		this.fkUser = fkUser;
	}

	public FkUserInfo getFkUserInfo() {
		return fkUserInfo;
	}

	public void setFkUserInfo(FkUserInfo fkUserInfo) {
		this.fkUserInfo = fkUserInfo;
	}

	public NbAjjCasFacadeIface getNbAjjCasFacadeIface() {
		return nbAjjCasFacadeIface;
	}

	public void setNbAjjCasFacadeIface(NbAjjCasFacadeIface nbAjjCasFacadeIface) {
		this.nbAjjCasFacadeIface = nbAjjCasFacadeIface;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<FkUser> getFkUsers() {
		return fkUsers;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setSaveCreate(boolean saveCreate) {
		this.saveCreate = saveCreate;
	}

	public boolean isSaveCreate() {
		return saveCreate;
	}

	public Long getId() {
		if (ids != null && ids.length > 0) {
			return ids[0];
		}
		return new Long(-1);
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public List<FkRole> getFkRoles() {
		return fkRoles;
	}

	public void setFkRoles(List<FkRole> fkRoles) {
		this.fkRoles = fkRoles;
	}

	/**
	 * @Description 因Set不能有重复值，用set过滤掉重复的站点
	 */
	public List<FkSite> getFkSites() {
		Set<FkSite> siteSet = new LinkedHashSet<FkSite>();
		if (fkRoles != null && fkRoles.size() > 0) {
			for (FkRole role : fkRoles) {
				siteSet.add(role.getFkSite());
			}
		}
		return new ArrayList<FkSite>(siteSet);
	}
}
