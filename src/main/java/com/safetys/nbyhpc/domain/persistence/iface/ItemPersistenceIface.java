package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItem;

public interface ItemPersistenceIface {
	public Long createItem(DaItem item)
			throws ApplicationAccessException;

	public void updateItem(DaItem item)
			throws ApplicationAccessException;

	public void mergeItem(DaItem item)
			throws ApplicationAccessException;

	public void deleteItem(DaItem item)
			throws ApplicationAccessException;

	public List<DaItem> loadItems(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaItem loadItem(DaItem item)
			throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
