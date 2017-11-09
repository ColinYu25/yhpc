<@fkMacros.pageHeader />
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/jquery-1.1.3.1.pack.js"></script>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/thickbox-compressed.js"></script>
<link href="${resourcePath}/css/marker_company_info.css" rel="stylesheet" type="text/css" />
<link href="${resourcePath}/css/thickbox.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!> 
<@enum.initAreaXML/>
<script type="text/javascript">
function scrollDoor() {
}
scrollDoor.prototype = { sd : function(menus, divs, openClass, closeClass) {
	var _this = this;
	if (menus.length != divs.length) {
		alert("菜单层数量和内容层数量不一样!");
		return false;
	}
	for ( var i = 0; i < menus.length; i++) {
		_this.$(menus[i]).value = i;
		_this.$(menus[i]).onmouseover = function() {
			for ( var j = 0; j < menus.length; j++) {
				_this.$(menus[j]).className = closeClass;
				_this.$(divs[j]).style.display = "none";
			}
			_this.$(menus[this.value]).className = openClass;
			_this.$(divs[this.value]).style.display = "block";
		}
	}
}, $ : function(oid) {
	if (typeof (oid) == "string")
		return document.getElementById(oid);
	return oid;
} }
window.onload = function() {
	var SDmodel = new scrollDoor();
	SDmodel.sd( [ "m01", "m02" ], [ "c01", "c02" ], "sd01", "sd02");
}

var checkExt = function(obj) {
	if (!(/(jpg|png|gif)$/i.test(obj.value))) {
		alert("只允许上传jpg、png、gif的文件");
		obj.select();// 选择后清空文件框
		document.selection.clear();
	}
};

function checkForm() {
	var imagefile = document.getElementById("daImageFile").value;
	if (!imagefile) {
		alert("请选择上传的文件！")
		return false;
	}
	return true;
}
</script>
<style type="text/css">
.noborder {
	border: 0;
}

.border {
	border: solid 1px #7f9db9
}

.onborder {
	background-color: #D8DBFE;
	border-top: solid 1px #D8E3EB;
	border-left: solid 1px #D8E3EB;
	border-bottom: solid 1px #0080C0;
	border-right: solid 1px #0080C0;
}

.input {
	height: 24px;
	BORDER-BOTTOM: #DBDBDB 1px solid;
	BORDER-LEFT: #DBDBDB 1px solid;
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	BACKGROUND: #f6f6f6;
	BORDER-TOP: #DBDBDB 1px solid;
	BORDER-RIGHT: #DBDBDB 1px solid;
	PADDING-TOP: 0px;
	-moz-border-radius: 0px;
	-webkit-border-radius: 0px
}

/*弹出上传*/
.main_up {
	margin-top: 10px;
	background: #F6F8FC
}

.dbsy_1 {
	border: solid 2px #C9D7F1;
}
</style>
</head>
<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="middle" class="h4">生产经营单位安全生产基本情况表</td>
  </tr>
  <tr>
    <td height="1" bgcolor="#cccccc"></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="main_table">
  <tr>
    <td align="center">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="dbsy" height="171">
	  <tr>
		<td height="28" class="dbsy_h1_bg">
		<ul class="scrollUl">
		  <li class="sd01" id="m01">单位基本情况</li>
		  <li class="sd02" id="m02">安全生产基本状况</li>
		  </li>
		</ul>
		</td>
	  </tr>
	  <tr>
		<td height="408" valign="top" align="center" style="padding:1px 0px">
	<div id="c01">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="table03">
	    <tr>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">单位名称</td>
	      <td width="196" align="left" class="td2" title="${company.companyName}">${company.companyName}</td>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">地 址</td>
	      <td width="196" align="left" class="td2">${company.regAddress}</td>
	    </tr>
	    <tr>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">所属区域</td>
	      <td width="196" align="left" class="td2">
			<#if company.firstArea??><@enum.getSelectArea '${company.firstArea}' />&nbsp;&nbsp;</#if>
			<#if company.secondArea??><@enum.getSelectArea '${company.secondArea}' />&nbsp;&nbsp;</#if>
			<#if company.thirdArea??><@enum.getSelectArea '${company.thirdArea}' />&nbsp;&nbsp;</#if>
		  </td>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">所属行业</td>
	      <td width="196" align="left" class="td2">
	      	<#if tradeTypes??><#list tradeTypes as t>${t.daIndustryParameter.name}&nbsp;&nbsp;</#list><#else>&nbsp;</#if>
	      </td>
	    </tr>
	    <tr>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">主要负责人</td>
	      <td width="196" align="left" class="td2">${company.fddelegate?default('&nbsp;')} </td>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">单位联系电话</td>
	      <td width="196" align="left" class="td2">
	      	<input id="phoneCode" name="phoneCode" value="${company.phoneCode}" entity="DaCompany" type="text" class="noborder"/>
	      </td>
	    </tr>
	    <tr>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">统一社会信用代码（工商注册号）</td>
	      <td width="196" align="left" class="td2">
	      	<input id="regNum" name="regNum" value="${company.regNum}" entity="DaCompany" type="text" class="noborder"/>
	      </td>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">组织机构代码</td>
	      <td width="196" align="left" class="td2">
	      	<input id="setupNumber" name="setupNumber" value="${company.setupNumber}" entity="DaCompany" type="text" class="noborder"/>
	      </td>
	    </tr>
	    <tr>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">规模以上<br>工业企业</td>
	      <td width="196" align="left" class="td2">
			<#if company?if_exists.daCompanyPass?if_exists.enterprise?exists && company.daCompanyPass.enterprise>
		    <input type="radio" name="enterprise" value="true" checked="true" entity="DaCompanyPass"/>是
		    <input type="radio" name="enterprise" value="false" entity="DaCompanyPass"/>否
		    <#else>
		    <input type="radio" name="enterprise" value="true" entity="DaCompanyPass"/>是
		    <input type="radio" name="enterprise" value="false" checked="true" entity="DaCompanyPass"/>否
			</#if>
		  </td>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">职工人数</td>
	      <td width="196" align="left" class="td2">
	      	<input id="employeeNumber" name="employeeNumber" value="${company.employeeNumber}" entity="DaCompany" type="text" class="noborder"/> 人
	      </td>
	    </tr>
	    <tr>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">是否重点<br>消防单位</td>
	      <td width="196" align="left" class="td2">
	      	<input type="radio" name="focusFireUnits" value="1" <#if company?if_exists.focusFireUnits??&&company.focusFireUnits == 1>checked="true"</#if> entity="DaCompany"/>是
	    	<input type="radio" name="focusFireUnits" value="0" <#if company?if_exists.focusFireUnits??&&company.focusFireUnits != 1>checked="true"</#if> entity="DaCompany"/>否   
	      </td>
	      <td width="84" align="center" valign="middle" bgcolor="f4f4f4" class="td1">经营范围</td>
	      <td width="196" align="left" class="td2">
	      	<input id="businessScope" name="businessScope" value="${company.businessScope}" entity="DaCompany" type="text" class="noborder"/>
	      </td>
	    </tr>
	  </table>
	  
	  <table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="280" align="center" valign="middle">
	    	<#if imageOne??>
	    	<a href="../${imageOne.path}" class="thickbox"><img src="../${imageOne.path}" width="260" height="195"/></a><br/>
	    		 <a href="#" onclick="javascript:showImageUpload(2,${imageOne.id})">编辑</a> <a href="#" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageOne.id}&daImageFile.daCompany.id=${company.id}'">删除</a>
	    	<#else>
	    		<img src="${resourcePath}/img/nopicture.jpg" width="260" height="195">
	    		<div id="oneOperate" style="font-size:13px;text-align:center;">
	    		 <a href="#" onclick="javascript:showImageUpload(1,-1)">上传</a>
	    		</div>
	    	</#if>
	    </td>
	    <td width="280" align="center" valign="middle">
		    <#if imageTwo??>
		    	<a href="../${imageTwo.path}" class="thickbox"><img src="../${imageTwo.path}" width="260" height="195"/></a><br/>
	    		 <a href="#" onclick="javascript:showImageUpload(2,${imageTwo.id})">编辑</a> <a href="#" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageTwo.id}&daImageFile.daCompany.id=${company.id}'">删除</a>
			<#else>
				<img src="${resourcePath}/img/nopicture.jpg" width="260" height="195">
				<div id="twoOperate" style="font-size:13px;text-align:center;">
	    		 <a href="#" onclick="javascript:showImageUpload(1,-1)">上传</a>
	    		</div>
			</#if>
	  	</td>
	  </tr>
	</table>
</div>
<div id="c02" class="hidden">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="84" rowspan="5" align="center" bgcolor="f4f4f4" class="td1">安管机构和人员</td>
      <td width="94" height="28" align="center" class="td3">安全机构设置</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td colspan="3" align="center" class="td4"><input name="smSet" size="15" value="${(securityManage.smSet)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="146" align="center" class="td3">专职人员数</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td colspan="3" align="center"><input name="fulltimeStaff" size="15" value="${(securityManage.fulltimeStaff)?if_exists}" type="text" entity="DaSecurityManage" class="noborder"/> 人</td>
    </tr>
    <tr>
      <td height="1" colspan="11" align="center" bgcolor="DBDBDB"></td>
    </tr>
    <tr>
      <td height="28" align="center" class="td3">主要负责人</td>
      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
      <td width="67" align="center" class="td3"><input name="mainPrincipal" size="8" value="${(securityManage.mainPrincipal)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="50" align="center" class="td3">证书号</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td align="center" class="td4"><input name="mpCertificate" size="8" value="${(securityManage.mpCertificate)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="44" align="center" class="td3">有效期</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="70" align="center"><input name="mpValidity" size="8" value="${(securityManage.mpValidity)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
    </tr>
    <tr>
      <td height="1" colspan="11" align="center" bgcolor="DBDBDB"></td>
    </tr>
    <tr>
      <td height="28" align="center" class="td3">安全负责人</td>
      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
      <td width="67" align="center" class="td3"><input name="securityPrincipal" size="8" value="${(securityManage.securityPrincipal)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="50" align="center" class="td3">证书号</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td align="center" class="td4"><input name="spCertificate" size="8" value="${(securityManage.spCertificate)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="44" align="center" class="td3">有效期</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="70" align="center"><input name="spValidity" size="8" value="${(securityManage.spValidity)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
    </tr>
  </table>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="84" rowspan="5" align="center" bgcolor="f4f4f4" class="td1">安全生产许可证</td>
    <tr>
      <td width="94" height="28" align="center" class="td3">种类</td>
      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
      <td width="67" align="center" class="td3" id="category">&nbsp;</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="50" align="center" class="td3">证书号</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="146" align="center" class="td3" id="licence">&nbsp;</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="44" align="center" class="td3">有效期</td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="70" align="center" id="certEnd">&nbsp;</td>
    </tr>
    <tr>
      <td height="1" colspan="11" align="center" bgcolor="DBDBDB"></td>
    </tr>
    <tr>
      <td height="28" align="center" class="td3">许可范围</td>
      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
      <td colspan="9" align="left">
        &nbsp;&nbsp;<textarea class="input" rows="2" cols="44" name="textarea" id="chinaName">最多不超过500字</textarea></td>
    </tr>
  </table>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  
     <iframe width="100%" align="center" onload="this.height=30" id="iframe_standards" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src="../map/loadNullStandard.xhtml"></iframe>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
	
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="84" height="28" align="center" bgcolor="f4f4f4" class="td1">重大危险源</td>
	    <td width="212" align="center" class="td3" colspan="2" id="isMajorDangerousSource">&nbsp;</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="146" align="center" class="td3">备案情况</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="115" align="center" class="td6" id="isKeepOnRecord">&nbsp;</td>
	  </tr>
	</table>
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  
  <iframe id="iframe_succor" align="center" width="100%" height="28" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src="http://60.190.2.104:8082/succor/manager/manager_view.xhtml?regNum=${company.regNum}"></iframe>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  <iframe id="iframe_nbyhpc" align="center" width="100%" height="28" frameborder=0 scrolling=NO src="../danger/loadDangercount.xhtml?company.id=${company.id}"></iframe>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  <iframe id="iframe_nbxzzf" align="center" width="100%" height="28" frameborder=0 scrolling=NO src="../companyPunishment/loadCompanyPunishmentsinfo.xhtml?company.id=${company.id}"></iframe>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		  <td height="1" bgcolor="DBDBDB"></td>
		</tr>
	  </table>
	 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"> 
	  <tr>
	    <td width="84" rowspan="3" align="center" bgcolor="E8F2FB"><span class="td1">事故情况</span></td>
	    <td colspan="7"><iframe id="iframe_nbsg" align="center" width="100%" height="28" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src=""></iframe></td>
	  </tr>
	  <tr>
	    <td height="1" colspan="7" align="center" bgcolor="DBDBDB"></td>
	  </tr>
	  <tr>
	    <td width="94" height="28" align="center" class="td3">一年内工伤人数</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="118" align="center">${injuryManage.insuredNum} 人</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="146" align="center" class="td3">工伤率</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="115" align="center">${injuryManage.injuryNum} ‰&nbsp;</td>
	  </tr>
	  </table>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td height="1" bgcolor="DBDBDB"></td>
	    </tr>
	 </table>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="84" rowspan="3" align="center" bgcolor="E8F2FB"><span class="td1">信用管理</span></td>
	    <td width="94" height="28" align="center" class="td3">信用等级</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="118" align="center">${creditManage.grade}&nbsp;</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="146" align="center" class="td3">承诺书</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td width="115" align="center"><#if creditManage?? && creditManage.promise??><a href="${(creditManage.promise)?if_exists}"><img src="${resourcePath}/images/download.gif" width="16" height="16" style="cursor:pointer;" title="点击下载"/></a><#else></#if></td>
	  </tr>
	  <tr>
	    <td height="1" colspan="7" align="center" bgcolor="DBDBDB"></td>
	  </tr>
	  <tr>
	    <td height="28" align="center" class="td3">公示</td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td align="center"><#if creditManage?? && creditManage.publicity??><a href="${(creditManage.publicity)?if_exists}"><img src="${resourcePath}/images/download.gif" width="16" height="16" style="cursor:pointer;" title="点击下载"/></a><#else></#if></td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td align="center" class="td3"><span class="td41">年度报告</span></td>
	    <td width="1" align="center" bgcolor="DBDBDB"></td>
	    <td align="center"><#if creditManage?? && creditManage.yearReport??><a href="${(creditManage.yearReport)?if_exists}"><img src="${resourcePath}/images/download.gif" width="16" height="16" style="cursor:pointer;" title="点击下载"/></a><#else></#if></td>
	  </tr>
	</table>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
</div>
		</td>
	  </tr>
	</table>
	</td>
  </tr>
</table>

<div id="uploadImageDiv" style="position:absolute; left:500px; display:none; left: 150px; top: 300px; font-size:13px;">
<form method="post" id="imageUploadForm" action="#" enctype="multipart/form-data" onsubmit="return checkForm();">
<input type="hidden" name="daImageFile.daCompany.id" value="${company.id}" />
  <table width="280" border="0" cellpadding="0" cellspacing="0" align="center" class="main_up">
  <tr>
    <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="dbsy_1" height="96">
      <tr>
        <td valign="top" align="center" style="padding:1px 0px"><table width="94%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="30" align="left" valign="bottom">图片上传：</td>
          </tr>
          <tr>
            <td height="30" align="left" valign="middle">
            <input type="file" id="daImageFile" name="daImageFile.file" size="34" contentEditable="false" onchange="checkExt(this)" />
            </td>
          </tr>
          <tr>
            <td height="30" align="center" valign="middle">
            	<input value="上传" type="submit" name="btnSubmit" style="background:url('${resourcePath}/img/map/bg1.gif');width:45px;height:20px;padding:0px;margin:0px;border:0px;" />&nbsp;
      			<input value="取消" type="button" name="btnCancel" style="background:url('${resourcePath}/img/map/bg1.gif');width:45px;height:20px;padding:0px;margin:0px;border:0px;" onclick="javascript:hideImageUpload()" />	
			</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
 </form>
</div>


<script language="javascript"> 
$(document).ready(function(){ 
	var inputVal = "";
	$('input:text').focus(function(){ 
		$(this).addClass('border');
		inputVal = $(this).val();
	}); 
	$('input:text').blur(function(){ 
		$(this).removeClass('border'); 
		$(this).removeClass('onborder'); 
		$(this).addClass('noborder'); 
		var entity = $(this).attr("entity");//获取对象的名称
		var name = $(this).attr("name");//获取input的名称
		var id = "";
		if (entity == 'DaCompany') {
			id = "${company.id}"; 
		}else {
			id = "${securityManage.id}";
		}
		if(inputVal != $(this).val()){
			$.ajax({
				type:"POST",
				url:"${base}/ajax/ajax_update.xhtml",
				data:"tableName="+ entity +"&id="+id+"&value="+ $(this).val() + "&fieldName="+name,
				//async:false,
				success:function(msg){
					if(!eval(msg)){
					alert("保存失败！");
					window.location.reload();
				}
				}
			});
		}
	}); 
	$('input:radio').click(function(){ 
		var entity = $(this).attr("entity");//获取对象的名称
		var name = $(this).attr("name");//获取input的名称
		$.ajax({
			type:"POST",
			url:"${base}/ajax/ajax_update.xhtml",
			data:"tableName="+ entity +"&id=${company.id}&value="+ $(this).val() + "&fieldName="+name,
			//async:false,
			success:function(msg){
				if(!eval(msg)){
					alert("保存失败！");
					window.location.reload();
				}
			}
		});
	}); 

	$('input:text').mouseover(function(){ 
		$(this).addClass('onborder'); //onborder
	}); 

	$('input:text').mouseout(function(){ 
		$(this).removeClass('onborder'); 
	}); 
}); 
</script>
<script type="text/javascript">
	function loadStandardOldAdvance(url,method){
		jQuery.ajax({
		   	url: url,
		   	data: "businessRegNum=${company.regNum}&method=" + method,
		   	type: "POST",
		   	dataType:"json",
		   	success: function(data){ 
		   		if(data.secondForm != null){
		   			$("#category").html('乙证');
		   			$("#licence").html(data.secondForm.licence);
		   			$("#certEnd").html(data.secondForm.certEnd);
		   			if(data.licenceScope != null){
			   			$("#chinaName").val(data.licenceScope.chinaName);
			   		}
			   		return;
		   		}
		   		if(data.companyInfo != null){
		   			$("#category").html('甲证');
		   			$("#licence").html(data.companyInfo.form2.licence);
		   			$("#certEnd").html(data.companyInfo.form2.certEnd);
		   			$("#chinaName").val(data.companyInfo.form2.chinaName);
		   			return;
		   		}
		   		if(data.companyInfo3 != null){
		   			$("#category").html('生产许可证');
		   			$("#licence").html(data.companyInfo3.form2.licence);
		   			$("#certEnd").html(data.companyInfo3.form2.certEnd);
		   			$("#chinaName").val(data.companyInfo3.form2.chinaName);
		   			return;
		   		}
		   		if(data.companyInfo4 != null){
		   			$("#category").html('生产储存批准书');
		   			$("#licence").html(data.companyInfo4.form2.licence);
		   			$("#certEnd").html(data.companyInfo4.form2.certEnd);
		   			$("#chinaName").val(data.companyInfo4.form2.chinaName);
		   			return;
		   		}
	   		}
		});
	}
	loadStandardOldAdvance('http://60.190.2.104:8082/nbwhp/second/newsecond.do', 'loadSecondByBgnAjax');
	loadStandardOldAdvance('http://60.190.2.104:8081/whp/proLicense/proLicense.do', 'loadProLicenseByBgnAjax');
	
	function loadRedirectUrl(id,siteId,url,method) {
	jQuery.ajax({
			   	url: "http://60.190.2.104/nbajj_colligation/siteIface/loadRedirectUrl.xhtml",
			   	data: "siteId=" + siteId,
			   	type: "POST",
			   	dataType:"json",
			   	success: function(data){ 
			   		data = data.replace(/\\/g,""); 
			   		url += "?businessRegNum=${company.regNum}$method=" + method;
					document.getElementById(id).src = data + "&url='" + url + "'";
		   		}
			});
	}
	loadRedirectUrl('iframe_nbsg',6,'nbsg/up/sgqk.do','count');
	loadRedirectUrl('iframe_whp',8,'whp/pla/pla.do','');
	
	//id:iframe的id
	function loadStandardOld(id,url,method){
		jQuery.ajax({
		   	url: url,
		   	data: "businessRegNum=${company.regNum}&method=" + method,
		   	type: "POST",
		   	dataType:"json",
		   	success: function(data){ 
		   		if(data != null){
		   			url += "?businessRegNum=${company.regNum}&method=loadSelfcheckByBgn";
					document.getElementById(id).src =url;
		   		}
	   		}
		});
	}
	//id:iframe的id
	function loadStandardNew(id,url){
		jQuery.ajax({
		   	url: url,
		   	data: "company.businessRegNum=${company.regNum}",
		   	type: "POST",
		   	dataType:"json",
		   	success: function(data){ 
		   		if(data != null){
			   		url = "http://60.190.2.104/standard/company/loadCompanyStandard.xhtml";
	   				url += "?company.businessRegNum=${company.regNum}";
					document.getElementById(id).src =url;
				}
	   		}
		});
	}
	loadStandardOld('iframe_standards','http://60.190.2.104:8083/jxbzh2/nselfcheck/nselfcheck.do', 'loadSelfcheckByBgnAjax');
	loadStandardOld('iframe_standards','http://60.190.2.104:8083/wlevel/nselfcheck/nselfcheck.do', 'loadSelfcheckByBgnAjax');
	loadStandardNew('iframe_standards','http://60.190.2.104/standard/company/loadCompanyStandardAjax.xhtml');
	
	function loadZdwxyInfo(companyName){
	$.get("../nbajj_colligation/statistic/isMhStatistic.xhtml",{mc: companyName},  function(result)
			  {
			  	var isMajorDangerousSource=document.getElementById("isMajorDangerousSource");
			  	var isKeepOnRecord=document.getElementById("isKeepOnRecord");
				if (result == true) {
					isMajorDangerousSource.innerHTML="有";
					isKeepOnRecord.innerHTML="已备案";
				} else {
					isMajorDangerousSource.innerHTML="无";
					isKeepOnRecord.innerHTML="未备案";
				}
			  });
	}
	window.onload=loadZdwxyInfo("${company.companyName}");
	
	
	//tag:1 上传 2：编辑
	function showImageUpload(tag, id){
		$("#uploadImageDiv").show();
		if(tag == 1){
			$("#imageUploadForm").attr("action", "../imageFile/createFile.xhtml");;
		}else{
			$("#imageUploadForm").attr("action", "../imageFile/updateFile.xhtml?daImageFile.id="+id);
		}
	}
	function hideImageUpload(){
		$("#uploadImageDiv").hide();
	}
	
</script>

</#escape> 
<@fkMacros.pageFooter />