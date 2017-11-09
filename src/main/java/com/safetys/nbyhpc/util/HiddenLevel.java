package com.safetys.nbyhpc.util;

import java.util.EnumSet;

public enum HiddenLevel {
	
	NORMAL {
		@Override public String getText() { return "一般隐患"; }
	},
	
	MAJOR {
		@Override public String getText() { return "重大隐患"; }
	};
	
	public String getCode() {
		return name();
	}
	
	public abstract String getText();
	
	public static EnumSet<HiddenLevel> getHiddenLevels() {
		return EnumSet.allOf(HiddenLevel.class);
	}
}
