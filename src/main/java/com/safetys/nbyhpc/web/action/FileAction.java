package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaFile;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallFile;
import com.safetys.nbyhpc.facade.iface.FileFacadeIface;
import com.safetys.nbyhpc.facade.iface.RollcallCompanyFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class FileAction extends DaAppAction{

	/**
	 * 会议记录、治理方案
	 */
	private static final long serialVersionUID = 2745553488777586373L;
	
	private DaFile file;
	
	private FileFacadeIface fileFacadeIface;
	
	private RollcallCompanyFacadeIface rollcallCompanyFacadeIface;
	
	private Pagination pagination;
	
	private List<DaRollcallCompany> rollcallCompanies;
	
	private List<DaRollcallFile> rollcallFiles;
	
	private DaCompany company;
	
	private DaRollcallCompany rollcallCompany;
	
	private DaRollcallFile rollcallFile;
	
	private int type;


	public DaRollcallFile getRollcallFile() {
		return rollcallFile;
	}

	public void setRollcallFile(DaRollcallFile rollcallFile) {
		this.rollcallFile = rollcallFile;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setRollcallCompanyFacadeIface(
			RollcallCompanyFacadeIface rollcallCompanyFacadeIface) {
		this.rollcallCompanyFacadeIface = rollcallCompanyFacadeIface;
	}

	public void setFile(DaFile file) {
		this.file = file;
	}

	public String loadFile(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			rollcallCompanies=rollcallCompanyFacadeIface.loadRollcallCompaniesOfLevel(company,pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String loadFileList(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try{
			rollcallFiles=fileFacadeIface.loadFileList(pagination, file,rollcallCompany,type);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String createFile(){
		try {
			setUploadRealPath();
			if(rollcallFile==null){
				rollcallFile=new DaRollcallFile();
			}
			rollcallFile.setUserId(getUserId());
			rollcallFile.setType(type);
			file.setUserId(getUserId());
			String error=fileFacadeIface.createFile(file,rollcallFile,rollcallCompany);
			try {
				if (error != null) {
					Integer.parseInt(error);
				}
			} catch (Exception e) {
				setMessageKey(error);
				return MESSAGE;
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createFileInit(){
		try{
			if(null!=file){
				file=fileFacadeIface.loadFile(file);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateFile(){
		setUploadRealPath();
		file.setUserId(getUserId());
		try{
			String error=fileFacadeIface.updateFile(file);
			try {
				if (error != null) {
					Integer.parseInt(error);
				}
			} catch (Exception e) {
				setMessageKey(error);
				return MESSAGE;
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteFile(){
		try{
			file=fileFacadeIface.loadFile(file);
			file.setDeleted(true);
			fileFacadeIface.deleteFile(file);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public void setFileFacadeIface(FileFacadeIface fileFacadeIface) {
		this.fileFacadeIface = fileFacadeIface;
	}

	public List<DaRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}

	public void setRollcallCompanies(List<DaRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public DaRollcallCompany getRollcallCompany() {
		return rollcallCompany;
	}

	public void setRollcallCompany(DaRollcallCompany rollcallCompany) {
		this.rollcallCompany = rollcallCompany;
	}

	public DaFile getFile() {
		return file;
	}

	public List<DaRollcallFile> getRollcallFiles() {
		return rollcallFiles;
	}

	public void setRollcallFiles(List<DaRollcallFile> rollcallFiles) {
		this.rollcallFiles = rollcallFiles;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
