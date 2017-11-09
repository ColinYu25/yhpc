<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<link href="${resourcePath}/css/style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="${resourcePath}/js/extjs3.4/css/ext-all.css"/>
	<style type="text/css">
		.table_4_blue {border: 1px solid #519ccc;}
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
	<script type="text/javascript" src="${resourcePath}/js/extjs3.4/ext-base.js"></script>
	<script type="text/javascript" src="${resourcePath}/js/extjs3.4/ext-all.js"></script>
	<script type="text/javascript" src="${resourcePath}/js/extjs3.4/ui/ext-ui.js"></script>
	<script language="JavaScript" src="${resourcePath}/js/FusionCharts.js"></script>
</head>
<script type="text/javascript">
	<#if anWeiUser?? && anWeiUser>
	Ext.QuickTips.init();
	Ext.onReady(function(){
		Ext.Ajax.request({
			url : '${base}/danger/loadDangersUnGorverCount.xhtml',
			params : {},
			success : function(response) {
				var data = response.responseText;
				if(data != 0) {
					new TipWindow({
						title : '<span class=commoncss>温謦提示</span>',
						width : 250,
						height : 150,
						padding : 8,
						hideTask : false,
						html : '<#if userDetail?? &&userDetail.factName??>${userDetail.factName}<#else>您好</#if>：当前你辖区内尚有[<a href="/nbyhpc/danger/loadDangersUnGorver.xhtml"><font color="red" style="text-decoration:underline;"><b>' + data + '</b></font></a>]项未整改的重大隐患，请及时检查核实，督促指导企业落实整改，并做好验收工作。谢谢!',
						iconCls : 'commentsIcon'
			 		}).show(Ext.getBody());
				}
			},
			failure : function() {}
		});
	});
	</#if>
</script>
<#if !userDetail.userIndustry?exists>
<body style="background-image:url(${resourcePath}/img/no_type_bg.jpg);">
<#else>
<#if userDetail.userIndustry=='qiye' || userDetail.userIndustry=='nongji' || userDetail.userIndustry=='zhijian' || userDetail.userIndustry=='haishi' || userDetail.userIndustry=='jiaojing' || userDetail.userIndustry=='xiaofang' || userDetail.userIndustry=='jianwei' || userDetail.userIndustry=='renfang'|| userDetail.userIndustry=='maoyi'|| userDetail.userIndustry=='lishe' || userDetail.secondArea!=0>
	<#if userDetail.userIndustry=='qiye'>
		<body style="background-image:url(${resourcePath}/img/wel_bg.jpg);">
		<div align="center"><img src="${resourcePath}/img/no_type_bg1.jpg">
		<!--<script>
		
		window.location="../company/loadCompany.xhtml";
		</script>-->
		
	<#else>
		<body style="background-image:url(${resourcePath}/img/wel_bg.jpg);">
		<div align="center"><img src="${resourcePath}/img/no_type_bg.jpg">
	</#if>
</body>
<#else>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td  width="100%" height="31" align="left" valign="bottom" background="${resourcePath}/img/right_111.gif">
    <table height="31"  cellpadding="0" cellspacing="0" width="601" class="zxgg"  background="${resourcePath}/img/right_14.gif" style="border:#519ccc solid 1px; border-bottom:none" >
      <tr >
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',0);" align="center" class="selectli1"><a href="#" class="a_blue_12b">${statistic.year}年宁波市安全生产隐患排查一般隐患统计图</a></td>
        <td width="1" bgcolor="#519ccc"></td>
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',1);" align="center" ><a href="#" class="a_blue_12">${statistic.year}年宁波市安全生产隐患排查重大隐患统计图</a></td>
      </tr>
    </table></td>
  </tr>
  <tbody id='aTabs' name='aTabs'>
    <tr>
      <td height="100%"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue" style="border-top: none">
        <tr>
          <td align="left" valign="top"><div id="chartdiv" align="left"></div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
  <tbody id='aTabs'  name='aTabs' style='display:none'>
    <tr>
      <td height="100%"><table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue" style="border-top:none">
        <tr>
          <td align="left" valign="top"><div id="dchartdiv" align="left"></div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
</table>
</body>
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
	var widthVal = screen.width-220;
	var heightVal = screen.height-286;
	var chart = new FusionCharts("${resourcePath}/flash/FCF_MSColumn3D.swf","ChartId",widthVal,heightVal);
	chart.setDataURL("${resourcePath}/xml/${statistic.year}_nbyhpc_${userDetail.userIndustry}_normal_danger.xml");	 
   	chart.addParam('wmode', 'opaque');//防止挡住控件
   	chart.render("chartdiv");
   	
   	var dchart = new FusionCharts("${resourcePath}/flash/FCF_MSColumn3D.swf","dChartId",widthVal,heightVal);
	dchart.setDataURL("${resourcePath}/xml/${statistic.year}_nbyhpc_${userDetail.userIndustry}_major_danger.xml");		   
   	chart.addParam('wmode', 'opaque');//防止挡住控件
   	dchart.render("dchartdiv");
</script>
</#if>
</#if>
</html>
