<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
<#escape x as (x)!>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<@fkMacros.login_company_pageheader />
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<title>新用户注册</title>
<script type="text/javascript" src="resources/default/wbox1.0/wbox.js"></script> 
<link rel="stylesheet" type="text/css" href="resources/default/wbox1.0/wbox/wbox.css" />
<style>
/*通用列表样式*/
.tab_list{  border-collapse:collapse;}
.tab_list thead tr td{ font-weight:bold; font-size:16px; background:#ebebeb} /*表格头*/
.tab_list tbody tr td{ font-size:14px;}
.tab_list td.title{ background:#ebebeb; list-style:40px; text-align:center;}
.tab_list td.title_left{ background:#ebebeb; list-style:40px; text-align:left; font-weight:bold;}
.tab_list tr.title_list{ background:#f6f6f6; font-size:16px; list-style:40px; text-align:center;}
.tab_list td{ line-height:30px; border:1px solid #dcdcdc; padding:0px 5px;  font-family:"Microsoft YaHei"; }
.tab_list td table td{ border:none;}
.tab_list td.pad_top_bot_10{ padding:10px 0px;}
.tips{ color:#e95605;}

.btn{ width:120px;cursor:hand; height:40px; font-size:15px; font-family:"Microsoft YaHei"; text-align:center; color:#fff; background:#096fd8; border:1px solid #096fd8;}
.input_validation{ width:300px; height:30px; line-height:30px; border:1px solid #959191;}
.input_tips{ margin-top:10px; display:none;}
</style>

<script language="javascript">

function setUser(name){
	
	parent.wbox.close();
	parent.document.getElementById("userName").value=name;
	//parent.document.getElementById("userPass").style.disabled=false;
	
	
	//parent.setUser(name);
}
function submitCreate(){


	document.getElementById("to_reg").disabled =true;

    if($("#companyName").val()==""){
        alert("单位名称不能为空！");
    	return false;
    }
    if($("#second-area").val()==""){
        alert("二级区域不能为空！");
    	return false;
    }
    
	$.ajax({
			type:"post",
			method:"post",
			dataType:"json",
			data:{"corecompany.flag":$('input:radio[name="flag"]:checked').val(),"corecompany.safetyMngPersonPhone":$("#safetyMngPersonPhone").val(),"corecompany.safetyMngPerson":$("#safetyMngPerson").val(),"corecompany.nationalEconomicType2":$("#nationalEconomicType2").val(),"corecompany.nationalEconomicType1":$("#nationalEconomicType1").val(),"corecompany.businessScope":$("#businessScope").val(),"corecompany.establishmentDay":$("#establishmentDay").val(),"corecompany.regAddress":$("#regAddress").val(),"corecompany.businessRegNum":$("#businessRegNum").val(),"corecompany.orgCode":$("#orgCode").val(),"corecompany.companyName":$("#companyName").val(),"corecompany.secondArea":$("#second-area").val(),"corecompany.thirdArea":$("#third-area").val(),"corecompany.fouthArea":$("#fouth-area").val(),"corecompany.uuid":$("#uuid").val(),"corecompany.legalPerson":$("#legalPerson").val()  ,"corecompany.businessAddress":$("#businessAddress").val(),"corecompany.phone":$("#phone").val(),"corecompany.productionScale":$("#productionScale").val(),"corecompany.enterprise":$("#enterprise").val(),"corecompany.managementLevel1":$("#managementLevel1").val(),"corecompany.managementLevel2":$("#managementLevel2").val(),"corecompany.economicType1":$("#economicType1").val(),"corecompany.economicType2":$("#economicType2").val(),"corecompany.principalPerson":$("#principalPerson").val(),"corecompany.legalTelephone":$("#legalTelephone").val(),"corecompany.principalTelephone":$("#principalTelephone").val(),"corecompany.principalMobile":$("#principalMobile").val()},
			url:"registerCompany.xhtml",
			success:function(data){
				if(data && data.flag){
					//alert(data.flag);
					$("#result").show();
					$("#info").html(data.flag);
					$("#dw").hide();
				}else{
					//alert("该企业不存在中心库中，请联系当地乡镇安监站（所）查询和添加帐号！");
					$("#dw").hide();
					$("#result").show();
					$("#info").html("该企业不存在中心库中，请联系当地乡镇安监站（所）查询和添加帐号");
				}
			}
	});
}

$(function(){
	$("#to_save").click(function(){
	
		if ($("#ncode").val()==""){
		 var flag=$('input:radio[name="flag"]:checked').val(); 
			if (flag==1){
				alert("必须输入统一社会信用代码（工商注册号）！");
			}else if (flag=="2"){
				alert("必须输入组织机构代码！");
			}
			//$("#ncode").focus();
			return false;
		}
		
		$("#dw").hide();
		$("#companyName").val("");
		$("#businessRegNum").val("");
		$("#orgCode").val("");
		$("#uuid").val("");
		$("#legalPerson").val("");
		$("#second-area").val("");
		$("#third-area").val("");
		$("#fouth-area").val("");
		
		$("#businessAddress").val("");
		$("#phone").val("");
		$("#productionScale").val("");
		$("#enterprise").val("");
		$("#managementLevel1").val("");
		$("#managementLevel2").val("");
		
			
	    $("#principalPerson").val("");
		$("#legalTelephone").val("");
		$("#principalTelephone").val("");
		$("#principalMobile").val("");	
		
		$.ajax({
			type:"post",
			method:"post",
			dataType:"json",
			data:{"corecompany.flag":$('input:radio[name="flag"]:checked').val(),"corecompany.businessRegNum":$("#ncode").val(),"corecompany.orgCode":$("#ncode").val()},
			url:"loadCheckCompany.xhtml",
			success:function(data){
				if(data && data.companyName){
					$("#companyName").val(data.companyName);
					$("#businessRegNum").val(data.businessRegNum);
					$("#orgCode").val(data.orgCode);
					$("#uuid").val(data.uuid);
					$("#legalPerson").val(data.legalPerson);
					
					$("#businessAddress").val(data.businessAddress);
					$("#phone").val(data.phone);
					$("#productionScale").val(data.productionScale);
					if(data.enterprise==null){
					  $("#enterprise").val("0");
					}else{
					  $("#enterprise").val(data.enterprise);
					}
					$("#managementLevel1").val(data.managementLevel1);
					$("#managementLevel2").val(data.managementLevel2);
					
					if(data && data.secondArea){
						$("#second-area").val(data.secondArea);
						initNextAreaSelect(data.secondArea,"third-area","second-area","third-area");
					}
					if(data && data.thirdArea){
						$("#third-area").val(data.thirdArea);
						initNextAreaSelect(data.thirdArea,"fouth-area","third-area","fouth-area");
					}
					if(data && data.fouthArea){
						$("#fouth-area").val(data.fouthArea);
						initNextAreaSelect(data.fouthArea,"fifth-area","fouth-area","fifth-area");
					}
					
					$("#safetyMngPersonPhone").val(data.safetyMngPersonPhone);
					$("#safetyMngPerson").val(data.safetyMngPerson);
					$("#focusFireUnits").val(data.focusFireUnits);
					$("#establishmentDay").val(data.establishmentDay);
					$("#businessScope").val(data.businessScope);
					$("#regAddress").val(data.regAddress);
					$("#nationalEconomicType1").val(data.nationalEconomicType1);
					$("#nationalEconomicType2").val(data.nationalEconomicType2);
					$("#economicType1").val(data.economicType1);
					$("#economicType2").val(data.economicType2);
					
					
					$("#principalPerson").val(data.principalPerson);
					$("#legalTelephone").val(data.legalTelephone);
					$("#principalTelephone").val(data.principalTelephone);
					$("#principalMobile").val(data.principalMobile);	
					
					$("#dw").show();
					$("#result").hide();
				}else{
					$("#result").show();
					$("#info").html("该企业不存在中心库中，请联系当地乡镇安监站（所）查询和添加帐号");
				}
			}
		});
	});
	

});


$(function(){ 
	$(".input_validation").mouseenter(function(){
	   $(".input_tips").show("slow");

	});
	
	 $(".input_tips").mouseleave(function(){
	   $(this).hide("slow");
	});
 })
</script>


</head>

<body>
<form id="form1" name="form1" method="post" action="">
	<input id="uuid" name="uuid"  type="hidden" >
 	<input id="businessRegNum" name="businessRegNum"  type="hidden" >
 	<input id="orgCode" name="orgCode"  type="hidden" >
 	<input id="legalPerson" name="legalPerson"  type="hidden" >
 	
 	<input id="businessAddress" name="businessAddress"  type="hidden" >
 	<input id="phone" name="phone"  type="hidden" >
 	<input id="productionScale" name="productionScale"  type="hidden" >
 	<input id="enterprise" name="enterprise"  type="hidden" >
 	<input id="managementLevel1" name="managementLevel1"  type="hidden" >
 	<input id="managementLevel2" name="managementLevel2"  type="hidden" >
 	
 	<input id="safetyMngPerson" name="safetyMngPerson"  type="hidden" >
 	<input id="safetyMngPersonPhone" name="safetyMngPersonPhone"  type="hidden" >
 	<input id="focusFireUnits" name="focusFireUnits"  type="hidden" >
 	<input id="establishmentDay" name="establishmentDay"  type="hidden" >
 	<input id="businessScope" name="businessScope"  type="hidden" >
 	<input id="regAddress" name="regAddress"  type="hidden" >
 	<input id="nationalEconomicType1" name="nationalEconomicType1"  type="hidden" >
 	<input id="nationalEconomicType2" name="nationalEconomicType2"  type="hidden" >
 	<input id="economicType1" name="economicType1"  type="hidden" >
 	<input id="economicType2" name="economicType2"  type="hidden" >
 	
 	<input id="principalPerson" name="principalPerson"  type="hidden" >
 	<input id="legalTelephone" name="legalTelephone"  type="hidden" >
 	<input id="principalTelephone" name="principalTelephone"  type="hidden" >
 	<input id="principalMobile" name="principalMobile"  type="hidden" >
 	
<table width="100%" class="tab_list">
  <tr>
    <td align="center"><input name="flag"   value="1"  type="radio"  checked="checked" />      统一社会信用代码（工商注册号）</td>
    <td align="center"><input type="radio" name="flag"   value="2"  />      组织机构代码</td>
  </tr>
  <tr>
    <td colspan="2" align="center" class="pad_top_bot_10"> 
      <input type="text" id="ncode" name="fkUser.userName"  class="input_validation" />
      <div class="input_tips">请使用本单位统一社会信用代码（工商注册号）进行注册，无统一社会信用代码（工商注册号），用组织机构代码</div>
   </td>
  </tr>
  <tr>
    <td colspan="2" align="center" class="pad_top_bot_10"><input type="button" name="to_save" id="to_save"  value="验证" class="btn"  /></td>
  </tr>
</table>

<table width="100%" class="tab_list" id="result"   style="display:none">
  <thead>
    <tr>
      <td >验证结果如下：</td>
    </tr>
  </thead>
  
  <tr>
    <td class="tips"  id="info"></td>
  </tr>
</table>

<table width="100%" class="tab_list"  id="dw"   style="display:none">
  <tr >
    <td align="center" class="pad_top_bot_10"><table width="90%" >
      <tr >
        <td width="19%">单位名称</td>
        <td width="81%" align="left"><span class="pad_top_bot_10">
          <input id="companyName" name="fkUserInfo.userCompany" type="text" class="input_validation"  />
        </span></td>
      </tr>
      <tr >
        <td>所在区域</td>
        <td align="left"><div id="div-area"></div></td>
      </tr>
    </table></td>
    </tr >
  <tr >
    <td align="center" class="pad_top_bot_10"><input type="button" name="to_reg" id="to_reg"   value="注册" class="btn"  onclick="javascript:submitCreate();" /></td>
  </tr>
</table>

</form>
</body>
</html>
<@fkMacros.initAreaXML />
<@fkMacros.selectAreas_fun "330200000000" "0" "0" "0" "0" />
</#escape> 
<@fkMacros.pageFooter />