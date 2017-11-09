package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjSend;

public interface SendPersistenceIface { 

	public Long createSend(ZjSend send) throws ApplicationAccessException;

	public void uptSend(ZjSend send) throws ApplicationAccessException;

	public ZjSend loadSend(ZjSend send) throws ApplicationAccessException;

	public List<ZjSend> loadSends(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
}