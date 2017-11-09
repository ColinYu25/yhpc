package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallDefer;
import com.safetys.nbyhpc.domain.model.DaRollcallDeferFile;

public interface RollcallDeferFacadeIface {
	public Long createRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException ;

	public DaRollcallDefer loadRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException ;

	public List<DaRollcallDefer> loadRollcallDefers(DaRollcallCompany rollcallCompany,Pagination pagination,UserDetailWrapper userDetail,int type) throws ApplicationAccessException ;

	public void updateRollcallDefer(DaRollcallDefer rollcallDefer)throws ApplicationAccessException ;

	public void deleteRollcallDefer(String id)throws ApplicationAccessException ;
	
	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException;
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	public DaRollcallCompany loadRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void updateRollcallDeferBySub(String id) throws ApplicationAccessException;
	
	public void updateRollcallDeferForPassByHql(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public String createRollcallDeferAgree(DaRollcallDefer rollcallDefer,DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException ;
	
	public String updateRollcallDeferAgree(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void updateRollcallDeferAgreeByHql(DaRollcallDefer rollcallDefer) throws ApplicationAccessException;
}
