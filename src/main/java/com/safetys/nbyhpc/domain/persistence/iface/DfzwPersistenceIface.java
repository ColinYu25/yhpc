/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;

/**
 * 打非治违主表持久层
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public interface DfzwPersistenceIface {

	public long create(ZjDfzwReport entity) throws ApplicationAccessException;

	public void update(ZjDfzwReport entity) throws ApplicationAccessException;

	public void delete(ZjDfzwReport entity) throws ApplicationAccessException;

	public List<ZjDfzwReport> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public ZjDfzwReport load(long id) throws ApplicationAccessException;

	public void merge(ZjDfzwReport entity) throws ApplicationAccessException;
}
