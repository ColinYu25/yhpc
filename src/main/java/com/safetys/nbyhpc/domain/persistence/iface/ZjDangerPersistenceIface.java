package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDanger;


public interface ZjDangerPersistenceIface {
	
	public Long createZjDanger(ZjDanger danger) throws ApplicationAccessException ;

	public ZjDanger loadZjDanger(ZjDanger danger) throws ApplicationAccessException ;

	public List<ZjDanger> loadZjDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException ;

	public void updateZjDanger(ZjDanger danger) throws ApplicationAccessException ;
	
	public void mergeZjDanger(ZjDanger danger) throws ApplicationAccessException;
}