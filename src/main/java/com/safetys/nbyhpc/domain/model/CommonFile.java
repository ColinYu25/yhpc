package com.safetys.nbyhpc.domain.model;

import java.io.File;
import java.io.Serializable;

public class CommonFile implements Serializable {

	private static final long serialVersionUID = 5191474007550885752L;

	private File file;
	
	private String fileFileName;
	
	private String path;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
