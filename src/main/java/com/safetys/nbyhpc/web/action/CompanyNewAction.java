package com.safetys.nbyhpc.web.action;

import java.io.IOException;
import java.util.List;

import com.googlecode.jsonplugin.annotations.JSON;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.vo.TCompanyVo;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
import com.sdicons.json.mapper.MapperException;

/**
 * @(#) CompanyNewAction.java
 * @date 2009-07-29
 * @author huangjl
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class CompanyNewAction extends DaAppAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 企业基本信息
	 */


	private CompanyFacadeIface companyFacadeIface;// 企业基本信息的业务接口

	private TCompany tcompany;// 中心库企业单位
	private TCompanyVo companyVo;
	// 操作提示
	private String msg = "ok";
	private boolean success = true;
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	/**
	 * 返回类型
	 * 
	 * @return
	 * @throws IOException 
	 * @throws MapperException 
	 */
	public String loadTCompany() throws IOException, MapperException {
			try {
				if(companyVo==null){
					companyVo=new TCompanyVo();
				}
				List<TCompany> list=companyFacadeIface.loadTCompany(tcompany);
				if(list!=null&&list.size()>0){
					if(list.size()>1){
						//msg='2'表示查询到多条记录，需要补充完善查询条件
						msg="2";
					}else{
						//msg='1'表示只查询到一条记录
						msg="1";
						tcompany=list.get(0);
						companyVo.setCompanyName(tcompany.getCompanyName());
						companyVo.setBusinessRegNum(tcompany.getBusinessRegNum());
						companyVo.setOrgCode(tcompany.getOrgCode());
						companyVo.setUuid(tcompany.getUuid());
						companyVo.setId(tcompany.getId());
					}
				}else{
					//msg='0'表示查询不到记录
					msg="0";
				}
			} catch (ApplicationAccessException e) {
				success = false;
				e.printStackTrace();
			}
		
		return SUCCESS;
	}

	
	/**
	 * 返回类型
	 * 
	 * @return
	 * @throws IOException 
	 * @throws MapperException 
	 */
	public String checkTCompany(){
			try {
				if(tcompany!=null){
					DaCompany c =new DaCompany();
					c.setUuid(tcompany.getUuid());
					List<DaCompany> list = companyFacadeIface.loadCompaniesAffirm(c, null, getUserDetail());
					
					if(list!=null&&list.size()>0){
						//msg='1'表示查询到记录
						msg="1";
					}else{
						//msg='0'表示查询不到记录
						msg="0";
					}
				}
			} catch (ApplicationAccessException e) {
				success = false;
				e.printStackTrace();
			}
		
		return SUCCESS;
	}


	/**
	 * @param companyFacadeIface the companyFacadeIface to set
	 */
	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}



	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the companyVo
	 */
	public TCompanyVo getCompanyVo() {
		return companyVo;
	}

	/**
	 * @param companyVo the companyVo to set
	 */
	public void setCompanyVo(TCompanyVo companyVo) {
		this.companyVo = companyVo;
	}

	/**
	 * @return the tcompany
	 */
	@JSON(serialize=false)
	public TCompany getTcompany() {
		return tcompany;
	}

	/**
	 * @param tcompany the tcompany to set
	 */
	public void setTcompany(TCompany tcompany) {
		this.tcompany = tcompany;
	}
	
}
