package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemSeasonDetail;
import com.safetys.nbyhpc.domain.model.DaItemSeasonReport;
import com.safetys.nbyhpc.domain.persistence.iface.ItemPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemSeasonDetailPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemSeasonReportPersistenceIface;
import com.safetys.nbyhpc.facade.iface.ItemSeasonReportFacadeIface;

public class ItemSeasonReportFacadeImpl implements ItemSeasonReportFacadeIface {

	private ItemSeasonReportPersistenceIface itemSeasonReportPersistenceIface;

	private ItemPersistenceIface itemPersistenceIface;

	private ItemSeasonDetailPersistenceIface itemSeasonDetailPersistenceIface;

	public DaItem loadItem(DaItem item) throws ApplicationAccessException {
		return itemPersistenceIface.loadItem(item);
	}

	public Long createItemSeasonReport(DaItemSeasonReport itemSeasonReport)
			throws ApplicationAccessException {
		Long id = itemSeasonReportPersistenceIface
					.createItemSeasonReport(itemSeasonReport);
			itemSeasonReport.setId(id);
				for(DaItemSeasonDetail detail : itemSeasonReport.getItemSeasonDetails()) {
					detail.setDaItemSeasonReport(itemSeasonReport);
					itemSeasonDetailPersistenceIface
							.createItemSeasonDetail(detail);
				}
			
		return id;
	}

	public void deleteItemSeasonReport(String ids)
			throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			itemSeasonReportPersistenceIface
					.deleteItemSeasonReport(new DaItemSeasonReport(Long
							.parseLong(ids.split(",")[i].trim())));
		}

	}

	public DaItemSeasonReport loadItemSeasonReport(
			DaItemSeasonReport itemSeasonReport)
			throws ApplicationAccessException {
		return itemSeasonReportPersistenceIface
				.loadItemSeasonReport(itemSeasonReport);
	}

	public List<DaItemSeasonReport> loadItemSeasonReports(
			DaItemSeasonReport itemSeasonReport, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaItemSeasonReport.class, "dsr");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return itemSeasonReportPersistenceIface.loadItemSeasonReports(
				detachedCriteriaProxy, pagination);
	}

	public void updateItemSeasonReport(DaItemSeasonReport itemSeasonReport)
			throws ApplicationAccessException {
		ResultSet rs=null;
		List<Long> list=new ArrayList<Long>();
		String sql="select id from da_item_season_detail where PAR_DA_ITE_ID="+itemSeasonReport.getId();
		rs=itemSeasonDetailPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				list.add(rs.getLong("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<list.size();i++){
			itemSeasonDetailPersistenceIface.deleteItemSeasonDetail(new DaItemSeasonDetail(list.get(i)));
		}
		for(DaItemSeasonDetail detail : itemSeasonReport.getItemSeasonDetails()) {
			if(detail!=null){
				if(detail.getCompanyName()!=null&&!"".equals(detail.getCompanyName())){
					detail.setDaItemSeasonReport(itemSeasonReport);
					itemSeasonDetailPersistenceIface
							.createItemSeasonDetail(detail);
				}
			}
		}
		itemSeasonReportPersistenceIface
				.updateItemSeasonReport(itemSeasonReport);
	}

	public ItemSeasonReportPersistenceIface getItemSeasonReportPersistenceIface() {
		return itemSeasonReportPersistenceIface;
	}

	public void setItemSeasonReportPersistenceIface(
			ItemSeasonReportPersistenceIface itemSeasonReportPersistenceIface) {
		this.itemSeasonReportPersistenceIface = itemSeasonReportPersistenceIface;
	}

	public ItemPersistenceIface getItemPersistenceIface() {
		return itemPersistenceIface;
	}

	public void setItemPersistenceIface(
			ItemPersistenceIface itemPersistenceIface) {
		this.itemPersistenceIface = itemPersistenceIface;
	}

	public ItemSeasonDetailPersistenceIface getItemSeasonDetailPersistenceIface() {
		return itemSeasonDetailPersistenceIface;
	}

	public void setItemSeasonDetailPersistenceIface(
			ItemSeasonDetailPersistenceIface itemSeasonDetailPersistenceIface) {
		this.itemSeasonDetailPersistenceIface = itemSeasonDetailPersistenceIface;
	}

}
