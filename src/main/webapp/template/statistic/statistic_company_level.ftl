<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th height="22">企业分级分类统计表</th>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list2">
		  <tr height="25"  align="center"><th width="8%" height="28" >区域</th>
		  	<#list areas?if_exists as a>
				<th width="5%" nowrap="true" title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
    		</#list>
    		<th width="5%" nowrap="true"><strong>合计</strong></th></tr>
    		 <#list statistics?if_exists as hs>
		  <tr height="25" align="center"><td  width="3%"><strong>${hs.enumName}</strong></td>
		  	<#assign rowTotal=0>
		  	<#list areas?if_exists as a>
			    <td onClick="showDetailRollcall(${a.areaCode},'${hs.enumCode}');" style="cursor:hand;">
			    <#list statistics?if_exists as s>
			    	<#if hs.enumCode==s.enumCode>
			    		<#if a.areaCode==s.areaCode>
			    			${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
			    		</#if>
			    	</#if>
			    </#list>
			    </td>
		    </#list>
		    <td onClick="showDetailRollcall(${area.areaCode},'${hs.enumCode}');"  style="cursor:hand;">
		    ${rowTotal}
		    </td>
		  </tr>
		 <#if hs_index==(statistics?size/areas?size-1)>
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
				window.location = "?area.areaCode="+areaCode;
			}
		}
		
		function showDetailRollcall(areaCode,enumCode) {
			window.location = "loadCompanyLevelPointList.xhtml?area.areaCode="+areaCode+"&statistic.enumCode="+enumCode;
		}
	</script>
</#escape>
<@fkMacros.pageFooter />