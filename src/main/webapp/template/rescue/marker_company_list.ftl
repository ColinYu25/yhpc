<@fkMacros.pageHeader />
<#escape x as (x)!> 
<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.3&services=true"></script>
<script>var enumObj=new Enum("${enumXmlUrl}");</script>
<script language="javascript">
function marker(markerId,markerName){
	  $("markerName").value=markerName;
	  $("markerId").value=markerId;
	  $("markerLink").value=$("markerLink").value+markerId;
	  var markerLink = "http://60.190.2.104/nbajj_colligation/companyInfo/loadCompanyInfoForZF.xhtml?company.id=" + markerId;
	  $('markerForm').set('send', {onComplete: function(response) {
	      if(response!=""){
	        var point = new BMap.Point(${markerLng},${markerLat});
			document.parentWindow.parent.addMarker(markerName,markerLink,point,1);//在地图上标记
		   }else{
 		   	 alert("标注操作失败!");	 	   	 
 		   }
 		   document.parentWindow.parent.closeWindow('marker-window');
		}});
  	  $('markerForm').send(); 	  
}
function search(){
	$('searchForm').submit();
}

//删除标准
function deleteMarker(markerId) {
	$('markerForm').action = "${contextPath}/map/deleteMarkerById.xhtml?mapMarker.id=" + markerId;
	$('markerForm').submit(); 
}
</script>
<form action="" method="post" name="searchForm" id="searchForm">
<input name="company.type" type="hidden" value="${company.type}" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input" align="center">
  <tr>
    <th width="15%">单位名称</th>
    <td width="25%"><input type="text" name="company.companyName" value="${company.companyName}"></td>
    <th width="15%">单位区域</th>
    <td width="35%"><div id="div-area"></div></td>
    <td width="10%"><input type="button" id="sub" value="搜　索"  class="confirm_but" style="height:25px; width:80px;border:0px" onclick="search();"/> </td>
  </tr>
</table>
	<#if company?? && company.isReged??>
		<input type="hidden" name="company.isReged" value="${company.isReged}" />
	</#if>
</form>
<form action="${contextPath}/map/createMarker.xhtml" method="post" id="markerForm" name="markerForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="5%">选择</th>
    <th width="5%">序号</th>
    <th width="30%">单位名称</th>
    <th width="30%">地址</th>
    <th width="10%">法人代表</th>
    <th width="10%">状态</th>
  </tr>
  <#if companys?exists>
  	<#list companys?if_exists as company>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${company.id}" value="${company.id}" <#if markerIds.indexOf('${company.id}')==-1><#else>disabled</#if> title="单击标注此项" onclick="javascript:marker('${company.id}','${company.companyName}')"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+company_index+1}<#else>${company_index+1}</#if>&nbsp;</td>
	    <td><div style="text-align:left">${company.companyName}&nbsp;</div></td>
	    <td><div style="text-align:left">${company.regAddress}&nbsp;</div></td>
	    <td>${company.fddelegate}&nbsp;</td>
	    <td>
		    <#if markerIds.indexOf('${company.id}')==-1>未标注<#else><a href="javascript:void(0)" onClick="deleteMarker('${company.id}');" style="color:red;">删除标注</a></#if> 
	    </td>
	  </tr>
	 </#list>
  </#if>
</table>
	<input type="hidden" name="mapMarker.markerImg" value="company.png" />
	<input type="hidden" name="mapMarker.markerLng" id="markerLng" value="${markerLng}" />
	<input type="hidden" name="mapMarker.markerLat" id="markerLat" value="${markerLat}" />
	<input type="hidden" name="mapMarker.markerHeight" value="16"  />
	<input type="hidden" name="mapMarker.markerWidth" value="16"  />
	<input type="hidden" name="mapMarker.markerLink"  id="markerLink" value="http://60.190.2.104/nbajj_colligation/companyInfo/loadCompanyInfo.xhtml?company.id="  />
	<input type="hidden" name="mapMarker.markerName" id="markerName" />
	<input type="hidden" name="mapMarker.markerId" id="markerId" />
	<input type="hidden" name="mapMarker.markerType" value="company" />
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape> 
<script>
	<#if company?? && company.isGoved??>
		jQuery("#gov").val("${company.isGoved}");
	</#if>
</script>
<@fkMacros.pageFooter />
