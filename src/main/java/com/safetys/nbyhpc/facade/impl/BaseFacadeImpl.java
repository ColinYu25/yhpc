package com.safetys.nbyhpc.facade.impl;

import com.safetys.nbyhpc.domain.persistence.iface.DaBasePersistenceIface;
import com.safetys.nbyhpc.facade.iface.BaseFacadeIface;

public abstract class BaseFacadeImpl<T> implements BaseFacadeIface<T> {

	@Override
	public void create(T t) {
		getService().create(t);
	}

	@Override
	public void update(T t) {
		getService().update(t);
	}
	
	@Override
	public T findById(Long id) {
		return getService().findById(id);
	}

	public abstract DaBasePersistenceIface<T> getService();

}
