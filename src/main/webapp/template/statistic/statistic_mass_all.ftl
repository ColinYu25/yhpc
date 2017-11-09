<#include "statistic_mass_head.ftl">
	<div class="box-con">
		<div class="box-title2">
			<div class="left"></div>
			<div class="center">
				<div class="fn2">
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
				             ${title}各地排查质量</b>
					</div>
					<div class="right">
						<ul class="fnlist">
							<li><a id="all"<#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeUrl(0);">全 年</a></li>
							<li><a id="one"<#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeUrl(1);">第一季度</a></li>
							<li><a id="two"<#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeUrl(2);">第二季度</a></li>
							<li><a id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeUrl(3);">第三季度</a></li>
							<li><a id="four"<#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeUrl(4);">第四季度</a></li>
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
				             	<td style="text-align:right;" class="shun">
				             		<a <#if statistic.month == item>class="hover"</#if> href="#" name="month" onclick="showMonthData(${item});">${item}月份</a>
				             	</td><!--<#if item ==statistic.month>disabled</#if>-->
				              </#list>
						  	</tr>	
						 </table>
					</div>				
					</div>
					<div class="right"></div>
				</div>
		<div id="mess_box" style="width:986px;height:auto !important;height:400px;min-height:400px; OVERFLOW-X:auto; overflow-y:hidden;OVERFLOW:scroll; border:0px;">
		<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
		  <tr>
			<td class="tbbg"><b>${area.areaName}各地</b>（<#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>）
				<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
				&nbsp;&nbsp;
				<#if refresh==1 >
					<a href="loadMassAll.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
				</#if>	
			</td>
		  </tr>
		  <tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr class="table02"><td width="15%" height="28" colspan="3">区域</td>
				  	<#list areas?if_exists as a>
						<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
		    		</#list>
		    		<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td></tr>
				  <#list 0..8 as i>
					  <tr class="table03" <#if i_index==0 || i_index==8>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if> align="center">
					  <#if i_index==0><td rowspan="9" width="4%"><strong>排<br>查<br>质<br>量</strong></td>
					  <td width="8%" nowrap="true" colspan="2"><strong>应报总数</strong></td><#assign ptype='t'>
					  <#elseif i_index==1><td width="4%" nowrap="true" rowspan="7"><strong>已报</strong></td><td nowrap="true"><strong>已报数</strong></td><#assign ptype='y'>
					  <#elseif i_index==2><td nowrap="true"><strong>企业自报数</strong></td><#assign ptype='q'>
					  <#elseif i_index==3><td nowrap="true" ><strong>企业自报率</strong></td>
					  <#elseif i_index==4><td nowrap="true" ><strong>代报数</strong></td><#assign ptype='d'>
					  <#elseif i_index==5><td nowrap="true"><strong>代报率</strong></td>
					  <#elseif i_index==6><td nowrap="true"><strong>零隐患数</strong></td><#assign ptype='z'>
					  <#elseif i_index==7><td nowrap="true" ><strong>零隐患率</strong></td>
					  <#elseif i_index==8><td nowrap="true" colspan="2"><strong>未报数</strong></td><#assign ptype='w'>
					  </#if>
					  <#assign rowTotal=0>
					  	<#list areas?if_exists as a>
						    <td <#if i_index!=3 && i_index!=5 && i_index!=7>onClick="showDetail(${a.areaCode},0,'${ptype}');" style="cursor:pointer;"</#if>>
							    <#list statistics?if_exists as s>
								    <#if a.areaCode==s.areaCode>
									    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
									    <#elseif i_index==1>${s.number}<#assign rowTotal=rowTotal+s.number>
									    <#elseif i_index==2>${s.qnum}<#assign rowTotal=rowTotal+s.qnum>
									    <#elseif i_index==3><#if s.number==0>/<#else>#{((s.qnum)/s.number*100);M1}%</#if>
									    <#elseif i_index==4>${s.number-s.qnum}<#assign rowTotal=rowTotal+s.number-s.qnum>
									    <#elseif i_index==5><#if s.zero==0>/<#else>#{((s.number-s.qnum)/s.number*100);M1}%</#if>
									    <#elseif i_index==6>${s.zero}<#assign rowTotal=rowTotal+s.zero>
									    <#elseif i_index==7><#if s.zero==0>/<#else>#{((s.zero)/s.number*100);M1}%</#if>
									    <#elseif i_index==8>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
									    </#if>
								    </#if>
							    </#list>
						    </td>
					    </#list>
					    <td <#if i_index!=3 && i_index!=5 && i_index!=7>onClick="showDetail(${area.areaCode},0,'${ptype}');" style="cursor:pointer;"</#if>>
					    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
					    <#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
					    <#elseif i_index==2>${rowTotal}<#assign qnum=rowTotal>
					    <#elseif i_index==3><#if number==0>/<#else>#{(qnum/number*100);M1}%</#if>
					    <#elseif i_index==4>${rowTotal}<#assign dnum=rowTotal>
					    <#elseif i_index==5><#if number==0>/<#else>#{(dnum/number*100);M1}%</#if>
					    <#elseif i_index==6>${rowTotal}<#assign zero=rowTotal>
					    <#elseif i_index==7><#if number==0>/<#else>#{(zero/number*100);M1}%</#if>
					    <#elseif i_index==8>${rowTotal}<#assign wnum=rowTotal>
					    </#if>
					    </td>
					  </tr>
				  </#list>
				</table>
			</td>
		  </tr>
		</table>
		</div>
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
				window.location = "?area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyMassByIndustryList.xhtml?company.year=${statistic.year}&company.month=${statistic.month}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
		}
		function changeUrl() {
			window.location = "?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
		
		function showMonthData(month) {
			window.location = "?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
		}
	</script>
<#include "statistic_foot.ftl">