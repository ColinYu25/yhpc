package com.safetys.nbyhpc.facade.iface;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaSecurityManage;

/**
 * @(#) SecurityManageFacadeIface.java
 * @date 2011-10-25
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */


public interface SecurityManageFacadeIface {
	
	/**
	 * update securityManage
	 * @param securityManage
	 * @throws ApplicationAccessException
	 */
	public void updateSecurityManage(DaSecurityManage securityManage)throws ApplicationAccessException;

	/**
	 * load SecurityManage by CompanyID
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaSecurityManage loadSecurityManageByCompanyId(Long companyId) throws ApplicationAccessException;
	
	/**
	 * update SecurityManage
	 * @param securityManage
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	//public String updateSecurityManage(DaSecurityManage securityManage, UserDetailWrapper userDetail)
		//	throws ApplicationAccessException;
    
	
}
