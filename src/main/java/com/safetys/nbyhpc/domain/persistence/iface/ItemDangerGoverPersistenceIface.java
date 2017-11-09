package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemDangerGover;

public interface ItemDangerGoverPersistenceIface {
	
	public Long createItemDangerGover(DaItemDangerGover itemDangerGover) throws ApplicationAccessException;

	public void updateItemDangerGover(DaItemDangerGover itemDangerGover) throws ApplicationAccessException;

	public void mergeItemDangerGover(DaItemDangerGover itemDangerGover) throws ApplicationAccessException;

	public void deleteItemDangerGover(DaItemDangerGover itemDangerGover) throws ApplicationAccessException;

	public List<DaItemDangerGover> loadItemDangerGovers(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaItemDangerGover loadItemDangerGover(DaItemDangerGover itemDangerGover) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
