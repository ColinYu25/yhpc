<@fkMacros.pageHeader />
<#escape x as (x)!>
<style type="text/css">
.map_table{
	border-left:1px solid #0099CC;
	border-top:1px solid #0099CC;
	width:100%;
}

.map_table th{
	background-color:#F0F0F0;
	font-size:12px;
	font-weight:bold;
	color:#333;
	text-align:right;
	border-bottom: 1px solid #0099CC;
	border-right: 1px solid #0099CC;
}
.map_table .input_title{FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cccccc); height:25px; font-size:14px; font-weight:bold; text-align:center;color:#0D448E;}

.map_table td{
	font-size:12px;
	color:#333;
	padding:2px;
	background-color:#F9F9F9;
	border-bottom: 1px solid #0099CC;
	border-right: 1px solid #0099CC;
}

.confirm_but{
	width:80px; height:25px;
	font-weight:bold;
	FONT-SIZE: 12px;	
	CURSOR: pointer;
	COLOR: black;
	background-image: url(${resourcePath}/img/aa.gif);
	border:0;
}

.companyName a:link,.companyName a:visited{
    color:#0000CC
}
.companyName a:hover {
    color:#FF3300
}
</style>
<script>var enumObj=new Enum("${enumXmlUrl}");</script>
<script>
	function submitForm(){
		var ids=document.getElementsByName('ids');
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				parent.window.accidentTankId=ids[i].value;//设定全局企业ID
				$('companyForm').submit();
				return false;
			}
		}
		alert('请先从列表中选择事发危险源!');
	}
function search(){
	var thirdArea = jQuery("#third-area").val();
	window.parent.loadMapMarkers(true,"all",jQuery("#companyName").val(),jQuery("#first-area").val(),jQuery("#second-area").val(),thirdArea,jQuery("#tradeTypes1").val(),jQuery("#tradeTypes2").val(),jQuery("#tradeTypes3").val());
	$('searchForm').action = "loadCompanysByRescue.xhtml";
	$('searchForm').submit();
}
function popMarker(markerName,markerId,type,url){
	var request = new Request.JSON({
	  		url:'${contextPath}/map/loadMapMarker.xhtml?mapMarker.markerType='+type+'&mapMarker.markerId='+markerId,
	  		onComplete: function(response) { 
				if(response!=null){
					 var markerLink = "loadCompany.xhtml?company.id=" + markerId;
					 document.parentWindow.parent.openInfoWindowByCompany(markerName,markerLink,response.markerLng,response.markerLat,570,680,1);
				}else{
					alert("没有在地图标注，无法定位!");
					return;
				}
	        }
	}).send(); 
}

getElementsByName:function (name) { 
var returns = document.getElementsByName(name); 
if(returns.length > 0) return returns; 
returns = new Array(); 
var e = document.getElementsByTagName('td'); 
for(i = 0; i < e.length; i++) { 
if(e[i].getAttribute("name") == name) { 
returns[returns.length] = e[i]; 
} 
} 
return returns; 
}

function changeBg(obj){
	for(i = 0; i < document.getElementsByName("tableBgs").length; i++){
		
		document.getElementsByName("tableBgs")[i].style.background="white";
	}
 	obj.style.background="#CCC";
}
</script>
<form action="" method="post" name="searchForm" id="searchForm">
<input name="company.type" type="hidden" value="${company.type}" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="map_table" align="center">
  <tr>
    <th nowrap="nowrap">单位名称</th>
    <td width="80%"><input type="text" id="companyName" name="company.companyName" value="${company.companyName}" size="36"></td>
  </tr>
  <tr>
   <th>区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域</th>
   <td><div id="div-area"></div></td>
  </tr>
  <tr>
   <th >管理行业</th>
   <td nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);" style="width:124px;"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail?? && userDetail.userIndustry?? && userDetail.userIndustry!='anwei'>selected</#if>>${t.name}</option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);" style="width:124px;"><option value="">--请选择--</option></select><br/>
	    <select name="company.tradeTypes" id="tradeTypes3" onchange="" style="width:254px;"><option value="">--请选择--</option></select></td>
  </tr>
  <tr>
	  	<td colspan="2" style="text-align:center">
	  		<input type="button" id="sub" value="搜　索"  class="confirm_but" onclick="search();"/> 
	  	</td>
  </tr>
</table>
	<#if company?? && company.isReged??>
		<input type="hidden" name="company.isReged" value="${company.isReged}" />
	</#if>
</form>
<form action="createModelInit.xhtml?analyseModule=rescue" method="post" id="companyForm" name="companyForm">
<#if companys?exists>
  	<#list companys?if_exists as company>
		<table name="tableBgs" id="tableBgs" width="100%" border="0" cellspacing="0" cellpadding="0" align="center" onclick="changeBg(this)">
		  <tr>
		    <td rowspan="2" width="25"><a href="javascript:void(0)" onClick="javascript:popMarker('${company.companyName}','${company.id}','company','loadCompany.xhtml?company.id=${company.id}');"><img src="${resourcePath}/img/map/company.png";></a></td>
		    <td class="companyName"><a href="javascript:void(0)" onClick="javascript:popMarker('${company.companyName}','${company.id}','company','loadCompany.xhtml?company.id=${company.id}');">${company.companyName}</a></td>
		  </tr>
		  <tr>
		    <td>地址：${company.regAddress}</td>
		  </tr>
		  <tr>
		    <td height="5"></td>
		  </tr>
		</table>
 	</#list>
</#if>
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
<@fkMacros.selectAreas_fun1 "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun1 "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>

<script type="text/javascript">
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

</script>
</#escape> 
<script>
	<#if company?? && company.isGoved??>
		jQuery("#gov").val("${company.isGoved}");
	</#if>
</script>
<@fkMacros.pageFooter />
