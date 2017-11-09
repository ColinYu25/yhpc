package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDept;


public interface DeptPersistenceIface {
	
	public Long createDept(DaDept dept) throws ApplicationAccessException;
	
	public void updateDept(DaDept dept) throws ApplicationAccessException;
	
	public void mergeDept(DaDept dept) throws ApplicationAccessException;
	
	public void deleteDept(DaDept dept) throws ApplicationAccessException;
	
	public List<DaDept> loadDeptes(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaDept loadDept(DaDept dept) throws ApplicationAccessException;
}