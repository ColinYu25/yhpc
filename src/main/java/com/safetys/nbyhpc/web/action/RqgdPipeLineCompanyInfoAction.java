package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;


/**
 * 燃气管道Action
 * @author qfc
 *
 */
public class RqgdPipeLineCompanyInfoAction extends PipeLineCompanyInfoAction{
	
	private List<FkArea> areas;

	@Override
	public String create() throws Exception {
		areas = pipeLineFacadeIface.loadAreadsByParentCode(Nbyhpc.AREA_CODE);
		return super.create();
	}

	@Override
	public String update() throws Exception {
		areas = pipeLineFacadeIface.loadAreadsByParentCode(Nbyhpc.AREA_CODE);
		return super.update();
	}

	@Override
	protected int getType() {
		return PipeLineFacadeIface.RQGD;
	}

	@Override
	public String getUrlPrefix(){
		return "rqCompany";
	}
	
	@Override
	public String getIncludeFile(){
		return RQ_MENU_NAME;
	}

	public List<FkArea> getAreas() {
		return areas;
	}
	
	
}

