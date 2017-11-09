package com.safetys.cas.web.action;

import java.util.List;

import com.safetys.cas.web.action.base.BaseAction;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.facade.iface.RoleFacadeIface;

/**
 * 设置角色
 * 
 * 配置子系统用户先设置默认角色
 * 
 * @author maomj
 *
 */
public class RoleSetAction  extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RoleFacadeIface roleFacadeIface;

	private List<FkRole> fkRoles;
	
	/**
	 * 配置默认角色
	 */
	public  Long[] roleIds;
	
	public static Long[] settingRoleIds={477413l}; //477413安监局查看权限    410安监部门
	
	public String loadRoles() {
		try {
			//
			fkRoles = roleFacadeIface.b();
			roleIds=RoleSetAction.settingRoleIds;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String defaultRoles() {
		RoleSetAction.settingRoleIds=roleIds;
		responseMessage.write(getText("global.updateok"), true);
		return SUCCESS;
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

	public void setRoleFacadeIface(RoleFacadeIface roleFacadeIface) {
		this.roleFacadeIface = roleFacadeIface;
	}
	
}
