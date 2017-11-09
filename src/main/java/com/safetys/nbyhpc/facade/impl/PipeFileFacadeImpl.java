package com.safetys.nbyhpc.facade.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaPipeFile;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallFile;
import com.safetys.nbyhpc.domain.persistence.iface.PipeFilePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallFilePersistenceIface;
import com.safetys.nbyhpc.facade.iface.PipeFileFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;

public class PipeFileFacadeImpl implements PipeFileFacadeIface {

	private PipeFilePersistenceIface pipeFilePersistenceIface;
	
	private PipeRollcallFilePersistenceIface pipeRollcallFilePersistenceIface;
	
	public String createFile(DaPipeFile file,DaPipeRollcallFile rollcallFile,DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		String l = null;
		Map map = FileUpload.UploadFile(file.getFile(), file.getFileFileName(),
				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD, false);
		String error = map.get("error").toString();
		if (error != null && !"".equals(error.trim())) {
			return error;
		}
		file.setFileRealPath(map.get("filePath")+"");
		file.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
		pipeFilePersistenceIface.createFile(file);
		rollcallFile.setDaPipeFile(file);
		rollcallFile.setDaPipeRollcallCompany(rollcallCompany);
		l=pipeRollcallFilePersistenceIface.createRollcallFile(rollcallFile).toString();
		return  l;
	}

	public DaPipeFile loadFile(DaPipeFile file) throws ApplicationAccessException {
		return pipeFilePersistenceIface.loadFile(file);
	}

	public List<DaPipeRollcallFile> loadFileList(Pagination pagination, DaPipeFile file
			,DaPipeRollcallCompany rollcallCompany,int type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallFile.class, "drf");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("drf.type", type));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.PAR_DA_PIPE_ROL_ID ="+rollcallCompany.getId()));
		if(file!=null){
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.da_pipe_file_id in (select id from da_pipe_file where file_name like '%"+file.getFileName()+"%')"));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("drf.modifyTime"));
		return pipeRollcallFilePersistenceIface.loadRollcallFiles(detachedCriteriaProxy, pagination);
	}

	public String updateFile(DaPipeFile file) throws ApplicationAccessException {
		Map map = FileUpload.UploadFile(file.getFile(), file.getFileFileName(),
				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD, false);
		String error = map.get("error").toString();
		if (error != null && !"".equals(error.trim())) {
			return error;
		}
		file.setFileRealPath(map.get("filePath")+"");
		file.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
		pipeFilePersistenceIface.updateFile(file);
		return file.getId().toString();
		
	}

	public void deleteFile(DaPipeFile file) throws ApplicationAccessException {
		Set rollcallFiles=file.getDaPipeRollcallFiles();
		Iterator it=rollcallFiles.iterator();
		DaPipeRollcallFile rollcallFile=null;
		while(it.hasNext()){
			rollcallFile=(DaPipeRollcallFile)it.next();
		}
		rollcallFile.setDeleted(true);
		pipeRollcallFilePersistenceIface.updateRollcallFile(rollcallFile);
		pipeFilePersistenceIface.updateFile(file);
	}

	public void setPipeFilePersistenceIface(
			PipeFilePersistenceIface pipeFilePersistenceIface) {
		this.pipeFilePersistenceIface = pipeFilePersistenceIface;
	}

	public void setPipeRollcallFilePersistenceIface(
			PipeRollcallFilePersistenceIface pipeRollcallFilePersistenceIface) {
		this.pipeRollcallFilePersistenceIface = pipeRollcallFilePersistenceIface;
	}



}
