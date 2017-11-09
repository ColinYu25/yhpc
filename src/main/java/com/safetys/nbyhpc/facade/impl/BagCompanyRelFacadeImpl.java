package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaBagCompanyRel;
import com.safetys.nbyhpc.domain.persistence.iface.BagCompanyRelPersistenceIface;
import com.safetys.nbyhpc.facade.iface.BagCompanyRelFacadeIface;

public class BagCompanyRelFacadeImpl implements BagCompanyRelFacadeIface {

	private BagCompanyRelPersistenceIface bagCompanyRelPersistenceIface;
	
	public void createBagCompanyRel(String bagId,String companyPassIds,Long userId) throws ApplicationAccessException{
		for(int i=0;i<companyPassIds.split(",").length;i++){
			DaBagCompanyRel bagCompanyRel=new DaBagCompanyRel();
				bagCompanyRel.setDaBag(new DaBag(Long.parseLong(bagId)));
				bagCompanyRel.setParDaComId(Long.parseLong(companyPassIds.split(",")[i]));
				bagCompanyRel.setUserId(userId);
			if(existsBagCompanyRel(Long.parseLong(companyPassIds.split(",")[i]))){
					bagCompanyRelPersistenceIface.deleteBagCompanyRel(loadBagCompanyRelByCompanyId(Long.parseLong(companyPassIds.split(",")[i])));
			}
			bagCompanyRelPersistenceIface.createBagCompanyRel(bagCompanyRel);
		}
	}
	
	
	
	private boolean existsBagCompanyRel(Long id) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
		.forClass(DaBagCompanyRel.class, "dcr");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcr.parDaComId", id));
		List<DaBagCompanyRel> bagCompanyRels = bagCompanyRelPersistenceIface.loadBagCompanyRels(
				detachedCriteriaProxy, null);
		if (bagCompanyRels.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Long createBagCompanyRel(DaBagCompanyRel bagCompanyRel)
			throws ApplicationAccessException {
		return bagCompanyRelPersistenceIface.createBagCompanyRel(bagCompanyRel);
	}

	public void deleteBagCompanyRel(String ids)
			throws ApplicationAccessException {
		for(int i=0;i<ids.split(",").length;i++){
			bagCompanyRelPersistenceIface.deleteBagCompanyRel(loadBagCompanyRelByCompanyId(Long
					.parseLong(ids.split(",")[i].trim())));
		}
	}

	public DaBagCompanyRel loadBagCompanyRel(DaBagCompanyRel bagCompanyRel)
			throws ApplicationAccessException {
		return bagCompanyRelPersistenceIface.loadBagCompanyRel(bagCompanyRel);
	}

	private DaBagCompanyRel loadBagCompanyRelByCompanyId(Long id)
			throws ApplicationAccessException {
		DaBagCompanyRel daBagCompanyRel=null;
		try {
			DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaBagCompanyRel.class,"dbcr");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dbcr.parDaComId", id));
			daBagCompanyRel=bagCompanyRelPersistenceIface.loadBagCompanyRels(detachedCriteriaProxy, null).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return daBagCompanyRel;
	}

	public List<DaBagCompanyRel> loadBagCompanyRels(
			DaBagCompanyRel bagCompanyRel, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaBagCompanyRel.class,"dbcr");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return bagCompanyRelPersistenceIface.loadBagCompanyRels(detachedCriteriaProxy, pagination);
	}
	
	public void updateBagCompanyRel(DaBagCompanyRel bagCompanyRel)
			throws ApplicationAccessException {
		bagCompanyRelPersistenceIface.updateBagCompanyRel(bagCompanyRel);
	}

	public BagCompanyRelPersistenceIface getBagCompanyRelPersistenceIface() {
		return bagCompanyRelPersistenceIface;
	}

	public void setBagCompanyRelPersistenceIface(
			BagCompanyRelPersistenceIface bagCompanyRelPersistenceIface) {
		this.bagCompanyRelPersistenceIface = bagCompanyRelPersistenceIface;
	}

}
