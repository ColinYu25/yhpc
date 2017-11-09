package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;
import com.safetys.nbyhpc.domain.persistence.iface.ActualizeTableNoticePersistenceIface;
import com.safetys.nbyhpc.facade.iface.ActualizeTableNoticeFacadeIface;

public class ActualizeTableNoticeFacadeImpl implements ActualizeTableNoticeFacadeIface {

	public List<DaActualTableNotice> loadActualizeProjectList(Pagination pagination, DaActualTableNotice daActualTableNotice, int type, int userId) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaActualTableNotice.class, "datn");
		if (daActualTableNotice != null) {
			if (daActualTableNotice.getTitle() != null && !"".equals(daActualTableNotice.getTitle().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("datn.title", daActualTableNotice.getTitle().trim(), MatchMode.ANYWHERE));
			}
			if (daActualTableNotice.getCreateTimeBegin() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("datn.createTimeBegin", daActualTableNotice.getCreateTimeBegin()));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("datn.type", type));

		if (userId != 0) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("datn.userId", Long.parseLong(userId + "")));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("datn.createTimeBegin"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("datn.id"));
		return actualizeTableNoticePersistenceIface.loadDaActualTableNotices(detachedCriteriaProxy, pagination);
	}

	private ActualizeTableNoticePersistenceIface actualizeTableNoticePersistenceIface;

	public Long createActualizeProject(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {

		return actualizeTableNoticePersistenceIface.createDaActualTableNotice(daActualTableNotice);
	}

	public void setActualTableNoticePersistenceIface(ActualizeTableNoticePersistenceIface actualizeTableNoticePersistenceIface) {
		this.actualizeTableNoticePersistenceIface = actualizeTableNoticePersistenceIface;
	}

	public void setActualizeTableNoticePersistenceIface(ActualizeTableNoticePersistenceIface actualizeTableNoticePersistenceIface) {
		this.actualizeTableNoticePersistenceIface = actualizeTableNoticePersistenceIface;
	}

	public DaActualTableNotice loadActualizeProject(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		return actualizeTableNoticePersistenceIface.loadDaActualTableNotice(daActualTableNotice);
	}

	public void updateActualizeProject(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		actualizeTableNoticePersistenceIface.updateDaActualTableNotice(daActualTableNotice);
	}

}
