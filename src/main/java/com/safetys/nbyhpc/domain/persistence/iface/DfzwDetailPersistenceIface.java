/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwReportDetail;

/**
 * 打非治违周报子表持久层
 * 
 * author   lih
 * @since	2012-5-10
 * @version	1.0.0
 */
public interface DfzwDetailPersistenceIface {

	public long create(ZjDfzwReportDetail entity) throws ApplicationAccessException;

	public void update(ZjDfzwReportDetail entity) throws ApplicationAccessException;

	public void delete(ZjDfzwReportDetail entity) throws ApplicationAccessException;

	public void deleteTrue(ZjDfzwReportDetail entity) throws ApplicationAccessException;

	public List<ZjDfzwReportDetail> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public ZjDfzwReportDetail load(long id) throws ApplicationAccessException;
}
