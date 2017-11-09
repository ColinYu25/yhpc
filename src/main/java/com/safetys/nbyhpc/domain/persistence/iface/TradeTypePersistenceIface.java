package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;

public interface TradeTypePersistenceIface {

	public Long createTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;

	public void updateTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;

	public void mergeTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;

	public void deleteTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;

	public List<DaIndustryParameter> loadTradeTypes(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaIndustryParameter loadTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException;
	
	public DaIndustryParameter loadTradeType(String tradeDepiction)
		throws ApplicationAccessException;
	
	public DaIndustryParameter loadTradeType(String tradeDepiction,int type)
	throws ApplicationAccessException;
	
	public List<DaIndustryParameter> executeHQLQuery(String hql)throws ApplicationAccessException;
	public DaIndustryParameter loadTradeType(Long id)throws ApplicationAccessException;
}