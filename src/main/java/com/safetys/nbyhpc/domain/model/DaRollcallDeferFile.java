package com.safetys.nbyhpc.domain.model;

import java.io.File;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaRollcallDeferFile extends DaBaseModel{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8340093722890636027L;

	private String fileName ;
	
	private Long userId;
	
	private String filePath;
	
	private  String fileRealPath;
	
	private File file;
	
	private String fileFactName;
	
	private Integer fileType;
	
	private String fileFileName;
	
	private DaRollcallDefer daRollcallDefer;

	public DaRollcallDeferFile(){
		
	}
	
	public DaRollcallDeferFile(long id) {
		super(id);
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

	public DaRollcallDefer getDaRollcallDefer() {
		return daRollcallDefer;
	}

	public void setDaRollcallDefer(DaRollcallDefer daRollcallDefer) {
		this.daRollcallDefer = daRollcallDefer;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
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

	public String getFileFactName() {
		return fileFactName;
	}

	public void setFileFactName(String fileFactName) {
		this.fileFactName = fileFactName;
	}

	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}
	
	
}
