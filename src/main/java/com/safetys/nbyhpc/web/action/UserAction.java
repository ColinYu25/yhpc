package com.safetys.nbyhpc.web.action;

import com.safetys.framework.orm.hibernate3.PersistenceHibernateDao;

/**
 * 
 * @author yangb
 *
 */
public class UserAction extends com.safetys.framework.web.action.UserAction{

	private PersistenceHibernateDao persistenceDao;
	
	@Override
	public String createUser() {
		String success = super.createUser();
		//框架太强大，不知道怎么改，暂时没有事务，没有时间调整，先凑合用
		persistenceDao.executeSQLUpdate("UPDATE FK_USER SET CAS_USER_NAME='" + getFkUser().getUserName() + "' WHERE ID=" + getFkUser().getId());
		return success;
	}

	public void setPersistenceDao(PersistenceHibernateDao persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
