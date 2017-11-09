/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.web.action;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class WorkSpaceCotentAction extends DaAppAction {

	private static final long serialVersionUID = -4290388463364882474L;

	private UserFacadeIface userFacadeIface;

	private FkUser fkUser;

	private Long[] roleIds;
	private FkUserInfo fkUserInfo;
	
	private Boolean saveCreate;
	/**
	 * 加载企业端首页
	 * 
	 * @return
	 */
	public String loadWorkSpaceContent() throws Exception {

		return SUCCESS;
	}

	public String loadTopWorkSpaceContent() throws Exception {

		return SUCCESS;
	}

	public String loadBottomWorkSpaceContent() throws Exception {

		return SUCCESS;
	}

	public String updatePassword2() {
		try {
			userFacadeIface.updatePassword(this.fkUser);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}

			addActionError(ae.getOurMessage());

			return "error";
		} catch (Exception e) {
			e.printStackTrace();

			return "error";
		}
		super.setMessageKey("修改密码成功！");
		super.setUrl("../user/loadUsers.xhtml");

		return MESSAGE_TO_REDIRECT;
	}

	
	 public String updateUser()
	  {
	    try
	    {
	      this.userFacadeIface.mergeUser(this.fkUser, this.fkUserInfo, this.roleIds);
	    }
	    catch (ApplicationAccessException ae)
	    {
	      if (log.isDebugEnabled())
	      {
	        log.debug(ae.getOurStackTrace());
	      }

	      addActionError(ae.getOurMessage());

	      return "error";
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();

	      return "error";
	    }
	    super.setMessageKey("修改用户成功！");
	    if(saveCreate){
			//super.setUrl("../user/createUserInit.xhtml");
	    	return "input";
		}else{
			//super.setUrl("../user/loadUsers.xhtml");
			return SUCCESS;
		}

		//return MESSAGE_TO_REDIRECT;
	  }

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public FkUser getFkUser() {
		return fkUser;
	}

	public void setFkUser(FkUser fkUser) {
		this.fkUser = fkUser;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public FkUserInfo getFkUserInfo() {
		return fkUserInfo;
	}

	public void setFkUserInfo(FkUserInfo fkUserInfo) {
		this.fkUserInfo = fkUserInfo;
	}

	public Boolean getSaveCreate() {
		return saveCreate;
	}

	public void setSaveCreate(Boolean saveCreate) {
		this.saveCreate = saveCreate;
	}



}
