package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaThreeIllegal;


public interface ThreeIllegalPersistenceIface {
	
	public Long createThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException;
	
	public void updateThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException;
	
	public void mergeThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException;
	
	public void deleteThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException;
	
	public List<DaThreeIllegal> loadThreeIllegals(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaThreeIllegal loadThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException;
}