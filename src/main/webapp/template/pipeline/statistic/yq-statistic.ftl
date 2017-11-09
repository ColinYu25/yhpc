<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<#assign lineType = ''>
<#if entity?? && entity.lineType??><#assign lineType = entity.lineType></#if>
<div style=" font-size:24px; font-family:黑体;text-align:center;padding:30px 0 10px 0;">宁波市<#if lineType == '0'>中低压燃气管道<#elseif lineType == '1'>石油天然气管道<#elseif lineType == '2'>高压燃气管道<#elseif lineType == '3'>危险化学品管道<#elseif lineType == '4'>港区内油气管道<#else>管道汇总</#if>基本情况统计表</div>
<table cellspacing="1" cellpadding="0" border="0" align="center"  width="99%" class="table_list2">
	<tr height="25" align="center">
  		<td width="15%" height="28" colspan="2" ><strong>区域</strong></td>
	  	<#list areas?if_exists as a>
			<td width="${85/(areas?size+1)}%"><strong>${a.areaName}</strong></td>
		</#list>
		<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td>
	</tr>
	<tr height="25" align="center">
		<td colspan="2"><strong>产权单位数</strong></td>
		<#assign rowTotal = 0/>
		<#list areas?if_exists as a>
		<#assign noneData = "/"/>
		<td>
		<#assign hasData = 'false'/>
		<#list result?if_exists as item>
		<#if item.areaCode?? && a.areaCode == item.areaCode>
			 <#assign noneData = ""/>
			 <#assign rowTotal = rowTotal + item.cqdw/>		
			 ${item.cqdw}
			 <#assign noneData = ""/>
		</#if>
		</#list>
		${noneData}
		</td>
		 </#list>
		 <td>
		${rowTotal}
		</td>
	</tr>	  
	<tr height="25" align="center">
	  <td colspan="2"><strong>使用单位数</strong></td>
	  <#assign rowTotal = 0/>
	  	<#list areas?if_exists as a>
		    <#assign url= 'statistic_companyList.xhtml?lineType=' + lineType + '&areaCode=' + a.areaCode>
		  	<#assign noneData = "/"/>
		  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
		  	<#assign hasData = 'false'/>
		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.sydw/>		
			    	 ${item.sydw}
			    	 <#assign noneData = ""/>
			    </#if>
		    </#list>
	    	${noneData}
	    	</td>
	 	</#list>
	    <#assign url= 'statistic_companyList.xhtml?lineType=' + lineType>
		 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
		    ${rowTotal}
		 </td>
    </tr>
	<tr height="25" align="center">
	  <td colspan="2"><strong>管道数</strong></td>
	  <#assign rowTotal = 0/>
	  	<#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	    <#assign url= 'statistic_pipelineList.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType>
	  	<#assign noneData = "/"/>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.yqgds/>		
			    	 ${item.yqgds}
			    	 <#assign noneData = ""/>
			    </#if>
		    </#list>
		    ${noneData}
	    </td>
	 	</#list>
		<#assign url= 'statistic_pipelineList.xhtml?lineType=' + lineType>
		 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
		    ${rowTotal}
		 </td>
   	</tr>		   
   	<tr>
   		<td rowspan="14"><strong>&nbsp;详&nbsp;<br>&nbsp;细&nbsp;<br>&nbsp;情&nbsp;<br>&nbsp;况&nbsp;</strong></td>
	    <td style="text-align:center"><strong>管道长度(Km)</strong></td>
	  	<#assign rowTotal = 0/>
	  	<#list areas?if_exists as a>
		  	<#assign noneData = "/"/>
		  	<td style="text-align:center">
		  	<#assign hasData = 'false'/>
		  	<#list result?if_exists as item>
			    <#if item.areaCode?? && a.areaCode == item.areaCode>
			    	 <#assign noneData = ""/>
			    	 <#assign rowTotal = rowTotal + item.gdcd/>		
			    	 ${item.gdcd?string("####.##")}
			    	 <#assign noneData = ""/>
			    </#if>
		    </#list>
		    	${noneData}
		    </td>
	 	</#list>
	 	<td style="text-align:center">
	    	${rowTotal?string("####.##")}
	 	</td>
   	</tr>
   	<tr height="25" align="center">
	  <td><strong>未办理规划许可</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
	    	<#assign url= 'statistic_rqPipelineListByIntProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=ghxklx&intPropertyValue=0'>
		<#else>
			<#assign url= 'statistic_yqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=hasGhLicence&propertyValue=false&lineType=' + lineType>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
		<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.blghxk/>		
	    	 ${item.blghxk}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	    </#list>
	 	<#assign url=''>
		<#if lineType == '0'>
	    	<#assign url= 'statistic_rqPipelineListByIntProperty.xhtml?propertyName=ghxklx&intPropertyValue=0'>
		<#else>
			<#assign url= 'statistic_yqPipelineListByProperty.xhtml?propertyName=hasGhLicence&propertyValue=false&lineType=' + lineType>
		</#if>
		 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
		    ${rowTotal}
		 </td>
   	</tr>
   	<tr height="25" align="center">
	  <td ><strong>安全生产许可证</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
			<#assign url = 'javascript:;'>
		<#else>
			<#assign url = 'statistic_yqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=hasScLicence&propertyValue=true&lineType=' + lineType>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
		<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.aqscxk/>		
    		 ${item.aqscxk}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url=''>
	 <#if lineType == '0'>
		<#assign url = 'javascript:;'>
	 <#else>
		<#assign url = 'statistic_yqPipelineListByProperty.xhtml?propertyName=hasScLicence&propertyValue=true&lineType=' + lineType>
	 </#if>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>	
   	<tr height="25" align="center">
	  <td ><strong>未通过竣工验收</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
	    	<#assign url= 'statistic_rqPipelineListByIntProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=jgys&intPropertyValue=0'>
		<#else>
			<#assign url= 'statistic_yqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=hasYs&propertyValue=false&lineType=' + lineType>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.jgys/>		
	    	 ${item.jgys}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url=''>
	 <#if lineType == '0'>
    	<#assign url= 'statistic_rqPipelineListByIntProperty.xhtml?propertyName=jgys&intPropertyValue=0'>
	 <#else>
		<#assign url= 'statistic_yqPipelineListByProperty.xhtml?propertyName=hasYs&propertyValue=false&lineType=' + lineType>
	 </#if>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>	
   	<tr height="25" align="center">
	  <td><strong>未办妥压力管道使用登记</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
	    	<#assign url= 'statistic_rqPipelineListByIntProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=ylgdsydj&intPropertyValue=0'>
		<#else>
			<#assign url= 'statistic_yqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=hasDj&propertyValue=false&lineType=' + lineType>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.sydj/>		
	    	 ${item.sydj}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url=''>
	 <#if lineType == '0'>
    	<#assign url= 'statistic_rqPipelineListByIntProperty.xhtml?propertyName=ylgdsydj&intPropertyValue=0'>
	 <#else>
		<#assign url= 'statistic_yqPipelineListByProperty.xhtml?propertyName=hasDj&propertyValue=false&lineType=' + lineType>
	 </#if>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>	
   	<tr height="25" align="center">
	  <td nowrap><strong>实际路径与竣工图纸不一致数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  <#assign noneData = "/"/>
	  	<td>
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.same/>		
	    	 ${item.same}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <td>
	    ${rowTotal}
	 </td>
   </tr>	
   	<tr height="25" align="center">
	  <td><strong>管道穿越人员密集场所数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
		<#assign url= 'statistic_yqAttechList.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType + '&attechType=1'>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.cyrymjcs/>		
	    	 ${item.cyrymjcs}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url= 'statistic_yqAttechList.xhtml?lineType=' + lineType + '&attechType=1'>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>	
   	<tr height="25" align="center">
	  <td><strong>管道违章占压数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
		<#assign url= 'statistic_yqAttechList.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType + '&attechType=4'>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.wzzy/>		
	    	 ${item.wzzy}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url= 'statistic_yqAttechList.xhtml?&lineType=' + lineType + '&attechType=4'>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>
   	<tr height="25" align="center">
	  <td><strong>涉及隐患数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
	    	<#assign url= 'statistic_rqTroubleList.xhtml?areaCode=' + a.areaCode>
		<#else>
			<#assign url= 'statistic_yqTroubleList.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.troubles/>		
	    	 ${item.troubles}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url=''>
	 <#if lineType == '0'>
    	<#assign url= 'statistic_rqTroubleList.xhtml?areaCode='>
	 <#else>
		<#assign url= 'statistic_yqTroubleList.xhtml?lineType=' + lineType>
	 </#if>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>
   	<tr height="25" align="center">
	  <td><strong>整改需政府协调数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
			<#assign url = 'javascript:;'>
		<#else>
			<#assign url= 'statistic_yqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType + '&attechType=5'>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.zfxt/>		
	    		${item.zfxt}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#if lineType == '0'>
		<#assign url = 'javascript:;'>
	 <#else>
		<#assign url= 'statistic_yqPipelineListByProperty.xhtml?lineType=' + lineType + '&attechType=5'>
	 </#if>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>
   	<tr height="25" align="center">
	  <td><strong>安全防护区内建设施工数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
		<#assign url= 'statistic_yqAttechList.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType + '&attechType=2'>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.fhqsg/>		
	    	 ${item.fhqsg}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url= 'statistic_yqAttechList.xhtml?lineType=' + lineType + '&attechType=2'>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>	
   	<tr height="25" align="center">
	  <td><strong>管道曾发生事故数</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
		<#assign url= 'statistic_yqAttechList.xhtml?areaCode=' + a.areaCode + '&lineType=' + lineType + '&attechType=3'>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.accidents/>		
	    	 ${item.accidents}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	<#assign url= 'statistic_yqAttechList.xhtml?lineType=' + lineType + '&attechType=3'>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>		   			   			   
   <tr height="25" align="center">
	  <td><strong>未定期检验检测</strong></td>
	  <#assign rowTotal = 0/>
	  <#list areas?if_exists as a>
	  	<#assign noneData = "/"/>
	  	<#assign url=''>
		<#if lineType == '0'>
	    	<#assign url= 'statistic_rqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=hasCheck&propertyValue=false'>
		<#else>
			<#assign url= 'statistic_yqPipelineListByProperty.xhtml?areaCode=' + a.areaCode + '&propertyName=hasCheck&propertyValue=false&lineType=' + lineType>
		</#if>
	  	<td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	  	<#assign hasData = 'false'/>
	  	<#list result?if_exists as item>
	    <#if item.areaCode?? && a.areaCode == item.areaCode>
	    	 <#assign noneData = ""/>
	    	 <#assign rowTotal = rowTotal + item.noCheckNum/>		
	    	 ${item.noCheckNum}
	    	 <#assign noneData = ""/>
	    </#if>
	    </#list>
	    ${noneData}
	    </td>
	 </#list>
	 <#assign url=''>
	 <#if lineType == '0'>
    	<#assign url= 'statistic_rqPipelineListByProperty.xhtml?propertyName=hasCheck&propertyValue=false'>
	 <#else>
		<#assign url= 'statistic_yqPipelineListByProperty.xhtml?propertyName=hasCheck&propertyValue=false&lineType=' + lineType>
	 </#if>
	 <td onclick="detail('${url}');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情">
	    ${rowTotal}
	 </td>
   </tr>		   		  		   	   			   			   
</table>
<script lineType="text/javascript">
	var trMoveColor="#cae5ff"; 
	
	function yqPipe(lineType) {
		window.location = "?entity.lineType=" + lineType;
	}
	
	function detail(url) {
		window.location = url ;
	}
</script>
</#escape>
<@fkMacros.pageFooter />