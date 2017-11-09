<#include "statistic_mass_head.ftl">
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
				 		<td height="22" colspan="3">区域</td>
					 	<#if areas?exists>
				  			<#list areas?if_exists as a><td width="${90/(areas?size + 1)}%" <#if statistic?? && statistic.areaType?? && statistic.areaType=='second_area'>style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看${a.areaName}统计" onClick="showAreaDetail(${a.areaCode});"</#if>>${a.areaName}</td></#list>
			  		 	</#if>
		  		 		<td width="${90/(areas?size + 1)}%">合计</td>
				  	</tr>
				  	<#-- 管道 -->
				  	<#assign seq = [100]>
				  	<#if statistic.remark?? && statistic.remark = ''>
				  		<#assign seq = [100]>
				  	<#elseif statistic.remark = 'anjian'>
				  		<#assign seq = [3]>
				  	<#elseif statistic.remark = 'fagai'>
				  		<#assign seq = [1]>
				  	<#elseif statistic.remark = 'chengguan'>
				  		<#assign seq = [20]>
				  	<#elseif statistic.remark = 'jiaotong'>
				  		<#assign seq = [4]>
				  	</#if>
				  	<#list seq as x>
				  		<#assign color1 = '#FFFFFF'>
				  		<#assign color2 = '#F5F5F5'>
					  	<tr class="table03" bgcolor=${color1 }>
					  		<td height="22" rowspan="11" nowrap>
					  			<strong>
					  			<#if statistic.remark?? && statistic.remark = ''>
							  		&nbsp;&nbsp;排&nbsp;&nbsp;<br>&nbsp;&nbsp;查&nbsp;&nbsp;<br>&nbsp;&nbsp;情&nbsp;&nbsp;<br>&nbsp;&nbsp;况&nbsp;&nbsp;<br>&nbsp;&nbsp;
							  	<#else>
							  		<#if statistic.remark?? && statistic.remark = 'anjian'>安监<#elseif statistic.remark?? && statistic.remark = 'fagai'>发改委<#elseif statistic.remark?? && statistic.remark = 'chengguan'>城管<#elseif statistic.remark?? && statistic.remark = 'jiaotong'>交通局<#else></#if><br>排查情况
							  	</#if>
							  	</strong>
					  		</td>
					  		<td colspan="2"><strong>单&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;&nbsp;数</strong></td>
						  	<#if areas?exists>
					  			<#list areas?if_exists as a><td height="22" onclick="showCompanyListDetail(${a.areaCode}, '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_q}</td></#list>
				  		 		<td onclick="showCompanyListDetail(0, '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_q + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color2 }>
			  		 		<td colspan="2"><strong>管道总数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_g}</td></#list>
							  	<td onclick="showPipelineListDetail(0, '', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_g + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color1 }>
			  		 		<td rowspan="7" nowrap><strong>&nbsp;&nbsp;已&nbsp;&nbsp;<br>&nbsp;&nbsp;报&nbsp;&nbsp;<br>&nbsp;&nbsp;管&nbsp;&nbsp;<br>&nbsp;&nbsp;道&nbsp;&nbsp;</strong></td>
			  		 		<td nowrap><strong>已报总数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '2', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_y}</td></#list>
							  	<td onclick="showPipelineListDetail(0, '2', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color2 }>
			  		 		<td><strong>自&nbsp;&nbsp;报&nbsp;&nbsp;数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '3', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_z}</td></#list>
							  	<td onclick="showPipelineListDetail(0, '3', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_z + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color2 }>
			  		 		<td><strong>自报率（%）</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22">{#if $T.returnMap${x}.s_${x}_${a.areaCode}_y != 0} {(100 * $T.returnMap${x}.s_${x}_${a.areaCode}_z / $T.returnMap${x}.s_${x}_${a.areaCode}_y).Fixed(2)} {#else} 0/0 {#/if}</td></#list>
							  	<td>{#if (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0) != 0} {(100*(<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_z + </#list> + 0) / (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0)).Fixed(2)} {#else} 0/0 {#/if}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color1 }>
			  		 		<td><strong>代&nbsp;&nbsp;报&nbsp;&nbsp;数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '4', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_z)}</td></#list>
							  	<td onclick="showPipelineListDetail(0, '4', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_z) + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color1 }>
			  		 		<td><strong>代报率（%）</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22">{#if $T.returnMap${x}.s_${x}_${a.areaCode}_y != 0} {(100 * ($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_z) / $T.returnMap${x}.s_${x}_${a.areaCode}_y).Fixed(2)} {#else} 0/0 {#/if}</td></#list>
							  	<td>{#if (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0) != 0} {(100*(<#list areas?if_exists as a>($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_z) + </#list> + 0) / (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0)).Fixed(2)} {#else} 0/0 {#/if}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color2 }>
			  		 		<td><strong>零隐患数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '5', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_f)}</td></#list>
								<td onclick="showPipelineListDetail(0, '5', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_f) + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color2 }>
			  		 		<td nowrap><strong>零隐患率（%）</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22">{#if $T.returnMap${x}.s_${x}_${a.areaCode}_y != 0} {(100 * ($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_f) / $T.returnMap${x}.s_${x}_${a.areaCode}_y).Fixed(2)} {#else} 0/0 {#/if}</td></#list>
							  	<td>{#if (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0) != 0} {(100*(<#list areas?if_exists as a>($T.returnMap${x}.s_${x}_${a.areaCode}_y - $T.returnMap${x}.s_${x}_${a.areaCode}_f) + </#list> + 0) / (<#list areas?if_exists as a>$T.returnMap${x}.s_${x}_${a.areaCode}_y + </#list> + 0)).Fixed(2)} {#else} 0/0 {#/if}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr class="table03" bgcolor=${color1 }>
			  		 		<td colspan="2"><strong>未上报管道数</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22" onclick="showPipelineListDetail(${a.areaCode}, '1', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.returnMap${x}.s_${x}_${a.areaCode}_g - $T.returnMap${x}.s_${x}_${a.areaCode}_y}</td></#list>
				  		 		<td onclick="showPipelineListDetail(0, '1', '<#if x!=100>${x}</#if>');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>($T.returnMap${x}.s_${x}_${a.areaCode}_g - $T.returnMap${x}.s_${x}_${a.areaCode}_y) + </#list> + 0}</td>
				  		 	</#if>
					  	</tr>
					  	<tr class="table03" bgcolor=${color1 }>
			  		 		<td nowrap colspan="2"><strong>上&nbsp;报&nbsp;率（%）</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22">{#if $T.returnMap${x}.s_${x}_${a.areaCode}_g != 0} {(100 * $T.returnMap${x}.s_${x}_${a.areaCode}_y / $T.returnMap${x}.s_${x}_${a.areaCode}_g).Fixed(2)} {#else} 0/0 {#/if}</td></#list>
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
		window.location = "loadMass.xhtml?statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}&statistic.areaCode=" + areaCode;
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
		window.location = "loadMass.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.month="+month+"&statistic.quarter=" + quarter;
	}
	
	function changeUrl2(remark) {
		window.location = "loadMass.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}&statistic.remark=" + remark;
	}

	function showMonthData(month) {
		window.location = "loadMass.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
	}	
	
	function showYearData(year) {
		window.location = "loadMass.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=${statistic.remark}&statistic.quarter=${statistic.quarter}&statistic.year=" + year;
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