package com.safetys.nbyhpc.domain.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.safetys.framework.domain.model.base.BaseModel;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.persistence.iface.DaBasePersistenceIface;

public class DaBasePersistenceImpl<T> implements DaBasePersistenceIface<T>{
	
	Logger log = Logger.getLogger(this.getClass());
	
	private Class<T> persistentClass;
	
	private PersistenceDao<T> persistenceDao;

	public DaBasePersistenceImpl() {
		Object superClass = getClass().getGenericSuperclass();
		if (superClass != null && superClass instanceof ParameterizedType) {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	@Override
	public void create(T entity) {
		persistenceDao.save(entity);
	}

	@Override
	public void delete(T entity) {
		persistenceDao.delete(entity);
	}

	@Override
	public void deleteFake(T entity) {
		if (entity instanceof BaseModel) {
			this.delete(entity.getClass(), ((BaseModel) entity).getId());
		}
	}

	private void delete(Class clazz, Long id) {
		String hql = "update " + clazz.getName() + " obj set obj.deleted = true where obj.id = " + id;
		this.executeHql(hql, new Object[0]);
	}

	@Override
	public List find(String hql, Object[] params, Pagination page) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				Object object = params[i];
				if (object instanceof String) {
					params[i] = ((String) object).replaceAll(" ", "");
				}
			}
		}
		if (page == null) {
			return this.persistenceDao.executeHQLQuery(hql, params);
		} else {
			if (params == null) {
				return this.persistenceDao.findPageByHQL(hql, page);
			}
			return persistenceDao.findPageByHQL(hql, page, params);
		}
	}

	@Override
	public List<T> findByHql(String hql, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				Object object = params[i];
				if (object instanceof String) {
					params[i] = ((String) object).replaceAll(" ", "");
				}
			}
		}
		return this.persistenceDao.executeHQLQuery(hql, params);

	}

	@Override
	public List<T> find(DetachedCriteriaProxy proxy, Pagination page) {
		if (page == null) {
			return persistenceDao.findAllByCriteria(proxy);
		} else {
			return persistenceDao.findPageByCriteria(proxy, page);
		}
	}

	@Override
	public T findById(long id) {
		return (T) findById(persistentClass, id);
	}

	@Override
	public Object findById(Class clazz, Serializable id) {
		return persistenceDao.get(clazz, id);
	}

	@Override
	public void update(T entity) {
		persistenceDao.update(entity);
	}

	@Override
	public int executeHql(String hql, Object[] params) {
		return persistenceDao.executeHQLUpdate(hql, params);
	}

	@Override
	public List find(String sql) {
		return persistenceDao.executeSQLQuery(sql);
	}

	@Override
	public List find(String sql, Object... params) {
		if (params == null || params.length == 0) {
			return find(sql);
		}
		return persistenceDao.executeSQLQuery(sql, params);
	}

	@Override
	public void marge(T entity) {
		persistenceDao.merge(entity);
	}

	@Override
	public int executeBySql(String sql) {
		return this.getPersistenceDao().executeSQLUpdate(sql);
	}

	@Override
	public int executeBySql(String sql, Object params[]) {
		return this.getPersistenceDao().executeSQLUpdate(sql, params);
	}

	@Override
	public ResultSet findBySql(String sql) {
		return persistenceDao.findBySql(sql);
	}
	
	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}
	public PersistenceDao<T> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<T> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
