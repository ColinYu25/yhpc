package com.safetys.nbyhpc.domain.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 会议记录、治理方案实体
 */
public class DaPipeFile extends DaBaseModel{

	
	private static final long serialVersionUID = -5793121656491306585L;

	private String fileName;
	
	private Long userId;
	
	private String filePath;
	
	private  String fileRealPath;
	
	private File file;
	
	private String fileFactName;
	
	private Integer fileType;
	
	private String fileFileName;

	private Set<DaPipeRollcallFile> daPipeRollcallFiles = new HashSet<DaPipeRollcallFile>(0);
	
	public DaPipeFile(){
		
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

	public Set<DaPipeRollcallFile> getDaPipeRollcallFiles() {
		return daPipeRollcallFiles;
	}

	public void setDaPipeRollcallFiles(Set<DaPipeRollcallFile> daPipeRollcallFiles) {
		this.daPipeRollcallFiles = daPipeRollcallFiles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
