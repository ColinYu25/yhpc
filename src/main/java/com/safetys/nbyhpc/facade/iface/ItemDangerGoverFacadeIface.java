package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.model.DaItemDangerGover;

public interface ItemDangerGoverFacadeIface {

	public Long createItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException;

	public void updateItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException;

	public void deleteItemDangerGover(String ids) throws ApplicationAccessException;

	public DaItemDangerGover loadItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException;
	
	public DaItemDangerGover loadItemDangerGover(Long itemDangerId)
			throws ApplicationAccessException;
	
	public boolean getItemDangerGover(Long itemDangerId)throws ApplicationAccessException;

	public List<DaItemDangerGover> loadItemDangerGovers(DaItemDangerGover itemDangerGover,
			Pagination pagination) throws ApplicationAccessException;
	
	public DaItemDanger loadDaItemDanger(DaItemDanger itemDanger)throws ApplicationAccessException;

}
