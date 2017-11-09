package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerGoverPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ItemPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.ItemDangerFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class ItemDangerFacadeImpl implements ItemDangerFacadeIface {

	private ItemDangerPersistenceIface itemDangerPersistenceIface;

	private ItemPersistenceIface itemPersistenceIface;

	private ItemDangerGoverPersistenceIface itemDangerGoverPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	/**
	 * 加载过期未整改工程项目重大隐患
	 */
	@SuppressWarnings("deprecation")
	public List<DaItemDanger> loadItemExpiredDangers(DaItemDanger itemDanger,
			Pagination pagination, String flag,UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaItemDanger.class, "did");
		detachedCriteriaProxy.createCriteria("did.daItem", "didt");
		if (itemDanger != null) {
			if (itemDanger.getDaItem().getItemname() != null
					&& !"".equals(itemDanger.getDaItem().getItemname())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"didt.itemname", itemDanger.getDaItem().getItemname(),
						MatchMode.ANYWHERE));
			}
			if (itemDanger.getFinishDate() != null
					&& !"".equals(itemDanger.getFinishDate())) {
				String year=itemDanger.getFinishDate().toLocaleString().substring(0, 4);
				String month=(itemDanger.getFinishDate().getMonth()+1)+"";
				Timestamp timestamp=Timestamp.valueOf(year+"-"+month+"-31 00:00:00");
				detachedCriteriaProxy.add(RestrictionsProxy.and(RestrictionsProxy.gt("did.finishDate", itemDanger.getFinishDate()), RestrictionsProxy.lt("did.finishDate", timestamp)));
			}
		}
		if (flag.equals("jianwei")) {
			detachedCriteriaProxy
					.add(RestrictionsProxy
							.sqlRestriction("PAR_DA_ITE_ID in (select id from DA_ITEM where TYPE= "
									+ Nbyhpc.CONSTRUCTION_PROJECT + ")"));
		} else if (flag.equals("jiaotong")) {
			detachedCriteriaProxy
					.add(RestrictionsProxy
							.sqlRestriction("PAR_DA_ITE_ID in (select id from DA_ITEM where TYPE= "
									+ Nbyhpc.TRAFFIC_PROJECT + ")"));
		} else if (flag.equals("shuili")) {
			detachedCriteriaProxy
					.add(RestrictionsProxy
							.sqlRestriction("PAR_DA_ITE_ID in (select id from DA_ITEM where TYPE= "
									+ Nbyhpc.WATER_PROJECT + ")"));
		}
		if (userDetail.getFifthArea() != null
				&& !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("didt.fifthArea",
					userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null
				&& !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("didt.fouthArea",
					userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null
				&& !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("didt.thirdArea",
					userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null
				&& !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("didt.secondArea",
					userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null
				&& !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("didt.firstArea",
					userDetail.getFirstArea()));
		} else {

		}
		detachedCriteriaProxy.add(RestrictionsProxy.lt("did.finishDate",
				new Date()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("did.govern",
				new Integer(0)));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return itemDangerPersistenceIface.loadItemDangers(
				detachedCriteriaProxy, pagination);
	}

	public boolean getItemDangerGover(String itemDangerId)
			throws ApplicationAccessException {
		/**
		 * DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaItemDangerGover.class, "didg");
		Object[] id = new Object[ids.split(",").length];
		for (int i = 0; i < ids.split(",").length; i++) {
			id[i]=ids.split(",")[i].trim();
		}
		detachedCriteriaProxy.add(RestrictionsProxy.in("didg.daItemDanger.id",id));
		List<DaItemDangerGover> rs = new ArrayList<DaItemDangerGover>();
		rs = itemDangerGoverPersistenceIface.loadItemDangerGovers(detachedCriteriaProxy,null);
		boolean flag = false;
		if (rs != null && rs.size()>0) {
			flag = true;
		}
		return flag;
		 */
		ResultSet rs = null;
		String sql = "select id from da_item_danger_gover where PAR_DA_IT_ID in ("
				+ itemDangerId + ") and IS_DELETED=0";
		rs = itemDangerPersistenceIface.findBySql(sql);
		Long id = null;
		boolean flag = false;
		try {
			while (rs.next()) {
				id = rs.getLong("id");
			}
			if (id == null) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 加载企业行业
	 */
	public List<DaIndustryParameter> loadTradeTypesForCompany(
			UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type",
				Nbyhpc.TROUBLE_TYPE));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,
				null);
	}

	public DaItem loadItem(DaItem item) throws ApplicationAccessException {
		return itemPersistenceIface.loadItem(item);
	}

	public Long createItemDanger(DaItemDanger itemDanger)
			throws ApplicationAccessException {
		return itemDangerPersistenceIface.createItemDanger(itemDanger);
	}

	public void deleteItemDanger(String ids) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			itemDangerPersistenceIface.deleteItemDanger(new DaItemDanger(Long
					.parseLong(ids.split(",")[i].trim())));
		}
	}

	public DaItemDanger loadItemDanger(DaItemDanger itemDanger)
			throws ApplicationAccessException {
		return itemDangerPersistenceIface.loadItemDanger(itemDanger);
	}

	public List<DaItemDanger> loadItemDangers(DaItemDanger itemDanger,
			Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaItemDanger.class, "did");
		detachedCriteriaProxy.createCriteria("did.daItem", "didt");
		if (itemDanger != null) {
			if (itemDanger.getDaItem().getItemname() != null
					&& !"".equals(itemDanger.getDaItem().getItemname())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"didt.itemname", itemDanger.getDaItem().getItemname(),
						MatchMode.ANYWHERE));
			}
		}
		if(itemDanger.getGovern() != null && itemDanger.getGovern()!=2){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("did.govern", itemDanger.getGovern()));
		}
		
		detachedCriteriaProxy.add(RestrictionsProxy.eq("didt.id", itemDanger
				.getDaItem().getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("did.modifyTime"));
		return itemDangerPersistenceIface.loadItemDangers(
				detachedCriteriaProxy, pagination);
	}

	public void updateItemDanger(DaItemDanger itemDanger)
			throws ApplicationAccessException {
		itemDangerPersistenceIface.updateItemDanger(itemDanger);
	}

	public ItemDangerPersistenceIface getItemDangerPersistenceIface() {
		return itemDangerPersistenceIface;
	}

	public void setItemDangerPersistenceIface(
			ItemDangerPersistenceIface itemDangerPersistenceIface) {
		this.itemDangerPersistenceIface = itemDangerPersistenceIface;
	}

	public ItemPersistenceIface getItemPersistenceIface() {
		return itemPersistenceIface;
	}

	public void setItemPersistenceIface(
			ItemPersistenceIface itemPersistenceIface) {
		this.itemPersistenceIface = itemPersistenceIface;
	}

	public TradeTypePersistenceIface getTradeTypePersistenceIface() {
		return tradeTypePersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public ItemDangerGoverPersistenceIface getItemDangerGoverPersistenceIface() {
		return itemDangerGoverPersistenceIface;
	}

	public void setItemDangerGoverPersistenceIface(
			ItemDangerGoverPersistenceIface itemDangerGoverPersistenceIface) {
		this.itemDangerGoverPersistenceIface = itemDangerGoverPersistenceIface;
	}

}
