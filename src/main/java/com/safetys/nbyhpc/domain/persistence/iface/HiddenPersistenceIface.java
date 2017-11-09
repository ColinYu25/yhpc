package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.SynErrorIds;
import com.safetys.nbyhpc.domain.model.SyncNo;
import com.safetys.nbyhpc.domain.model.VDangerEx;
import com.safetys.nbyhpc.domain.model.VNormalDangerEx;

public interface HiddenPersistenceIface {
	List<VDangerEx> getVDangerExList(
			DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException;

	List<VNormalDangerEx> getVNormalDangerExList(
			DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException;

	SyncNo getSyncNo(DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException;

	void saveOrUpdateSyncNo(SyncNo SyncNo) throws ApplicationAccessException;

	int executeSQLUpdate(final String sql);

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public void saveOrUpdateSynErrorIds(SynErrorIds synErrorIds)
			throws ApplicationAccessException;

	public List<SynErrorIds> getSynErrorIdsList(
			DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException;
}