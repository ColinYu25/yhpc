package com.safetys.nbyhpc.web.action.mobile.vo;

import java.util.List;

import com.safetys.framework.domain.model.FkEnum;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.util.DateUtils;

public class DaNormalDangerVo extends MobileVo {

	private static final long serialVersionUID = -5293732463467197844L;

	private String description;

	private String completedDate;
	
	private Long industryId;
	
	private String industryText;
	
	private Long secondIndustryId;
	
	private String secondIndustryText;
	
	private boolean repaired;
	
	private String hazardSourceCode;
	
	private String hazardSourceName;
	
	private float governMoney;
	
	private String linkMan;

	private String linkTel;
	
	private Long userId;
	
	private String createTime;
	
	private List<DangerImageVo> imageList;
	
	private String companyName;
	
	private boolean allowEdit; //是否允许修改
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public Long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}

	public String getIndustryText() {
		return industryText;
	}

	public void setIndustryText(String industryText) {
		this.industryText = industryText;
	}

	public boolean isRepaired() {
		return repaired;
	}

	public void setRepaired(boolean repaired) {
		this.repaired = repaired;
	}

	public String getHazardSourceCode() {
		return hazardSourceCode;
	}

	public void setHazardSourceCode(String hazardSourceCode) {
		this.hazardSourceCode = hazardSourceCode;
	}

	public String getHazardSourceName() {
		return hazardSourceName;
	}

	public void setHazardSourceName(String hazardSourceName) {
		this.hazardSourceName = hazardSourceName;
	}

	public float getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(float governMoney) {
		this.governMoney = governMoney;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public List<DangerImageVo> getImageList() {
		return imageList;
	}

	public void setImageList(List<DangerImageVo> imageList) {
		this.imageList = imageList;
	}

	public Long getSecondIndustryId() {
		return secondIndustryId;
	}

	public void setSecondIndustryId(Long secondIndustryId) {
		this.secondIndustryId = secondIndustryId;
	}

	public String getSecondIndustryText() {
		return secondIndustryText;
	}

	public void setSecondIndustryText(String secondIndustryText) {
		this.secondIndustryText = secondIndustryText;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public boolean isAllowEdit() {
		return allowEdit;
	}

	public void setAllowEdit(boolean allowEdit) {
		this.allowEdit = allowEdit;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void buildByNormalDanger(DaNomalDanger danger, List<FkEnum> hazardSourceEnums, Long userId) {
		super.buildVo(danger);
		this.description = danger.getDescription();
		this.completedDate = DateUtils.date2Str(danger.getCompletedDate(), "yyyy-MM-dd");
		this.industryId = danger.getIndustry().getId();
		this.industryText = danger.getIndustry().getName();
		this.secondIndustryId = danger.getSecondIndustry().getId();
		this.secondIndustryText = danger.getSecondIndustry().getName();
		this.repaired = danger.isRepaired();
		this.hazardSourceCode = danger.getHazardSourceCode();
		this.linkMan = danger.getLinkMan();
		this.linkTel = danger.getLinkTel();
		for (FkEnum hs : hazardSourceEnums) {
			if (hs.getEnumCode().equals(this.hazardSourceCode)) {
				this.hazardSourceName = hs.getEnumName();
				break;
			}
		}
		this.governMoney = danger.getGovernMoney();
		this.createTime = DateUtils.date2Str(danger.getCreateTime(), "yyyy-MM-dd");
		//当前谁创建的谁可以修改
		this.allowEdit = danger.getUserId().equals(userId) && danger.isAllowEdit();
		this.userId = danger.getUserId();
	}
	
}
