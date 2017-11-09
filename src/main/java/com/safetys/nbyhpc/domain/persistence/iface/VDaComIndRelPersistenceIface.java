package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.VDaCompanyIndustryRel;

public interface VDaComIndRelPersistenceIface {

	public List<VDaCompanyIndustryRel> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;

	public List<VDaCompanyIndustryRel> getHy(long comId);

	public List<VDaCompanyIndustryRel> getHy(long comId, long parDaIndId);

}