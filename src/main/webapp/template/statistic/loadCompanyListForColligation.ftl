<@fkMacros.pageHeaderStatistic />
<#escape x as (x)!>
<#if statistic?? && statistic.remark??>
	<#if statistic.remark=='anjian'> 
		<#assign typeName='安监'>
	<#elseif statistic.remark=='wenguang'>
		<#assign typeName='文广'>
	<#elseif statistic.remark=='jiaotong'>
		<#assign typeName='交通'>
	<#elseif statistic.remark=='maoyi'>
		<#assign typeName='商务'>
	<#elseif statistic.remark=='chengguan'>
		<#assign typeName='城管'>
	<#elseif statistic.remark=='haiyang'>
		<#assign typeName='海洋渔业'>
	<#elseif statistic.remark=='shuili'>
		<#assign typeName='水利'>
	<#elseif statistic.remark=='lvyou'>
		<#assign typeName='旅游'>
	<#elseif statistic.remark=='jiaoyu'>
		<#assign typeName='教育'>
	<#elseif statistic.remark=='weisheng'>
		<#assign typeName='卫生'>
	<#elseif statistic.remark=='minzong'>
		<#assign typeName='民宗'>
	<#elseif statistic.remark=='dianli'>
		<#assign typeName='电业'>
	</#if>
</#if>
<link rel="stylesheet" type="text/css" href="${resourcePath}/css/public.css" />
<link rel="stylesheet" type="text/css" href="${resourcePath}/css/other.css" />
<div class="sub_main">
<div id="main"  style="height:700px;">
	<div class="main_content padd10">
	<div id="main_content">
	<div id="proposal-review" style="margin-left:0px;"><#--Jquery + json + jTemplate-->
	<textarea id="proposal-template" style="display:none">		
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main_tab">
	  <tr  class="tit" id="tr_area" ><td >监管/ 区域 </td>
	   <td>${area.areaName}</td>
	  	<#list areas?if_exists as a>
			<td> 
				<#if areaLevel='firstArea'>
					<a href="loadCompanyListForColligation.xhtml?area.areaCode=${a.areaCode}&area.areaName=${a.areaName}&areaLevel=secondArea">${a.areaName}</a>
				<#else>
					${a.areaName}
				</#if>
			</td>
		</#list>
		<#--td>其他</td-->
	  </tr>
	{#foreach $T.returnMap.mapList as record}
		{#if $T.record.type != 'alltype' && $T.record.type != 'qit' }
			<tr>
		  		<td class="font" >{$T.record.typeName}</td>
		  		<td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>firstArea<#else>secondArea</#if>' ,${area.areaCode}, '${area.areaName}', '{$T.record.type}');">
					{#if $T.record.row_sum > ''}
						{$T.record.row_sum}
					{#else}
						 0
					{#/for} 
				</td>
				<#list areas?if_exists as a>
					<td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>secondArea<#else>thirdArea</#if>' ,${a.areaCode}, '${a.areaName}', '{$T.record.type}');">
						{#if $T.record.area_${a.areaCode} > ''}
							{$T.record.area_${a.areaCode}}
						{#else}
							 0
						{#/for} 
					</td>
				</#list>
				<#--td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>firstArea<#else>secondArea</#if>' ,${area.areaCode}, '${area.areaName}', '{$T.record.type}');">
					{#if $T.record.area_${area.areaCode} > ''}
						{$T.record.area_${area.areaCode}}
					{#else}
						 0
					{#/for} 
				</td-->
			</tr>
		{#/if}
	{#/for} 
	<!--其他-->
	{#foreach $T.returnMap.mapList as record}
		{#if $T.record.type == 'qit'}
			<tr>
		  		<td class="font" >其他</td>
		  		<td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>firstArea<#else>secondArea</#if>' ,${area.areaCode}, '${area.areaName}', '{$T.record.type}');">
					{#if $T.record.row_sum > ''}
						{$T.record.row_sum}
					{#else}
						 0
					{#/for} 
				</td>
				<#list areas?if_exists as a>
					<td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>secondArea<#else>thirdArea</#if>' ,${a.areaCode}, '${a.areaName}', '{$T.record.type}');">
						{#if $T.record.area_${a.areaCode} > ''}
							{$T.record.area_${a.areaCode}}
						{#else}
							 0
						{#/for} 
					</td>
				</#list>
				<#-->td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'><#else>secondArea</#if>' ,${area.areaCode}, '${area.areaName}', '{$T.record.type}');">
					{#if $T.record.area_${area.areaCode} > ''}
						{$T.record.area_${area.areaCode}}
					{#else}
						 0
					{#/for} 
				</td-->
			</tr>
		{#/if}
	{#/for} 
	<!--合计-->
	{#foreach $T.returnMap.mapList as record}
		{#if $T.record.type == 'alltype'}
			<tr>
		  		<td class="font" >合计</td>
		  		<td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>firstArea<#else>secondArea</#if>' ,${area.areaCode}, '${area.areaName}', '{$T.record.type}');">
					{#if $T.record.row_sum > ''}
						{$T.record.row_sum}
					{#else}
						 0
					{#/for} 
				</td>
				<#list areas?if_exists as a>
					<td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'>secondArea<#else>thirdArea</#if>' ,${a.areaCode}, '${a.areaName}', '{$T.record.type}');">
						{#if $T.record.area_${a.areaCode} > ''}
							{$T.record.area_${a.areaCode}}
						{#else}
							 0
						{#/for} 
					</td>
				</#list>
				<#-->td style="cursor:hand;" onClick="showDetails('<#if areaLevel='firstArea'><#else>secondArea</#if>' ,${area.areaCode}, '${area.areaName}', '{$T.record.type}');">
					{#if $T.record.area_${area.areaCode} > ''}
						{$T.record.area_${area.areaCode}}
					{#else}
						 0
					{#/for} 
				</td-->
			</tr>
		{#/if}
	{#/for} 
    </table>
	</textarea>
</div><#--Jquery + json + jTemplate 结束-->
</div>
<script type="text/javascript">
	jQuery(document).ready(function(){
		chartRedraw1();
	});

	function showDetails(areaLevel, areaCode, areaName, type){
		window.location = "statistic/nosecuritycheck/loadCompanysForColligationShowDetails.xhtml?areaLevel="+areaLevel+"&area.areaName="+areaName+"&area.areaCode="+areaCode+"&type1="+type;
	}		
	
	function chartRedraw1(){
		jQuery.ajax({type : "post", method : "post", dataType : "json",
			data:{"area.areaCode": ${area.areaCode},"areaLevel": '${areaLevel}' },
			url : "${contextPath}/statistic/nosecuritycheck/loadCompanyListForColligationJson.xhtml",
			success : function(data) {//异步获取的数据
				if(data){
					jQuery("#proposal-review").setTemplateElement("proposal-template");//初始化Template
					jQuery("#proposal-review").processTemplate(data);//渲染box
				}else{
					alert("统计报表数据出错！");
				}
			}
		});	
	}
 </script>
</#escape>
<@fkMacros.pageFooter />