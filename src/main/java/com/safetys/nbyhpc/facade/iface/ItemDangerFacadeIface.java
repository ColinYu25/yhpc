package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;

public interface ItemDangerFacadeIface {

	public Long createItemDanger(DaItemDanger itemDanger)
			throws ApplicationAccessException;

	public void updateItemDanger(DaItemDanger itemDanger)
			throws ApplicationAccessException;

	public void deleteItemDanger(String ids) throws ApplicationAccessException;

	public DaItemDanger loadItemDanger(DaItemDanger itemDanger)
			throws ApplicationAccessException;

	public List<DaItemDanger> loadItemDangers(DaItemDanger itemDanger,
			Pagination pagination) throws ApplicationAccessException;
	
	public List<DaItemDanger> loadItemExpiredDangers(DaItemDanger itemDanger,
			Pagination pagination,String flag,UserDetailWrapper userDetail) throws ApplicationAccessException;

	public DaItem loadItem(DaItem item) throws ApplicationAccessException;
	
	public boolean getItemDangerGover(String ids) throws ApplicationAccessException;

	public List<DaIndustryParameter> loadTradeTypesForCompany(
			UserDetailWrapper userDetail) throws ApplicationAccessException;
}
