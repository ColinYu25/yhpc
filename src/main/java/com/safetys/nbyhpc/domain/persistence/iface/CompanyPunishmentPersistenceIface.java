package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishment;


public interface CompanyPunishmentPersistenceIface {
	public void updateCompanyPunishment(String hql)
	            throws ApplicationAccessException;
	
	public List<DaCompanyPunishment> loadDaCompanyPunishment(String sql)
			throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException;
	
	public List<DaCompanyPunishment> loadDaCompanyPunishments(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException ;
   
	
	public DaCompanyPunishment loadCompanyPunishment(DaCompanyPunishment companyPunishment)throws ApplicationAccessException;
}
