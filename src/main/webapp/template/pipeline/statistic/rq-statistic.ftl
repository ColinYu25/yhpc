<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.table_4_blue11 {border: 0px solid #519ccc;}
.selectli1 {
	background: url(${resourcePath}/img/right_13.gif);
}
a {
	font-size: 12px;
	color: #000;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000;
}
a:hover {
	text-decoration: none;
	color: #000;
}
a:active {
	text-decoration: none;
	color: #000;
}
.a_blue_12b {font-size: 12px;color: #1767A8; font-weight:bold;}
.a_blue_12b:link {text-decoration: none;}
.a_blue_12b:visited {text-decoration: none;	color: #1767A8;}
.a_blue_12b:hover {text-decoration: none;color: #1767A8;}
.a_blue_12b:active {text-decoration: none;color: #1767A8;}

.a_blue_12 {font-size: 12px;color: #1767A8;}
.a_blue_12:link {text-decoration: none;}
.a_blue_12:visited {text-decoration: none;	color: #1767A8;}
.a_blue_12:hover {text-decoration: none;color: #1767A8;}
.a_blue_12:active {text-decoration: none;color: #1767A8;}
body{ margin:0px; padding:0px;}
td, .table_list2 td{
	padding:0;
	text-align:center
}
</style>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">

  <tbody id='aTabs' name='aTabs'>
    <tr>
      <td height="100%"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top: none">
        <tr>
          <td align="left" valign="top"><div id="chartdiv" align="left">

           <div id='page1'>
         <div style=" font-size:24px; font-family:黑体;text-align:center;padding:40px 0 10px 0;">宁波市中低压燃气管道基本情况统计表</div>
		  <table  cellspacing="1" cellpadding="0" border="0" align="center"  width="99%" class="table_list2">
		  <tr height="25" align="center">
		  <td width="15%" height="28" colspan="2" ><strong>区域</strong></td>
		  	<#list areas?if_exists as a>
				<td width="${85/(areas?size+1)}%"   style="cursor:hand;" ><strong>${a.areaName}</strong></td>
    		</#list>
    		<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td>
    		</tr>
  
    		<tr height="25" align="center">
    		  <td colspan="2">使用单位数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.sydw/>		
			    	 <a href="statistic_rqCompanyList.xhtml?areaCode=${item.areaCode}">${item.sydw}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqCompanyList.xhtml">${rowTotal}</a>
			 </td>
		   </tr>
		   
    		<tr height="25" align="center">
    		  <td colspan="2">油气管道数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.yqgds/>		
			    	 <a href="statistic_rqPipelineList.xhtml?areaCode=${item.areaCode}">${item.yqgds}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqPipelineList.xhtml">${rowTotal}</a>
			 </td>
		   </tr>		   

		   
		   <tr >
		   <td rowspan="14">详细情况</td>
    		  <td style="text-align:center">管道长度(Km)</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td style="text-align:center">
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.gdcd/>		
			    	 ${item.gdcd?string("####.##")}
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td style="text-align:center">
			    ${rowTotal?string("####.##")}
			 </td>
		   </tr>
		   	
		   	<tr height="25" align="center">
    		  <td >未办理规划许可</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.blghxk/>		
			    	 <a href="statistic_rqPipelineListByIntProperty.xhtml?areaCode=${item.areaCode}&propertyName=ghxklx&intPropertyValue=0">${item.blghxk}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqPipelineListByIntProperty.xhtml?propertyName=ghxklx&intPropertyValue=0">${rowTotal}</a>
			 </td>
		   </tr>
		   
		   	<tr height="25" align="center">
    		  <td >安全生产许可证</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.aqscxk/>		
			    	 ${item.aqscxk}
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    ${rowTotal}
			 </td>
		   </tr>	
		   
		   	<tr height="25" align="center">
    		  <td >未通过竣工验收</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.jgys/>		
			    	 <a href="statistic_rqPipelineListByIntProperty.xhtml?areaCode=${item.areaCode}&propertyName=jgys&intPropertyValue=0">${item.jgys}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqPipelineListByIntProperty.xhtml?propertyName=jgys&intPropertyValue=0">${rowTotal}</a>
			 </td>
		   </tr>	
		   
		   	<tr height="25" align="center">
    		  <td>未办妥压力管道使用登记</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.sydj/>		
			    	 <a href="statistic_rqPipelineListByIntProperty.xhtml?areaCode=${item.areaCode}&propertyName=ylgdsydj&intPropertyValue=0">${item.sydj}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqPipelineListByIntProperty.xhtml?propertyName=ylgdsydj&intPropertyValue=0">${rowTotal}</a>
			 </td>
		   </tr>	
		   
		   	<tr height="25" align="center">
    		  <td>实际路径与竣工图纸不一致数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.same/>		
			    	 ${item.same}
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    ${rowTotal}
			 </td>
		   </tr>	
		   		
		   	<tr height="25" align="center">
    		  <td>管道穿越人员密集场场所数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.cyrymjcs/>		
			    	 <a href="statistic_rqAttechList.xhtml?areaCode=${item.areaCode}&type=1">${item.cyrymjcs}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqPipelineList.xhtml">${rowTotal}</a>
			 </td>
		   </tr>	
		   
		   	<tr height="25" align="center">
    		  <td>管道违章占压数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.wzzy/>		
			    	 <a href="statistic_rqAttechList.xhtml?areaCode=${item.areaCode}&attechType=4">${item.wzzy}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqAttechList.xhtml?areaCode=&attechType=4">${rowTotal}</a>
			 </td>
		   </tr>
		   	<tr height="25" align="center">
    		  <td>涉及隐患数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.troubles/>		
			    	 <a href="statistic_rqTroubleList.xhtml?areaCode=${item.areaCode}">${item.troubles}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqTroubleList.xhtml?areaCode=">${rowTotal}</a>
			 </td>
		   </tr>
		   	<tr height="25" align="center">
    		  <td>整改需政府协调数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.zfxt/>		
			    	 ${item.zfxt}
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    ${rowTotal}
			 </td>
		   </tr>
		   	<tr height="25" align="center">
    		  <td>安全防护区内建设施工数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.fhqsg/>		
			    	 <a href="statistic_rqAttechList.xhtml?areaCode=${item.areaCode}&attechType=2">${item.fhqsg}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqAttechList.xhtml?areaCode=&attechType=2">${rowTotal}</a>
			 </td>
		   </tr>	
		   	<tr height="25" align="center">
    		  <td>管道曾发生事故数</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.accidents/>		
			    	 <a href="statistic_rqAttechList.xhtml?areaCode=${item.areaCode}&attechType=3">${item.accidents}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    <a href="statistic_rqAttechList.xhtml?areaCode=${item.areaCode}&attechType=3">${rowTotal}</a>
			 </td>
		   </tr>	
		   <!--
		   <tr height="25" align="center">
    		  <td>定期检验检测</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.hasCheckNum/>		
			    	 ${item.hasCheckNum}
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			    
			 </#list>
			 <td>
			    ${rowTotal}
			 </td>
		   </tr>
		   -->
		   <tr height="25" align="center">
    		  <td>未定期检验检测</td>
    		  <#assign rowTotal = 0/>
    		  <#list areas?if_exists as a>
    		  <#assign noneData = "/"/>
    		  	<td>
    		  	<#assign hasData = 'false'/>
    		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.noCheckNum/>		
			    	 <a href="statistic_rqPipelineListByProperty.xhtml?areaCode=${item.areaCode}&propertyName=hasCheck&propertyValue=false">${item.noCheckNum}</a>
			    	 <#assign noneData = ""/>
			    </#if>
			    </#list>
			    ${noneData}
			    </td>
			 </#list>
			 <td>
			    <a href="statistic_rqPipelineListByProperty.xhtml?areaCode=${item.areaCode}&propertyName=hasCheck&propertyValue=false">${rowTotal}</a>
			 </td>
		   </tr>	   			   			   
       </table>
          </div>
          
          </div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
</table>
<br/>
<script type="text/javascript">

	var tID=0; 
	var thisObj;
	function ShowTabss(obj,tabs,ID){ 
		if(thisObj!=obj)tID=0;
		//初始化开始
		for(var i=0;i<document.getElementsByName(obj).length;i++){
			document.getElementsByName(obj)[i].className='';
			document.getElementsByName(obj)[i].getElementsByTagName("a")[0].className='a_blue_12';
			document.getElementsByName(tabs)[i].style.display='none'; 
		}
		document.getElementsByName(tabs)[ID].style.display=''; 
		//初始化结束
		//把TD设为选中状态
		document.getElementsByName(obj)[ID].className='selectli1';
		//把TD设为选中状态后改变其中的a元素的样式
		document.getElementsByName(obj)[ID].getElementsByTagName("a")[0].className='a_blue_12b';
		tID=ID; 
		thisObj=obj;
	}
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "?statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyByIndustryList.xhtml?company.year=${statistic.year}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
		}
		function changeQUrl() {
			window.location = "?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
		
		function winLink() {
			window.location = "loadReportByAjAll.xhtml";
		}
		printParam(0,0,0,0,2);
	</script>
</#escape>
<@fkMacros.pageFooter />