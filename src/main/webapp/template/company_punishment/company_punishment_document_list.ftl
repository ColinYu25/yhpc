<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script type="text/javascript" src="../resources/default/js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
<style>
table{ font-size:12px; font-family:Arial, Helvetica, sans-serif}
<!--.line{ border-top: solid 1px #b5d6e7;border-left: solid 1px #b5d6e7}-->
.line{ margin:1px 0}
.line td{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #b5d6e7; border-right:1px solid #b5d6e7; }
.line th{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #b5d6e7; border-right:1px solid #b5d6e7;color:#0e4a8c; background-color:#e8f2fb }
#h1{font-size:20px; color:#FFFFFF; font-weight:normal; background-color:#5a94cb; font-family:黑体}
.h2 th{font-size:12px; font-weight:bold; background-color:#FFFFFF}
#h3{ font-size:14px;background-color:#e8f2fb}

#line7{ border-left: solid 1px #6daac2;border-top: solid 1px #6daac2;}
#line7 td{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #6daac2; border-right:1px solid #6daac2; }
#line7 th{ padding:5px;border-left:1px solid #fff; border-top:1px solid #fff; border-bottom:1px solid #6daac2; border-right:1px solid #6daac2;color:#0e4a8c; background-color:#e8f2fb }
</style>
<style type="text/css">
<!--
.noborder {border: 0;}
.border { border: solid 1px #7f9db9}
.onborder{background-color: #D8DBFE;
	border-top:solid 1px #D8E3EB;
	border-left:solid 1px #D8E3EB;
	border-bottom:solid 1px #0080C0;
	border-right:solid 1px #0080C0;} 
-->
</style>
<div class="con_box">

	<div class="Current_bg margin2">
		<div class="Current">当前功能：执法检查</div>
	</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="10%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="20%" nowrap>时间</th>
    <th width="20%" nowrap>名称</th>
    <th width="25%" nowrap>执法地址</th>
    <th width="25%" nowrap>执法人</th>
  </tr>
  <#if companiePunishmentDocuments?exists>
  <#list companiePunishmentDocuments?if_exists as c>
  <tr>
    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
    <td nowrap>${c.documentTime}&nbsp;</td>
    <td nowrap>${c.documentName}&nbsp;</td>
    <td nowrap>${c.documentAdd}&nbsp;</td>
    <td nowrap>${c.documentPeople}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 <tr>
	<td height="5"></td>
 </tr>
 <tr>
	<td class="btn">
		<input type="button" value=" 返 回 "  onClick="history.go(-1)" />
	</td>
 </tr>
</table>
<script language="javascript"> 
$(document).ready(function(){ 
	$('input').focus(function(){ 
	$(this).addClass('border'); 
	}); 

	$('input').blur(function(){ 
	$(this).removeClass('border'); 
	$(this).removeClass('onborder'); 
	$(this).addClass('noborder');
	$.get("../companyPunishment/updateCompanyPunishment.xhtml", function(result){
    }); 
	}); 

	$('input').mouseover(function(){ 
	$(this).addClass('onborder'); 
	}); 

	$('input').mouseout(function(){ 
	$(this).removeClass('onborder'); 
	}); 
}); 
</script> 

<script type="text/javascript">
  

</div>
</#escape>            
<@fkMacros.pageFooter />