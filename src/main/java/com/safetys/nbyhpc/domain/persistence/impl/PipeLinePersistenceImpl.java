package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.persistence.iface.PipeLinePersistenceIface;

public class PipeLinePersistenceImpl implements PipeLinePersistenceIface {
	private PersistenceDao<DaPipelineInfo> persistenceDao;

	public Long createDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		return (Long) persistenceDao.save(pipeLine);
	}

	public void deleteDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		pipeLine = loadPipeLine(pipeLine);
		pipeLine.setDeleted(true);
		persistenceDao.merge(pipeLine);
	}

	public DaPipelineInfo loadPipeLine(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		return persistenceDao.get(DaPipelineInfo.class, pipeLine.getId());
	}

	public List<DaPipelineInfo> loadPipeLines(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		List<DaPipelineInfo> list = null;
		try{
			if(pagination != null)
				list = persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
			else
				list = persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public void updateDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		persistenceDao.update(pipeLine);
	}

	public void mergeDanger(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		persistenceDao.merge(pipeLine);
	}	

	public void setPersistenceDao(PersistenceDao<DaPipelineInfo> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
