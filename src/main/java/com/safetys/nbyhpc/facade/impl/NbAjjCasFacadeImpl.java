package com.safetys.nbyhpc.facade.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.RolePersistenceIface;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.encoding.PasswordEncoderIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.framework.util.DataAccess;
import com.safetys.nbyhpc.facade.iface.NbAjjCasFacadeIface;

public class NbAjjCasFacadeImpl implements NbAjjCasFacadeIface{
	
	private UserPersistenceIface userPersistenceIface;
	private RolePersistenceIface rolePersistenceIface;
	private PasswordEncoderIface passwordEncoderIface;
	
	/**
	 * @description 模块调用：只加载当前用户所属角色的所有下级角色列表
	 * @return List<FkRole>
	 * @throws ApplicationAccessException
	 */
	public List<FkRole> loadRoles(Long userId) throws ApplicationAccessException{
		FkUser fkUser=loadUser(userId);
		Iterator<FkRole> iterator=fkUser.getFkRoles().iterator();
		StringBuffer buffer=new StringBuffer();
		buffer.append("from FkRole where");
		while(iterator.hasNext()){
			FkRole fkRole=iterator.next();
			buffer.append(" gradePath like '");
			buffer.append(fkRole.getGradePath());
			buffer.append("%'");
			if(iterator.hasNext()){
				buffer.append(" or ");
			}else{
				buffer.append(" and deleted=0 "); 
			}
		}
		buffer.append(" order by gradePath");
		return rolePersistenceIface.loadRoles(buffer.toString());
	}
	
	public List<FkRole> loadRoles() throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(FkRole.class,"fr");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("fr.id"));
		List<FkRole> fkRoles=rolePersistenceIface.loadRoles(detachedCriteriaProxy);
		return fkRoles;
	}
	
	public FkUser loadUser(Long id) throws ApplicationAccessException{
		return userPersistenceIface.loadUser(id);
	}
	public List<FkArea> loadAreasByGradeRate(int gradeRate) throws ApplicationAccessException{
//		return areaPersistenceIface.loadAreasByGradeRate(gradeRate);
		return null;
	}	
	public void createUser(FkUser fkUser,FkUserInfo fkUserInfo,Long[] roleIds) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(FkRole.class);
		detachedCriteriaProxy.add(RestrictionsProxy.in("id",roleIds));
		List<FkRole> fkRoles=rolePersistenceIface.loadRoles(detachedCriteriaProxy);
		Set<FkRole> set = new HashSet<FkRole>(fkRoles);
		fkUser.setFkRoles(set);
		String encodePass=passwordEncoderIface.encodePassword(fkUser.getUserPass());
		fkUser.setUserPass(encodePass);
		fkUserInfo.setFkUser(fkUser);
		fkUser.setFkUserInfo(fkUserInfo);
		userPersistenceIface.createUser(fkUser);
	}
	public List<FkUser> checkDuplicateUser(String userName) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(FkUser.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("userName",userName));
		List<FkUser> fkUsers=userPersistenceIface.loadUsers(detachedCriteriaProxy);
		return fkUsers;
	}
	public List<FkUser> loadUsers(FkUser fkUser,FkUserInfo fkUserInfo,Long[] roleIds,Pagination pagination) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(FkUser.class);
		detachedCriteriaProxy.createAlias("fkUserInfo","fui");
		UserDetailWrapper userDetail=getUserDetail();
		if(userDetail.getUserIndustry()!=null){//用户所属行业访问控制
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.userIndustry",userDetail.getUserIndustry()));
		}
		if(fkUser==null&&fkUserInfo==null){
			/*
			 * 一级区域访问权限，不需限制
			 * 二级区域访问权限，加一级区域限制
			 * 其它类似
			 */
			DataAccess dataAccess=new DataAccess();
			if(dataAccess.isFirstAreaAccess()){
				//empty body
			}else if(dataAccess.isSecondAreaAccess()){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea",userDetail.getFirstArea()));
			}else if(dataAccess.isThirdAreaAccess()){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea",userDetail.getSecondArea()));
			}else if(dataAccess.isFouthAreaAccess()){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.thirdArea",userDetail.getThirdArea()));
			}else if(dataAccess.isFifthAreaAccess()){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.fouthArea",userDetail.getFouthArea()));
			}
		}else{
			if(fkUser!=null&&fkUser.getUserName()!=null&&!fkUser.getUserName().equals("")){
				detachedCriteriaProxy.add(RestrictionsProxy.like("userName","%"+fkUser.getUserName()+"%"));
			}
			if(fkUserInfo!=null){
				if(fkUserInfo.getFirstArea()!=null&&fkUserInfo.getFirstArea()!=0){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.firstArea",fkUserInfo.getFirstArea()));
				}
				if(fkUserInfo.getSecondArea()!=null&&fkUserInfo.getSecondArea()!=0){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.secondArea",fkUserInfo.getSecondArea()));
				}
				if(fkUserInfo.getThirdArea()!=null&&fkUserInfo.getThirdArea()!=0){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.thirdArea",fkUserInfo.getThirdArea()));
				}
				if(fkUserInfo.getFouthArea()!=null&&fkUserInfo.getFouthArea()!=0){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.fouthArea",fkUserInfo.getFouthArea()));
				}
				if(fkUserInfo.getFifthArea()!=null&&fkUserInfo.getFifthArea()!=0){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.fifthArea",fkUserInfo.getFifthArea()));
				}
				if(fkUserInfo.getFactName()!=null&&!fkUserInfo.getFactName().equals("")){
					detachedCriteriaProxy.add(RestrictionsProxy.like("fui.factName","%"+fkUserInfo.getFactName()+"%"));
				}
				if(fkUserInfo.getUserCompany()!=null&&!fkUserInfo.getUserCompany().equals("")){
					detachedCriteriaProxy.add(RestrictionsProxy.like("fui.userCompany","%"+fkUserInfo.getUserCompany()+"%"));
				}
				if(fkUserInfo.getUserMobile()!=null&&!fkUserInfo.getUserMobile().equals("")){
					detachedCriteriaProxy.add(RestrictionsProxy.like("fui.userMobile","%"+fkUserInfo.getUserMobile()+"%"));
				}
			}
		}
//		detachedCriteriaProxy.createAlias("fkRoles","frs");
//		if(roleIds!=null&&roleIds[0]!=0){
//			detachedCriteriaProxy.add(RestrictionsProxy.in("frs.id",roleIds));
//		}
//		detachedCriteriaProxy.distinctRootEntity();//去掉结果集中重复的数据       此功能不起效果，需解决
//		detachedCriteriaProxy.setFetchMode("frs.fkRoles.fkResources", FetchModeProxy.getSelectMode());
		
		return userPersistenceIface.loadUsers(pagination,detachedCriteriaProxy);
	}
	public void deleteUser(String userName) throws ApplicationAccessException{
		Long[] ids = new Long[1];
		List<FkUser> fkUsers=checkDuplicateUser(userName);
		if(fkUsers!=null&&fkUsers.size()>0){
			ids[0] = fkUsers.get(0).getId();
			userPersistenceIface.deleteUsers(ids);
		}
	}
	
	public void mergeUser(FkUser fkUser,FkUserInfo fkUserInfo,Long[] roleIds)throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(FkRole.class);
		detachedCriteriaProxy.add(RestrictionsProxy.in("id",roleIds));
		List<FkRole> fkRoles=rolePersistenceIface.loadRoles(detachedCriteriaProxy);
		Set<FkRole> set = new HashSet<FkRole>(fkRoles);
		fkUser.setFkRoles(set);
		List<FkUser> fkUsers=checkDuplicateUser(fkUser.getUserName());
		if(fkUsers!=null&&fkUsers.size()>0){
			FkUser temp = fkUsers.get(0);
			fkUser.setId(temp.getId());
			fkUser.setUserPass(temp.getUserPass());
			fkUser.setUserName(temp.getUserName());
			fkUser.setDeleted(temp.isDeleted());
			fkUserInfo.setId(temp.getId());
			fkUserInfo.setFkUser(fkUser);
			fkUser.setFkUserInfo(fkUserInfo);
			userPersistenceIface.mergeUser(fkUser);
		}
	}
	
	public void updatePerson(FkUser fkUser,FkUserInfo fkUserInfo)throws ApplicationAccessException{	
		FkUser fkUserT=userPersistenceIface.loadUser(fkUser.getId());
		FkUserInfo fkUserInfoT=fkUserT.getFkUserInfo();
		fkUserInfoT.setFactName(fkUserInfo.getFactName());
		fkUserInfoT.setBornDate(fkUserInfo.getBornDate());
		fkUserInfoT.setIdCard(fkUserInfo.getIdCard());
		fkUserInfoT.setUserPhone(fkUserInfo.getUserPhone());
		fkUserInfoT.setUserMobile(fkUserInfo.getUserMobile());
		fkUserInfoT.setUserEmail(fkUserInfo.getUserEmail());
		fkUserInfoT.setUserCompany(fkUserInfo.getUserCompany());
		fkUserT.setFkUserInfo(fkUserInfoT);
		userPersistenceIface.updateUser(fkUserT);
	}
	/**
	 * Modify password  by person 
	 */
	public void updatePassword(FkUser fkUser,String oldPassword)throws ApplicationAccessException{	
		FkUser temp=userPersistenceIface.loadUser(fkUser.getId());
		String encodeOldPass=passwordEncoderIface.encodePassword(oldPassword);
		if(!temp.getUserPass().equals(encodeOldPass)){
			
		}
		String encodeNewPass=passwordEncoderIface.encodePassword(fkUser.getUserPass());
		temp.setUserPass(encodeNewPass);
		userPersistenceIface.updateUser(temp);
	}
	/**
	 * Reset password  by administrator 
	 */
	public void updatePassword(FkUser fkUser)throws ApplicationAccessException{	
		List<FkUser> fkUsers=checkDuplicateUser(fkUser.getUserName());
		if(fkUsers!=null&&fkUsers.size()>0){
			FkUser temp = fkUsers.get(0);
			String encodeNewPass=passwordEncoderIface.encodePassword(fkUser.getUserPass());
			temp.setUserPass(encodeNewPass);
			userPersistenceIface.updateUser(temp);
		}
	}
	private UserDetailWrapper getUserDetail() {
		SecurityContext context=SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		UserDetailWrapper user=(UserDetailWrapper)authentication.getPrincipal();
		return user;
	}
	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public void setRolePersistenceIface(RolePersistenceIface rolePersistenceIface) {
		this.rolePersistenceIface = rolePersistenceIface;
	}
	public void setPasswordEncoderIface(PasswordEncoderIface passwordEncoderIface) {
		this.passwordEncoderIface = passwordEncoderIface;
	}
}
