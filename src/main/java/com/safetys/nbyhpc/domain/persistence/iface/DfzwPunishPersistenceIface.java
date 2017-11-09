/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;

/**
 * 打非治违处罚表持久层
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public interface DfzwPunishPersistenceIface {

	public long create(ZjDfzwPunish entity) throws ApplicationAccessException;

	public void update(ZjDfzwPunish entity) throws ApplicationAccessException;

	public void delete(ZjDfzwPunish entity) throws ApplicationAccessException;

	public List<ZjDfzwPunish> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public ZjDfzwPunish load(long id) throws ApplicationAccessException;

	public void merge(ZjDfzwPunish entity) throws ApplicationAccessException;
}
