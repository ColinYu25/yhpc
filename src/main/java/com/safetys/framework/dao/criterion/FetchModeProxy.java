package com.safetys.framework.dao.criterion;

import org.hibernate.FetchMode;

public class FetchModeProxy {

	private FetchMode fetchMode;

	protected FetchModeProxy(FetchMode fetchMode) {
		this.fetchMode = fetchMode;
	}

	public static FetchModeProxy getDefaultMode() {
		return new FetchModeProxy(FetchMode.DEFAULT);
	}

	public static FetchModeProxy getJoinMode() {
		return new FetchModeProxy(FetchMode.JOIN);
	}

	public static FetchModeProxy getSelectMode() {
		return new FetchModeProxy(FetchMode.SELECT);
	}

	FetchMode getFetchMode() {
		return fetchMode;
	}

}
