<@fkMacros.pageHeader />
<@fkMacros.company_pageheader />
<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
<#escape x as (x)!>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="cn.safetys.constant.SystemConstant"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8" %>
<% 
if(SystemConstant.P!=null
		&&!SystemConstant.P.isEnterprise()
		&&SystemConstant.P.isGovernment()){
	response.sendRedirect(request.getContextPath()+"/login_zf.jsp");
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<title>新用户注册</title>
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

.btn{ width:120px; height:40px; font-size:15px; font-family:"Microsoft YaHei"; text-align:center; color:#fff; background:#096fd8; border:1px solid #096fd8;}
.input_validation{ width:300px; height:30px; line-height:30px; border:1px solid #959191;}
.input_tips{ margin-top:10px; display:none;}
</style>


<script language="javascript" src="resources/default/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
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
 	
 	
 	<input id="safetyMngPerson" name="safetyMngPerson"  type="hidden" >
 	<input id="safetyMngPersonPhone" name="safetyMngPersonPhone"  type="hidden" >
 	<input id="focusFireUnits" name="focusFireUnits"  type="hidden" >
 	<input id="establishmentDay" name="establishmentDay"  type="hidden" >
 	<input id="businessScope" name="businessScope"  type="hidden" >
 	<input id="regAddress" name="regAddress"  type="hidden" >
 	<input id="nationalEconomicType1" name="nationalEconomicType1"  type="hidden" >
 	<input id="nationalEconomicType2" name="nationalEconomicType2"  type="hidden" >
 	
<table width="100%" class="tab_list">
<!-- <thead>
  <tr>
    <td colspan="2" align="center">新用户注册</td>
  </tr>
  </thead>-->
  <tr>
    <td align="center"><input name="flag"   value="1"  type="radio"  checked="checked" />      统一社会信用代码（工商注册号）</td>
    <td align="center"><input type="radio" name="flag"   value="2"  />      组织机构代码</td>
  </tr>
  <tr>
    <td colspan="2" align="center" class="pad_top_bot_10">
      <input type="text" id="ncode" name="fkUser.userName"  class="input_validation" />
      <div class="input_tips">请使用本单位统一社会信用代码（工商注册号）进行注册，如无统一社会信用代码（工商注册号），再使用组织机构代码</div>

   </td>
  </tr>
  <tr>
    <td colspan="2" align="center" class="pad_top_bot_10"><input type="submit" name="to_save" id="to_save"  value="验证" class="btn" /></td>
  </tr>

  
</table>


<table width="100%" class="tab_list">
  <thead>
    <tr>
      <td class="title">验证结果如下：</td>
    </tr>
  </thead>
  
  <tr>
    <td class="tips">该企业不存在中心库中，请联系当地乡镇安监站（所）查询和添加帐号！</td>
  </tr>
</table>

<table width="100%" class="tab_list">
<thead>
  <tr>
    <td class="title">验证结果如下：</td>
    </tr>
    </thead>
  <tr>
    <td align="center" class="pad_top_bot_10"><table width="90%" >
      <tr  id="dw"  style="display:none">
        <td width="19%">单位名称</td>
        <td width="81%" align="left"><span class="pad_top_bot_10">
          <input id="companyName" name="fkUserInfo.userCompany" type="text" class="input_validation"  />
        </span></td>
      </tr>
      <tr id="dq"  style="display:none">
        <td>所在地区</td>
        <td align="left"><div id="div-area"></div></td>
      </tr>
    </table></td>
    </tr>
  <tr id="zc"  style="display:none">
    <td align="center" class="pad_top_bot_10"><input type="submit" name="button2" id="button2" value="注册" class="btn"  onclick="javascript:submitCreate();" /></td>
  </tr>
</table>

</form>
</body>
</html>

<script language="javascript">
function submitCreate(){
	alert("注册");
    if($("#companyName").val()==""){
        alert("单位名称不能为空！");
    	return false;
    }
    if($("#second-area").val()==""){
        alert("二级区域不能为空！");
    	return false;
    }
    
    
	alert("注册");
      
	$.ajax({
			type:"post",
			method:"post",
			dataType:"json",
			data:{"corecompany.flag":$('input:radio[name="flag"]:checked').val(),"corecompany.safetyMngPersonPhone":$("#safetyMngPersonPhone").val(),"corecompany.safetyMngPerson":$("#safetyMngPerson").val(),"corecompany.nationalEconomicType2":$("#nationalEconomicType2").val(),"corecompany.nationalEconomicType1":$("#nationalEconomicType1").val(),"corecompany.businessScope":$("#businessScope").val(),"corecompany.establishmentDay":$("#establishmentDay").val(),"corecompany.regAddress":$("#regAddress").val(),"corecompany.businessRegNum":$("#businessRegNum").val(),"corecompany.orgCode":$("#orgCode").val(),"corecompany.companyName":$("#companyName").val(),"corecompany.secondArea":$("#second-area").val(),"corecompany.thirdArea":$("#third-area").val(),"corecompany.uuid":$("#uuid").val(),"corecompany.legalPerson":$("#legalPerson").val()},
			url:"registerCompany.xhtml",
			success:function(data){
				if(data && data.flag){
					alert(data.flag);
				}else{
					alert("该企业不存在中心库中，请联系当地乡镇安监站（所）查询和添加帐号！");
					$("#dw").hide();
					$("#dq").hide();
					$("#zc").hide();
				}
			}
	});
}

$(document).ready(function(){ 
	$("#to_save").click(function(){
		if ($("#ncode").val()==""){
		 var flag=$('input:radio[name="flag"]:checked').val(); 
			if (flag==1){
				alert("必须输入统一社会信用代码（工商注册号）！");
			}else if (flag=="2"){
				alert("必须输入组织机构代码！");
			}
			$("#ncode").focus();
			return false;
		}
		
		$("#companyName").val("");
		$("#businessRegNum").val("");
		$("#orgCode").val("");
		$("#uuid").val("");
		$("#legalPerson").val("");
		$("#second-area").val("");
		$("#third-area").val("");
		
		
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
					if(data && data.secondArea){
						$("#second-area").val(data.secondArea);
						initNextAreaSelect(data.secondArea,"third-area","second-area","third-area");
					}
					if(data && data.thirdArea){
						$("#third-area").val(data.thirdArea);
						initNextAreaSelect(data.thirdArea,"fouth-area","third-area","fouth-area");
					}
					
					
					alert(data.regAddress);
					$("#safetyMngPersonPhone").val(data.safetyMngPersonPhone);
					$("#safetyMngPerson").val(data.safetyMngPerson);
					$("#focusFireUnits").val(data.focusFireUnits);
					$("#establishmentDay").val(data.establishmentDay);
					$("#businessScope").val(data.businessScope);
					$("#regAddress").val(data.regAddress);
					$("#nationalEconomicType1").val(data.nationalEconomicType1);
					
					$("#nationalEconomicType2").val(data.nationalEconomicType2);
					
					
					
					
					$("#dw").show();
					$("#dq").show();
					$("#zc").show();
				}else{
					alert("该企业不存在中心库中，请联系当地乡镇安监站（所）查询和添加帐号！");
				}
			}
		});
	});
});

</script>

<@fkMacros.initAreaXML />
<@fkMacros.selectAreas_fun "330200000000" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" />
</#escape> 
<@fkMacros.pageFooter />