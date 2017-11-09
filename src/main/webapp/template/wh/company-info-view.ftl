<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
table{ font-size:12px; color:#000000;}
.line { border:solid 1px #000000;border-right:none;border-bottom:none;}
.line td { border-right:solid 1px #000000; border-bottom:solid 1px #000000;word-break:break-all;}
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
<script language="javascript" type="text/javascript" src="${resourcePath}/datePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>

<script language="javascript" src="${resourcePath}/js/print.js"></script>
<script type="text/javascript">
function get(id){return document.getElementById(id)};
var areaObj=new Area("${areaXmlUrl}");
</script>
</head>

<body>

<div id="page1">
<table width="90%" border="0" cellspacing="0" cellpadding="10" align="center">
  <tr>
    <td colspan="2" align="center" style=" font-size:24px; font-family:黑体">危险化学品企业安全生产工作年度情况报告表</td>
  </tr>
  <tr>
    <td width="60%">单位名称(盖章)：</td>
    <td width="40%" align="right">报告时间：${entity.reportdate?string('yyyy 年 MM 月 dd 日')} </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="2" align="center" style=" font-size:16px"><strong>一、企业基本概况</strong></td>
  </tr>
  <tr>
    <td width="20%">企业名称</td>
    <td width="80%">${entity.company.companyName}&nbsp;</td>
  </tr>
  <tr>
    <td>企业地址</td>
    <td >
    <div style="float:left">
    ${entity.company.regAddress}
    </div>
    <div style="float:right">    
     所在区域：　　　 
     ${secondArea}
       县(市)区　
     ${thirdArea}      
       乡镇(街道)
     </div>
     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line" style=" border-top:none">
  <tr>
    <td colspan="3" align="center">法人代表(或主要负责人)</td>
    <td colspan="3" align="center">分管安全负责人</td>
  </tr>
  <tr>
    <td width="16%" align="center">姓名</td>
    <td width="18%" align="center">年内有无变化</td>
    <td width="16%" align="center">手机号码</td>
    <td width="16%" align="center">姓名</td>
    <td width="18%" align="center">年内有无变化</td>
    <td width="16%" align="center">手机号码</td>
  </tr>
  <tr>
    <td align="center">${entity.frdb}&nbsp;</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.haschangeFrdb?? && entity.haschangeFrdb == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>
        <input type="checkbox" name="entity.haschangeFrdb" value="true" ${isTrue}/> 有　
        <input type="checkbox" name="entity.haschangeFrdb" value="false" ${isFalse}/> 无
    </td>
    <td align="center">${entity.frdbPhone}&nbsp;</td>
    <td align="center">${entity.fgfzr}&nbsp;</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.haschangeFgfzr?? && entity.haschangeFgfzr == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>
	<input type="checkbox" name="entity.haschangeFgfzr" value="true" ${isTrue}/> 有　
	<input type="checkbox" name="entity.haschangeFgfzr" value="false" ${isFalse}/> 无</td>
    <td align="center">${entity.fgfzrPhone}&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="center">安全部门：
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasAqbm?? && entity.hasAqbm == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>    
	    <input type="checkbox" name="entity.hasAqbm" value="true" ${isTrue}/> 有　　
		<input type="checkbox" name="entity.hasAqbm" value="false" ${checked}/> 无</td>
    <td colspan="3" align="center">安全部门负责人</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="5" class="line" style=" border-top:none">
<tr>
    <td width="25%" align="center">部门名称</td>
    <td width="25%" align="center">专职安管员数</td>
    <td width="16%" align="center">姓名</td>
    <td width="18%" align="center">年内有无变化</td>
    <td width="16%" align="center">手机号码</td>
  </tr>
  <tr>
    <td align="center">${entity.aqbm}&nbsp;</td>
    <td align="center">${entity.zzagy}&nbsp;</td>
    <td align="center">${entity.aqbmFzr}&nbsp;</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.haschangeAqbm?? && entity.haschangeAqbm == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
	<input type="checkbox" name="entity.haschangeAqbm" value="true" ${isTrue}/> 有　
	<input type="checkbox" name="entity.haschangeAqbm" value="false" ${isFalse}/> 无</td>
    <td>${entity.aqbmFzrPhone}&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="5" class="line" style=" border-top:none">
  <tr>
    <td width="25%" rowspan="2">职工总数</td>
    <td width="25%" rowspan="2" align="center">${entity.works} <span style="margin-right:40px">名</span></td>
    <td width="25%" align="center">年内职工流出数</td>
    <td width="25%" align="center">年内新招职工数</td>
  </tr>
  <tr>
    <td align="center">
	<#if entity?? && entity.lostWorks?? && entity.lostWorks gt 0 >
	${entity.lostWorks} <span style="margin-right:40px">名</span>
	<#else>
	/
	</#if>	    
    </td>
    <td align="center">
	<#if entity?? && entity.newWorks?? && entity.newWorks gt 0 >
	${entity.newWorks} <span style="margin-right:40px">名</span>
	<#else>
	/
	</#if>	     
    </td>
  </tr>
   <tr>
    <td width="25%">年度工业产值</td>
    <td width="25%" align="center">${entity.annualProduction} <span style="margin-right:40px">万元</span></td>
    <td width="25%" align="center">年内安全生产投入</td>
    <td width="25%" align="center">
	<#if entity?? && entity.annualInput?? && entity.annualInput gt 0 >
	${entity.annualInput} <span style="margin-right:40px">万元</span>
	<#else>
	/
	</#if>    
    </td>
  </tr>
</table>
</div>

<br>
<div id="page2">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>二、安全生产许可情况</strong></td>
  </tr>
  <tr>
    <td width="25%">安全生产(经营)许可证编号</td>
	<td width="25%" align="center">${entity.licence}&nbsp;</td>
	<td width="25%">有效期</td>
	<td width="25%" align="center">
	${entity.enddate?string('yyyy 年 MM 月 dd 日')}&nbsp;
	</td>
  </tr>
  <tr>
    <td>主要许可范围</td>
    <td colspan="3">
    ${entity.permitScope}
    &nbsp;</td>
  </tr>
  <tr>
    <td>有无新、改、扩建项目</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasNewproject?? && entity.hasNewproject == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="checkbox" name="entity.hasNewproject" value="true" ${isTrue}/> 有　
		<input type="checkbox" name="entity.hasNewproject" value="false" ${isFalse}/> 无    </td>
    <td>有无试生产项目</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasTestproject?? && entity.hasTestproject == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
	<input type="checkbox" name="entity.hasTestproject" value="true" ${isTrue}/> 有　
	<input type="checkbox" name="entity.hasTestproject" value="false" ${isFalse}/> 无</td>
  </tr>
  <tr>
    <td>试生产有无超期</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasExceedTestproject?? && entity.hasExceedTestproject == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>      
        <input type="checkbox" name="entity.hasExceedTestproject" value="true" ${isTrue}/> 有　
		<input type="checkbox" name="entity.hasExceedTestproject" value="false" ${isFalse}/> 无    </td>
    <td>&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>三、安全生产许可情况</strong></td>
  </tr>
  <tr>
    <td>安评报告是否在有效期内</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasVoidApgb?? && entity.hasVoidApgb == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>  	
        <input type="checkbox" name="entity.hasVoidApgb" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.hasVoidApgb" value="false" ${isFalse}/> 否</td>
	<td>安评报告对策措施是否落实</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasVoidApgb?? && entity.hasVoidApgb == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>  	
        <input type="checkbox" name="entity.hasApbgls" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.hasApbgls" value="false" ${isFalse}/> 否</td>		
  </tr>
  <tr>
    <td>安全评价机构名称</td>
    <td colspan="3">${entity.aqpjjg}&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>四、安全生产标准化开展情况</strong></td>
  </tr>
  <tr>
    <td>达标级别</td>
	<td>
	<#if entity?? && entity.grade??>
		<#if entity.grade == '1'>
			国家一级
		<#elseif entity.grade == '2'>
			国家二级
		<#elseif entity.grade == '3'>
			省级
		<#elseif entity.grade == '4'>
			市级
		</#if>
	</#if>
	&nbsp;</td>
	<td>达标有效期</td>
	<td width="25%" align="center">
	${entity.levelEnddate?string('yyyy 年 MM 月 dd 日')}&nbsp;
	</td>		
  </tr>
  <tr>
    <td>安全生产标准化体系是否有效运行</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.isAqbzhRunok?? && entity.isAqbzhRunok == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>      
        <input type="checkbox" name="entity.isAqbzhRunok" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.isAqbzhRunok" value="false" ${isFalse}/> 否</td>
	<td>年度安全生产标准化自评得分</td>
    <td align="center">
    <#if entity?? && entity.standardScore?? && entity.standardScore gt 0 >
	${entity.standardScore} <span style="margin-right:40px">分</span>
	<#else>
	/
	</#if>
    </td>	
  </tr>
</table>
</div>

<br>
<div id="page3">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>五、隐患排查治理情况</strong></td>
  </tr>
  <tr>
    <td width="25%">是否建立隐患排查治理规章制度</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasTroubleRule?? && entity.hasTroubleRule == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	
        <input type="checkbox" name="entity.hasTroubleRule" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.hasTroubleRule" value="false" ${isFalse}/> 否</td>
	<td width="25%">是否经常开展隐患排查治理</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasTroubleWork?? && entity.hasTroubleWork == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="checkbox" name="entity.hasTroubleWork" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.hasTroubleWork" value="false" ${isFalse}/> 否</td>
  </tr>
  <tr>
    <td>有无建立隐患排查治理台帐</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYhpctz?? && entity.hasYhpctz == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="checkbox" name="entity.hasYhpctz" value="true" ${isTrue}/> 有　
        <input type="checkbox" name="entity.hasYhpctz" value="false" ${isFalse}/> 无</td>
	<td>是否依法报送隐患排查治理信息</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYfbsyh?? && entity.hasYfbsyh == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="checkbox" name="entity.hasYfbsyh" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.hasYfbsyh" value="false" ${isFalse}/> 否</td>
  </tr>
  <tr>
    <td>重大事故隐患整改“五落实”</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasZdyhzg?? && entity.hasZdyhzg == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>      
        <input type="checkbox" name="entity.hasZdyhzg" value="true" ${isTrue}/> 有　
		<input type="checkbox" name="entity.hasZdyhzg" value="false" ${isFalse}/> 无    </td>
    <td>是否落实执法部门提出的安全生产整改意见</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasLszgyj?? && entity.hasLszgyj == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>       
	<input type="checkbox" name="entity.hasLszgyj" value="true" ${isTrue}/> 有　
	<input type="checkbox" name="entity.hasLszgyj" value="false" ${isFalse}/> 无</td>
  </tr>
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>六、应急管理工作情况</strong></td>
  </tr>
  <tr>
    <td>应急救援预案是否评审</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYjjyyaPs?? && entity.hasYjjyyaPs == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>   	
        <input type="checkbox" name="entity.hasYjjyyaPs" value="true" ${isTrue}/> 是　
        <input type="checkbox" name="entity.hasYjjyyaPs" value="false" ${isFalse}/> 否</td>
	<td>
        应急救援预案是否备案</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.isYjjyyaBa?? && entity.isYjjyyaBa == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	
        <input type="checkbox" name="entity.isYjjyyaBa" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.isYjjyyaBa" value="false" ${isFalse}/> 否</td>		
  </tr>
  <tr>
    <td>应急救援预案备案时间</td>
	<td align="center">
	${entity.yjjyyaBaDate?string('yyyy 年 MM 月 dd 日')}
	</td>
	<td>
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
        <input type="checkbox" name="entity.yjjyyaBaDept" value="3" ${shen}/> 省
        <input type="checkbox" name="entity.yjjyyaBaDept" value="2" ${shi}/> 市
		<input type="checkbox" name="entity.yjjyyaBaDept" value="1" ${xian}/> 县</td>		
  </tr>
  <tr>
    <td>年内综合应急演练</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasYjyl?? && entity.hasYjyl == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="checkbox" name="entity.hasYjyl" value="true" ${isTrue}/> 有　
		<input type="checkbox" name="entity.hasYjyl" value="false" ${isFalse}/> 无</td>
	<td>
        参加综合应急演练人数</td>
	<td align="center">
	<#if entity?? && entity.ylrs?? && entity.ylrs gt 0 >
	${entity.ylrs} 人
	<#else>
	/
	</#if>
	</td>		
  </tr>
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>七、重大危险源管理情况</strong></td>
  </tr>
  <tr>
    <td>是否构成重大危险源单位</td>
	<td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.isZdwxy?? && entity.isZdwxy == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="checkbox" name="entity.isZdwxy" value="true" ${isTrue}/> 是　
	    <input type="checkbox" name="entity.isZdwxy" value="false" ${isFalse}/> 否</td>
	<td>重大危险源数量</td>
	<td width="25%" align="center">
	<#if entity?? && entity.zdwxyNum?? && entity.zdwxyNum gt 0 >
	${entity.zdwxyNum} 个
	<#else>
	/
	</#if>	
	</td>		
  </tr>
  <tr>
    <td>年内有无新增重大危险源</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasNewzdwxy?? && entity.hasNewzdwxy == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	    
        <input type="checkbox" name="entity.hasNewzdwxy" value="true" ${isTrue}/> 有　
		<input type="checkbox" name="entity.hasNewzdwxy" value="false" ${isFalse}/> 无 </td>
	<td>是否每半年报告一次重大危险源安全管理自查情况</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.hasReportZdwxy?? && entity.hasReportZdwxy == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 	     
        <input type="checkbox" name="entity.hasReportZdwxy" value="true" ${isTrue}/> 是　
	    <input type="checkbox" name="entity.hasReportZdwxy" value="false" ${isFalse}/> 否</td>
  </tr>
</table>
</div>

<br>
<div id="page4">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>八、安全生产持证上岗情况</strong></td>
  </tr>
  <tr>
    <td width="25%">法人代表(主要负责人)是否取证</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.frdbHasCert?? && entity.frdbHasCert == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="checkbox" name="entity.frdbHasCert" value="true" ${isTrue}/> 是　
        <input type="checkbox" name="entity.frdbHasCert" value="false" ${isFalse}/> 否</td>
	<td width="25%">分管安全负责人是否取证</td>
	<td width="25%" align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.fgfzrHasCert?? && entity.fgfzrHasCert == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if> 		
        <input type="checkbox" name="entity.fgfzrHasCert" value="true" ${isTrue}/> 是　
		<input type="checkbox" name="entity.fgfzrHasCert" value="false" ${isFalse}/> 否</td>
  </tr>
  <tr>
    <td>安全部门负责人是否取证</td>
    <td align="center">
    	<#assign isTrue=""/>
    	<#assign isFalse="checked"/>
        <#if entity?? && entity.aqbmfzrHasCert?? && entity.aqbmfzrHasCert == true >
	    	<#assign isTrue ="checked"/>
	    	<#assign isFalse =""/>        
        </#if>     
        <input type="checkbox" name="entity.aqbmfzrHasCert" value="true" ${isTrue}/> 有　
		<input type="checkbox" name="entity.aqbmfzrHasCert" value="false" ${isFalse}/> 无</td>
	<td>特种作业人员持证率</td>
    <td align="center"> 
    <#if entity?? && entity.tzyzryPercent?? && entity.tzyzryPercent gt 0>
    ${entity.tzyzryPercent}　%
    <#else>
    /
    </#if>
    </td>
  </tr>
</table>  
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line" style=" border-top:none">  
  <tr>
    <td colspan="4" align="center" style=" font-size:16px"><strong>九、生产安全事故情况(含未造成人员伤亡的生产安全事故)</strong></td>
  </tr>
  <tr>
	<#assign accidentTotal=0/>
	<#assign deadTotal=0/>
	<#assign hurtTotal=0/>
	<#assign lostTotal=0/>			
	<#if entity?? && entity.accidents?? && entity.accidents?size gt 0 >
	<#list entity.accidents as item>  
		<#assign accidentTotal=accidentTotal + 1/>
		<#if item.dead?? && item.dead gt 0>
			<#assign deadTotal=deadTotal + item.dead/>
		</#if>
		<#if item.hurt?? && item.hurt gt 0>		
			<#assign hurtTotal=hurtTotal + item.hurt/>
		</#if>		
		<#if item.lost?? && item.lost gt 0>		
			<#assign lostTotal=lostTotal + item.lost/>	
		</#if>		
	</#list>
	<#else>
	<#assign accidentTotal="/"/>
	<#assign deadTotal="/"/>
	<#assign hurtTotal="/"/>
	<#assign lostTotal="/"/>	
	</#if>  
    <td width="20%" align="center">发生事故数</td>
	<td width="15%" align="center">　　${accidentTotal}　　起</td>
	<td width="15%" align="center">事故损失</td>
	<td width="50%">死亡：　${deadTotal}　人； 受伤：　${hurtTotal}　人； 直接经济损失：　${lostTotal}　万元</td>		
  </tr>
	<#if entity?? && entity.accidents?? && entity.accidents?size gt 0 >
	<#list entity.accidents as item>  
  <tr>
    <td align="center">事故类型</td>
	<td align="center">
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.type == 1>checked</#if>/> 火灾<br>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.type == 2>checked</#if>/> 爆炸<br>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.type == 3>checked</#if>/> 泄漏<br>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.type == 4>checked</#if>/> 其它
	</td>
	<td align="center">人员伤亡原因</td>
	<td>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 1>checked</#if>/> 物体打击　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 2>checked</#if>/> 车辆伤害　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 3>checked</#if>/> 机械伤害
	<br>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 4>checked</#if>/> 起重伤害　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 5>checked</#if>/> 触电　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 6>checked</#if>/> 
	灼伤　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 7>checked</#if>/> 火灾
	<br>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 8>checked</#if>/> 高处坠落　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 9>checked</#if>/> 电容爆炸　　
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 10>checked</#if>/> 中毒和窒息
	<br>
	<input type="checkbox" name="checkbox" value="checkbox" <#if item.hurtType == 11>selected</#if>/> 其他伤害</td>		
  </tr>
  <tr>
    <td align="center" colspan="3">事故主要原因(简述)</td>
	<td align="center">事故责任人处理情况</td>		
  </tr>
  <tr>
    <td colspan="3" align="center" height="70">
    ${item.mainCause}
    </td>
	<td align="center">
     ${item.processResult}
	</td>		
  </tr>
</#list>
</#if>  
</table>
</div>

<br>
<div id="page5">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="line">
  <tr>
    <td colspan="2" align="center" style=" font-size:16px"><strong>十、其他</strong></td>
  </tr>
  <tr>
    <td align="center" width="50%">年度安全生产工作创新及亮点</td>
	<td align="center" width="50%">年度安全生产工作存在的突出问题</td>
  </tr>
  <tr>
    <td align="center" height="100">
     ${entity.workinfo}&nbsp;
    </td>
    <td align="center">
     ${entity.workinfoProblem}&nbsp;
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center" style=" font-size:16px"><strong>十一、下一年度安全生产工作要点及建议</strong></td>
  </tr>
  <tr>
    <td colspan="2" align="center" height="100">
	  ${entity.workAdvise}&nbsp;
	</td>
  </tr>
</table>
</div>
<div style="text-align:center;padding-top:5px;">

<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/> 
<input name="print" type="button"  value=" 打  印 "  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>
<input type="button" value=" 取 消 " onclick="window.location.href ='company_list.xhtml'"/> 
</div>

</body>
</html>
</#escape>
<script>
printParam(20,10,0,10,2);
jQuery(function($) {
	$("input[type='checkbox']").click(function(){
		return false;
	})
});
</script>
