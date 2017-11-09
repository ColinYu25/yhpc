package com.safetys.nbyhpc.facade.impl;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeAttech;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipeTrouble;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.DaRqPipelineInfo;
import com.safetys.nbyhpc.domain.model.DaYqPipelineInfo;
import com.safetys.nbyhpc.domain.model.WhCompanyInfo;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeLinePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PipeNomalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.impl.BasePersistenceImpl;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.YqStatisticBean;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author qiufc
 * 2011-07-08 qiufc 修改loadYqCompanyListByArea方法，增加管道类型字段，可以按照不同管道查询企业
 */
public class PipeLineFacadeImpl implements PipeLineFacadeIface {
	
	private CompanyPersistenceIface companyPersistenceIface;
	
	private PipeLinePersistenceIface pipeLinePersistenceIface;
	
	private PipeNomalDangerPersistenceIface pipeNomalDangerPersistenceIface;
	
	private PipeDangerPersistenceIface pipeDangerPersistenceIface;
	
	private String yqStatisticSql;
	
	private String rqStatisticSql;
	
	private BasePersistenceImpl persistenceImpl;
	
	private InputStream inputStream;

	private File tempFile;

	private Writer out;

	private String realPath;

	private String filePath;

	private String fileName;
	
	Logger log = Logger.getLogger(this.getClass());

	public void create(DaPipelineCompanyInfo entity) throws Exception {
		persistenceImpl.create(entity);
		
	}
	
	private void createTroubles(DaPipelineInfo pipeLine){
		Set<DaPipeTrouble> troubles = pipeLine.getDaPipeTroubles();
		if (troubles == null || troubles.size() == 0){
			return;
		}
		for (DaPipeTrouble item : troubles) {
			item.setDaPipelineInfo(pipeLine);
			persistenceImpl.create(item);
			
		}
	}
	
	private void createAttechs(DaPipelineInfo pipeLine){
		Set<DaPipeAttech> attechs = pipeLine.getDaPipeAttechs();
		if (attechs == null || attechs.size() == 0){
			return;
		}
		for (DaPipeAttech item : attechs) {
			item.setDaPipelineInfo(pipeLine);
			persistenceImpl.create(item);
		}
	}
	
	private void deleteIfExist(long lineId){
		if (lineId <= 0){
			return;
		}
		persistenceImpl.executeHql("update DaPipeTrouble obj set obj.deleted = true where obj.daPipelineInfo.id = ?", new Object[]{lineId});
		persistenceImpl.executeHql("update DaPipeAttech obj set obj.deleted = true where obj.daPipelineInfo.id = ?", new Object[]{lineId});
	}

	public void delete(DaPipelineCompanyInfo entity) throws Exception {
		persistenceImpl.executeHql("update DaPipelineCompanyInfo obj set obj.deleted = true where obj.daPipelineInfo.id = ?", new Object[]{entity.getId()});
	}


	public List<WhCompanyInfo> find(DaPipelineCompanyInfo entity, Pagination pagination) throws Exception {
		throw new java.lang.NullPointerException("未实现该方法");
	}

	public DaPipelineCompanyInfo findById(long id) throws Exception {
		return (DaPipelineCompanyInfo)findById(DaPipelineCompanyInfo.class, id);
	}


	public Object findById(Class clazz, long id) throws Exception {
		return persistenceImpl.findById(clazz, id);
	}


	public void update(DaPipelineCompanyInfo entity) throws Exception {
		persistenceImpl.update(entity);
	}

	public DaPipelineCompanyInfo findByCompanyId(long companyId, int type) throws Exception {
		String hql = "from DaPipelineCompanyInfo obj where obj.deleted = false and obj.company.id = ? and obj.type = ? ";
		List<DaPipelineCompanyInfo> list = persistenceImpl.find(hql, new Object[]{companyId, type}, null);
		if (list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	public void setPersistenceImpl(BasePersistenceImpl persistenceImpl) {
		this.persistenceImpl = persistenceImpl;
	}

	public void createPipeline(DaPipelineInfo entity) throws Exception {
		deleteIfExist(entity.getId());
		persistenceImpl.create(entity);
		createTroubles(entity);
		createAttechs(entity);
	}

	public void deletePipelineById(long id) throws Exception {
		persistenceImpl.executeHql("update DaPipelineInfo obj set obj.deleted = true where obj.id = ?", new Object[]{id});
	}
	
	public void deleteAll(String ids) throws ApplicationAccessException {
		String hql = "update DaPipelineInfo set IS_DELETED=1 where id in (" + ids + ")";
		persistenceImpl.executeHql(hql, null);
	}

	public DaPipelineInfo findPipelineById(long id) throws Exception {
		return (DaPipelineInfo)persistenceImpl.findById(DaPipelineInfo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<DaPipelineInfo> findPiplineByCompanyInfoId(long id) throws Exception {
		String hql = " from DaPipelineInfo obj where obj.daPipelineCompanyinfo.id = ? and obj.deleted = false order by id desc";
		return persistenceImpl.find(hql, new Object[]{id}, null);
	}
	
	public InputStream export(DaPipelineInfo entity, DaCompany com, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		try {
			this.realPath = entity.getRealPath();
			this.filePath = "excel/excel_template.ftl";
			this.fileName = entity.getFileName();
			String templateStr = getTemplate();
			
			StringBuffer header = new StringBuffer();
			header.append("<tr style='padding-left:5px; padding-right:5px;height:69px;text-align: center;font-size: 35px;font-weight: bold;'><td colspan='22' >危险化学品输送管道安全现状调查汇总表</td></tr>");
			header.append("<tr style='padding-left:5px; padding-right:5px;height:30px;text-align: left;font-size: 14px;font-weight: bold;'><td colspan='11' >填报单位（盖章）：</td><td colspan='11' >填表时间:</td></tr>");
			header.append("<tr style='padding-left:5px; padding-right:5px;text-align: center;font-size: 14px;font-weight: bold;'><td rowspan='2'>序号</td><td rowspan='2'>管道名称</td><td rowspan='2'>输送介质</td><td colspan='2'>管径和总长度（Km）</td>");
			header.append("<td colspan='2'>管道起点和终点</td><td colspan='2'>管道压力（MPa）</td><td colspan='2'>管道输送能力</td><td colspan='4'>目前状态</td>");
			header.append("<td rowspan='2'>铺设方式（架空、地面、埋地）</td><td colspan='2'>穿越城镇建成区域情况</td><td rowspan='2'>投运时间</td>");
			header.append("<td rowspan='2'>运行管理单位</td><td rowspan='2'>运行单位联系人</td><td rowspan='2'>联系电话</td></tr>");
			header.append("<tr style='padding-left:5px; padding-right:5px;text-align: center;font-size: 14px;font-weight: bold;'><td>管径（mm）</td><td>总长度（Km）</td><td>起点</td><td>终点</td><td>操作压力</td><td>设计压力</td><td>气态（万m³/年）</td>");
			header.append("<td>固态（万吨/年）</td><td>在建</td><td>拟建</td><td>运行</td><td>废弃</td><td>穿越长度（Km）</td><td>安全等级</td></tr>");
			
			List<DaPipelineInfo> result = this.loadAllPipeLinesExcel(entity, com, null, userDetail);
			StringBuffer content = new StringBuffer();
			int i = 1;
			for (DaPipelineInfo r : result) {
				content.append("<tr style='padding-left:5px; padding-right:5px; text-align: center;'>");
				content.append("<td>" + i + "</td>");
				content.append("<td>" + (r.getName()==null ? "" : r.getName()) + "</td>");
				content.append("<td>" + (r.getMedium()==null ? "" : r.getMedium()) + "</td>");
				content.append("<td>" + r.getDiameter()+ "</td>");
				content.append("<td>" + r.getLength() + "</td>");
				content.append("<td>" + (r.getStartPoint()==null ? "" : r.getStartPoint()) + "</td>");
				content.append("<td>" + (r.getEndPoint()==null ? "" : r.getEndPoint()) + "</td>");
				content.append("<td>" + r.getMaxPressure() + "</td>");
				content.append("<td>" + r.getPressure() + "</td>");
				content.append("<td>" + r.getGasTranAbility() + "</td>");
				content.append("<td>" + r.getLiquidTranAbility() + "</td>");
				content.append("<td></td>");
				content.append("<td></td>");
				content.append("<td></td>");
				content.append("<td></td>");
				int buidType = r.getBuildType();
				String buidTypeStr = "";
				if(buidType==1){
					buidTypeStr = "架空";
				}
				if(buidType==2){
					buidTypeStr = "埋地 深度" + r.getMdNumSpan() + "m";
					
				}
				if(buidType==3){
					buidTypeStr = "地面";
				}
				content.append("<td>" + buidTypeStr + "</td>");
				content.append("<td>" + r.getYczjcqNum() + "</td>");
				content.append("<td>" + (r.getSafetyLevelNo()==null ? "" : r.getSafetyLevelNo()) + "</td>");
				content.append("<td>" + (r.getBeginUseDate()==null ? "" : r.getBeginUseDate()) + "</td>");
				content.append("<td>" + (r.getDaPipelineCompanyinfo().getCompany().getCompanyName()==null ? "" : r.getDaPipelineCompanyinfo().getCompany().getCompanyName()) + "</td>");
				content.append("<td>" + (r.getDaPipelineCompanyinfo().getCompany().getFddelegate()==null ? "" : r.getDaPipelineCompanyinfo().getCompany().getFddelegate()) + "</td>");
				content.append("<td>" + (r.getDaPipelineCompanyinfo().getCompany().getPhoneCode()==null ? "" : r.getDaPipelineCompanyinfo().getCompany().getPhoneCode()) + "</td>");
				content.append("</tr>");
				i++;
			}
			content.append("<tr style='padding-left:5px; padding-right:5px;height:30px;text-align: left;font-size: 14px;font-weight: bold;'><td colspan='11' >填表人：</td><td colspan='11' >填表时间:</td></tr>");
			String table = templateStr.replace("${content}", header.toString() + content);
			if (out != null) {
				out.append(table);
			}
			inputStream = new BufferedInputStream(new FileInputStream(tempFile));
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return inputStream;
	}

	/**
	 * 共用查询管道记录  Excel
	 * @param detachedCriteriaProxy
	 * @param entity
	 * @param com
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadAllPipeLinesExcel(DaPipelineInfo entity, DaCompany com, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = null;
		if(entity.getType()==0){
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRqPipelineInfo.class, "dpli");
		}else{
			detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dpli");
		}
		detachedCriteriaProxy.createAlias("daPipelineCompanyinfo", "dplci").createAlias("dplci.company", "dc");
		if(null != com){//企业用户
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.id", com.getId()));
		}else{//安监、城管、交通、发改委
			String userIndustry = userDetail.getUserIndustry();
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(null != userIndustry){
//				if("anjian".equals(userIndustry)){
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 3));
//				}//安监可查看所有类型管道
				if("jiaotong".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 4));
				}
				if("chengguan".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dpli.type", 0), RestrictionsProxy.eq("dpli.type", 2)));
				}
				if("fagai".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 1));
				}
			}
		}
		if (entity != null) {
			//管道名称
			if(null!=entity.getName() && !"".equals(entity.getName().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.name", entity.getName().trim(), MatchMode.ANYWHERE));
			//管道起点
			if(null!=entity.getStartPoint() && !"".equals(entity.getStartPoint().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.startPoint", entity.getStartPoint().trim(), MatchMode.ANYWHERE));
			//管道止点
			if(null!=entity.getEndPoint() && !"".equals(entity.getEndPoint().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.endPoint", entity.getEndPoint().trim(), MatchMode.ANYWHERE));
			//管道材质
			if(null!=entity.getMaterial() && !"".equals(entity.getMaterial().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.material", entity.getMaterial().trim(), MatchMode.ANYWHERE));
			//传输介质
			if(null!=entity.getMedium() && !"".equals(entity.getMedium().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.medium", entity.getMedium().trim(), MatchMode.ANYWHERE));
			//管道种类
			if(entity.getType()>-1)
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", entity.getType()));
			//铺设方式
			if(entity.getBuildType()>0)//0查询全部
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.buildType", entity.getBuildType()));
		}
		if (entity != null && null!=entity.getDaPipelineCompanyinfo() && null!=entity.getDaPipelineCompanyinfo().getCompany()) {
			DaCompany company = entity.getDaPipelineCompanyinfo().getCompany();
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(),MatchMode.ANYWHERE));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		return pipeLinePersistenceIface.loadPipeLines(detachedCriteriaProxy, pagination);
	}
	
	private String getTemplate() throws Exception {
		Configuration freemarker_cfg = null;
		freemarker_cfg = new Configuration();
		// 定义freemarker模板目录
		freemarker_cfg.setDirectoryForTemplateLoading(new File(realPath));
		// // 获取模板
		Template template = freemarker_cfg.getTemplate("template/" + filePath, "UTF-8");
		// 得到临时文件
		Properties p = System.getProperties();
		String tempPath = p.getProperty("java.io.tmpdir");
		String separator = p.getProperty("file.separator");
		tempFile = new File(tempPath + separator + fileName);
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8"));
		return template.toString();
	}

	
	public void updatePipeline(DaPipelineInfo entity) throws Exception {
		//deleteIfExist(entity.getId());//troubles， attech 表中的信息
		persistenceImpl.update(entity);
		createTroubles(entity);
		createAttechs(entity);
		
	}

	public Object findPipelineById(Class clazz, long id) throws Exception {
		return persistenceImpl.findById(clazz, id);
	}

	public List<FkArea> loadAreadsByParentCode(Long code) throws Exception {
		String hql = " from FkArea obj where obj.fatherId in (select f.id from FkArea f where f.areaCode = ? and f.deleted = false) and obj.deleted = false order by sort_num";
		return persistenceImpl.find(hql, new Object[]{code}, null);
	}

	public List<DaCompany> loadCompanyies(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
	/*	并未在使用的代码
	 * StringBuffer hql = new StringBuffer();
		hql.append("select obj from DaCompany obj inner join obj.daCompanyLogout where obj.deleted = false ");
		List<Object> params = new ArrayList<Object>();
		if (company != null){
			if (!StringUtils.isEmpty(company.getCompanyName())){
				hql.append(" and obj.companyName = ?");
				params.add(company.getCompanyName());
			}
			if (!StringUtils.isEmpty(company.getRegAddress())){
				hql.append(" and obj.regAddress = ?");
				params.add(company.getRegAddress());
			}
			
			if (!StringUtils.isEmpty(company.getRegAddress())){
				hql.append(" and obj.regAddress = ?");
				params.add(company.getRegAddress());
			}
			if (company.getFifthArea() != null && company.getFifthArea() > 0){
				hql.append(" and obj.fifthArea = ?");
				params.add(company.getFifthArea());
			}else if (company.getFouthArea() != null && company.getFouthArea() > 0){
				hql.append(" and obj.fouthArea = ?");
				params.add(company.getFouthArea());
			}else if(company.getThirdArea() != null && company.getThirdArea() > 0){
				hql.append(" and obj.thirdArea = ?");
				params.add(company.getThirdArea());
			}else if (company.getSecondArea() != null && company.getSecondArea() > 0){
				hql.append(" and obj.secondArea = ?");
				params.add(company.getSecondArea());
			}else if (company.getFirstArea()!= null && company.getFirstArea() > 0){
				hql.append(" and obj.firstArea = ?");
				params.add(company.getFirstArea());
			}
			
		}
		hql.append(" order by obj.id desc");*/
		
		
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		List<DaCompany> companys;
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(),MatchMode.ANYWHERE));
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
//				String tradeTypess = company.getTradeTypes();
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		/* 取消区域限制
		 * if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",userDetail.getFirstArea()));
		} */
		
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy,pagination);

		return companys;
	}
	
	/**
	 * 根据登用用户区域对应的企业信息
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompanyByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createAlias("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long) userDetail.getUserId()));
		List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		if(null!=companies && companies.size()>0){
			return companies.get(0);
		} 
		return null;
	}
	
	/**
	 * 管道列表(油气)
	 * @param entity 
	 * @param company 
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadYqPipeLines(DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaYqPipelineInfo.class, "dpli");
		return this.loadPipeLines(detachedCriteriaProxy, entity, com, checkType, pagination, userDetail);
	}
	
	/**
	 * 管道列表(燃气)
	 * @param entity 
	 * @param company 
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadRqPipeLines(DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaRqPipelineInfo.class, "dpli");
		return this.loadPipeLines(detachedCriteriaProxy, entity, com, checkType, pagination, userDetail);
	}
	
	/**
	 * 管道列表(油气和燃气)
	 * @param entity 
	 * @param company 
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadAllPipeLines(DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipelineInfo.class, "dpli");
		return this.loadPipeLines(detachedCriteriaProxy, entity, com, checkType, pagination, userDetail);
	}
	
	/**
	 * 共用查询管道记录
	 * @param detachedCriteriaProxy
	 * @param entity
	 * @param com
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipeLines(DetachedCriteriaProxy detachedCriteriaProxy, DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception {
		detachedCriteriaProxy.createAlias("daPipelineCompanyinfo", "dplci").createAlias("dplci.company", "dc");
		if(null != com){//企业用户
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.id", com.getId()));
		}else{//安监、城管、交通、发改委
			String userIndustry = userDetail.getUserIndustry();
			//高压管道：港区内油气管道4（type=4）- 交通 //工业管道3（type=3）- 安监 //长输管道1（type=1）- 发改委 //城镇燃气管道2  - 城管 （type=2） LINE_TYPE=2
			//中低压管道：中低压燃气管道  - 城管  (LINE_TYPE) LINE_TYPE=1
			if(null != userIndustry){
//				if("anjian".equals(userIndustry)){
//					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 3));
//				}//安监可查看所有类型管道
				if("jiaotong".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 4));
				}
				if("chengguan".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dpli.type", 0), RestrictionsProxy.eq("dpli.type", 2)));
				}
				if("fagai".equals(userIndustry)){
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", 1));
				}
			}
		}
		if (entity != null) {
			//管道名称
			if(null!=entity.getName() && !"".equals(entity.getName().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.name", entity.getName().trim(), MatchMode.ANYWHERE));
			//所在区域
			if(null!=entity.getAreaCode2() && entity.getAreaCode2()>0)
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.areaCode", entity.getAreaCode2()));
			//管道起点
			if(null!=entity.getStartPoint() && !"".equals(entity.getStartPoint().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.startPoint", entity.getStartPoint().trim(), MatchMode.ANYWHERE));
			//管道止点
			if(null!=entity.getEndPoint() && !"".equals(entity.getEndPoint().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.endPoint", entity.getEndPoint().trim(), MatchMode.ANYWHERE));
			//管道材质
			if(null!=entity.getMaterial() && !"".equals(entity.getMaterial().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.material", entity.getMaterial().trim(), MatchMode.ANYWHERE));
			//传输介质
			if(null!=entity.getMedium() && !"".equals(entity.getMedium().trim()))
				detachedCriteriaProxy.add(RestrictionsProxy.like("dpli.medium", entity.getMedium().trim(), MatchMode.ANYWHERE));
			//管道种类
			if(entity.getType()>-1)
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.type", entity.getType()));
			//铺设方式
			if(entity.getBuildType()>0)//0查询全部
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dpli.buildType", entity.getBuildType()));
		}
		if (entity != null && null!=entity.getDaPipelineCompanyinfo() && null!=entity.getDaPipelineCompanyinfo().getCompany()) {
			DaCompany company = entity.getDaPipelineCompanyinfo().getCompany();
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(),MatchMode.ANYWHERE));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		List<DaPipelineInfo> pipeLines = pipeLinePersistenceIface.loadPipeLines(detachedCriteriaProxy, pagination);
		if(null!=checkType && !"".equals(checkType)){
			//统计管道的一般隐患数
			if("nomalDanger".equals(checkType)){
				for (DaPipelineInfo pipeLine : pipeLines) {
					List<DaPipeNomalDanger> normalDangers = loadNomalDangersByPipeLine(pipeLine);
					int size = (normalDangers == null) ? 0 : normalDangers.size();
//					System.out.println("size统计管道的一般隐患数: "+size);
					pipeLine.setCount(size);
				}
			}
			//统计管道的重大隐患数
			if("danger".equals(checkType)){
				for (DaPipelineInfo pipeLine : pipeLines) {
					List<DaPipeDanger> dangers = loadDangersByPipeLine(pipeLine);
					int size = (dangers == null) ? 0 : dangers.size();
					pipeLine.setCount(size);
				}
			}
		}
		
		return pipeLines;
	}
	
	/**
	 * 查询每个管道的目标隐患数据（重大隐患）
	 * @param pipeLine
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	private List<DaPipeDanger> loadDangersByPipeLine(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.id", pipeLine.getId()));
		List<DaPipeDanger> dangers = pipeDangerPersistenceIface.loadDangers(detachedCriteriaProxy, null);
		return dangers;
	}
	
	/**
	 * 查询每个管道的目标隐患数据
	 * @param pipeLine
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	private List<DaPipeNomalDanger> loadNomalDangersByPipeLine(DaPipelineInfo pipeLine) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPipeNomalDanger.class, "dnd");
		detachedCriteriaProxy.createAlias("dnd.pipeLine", "pl");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("pl.id", pipeLine.getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dnd.danger", true));
		List<DaPipeNomalDanger> normalDangers = pipeNomalDangerPersistenceIface.loadNomalDangers(detachedCriteriaProxy, null);
		return normalDangers;
	}

	public List rqStatistic(DaPipelineInfo lineInfo) throws Exception {
		String sql  = rqStatisticSql;
		sql  = sql.replaceAll(":type", String.valueOf(RQGD));
		log.info("燃气管道统计sql: " + sql);
		return doStatistic(sql);
	}
	
	public List yqStatistic(DaPipelineInfo lineInfo) throws Exception {
		String sql = yqStatisticSql;
		if (lineInfo != null && !"".equals(lineInfo.getLineType())){
			sql = sql.replaceAll(":lineType", lineInfo.getLineType());
		}else{
			sql = sql.replaceAll("and p.type = :lineType", "");
		}
		log.info("管道统计sql: " + sql);
		return doStatistic(sql);
	}
	
	private List doStatistic(String sql) throws Exception {
		ResultSet rs = persistenceImpl.findBySql(sql);
		if (rs == null){
			return null;
		}
		List list = new ArrayList();
		int cols = rs.getMetaData().getColumnCount();
		String[] colNames = new String[cols];
		for (int i = 1; i < cols; i++) {
			colNames[i] = rs.getMetaData().getColumnName(i);
		}
		while (rs.next()) {
			YqStatisticBean item = new YqStatisticBean();
			item.setAreaName(rs.getString("AREA_NAME"));
			String areaCode = rs.getString("AREA_CODE");
			if (areaCode != null && areaCode.trim().length() > 0){
				item.setAreaCode(Long.parseLong(areaCode));
			}
			
			item.setCqdw(rs.getInt("CQCOMPANYNUM"));
			item.setSydw(rs.getInt("COMPANYNUM"));
			item.setYqgds(rs.getInt("LINENUM"));
			item.setGdcd(rs.getDouble("LENGTH"));
			item.setBlghxk(rs.getInt("GHLICENCE"));
			item.setAqscxk(rs.getInt("SCLICENCE"));
			item.setJgys(rs.getInt("HASYS"));
			item.setSydj(rs.getInt("HASDJ"));
			item.setSame(rs.getInt("NOTSAMENUM"));
			item.setCyrymjcs(rs.getInt("CYRYMJCSNUM"));
			item.setWzzy(rs.getInt("WZZYNUM"));
			item.setTroubles(rs.getInt("TROUBLENUM"));
			item.setZfxt(rs.getInt("ZFXTNUM"));
			item.setFhqsg(rs.getInt("FHQSGNUM"));
			item.setAccidents(rs.getInt("ACCIDENTNUM"));
			item.setHasCheckNum(rs.getInt("HASCHECK"));
			item.setNoCheckNum(rs.getInt("NOCHECK"));
			list.add(item);
		}
		return list;
	}
	
	/**
	 * 根据管道类型、区域加载企业信息
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadCompanyListByArea(String lineType, Long areaCode, Pagination page) throws Exception {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "c");
		String sql =" select distinct(pc.cid) from ("
				   +"   select p.company_id as pcid from da_pipeline_info p where p.is_deleted=0 " + ((null!=lineType && !"".equals(lineType)) ? "and p.type = " + lineType : "") + " group by p.company_id"
				   +" ) p left join ("  
				   +"   select pc2.id, pc2.company_id as cid, pc2.cq_company_id as qcid from da_pipeline_companyinfo pc2 where pc2.is_deleted=0"
				   +" ) pc on pc.id=p.pcid and pc.cid is not null";
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (" + sql + ")"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("c.firstArea", Nbyhpc.AREA_CODE));
		if(null!=areaCode && areaCode>0){
			if(areaCode - Nbyhpc.AREA_CODE != 0l){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("c.secondArea", areaCode));
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("c.secondArea", 0l), RestrictionsProxy.isNull("c.secondArea")));
			}
		}
			
		detachedCriteriaProxy.addOrder(OrderProxy.desc("c.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, page);
	}
	
	/**
	 * 根据区域代码，加载燃气管道企业列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadRqCompanyListByArea(Long areaCode, Pagination page) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from DaPipelineCompanyInfo info where info.deleted = false ");
		List<Object> params = new ArrayList<Object>();
		
		hql.append(" and info.type = ? ");
		params.add(RQGD);
		
		if (areaCode != null && areaCode > 0){
			hql.append(" and info.areaCode = ? ");
			params.add(areaCode);
		}else{
			hql.append(" and length(info.areaCode) >= 6 ");
		}
		hql.append(" order by id");
		return persistenceImpl.find(hql.toString(), params.toArray(), page);
	}
	
	/**
	 * 根据区域代码，加载油气管道企业列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadYqCompanyListByArea(Long areaCode, int pipeLineType, Pagination page) throws Exception {
		return doLoadCompanyListByArea(YQGD, pipeLineType, areaCode, page);
	}
	
	/**
	 * 根据管道类型、区域加载企业信息
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private List<DaPipelineCompanyInfo> doLoadCompanyListByArea(int type, int pipeLineType, Long areaCode, Pagination page) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select info from DaPipelineCompanyInfo info inner join info.daPipelineInfos line where info.deleted = false ");
		List<Object> params = new ArrayList<Object>();
		if (!(type == 0 && pipeLineType == 0)){
			hql.append(" and info.type = ? ");
			params.add(type);
		}
		hql.append(" and info.company.firstArea = ?");
		params.add(Nbyhpc.AREA_CODE);
		
		if (pipeLineType > 0){
			hql.append(" and line.type = ? ");
			params.add(pipeLineType);
		}
		if (areaCode != null && areaCode > 0){
			hql.append(" and info.company.secondArea = ? ");
			params.add(areaCode);
		}
		
		hql.append(" order by info.id");
		return persistenceImpl.find(hql.toString(), params.toArray(), page);
	}
	
	/**
	 * 根据区域、管道类型，加载管道列表
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipLineByArea(DaPipelineInfo entity,String type, Long areaCode, Pagination page) throws Exception {
		List<Object> params = new ArrayList<Object>();
		
		String hql = "from DaPipelineInfo obj where obj.deleted = false ";
		hql += " and obj.daPipelineCompanyinfo.company.firstArea = ?";
		params.add(Nbyhpc.AREA_CODE);
		
		if (areaCode != null && areaCode > 0){
			if(areaCode-Nbyhpc.AREA_CODE != 0l){
				hql += " and obj.daPipelineCompanyinfo.company.secondArea = ?";
				params.add(areaCode);
			}else{
				hql += " and (obj.daPipelineCompanyinfo.company.secondArea = 0 or obj.daPipelineCompanyinfo.company.secondArea is null) ";
			}
		}
		if(entity!=null){
			//管道种类
			if(entity.getType()>-1){
				hql += " and obj.type = "+entity.getType();
			}
			//铺设方式
			if(entity.getBuildType()>0){//0查询全部
				hql += " and obj.buildType = "+entity.getBuildType();
			}
			if(entity.getName()!=null&&!entity.getName().trim().equals("")){
				hql += " and obj.name like '%"+entity.getName()+"%'";
			}
			if(entity.getDaPipelineCompanyinfo()!=null&&entity.getDaPipelineCompanyinfo().getCompany()!=null
					&&entity.getDaPipelineCompanyinfo().getCompany().getCompanyName()!=null
					&&!entity.getDaPipelineCompanyinfo().getCompany().getCompanyName().trim().equals("")){
				hql += " and obj.daPipelineCompanyinfo.company.companyName like '%"+entity.getDaPipelineCompanyinfo().getCompany().getCompanyName()+"%'";
			}
		}
		if (null != type && !"".equals(type)){
			hql += " and obj.type = " + type;
		}
		
		hql += " order by obj.daPipelineCompanyinfo.id";
		
		return persistenceImpl.find(hql, params.toArray(), page);
	}

	/**
	 * 根据区域、管道类型，加载油气管道列表
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadYqPipLineByArea(int type, Long areaCode, Pagination page) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String hql = "from DaPipelineInfo obj where obj.deleted = false ";
		if (type <= 0){
		} else {
			hql = "from DaYqPipelineInfo obj where obj.deleted = false ";
		}
		
		hql += " and obj.daPipelineCompanyinfo.company.firstArea = ?";
		params.add(Nbyhpc.AREA_CODE);
		
		if (areaCode != null && areaCode > 0){
			if(areaCode-Nbyhpc.AREA_CODE != 0l){
				hql += " and obj.daPipelineCompanyinfo.company.secondArea = ?";
				params.add(areaCode);
			}else{
				hql += " and (obj.daPipelineCompanyinfo.company.secondArea = 0 or obj.daPipelineCompanyinfo.company.secondArea is null) ";
			}
		}
		
		if (type > 0){
			hql += " and obj.type = ? ";
			params.add(type);
		}
		
		hql += " order by obj.daPipelineCompanyinfo.id";
		
		return persistenceImpl.find(hql, params.toArray(), page);
	}
	
	/**
	 * 根据区域，加载燃气管道类型
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadRqPipLineByArea(Long areaCode, Pagination page) throws Exception {
		String hql = "from DaRqPipelineInfo obj where  obj.deleted = false";
		List<Object> params = new ArrayList<Object>();
		
		if (areaCode != null && areaCode > 0){
			hql += " and obj.areaCode.areaCode = ? ";
			params.add(areaCode);
		}else{
			hql += " and obj.areaCode.areaCode > 0 ";
		}
		
		hql += " order by obj.daPipelineCompanyinfo.id";
		return persistenceImpl.find(hql, params.toArray(), page);
	}

	public List<DaPipelineCompanyInfo> loadRqPipLineByProperty(String propertyName, boolean value, Long areaCode, Pagination page) throws Exception {
		String hql = "from DaRqPipelineInfo obj where  obj.deleted = false";
		List<Object> params = new ArrayList<Object>();
		
		hql += " and obj.type = ?";
		params.add(0);

		hql += " and obj.daPipelineCompanyinfo.company.firstArea = ?";
		params.add(Nbyhpc.AREA_CODE);
		
		if (areaCode != null && areaCode > 0){
			if(areaCode-Nbyhpc.AREA_CODE != 0l){
				hql += " and obj.daPipelineCompanyinfo.company.secondArea = ?";
				params.add(areaCode);
			}else{
				hql += " and (obj.daPipelineCompanyinfo.company.secondArea = 0 or obj.daPipelineCompanyinfo.company.secondArea is null) ";
			}
		}
		
		if (!StringUtils.isEmpty(propertyName)){
			hql += " and (obj." + propertyName + " = ? or obj." + propertyName + " is null) ";
			params.add(value);
		}
		
		hql += " order by obj.daPipelineCompanyinfo.id";
		return persistenceImpl.find(hql, params.toArray(), page);
	}
	
	public List<DaPipelineCompanyInfo> loadRqPipLineByIntProperty(String propertyName, int value, Long areaCode, Pagination page) throws Exception {
		String hql = "from DaPipelineInfo obj where obj.deleted = false";
		List<Object> params = new ArrayList<Object>();
		
		hql += " and obj.type = ?";
		params.add(0);

		hql += " and obj.daPipelineCompanyinfo.company.firstArea = ?";
		params.add(Nbyhpc.AREA_CODE);
		
		if (areaCode != null && areaCode > 0){
			if(areaCode-Nbyhpc.AREA_CODE != 0l){
				hql += " and obj.daPipelineCompanyinfo.company.secondArea = ?";
				params.add(areaCode);
			}else{
				hql += " and (obj.daPipelineCompanyinfo.company.secondArea = 0 or obj.daPipelineCompanyinfo.company.secondArea is null) ";
			}
		}
		
		if (!StringUtils.isEmpty(propertyName)){
			hql += " and (obj." + propertyName + " = ? or obj." + propertyName + " is null) ";
			params.add(value);
		}
		
		hql += " order by obj.daPipelineCompanyinfo.id";
		return persistenceImpl.find(hql, params.toArray(), page);
	}

	/**
	 * 
	 * @param propertyName 属性名称
	 * @param value  属性值
	 * @param areaCode 区域代码
	 * @param type  管道类型：0:全部， 1：长输管道 2：城镇燃气管道 3：工业管道 4：港区内油气管道 
	 * @return
	 */
	public List<DaPipelineCompanyInfo> loadYqPipLineByProperty(String propertyName, boolean value, Long areaCode, String lineType, Pagination page) throws Exception {
		String hql = "from DaPipelineInfo obj where obj.deleted = false ";
		List<Object> params = new ArrayList<Object>();
		
		hql += " and obj.daPipelineCompanyinfo.company.firstArea = ?";
		params.add(Nbyhpc.AREA_CODE);
		
		if (areaCode != null && areaCode > 0){
			if(areaCode-Nbyhpc.AREA_CODE != 0l){
				hql += " and obj.daPipelineCompanyinfo.company.secondArea = ?";
				params.add(areaCode);
			}else{
				hql += " and (obj.daPipelineCompanyinfo.company.secondArea = 0 or obj.daPipelineCompanyinfo.company.secondArea is null) ";
			}
		}
		
		if (!StringUtils.isEmpty(propertyName)){
			hql += " and (obj." + propertyName + " = ? or obj." + propertyName + " is null) ";
			params.add(value);
		}
		
		if (null!=lineType && !"".equals(lineType)){
			hql += " and obj.type = ? ";
			params.add(Integer.valueOf(lineType));
		}
		hql += " order by obj.daPipelineCompanyinfo.id";
		
		return persistenceImpl.find(hql, params.toArray(), page);
	}
	
	public List<DaPipeAttech> loadAttechByArea(Long areaCode, String lineType, int attechType, Pagination page) throws Exception {
		StringBuffer hql = new StringBuffer(" from DaPipeAttech obj where obj.deleted = false ");
		List<Object> params = new ArrayList<Object>();
		
		hql.append(" and obj.daPipelineInfo.daPipelineCompanyinfo.company.firstArea = ?");
		params.add(Nbyhpc.AREA_CODE);
		
		if (areaCode != null && areaCode > 0){
			if(areaCode-Nbyhpc.AREA_CODE != 0l){
				hql.append(" and obj.daPipelineInfo.daPipelineCompanyinfo.company.secondArea = ?");
				params.add(areaCode);
			}else{
				hql.append(" and (obj.daPipelineInfo.daPipelineCompanyinfo.company.secondArea = 0 or obj.daPipelineCompanyinfo.company.secondArea is null) ");
			}
		}
		
		if (attechType > 0){
			hql.append(" and obj.type = ? ");
			params.add(attechType);
		}
		
		if (null!=lineType && !"".equals(lineType)){
			hql.append(" and obj.daPipelineInfo.type = ? ");
			params.add(Integer.valueOf(lineType));
		}
		hql.append(" order by id desc");
		return persistenceImpl.find(hql.toString(), params.toArray(), page);
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setYqStatisticSql(String yqStatisticSql) {
		this.yqStatisticSql = yqStatisticSql;
	}

	public void setRqStatisticSql(String rqStatisticSql) {
		this.rqStatisticSql = rqStatisticSql;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public void setPipeLinePersistenceIface(
			PipeLinePersistenceIface pipeLinePersistenceIface) {
		this.pipeLinePersistenceIface = pipeLinePersistenceIface;
	}

	public void setPipeNomalDangerPersistenceIface(
			PipeNomalDangerPersistenceIface pipeNomalDangerPersistenceIface) {
		this.pipeNomalDangerPersistenceIface = pipeNomalDangerPersistenceIface;
	}

	public void setPipeDangerPersistenceIface(
			PipeDangerPersistenceIface pipeDangerPersistenceIface) {
		this.pipeDangerPersistenceIface = pipeDangerPersistenceIface;
	}

}

