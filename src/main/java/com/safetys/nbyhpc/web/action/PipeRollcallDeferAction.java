package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDefer;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDeferFile;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.facade.iface.PipeRollcallDeferFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class PipeRollcallDeferAction extends DaAppAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3944950415924883857L;

	private PipeRollcallDeferFacadeIface pipeRollcallDeferFacadeIface;
	
	private Pagination pagination;
	
	private List<DaPipeRollcallCompany> rollcallCompanies;
	
	private List<DaPipeRollcallDefer> rollcallDefers;
	
	private DaPipelineCompanyInfo pipelineCompanyInfo;// 企业单位
	
	private DaPipeDanger danger;
	
	private DaPipeRollcallCompany rollcallCompany;
	
	private DaPipeRollcallDefer rollcallDefer;
	
	private DaPipeRollcallDeferFile file;
	
	private String ids;
	

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String createRollcallDeferAgree(){
		try {
			setUploadRealPath();
			if(rollcallDefer==null){
				rollcallDefer=new DaPipeRollcallDefer();
			}
			if(file==null){
				file=new DaPipeRollcallDeferFile();
			}
			rollcallDefer.setUserId(getUserId());
			rollcallDefer.setDone(true);
			rollcallDefer.setIsAgree(2);
			rollcallDefer.setType(1);
			file.setUserId(getUserId());
			String error=pipeRollcallDeferFacadeIface.createRollcallDeferAgree(rollcallDefer,file);
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
	
	public String updateRollcallDeferAgree(){
//		setUploadRealPath();
		rollcallDefer.setUserId(getUserId());
		rollcallDefer.setType(1);
		try{
//			String error=rollcallDeferFacadeIface.updateRollcallDeferAgree(file);
//			try {
//				if (error != null) {
//					Integer.parseInt(error);
//				}
//			} catch (Exception e) {
//				setMessageKey(error);
//				return MESSAGE;
//			}
			pipeRollcallDeferFacadeIface.updateRollcallDeferAgreeByHql(rollcallDefer);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteRollcallDeferAgree(){
		try{
			pipeRollcallDeferFacadeIface.deleteRollcallDefer(ids);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgrees(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			int type=1;
			rollcallDefers=pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgreePasses(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			int type=1;
			rollcallDefers=pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createRollcallDeferAgreeInit(){
		try {
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			if(rollcallDefer==null){
				rollcallDefer=new DaPipeRollcallDefer();
			}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
	        String mDateTime=formatter.format(cal.getTime());
			rollcallDefer.setTime(mDateTime);
//			rollcallDefers = rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),1);
//			if (rollcallDefers.size() > 0) {
//				rollcallDefer = rollcallDefers.get(0);
//			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgree(){
		try {
			rollcallDefer = pipeRollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaPipeRollcallCompany());
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String createRollcallDeferInit(){
		try {
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
			if (rollcallDefers.size() > 0) {
				rollcallDefer = rollcallDefers.get(0);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createRollcallDeferCha(){
		try {
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
			if (rollcallDefers.size() > 0) {
				rollcallDefer = rollcallDefers.get(0);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadRollcallDeferForPassCha(){
		try {
			
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
			if (rollcallDefers.size() > 0) {
				rollcallDefer = rollcallDefers.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadRollcallDeferAgreesCha(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			int type=1;
			rollcallDefers=pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgreePassesCha(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			int type=1;
			rollcallDefers=pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgreeCha(){
		try {
			rollcallDefer = pipeRollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaPipeRollcallCompany());
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgreeForPassCha(){
		try {
			rollcallDefer = pipeRollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaPipeRollcallCompany());
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	//----
	public String updateRollcallDeferBySub() {
		try {
			pipeRollcallDeferFacadeIface.updateRollcallDeferBySub(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createRollcallDefer() {
		try {
			rollcallDefer.setUserId(getUserId());
			rollcallDefer.setDone(true);
			rollcallDefer.setIsAgree(2);
			rollcallDefer.setType(0);
			pipeRollcallDeferFacadeIface.createRollcallDefer(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDefer(){
		try {
			rollcallDefer = pipeRollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaPipeRollcallCompany());
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadRollcallDeferAgreeForPass(){
		try {
			rollcallDefer = pipeRollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaPipeRollcallCompany());
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateRollcallDeferAgreeForPass() {
		try {
			rollcallDefer.setType(0);
			pipeRollcallDeferFacadeIface.updateRollcallDeferForPassByHql(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferForPass(){
		try {
			
			rollcallCompany = pipeRollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = pipeRollcallDeferFacadeIface.loadDanger(new DaPipeDanger(rollcallCompany.getDaPipeDanger().getId()));
			//company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
			if (rollcallDefers.size() > 0) {
				rollcallDefer = rollcallDefers.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateRollcallDeferForPass() {
		try {
			rollcallDefer.setType(0);
			pipeRollcallDeferFacadeIface.updateRollcallDeferForPassByHql(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateRollcallDefer(){
		try {
			rollcallDefer.setType(0);
			rollcallDefer.setUserId(getUserId());
			pipeRollcallDeferFacadeIface.updateRollcallDefer(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteRollcallDefer(){
		try {
			pipeRollcallDeferFacadeIface.deleteRollcallDefer(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	

	public String loadRollcallDefers(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			int type=0;
			rollcallDefers=pipeRollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public PipeRollcallDeferFacadeIface getRollcallDeferFacadeIface() {
		return pipeRollcallDeferFacadeIface;
	}

	public void setPipeRollcallDeferFacadeIface(
			PipeRollcallDeferFacadeIface pipeRollcallDeferFacadeIface) {
		this.pipeRollcallDeferFacadeIface = pipeRollcallDeferFacadeIface;
	}

	public List<DaPipeRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}

	public void setRollcallCompanies(List<DaPipeRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public List<DaPipeRollcallDefer> getRollcallDefers() {
		return rollcallDefers;
	}

	public void setRollcallDefers(List<DaPipeRollcallDefer> rollcallDefers) {
		this.rollcallDefers = rollcallDefers;
	}

	public DaPipelineCompanyInfo getPipelineCompanyInfo() {
		return pipelineCompanyInfo;
	}

	public void setPipelineCompanyInfo(DaPipelineCompanyInfo pipelineCompanyInfo) {
		this.pipelineCompanyInfo = pipelineCompanyInfo;
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

	public DaPipeRollcallDefer getRollcallDefer() {
		return rollcallDefer;
	}

	public void setRollcallDefer(DaPipeRollcallDefer rollcallDefer) {
		this.rollcallDefer = rollcallDefer;
	}

	public DaPipeRollcallDeferFile getFile() {
		return file;
	}

	public void setFile(DaPipeRollcallDeferFile file) {
		this.file = file;
	}

}
