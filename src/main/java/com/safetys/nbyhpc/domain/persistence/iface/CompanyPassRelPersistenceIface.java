package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPassRel;

public interface CompanyPassRelPersistenceIface {
	public Long creatCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException;

	public DaCompanyPassRel loadCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException;

	public void updateCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException;

	public void deleteCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException;

	public List<DaCompanyPassRel> loadCompanyPassRels(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

}
