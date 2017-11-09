package com.safetys.nbyhpc.web.action.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jettison.json.JSONException;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaCreditManage;
import com.safetys.nbyhpc.domain.model.DaImageFile;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaInjuryManage;
import com.safetys.nbyhpc.domain.model.DaSecurityManage;
import com.safetys.nbyhpc.domain.model.MapMarker;
import com.safetys.nbyhpc.domain.model.TCoreArea;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.CreditManageFacadeIface;
import com.safetys.nbyhpc.facade.iface.ImageFileFacadeIface;
import com.safetys.nbyhpc.facade.iface.InjuryManageFacadeIface;
import com.safetys.nbyhpc.facade.iface.MapFacadeIface;
import com.safetys.nbyhpc.facade.iface.PubAreaFacadeIface;
import com.safetys.nbyhpc.facade.iface.SecurityManageFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
//import com.safetys.nbyhpc.util.PropertiesTool;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

public class MapAction extends BaseAppAction {
	private static final long serialVersionUID = -1707481339609719132L;
	private static final String ROLE_COMPANY_CODE = "ROLE_COMPANY";
	private String markerLng;// 地图上的经度

	private String markerLat;// 地图上的纬度

	private String markerType;// 标注的类型

	private DaCompany company;

	private DaCompanyPass companyPass;
	
	private List<DaCompany> companys;

	private List<DaCompany> unmarkerCompanies; //未标注企业集合
	
	private FkArea area;

	protected Pagination pagination;

	private MapFacadeIface mapFacadeIface;

	private MapMarker mapMarker;// 地图标记

	private List<MapMarker> mapMarkers;// 标记列表

	private String markerIds;

	private Long companyId;

	private String analyseModule;

	private List<DaIndustryParameter> tradeTypes;// 行业集合

	private CompanyFacadeIface companyFacadeIface;

	private DaSecurityManage securityManage;

	private DaInjuryManage injuryManage;

	private DaCreditManage creditManage;

	private SecurityManageFacadeIface securityManageFacadeIface;

	private InjuryManageFacadeIface injuryManageFacadeIface;

	private CreditManageFacadeIface creditManageFacadeIface;

	private ImageFileFacadeIface imageFileFacadeIface;
	
	private PubAreaFacadeIface pubAreaFacadeIface;

	private List<DaImageFile> imageFileList; // 图片上传列表

	private DaImageFile imageOne;

	private DaImageFile imageTwo;

	private String areaCode;

	private String flashCode;
	
	private String readValue;
	
	private List<TCoreArea> childAreas;

	/**
	 * 加载地图页面
	 * 
	 * @return
	 */
	public String loadMap() {
		try {
			
			// mapMarkers = mapFacadeIface.loadMapMarkers(pagination);
			if (company == null) {
				company = new DaCompany();
				List<DaCompanyPass> list = mapFacadeIface.loadCompanyPassByComUserId(getUserDetail());
				if (list != null && list.size() > 0) {
					companyPass = list.get(0);
					company.setId(companyPass.getId());// 根据当前登录的企业用户id查询企业基本信息
				}
			}
			if (mapMarker == null) { // 查询当前用户
				mapMarker = new MapMarker();
			}
			mapMarker.setMarkerId(company.getId());
			mapMarker.setMarkerType("company");
			loadMapMarker(); // 查询当前企业的坐标
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载政府端地图页面
	 * 
	 * @return
	 */
	public String loadMapForColligation() {
		try {
			// 以下代码注释原因：默认不显示地图标记
//			 mapMarkers = mapFacadeIface.loadMapMarkers(pagination);
			if (company == null) {
				company = new DaCompany();
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(8); 
			}
//			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null); //地图中企业搜索不限制用户所属行业
			companys = mapFacadeIface.loadInitCompanys(company, pagination, this.getUserDetail());	
			//查找属性文件，先判断当前是否需要执行系统自动标注功能
//			String filePath = ConfigUtil.getPropertyValue("properties.dir") + "map_tab_status.properties"; //定义要读取的属性文件路径(名称)
//			String key = "isNeedAuto";
//			readValue = PropertiesTool.readValue(filePath, key);
//			if ("1".equals(readValue)) {
			//实现搜索时自动标注
			if (companys != null && companys.size() > 0) {
				unmarkerCompanies = mapFacadeIface.loadUnmarkerCompanies(companys, null); //查询当前搜索列表中未标注的企业
				//页面端执行完后，将属性文件中的isNeedAuto修改为0；
//				PropertiesTool.updateValue(filePath, key, "0"); 
//			}
				mapMarkers = mapFacadeIface.loadMapMarkers(companys,null); //
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载政府端地图页面
	 * 
	 * @return
	 */
	public String loadMapForSearch() {
		try {
			if (company == null) {
				company = new DaCompany();
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(8); 
			}
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null); //地图中企业搜索不限制用户所属行业
			companys = mapFacadeIface.loadCompanys(company, pagination, this.getUserDetail());	
			//实现搜索时自动标注
			if (companys != null && companys.size() > 0) {
				unmarkerCompanies = mapFacadeIface.loadUnmarkerCompanies(companys, null); //查询当前搜索列表中未标注的企业
				mapMarkers = mapFacadeIface.loadMapMarkers(companys,null); //查询搜索列表中已标注企业的标注信息
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	//企业档案新格式数据采集:9000多家规模以上企业
	public String loadMapForGsCompanies() {
		try {
			if (company == null) {
				company = new DaCompany();
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(8); 
			}
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null); //地图中企业搜索不限制用户所属行业
			companys = mapFacadeIface.loadMapForGsCompanies(company, pagination, this.getUserDetail());	
			childAreas = pubAreaFacadeIface.findChildAreas(Nbyhpc.AREA_CODE);
			//实现搜索时自动标注
			if (companys != null && companys.size() > 0) {
				unmarkerCompanies = mapFacadeIface.loadUnmarkerCompanies(companys, null); //查询当前搜索列表中未标注的企业
				mapMarkers = mapFacadeIface.loadMapMarkers(companys,null); //
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 应急预案启动页面
	 * 
	 * @return
	 */
	public String loadRescueStartIndex() {
		return SUCCESS;
	}

	/**
	 * @description 企业标注列表
	 */
	public String loadCompanysByMarker() {
		try {
			if (company == null) {
				company = new DaCompany();
			}
			if (area != null) {
				area = mapFacadeIface.loadArea(area.getAreaCode());
				int areaRate = area.getGradePath().split("/").length - 1;
				if (areaRate == 5) {
					company.setSecondArea(area.getAreaCode());
				} else {
					company.setThirdArea(area.getAreaCode());
				}
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(8);
			}
			companys = mapFacadeIface.loadCompanys(company, pagination, this
					.getUserDetail());
			markerIds = mapFacadeIface.loadPhMapMarkerByType(markerType, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}

	/**
	 * 加载应急预案选择的企业列表
	 * 
	 * @return
	 */
	public String loadCompanysByRescue() {
		try {
			if (company == null) {
				company = new DaCompany();
			}
			// 判断是否是从flash中转跳过来
			if (null != areaCode) {
				if (null == area) {
					area = new FkArea();
				}

				area.setAreaCode(new Long(areaCode));
				// 由于从flash中转跳过来是2或3级地区,所以要获得2级和1级地区
				area = mapFacadeIface.loadArea(area.getAreaCode());
				int areaRate = area.getGradePath().split("/").length - 1;
				if (areaRate == 4) {
					company.setSecondArea(area.getAreaCode());
				} else {
					company.setThirdArea(area.getAreaCode());
					area = mapFacadeIface.loadAreaById(area.getFatherId());
					company.setSecondArea(area.getAreaCode());
				}
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(8);
			}
			tradeTypes = companyFacadeIface
					.loadTradeTypesForCompany(getUserDetail());
			companys = mapFacadeIface.loadCompanys(company, pagination, this
					.getUserDetail());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return INPUT;
	}

	/**
	 * @description 标记
	 */
	public void createMarker() throws IOException {
		try {
			if (mapFacadeIface.vlidateUserRoles(getUserDetail(),ROLE_COMPANY_CODE)) {
				// 如果当前操作人员是企业用户
				List<DaCompanyPass> list = mapFacadeIface
						.loadCompanyPassByComUserId(getUserDetail());
				if (list != null && list.size() > 0) {
					if (mapFacadeIface.loadMapMarker("company", list.get(0).getId(), null) != null) {
						addActionError("您已经标注过了!");
						return;
					} else {
						company = list.get(0).getDaCompany();
						mapMarker.setMarkerId(company.getId());
						mapMarker.setMarkerName(company.getCompanyName());
						mapMarker.setMarkerType("company");
						mapMarker.setMarkerHeight(16);
						mapMarker.setMarkerWidth(16);
						mapMarker.setMarkerImg("company.png");
						mapMarker.setMarkerLink("map/loadCompany.xhtml?company.id="+ company.getId());
						mapMarker = mapFacadeIface.createMarker(mapMarker,getUserId());
					}
				}
			} else {
				mapMarker = mapFacadeIface.createMarker(mapMarker, getUserId());
			}
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		getResponse().getWriter().print(mapMarker.getMarkerId());
	}

	/**
	 * 删除标记
	 * 
	 * @throws IOException
	 * @throws JSONException 
	 */
	public void deleteMarker() throws IOException {
		try {
			mapFacadeIface.deleteMarker(mapMarker);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		getResponse().getWriter().print(true);
	}
	
	/**
	 * 删除标记
	 * 
	 * @throws IOException
	 */
	public String deleteMarkerById() throws IOException {
		try {
			mapFacadeIface.deleteMarker(mapMarker);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载企业基本信息
	 * 
	 * @param
	 * @return 返回
	 * @throws Exception
	 */
	public String loadCompany() {
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(mapFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = mapFacadeIface.loadCompany(company);
			securityManage = securityManageFacadeIface.loadSecurityManageByCompanyId(company.getId());
			injuryManage = injuryManageFacadeIface.loadInjuryManageByCompanyId(company.getId());
			creditManage = creditManageFacadeIface.loadCreditManageByCompanyId(company.getId());// 查询信用管理信息
			imageFileList = imageFileFacadeIface.loadFiles(company.getId());
			if (null != imageFileList && imageFileList.size() > 0) {
				if (null != imageFileList.get(0)) {
					setImageOne(imageFileList.get(0));
				}
			}
			if (null != imageFileList && imageFileList.size() > 1) {
				if (null != imageFileList.get(1)) {
					setImageTwo(imageFileList.get(1));
				}
			}
			if (company.getHzTradeTypes() != null) {
				Iterator itr = company.getHzTradeTypes().iterator();
				while (itr.hasNext()) {
					DaIndustryParameter daIndustryParameter = (DaIndustryParameter) itr.next();
					daIndustryParameter = mapFacadeIface.loadTradeTypeById(daIndustryParameter);
					if (tradeTypes == null) {
						tradeTypes = new ArrayList<DaIndustryParameter>();
					}
					tradeTypes.add(daIndustryParameter);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return INPUT;
	}

	public String createModelInit() {
		try {
			// phVapor = vaporFacadeIface.loadVapor(getUserId(), tankId);
			// storageTankInfo = vaporFacadeIface.loadStorageTank(tankId);
			company = mapFacadeIface.loadCompany(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}

	/**
	 * 查坐标的公用方法
	 * 
	 * @throws IOException
	 * @throws JSONException 
	 * 
	 */
	public String loadMapMarker() throws IOException {
		try {
			mapMarker = mapFacadeIface.loadMapMarker(mapMarker.getMarkerType(),
					mapMarker.getMarkerId(), pagination);
		} catch (Exception e) {
			getResponse().getWriter().println("error");
			e.printStackTrace();
		}
		// getResponse().getWriter().println(mapMarker.getMarkerX() + "," +
		// mapMarker.getMarkerY());
		return SUCCESS;
	}

	/**
	 * 加载空标准化页面
	 * 
	 * @return
	 */
	public String loadNullStandard() {
		//
		return SUCCESS;
	}

	/**
	 * 加载flash地图,根据传入的areacode来判断该显示那张flash
	 * 
	 * @return
	 */
	public String loadFlashMap() {
		flashCode = "";
		if (null != areaCode) {
			String flashPath = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getRealPath("/");
			flashPath = url + "resources\\default\\flash\\map" + areaCode
					+ ".swf";
			File flash = new File(flashPath);
			if (flash.canRead()) {
				flashCode = areaCode;
			}
		}
		return SUCCESS;
	}

	public String loadChartIndex() {
		return SUCCESS;
	}
	
	/**
	 * 验证企业通过快速定位搜索的名称在企业库中是否是唯一的
	 * 
	 * @throws IOException
	 * @throws MapperException 
	 * @throws JSONException 
	 * 
	 */
	public String loadUniqueCompany() throws IOException, MapperException {
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try{
			if (company == null) {
				company = new DaCompany();
			}
			if (area != null) {
				area = mapFacadeIface.loadArea(area.getAreaCode());
				int areaRate = area.getGradePath().split("/").length - 1;
				if (areaRate == 5) {
					company.setSecondArea(area.getAreaCode());
				} else {
					company.setThirdArea(area.getAreaCode());
				}
			}
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(8);
			}
			companys = mapFacadeIface.loadCompanys(company, pagination, this
					.getUserDetail()); //地图界面点击“快速定位”搜索后自动标注功能
//			companys = mapFacadeIface.loadCompanysForQuickSearch(company, pagination, this
//					.getUserDetail());
			markerIds = mapFacadeIface.loadPhMapMarkerByType("company", null);
			if (mapMarker == null) {
				mapMarker = new MapMarker();
			}
			boolean isLabel = false; //是否标注
			if (companys != null && companys.size() > 0 && companys.size() == 1) { //
				if (null != markerIds && !"".equals(markerIds)) {
					if (markerIds.contains(String.valueOf(companys.get(0).getId()))) {
						isLabel = true;
						resultJSON.put("isLabel", isLabel);
						resultJSON.put("markerId", companys.get(0).getId());
						resultJSON.put("markerName", companys.get(0).getCompanyName());
					}
				}
				if (!isLabel) {
					mapMarker.setMarkerId(companys.get(0).getId());
					mapMarker.setMarkerName(companys.get(0).getCompanyName());
					mapMarker.setMarkerType("company");
					mapMarker.setMarkerHeight(16);
					mapMarker.setMarkerWidth(16);
					mapMarker.setMarkerImg("company.png");
					mapMarker.setMarkerLink("");
					mapMarker.setMarkerLat(markerLat);
					mapMarker.setMarkerLng(markerLng);
					//直接将查询到的企业标注信息保存到数据库
					mapMarker = mapFacadeIface.createMarker(mapMarker, getUserId());
					resultJSON.put("markerId", companys.get(0).getId());
					resultJSON.put("markerName", companys.get(0).getCompanyName());
					resultJSON.put("result", true);
				} else {
					resultJSON.put("result", false);
				}
			}
	 	}catch(Exception e){
	 		e.printStackTrace();
	 		resultJSON.put("result", false);
		}
	 	getResponse().getWriter().flush();
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List<DaCompany> getCompanys() {
		return companys;
	}

	public void setCompanys(List<DaCompany> companys) {
		this.companys = companys;
	}

	public FkArea getArea() {
		return area;
	}

	public void setArea(FkArea area) {
		this.area = area;
	}

	public void setMapFacadeIface(MapFacadeIface mapFacadeIface) {
		this.mapFacadeIface = mapFacadeIface;
	}

	public String getMarkerType() {
		return markerType;
	}

	public void setMarkerType(String markerType) {
		this.markerType = markerType;
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

	public String getMarkerIds() {
		return markerIds;
	}

	public void setMarkerIds(String markerIds) {
		this.markerIds = markerIds;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAnalyseModule() {
		return analyseModule;
	}

	public void setAnalyseModule(String analyseModule) {
		this.analyseModule = analyseModule;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public DaSecurityManage getSecurityManage() {
		return securityManage;
	}

	public void setSecurityManage(DaSecurityManage securityManage) {
		this.securityManage = securityManage;
	}

	public DaInjuryManage getInjuryManage() {
		return injuryManage;
	}

	public void setInjuryManage(DaInjuryManage injuryManage) {
		this.injuryManage = injuryManage;
	}

	public SecurityManageFacadeIface getSecurityManageFacadeIface() {
		return securityManageFacadeIface;
	}

	public void setSecurityManageFacadeIface(
			SecurityManageFacadeIface securityManageFacadeIface) {
		this.securityManageFacadeIface = securityManageFacadeIface;
	}

	public InjuryManageFacadeIface getInjuryManageFacadeIface() {
		return injuryManageFacadeIface;
	}

	public void setInjuryManageFacadeIface(
			InjuryManageFacadeIface injuryManageFacadeIface) {
		this.injuryManageFacadeIface = injuryManageFacadeIface;
	}

	public void setCreditManage(DaCreditManage creditManage) {
		this.creditManage = creditManage;
	}

	public DaCreditManage getCreditManage() {
		return creditManage;
	}

	public void setCreditManageFacadeIface(
			CreditManageFacadeIface creditManageFacadeIface) {
		this.creditManageFacadeIface = creditManageFacadeIface;
	}

	public List<DaImageFile> getImageFileList() {
		return imageFileList;
	}

	public void setImageFileList(List<DaImageFile> imageFileList) {
		this.imageFileList = imageFileList;
	}

	public DaImageFile getImageOne() {
		return imageOne;
	}

	public void setImageOne(DaImageFile imageOne) {
		this.imageOne = imageOne;
	}

	public DaImageFile getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(DaImageFile imageTwo) {
		this.imageTwo = imageTwo;
	}

	public void setImageFileFacadeIface(
			ImageFileFacadeIface imageFileFacadeIface) {
		this.imageFileFacadeIface = imageFileFacadeIface;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getFlashCode() {
		return flashCode;
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

	public DaCompanyPass getCompanyPass() {
		return companyPass;
	}

	public void setCompanyPass(DaCompanyPass companyPass) {
		this.companyPass = companyPass;
	}

	public List<DaCompany> getUnmarkerCompanies() {
		return unmarkerCompanies;
	}

	public void setUnmarkerCompanies(List<DaCompany> unmarkerCompanies) {
		this.unmarkerCompanies = unmarkerCompanies;
	}

	public String getReadValue() {
		return readValue;
	}

	public void setReadValue(String readValue) {
		this.readValue = readValue;
	}

	public List<TCoreArea> getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(List<TCoreArea> childAreas) {
		this.childAreas = childAreas;
	}

	public void setPubAreaFacadeIface(PubAreaFacadeIface pubAreaFacadeIface) {
		this.pubAreaFacadeIface = pubAreaFacadeIface;
	}

}
