package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
/**
 * @(#)			DangerGorverFacadeIface.java
 * @date		2009-08-17
 * @author		lvx
 * @copyright	(c) 2009 Safetys.cn
 * All rights reserved.
 *
 */

public interface PipeDangerGorverFacadeIface {
	
	/**
	 * 创建一个重大隐患整改信息
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 加载一个重大隐患整改信息
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPipeDangerGorver loadDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 修改一个重大隐患整改信息
	 * @param dangerGorver
	 * @throws ApplicationAccessException
	 */
	public void updateDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 删除重大隐患整改信息(可批量)
	 * @param dangerGorver
	 * @throws ApplicationAccessException
	 */
	public void deleteDangerGorvers(String ids) throws ApplicationAccessException;
	
	public void deleteDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 加载初始数据
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeDangerGorver> loadDangerGorversOfOne(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	/**
	 * 加载企业信息
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPipelineCompanyInfo loadCompany(DaPipelineCompanyInfo pipeLine) throws ApplicationAccessException;
	
	/**
	 * 加载隐患信息
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException;
}
