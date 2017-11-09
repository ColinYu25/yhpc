<#include "statistic_month_head.ftl">
<table width="986" height="145" border="0" cellspacing="0" cellpadding="0" class="table01">
  <tr>
	<td class="tbbg"><b>${statistic.areaName}各地</b>（<#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>）</td>
  </tr>
  <tr>
	<td valign="top">
		<div id="proposal-review" style="margin-left:0px;"><#--Jquery + json + jTemplate-->
			<textarea id="proposal-template" style="display:none">	
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				 	<tr class="table02">
				 		<td height="22" colspan="2">区域</td>
					 	<#if areas?exists>
				  			<#list areas?if_exists as a><td width="${90/(areas?size + 1)}%" <#if statistic?? && statistic.areaType?? && statistic.areaType=='second_area'>style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看${a.areaName}统计" onClick="showAreaDetail(${a.areaCode});"</#if>>${a.areaName}</td></#list>
			  		 	</#if>
		  		 		<td width="${90/(areas?size + 1)}%">合计</td>
				  	</tr>
				  	<#-- 管道 -->
				  	<#if statistic.remark?? && statistic.remark = ''>
				  		<#assign seq = [100, 1, 3, 4, 2, 0]>
				  	<#elseif statistic.remark = 'anjian'>
				  		<#assign seq = [3]>
				  	<#elseif statistic.remark = 'fagai'>
				  		<#assign seq = [1]>
				  	<#elseif statistic.remark = 'chengguan'>
				  		<#assign seq = [2, 0]>
				  	<#elseif statistic.remark = 'jiaotong'>
				  		<#assign seq = [4]>
				  	</#if>
				  	<#list seq as x>
				  		<#if x_index%2 == 0>
				  			<#assign color = '#FFFFFF'>
				  		<#else>
				  			<#assign color = '#F5F5F5'>
				  		</#if>
					  	<tr class="table03" bgcolor=${color }>
					  		<td height="22" rowspan="4" nowrap>
					  			<strong>
					  			<#if statistic.remark?? && statistic.remark = ''>
							  		<#if x==0>中低压燃气管道<#elseif x==1>长输管道<#elseif x==2>高压燃气管道<#elseif x==3>工业管道<#elseif x==4>港区内油气管道<#else>合计</#if>
							  	<#else>
							  		<#if x==0>中低压燃气管道<#elseif x==1>发改委<br/>（长输管道）<#elseif x==2>高压燃气管道<#elseif x==3>安监<br/>（工业管道）<#elseif x==4>交通局<br/>（港区内油气管道）<#else>合计</#if>
							  	</#if>
							  	</strong>
					  		</td>
					  		<td><strong>单&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;&nbsp;数</strong></td>
						  	<#if areas?exists>
					  			<#list areas?if_exists as a><td height="22" onclick="showCompanyListDetail(${a.areaCode}, '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_q}</td></#list>
				  		 		<td onclick="showCompanyListDetail(0, '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_q + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color }>
			  		 		<td><strong>管&nbsp;&nbsp;&nbsp;道&nbsp;&nbsp;&nbsp;数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_g}</td></#list>
							  	<td onclick="showPipelineListDetail(0, '', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_g + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color }>
			  		 		<td><strong>未录入管道数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '1', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_g - $T.returnMap${x}.s_${x}_${a.areaCode}_y}</td></#list>
				  		 		<td onclick="showPipelineListDetail(0, '1', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>($T.returnMap${x}.s_${x}_${a.areaCode}_g - $T.returnMap${x}.s_${x}_${a.areaCode}_y) + </#list> + 0}</td>
				  		 	</#if>
					  	</tr>
					  	<tr class="table03" bgcolor=${color }>
			  		 		<td nowrap><strong>录&nbsp;入&nbsp;率（%）</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22">{#if $T.returnMap${x}.s_${x}_${a.areaCode}_g != 0} {(100 * $T.returnMap${x}.s_${x}_${a.areaCode}_y / $T.returnMap${x}.s_${x}_${a.areaCode}_g).Fixed(2, true)} {#else} 0/0 {#/if}</td></#list>
							  	<td>{#if (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_g + </#list> + 0) != 0} {(100*(<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0) / (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_g + </#list> + 0)).Fixed(2)} {#else} 0/0 {#/if}</td>
				  		 	</#if>
					  	</tr>
				  	</#list>
				</table>
			</textarea>
		</div>
	</td>
  </tr>
</table>
<script>
	var trMoveColor="#cae5ff"; 
	
	jQuery("#proposal-review").setTemplateElement("proposal-template");//初始化Template
	jQuery("#proposal-review").processTemplate(${jdata});//渲染box

	function showAreaDetail(areaCode) {
		window.location = "loadMonth.xhtml?statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}&statistic.areaCode=" + areaCode;
	}
	
	function changeUrl(quarter) {
		var m = ${statistic.month};
		var month = m;
		if(parseFloat(quarter)-parseFloat(1)==0){
			if(parseFloat(m)>=1 && parseFloat(m)<4){
			}else{
				month = 1;
			}
		}
		if(parseFloat(quarter)-parseFloat(2)==0){
			if(parseFloat(m)>=4 && parseFloat(m)<7){
			}else{
				month = 4;
			}
		}
		if(parseFloat(quarter)-parseFloat(3)==0){
			if(parseFloat(m)>=7 && parseFloat(m)<10){
			}else{
				month = 7;
			}
		}
		if(parseFloat(quarter)-parseFloat(4)==0){
			if(parseFloat(m)>=10 && parseFloat(m)<=12){
			}else{
				month = 10;
			}
		}
		window.location = "loadMonth.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.month="+month+"&statistic.quarter=" + quarter;
	}
	
	function changeUrl2(remark) {
		window.location = "loadMonth.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}&statistic.remark=" + remark;
	}

	function showMonthData(month) {
		window.location = "loadMonth.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
	}	
	
	function showYearData(year) {
		window.location = "loadMonth.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=${statistic.remark}&statistic.quarter=${statistic.quarter}&statistic.year=" + year;
	}
	
	function showCompanyListDetail(areaCode, type) {
		<#if statistic?? && statistic.areaType?? && statistic.areaType=='third_area'>
			window.location = "statistic_companyList.xhtml?statistic.secondArea=${statistic.areaCode}&statistic.areaCode="+areaCode+"&statistic.lineType=" + type;
		<#else>
			window.location = "statistic_companyList.xhtml?statistic.areaCode="+areaCode+"&statistic.lineType=" + type;
		</#if>
	}	
	
	function showPipelineListDetail(areaCode, paramType, type) {
		<#if statistic?? && statistic.areaType?? && statistic.areaType=='third_area'>
			window.location = "statistic_pipelineList.xhtml?statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}&statistic.secondArea=${statistic.areaCode}&statistic.paramType="+paramType+"&statistic.areaCode="+areaCode+"&statistic.lineType=" + type;
		<#else>
			window.location = "statistic_pipelineList.xhtml?statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}&statistic.paramType="+paramType+"&statistic.areaCode="+areaCode+"&statistic.lineType=" + type;
		</#if>
	}	
</script>
<#include "statistic_foot.ftl">