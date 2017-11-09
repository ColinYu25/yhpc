package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaPipeTrouble;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.facade.iface.PipeTroubleFacadeIface;

/**
 * 
 * @author qiufc
 * 2011-07-08 qiufc  修改loadRqTroublesByArea方法中的hql查询，把原先查询管道的区域，改成查询企业区域
 */
public class PipeTroubleFacadeImpl implements PipeTroubleFacadeIface {

	private BasePersistenceImpl persistenceImpl;
	Logger log = Logger.getLogger(this.getClass());
	
	public void create(DaPipeTrouble entity) throws Exception {
		persistenceImpl.create(entity);
	}

	public void delete(DaPipeTrouble entity) throws Exception {
		persistenceImpl.delete(entity);
	}

	public List<DaPipeTrouble> find(DaPipeTrouble entity, Pagination page) throws Exception {
		String hql = " from DaPipeTrouble obj where obj.daPipelineInfo.id = ? and obj.deleted = false order by id desc";
		return persistenceImpl.find(hql, new Object[]{entity.getDaPipelineInfo().getId()}, page);
	}

	public Object findById(Class clazz, long id) throws Exception {
		return persistenceImpl.findById(clazz, id);
	}

	public void update(DaPipeTrouble entity) throws Exception {
		persistenceImpl.update(entity);
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public List<DaPipeTrouble> loadRqTroublesByArea(Long areaCode, Pagination page){
		StringBuffer hql = new StringBuffer(" from DaPipeTrouble obj where obj.deleted = false ");
		hql.append("and obj.daPipelineInfo.daPipelineCompanyinfo.type = 1 ");
		if (areaCode != null && areaCode > 0){
			hql.append(" and obj.daPipelineInfo.areaCode = " + areaCode);
		}
		hql.append(" order by id desc");
		return persistenceImpl.find(hql.toString(), new Object[0], page);
	}
	
	public List<DaPipeTrouble> loadYqTroublesByArea(Long areaCode, int lineType, Pagination page){
		StringBuffer hql = new StringBuffer(" from DaPipeTrouble obj where obj.deleted = false ");
		hql.append("and obj.daPipelineInfo.daPipelineCompanyinfo.type = 0 ");
		if (areaCode != null && areaCode > 0){
			hql.append(" and obj.daPipelineInfo.daPipelineCompanyinfo.company.secondArea = " + areaCode);
		}
		if (lineType > 0){
			hql.append(" and obj.daPipelineInfo.type = " + lineType);
		}
		hql.append(" order by id desc");
		//List list = persistenceImpl.find(hql.toString(), new Object[0], page);
		return persistenceImpl.find(hql.toString(), new Object[0], page);
	}
	
}
