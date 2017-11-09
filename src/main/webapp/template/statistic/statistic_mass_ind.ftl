<#include "statistic_mass_head.ftl">
	<div class="box-con">
		<div class="box-title">
						<div class="left"></div>
						<div class="center">
						 <#list statistics?if_exists as s>
	  	 					<#assign allParam=s.sonNumber1+s.sonNumber2+3>
							<div class="mlist">
								<#include "statistic_ind_mass_head.ftl">
							</div>
							<div class="fn">
								<div class="left">
									<input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
									<b> <#assign title=""/>
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
							             ${title}<#if area.fatherId==3020  && s.enumName=="贸易局">商务局<#elseif area.fatherId==3020  && s.enumName=="贸易">商务<#else>${s.enumName}</#if>排查质量</b>
								</div>
								<div class="right">
									<ul class="fnlist">
										<li><a id="all"  <#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeQUrl(0);">全 年</a></li>
										<li><a id="one"  <#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeQUrl(1);">第一季度</a></li>
										<li><a id="two"  <#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeQUrl(2);">第二季度</a></li>
										<li><a id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeQUrl(3);">第三季度</a></li>
										<li><a id="four" <#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeQUrl(4);">第四季度</a></li>
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
						<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
						  <tr>
							<td class="tbbg"><b><#if area.fatherId==3020   && s.enumName=="贸易局" >商务局<#elseif area.fatherId==3020   && s.enumName=="贸易" >商务<#else>${s.enumName}</#if></b>（${title}）
							<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
							&nbsp;&nbsp;
							<#if refresh==1 >
								<a href="loadMassByIndustry.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
							</#if>
							</td>
						  </tr>
						  <tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr class="table02"><td width="15%" height="28" colspan="${s.sonNumber1+s.sonNumber2+2}"><#if s_index==0><#break></#if></#list>区域</td>
							  	<#list areas?if_exists as a>
									<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
					    		</#list>
					    		<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td></tr>
							   <#list statistics?if_exists as hy>
					    		<#list 0..4 as i>
								  <tr class="table03" <#if hy_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if> align="center">
								  <#if i_index==0>
								  		<#if ((hy.sonNumber1+hy.sonNumber2)>0)>
								  		<#assign colParam=hy.sonNumber1+hy.sonNumber2>
								  		<td rowspan="${hy.sonCount*5}" width="3%"><strong>
								  			<!--<#list 0..hy.enumName?length-1 as hl>${hy.enumName}<br></#list> -->
								  			<#if area.fatherId==3020   && (hy.enumName=="贸易局" || hy.enumName=="贸易")>商<br>务<br>局<#else>${hy.enumName}</#if>
								  			
								  		</strong></td>
								  		<td rowspan="5" colspan="${colParam}"><strong>合计</strong></td>
								  		<#else><#if ((hy.sonNumber1+hy.sonNumber2)==0 && hy_index=7)><#assign colParam=colParam+1></#if><td rowspan="5" <#if colParam?exists>colspan="${colParam}"<#else>colspan="1"</#if>><strong>${hy.enumName}</strong></td>
								  		</#if>
								  		<td width="5%" nowrap="true">已 报 数</td><#assign ptype='y'>
									  <#elseif i_index==1><td nowrap="true">零隐患数</td><#assign ptype='z'>
									  <#elseif i_index==2><td nowrap="true">零隐患率</td>
									  <#elseif i_index==3><td nowrap="true" >企业自报数</td><#assign ptype='q'>
									  <#elseif i_index==4><td nowrap="true" >企业自报率</td>
									  </#if>
									  <#assign rowTotal=0>
									  	<#list areas?if_exists as a>
									    <td <#if i_index!=2 && i_index!=4>onClick="showDetail(${a.areaCode},'${hy.industryId}','${ptype}');" style="cursor:pointer;"</#if>>
									    <#list statistics?if_exists as s>
									    <#if hy.industryId==s.industryId>
									    <#if a.areaCode==s.areaCode>
									    <#if i_index==0>${s.number}<#assign rowTotal=rowTotal+s.number>
									    <#elseif i_index==1>${s.zero}<#assign rowTotal=rowTotal+s.zero>
									    <#elseif i_index==2><#if s.number==0>/<#else>#{((s.zero)/s.number*100);M1}%</#if>
									    <#elseif i_index==3>${s.qnum}<#assign rowTotal=rowTotal+s.qnum>
									    <#elseif i_index==4><#if s.number==0>/<#else>#{((s.qnum)/s.number*100);M1}%</#if>
									    </#if></#if></#if></#list></td>
									    </#list>
									    <td <#if i_index!=2 && i_index!=4>onClick="showDetail(${area.areaCode},'${hy.industryId}','${ptype}');" style="cursor:pointer;"</#if>>
									    <#if i_index==0>${rowTotal}<#assign number=rowTotal>
									    <#elseif i_index==1>${rowTotal}<#assign zero=rowTotal>
									    <#elseif i_index==2><#if number==0>/<#else>#{(zero/number*100);M1}%</#if>
									    <#elseif i_index==3>${rowTotal}<#assign qnum=rowTotal>
									    <#elseif i_index==4><#if number==0>/<#else>#{(qnum/number*100);M1}%</#if>
									    </#if></td>
									  </tr>
								  </#list>
								  <#if hy_index==(statistics?size/areas?size-1)>
									 <#break>
								 </#if>
								 </#list>
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
				window.location = "?statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyMassByIndustryList.xhtml?company.year=${statistic.year}&company.month=${statistic.month}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
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