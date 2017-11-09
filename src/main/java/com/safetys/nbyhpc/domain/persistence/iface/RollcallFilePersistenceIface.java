package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallFile;

public interface RollcallFilePersistenceIface {
	public Long createRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException;

	public void updateRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException;

	public void mergeRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException;

	public void deleteRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException;

	public List<DaRollcallFile> loadRollcallFiles(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaRollcallFile loadRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
