<@fkMacros.pageHeader />
<#escape x as (x)!>
<@fkMacros.initAreaXML />
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22" >隐患详情</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<table width="98%" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
	<th style="width:14%;" align="right" nowrap>单位名称</th>
	<td>${entity.companyName}</td>
	<th width="11%" align="right" nowrap>所在区域</th>
	<td><@fkMacros.getSelectArea entity.secondArea/> <@fkMacros.getSelectArea entity.thirdArea/></td>
  </tr>
  <tr>
    <th align="right" nowrap>隐患描述</th>
    <td>${entity.description}</td>
    <th align="right" nowrap>隐患发现时间</th>
	<td>${entity.hiddenDate?string('yyyy-MM-dd')}</td>
  </tr>
  <tr>
    <th align="right" nowrap>隐患分类</th>
    <td>${entity.type1Text} ${entity.type2Text}&nbsp;</td>
    <th align="right" nowrap>是否整改</th>
	<td><#if entity.completed>是<#else>否</#if></td>
  </tr>
  <tr>
    <th align="right" nowrap>整改时间</th>
    <td>${entity.completeDate?string('yyyy-MM-dd')}&nbsp;</td>
    <th align="right" nowrap>隐患等级</th>
	<td>${entity.hiddenLevelText}&nbsp;</td>
  </tr>
  <tr>
    <th align="right" nowrap>隐患来源系统</th>
    <td colspan="3">${entity.fromSysText}&nbsp;</td>
  </tr>
</table>
</#escape>
<@fkMacros.pageFooter />