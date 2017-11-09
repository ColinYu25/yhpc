package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDanger1;

public interface Danger1PersistenceIface {

	public Long createDanger(DaDanger1 danger) throws ApplicationAccessException;

	public void updateDanger(DaDanger1 danger) throws ApplicationAccessException;

	public void mergeDanger(DaDanger1 danger) throws ApplicationAccessException;

	public void deleteDanger(DaDanger1 danger) throws ApplicationAccessException;

	public List<DaDanger1> loadDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;

	public DaDanger1 loadDanger(DaDanger1 danger) throws ApplicationAccessException;

	public List<DaDanger1> findPageByHQL(String hql, Pagination pagination) throws ApplicationAccessException;

	public ResultSet findPageBySQL(final String sql) throws ApplicationAccessException;
}