<@fkMacros.pageMapHeader />
<@fkMacros.winLIKE />
<#escape x as (x)!> 
<@fkMacros.loading />
<#assign sysUrl="http://60.190.2.183:18080/nbyhpc/coreCompany"> 
<script type="text/javascript" src="${resourcePath}/js/MarkerTool_min.js"></script>
<script>var enumObj=new Enum("${enumXmlUrl}");</script>
</head>

<body>
<!--头部开始-->
<div id="map_top">
  <table width="100%" border="0">
    <tr>
      <td width="18%"><input type="text" class="input_search" id="address_search" title="请输入地名，大厦快速定位" value="请输入地名，大厦快速定位"></td>
      <td width="82%"><input type="image" src="${resourcePath}/img/map/btn_dw.jpg" onclick="fun_geocoder_getPoint();"></td>
    </tr>
  </table>
</div>

<div id="top_control_arrow_box">
  <div id="top_control_arrow"><img src="${resourcePath}/img/map/arrow_top_down.png" alt="展开与收起"/></div>
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
   <!--内容左侧 地图开始-->
    <td valign="top" width="100%"> 
    <div class="arrow"><img src="${resourcePath}/img/map/arrow_open.png" ></div>
    <div style="width:100%;height:666px;border:#ccc solid 1px; position:relative; z-index:99;" id="dituContent"> </div>
    </td>
  <!--内容左侧 地图结束--> 
  
  <!--内容右侧 列表box开始-->
    <td valign="top">
      <div id="right_box">
        <div class="right_title">企业档案</div>
        <div class="map_search_box">
        <form action="loadMapForGsCompanies.xhtml" method="post" name="searchForm" id="searchForm">
	        <table width="100%" class="tab_form">
	          <tr>  
	            <td width="29%" class="title">单位名称</td>
	            <td colspan="2"><input type="text" id="companyName" name="company.companyName" value="${company.companyName}" size="27"></td>
	          </tr>
	          <tr>
	            <td class="title">区　　域</td>
	            <td colspan="2">
	            <select id="second-area" name="company.secondArea" style="width:80px;" onChange="loadThirdAreas('',this.value,'company.thirdArea');">
			        	<option value="0">--请选择--</option>
			        	<#if childAreas??>
			        		<#list childAreas as s>
				    			<option value="${s.areaCode}">${s.shortName?default(s.areaName)}</option>
				    		</#list>
				    	</#if>
			    </select>&nbsp;
			    <select name="company.thirdArea" id="third-area" style="width:80px;">
				    		<option value="0">－请选择－</option>
				</select>
			   </td>
	          </tr>
	          <tr>
	            <td class="title">管理行业</td>
	            <td colspan="2"><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);" style="width:80px;"><option value="" selected>--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail?? && userDetail.userIndustry?? && userDetail.userIndustry!='anwei'></#if>>${t.name}</option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);" style="width:80px;"><option value="">--请选择--</option></select></td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	            <td colspan="2"><select name="company.tradeTypes" id="tradeTypes3" onchange="" style="width:165px;"><option value="">--请选择--</option></select></td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	            <td width="42%"><input type="image" src="${resourcePath}/img/map/btn_search_submit.jpg" width="62" height="29" class="m_t_b_10" onclick="search();"></td>
	            <td></td>
	          </tr>
	        </table>
        </form>
        </div>
        <table width="100%" class="tab_list_map" id="map_list">
          <#if companys?exists>
  			<#list companys?if_exists as company>
  			 <tr id="show_box" onClick="javascript:popZfMarker('${company.companyName}','${company.id}','company','${sysUrl}/loadCoreCompanyInfo.xhtml?coreCompany.id=${company.id}');">
	            <td width="18%" class="img"><img src="${resourcePath}/img/map/map_point.png" width="26" height="32" /></td>
	            <td width="82%"><span>${company.companyName}</span><br /> 地址：${company.regAddress}</td>
	          </tr>
  			</#list>
		  </#if>
        </table>
        <table width="98%" cellpadding="0" cellspacing="1" >
			<tr>
			<td align="center">
				<script language='javascript'>
	function paginationSubmit(event,submitType){
		event=event?event:(window.event?window.event:null);
		if (event.keyCode==13){
		if(submitType=='page'){toPage();}else if(submitType=='pageSize'){toPageSize();}}}function toPage(){ 
		var reg=/^[0-9]*$/;
		var paNewPageSize=document.getElementById('paNewPageSize').value;
		var isCurrent=reg.exec(paNewPageSize);if(!isCurrent||paNewPageSize<=0){   alert('输入格式错误，应为大于零的正整数！');   document.getElementById('paNewPageSize').value=1;   return false;}else if(paNewPageSize==1){   return false;}else if(paNewPageSize>${pagination.totalPage}){   alert('输入值不能大于最大页数！');   return false;}document.getElementById('paItemCount').value=(eval(paNewPageSize-1))*7;document.forms['navigationForm'].submit();return false;}
		function toPageSize(){ 
		var reg=/^[0-9]*$/;
		var pageSizeValue=document.getElementById('paPageSize').value;
		var isCurrent=reg.exec(pageSizeValue);if(!isCurrent||pageSizeValue<=0){   alert('输入格式错误，应为大于零的正整数！');   document.getElementById('paPageSize').value=${pagination.pageSize};   return false;}else if(pageSizeValue==${pagination.pageSize}){   return false;}else if(pageSizeValue>${pagination.pageSize}){   alert('每页显示的条数不能大于8条！');   return false;}document.forms['navigationForm'].submit();return false;
	}
</script>
<style type='text/css'> 
#pagination{line-height:20px;color:#000;font-size:13px;}
#pagination a{text-decoration:none;color:#000;}
#pagination a:hover{text-decoration:underline;}
#pagination input{text-align:center;border-right: #ffffff 0px solid;border-top: #ffffff 0px solid;font-size: 9pt; border-left: #ffffff 0px solid;border-bottom: #c0c0c0 1px solid;background-color: #ffffff}
#pagination select{border-right: #000000 1px solid;border-top: #ffffff 1px solid;font-size: 12px; border-left: #ffffff 1px solid;color:#003366;border-bottom: #000000 1px solid;background-color: #f4f4f4;}
</style>
<form action='?' method='post' name='navigationForm'>
<input type='hidden' name='company.secondArea' value='${company.secondArea}'>
<input type='hidden' name='company.tradeTypes' value='${company.tradeTypes}'>
<input type='hidden' name='y' value='${x}'>
<input type='hidden' name='company.thirdArea' value='${company.thirdArea}'>
<input type='hidden' name='company.companyName' value='${company.companyName}'>
<input type='hidden' name='x' value='${y}'>
<input type='hidden' id='paTotalCount' name='pagination.totalCount' value='951'>
<input type='hidden' id='paItemCount' name='pagination.itemCount' value='0'>
<div id='pagination'>共${pagination.totalCount}条,每页<input size='1' type='text' id='paPageSize' name='pagination.pageSize' value='${pagination.pageSize}' onChange='toPageSize();' onKeyDown='paginationSubmit(event,"pageSize");' />条,
	页次${pagination.currentPage}/${pagination.totalPage}页,
	<#if pagination.totalCount gt pagination.pageSize>
		<#if pagination.currentPage gt 1>
			<a href='javascript:void(0)' class='paginationIndex' onClick="javascript:document.getElementById('paItemCount').value='0';document.navigationForm.submit();return false;">首页</a>&nbsp;
			<a href='javascript:void(0)' class='paginationIndex' onClick="javascript:document.getElementById('paItemCount').value='${pagination.pageSize*(pagination.currentPage-2)}';document.navigationForm.submit();return false;">上页</a>&nbsp;
		</#if>
		<#if pagination.currentPage != pagination.totalPage>
			<a href='javascript:void(0)' class='paginationIndex' onClick="javascript:document.getElementById('paItemCount').value='${pagination.pageSize*(pagination.currentPage)}';document.navigationForm.submit();return false;">下页</a>&nbsp;
		</#if>
	</#if>
	&nbsp;&nbsp;跳转到第<input size='1' id='paNewPageSize' value='${pagination.currentPage}' onChange='toPage();' onKeyDown='paginationSubmit(event,"page");' />页（按Enter键）
</div>
</form>
			</td>
		</tr>
</table>
      </div>
    </td>
    <!--内容右侧 列表box结束-->
  </tr>
</table>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<!--<@fkMacros.selectAreas_fun1 "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun1 "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>-->
<!--内容结束-->
</body>
<script type="text/javascript">
	initMap();//创建和初始化地图
	 var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
    {"input": "address_search", "location": map});

    ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件

        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province + _value.city + _value.district + _value.street + _value.business;
        }

        str = "FromItemindex = " + e.fromitem.index + "value = " + value;

    });
    
    ac.addEventListener("onconfirm", function(e) {    
	    //鼠标点击下拉列表后的事务
	    var _value = e.item.value;    
	    myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;      
	    setPlace();
    });
	
	function search(){
		var thirdArea = jQuery("#third-area").val();
		loadMapMarkers(true,"all",jQuery("#companyName").val(),jQuery("#first-area").val(),jQuery("#second-area").val(),thirdArea,jQuery("#tradeTypes1").val(),jQuery("#tradeTypes2").val(),jQuery("#tradeTypes3").val());
		$('searchForm').submit();
	}
	
	function searchInit(){
		
	}
	
	<#if userDetail?? && userDetail.userIndustry?? && userDetail.userIndustry!='anwei'>
	changeTrade(get("tradeTypes1").value,1);
</#if>

function changeTrade(tradeId, type) {
	var length = 1;
	if(type == 1) {
		cleanSelect(get("tradeTypes2"));
		cleanSelect(get("tradeTypes3"));
	} else if (type == 2){
		cleanSelect(get("tradeTypes3"));
	}
	<#list tradeTypes?if_exists as t>
		<#list t.daIndustryParameters?if_exists as st>
			if (type == 1 && tradeId == "${t.id}" && "${st.type}" == 1) {
				var opt = new Option("${st.name}", "${st.id}");
				get("tradeTypes2").options[length] = opt;
				opt = null;
				length ++;
			}
			<#list st.daIndustryParameters?if_exists as sst>
				if (type == 2 && tradeId == "${st.id}" && "${sst.type}" == 1) {
					var opt = new Option("${sst.name}", "${sst.id}");
					get("tradeTypes3").options[length] = opt;
					opt = null;
					length ++;
				}
			</#list>
		</#list>
	</#list>
}
<#if company?exists>
	<#if company.tradeTypes?exists>
		var trades = "${company.tradeTypes}";
		getName("company.tradeTypes")[getName("company.tradeTypes").length-1].value = trades;
		for (var i=0;i<trades.split(",").length;i++) {
			if (trim(trades.split(",")[i]) != "") {
				get("tradeTypes"+(i+1)).value = trim(trades.split(",")[i]);
				changeTrade(trim(trades.split(",")[i]), (i+1));
			}
		}
	</#if>
</#if>

<!--系统自动通过地址实现企业标注功能-->
 <#if unmarkerCompanies?exists>
  	<#list unmarkerCompanies?if_exists as company>
 		pickup_point("${company.regAddress}","${company.id}","${company.companyName}")
 	</#list>
 </#if>
 
 <!--自动在地图上标注搜索列表中的7家企业-->
 <#if mapMarkers?exists>
 	//将查询出的结果标记到地图上
// 	var pointArr = new Array();
	<#list mapMarkers?if_exists as marker>
		var point = new BMap.Point(${marker.markerLng},${marker.markerLat});
		var markerLink = "${sysUrl}/loadCoreCompanyInfo.xhtml?coreCompany.id=${marker.markerId}";
		addMarkerInit('${marker.markerName}', markerLink, point, ${marker_index} + 1);
//		pointArr[${marker_index}] = point;
	</#list>
//	if (pointArr.length > 0) {
//		adjustVisualField(pointArr);
//	}
 </#if>
 
var wBox = "";
var param_markerLng = "";
var param_markerLat = "";
//打开已标记过的企业信息窗口
function popZfMarker(markerName,markerId,type){;
//	map.clearOverlays(); //清除地图上所有标记
	var markerLink = "${sysUrl}/loadCoreCompanyInfo.xhtml?coreCompany.id=" + markerId;
	var request = new Request.JSON({
	  		url:'loadMapMarker.xhtml?mapMarker.markerType='+type+'&mapMarker.markerId='+markerId,
	  		onComplete: function(response) { 
				if(response!=null){
					openInfoWindowByCompany(markerName,markerLink,response.markerLng,response.markerLat,570,500,1);
				}else{
					companyInfo(markerName,markerLink,markerId);
				}
	        }
	}).send(); 
}

var isMarker = 1;
function companyInfo(markerName,markerLink,markerId){
	isMarker = 0;
	wBox=jQuery("#show_box").wBox({
		title: "生产经营单位企业档案&nbsp;",
	 	requestType:"iframe",
	 	iframeWH:{width:570,height:520},
	 	show:true,
	 	target:markerLink
	 });
}

//区域下拉单赋值
<#if company?? && company.secondArea??>
	jQuery("#second-area").val("${company.secondArea}");
	loadThirdAreas('',"${company.secondArea}","company.thirdArea");
</#if>
	
function loadThirdAreas(obj,areaCode,third_area){
    if (areaCode == 'undefined' || areaCode == '') {
    	areaCode = jQuery(obj).val();
    } 
	jQuery("#third-area option").remove();
	jQuery.ajax({
        url : '../coreCompany/ajaxThirdArea.xhtml',
        type : 'post',
        dataType : 'html',
        data : 'areaCode=' + areaCode +'&third_area='+third_area+'&s='+ Math.round(Math.random() * 10000),
        success : function(html) {
            if (html != null && html != '') {
                jQuery("#third-area").append(html);
            }
        }
    });
}
</script>
</#escape> 
<@fkMacros.pageFooter /> 