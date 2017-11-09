package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemDanger;

public interface ItemDangerPersistenceIface {
	
	public Long createItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException;

	public void updateItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException;

	public void mergeItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException;

	public void deleteItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException;

	public List<DaItemDanger> loadItemDangers(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaItemDanger loadItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
