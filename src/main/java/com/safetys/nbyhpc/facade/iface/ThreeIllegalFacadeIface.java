package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaThreeIllegal;

/**
 * @(#) ThreeIllegalFacadeIface.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface ThreeIllegalFacadeIface {

	/**
	 * 创建一个打非信息
	 * 
	 * @param threeIllegal
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createThreeIllegal(DaThreeIllegal threeIllegal)
			throws ApplicationAccessException;

	/**
	 * 加载一个打非信息
	 * 
	 * @param threeIllegal
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaThreeIllegal loadThreeIllegal(DaThreeIllegal threeIllegal)
			throws ApplicationAccessException;

	/**
	 * 修改一个打非信息
	 * 
	 * @param threeIllegal
	 * @throws ApplicationAccessException
	 */
	public void updateThreeIllegal(DaThreeIllegal threeIllegal)
			throws ApplicationAccessException;

	/**
	 * 删除打非信息(可批量)
	 * 
	 * @param threeIllegal
	 * @throws ApplicationAccessException
	 */
	public void deleteThreeIllegals(String ids)
			throws ApplicationAccessException;

	public void deleteThreeIllegal(DaThreeIllegal threeIllegal)
			throws ApplicationAccessException;

	/**
	 * 加载打非信息，或分页或不分
	 * 
	 * @param threeIllegal
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaThreeIllegal> loadThreeIllegals(DaThreeIllegal threeIllegal,
			Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	/**
	 * 加载行业信息
	 * 
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaIndustryParameter> loadIndustryParameters(
			UserDetailWrapper userDetail) throws ApplicationAccessException;
}
