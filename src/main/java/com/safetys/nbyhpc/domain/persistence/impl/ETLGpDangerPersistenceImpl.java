package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.VGpDanger;
import com.safetys.nbyhpc.domain.persistence.iface.ETLGpDangerPersistenceIface;

/**
 * @author llj
 * @create_time: 2014-5-7
 * @Description: 更新及获取本地同步号信息
 */
public class ETLGpDangerPersistenceImpl implements ETLGpDangerPersistenceIface {

	private PersistenceDao<VGpDanger> persistenceDao;

	public List<VGpDanger> getSwapList() {
		String hql = "from VGpDanger com where com.isSynchro=1 ";
		return persistenceDao.executeHQLQuery(hql);
	}

	public void updateSynStatus(List<Object> idList) {
		StringBuilder hqlStringBuilder = new StringBuilder();
		hqlStringBuilder.append("update DaRollcallCompany com set com.isSynchro=0 where com.daDanger.id in (");
		for (Object id : idList) {
			hqlStringBuilder.append(id + ",");
		}
		hqlStringBuilder.delete(hqlStringBuilder.length() - 1, hqlStringBuilder.length());
		hqlStringBuilder.append(")");

		System.out.println("hqlStringBuilder: " + hqlStringBuilder);
		persistenceDao.executeHQLUpdate(hqlStringBuilder.toString());
	}

	public PersistenceDao<VGpDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<VGpDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
