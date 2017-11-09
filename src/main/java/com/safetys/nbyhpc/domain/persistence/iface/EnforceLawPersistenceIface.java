package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaZhifaReport;

public interface EnforceLawPersistenceIface {

	public Long createEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;

	public List<DaZhifaReport> loadEnforceLaws(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaZhifaReport loadEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;

	public void updateEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql)throws ApplicationAccessException;
}