package com.safetys.nbyhpc.facade.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.safetys.center.db.cxf.WsLoader;
import cn.safetys.center.db.cxf.company.ICompany;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.HiddenSummary;
import com.safetys.nbyhpc.domain.persistence.iface.HiddenSummaryPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.HiddenSummaryFacadeIface;
import com.safetys.nbyhpc.util.HiddenLevel;
import com.safetys.nbyhpc.util.HiddenSource;
import com.safetys.nbyhpc.util.HiddenTable;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.vo.HiddenVo;

public class HiddenSummaryFacadeImpl extends BaseFacadeImpl<HiddenSummary> implements HiddenSummaryFacadeIface {

	private HiddenSummaryPersistenceIface hiddenSummaryPersistenceIface;
	
	private CompanyFacadeIface companyFacadeIface;
	
	@Override
	public List<HiddenSummary> findByHiddenVo(HiddenVo hiddenVo, Pagination page) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from HiddenSummary obj where obj.deleted = false");
		if (hiddenVo != null) {
			if (StringUtils.isNotBlank(hiddenVo.getCompanyName())) {
				hql.append(" and obj.companyName like ?");
				params.add("%" + hiddenVo.getCompanyName() + "%");
			}
			if (StringUtils.isNotBlank(hiddenVo.getDescription())) {
				hql.append(" and obj.description like ?");
				params.add("%" + hiddenVo.getDescription() + "%");
			}
			if (StringUtils.isNotBlank(hiddenVo.getYear())) {
				hql.append(" and to_char(obj.hiddenDate, 'yyyy') = ?");
				params.add(hiddenVo.getYear());
			}
			if (StringUtils.isNotBlank(hiddenVo.getFromSys())) {
				hql.append(" and obj.fromSys = ?");
				params.add(hiddenVo.getFromSys());
			}
			if (StringUtils.isNotBlank(hiddenVo.getType1())) {
				hql.append(" and obj.type1 = ?");
				params.add(hiddenVo.getType1());
			}
			if (StringUtils.isNotBlank(hiddenVo.getHiddenLevel())) {
				hql.append(" and obj.hiddenLevel = ?");
				params.add(hiddenVo.getHiddenLevel());
			}
			if (hiddenVo.getCompleted() != null) {
				hql.append(" and obj.completed = ?");
				params.add(hiddenVo.getCompleted());
			}
			if (StringUtils.isNotBlank(hiddenVo.getThirdArea())) {
				hql.append(" and obj.thirdArea = ?");
				params.add(hiddenVo.getThirdArea());
			}
			if (StringUtils.isNotBlank(hiddenVo.getSecondArea())) {
				hql.append(" and obj.secondArea = ?");
				params.add(hiddenVo.getSecondArea());
			}
			if (StringUtils.isNotBlank(hiddenVo.getFirstArea())) {
				hql.append(" and obj.firstArea = ?");
				params.add(hiddenVo.getFirstArea());
			}
		}
		return hiddenSummaryPersistenceIface.find(hql.toString(), params.toArray(), page);
	}

	/**
	 * 后加的字段一定要判断json串里是否有这个KEY
	 * 因为老的增量里是没有这个字段的
	 */
	@Override
	public void updateFromCenterDB(String fromTable, JSONObject jsonData) {
		String id = jsonData.getString("ID");
		HiddenSummary entity = findByFormTableAndFromId(fromTable, id);
		if (entity == null) {
			entity = new HiddenSummary();
			entity.setFromTable(fromTable);
			entity.setFromId(id);
		}
		if (HiddenTable.T_LAW_YH_INFO.getCode().equals(fromTable)) {
			buildByLawYhInfo(jsonData, entity, false);
			entity.setFromSys(HiddenSource.XZZF.getCode());
		} else if (HiddenTable.T_DAILY_CHECK.getCode().equals(fromTable)) {
			boolean needReceive = buildByDailyCheck(jsonData, entity);
			if (!needReceive) {
				return; //FIXME 这里应该抛异常出去比较保险 RuntimeException
			}
			if (jsonData.getString("ASSEMBLEID").startsWith("XZJD_")) {
				entity.setFromSys(HiddenSource.STREET.getCode());
			} else if (jsonData.getString("ASSEMBLEID").startsWith("BZH_")) {
				entity.setFromSys(HiddenSource.STANDARD.getCode());
			}
		} else if (HiddenTable.T_HIDDEN_INFO.getCode().equals(fromTable)) {
			buildByHiddenInfo(jsonData, entity);
			entity.setFromSys(HiddenSource.YHPC.getCode());
		} else if (HiddenTable.T_HIDDEN_UN_SERIOUS.getCode().equals(fromTable)) {
			buildByHiddenUnSerious(jsonData, entity);
			entity.setFromSys(HiddenSource.YHPC.getCode());
		}
		String uuid = jsonData.getString("COMPANY_UUID");
		if (HiddenTable.T_LAW_YH_INFO.getCode().equals(fromTable)) {
			//这里需要考虑换COMPANY的情况
			if (entity.getId() == null || entity.getId() <= 0) {
				String resultStr = WsLoader.getInterface(ICompany.class).getCompanyInfo(Nbyhpc.TOKEN, uuid);
				JSONObject json = JSONObject.fromObject(resultStr);
				JSONObject dataJson = json.getJSONObject("data");
				buildArea(dataJson, entity);
			}
		} else {
			buildArea(jsonData, entity);
		}
		entity.setCompanyName(jsonData.getString("COMPANY_NAME"));
		entity.setCompanyId(jsonData.getLong("COMPANY_ID"));
		entity.setUuid(uuid);
		entity.setDeleted("1".equals(jsonData.getString("IS_DELETED")));
		if (entity.getId() != null && entity.getId() > 0) {
			entity.setModifyTime(new Date());
			hiddenSummaryPersistenceIface.update(entity);
		} else {
			hiddenSummaryPersistenceIface.create(entity);
		}
	}

	@Override
	public void updateFromCenterDB4Xzzf(JSONObject jsonData) {
		String fromTable = HiddenTable.T_LAW_YH_INFO.getCode();
		String xcjcjlId = jsonData.getString("XCJCJL_L_ID");//现场检查记录ID
		if (StringUtils.isBlank(xcjcjlId)) {
			//非现场检查记录的隐患跟其他一样
			updateFromCenterDB(fromTable, jsonData);
		} else {
			//现场检查记录的隐患只能算一条
			HiddenSummary entity = findByFormTableAndTemp1(fromTable, xcjcjlId);
			String id = jsonData.getString("ID");
			String uuid = jsonData.getString("COMPANY_UUID");
			if (entity == null) {
				entity = new HiddenSummary();
				entity.setFromTable(fromTable);
				entity.setFromId(id);
				entity.setFromSys(HiddenSource.XZZF.getCode());
				entity.setTemp1(jsonData.getString("XCJCJL_L_ID"));
				//这里需要考虑换COMPANY的情况
				String resultStr = WsLoader.getInterface(ICompany.class).getCompanyInfo(Nbyhpc.TOKEN, uuid);
				JSONObject json = JSONObject.fromObject(resultStr);
				JSONObject dataJson = json.getJSONObject("data");
				buildArea(dataJson, entity);
			}
			JSONArray ja = entity.getTemp2JsonArray();
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				if (jo.getString("ID").equals(jsonData.getString("ID"))) {
					ja.remove(i);
					break;
				}
			}
			ja.add(jsonData);
			entity.setTemp2(ja.toString());
			buildByLawYhInfo(jsonData, entity, true);
			entity.setCompanyName(jsonData.getString("COMPANY_NAME"));
			entity.setCompanyId(jsonData.getLong("COMPANY_ID"));
			entity.setUuid(uuid);
			boolean isDeleted = true;
			JSONArray newTemp2 = entity.getTemp2JsonArray();
			//有一个没删除，那就是未删除
			for (int i = 0; i < newTemp2.size(); i++) {
				JSONObject jo = newTemp2.getJSONObject(i);
				if ("0".equals(jo.getString("IS_DELETED"))) {
					isDeleted = false;
					break;
				}
			}
			entity.setDeleted(isDeleted);
			if (entity.getId() != null && entity.getId() > 0) {
				entity.setModifyTime(new Date());
				hiddenSummaryPersistenceIface.update(entity);
			} else {
				hiddenSummaryPersistenceIface.create(entity);
			}
		}
	}

	private void buildArea(JSONObject jsonData, HiddenSummary entity) {
		if (jsonData.has("FIRST_AREA")) {
			String firstArea = jsonData.getString("FIRST_AREA");
			//这里不考虑没有字段设置为空
			if (!StringUtils.isBlank(firstArea)) {
				entity.setFirstArea(firstArea);
			}
		}
		if (jsonData.has("SECOND_AREA")) {
			String secondArea = jsonData.getString("SECOND_AREA");
			//这里不考虑没有字段设置为空
			if (!StringUtils.isBlank(secondArea)) {
				entity.setSecondArea(secondArea);
			}
		}
		if (jsonData.has("THIRD_AREA")) {
			String thirdArea = jsonData.getString("THIRD_AREA");
			//这里不考虑没有字段设置为空
			if (!StringUtils.isBlank(thirdArea)) {
				entity.setThirdArea(thirdArea);
			}
		}
	}

	/**
	 * 
	 * @param jsonData
	 * @param entity
	 * @param isXcjx 是否是现场检查记录对应的隐患
	 */
	private void buildByLawYhInfo(JSONObject jsonData, HiddenSummary entity, boolean isXcjx) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity.setDescription(jsonData.getString("CONTENT"));
		String hiddenDate = jsonData.getString("FILL_DATE");
		if (StringUtils.isNotBlank(hiddenDate)) {
			try {
				entity.setHiddenDate(sdf.parse(hiddenDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			entity.setHiddenDate(null);
		}
		if (isXcjx) {
			boolean isCompleted = true;
			JSONArray ja = entity.getTemp2JsonArray();
			//有一个没整改，那就是未整改
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				if ("0".equals(jo.getString("IS_DELETED")) && !"1".equals(jo.getString("IS_DONE"))) {
					isCompleted = false;
					break;
				}
			}
			entity.setCompleted(isCompleted);
		} else {
			String isDone = jsonData.getString("IS_DONE");
			entity.setCompleted("1".equals(isDone));
		}
		if (entity.isCompleted()) {
			//这里偷懒了，不去判断那个隐患的时间大小，因为统计是根据年份统计，除非刚好跨年的隐患
			if (jsonData.has("DONE_DATE")) {
				String completeDate = jsonData.getString("DONE_DATE");
				if (StringUtils.isNotBlank(completeDate)) {
					try {
						entity.setCompleteDate(sdf.parse(completeDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (entity.getCompleteDate() == null) {
					//如果整改完成没有日期，那就默认给他隐患发现日期，按照年份统计问题不大，除非刚好跨年的隐患
					entity.setCompleteDate(entity.getHiddenDate());
				} 
			}
		} else {
			entity.setCompleteDate(null);
		}
		entity.setHiddenLevel(HiddenLevel.NORMAL.getCode());
		if (jsonData.has("TYPE2")) {
			String type2 = jsonData.getString("TYPE2");
			entity.setType2(type2);
			DaIndustryParameter ip = companyFacadeIface.getHiddenTypeByNormalCode(type2);
			entity.setType1(ip.getDaIndustryParameter().getCode());
		}
	}
	
	private boolean buildByDailyCheck(JSONObject jsonData, HiddenSummary entity) {
		String description = jsonData.getString("CHECK_MAJOR_ACCIDENT");
		//如果没有隐患信息，就不需要同步
		if (StringUtils.isBlank(description)) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity.setDescription(description);
		String hiddenDate = jsonData.getString("HIDDEN_INPUT_TIME");
		if (StringUtils.isNotBlank(hiddenDate)) {
			try {
				entity.setHiddenDate(sdf.parse(hiddenDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			entity.setHiddenDate(null);
		}
		String recStatus = jsonData.getString("REC_STATUS");
		entity.setCompleted("NP_1".equals(recStatus));
		if (entity.isCompleted()) {
			String completeDate = jsonData.getString("REC_FINISH_TIME");
			if (StringUtils.isBlank(completeDate)) {
				entity.setCompleteDate(null);
			} else {
				try {
					entity.setCompleteDate(sdf.parse(completeDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else {
			entity.setCompleteDate(null);
		}
		if ("NP_1".equals(jsonData.getString("HIDDEN_LEVEL"))) {
			entity.setHiddenLevel(HiddenLevel.MAJOR.getCode());
		} else {
			entity.setHiddenLevel(HiddenLevel.NORMAL.getCode());
		}
		String hiddenType = jsonData.getString("HIDDEN_TYPE");
		DaIndustryParameter ip = companyFacadeIface.getHiddenTypeByNormalCode(hiddenType);
		if (Integer.valueOf(2).equals(ip.getGradeRate())) {
			entity.setType1(ip.getDaIndustryParameter().getCode());
			entity.setType2(ip.getCode());
		} else if (Integer.valueOf(1).equals(ip.getGradeRate())) {
			entity.setType1(ip.getCode());
		}
		return true;
	}
	
	private void buildByHiddenInfo(JSONObject jsonData, HiddenSummary entity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity.setDescription(jsonData.getString("HIDDEN_DES"));
		String hiddenDate = jsonData.getString("HIDDEN_FIND_TIME");
		try {
			entity.setHiddenDate(sdf.parse(hiddenDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		entity.setCompleted("1".equals(jsonData.getString("RESOLVE_STATE")));
		String completeDate = jsonData.getString("HIDDEN_COMPLETE_DATE");
		if (StringUtils.isBlank(completeDate)) {
			entity.setCompleteDate(null);
		} else {
			try {
				entity.setCompleteDate(sdf.parse(completeDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		entity.setHiddenLevel(HiddenLevel.NORMAL.getCode());
		entity.setType1(jsonData.getString("HIDDEN_TYPE1"));
		entity.setType2(jsonData.getString("HIDDEN_TYPE2"));
	}
	
	private void buildByHiddenUnSerious(JSONObject jsonData, HiddenSummary entity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity.setDescription(jsonData.getString("HIDDEN_DES"));
		String hiddenDate = jsonData.getString("HAPPEN_TIME");
		try {
			entity.setHiddenDate(sdf.parse(hiddenDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		entity.setCompleted("1".equals(jsonData.getString("RESOLVE_STATE")));
		String completeDate = jsonData.getString("FINISH_TIME");
		if (StringUtils.isBlank(completeDate)) {
			entity.setCompleteDate(null);
		} else {
			try {
				entity.setCompleteDate(sdf.parse(completeDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		entity.setHiddenLevel(HiddenLevel.MAJOR.getCode());
		if (jsonData.has("HIDDEN_TYPE1")) {
			entity.setType1(jsonData.getString("HIDDEN_TYPE1"));
		}
		if (jsonData.has("HIDDEN_TYPE2")) {
			entity.setType2(jsonData.getString("HIDDEN_TYPE2"));
		}
	}

	private HiddenSummary findByFormTableAndFromId(String fromTable, String fromId) {
		String hql = "from HiddenSummary obj where obj.fromTable = ? and obj.fromId = ? ";
		List<HiddenSummary> list = hiddenSummaryPersistenceIface.findByHql(hql, fromTable, fromId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	private HiddenSummary findByFormTableAndTemp1(String fromTable, String temp1) {
		String hql = "from HiddenSummary obj where obj.fromTable = ? and temp1 = ?";
		List<HiddenSummary> list = hiddenSummaryPersistenceIface.findByHql(hql, fromTable, temp1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public HiddenSummaryPersistenceIface getService() {
		return hiddenSummaryPersistenceIface;
	}

	public void setHiddenSummaryPersistenceIface(HiddenSummaryPersistenceIface hiddenSummaryPersistenceIface) {
		this.hiddenSummaryPersistenceIface = hiddenSummaryPersistenceIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

}
