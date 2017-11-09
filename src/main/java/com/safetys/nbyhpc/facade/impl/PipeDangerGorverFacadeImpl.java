package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerGorverPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;
import com.safetys.nbyhpc.facade.iface.PipeDangerGorverFacadeIface;

public class PipeDangerGorverFacadeImpl implements PipeDangerGorverFacadeIface {

	private PipeDangerGorverPersistenceIface pipeDangerGorverPersistenceIface;
	
	private PipeDangerPersistenceIface pipeDangerPersistenceIface;
	
	private BasePersistenceImpl persistenceImpl;

	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException{
		return pipeDangerPersistenceIface.loadDanger(danger);
	}
	public void createDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		pipeDangerGorverPersistenceIface.createDangerGorver(dangerGorver);
	}
	
	public DaPipelineCompanyInfo loadCompany(DaPipelineCompanyInfo pipeLine) throws ApplicationAccessException {
//		return companyPersistenceIface.loadCompany(company);
		return null;
	}

	public void deleteDangerGorvers(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			pipeDangerGorverPersistenceIface.deleteDangerGorver(new DaPipeDangerGorver(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public void deleteDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		pipeDangerGorverPersistenceIface.deleteDangerGorver(dangerGorver);
	}
	
	public DaPipeDangerGorver loadDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		return pipeDangerGorverPersistenceIface.loadDangerGorver(dangerGorver);
	}
	
	public List<DaPipeDangerGorver> loadDangerGorversOfOne(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDangerGorver.class, "ddg");
		if(dangerGorver != null) {
			if(dangerGorver.getDaPipeDanger() != null){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("ddg.daPipeDanger.id", dangerGorver.getDaPipeDanger().getId()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return pipeDangerGorverPersistenceIface.loadDangerGorvers(detachedCriteriaProxy, null);
	}
	

	public void updateDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		DaPipeDangerGorver oldDanger = loadDangerGorver(dangerGorver);
		dangerGorver.setCreateTime(oldDanger.getCreateTime());
		pipeDangerGorverPersistenceIface.mergeDangerGorver(dangerGorver);
	}	

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}
	public void setPipeDangerGorverPersistenceIface(
			PipeDangerGorverPersistenceIface pipeDangerGorverPersistenceIface) {
		this.pipeDangerGorverPersistenceIface = pipeDangerGorverPersistenceIface;
	}
	public void setPipeDangerPersistenceIface(
			PipeDangerPersistenceIface pipeDangerPersistenceIface) {
		this.pipeDangerPersistenceIface = pipeDangerPersistenceIface;
	}
	
}
