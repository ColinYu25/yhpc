package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.ZjIdea;
import com.safetys.nbyhpc.domain.persistence.iface.IdeaPersistenceIface;
import com.safetys.nbyhpc.facade.iface.IdeaFacadeIface;

public class IdeaFacadeImpl implements IdeaFacadeIface{
	private IdeaPersistenceIface ideaPersistenceIface;
	public void createIdea(ZjIdea idea){
		ideaPersistenceIface.createIdea(idea);
	}
	public List<ZjIdea> loadIdeas(Long id) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(ZjIdea.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("tableId", id));
		return ideaPersistenceIface.loadIdeas(detachedCriteriaProxy);
	}
	public IdeaPersistenceIface getIdeaPersistenceIface() {
		return ideaPersistenceIface;
	}
	public void setIdeaPersistenceIface(IdeaPersistenceIface ideaPersistenceIface) {
		this.ideaPersistenceIface = ideaPersistenceIface;
	}

}
