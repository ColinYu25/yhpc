package com.safetys.nbyhpc.util;

import java.util.Set;

import com.safetys.framework.domain.model.FkRole;

public class RoleType {
	public final static String ADMINISTRATOR = "ROLE_ADMINISTRATOR,ROLE_ANWEIBAN,ROLE_ANWEI_CITY";

	public final static String ROLE_COMPANY = "ROLE_COMPANY"; //企业
	
	public final static String ROLE_PIPECOMPANY = "ROLE_PIPECOMPANY";//管道企业
	
	public final static String MANAGER = "ROLE_MANAGER";

	public final static String CITY_PRO = "ROLE_CITY_PRO";

	public final static String COUNTY_PRO = "ROLE_COUNTY_PRO";

	public final static String TOWN_PRO = "ROLE_TOWN_PRO";
	
	public final static String ROLE_AJJ_LOOK = "ROLE_AJJ_LOOK";//安监局查看
	
	public final static String ROLE_JIANWEI = "ROLE_JIANWEI";
	
	/**
	 * 判断用户是否属于某个角色，使用模糊查询
	 * 
	 * @param roleCode
	 * @param listRole
	 * @param isLike
	 * @return
	 */
	public final static boolean isRoleByCode(String roleCode,
			Set<FkRole> listRole, boolean isLike) {
		if (roleCode.indexOf(",") > -1) {
			String[] depics = roleCode.split(",");
			for (int i = 0; i < depics.length; i++) {
				for (FkRole role : listRole) {
					if ((!isLike && depics[i].equals(role.getRoleCode()))
							|| (isLike && role.getRoleCode().indexOf(depics[i]) > -1)) {
						return true;
					}
				}
			}
			return false;
		} else {
			for (FkRole role : listRole) {
				if ((!isLike && roleCode.equals(role.getRoleCode()))
						|| (isLike && role.getRoleCode().indexOf(roleCode) > -1)) {
					return true;
				}
			}
			return false;
		}
	}

	
	public final static boolean isRoleByCode(String roleCode,
			Set<FkRole> listRole) {
		return isRoleByCode(roleCode, listRole, false);
	}

	public final static String[] getRoleDepic(Set<FkRole> roles) {
		String depicStr = "";
		int i = 0;
		for (FkRole role : roles) {
			if (role.getRoleDepic() != null
					&& !"".equals(role.getRoleDepic().trim())) {
				if (i == 0) {
					depicStr = role.getRoleDepic();
				} else {
					depicStr += "," + role.getRoleDepic();
				}
				i++;
			}
		}
		if ("".equals(depicStr)) {
			return null;
		}
		return depicStr.split(",");
	}

	public final static String getRoleDepicStr(Set<FkRole> roles) {
		String depicStr = "";
		int i = 0;
		for (FkRole role : roles) {
			if (role.getRoleDepic() != null
					&& !"".equals(role.getRoleDepic().trim())) {
				if (i == 0) {
					depicStr = role.getRoleDepic();
				} else {
					depicStr += "," + role.getRoleDepic();
				}
				i++;
			}
		}
		return depicStr;
	}

	public final static String getRoleCodeStr(Set<FkRole> roles) {
		String codeStr = "";
		int i = 0;
		for (FkRole role : roles) {
			if (role.getRoleCode() != null
					&& !"".equals(role.getRoleCode().trim())) {
				if (i == 0) {
					codeStr = role.getRoleCode();
				} else {
					codeStr += "," + role.getRoleCode();
				}
				i++;
			}
		}
		return codeStr;
	}
}
