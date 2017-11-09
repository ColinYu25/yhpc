package com.safetys.nbyhpc.facade.iface;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.Statistic;

public interface StaticCacheFacadeIface { 

	public void loadCacheHis(String[] cname) throws ApplicationAccessException;
	
	public void loadCacheTradeTypes() throws ApplicationAccessException;

	public void loadDtCacheHis(String cacheName, FkArea area, Statistic statistic, int quarter) throws ApplicationAccessException;
	
	public void loadAxis2Client() throws ApplicationAccessException;
	
	public void sendData(int  type) throws ApplicationAccessException;
	
	public void reCreateCache(int type) throws ApplicationAccessException;
}
