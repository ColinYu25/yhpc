<@fkMacros.pageHeaderPrint />
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.7.1.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.jtemplates.js">
</script><script>jQuery.noConflict();</script>
	<div style=" font-size:24px; font-family:黑体;text-align:center;padding:30px 0 10px 0;">
		<input type="text" id="year" value="${statistic.year}" onChange="changeYearUrl(this.value);" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="4" maxlength="4">年
		<select name="statistic.type" onChange="changeTypeUrl(this.value);"> 
			<option value="-1">--请选择--</option>
			<option value="1" <#if statistic?? && statistic.type?? && statistic.type == 1>selected</#if>>石油天然气管道</option> 
	    	<option value="2" <#if statistic?? && statistic.type?? && statistic.type == 2>selected</#if>>城镇燃气管道</option> 
	    	<option value="3" <#if statistic?? && statistic.type?? && statistic.type == 3>selected</#if>>危险化学品管道</option> 
	    	<option value="4" <#if statistic?? && statistic.type?? && statistic.type == 4>selected</#if>>港区内油气管道</option> 
	    	<option value="0" <#if statistic?? && statistic.type?? && statistic.type == 0>selected</#if>>中低压燃气管道</option>  
		</select>
		${statistic.areaName}重大隐患统计表
	</div>
		<div id="proposal-review" style="margin-left:0px;"><#--Jquery + json + jTemplate-->
			<textarea id="proposal-template" style="display:none">	
				<table cellspacing="1" cellpadding="0" border="0" align="center"  width="99%" class="table_list2">
				 	<tr align="center">
				 		<td height="22" colspan="2"><strong>区域</td>
					 	<#if areas?exists>
				  			<#list areas?if_exists as a><td width="${90/(areas?size + 1)}%" <#if statistic?? && statistic.areaType?? && statistic.areaType=='second_area'>style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看${a.areaName}统计" onClick="changeAreaUrl(${a.areaCode});"</#if>><strong>${a.areaName}</strong></td></#list>
			  		 	</#if>
		  		 		<td width="${90/(areas?size + 1)}%"><strong>合计</strong></td>
				  	</tr>
				  	<tr align="center">
				  		<td height="22" rowspan="3" nowrap><strong>合计&nbsp;</strong></td>
				  		<td><strong>隐&nbsp;&nbsp;患&nbsp;&nbsp;数</strong></td>
					  	<#if areas?exists>
				  			<#list areas?if_exists as a><td height="22" onclick="showAreaDetail('', '${a.areaCode}', '');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.s_${a.areaCode}_t}</td></#list>
			  		 		<td onclick="showAreaDetail('', '', '');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.s_${a.areaCode}_t + </#list> + 0}</td>
			  		 	</#if>
		  		 	</tr>
		  		 	<tr align="center">
				  		<td><strong>未&nbsp;&nbsp;整&nbsp;&nbsp;改</strong></td>
			  		 	<#if areas?exists>
				  			<#list areas?if_exists as a><td height="22" onclick="showAreaDetail('', '${a.areaCode}', '1');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.s_${a.areaCode}_t - $T.s_${a.areaCode}_y}</td></#list>
			  		 		<td onclick="showAreaDetail('', '', '1');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>($T.s_${a.areaCode}_t - $T.s_${a.areaCode}_y) + </#list> + 0}</td>
			  		 	</#if>
		  		 	</tr>
		  		 	<tr align="center">
				  		<td nowrap><strong>整改率（%）</strong></td>
			  		 	<#if areas?exists>
						  	<#list areas?if_exists as a><td height="22">{#if $T.s_${a.areaCode}_t != 0} {(100 * $T.s_${a.areaCode}_y / $T.s_${a.areaCode}_t).Fixed(2, true)} {#else} 0/0 {#/if}</td></#list>
						  	<td>{#if (<#list areas?if_exists as a>$T.s_${a.areaCode}_t + </#list> + 0) != 0} {(100*(<#list areas?if_exists as a>$T.s_${a.areaCode}_y + </#list> + 0) / (<#list areas?if_exists as a>$T.s_${a.areaCode}_t + </#list> + 0)).Fixed(2)} {#else} 0/0 {#/if}</td>
			  		 	</#if>
		  		 	</tr>
				  	{#foreach $T.mapList as record}
					  	<tr align="center">
					  		<td height="22" rowspan="3" nowrap><strong>{$T.record.s_tName}&nbsp;</strong></td>
					  		<td><strong>隐&nbsp;&nbsp;患&nbsp;&nbsp;数</strong></td>
						  	<#if areas?exists>
					  			<#list areas?if_exists as a><td height="22" onclick="showAreaDetail('{$T.record.s_tName}', '${a.areaCode}', '');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.record.s_${a.areaCode}_t}</td></#list>
				  		 		<td onclick="showAreaDetail('{$T.record.s_tName}', '', '');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>$T.record.s_${a.areaCode}_t + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr align="center">
					  		<td><strong>未&nbsp;&nbsp;整&nbsp;&nbsp;改</strong></td>
				  		 	<#if areas?exists>
					  			<#list areas?if_exists as a><td height="22" onclick="showAreaDetail('{$T.record.s_tName}', '${a.areaCode}', '1');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{$T.record.s_${a.areaCode}_t - $T.record.s_${a.areaCode}_y}</td></#list>
				  		 		<td onclick="showAreaDetail('{$T.record.s_tName}', '', '1');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">{<#list areas?if_exists as a>($T.record.s_${a.areaCode}_t - $T.record.s_${a.areaCode}_y) + </#list> + 0}</td>
				  		 	</#if>
			  		 	</tr>
			  		 	<tr align="center">
					  		<td><strong>整改率（%）</strong></td>
				  		 	<#if areas?exists>
							  	<#list areas?if_exists as a><td height="22">{#if $T.record.s_${a.areaCode}_t != 0} {(100 * $T.record.s_${a.areaCode}_y / $T.record.s_${a.areaCode}_t).Fixed(2, true)} {#else} 0/0 {#/if}</td></#list>
							  	<td>{#if (<#list areas?if_exists as a>$T.record.s_${a.areaCode}_t + </#list> + 0) != 0} {(100*(<#list areas?if_exists as a>$T.record.s_${a.areaCode}_y + </#list> + 0) / (<#list areas?if_exists as a>$T.record.s_${a.areaCode}_t + </#list> + 0)).Fixed(2)} {#else} 0/0 {#/if}</td>
				  		 	</#if>
			  		 	</tr>
				  	{#/for} 
				</table>
				</br>
			</textarea>
		</div>
<script>
	var trMoveColor="#cae5ff"; 
	
	jQuery("#proposal-review").setTemplateElement("proposal-template");//初始化Template
	jQuery("#proposal-review").processTemplate(${jdata});//渲染box
	
	function changeYearUrl(year) {
		window.location = "loadDanger.xhtml?statistic.type=${statistic.type}&statistic.year="+year+"&statistic.areaCode=${statistic.areaCode}";
	}
	
	function changeTypeUrl(type) {
		window.location = "loadDanger.xhtml?statistic.type="+type+"&statistic.year=${statistic.year}&statistic.areaCode=${statistic.areaCode}";
	}
	
	function changeAreaUrl(areaCode) {
		window.location = "loadDanger.xhtml?statistic.type=${statistic.type}&statistic.year=${statistic.year}&statistic.areaCode=" + areaCode;
	}
	
	function showAreaDetail(dType, areaCode, repaired) {
		window.location = "loadDangerDetail.xhtml?statistic.dType="+dType+"&statistic.repaired="+repaired+"&statistic.type=${statistic.type}&statistic.secondArea=${statistic.areaCode}&statistic.areaType=${statistic.areaType}&statistic.year=${statistic.year}&statistic.areaCode=" + areaCode;
	}
</script>
<@fkMacros.pageFooter />