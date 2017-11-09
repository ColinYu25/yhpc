package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.facade.iface.FkAreaFacadeIface;

/**
 * 
 * @author yangb
 *
 */
public class FkAreaFacadeImpl implements FkAreaFacadeIface {

	private AreaPersistenceIface areaPersistenceIface;
	
	@Override
	public List<FkArea> loadChildrens(Long areaCode) {
		if (areaCode == null) {
			return new ArrayList<FkArea>();
		}
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sortNum"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}

	@Override
	public FkArea findByCode(Long areaCode) {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fa.areaCode", areaCode));
		List<FkArea> list = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

}
