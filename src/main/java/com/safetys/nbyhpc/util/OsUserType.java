package com.safetys.nbyhpc.util;

import java.util.EnumSet;

public enum OsUserType {
	
	ES {
		@Override public String getText() { return "企业用户"; }
	},
	
	GOV {
		@Override public String getText() { return "政府用户"; }
	};
	
	public String getCode() {
		return name();
	}
	
	public abstract String getText();
	
	public static EnumSet<OsUserType> getOsUserTypes() {
		return EnumSet.allOf(OsUserType.class);
	}
}
