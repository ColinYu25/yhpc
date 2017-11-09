package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaInjuryManage;
import com.safetys.nbyhpc.domain.persistence.iface.InjuryManagePersistenceIface;
import com.safetys.nbyhpc.facade.iface.InjuryManageFacadeIface;

public class InjuryManageFacadeImpl implements InjuryManageFacadeIface {

	private InjuryManagePersistenceIface injuryManagePersistenceIface;
	
	/**
	 * 通过CompanyId得到安管信息
	 */
	public DaInjuryManage loadInjuryManageByCompanyId(Long companyId)
			throws ApplicationAccessException {
		String hql = "from DaInjuryManage where daCompany.id = ? and deleted = 0";
		Object[] params = {companyId};
		List<DaInjuryManage> listDaInjuryManage = injuryManagePersistenceIface.loadDaInjuryManage(hql, params);
		if(listDaInjuryManage.size() > 0){
			return listDaInjuryManage.get(0);
		}
		return null;
	}

	/**
	 * 更新安管信息
	 */
	public void updateInjuryManage(DaInjuryManage injuryManage)
			throws ApplicationAccessException {
		injuryManagePersistenceIface.updateDaInjuryManage(injuryManage);
	}

	public InjuryManagePersistenceIface getInjuryManagePersistenceIface() {
		return injuryManagePersistenceIface;
	}

	public void setInjuryManagePersistenceIface(
			InjuryManagePersistenceIface injuryManagePersistenceIface) {
		this.injuryManagePersistenceIface = injuryManagePersistenceIface;
	}


}