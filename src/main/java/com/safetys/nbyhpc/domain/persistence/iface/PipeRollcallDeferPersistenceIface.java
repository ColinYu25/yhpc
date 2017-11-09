package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDefer;


public interface PipeRollcallDeferPersistenceIface {
	
	public Long createRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public void updateRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public void mergeRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public void deleteRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public List<DaPipeRollcallDefer> loadRollcallDefers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPipeRollcallDefer loadRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
}