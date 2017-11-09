package com.safetys.nbyhpc.domain.persistence.iface;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.Department;

public interface DepartmentPersistenceIface { 
	public Department loadDepartment(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException;
}