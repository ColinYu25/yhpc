package com.safetys.nbyhpc.facade.iface.android;

import java.io.File;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.android.ClientChangeLog;
import com.safetys.nbyhpc.facade.iface.BaseFacadeIface;

public interface ClientChangeLogFacadeIface extends BaseFacadeIface<ClientChangeLog> {

	public List<ClientChangeLog> loadClientChangeLogs(ClientChangeLog entity, Pagination page) ;
	
	public ClientChangeLog loadLastedClientChangeLog(String os, String type);
	
	public void create(ClientChangeLog entity, File file, String fileFileName);
	
	public long loadMaxVersionNum(String type);
	
}
