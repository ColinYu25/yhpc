package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.ETLAqsc;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.domain.persistence.iface.ETLAqscInfoPersistenceIface;

/**
 * @author llj
 * @create_time: 2014-5-4
 * @Description:更新及获取本地同步号信息
 */
public class ETLAqscInfoPersistenceImpl implements ETLAqscInfoPersistenceIface {

	private PersistenceDao<TCompany> persistenceDao;

	public List<TCompany> getSwapList() {
		String hql = "from TCompany com where com.isSynchro=1";
		return persistenceDao.executeHQLQuery(hql);
	}

	public void updateSynStatus(List<Object> idList) {
		StringBuilder hqlStringBuilder = new StringBuilder();
		hqlStringBuilder.append("update TCompany com set com.isSynchro=0 where com.id in (");
		for (Object id : idList) {
			hqlStringBuilder.append(id + ",");
		}
		hqlStringBuilder.delete(hqlStringBuilder.length() - 1, hqlStringBuilder.length());
		hqlStringBuilder.append(")");
		persistenceDao.executeHQLUpdate(hqlStringBuilder.toString());
	}

	public void save(ETLAqsc entity) throws Exception {
		if (entity.getCOMPANY_UUID() != null) {
			String hql = "from TCompany com where com.uuid ='" + entity.getCOMPANY_UUID() + "'";
			System.out.println("安全生产信息同步，uuid相同 更新: " + hql);
			List<TCompany> tcompany = persistenceDao.executeHQLQuery(hql);

			if (tcompany.size() > 0) { // uuid相同,则更新 (中心库高优先级字段,其他字段不更新)
				TCompany tc = tcompany.get(0);
				tc.setSecurityPrincipalPerson(entity.getSECURITY_PRINCIPAL_PERSON());
				tc.setSecurityPrincipalTel(entity.getSECURITY_PRINCIPAL_TELEPHONE());
				persistenceDao.update(tc);

			} else { // "uuid为空 名称相同 更新uuid (中心库高优先级字段,其他字段不更新)
				hql = "from TCompany com where com.uuid is null  and com.isHidden=1 and  com.companyName='" + entity.getCOMPANY_NAME() + "'";
				System.out.println("安全生产信息同步，uuid为空  名称相同   更新: " + hql);
				List<TCompany> tcompany1 = persistenceDao.executeHQLQuery(hql);
				if (tcompany1.size() > 0) {
					TCompany tc = tcompany1.get(0);
					tc.setUuid(entity.getCOMPANY_UUID());
					tc.setSecurityPrincipalPerson(entity.getSECURITY_PRINCIPAL_PERSON());
					tc.setSecurityPrincipalTel(entity.getSECURITY_PRINCIPAL_TELEPHONE());
					persistenceDao.update(tc);
				}
			}
		}
	}

	public PersistenceDao<TCompany> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<TCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
