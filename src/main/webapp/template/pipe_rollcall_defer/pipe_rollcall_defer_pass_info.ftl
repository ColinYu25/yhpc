<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#assign url='updateRollcallDeferForPass.xhtml'>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("rollcallDeferForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}

</script>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<@fkMacros.formValidator 'rollcallDeferForm' />
<form id="rollcallDeferForm" name="rollcallDeferForm" action="" method="post">
<table width="97.7%" border="0"  cellpadding="0" cellspacing="0"  style="background-image:url(${resourcePath}/images/bk.jpg);">
  <tr>
  <td align="center">
  <table width="570" height="250" border="0" cellpadding="0" cellspacing="0">
  <tr><td><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患治理<br>延期审批意见书</strong></td></tr>
  <tr><td height="50">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
    <span style="text-decoration:underline;"><#if rollcallCompany.wordOne?exists>${rollcallCompany.wordOne}<#else>　　　　　　　　</#if></span>字〔20<span style="text-decoration:underline;"><#if rollcallCompany.wordYear?exists>${rollcallCompany.wordYear}<#else>　</#if></span>〕第<span style="text-decoration:underline;"><#if rollcallCompany.wordTwo?exists>${rollcallCompany.wordTwo}<#else>　　</#if></span>号　　　　　　　签发人：<input type="text" name="rollcallDefer.signatory" value="${rollcallDefer.signatory}" id="signatory" size="8" maxlength="10"></td></tr>
  	<tr><td height="10">&nbsp;</td></tr>
    <tr><td style="font-size:18px;line-height:50px;" align="left">
  <span style="text-decoration:underline;">${danger.pipeLine.daPipelineCompanyinfo.company.companyName}：</span><br>
  　　根据你单位延期治理申请，经审查决定（见打√栏）：<br>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
　　<input type="checkbox" onClick="checkedThis(this,'rollcallDefer.isAgree');" name="rollcallDefer.isAgree" value="1" <#if rollcallDefer?exists><#if rollcallDefer.isAgree==1>checked</#if></#if>>同意延期治理，治理期限延期至 <span style="text-decoration:underline;">${rollcallDefer.deferTime?string('yyyy年MM月dd日')}</span>。<br>
	</td></tr>
	<tr><td height="6">&nbsp;</td></tr>
  	<tr><td style="font-size:18px;line-height:50px;" align="left">
　　<input type="checkbox" onClick="checkedThis(this,'rollcallDefer.isAgree');" name="rollcallDefer.isAgree" value="0" <#if !rollcallDefer?exists || rollcallDefer.isAgree!=1>checked</#if>>不同意延期治理。</td>
</tr>
<tr><td height="6">&nbsp;</td></tr>
<tr>
  <td><textarea id="remark" name="rollcallDefer.remark" rows="8" cols="70">${rollcallDefer.remark}</textarea><span class="red_point">*</span><ui:v for="remark" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
</tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left"><br><br>
　　　　　　　　　　　　　　　　　　　　挂牌督办单位（盖章）<br>
　　　　　　　　　　　　　　　　　　　　　　<input type="text" id="rollcallUnitTime" value="${rollcallDefer.rollcallUnitTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'rollcallUnitTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10"><input type="hidden"  id="rollcallUnitTime_2"  name="rollcallDefer.rollcallUnitTime"  value="${rollcallDefer.rollcallUnitTime?date}"/><br>
送达人（签字）：<input type="text" id="sendoffMan"  name="rollcallDefer.sendoffMan" value="${rollcallDefer.sendoffMan}" size="10" maxlength="10"><br>       
治理责任单位主要负责人（签收）：<input type="text"  id="signinMan"  name="rollcallDefer.signinMan" value="${rollcallDefer.signinMan}"  size="10" maxlength="10"><br>
　　　　　　　　　　　　　　　　　　　　　　<input type="text" id="signinTime" value="${rollcallDefer.signinTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'signinTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10"><input type="hidden"  id="signinTime_2"  name="rollcallDefer.signinTime"  value="${rollcallDefer.signinTime?date}"/><br>
</td></tr>
  <tr><td style="font-size:14px;line-height:20px;" align="left">
注：本通知书一式三份，治理责任单位、督办单位、同级政府安委办、各执一份。
</td></tr>
  </table>
  </td>
  </tr>
</table>
<table width="97.7%" border="0" height="70" cellspacing="0" cellpadding="0" style="background-image:url(${resourcePath}/images/bk.jpg);" >
  <tr>
	<td align="center">
	<input name="yuran" type="button"  value="保　 存"  onclick="submitCreate()"/>　　　
	<input name="print" type="button"  value="打   印"  onclick="javascript:window.location='loadRollcallDeferForPassCha.xhtml?rollcallCompany.id=${rollcallCompany.id}'"/>　　　
	<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/></td>
  </tr>
</table>
<input type="hidden" name="rollcallDefer.id" value="${rollcallDefer.id}">
</form>
<script type="text/javascript">
	function checkedThis(obj,objId){
       var boxArray = get(objId);
       for(var i=0;i<=boxArray.length-1;i++){
            if(boxArray[i]==obj && obj.checked){
               boxArray[i].checked = true;
            }else{
               boxArray[i].checked = false;
            }
       }
    }
</script>
</#escape> 
<@fkMacros.pageFooter />