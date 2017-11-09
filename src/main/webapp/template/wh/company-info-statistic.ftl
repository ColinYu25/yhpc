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
</style>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">

  <tbody id='aTabs' name='aTabs'>
    <tr>
      <td height="100%"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top: none">
        <tr>
          <td align="left" valign="top"><div id="chartdiv" align="left">
          	<table width="100%" cellspacing="1" cellpadding="0" border="0" align="center" >
		   <tr>
			<td align="center">
			<br>
			统计年份：<input type="text" name="year" value="${year}" class="wdate" maxlength="4" size="4" onfocus="WdatePicker({dateFmt:'yyyy'});" onchange="window.location.href='company_statistic.xhtml?year='+ this.value"/>
			<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　
			<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　
			<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/>
			</td></tr>
           </table>
           <div id='page1'>
         <div style=" font-size:24px; font-family:黑体;text-align:center;padding:40px 0 10px 0;">宁波市危险化学品企业安全生产工作年度报告表</div>
		  <table  cellspacing="1" cellpadding="0" border="0" align="center"  width="90%" class="table_list2">
		  <tr height="25" align="center"><td width="15%" height="28" ><strong>区域</strong></td>
		  	<#list areas?if_exists as a>
				<td width="${85/(areas?size+1)}%"   style="cursor:hand;" ><strong>${a.areaName}</strong></td>
    		</#list>
    		<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td></tr>
    		  <#list result?if_exists as hy>
			  <#assign inhereTotal=0>
			  <#assign numberTotal=0>
			  <#list 0..3 as i>
			  <tr height="25" align="center">
			  	<#if i_index==0>
			  		<td nowrap="true" width="6%"><strong>企 业 数</strong></td><#assign ptype='w'>
			  	<#elseif i_index==1>
			  		<td nowrap="true" width="6%"><strong>已 报 数</strong></td>
			  	<#elseif i_index==2>
			  		<td nowrap="true" width="6%"><strong>未 报 数</strong></td>
			  	<#elseif i_index==3>
			  		<td nowrap="true" width="6%"><strong>上 报 率</strong></td>
			  	</#if>

			  	<#list areas?if_exists as a>
			    <td>
			    <#list result?if_exists as s>
			    <#if a.areaCode==s.areaCode>
			    <#if i_index==0>${s.inhere}<#assign inhereTotal=inhereTotal+s.inhere>
			    <#elseif i_index==1><#if s.number==0>/<#else><a href="company_reportedCompanyList.xhtml?areaCode=${a.areaCode}&year=${year}">#{s.number}</a><#assign numberTotal=numberTotal+s.number></#if>
			    <#elseif i_index==2><#if (s.inhere - s.number) == 0>/<#else><a href="company_unReportCompanyList.xhtml?areaCode=${a.areaCode}&year=${year}">#{s.inhere - s.number}</a></#if>			    
			    <#elseif i_index==3><#if s.number==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if></#if>
			    </#if>
			    </#list></td>
			    </#list>
			    <td>
			    <#if i_index==0>${inhereTotal}
			    <#elseif i_index==1><#if numberTotal==0>/<#else>${numberTotal}</#if>
			    <#elseif i_index==2><#if (inhereTotal - numberTotal)==0>/<#else>${inhereTotal - numberTotal}</#if>			    
			    <#elseif i_index==3><#if numberTotal==0>/<#else>#{(numberTotal/inhereTotal*100);M1}%</#if>
			    </#if>

			    </td>
			  </tr>
			 </#list>
			 <#if hy_index==(result?size/areas?size-1)>
			 <#break>
			 </#if>
			 </#list>
		  </tr>	  
       </table>
       <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:14px;line-height:25px;">单位负责人（签字）：　　　　　　　填表人（签字）：　　　　　　　联系电话：　　　　　　　填报日期：　　　年　　月　　日</td>
	  </tr>
	</table>
          </div>
          
          </div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
  <tbody id='aTabs'  name='aTabs' style='display:none'>
    <tr>
      <td height="100%"><table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top:none">
        <tr>
          <td align="left" valign="top"><div id="dchartdiv" align="left"></div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
</table>
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