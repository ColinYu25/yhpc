package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaFile;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeFile;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallFile;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallFile;
import com.safetys.nbyhpc.facade.iface.FileFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeFileFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeRollcallCompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.RollcallCompanyFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class PipeFileAction extends DaAppAction{

	/**
	 * 会议记录、治理方案
	 */
	private static final long serialVersionUID = 2745553488777586373L;
	
	private DaPipeFile file;
	
	private PipeFileFacadeIface pipeFileFacadeIface;
	
	private PipeRollcallCompanyFacadeIface pipeRollcallCompanyFacadeIface;
	
	private Pagination pagination;
	
	private List<DaPipeRollcallCompany> rollcallCompanies;
	
	private List<DaPipeRollcallFile> rollcallFiles;
	
	private DaPipeDanger danger;
	
	private DaPipelineCompanyInfo pipeLine;
	
	private DaPipeRollcallCompany rollcallCompany;
	
	private DaPipeRollcallFile rollcallFile;
	
	private int type;



	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String loadFile(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			rollcallCompanies=pipeRollcallCompanyFacadeIface.loadRollcallCompaniesOfLevel(pipeLine,pagination, getUserDetail());
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
			rollcallFiles=pipeFileFacadeIface.loadFileList(pagination, file,rollcallCompany,type);
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
				rollcallFile=new DaPipeRollcallFile();
			}
			rollcallFile.setUserId(getUserId());
			rollcallFile.setType(type);
			file.setUserId(getUserId());
			String error=pipeFileFacadeIface.createFile(file,rollcallFile,rollcallCompany);
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
				file=pipeFileFacadeIface.loadFile(file);
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
			String error=pipeFileFacadeIface.updateFile(file);
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
			file=pipeFileFacadeIface.loadFile(file);
			file.setDeleted(true);
			pipeFileFacadeIface.deleteFile(file);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public List<DaPipeRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}

	public void setRollcallCompanies(List<DaPipeRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public List<DaPipeRollcallFile> getRollcallFiles() {
		return rollcallFiles;
	}

	public void setRollcallFiles(List<DaPipeRollcallFile> rollcallFiles) {
		this.rollcallFiles = rollcallFiles;
	}

	public DaPipeDanger getDanger() {
		return danger;
	}

	public void setDanger(DaPipeDanger danger) {
		this.danger = danger;
	}

	public DaPipeRollcallCompany getRollcallCompany() {
		return rollcallCompany;
	}

	public void setRollcallCompany(DaPipeRollcallCompany rollcallCompany) {
		this.rollcallCompany = rollcallCompany;
	}

	public DaPipeRollcallFile getRollcallFile() {
		return rollcallFile;
	}

	public void setRollcallFile(DaPipeRollcallFile rollcallFile) {
		this.rollcallFile = rollcallFile;
	}

	public void setPipeRollcallCompanyFacadeIface(
			PipeRollcallCompanyFacadeIface pipeRollcallCompanyFacadeIface) {
		this.pipeRollcallCompanyFacadeIface = pipeRollcallCompanyFacadeIface;
	}

	public DaPipeFile getFile() {
		return file;
	}

	public void setFile(DaPipeFile file) {
		this.file = file;
	}

	public void setPipeFileFacadeIface(PipeFileFacadeIface pipeFileFacadeIface) {
		this.pipeFileFacadeIface = pipeFileFacadeIface;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public DaPipelineCompanyInfo getPipeLine() {
		return pipeLine;
	}

	public void setPipeLine(DaPipelineCompanyInfo pipeLine) {
		this.pipeLine = pipeLine;
	}

}
