<@fkMacros.pageHeader />
<#escape x as (x)!> 
<@enum.initAreaXML/>
<style>
table{ font-size:12px; font-family:Arial, Helvetica, sans-serif}
<!--.line{ border-top: solid 1px #DBDBDB;border-left: solid 1px #DBDBDB}-->
.line{ margin:1px 0}
.line td{ padding:3px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB; cursor:pointer}
.line th{ padding:3px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB;color:#0e4a8c; background-color:#e8f2fb }
#h1{height:36px; font-size:16px;  font-weight:bold; text-align:center; color:#FFFFFF;  background-color:#7193D8; font-family:宋体}
.h2 th{font-size:12px; font-weight:bold; background-color:#FFFFFF}
#h3{ font-size:12px;background-color:#e8f2fb}
#h4{height:32px; font-size:14px;  font-weight:bold; text-align:left; color:#333333;  padding-left:20px; background-color:#C7D5F1; font-family:宋体}
#line1{ border-left: solid 1px #DBDBDB;border-top: solid 1px #DBDBDB;}
#line1 td{ padding:3px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB; cursor:pointer}
#line1 th{ padding:3px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB;color:#0e4a8c; background-color:#e8f2fb }

#line3{ border-left: solid 1px #DBDBDB;border-top: solid 1px #DBDBDB;}
#line3 td{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB; cursor:pointer}
#line3 th{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB;color:#0e4a8c; background-color:#e8f2fb }

#line5{ border-left: solid 1px #DBDBDB;border-top: solid 1px #DBDBDB;}
#line5 td{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB; cursor:pointer}
#line5 th{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB;color:#0e4a8c; background-color:#e8f2fb }

#line7{ border-left: solid 1px #DBDBDB;border-top: solid 1px #DBDBDB;}
#line7 td{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB; cursor:pointer}
#line7 th{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB;color:#0e4a8c; background-color:#e8f2fb }

#line9{ border-left: solid 1px #DBDBDB;border-top: solid 1px #DBDBDB;}
#line9 td{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB; cursor:pointer}
#line9 th{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #DBDBDB; border-right:1px solid #DBDBDB;color:#0e4a8c; background-color:#e8f2fb }

.table03{ text-align:center; background:#FFF;}
.table03 td{ border-bottom:1px solid #DBDBDB;}
.td1{ height:28px; font-weight:bold; border-right:0px solid #DBDBDB; color:#394C86;padding-right:0px;}
.td2{ height:28px;color:#333333;text-align:left;}
.td3{ height:28px;color:#394C86;text-align:center;}
.td4{ height:28px;color:#333333;text-align:center;}
.td6{ height:28px;color:#FF0000;text-align:center;}
.input { height:24px;
	BORDER-BOTTOM: #DBDBDB 1px solid; BORDER-LEFT: #DBDBDB 1px solid; PADDING-BOTTOM: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; BACKGROUND: #f6f6f6; BORDER-TOP: #DBDBDB 1px solid; BORDER-RIGHT: #DBDBDB 1px solid; PADDING-TOP: 0px; -moz-border-radius: 0px; -webkit-border-radius: 0px
}
/*
#line1{ border: solid 1px #8fcdff;}
#line2{ border: solid 1px #ca8eff;}
#line3{ border: solid 1px #f69ec8;}
#line4{ border: solid 1px #ffcf94;}
#line5{ border: solid 1px #c5f784;}
#line6{ border: solid 1px #8dffcb;}
#line7{ border: solid 1px #8fcdff;}
#line8{ border: solid 1px #ca8eff;}
#line9{ border: solid 1px #f69ec8;}
#line10{ border: solid 1px #ffcf94;}*/

.noborder {border: 0;}
.border { border: solid 1px #7f9db9}
.onborder{background-color: #D8DBFE;
border-top:solid 1px #D8E3EB;
border-left:solid 1px #D8E3EB;
border-bottom:solid 1px #0080C0;
border-right:solid 1px #0080C0;} 
</style>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<div style=" width:560px; height:auto;" margin:0 auto">
  <table width="560" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="4" align="center" id="h1" ><span>生产经营单位安全生产基本情况表</span></th>
  </tr>
  <tr>
    <th colspan="4" align="center" id="h4" >企业基本情况</th>
  </tr>
</table>
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0"  class="table03">
    <tr>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">企业名称</td>
      <td width="196" align="left" class="td2">${company.companyName}</td>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">地 址</td>
      <td width="196" align="left" class="td2">${company.regAddress}</td>
    </tr>
    <tr>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">所属区域</td>
      <td width="196" align="left" class="td2"><#if company.firstArea??><@enum.getSelectArea '${company.firstArea}' />&nbsp;&nbsp;</#if>
		<#if company.secondArea??><@enum.getSelectArea '${company.secondArea}' />&nbsp;&nbsp;</#if>
		<#if company.thirdArea??><@enum.getSelectArea '${company.thirdArea}' />&nbsp;&nbsp;</#if></td>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">所属行业</td>
      <td width="196" align="left" class="td2"><#if tradeTypes??><#list tradeTypes as t>${t.daIndustryParameter.name}&nbsp;&nbsp;</#list><#else>&nbsp;</#if></td>
    </tr>
    <tr>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">主要负责人</td>
      <td width="196" align="left" class="td2">${company.fddelegate?default('&nbsp;')} </td>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">单位联系电话</td>
      <td width="196" align="left" class="td2"><input id="phoneCode" name="phoneCode" value="${company.phoneCode}" entity="DaCompany" type="text" class="noborder"/></td>
    </tr>
    <tr>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">统一社会信用代码（工商注册号）</td>
      <td width="196" align="left" class="td2"><input id="regNum" name="regNum" value="${company.regNum}" entity="DaCompany" type="text" class="noborder"/></td>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">组织机构代码</td>
      <td width="196" align="left" class="td2"><input id="setupNumber" name="setupNumber" value="${company.setupNumber}" entity="DaCompany" type="text" class="noborder"/></td>
    </tr>
    <tr>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">规模以上<br>工业企业</td>
      <td width="196" align="left" class="td2"><#if company?if_exists.daCompanyPass?if_exists.enterprise?exists && company.daCompanyPass.enterprise>
    <input type="radio" name="enterprise" value="true" checked="true" entity="DaCompanyPass"/>是
    <input type="radio" name="enterprise" value="false" entity="DaCompanyPass"/>否
    <#else>
    <input type="radio" name="enterprise" value="true" entity="DaCompanyPass"/>是
    <input type="radio" name="enterprise" value="false" checked="true" entity="DaCompanyPass"/>否
	</#if> </td>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">职工人数</td>
      <td width="196" align="left" class="td2"><input id="employeeNumber" name="employeeNumber" value="${company.employeeNumber}" entity="DaCompany" type="text" class="noborder"/> 人 </td>
    </tr>
    <tr>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">是否重点<br>消防单位</td>
      <td width="196" align="left" class="td2"><input type="radio" name="focusFireUnits" value="1" <#if company?if_exists.focusFireUnits??&&company.focusFireUnits == 1>checked="true"</#if> entity="DaCompany"/>是
    		<input type="radio" name="focusFireUnits" value="0" <#if company?if_exists.focusFireUnits??&&company.focusFireUnits != 1>checked="true"</#if> entity="DaCompany"/>否  </td>
      <td width="84" align="center" valign="middle" bgcolor="E8F2FB" class="td1">经营范围</td>
      <td width="196" align="left" class="td2"><input id="businessScope" name="businessScope" value="${company.businessScope}" entity="DaCompany" type="text" class="noborder"/></td>
    </tr>
  </table>
  <table width="560" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <th colspan="4" align="center" id="h4" >安全生产基本状况</th>
    </tr>
  </table>
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="100" rowspan="5" align="center" bgcolor="E8F2FB"><span class="td1">安管机构<br/>和人员</span></td>
      <td width="94" height="28" align="center"><span class="td3">安全机构设置</span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td colspan="3" align="center"><span class="td4"><input name="smSet" size="15" value="${(securityManage.smSet)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="146" align="center"><span class="td3">专职人员数</span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td colspan="3" align="center"><input name="fulltimeStaff" size="15" value="${(securityManage.fulltimeStaff)?if_exists}" type="text" entity="DaSecurityManage" class="noborder"/> 人</td>
    </tr>
    <tr>
      <td height="1" colspan="11" align="center" bgcolor="DBDBDB"></td>
    </tr>
    <tr>
      <td width="94" height="28" align="center"><span class="td3">主要负责人</span></td>
      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
      <td width="67" align="center"><span class="td4"><input name="mainPrincipal" size="8" value="${(securityManage.mainPrincipal)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="50" align="center"><span class="td4">证书号</span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td align="center"><span class="td4"><input name="mpCertificate" size="8" value="${(securityManage.mpCertificate)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="44" align="center"><span class="td4">有效期</span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="70" align="center"><span class="td7"><input name="mpValidity" size="8" value="${(securityManage.mpValidity)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
    </tr>
    <tr>
      <td height="1" colspan="11" align="center" bgcolor="DBDBDB"></td>
    </tr>
    <tr>
      <td height="28" align="center"><span class="td3">安全负责人</span></td>
      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
      <td width="67" align="center"><span class="td4"><input name="securityPrincipal" size="8" value="${(securityManage.securityPrincipal)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="50" align="center"><span class="td4">证书号</span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td align="center"><span class="td4"><input name="spCertificate" size="8" value="${(securityManage.spCertificate)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="44" align="center"><span class="td4">有效期</span></td>
      <td width="1" align="center" bgcolor="DBDBDB"></td>
      <td width="70" align="center"><span class="td7"><input name="spValidity" size="8" value="${(securityManage.spValidity)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></span></td>
    </tr>
  </table>
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  <!--
  <iframe id="iframe_whp" align="center" width="100%" height="56" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src=""></iframe>
  -->
  	<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td width="84" rowspan="5" align="center" bgcolor="E8F2FB"><span class="td1">安全生产<br />许可证</span></td>
		 </tr>
	    <tr>
	      <td width="94" height="28" align="center"><span class="td3">种类</span></td>
	      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
	      <td width="67" align="center" id="category">&nbsp;</td>
	      <td width="1" align="center" bgcolor="DBDBDB"></td>
	      <td width="50" align="center"><span class="td4">证书号</span></td>
	      <td width="1" align="center" bgcolor="DBDBDB"></td>
	      <td width="146" align="center" id="licence">&nbsp;</td>
	      <td width="1" align="center" bgcolor="DBDBDB"></td>
	      <td width="44" align="center"><span class="td4">有效期</span></td>
	      <td width="1" align="center" bgcolor="DBDBDB"></td>
	      <td width="70" align="center" id="certEnd">&nbsp;</td>
		 </tr>
	    <tr>
	      <td height="1" colspan="11" align="center" bgcolor="DBDBDB"></td>
	    </tr>
	    <tr>
	      <td height="28" align="center"><span class="td3">许可范围</span></td>
	      <td width="1" height="28" align="center" bgcolor="DBDBDB"></td>
	      <td colspan="9" align="left"><span>
	        &nbsp;&nbsp;<textarea class="input" rows="2" cols="44" name="textarea" id="chinaName">最多不超过500字</textarea>
	      </span></td>
	    </tr>
	  </table>
			  
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  
  <iframe width="100%" align="center" onload="this.height=30" id="iframe_standards" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src="../map/loadNullStandard.xhtml"></iframe>
 
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td height="1" bgcolor="DBDBDB"></td>
	    </tr>
  	</table>
	
        <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="84" height="28" align="center" bgcolor="E8F2FB"><span class="td1">重大危险源</span></td>
            <td align="center" id="isMajorDangerousSource">&nbsp;</td>
            <td width="1" align="center" bgcolor="DBDBDB"></td>
            <td width="146" align="center"><span class="td3">备案情况</span></td>
            <td width="1" align="center" bgcolor="DBDBDB"></td>
            <td width="115" align="center" id="isKeepOnRecord">&nbsp;</td>
          </tr>
        </table>
	    <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
        <iframe id="iframe_succor" align="center" width="100%" height="28" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src="http://127.0.0.1:86/succor/manager/manager_view.xhtml?regNum=${company.regNum}"></iframe>
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
        <iframe id="iframe_nbyhpc" align="center" width="100%" height="28" frameborder=0 scrolling=NO src="../danger/loadDangercount.xhtml?company.id=${company.id}"></iframe>
  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
  <iframe id="iframe_nbxzzf" align="center" width="100%" height="28" frameborder=0 scrolling=NO src="../companyPunishment/loadCompanyPunishmentsinfo.xhtml?company.id=${company.id}"></iframe>
         <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
			  <td height="1" bgcolor="DBDBDB"></td>
			</tr>
		  </table>
		 <table width="560" border="0" align="center" cellpadding="0" cellspacing="0"> 
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
          <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
		      <td height="1" bgcolor="DBDBDB"></td>
		    </tr>
  		 </table>
  		  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
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
	    <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="1" bgcolor="DBDBDB"></td>
    </tr>
  </table>
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
	loadStandardOldAdvance('http://127.0.0.1:8089/nbwhp/second/newsecond.do', 'loadSecondByBgnAjax');
	loadStandardOldAdvance('http://127.0.0.1:8089/whp/proLicense/proLicense.do', 'loadProLicenseByBgnAjax');
	
	function loadRedirectUrl(id,siteId,url,method) {
	jQuery.ajax({
			   	url: "http://127.0.0.1:82/nbajj_colligation/siteIface/loadRedirectUrl.xhtml",
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
			   		url = "http://127.0.0.1:83/standard/company/loadCompanyStandard.xhtml";
	   				url += "?company.businessRegNum=${company.regNum}";
					document.getElementById(id).src =url;
				}
	   		}
		});
	}
	loadStandardOld('iframe_standards','http://127.0.0.1:8089/jxbzh/nselfcheck/nselfcheck.do', 'loadSelfcheckByBgnAjax');
	loadStandardOld('iframe_standards','http://127.0.0.1:8089/wlevel/nselfcheck/nselfcheck.do', 'loadSelfcheckByBgnAjax');
	loadStandardNew('iframe_standards','http://127.0.0.1:83/standard/company/loadCompanyStandardAjax.xhtml');
	
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
	
</script>

</#escape> 
<@fkMacros.pageFooter />