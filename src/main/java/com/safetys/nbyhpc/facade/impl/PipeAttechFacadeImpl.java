package com.safetys.nbyhpc.facade.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaPipeAttech;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.facade.iface.PipeAttechFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;
import com.safetys.nbyhpc.util.OperateResult;

public class PipeAttechFacadeImpl implements PipeAttechFacadeIface {
	
	private BasePersistenceImpl persistenceImpl;
	Logger log = Logger.getLogger(this.getClass());

	public void create(DaPipeAttech entity) throws Exception {
		
		persistenceImpl.create(entity);
	}

	public void delete(DaPipeAttech entity) throws Exception {
		persistenceImpl.delete(entity);
	}

	public List<DaPipeAttech> find(DaPipeAttech entity, Pagination page) throws Exception {
		StringBuffer hql = new StringBuffer(" from DaPipeAttech obj where obj.deleted = false ");
		hql.append(" and obj.daPipelineInfo.id = ? and obj.type = ? order by id desc");
		
		return persistenceImpl.find(hql.toString(), new Object[]{entity.getDaPipelineInfo().getId(), entity.getType()}, page);
	}

	public Object findById(Class clazz, long id) throws Exception {
		return persistenceImpl.findById(clazz, id);
	}

	public void update(DaPipeAttech entity) throws Exception {
		persistenceImpl.update(entity);
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	
	public OperateResult uploadFile(DaPipeAttech file) throws ApplicationAccessException {
		Map map = FileUpload.UploadFile(file.getUploadFile(), file.getUploadFileFileName(),
				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD, false);
		String error = map.get("error").toString();
		if (error != null && !"".equals(error.trim())) {
			return new OperateResult(false, error);
		}
		return new OperateResult(true, null, map.get("filePath"));
	}
	
	public List<DaPipeAttech> loadRqAttechByArea(Long area, int type) throws Exception {
		StringBuffer hql = new StringBuffer(" from DaPipeAttech obj where obj.deleted = false ");
		hql.append("and obj.daPipelineInfo.daPipelineCompanyinfo.type = 1 and obj.type = " + type);
		if (area != null && area > 0){
			hql.append(" and obj.daPipelineInfo.areaCode = " + area);
		}else{
			hql.append(" and obj.daPipelineInfo.areaCode >0 " );
		}
		hql.append(" order by id desc");
		return persistenceImpl.find(hql.toString(), new Object[0], null);
	}
	
	public List<DaPipeAttech> loadYqAttechByArea(Long area, int type, int lineType) throws Exception {
		StringBuffer hql = new StringBuffer(" from DaPipeAttech obj where obj.deleted = false ");
		hql.append("and obj.daPipelineInfo.daPipelineCompanyinfo.type = 0 and obj.type = " + type);
		if (area != null && area > 0){
			hql.append(" and obj.daPipelineInfo.daPipelineCompanyinfo.company.secondArea = " + area);
		}
		if (lineType > 0){
			hql.append(" and obj.daPipelineInfo.type = " + lineType);
		}
		hql.append(" order by id desc");
		return persistenceImpl.find(hql.toString(), new Object[0], null);
	}

}
