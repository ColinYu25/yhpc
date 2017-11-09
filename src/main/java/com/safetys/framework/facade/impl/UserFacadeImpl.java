package com.safetys.framework.facade.impl;

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
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.encoding.PasswordEncoderIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.framework.util.DataAccess;

public class UserFacadeImpl implements UserFacadeIface {
	private RolePersistenceIface rolePersistenceIface;

	private UserPersistenceIface userPersistenceIface;

	private PasswordEncoderIface passwordEncoderIface;
	
	public List<FkArea> loadAreasByGradeRate(int gradeRate)
			throws ApplicationAccessException {
		return null;
	}

	public FkUser loadUser(Long id) throws ApplicationAccessException {
		return this.userPersistenceIface.loadUser(id);
	}

	public void updatePassword(FkUser fkUser, String oldPassword)
			throws ApplicationAccessException {
		FkUser temp = this.userPersistenceIface.loadUser(fkUser.getId());

		String encodeOldPass = this.passwordEncoderIface
				.encodePassword(oldPassword);

		if (!(temp.getUserPass().equals(encodeOldPass)));
		String encodeNewPass = this.passwordEncoderIface.encodePassword(fkUser
				.getUserPass());

		temp.setUserPass(encodeNewPass);

		this.userPersistenceIface.updateUser(temp);
	}

	public List<FkUser> loadUsers(FkUser fkUser, FkUserInfo fkUserInfo,
			Long[] roleIds, Pagination pagination)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy;
		UserDetailWrapper userDetail;
		(detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkUser.class))
				.createAlias("fkUserInfo", "fui");

		if ((userDetail = getUserDetail()).getUserIndustry() != null&&!"admin".equals(userDetail.getUserIndustry())) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.userIndustry",
					userDetail.getUserIndustry()));
		}

		if ((fkUser == null) && (fkUserInfo == null)) {
			DataAccess dataAccess;
			if (!((dataAccess = new DataAccess()).isFirstAreaAccess()))
				if (dataAccess.isSecondAreaAccess()) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.firstArea", userDetail.getFirstArea()));
				} else if (dataAccess.isThirdAreaAccess())
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.secondArea", userDetail.getSecondArea()));
				else if (dataAccess.isFouthAreaAccess()) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.thirdArea", userDetail.getThirdArea()));
				} else if (dataAccess.isFifthAreaAccess())
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.fouthArea", userDetail.getFouthArea()));
		} else {
			if ((fkUser != null) && (fkUser.getUserName() != null)
					&& (!(fkUser.getUserName().equals("")))) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("userName",
						"%" + fkUser.getUserName() + "%"));
			}

			if (fkUserInfo != null) {
				if ((fkUserInfo.getUserIndustry() != null)
						&& (!(fkUserInfo.getUserIndustry().equals("")))&&!"admin".equals(fkUserInfo.getUserIndustry())) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("fui.userIndustry",
							fkUserInfo.getUserIndustry()));
				}
				if ((fkUserInfo.getFirstArea() != null)
						&& (fkUserInfo.getFirstArea().longValue() != 0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.firstArea", fkUserInfo.getFirstArea()));
				}
				if ((fkUserInfo.getSecondArea() != null)
						&& (fkUserInfo.getSecondArea().longValue() != 0L))
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.secondArea", fkUserInfo.getSecondArea()));
				if ((fkUserInfo.getThirdArea() != null)
						&& (fkUserInfo.getThirdArea().longValue() != 0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.thirdArea", fkUserInfo.getThirdArea()));
				}

				if ((fkUserInfo.getFouthArea() != null)
						&& (fkUserInfo.getFouthArea().longValue() != 0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.fouthArea", fkUserInfo.getFouthArea()));
				}
				if ((fkUserInfo.getFifthArea() != null)
						&& (fkUserInfo.getFifthArea().longValue() != 0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq(
							"fui.fifthArea", fkUserInfo.getFifthArea()));
				}
				if ((fkUserInfo.getFactName() != null)
						&& (!(fkUserInfo.getFactName().equals(""))))
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"fui.factName", "%" + fkUserInfo.getFactName()
									+ "%"));
				if ((fkUserInfo.getUserCompany() != null)
						&& (!(fkUserInfo.getUserCompany().equals("")))) {
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"fui.userCompany", "%"
									+ fkUserInfo.getUserCompany() + "%"));
				}

				if ((fkUserInfo.getUserMobile() != null)
						&& (!(fkUserInfo.getUserMobile().equals("")))) {
					detachedCriteriaProxy.add(RestrictionsProxy.like(
							"fui.userMobile", "%" + fkUserInfo.getUserMobile()
									+ "%"));
				}

			}

		}

		return this.userPersistenceIface.loadUsers(pagination,
				detachedCriteriaProxy);
	}

	@SuppressWarnings("unchecked")
	public void createUser(FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkRole.class);
		detachedCriteriaProxy.add(RestrictionsProxy.in("id", roleIds));

		List fkRoles = this.rolePersistenceIface
				.loadRoles(detachedCriteriaProxy);

		Set set = new HashSet(fkRoles);

		fkUser.setFkRoles(set);

		String encodePass = this.passwordEncoderIface.encodePassword(fkUser
				.getUserPass());

		fkUser.setUserPass(encodePass);

		fkUserInfo.setFkUser(fkUser);

		fkUser.setFkUserInfo(fkUserInfo);

		this.userPersistenceIface.createUser(fkUser);
		
	}

	public void updatePerson(FkUser fkUser, FkUserInfo fkUserInfo)
			throws ApplicationAccessException {
		FkUser fkUserT;
		FkUserInfo fkUserInfoT;
		(fkUserInfoT = (fkUserT = this.userPersistenceIface.loadUser(fkUser
				.getId())).getFkUserInfo()).setFactName(fkUserInfo
				.getFactName());

		fkUserInfoT.setBornDate(fkUserInfo.getBornDate());

		fkUserInfoT.setIdCard(fkUserInfo.getIdCard());

		fkUserInfoT.setUserPhone(fkUserInfo.getUserPhone());

		fkUserInfoT.setUserMobile(fkUserInfo.getUserMobile());

		fkUserInfoT.setUserEmail(fkUserInfo.getUserEmail());

		fkUserInfoT.setUserCompany(fkUserInfo.getUserCompany());

		fkUserT.setFkUserInfo(fkUserInfoT);

		this.userPersistenceIface.updateUser(fkUserT);
	}

	public List<FkRole> loadRoles(Long userId)
			throws ApplicationAccessException {
		@SuppressWarnings("unused")
		FkUser fkUser;
		Iterator iterator = loadUser(userId).getFkRoles().iterator();

		StringBuffer buffer = new StringBuffer();

		buffer.append("from FkRole where");

		while (iterator.hasNext()) {
			FkRole fkRole = (FkRole) iterator.next();

			buffer.append(" gradePath like '");

			buffer.append(fkRole.getGradePath());

			buffer.append("%'");

			if (iterator.hasNext())
				buffer.append(" or ");
			else
				buffer.append(" and deleted=0 ");
		}
		buffer.append(" order by gradePath");

		return this.rolePersistenceIface.loadRoles(buffer.toString());
	}

	public void updatePassword(FkUser fkUser) throws ApplicationAccessException {
		FkUser temp = this.userPersistenceIface.loadUser(fkUser.getId());

		String encodeNewPass = this.passwordEncoderIface.encodePassword(fkUser
				.getUserPass());

		temp.setUserPass(encodeNewPass);

		this.userPersistenceIface.updateUser(temp);
	}

	@SuppressWarnings("unchecked")
	public void mergeUser(FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy;
		(detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkRole.class))
				.add(RestrictionsProxy.in("id", roleIds));

		List fkRoles = this.rolePersistenceIface
				.loadRoles(detachedCriteriaProxy);

		Set set = new HashSet(fkRoles);

		fkUser.setFkRoles(set);

		FkUser temp = this.userPersistenceIface.loadUser(fkUser.getId());

		fkUser.setUserPass(temp.getUserPass());

		fkUser.setUserName(temp.getUserName());

		fkUser.setDeleted(temp.isDeleted());

		fkUserInfo.setFkUser(fkUser);

		fkUser.setFkUserInfo(fkUserInfo);

		this.userPersistenceIface.mergeUser(fkUser);
	}

	public void setUserPersistenceIface(
			UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public void deleteUsers(Long[] ids) throws ApplicationAccessException {
		this.userPersistenceIface.deleteUsers(ids);
	}

	public void setRolePersistenceIface(
			RolePersistenceIface rolePersistenceIface) {
		this.rolePersistenceIface = rolePersistenceIface;
	}

	public void setPasswordEncoderIface(
			PasswordEncoderIface passwordEncoderIface) {
		this.passwordEncoderIface = passwordEncoderIface;
	}

	public void checkDuplicateUser(String userName)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy;
		List fkUsers;
		(detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkUser.class))
				.add(RestrictionsProxy.eq("userName", userName));

		if (((fkUsers = this.userPersistenceIface
				.loadUsers(detachedCriteriaProxy)) == null)
				|| (fkUsers.size() <= 0)) {
			return;
		}

		throw new ApplicationAccessException("User with this name[" + userName
				+ "] already exists.");
	}

	private UserDetailWrapper getUserDetail() {
		@SuppressWarnings("unused")
		SecurityContext context;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		UserDetailWrapper user = (UserDetailWrapper) authentication
				.getPrincipal();

		return user;
	}

}