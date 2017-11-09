package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaInjuryManage;

public interface InjuryManagePersistenceIface {

	/**
	 * update DaInjuryManage
	 * @param daInjuryManage
	 * @throws ApplicationAccessException
	 */
	public void updateDaInjuryManage(DaInjuryManage daInjuryManage)
			throws ApplicationAccessException;

	/**
	 * load DaInjuryManage by CompanyId
	 * @param daInjuryManage
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaInjuryManage> loadDaInjuryManage(String hql,Object[] params)
			throws ApplicationAccessException;
	
	/**
	 * update DaInjuryManage through hql
	 * @param hql
	 * @throws ApplicationAccessException
	 */
	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

}