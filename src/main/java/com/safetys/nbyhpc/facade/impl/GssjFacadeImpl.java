package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.GsQydj;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.facade.iface.GssjFacadeIface;

public class GssjFacadeImpl implements GssjFacadeIface {

	private BasePersistenceImpl persistenceImpl;

	Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public List<GsQydj> loadGssj(GsQydj gs, Pagination page) {
		StringBuffer hql = new StringBuffer(
				" from GsQydj obj where obj.deleted = false ");
		if (gs != null) {
			if (gs.getQymc() != null && !gs.getQymc().equals(""))
				hql.append(" and obj.qymc like '%" + gs.getQymc()+"%'");
			if (gs.getZs() != null && !gs.getZs().equals(""))
				hql.append(" and obj.zs like '%" + gs.getZs()+"%'");
		}
		hql.append(" order by rkrq desc");
		return persistenceImpl.find(hql.toString(), new Object[0], page);
	}
	
	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

}
