/*
 * @(#) PhMapMarker.java        
 * Date 2009-6-3                                           
 * Copyright (c) 2009 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;


public class MapMarker extends DaBaseModel {

	private static final long serialVersionUID = -2891798041732535268L;

	//private Integer spatialId;
	
	private Long markerId;
	
	private String markerType;
	
	private String markerLng;
	
	private String markerLat;
	
	private String markerName;
	
	private int markerWidth;
	
	private int markerHeight;
	
	private String markerImg;
	
	private String markerLink;
	
	public Long getMarkerId() {
		return markerId;
	}

	public void setMarkerId(Long markerId) {
		this.markerId = markerId;
	}

	public String getMarkerType() {
		return markerType;
	}

	public void setMarkerType(String markerType) {
		this.markerType = markerType;
	}

	public String getMarkerName() {
		return markerName;
	}

	public void setMarkerName(String markerName) {
		this.markerName = markerName;
	}

	public String getMarkerLink() {
		return markerLink;
	}

	public void setMarkerLink(String markerLink) {
		this.markerLink = markerLink;
	}


	public String getMarkerImg() {
		return markerImg;
	}

	public void setMarkerImg(String markerImg) {
		this.markerImg = markerImg;
	}

	public int getMarkerHeight() {
		return markerHeight;
	}

	public void setMarkerHeight(int markerHeight) {
		this.markerHeight = markerHeight;
	}

	public int getMarkerWidth() {
		return markerWidth;
	}

	public void setMarkerWidth(int markerWidth) {
		this.markerWidth = markerWidth;
	}

	public String getMarkerLng() {
		return markerLng;
	}

	public void setMarkerLng(String markerLng) {
		this.markerLng = markerLng;
	}

	public String getMarkerLat() {
		return markerLat;
	}

	public void setMarkerLat(String markerLat) {
		this.markerLat = markerLat;
	}

	
}

