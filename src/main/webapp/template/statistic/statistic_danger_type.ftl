<#include "statistic_head.ftl">
	<div id="content">
	<table width="100%" cellspacing="1" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 12px; color: #4F6B72;; background-color:#C1DAD7">
    <tbody>
         <#list statistics?if_exists as s>
	  	 <#assign allParam=s.sonNumber1+s.sonNumber2+2>
          <tr><th height="25" bgcolor="#D6EEF0" align="center" colspan="${areas?size+allParam}"><span class="head">${s.enumName}</span></th></tr>
		  <tr height="25" bgcolor="#dcfafa" align="center"><th width="20%" height="28" colspan="${s.sonNumber1+s.sonNumber2+1}"><#if s_index==0><#break></#if></#list>区域</th>
		  	<#list areas?if_exists as a>
				<th width="5%" nowrap="true" title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
    		</#list>
    		<th nowrap="true" width="5%"><strong>合计</strong></th></tr>
    		  <#list statistics?if_exists as hy>
			  <#list 0..2 as i>
			  <tr height="25" <#if hy_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#dcfafa"</#if> align="center">
			  	<#if i_index==0>
			  	<#assign isSon=0>
			  		<#if ((hy.sonNumber1+hy.sonNumber2)>0)>
			  		<#assign isSon=1>
			  		<#assign colParam=hy.sonNumber1+hy.sonNumber2>
			  		<#if hy_index!=0>
			  		<th rowspan="${hy.sonCount*3}" width="3%"><strong>
			  			<#list 0..hy.enumName?length-1 as hl>${hy.enumName?substring(hl_index,hl_index+1)}<br></#list>
			  			<#assign industry='${hy.industryId}'>
			  		</strong></th>
			  		<#else>
			  			<#assign industry='all'>
			  		</#if>
			  		<th rowspan="3" colspan="${colParam}"><strong>合计</strong></th>
			  		<#else><th rowspan="3" <#if colParam?exists>colspan="${colParam}"<#else>colspan="1"</#if>><strong>${hy.enumName}</strong></th>
			  		</#if>
				  	<th nowrap="true" width="8%"><strong>隐 患 数</strong></th><#assign isGorver='1'>
			  	<#elseif i_index==1>
			  		<th nowrap="true" width="8%"><strong>未整改数</strong></th><#assign isGorver='0'>
			  	<#elseif i_index==2>
			  		<th nowrap="true" width="8%"><strong>整 改 率</strong></th>
			  	</#if>
			  	<#assign rowTotal=0>
			  	<#list areas?if_exists as a>
			  	<#if isSon==1>
			  		<#if i_index==0>
				  		<#assign idvalue='id=danger_${industry}_${a.areaCode} name=danger_${industry}_${a.areaCode}'>
				  	<#elseif i_index==1>
				  		<#assign idvalue='id=notgorver_${industry}_${a.areaCode} name=notgorver_${industry}_${a.areaCode}'>
				  	<#elseif i_index==2>
				  		<#assign idvalue='id=gorver_${industry}_${a.areaCode} name=gorver_${industry}_${a.areaCode}'>
				  	</#if>
			  	<#else>
				  	<#if i_index==0>
				  		<#assign idvalue='id=z_danger_${industry}_${a.areaCode} name=z_danger_${industry}_${a.areaCode}'>
				  	<#elseif i_index==1>
				  		<#assign idvalue='id=z_notgorver_${industry}_${a.areaCode} name=z_notgorver_${industry}_${a.areaCode}'>
				  	<#elseif i_index==2>
				  		<#assign idvalue='id=z_gorver_${industry}_${a.areaCode} name=z_gorver_${industry}_${a.areaCode}'>
				  	</#if>
				  </#if>
				  <#if isSon==1>
			  		<#if i_index==0>
				  		<#assign allidvalue='id=danger_${industry} name=danger_${industry}'>
				  	<#elseif i_index==1>
				  		<#assign allidvalue='id=notgorver_${industry} name=notgorver_${industry}'>
				  	<#elseif i_index==2>
				  		<#assign allidvalue='id=gorver_${industry} name=gorver_${industry}'>
				  	</#if>
			  	<#else>
				  	<#if i_index==0>
				  		<#assign allidvalue='id=z_danger_${industry} name=z_danger_${industry}'>
				  	<#elseif i_index==1>
				  		<#assign allidvalue='id=z_notgorver_${industry} name=z_notgorver_${industry}'>
				  	<#elseif i_index==2>
				  		<#assign allidvalue='id=z_gorver_${industry} name=z_gorver_${industry}'>
				  	</#if>
				  </#if>
			    <td ${idvalue} <#if i_index!=2>onClick="showDetail(${a.areaCode},'${hy.industryId}','${isGorver}');"  style="cursor:pointer;"</#if>>
			    <#list statistics?if_exists as s>
			    <#if hy.industryId==s.industryId><#if a.areaCode==s.areaCode>
			    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
			    <#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
			    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if></#if></#if></#if></#list></td>
			    </#list>
			    <td ${allidvalue} <#if i_index!=2>onClick="showDetail(${area.areaCode},'${hy.industryId}','${isGorver}');"  style="cursor:pointer;"</#if>>
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
		<tr height="25" bgcolor="#D6EEF0" align="center">
            <td height="20" colspan="${areas?size+allParam}"></td>
            </tr>
        </tbody></table>
	</div>
	<script type="text/javascript">
		function get(obj) {
			return document.getElementById(obj);
		}
		function getName(obj) {
			return document.getElementsByName(obj);
		}
		function total() {
			if(getName("z_"+arguments[0]).length != undefined) {
				var td_value = 0 ;
				for(var i = 0 ; i < getName("z_"+arguments[0]).length ; i ++) {
					td_value = parseInt(td_value) + parseInt(getName("z_"+arguments[0])[i].innerHTML);
				}
				get(arguments[0]).innerHTML = td_value;
			}else {
				get(arguments[0]).innerHTML = get("z_"+arguments[0]).innerHTML;
			}
		}
		function totalRate() {
			var inhere = get("danger_"+arguments[0]);
			var number = get("notgorver_"+arguments[0]);
			var rate = get("gorver_"+arguments[0])
			if(inhere != null && number != null && inhere != undefined && number != undefined) {
				if(parseInt(inhere.innerHTML)==0){
					rate.innerHTML = "/";
				}else{
					var value = (parseInt(inhere.innerHTML)-parseInt(number.innerHTML))/parseInt(inhere.innerHTML)*100;
					if(value.toString().indexOf(".") != -1){
						value = Math.round (value*Math.pow(10,1))/Math.pow(10,1); 
					}
					rate.innerHTML = value+"%";
				}
			}else{
				rate.innerHTML = "/";
			}
		}
		function totalAll() {
			if(arguments[0] != null){
				get("danger_all"+arguments[0]).innerHTML = parseInt(get("danger_1230"+arguments[0]).innerHTML)+parseInt(get("danger_1262"+arguments[0]).innerHTML);
				get("notgorver_all"+arguments[0]).innerHTML = parseInt(get("notgorver_1230"+arguments[0]).innerHTML)+parseInt(get("notgorver_1262"+arguments[0]).innerHTML);
			}else{
				get("danger_all").innerHTML = parseInt(get("danger_1230").innerHTML)+parseInt(get("danger_1262").innerHTML);
				get("notgorver_all").innerHTML = parseInt(get("notgorver_1230").innerHTML)+parseInt(get("notgorver_1262").innerHTML);
			}
		}
		<#list areas?if_exists as a>
			total('danger_all_${a.areaCode}');
			total('notgorver_all_${a.areaCode}');
			totalRate('all_${a.areaCode}');
		</#list>
		total('danger_all');
		total('notgorver_all');
		totalRate('all');
		
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "loadDangerTypeByIndustry.xhtml?remark=${remark}&area.areaCode="+areaCode;
			}
		}
		function showDetail(areaCode,industryId,isGorver) {
			window.location = "loadDangerTypeByIndustryList.xhtml?area.areaCode="+areaCode+"&statistic.industryId="+industryId+"&statistic.isGorver="+isGorver;
		}
	</script>
	<#include "statistic_foot.ftl">