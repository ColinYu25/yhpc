package com.safetys.nbyhpc.facade.iface;

import java.sql.SQLException;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaItem;

public interface ItemFacadeIface {
	
	public Long createItem(DaItem item)throws ApplicationAccessException;
	
	public void updateItem(DaItem item)throws ApplicationAccessException;
	
	public void deleteItem(String ids)throws ApplicationAccessException;
	
	public DaItem loadItem(DaItem item)throws ApplicationAccessException;

	public List<DaItem> loadItems(DaItem item,Pagination pagination,String yaer,String flag,UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public List<DaItem> loadGoverItems(DaItem item,Pagination pagination,String flag,UserDetailWrapper userDetail) throws ApplicationAccessException, SQLException;
	
	public List<DaItem> loadUnGoverItems(DaItem item,Pagination pagination,String flag,UserDetailWrapper userDetail) throws ApplicationAccessException, SQLException;
}
