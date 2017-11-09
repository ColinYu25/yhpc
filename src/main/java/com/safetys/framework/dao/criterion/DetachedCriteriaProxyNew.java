package com.safetys.framework.dao.criterion;

import org.hibernate.criterion.DetachedCriteria;

import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;



public class DetachedCriteriaProxyNew extends DetachedCriteriaProxy{
	DetachedCriteria detachedCriteria;


	protected DetachedCriteriaProxyNew(String entityName, String alias,
			boolean isDeleted) {
		super(entityName, alias, isDeleted);
		
	}
	
	//添加三个参数的构造方法，isDelete字段可以自由设置
	public static DetachedCriteriaProxy forClass(Class clazz,String alias,boolean isDeleted) {
    	return new DetachedCriteriaProxyNew(clazz.getName(),alias,isDeleted);
    }
	/**
	 * @return the detachedCriteria
	 */
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	/**
	 * @param detachedCriteria the detachedCriteria to set
	 */
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
}

