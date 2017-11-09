package com.safetys.nbyhpc.util;

import java.util.Set;

import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;

public class RoleType1 {
	
	public final static String ADMIN = "ROLE_ADMINISTRATOR";
	
	public final static String PROVINCE = "ROLE_PRODUCE,ROLE_JIAOTONG,ROLE_JIANWEI,ROLE_GONGAN,ROLE_JIAOYU,ROLE_CHENGGUAN,ROLE_SHUILI," +
			                              "ROLE_HAIYANGYUYE,ROLE_LVYOU,ROLE_WENGUANG,ROLE_WEISHENG,ROLE_MINZONG,ROLE_DIANLI,ROLE_NONGJI," +
			                              "ROLE_HAISHI,ROLE_ZHIJIAN,ROLE_JIAOJING,ROLE_ANWEIBAN,ROLE_MAOYI,ROLE_FAGAI," +
			                              "ROLE_XIAOFANG,ROLE_GUOTU,ROLE_GONGSHANG,ROLE_RENSHE,ROLE_JINGXIN,ROLE_HAISHI";
	
	public final static String CITY = "ROLE_ANJIAN_CITY,ROLE_JIAOTONG_CITY,ROLE_JIANWEI_CITY,ROLE_GONGAN_CITY,ROLE_JIAOYU_CITY,ROLE_CHENGGUAN_CITY,ROLE_SHUILI_CITY," +
            "ROLE_HAIYANGYUYE_CITY,ROLE_LVYOU_CITY,ROLE_WENGUANG_CITY,ROLE_WEISHENG_CITY,ROLE_MINZONG_CITY,ROLE_DIANLI_CITY,ROLE_NONGJI_CITY," +
            "ROLE_HAISHI_CITY,ROLE_ZHIJIAN_CITY,ROLE_JIAOJING_CITY,ROLE_ANWEI_CITY,ROLE_MAOYI_CITY,ROLE_FAGAI_CITY," +
			"ROLE_XIAOFANG_CITY,ROLE_GUOTU_CITY,ROLE_GONGSHANG_CITY,ROLE_RENSHE_CITY,ROLE_JINGXIN_CITY,ROLE_HAISHI_CITY";
	
	public final static String COUNTY = "TOWN";
	
	public final static String AJJLOOK = "ROLE_AJJ_LOOK";//安监局查看角色
	
	public final static boolean isRoleByDepic(String roleCode,Set<FkRole> listRole) {
		if(roleCode.indexOf(",") > -1) {
			String [] depics = roleCode.split(",");
			for(int i = 0 ; i < depics.length ; i ++) {
				for (FkRole role1 : listRole) {
					
					if (depics[i].equals(role1.getRoleCode())) {
						return true;
					}
				}
			}
			return false;
		}else {
			for (FkRole role1 : listRole) {
				if (roleCode.equals(role1.getRoleCode())) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * 取得用户的所属部门
	 * @param listRole
	 * @return
	 */
	public final static String roleDepic(String roleCode, Set<FkRole> listRole) {
		if(roleCode.indexOf(",") > -1) {
			String [] depics = roleCode.split(",");
			for(int i = 0 ; i < depics.length ; i ++) {
				for (FkRole role1 : listRole) {
					if (depics[i].equals(role1.getRoleCode())) {
						return role1.getRoleDepic();
					}
				}
			}
			return "";
		}else {
			for (FkRole role1 : listRole) {
				if (roleCode.equals(role1.getRoleCode())) {
					return role1.getRoleDepic();
				}
			}
			return "";
		}
	}
	
	public final static String roleByDepic(FkUser user) {
		if(null!=user){
			Long firstArea = user.getFkUserInfo().getFirstArea();
			Long secondArea = user.getFkUserInfo().getSecondArea();
			Long thirdArea = user.getFkUserInfo().getThirdArea();
			if(firstArea!=null && firstArea > 0l){
				if(secondArea!=null && secondArea > 0l){
					if(thirdArea!=null && thirdArea > 0l){
						return null;
					}else{
						return RoleType1.roleDepic(RoleType1.CITY, user.getFkRoles());
					}
				}else{
					return RoleType1.roleDepic(RoleType1.PROVINCE, user.getFkRoles());
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public final static String roleLevelByUser(FkUser user) {
		if(null!=user){
			Long firstArea = user.getFkUserInfo().getFirstArea();
			Long secondArea = user.getFkUserInfo().getSecondArea();
			Long thirdArea = user.getFkUserInfo().getThirdArea();
			if(firstArea!=null && firstArea > 0l){
				if(secondArea!=null && secondArea > 0l){
					if(thirdArea!=null && thirdArea > 0l){
						return null;
					}else{
						return "CITY";
					}
				}else{
					return "PROVINCE";
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
