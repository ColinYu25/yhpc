package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallDefer;
import com.safetys.nbyhpc.domain.model.DaRollcallDeferFile;
import com.safetys.nbyhpc.facade.iface.RollcallDeferFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class RollcallDeferAction extends DaAppAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3944950415924883857L;

	private RollcallDeferFacadeIface rollcallDeferFacadeIface;
	
	private Pagination pagination;
	
	private List<DaRollcallCompany> rollcallCompanies;
	
	private List<DaRollcallDefer> rollcallDefers;
	
	private DaCompany company;
	
	private DaDanger danger;
	
	private DaRollcallCompany rollcallCompany;
	
	private DaRollcallDefer rollcallDefer;
	
	private DaRollcallDeferFile file;
	
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
				rollcallDefer=new DaRollcallDefer();
			}
			if(file==null){
				file=new DaRollcallDeferFile();
			}
			rollcallDefer.setUserId(getUserId());
			rollcallDefer.setDone(true);
			rollcallDefer.setIsAgree(2);
			rollcallDefer.setType(1);
			file.setUserId(getUserId());
			String error=rollcallDeferFacadeIface.createRollcallDeferAgree(rollcallDefer,file);
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
			rollcallDeferFacadeIface.updateRollcallDeferAgreeByHql(rollcallDefer);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteRollcallDeferAgree(){
		try{
			rollcallDeferFacadeIface.deleteRollcallDefer(ids);
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
			rollcallDefers=rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
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
			rollcallDefers=rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createRollcallDeferAgreeInit(){
		try {
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			if(rollcallDefer==null){
				rollcallDefer=new DaRollcallDefer();
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
			rollcallDefer = rollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaRollcallCompany());
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String createRollcallDeferInit(){
		try {
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
			if (rollcallDefers.size() > 0) {
				rollcallDefer = rollcallDefers.get(0);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	//--------
	public String createRollcallDeferCha(){
		try {
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
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
			
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
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
			rollcallDefers=rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
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
			rollcallDefers=rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgreeCha(){
		try {
			rollcallDefer = rollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaRollcallCompany());
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferAgreeForPassCha(){
		try {
			rollcallDefer = rollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaRollcallCompany());
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	//----
	public String updateRollcallDeferBySub() {
		try {
			rollcallDeferFacadeIface.updateRollcallDeferBySub(ids);
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
			rollcallDeferFacadeIface.createRollcallDefer(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDefer(){
		try {
			rollcallDefer = rollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaRollcallCompany());
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadRollcallDeferAgreeForPass(){
		try {
			rollcallDefer = rollcallDeferFacadeIface.loadRollcallDefer(rollcallDefer);
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallDefer.getDaRollcallCompany());
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateRollcallDeferAgreeForPass() {
		try {
			rollcallDefer.setType(0);
			rollcallDeferFacadeIface.updateRollcallDeferForPassByHql(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallDeferForPass(){
		try {
			
			rollcallCompany = rollcallDeferFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallDeferFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallDeferFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			rollcallDefers = rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,null,getUserDetail(),0);
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
			rollcallDeferFacadeIface.updateRollcallDeferForPassByHql(rollcallDefer);
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
			rollcallDeferFacadeIface.updateRollcallDefer(rollcallDefer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteRollcallDefer(){
		try {
			rollcallDeferFacadeIface.deleteRollcallDefer(ids);
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
			rollcallDefers=rollcallDeferFacadeIface.loadRollcallDefers(rollcallCompany,pagination,getUserDetail(),type);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
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
	public DaRollcallDefer getRollcallDefer() {
		return rollcallDefer;
	}
	public void setRollcallDefer(DaRollcallDefer rollcallDefer) {
		this.rollcallDefer = rollcallDefer;
	}
	public List<DaRollcallDefer> getRollcallDefers() {
		return rollcallDefers;
	}
	public void setRollcallDefers(List<DaRollcallDefer> rollcallDefers) {
		this.rollcallDefers = rollcallDefers;
	}
	public void setRollcallDeferFacadeIface(
			RollcallDeferFacadeIface rollcallDeferFacadeIface) {
		this.rollcallDeferFacadeIface = rollcallDeferFacadeIface;
	}

	public DaDanger getDanger() {
		return danger;
	}

	public void setDanger(DaDanger danger) {
		this.danger = danger;
	}

	public DaRollcallDeferFile getFile() {
		return file;
	}

	public void setFile(DaRollcallDeferFile file) {
		this.file = file;
	}
	

}
