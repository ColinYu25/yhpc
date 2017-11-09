<@fkMacros.pageHeaderPrintNew />
<#escape x as (x)!>
<style media=print> 
	.noprint{display:none;} 
</style>
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
		
		//下季度1-15号打印上一季度
		
		if(t_quarter==1 ){//当前时间是第一季度只能打印上年的第四季度的报表
			if(parseFloat(4) == quarter){
				if(t_year-parseFloat(1) == parseFloat(${statistic.year})){//判断是不是上年
					if(t_month==parseFloat(1)){//只能在一月打印
						 if(parseFloat(1)<=t_today && t_today<=parseFloat(15)){//只能在一月(1-15号)打印
						 		jQuery("#yuran").attr("disabled",""); 
								jQuery("#print").attr("disabled","");
								
								//填表日期默认是打印时间
						        date=t_year+"年"+t_month+"月"+t_today+"日";
						        document.getElementById("span1").innerHTML=date;
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
								
								//填表日期默认是打印时间
						        date=t_year+"年"+t_month+"月"+t_today+"日";
						        document.getElementById("span1").innerHTML=date;
						 }
					}
				}
			}
		}
		
		//本季度25开始可打印
			
		if(t_quarter==quarter ){//查询本季度  
		if(t_month==parseFloat(3) || t_month==parseFloat(6) || t_month==parseFloat(9)  || t_month==parseFloat(12)){//只能在3、6、9、12月打印
			 if( t_today>=25){//25号之后可打印
					jQuery("#yuran").attr("disabled",""); 
					jQuery("#print").attr("disabled","");
								
					//填表日期默认是打印时间
					date=t_year+"年"+t_month+"月"+t_today+"日";  
					document.getElementById("span1").innerHTML=date;
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
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
    	<td width="100%" height="31" align="left" valign="bottom" background="${resourcePath}/img/right_111.gif">
    		<table height="31"  cellpadding="0" cellspacing="0" width="601" class="zxgg"  background="${resourcePath}/img/right_14.gif" style="border:#519ccc solid 1px; border-bottom:none" >
      			<tr >
			        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',0);" align="center" class="selectli1"><a href="#" class="a_blue_12b">季度统计汇总表</a></td>
			        <td width="1" bgcolor="#519ccc"></td>
			        <td width="300" id='aTabTitle' name='aTabTitle' onClick="ShowTabss('aTabTitle','aTabs',1);winLink();" align="center" ><a href="#" class="a_blue_12">年度统计汇总表</a></td>
      			</tr>
    		</table>
    	</td>
	</tr>
  	<tbody id='aTabs' name='aTabs'>
    <tr>
    	<td height="100%">
    		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top: none">
        		<tr>
          			<td align="left" valign="top">
          				<div id="chartdiv" align="left">
          					<table width="100%" cellspacing="1" cellpadding="0" border="0" align="center" >
		   						<tr>
									<td align="center">
										<br>
										<!--<input  type="button" value="打印预览测试"    onclick="javascript:doPrintTest('printPreview', this);"/>-->
										<input name="yuran" id="yuran" type="button" value="打印预览"  disabled="disabled"  onclick="javascript:doPrintByAjax('printPreview', this);"/>　　　
										<input name="print" id="print" type="button" value="打   印"  disabled="disabled" onclick="javascript:if(confirm('   确定要打印吗?')){doPrintByAjax('print', this);}"/>　　　
										<input name="print" type="button" value="返   回"  onclick="javascript:history.back(-1);"/>
										<a style="float:right;margin-right:100px;" title="无法自动安装打印控件的请下载后安装" href="${resourcePath}/jatools/print.rar">打印控件下载</a>
										<br><br>
		  								<span class="head">
		  									<input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
             　　										<input name="firstQuarter" type="button"  value="一季度"  onclick="changeQUrl(1);"/>
							                &nbsp; &nbsp;<input name="secondQuarter" type="button"  value="二季度"  onclick="changeQUrl(2);"/>
							                &nbsp; &nbsp;<input name="thirdQuarter" type="button"  value="三季度"  onclick="changeQUrl(3);"/>
							                &nbsp; &nbsp;<input name="fouthQuarter" type="button"  value="四季度"  onclick="changeQUrl(4);"/>
							             </span>
               						</td>
               					</tr>
           					</table>
           					<center>
           						<div id='page1'>
         							<table width="649" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
		   								<tr><td align="center" colspan="${areas?size+3}"><br><strong style="font-size:16px;line-height:40px;">生产经营单位（组织）第<#if statistic.quarter==1>一<#elseif statistic.quarter==2>二<#elseif statistic.quarter==3>三<#elseif statistic.quarter==4>四</#if>季度安全生产隐患排查治理情况统计季报表</strong></td></tr>
		  								<tr><td align="left" style="font-size:14px;line-height:25px;">单位名称（章）：${statisticForCompany.companyName}</td></tr>
									</table>
		  							<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="649">
		  								<TR>
										    <td width="10%" rowSpan="2" align="center"><strong>单　位</strong></td>
										    <td width="10%" align="center"><strong>地　址</strong></td>
										    <TD colSpan="3" width="80%" align="left">&nbsp;${statisticForCompany.address}</TD>
		  								</TR>
		  								<TR>
										    <td align="center"><strong>区　域</strong></td>
										    <TD colSpan="5" align="left">&nbsp;${statisticForCompany.areaName}&nbsp;</TD>
		  								</TR>
		  								<TR>
										    <td rowSpan="2" align="center"><strong>作　业<br>场　所</strong></td>
										    <td align="center"><strong>地　址</strong></td>
										    <TD colSpan="3" align="left">&nbsp;${statisticForCompany.bussnissAddress}</TD>
		  								</TR>
		  								<TR>
										    <td align="center"><strong>区　域</strong></td>
										    <TD colSpan="3" align="left">&nbsp;　　　　　　　　　　　　　县（市）区　　　　　　　　　　　乡镇（街道）</TD>
		  								</TR>
		  								<TR>
										    <td colspan="2" align="center"><strong>联 系 人</strong> </td>
										    <TD width="20%" align="left">&nbsp;${statisticForCompany.linkMan}</TD>
										    <td align="center" width="15%"><strong>联系电话</strong></td>
										    <TD align="left">&nbsp;座机：<#if statisticForCompany?if_exists.linkTel?exists>${statisticForCompany.linkTel}&nbsp;
										    <#else>　　　　　　</#if>手机：${statisticForCompany.linkMobile}</TD>
		  								</TR>
									</table>
									<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="649">
									  <TR>
									    <td width="50%" colSpan="2" align="center"><strong>一 般 隐 患</strong></td>
									    <TD colSpan="2" width="50%" align="center"><strong>重 大 隐 患</strong></TD>
									  </TR>
									  <TR>
									    <td align="center" width="25%"><strong>发现：<#if statisticForCompany?exists&&statisticForCompany.troubNum?exists&&statisticForCompany.troubNum gt 0> <a href="${base}/seasonReport/loadNomalDangers.xhtml?daNomalDanger.jbFlag=1&company.id=${company.id}&daNomalDanger.isAllYear=0&daNomalDanger.repairState=2&daNomalDanger.jbYear=${statistic.year}&daNomalDanger.jbQuarter=${statistic.quarter}" target="block">　${statisticForCompany.troubNum}</a>　<#else>${statisticForCompany.troubNum}</#if>项</strong></td>
									    <td align="center" width="25%"><strong>尚未整改：<#if statisticForCompany?exists&&statisticForCompany.troubNum?exists&&statisticForCompany.troubCleanNum?exists&&statisticForCompany.troubNum-statisticForCompany.troubCleanNum gt 0> <a href="${base}/seasonReport/loadNomalDangers.xhtml?daNomalDanger.jbFlag=1&company.id=${company.id}&daNomalDanger.isAllYear=0&daNomalDanger.repairState=0&daNomalDanger.jbYear=${statistic.year}&daNomalDanger.jbQuarter=${statistic.quarter}" target="block"> ${statisticForCompany.troubNum-statisticForCompany.troubCleanNum}</a>　<#else>${statisticForCompany.troubNum-statisticForCompany.troubCleanNum}</#if>项</strong></td>
									    <td align="center" width="25%"><strong>发现：　<#if statisticForCompany?exists&&statisticForCompany.bigTroubNum?exists&&statisticForCompany.bigTroubNum gt 0> <a href="${base}/danger/loadDangers.xhtml?danger.jbFlag=1&company.id=${company.id}&danger.isAllYear=0&danger.repairState=2&danger.jbYear=${statistic.year}&danger.jbQuarter=${statistic.quarter}" target="block">${statisticForCompany.bigTroubNum}</a><#else>${statisticForCompany.bigTroubNum}</#if> 　项</strong></td>
									    <td align="center" width="25%"><strong>尚未整改：<#if statisticForCompany?exists&&statisticForCompany.planBigTroubNum?exists&&statisticForCompany.planBigTroubNum gt 0><a href="${base}/danger/loadDangers.xhtml?danger.jbFlag=1&company.id=${company.id}&danger.isAllYear=0&danger.repairState=0&danger.jbYear=${statistic.year}&danger.jbQuarter=${statistic.quarter}" target="block">${statisticForCompany.planBigTroubNum}</a><#else>${statisticForCompany.planBigTroubNum}</#if>　　　项</strong></td>
									  </TR>
									</table>
									<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="649">
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
       								<table width="649" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
									  <tr>
										<td align="center" style="font-size:14px;line-height:25px;">单位负责人：<#if company?exists&&company.fddelegate?exists><span class="noprint">${company.fddelegate}</span></#if>　　　　　　　　填表人：<#if company?exists&&company.safetyMngPerson?exists&&company.safetyMngPerson!=''><span class="noprint">${company.safetyMngPerson}</span></#if>　　　　　　　　　填报日期：<span id="span1">${statisticForCompany.reportDate}</span></td>
									  </tr>
	  								  <tr>
	  								  	<td  height=300>
	 										<div align=right id="div1"></div>
	  									</td>
	  								  </tr>
									</table>
          						</div>
          					</center>
          				</div>
          			</td>
        		</tr>
      		</table>
      	</td>
    </tr>
  </tbody>
  <tbody id='aTabs'  name='aTabs' style='display:none'>
    <tr>
      <td height="100%">
      	<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0" class="table_4_blue11" style="border-top:none">
	        <tr>
	          <td align="left" valign="top"><div id="dchartdiv" align="left"></div></td>
	        </tr>
     	 </table>
      </td>
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
			window.location = "?statistic.year="+get("year").value.substring(0,4)+"&company.id=${company.id}&statistic.quarter="+arguments[0];
		}
		
		function winLink() {
			window.location = "loadReportByCompanyAll.xhtml?company.id=${company.id}&statistic.year=${statistic.year}&statistic.oldYear=${statistic.year}&statistic.oldQuarter=${statistic.quarter}";
		}
		printParam(25,0,0,0,1);
		
		function doPrintByAjax(how, obj)
		{			
		    var jatoolsPrinter = document.getElementById("jatoolsPrinter");
		    obj.setAttribute("disabled", "disabled");

			var reportDate1=document.getElementById("span1").innerHTML.replace("年","-").replace("月","-").replace("日","");
			var url = "${base}/company/saveQuarterReport.xhtml"
			jQuery.ajax({
			   type: "POST",
			   url: url,
			   dataType:"text",
			   data: {"entity.year" : '${statistic.year}', "entity.quarter" : '${statistic.quarter}', "entity.reportDate1" : reportDate1, "entity.company.id": '${company.id}', rand : Math.random()},
			   success: function(msg){
			     if(msg=='fail'){
			         alert("打印失败，请重新打印！");
			         return;
			     }
			     if(how == 'printPreview'){
			    		jatoolsPrinter.printPreview(myreport);   // 打印预览
				  }else if(how == 'print'){
				   		jatoolsPrinter.print(myreport ,true);   // 打印前弹出打印设置对话框
				  }else{
				   		jatoolsPrinter.print(myreport ,false);       // 不弹出对话框打印
				  }	
				  		jQuery(obj).removeAttr("disabled", "");
			   },
			   error:function (XMLHttpRequest, textStatus, errorThrown) {
			        alert("打印失败，请重新打印！");
			   		//alert(textStatus);
				}
			});			
		}
		
		
		
	  function doPrintTest(how, obj)
		{			
			var jatoolsPrinter = navigator.userAgent.indexOf('MSIE')>-1 ? ojatoolsPrinter : ejatoolsPrinter;//判别IE或者非IE	
		    if(typeof(jatoolsPrinter.page_div_prefix)=='undefined'){
		        alert("请按页顶上的黄色提示下载ActiveX控件.如果没有提示请按以下步骤设置ie.\n 工具-> internet 选项->安全->自定义级别,设置 ‘下载未签名的 ActiveX ’为'启用'状态")
		        return ;
		    }
		    obj.setAttribute("disabled", "disabled");
           jatoolsPrinter.printPreview(myreport);   // 打印预览		
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
        
       function showDaNomalDangers(repairState) {
	       window.open ("${base}/seasonReport/loadNomalDangers.xhtml?daNomalDanger.jbFlag=1&company.id=${company.id}&daNomalDanger.isAllYear=0&daNomalDanger.repairState=2&daNomalDanger.jbYear=${statistic.year}&daNomalDanger.jbQuarter=${statistic.quarter}",'newwindow','') 
	       
	   }
	
	   function showDangers(repairState) {
		   window.location = "${base}/danger/loadDangers.xhtml?danger.jbFlag=1&company.id=${company.id}&daNomalDanger.isAllYear=0&danger.repairState="+repairState+"&danger.jbYear=${statistic.year}&danger.jbQuarter=${statistic.quarter}";
	   }
	</script>
</#escape>
<@fkMacros.pageFooter />