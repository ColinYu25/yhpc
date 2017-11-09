package com.safetys.nbyhpc.util;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.PersistenceHibernateDao;

public class HibernateCacheClear {
	
	private HibernateCacheClear(){}
	private static HibernateCacheClear hibernateCacheClear;
	private static synchronized void makeInstance(){
		
		hibernateCacheClear=new HibernateCacheClear();
	}
	public static HibernateCacheClear getInstance(){
		if(hibernateCacheClear==null)
			makeInstance();
		return hibernateCacheClear;
	}
	public void clearCache(){
		PersistenceDao<Object> persistenceDao= new PersistenceHibernateDao<Object>();
		persistenceDao.clearCache();
	}
}
