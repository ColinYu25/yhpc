package com.safetys.cas.web.action;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


import com.safetys.cas.web.action.base.BaseAction;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkSite;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.facade.iface.CasUserFacadeIface;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.framework.orm.hibernate3.PersistenceHibernateDao;
import com.safetys.framework.util.ConfigUtil;

/**
 * 配置用户
 * 
 * @author maomj
 */
public class UserAction extends BaseAction { 

	private static final long serialVersionUID = 5241392654492908378L;
	private FkUser fkUser;
	private List<FkUser> fkUsers;
	private List<FkRole> fkRoles;
	private FkUserInfo fkUserInfo;
	private UserFacadeIface userFacadeIface;
	private PersistenceHibernateDao persistenceDao;
	private Pagination pagination;
	private boolean saveCreate;
	private Long[] roleIds;

	public String bindingUser() {

		// 把要绑的用户发送给cas

		return SUCCESS;
	}

	public String createUser() {
		try {

			// casUser 转换为 fkUser fkUserInfo
			List l = persistenceDao.executeHQLQuery("select count(1) from FkUser where userName='" + fkUser.getUserName() + "'");
			boolean res = (l != null && l.size() > 0) ? true : false;
			if (res) {
				userFacadeIface.createUser(fkUser, fkUserInfo, RoleSetAction.settingRoleIds);

				responseMessage.write(getText("global.createok"), true);
			} else {
				// 修改操作请在子系统中进行
				responseMessage.write(getText("global.error"), false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.write(getText("global.error"), false);
		}
		return SUCCESS;
	}

	public String loadUsers() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
			}
			fkUsers = userFacadeIface.loadUsers(fkUser, fkUserInfo, roleIds, pagination);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 区域过滤
	 * 
	 * @return
	 */
	public String loadUsers2() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
			}
			fkUsers = userFacadeIface.loadUsers(fkUser, fkUserInfo, roleIds, pagination);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	private String userName;

	private String localUserName;

	private boolean forceUpdate = false;

	private boolean rePassword = false;

	private CasUserFacadeIface casUserFacadeIface;

	public String synchro() {

		//WebClient client = Client.getInstance(ConfigUtil.getPropertyValue("cas.ws.url"));
		//client.path(ConfigUtil.getPropertyValue("cas.synchro.user.url") + "/" + userName).accept(new String[] { "application/json" });

		//client.header("userId", new Object[] { getUserDetail().getUsername() });
		//client.header("userPass", new Object[] { getUserDetail().getPassword() });
//		try {
//			//CasUser casUser = (CasUser) client.get(CasUser.class);
//			CasUser casUser = null;
//			if (StringUtils.isBlank(localUserName)) {
//				localUserName = userName;
//			}
//			FkUser user = casUserFacadeIface.loadUserByUserName(localUserName);
//
//			if (user == null) {
//				// 获取默认权限
//				Long[] roldeIds = RoleSetAction.settingRoleIds;
//				if (roldeIds == null) {
//					// 角色未绑定
//					// 跳转页面
//					responseMessage.write(getText("synchro.role.binding.error"), false);
//					return SUCCESS;
//				}
//
//				FkUser fkUser = new FkUser();
//				FkUserInfo fkUserInfo = new FkUserInfo();
//
//				fkUser.setUserName(localUserName);
//				fkUser.setUserPass(casUser.getPassword());
//
//				fkUserInfo.setUserPhone(casUser.getPhone());
//				fkUserInfo.setFactName(casUser.getName());
//				casUserFacadeIface.createUser(fkUser, fkUserInfo, roldeIds, userName);
//				casUserFacadeIface.updateUser(fkUser, userName);
//
//			} else if (forceUpdate) {
//				user.setUserName(localUserName);
//				if (rePassword)
//					user.setUserPass(casUser.getPassword());
//				user.setDeleted(false);
//				FkUserInfo fkUserInfo = casUserFacadeIface.loadUseInfoByUserId(user.getId());
//				fkUserInfo.setUserPhone(casUser.getPhone());
//				fkUserInfo.setFactName(casUser.getName());
//				user.setFkUserInfo(fkUserInfo);
//				casUserFacadeIface.updateUser(user, userName);
//			}
//
//			// 同步成功
//			responseMessage.write(getText("synchro.cas.user.success"), true);
//
//		} catch (Exception e) {
//			// 无法获取中心账号，同步失败
//			e.printStackTrace();
//			responseMessage.write(getText("synchro.cas.user.error"), false);
//		}
		return SUCCESS;
	}

	public void setRePassword(boolean rePassword) {
		this.rePassword = rePassword;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public void setLocalUserName(String localUserName) {
		this.localUserName = localUserName;
	}

	public void setCasUserFacadeIface(CasUserFacadeIface casUserFacadeIface) {
		this.casUserFacadeIface = casUserFacadeIface;
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

	public UserFacadeIface getUserFacadeIface() {
		return userFacadeIface;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
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

	public void setPersistenceDao(PersistenceHibernateDao persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
