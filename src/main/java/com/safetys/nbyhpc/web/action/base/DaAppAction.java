package com.safetys.nbyhpc.web.action.base;

import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.util.FileUpload;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType;
/**
 * @(#) NbAppAction.java
 * @date 2009-07-28
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaAppAction extends BaseAppAction {

	private static final long serialVersionUID = 1575946049147358432L;

	private String url;// 设置转向的url链接

	public final static String MESSAGE = "message";// 弹出提示的action result

	public final static String READONLY = "readonly";
	
	private final static String REPORT_URL = "reportUrl";

	protected UserPersistenceIface userPersistenceIface;
	
	//页面提示内容
	private String info;
	
	//跳转的url
	private String fw;

	/**
	 * 设置上传的绝对目录
	 * 
	 */
	public void setUploadRealPath() {
		if (FileUpload.getREAL_PATH() == null
				|| "".equals(FileUpload.getREAL_PATH())) {
			FileUpload.setREAL_PATH(((ServletContext) ActionContext
					.getContext().get(ServletActionContext.SERVLET_CONTEXT))
					.getRealPath(""));// 设置项目绝对路径为文件保存路径
		}
	}

	/**
	 * 判断当前用户是否为超级管理员或数据管理员
	 * @return
	 */
	public boolean isAdmin() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId())
				.getFkRoles();
		boolean isAdmin = RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles)
				|| RoleType.isRoleByCode(RoleType.MANAGER, roles);
		return isAdmin;
	}
	
	public boolean isAdmin(UserDetailWrapper userDetail) {
		return Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry());
	}
	/**
	 * 是否是安委办用户
	 * @return
	 */
	public boolean isAnWeiUser() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId()).getFkRoles();
		return RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles);
	}

	public boolean isJianWeiRole() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId()).getFkRoles();
		return RoleType.isRoleByCode(RoleType.ROLE_JIANWEI, roles);
	}
	
	/**
	 * 加载当前用户的角色类型
	 * @return
	 */
	public String getRoleType() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId())
				.getFkRoles();
		return RoleType.getRoleCodeStr(roles);
	}
	
	/**
	 * 查询权限
	 * @return
	 */
	public boolean isViewRole() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId()).getFkRoles();
		return RoleType.isRoleByCode(RoleType.ROLE_AJJ_LOOK, roles);
	}
	
	/**
	 * 根据企业信息返回提示页面
	 * @return
	 */
	public String check(DaCompany company) {
		
		
		//add by huangjl   企业没有修改信息的话，跳转到提示页面。
		if (getUserDetail().getUserIndustry().equals("qiye")) {
			
			//企业没有修改信息的话，跳转到提示页面
			if(company!=null&&company.getIsModify()!=null){
				if(company.getIsModify()==0){
					this.info="updateCompany";
					return "info";
				}
			}else{
				this.info="updateCompany";
				return "info";
			}
			
			//先判断是否要修改用户名为工商注册号
			if(Nbyhpc.ISMODIFYUSERNAME){
				//企业用户名和工商注册号不一致的情况下，跳转到提示页面
//				if(company!=null&&company.getRegNum()!=null&&!"".equals(company.getRegNum())){
//					   if(!getUserDetail().getUsername().equals(company.getRegNum())){
//						   this.info="updateUserName";
//						   return "info";
//					   }
//				}else{
//					//企业用户名和组织机构代码不一致的情况下，跳转到提示页面
//					if(company!=null&&company.getSetupNumber()!=null){
//						   if(!getUserDetail().getUsername().equals(company.getSetupNumber())){
//							   this.info="updateUserNameSetupNumber";
//							   return "info";
//						   }
//					}
//				}
				
				String flag="0";
				if(company!=null){
					 //工商注册号和组织机构代码都为空的情况，不要修改
					if((company.getSetupNumber()==null||"".equals(company.getSetupNumber()))&&(company.getHaveRegNum()==null||"0".equals(company.getHaveRegNum())||"".equals(company.getHaveRegNum()))){
						 flag="1";
					}else{
						if(company.getRegNum()!=null&&!"".equals(company.getRegNum())&&getUserDetail().getUsername().equals(company.getRegNum())){
								   flag="1";
						}else{
							//企业用户名和组织机构代码不一致的情况下，跳转到提示页面
							if(company.getSetupNumber()!=null&&getUserDetail().getUsername().equals(company.getSetupNumber())){
								  flag="1";
							}
						}
						
					}
				}else{
					flag="1";
				}
			
				
				
				
				
				if(flag.equals("0")){
					 this.info="updateUserName";
					 return "info";
				}
				
			}
			
			if ("true".equals(String.valueOf(getRequest().getSession().getAttribute("easyPassword")))) {
				this.info="updatePassword";
				return "info";
			}
			
		}
		return null;
	}
	
	public String getReportUrl() {
		return getText(REPORT_URL);
	}
	
	/**
	 * 加载当前用户的角色描述
	 * @return
	 */
	public String getRoleDepic() {
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId())
				.getFkRoles();
		return RoleType.getRoleDepicStr(roles);
	}
	
	public String getAreaXmlUrl() {
		return (new StringBuilder()).append(getResourcePath()).append("/js")
				.append(getContextPath()).append("_area.xml").toString();
	}
	
	public String getAllAreaXmlUrl() {
		return (new StringBuilder()).append(getResourcePath()).append("/js")
				.append(getContextPath()).append("_area1.xml").toString();
	}

	public String getEnumXmlUrl() {
		return (new StringBuilder()).append(getResourcePath()).append("/js")
				.append(getContextPath()).append("_enum.xml").toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserPersistenceIface(
			UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getFw() {
		return fw;
	}

	public void setFw(String fw) {
		this.fw = fw;
	}

	@Override
	public void prepare() throws Exception {
		super.prepare();
//		System.out.println("URL =.= " + getRequest().getRequestURI());
//		Enumeration names = getRequest().getParameterNames();
//		System.out.println("===========================================");
//		while (names.hasMoreElements()) {
//			String name = (String) names.nextElement();
//			System.out.println(name + " =.= " + getRequest().getParameter(name));
//		}
//		System.out.println("===========================================");
	}
}
