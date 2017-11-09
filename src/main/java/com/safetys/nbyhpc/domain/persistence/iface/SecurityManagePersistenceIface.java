package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaSecurityManage;

public interface SecurityManagePersistenceIface {

	/**
	 * update DaSecurityManage
	 * @param daSecurityManage
	 * @throws ApplicationAccessException
	 */
	public void updateDaSecurityManage(DaSecurityManage daSecurityManage)
			throws ApplicationAccessException;

	/**
	 * load DaSecurityManage by CompanyId
	 * @param daSecurityManage
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaSecurityManage> loadDaSecurityManage(String hql,Object[] params)
			throws ApplicationAccessException;
	
	/**
	 * update DaSecurityManage through hql
	 * @param hql
	 * @throws ApplicationAccessException
	 */
	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

}