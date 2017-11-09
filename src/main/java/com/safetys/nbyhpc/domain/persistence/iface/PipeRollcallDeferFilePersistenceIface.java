package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDeferFile;


public interface PipeRollcallDeferFilePersistenceIface {
	
	public Long createRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void updateRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void mergeRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void deleteRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public List<DaPipeRollcallDeferFile> loadRollcallDeferFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPipeRollcallDeferFile loadRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
}