package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaZhifaReport;
import com.safetys.nbyhpc.domain.model.DaZhifaReportDetail;

public interface EnforceLawFacadeIface {
	public List<DaIndustryParameter> loadTradeTypes(UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public void createEnforceLaw(DaZhifaReport daZhifaReport,
			List<DaZhifaReportDetail> daZhifaReportDetails,
			UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void updateEnforceLaw(DaZhifaReport daZhifaReport,
			List<DaZhifaReportDetail> daZhifaReportDetails,
			UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<DaZhifaReport> loadEnforceLaws(UserDetailWrapper userDetail,
			Pagination pagination, DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;

	public DaZhifaReport loadEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;

	public void deleteEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;

	public List<FkArea> loadAreas(UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public List<DaZhifaReportDetail> loadStatistics(UserDetailWrapper userDetail, DaZhifaReport daZhifaReport)
			throws ApplicationAccessException;
}
