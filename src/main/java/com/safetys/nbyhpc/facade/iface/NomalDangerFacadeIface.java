package com.safetys.nbyhpc.facade.iface;

import java.io.File;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;

public interface NomalDangerFacadeIface {
	public List<DaNomalDanger> loadNomalDangers(DaCompany company, UserDetailWrapper userDetail, Pagination pagination, DaNomalDanger nomalDanger, int otherTradeDanger) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangers1(DaCompany company, UserDetailWrapper userDetail, Pagination pagination, DaNomalDanger nomalDanger, int otherTradeDanger) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangers(DaBag bag, UserDetailWrapper userDetail, Pagination pagination, DaNomalDanger nomalDanger, int otherTradeDanger) throws ApplicationAccessException;

	public DaNomalDanger loadNomalDanger(DaNomalDanger nomaldanger) throws ApplicationAccessException;

	public DaNomalDanger loadLinkManByBefore(DaCompany company) throws ApplicationAccessException;

	public void createNomalDanger(DaNomalDanger nomalDanger, DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void createNomalDanger(DaNomalDanger nomalDanger, DaBag bag, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void createMoreNomalDanger(DaNomalDanger nomalDanger, DaCompany company, UserDetailWrapper userDetail, List<DaNomalDanger> nomalDangers) throws ApplicationAccessException;

	public void createCountNomalDanger(DaNomalDanger nomalDanger, DaCompany company, UserDetailWrapper userDetail, int yhcount) throws ApplicationAccessException;

	public void updateNomalDanger(DaNomalDanger nomalDanger) throws ApplicationAccessException;

	public void deleteNormalDanger(String nomalDangerIds) throws ApplicationAccessException;
	
	public void deleteNormalDanger1(DaNomalDanger nomalDanger) throws ApplicationAccessException;

	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<DaCompany> loadCompanysByIsRepair(DaCompany company, DaNomalDanger nomalDanger, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException;

	public List<DaCompany> loadCompanysByIsRepairNew(DaCompany company, DaNomalDanger nomalDanger, UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException;

	/**
	 * 修改录入时间超过7天的未治理隐患治理状态为已治理
	 * 
	 * @param days
	 */
	public void updateNomalDangerRepaired(int days) throws ApplicationAccessException;
	
	public int loadnowYhCount(DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangers(DaCompany company, Pagination pagination, DaNomalDanger nomalDanger, UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public void saveNormalDanger(DaNomalDanger daNomalDanger, Long userId, DaCompany company, List<File> upFiles, String deletedFileIds) throws Exception;
}
