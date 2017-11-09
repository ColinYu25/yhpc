<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr><th>已上报季报列表</th></tr>
</table>
<form action="" method="post" name="companyQuarterReportForm" id="companyQuarterReportForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
    <th>季报年份</th>
    <td width="35%">
    <select name="entity.year" id="entity.year">
    	<option value="0">-请选择-</option>
    	<option <#if entity?exists><#if entity.year?exists && entity.year == 2008>selected</#if></#if> value="2008">2008年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2009>selected</#if></#if> value="2009">2009年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2010>selected</#if></#if> value="2010">2010年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2011>selected</#if></#if> value="2011">2011年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2012>selected</#if></#if> value="2012">2012年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2013>selected</#if></#if> value="2013">2013年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2014>selected</#if></#if> value="2014">2014年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2015>selected</#if></#if> value="2015">2015年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2016>selected</#if></#if> value="2016">2016年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2017>selected</#if></#if> value="2017">2017年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2018>selected</#if></#if> value="2018">2018年</option>
    	<option <#if entity?exists><#if entity.year?exists &&  entity.year == 2019>selected</#if></#if> value="2019">2019年</option>
    </select>
    </td>
    <th>季度</th>
    <td width="35%">
    <select name="entity.quarter" id="entity.quarter">
    	<option value="0">-请选择-</option>
    	<option <#if entity?exists><#if entity.quarter?exists && entity.quarter == 1>selected</#if></#if> value="1">第一季度</option>
    	<option <#if entity?exists><#if entity.quarter?exists && entity.quarter == 2>selected</#if></#if> value="2">第二季度</option>
    	<option <#if entity?exists><#if entity.quarter?exists && entity.quarter == 3>selected</#if></#if> value="3">第三季度</option>
    	<option <#if entity?exists><#if entity.quarter?exists && entity.quarter == 4>selected</#if></#if> value="4">第四季度</option>
    </select></td>
  </tr>
  <!--<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm('companyQuarterReportForm');"/></div></th></tr>-->
</table>
</form>
<div class="menu">
  	<ul>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	<li id="img_lookup"><a href="javaScript:;" class="b13" onClick="get('companyQuarterReportForm').submit();"><b>查询</b></a></li>
	</ul>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th id="th_id" width="4%" nowrap>序号</th>
    <th id="th_address" width="32%" nowrap>季报年份</th>
    <th id="th_fdDelegate" width="32%" nowrap>季度</th>
    <th id="th_companyName" width="32%" nowrap>上报状态</th>
  </tr>
  <#if result?exists && result?size gt 0>
  	<#list result?if_exists as c>
	  <tr>
	    <td width="4%">${c_index+1}</td>
	    <td width="32%">${c.year}年&nbsp;</td>
	    <td width="32%">第<#if c.quarter?exists && c.quarter == 1>一</#if><#if c.quarter?exists && c.quarter == 2>二</#if><#if c.quarter?exists && c.quarter == 3>三</#if><#if c.quarter?exists && c.quarter == 4>四</#if>季度&nbsp;</td>
	    <td width="32%">已上报</td>
	  </tr>
	 </#list>
  <#else>
  	 <tr>
	    <td colspan="4">此处暂时没有记录！ </td>
	  </tr>
  </#if>
</table>
<#if result?exists && result?size gt 0>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</#if>
</#escape>
<@fkMacros.pageFooter />