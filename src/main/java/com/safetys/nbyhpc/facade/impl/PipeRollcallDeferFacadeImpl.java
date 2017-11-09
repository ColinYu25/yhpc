package com.safetys.nbyhpc.facade.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDefer;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDeferFile;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallDeferFilePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallDeferPersistenceIface;
import com.safetys.nbyhpc.facade.iface.PipeRollcallDeferFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * @(#) RollcallDeferFacadeImpl.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public class PipeRollcallDeferFacadeImpl implements PipeRollcallDeferFacadeIface {

	private PipeRollcallDeferPersistenceIface pipeRollcallDeferPersistenceIface;
	
	private PipeDangerPersistenceIface pipeDangerPersistenceIface;
	
	private BasePersistenceImpl persistenceImpl;
	
	private PipeRollcallCompanyPersistenceIface pipeRollcallCompanyPersistenceIface;
	
	private PipeRollcallDeferFilePersistenceIface rollcallDeferFilePersistenceIface;
	
	public String createRollcallDeferAgree(DaPipeRollcallDefer rollcallDefer,DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		String l = null;
		l=pipeRollcallDeferPersistenceIface.createRollcallDefer(rollcallDefer).toString();
//		Map map = FileUpload.UploadFile(rollcallDeferFile.getFile(), rollcallDeferFile.getFileFileName(),
//				FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD);
//		String error = map.get("error").toString();
//		if (error != null && !"".equals(error.trim())) {
//			return error;
//		}
//		rollcallDeferFile.setFileRealPath(map.get("filePath")+"");
//		rollcallDeferFile.setFileFactName((map.get("filePath")+"").substring(FileUpload.UPLOAD_PATH_EMERGENCY.length()+1));
//		rollcallDeferFile.setDaPipeRollcallDefer(rollcallDefer);
//		l=rollcallDeferFilePersistenceIface.createRollcallDeferFile(rollcallDeferFile).toString();
		return  l;
	}
	
	public String updateRollcallDeferAgree(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
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
	
	public void updateRollcallDeferAgreeByHql(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String companyApplyTime = "";
		if(rollcallDefer.getCompanyApplyTime() != null){
			companyApplyTime = "companyApplyTime = to_date('"+formatter.format(rollcallDefer.getCompanyApplyTime())+"','yyyy-MM-dd')";
		}else{
			companyApplyTime = "companyApplyTime = null,";
		}
		String hql = "update DaPipeRollcallDefer set govment = '"+rollcallDefer.getGovment()+"' ," 
											+ companyApplyTime
											+ " where id = "+rollcallDefer.getId();
		pipeRollcallCompanyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public void updateRollcallDeferBySub(String id) throws ApplicationAccessException {
		String hql = "update DaPipeRollcallDefer set done = true  where id in ("+id+")";
		pipeRollcallCompanyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public void updateRollcallDeferForPassByHql(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
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
		String hql = "update DaPipeRollcallDefer set isAgree = "+rollcallDefer.getIsAgree()+" ," 
											   + "remark = '"+rollcallDefer.getRemark()+"',"
											   + unitTime
											   + "sendoffMan = '"+rollcallDefer.getSendoffMan()+"',"
											   + "signinMan = '"+rollcallDefer.getSigninMan()+"',"
											   + signinTime
											   + "signatory = '"+rollcallDefer.getSignatory()+"' where id = "+rollcallDefer.getId();
		pipeRollcallCompanyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public DaPipeRollcallCompany loadRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException{
		return pipeRollcallCompanyPersistenceIface.loadRollcallCompany(rollcallCompany);
	}
	
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException{
		return pipeDangerPersistenceIface.loadDanger(danger);
	}
	
	public DaPipelineCompanyInfo loadCompany(DaPipelineCompanyInfo pipeLine) throws ApplicationAccessException {
		//return companyPersistenceIface.loadCompany(pipeLine);
		return null;
	}
	
	public Long createRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return pipeRollcallDeferPersistenceIface.createRollcallDefer(rollcallDefer);
	}

	public DaPipeRollcallDefer loadRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return pipeRollcallDeferPersistenceIface.loadRollcallDefer(rollcallDefer);
	}

	public List<DaPipeRollcallDefer> loadRollcallDefers(DaPipeRollcallCompany DaPipeRollcallCompany, Pagination pagination,UserDetailWrapper userDetail,int type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeRollcallDefer.class, "drd");
		if (DaPipeRollcallCompany != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("drd.daPipeRollcallCompany.id", DaPipeRollcallCompany.getId()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("drd.type",type));
		if(userDetail != null){
			if(userDetail.getUserIndustry() != null && !Nbyhpc.USER_INDUSTRY_COMPANY.equals(userDetail.getUserIndustry())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("drd.done",true));
			}
		}
		return pipeRollcallDeferPersistenceIface.loadRollcallDefers(detachedCriteriaProxy, pagination);
	}
	

	public void updateRollcallDefer(DaPipeRollcallDefer rollcallDefer)
			throws ApplicationAccessException {
		DaPipeRollcallDefer oldRollcallDefer = loadRollcallDefer(rollcallDefer);
		rollcallDefer.setCreateTime(oldRollcallDefer.getCreateTime());
		pipeRollcallDeferPersistenceIface.mergeRollcallDefer(rollcallDefer);
	}

	public void deleteRollcallDefer(String id)throws ApplicationAccessException {
		for (int i = 0; i < id.split(",").length; i++) {
			pipeRollcallDeferPersistenceIface.deleteRollcallDefer(new DaPipeRollcallDefer(Long.parseLong(id)));
		}
	}

	public void setPipeRollcallCompanyPersistenceIface(
			PipeRollcallCompanyPersistenceIface pipeRollcallCompanyPersistenceIface) {
		this.pipeRollcallCompanyPersistenceIface = pipeRollcallCompanyPersistenceIface;
	}

	public PipeRollcallDeferFilePersistenceIface getRollcallDeferFilePersistenceIface() {
		return rollcallDeferFilePersistenceIface;
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public void setPipeRollcallDeferPersistenceIface(
			PipeRollcallDeferPersistenceIface pipeRollcallDeferPersistenceIface) {
		this.pipeRollcallDeferPersistenceIface = pipeRollcallDeferPersistenceIface;
	}

	public void setPipeDangerPersistenceIface(
			PipeDangerPersistenceIface pipeDangerPersistenceIface) {
		this.pipeDangerPersistenceIface = pipeDangerPersistenceIface;
	}
	
}
