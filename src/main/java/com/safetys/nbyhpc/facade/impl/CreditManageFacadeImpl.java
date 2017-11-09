package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCreditManage;
import com.safetys.nbyhpc.domain.persistence.iface.CreditManagePersistenceIface;
import com.safetys.nbyhpc.facade.iface.CreditManageFacadeIface;

public class CreditManageFacadeImpl implements CreditManageFacadeIface {

	private CreditManagePersistenceIface creditManagePersistenceIface;
	
	/**
	 * 通过CompanyId得到安管信息
	 */
	public DaCreditManage loadCreditManageByCompanyId(Long companyId)
			throws ApplicationAccessException {
		String hql = "from DaCreditManage where daCompany.id = ?";
		Object[] params = { companyId};
		List<DaCreditManage> listCreditManage = creditManagePersistenceIface.loadDaCreditManage(hql, params);
		if(listCreditManage.size() > 0){
			return listCreditManage.get(0);
		}
		return null;
	}

	public void setCreditManagePersistenceIface(
			CreditManagePersistenceIface creditManagePersistenceIface) {
		this.creditManagePersistenceIface = creditManagePersistenceIface;
	}

}