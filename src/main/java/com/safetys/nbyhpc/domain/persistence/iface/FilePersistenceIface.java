package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaFile;

public interface FilePersistenceIface {
	public Long createFile(DaFile file) throws ApplicationAccessException;

	public void updateFile(DaFile file) throws ApplicationAccessException;

	public void mergeFile(DaFile file) throws ApplicationAccessException;

	public void deleteFile(DaFile file) throws ApplicationAccessException;

	public List<DaFile> loadFiles(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaFile loadFile(DaFile file) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
