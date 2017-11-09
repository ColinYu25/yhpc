<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if danger?if_exists.isGorver?if_exists=='0'>
	<#assign letter='重大隐患未治理隐患列表'>
<#elseif  danger?if_exists.isGorver?if_exists=='1'>
	<#assign letter='重大隐患已治理隐患列表'>
<#elseif  danger?if_exists.isGorver?if_exists=='2'>
	<#assign letter='重大隐患到期提醒隐患列表'>
<#else>
	<#assign letter='重大隐患隐患列表'>
</#if>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">${company.companyName}${letter}</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_amend"><a href="javascript:window.location='createDangerInit.xhtml?danger.daCompanyPass.id=${company.id}'" class="b2"><b>添加</b></a></li>
	<li id="img_vary"><a href="javascript:loadNote('loadDanger.xhtml?danger.id');" class="b8"><b>修改</b></a></li>
	<#if danger?if_exists.isGorver?if_exists=='1'>
	<li id="img_zgtzs"><a href="javascript:loadNote('../dangerGorver/createDangerGorverInit.xhtml?dangerGorver.daDanger.id');" class="b_zgtzs"><b>查看整改信息</b></a></li>
    <#else>
    <li id="img_report"><a href="javascript:loadNote('../dangerGorver/createDangerGorverInit.xhtml?dangerGorver.daDanger.id');" class="b7"><b>整改</b></a></li>
    </#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
	</ul>
	</div>
	
</div> 
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="11%" nowrap>隐患编号</th>
    <th width="18%" nowrap>隐患地址</th>
    <th width ="30%" nowrap>隐患内容</th>
    <th width="6%" nowrap>是否挂牌</th>
    <#if !danger?if_exists.isGorver?exists>
    <th width="6%" nowrap>是否整改</th>
    </#if>
    <th width="7%" nowrap>联 系 人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if dangers?exists>
  <#list dangers?if_exists as c>
  <tr>
    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
    <td nowrap>${c.dangerNo}&nbsp;</td>
    <td nowrap><div align="left"><a href="loadDanger.xhtml?danger.id=${c.id}">${c.dangerAdd}</a>&nbsp;</div></td>
    <td nowrap>${c.description}&nbsp;</td>
    <td nowrap><#if (c.daRollcallCompanies?size>0)>是<#else>否</#if></td>
    <#if !danger?if_exists.isGorver?exists>
    <td nowrap><#if (c.daDangerGorvers?size>0)>是<#else>否</#if></td>
     </#if>
    <td nowrap>${c.linkMan}&nbsp;</td>
    <td nowrap>${c.linkTel}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<#if (dangers?if_exists?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
</#escape>
<@fkMacros.pageFooter />