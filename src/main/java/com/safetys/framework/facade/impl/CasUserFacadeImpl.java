package com.safetys.framework.facade.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.safetys.cxf.ws.rest.CasUser;

import com.safetys.cas.web.action.RoleSetAction;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.persistence.iface.RolePersistenceIface;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.facade.iface.CasUserFacadeIface;
import com.safetys.framework.orm.hibernate3.PersistenceHibernateDao;

public class CasUserFacadeImpl implements CasUserFacadeIface {

	private UserPersistenceIface userPersistenceIface;

	private RolePersistenceIface rolePersistenceIface;

	private PersistenceHibernateDao persistenceDao;

	public void createUser(FkUser fkUser, FkUserInfo fkUserInfo, List<FkRole> fkRoles, String casUserName) {
		Set<FkRole> set = new HashSet<FkRole>(fkRoles);
		fkUser.setFkRoles(set);

		fkUserInfo.setFkUser(fkUser);
		fkUser.setFkUserInfo(fkUserInfo);
		userPersistenceIface.createUser(fkUser);

		persistenceDao.executeSQLUpdate("UPDATE FK_USER SET CAS_USER_NAME='" + casUserName + "' WHERE ID=" + fkUser.getId());

	}

	public FkUser loadUserByUserName(String userName) {
		List<FkUser> users = persistenceDao.executeHQLQuery("from FkUser where userName='" + userName + "'");
		if (users.size() > 0) {
			FkUser u = users.get(0);
			// 初始化
			return userPersistenceIface.loadUser(u.getId());
		}
		return null;
	}

	public FkUser loadUserByCasUserName(CasUser casUser) {
		List<BigDecimal> l = persistenceDao.executeSQLQuery("SELECT ID FROM FK_USER WHERE CAS_USER_NAME='" + casUser.getUsername() + "'");
		Long id = 0l;
		if (l.size() > 0) {
			id = l.get(0).longValue();
			return userPersistenceIface.loadUser(id);
		} else { // 新添
			List<BigDecimal> list1 = persistenceDao.executeSQLQuery("select max(id) from FK_USER t  ");
			id = list1.get(0).longValue() + 1;
//			System.out.println("id: " + id);
			persistenceDao.executeSQLUpdate("insert into fk_user(id, user_name, user_pass,is_deleted,create_time,modify_time,cas_user_name)values  (" + id + ", '" + casUser.getUsername() + "', '437fa19bde6316ccd9db4872d9fa93709a4759d8',0, to_date('2014-3-20','yyyy-MM-dd'),to_date('2014-3-20','yyyy-MM-dd'),'" + casUser.getUsername() + "')");
			persistenceDao.executeSQLUpdate("insert into fk_user_info (id,   first_area,   fact_name,   user_company, is_deleted,   create_time,   modify_time,   user_industry)values  (" + id + ", '330200000000', '" + casUser.getName() + "','" + casUser.getName() + "',0, to_date('2014-3-20','yyyy-MM-dd'),to_date('2014-3-20','yyyy-MM-dd'),'anjian')");

			RoleSetAction a = new RoleSetAction();
			Long[] roleId = a.settingRoleIds;
			if (roleId.length > 0) {
				for (int i = 0; i < roleId.length; i++) {
					persistenceDao.executeSQLUpdate("insert into FK_USER_ROLE_REL f  (user_id,   role_id)values  (" + id + ", " + roleId[i] + ")"); // 安监局查看权限
				}
			}
			return userPersistenceIface.loadUser(id);
		}
	}
	
    public void evict(FkUser fkUser) {
        persistenceDao.evict(fkUser);
    }

    public void updateUser(FkUser fkUser, String casUserName) {
        persistenceDao.executeSQLUpdate("UPDATE FK_USER SET CAS_USER_NAME='" + casUserName + "' WHERE ID=" + fkUser.getId());
    }


	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public void setRolePersistenceIface(RolePersistenceIface rolePersistenceIface) {
		this.rolePersistenceIface = rolePersistenceIface;
	}

	public void setPersistenceDao(PersistenceHibernateDao persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public FkUserInfo loadUseInfoByUserId(Long id) {
		return (FkUserInfo) persistenceDao.load(FkUserInfo.class, id);
	}

}
