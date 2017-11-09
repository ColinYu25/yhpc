package com.safetys.nbyhpc.domain.model;

import java.io.Serializable;
import java.util.Date;

public class SynErrorIds implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String syncKey;
	private Long dangerId;
	private Long dangerVersionId;
	private Date createTime = new Date();
	private boolean deleted = false;
	private String doFlag;

	public String getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(boolean deleted) {
	}

	/**
	 * @return the dangerId
	 */
	public Long getDangerId() {
		return dangerId;
	}

	/**
	 * @param dangerId
	 *            the dangerId to set
	 */
	public void setDangerId(Long dangerId) {
		this.dangerId = dangerId;
	}

	/**
	 * @return the dangerVersionId
	 */
	public Long getDangerVersionId() {
		return dangerVersionId;
	}

	/**
	 * @param dangerVersionId
	 *            the dangerVersionId to set
	 */
	public void setDangerVersionId(Long dangerVersionId) {
		this.dangerVersionId = dangerVersionId;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @return the doFlag
	 */
	public String getDoFlag() {
		return doFlag;
	}

	/**
	 * @param doFlag the doFlag to set
	 */
	public void setDoFlag(String doFlag) {
		this.doFlag = doFlag;
	}

}
