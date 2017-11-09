package com.safetys.nbyhpc.util;

import java.util.EnumSet;

public enum HiddenTable {

	T_DAILY_CHECK {
		@Override public String getText() { return "标准化、网格化隐患表"; }
	},
	
	T_LAW_YH_INFO {
		@Override public String getText() { return "执法系统隐患表"; }
	},
	
	T_HIDDEN_INFO {
		@Override public String getText() { return "隐患系统一般隐患表"; }
	},
	
	T_HIDDEN_UN_SERIOUS {
		@Override public String getText() { return "隐患系统重大隐患表"; }
	};
	
	public String getCode() {
		return name();
	}
	
	public abstract String getText();
	
	public static EnumSet<HiddenTable> getHiddenTables() {
		return EnumSet.allOf(HiddenTable.class);
	}
}
