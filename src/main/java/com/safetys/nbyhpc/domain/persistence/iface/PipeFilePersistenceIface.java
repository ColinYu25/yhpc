package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeFile;

public interface PipeFilePersistenceIface {
	public Long createFile(DaPipeFile file) throws ApplicationAccessException;

	public void updateFile(DaPipeFile file) throws ApplicationAccessException;

	public void mergeFile(DaPipeFile file) throws ApplicationAccessException;

	public void deleteFile(DaPipeFile file) throws ApplicationAccessException;

	public List<DaPipeFile> loadFiles(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaPipeFile loadFile(DaPipeFile file) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
