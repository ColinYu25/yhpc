<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${base}/validator/css/validator.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
table{ font-size:12px; color:#000000;}
.line { border:solid 1px #000000;border-right:none;border-bottom:none;}
.line td { border-right:solid 1px #000000; border-bottom:solid 1px #000000;font-weight:bold}
.under_border{
	border-bottom-width: 1px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: solid;
	border-left-style: none;	
}
.none_border{
	border-top-style: none;
	border-top-style: none;
	border-right-style: none;
	border-left-style: none;	
}
-->
</style>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
<script language="javascript" type="text/javascript" src="${base}/datePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script language="JavaScript" type="text/javascript" src="${base}/validator/js/mootools.js"></script>
<script language="JavaScript" type="text/javascript" src="${base}/validator/js/full-validator.js"></script>
<script language="javascript" src="${resourcePath}/js/print.js"></script>
<script type="text/javascript">
function get(id){return document.getElementById(id)};
var areaObj=new Area("${areaXmlUrl}");

var formValidator=Validator.setup({   
    form : 'companyInfoForm',
    configs : 'attribute,tag',
	warns : 'follow,color,class,alert',
	color : {warn :'black', pass:'black'}
});  


function doValidate(){
if(formValidator.validate()){
		//alert("00000");
		
		//alert(document.getElementById("frdb").value);
		
		get("companyInfoForm").submit();
  		return false;
 	}
 	return false;
}
</script>
</head>

<body>
<form action="company_save.xhtml" method="post" name="companyInfoForm" id="companyInfoForm" onsubmit="return doValidate();">
<input type="hidden" name="entity.id" value="${entity.id}"/>
<input type="hidden" name="entity.isSynchro" value="1"/>
<input type="hidden" name="entity.company.id" value="${company.id}"/>
<div id="page1">
<table width="90%" border="0" cellspacing="0" cellpadding="10" align="center">
  <tr>
    <td colspan="2" align="center" style=" font-size:24px; font-family:黑体">危险化学品企业安全生产工作年度情况报告表</td>
  </tr>
  <tr>
    <td width="60%">单位名称(盖章)：</td> 
    <td width="40%" align="right">报告时间：
    <input type="text" name="entity.reportdate" id="reportdate" class="under_border" value="${entity.reportdate?string('yyyy-MM-dd')}" maxlength="10" size="17" onfocus="WdatePicker();"/> 
    <ui:v for="reportdate" rule="require" empty="报告时间为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
	</td>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" style=" font-size:16px" bgcolor="#C8F1FF"><strong>一、企业基本概况</strong></td>
  </tr>
  <tr>
    <td width="20%" bgcolor="#ccffFF">企业名称</td>
    <td width="80%" colspan="3">${entity.company.companyName}&nbsp;</td>
  </tr>
  <tr>
    <td width="20%" bgcolor="#ccffFF">企业地址</td>
    <td width="30%">
    ${entity.company.regAddress}
	</td>

	<td width="20%" bgcolor="#ccffFF">所在区域</td>
    <td >
      
     <#if entity?? && entity.company?? && entity.company.secondArea??>
     <@fkMacros.getSelectArea entity.company.secondArea/>
     </#if>
       县(市)区　
     <#if entity?? && entity.company?? && entity.company.secondArea??>
    	 <@fkMacros.getSelectArea entity.company.thirdArea/>
     </#if>       
       乡镇(街道)
     <div style="float:right"> </div>
     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line" style=" border-top:none">
  <tr>
    <td colspan="3" align="center" bgcolor="#DFFAFF">法人代表(或主要负责人)</td>
    <td colspan="3" align="center" bgcolor="#DFFAFF">分管安全负责人</td>
  </tr>
  <tr>
    <td width="16%" align="center" bgcolor="#ccffFF">姓====名</td>
    <td width="18%" align="center" bgcolor="#ccffFF">年内有无变化</td>
    <td width="16%" align="center" bgcolor="#ccffFF">手机号码</td>
    <td width="16%" align="center" bgcolor="#ccffFF">姓名</td>
    <td width="18%" align="center" bgcolor="#ccffFF">年内有无变化</td>
    <td width="16%" align="center" bgcolor="#ccffFF">手机号码</td>
  </tr>
  <tr>
    <td align="center"><input type="text" name="entity.frdb" value="${entity.frdb}" class="under_border" id="frdb" size="10" maxlength="15"/><ui:v for="frdb" rule="require" empty="法人代表(或主要负责人)为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></&nbsp;</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.haschangeFrdb?? && entity.haschangeFrdb == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>
        <input type="radio" name="entity.haschangeFrdb" value="true" ${isTrue}/> 有　
        <input type="radio" name="entity.haschangeFrdb" value="false" ${isFalse}/> 无
    </td>
    <td align="center"><input type="text" name="entity.frdbPhone" value="${entity.frdbPhone}" id="frdb_phone" class="under_border" size="10" maxlength="15"/><ui:v for="frdb_phone" rule="phone_mobile" empty="法人代表(或主要负责人)手机为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="法人代表手机格式错误&nbsp;"/>&nbsp;</td>
    <td align="center"><input type="text" name="entity.fgfzr" value="${entity.fgfzr}" id="fgfzr" class="under_border" size="10" maxlength="15"/><ui:v for="fgfzr" rule="require" empty="负责人为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>&nbsp;</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.haschangeFgfzr?? && entity.haschangeFgfzr == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>
	<input type="radio" name="entity.haschangeFgfzr" value="true" ${isTrue}/> 有　
	<input type="radio" name="entity.haschangeFgfzr" value="false" ${isFalse}/> 无</td>
    <td align="center"><input type="text" name="entity.fgfzrPhone" value="${entity.fgfzrPhone}" id="fgfzrPhone" class="under_border" size="10" maxlength="15"/><ui:v for="fgfzrPhone" rule="phone_mobile" empty="负责人手机为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="负责人手机格式错误&nbsp;"/>&nbsp;</td>
  </tr>

</table>

<table width="100%" border="0" cellspacing="0" cellpadding="5" class="line" style=" border-top:none">
   <tr>
    <td width="25%" bgcolor="#ccffFF">安全部门负责人</td>
    <td width="25%" align=""><input type="text" name="entity.aqbmFzr" value="${entity.aqbmFzr}" class="under_border" size="10" maxlength="15"/>&nbsp;</td>
    <td width="25%" align="" bgcolor="#ccffFF">年内有无变化&nbsp;</td>
    <td width="25%" align="">
        	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.haschangeAqbm?? && entity.haschangeAqbm == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
	<input type="radio" name="entity.haschangeAqbm" value="true" ${isTrue}/> 有　
	<input type="radio" name="entity.haschangeAqbm" value="false" ${isFalse}/> 无
    &nbsp;</td>
  </tr>  
   <tr>
    <td width="25%" bgcolor="#ccffFF">安全部门负责人手机</td>
    <td width="25%" align=""><input type="text" name="entity.aqbmFzrPhone" value="${entity.aqbmFzrPhone}" class="under_border" size="10" maxlength="15"/>&nbsp;</td>
    <td width="25%" align="center" bgcolor="#ccffFF">&nbsp;</td>
    <td width="25%" align="center">
        	
    &nbsp;</td>
  </tr>   
   <tr>
    <td width="25%" bgcolor="#ccffFF">有无安全部门</td>
    <td width="25%" align="">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasAqbm?? && entity.hasAqbm == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>    
	    <input type="radio" name="entity.hasAqbm" value="true" ${isTrue}/> 有　　
		<input type="radio" name="entity.hasAqbm" value="false" ${checked}/> 无</td>
		
    <td width="25%" align="" bgcolor="#ccffFF">安全部门名称&nbsp;</td>
    <td width="25%" align=""><input type="text" name="entity.aqbm" value="${entity.aqbm}" class="under_border" size="10"/>&nbsp;</td>
  </tr> 
   <tr>
    <td width="25%" bgcolor="#ccffFF">专职安管员数</td>
    <td width="25%" align=""><input type="text" name="entity.zzagy" value="${entity.zzagy}" id="zzagy" class="under_border" size="5" maxlength="15"/> 人<ui:v for="zzagy" rule="number" empty="&nbsp;" require="false" pass="&nbsp;" tips="&nbsp;" warn="专职安管员数必须为数字&nbsp;"/>&nbsp;</td>		
    <td width="25%" align="" bgcolor="#ccffFF">职工总数&nbsp;</td>
    <td width="25%" align=""><input type="text" name="entity.works" value="${entity.works}" id="works" style="width:40px" maxlength="6" class="under_border"/><span style="margin-right:40px">名</span><ui:v for="works" rule="number" empty="职工总数为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="职工总数必须为数字&nbsp;"/></td>
  </tr>   
  
   <tr>
    <td width="25%" bgcolor="#ccffFF">年内职工流出数</td>
    <td width="25%" align=""><input type="text" name="entity.lostWorks" value="${entity.lostWorks}" id="lostWorks" style="width:40px" maxlength="6" class="under_border"/><span style="margin-right:40px">名</span><ui:v for="lostWorks" rule="number" empty="年内职工流出数为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="年内职工流出数必须为数字&nbsp;"/></td>
    <td width="25%" align="" bgcolor="#ccffFF">年内新招职工数</td>
    <td width="25%" align=""><input type="text" name="entity.newWorks" value="${entity.newWorks}" id="newWorks" style="width:40px" maxlength="6" class="under_border"/><span style="margin-right:40px">名</span><ui:v for="newWorks" rule="number" empty="年内新招职工数为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="年内新招职工数必须为数字&nbsp;"/></td>
  </tr>  
   <tr>
    <td width="25%" bgcolor="#ccffFF">年度工业产值</td>
    <td width="25%" align=""><input type="text" name="entity.annualProduction" value="${entity.annualProduction}" id="annualProduction" style="width:70px" maxlength="8" class="under_border"/><span style="margin-right:40px">万元</span><ui:v for="annualProduction" rule="double" empty="年度工业产值为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="年度工业产值必须为数字&nbsp;"/></td>
    <td width="25%" align="" bgcolor="#ccffFF">年内安全生产投入</td>
    <td width="25%" align=""><input type="text" name="entity.annualInput" value="${entity.annualInput}" id="annualInput" style="width:40px" maxlength="6" class="under_border"/><span style="margin-right:40px">万元</span><ui:v for="annualInput" rule="double" empty="年内安全生产投入为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="年内安全生产投入必须为数字&nbsp;"/></td>
  </tr>
</table>
</div>

<br>
<div id="page2">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>二、安全生产许可情况</strong></td>
  </tr>
  <tr>
    <td width="25%" bgcolor="#ccffFF">安全生产(经营)许可证编号</td>
	<td width="25%" align="center"><input type="text" name="entity.licence" value="${entity.licence}" id="licence" class="under_border"/>&nbsp;</td>
	<td width="25%" bgcolor="#ccffFF">有效期</td>
	<td width="25%" align="center">
	<input type="text" name="entity.enddate" id="enddate" class="under_border" value="${entity.enddate?string('yyyy-MM-dd')}" maxlength="10" size="17" onfocus="WdatePicker();"/>
	</td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">主要许可范围</td>
    <td colspan="3">
    <textarea name="entity.permitScope" id="permitScope" style="width:98%;height:50px;word-break:break-all;" cols="10" wrap="virtual">${entity.permitScope}</textarea>
    &nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">有无新、改、扩建项目</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasNewproject?? && entity.hasNewproject == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="radio" name="entity.hasNewproject" value="true" ${isTrue}/> 有　
		<input type="radio" name="entity.hasNewproject" value="false" ${isFalse}/> 无    </td>
    <td bgcolor="#ccffFF">有无试生产项目</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasTestproject?? && entity.hasTestproject == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
	<input type="radio" name="entity.hasTestproject" value="true" ${isTrue}/> 有　
	<input type="radio" name="entity.hasTestproject" value="false" ${isFalse}/> 无</td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">试生产有无超期</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasExceedTestproject?? && entity.hasExceedTestproject == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>      
        <input type="radio" name="entity.hasExceedTestproject" value="true" ${isTrue}/> 有　
		<input type="radio" name="entity.hasExceedTestproject" value="false" ${isFalse}/> 无    </td>
    <td bgcolor="#ccffFF">&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>三、安全评价情况</strong></td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">安评报告是否在有效期内</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasVoidApgb?? && entity.hasVoidApgb == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>  	
        <input type="radio" name="entity.hasVoidApgb" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.hasVoidApgb" value="false" ${isFalse}/> 否</td>
	<td bgcolor="#ccffFF">安评报告对策措施是否落实</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasVoidApgb?? && entity.hasVoidApgb == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>  	
        <input type="radio" name="entity.hasApbgls" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.hasApbgls" value="false" ${isFalse}/> 否</td>		
  </tr>
  <tr>
    <td bgcolor="#ccffFF">安全评价机构名称</td>
    <td colspan="3"><input type="text" name="entity.aqpjjg" value="${entity.aqpjjg}" id="aqpjjg" class="under_border" size="40" maxlength="50"/>
    <ui:v for="aqpjjg" rule="require" empty="安全评价机构名称为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
    &nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>四、安全生产标准化开展情况</strong></td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">达标级别</td>
	<td align="center">
	<select name="entity.grade" id="grade">
	<option value="1" <#if entity?? && entity.grade?? && entity.grade == '1'>selected</#if>>国家一级</option>
	<option value="2" <#if entity?? && entity.grade?? && entity.grade == '2'>selected</#if>>国家二级</option>		
	<option value="3" <#if entity?? && entity.grade?? && entity.grade == '3'>selected</#if>>省级</option>
	<option value="4" <#if entity?? && entity.grade?? && entity.grade == '4'>selected</#if>>市级</option>	
	<option value="0" <#if entity?? && entity.grade?? && entity.grade == '0'>selected</#if>>无</option>		
	</select>	
	</td>
	<td bgcolor="#ccffFF">达标有效期</td>
	<td width="25%" align="center">
	<input type="text" name="entity.levelEnddate" id="levelEnddate" class="under_border" value="${entity.levelEnddate?string('yyyy-MM-dd')}" maxlength="10" size="17" onfocus="WdatePicker();"/>
	</td>		
  </tr>
  <tr>
    <td bgcolor="#ccffFF">安全生产标准化体系是否有效运行</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.isAqbzhRunok?? && entity.isAqbzhRunok == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>      
        <input type="radio" name="entity.isAqbzhRunok" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.isAqbzhRunok" value="false" ${isFalse}/> 否</td>
	<td bgcolor="#ccffFF">年度安全生产标准化自评得分</td>
    <td align="center">　<input type="text" name="entity.standardScore" id="standardScore" value="${entity.standardScore?string('###.##')}" class="under_border" size="3" maxlength="5"/>分
    <ui:v for="standardScore" rule="require" empty="年度安全生产标准化自评得分为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
    </td>	
  </tr>
</table>
</div>

<br>
<div id="page3">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>五、隐患排查治理情况</strong></td>
  </tr>
  <tr>
    <td width="25%" bgcolor="#ccffFF">是否建立隐患排查治理规章制度</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasTroubleRule?? && entity.hasTroubleRule == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	
        <input type="radio" name="entity.hasTroubleRule" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.hasTroubleRule" value="false" ${isFalse}/> 否</td>
	<td width="25%" bgcolor="#ccffFF">是否经常开展隐患排查治理</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasTroubleWork?? && entity.hasTroubleWork == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="radio" name="entity.hasTroubleWork" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.hasTroubleWork" value="false" ${isFalse}/> 否</td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">有无建立隐患排查治理台帐</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYhpctz?? && entity.hasYhpctz == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="radio" name="entity.hasYhpctz" value="true" ${isTrue}/> 有　
        <input type="radio" name="entity.hasYhpctz" value="false" ${isFalse}/> 无</td>
	<td bgcolor="#ccffFF">是否依法报送隐患排查治理信息</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYfbsyh?? && entity.hasYfbsyh == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="radio" name="entity.hasYfbsyh" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.hasYfbsyh" value="false" ${isFalse}/> 否</td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">重大事故隐患整改“五落实”</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasZdyhzg?? && entity.hasZdyhzg == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>      
        <input type="radio" name="entity.hasZdyhzg" value="true" ${isTrue}/> 有　
		<input type="radio" name="entity.hasZdyhzg" value="false" ${isFalse}/> 无    </td>
    <td bgcolor="#ccffFF">是否落实执法部门提出的安全生产整改意见</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasLszgyj?? && entity.hasLszgyj == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>       
	<input type="radio" name="entity.hasLszgyj" value="true" ${isTrue}/> 有　
	<input type="radio" name="entity.hasLszgyj" value="false" ${isFalse}/> 无</td>
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>六、应急管理工作情况</strong></td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">应急救援预案是否评审</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYjjyyaPs?? && entity.hasYjjyyaPs == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>   	
        <input type="radio" name="entity.hasYjjyyaPs" value="true" ${isTrue}/> 是　
        <input type="radio" name="entity.hasYjjyyaPs" value="false" ${isFalse}/> 否</td>
	<td bgcolor="#ccffFF">
        应急救援预案是否备案</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.isYjjyyaBa?? && entity.isYjjyyaBa == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	
        <input type="radio" name="entity.isYjjyyaBa" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.isYjjyyaBa" value="false" ${isFalse}/> 否</td>		
  </tr>
  <tr>
    <td bgcolor="#ccffFF">应急救援预案备案时间</td>
	<td align="center">
	<input type="text" name="entity.yjjyyaBaDate" id="yjjyyaBaDate" class="under_border" value="${entity.yjjyyaBaDate?string('yyyy-MM-dd')}" maxlength="10" size="17" onfocus="WdatePicker();"/>
	<ui:v for="yjjyyaBaDate" rule="require" empty="应急救援预案备案时间为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
	</td>
	<td bgcolor="#ccffFF">
        应急救援预案备案部门</td>
	<td align="center">
    	<#assign shen=""/>
    	<#assign shi=""/>
    	<#assign xian=""/>    	
        <#if entity?? && entity.yjjyyaBaDept?? && entity.yjjyyaBaDept == 3 >
	    	<#assign shen="checked"/>
	    <#elseif entity?? && entity.yjjyyaBaDept?? && entity.yjjyyaBaDept == 2>
	    	<#assign shi="checked"/>
	    <#elseif entity?? && entity.yjjyyaBaDept?? && entity.yjjyyaBaDept == 1>
	    	<#assign xian="checked"/>
        </#if> 	
        <input type="radio" name="entity.yjjyyaBaDept" value="3" ${shen}/> 省
        <input type="radio" name="entity.yjjyyaBaDept" value="2" ${shi}/> 市
		<input type="radio" name="entity.yjjyyaBaDept" value="1" ${xian}/> 县</td>		
  </tr>
  <tr>
    <td bgcolor="#ccffFF">年内综合应急演练</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYjyl?? && entity.hasYjyl == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="radio" name="entity.hasYjyl" value="true" ${isTrue}/> 有　
		<input type="radio" name="entity.hasYjyl" value="false" ${isFalse}/> 无</td>
	<td bgcolor="#ccffFF">
        参加综合应急演练人数</td>
	<td align="center"><input type="text" name="entity.ylrs" value="${entity.ylrs}" id="ylrs" class="under_border" size="3" maxlength="4"/> 人
	<ui:v for="ylrs" rule="number" empty="参加综合应急演练人数为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="参加综合应急演练人数只能输入数字&nbsp;"/>	
	</td>		
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>七、重大危险源管理情况</strong></td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">是否构成重大危险源单位</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.isZdwxy?? && entity.isZdwxy == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="radio" name="entity.isZdwxy" value="true" ${isTrue}/> 是　
	    <input type="radio" name="entity.isZdwxy" value="false" ${isFalse}/> 否</td>
	<td bgcolor="#ccffFF">重大危险源数量</td>
	<td width="25%" align="center"><input type="text" name="entity.zdwxyNum" value="${entity.zdwxyNum}" id="zdwxyNum" class="under_border" size="3" maxlength="4"/> 个
	<ui:v for="zdwxyNum" rule="number" empty="重大危险源数量为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="重大危险源数量只能输入数字&nbsp;"/>	
	</td>		
  </tr>
  <tr>
    <td bgcolor="#ccffFF">年内有无新增重大危险源</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasNewzdwxy?? && entity.hasNewzdwxy == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	    
        <input type="radio" name="entity.hasNewzdwxy" value="true" ${isTrue}/> 有　
		<input type="radio" name="entity.hasNewzdwxy" value="false" ${isFalse}/> 无 </td>
	<td bgcolor="#ccffFF">是否每半年报告一次重大危险源安全管理自查情况</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasReportZdwxy?? && entity.hasReportZdwxy == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	     
        <input type="radio" name="entity.hasReportZdwxy" value="true" ${isTrue}/> 是　
	    <input type="radio" name="entity.hasReportZdwxy" value="false" ${isFalse}/> 否</td>
  </tr>
</table>
</div>

<br>
<div id="page4">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>八、安全生产持证上岗情况</strong></td>
  </tr>
  <tr>
    <td width="25%" bgcolor="#ccffFF">法人代表(主要负责人)是否取证</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.frdbHasCert?? && entity.frdbHasCert == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="radio" name="entity.frdbHasCert" value="true" ${isTrue}/> 是　
        <input type="radio" name="entity.frdbHasCert" value="false" ${isFalse}/> 否</td>
	<td width="25%" bgcolor="#ccffFF">分管安全负责人是否取证</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.fgfzrHasCert?? && entity.fgfzrHasCert == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="radio" name="entity.fgfzrHasCert" value="true" ${isTrue}/> 是　
		<input type="radio" name="entity.fgfzrHasCert" value="false" ${isFalse}/> 否</td>
  </tr>
  <tr>
    <td bgcolor="#ccffFF">安全部门负责人是否取证</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.aqbmfzrHasCert?? && entity.aqbmfzrHasCert == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="radio" name="entity.aqbmfzrHasCert" value="true" ${isTrue}/> 有　
		<input type="radio" name="entity.aqbmfzrHasCert" value="false" ${isFalse}/> 无</td>
	<td bgcolor="#ccffFF">特种作业人员持证率</td>
    <td align="center"> <input type="text" name="entity.tzyzryPercent" value="${entity.tzyzryPercent}" id="tzyzryPercent" class="under_border" size="3" maxlength="4"/>　%
    <ui:v for="tzyzryPercent" rule="double" empty="特种作业人员持证率为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="特种作业人员持证率只能输入数字&nbsp;"/>	
    </td>
  </tr>
</table>  
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line" style=" border-top:none">  
  <tr>
    <td align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>九、生产安全事故情况(含未造成人员伤亡的生产安全事故)</strong> </td>
  </tr>
  <tr>
    <td align="center" style=" font-size:16px">
    <a href="javascript:add()">增加</a>
    </td>
  </tr>
</table>
<div id="container">
<#assign accidentNum = 0/>
<#if entity?? && entity.accidents?? && entity.accidents?size gt 0 >
<#list entity.accidents as item>
<#assign accidentNum = item_index + 1/>
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line" style=" border-top:none" id="tab${item_index}">  
  <tr>
    <td width="10%" rowspan="3" align="center" bgcolor="#ccffFF">事故类型</td>
	<td width="10%" rowspan="3" align="center">
	<input type="hidden" name="accidents[${item_index}].id" value="${item.id}"/>
	<select name="accidents[${item_index}].type">
	<option value="1" <#if item.type == 1>selected</#if>/> 火灾</option>
	<option value="2" <#if item.type == 2>selected</#if>/> 爆炸</option>
	<option value="3" <#if item.type == 3>selected</#if>/> 泄漏</option>
	<option value="4" <#if item.type == 4>selected</#if>/> 其它</option>
	</select>
	</td>
    <td width="10%" rowspan="3" align="center" bgcolor="#ccffFF">伤亡原因</td>
	<td width="10%"  rowspan="3" align="center">
	<select name="accidents[${item_index}].hurtType">
	<option value="1" <#if item.hurtType == 1>selected</#if>/>物体打击</option>
	<option value="2" <#if item.hurtType == 2>selected</#if>/>车辆伤害　</option>
	<option value="3" <#if item.hurtType == 3>selected</#if>/>机械伤害</option>
	<option value="4" <#if item.hurtType == 4>selected</#if>/>起重伤害</option>
	<option value="5" <#if item.hurtType == 5>selected</#if>/>触电</option>
	<option value="6" <#if item.hurtType == 6>selected</#if>/>灼伤</option>
	<option value="7" <#if item.hurtType == 7>selected</#if>/>火灾</option>
	<option value="8" <#if item.hurtType == 8>selected</#if>/>高处坠落</option>
	<option value="9" <#if item.hurtType == 9>selected</#if>/>电容爆炸</option>					
	<option value="10" <#if item.hurtType == 11>selected</#if>/>中毒和窒息</option>					
	<option value="11" <#if item.hurtType == 11>selected</#if>/>其他伤害</option>							
	</select>
	</td>	
	<td width="10%" rowspan="3" align="center" bgcolor="#ccffFF">事故损失</td>

	<td width="" rowspan="3">死亡：<input type="text" name="accidents[${item_index}].dead" size="3" value="${item.dead}"/>人；<br/><br/> 
	受伤：<input type="text" name="accidents[${item_index}].hurt" size="3" value="${item.hurt}"/>人；<br/><br/> 
	直接经济损失：　　　
	<input type="text" name="accidents[${item_index}].lost" size="4" value="${item.lost}"/>
	万元</td>		
  </tr>
  
  <tr>
    <td align="center" bgcolor="#ccffFF" >事故主要原因(简述)</td>
	<td align="center" bgcolor="#ccffFF">事故责任人处理情况</td>	
	<td align="center" rowspan="2"><a href="javascript:del(${item_index})">删除</a></td>		
  </tr>
  <tr>
    <td align="center">
     <textarea style="width:95%;height:100px" name="accidents[${item_index}].mainCause">${item.mainCause}</textarea>
    </td>
	<td align="center">
     <textarea style="width:95%;height:100px" name="accidents[${item_index}].processResult">${item.processResult}</textarea>
	</td>
  </tr> 
</table>
</#list>
</#if>
</div>
</div>

<br>
<div id="page5">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="2" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>十、其他</strong></td>
  </tr>
  <tr>
    <td width="50%" align="center" bgcolor="#DFFAFF">年度安全生产工作创新及亮点</td>
	<td width="50%" align="center" bgcolor="#DFFAFF">年度安全生产工作存在的突出问题</td>
  </tr>
  <tr>
    <td align="center">
     <textarea style="width:95%;height:100px" name="entity.workinfo" id="workinfo">${entity.workinfo}</textarea>
     <ui:v for="workinfo" rule="require" empty="年度安全生产工作创新及亮点为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>	
    </td>
    <td align="center">
     <textarea style="width:95%;height:100px" name="entity.workinfoProblem" id="workinfoProblem">${entity.workinfoProblem}</textarea>
     <ui:v for="workinfoProblem" rule="require" empty="年度安全生产工作存在的突出问题点为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>	     
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center" bgcolor="#C8F1FF" style=" font-size:16px"><strong>十一、下一年度安全生产工作要点及建议</strong></td>
  </tr>
  <tr>
    <td colspan="2" align="center">
	  <textarea style="width:98%;height:100px" name="entity.workAdvise" id="workAdvise">${entity.workAdvise}</textarea>
		<ui:v for="workAdvise" rule="require" empty="下一年度安全生产工作要点及建议为必填&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>	     
	</td>
  </tr>
</table>
</div>
<div style="text-align:center;padding-top:5px;">
<input type="button" value=" 保 存 " onclick="doValidate()"/>
<input type="button" value=" 取 消 " onclick="window.location.href ='company_list.xhtml'"/>
<!--
<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　
<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　
-->
</div>
</form>

<div id="tableContent" style="display:none">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line" style=" border-top:none" id="tab@">  

  <tr>
    <td width="10%" rowspan="3" align="center" bgcolor="#ccffFF">事故类型</td>
	<td width="10%" rowspan="3" align="center">
	<select name="accidents[@].type">
	<option value="1" /> 火灾</option>
	<option value="2" /> 爆炸</option>
	<option value="3" /> 泄漏</option>
	<option value="4" /> 其它</option>
	</select>
	</td>
    <td width="10%" rowspan="3" align="center" bgcolor="#ccffFF">伤亡原因</td>
	<td width="10%"  rowspan="3" align="center">
	<select name="accidents[@].hurtType">
	<option value="1" />物体打击</option>
	<option value="2" />车辆伤害　</option>
	<option value="3" />机械伤害</option>
	<option value="4" />起重伤害</option>
	<option value="5" />触电</option>
	<option value="6" />灼伤</option>
	<option value="7" />火灾</option>
	<option value="8" />高处坠落</option>
	<option value="9" />电容爆炸</option>					
	<option value="10" />中毒和窒息</option>					
	<option value="11" />其他伤害</option>							
	</select>
	</td>	
	<td width="10%" rowspan="3" align="center" bgcolor="#ccffFF">事故损失</td>
	<td width="" rowspan="3">死亡：<input type="text" name="accidents[@].dead" size="3"/>人；<br/><br/> 
	受伤：<input type="text" name="accidents[@].hurt" size="3"/>人；<br/><br/> 
	直接经济损失：　　　
	<input type="text" name="accidents[@].lost" size="4"/>
	万元</td>		
  </tr>
  
  <tr>
    <td align="center" bgcolor="#ccffFF">事故主要原因(简述)</td>
	<td align="center" bgcolor="#ccffFF">事故责任人处理情况</td>	
	<td align="center" rowspan="2"><a href="javascript:del(@)">删除</a></td>		
  </tr>
  <tr>
    <td align="center">
     <textarea style="width:95%;height:100px" name="accidents[@].mainCause"></textarea>
    </td>
	<td align="center">
     <textarea style="width:95%;height:100px" name="accidents[@].processResult"></textarea>
	</td>
  </tr> 
</table>
</div>
</body>
</html>
</#escape>
<script>
var row = ${accidentNum};
function add(){
	var content = jQuery("#tableContent").html();
	content = content.replace(/@/g, row);
	jQuery("#container").append(content);
	row++;
}

function del(row){
	jQuery("#tab" + row).remove();
}
</script>
