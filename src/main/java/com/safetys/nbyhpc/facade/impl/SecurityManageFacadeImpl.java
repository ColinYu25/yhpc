package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaSecurityManage;
import com.safetys.nbyhpc.domain.persistence.iface.SecurityManagePersistenceIface;
import com.safetys.nbyhpc.facade.iface.SecurityManageFacadeIface;

public class SecurityManageFacadeImpl implements SecurityManageFacadeIface {

	private SecurityManagePersistenceIface securityManagePersistenceIface;
	
	/**
	 * 通过CompanyId得到安管信息
	 */
	public DaSecurityManage loadSecurityManageByCompanyId(Long companyId)
			throws ApplicationAccessException {
		String hql = "from DaSecurityManage where daCompany.id = ? and deleted = 0";
		Object[] params = {companyId};
		List<DaSecurityManage> listDaSecurityManage = securityManagePersistenceIface.loadDaSecurityManage(hql, params);
		if(listDaSecurityManage.size() > 0){
			return listDaSecurityManage.get(0);
		}
		return null;
	}

	/**
	 * 更新安管信息
	 */
	public void updateSecurityManage(DaSecurityManage securityManage)
			throws ApplicationAccessException {
		securityManagePersistenceIface.updateDaSecurityManage(securityManage);
	}


	public SecurityManagePersistenceIface getSecurityManagePersistenceIface() {
		return securityManagePersistenceIface;
	}

	public void setSecurityManagePersistenceIface(
			SecurityManagePersistenceIface securityManagePersistenceIface) {
		this.securityManagePersistenceIface = securityManagePersistenceIface;
	}

}