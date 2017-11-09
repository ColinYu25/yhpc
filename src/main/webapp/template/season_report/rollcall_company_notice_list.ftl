<@fkMacros.pageHeader />
<#escape x as (x)!>
<script type="text/javascript">
var enumObj=new Enum("${enumXmlUrl}");
var areaObj = new Area("${AreaXmlUrl}");
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">重大隐患挂牌督办记录表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if userDetail.userIndustry!='qiye'>
		<li id="img_gp"><a href="javascript:loadNote('loadRollcallCompany.xhtml?rollcallCompany.id');" class="b_gp"><b>修改</b></a></li>
	</#if>
	<li id="img_xcjcjl"><a href="javascript:loadNote('loadRollcallCompanyForNotice.xhtml?rollcallCompany.id');" class="b_xcjcjl" ><b>督办通知书</b></a></li>
	<#if userDetail.userIndustry=='qiye'>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDefers.xhtml?rollcallCompany.id');" class="b_xcjcjl"><b>企业延期申请</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:alertErr('验收');" class="b_xcjcjl"><b>企业验收申请</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	</ul>
	</div>
</div>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="9%" nowrap>隐患编号</th>
    <th width="18%" nowrap>隐患地址</th>
    <th width="6%" nowrap>是否整改</th>
    <th width="7%" nowrap>挂牌级别</th>
    <th width="9%" nowrap>督办单位</th>
    <th width="9%" nowrap>督办完成时间</th>
  </tr>
  <#if rollcallCompanies?exists>
  <#list rollcallCompanies?if_exists as r>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${r.id}" value="${r.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+r_index+1}<#else>${r_index+1}</#if></td>
    <td nowrap>${r.daDanger.dangerNo}&nbsp;</td>
    <td nowrap><div align="left"><a href="../danger/loadDanger.xhtml?danger.id=${r.daDanger.id}">${r.daDanger.dangerAdd}</a>&nbsp;</div></td>
    <td nowrap><#if (r.daDanger?if_exists.daDangerGorvers?size>0)>是<#else>否</#if></td>
    <td><#if r.level?exists><script type="text/javascript">document.write(enumObj.getSelect("${r.level}"));</script></#if>挂牌</td>
    <td><#if r.govment?exists><script type="text/javascript">document.write(areaObj.getArea("${r.govment}")[0]);</script></#if>&nbsp;</td>
    <td>${r.completeTime?date}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (rollcallCompanies?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script>
function alertErr(){
 	alert("该企业未提交重大事故隐患治理"+arguments[0]+"申请书");
 }
</script>
</#escape>
<@fkMacros.pageFooter />