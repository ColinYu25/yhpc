package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallDeferFile;


public interface RollcallDeferFilePersistenceIface {
	
	public Long createRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void updateRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void mergeRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void deleteRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public List<DaRollcallDeferFile> loadRollcallDeferFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaRollcallDeferFile loadRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
}