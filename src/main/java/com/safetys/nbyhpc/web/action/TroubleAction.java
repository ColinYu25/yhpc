package com.safetys.nbyhpc.web.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaTrouble;
import com.safetys.nbyhpc.domain.model.DaTroubleCompany;
import com.safetys.nbyhpc.domain.model.DaTroubleFile;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.facade.iface.TroubleFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
public class TroubleAction extends DaAppAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5888452403543929497L;

	private TroubleFacadeIface troubleFacadeIface;
	
	private List<DaTroubleFile> troubleFiles;
	
	private List<DaTroubleCompany> troubleCompanies;
	
	private List<DaTrouble> troubles;
	
	private List<FkEnum> enums;
	
	private List<DaDept> deptes;
	
	private List<DaDept> notBackDeptes;
	
	private List<DaDept> notResultDeptes;
	
	private List<DaIndustryParameter> tradeTypes;// 行业集合
	
	private DaTroubleCompany troubleCompany;
	
	private FkEnum fkEnum;
	
	private List<FkArea> areas;
	
	private Statistic stDown;
	
	private Statistic stUp;
	
	private DaDept dept;
	
	private DaDept faDept;
	
	private DaTrouble trouble;
	
	private DaTroubleFile troubleFile;
	
	private Pagination pagination;//分页对象
	
	private String ids;//序号的集合，针对类似于批量删除操作
	
	
	public String loadCompanies(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = troubleFacadeIface.loadTradeTypesForCompany();
//			companies = troubleFacadeIface.loadCompanies(company,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByCopy(){
		try {
			enums = troubleFacadeIface.loadEnums(getUserDetail());
//			company = troubleFacadeIface.loadCompany(company);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByCue(){
		return SUCCESS;
	}
	
	
	public String createTrouble(){
		try {
			setUploadRealPath();
			if(trouble == null)
				trouble = new DaTrouble();
			trouble.setUserId(getUserId());
			if(dept == null)
				dept = new DaDept();
        	dept.setSubmitTime(new Date());
        	dept.setSubmit(true);
			dept.setUserId(getUserId());
			dept.setFirstArea(getUserDetail().getFirstArea());
			dept.setSecondArea(getUserDetail().getSecondArea());
			dept.setThirdArea(getUserDetail().getThirdArea());
			dept.setFindDeptTrade(getUserDetail().getUserIndustry());
			dept.setBack(false);
			dept.setResult(false);
			if(dept.getTroubleCopyType()==4)
				dept.setReceiptTime(new Date());
			if(dept.getTroubleCopyType()==1)
				dept.setMostlyCompanyArea(getUserDetail().getSecondArea());
			String error = troubleFacadeIface.createTroubleDept(troubleCompany,trouble, dept, troubleFiles);
			try {
				if (error != null) {
					Integer.parseInt(error);
				}
			} catch (Exception e) {
				setMessageKey(error);
				return MESSAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleByDownOrUp(){
		try {
			setUploadRealPath();
			if(trouble == null)
				trouble = new DaTrouble();
			trouble.setUserId(getUserId());
			if(dept == null)
				dept = new DaDept();
        	dept.setSubmitTime(new Date());
        	dept.setSubmit(true);
			dept.setUserId(getUserId());
			dept.setFirstArea(getUserDetail().getFirstArea());
			dept.setSecondArea(getUserDetail().getSecondArea());
			dept.setThirdArea(getUserDetail().getThirdArea());
			dept.setFindDeptTrade(getUserDetail().getUserIndustry());
			dept.setBack(false);
			dept.setResult(false);
			if(dept.getTroubleCopyType()==4)
				dept.setReceiptTime(new Date());
			if(dept.getTroubleCopyType()==1)
				dept.setMostlyCompanyArea(getUserDetail().getSecondArea());
			String error = troubleFacadeIface.createTroubleDept(troubleCompany,trouble, dept, troubleFiles);
			try {
				if (error != null) {
					Integer.parseInt(error);
				}
			} catch (Exception e) {
				setMessageKey(error);
				return MESSAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateTrouble(){
		try {
			if(trouble == null)
				trouble = new DaTrouble();
			trouble.setUserId(getUserId());
//			trouble.setDaCompanyPass(new DaCompanyPass(company.getId()));
			if(dept == null)
				dept = new DaDept();
			dept.setUserId(getUserId());
			dept.setFirstArea(getUserDetail().getFirstArea());
			dept.setSecondArea(getUserDetail().getSecondArea());
			dept.setThirdArea(getUserDetail().getThirdArea());
			troubleFacadeIface.updateTroubleDept(trouble, dept, troubleFiles);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleByDept(){
		try {
			enums = troubleFacadeIface.loadEnums(getUserDetail());
//			company = troubleFacadeIface.loadCompany(company);
			dept = troubleFacadeIface.loadDept(dept);
			trouble = troubleFacadeIface.loadTrouble(dept.getDaTrouble());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleByDeptPrint(){
		try {
			enums = troubleFacadeIface.loadAllEnums(getUserDetail());
			fkEnum = troubleFacadeIface.loadEnum(getUserDetail());
			dept = troubleFacadeIface.loadDept(dept);
			trouble = troubleFacadeIface.loadTrouble(dept.getDaTrouble());
			troubleFiles = troubleFacadeIface.loadTroubleFilesByTrouble(trouble,null);
			if(troubleFacadeIface.loadArea(trouble.getFirstArea()) != null)
				trouble.setFirstAreaName(troubleFacadeIface.loadArea(trouble.getFirstArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getSecondArea()) != null)
				trouble.setSecondAreaName(troubleFacadeIface.loadArea(trouble.getSecondArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getThirdArea()) != null)
				trouble.setThirdAreaName(troubleFacadeIface.loadArea(trouble.getThirdArea()).getAreaName());
			if(troubleFacadeIface.loadArea(dept.getMostlyCompanyArea()) != null)
				dept.setMostlyCompanyAreaName(troubleFacadeIface.loadArea(dept.getMostlyCompanyArea()).getAreaName());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleByBackPrint(){
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setTroubleCopyType(4);
			enums = troubleFacadeIface.loadAllEnums(getUserDetail());
			fkEnum = troubleFacadeIface.loadEnum(getUserDetail());
			dept = troubleFacadeIface.loadDeptById(dept);
			trouble = troubleFacadeIface.loadTrouble(dept.getDaTrouble());
			troubleFiles = troubleFacadeIface.loadTroubleFilesByTrouble(trouble,null);
			if(troubleFacadeIface.loadArea(trouble.getFirstArea()) != null)
				trouble.setFirstAreaName(troubleFacadeIface.loadArea(trouble.getFirstArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getSecondArea()) != null)
				trouble.setSecondAreaName(troubleFacadeIface.loadArea(trouble.getSecondArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getThirdArea()) != null)
				trouble.setThirdAreaName(troubleFacadeIface.loadArea(trouble.getThirdArea()).getAreaName());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleByResultPrint(){
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setTroubleCopyType(5);
			enums = troubleFacadeIface.loadAllEnums(getUserDetail());
			fkEnum = troubleFacadeIface.loadEnum(getUserDetail());
			dept = troubleFacadeIface.loadDeptById(dept);
			trouble = troubleFacadeIface.loadTrouble(dept.getDaTrouble());
			troubleFiles = troubleFacadeIface.loadTroubleFilesByTrouble(trouble,null);
			if(troubleFacadeIface.loadArea(trouble.getFirstArea()) != null)
				trouble.setFirstAreaName(troubleFacadeIface.loadArea(trouble.getFirstArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getSecondArea()) != null)
				trouble.setSecondAreaName(troubleFacadeIface.loadArea(trouble.getSecondArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getThirdArea()) != null)
				trouble.setThirdAreaName(troubleFacadeIface.loadArea(trouble.getThirdArea()).getAreaName());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByBack(){
		try {
			faDept = troubleFacadeIface.loadDept(dept);
			trouble = troubleFacadeIface.loadTrouble(faDept.getDaTrouble());
			troubleFiles = troubleFacadeIface.loadTroubleFilesByTrouble(trouble,null);
			if(troubleFacadeIface.loadArea(trouble.getFirstArea()) != null)
				trouble.setFirstAreaName(troubleFacadeIface.loadArea(trouble.getFirstArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getSecondArea()) != null)
				trouble.setSecondAreaName(troubleFacadeIface.loadArea(trouble.getSecondArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getThirdArea()) != null)
				trouble.setThirdAreaName(troubleFacadeIface.loadArea(trouble.getThirdArea()).getAreaName());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByResult(){
		try {
			faDept = troubleFacadeIface.loadDept(dept);
			trouble = troubleFacadeIface.loadTrouble(faDept.getDaTrouble());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByDownOrUp(){
		try {
			fkEnum = troubleFacadeIface.loadEnum(getUserDetail());
			areas = troubleFacadeIface.loadSecondAreas();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByDownOrUpBack(){
		try {
			fkEnum = troubleFacadeIface.loadEnum(getUserDetail());
//			company = troubleFacadeIface.loadCompany(company);
			trouble = troubleFacadeIface.loadTrouble(dept.getDaTrouble());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDown(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("0");
			dept.setTroubleCopyType(1);
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
			enums = troubleFacadeIface.loadAllEnums(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownNotBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setBack(false);
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
			enums = troubleFacadeIface.loadAllEnums(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("2");
			dept.setTroubleCopyType(1);
			dept.setBack(true);
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
			enums = troubleFacadeIface.loadEnums(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownNotResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("3");
			dept.setTroubleCopyType(1);
			dept.setResult(false);
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
			enums = troubleFacadeIface.loadEnums(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(1);
			dept.setResult(true);
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
			enums = troubleFacadeIface.loadEnums(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	

	public String loadTroubleUp(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("0");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleUpNotBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("1");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleUpBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("2");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleUpNotResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("3");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleUpResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("4");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleHome(){
		try {
			stDown = troubleFacadeIface.loadStatisticOfDownByUser(getUserDetail());
			stUp = troubleFacadeIface.loadStatisticOfUpByUser(getUserDetail());
			if(dept == null)
				dept =  new DaDept();
			dept.setType("1");
			notBackDeptes = troubleFacadeIface.loadTroubleUp(dept,trouble,null,getUserDetail());
			dept.setType("3");
			notResultDeptes = troubleFacadeIface.loadTroubleUp(dept,trouble,null,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadStatisticOfDownXmlByUser(){
		try {
			print(troubleFacadeIface.loadStatisticOfDownXmlByUser(getUserDetail()));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String loadStatisticOfUpXmlByUser(){
		try {
			print(troubleFacadeIface.loadStatisticOfUpXmlByUser(getUserDetail()));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void print(String content) throws IOException {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(content);
	}
	
	public String loadTroubleBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("1");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("3");
			deptes = troubleFacadeIface.loadTroubleUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownOrUp(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("3");
			deptes = troubleFacadeIface.loadTroubleDownOrUp(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleByDownOrUpOrBackOrResult(){
		try {
			if(trouble == null)
				trouble = new DaTrouble();
			trouble.setUserId(getUserId());
			if(dept == null)
				dept = new DaDept();
        	dept.setSubmitTime(new Date());
        	dept.setSubmit(true);
			dept.setUserId(getUserId());
			dept.setFirstArea(getUserDetail().getFirstArea());
			dept.setSecondArea(getUserDetail().getSecondArea());
			dept.setThirdArea(getUserDetail().getThirdArea());
			dept.setFindDeptTrade(getUserDetail().getUserIndustry());
			dept.setBack(false);
			dept.setResult(false);
			if(dept.getTroubleCopyType()==4)
				dept.setReceiptTime(new Date());
			troubleFacadeIface.createTroubleByDownOrUpOrBackOrResult(trouble, dept);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createTroubleInitByDownOrUpT(){
		try {
			faDept = troubleFacadeIface.loadDept(dept);
			trouble = troubleFacadeIface.loadTrouble(faDept.getDaTrouble());
			troubleFiles = troubleFacadeIface.loadTroubleFilesByTrouble(trouble,null);
			if(troubleFacadeIface.loadArea(trouble.getFirstArea()) != null)
				trouble.setFirstAreaName(troubleFacadeIface.loadArea(trouble.getFirstArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getSecondArea()) != null)
				trouble.setSecondAreaName(troubleFacadeIface.loadArea(trouble.getSecondArea()).getAreaName());
			if(troubleFacadeIface.loadArea(trouble.getThirdArea()) != null)
				trouble.setThirdAreaName(troubleFacadeIface.loadArea(trouble.getThirdArea()).getAreaName());
			fkEnum = troubleFacadeIface.loadEnum(getUserDetail());
			areas = troubleFacadeIface.loadSecondAreas();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadTroubleDownUp(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("0");
			if(getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0L){
				dept.setTroubleCopyType(3);
			}else{
				dept.setTroubleCopyType(2);
			}
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownUpNotBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("1");
			dept.setBack(false);
			if(getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0L){
				dept.setTroubleCopyType(3);
			}else{
				dept.setTroubleCopyType(2);
			}
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownUpBack(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("2");
			dept.setBack(true);
			if(getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0L){
				dept.setTroubleCopyType(3);
			}else{
				dept.setTroubleCopyType(2);
			}
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownUpNotResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("3");
			dept.setResult(false);
			if(getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0L){
				dept.setTroubleCopyType(3);
			}else{
				dept.setTroubleCopyType(2);
			}
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadTroubleDownUpResult(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(dept == null)
				dept =  new DaDept();
			dept.setType("4");
			dept.setResult(true);
			if(getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0L){
				dept.setTroubleCopyType(3);
			}else{
				dept.setTroubleCopyType(2);
			}
			deptes = troubleFacadeIface.loadTroubleDown(dept,trouble,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setTroubleFacadeIface(TroubleFacadeIface troubleFacadeIface) {
		this.troubleFacadeIface = troubleFacadeIface;
	}

	public DaDept getDept() {
		return dept;
	}

	public void setDept(DaDept dept) {
		this.dept = dept;
	}

	public DaTrouble getTrouble() {
		return trouble;
	}

	public void setTrouble(DaTrouble trouble) {
		this.trouble = trouble;
	}

	public DaTroubleFile getTroubleFile() {
		return troubleFile;
	}

	public void setTroubleFile(DaTroubleFile troubleFile) {
		this.troubleFile = troubleFile;
	}

	public List<DaTroubleFile> getTroubleFiles() {
		return troubleFiles;
	}

	public void setTroubleFiles(List<DaTroubleFile> troubleFiles) {
		this.troubleFiles = troubleFiles;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public List<FkEnum> getEnums() {
		return enums;
	}

	public void setEnums(List<FkEnum> enums) {
		this.enums = enums;
	}

	public FkEnum getFkEnum() {
		return fkEnum;
	}

	public void setFkEnum(FkEnum fkEnum) {
		this.fkEnum = fkEnum;
	}

	public List<DaTrouble> getTroubles() {
		return troubles;
	}

	public void setTroubles(List<DaTrouble> troubles) {
		this.troubles = troubles;
	}

	public List<DaTroubleCompany> getTroubleCompanies() {
		return troubleCompanies;
	}

	public void setTroubleCompanies(List<DaTroubleCompany> troubleCompanies) {
		this.troubleCompanies = troubleCompanies;
	}

	public DaTroubleCompany getTroubleCompany() {
		return troubleCompany;
	}

	public void setTroubleCompany(DaTroubleCompany troubleCompany) {
		this.troubleCompany = troubleCompany;
	}

	public List<DaDept> getDeptes() {
		return deptes;
	}

	public void setDeptes(List<DaDept> deptes) {
		this.deptes = deptes;
	}

	public DaDept getFaDept() {
		return faDept;
	}

	public void setFaDept(DaDept faDept) {
		this.faDept = faDept;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

	public Statistic getStDown() {
		return stDown;
	}

	public void setStDown(Statistic stDown) {
		this.stDown = stDown;
	}

	public Statistic getStUp() {
		return stUp;
	}

	public void setStUp(Statistic stUp) {
		this.stUp = stUp;
	}

	public List<DaDept> getNotBackDeptes() {
		return notBackDeptes;
	}

	public void setNotBackDeptes(List<DaDept> notBackDeptes) {
		this.notBackDeptes = notBackDeptes;
	}

	public List<DaDept> getNotResultDeptes() {
		return notResultDeptes;
	}

	public void setNotResultDeptes(List<DaDept> notResultDeptes) {
		this.notResultDeptes = notResultDeptes;
	}
	

}
