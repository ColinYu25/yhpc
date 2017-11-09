package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.nbyhpc.domain.model.SyncFileWait;

public interface SyncFileWaitFacadeIface {
	
	public void add(String realFilePath,String filePath);
	
	public List<SyncFileWait> findAll();
	
	public void delete(SyncFileWait wait);
}
