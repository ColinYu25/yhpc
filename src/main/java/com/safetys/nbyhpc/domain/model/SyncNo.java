package com.safetys.nbyhpc.domain.model;

import java.io.Serializable;
import java.util.Date;

public class SyncNo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String syncKey;
	private Long syncLastNo;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private boolean deleted = false;

	public String getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}

	public Long getSyncLastNo() {
		return syncLastNo;
	}

	public void setSyncLastNo(Long syncLastNo) {
		this.syncLastNo = syncLastNo;
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
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(boolean deleted) {
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

}
