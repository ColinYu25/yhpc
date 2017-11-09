package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemSeasonReport;

public interface ItemSeasonReportFacadeIface {
	public Long createItemSeasonReport(DaItemSeasonReport itemSeasonReport)throws ApplicationAccessException;
	
	public void updateItemSeasonReport(DaItemSeasonReport itemSeasonReport)throws ApplicationAccessException;
	
	public void deleteItemSeasonReport(String ids)throws ApplicationAccessException;
	
	public DaItemSeasonReport loadItemSeasonReport(DaItemSeasonReport itemSeasonReport)throws ApplicationAccessException;

	public List<DaItemSeasonReport> loadItemSeasonReports(DaItemSeasonReport itemSeasonReport,Pagination pagination)throws ApplicationAccessException;
	
	public DaItem loadItem(DaItem item) throws ApplicationAccessException;
}
