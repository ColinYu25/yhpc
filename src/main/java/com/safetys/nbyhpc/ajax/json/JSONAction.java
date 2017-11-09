/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.ajax.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.MapMarker;
import com.safetys.nbyhpc.facade.iface.MapFacadeIface;

/**
 * 利用JQuery+Ajax获取JSON数据
 * 
 * @author lvjl
 * @since 2011-10-25
 * @version 1.0.0
 */
public class JSONAction extends ActionSupport {

	private static final long serialVersionUID = -1307823640462524127L;
	List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
	private String result;
	private MapFacadeIface mapFacadeIface;
	private DaCompany company;
	private MapMarker mapMarker;// 地图标记
	private List<MapMarker> mapMarkers;// 标记列表

	/**
	 * 加载地图上被标注的企业
	 * 
	 * @return
	 */
	public String loadMapMarkers() {
		try {
			mapMarkers = mapFacadeIface.loadMapMarkers(company, mapMarker);
			for (MapMarker mapMarker : mapMarkers) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("markerId", String.valueOf(mapMarker.getMarkerId()));
				map.put("markerType", mapMarker.getMarkerType());
				map.put("markerImg", mapMarker.getMarkerImg());
				map.put("markerLink", mapMarker.getMarkerLink());
				map.put("markerLng", mapMarker.getMarkerLng());
				map.put("markerLat", mapMarker.getMarkerLat());
				mapList.add(map);
			}
			// 将要返回的List对象进行json处理
			result = JsonUtil.list2json(mapList);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void setMapList(List<Map<String, String>> mapList) {
		this.mapList = mapList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setMapFacadeIface(MapFacadeIface mapFacadeIface) {
		this.mapFacadeIface = mapFacadeIface;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public MapMarker getMapMarker() {
		return mapMarker;
	}

	public void setMapMarker(MapMarker mapMarker) {
		this.mapMarker = mapMarker;
	}

	public List<MapMarker> getMapMarkers() {
		return mapMarkers;
	}

	public void setMapMarkers(List<MapMarker> mapMarkers) {
		this.mapMarkers = mapMarkers;
	}

	public List<Map<String, String>> getMapList() {
		return mapList;
	}
}
