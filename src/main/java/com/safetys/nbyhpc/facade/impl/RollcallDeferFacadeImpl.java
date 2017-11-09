package com.safetys.nbyhpc.facade.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallDefer;
import com.safetys.nbyhpc.domain.model.DaRollcallDeferFile;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallDeferFilePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallDeferPersistenceIface;
import com.safetys.nbyhpc.facade.iface.RollcallDeferFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @(#) RollcallDeferFacadeImpl.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public class RollcallDeferFacadeImpl implements RollcallDeferFacadeIface {

	private RollcallDeferPersistenceIface rollcallDeferPersistenceIface;
	
	private DangerPersistenceIface dangerPersistenceIface;
	
	private CompanyPersistenceIface companyPersistenceIface;
	
	private RollcallCompanyPersistenceIface rollcallCompanyPersistenceIface;
	
	private RollcallDeferFilePersistenceIface rollcallDeferFilePersistenceIface;
	
	public String createRollcallDeferAgree(DaRollcallDefer rollcallDefer,DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		String l = null;
		l=rollcallDeferPersistenceIface.createRollcallDefer(rollcallDefer).toString();
//		Map map = FileUpload.UploadFile(rollcallDeferFile.getFile(), rollcallDeferFile.getFileFileName(),
//				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD);
//		String error = map.get("error").toString();
//		if (error != null && !"".equals(error.trim())) {
//			return error;
//		}
//		rollcallDeferFile.setFileRealPath(map.get("filePath")+"");
//		rollcallDeferFile.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
//		rollcallDeferFile.setDaRollcallDefer(rollcallDefer);
//		l=rollcallDeferFilePersistenceIface.createRollcallDeferFile(rollcallDeferFile).toString();
		return  l;
	}
	
	public String updateRollcallDeferAgree(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
//		Map map = FileUpload.UploadFile(rollcallDeferFile.getFile(), rollcallDeferFile.getFileFileName(),
//				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD);
//		String error = map.get("error").toString();
//		if (error != null && !"".equals(error.trim())) {
//			return error;
//		}
//		rollcallDeferFile.setFileRealPath(map.get("filePath")+"");
//		rollcallDeferFile.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
		rollcallDeferFilePersistenceIface.updateRollcallDeferFile(rollcallDeferFile);
		return rollcallDeferFile.getId().toString();
	}
	
	public void updateRollcallDeferAgreeByHql(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String companyApplyTime = "";
		if(rollcallDefer.getCompanyApplyTime() != null){
			companyApplyTime = "companyApplyTime = to_date('"+formatter.format(rollcallDefer.getCompanyApplyTime())+"','yyyy-MM-dd')";
		}else{
			companyApplyTime = "companyApplyTime = null,";
		}
		String hql = "update DaRollcallDefer set govment = '"+rollcallDefer.getGovment()+"' ," 
											+ companyApplyTime
											+ " where id = "+rollcallDefer.getId();
		rollcallCompanyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public void updateRollcallDeferBySub(String id) throws ApplicationAccessException {
		String hql = "update DaRollcallDefer set done = true  where id in ("+id+")";
		rollcallCompanyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public void updateRollcallDeferForPassByHql(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String unitTime = "";
		String signinTime = "";
		if(rollcallDefer.getRollcallUnitTime() != null){
			unitTime = "rollcallUnitTime = to_date('"+formatter.format(rollcallDefer.getRollcallUnitTime())+"','yyyy-MM-dd'),";
		}else{
			unitTime = "rollcallUnitTime = null,";
		}
		if(rollcallDefer.getSigninTime() != null){
			signinTime = "signinTime = to_date('"+formatter.format(rollcallDefer.getSigninTime())+"','yyyy-MM-dd'),";
		}else{
			signinTime = "signinTime = null,";
		}
		String hql = "update DaRollcallDefer set isAgree = "+rollcallDefer.getIsAgree()+" ," 
											   + "remark = '"+rollcallDefer.getRemark()+"',"
											   + unitTime
											   + "sendoffMan = '"+rollcallDefer.getSendoffMan()+"',"
											   + "signinMan = '"+rollcallDefer.getSigninMan()+"',"
											   + signinTime
											   + "signatory = '"+rollcallDefer.getSignatory()+"' where id = "+rollcallDefer.getId();
		rollcallCompanyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public DaRollcallCompany loadRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException{
		return rollcallCompanyPersistenceIface.loadRollcallCompany(rollcallCompany);
	}
	
	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException{
		return dangerPersistenceIface.loadDanger(danger);
	}
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}
	
	public Long createRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return rollcallDeferPersistenceIface.createRollcallDefer(rollcallDefer);
	}

	public DaRollcallDefer loadRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return rollcallDeferPersistenceIface.loadRollcallDefer(rollcallDefer);
	}

	public List<DaRollcallDefer> loadRollcallDefers(DaRollcallCompany daRollcallCompany, Pagination pagination,UserDetailWrapper userDetail,int type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRollcallDefer.class, "drd");
		if (daRollcallCompany != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("drd.daRollcallCompany.id", daRollcallCompany.getId()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("drd.type",type));
		if(userDetail != null){
			if(userDetail.getUserIndustry() != null && !Nbyhpc.USER_INDUSTRY_COMPANY.equals(userDetail.getUserIndustry())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("drd.done",true));
			}
		}
		return rollcallDeferPersistenceIface.loadRollcallDefers(detachedCriteriaProxy, pagination);
	}
	

	public void updateRollcallDefer(DaRollcallDefer rollcallDefer)
			throws ApplicationAccessException {
		DaRollcallDefer oldRollcallDefer = loadRollcallDefer(rollcallDefer);
		rollcallDefer.setCreateTime(oldRollcallDefer.getCreateTime());
		rollcallDeferPersistenceIface.mergeRollcallDefer(rollcallDefer);
	}

	public void deleteRollcallDefer(String id)throws ApplicationAccessException {
		for (int i = 0; i < id.split(",").length; i++) {
			rollcallDeferPersistenceIface.deleteRollcallDefer(new DaRollcallDefer(Long.parseLong(id)));
		}
	}
	
	public void setRollcallDeferPersistenceIface(
			RollcallDeferPersistenceIface rollcallDeferPersistenceIface) {
		this.rollcallDeferPersistenceIface = rollcallDeferPersistenceIface;
	}

	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setDangerPersistenceIface(
			DangerPersistenceIface dangerPersistenceIface) {
		this.dangerPersistenceIface = dangerPersistenceIface;
	}

	public void setRollcallCompanyPersistenceIface(
			RollcallCompanyPersistenceIface rollcallCompanyPersistenceIface) {
		this.rollcallCompanyPersistenceIface = rollcallCompanyPersistenceIface;
	}

	public void setRollcallDeferFilePersistenceIface(
			RollcallDeferFilePersistenceIface rollcallDeferFilePersistenceIface) {
		this.rollcallDeferFilePersistenceIface = rollcallDeferFilePersistenceIface;
	}

}
