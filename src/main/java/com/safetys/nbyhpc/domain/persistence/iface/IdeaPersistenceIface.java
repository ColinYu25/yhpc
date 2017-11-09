package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjIdea;

public interface IdeaPersistenceIface {
	public void createIdea(ZjIdea idea);
	public List<ZjIdea> loadIdeas(DetachedCriteriaProxy detachedCriteriaProxy);
}
