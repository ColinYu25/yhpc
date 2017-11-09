package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaBagCompanyRel;

public interface BagCompanyRelFacadeIface {
	
	public Long createBagCompanyRel(DaBagCompanyRel bagCompanyRel) throws ApplicationAccessException;

	public void updateBagCompanyRel(DaBagCompanyRel bagCompanyRel) throws ApplicationAccessException;

	public void deleteBagCompanyRel(String ids) throws ApplicationAccessException;

	public DaBagCompanyRel loadBagCompanyRel(DaBagCompanyRel bagCompanyRel) throws ApplicationAccessException;

	public List<DaBagCompanyRel> loadBagCompanyRels(DaBagCompanyRel bagCompanyRel, Pagination pagination)
			throws ApplicationAccessException;
	
	public void createBagCompanyRel(String bagId,String companyPassIds,Long userId) throws ApplicationAccessException;
	
}
