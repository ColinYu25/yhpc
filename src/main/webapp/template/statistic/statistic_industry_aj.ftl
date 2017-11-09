<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script>
	function get(obj) {
		return document.getElementById(obj);
	}
</script>
</head>
<#assign menuName = "季度"/>
<#assign menuName2 = "季度"/>
<#assign topName = "季报"/>
<#if statistic.month?? && statistic.month != 0>
	<#assign menuName = "月"/>
	<#assign menuName2 = "月"/>
	<#assign topName = "月报"/>
<#else>
	<#if statistic.quarter??  && statistic.quarter != 0>
		<#assign menuName = "季度"/>
		<#assign topName = "季报"/>
		<#assign menuName2 = "季度"/>
	<#else>
		<#assign menuName = "年"/>
		<#assign menuName2 = "年"/>
		<#assign topName = "年报"/>
	</#if>
</#if>
<body>&nbsp;
<div class="main">
	<div class="box">
			<div class="box-top">
				<div class="box-nav">
					<ul class="fnlist4">
						<li><a <#if statistic.remark?exists && statistic.remark=='anjian_whp'>class="hover"</#if> href="javascript:loactionUrl('anjian_whp');">危险化学品</a></li>
						<li><a <#if statistic.remark?exists && statistic.remark=='anjian_fire'>class="hover"</#if> href="javascript:loactionUrl('anjian_fire');">烟花爆竹</a></li>
						<li><a <#if statistic.remark?exists && statistic.remark=='anjian_mine'>class="hover"</#if> href="javascript:loactionUrl('anjian_mine');">非煤矿山</a></li>
						<li><a <#if statistic.remark?exists && statistic.remark=='anjian_machine'>class="hover"</#if> href="javascript:loactionUrl('anjian_machine');">机械制造</a></li>
						<li><a <#if statistic.remark?exists && statistic.remark=='anjian_other'>class="hover"</#if> href="javascript:loactionUrl('anjian_other');">其他行业</a></li>
					</ul>
				</div>
			</div>
			<div class="box-con">
		<div class="box-title2">
			<div class="left"></div>
			<div class="center">
			<div class="fn2">
					 <#list statistics?if_exists as s>
	  					<#assign allParam=s.sonNumber1+s.sonNumber2+3>
					<div class="left">
						<input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
						<b><#assign title=""/>
			             <#if statistic.month gt 0>
			             <#assign title=statistic.month +"月份"/>
			             <#else>
				             <#if statistic.quarter==1>
					             <#assign title="第一季度"/>	             
				             <#elseif statistic.quarter==2>
				             	<#assign title="第二季度"/>	 
				             <#elseif statistic.quarter==3>
					             <#assign title="第三季度"/>
				             <#elseif statistic.quarter==4>
					             <#assign title="第四季度"/>             
				             <#else>
				                 <#assign title="全年"/>             
				             </#if>
			             </#if>
			             
			           ${title}<#if statistic.remark=='anjian_other'>其他行业<#else>${s.enumName}月报情况</#if></b>
					</div>
					<div class="right">
						<ul class="fnlist">
							<li><a id="all"<#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeQUrl(0);">全 年</a></li>
							<li><a id="one"<#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeQUrl(1);">第一季度</a></li>
							<li><a id="two"<#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeQUrl(2);">第二季度</a></li>
							<li><a id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeQUrl(3);">第三季度</a></li>
							<li><a id="four"<#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeQUrl(4);">第四季度</a></li>
						</ul>
					</div>
				</div>
				<div class="yf">
					<table  border="0" cellspacing="0" cellpadding="0" >
				  		<tr>
						 <#assign beginMonth = "1"/>
			              <#assign endMonth = "12"/> 
			              <#if statistic.quarter == 0>
				              <#assign beginMonth = 1/>
			    	          <#assign endMonth = 12/>               
			              <#else >
				              <#assign beginMonth = (statistic.quarter * 3) -2/>
			    	          <#assign endMonth = (statistic.quarter * 3)/>                             
			              </#if>
			              <#list beginMonth..endMonth as item>
			             	<td style="text-align:right;" class="shun"><a <#if statistic.month == item>class="hover"</#if> href="#" name="month" onclick="showMonthData(${item});">${item}月份</a></td><!--<#if item ==statistic.month>disabled</#if>-->
			              </#list>
					  	</tr>	
					 </table>
				</div>			
			</div>
			<div class="right"></div>
		</div>
		<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
		  <tr>
			<td class="tbbg"><b>${area.areaName}各地</b>（<#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>）
				<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
							&nbsp;&nbsp;
				<#if refresh==1 >
					<#if statistic.remark?exists && statistic.remark=='anjian_other'>
							<a href="loadCompanyByIndustryOfAjOther.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
					<#else>
							<a href="loadCompanyByIndustryOfAj.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
					</#if>
				</#if>							
			</td>
		  </tr>
		  <tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr class="table02">
					<td width="15%" height="28" colspan="${s.sonNumber1+s.sonNumber2+2}"><#if s_index==0><#break></#if></#list>区域</td>
				  	<#list areas?if_exists as a>
						<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
		    		</#list>
		    		<td width="${85/(areas?size+1)}%"><strong>合计</strong></td></tr>
				   <#list statistics?if_exists as hy>
					  <#list 0..2 as i>
					  <tr class="table03" <#if hy_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if> align="center">
					  	<#if i_index==0>
					  		<#if ((hy.sonNumber1+hy.sonNumber2)>0)>
					  		<#assign colParam=hy.sonNumber1+hy.sonNumber2>
					  		<td rowspan="${hy.sonCount*3}" width="3%"><strong>
					  			<#list 0..hy.enumName?length-1 as hl>${hy.enumName?substring(hl_index,hl_index+1)}<br></#list>
					  		</strong></td>
					  		<td rowspan="3" colspan="${colParam}"><strong>合计</strong></td>
					  		<#else><td rowspan="3" <#if colParam?exists>colspan="${colParam}"<#else>colspan="1"</#if>><strong>${hy.enumName}</strong></td>
					  		</#if>
						  	<td nowrap="true" width="6%"><strong>应录入数</strong></td><#assign ptype='p'>
					  	<#elseif i_index==1>
					  		<td nowrap="true" width="6%"><strong>${menuName}未录入数</strong></td><#assign ptype='w'>
					  	<#elseif i_index==2>
					  		<td nowrap="true" width="6%"><strong>${menuName}录入率</strong></td>
					  	</#if>
					  	<#assign rowTotal=0>
					  	<#list areas?if_exists as a>
					    <td <#if i_index!=2>onClick="showDetail(${a.areaCode},'${hy.industryId}','${ptype}');"  style="cursor:pointer;"</#if>>
					    <#list statistics?if_exists as s>
					    <#if hy.industryId==s.industryId><#if a.areaCode==s.areaCode>
					    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
					    <#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
					    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if></#if></#if></#if></#list></td>
					    </#list>
					    <td <#if i_index!=2>onClick="showDetail(${area.areaCode},'${hy.industryId}','${ptype}');"  style="cursor:pointer;"</#if>>
					    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
					    <#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
					    <#elseif i_index==2><#if inhere==0>/<#else>#{((inhere-number)/inhere*100);M1}%</#if></#if></td>
					  </tr>
					 </#list>
					 <#if hy_index==(statistics?size/areas?size-1)>
					 <#break>
					 </#if>
					 </#list>
				</table>
			</td>
		  </tr>
		</table>
		</div>
	</div>
	<script type="text/javascript">
	
	    var date = new Date();
	    var nowYear=${statistic.year};
	    var month=date.getMonth()+1;
	    var year=date.getYear();
	    
	    if (year==nowYear){
		    if(month<=3){
				get("two").disabled=true;
				get("three").disabled=true;
				get("four").disabled=true;
			}else if(month<=6){
			    get("three").disabled=true;
				get("four").disabled=true;
			}else if(month<=9){
			    get("four").disabled=true;
			}	    
		}
	
		
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
			<#if statistic.remark=='anjian_other'>
				window.location = "loadCompanyByIndustryOfAjOther.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			<#else>
				window.location = "loadCompanyByIndustryOfAj.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			</#if>
			}
		}
		function loactionUrl(remark) {
			if (remark !=null && remark == 'anjian_other'){
				window.location = "loadCompanyByIndustryOfAjOther.xhtml?statistic.remark="+remark;
			}else{
				window.location = "loadCompanyByIndustryOfAj.xhtml?statistic.isSonType=1&statistic.remark="+remark;
			}
		}
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyByIndustryList.xhtml?company.year=${statistic.year}&company.month=${statistic.month}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
		}
		function changeQUrl() {
			<#if statistic.remark=='anjian_other'>
				window.location = "loadCompanyByIndustryOfAjOther.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
			<#else>
				window.location = "loadCompanyByIndustryOfAj.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
			</#if>
		}
		
		function showMonthData(month) {
			<#if statistic.remark=='anjian_other'>
				window.location = "loadCompanyByIndustryOfAjOther.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
			<#else>
				window.location = "loadCompanyByIndustryOfAj.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
			</#if>
		}
	</script>
<#include "statistic_foot.ftl">