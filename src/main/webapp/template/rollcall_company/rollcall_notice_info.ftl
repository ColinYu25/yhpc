<@fkMacros.pageHeader />
<#escape x as (x)!>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("rollcallCompanyForm");
  		  obj.action="updateRollcallCompanyForNotice.xhtml";
 		  obj.submit();
 	}
}
</script>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if !viewRole?? || !viewRole>
		<li id="img_save"><a href="javascript:submitCreate();" class="b1"><b>保存</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'rollcallCompanyForm' />
<form id="rollcallCompanyForm" name="rollcallCompanyForm" method="post" action="">
<table width="97.7%" border="0"  cellpadding="0" cellspacing="0"  style="background-image:url(${resourcePath}/images/bk.jpg);">
  <tr>
  <td align="center">
  <table width="570" border="0" cellpadding="0" cellspacing="0">
  <tr><td><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患挂牌督办<br>通　　知　　书</strong></td></tr>
  <tr height="50"><td>&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:40px;" align="left">
  <input type="text" name="rollcallCompany.wordOne"  id="wordOne" size="10" maxlength="20" value="${rollcallCompany.wordOne}">字〔20<input type="text" name="rollcallCompany.wordYear" value="${rollcallCompany.wordYear}"  id="wordYear" style="width:20px;" maxlength="5">〕第<input type="text" name="rollcallCompany.wordTwo" value="${rollcallCompany.wordTwo}"  id="wordTwo" style="width:20px;" maxlength="5">号　　　　　　　　 签发人：<input type="text" name="rollcallCompany.signatory" value="${rollcallCompany.signatory}" id="signatory" size="6" maxlength="10"></td></tr>
  <tr><td height="10">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
  <span style="text-decoration:underline;">${company.companyName}：</span><br>
  　　经检查，你单位存在以下重大事故隐患：<span style="text-decoration:underline;">${rollcallCompany.daDanger.description}。</span><br>
  　　依据相关法律法规和《宁波市重大事故隐患治理挂牌督办暂行办法》，决定对你单位上述重大事故隐患治理工作实施挂牌督办，责令你单位于<input type="text" id="finishTime" value="${rollcallCompany.finishTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'finishTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10">
    <input type="hidden" name="rollcallCompany.finishTime" id="finishTime_2" value="${rollcallCompany.finishTime?date}"/>前，将该事故隐患治理完毕。<br>
　　治理期间应做好以下工作：<br>
　　1．单位主要负责人应组织制定重大事故隐患治理方案并组织实施，并在<input type="text" id="planTime" value="${rollcallCompany.planTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'planTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10">
    <input type="hidden" name="rollcallCompany.planTime" id="planTime_2" value="${rollcallCompany.planTime?date}"/>前，将治理方案报我单位备案；<br>
　　2．自收到通知书之日起，每季度至少一次向我单位报告重大事故隐患治理进展情况；<br>
　　3．在治理期限届满前向我单位提交《宁波市安全生产重大事故隐患治理验收申请书》，申请验收。确不能在限期内治理完毕的，应在治理期限届满前15个工作日内向我单位提交《宁波市安全生产重大事故隐患治理延期申请表》，申请延期；<br>
　　4．治理期间，落实安全防控措施，切实防止事故发生。<br><br><br>
　　　　　　　　　　　　　　　　　　　　　挂牌督办单位（章）<br>
　　　　　　　　　　　　　　　　　　　　　　<input type="text" id="rollcallUnitTime" value="${rollcallCompany.rollcallUnitTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'rollcallUnitTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10"><input type="hidden"  id="rollcallUnitTime_2"  name="rollcallCompany.rollcallUnitTime"  value="${rollcallCompany.rollcallUnitTime?date}"/><br>
　送达人（签字）：<input type="text" id="sendoffMan"  name="rollcallCompany.sendoffMan" value="${rollcallCompany.sendoffMan}" size="10" maxlength="10"><br>
　治理责任单位主要负责人（签收）：<input type="text"  id="signinMan"  name="rollcallCompany.signinMan" value="${rollcallCompany.signinMan}"  size="10" maxlength="10"><br>
　　　　　　　　　　　　　　　　　　　　　　<input type="text" id="signinTime" value="${rollcallCompany.signinTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'signinTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10"><input type="hidden"  id="signinTime_2"  name="rollcallCompany.signinTime"  value="${rollcallCompany.signinTime?date}"/><br>
</td></tr>
  <tr><td style="font-size:14px;line-height:20px;" align="left">
　注：本通知书一式二份，督办单位、治理责任单位各执一份。<br>
</td></tr>
  </table>
  </td>
  </tr>
</table>
<table width="97.7%" border="0" height="70" cellspacing="0" cellpadding="0" style="background-image:url(${resourcePath}/images/bk.jpg);" >
  <tr>
	<td align="center">
	<#if !viewRole?? || !viewRole>
	<input name="yuran" type="button"  value="保　 存"  onclick="submitCreate()"/>
	</#if>
	<input name="print" type="button"  value="打   印"  onclick="javascript:window.location='loadRollcallCompanyForNotice.xhtml?rollcallCompany.id=${rollcallCompany.id}'"/>　　　
	<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/></td>
  </tr>
</table>
<input type="hidden" name="rollcallCompany.daDanger.id" value="${rollcallCompany.daDanger.id}" id="dangerId"/>
<input type="hidden" name="rollcallCompany.id" value="${rollcallCompany.id}" id="id"/>
</form>
</#escape>
<@fkMacros.pageFooter />