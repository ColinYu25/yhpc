package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.persistence.iface.ItemPersistenceIface;
import com.safetys.nbyhpc.facade.iface.ItemFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class ItemFacadeImpl implements ItemFacadeIface{

	private ItemPersistenceIface itemPersistenceIface;
	
	private ItemDangerGoverFacadeImpl itemDangerGoverFacadeImpl;
	
	/**
	 * 加载未治理隐患
	 */
	public List<DaItem> loadUnGoverItems(DaItem item,Pagination pagination,String flag,UserDetailWrapper userDetail) throws ApplicationAccessException, SQLException{
		List<Long> ids=loadUnGoverDangerItemIds();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
		.forClass(DaItem.class, "di");
		if(ids.size()!=0){
			if(item!=null){
				if (item.getItemname() != null
						&& !"".equals(item.getItemname().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"di.itemname", item.getItemname().trim(),
							MatchMode.ANYWHERE));
				}
				if (item.getItemaddress() != null
						&& !"".equals(item.getItemaddress().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"di.itemaddress", item.getItemaddress().trim(),
							MatchMode.ANYWHERE));
				}
				if (item.getFirstArea() != null && item.getFirstArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",
							item.getFirstArea()));
					if (item.getSecondArea() != null
							&& item.getSecondArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq(
								"di.secondArea", item.getSecondArea()));
						if (item.getThirdArea() != null
								&& item.getThirdArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq(
									"di.thirdArea", item.getThirdArea()));
							if (item.getFouthArea() != null
									&& item.getFouthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq(
										"di.fouthArea", item.getFouthArea()));
								if (item.getFifthArea() != null
										&& item.getFifthArea() != 0L) {
									detachedCriteriaProxy
											.add(RestrictionsProxy.eq(
													"di.fifthArea", item
															.getFifthArea()));
								}
							}
						}
					}
				}
				if (item.getIscompleted() != null
						&& !"".equals(item.getIscompleted().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"di.iscompleted", item.getIscompleted().trim()));
				}
			}
			if(flag.equals("jianwei")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.CONSTRUCTION_PROJECT));
			}else if(flag.equals("jiaotong")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.TRAFFIC_PROJECT));
			}else if(flag.equals("shuili")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.WATER_PROJECT));
			}else if(flag.equals("chengguan")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.CITY_PROJECT));
			}
			if (userDetail.getFifthArea() != null
					&& !userDetail.getFifthArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fifthArea",
						userDetail.getFifthArea()));
			} else if (userDetail.getFouthArea() != null
					&& !userDetail.getFouthArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fouthArea",
						userDetail.getFouthArea()));
			} else if (userDetail.getThirdArea() != null
					&& !userDetail.getThirdArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea",
						userDetail.getThirdArea()));
			} else if (userDetail.getSecondArea() != null
					&& !userDetail.getSecondArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea",
						userDetail.getSecondArea()));
			} else if (userDetail.getFirstArea() != null
					&& !userDetail.getFirstArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",
						userDetail.getFirstArea()));
			} else {

			}
			detachedCriteriaProxy.add(RestrictionsProxy.in("di.id", ids));
			detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
			return itemPersistenceIface.loadItems(detachedCriteriaProxy, pagination);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 加载已治理隐患
	 */
	public List<DaItem> loadGoverItems(DaItem item,Pagination pagination,String flag,UserDetailWrapper userDetail) throws ApplicationAccessException, SQLException{
		List<Long> ids=loadDangerItemIds();
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
		.forClass(DaItem.class, "di");
		if(ids.size()!=0){
			if(item!=null){
				if (item.getItemname() != null
						&& !"".equals(item.getItemname().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"di.itemname", item.getItemname().trim(),
							MatchMode.ANYWHERE));
				}
				if (item.getItemaddress() != null
						&& !"".equals(item.getItemaddress().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"di.itemaddress", item.getItemaddress().trim(),
							MatchMode.ANYWHERE));
				}
				if (item.getFirstArea() != null && item.getFirstArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",
							item.getFirstArea()));
					if (item.getSecondArea() != null
							&& item.getSecondArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq(
								"di.secondArea", item.getSecondArea()));
						if (item.getThirdArea() != null
								&& item.getThirdArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq(
									"di.thirdArea", item.getThirdArea()));
							if (item.getFouthArea() != null
									&& item.getFouthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq(
										"di.fouthArea", item.getFouthArea()));
								if (item.getFifthArea() != null
										&& item.getFifthArea() != 0L) {
									detachedCriteriaProxy
											.add(RestrictionsProxy.eq(
													"di.fifthArea", item
															.getFifthArea()));
								}
							}
						}
					}
				}
				if (item.getIscompleted() != null
						&& !"".equals(item.getIscompleted().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"di.iscompleted", item.getIscompleted().trim()));
				}
			}
			if(flag.equals("jianwei")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.CONSTRUCTION_PROJECT));
			}else if(flag.equals("jiaotong")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.TRAFFIC_PROJECT));
			}else if(flag.equals("shuili")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.WATER_PROJECT));
			}else if(flag.equals("chengguan")){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.CITY_PROJECT));
			}
			if (userDetail.getFifthArea() != null
					&& !userDetail.getFifthArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fifthArea",
						userDetail.getFifthArea()));
			} else if (userDetail.getFouthArea() != null
					&& !userDetail.getFouthArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fouthArea",
						userDetail.getFouthArea()));
			} else if (userDetail.getThirdArea() != null
					&& !userDetail.getThirdArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea",
						userDetail.getThirdArea()));
			} else if (userDetail.getSecondArea() != null
					&& !userDetail.getSecondArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea",
						userDetail.getSecondArea()));
			} else if (userDetail.getFirstArea() != null
					&& !userDetail.getFirstArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",
						userDetail.getFirstArea()));
			} else {

			}
			detachedCriteriaProxy.add(RestrictionsProxy.in("di.id", ids));
			detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
			return itemPersistenceIface.loadItems(detachedCriteriaProxy, pagination);
		}else{
			return null;
		}
		
	}
	
	private List<Long> loadIds(Long itemId) throws ApplicationAccessException{
		List<Long> ids=new ArrayList<Long>();
		ResultSet rs=null;
		String sql="select id from DA_ITEM_DANGER where PAR_DA_ITE_ID="+itemId+" and GOVERN=1";
		rs=itemPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				ids.add(rs.getLong("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	
	private List<Long> loadDangerItemIds() throws ApplicationAccessException, SQLException{
		ResultSet rs=null;
		DaItem item=null;
		List<Long> ids=new ArrayList<Long>();
		String sql="select PAR_DA_ITE_ID from DA_ITEM_DANGER group by PAR_DA_ITE_ID";
		rs=itemPersistenceIface.findBySql(sql);
		while(rs.next()){
			ids.add(rs.getLong("PAR_DA_ITE_ID"));
			List<Long> nids= loadIds(rs.getLong("PAR_DA_ITE_ID"));
			int num1= loadloadItemDangerGoversNum(nids);
			item=itemPersistenceIface.loadItem(new DaItem(rs.getLong("PAR_DA_ITE_ID")));
			item.setGoverDangernNum(num1);
		}
		return ids;
	}
	
	private int loadloadItemDangerGoversNum(List<Long> id) throws ApplicationAccessException{
		ResultSet rs=null;
		List<Long> list=new ArrayList<Long>();
		String str="";
		if(id.size()!=0){
			for(int i=0;i<id.size();i++){
				str+=id.get(i)+",";
			}
			String sql="select id from DA_ITEM_DANGER_GOVER where PAR_DA_IT_ID in ("+str.substring(0, str.length()-1)+")";
			rs=itemPersistenceIface.findBySql(sql);
			try {
				while(rs.next()){
					list.add(rs.getLong("id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list.size();
		}else{
			return 0;
		}
		
	}
	
//	private List<Long> loadGoverDangerItemIds() throws ApplicationAccessException, SQLException{
//		ResultSet rs=null;
//		DaItem item=null;
//		List<Long> ids=new ArrayList<Long>();
//		String sql="select PAR_DA_ITE_ID,count(*)as num from DA_ITEM_DANGER where id in (select PAR_DA_IT_ID from DA_ITEM_DANGER_GOVER where IS_DELETED=0) group by PAR_DA_ITE_ID";
//		rs=itemPersistenceIface.findBySql(sql);
//		while(rs.next()){
//			ids.add(rs.getLong("PAR_DA_ITE_ID"));
//			item=itemPersistenceIface.loadItem(new DaItem(rs.getLong("PAR_DA_ITE_ID")));
//			item.setGoverDangernNum(rs.getInt("num"));
//		}
//		return ids;
//	}
	
	private List<Long> loadUnGoverDangerItemIds() throws ApplicationAccessException, SQLException{
		ResultSet rs=null;
		DaItem item=null;
		List<Long> ids=new ArrayList<Long>();
		String sql="select PAR_DA_ITE_ID,count(*)as num from DA_ITEM_DANGER where id not in (select PAR_DA_IT_ID from DA_ITEM_DANGER_GOVER where IS_DELETED=0) group by PAR_DA_ITE_ID";
		rs=itemPersistenceIface.findBySql(sql);
		while(rs.next()){
			ids.add(rs.getLong("PAR_DA_ITE_ID"));
			item=itemPersistenceIface.loadItem(new DaItem(rs.getLong("PAR_DA_ITE_ID")));
			item.setUnGoverDangernNum(rs.getInt("num"));
		}
		return ids;
	}
	
	public Long createItem(DaItem item) throws ApplicationAccessException {
		return itemPersistenceIface.createItem(item);
	}

	public void deleteItem(String ids) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			itemPersistenceIface.deleteItem(new DaItem(
					Long.parseLong(ids.split(",")[i].trim())));
		}
		
	}

	public DaItem loadItem(DaItem item) throws ApplicationAccessException {
		return itemPersistenceIface.loadItem(item);
	}

	public List<DaItem> loadItems(DaItem item, Pagination pagination,String year,String flag,UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaItem.class, "di");
		if(item!=null){
			if (item.getItemname() != null
					&& !"".equals(item.getItemname().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"di.itemname", item.getItemname().trim(),
						MatchMode.ANYWHERE));
			}
			if (item.getItemaddress() != null
					&& !"".equals(item.getItemaddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"di.itemaddress", item.getItemaddress().trim(),
						MatchMode.ANYWHERE));
			}
			if (item.getFirstArea() != null && item.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",item.getFirstArea()));
				if (Integer.valueOf(1).equals(item.getSelf())) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea", 0L));
				} else {
					if (item.getSecondArea() != null && item.getSecondArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea", item.getSecondArea()));
						if (item.getThirdArea() != null && item.getThirdArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea", item.getThirdArea()));
							if (item.getFouthArea() != null && item.getFouthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fouthArea", item.getFouthArea()));
								if (item.getFifthArea() != null && item.getFifthArea() != 0L) {
									detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fifthArea", item.getFifthArea()));
								}
							}
						}
					}
				}
			}
		}
		if(item!=null){
			if (item.getIscompleted() != null
					&& !"".equals(item.getIscompleted().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq(
						"di.iscompleted", item.getIscompleted().trim()));
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.eq(
						"di.iscompleted","0"));
			}
		}else{
			detachedCriteriaProxy.add(RestrictionsProxy.eq(
					"di.iscompleted", "0"));
		}
//		
//		String y1="";
//		if(year!=null&&!"".equals(year)){
//			y1=year+"-01-01 00:00:00";
//			Timestamp timestamp=Timestamp.valueOf(y1);
//			detachedCriteriaProxy.add(RestrictionsProxy.ge("di.createTime", timestamp));
//			
//		}
		if(flag.equals("jianwei")){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.CONSTRUCTION_PROJECT));
//			System.out.println("di.type: "+Nbyhpc.CONSTRUCTION_PROJECT);
		}else if(flag.equals("jiaotong")){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.TRAFFIC_PROJECT));
		}else if(flag.equals("shuili")){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.WATER_PROJECT));
		}else if(flag.equals("chengguan")){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("di.type", Nbyhpc.CITY_PROJECT));
		}
		boolean boo=true;
		for(Object obj:userDetail.getAuthorities()){
			if (Nbyhpc.ROLE.equals(obj + "")) {
				boo=false;
				detachedCriteriaProxy.add(RestrictionsProxy.in("di.secondArea", Nbyhpc.QC_AREA));
			}
		}
		if(boo){
			if (userDetail.getFifthArea() != null
					&& !userDetail.getFifthArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fifthArea",
						userDetail.getFifthArea()));
			} else if (userDetail.getFouthArea() != null
					&& !userDetail.getFouthArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.fouthArea",
						userDetail.getFouthArea()));
			} else if (userDetail.getThirdArea() != null
					&& !userDetail.getThirdArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.thirdArea",
						userDetail.getThirdArea()));
//				System.out.println("三："+userDetail.getThirdArea());
			} else if (userDetail.getSecondArea() != null
					&& !userDetail.getSecondArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea",
						userDetail.getSecondArea()));
//				System.out.println("二："+userDetail.getSecondArea());
			} else if (userDetail.getFirstArea() != null
					&& !userDetail.getFirstArea().equals(0L)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("di.firstArea",
						userDetail.getFirstArea()));
				if (item != null && Integer.valueOf(1).equals(item.getSelf())) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("di.secondArea", 0L));
				}
//				System.out.println("一："+userDetail.getFirstArea());
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));//被王炯波注释 2009-11-18
		return itemPersistenceIface.loadItems(detachedCriteriaProxy, pagination);
	}

	public void updateItem(DaItem item) throws ApplicationAccessException {
		itemPersistenceIface.updateItem(item);
	}

	public ItemPersistenceIface getItemPersistenceIface() {
		return itemPersistenceIface;
	}

	public void setItemPersistenceIface(ItemPersistenceIface itemPersistenceIface) {
		this.itemPersistenceIface = itemPersistenceIface;
	}

	public ItemDangerGoverFacadeImpl getItemDangerGoverFacadeImpl() {
		return itemDangerGoverFacadeImpl;
	}

	public void setItemDangerGoverFacadeImpl(
			ItemDangerGoverFacadeImpl itemDangerGoverFacadeImpl) {
		this.itemDangerGoverFacadeImpl = itemDangerGoverFacadeImpl;
	}

}
