package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaAcceptance;
import com.safetys.nbyhpc.domain.persistence.iface.AcceptancePersistenceIface;
import com.safetys.nbyhpc.facade.iface.AcceptanceFacadeIface;

public class AcceptanceFacadeImpl implements AcceptanceFacadeIface{

	private AcceptancePersistenceIface acceptancePersistenceIface;
	
	public String createAcceptance() throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public DaAcceptance loadAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List loadAcceptanceList(Pagination pagination) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public AcceptancePersistenceIface getAcceptancePersistenceIface() {
		return acceptancePersistenceIface;
	}

	public void setAcceptancePersistenceIface(
			AcceptancePersistenceIface acceptancePersistenceIface) {
		this.acceptancePersistenceIface = acceptancePersistenceIface;
	}

}
