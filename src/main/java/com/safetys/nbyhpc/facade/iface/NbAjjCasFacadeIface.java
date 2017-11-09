package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;

public interface NbAjjCasFacadeIface {

	public FkUser loadUser(Long id) throws ApplicationAccessException;

	public List<FkRole> loadRoles(Long userId)
			throws ApplicationAccessException;

	public List<FkArea> loadAreasByGradeRate(int gradeRate)
			throws ApplicationAccessException;

	public void createUser(FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds)
			throws ApplicationAccessException;

	public List<FkUser> loadUsers(FkUser fkUser, FkUserInfo fkUserInfo,
			Long[] roleIds, Pagination pagination)
			throws ApplicationAccessException;

	public void deleteUser(String userName) throws ApplicationAccessException;

	public void mergeUser(FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds)
			throws ApplicationAccessException;

	public void updatePerson(FkUser fkUser, FkUserInfo fkUserInfo)
			throws ApplicationAccessException;

	public void updatePassword(FkUser fkUser, String oldPassword)
			throws ApplicationAccessException;

	public void updatePassword(FkUser fkUser) throws ApplicationAccessException;

	public List<FkUser> checkDuplicateUser(String userName)
			throws ApplicationAccessException;
	
	public List<FkRole> loadRoles() throws ApplicationAccessException;
}
