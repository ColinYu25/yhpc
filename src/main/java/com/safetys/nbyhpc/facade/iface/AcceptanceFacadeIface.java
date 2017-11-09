package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaAcceptance;

public interface AcceptanceFacadeIface {
	
	public String createAcceptance()throws ApplicationAccessException;

	public List loadAcceptanceList(Pagination pagination)throws ApplicationAccessException;
	
	public DaAcceptance loadAcceptance(DaAcceptance acceptance)throws ApplicationAccessException;
	
	public String updateAcceptance(DaAcceptance acceptance)throws ApplicationAccessException;
	
	public void deleteAcceptance(DaAcceptance acceptance)throws ApplicationAccessException;
}


