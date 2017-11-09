package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;

public interface BagFacadeIface {

	public Long createBag(DaBag bag) throws ApplicationAccessException;

	public void updateBag(DaBag bag) throws ApplicationAccessException;

	public void deleteBag(String ids) throws ApplicationAccessException;

	public DaBag loadBag(DaBag bag) throws ApplicationAccessException;

	public List<DaBag> loadBags(DaBag bag, Pagination pagination,UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public List<DaCompany> loadUnBagCompanies(DaCompany company,
			Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<DaBag> loadBagsByBagType(String tp,UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public List<DaBag> loadAlreadyBags(DaBag bag, Pagination pagination,UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public List<DaCompany> loadBagCompanies(DaCompany company,
			Pagination pagination, String bagId)
			throws ApplicationAccessException;

	public boolean isAllowBag(String ids) throws ApplicationAccessException;
}
