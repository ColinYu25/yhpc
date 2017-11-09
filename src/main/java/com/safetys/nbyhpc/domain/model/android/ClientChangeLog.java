package com.safetys.nbyhpc.domain.model.android;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.mobile.MBaseModel;
import com.safetys.nbyhpc.util.OsType;
import com.safetys.nbyhpc.util.OsUserType;

/**
 * 版本信息
 *
 */
public class ClientChangeLog extends MBaseModel {

	private static final long serialVersionUID = -7654406001577080772L;
	
	private String packageEnName;// 安装包英文名（原名）
	
	private String packageZhName;// 安装包中文名
	
	private String versionCode;// 版本号
	
	private Long versionNum;// 版本序号
	
	private Date publishDate;// 发布时间
	
	private String changeLog;// 发布日志
	
	private String os;// 支持客户端系统OsType.java
	
	private String type; //类型OsUserType.java
	
	private String fileInfo;
	
	private String orignalFileName;

	public String getPackageEnName() {
		return packageEnName;
	}

	public void setPackageEnName(String packageEnName) {
		this.packageEnName = packageEnName;
	}

	public String getPackageZhName() {
		return packageZhName;
	}

	public void setPackageZhName(String packageZhName) {
		this.packageZhName = packageZhName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public Long getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getOrignalFileName() {
		return orignalFileName;
	}

	public void setOrignalFileName(String orignalFileName) {
		this.orignalFileName = orignalFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getTypeText() {
		return OsUserType.valueOf(type).getText();
	}
	
	public String getOsTypeText() {
		return OsType.valueOf(os).getText();
	}
}
