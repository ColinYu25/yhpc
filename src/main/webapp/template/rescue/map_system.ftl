<@fkMacros.pageEnterpriseMapHeader />
<@fkMacros.winLIKE />
<#escape x as (x)!> 
<@fkMacros.loading />
<script type="text/javascript" src="${resourcePath}/js/MarkerTool_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.3&services=true"></script>
</head>

<body>
<!--头部开始-->
<div id="map_top">
  <table width="100%" border="0">
    <tr>
      <td width="3%"><img src="${resourcePath}/img/map/icon_fdj.jpg" width="30" height="30" /></td>
      <td width="18%"><input type="text" class="input_search" id="address_search" title="请输入地名，大厦快速定位" value="请输入地名，大厦快速定位"></td>
      <td width="79%">
      <input type="button" value="快速定位"class="input_btn" onclick="fun_geocoder_getPoint();">
      <#if mapMarker??><input type="button" value="重新标注" class="input_btn" onclick="rePoint('${mapMarker.markerId}',2);"/><#else><input type="button" value="标 注" class="input_btn" onclick="selectStyle(2)"/></#if>
      </td>
    </tr>
  </table>
</div>
<!--头部结束-->

<!--form表单开始 -->
<form action="${contextPath}/map/createMarker.xhtml" method="post" id="markerForm" name="markerForm">
	<input type="hidden" name="mapMarker.markerImg" value="company.png" />
	<input type="hidden" name="mapMarker.markerLng" id="markerLng" value="${markerLng}" />
	<input type="hidden" name="mapMarker.markerLat" id="markerLat" value="${markerLat}" />
	<input type="hidden" name="mapMarker.markerHeight" value="16"  />
	<input type="hidden" name="mapMarker.markerWidth" value="16"  />
	<input type="hidden" name="mapMarker.markerLink"  id="markerLink" value="map/loadCompany.xhtml?company.id="  />
	<input type="hidden" name="mapMarker.markerName" id="markerName" />
	<input type="hidden" name="mapMarker.markerId" id="markerId" />
	<input type="hidden" name="mapMarker.markerType" value="company" />
</form>
<!--form表单结束-->

<!--内容开始 一行2列-->
<table width="100%" border="0" height="100%">
  <tr>
   <!--内容 地图开始-->
    <td valign="top" width="100%"> 
    <div style="width:100%;height:666px;border:#ccc solid 1px; position:relative; z-index:99;" id="dituContent"> </div>
    </td>
  <!--内容地图结束--> 
  </tr>
</table>
<!--内容结束-->
</body>
<script type="text/javascript">
	initMap();//创建和初始化地图
	
	 var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
    {"input": "address_search", "location": map });

    ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province + _value.city + _value.district + _value.street + _value.business;
        }
        str = "FromItemindex = " + e.fromitem.index + "value = " + value;

    });
	
	<#if companyPass?? && companyPass.daCompany??>
		//企业标注功能
		enterpriseMarkerTool('${companyPass.daCompany.companyName}','http://60.190.2.104/nbajj_colligation/companyInfo/loadCompanyInfo.xhtml?company.id=${companyPass.daCompany.id}',1);
	</#if>
	<#if mapMarker??>
		//默认打开已标注过的企业档案信息窗口
		popMarker('${mapMarker.markerName}','${mapMarker.markerId}','company');
	</#if>
</script>
</#escape> 
<@fkMacros.pageFooter /> 