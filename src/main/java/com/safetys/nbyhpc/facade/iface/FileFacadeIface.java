package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaFile;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallFile;

public interface FileFacadeIface {
	
	public String createFile(DaFile file,DaRollcallFile rollcallFile,DaRollcallCompany rollcallCompany)throws ApplicationAccessException;

	public List<DaRollcallFile> loadFileList(Pagination pagination,DaFile file,DaRollcallCompany rollcallCompany,int type)throws ApplicationAccessException;
	
	public DaFile loadFile(DaFile file)throws ApplicationAccessException;
	
	public String updateFile(DaFile file)throws ApplicationAccessException;
	
	public void deleteFile(DaFile file)throws ApplicationAccessException;
}


