package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.model.DaItemDangerGover;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerGoverPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerPersistenceIface;
import com.safetys.nbyhpc.facade.iface.ItemDangerGoverFacadeIface;

public class ItemDangerGoverFacadeImpl implements ItemDangerGoverFacadeIface {

	private ItemDangerGoverPersistenceIface itemDangerGoverPersistenceIface;

	private ItemDangerPersistenceIface itemDangerPersistenceIface;
	
	public DaItemDangerGover loadItemDangerGover(Long itemDangerId) throws ApplicationAccessException {
		ResultSet rs=null;
		DaItemDangerGover itemDangerGover=new DaItemDangerGover();
		String sql="select id from da_item_danger_gover where PAR_DA_IT_ID="+itemDangerId+" and IS_DELETED=0";
		rs=itemDangerGoverPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				itemDangerGover.setId(rs.getLong("id"));
			}
			itemDangerGover=itemDangerGoverPersistenceIface.loadItemDangerGover(itemDangerGover);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemDangerGover;
	}
	
	public boolean getItemDangerGover(Long itemDangerId)throws ApplicationAccessException{
		ResultSet rs=null;
		String sql="select id from da_item_danger_gover where PAR_DA_IT_ID="+itemDangerId+" and IS_DELETED=0";
		rs=itemDangerGoverPersistenceIface.findBySql(sql);
		Long id=null;
		boolean flag=true;
		try {
			while(rs.next()){
				id=rs.getLong("id");
			}
			if (id==null){
				flag=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public DaItemDanger loadDaItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException {
		return itemDangerPersistenceIface.loadItemDanger(itemDanger);
	}
	
	public Long createItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		DaItemDanger itemDanger= itemDangerPersistenceIface.loadItemDanger(new DaItemDanger(itemDangerGover.getDaItemDanger().getId()));
		itemDanger.setGovern(new Integer("1"));
		itemDangerPersistenceIface.updateItemDanger(itemDanger);
		return itemDangerGoverPersistenceIface
				.createItemDangerGover(itemDangerGover);
	}

	public void deleteItemDangerGover(String ids)
			throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			DaItemDangerGover itemDangerGover=itemDangerGoverPersistenceIface.loadItemDangerGover(new DaItemDangerGover(Long
					.parseLong(ids.split(",")[i].trim()))); 
			DaItemDanger itemDanger= itemDangerPersistenceIface.loadItemDanger(new DaItemDanger(itemDangerGover.getDaItemDanger().getId()));
			itemDanger.setGovern(new Integer("0"));
			itemDangerPersistenceIface.updateItemDanger(itemDanger);
			itemDangerGoverPersistenceIface
					.deleteItemDangerGover(new DaItemDangerGover(Long
							.parseLong(ids.split(",")[i].trim())));
			
		}
	}

	public DaItemDangerGover loadItemDangerGover(
			DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		return itemDangerGoverPersistenceIface.loadItemDangerGover(itemDangerGover);
	}
	
	public List<DaItemDangerGover> loadItemDangerGovers(
			DaItemDangerGover itemDangerGover, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaItemDangerGover.class, "dit");
		detachedCriteriaProxy.createAlias("dit.daDanger", "ditd");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return itemDangerGoverPersistenceIface.loadItemDangerGovers(
				detachedCriteriaProxy, pagination);
	}

	public void updateItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		itemDangerGoverPersistenceIface.updateItemDangerGover(itemDangerGover);
	}

	public ItemDangerGoverPersistenceIface getItemDangerGoverPersistenceIface() {
		return itemDangerGoverPersistenceIface;
	}

	public void setItemDangerGoverPersistenceIface(
			ItemDangerGoverPersistenceIface itemDangerGoverPersistenceIface) {
		this.itemDangerGoverPersistenceIface = itemDangerGoverPersistenceIface;
	}

	public ItemDangerPersistenceIface getItemDangerPersistenceIface() {
		return itemDangerPersistenceIface;
	}

	public void setItemDangerPersistenceIface(
			ItemDangerPersistenceIface itemDangerPersistenceIface) {
		this.itemDangerPersistenceIface = itemDangerPersistenceIface;
	}

}
