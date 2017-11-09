package com.safetys.nbyhpc.domain.model.base;

import java.util.List;

import com.safetys.framework.domain.model.base.BaseModel;
import com.safetys.nbyhpc.domain.model.CommonFile;
import com.safetys.nbyhpc.domain.model.DaDangerImage;

public class DaBaseModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7704077276579079753L;

	private List<CommonFile> listFile;
	
	private List<DaDangerImage> daDangerImageList;
	
	public DaBaseModel () {
		
	}
	
	public DaBaseModel (long id) {
		super.setId(id);
	}
	
	public List<CommonFile> getListFile() {
		return listFile;
	}

	public void setListFile(List<CommonFile> listFile) {
		this.listFile = listFile;
	}

	public List<DaDangerImage> getDaDangerImageList() {
		return daDangerImageList;
	}

	public void setDaDangerImageList(List<DaDangerImage> daDangerImageList) {
		this.daDangerImageList = daDangerImageList;
	}
	
}
