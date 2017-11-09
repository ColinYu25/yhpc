package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;

/**
 * @(#) TradeTypeFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface TradeTypeFacadeIface {

	/**
	 * 创建一个行业信息
	 * 
	 * @param tradeType
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Long createTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;

	/**
	 * 加载一个行业信息
	 * 
	 * @param tradeType
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaIndustryParameter loadTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;
	/**
	 * 加载一个行业信息
	 * 
	 * @param tradeDepiction
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaIndustryParameter loadTradeType(String tradeDepiction)
			throws ApplicationAccessException;
	/**
	 * 修改一个行业信息
	 * 
	 * @param tradeType
	 * @throws ApplicationAccessException
	 */
	public void updateTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;

	/**
	 * 删除行业信息(可批量)
	 * 
	 * @param tradeType
	 * @throws ApplicationAccessException
	 */
	public void deleteTradeType(String ids) throws ApplicationAccessException;

	/**
	 * 加载行业信息，或分页或不分
	 * 
	 * @param tradeType
	 * @param wrapper
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaIndustryParameter> loadTradeTypes(
			DaIndustryParameter tradeType, UserDetailWrapper userDetail,
			Pagination pagination) throws ApplicationAccessException;
	/**
	 * 加载所有不同类型是行业
	 */
	
	public List<DaIndustryParameter> loadTradeTypes(Integer type) throws ApplicationAccessException;
	

}
