package com.safetys.nbyhpc.facade.iface;


public interface BaseFacadeIface<T> {

	public void create(T t);
	
	public void update(T t);
	
	public T findById(Long id);
	
}
