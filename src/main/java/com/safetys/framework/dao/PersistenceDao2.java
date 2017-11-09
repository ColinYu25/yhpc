package com.safetys.framework.dao;

import java.util.List;

import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.pagination.Pagination;

public interface PersistenceDao2<T> {
	 public List<T> findAllByCriteria(DetachedCriteriaProxy2 detachedCriteriaProxy);

	List<T> findPageByCriteria(DetachedCriteriaProxy2 detachedCriteriaProxy,
			Pagination pagination);
}
