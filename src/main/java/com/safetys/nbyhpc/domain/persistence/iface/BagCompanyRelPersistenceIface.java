package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaBagCompanyRel;

public interface BagCompanyRelPersistenceIface {
	
	public Long createBagCompanyRel(DaBagCompanyRel daBagCompanyRel) throws ApplicationAccessException;

	public void updateBagCompanyRel(DaBagCompanyRel daBagCompanyRel) throws ApplicationAccessException;

	public void mergeBagCompanyRel(DaBagCompanyRel daBagCompanyRel) throws ApplicationAccessException;

	public void deleteBagCompanyRel(DaBagCompanyRel daBagCompanyRel) throws ApplicationAccessException;

	public List<DaBagCompanyRel> loadBagCompanyRels(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaBagCompanyRel loadBagCompanyRel(DaBagCompanyRel daBagCompanyRel) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
