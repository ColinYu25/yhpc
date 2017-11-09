package com.safetys.nbyhpc.web.action;

import java.util.List;
import java.util.Set;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;
import com.safetys.nbyhpc.facade.iface.ActualizeTableNoticeFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class ActualTableNoticeAction extends DaAppAction{
	
	
	/**
	 * 表格下载、通知通告、实施方案
	 */
	private static final long serialVersionUID = -5550751362979514640L;

	private DaActualTableNotice daActualTableNotice;
	
	private ActualizeTableNoticeFacadeIface actualizeTableNoticeFacadeIface;
	
	private Pagination pagination;
	
	private List<DaActualTableNotice> actualizeTableNoticeList;
	
	private Boolean state=null;
	
	private Boolean sysComit=null;
	
	public Boolean getSysComit() {
		return sysComit;
	}
	public void setSysComit(Boolean sysComit) {
		this.sysComit = sysComit;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public DaActualTableNotice getDaActualTableNotice() {
		return daActualTableNotice;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public void setDaActualTableNotice(DaActualTableNotice daActualTableNotice) {
		this.daActualTableNotice = daActualTableNotice;
	}
	/**
	 * 实施方案列表 1:实施方案
	 * */
	public String loadActualizeProject(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(10);
		}
		int userId = buildInitParams();
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.ACTUALIZE,userId);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	private int buildInitParams() {
		UserDetailWrapper userDetail = getUserDetail();
		int userId = userDetail.getUserId();
		Set<FkRole> roles = userPersistenceIface.loadUser(getUserId()).getFkRoles();
		if (RoleType.isRoleByCode(RoleType.ROLE_AJJ_LOOK, roles)) {
			sysComit = false;
			userId = 0;//hack 因为方法里判断了不等于0
		}
		return userId;
	}
	/**
	 * 实施方案增加
	 * */
	public String createActualizeProject(){
		try{
			daActualTableNotice.setUserId(getUserId());
			daActualTableNotice.setType(1);
			daActualTableNotice.setAuditing(false);
			daActualTableNotice.setDeleted(false);
			actualizeTableNoticeFacadeIface.createActualizeProject(daActualTableNotice);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String createActualizeProjectInit(){
		try{
			if(daActualTableNotice!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String updateActualizeProject(){
		try{
			if(state!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
				daActualTableNotice.setAuditing(state);
			}
			daActualTableNotice.setUserId(getUserId());
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String deleteActualizeProject(){
		try{
			daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			daActualTableNotice.setDeleted(true);
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 表格下载 2：表格下载
	 * */
	
	public String loadTableDownload(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(10);
		}
		int userId = buildInitParams();
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.TABLE,userId);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	/**
	 * 表格下载增加
	 * */
	public String createTableDownload(){
		try{
			daActualTableNotice.setUserId(getUserId());
			daActualTableNotice.setType(2);
			daActualTableNotice.setAuditing(false);
			daActualTableNotice.setDeleted(false);
			actualizeTableNoticeFacadeIface.createActualizeProject(daActualTableNotice);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String createTableDownloadInit(){
		try{
			if(daActualTableNotice!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String updateTableDownload(){
		try{
			if(state!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
				daActualTableNotice.setAuditing(state);
			}
			daActualTableNotice.setUserId(getUserId());
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String deleteTableDownload(){
		try{
			daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			daActualTableNotice.setDeleted(true);
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 通知通告 3：通知通告
	 * */
	
	public String loadNotice(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(10);
		}
		int userId = buildInitParams();
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.NOTICE,userId);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	
	
	/**
	 * 信息公开  栏目
	 * */
	public String loadMainNotice(){
		return SUCCESS;
	}
	
	/**
	 * 信息公开  栏目
	 * */
	public String loadMainNotice1(){
		return SUCCESS;
	}
	
	
	/**
	 * 通知通告 3：通知通告
	 * */
	public String loadStatisticNotices(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(15);
		}
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.NOTICE,0);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	/**
	 * 通知通告增加
	 * */
	public String createNotice(){
		try{
			daActualTableNotice.setUserId(getUserId());
			daActualTableNotice.setType(3);
			daActualTableNotice.setAuditing(false);
			daActualTableNotice.setDeleted(false);
			actualizeTableNoticeFacadeIface.createActualizeProject(daActualTableNotice);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticNotice(){
		try{
			if(daActualTableNotice!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createNoticeInit(){
		try{
			if(daActualTableNotice!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateNotice(){
		try{
			if(state!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
				daActualTableNotice.setAuditing(state);
			}
			daActualTableNotice.setUserId(getUserId());
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String deleteNotice(){
		try{
			daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			daActualTableNotice.setDeleted(true);
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 法规标准增加
	 * */
	public String createLow(){
		try{
			daActualTableNotice.setUserId(getUserId());
			daActualTableNotice.setType(4);
			daActualTableNotice.setAuditing(false);
			daActualTableNotice.setDeleted(false);
			actualizeTableNoticeFacadeIface.createActualizeProject(daActualTableNotice);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String createLowInit(){
		try{
			if(daActualTableNotice!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String updateLow(){
		try{
			if(state!=null){
				daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
				daActualTableNotice.setAuditing(state);
			}
			daActualTableNotice.setUserId(getUserId());
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String deleteLow(){
		try{
			daActualTableNotice=actualizeTableNoticeFacadeIface.loadActualizeProject(daActualTableNotice);
			daActualTableNotice.setDeleted(true);
			actualizeTableNoticeFacadeIface.updateActualizeProject(daActualTableNotice);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadLow(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(10);
		}
		int userId = buildInitParams();
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.LOW, userId);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticLow(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(15);
		}
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.LOW,0);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	public String loadStatisticActualizeProject(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(15);
		}
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.ACTUALIZE,0);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	public String loadStatisticTableDownload(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(15);
		}
		try{
			actualizeTableNoticeList=actualizeTableNoticeFacadeIface.loadActualizeProjectList(pagination,daActualTableNotice,Nbyhpc.TABLE,0);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	
	public void setActualizeTableNoticeFacadeIface(
			ActualizeTableNoticeFacadeIface actualizeTableNoticeFacadeIface) {
		this.actualizeTableNoticeFacadeIface = actualizeTableNoticeFacadeIface;
	}
	public List<DaActualTableNotice> getActualizeTableNoticeList() {
		return actualizeTableNoticeList;
	}
	public void setActualizeTableNoticeList(
			List<DaActualTableNotice> actualizeTableNoticeList) {
		this.actualizeTableNoticeList = actualizeTableNoticeList;
	}
	
}
