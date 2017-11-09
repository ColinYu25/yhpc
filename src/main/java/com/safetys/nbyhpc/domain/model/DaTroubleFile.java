package com.safetys.nbyhpc.domain.model;

import java.io.File;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaTroubleFile extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7401453700396335852L;

	private String fileName;

	private Long userId;

	private String filePath;

	private File file;

	private String fileType;
	
	private String fileFileName;
	
	private String fileContentType;

	private DaTrouble daTrouble;

	public DaTroubleFile() {

	}

	public DaTrouble getDaTrouble() {
		return daTrouble;
	}

	public void setDaTrouble(DaTrouble daTrouble) {
		this.daTrouble = daTrouble;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
}
