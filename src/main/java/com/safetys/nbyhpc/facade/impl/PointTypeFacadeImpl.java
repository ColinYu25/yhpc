package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaPointType;
import com.safetys.nbyhpc.domain.persistence.iface.PointTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.PointTypeFacadeIface;

public class PointTypeFacadeImpl implements PointTypeFacadeIface {

	private PointTypePersistenceIface pointTypePersistenceIface;


	public void createPointType(DaPointType pointType) throws ApplicationAccessException {
		pointTypePersistenceIface.createPointType(pointType);
	}

	public void deletePointType(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			pointTypePersistenceIface.deletePointType(new DaPointType(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public DaPointType loadPointType(DaPointType pointType) throws ApplicationAccessException {
		return pointTypePersistenceIface.loadPointType(pointType);
	}

	public List<DaPointType> loadPointTypes(DaPointType pointType,Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPointType.class, "dpt");
		detachedCriteriaProxy.createCriteria("daIndustryParameter", "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dpt.daIndustryParameter.id",pointType.getDaIndustryParameter().getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("dpt.sortNum"));
		return pointTypePersistenceIface.loadPointTypes(detachedCriteriaProxy, pagination);
	}

	public void updatePointType(DaPointType pointType) throws ApplicationAccessException {
		DaPointType oldPointType = loadPointType(pointType);
		pointType.setCreateTime(oldPointType.getCreateTime());
		pointTypePersistenceIface.mergePointType(pointType);
	}
	

	public void setPointTypePersistenceIface(
			PointTypePersistenceIface pointTypePersistenceIface) {
		this.pointTypePersistenceIface = pointTypePersistenceIface;
	}

}
