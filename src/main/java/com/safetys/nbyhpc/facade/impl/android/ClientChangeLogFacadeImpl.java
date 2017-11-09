package com.safetys.nbyhpc.facade.impl.android;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.android.ClientChangeLog;
import com.safetys.nbyhpc.domain.persistence.iface.android.ClientChangeLogPersistence;
import com.safetys.nbyhpc.facade.iface.android.ClientChangeLogFacadeIface;
import com.safetys.nbyhpc.facade.impl.BaseFacadeImpl;
import com.safetys.nbyhpc.util.FileUpload;
import com.safetys.nbyhpc.util.OsType;
import com.safetys.nbyhpc.util.OsUserType;

public class ClientChangeLogFacadeImpl extends BaseFacadeImpl<ClientChangeLog> implements ClientChangeLogFacadeIface {

	private ClientChangeLogPersistence clientChangeLogPersistence;
	
	@Override
	public List<ClientChangeLog> loadClientChangeLogs(ClientChangeLog entity, Pagination page) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from ClientChangeLog where deleted = false ");
		if (entity != null) {
			if (!StringUtils.isBlank(entity.getType())) {
				hql.append(" and type = ?");
				params.add(entity.getType());
			}
		}
		hql.append(" order by publishDate desc");
		return clientChangeLogPersistence.find(hql.toString(), params.toArray(), page);
	}

	@Override
	public ClientChangeLog loadLastedClientChangeLog(String os, String type) {
		String hql = "from ClientChangeLog where deleted = false and versionNum=(select max(versionNum) from ClientChangeLog where deleted = false and os = ? and type = ?) and os = ? and type = ? order by publishDate desc";
		List<ClientChangeLog> list = clientChangeLogPersistence.findByHql(hql, new Object[] { os, type, os, type });
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void create(ClientChangeLog entity, File file, String fileFileName) {
		if (OsType.ANDROID.getCode().equals(entity.getOs())) {
			if (OsUserType.ES.getCode().equals(entity.getType())) {
				fileFileName = "yhpc_es.apk";
			} else {
				fileFileName = "yhpc_gov.apk";
			}
		}
		Map map = FileUpload.UploadFile(file, fileFileName, FileUpload.UPLOAD_CLIENT, null, true);
		entity.setFileInfo(String.valueOf(map.get("filePath")));
		entity.setOrignalFileName(fileFileName);
		entity.setPublishDate(new Date());
		clientChangeLogPersistence.create(entity);
	}

	@Override
	public long loadMaxVersionNum(String type) {
		String hql = " select max(versionNum) from ClientChangeLog where deleted = false and type = ?";
		List list = clientChangeLogPersistence.findByHql(hql, type);
		if (list != null && !list.isEmpty() && list.get(0) != null) {
			return Long.valueOf(list.get(0).toString());
		}
		return 0l;
	}
	
	@Override
	public ClientChangeLogPersistence getService() {
		return clientChangeLogPersistence;
	}

	public void setClientChangeLogPersistence(ClientChangeLogPersistence clientChangeLogPersistence) {
		this.clientChangeLogPersistence = clientChangeLogPersistence;
	}

}
