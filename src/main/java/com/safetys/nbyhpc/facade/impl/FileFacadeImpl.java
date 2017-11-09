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
import com.safetys.nbyhpc.domain.model.DaFile;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallFile;
import com.safetys.nbyhpc.domain.persistence.iface.FilePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallFilePersistenceIface;
import com.safetys.nbyhpc.facade.iface.FileFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;

public class FileFacadeImpl implements FileFacadeIface {

	private FilePersistenceIface filePersistenceIface;
	
	private RollcallFilePersistenceIface rollcallFilePersistenceIface;
	
	public String createFile(DaFile file,DaRollcallFile rollcallFile,DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		String l = null;
		Map map = FileUpload.UploadFile(file.getFile(), file.getFileFileName(),
				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD, false);
		String error = map.get("error").toString();
		if (error != null && !"".equals(error.trim())) {
			return error;
		}
		file.setFileRealPath(map.get("filePath")+"");
		file.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
		filePersistenceIface.createFile(file);
		rollcallFile.setDaFile(file);
		rollcallFile.setDaRollcallCompany(rollcallCompany);
		l=rollcallFilePersistenceIface.createRollcallFile(rollcallFile).toString();
		return  l;
	}

	public DaFile loadFile(DaFile file) throws ApplicationAccessException {
		return filePersistenceIface.loadFile(file);
	}

	public List<DaRollcallFile> loadFileList(Pagination pagination, DaFile file
			,DaRollcallCompany rollcallCompany,int type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallFile.class, "drf");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("drf.type", type));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.PAR_DA_ROL_ID ="+rollcallCompany.getId()));
		if(file!=null){
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.da_file_id in (select id from da_file where file_name like '%"+file.getFileName()+"%')"));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("drf.modifyTime"));
		return rollcallFilePersistenceIface.loadRollcallFiles(detachedCriteriaProxy, pagination);
	}

	public String updateFile(DaFile file) throws ApplicationAccessException {
		Map map = FileUpload.UploadFile(file.getFile(), file.getFileFileName(),
				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD, false);
		String error = map.get("error").toString();
		if (error != null && !"".equals(error.trim())) {
			return error;
		}
		file.setFileRealPath(map.get("filePath")+"");
		file.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
		filePersistenceIface.updateFile(file);
		return file.getId().toString();
		
	}

	public void deleteFile(DaFile file) throws ApplicationAccessException {
		Set rollcallFiles=file.getDaRollcallFiles();
		Iterator it=rollcallFiles.iterator();
		DaRollcallFile rollcallFile=null;
		while(it.hasNext()){
			rollcallFile=(DaRollcallFile)it.next();
		}
		rollcallFile.setDeleted(true);
		rollcallFilePersistenceIface.updateRollcallFile(rollcallFile);
		filePersistenceIface.updateFile(file);
	}

	public void setFilePersistenceIface(FilePersistenceIface filePersistenceIface) {
		this.filePersistenceIface = filePersistenceIface;
	}

	public RollcallFilePersistenceIface getRollcallFilePersistenceIface() {
		return rollcallFilePersistenceIface;
	}

	public void setRollcallFilePersistenceIface(
			RollcallFilePersistenceIface rollcallFilePersistenceIface) {
		this.rollcallFilePersistenceIface = rollcallFilePersistenceIface;
	}

}
