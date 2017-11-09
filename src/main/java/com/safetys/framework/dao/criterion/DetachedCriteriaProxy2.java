package com.safetys.framework.dao.criterion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;

public class DetachedCriteriaProxy2{
	DetachedCriteria detachedCriteria;
	private List<Order> orderEntries = new ArrayList<Order>();
	
	protected DetachedCriteriaProxy2(String entityName) {
		detachedCriteria=DetachedCriteria.forEntityName(entityName);
		//detachedCriteria.add(RestrictionsProxy.eq("deleted",false));
	}
	protected DetachedCriteriaProxy2(String entityName,boolean isDeleted) {
		detachedCriteria=DetachedCriteria.forEntityName(entityName);
		detachedCriteria.add(RestrictionsProxy.eq("deleted",isDeleted));
	}	    
    protected DetachedCriteriaProxy2(String entityName, String alias) {
    	detachedCriteria=DetachedCriteria.forEntityName(entityName,alias);
    	detachedCriteria.add(RestrictionsProxy.eq(alias+".deleted",false));
    }
    protected DetachedCriteriaProxy2(String entityName, String alias,boolean isDeleted) {
    	detachedCriteria=DetachedCriteria.forEntityName(entityName,alias);
    	detachedCriteria.add(RestrictionsProxy.eq(alias+".deleted",isDeleted));
    }
    
    public Criteria getExecutableCriteria(Session session) {
    	return detachedCriteria.getExecutableCriteria(session);
    }
    
    public static DetachedCriteriaProxy2 forEntityName(String entityName) { 	
    	return new DetachedCriteriaProxy2(entityName);
    }
    
    public static DetachedCriteriaProxy2 forEntityName(String entityName,
						 String alias) {
	return new DetachedCriteriaProxy2(entityName, alias);
    }
    
    public static DetachedCriteriaProxy2 forClass(Class clazz) {
	return new DetachedCriteriaProxy2(clazz.getName());
    }
    
    public static DetachedCriteriaProxy2 forClass(Class clazz, String alias) {
	return new DetachedCriteriaProxy2(clazz.getName(), alias);
    }
    
    public DetachedCriteriaProxy2 add(Criterion criterion) {
    	detachedCriteria.add(criterion);
    	return this;
    }
    /**
     * @Override
     */
    public DetachedCriteriaProxy2 addOrder(Order order) {
    	//detachedCriteria.addOrder(order);
    	orderEntries.add(order);
    	return this;
    }
    public List getOrderEntries() {
    	return orderEntries;
    }
    
    public DetachedCriteriaProxy2 createAlias
	(String associationPath, String alias) throws HibernateException {
    	detachedCriteria.createAlias(associationPath, alias);
    	detachedCriteria.add(RestrictionsProxy.eq(alias+".deleted",false));
    	return this;
    }
    
    public DetachedCriteriaProxy2 createCriteria
	(String associationPath, String alias) throws HibernateException {
    	detachedCriteria=detachedCriteria.createCriteria(associationPath,alias);
    	detachedCriteria.add(RestrictionsProxy.eq(alias+".deleted",false));
	return this;
    }
    
    public DetachedCriteriaProxy2 createCriteria(String associationPath)
	throws HibernateException {
    	detachedCriteria=detachedCriteria.createCriteria(associationPath);
    	detachedCriteria.add(RestrictionsProxy.eq("deleted",false));
    	return this;
    }
    
    public String getAlias() {
	return detachedCriteria.getAlias();
    }
    
    public DetachedCriteriaProxy2 setFetchMode (String associationPath, FetchModeProxy mode) throws HibernateException {
    	detachedCriteria.setFetchMode(associationPath,mode.getFetchMode());
	return this;
    }
    
    public DetachedCriteriaProxy2 setProjection(Projection projection) {
    	detachedCriteria.setProjection(projection);
	return this;
    }
    
    public DetachedCriteriaProxy2 setResultTransformer
	(ResultTransformer resultTransformer) {
    	detachedCriteria.setResultTransformer(resultTransformer);
	return this;
    }
//    public DetachedCriteriaProxy distinctRootEntity() {
//    	detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);
//	return this;
//    }
    DetachedCriteria getDetachedCriteria(){
    	return detachedCriteria;
    }
    public String toString() {
	return "DetachedFilter(" + detachedCriteria.toString() + ')';
    }
}

