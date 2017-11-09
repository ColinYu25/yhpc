package com.safetys.nbyhpc.facade.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.TradeTypeFacadeIface;
import com.safetys.nbyhpc.util.RoleType;

public class TradeTypeFacadeImpl implements TradeTypeFacadeIface {

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private UserPersistenceIface userPersistenceIface;

	public Long createTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException {
		Long id = tradeTypePersistenceIface.createTradeType(tradeType);
		tradeType.setId(id);
		updateGradePath(tradeType);
		return id;
	}

	public void deleteTradeType(String ids) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			tradeTypePersistenceIface.deleteTradeType(new DaIndustryParameter(
					Long.parseLong(ids.split(",")[i].trim())));
		}
	}

	public DaIndustryParameter loadTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException {
		return tradeTypePersistenceIface.loadTradeType(tradeType);
	}

	public DaIndustryParameter loadTradeType(String tradeDepiction)
			throws ApplicationAccessException {
		DaIndustryParameter industryParamenter = new DaIndustryParameter();
		industryParamenter.setDepiction(tradeDepiction);
		return tradeTypePersistenceIface.loadTradeType(industryParamenter);
	}

	public List<DaIndustryParameter> loadTradeTypes(
			DaIndustryParameter tradeType, UserDetailWrapper userDetail,
			Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
				.forClass(DaIndustryParameter.class, "htt");
		if (tradeType != null) {
			if (tradeType.getName() != null
					&& !"".equals(tradeType.getName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("name",
						tradeType.getName().trim(), MatchMode.ANYWHERE));
			}
			if (tradeType.getDepiction() != null
					&& !"".equals(tradeType.getDepiction().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("depiction",
						tradeType.getDepiction().trim(), MatchMode.ANYWHERE));
			}
		}
		Set<FkRole> roles = userPersistenceIface.loadUser(
				(long) userDetail.getUserId()).getFkRoles();
		boolean isAdmin = RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles)
				|| RoleType.isRoleByCode(RoleType.MANAGER, roles);
		if (!isAdmin) {
			String depics = RoleType.getRoleDepicStr(roles);
			String gradeWhere = "";
			String[] depicList = depics.split(",");
			for (int i = 0; i < depicList.length; i++) {
				gradeWhere += " or grade_path like (select grade_path||'/%' from hz_trade_type where depiction='"
						+ depicList[i] + "') ";
			}
			depics = "'" + depics.replace(",", "','") + "'";
			detachedCriteriaProxy.add(RestrictionsProxy
					.sqlRestriction("this_.depiction in (" + depics + ") "
							+ gradeWhere));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.asc("type"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,
				pagination);
	}

	public List<DaIndustryParameter> loadTradeTypes(Integer type)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("type", type));
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("daIndustryParameter"));		
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public void updateTradeType(DaIndustryParameter tradeType)
			throws ApplicationAccessException {
		DaIndustryParameter oldTradeType = loadTradeType(tradeType);
		oldTradeType.setType(tradeType.getType());
		oldTradeType.setName(tradeType.getName());
		oldTradeType.setDepiction(tradeType.getDepiction());
		oldTradeType.setModifyTime(tradeType.getModifyTime());
		oldTradeType.setCode(tradeType.getCode());
		oldTradeType.setSort(tradeType.getSort());
		tradeTypePersistenceIface.mergeTradeType(oldTradeType);
	}

	private void updateGradePath(DaIndustryParameter tradeType)
			throws ApplicationAccessException {
		DaIndustryParameter fatherTrade = new DaIndustryParameter();
		String gradePath = "";
		if (tradeType.getDaIndustryParameter() != null) {
			fatherTrade = loadTradeType(tradeType.getDaIndustryParameter());
		}
		if (fatherTrade != null && fatherTrade.getGradePath() != null
				&& !"".equals(fatherTrade.getGradePath())) {
			gradePath = fatherTrade.getGradePath() + "/" + tradeType.getId();
		} else {
			gradePath = "/" + tradeType.getId();
		}
		tradeType.setGradePath(gradePath);
		tradeTypePersistenceIface.mergeTradeType(tradeType);
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setUserPersistenceIface(
			UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

}
