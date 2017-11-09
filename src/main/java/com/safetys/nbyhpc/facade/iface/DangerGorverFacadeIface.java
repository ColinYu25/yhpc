package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.Department;
/**
 * @(#)			DangerGorverFacadeIface.java
 * @date		2009-08-17
 * @author		lvx
 * @copyright	(c) 2009 Safetys.cn
 * All rights reserved.
 *
 */

public interface DangerGorverFacadeIface {
	
	/**
	 * 创建一个重大隐患整改信息
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 加载一个重大隐患整改信息
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaDangerGorver loadDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 修改一个重大隐患整改信息
	 * @param dangerGorver
	 * @throws ApplicationAccessException
	 */
	public void updateDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 删除重大隐患整改信息(可批量)
	 * @param dangerGorver
	 * @throws ApplicationAccessException
	 */
	public void deleteDangerGorvers(String ids) throws ApplicationAccessException;
	
	public void deleteDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 加载初始数据
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaDangerGorver> loadDangerGorversOfOne(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 加载企业信息
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	/**
	 * 加载隐患信息
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException;
	
	/**
	 * 获得行业县级部门全称生成对应的主管单位
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Department getCountyLevel(FkUserInfo fkUserInfo) throws ApplicationAccessException;
}
