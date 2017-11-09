package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;

public interface ActualizeTableNoticeFacadeIface {
	
	public Long createActualizeProject(DaActualTableNotice daActualTableNotice)throws ApplicationAccessException;

	public List<DaActualTableNotice> loadActualizeProjectList(Pagination pagination,DaActualTableNotice daActualTableNotice,int type,int userId)throws ApplicationAccessException;
	
	public DaActualTableNotice loadActualizeProject(DaActualTableNotice daActualTableNotice)throws ApplicationAccessException;
	
	public void updateActualizeProject(DaActualTableNotice daActualTableNotice)throws ApplicationAccessException;
}


