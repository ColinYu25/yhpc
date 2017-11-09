package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallDefer;


public interface RollcallDeferPersistenceIface {
	
	public Long createRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public void updateRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public void mergeRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public void deleteRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public List<DaRollcallDefer> loadRollcallDefers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaRollcallDefer loadRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
}