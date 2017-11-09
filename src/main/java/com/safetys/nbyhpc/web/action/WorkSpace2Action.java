/*
 * @(#)WorkSpaceAction.java        
 * Date 2008-3-5                                           
 * Copyright (c) 2008 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.nbyhpc.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;

import cn.safetys.constant.SystemConstant;
import cn.safetys.cxf.ws.rest.CasApp;
import cn.safetys.cxf.ws.rest.CasUser;
import cn.safetys.util.OaPasswordUtils;

import com.google.gson.Gson;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.web.action.WorkSpaceAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.Icon;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.util.MyApp;
import com.safetys.nbyhpc.util.RoleType;

import edu.yale.its.tp.cas.client.CASReceipt;
import edu.yale.its.tp.cas.client.filter.CASFilter;

/**
 * @Author:lisl
 * @Created:2012-12-9 
 */
public class WorkSpace2Action extends WorkSpaceAction {
	
	private static final String SSO_URL = "ssoURL";
	private static final String UCENTER_URL = "ucenterURL";
	
	private String flag="";
	private String DangerId;
	private String companyId;
	private String EntityId;
	private static final long serialVersionUID = -2460652344681852907L;

	private String companyIsDelete="0";//企业是否删除，“0”表示未删除，“1”表示删除了。默认是“0”未删除
	
	private boolean whpCompanyUser; //安监局危险化学品企业用户
	
	private boolean whpPipeCompanyUser;//危化品管道企业
	
	private String ticket;
    private String usertype;
    
	private CompanyFacadeIface companyFacadeIface;// 企业基本信息的业务接口
	protected UserPersistenceIface userPersistenceIface;
	@Override
	public String loadMainWorkSpace() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId()).getFkRoles();
		//去掉URL上的参数，防止退出时候，刷新其他页面报错
	    if(SystemConstant.P!=null
	    		&&SystemConstant.P.isEnterprise()
	    		&&!SystemConstant.P.isGovernment()){
	    	boolean b = false;
	    	for (FkRole role : roles) {
				if(StringUtils.equals(role.getRoleCode(), "ROLE_XIAOFANG")
						||StringUtils.equals(role.getRoleCode(), "ROLE_XIAOFANG_CITY")
						||StringUtils.equals(role.getRoleCode(), "ROLE_JIAOJING")
						||StringUtils.equals(role.getRoleCode(), "ROLE_JIAOJING_CITY")
						||StringUtils.equals(role.getRoleCode(), "ROLE_HAISHI")
						||StringUtils.equals(role.getRoleCode(), "ROLE_HAISHI_CITY")
						||StringUtils.equals(role.getRoleCode(), "ROLE_COMPANY")
						||StringUtils.equals(role.getRoleCode(), "ROLE_JIAOTONG")
						||StringUtils.equals(role.getRoleCode(), "ROLE_JIAOTONG_CITY")
						||StringUtils.equals(role.getRoleCode(), "ROLE_ADMINISTRATOR")){
					
					b = true;
					break;
				}
			}
	    	if(!b){
	    		try {
					ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
					ServletActionContext.getResponse().getWriter().write("非消防、交警、海事部门用户，无法登录！");
					ServletActionContext.getRequest().getSession().invalidate();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	return null;
	    	}
	    }
	    if (RoleType.isRoleByCode(RoleType.ROLE_COMPANY, roles)) {
	    	try {
				DaCompany company =companyFacadeIface.loadCompanyByUserId(Long.valueOf(getUserDetail().getUserId()));
				Set<DaIndustryParameter> hzTradeTypes = company.getHzTradeTypes();
				for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
					if ("121".equals(daIndustryParameter.getCode())) {
						whpCompanyUser = true;
						break;
					}
				}
	    	} catch (ApplicationAccessException e) {
				e.printStackTrace();
			}
	    }
	    if (whpCompanyUser && RoleType.isRoleByCode(RoleType.ROLE_PIPECOMPANY, roles)) {
	    	whpPipeCompanyUser = true;
	    }
	    
		if (StringUtils.isNotBlank(ticket) || StringUtils.isNotBlank(usertype)) {
			return "redirect";
		}
		return super.loadMainWorkSpace();
	}

	@Override
	public String loadTopWorkSpace() {
		setMenuTitle();
		return super.loadTopWorkSpace();
	}

	public String loadMainWorkSpaceRedirect() {
		setMenuTitle();
		//add by huangjl  此处添加判断企业是否删除的功能，如果企业删除了，则不允许登录隐患排查系统
		if ("qiye".equals(getUserDetail().getUserIndustry())){
			//String companyName=getUserDetail().getUserCompany();
			//不能根据 企业名称 查询，企业名称可能不是唯一的，还是要根据userId，查询到关联的企业信息
			try {
				 DaCompany company =companyFacadeIface.loadCompanyByUserId(Long.valueOf(getUserDetail().getUserId()));
				 if(company!=null&&company.getHzTradeTypes()!=null&&company.getHzTradeTypes().size()>0){
//					System.out.print("行业不为空！");
					companyIsDelete="0";
				 }else{
//					 System.out.print("行业为空！");
					 companyIsDelete="1";
				 }
			} catch (ApplicationAccessException e) {
				e.printStackTrace();
			}
		
		}
		return SUCCESS;
	}
	
	public String loadMainWorkSpace1() {
		return SUCCESS;
	}
	
	public String loadMainWorkSpace2() {
		return SUCCESS;
	}
	
	public String loadTopWorkSpace1() {
		setMenuTitle();
		return SUCCESS;
	}
	
	public String loadTopWorkSpace2() {
		setMenuTitle();
		return SUCCESS;
	}
	
	public String loadRightWorkSpacePipe() {
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public void setMenuTitle() {
		try {
			HttpSession session = getRequest().getSession();
			String awStr = (String)session.getAttribute("aw");
			if (!"true".equals(awStr)) {
				CasUser casUser = (CasUser) session.getAttribute("casUser");
				if(casUser != null) {
					String ssoURL = getSsoUrl();
					String ucenterURL = getUCenterUrl(); // 不用
					// http://127.0.0.1:9090/ucenter/images/module/图片.png
					// 如果要获取业务系统的图标
					// 需要加上ucenter的前缀
					// 获取"我的"应用
					// User user = (User)this.getUserDetail();
					List<Icon> icons = new ArrayList<Icon>();
					for (int i = 1; i < 30; i++) {
						Icon icon = new Icon();
						icon.setId(i);
						icon.setTitle("应用" + i);
						icons.add(icon);
					}

					ServletActionContext.getRequest().setAttribute("icons", icons);
					// 真实data
					List<CasApp> realIcons = new ArrayList<CasApp>();
					StringBuffer jSon = new StringBuffer();
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet get = new HttpGet(ssoURL + "cxfservices/securityRest/apps/" + casUser.getUsername());
					get.addHeader("userId", casUser.getUsername());
					get.addHeader("userPass", casUser.getPassword());
					HttpResponse response = httpClient.execute(get);

					// 先从响应头得到实体
					HttpEntity entity = response.getEntity();
					// 得到实体输入流
					try {
						InputStream inSm = entity.getContent();
						BufferedReader buffer = new BufferedReader(new InputStreamReader(inSm, "UTF-8")); // encoding

						Scanner inScn = new Scanner(buffer);
						while (inScn.hasNextLine()) {
							jSon.append(inScn.nextLine());
						}
						entity.consumeContent();
					} catch (IllegalStateException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					// Json 转对象
					Gson gson = new Gson();
					System.err.println(jSon);

					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int w = cal.get(Calendar.DAY_OF_WEEK);
					String k = casUser.getPassword().substring(w, w + 10);
					String u = OaPasswordUtils.encode(casUser.getUsername());
					CASReceipt receipt = (CASReceipt) session.getAttribute(CASFilter.CAS_FILTER_RECEIPT);
					String usertypeStr = receipt.getUsertype().getKey();
					awStr = String.valueOf(receipt.isAw());
					String urlParams = "?u=" + u + "&k=" +  k + "&usertype=" + usertypeStr + "&aw=" + awStr;
					
					MyApp myApp = gson.fromJson(jSon.toString(), MyApp.class);
					for (CasApp casApp : myApp.getInfos()) {
						casApp.setImageFileName(ucenterURL + "images/module/" + casApp.getImageFileName());
						casApp.setAppURL(casApp.getAppURL() + urlParams);
						realIcons.add(casApp);
					}
					// TODO 这里先"硬编码" 推荐的改进 应该在用户管理中心中增加"应用"类的visabled属性
					// List<String> exclusions = Arrays.asList(new String[]{"办公集成","企业档案"});
					Iterator<CasApp> iter = realIcons.iterator();
					while (iter.hasNext()) {
						CasApp casApp = iter.next();
						if ("企业档案".equals(casApp.getTitle())) {
							ServletActionContext.getRequest().setAttribute("enterpriseArchives", casApp);
						}
						if (casApp.isVisibility() == false) {
							// if (exclusions.indexOf(casApp.getTitle())>=0) {
							iter.remove();
						}
					}
					ServletActionContext.getRequest().setAttribute("realIcons", realIcons);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String option() {
		return SUCCESS;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDangerId() {
		return DangerId;
	}

	public void setDangerId(String dangerId) {
		DangerId = dangerId;
	}

	public String getEntityId() {
		return EntityId;
	}

	public void setEntityId(String entityId) {
		EntityId = entityId;
	}

	/**
	 * @return the companyFacadeIface
	 */
	public CompanyFacadeIface getCompanyFacadeIface() {
		return companyFacadeIface;
	}

	/**
	 * @param companyFacadeIface the companyFacadeIface to set
	 */
	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	/**
	 * @return the companyIsDelete
	 */
	public String getCompanyIsDelete() {
		return companyIsDelete;
	}

	/**
	 * @param companyIsDelete the companyIsDelete to set
	 */
	public void setCompanyIsDelete(String companyIsDelete) {
		this.companyIsDelete = companyIsDelete;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getSsoUrl() {
		return getText(SSO_URL);
	}

	public String getUCenterUrl() {
		return getText(UCENTER_URL);
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public UserPersistenceIface getUserPersistenceIface() {
		return userPersistenceIface;
	}

	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public boolean isWhpCompanyUser() {
		return whpCompanyUser;
	}

	public void setWhpCompanyUser(boolean whpCompanyUser) {
		this.whpCompanyUser = whpCompanyUser;
	}

	public boolean isWhpPipeCompanyUser() {
		return whpPipeCompanyUser;
	}

	public void setWhpPipeCompanyUser(boolean whpPipeCompanyUser) {
		this.whpPipeCompanyUser = whpPipeCompanyUser;
	}
	
}
