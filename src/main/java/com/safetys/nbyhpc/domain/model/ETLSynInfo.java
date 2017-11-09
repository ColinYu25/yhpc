package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description:
 */
public class ETLSynInfo extends DaBaseModel {

	/**
	 * 包实体
	 */
	private static final long serialVersionUID = -9183587512447384092L;

	private long SynNo; 

	private String TabName;

	public long getSynNo() {
		return SynNo;
	}

	public void setSynNo(long synNo) {
		SynNo = synNo;
	}

	public String getTabName() {
		return TabName;
	}

	public void setTabName(String tabName) {
		TabName = tabName;
	}


	

}
