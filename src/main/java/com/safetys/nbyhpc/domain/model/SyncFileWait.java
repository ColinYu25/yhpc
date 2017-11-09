package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;


public class SyncFileWait extends DaBaseModel{

	private static final long serialVersionUID = 3022894564387221332L;
	
	private String filePath;
	
	private String realFilePath;//REAL_FILE_PATH
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getRealFilePath() {
		return realFilePath;
	}
	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}
	
	
	
}
