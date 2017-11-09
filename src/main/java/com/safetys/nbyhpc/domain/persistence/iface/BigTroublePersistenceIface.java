package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;

public interface BigTroublePersistenceIface {
	public void createBigTrouble(ZjBigTrouble bigTrouble);

	public List<ZjBigTrouble> loadBigTroubles(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination paginatio);

	public void deleteBigTrouble(ZjBigTrouble bigTrouble, FkUser fkUser)
			throws ApplicationAccessException;

	public ZjBigTrouble loadBigTrouble(long id)
			throws ApplicationAccessException;

	public void updateBigTrouble(ZjBigTrouble bigTrouble);
	
	public void updateBigTroubleOut(ZjBigTrouble bigTrouble);
	
	public List<ZjBigTrouble> loadBigTroublesForStatistic(DetachedCriteriaProxy detachedCriteriaProxy);
}
