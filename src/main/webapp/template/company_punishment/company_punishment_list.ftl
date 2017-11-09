<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script type="text/javascript" src="../resources/default/js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>

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
		<div class="Current">当前功能：行政处罚文书</div>
	</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list" id="table_color">
  <tr>
    <th width="10%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="45%" nowrap>时间</th>
    <th width="45%" nowrap>名称</th>
  </tr>
   <#if companiePunishments1?exists>
  <#list companiePunishments1?if_exists as c>
  <tr>
    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
    <td nowrap>${c.punishTime}&nbsp;</td>
    <td nowrap>${c.punishType}&nbsp;</td>
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