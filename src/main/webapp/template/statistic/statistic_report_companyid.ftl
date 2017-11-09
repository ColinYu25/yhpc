<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	jQuery(document).ready(function(){
		var reportDate = '${reportDate}';
	    var t_today = parseFloat(reportDate.substring(8,10)); //获取当前日(1-31)
	    var t_year = parseFloat(reportDate.substring(0,4));
	   	var t_month = parseFloat(reportDate.substring(5,7));//获取当前月份(0-11,0代表1月),所以需要+1
	   	var t_quarter =0	;//判断当前时间所在的季度，初始0
	   	var quarter = parseFloat(${statistic.quarter});//读取季度，1：一季度，2：二季度，3：三季度，4：四季度
	    
	    //判断是否是第一季度
		if(parseFloat(0)<parseFloat(t_month) &&  parseFloat(t_month)<=parseInt(3)){
			t_quarter = 1;
		}else{
			//判断是否是第二季度
			if(parseFloat(3)<parseFloat(t_month) &&  parseFloat(t_month)<=parseInt(6)){
				t_quarter = 2;
			}else{
				//判断是否是第三季度
				if(parseFloat(6)<parseFloat(t_month) &&  parseFloat(t_month)<=parseInt(9)){
					t_quarter = 3;
				}else{
					//判断是否是第四季度
					if(parseFloat(9)<parseFloat(t_month) &&  parseFloat(t_month)<=parseInt(12)){
						t_quarter = 4;
					}
				}
			}
		}
		
		//alert("reportDate="+reportDate+",t_today="+t_today+",t_year="+t_year+",t_month="+t_month+",t_quarter="+t_quarter+",quarter="+quarter);
		
		if(t_quarter==1 ){//当前时间是第一季度只能打印上年的第四季度的报表
			if(parseFloat(4) == quarter){
				if(t_year-parseFloat(1) == parseFloat(${statistic.year})){//判断是不是上年
					if(t_month==parseFloat(1)){//只能在一月打印
						 if(parseFloat(1)<=t_today && t_today<=parseFloat(15)){//只能在一月(1-15号)打印
						 		jQuery("#yuran").attr("disabled",""); 
								jQuery("#print").attr("disabled","");
						 }
					}
				}
			}
		}else {//if(t_quarter>2 && t_quarter=<4)//当前时间不是第一季度只能打印本年的上个季度的报表
			if(t_quarter-parseFloat(1) == quarter){//判断是不是上个季度
				if(t_year==parseFloat(${statistic.year})){//判断是不是本年
					if(t_month==parseFloat(4) || t_month==parseFloat(7) || t_month==parseFloat(10)){//只能在4、7、10月打印
						 if(parseFloat(1)<=t_today && t_today<=parseFloat(15)){//只能在4、7、10月（1-15号）打印
						 		jQuery("#yuran").attr("disabled",""); 
								jQuery("#print").attr("disabled","");
						 }
					}
				}
			}
		}
	});
</script>
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
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',0);" align="center" class="selectli1"><a href="#" class="a_blue_12b">季度统计汇总表</a></td>
        <td width="1" bgcolor="#519ccc"></td>
        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',1);winLink();" align="center" ><a href="#" class="a_blue_12">年度统计汇总表</a></td>
      </tr>
    </table></td>
  </tr>
  <tbody id='aTabs' name='aTabs'>
    <tr>
      <td height="100%"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top: none">
        <tr>
          <td align="left" valign="top"><div id="chartdiv" align="left">
          	<table width="100%" cellspacing="1" cellpadding="0" border="0" align="center" >
		   <tr>
			<td align="center">
			<br>
			<input name="yuran" id="yuran" type="button" value="打印预览"  disabled="disabled"  onclick="javascript:doPrintByAjax('printPreview', this);"/>　　　
			<input name="print" id="print" type="button" value="打   印"  disabled="disabled" onclick="javascript:if(confirm('   确定要打印吗?')){doPrintByAjax('print', this);}"/>　　　
			<input name="print" type="button" value="返   回"  onclick="javascript:history.back(-1);"/>
			<br><br>
		  <span class="head"><input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
             　　<input name="firstQuarter" type="button"  value="一季度"  onclick="changeQUrl(1);"/>
               &nbsp; &nbsp;<input name="secondQuarter" type="button"  value="二季度"  onclick="changeQUrl(2);"/>
               &nbsp; &nbsp;<input name="thirdQuarter" type="button"  value="三季度"  onclick="changeQUrl(3);"/>
               &nbsp; &nbsp;<input name="fouthQuarter" type="button"  value="四季度"  onclick="changeQUrl(4);"/></span></td></tr>
           </table>
           <div id='page1'>
         <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
		  <tr>
		   <tr><td align="center" colspan="${areas?size+3}"><br><strong style="font-size:16px;line-height:40px;">生产经营单位（组织）第<#if statistic.quarter==1>一<#elseif statistic.quarter==2>二<#elseif statistic.quarter==3>三<#elseif statistic.quarter==4>四</#if>季度安全生产隐患排查治理情况统计季报表</strong></td></tr>
		  </tr>
		  <tr><td align="center" style="font-size:14px;line-height:25px;">单位名称（章）：${statisticForCompany.companyName}　　　　　　　　　　　　　　　　　年　　月　　日<br></td></tr>
		</table>
		  <table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="650">
  <TR>
    <td width="10%" rowSpan="2" align="center"><strong>单　位</strong></td>
    <td width="10%" align="center"><strong>地　址</strong></td>
    <TD colSpan="3" width="80%">&nbsp;${statisticForCompany.address}</TD>
  </TR>
  <TR>
    <td align="center"><strong>区　域</strong></td>
    <TD colSpan="5">&nbsp;${statisticForCompany.areaName}&nbsp;</TD>
  </TR>
  <TR>
    <td rowSpan="2" align="center"><strong>作　业<br>场　所</strong></td>
    <td align="center"><strong>地　址</strong></td>
    <TD colSpan="3">&nbsp;</TD>
  </TR>
  <TR>
    <td align="center"><strong>区　域</strong></td>
    <TD colSpan="3">&nbsp;　　　　　　　　　　　　　县（市）区　　　　　　　　　　　乡镇（街道）</TD>
  </TR>
  <TR>
    <td colspan="2" align="center"><strong>联 系 人</strong> </td>
    <TD width="20%">&nbsp;${statisticForCompany.linkMan}</TD>
    <td align="center" width="15%"><strong>联系电话</strong></td>
    <TD>&nbsp;座机：<#if statisticForCompany?if_exists.linkTel?exists>${statisticForCompany.linkTel}&nbsp;
    <#else>　　　　　　</#if>手机：${statisticForCompany.linkMobile}</TD>
  </TR>
</table>
<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="650">
  <TR>
    <td width="50%" colSpan="2" align="center"><strong>一 般 隐 患</strong></td>
    <TD colSpan="2" width="50%" align="center"><strong>重 大 隐 患</strong></TD>
  </TR>
  <TR>
    <td align="center" width="25%"><strong>发现：　${statisticForCompany.troubNum}　项</strong></td>
    <td align="center" width="25%"><strong>尚未整改：　${statisticForCompany.troubNum-statisticForCompany.troubCleanNum}　项</strong></td>
    <td align="center" width="25%"><strong>发现：　${statisticForCompany.bigTroubNum}　项</strong></td>
    <td align="center" width="25%"><strong>尚未整改：　${statisticForCompany.planBigTroubNum}　项</strong></td>
  </TR>
</table>
<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="650">
  <TR>
    <td width="100%" colSpan="6" align="center"><strong>列入治理计划的未整改重大隐患共  　　${statisticForCompany.planBigTroubNum}　　 项，其中</strong></td>
  </TR>
  <TR>
    <td align="center" width="16.65%"><strong>落实治理<br>目标任务</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>经费物资</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>机构人员</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>时　　间</strong></td>
    <td align="center" width="16.65%"><strong>落实安全措施<br>及应急预案</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>资金（累计）</strong></td>
  </TR>
  <tr>
  	<td align="center" width="16.65%">${statisticForCompany.target}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.goods}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.resource}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.finishDate}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.safetyMethod}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.governMoney.toString()}　万元</td>
  </tr>
</table>
       <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:14px;line-height:25px;">单位负责人：　　　　　　　　　填表人：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	  <tr><td  height=300>
	  
	 <div   align=right  id="div1"></div>
	  </td>
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
	jQuery.noConflict();
	
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
		function changeQUrl() {
			window.location = "?companyId=${statisticForCompany.companyId}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
		
		function winLink() {
			window.location = "loadReportByCompanyIdAll.xhtml?companyId=${statisticForCompany.companyId}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}";
		}
		printParam(25,0,0,0,1);
		
		function doPrintByAjax(how, obj)
		{
		
			//加上打印时间
			var today = new Date();  		
			date=(today.getYear())+"年"+(today.getMonth()+1)+"月"+today.getDate()+"日"+" ";  
			document.getElementById("div1").innerHTML=" 打印时间："+date+"&nbsp;&nbsp;&nbsp;&nbsp;";
			
			var jatoolsPrinter = navigator.userAgent.indexOf('MSIE')>-1 ? ojatoolsPrinter : ejatoolsPrinter;//判别IE或者非IE	
		    if(typeof(jatoolsPrinter.page_div_prefix)=='undefined'){
		        alert("请按页顶上的黄色提示下载ActiveX控件.如果没有提示请按以下步骤设置ie.\n 工具-> internet 选项->安全->自定义级别,设置 ‘下载未签名的 ActiveX ’为'启用'状态")
		        return ;
		    }
		    obj.setAttribute("disabled", "disabled");

/*
			jQuery.get("${base}/company/saveQuarterReport.xhtml?entity.year=${statistic.year}&entity.quarter=${statistic.quarter}&entity.company.id=${company.id}&rand=" + new Date(), function(){
			  if(how == 'printPreview'){
		    	jatoolsPrinter.printPreview(myreport);   // 打印预览
			  }else if(how == 'print'){
			   		jatoolsPrinter.print(myreport ,false);   // 打印前弹出打印设置对话框
			  }else{
			   		jatoolsPrinter.print(myreport ,false);       // 不弹出对话框打印
			  }	
			   obj.setAttribute("disabled", "");
			});
***/			
			
			var url = "${base}/company/saveQuarterReport.xhtml?entity.year=${statistic.year}&entity.quarter=${statistic.quarter}&entity.company.id=${company.id}&rand=" + new Date();
		
			jQuery.ajax({
			   type: "POST",
			   url: url,
			   data: "name=John&location=Boston",
			   complete: function(msg){
			     if(how == 'printPreview'){
			    		jatoolsPrinter.printPreview(myreport);   // 打印预览
				  }else if(how == 'print'){
				   		jatoolsPrinter.print(myreport ,false);   // 打印前弹出打印设置对话框
				  }else{
				   		jatoolsPrinter.print(myreport ,false);       // 不弹出对话框打印
				  }	
				  		obj.setAttribute("disabled", "");
			   },
			   error:function (XMLHttpRequest, textStatus, errorThrown) {
			   		//alert(textStatus);
				}
			});			
		}
		
		function printParam(topMagin,leftMargin,bottomMargin,rightMagin,orientation){
		settings = new Object(); 
		settings.topMargin = topMagin; 
		settings.leftMargin = leftMargin; 
		settings.bottomMargin = bottomMargin; 
		settings.rightMargin = rightMagin; 
		settings.orientation = orientation;
		myreport.print_settings=settings;
		}	
		
	</script>
</#escape>
<@fkMacros.pageFooter />