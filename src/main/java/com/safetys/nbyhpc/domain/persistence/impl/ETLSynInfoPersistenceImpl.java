package com.safetys.nbyhpc.domain.persistence.impl;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.ETLSynInfo;
import com.safetys.nbyhpc.domain.persistence.iface.ETLSynInfoPersistenceIface;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public class ETLSynInfoPersistenceImpl implements ETLSynInfoPersistenceIface {
	private PersistenceDao<ETLSynInfo> persistenceDao;

	public ETLSynInfo getSynNo(String tabName) {
		String hql = "from ETLSynInfo where TabName='"+tabName+"'";
		return (ETLSynInfo) persistenceDao.executeHQLQuery(hql).get(0);
	}

	public void save(ETLSynInfo etlSynInfo) {
		System.out.println("保存同步号");
		persistenceDao.save(etlSynInfo);
	}
	
	
	public void update(ETLSynInfo etlSynInfo) {
		System.out.println("更新同步号");
		persistenceDao.update(etlSynInfo);
	}

	public PersistenceDao<ETLSynInfo> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<ETLSynInfo> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
