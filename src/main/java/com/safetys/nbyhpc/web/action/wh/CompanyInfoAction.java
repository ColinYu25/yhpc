package com.safetys.nbyhpc.web.action.wh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.WhAccident;
import com.safetys.nbyhpc.domain.model.WhCompanyInfo;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.facade.iface.WhCompanyInfoFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.Nbyhpc;

public class CompanyInfoAction extends BaseAppAction{
	
	public static final String MESSAGE_TO_BACK = "message_to_back";
	public static final String MESSAGE_TO_REDIRECT = "message_to_redirect";
	public static final String OPERATE_ERROR = "opererror";
	public static final String MESSAGE = "message";
	
	private WhCompanyInfoFacadeIface companyInfoFacadeIface;
	private CompanyFacadeIface companyFacadeIface;
	private WhCompanyInfo entity;
	private DaCompany company;
	private Pagination pagination;
	private List result;
	private String firstArea;
	private String secondArea;
	private String thirdArea;
	private String year;
	private List<FkArea> areas;
	private StatisticFacadeIface statisticFacadeIface;
	private List<WhAccident> accidents = new ArrayList<WhAccident>();
	private Long areaCode;
	
	private String info;
	public String create() throws Exception {
		if (entity == null){
			entity = new WhCompanyInfo();
		}
		company = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
		entity.setCompany(company);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		entity.setYear(year);
		return INPUT;
	}
	
	public String update() throws Exception {
		if (entity == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要修改的信息");
			return MESSAGE_TO_BACK;
		}
		entity = companyInfoFacadeIface.findById(entity.getId());
		
		if (entity.getState() != null && entity.getState() == WhCompanyInfoFacadeIface.REPORTED){
			this.setMessageKey("该信息已上报，不能再修改！");
			return MESSAGE_TO_BACK;
		}
		return INPUT;
	}
	
	public String save() throws Exception {
//		System.out.println("保存");
		if (entity == null){
			this.setMessageKey("没有找到要保存的信息");
			return MESSAGE_TO_BACK;
		}
		if (entity.getReportdate() != null ){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getReportdate());
			int year = calendar.get(Calendar.YEAR);
			entity.setYear(year);
		}
		if (entity.getYear() == null){
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			entity.setYear(year);
		}
		company = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
//		System.out.println("company"+company.getCompanyName());
		boolean hasExist = companyInfoFacadeIface.hasExistedByYear(entity.getYear(), company.getId(), entity.getId());//判断当年是否已经了录入股
//		System.out.println("hasExist: "+hasExist);
		if (hasExist) {
			this.setMessageKey("您已经录入过 “ " + entity.getYear() + "年”的数据，不能再次录入！");
			return MESSAGE_TO_BACK;
		}
		
		entity.setCompany(company);
		if (entity.getId() > 0){
			companyInfoFacadeIface.update(entity, accidents);
		}else{
			companyInfoFacadeIface.create(entity, accidents);
		}
		return SUCCESS;
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
//				//企业用户名和工商注册号不一致的情况下，跳转到提示页面
//				if(company!=null&&company.getRegNum()!=null&&!"".equals(company.getRegNum())){
//					 if(!getUserDetail().getUsername().equals(company.getRegNum())){
//						   this.info="updateUserName";
//						   return "info";
//					   }
//				}else{
//					//企业用户名和工商注册号不一致的情况下，跳转到提示页面
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
	public String list() throws Exception {
		DaCompany company2 = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
		//add by huangjl   企业没有修改信息的话，跳转到提示页面。
		String returnURL=this.check(company2);
		if(returnURL!=null){
			return returnURL;
		}
		result = companyInfoFacadeIface.loadCompanyInfoByUserId(this.getUserId(), this.getPagination());
		return "list";
	}
	
	public String delete() throws Exception {
		if (entity == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要删除的信息");
			return MESSAGE_TO_BACK;
		}
		boolean hasReported = companyInfoFacadeIface.hasReported(entity.getId());
		if (hasReported){
			this.setMessageKey("该信息已上报，不能再修改！");
			return MESSAGE_TO_BACK;
		}
		companyInfoFacadeIface.delete(entity);
		return SUCCESS;
	}

	/**
	 * 已上报企业列表
	 * @return
	 */
	public String reportedCompanyList() throws Exception {
		if (StringUtils.isEmpty(year)){
			return "company-list";
		}
		result = companyInfoFacadeIface.loadReportedCompanyList(areaCode, Integer.parseInt(year), this.getPagination());
		return "company-list";
	}
	
	/**
	 * 未上报企业列表
	 * @return
	 */
	public String unReportCompanyList() throws Exception {
		if (StringUtils.isEmpty(year)){
			return "company-list";
		}
		result = companyInfoFacadeIface.loadUnreportCompanyList(areaCode, Integer.parseInt(year), this.getPagination());
		return "unreport-list";
	}
	
	/**
	 * 上报
	 * @return
	 * @throws Exception
	 */
	public String report() throws Exception {
		if (entity == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要上报的信息");
			return MESSAGE_TO_BACK;
		}
		boolean hasReported = companyInfoFacadeIface.hasReported(entity.getId());
		if (hasReported){
			this.setMessageKey("该信息已上报，无需再次上报！");
			return MESSAGE_TO_BACK;
		}
		
		boolean state = companyInfoFacadeIface.report(entity.getId());
		if (state){
			this.setMessageKey("上报成功");
			this.getRequest().setAttribute("url", "company_list.xhtml");
			return MESSAGE_TO_REDIRECT;
		}else{
			this.setMessageKey("上报错误！");
			return MESSAGE_TO_BACK;
		}
	}
	
	
	public String back() throws Exception {
		if (entity == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要退回的信息");
			return MESSAGE_TO_BACK;
		}
		boolean state = companyInfoFacadeIface.back(entity.getId());
		if (state){
			this.setMessageKey("退回成功");
			this.getRequest().setAttribute("url", "company_reportedCompanyList.xhtml?areaCode=" + this.areaCode + "&year=" + this.getYear());
			return MESSAGE_TO_REDIRECT;
		}else{
			this.setMessageKey("退回错误！");
			return MESSAGE_TO_BACK;
		}
	}
	
	public String view() throws Exception {
		if (entity == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要查看的信息");
			return MESSAGE_TO_BACK;
		}
		entity = companyInfoFacadeIface.findById(entity.getId());
		
		if (entity != null && entity.getCompany() != null){
			company = entity.getCompany();
			secondArea = company.getSecondArea()!=null ? companyInfoFacadeIface.getAreaNameById(company.getSecondArea()) : null;
			thirdArea = company.getThirdArea()!=null ? companyInfoFacadeIface.getAreaNameById(company.getThirdArea()) : null;
		}
		return "view";
	}
	
	public String statistic() throws Exception {
		if (StringUtils.isEmpty(year)){
			year = DateUtils.date2Str(new Date(), "yyyy");
		}
		result = companyInfoFacadeIface.statistic(year);
		initAreas();
		return "statistic";
	}
	
	private void initAreas() throws Exception {
		areas = statisticFacadeIface.loadAreas(Nbyhpc.AREA_CODE);
	}
	
	public void setCompanyInfoFacadeIface(WhCompanyInfoFacadeIface companyInfoFacadeIface) {
		this.companyInfoFacadeIface = companyInfoFacadeIface;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public WhCompanyInfo getEntity() {
		return entity;
	}

	public void setEntity(WhCompanyInfo entity) {
		this.entity = entity;
	}

	public Pagination getPagination() {
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public String getFirstArea() {
		return firstArea;
	}

	public String getSecondArea() {
		return secondArea;
	}

	public String getThirdArea() {
		return thirdArea;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
	}

	public List<WhAccident> getAccidents() {
		return accidents;
	}

	public void setAccidents(List<WhAccident> accidents) {
		this.accidents = accidents;
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
}
