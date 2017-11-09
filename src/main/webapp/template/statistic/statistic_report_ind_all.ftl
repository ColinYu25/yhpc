<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<#if statistic.remark=='wenguang'>
	<#assign typeName='文化娱乐场所'>
<#elseif statistic.remark=='maoyi'>
	<#assign typeName='商（市）场'>
<#elseif statistic.remark=='haiyang'>
	<#assign typeName='渔业行业'>
<#elseif statistic.remark=='lvyou'>
	<#assign typeName='旅游行业'>
<#elseif statistic.remark=='jiaoyu'>
	<#assign typeName='教育领域'>	
<#elseif statistic.remark=='weisheng'>
	<#assign typeName='卫生'>
<#elseif statistic.remark=='renfang'>
	<#assign typeName='人防设施'>
<#elseif statistic.remark=='minzong'>
	<#assign typeName='宗教场所'>
<#elseif statistic.remark=='dianli'>
	<#assign typeName='供电设施'>
</#if>

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
  <tr>
    <td  width="100%" height="31" align="left" valign="bottom" background="${resourcePath}/img/right_111.gif">
    <table height="31"  cellpadding="0" cellspacing="0" width="601" class="zxgg"  background="${resourcePath}/img/right_14.gif" style="border:#519ccc solid 1px; border-bottom:none" >
      <tr >
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',0);winLink();" align="center" ><a href="#" class="a_blue_12">季度统计汇总表</a></td>
        <td width="1" bgcolor="#519ccc"></td>
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',1);" align="center" class="selectli1"><a href="#" class="a_blue_12b"><input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" onChange="changeQUrl();" class="Wdate" size="8" maxlength="6">年度统计汇总表</a></td>
      </tr>
    </table></td>
  </tr>
  <tbody id='aTabs' name='aTabs' style='display:none'>
    <tr>
      <td height="100%"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top: none">
        <tr>
          <td align="left" valign="top"><div id="chartdiv" align="left">
          </div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
  <tbody id='aTabs'  name='aTabs'>
    <tr>
      <td height="100%"><table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top:none">
        <tr>
          <td align="left" valign="top"><div id="dchartdiv" align="left">
          <table width="100%" cellspacing="1" cellpadding="0" border="0" align="center" >
		   <tr>
			<td align="center">
			<br>
			<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　
			<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　
			<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/>
			</td></tr>
           </table>
           <div id='page1'>
         <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
		  <tr>
		   <tr><td align="center" colspan="${areas?size+3}"><br><strong style="font-size:23px;line-height:30px;">${typeName}安全生产隐患排查治理情况统计年报表</strong></td></tr>
		  </tr>
		  <tr><td align="center" style="font-size:14px;line-height:25px;">填报单位（章）：　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　年　　月　　日</td></tr>
		</table>
		  <table  cellspacing="1" cellpadding="0" border="0" align="center"  width="90%" class="table_list2">
		  <tr height="25" align="center"><td width="15%" height="28" colspan="2"><strong>区域</strong></td>
		  	<#list areas?if_exists as a>
				<td width="${85/(areas?size+1)}%" <#if area.areaCode==330200000000>nowrap="true"</#if> title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
    		</#list>
    		<td width="${85/(areas?size+1)}%" <#if area.areaCode==330200000000>nowrap="true"</#if>><strong>合计</strong></td></tr>
    		  <#list statistics?if_exists as hy>
			  <#list 0..3 as i>
			  <tr height="25" align="center">
			  	<#if i_index==0>
			  		<td rowspan="4"><strong><#if hy_index==0>合计<#else>${hy.enumName}</#if></strong></td>
				  	<td nowrap="true" width="6%"><strong>应 报 数</strong></td><#assign ptype='p'>
			  	<#elseif i_index==1>
			  		<td nowrap="true" width="6%"><strong>未 报 数</strong></td><#assign ptype='w'>
			  	<#elseif i_index==2>
			  		<td nowrap="true" width="6%"><strong>年 报 率</strong></td>
			  	<#elseif i_index==3>
			  		<td nowrap="true" width="6%"><strong>零隐患率</strong></td>
			  	</#if>
			  	<#assign rowTotal=0>
			  	<#assign number=0>
			  	<#list areas?if_exists as a>
			    <td>
			    <#list statistics?if_exists as s>
			    <#if hy.industryId==s.industryId><#if a.areaCode==s.areaCode>
			    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
			    <#elseif i_index==1>${s.inhere-s.reportedNum}<#assign rowTotal=rowTotal+s.inhere-s.reportedNum>
			    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.reportedNum/s.inhere*100);M1}%<#assign rowTotal=rowTotal+s.number></#if>
			    <#elseif i_index==3><#assign rowTotal=rowTotal+s.zero><#if s.number==0>/<#else>#{(s.zero/s.number*100);M1}%</#if></#if>
			    </#if></#if></#list></td>
			    </#list>
			    <td>
			    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
			    <#elseif i_index==1>${rowTotal}<#assign reportedNum=rowTotal>
			    <#elseif i_index==2><#if inhere==0>/<#else>#{((inhere-reportedNum)/inhere*100);M1}%<#assign number=rowTotal></#if>
			    <#elseif i_index==3><#assign zero=rowTotal><#if (inhere-number)==0>/<#else>#{(zero/(inhere-number)*100);M1}%</#if>
			    </#if></td>
			  </tr>
			 </#list>
			 <#if hy_index==(statistics?size/areas?size-1)>
			 <#break>
			 </#if>
			 </#list>
		  </tr>	  
       </table>
       <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:14px;line-height:25px;">单位负责人（签字）：　　　　　　　　　填表人（签字）：　　　　　　　　　联系电话：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	</table>
          </div></div></td>
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
				window.location = "loadReportByIndAll.xhtml?statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyByIndustryList.xhtml?company.year=${statistic.year}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
		}
		function changeQUrl() {
			window.location = "loadReportByIndAll.xhtml?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4);
		}
		function winLink() {
			window.location = "loadReportByInd.xhtml";
		}
		<#if statistic.remark?if_exists!='jiaotong'&&area.areaCode==330200000000>
		printParam(10,0,0,0,2);
		<#else>
		printParam(0,0,0,0,2);
		</#if>
	</script>
</#escape>
<@fkMacros.pageFooter />