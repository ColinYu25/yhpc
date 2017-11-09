package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaBag;

public interface BagPersistenceIface {
	public Long createBag(DaBag daBag) throws ApplicationAccessException;

	public void updateBag(DaBag daBag) throws ApplicationAccessException;

	public void mergeBag(DaBag daBag) throws ApplicationAccessException;

	public void deleteBag(DaBag daBag) throws ApplicationAccessException;

	public List<DaBag> loadBags(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaBag loadBag(DaBag daBag) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
