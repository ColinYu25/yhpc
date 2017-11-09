<#if statistic.isStType==1>
<#include "statistic_trouble_head.ftl">
<#elseif statistic.isStType==2 >
<#include "statistic_mass_head.ftl">
<#else>
<#include "quarter_head.ftl">
</#if>
	<div class="box-con">
		<div class="box-title7">
						<div class="left"></div>
						<div class="center">
							<div class="mlist">
								<#if statistic.isStType==1>
								<#include "statistic_ind_trouble_head.ftl">
								<#elseif statistic.isStType==2 >
								<#include "statistic_ind_mass_head.ftl">
								<#else>
								<#include "quarter_industry_head.ftl">
								</#if>
							</div>
							<div class="fn2">
								<div class="left">
									<input type="text" id="year"  onchange="year_onchange();" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
   				 					<b><#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else>
    								<#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>房屋建筑工程
									</b>
								</div>
								<div class="right">
									<ul class="fnlist">
										<li><a id="all"<#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeQUrl(0);">全 年</a></li>
										<li><a onclick="changeQUrl(1);" id="one" <#if statistic.quarter == 1>class="hover"</#if> href="#" >第一季度</a></li>
										<li><a onclick="changeQUrl(2);" id="two"<#if statistic.quarter == 2>class="hover"</#if> href="#" >第二季度</a></li>
										<li><a onclick="changeQUrl(3);" id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" >第三季度</a></li>
										<li><a onclick="changeQUrl(4);" id="four"<#if statistic.quarter == 4>class="hover"</#if> href="#" >第四季度</a></li>
									</ul>
								</div>
							</div>							
						</div>
						<div class="right"></div>
						</div>
						<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
						  <tr>
							<td class="tbbg"><b>住建</b>（<#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else>
    								<#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>）
    						<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if>	</strong>		
							&nbsp;&nbsp;
							<#if refresh==1 >
								<a href="loadQuarterItem.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
							</#if>
    						</td>
						  </tr>
						  <tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							 <#if area.areaCode==330200000000>
							  <tr class="table02"><td width="15%" height="28" colspan="2" rowspan="1">区域</td>
							  	<#list areasByGroupNum?if_exists as a>
										<td width="${85/(areasByGroupNum?size+1)}%"  title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')" rowspan="1"><strong>${a.areaName}</strong></td>
								</#list>
								<td width="5%" rowspan="1" nowrap="true"><strong>合计</strong></td></tr>
								<#else>
								<tr class="table02" align="center"><td width="15%" height="28" colspan="2">区域</td>
							  	<#list areasByGroupNum?if_exists as a>
								    <#if (a.areaCode==0)>
										   <td width="${85/(areasByGroupNum?size+1)}%"><strong>${a.areaName}</strong></td>
									<#else>
										   <td width="${85/(areasByGroupNum?size+1)}%"  title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
									</#if>
								</#list>
								<td width="${85/(areasByGroupNum?size+1)}%" ><strong>合计</strong></td></tr>
								</#if>
								<#list 0..3 as i>
									  <tr class="table03" bgcolor="#ffffff" align="center">
									  <#if i_index==0><td rowspan="4"><strong>排<br>查<br>进<br>度</strong></td><td width="6%" nowrap="true">项目基数</td><#assign ptype='paicha'><#assign isGorver=''>
									  <#elseif i_index==1><td width="6%" nowrap="true">未季报数</td><#assign ptype='paicha'><#assign isGorver='0'>
									  <#elseif i_index==2><td width="6%" nowrap="true">季 报 率</td>
									  <#elseif i_index==3><td width="6%" nowrap="true">已竣工数</td><#assign ptype='paicha'><#assign isGorver='2'></#if>
									  <#assign rowTotal=0>
									  	<#list areasByGroupNum?if_exists as a>
									    <td <#if i_index!=2>onClick="showDetailItem(${a.areaCode},'${isGorver}','${ptype}');" style="cursor:hand;"</#if>>
									    <#list statistics?if_exists as s><#if a.areaCode==s.areaCode>
									    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
									    <#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
									    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if>
									    <#elseif i_index==3>${s.anum}<#assign rowTotal=rowTotal+s.anum>
									    </#if></#if></#list></td>
									    </#list>
									    <td <#if i_index!=2>onClick="showDetailItem(${area.areaCode},'${isGorver}','${ptype}');" style="cursor:hand;"</#if>>
									    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
									    <#elseif i_index==1>${rowTotal}<#assign bnum=rowTotal>
									    <#elseif i_index==2><#if anum==0>/<#else>#{((anum-bnum)/anum*100);M1}%</#if>
									    <#elseif i_index==3>${rowTotal}<#assign aanum=rowTotal></#if></td>
									  </tr>
								  </#list>
								<#list 0..5 as i>
									  <tr class="table03" <#if i_index==0 || i_index==1 || i_index==2>bgcolor="#F5F5F5"<#else>bgcolor="#ffffff"</#if> align="center">
									  <#if i_index==0><td rowspan="3"><strong>一般隐患</strong></td><td width="6%" nowrap="true">隐 患 数</td>
									  <#elseif i_index==1><td width="6%" nowrap="true">未整改数</td>
									  <#elseif i_index==2><td width="6%" nowrap="true">整 改 率</td>
									  <#elseif i_index==3><td rowspan="3"><strong>重大隐患</strong></td><td width="6%" nowrap="true" >隐 患 数</td><#assign ptype='danger'><#assign isGorver=''>
									  <#elseif i_index==4><td width="6%" nowrap="true">未整改数</td><#assign ptype='danger'><#assign isGorver='0'>
									  <#elseif i_index==5><td width="6%" nowrap="true">整 改 率</td></#if>
									  <#assign rowTotal=0>
									  	<#list areasByGroupNum?if_exists as a>
									    <td <#if i_index==3 || i_index==4>onClick="showDetailItem(${a.areaCode},'${isGorver}','${ptype}');" style="cursor:hand;"</#if>>
									    <#list statisticsForDanger?if_exists as s><#if a.areaCode==s.areaCode>
									    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
									    <#elseif i_index==1>${s.number}<#assign rowTotal=rowTotal+s.number>
									    <#elseif i_index==2><#if s.inhere==0>/<#else>#{((s.inhere-s.number)/s.inhere*100);M1}%</#if>
									    <#elseif i_index==3>${s.anum}<#assign rowTotal=rowTotal+s.anum>
									    <#elseif i_index==4>${s.anum-s.bnum}<#assign rowTotal=rowTotal+s.anum-s.bnum>
									    <#elseif i_index==5><#if s.anum==0>/<#else>#{(s.bnum/s.anum*100);M1}%</#if>
									    </#if></#if></#list></td>
									    </#list>
									    <td <#if i_index==3 || i_index==4>onClick="showDetailItem(${area.areaCode},'${isGorver}','${ptype}');" style="cursor:hand;"</#if>>
									    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
									    <#elseif i_index==1>${rowTotal}<#assign bnum=rowTotal>
									    <#elseif i_index==2><#if anum==0>/<#else>#{((anum-bnum)/anum*100);M1}%</#if>
									    <#elseif i_index==3>${rowTotal}<#assign aanum=rowTotal>
									    <#elseif i_index==4>${rowTotal}<#assign bbnum=rowTotal>
									    <#elseif i_index==5><#if aanum==0>/<#else>#{((aanum-bbnum)/aanum*100);M1}%</#if></#if></td>
									  </tr>
								  </#list>
								  </tr>	  
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
	    var nowQuarter=${current_quarter};
	    
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
		 
    function year_onchange() {
       var changeYear=document.getElementById('year').value;
       var changeYear1=changeYear.split("年",1);
       
       if(year==changeYear1){
           if(nowQuarter==1){
				get("two").disabled=true;
				get("three").disabled=true;
				get("four").disabled=true;
			}else if(nowQuarter==2){
			    get("three").disabled=true;
				get("four").disabled=true;
			}else if(nowQuarter==3){
			    get("four").disabled=true;
			}	 
       }else{
            get("one").disabled=false;
            get("two").disabled=false;
			get("three").disabled=false;
			get("four").disabled=false;
       }
    }
	
	function roleName(){
		var roleName = document.getElementsByName("roleName");
		for(var i = 0 ; i<roleName.length-1;i ++){
			if (roleName[i].innerHTML == roleName[i+1].innerHTML ){
				var k = 0;
				for(var j = 0 ; j<roleName.length-1;j ++){
					if (roleName[j].innerHTML == roleName[j+1].innerHTML && roleName[j].innerHTML == roleName[i].innerHTML ){
						k = k+1;
					}
				}
				roleName[i].colSpan = parseInt(roleName[i].colSpan) + k;			
				roleName[i+1].style.display = "none";
			}
		}
	}
	<#--<#if area.areaCode==330200000000>roleName();</#if>-->
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "?statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		function showDetailItem(areaCode,isGorver,ptype) {
			if(ptype == 'danger'){
				window.location = "loadItemTroubleByIndustryList.xhtml?area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.remark=${statistic.remark}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}else{
				window.location = "loadItemByIndustryList.xhtml?area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.remark=${statistic.remark}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		function changeQUrl() {
			window.location = "?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
		/**
		*根据年份查询统计表
		*/
		function showMonthData(month) {
			window.location = "?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
		}
	</script>
	<#include "statistic_foot.ftl">