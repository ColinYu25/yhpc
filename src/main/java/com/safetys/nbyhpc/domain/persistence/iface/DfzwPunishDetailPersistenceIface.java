/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunishDetail;

/**
 * 打非治违处罚子表持久层
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public interface DfzwPunishDetailPersistenceIface {

	public long create(ZjDfzwPunishDetail entity) throws ApplicationAccessException;

	public void update(ZjDfzwPunishDetail entity) throws ApplicationAccessException;

	public void delete(ZjDfzwPunishDetail entity) throws ApplicationAccessException;

	public void deleteTrue(ZjDfzwPunishDetail entity) throws ApplicationAccessException;

	public List<ZjDfzwPunishDetail> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public ZjDfzwPunishDetail load(long id) throws ApplicationAccessException;
}
