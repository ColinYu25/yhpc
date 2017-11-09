package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompanyPassRel;

public interface CompanyPassRelFacadeIface {
	public List<DaCompanyPassRel> loadCompanyPassRels()throws ApplicationAccessException;
}
