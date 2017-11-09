package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.TCoreArea;
import com.safetys.nbyhpc.domain.persistence.iface.PubAreaPersistenceIface;
import com.safetys.nbyhpc.facade.iface.PubAreaFacadeIface;

public class PubAreaFacadeImpl implements PubAreaFacadeIface {
	private PubAreaPersistenceIface pubAreaPersistenceIface;

	public List<TCoreArea> findChildAreas(Long areaCode)
			throws ApplicationAccessException {
		return pubAreaPersistenceIface
		.executeHQLQuery(
				"from TCoreArea obj where obj.pId=(select o.id from TCoreArea o where o.areaCode=?) and obj.deleted = false order by sortNum",
				new Object[] {areaCode});
	}

	public void setPubAreaPersistenceIface(
			PubAreaPersistenceIface pubAreaPersistenceIface) {
		this.pubAreaPersistenceIface = pubAreaPersistenceIface;
	}

}
