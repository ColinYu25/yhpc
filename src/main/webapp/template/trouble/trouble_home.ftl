<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script language="JavaScript" src="${resourcePath}/js/FusionCharts.js"></script>
<script language="JavaScript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<link href="${resourcePath}/css/css_home.css" rel="stylesheet" type="text/css">
<link href="${resourcePath}/css/index.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="neirong">
<table width="100%"  border="0" align="right" cellpadding="0" cellspacing="0" class="whole">
  <tr>
    <td width="70%">
		<table width="99%"  border="0" cellpadding="0" cellspacing="0" class="margin3">
	  <tr>
		<td class="topleft"><img src="${resourcePath}/images/topleft.gif"></td>
		<td class="topcenter"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="30"><img src="${resourcePath}/images/biao.gif" width="21" height="16"></td>
			<td class="topzi">已接收隐患统计图</td>
		  </tr>
		</table>
	</td>
    <td class="topright"><img src="${resourcePath}/images/topright.gif"></td>
  </tr>
  <tr>
    <td class="centerlefta1a"><img src="${resourcePath}/images/centerlefta.jpg"></td>
	<!--放统计表开始-->
    <td>
	  <div style="text-align:center" id="chart1"></div>
	
	</td>
	<!--放统计表结束-->
    <td class="centerrighta1a"></td>
  </tr>
  <tr>
    <td class="centerleft"><img src="${resourcePath}/images/centerleft.jpg"></td>
    <td class="center"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30"><img src="${resourcePath}/images/biao.gif" width="21" height="16"></td>
        <td class="topzi">已抄告隐患统计图</td>
      </tr>
    </table></td>
    <td class="centerright"><img src="${resourcePath}/images/centerright.jpg"></td>
  </tr>
  <tr>
    <td class="centerlefta1a"><img src="${resourcePath}/images/centerlefta.jpg"></td>
    <!--放统计表开始-->
    <td>
	<div style="text-align:center" id="chartdiv"></div>
	</td>
	<!--放统计表结束-->
    <td class="centerrighta1a"></td>
  </tr>
  <tr>
    <td class="bleft"><img src="${resourcePath}/images/bleft.jpg"></td>
    <td class="bcenter"><img src="${resourcePath}/images/bcenter.jpg"></td>
    <td class="bright"><img src="${resourcePath}/images/bright.jpg"></td>
  </tr>
  
</table>

	</td>
    <td>
	<table width="99%"  border="0" cellspacing="0" cellpadding="0">
 <tr>
    <td class="topleft"><img src="${resourcePath}/images/topleft.gif"></td>
    <td class="topcenter"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30"><img src="${resourcePath}/images/biao.gif" width="21" height="16"></td>
        <td class="topzi">工作提醒</td>
      </tr>
    </table></td>
    <td class="topright"><img src="${resourcePath}/images/topright.gif"></td>
  </tr>
  <tr>
    <td class="centerlefta2ax"><img src="${resourcePath}/images/centerlefta.jpg"></td>
    <td valign="top">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="p">
  <tr>
    <td>
	<div style="height:80px; padding:5px">
	您当前共已接收 <span style="color:Red"><b>${stUp.total}</b></span> 个隐患，其中：<br/>
	已接收未回复隐患：<span style="color:Red"><b>${stUp.total-stUp.inhere}</b></span> 个&nbsp;[<a href="loadTroubleBack.xhtml">查看</a>]<br/>
	已接收未反馈隐患：<span style="color:Red"><b>${stUp.total-stUp.number}</b></span> 个&nbsp;[<a href="loadTroubleResult.xhtml">查看</a>]<br/>
	您当前共已抄告 <span style="color:Red"><b>${stDown.total}</b></span> 个隐患，其中：<br/>
	已抄告未回复隐患：<span style="color:Red"><b>${stDown.total-stDown.inhere}</b></span> 个&nbsp;[<a href="loadTroubleDownNotBack.xhtml">查看</a>]<br/>
	已抄告未反馈隐患：<span style="color:Red"><b>${stDown.total-stDown.number}</b></span> 个&nbsp;[<a href="loadTroubleDownNotResult.xhtml">查看</a>]</div>
	</td>
  </tr>
</table>
	</td>
    <td class="centerrighta2ax"></td>
  </tr>
  <tr>
    <td class="centerleft"><img src="${resourcePath}/images/centerleft.jpg"></td>
    <td class="center"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30"><img src="${resourcePath}/images/biao.gif" width="21" height="16"></td>
        <td class="topzi">已接收未回复隐患</td>
       <td><div align="right" class="gengduo"><a href="loadTroubleBack.xhtml"><img src="${resourcePath}/images/gengduo.gif" border="0"></a></div></td>
      </tr>
    </table></td>
    <td class="centerright"><img src="${resourcePath}/images/centerright.jpg"></td>
  </tr>
  <tr>
    <td class="centerlefta2a"><img src="${resourcePath}/images/centerlefta.jpg"></td>
    <td valign="top">
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="table_list">
	<#if notBackDeptes?exists>
	  	<#list notBackDeptes as d>
	      <tr>
		    <td width="11"><img src="${resourcePath}/images/xiao.gif" width="11" height="12"></td>
	        <td ><div align="left"><a href="loadTroubleByDeptPrint.xhtml?dept.id=${d.id}">${d.daTrouble.troubleCompanyName}</a></div></td>
	        <td width="11" nowrap><a href="createTroubleInitByBack.xhtml?dept.id=${d.id}">回复</a></td>
	      </tr>
	      <#if d_index==7>
			 <#break>
		 </#if>
	    </#list>
  	</#if>
    </table></td>
    <td class="centerrighta2a"></td>
  </tr>
   <tr>
    <td class="centerleft"><img src="${resourcePath}/images/centerleft.jpg"></td>
    <td class="center"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30"><img src="${resourcePath}/images/biao.gif" width="21" height="16"></td>
        <td class="topzi">已接收未反馈隐患</td>
        <td><div align="right" class="gengduo"><a href="loadTroubleResult.xhtml"><img src="${resourcePath}/images/gengduo.gif" border="0"></a></div></td>
      </tr>
    </table></td>
    <td class="centerright"><img src="${resourcePath}/images/centerright.jpg"></td>
  </tr>
  <tr>
    <td class="centerlefta2a"><img src="${resourcePath}/images/centerlefta.jpg"></td>
    <td valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="0" class="table_list">
    <#if notResultDeptes?exists>
	  	<#list notResultDeptes as d>
      <tr>
        <td width="11"><img src="${resourcePath}/images/xiao.gif" width="11" height="12" hspace="0" vspace="0"></td>
        <td><div align="left"><a href="loadTroubleByDeptPrint.xhtml?dept.id=${d.id}">${d.daTrouble.troubleCompanyName}</a></div></td>
        <td width="11" nowrap><a href="createTroubleInitByResult.xhtml?dept.id=${d.id}">反馈</a></td>
	</tr>
	<#if d_index==7>
			 <#break>
		 </#if>
	    </#list>
  	</#if>
    </table></td>
    <td class="centerrighta2a"></td>
  </tr>
  <tr>
    <td class="bleft"><img src="${resourcePath}/images/bleft.jpg"></td>
    <td class="bcenter"><img src="${resourcePath}/images/bcenter.jpg"></td>
    <td class="bright"><img src="${resourcePath}/images/bright.jpg"></td>
  </tr>
  
</table>
	</td>
  </tr>
</table>
</div>
<script type="text/javascript">
	var j = jQuery.noConflict();
	j.ajax({
   		type: "POST",
   		url: "loadStatisticOfUpXmlByUser.xhtml",
   		dataType: "html",
   		success: function(msg){
     			var chart = new FusionCharts("${resourcePath}/flash/Column2D.swf", "ChartId", "520", "290");
  				chart.setDataXML(msg);		   
    			chart.render("chart1");
   			}
		});
	j.ajax({
   		type: "POST",
   		url: "loadStatisticOfDownXmlByUser.xhtml",
   		dataType: "html",
   		success: function(msg){
     			var chart = new FusionCharts("${resourcePath}/flash/Column2D.swf", "ChartId", "520", "290");
  				chart.setDataXML(msg);		   
    			chart.render("chartdiv");
   			}
		});
	
</script>
</body>
</html>
