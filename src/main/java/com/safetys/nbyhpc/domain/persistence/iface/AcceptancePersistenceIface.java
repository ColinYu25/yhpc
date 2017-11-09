package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaAcceptance;

public interface AcceptancePersistenceIface {
	public Long createAcceptance(DaAcceptance acceptance) throws ApplicationAccessException;

	public void updateAcceptance(DaAcceptance acceptance) throws ApplicationAccessException;

	public void mergeAcceptance(DaAcceptance acceptance) throws ApplicationAccessException;

	public void deleteAcceptance(DaAcceptance acceptance) throws ApplicationAccessException;

	public List<DaAcceptance> loadAcceptances(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaAcceptance loadAcceptance(DaAcceptance acceptance) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
