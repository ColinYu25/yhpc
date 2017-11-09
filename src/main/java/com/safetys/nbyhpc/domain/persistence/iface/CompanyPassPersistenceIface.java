package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;

public interface CompanyPassPersistenceIface {

	public void deleteCompanyPass(DaCompanyPass companyPass)
			throws ApplicationAccessException;

	public List<DaCompanyPass> loadCompanyPasses(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;
	
	public void updateCompanyPass(DaCompanyPass companyPass)
		throws ApplicationAccessException;
}
