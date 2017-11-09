package com.safetys.nbyhpc.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.safetys.nbyhpc.domain.model.CommonFile;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class DaDangerImageAction extends DaAppAction {

	private static final long serialVersionUID = -6008764569287262831L;

	private static final String REDIRECT = "redirect";
	
	private DaDangerImage entity;
	
	private DaNomalDanger nomalDanger;
	
	private DaDangerImageFacadeIface daDangerImageFacadeIface;
	
	private List<DaDangerImage> daDangerImageList;
	
	private File file;
	
	private String name;
	
	public String showImageWin() {
		daDangerImageList = daDangerImageFacadeIface.findByNormalDanger(nomalDanger);
		return SUCCESS;
	}
	
	public void upload() {
		List<CommonFile> list = new ArrayList<CommonFile>();
		CommonFile cf = new CommonFile();
		cf.setFile(file);
		cf.setFileFileName(name);
		list.add(cf);
		nomalDanger.setListFile(list);
		daDangerImageFacadeIface.createByNormalDanger(nomalDanger);
		try {
			getResponse().getWriter().print(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delete() throws IOException {
		daDangerImageFacadeIface.delete(entity);
		getResponse().getWriter().print(true);
	}

	public DaDangerImage getEntity() {
		return entity;
	}

	public void setEntity(DaDangerImage entity) {
		this.entity = entity;
	}

	public void setDaDangerImageFacadeIface(DaDangerImageFacadeIface daDangerImageFacadeIface) {
		this.daDangerImageFacadeIface = daDangerImageFacadeIface;
	}

	public DaNomalDanger getNomalDanger() {
		return nomalDanger;
	}

	public void setNomalDanger(DaNomalDanger nomalDanger) {
		this.nomalDanger = nomalDanger;
	}

	public List<DaDangerImage> getDaDangerImageList() {
		return daDangerImageList;
	}

	public void setDaDangerImageList(List<DaDangerImage> daDangerImageList) {
		this.daDangerImageList = daDangerImageList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
