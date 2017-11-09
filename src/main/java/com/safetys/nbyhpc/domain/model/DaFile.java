package com.safetys.nbyhpc.domain.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaFile extends DaBaseModel{

	/**
	 * 会议记录、治理方案实体
	 */
	private static final long serialVersionUID = -5793121656491306585L;

	private String fileName;
	
	private Long userId;
	
	private String filePath;
	
	private  String fileRealPath;
	
	private File file;
	
	private String fileFactName;
	
	private Integer fileType;
	
	private String fileFileName;

	private Set<DaRollcallFile> daRollcallFiles = new HashSet<DaRollcallFile>(0);
	
	public DaFile(){
		
	}
	
	public String getFileFactName() {
		return fileFactName;
	}

	public void setFileFactName(String fileFactName) {
		this.fileFactName = fileFactName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<DaRollcallFile> getDaRollcallFiles() {
		return daRollcallFiles;
	}

	public void setDaRollcallFiles(Set<DaRollcallFile> daRollcallFiles) {
		this.daRollcallFiles = daRollcallFiles;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
}
