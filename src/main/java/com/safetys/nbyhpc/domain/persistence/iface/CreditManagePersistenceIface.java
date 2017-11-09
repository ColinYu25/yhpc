package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCreditManage;

public interface CreditManagePersistenceIface {

	/**
	 * load DaSecurityManage by CompanyId
	 * @param daSecurityManage
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCreditManage> loadDaCreditManage(String hql, Object[] params)
	throws ApplicationAccessException;
	
}