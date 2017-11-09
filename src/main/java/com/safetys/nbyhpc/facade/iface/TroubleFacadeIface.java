package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaTrouble;
import com.safetys.nbyhpc.domain.model.DaTroubleCompany;
import com.safetys.nbyhpc.domain.model.DaTroubleFile;
import com.safetys.nbyhpc.domain.model.Statistic;
/**
 * @(#)			DangerGorverFacadeIface.java
 * @date		2009-08-17
 * @author		lvx
 * @copyright	(c) 2009 Safetys.cn
 * All rights reserved.
 *
 */

public interface TroubleFacadeIface {
	
	public List<DaCompany> loadCompanies (DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	public String createTroubleDept (DaTroubleCompany troubleCompany,DaTrouble trouble,DaDept dept,List<DaTroubleFile> troubleFiles)throws ApplicationAccessException ;
	
	public void updateTroubleDept(DaTrouble trouble,DaDept dept,List<DaTroubleFile> troubleFiles)throws ApplicationAccessException;
	
	public void deleteTroubles(String ids) throws ApplicationAccessException;
	
	public void deleteTrouble(DaTrouble trouble) throws ApplicationAccessException;
	
	public DaTrouble loadTrouble(DaTrouble trouble) throws ApplicationAccessException;
	
	public FkArea loadArea(Long areaCode) throws ApplicationAccessException;
	
	public List<FkArea> loadSecondAreas() throws ApplicationAccessException;
	
	public List<DaIndustryParameter> loadTradeTypesForCompany() throws ApplicationAccessException;
	
	public List<FkEnum> loadEnums(UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<FkEnum> loadAllEnums(UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public DaDept loadDept(DaDept dept) throws ApplicationAccessException;
	
	public DaDept loadDeptById(DaDept dept) throws ApplicationAccessException;
	
	public List<DaDept> loadDeptes(DaDept dept,Pagination pagination) throws ApplicationAccessException;
	
	public FkEnum loadEnum(UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<DaDept> loadTroubleDown (DaDept dept,DaTrouble trouble,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<DaDept> loadTroubleUp (DaDept dept,DaTrouble trouble,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<DaDept> loadTroubleDownOrUp (DaDept dept,DaTrouble trouble,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public String loadStatisticOfDownXmlByUser(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public String loadStatisticOfUpXmlByUser(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public Statistic loadStatisticOfDownByUser(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public Statistic loadStatisticOfUpByUser(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public void createTroubleByDownOrUpOrBackOrResult (DaTrouble trouble,DaDept dept)throws ApplicationAccessException;
	
	public List<DaTroubleFile> loadTroubleFilesByTrouble(DaTrouble trouble,Pagination pagination) throws ApplicationAccessException;
}
