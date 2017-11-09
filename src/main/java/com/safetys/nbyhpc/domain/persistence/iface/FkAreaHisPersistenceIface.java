package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.FkAreaHis;

public interface FkAreaHisPersistenceIface { 

	    public abstract void createArea(FkAreaHis fkarea);

	    public abstract List loadAreas(DetachedCriteriaProxy detachedcriteriaproxy, Pagination pagination);

	    public abstract void updateArea(FkAreaHis fkarea);

	    public abstract List loadAreasByHql(String s);

	    public abstract List loadAreas(DetachedCriteriaProxy detachedcriteriaproxy);
	    
	}

	/*
		DECOMPILATION REPORT

		Decompiled from: D:\workspace3.7-2013\nbyhpc1\src\main\webapp\WEB-INF\lib\framework-core-1.1.0.jar
		Total time: 62 ms
		Jad reported messages/errors:
		Exit status: 0
		Caught exceptions:
	*/

