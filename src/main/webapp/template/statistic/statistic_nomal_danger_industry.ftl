<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th height="22">
	<#list statistics?if_exists as s>
	  	 <#assign allParam=s.sonNumber1+s.sonNumber2+3>
	<input type="text" id="year" value="${statistic.year}年" onchange="changeQUrl();" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
	${s.enumName}一般隐患整治统计表</th>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list2">
		  <tr height="25"  align="center"><th width="20%" height="28" colspan="${s.sonNumber1+s.sonNumber2+2}"><#if s_index==0><#break></#if></#list>区域</th>
		  	<#list areas?if_exists as a>
				<th width="5%" nowrap="true" title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
    		</#list>
    		<th width="5%" nowrap="true"><strong>合计</strong></th></tr>
    		  <#list statistics?if_exists as hy>
			  <#list 0..2 as i>
			  <tr height="25" <#if hy_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#f0f0f0"</#if> align="center">
			  	<#if i_index==0>
			  		<#if ((hy.sonNumber1+hy.sonNumber2)>0)>
			  		<#assign colParam=hy.sonNumber1+hy.sonNumber2>
			  		<td rowspan="${hy.sonCount*3}" width="3%"><strong>
			  			<#if (hy.enumName?length > 5)>
			  				<#list 0..1 as h2>${hy.enumName?substring(h2_index * hy.enumName?length/2,(h2_index+1)*hy.enumName?length/2)}<br></#list>
			  			<#else>
			  				<#list 0..hy.enumName?length-1 as hl>${hy.enumName?substring(hl_index,hl_index+1)}<br></#list>
			  			</#if>
			  		</strong></td>
			  		<td rowspan="3" colspan="${colParam}"><strong>合计</strong></td>
			  		<#else><#if ((hy.sonNumber1+hy.sonNumber2)==0 && hy_index=7)><#assign colParam=colParam+1></#if><td rowspan="3" <#if colParam?exists>colspan="${colParam}"<#else>colspan="1"</#if>><strong>${hy.enumName}</strong></td>
			  		</#if>
				  	<td nowrap="true" width="6%"><strong>隐患数</strong></td><#assign isGorver=''>
			  	<#elseif i_index==1>
			  		<td nowrap="true" width="6%"><strong>未整改数</strong></td><#assign isGorver='0'>
			  	<#elseif i_index==2>
			  		<td nowrap="true" width="6%"><strong>整改率</strong></td>
			  	</#if>
			  	<#assign rowTotal=0>
			  	<#list areas?if_exists as a>
			    <td <#if i_index!=2>onClick="showDetail(${a.areaCode},'${hy.industryId}','${isGorver}');"  style="cursor:hand;"</#if>>
			    <#list statistics?if_exists as s>
			    <#if hy.industryId==s.industryId><#if a.areaCode==s.areaCode>
			    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
			    <#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
			    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if></#if></#if></#if></#list></td>
			    </#list>
			    <td <#if i_index!=2>onClick="showDetail(${area.areaCode},'${hy.industryId}','${isGorver}');"  style="cursor:hand;"</#if>>
			    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
			    <#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
			    <#elseif i_index==2><#if inhere==0>/<#else>#{((inhere-number)/inhere*100);M1}%</#if></#if></td>
			  </tr>
			 </#list>
			 <#if hy_index==(statistics?size/areas?size-1)>
			 <#break>
			 </#if>
			 </#list>
		  </tr>	  
	</table>
	<script type="text/javascript">
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "?area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4);
			}
		}
		
		function showDetail(areaCode,industryId,isGorver) {
			window.location = "loadNomalDangerByIndustryList.xhtml?statistic.year=${statistic.year}&area.areaCode="+areaCode+"&statistic.industryId="+industryId+"&statistic.isGorver="+isGorver;
		}
		function changeQUrl() {
			window.location = "?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4);
		}
	</script>
</#escape>
<@fkMacros.pageFooter />