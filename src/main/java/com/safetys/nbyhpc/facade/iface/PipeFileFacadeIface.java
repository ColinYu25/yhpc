package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaPipeFile;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallFile;

public interface PipeFileFacadeIface {
	
	public String createFile(DaPipeFile file,DaPipeRollcallFile rollcallFile,DaPipeRollcallCompany rollcallCompany)throws ApplicationAccessException;

	public List<DaPipeRollcallFile> loadFileList(Pagination pagination,DaPipeFile file,DaPipeRollcallCompany rollcallCompany,int type)throws ApplicationAccessException;
	
	public DaPipeFile loadFile(DaPipeFile file)throws ApplicationAccessException;
	
	public String updateFile(DaPipeFile file)throws ApplicationAccessException;
	
	public void deleteFile(DaPipeFile file)throws ApplicationAccessException;
}


