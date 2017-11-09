package com.safetys.nbyhpc.util;

public class State {

	private String key;

	private String value;

	public State() {
	}

	public State(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public State(Integer key, String value) {
		this.key = key + "";
		this.value = value;
	}

	public Integer getIntKey() {
		return Integer.parseInt(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
