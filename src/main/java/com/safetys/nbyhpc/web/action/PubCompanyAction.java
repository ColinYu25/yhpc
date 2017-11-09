package com.safetys.nbyhpc.web.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.nbyhpc.domain.model.DaImageFile;
import com.safetys.nbyhpc.domain.model.TCoreArea;
import com.safetys.nbyhpc.domain.model.TCoreBzh;
import com.safetys.nbyhpc.domain.model.TCoreCompany;
import com.safetys.nbyhpc.domain.model.TCoreXzxk;
import com.safetys.nbyhpc.facade.iface.ImageFileFacadeIface;
import com.safetys.nbyhpc.facade.iface.PubAreaFacadeIface;
import com.safetys.nbyhpc.facade.iface.PubCompanyFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

public class PubCompanyAction extends DaAppAction {

	/**
	 * 企业基础数据读取
	 */
	private static final long serialVersionUID = 1732652428964996193L;
	private PubCompanyFacadeIface pubCompanyFacadeIface;
	private ImageFileFacadeIface imageFileFacadeIface;
	private PubAreaFacadeIface pubAreaFacadeIface;
	private Map<String, Object> map;
	private TCoreCompany coreCompany;
	private List<DaImageFile> imageFileList; // 图片上传列表
	private DaImageFile imageOne;
	private DaImageFile imageTwo;
	private List<TCoreArea> childAreas;
	private String areaCode;
	private String third_area;
	private String fouth_area;

	/**
	 * 企业读取档案信息
	 * 
	 * @throws Exception
	 */
	public String loadCoreCompanyInfo() {
		try {
			if (null != coreCompany && null != coreCompany.getId()) {
				map = pubCompanyFacadeIface.loadCoreCompanyInfo(coreCompany.getId());
				imageFileList = imageFileFacadeIface.loadFiles(coreCompany.getId());
				if (null != imageFileList && imageFileList.size() > 0) {
					for(int i = 0 ;i<imageFileList.size();i++){
						if(i==2){break;}
						if(imageFileList.get(i)!=null){
							if(imageFileList.get(i).getFileDescription()!=null && imageFileList.get(i).getFileDescription().equals("1")){
								setImageOne(imageFileList.get(i));
							}else if(imageFileList.get(i).getFileDescription()!=null && imageFileList.get(i).getFileDescription().equals("2")){
								setImageTwo(imageFileList.get(i));
							}else{
								if(i==0){
									setImageOne(imageFileList.get(i));
								}else if(i==1){
									setImageTwo(imageFileList.get(i));
								}
							}
						}
					}
				}
//				if (null != imageFileList && imageFileList.size() > 0) {
//					if (null != imageFileList.get(0)) {
//						setImageOne(imageFileList.get(0));
//					}
//				}
//				if (null != imageFileList && imageFileList.size() > 1) {
//					if (null != imageFileList.get(1)) {
//						setImageTwo(imageFileList.get(1));
//					}
//				}
				childAreas = pubAreaFacadeIface.findChildAreas(Nbyhpc.AREA_CODE);
			} else {
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * 异步调用
	 * 
	 * @description
	 */
	public String updateSqlVal() throws IOException, MapperException {
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try {
			String[] id = (String[]) map.get("id");
			String[] tableName = (String[]) map.get("entity");
			String[] fieldName = (String[]) map.get("fieldName");
			String[] value = (String[]) map.get("value");

			if ("".equals(id)) {
				try {
					String sql_ = "insert into " + tableName;
					pubCompanyFacadeIface.executeSQLUpdate(sql_);
				} catch (Exception e) {
					e.printStackTrace();
					resultJSON.put("result", false);
				}
			} else {
				if (pubCompanyFacadeIface.updateSqlVal(tableName[0],
						fieldName[0], Integer.valueOf(id[0]), value[0])) {
					resultJSON.put("result", true);
				} else {
					resultJSON.put("result", false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJSON.put("result", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(
				JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}
	
	/**
	 * 异步添加许可证
	 * @return
	 * @throws IOException
	 * @throws MapperException
	 */
	public String ajaxAddXzxk() throws IOException,MapperException{
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try{
			String[] company_id = (String[])map.get("company_id");
			String[] xkType = (String[])map.get("xkType");
			String[] licence = (String[])map.get("licence");
			String[] validityEnd = (String[])map.get("validityEnd");
			String[] permitScope = (String[])map.get("permitScope");
			TCoreXzxk xzxk = new TCoreXzxk();
			coreCompany = new TCoreCompany();
			coreCompany.setId(Long.parseLong(company_id[0]));
			xzxk.setCoreCompany(coreCompany);
			xzxk.setXkType(xkType[0]);
			xzxk.setLicence(licence[0]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			xzxk.setValidityEnd(sdf.parse(validityEnd[0]));
			xzxk.setPermitScope(permitScope[0]);
			long id = pubCompanyFacadeIface.ajaxAddXzxk(xzxk);
			if(id>0){
				resultJSON.put("id", id);
				resultJSON.put("result", true);
			}else{
				resultJSON.put("result", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
			resultJSON.put("result", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}
	
	
	/**
	 * 异步删除许可证
	 * @return
	 * @throws IOException
	 * @throws MapperException
	 */
	public String ajaxDelXzxk() throws IOException,MapperException{
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try{
			String[] id = (String[])map.get("id");
			if(id!=null&&id[0]!=null&&!id[0].equals("")){
				int i = pubCompanyFacadeIface.executeSQLUpdate("UPDATE T_CORE_XZXK SET IS_DELETED = 1 WHERE ID = "+id[0]);
				if(i>0){
					resultJSON.put("result", true);
				}else{
					resultJSON.put("result", false);
				}
			}else{
				resultJSON.put("result", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
			resultJSON.put("result", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}
	
	/**
	 * 异步添加标准化
	 * @return
	 * @throws IOException
	 * @throws MapperException
	 */
	public String ajaxAddBzh() throws IOException,MapperException{
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try{
			String[] company_id = (String[])map.get("company_id");
			String[] bzhType = (String[])map.get("bzhType");
			String[] licence = (String[])map.get("licence");
			String[] validityEnd = (String[])map.get("validityEnd");
			String[] bzhGrade = (String[])map.get("bzhGrade");
			TCoreBzh bzh = new TCoreBzh();
			coreCompany = new TCoreCompany();
			coreCompany.setId(Long.parseLong(company_id[0]));
			bzh.setCoreCompany(coreCompany);
			bzh.setBzhType(bzhType[0]);
			bzh.setLicence(licence[0]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bzh.setValidityEnd(sdf.parse(validityEnd[0]));
			bzh.setBzhGrade(bzhGrade[0]);
			long id = pubCompanyFacadeIface.ajaxAddBzh(bzh);
			if(id>0){
				resultJSON.put("id", id);
				resultJSON.put("result", true);
			}else{
				resultJSON.put("result", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
	 		resultJSON.put("result", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}
	
	/**
	 * 异步删除标准化
	 * @return
	 * @throws IOException
	 * @throws MapperException
	 */
	public String ajaxDelBzh() throws IOException,MapperException{
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try{
			String[] id = (String[])map.get("id");
			if(id!=null&&id[0]!=null&&!id[0].equals("")){
				int i = pubCompanyFacadeIface.executeSQLUpdate("UPDATE T_CORE_BZH SET IS_DELETED = 1 WHERE ID = "+id[0]);
				if(i>0){
					resultJSON.put("result", true);
				}else{
					resultJSON.put("result", false);
				}
			}else{
				resultJSON.put("result", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
	 		resultJSON.put("result", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}
	
	/**
	 * AJAX 获取三级区域
	 * @throws Exception
	 */
	public String ajaxThirdArea() throws Exception {
		StringBuilder html = new StringBuilder();
		List<TCoreArea> thirdAreas = new ArrayList<TCoreArea>();
		thirdAreas = pubAreaFacadeIface.findChildAreas(new Long(areaCode));
		html.append("<option value='0'>－请选择－</option>");
		for (TCoreArea tmpObj : thirdAreas) {
			if(third_area!=null&&!third_area.equals("null")&&!third_area.equals("")&&!third_area.equals("undefined")&&third_area.equals(tmpObj.getAreaCode().toString())){
				html.append("<option selected='selected' value='" + tmpObj.getAreaCode() + "'>" + tmpObj.getAreaName() + "</option>");
			}else{
				html.append("<option value='" + tmpObj.getAreaCode() + "'>" + tmpObj.getAreaName() + "</option>");
			}
		}
		this.getResponse().setContentType("text/plain;charset=utf-8");
		this.getResponse().getWriter().print(html.toString());
		this.getResponse().getWriter().close();
		return null;
	}
	
	/**
	 * AJAX 获取四级区域
	 * @throws Exception
	 */
	public String ajaxFouthArea() throws Exception {
		StringBuilder html = new StringBuilder();
		List<TCoreArea> fourthAreas = new ArrayList<TCoreArea>();
		fourthAreas = pubAreaFacadeIface.findChildAreas(new Long(areaCode));
		html.append("<option value='0'>－请选择－</option>");
		for (TCoreArea tmpObj : fourthAreas) {
			if(fouth_area!=null&&!fouth_area.equals("null")&&!fouth_area.equals("")&&!fouth_area.equals("undefined")&&fouth_area.equals(tmpObj.getAreaCode().toString())){
				html.append("<option selected='selected' value='" + tmpObj.getAreaCode() + "'>" + tmpObj.getAreaName() + "</option>");
			}else{
				html.append("<option value='" + tmpObj.getAreaCode() + "'>" + tmpObj.getAreaName() + "</option>");
			}
		}
		this.getResponse().setContentType("text/plain;charset=utf-8");
		this.getResponse().getWriter().print(html.toString());
		this.getResponse().getWriter().close();
		return null;
	}

	public void setPubCompanyFacadeIface(PubCompanyFacadeIface pubCompanyFacadeIface) {
		this.pubCompanyFacadeIface = pubCompanyFacadeIface;
	}

	public TCoreCompany getCoreCompany() {
		return coreCompany;
	}

	public void setCoreCompany(TCoreCompany coreCompany) {
		this.coreCompany = coreCompany;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
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

	public List<TCoreArea> getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(List<TCoreArea> childAreas) {
		this.childAreas = childAreas;
	}

	public void setPubAreaFacadeIface(PubAreaFacadeIface pubAreaFacadeIface) {
		this.pubAreaFacadeIface = pubAreaFacadeIface;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getThird_area() {
		return third_area;
	}

	public void setThird_area(String thirdArea) {
		third_area = thirdArea;
	}

	public String getFouth_area() {
		return fouth_area;
	}

	public void setFouth_area(String fouthArea) {
		fouth_area = fouthArea;
	}

}
