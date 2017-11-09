package com.safetys.nbyhpc.web.action.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeAttechFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeTroubleFacadeIface;

/**
 * 管道情况统计
 * @author qiufc
 * 2011-07-08 qiufc 修改yqPipelineListByProperty方法在，增加按照管道类型查询数据。
 */
public class StatisticAction extends BaseAppAction{
	
	protected PipeLineFacadeIface pipeLineFacadeIface;
	protected PipeAttechFacadeIface pipeAttechFacadeIface;
	protected PipeTroubleFacadeIface pipeTroubleFacadeIface;
	protected Pagination pagination;
	private Long areaCode;
	private int type;//管道类型
	private int attechType;//附件类型
	protected List result;
	private String propertyName;
	private boolean propertyValue;
	private int intPropertyValue;
	private String lineType;//管道类型
	private DaPipelineInfo entity;
	
	/**
	 * 油气企业列表
	 * @return
	 * @throws Exception 
	 */
	public String yqCompanyList() throws Exception{
		try{
			result = pipeLineFacadeIface.loadYqCompanyListByArea(areaCode, type, null);
			Map<String, String> map = new HashMap<String, String>();
			List<DaPipelineCompanyInfo> t_list = (List<DaPipelineCompanyInfo>) result;
			List<DaPipelineCompanyInfo> new_list = new ArrayList<DaPipelineCompanyInfo>();
			for(DaPipelineCompanyInfo p : t_list){
				if(null!=p.getCompany()){
					String cid = String.valueOf(p.getCompany().getId());
					if(!(null!=map.get(cid) && cid.equals(map.get(cid)))){
						map.put(cid, cid);	
						new_list.add(p);
					}
				}
			}
			/**
			 * 分页
			 */
			List<DaPipelineCompanyInfo> lists = new ArrayList<DaPipelineCompanyInfo>();
			int itemCount = 0;
			long afterCount = 0;
			pagination = this.getPagination();
			pagination.setTotalCount(new_list.size());
			if (pagination.getItemCount() == 0) {
				itemCount = 0;
				if (pagination.getPageSize() > pagination.getTotalCount()) {
					afterCount = pagination.getTotalCount();
				} else {
					afterCount = pagination.getPageSize();
				}
			} else if (pagination.getItemCount() > 0) {
				itemCount = pagination.getPageSize()
						* (pagination.getCurrentPage() - 1);
				if (pagination.getTotalCount() < pagination.getCurrentPage()
						* pagination.getPageSize()) {
					afterCount = pagination.getTotalCount();
				} else {
					afterCount = pagination.getCurrentPage()
							* pagination.getPageSize();
				}
			}
			for (int i = itemCount; i < afterCount; i++) {
				lists.add(new_list.get(i));
			}
			result = lists;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "yq-company-list";
	}
	
	/**
	 * 企业列表
	 * @return
	 */
	public String companyList() throws Exception{
		result = pipeLineFacadeIface.loadCompanyListByArea(lineType, areaCode, this.getPagination());
		return "company-list";
	}
	
	/**
	 * 油气管道列表
	 * @return
	 */
	public String pipelineList() throws Exception{
		result = pipeLineFacadeIface.loadPipLineByArea(this.getEntity(),lineType, areaCode, this.getPagination());
		if(null!=lineType && "0".equals(lineType)){
			return "rq-pipline-list";
		}else{
			return "yq-pipline-list";
		}
	}
	
	/**
	 * 燃气企业列表
	 * @return
	 */
	public String rqCompanyList() throws Exception{
		result = pipeLineFacadeIface.loadRqCompanyListByArea(areaCode, this.getPagination());
		return "rq-company-list";
	}
	
	/**
	 * 油气管道列表
	 * @return
	 */
	public String yqPipelineList() throws Exception{
		result = pipeLineFacadeIface.loadYqPipLineByArea(type, areaCode, this.getPagination());
		return "yq-pipline-list";
	}
	
	/**
	 * 统计中，根据“是否通过竣工验收、是否办理安全生产许可证”等属性，加载油气管道。
	 * @return
	 * @throws Exception
	 */
	public String yqPipelineListByProperty() throws Exception{
		result = pipeLineFacadeIface.loadYqPipLineByProperty(propertyName, propertyValue, areaCode, lineType, this.getPagination());
		return "yq-pipline-list";
	}
	
	/**
	 * 统计中，根据“是否通过竣工验收、是否办理安全生产许可证”等属性，加载燃气管道。
	 * @return
	 * @throws Exception
	 */
	public String rqPipelineListByProperty() throws Exception{
		result = pipeLineFacadeIface.loadRqPipLineByProperty(propertyName, propertyValue, areaCode, this.getPagination());
		return "rq-pipline-list";
	}
	
	/**
	 * 统计中，根据“是否通过竣工验收、是否办理安全生产许可证”等属性，加载燃气管道。
	 * @return
	 * @throws Exception
	 */
	public String rqPipelineListByIntProperty() throws Exception{
		result = pipeLineFacadeIface.loadRqPipLineByIntProperty(propertyName, intPropertyValue, areaCode, this.getPagination());
		return "rq-pipline-list";
	}
	
	
	/**
	 * 燃气管道列表
	 * @return
	 * @throws Exception 
	 */
	public String rqPipelineList() throws Exception{
		result = pipeLineFacadeIface.loadRqPipLineByArea(areaCode, this.getPagination());
		return "rq-pipline-list";
	}

	/**
	 * 统计中，查看燃气管道隐患列表。
	 * @return
	 * @throws Exception
	 */
	public String rqTroubleList() throws Exception{
		result = pipeTroubleFacadeIface.loadRqTroublesByArea(areaCode, this.getPagination());
		return "rq-trouble-list";
	}
	
	/**
	 * 统计中，查看油气管道隐患列表。
	 * @return
	 * @throws Exception
	 */
	public String yqTroubleList() throws Exception{
		result = pipeTroubleFacadeIface.loadYqTroublesByArea(areaCode, type, this.getPagination());
		return "yq-trouble-list";
	}
	
	/**
	 * 统计中，查看燃气附件列表。
	 * @return
	 * @throws Exception
	 */
	public String rqAttechList() throws Exception{
		result = pipeLineFacadeIface.loadAttechByArea(areaCode, lineType, attechType, this.getPagination());
		return "rq-attech-list";
	}
	
	/**
	 * 统计中，查看油气附件列表。
	 * @return
	 * @throws Exception
	 */
	public String yqAttechList() throws Exception{
		try{
			result = pipeLineFacadeIface.loadAttechByArea(areaCode, lineType, attechType, this.getPagination());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "yq-attech-list";
	}
	
	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public List getResult() {
		return result;
	}

	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}
	public Pagination getPagination() {
		if (pagination == null){
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAttechType() {
		return attechType;
	}

	public void setAttechType(int attechType) {
		this.attechType = attechType;
	}

	public void setPipeAttechFacadeIface(PipeAttechFacadeIface pipeAttechFacadeIface) {
		this.pipeAttechFacadeIface = pipeAttechFacadeIface;
	}

	public void setPipeTroubleFacadeIface(PipeTroubleFacadeIface pipeTroubleFacadeIface) {
		this.pipeTroubleFacadeIface = pipeTroubleFacadeIface;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(boolean propertyValue) {
		this.propertyValue = propertyValue;
	}

	public int getIntPropertyValue() {
		return intPropertyValue;
	}

	public void setIntPropertyValue(int intPropertyValue) {
		this.intPropertyValue = intPropertyValue;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public DaPipelineInfo getEntity() {
		return entity;
	}

	public void setEntity(DaPipelineInfo entity) {
		this.entity = entity;
	}
	
}
