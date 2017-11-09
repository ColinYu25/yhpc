package com.safetys.nbyhpc.facade.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaImageFile;
import com.safetys.nbyhpc.domain.persistence.iface.ImageFilePersistenceIface;
import com.safetys.nbyhpc.facade.iface.ImageFileFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.OperateResult;

@SuppressWarnings("unchecked")
public class ImageFileFacadeImpl implements ImageFileFacadeIface {

	private ImageFilePersistenceIface imagefilePersistenceIface;

	public OperateResult create(DaImageFile entity) throws Exception {
		DaImageFile file = entity;
		file.setCreateTime(new Date());
		// 保存文件
		String error = saveFile(file, FileUpload.UPLOAD_PATH_FILE);
		if (!Nbyhpc.NO_FILE.equals(error)) {
			if (error != null && !"".equals(error.trim())) {
				return new OperateResult(false, error);
			}
		}
		// 保存文件信息
		imagefilePersistenceIface.createFile(file);
		return new OperateResult(true, "添加成功");
	}

	public OperateResult update(DaImageFile entity) throws Exception {
		DaImageFile oldStFile = imagefilePersistenceIface.loadDaImageFile(entity);
		oldStFile.setModifyTime(new Date());
		oldStFile.setName(entity.getName());
		oldStFile.setNum(entity.getNum());
		oldStFile.setUserId(entity.getUserId());
		if (entity.getFile() != null) {
			String file_error = this.updateFile(entity, oldStFile);
			if (file_error != null && !"".equals(file_error.trim())) {
				return new OperateResult(false, file_error);
			}
		}
		imagefilePersistenceIface.mergeDaImageFile(oldStFile);
		return new OperateResult(true, "更新成功");
	}

	public OperateResult deleteFalse(DaImageFile entity) throws Exception {
		DaImageFile oldStFile = imagefilePersistenceIface.loadDaImageFile(entity);
		if (oldStFile != null && oldStFile.getPath() != null) {
			deleteFile(oldStFile.getPath());
			oldStFile.setPath(null);
			oldStFile.setSize(null);
			oldStFile.setType(null);
			oldStFile.setFileOriginalName(null);
		}
		oldStFile.setDeleted(true);
		oldStFile.setUserId(entity.getUserId());
		oldStFile.setModifyTime(new Date());
		imagefilePersistenceIface.mergeDaImageFile(oldStFile);
		return new OperateResult(true, "删除成功");
	}

	public String saveFile(DaImageFile daImageFile, String dirPath) {
		DaImageFile file = daImageFile;

		file.setPath(dirPath);
		Map map = getUploadInfo(file, null);
		String error = map.get("error").toString();
		if (!Nbyhpc.NO_FILE.equals(error)) {
			if (error != null && !"".equals(error.trim())) {
				return error;
			}
		}
		if (!Nbyhpc.NO_FILE.equals(error)) {
			file.setPath(map.get("filePath").toString());
			file.setSize(Long.parseLong(map.get("fileSize").toString()));
			file.setType(map.get("fileType").toString());
			file.setFileOriginalName(file.getFileFileName());
		}
		return "";
	}

	public String updateFile(DaImageFile daImageFile, DaImageFile _stFile)
			throws ApplicationAccessException {
		DaImageFile model = daImageFile;
		String originalPath = _stFile.getPath(); // 保存原文件的路径
		model.setPath(FileUpload.UPLOAD_PATH_FILE);
		Map map = getUploadInfo(model, null);
		String error = map.get("error").toString();
		if (!Nbyhpc.NO_FILE.equals(error)) {
			if (error != null && !"".equals(error.trim())) {
				return error;
			}
		}
		if (!Nbyhpc.NO_FILE.equals(error)) {// 有新上传的文件
			FileUpload.deleteFile(originalPath);
			_stFile.setPath(map.get("filePath").toString());
			_stFile.setSize(Long.parseLong(map.get("fileSize").toString()));
			_stFile.setType(map.get("fileType").toString());
			_stFile.setFileOriginalName(model.getFileFileName());
		} else {
			model.setPath(originalPath);
		}
		return "";
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public String deleteFile(String path) {
		try {
			FileUpload.deleteFile(path);
			return "成功删除文件";
		} catch (Exception e) {
			return "删除文件失败";
		}
	}

	/**
	 * 获取实体与文件操作相关的属性
	 * 
	 * @param model
	 * @param oldFile
	 * @return
	 */
	private Map getUploadInfo(DaImageFile daImageFile, String oldFile) {
		if (oldFile != null && oldFile.length() > 0) {
			FileUpload.deleteFile(oldFile);
		}
		Map map = FileUpload.UploadFile(daImageFile.getFile(), daImageFile
				.getFileFileName(), daImageFile.getPath(), daImageFile.getAllowType(), false);
		return map;
	}

	public List<DaImageFile> loadFiles(Date beginTime, Date endTime, DaImageFile daImageFile,
			Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaImageFile.class, "daImageFile");
		if (null != beginTime && null != endTime) {
			detachedCriteriaProxy.add(RestrictionsProxy.between(
					"daImageFile.createTime", beginTime, endTime));
		}
		if(null != daImageFile){
			if (daImageFile.getNum() != null && !"".equals(daImageFile.getNum().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("daImageFile.num",
						daImageFile.getNum().trim(), MatchMode.ANYWHERE));
			}
			if (daImageFile.getName() != null && !"".equals(daImageFile.getName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("daImageFile.name",
						daImageFile.getName().trim(), MatchMode.ANYWHERE));
			}
			if (daImageFile.getType() != null && !"".equals(daImageFile.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("daImageFile.type",
						daImageFile.getType()));
			}
			if (daImageFile.getSize() != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("daImageFile.size",
						daImageFile.getSize()));
			}
		}
		return imagefilePersistenceIface
				.loadFiles(detachedCriteriaProxy, pagination);
	}
	
	public List<DaImageFile> loadFiles(Long companyId) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaImageFile.class, "d");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("d.daCompany.id", companyId));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("d.deleted", false));
		return imagefilePersistenceIface.loadFiles(detachedCriteriaProxy, null);
	}

	public DaImageFile loadFile(DaImageFile daImageFile) throws ApplicationAccessException {
		return imagefilePersistenceIface.loadDaImageFile(daImageFile);
	}

	public OperateResult merge(DaImageFile entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public OperateResult deleteTrue(DaImageFile entity) throws Exception {
		imagefilePersistenceIface.deleteFile(entity, true);
		return new OperateResult(true, "删除成功");
	}
	public void setImagefilePersistenceIface(
			ImageFilePersistenceIface imagefilePersistenceIface) {
		this.imagefilePersistenceIface = imagefilePersistenceIface;
	}

}
