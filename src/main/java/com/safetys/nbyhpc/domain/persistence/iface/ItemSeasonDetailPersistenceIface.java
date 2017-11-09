package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemSeasonDetail;

public interface ItemSeasonDetailPersistenceIface {
	
	public Long createItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException;

	public void updateItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException;

	public void mergeItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException;

	public void deleteItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException;

	public List<DaItemSeasonDetail> loadItemSeasonDetails(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaItemSeasonDetail loadItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
