<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
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
	<script language="JavaScript" src="${resourcePath}/js/FusionCharts.js"></script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td  width="100%" height="31" align="left" valign="bottom" background="${resourcePath}/img/right_111.gif">
    <table height="31"  cellpadding="0" cellspacing="0" width="601" class="zxgg"  background="${resourcePath}/img/right_14.gif" style="border:#519ccc solid 1px; border-bottom:none" >
      <tr >
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',0);" align="center" class="selectli1"><a href="#" class="a_blue_12b">${statistic.year}年${chart.num1}安全生产隐患排查重大隐患统计图</a></td>
        <td width="1" bgcolor="#519ccc"></td>
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',1);" align="center" ><a href="#" class="a_blue_12">${statistic.year}年各${chart.num2}安全生产隐患排查重大隐患统计图</a></td>
      </tr>
    </table></td>
  </tr>
  <tbody id='aTabs' name='aTabs'>
    <tr>
      <td height="100%"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue" style="border-top: none">
        <tr>
          <td align="align" valign="top"><div id="chartdiv" align="left"></div></td>
          <td align="left" valign="top"><div id="chartdiv1" align="left"></div></td>
        </tr>
      </table></td>
    </tr>
  </tbody>
  <tbody id='aTabs'  name='aTabs' style='display:none'>
    <tr>
      <td height="100%"><table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue" style="border-top:none">
        <tr>
          <td align="align" valign="top"><div id="dchartdiv" align="left"></div></td>
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
	var chart = new FusionCharts("${resourcePath}/flash/MSColumn3D.swf","ChartId",500,heightVal);
	chart.setDataURL("${resourcePath}/xml/${chart.title1}.xml");	 
   	chart.render("chartdiv");
   	
   	var chart2 = new FusionCharts("${resourcePath}/flash/Pie3D.swf","ChartId1",500,heightVal);
	chart2.setDataURL("${resourcePath}/xml/${chart.title2}.xml");	 
   	chart2.render("chartdiv1");
   	
   	var dchart = new FusionCharts("${resourcePath}/flash/MSColumn3D.swf","dChartId",widthVal,heightVal);
	dchart.setDataURL("${resourcePath}/xml/${chart.title3}.xml");		   
   	dchart.render("dchartdiv");
</script>
</html>
