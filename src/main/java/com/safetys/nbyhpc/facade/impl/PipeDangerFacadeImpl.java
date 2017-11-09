package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.facade.iface.PipeDangerFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class PipeDangerFacadeImpl implements PipeDangerFacadeIface {

	private PipeDangerPersistenceIface pipeDangerPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private BasePersistenceImpl persistenceImpl;
	
	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void createDanger(DaPipeDanger danger) throws ApplicationAccessException {
		danger.setDangerNo(getHiddenNum());
		pipeDangerPersistenceIface.createDanger(danger);
	}
	
	public List<DaIndustryParameter> loadIndustrysForDanger() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id = (select id from da_industry_parameter where depiction = '"+Nbyhpc.PIPE_DANGER_TYPE+"')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.PIPE_LINE_TYPE));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("dip.depiction"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,null);
	}
	
//	public List<DaPipelineInfoPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException {
//		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfoPass.class, "dcp");
//		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long)userDetail.getUserId()));
//		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
//	}

	public void deleteDangers(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			pipeDangerPersistenceIface.deleteDanger(new DaPipeDanger(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public void deleteDanger(DaPipeDanger danger) throws ApplicationAccessException {
		pipeDangerPersistenceIface.deleteDanger(danger);
	}
	
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException {
		return pipeDangerPersistenceIface.loadDanger(danger);
	}
	
	public DaPipelineInfo loadCompany(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		//return companyPersistenceIface.loadCompany(company);
		return null;
	}
	
	public List<DaPipelineInfo> loadCompanyByRegNum(DaPipelineInfo pipeLine)throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dc");
//		if (pipeLine != null) {
//			if (!StringUtils.isEmpty(pipeLine.getRegNum())) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.regNum",company.getRegNum()));
//			}
//		}
//		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
//		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		return null;
	}
	
	public List<DaPipeDanger> loadDangers(DaPipeDanger danger,DaPipelineInfo pipeLine,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDanger.class, "dd");
		if(pipeLine != null){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.pipeLine.id", pipeLine.getId()));
		}
		if(danger != null){
			if("0".equals(danger.getIsGorver())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)"));
			}else if("1".equals(danger.getIsGorver())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)"));
			}else if ("2".equals(danger.getIsGorver())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <to_date('"+computeDate("30")+"','yyyy-MM-dd')"));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dd.id"));
		return pipeDangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}
	public DaPipeDanger loadDangeres(DaPipeDanger danger) throws ApplicationAccessException {
		return pipeDangerPersistenceIface.loadDanger(danger);
	}
	
	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type",Nbyhpc.COMPANY_TRADE));
		if (userDetail != null) {
//			Set<FkRole> roles = userPersistenceIface.loadUser((long) userDetail.getUserId()).getFkRoles();
//			if (RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles)) {
//				
//			} else {
			String userIndustry = userDetail.getUserIndustry();
			if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("depiction",userIndustry));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id is null"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,null);
	}
	
	public List<DaPipeDanger> loadDangersOfCompanies(DaPipeDanger danger, DaCompany com, DaPipelineInfo pipeLine,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDanger.class, "dd");
		detachedCriteriaProxy.createCriteria("pipeLine", "pl").createAlias("pl.daPipelineCompanyinfo","plc").createAlias("plc.company","dc").createAlias("dc.daCompanyPass","dcp");
		if(null!=com){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.id", com.getId()));
		}else{//安监、城管、交通、发改委
			String userIndustry = userDetail.getUserIndustry();
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(null != userIndustry){
//				if("anjian".equals(userIndustry)){
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", 3));
//				}
				if("jiaotong".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", 4));
				}
				if("chengguan".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.or( RestrictionsProxy.eq("pl.type", 0), RestrictionsProxy.eq("pl.type", 2)));
				}
				if("fagai".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.type", 1));
				}
			}
		}
		if(danger != null){
			if (danger.getDangerAdd() != null && !"".equals(danger.getDangerAdd().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.dangerAdd",danger.getDangerAdd().trim(),MatchMode.ANYWHERE));
			}
			if (danger.getLinkMan() != null && !"".equals(danger.getLinkMan().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.linkMan",danger.getLinkMan().trim(),MatchMode.ANYWHERE));
			}
			if (danger.getDangerNo() != null && !"".equals(danger.getDangerNo().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dd.dangerNo",danger.getDangerNo().trim(),MatchMode.ANYWHERE));
			}
			if (danger.getFirstArea() != null && danger.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.firstArea",danger.getFirstArea()));
				if (danger.getSecondArea() != null && danger.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.secondArea", danger.getSecondArea()));
					if (danger.getThirdArea() != null && danger.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.thirdArea", danger.getThirdArea()));
						if (danger.getFouthArea() != null && danger.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.fouthArea", danger.getFouthArea()));
							if (danger.getFifthArea() != null && danger.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.fifthArea", danger.getFifthArea()));
							}
						}
					}
				}
			}
			if("0".equals(danger.getIsGorver())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select ddg.par_da_pipe_dan_id from da_pipe_danger_gorver ddg where ddg.is_deleted=0)"));
			}else if("1".equals(danger.getIsGorver())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select ddg.par_da_pipe_dan_id from da_pipe_danger_gorver ddg where ddg.is_deleted=0)"));
			}else if ("2".equals(danger.getIsGorver())){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in(select ddg.par_da_pipe_dan_id from da_pipe_danger_gorver ddg where ddg.is_deleted=0)"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <to_date('"+computeDate("30")+"','yyyy-MM-dd')"));
				if (danger.getStartTime() != null && !"".equals(danger.getStartTime()) && danger.getEndTime() != null && !"".equals(danger.getEndTime())) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date >=to_date('"+formatter.format(danger.getStartTime())+"','yyyy-MM-dd')"));
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <=to_date('"+formatter.format(danger.getEndTime())+"','yyyy-MM-dd')"));
				}else{
					if (danger.getStartTime() != null && !"".equals(danger.getStartTime())){
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date >=to_date('"+formatter.format(danger.getStartTime())+"','yyyy-MM-dd')"));
					}else if (danger.getEndTime() != null && !"".equals(danger.getEndTime())){
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.finish_date <=to_date('"+formatter.format(danger.getEndTime())+"','yyyy-MM-dd')"));
					}
				}
			}
			if("0".equals(danger.getIsRollcall())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select drc.par_da_pipe_dan_id from da_pipe_rollcall_company drc where drc.is_deleted=0)"));
			}else if("1".equals(danger.getIsRollcall())){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select drc.par_da_pipe_dan_id from da_pipe_rollcall_company drc where drc.is_deleted=0)"));
			}
			int nextYear = danger.getNowYear()+1;
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.create_time between to_date('"+danger.getNowYear()+"-01-01','yyyy-MM-dd') " 
					+ " and to_date('"+nextYear+"-01-01','yyyy-MM-dd')"));
		}
		if (danger != null && danger.getPipeLine() != null && null!=danger.getPipeLine().getName() && !danger.getPipeLine().getName().equals("")) {
			detachedCriteriaProxy.add(RestrictionsProxy.like("pl.name",danger.getPipeLine().getName().trim(),MatchMode.ANYWHERE));
		}
		if (danger != null && danger.getPipeLine() != null && null!=danger.getPipeLine().getDaPipelineCompanyinfo() && null!=danger.getPipeLine().getDaPipelineCompanyinfo().getCompany()) {
			DaCompany company = danger.getPipeLine().getDaPipelineCompanyinfo().getCompany();
			if(company != null){
				if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
					detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
				}
				if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",company.getFirstArea()));
					if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
						if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
							if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
								if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
									detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
								}
							}
						}
					}
				}
				if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
					String trade = "";
					for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
						if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
							trade = company.getTradeTypes().split(",")[i].trim();
							break;
						}
					}
					if (!"".equals(trade)) {
						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
					}
				}
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return pipeDangerPersistenceIface.loadDangers(detachedCriteriaProxy, pagination);
	}
	
	public List<Statistic> loadDangersOfCompaniesBySql(DaPipeDanger danger,DaPipelineInfo pipeLine,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		List<Statistic> statistics2 = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sql = "select dc.company_name,dd.danger_no,dd.danger_add,dd.link_man,dd.link_tel,drc.id,dd.id,dd.par_da_com_id"
			+ " from da_danger dd left join da_company_pass dcp on dcp.par_da_com_id=dd.par_da_com_id "
			+ " left join da_company dc on dc.id=dd.par_da_com_id left join da_rollcall_company drc "
			+ " on drc.par_da_dan_id=dd.id and drc.is_deleted=0 where dd.is_deleted=0 and dcp.is_affirm=1 "
			+ " and dcp.is_deleted=0 and dc.is_deleted=0";
		if(danger != null){
			if("0".equals(danger.getIsGorver())){
				sql += " and dd.id not in(select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)";
			}else if("1".equals(danger.getIsGorver())){
				sql += " and dd.id in (select ddg.par_da_dan_id from da_danger_gorver ddg where ddg.is_deleted=0)";
			}
			if("0".equals(danger.getIsRollcall())){
				sql += " and dd.id not in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)";
			}else if("1".equals(danger.getIsRollcall())){
				sql += " and dd.id in (select drc.par_da_dan_id from da_rollcall_company drc where drc.is_deleted=0)";
			}
			if (danger.getDangerAdd() != null && !"".equals(danger.getDangerAdd().trim())) {
				sql += " and dd.danger_add like '%"+danger.getDangerAdd()+"%'";
			}
			if (danger.getLinkMan() != null && !"".equals(danger.getLinkMan().trim())) {
				sql += " and dd.link_man like '%"+danger.getLinkMan()+"%'";
			}
		}
//		if(company != null){
//			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
//				sql += " and dc.company_name like '%"+company.getCompanyName()+"%'";
//			}
//			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
//				sql += " and dc.first_area = "+company.getFirstArea();
//				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
//					sql += " and dc.second_area = "+company.getSecondArea();
//					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
//						sql += " and dc.third_area = "+company.getThirdArea();
//						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
//							sql += " and dc.fouth_area = "+company.getFouthArea();
//							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
//								sql += " and dc.fifth_area = "+company.getFifthArea();
//							}
//						}
//					}
//				}
//			}
//		}		
//		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
//			sql += " and dc.fifth_area = "+userDetail.getFifthArea();
//		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
//			sql += " and dc.fouth_area = "+userDetail.getFouthArea();
//		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
//			sql += " and dc.third_area = "+userDetail.getThirdArea();
//		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
//			sql += " and dc.second_area = "+userDetail.getSecondArea();
//		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
//			sql += " and dc.first_area = "+userDetail.getFirstArea();
//		} 
//		sql += " order by dc.id desc ,dc.company_name ";
//		ResultSet res = companyPersistenceIface.findBySql(sql);	
//		try {
//			while (res.next()) {
//				statistic = new Statistic();
//				statistic.setCompanyName(res.getString(1));
//				statistic.setDangerNo(res.getString(2));
//				statistic.setDangerAdd(res.getString(3));
//				statistic.setLinkMan(res.getString(4));
//				statistic.setLinkTel(res.getString(5));
//				statistic.setIsRollcall(res.getString(6));
//				statistic.setId(res.getLong(7));
//				statistic.setCompanyId(res.getLong(8));
//				statistics.add(statistic);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		/**
		 * 分页
		 */
		int itemCount=0;
		int afterCount=0;
		pagination.setTotalCount(statistics.size());
		if(pagination.getItemCount()==0){
			 itemCount=0;
			 if(pagination.getPageSize()>pagination.getTotalCount()){
				 afterCount=10;
			 }else{
				 afterCount=pagination.getPageSize();
			 }
		}else if(pagination.getItemCount()>0){
			itemCount=pagination.getPageSize()*(pagination.getCurrentPage()-1);
			if(pagination.getTotalCount()<pagination.getCurrentPage()*pagination.getPageSize()){
				afterCount=10;
			}else{
				afterCount=pagination.getCurrentPage()*pagination.getPageSize();
			}
		}
		for (int i=itemCount;i<afterCount;i++) {
			statistic= new Statistic();
			statistic.setCompanyName(statistics.get(i).getCompanyName());
			statistic.setDangerNo(statistics.get(i).getDangerNo());
			statistic.setDangerAdd(statistics.get(i).getDangerAdd());
			statistic.setLinkMan(statistics.get(i).getLinkMan());
			statistic.setLinkTel(statistics.get(i).getLinkTel());
			statistic.setIsRollcall(statistics.get(i).getIsRollcall());
			statistic.setId(statistics.get(i).getId());
			statistic.setCompanyId(statistics.get(i).getCompanyId());
			statistics2.add(statistic);
		}
		return statistics2;
	}
	
	/**
	 * 日期处理模块(将日期加上某些天或减去天数)返回字符串
	 * @param param
	 * @return
	 */
	private String computeDate(String param){   
        int strTo;   
        try{   
            strTo = Integer.parseInt(param);   
        }   
        catch   (Exception   e)   {   
            e.printStackTrace();   
            strTo = 0;   
        }   
        Calendar strDate = Calendar.getInstance();   //java.util包   
        strDate.add(Calendar.DATE,strTo); //日期减   如果不够减会将月变动   
        String resultDate = strDate.get(Calendar.YEAR) + "-" + String.valueOf(strDate.get(Calendar.MONTH)+1) + "-" + strDate.get(Calendar.DATE);//生成(年-月-日)字符串
        return resultDate;   
    }
	
	private List<DaPipeDanger> loadDangerForNum(DaPipeDanger danger)throws ApplicationAccessException {
		List<DaPipeDanger> dangers = new ArrayList<DaPipeDanger>();
		DaPipeDanger sdanger;
		String sql = "select danger_no from (select dd.danger_no from da_pipe_danger dd where " 
				+ " dd.par_da_pipe_id = "+danger.getPipeLine().getId()+" order by dd.id desc) d where rownum=1";
		ResultSet res = persistenceImpl.findBySql(sql);
		try {
			while (res.next()) {
				sdanger = new DaPipeDanger();
				sdanger.setDangerNo(res.getString(1));
				dangers.add(sdanger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dangers;
	}
	
	/**
	 * 当前年份的（重大）隐患总数+1作为这个隐患的编号。
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	private String loadDangerForNum()throws ApplicationAccessException {
		String sql = "select LPAD(count(dd.id)+1,3,'0') from da_pipe_danger dd where dd.create_time >=trunc(sysdate,'YYYY') and dd.create_time<=add_months(trunc(sysdate,'YYYY'),12)-1";
		ResultSet res = persistenceImpl.findBySql(sql);
		try {
			while (res.next()) {
				return res.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "001";
	}
		
	@SuppressWarnings("unused")
	private String getHiddenNum(DaPipeDanger danger)throws ApplicationAccessException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date now = new Date();
		String year = formatter.format(now);
		String hiddenNumber = "";
		List<DaPipeDanger> dangers = loadDangerForNum(danger);
		if(dangers.size()>0){
			String hiddenNum = dangers.get(0).getDangerNo();
			if(hiddenNum!=null && !"".equals(hiddenNum)){
				String lastNum = hiddenNum.split("-")[0];
				if (lastNum.equals(Nbyhpc.DANGER_NUMBER + year)) {
					int newNo = Integer.parseInt(hiddenNum.split("-")[1]) + 1;
					if (String.valueOf(newNo).length() == 1) {
						hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-0" + newNo;
					} else if (String.valueOf(newNo).length() == 2) {
						hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-" + newNo;
					}
				} else {
					hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-01";
				}
			}else{hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-01";}
		}else{
			hiddenNumber = Nbyhpc.DANGER_NUMBER + year + "-01";
		}
		return hiddenNumber;
	}
	
	private String getHiddenNum()throws ApplicationAccessException {
		Calendar c = Calendar.getInstance();
		int yyyy = c.get(Calendar.YEAR);
		String hiddenNumber = loadDangerForNum();
		if(hiddenNumber!=null&&hiddenNumber.length()==3){
			hiddenNumber = Nbyhpc.GD_DANGER_NUMBER+yyyy+hiddenNumber;
		}else{
			hiddenNumber = Nbyhpc.GD_DANGER_NUMBER+yyyy+"001";
		}
		return hiddenNumber;
	}
	

	public void updateDanger(DaPipeDanger danger) throws ApplicationAccessException {
		DaPipeDanger oldDanger = loadDanger(danger);
		danger.setCreateTime(oldDanger.getCreateTime());
		pipeDangerPersistenceIface.mergeDanger(danger);
	}
	
	public long  loadCompanyQuarterlyAccount(DaPipelineInfo pipeLine)throws ApplicationAccessException{
		long count=0;
		//String sql = "select count(*)  from da_company_quarter_report t where t.company_id="+company.getId()+" and t.quarter= to_char(sysdate, 'q')";
//	    ResultSet res = companyPersistenceIface.findBySql(sql);
//	    try {
//		    while (res.next()) {
//			  count=res.getLong(1);
//		    }
//	    } catch (SQLException e) {
//		    e.printStackTrace();
//	    }
	    return count;
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public void setPipeDangerPersistenceIface(
			PipeDangerPersistenceIface pipeDangerPersistenceIface) {
		this.pipeDangerPersistenceIface = pipeDangerPersistenceIface;
	}

}
