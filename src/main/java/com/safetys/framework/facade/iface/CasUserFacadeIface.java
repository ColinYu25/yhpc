package com.safetys.framework.facade.iface;

import java.util.List;

import cn.safetys.cxf.ws.rest.CasUser;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;

public interface CasUserFacadeIface {
	
	public void createUser(FkUser fkUser,FkUserInfo fkUserInfo,List<FkRole> fkRoles,String casUserName);
	
	public FkUser loadUserByUserName(String userName);
	
	public FkUser loadUserByCasUserName(CasUser casUser);
	
	public FkUserInfo loadUseInfoByUserId(Long id);
	
	public void evict(FkUser fkUser);
	
	public void updateUser(FkUser fkUser,String casUserName);
	
}
