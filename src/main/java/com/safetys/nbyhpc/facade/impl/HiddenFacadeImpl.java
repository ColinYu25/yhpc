package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.SynErrorIds;
import com.safetys.nbyhpc.domain.model.SyncNo;
import com.safetys.nbyhpc.domain.model.VDangerEx;
import com.safetys.nbyhpc.domain.model.VNormalDangerEx;
import com.safetys.nbyhpc.domain.persistence.iface.HiddenPersistenceIface;
import com.safetys.nbyhpc.facade.iface.HiddenIface;

public class HiddenFacadeImpl implements HiddenIface {

	private HiddenPersistenceIface hiddenPersistenceIface;

	public List<VDangerEx> getVDangerExListByVersionId(Long versionId,
			Long count) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(VDangerEx.class);
		detachedCriteriaProxy
				.add(RestrictionsProxy
						.sqlRestriction("  version_id>="
								+ versionId
								+ " and (version_id<=(select max(version_id) from (select ex.version_id from v_danger_ex ex where ex.version_id>="
								+ versionId
								+ " order by  ex.version_id) where rownum<="
								+ count
								+ " )) order by version_id asc "));
		return hiddenPersistenceIface.getVDangerExList(detachedCriteriaProxy);

	}

	public List<VNormalDangerEx> getVNormalDangerExListByVersionId(
			Long versionId, Long count) throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(VNormalDangerEx.class);
		detachedCriteriaProxy
				.add(RestrictionsProxy
						.sqlRestriction("  version_id>="
								+ versionId
								+ " and （version_id<=(select max(version_id) from (select ex.version_id from v_normal_danger_ex ex where ex.version_id>="
								+ versionId
								+ " order by  ex.version_id) where rownum<="
								+ count + " )) order by version_id asc "));
		return hiddenPersistenceIface
				.getVNormalDangerExList(detachedCriteriaProxy);
	}

	public VNormalDangerEx getVNormalDangerEx(Long versionId, Long id)
			throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(VNormalDangerEx.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("id", id));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("versionId", versionId));
		List<VNormalDangerEx> vNormalDangerEx = hiddenPersistenceIface
				.getVNormalDangerExList(detachedCriteriaProxy);
		if (vNormalDangerEx != null && vNormalDangerEx.size() > 0) {
			return vNormalDangerEx.get(0);
		} else {
			return null;
		}
	}

	public VDangerEx getVDangerEx(Long versionId, Long id)
			throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(VDangerEx.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("id", id));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("versionId", versionId));
		List<VDangerEx> vDangerEx = hiddenPersistenceIface
				.getVDangerExList(detachedCriteriaProxy);
		if (vDangerEx != null && vDangerEx.size() > 0) {
			return vDangerEx.get(0);
		} else {
			return null;
		}
	}

	public List<SynErrorIds> getSynErrorIdsByVersionId(String syncKey,
			Long count) throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(SynErrorIds.class);
		detachedCriteriaProxy.add(RestrictionsProxy
				.sqlRestriction(" is_deleted=0 and sync_key='" + syncKey
						+ "' and rownum<=" + count
						+ " order by danger_version_id asc"));
		return hiddenPersistenceIface.getSynErrorIdsList(detachedCriteriaProxy);
	}
	
	public SynErrorIds getSynErrorIdsByVersionId(Long dangerId,
			Long dangerVersionId) throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(SynErrorIds.class);
		detachedCriteriaProxy.add(RestrictionsProxy
				.sqlRestriction(" is_deleted=0 and danger_id=" + dangerId + " and danger_version_id=" + dangerVersionId+ " "));
		List<SynErrorIds> list= hiddenPersistenceIface.getSynErrorIdsList(detachedCriteriaProxy);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public SyncNo getSyncNoByKey(String key) throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2
				.forClass(SyncNo.class);
		detachedCriteriaProxy.add(RestrictionsProxy
				.sqlRestriction("  SYNC_KEY='" + key + "' and IS_DELETED=0  "));
		return hiddenPersistenceIface.getSyncNo(detachedCriteriaProxy);
	}

	public void saveSyncNo(SyncNo syncNo) throws ApplicationAccessException {
		hiddenPersistenceIface.saveOrUpdateSyncNo(syncNo);
	}

	public void saveOrUpdateSynErrorIds(SynErrorIds synErrorIds)
			throws ApplicationAccessException {
		hiddenPersistenceIface.saveOrUpdateSynErrorIds(synErrorIds);
	}

	public Long loadLimitVersionId(Long startVersionId, Long count, String flag)
			throws ApplicationAccessException, SQLException {
		ResultSet rs = null;
		if ("normalDanger".equals(flag)) {
			String sql = "select max(version_id) from (select ex.version_id from v_normal_danger_ex ex where ex.version_id>="
					+ startVersionId
					+ " order by  ex.version_id) where rownum<=" + count + "";
			rs = hiddenPersistenceIface.findBySql(sql);
			if (rs.next()) {
				return rs.getLong(1);
			}
		} else {
			String sql = "select max(version_id) from (select ex.version_id from v_danger_ex ex where ex.version_id>="
					+ startVersionId
					+ " order by  ex.version_id) where rownum<=" + count + "";
			rs = hiddenPersistenceIface.findBySql(sql);
			if (rs.next()) {
				return rs.getLong(1);
			}
		}
		// 查不到的话，默认startVersionId+count;
		return startVersionId + count;
	}

	public void updateExFlag(Long id, String flag)
			throws ApplicationAccessException {

		if ("normalDanger".equals(flag)) {
			String sql = "update DA_NORMAL_DANGER set EX_FLAG='1' where id="
					+ id + "";
			hiddenPersistenceIface.executeSQLUpdate(sql);
		} else {
			String sql = "update DA_DANGER set EX_FLAG='1' where id=" + id + "";
			hiddenPersistenceIface.executeSQLUpdate(sql);
		}

	}

	/**
	 * @return the hiddenPersistenceIface
	 */
	public HiddenPersistenceIface getHiddenPersistenceIface() {
		return hiddenPersistenceIface;
	}

	/**
	 * @param hiddenPersistenceIface
	 *            the hiddenPersistenceIface to set
	 */
	public void setHiddenPersistenceIface(
			HiddenPersistenceIface hiddenPersistenceIface) {
		this.hiddenPersistenceIface = hiddenPersistenceIface;
	}

}
