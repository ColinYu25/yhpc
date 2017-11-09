package com.safetys.framework.dao.impl;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.safetys.framework.dao.PersistenceDao2;
import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.pagination.Pagination;
public class PersistenceHibernateDao2 <T> extends HibernateDaoSupport implements PersistenceDao2<T>{
	  public PersistenceHibernateDao2(){
	    	/*empty*/
	  }

	public List<T> findAllByCriteria(
			final DetachedCriteriaProxy2 detachedCriteriaProxy) {
		  return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException {   
	                Criteria criteria = detachedCriteriaProxy.getExecutableCriteria(session);   
	                List<Order> orderEntries=detachedCriteriaProxy.getOrderEntries();
	            	for(int i=0;i<orderEntries.size();i++){
	            		criteria.addOrder(orderEntries.get(i));
	            	}
	                return criteria.list();   
	            }   
	        });   
	}    

	public List<T> findPageByCriteria(final DetachedCriteriaProxy2 detachedCriteriaProxy,final Pagination pagination) {
	    	return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {   
	            public Object doInHibernate(Session session) throws HibernateException {   
	            	Criteria criteria =(Criteria)detachedCriteriaProxy.getExecutableCriteria(session);
	                if(pagination.isEmptyTotalCount()){
	                	int totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();   
	                	pagination.setTotalCount(totalCount);
	                }
	                criteria.setProjection(null);
	                criteria.setResultTransformer(Criteria.ROOT_ENTITY);   
	                List<Order> orderEntries=detachedCriteriaProxy.getOrderEntries();
	            	for(int i=0;i<orderEntries.size();i++){
	            		criteria.addOrder(orderEntries.get(i));
	            	}
	                return criteria.setFirstResult(pagination.getItemCount()).setMaxResults(pagination.getPageSize()).list();   
	            }   
	        });   
	 }   
}
