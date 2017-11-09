package com.safetys.nbyhpc.facade.iface;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCreditManage;

/**
 * @(#) CreditManageFacadeIface.java
 * @date 2011-10-27
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */


public interface CreditManageFacadeIface {
	
	/**
	 * load SecurityManage by CompanyID
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaCreditManage loadCreditManageByCompanyId(Long companyId) throws ApplicationAccessException;
	
}
