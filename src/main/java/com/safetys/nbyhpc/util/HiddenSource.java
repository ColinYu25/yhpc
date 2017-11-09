package com.safetys.nbyhpc.util;

import java.util.EnumSet;

public enum HiddenSource {

	YHPC {
		@Override public String getText() { return "隐患排查"; }
	},
	
	XZZF {
		@Override public String getText() { return "执法检查"; }
	},
	
	STREET {
		@Override public String getText() { return "网格化协查"; }
	},
	
	STANDARD {
		@Override public String getText() { return "中介机构评审"; }
	};
	
	public String getCode() {
		return name();
	}
	
	public abstract String getText();
	
	public static EnumSet<HiddenSource> getHiddenSources() {
		return EnumSet.allOf(HiddenSource.class);
	}
	
}
