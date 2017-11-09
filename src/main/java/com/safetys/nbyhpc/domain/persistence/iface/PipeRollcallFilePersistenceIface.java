package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallFile;

public interface PipeRollcallFilePersistenceIface {
	public Long createRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException;

	public void updateRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException;

	public void mergeRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException;

	public void deleteRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException;

	public List<DaPipeRollcallFile> loadRollcallFiles(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaPipeRollcallFile loadRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
