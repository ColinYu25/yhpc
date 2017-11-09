<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">隐患治理延期申请列表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if userDetail.userIndustry=='qiye'>
	<li id="img_save"><a href="javascript:window.location='createRollcallDeferInit.xhtml?rollcallCompany.id=${rollcallCompany.id}'" class="b1"><b>添加</b></a></li>
	<li id="img_amend"><a href="javascript:loadNote('loadRollcallDefer.xhtml?rollcallDefer.id');" class="b2"><b>修改</b></a></li>
	<li id="img_del"><a href="javascript:deleteNote(get('rollcallDeferFormForDelete'),'deleteRollcallDefer.xhtml');" class="b3"><b>删除</b></a></li>
	<li id="img_gp"><a href="#" class="b_gp" onClick="javascript:subNote(get('rollcallDeferFormForDelete'),'updateRollcallDeferBySub.xhtml');"><b>上报</b></a></li>
	<#else>
	<li id="img_amend"><a href="javascript:loadNote('loadRollcallDeferForPass.xhtml?rollcallDefer.id');" class="b2"><b>审批</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<form action="" method="post" name="rollcallDeferFormForDelete" id="rollcallDeferFormForDelete">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="33%" nowrap>延期理由</th>
    <th width="9%" nowrap>延期日期至</th>
    <th width="9%" nowrap>上报情况</th>
    <th width="9%" nowrap>审批状态</th>
    <th width="34%" nowrap>审批结果</th>
  </tr>
  <#if rollcallDefers?exists>
  <#list rollcallDefers?if_exists as r>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${r.id}" value="${r.id}" <#if r.done?exists&&r.done&&userDetail.userIndustry=='qiye'>disabled="true"</#if>></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+r_index+1}<#else>${r_index+1}</#if></td>
    <td nowrap><div align="center"><#if r.done?exists&&r.done>${r.deferReason}<#else><a href="loadRollcallDefer.xhtml?rollcallDefer.id=${r.id}">${r.deferReason}</a></#if>&nbsp;</div></td>
    <td nowrap>${r.deferTime?date}&nbsp;</td>
    <td nowrap><#if r.done?exists&&r.done>已<#else>未</#if>上报</td>
    <td><#if r.done?exists&&r.done><#if r.isAgree?exists&&r.isAgree==2>未<#else>已</#if>审批</#if>&nbsp;</td>
    <td><#if r.done?exists&&r.done><#if r.isAgree?exists&&r.isAgree!=2><#if r.isAgree?exists&&r.isAgree==1>同意延期治理，治理期限延期至${r.deferTime?date}：${r.remark}
    	<#else>不同意延期治理：${r.remark}</#if></#if></#if>&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (rollcallDefers?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script>
function subNote() {
	if(chooseCheckBox()) {
		if(window.confirm('确定上报吗？')) {
			if(arguments[1])
				arguments[0].action=arguments[1];
			if(arguments[0].action != null)
				arguments[0].submit();
		}
	}
}
</script>
</#escape>
<@fkMacros.pageFooter />