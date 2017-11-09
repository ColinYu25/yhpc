package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaPointType;
/**
 * @(#)			PointTypeFacadeIface.java
 * @date		2008-12-23
 * @author		lihu
 * @copyright	(c) 2008 Safetys.cn
 * All rights reserved.
 *
 */

public interface PointTypeFacadeIface {
	
	/**
	 * 创建一个企业基本信息
	 * @param pointType
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createPointType(DaPointType pointType) throws ApplicationAccessException;
	
	/**
	 * 加载一个企业基本信息
	 * @param pointType
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPointType loadPointType(DaPointType pointType) throws ApplicationAccessException;
	
	/**
	 * 修改一个企业基本信息
	 * @param pointType
	 * @throws ApplicationAccessException
	 */
	public void updatePointType(DaPointType pointType) throws ApplicationAccessException;
	
	/**
	 * 删除企业基本信息(可批量)
	 * @param pointType
	 * @throws ApplicationAccessException
	 */
	public void deletePointType(String ids) throws ApplicationAccessException;
	
	/**
	 * 加载企业基本信息，或分页或不分
	 * @param pointType
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPointType> loadPointTypes(DaPointType pointType,Pagination pagination) throws ApplicationAccessException;
	
}
