package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;


public interface PipeLinePersistenceIface {
	
	public Long createDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException;
	
	public void updateDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException;
	
	public void mergeDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException;
	
	public void deleteDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException;
	
	public List<DaPipelineInfo> loadPipeLines(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPipelineInfo loadPipeLine(DaPipelineInfo pipeLine) throws ApplicationAccessException;
}