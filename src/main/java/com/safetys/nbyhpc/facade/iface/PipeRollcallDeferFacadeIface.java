package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDefer;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDeferFile;

public interface PipeRollcallDeferFacadeIface {
	public Long createRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException ;

	public DaPipeRollcallDefer loadRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException ;

	public List<DaPipeRollcallDefer> loadRollcallDefers(DaPipeRollcallCompany rollcallCompany,Pagination pagination,UserDetailWrapper userDetail,int type) throws ApplicationAccessException ;

	public void updateRollcallDefer(DaPipeRollcallDefer rollcallDefer)throws ApplicationAccessException ;

	public void deleteRollcallDefer(String id)throws ApplicationAccessException ;
	
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public DaPipelineCompanyInfo loadCompany(DaPipelineCompanyInfo pipeLine) throws ApplicationAccessException;
	
	public DaPipeRollcallCompany loadRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void updateRollcallDeferBySub(String id) throws ApplicationAccessException;
	
	public void updateRollcallDeferForPassByHql(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
	
	public String createRollcallDeferAgree(DaPipeRollcallDefer rollcallDefer,DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException ;
	
	public String updateRollcallDeferAgree(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException;
	
	public void updateRollcallDeferAgreeByHql(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException;
}
