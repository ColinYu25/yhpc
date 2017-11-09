package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaTroubleFile;


public interface TroubleFilePersistenceIface {
	
	public Long createTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException;
	
	public void updateTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException;
	
	public void mergeTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException;
	
	public void deleteTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException;
	
	public List<DaTroubleFile> loadTroubleFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaTroubleFile loadTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException;
}