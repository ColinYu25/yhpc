package com.safetys.nbyhpc.web.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishment;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishmentDocument;
import com.safetys.nbyhpc.facade.iface.CompanyPunishmentDocumentFacadeIface;
import com.safetys.nbyhpc.facade.iface.CompanyPunishmentFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class CompanyPunishmentAction extends DaAppAction{
	
	private static final long serialVersionUID = 6683641507146857298L;
	
	
	private CompanyPunishmentFacadeIface companyPunishmentFacadeIface;// 企业行政处罚接口
	
	private NomalDangerFacadeIface nomalDangerFacadeIface;
	
	private CompanyPunishmentDocumentFacadeIface companyPunishmentDocumentFacadeIface;
	
	private List<DaCompanyPunishment> companiePunishments;// 企业行政处罚集合
	
	private List<DaCompanyPunishmentDocument> companiePunishmentDocuments;
	
	private List<DaCompanyPunishment> companiePunishments1=new ArrayList<DaCompanyPunishment>();
	
	private DaCompanyPunishment companyPunishment;
	
	
	private DaCompany company;
	
	private int count=0;
	
	private int count1=0;

	public String updateCompanyPunishment(){
		PrintWriter out=null;
		 try{
			 
			 
//			 String a=request.getParameter("a");
//			 System.out.println(a);
//			 System.out.println("111");
//			 companyPunishmentFacadeIface.updatePunishment(companyPunishment);
//			 response.setContentType("text/html;charset=UTF-8");
			 out = getResponse().getWriter(); 
		     out.flush();   
		     out.close();
		 }catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	 	return null;
	}
	
	public String loadCompanyPunishments()throws Exception{
		try {
//			if(company == null){
//				company = new DaCompany();
//			}
//			company = companyPunishmentFacadeIface.loadCompany(company);
//			System.out.println(company);
//			if(companyPunishment == null){
//				companyPunishment = new DaCompanyPunishment();
			
//			companyPunishment = companyPunishmentFacadeIface.loadCompanyPunishment(companyPunishment);
			if(company == null){
				company = new DaCompany();
				company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			companiePunishments=companyPunishmentFacadeIface.loadCompanyPunishment(company);
			
			 Date   date   =   new   Date(); 
	         SimpleDateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd"); 
	         String time= df.format(date);
	         String[] Time = time.split("[- :]");
	         String year1=Time[0];
	         String month1=Time[1];
	         String day1=Time[2];
	         String time1=String.valueOf((Integer.parseInt(year1)-1))+"-"+month1+"-"+day1;
	         Date dateAfter=df.parse(time);   
	 	     Date dateBefor=df.parse(time1);
//	 	    System.out.println("111");
	 	    for(DaCompanyPunishment a:companiePunishments){	 	   
		 	       Date date1;
		 	       date1=a.getPunishTime();		 	    
		 	      if(date1.before(dateAfter) && date1.after(dateBefor)){		
		 	    	 companiePunishments1.add(a);
		            }
			}

	 	   count=companiePunishments1.size()+1;
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String loadCompanyPunishmentsinfo()throws Exception{
		try {
			if(company == null){
				company = new DaCompany();
				company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			 Date   date   =   new   Date(); 
	         SimpleDateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd"); 
	         String time= df.format(date);
	         String[] Time = time.split("[- :]");
	         String year1=Time[0];
	         String month1=Time[1];
	         String day1=Time[2];
	         String time1=String.valueOf((Integer.parseInt(year1)-1))+"-"+month1+"-"+day1;
	         Date dateAfter=df.parse(time);   
	 	     Date dateBefor=df.parse(time1);
	 	     companiePunishments=companyPunishmentFacadeIface.loadCompanyPunishment(company);
	 	     for(DaCompanyPunishment a:companiePunishments){
//				 System.out.println(a.getPunishTime());		 	   
		 	       Date date1;
		 	       date1=a.getPunishTime();		 	    
		 	      if(date1.before(dateAfter) && date1.after(dateBefor)){		
		 	    	 companiePunishments1.add(a);
		            }
			}
			count=companiePunishments1.size();
//			System.out.println(count);
			companiePunishmentDocuments=companyPunishmentDocumentFacadeIface.loadCompanyPunishmentDocuments(company);
			count1=companiePunishmentDocuments.size();
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadCompanyPunishmentDocumentList()throws Exception{
		try {
			if(company == null){
				company = new DaCompany();
				company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			companiePunishmentDocuments=companyPunishmentDocumentFacadeIface.loadCompanyPunishmentDocuments(company);
			count=companiePunishmentDocuments.size()+1;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	


	public void setCompanyPunishmentFacadeIface(
			CompanyPunishmentFacadeIface companyPunishmentFacadeIface) {
		this.companyPunishmentFacadeIface = companyPunishmentFacadeIface;
	}

	public List<DaCompanyPunishment> getCompaniePunishments() {
		return companiePunishments;
	}

	public void setCompaniePunishments(List<DaCompanyPunishment> companiePunishments) {
		this.companiePunishments = companiePunishments;
	}

	public DaCompanyPunishment getCompanyPunishment() {
		return companyPunishment;
	}

	public void setCompanyPunishment(DaCompanyPunishment companyPunishment) {
		this.companyPunishment = companyPunishment;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public List<DaCompanyPunishment> getCompaniePunishments1() {
		return companiePunishments1;
	}

	public void setCompaniePunishments1(
			List<DaCompanyPunishment> companiePunishments1) {
		this.companiePunishments1 = companiePunishments1;
	}

	public void setCompaniePunishmentDocuments(
			List<DaCompanyPunishmentDocument> companiePunishmentDocuments) {
		this.companiePunishmentDocuments = companiePunishmentDocuments;
	}

	public void setCompanyPunishmentDocumentFacadeIface(
			CompanyPunishmentDocumentFacadeIface companyPunishmentDocumentFacadeIface) {
		this.companyPunishmentDocumentFacadeIface = companyPunishmentDocumentFacadeIface;
	}

	public List<DaCompanyPunishmentDocument> getCompaniePunishmentDocuments() {
		return companiePunishmentDocuments;
	}

	public void setNomalDangerFacadeIface(
			NomalDangerFacadeIface nomalDangerFacadeIface) {
		this.nomalDangerFacadeIface = nomalDangerFacadeIface;
	}
}
