package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.VNormalDanger;
import com.safetys.nbyhpc.domain.persistence.iface.ETLNormalDangerPersistenceIface;

/**
 * @author llj
 * @create_time: 2014-5-7
 * @Description: 更新及获取本地同步号信息
 */
public class ETLNormalDangerPersistenceImpl implements ETLNormalDangerPersistenceIface {

	private PersistenceDao<VNormalDanger> persistenceDao;

	public List<VNormalDanger> getSwapList() {
		String hql = "from VNormalDanger com where com.isSynchro=1 "; //   where com.createTime>=to_date('2014/01/01','yyyy-MM-dd')  
		return persistenceDao.executeHQLQuery(hql);
	}

	public void updateSynStatus(List<Object> idList) {
		StringBuilder hqlStringBuilder = new StringBuilder();
		hqlStringBuilder.append("update DaNomalDanger com set com.isSynchro=0 where com.id in (");
		for (Object id : idList) {
			hqlStringBuilder.append(id + ",");
		}
		hqlStringBuilder.delete(hqlStringBuilder.length() - 1, hqlStringBuilder.length());
		hqlStringBuilder.append(")");
		
		System.out.println("hqlStringBuilder: "+hqlStringBuilder);
		persistenceDao.executeHQLUpdate(hqlStringBuilder.toString());
	}

	public PersistenceDao<VNormalDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<VNormalDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
