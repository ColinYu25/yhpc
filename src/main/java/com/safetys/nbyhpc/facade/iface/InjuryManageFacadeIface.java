package com.safetys.nbyhpc.facade.iface;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaInjuryManage;

/**
 * @(#) InjuryManageFacadeIface.java
 * @date 2011-10-26
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface InjuryManageFacadeIface {
	
	/**
	 * update InjuryManage
	 * @param injuryManage
	 * @throws ApplicationAccessException
	 */
	public void updateInjuryManage(DaInjuryManage injuryManage)throws ApplicationAccessException;

	/**
	 * load DaInjuryManage by CompanyID
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaInjuryManage loadInjuryManageByCompanyId(Long companyId) throws ApplicationAccessException;
	
	
}
