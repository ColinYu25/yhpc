package com.safetys.nbyhpc.domain.model;

import java.io.File;
import java.io.Serializable;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
import com.safetys.nbyhpc.util.FileUpload;

public class DaImageFile extends DaBaseModel implements Serializable {

	private static final long serialVersionUID = -702268827508683372L;

	/**
	 * 文件实体
	 */

	private String num;//文件编号
	
	private String name;//文件名
	
	private File file;// 文件对象
	
	private String fileFileName;//上传过程中得到的文件名
	
	private String fileContentType;//上传过程中得到的文件类型
	
	private String path;//文件路径
	
	private Long size;//文件大小
	
	private String type;//文件类型
	
	private String fileOriginalName;//文件原名称

	private boolean fileDeleted;// 是否删除文件
	
	private String fileDescription; //图片说明
	
	private DaCompany daCompany; 
	
	private Long userId;
	
	private String urlPath;
	
	private String[] allowType = FileUpload.ALLOW_TYPE_JPG;
	
	public DaImageFile(){
		
	}
	public DaImageFile(Long id){
		super(id);
	}

	public String[] getAllowType() {
		return allowType;
	}

	public void setAllowType(String[] allowType) {
		this.allowType = allowType;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileOriginalName() {
		return fileOriginalName;
	}

	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public boolean isFileDeleted() {
		return fileDeleted;
	}
	
	public void setFileDeleted(boolean fileDeleted) {
		this.fileDeleted = fileDeleted;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public DaCompany getDaCompany() {
		return daCompany;
	}
	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
}
