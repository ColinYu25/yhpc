package com.safetys.nbyhpc.util;

import java.util.EnumSet;

/**
 * 操作系统类型
 * @author yangb
 *
 */
public enum OsType {

	ANDROID {
		@Override public String getText() { return "Android系统"; }
	},
	
	ISO {
		@Override public String getText() { return "IOS系统"; }
	};
	
	public String getCode() {
		return name();
	}
	
	public abstract String getText();
	
	public static EnumSet<OsType> getOsTypes() {
		return EnumSet.allOf(OsType.class);
	}
}
