package com.safetys.nbyhpc.util;

public class OperateResult {

	private boolean state;
	private String message;
	private Object result;
	
	public OperateResult() {
		super();
	}
	public OperateResult(boolean state, String message) {
		super();
		this.state = state;
		this.message = message;
	}
	
	public OperateResult(boolean state, String message, Object result) {
		super();
		this.state = state;
		this.message = message;
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
