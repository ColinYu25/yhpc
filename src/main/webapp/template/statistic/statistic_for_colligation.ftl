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
<style type="text/css">
	.T_h1 {color: #245d8a; font-size: 24px; font-weight: bold}
	.T_line { border:solid 1px #99bbe8; border-collapse:collapse; font-size:13px; }
	.T_line td,.T_line th{ text-align:center;padding:5px 3px;border:solid 1px #ccc; border-collapse:collapse; border-top:none}
	.T_line th{ color:#416aa3; background:url(${resourcePath}/images/img07.jpg) left bottom repeat-x;padding:8px 5px; border-color:#99bbe8}
	.T_line th a:link,.T_line th a:visited { color:#416aa3;}
	.T_line th a:hover { color:#FF0000;}
	.font{ font-weight:bold;color:#416aa3; }<!--background:#e0ecff;-->
	.font2{ color:#333;}
</style>

<body>
	
<div class="sub_main" >
<div id="main"  style="height:750px;">
<!--<div class="list_title_blue" >		${statistic.year}年宁波市隐患排查整治情况表
</div>-->

	<div class="main_content padd10">
	<div id="main_content">	
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main_tab">
	  <tr  class="tit" id="tr_area"  ><td   colspan="3">区域</td>

	  	<#list areas?if_exists as a>
			<td title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')">${a.areaName}</td>
		</#list>
		<td>合计</td>
	  </tr>
	  
		<#list 0..6 as i>
			  <tr height="25" <#if i_index==0 || i_index==1 || i_index==2>bgcolor="#ffffff"<#else>bgcolor="#f1f4f9"</#if> align="center">
			  <#if i_index==0><td rowspan="3" colspan="2" class="font">一般<br/>隐患</td><td widtd="6%" nowrap="true" class="font2">隐 患 数</td><#assign ptype='normal'><#assign isGorver=''>
			  <#elseif i_index==1><td widtd="6%" nowrap="true" class="font2">未整改数</td><#assign ptype='normal'><#assign isGorver='0'>
			  <#elseif i_index==2><td widtd="6%" nowrap="true" class="font2">整 改 率</td>
			  <#elseif i_index==3><td rowspan="4" colspan="2" class="font">重<br>大<br>隐<br>患</td><td widtd="6%" nowrap="true" class="font2">隐 患 数</td><#assign ptype='danger'><#assign isGorver=''>
			  <#elseif i_index==4><td widtd="6%" nowrap="true" class="font2">未整改数</td><#assign ptype='danger'><#assign isGorver='0'>
			  <#elseif i_index==5><td widtd="6%" nowrap="true" class="font2">到期未整改数</td><#assign ptype='danger'><#assign isGorver='2'>
			  <#elseif i_index==6><td widtd="6%" nowrap="true" class="font2">整 改 率</td></#if>
			  <#assign rowTotal=0>
			  	<#list areas?if_exists as a>
			    <td <#if i_index!=2 && i_index!=6>onClick="showDetail(${a.areaCode},'${isGorver}','${ptype}');" style="cursor:hand;"</#if>>
			    <#list statisticsForDanger?if_exists as s><#if a.areaCode==s.areaCode>
			    <#if i_index==0>${s.anum}<#assign rowTotal=rowTotal+s.anum>
			    <#elseif i_index==1>${s.anum-s.bnum}<#assign rowTotal=rowTotal+s.anum-s.bnum>
			    <#elseif i_index==2><#if s.anum==0>/<#else>#{(s.bnum/s.anum*100);M1}%</#if>
			    <#elseif i_index==3>${s.aanum}<#assign rowTotal=rowTotal+s.aanum>
			    <#elseif i_index==4>${s.aanum-s.bbnum}<#assign rowTotal=rowTotal+s.aanum-s.bbnum>
			    <#elseif i_index==5>${s.ccnum}<#assign rowTotal=rowTotal+s.ccnum>
			    <#elseif i_index==6><#if s.aanum==0>/<#else>#{(s.bbnum/s.aanum*100);M1}%</#if>
			    </#if></#if></#list></td>
			    </#list>
			    <td <#if i_index!=2 && i_index!=6>onClick="showDetail(${area.areaCode},'${isGorver}','${ptype}');" style="cursor:hand;"</#if>>
			    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
			    <#elseif i_index==1>${rowTotal}<#assign bnum=rowTotal>
			    <#elseif i_index==2><#if anum==0>/<#else>#{((anum-bnum)/anum*100);M1}%</#if>
			    <#elseif i_index==3>${rowTotal}<#assign aanum=rowTotal>
			    <#elseif i_index==4>${rowTotal}<#assign bbnum=rowTotal>
			    <#elseif i_index==5>${rowTotal}
			    <#elseif i_index==6><#if aanum==0>/<#else>#{((aanum-bbnum)/aanum*100);M1}%</#if></#if></td>
			  </tr>
		  </#list>
	      <#list statistics?if_exists as hs>
		  <#list 0..2 as i>
		  <tr height="25" <#if hs_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#f1f4f9"</#if> align="center">
		  <#if i_index==0>
		  	<#if hs_index==0>
		  		<td rowspan="${statistics?size/areas?size*3}" width="3%" class="font">挂</br></br>牌</br></br>隐</br></br>患</td>
		  	</#if>
		  	<td rowspan="3" width="3%">${hs.enumName}</td><td nowrap="nowrap" width="6%" class="font2">挂 牌 数</td><#assign isGorver=''>
		  	<#elseif i_index==1><td nowrap="nowrap" width="6%" class="font2">未整改数</td><#assign isGorver='0'>
		  	<#elseif i_index==2><td nowrap="nowrap" width="6%" class="font2">整 改 率</td>
		  </#if>
		  	<#assign rowTotal=0>
		  	<#list areas?if_exists as a>
			    <td <#if i_index!=2>onClick="showDetailRollcall(${a.areaCode},'${isGorver}','${hs.enumCode}');" style="cursor:hand;"</#if>>
			    <#list statistics?if_exists as s>
			    	<#if hs.enumCode==s.enumCode>
			    		<#if a.areaCode==s.areaCode>
			    			<#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
			    			<#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
			    			<#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if></#if>
			    		</#if>
			    	</#if>
			    </#list>
			    </td>
		    </#list>
		    <td <#if i_index!=2>onClick="showDetailRollcall(${area.areaCode},'${isGorver}','${hs.enumCode}');"  style="cursor:hand;"</#if>>
		    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
		    <#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
		    <#elseif i_index==2><#if inhere==0>/<#else>#{((inhere-number)/inhere*100);M1}%</#if></#if>
		    </td>
		  </tr>
		 </#list>
		 <#if hs_index==(statistics?size/areas?size-1)>
		 <#break>
		 </#if>
		 </#list>
		<tr height="25" bgcolor="#f1f4f9" align="center">
            <td height="20" colspan="${areas?size+4}"></td>
            </tr>
        </table>
	</div>
	<script type="text/javascript">
		var remarkParam = "";
		<#if statistic?? && statistic.remark??>
			remarkParam = "statistic.remark=${statistic.remark}&";
		</#if>
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
			
				return false;
			} else{
		
				window.location = "loadTroubleForColligation.xhtml?"+remarkParam+"statistic.isSonType=1&area.areaCode="+areaCode+"&statistic.year=2012";
				}
		}
		function showDetail(areaCode,isGorver,ptype) {
			if(ptype == 'danger'){
				window.parent.location = "loadDangerTroubleByTypeList.xhtml?"+remarkParam+"statistic.isSonType=1&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.isRollcall=0";
			}else{
				window.parent.location = "loadNomalTroubleByTypeList.xhtml?"+remarkParam+"statistic.isSonType=1&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&statistic.isGorver="+isGorver;
			}
		}
		function showDetailRollcall(areaCode,isGorver,enumCode) {
			window.parent.location = "loadDangerTroubleByTypeList.xhtml?"+remarkParam+"statistic.isSonType=1&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.enumCode="+enumCode+"&statistic.isRollcall=1";
		}
		function changeQUrl() {
			window.parent.location = "loadTroubleForColligation.xhtml?"+remarkParam+"statistic.isSonType=${statistic.isSonType}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
	</script>
</#escape>
<@fkMacros.pageFooter />