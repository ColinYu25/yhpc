<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th height="22">
	<#list statistics?if_exists as s>
	  	 <#assign allParam=s.sonNumber1+s.sonNumber2+3>
	<input type="text" id="year" value="${statistic.year}年" onchange="changeQUrl();" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
	已抄告和已接收隐患统计表</th>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list2">
		  	  <tr height="25"  align="center"><th width="12%" height="28" colspan="${s.sonNumber1+s.sonNumber2 + 2}"><#if s_index==0><#break></#if></#list>区域</th>
			  	<#list areas?if_exists as a>
					<th width="5%" nowrap="true" title="点击查看该区域统计"  style="cursor:hand;" onClick=""><strong>${a.areaName}</strong></td>
	    		</#list>
	    		<th width="5%" nowrap="true"><strong>合计</strong></th>
    		  </tr>

    		
			  <#list 0..2 as i>
			  <tr height="25" bgcolor="#ffffff" align="center">
			  	<#if i_index==0>
			  		<td rowspan="3" width="5%"><strong>已抄告</strong></td>
			  	    <td nowrap="true" width="9%"><strong>隐患数</strong></td><#assign isGorver='0'>
			  	<#elseif i_index==1>
			  		<td nowrap="true" width="9%"><strong>未回复数</strong></td><#assign isGorver='1'>
			  	<#elseif i_index==2>
			  		<td nowrap="true" width="9%"><strong>未反馈数</strong></td><#assign isGorver='2'>
			  	</#if>
			  	<#assign rowTotal=0>
			  	
			  	<#list areas?if_exists as a>
			  		<td <#if i_index==1>
			  				style="cursor:hand;" onClick="showDetail('${a.areaCode}',${isGorver},'${area.areaCode}')"
			  				<#elseif i_index==2>
			  					style="cursor:hand;" onClick="showDetail('${a.areaCode}',${isGorver},'${area.areaCode}')"
			  				<#elseif i_index==0>
			  					style="cursor:hand;" onClick="showDetail('${a.areaCode}',${isGorver},'${area.areaCode}')"
			  			</#if>
			  		>
				  		<#list statistics?if_exists as s>
				  			<#if s_index < (statistics.size() / 2)>
					  			<#if a.areaCode==s.areaCode>
					    			<#if i_index==0>${s.total}<#assign rowTotal=rowTotal+s.total>
					    				<#elseif i_index==1>${s.total-s.inhere}<#assign rowTotal=rowTotal+s.total - s.inhere>
					    				<#elseif i_index==2>${s.total-s.number}<#assign rowTotal=rowTotal + s.total - s.number>
					    			</#if>
				    			</#if>
			    			</#if>
				  		</#list>
			  		</td>
			  	</#list>
			    	<td 
			    		<#if i_index==1>
				  				style="cursor:hand;" onClick="showDetailTotal(${isGorver},'${area.areaCode}')"
				  				<#elseif i_index==2>
				  					style="cursor:hand;" onClick="showDetailTotal(${isGorver},'${area.areaCode}')"
				  				<#elseif i_index==0>
				  					style="cursor:hand;" onClick="showDetailTotal(${isGorver},'${area.areaCode}')"
				  			</#if>
			    	>
			    		<#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
					    	<#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
					    	<#elseif i_index==2>${rowTotal}<#assign total=rowTotal>
				    	</#if>
			    	</td>
			    
			  </tr>
			 </#list>
			 
			 <#list 0..2 as i>
			  <tr height="25" bgcolor="#f0f0f0" align="center">
			  		<#if i_index==0>
			  		<td rowspan="3" width="5%"><strong>已接收</strong></td>
			  	    <td nowrap="true" width="5%"><strong>隐患数</strong></td><#assign isGorver='3'>
			  	<#elseif i_index==1>
			  		<td nowrap="true" width="5%"><strong>未回复数</strong></td><#assign isGorver='4'>
			  	<#elseif i_index==2>
			  		<td nowrap="true" width="5%"><strong>未反馈数</strong></td><#assign isGorver='5'>
			  	</#if>
			  	<#assign rowTotal=0>
			  	
			  	<#list areas?if_exists as a>
			  		<td 
			  			<#if i_index==1>
			  				style="cursor:hand;" onClick="showDetail('${a.areaCode}',${isGorver},'${area.areaCode}')"
			  				<#elseif i_index==2>
			  					style="cursor:hand;" onClick="showDetail('${a.areaCode}',${isGorver},'${area.areaCode}')"
			  				<#elseif i_index==0>
			  					style="cursor:hand;" onClick="showDetail('${a.areaCode}',${isGorver},'${area.areaCode}')"
			  			</#if>
			  		>
				  		<#list statistics?if_exists as s>
				  			<#if (statistics.size()/2-1) < s_index >
					  			<#if a.areaCode==s.areaCode>
					    			<#if i_index==0>${s.total}<#assign rowTotal=rowTotal+s.total>
					    				<#elseif i_index==1>${s.total-s.inhere}<#assign rowTotal=rowTotal+s.total - s.inhere>
					    				<#elseif i_index==2>${s.total-s.number}<#assign rowTotal=rowTotal + s.total - s.number>
					    			</#if>
				    			</#if>
			    			</#if>
				  		</#list>
			  		</td>
			  	</#list>
			    <td 
			    	<#if i_index==1>
				  				style="cursor:hand;" onClick="showDetailTotal(${isGorver},'${area.areaCode}')"
				  				<#elseif i_index==2>
				  					style="cursor:hand;" onClick="showDetailTotal(${isGorver},'${area.areaCode}')"
				  				<#elseif i_index==0>
				  					style="cursor:hand;" onClick="showDetailTotal(${isGorver},'${area.areaCode}')"
				  		</#if>
			    >
		    		<#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
				    	<#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
				    	<#elseif i_index==2>${rowTotal}
			    	</#if>
			    </td>
			  </tr>
			 </#list>
	</table>
	<script type="text/javascript">
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "?area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4);
			}
		}
		function showDetailTotal(type,code){
			//alert(type);
			if(type=="0"){
				window.location = "loadTroubleDownByUserThirdTotal.xhtml?statistic.year=${statistic.year}&area.areaCode=" + code;
			}
			if(type=="1"){
				window.location = "loadTroubleDownNoBackByUserThirdTotal.xhtml?statistic.year=${statistic.year}&area.areaCode=" + code;
			}
			if(type=="3"){
				window.location = "loadTroubleUpByUserThirdTotal.xhtml?statistic.year=${statistic.year}&area.areaCode=" + code;
			}
			if(type=="2"){
				window.location = "loadTroubleDownNoResultByUserThirdTotal.xhtml?statistic.year=${statistic.year}&area.areaCode=" + code;
			}
			if(type=="4"){
				window.location = "loadTroubleUpNoBackByUserThirdTotal.xhtml?statistic.year=${statistic.year}&area.areaCode=" + code;
			}
			if(type=="5"){
				window.location = "loadTroubleUpNoResultByUserThirdTotal.xhtml?statistic.year=${statistic.year}&area.areaCode=" + code;
			}
		}
		function showDetail(areaCode,type,code) {
			//alert("xx");
			//alert(type);
			//alert(code);
			if(type == "0"){
				window.location = "loadTroubleDownByUser.xhtml?statistic.year=${statistic.year}&areaThird.areaCode="+areaCode + "&area.areaCode="+code;
			}
			if(type=="1"){
				window.location = "loadTroubleDownNoBackByUser.xhtml?statistic.year=${statistic.year}&areaThird.areaCode="+areaCode+ "&area.areaCode="+code;
			}
			if(type=="3"){
				window.location = "loadTroubleUpByUser.xhtml?statistic.year=${statistic.year}&areaThird.areaCode="+areaCode+ "&area.areaCode="+code;
			}
			if(type=="2"){
				window.location = "loadTroubleDownNoResultByUser.xhtml?statistic.year=${statistic.year}&areaThird.areaCode="+areaCode+ "&area.areaCode="+code;
			}
			if(type=="4"){
				window.location = "loadTroubleUpNoBackByUser.xhtml?statistic.year=${statistic.year}&areaThird.areaCode="+areaCode+ "&area.areaCode="+code;
			}
			if(type=="5"){
				window.location = "loadTroubleUpNoResultByUser.xhtml?statistic.year=${statistic.year}&areaThird.areaCode="+areaCode+ "&area.areaCode="+code;
			}
		}

		function changeQUrl() {
			window.location = "loadTroubleUpAndDownByUserThird.xhtml?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4);
		}
	</script>
</#escape>
<@fkMacros.pageFooter />