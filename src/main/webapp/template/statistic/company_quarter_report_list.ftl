<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr><th>已上报季报列表</th></tr>
</table>
<form action="findCompanyQuarterReport.xhtml" method="post" name="companyQuarterReportForm" id="companyQuarterReportForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
    <th style="width:75px;">季报年份：</th>
    <td style="width:100px;">
    <select name="companyQuarterReprot.year" id="companyQuarterReprot.year">
    	<option value="0">-请选择-</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists && companyQuarterReprot.year == 2008>selected</#if></#if> value="2008">2008年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2009>selected</#if></#if> value="2009">2009年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2010>selected</#if></#if> value="2010">2010年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2011>selected</#if></#if> value="2011">2011年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2012>selected</#if></#if> value="2012">2012年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2013>selected</#if></#if> value="2013">2013年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2014>selected</#if></#if> value="2014">2014年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2015>selected</#if></#if> value="2015">2015年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2016>selected</#if></#if> value="2016">2016年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2017>selected</#if></#if> value="2017">2017年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2018>selected</#if></#if> value="2018">2018年</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.year?exists &&  companyQuarterReprot.year == 2019>selected</#if></#if> value="2019">2019年</option>
    </select>
    </td>
    <th style="width:75px;">季度：</th>
    <td style="width:100px;">
    <select name="companyQuarterReprot.quarter" id="companyQuarterReprot.quarter">
    	<option value="0">-请选择-</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.quarter?exists && companyQuarterReprot.quarter == 1>selected</#if></#if> value="1">第一季度</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.quarter?exists && companyQuarterReprot.quarter == 2>selected</#if></#if> value="2">第二季度</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.quarter?exists && companyQuarterReprot.quarter == 3>selected</#if></#if> value="3">第三季度</option>
    	<option <#if companyQuarterReprot?exists><#if companyQuarterReprot.quarter?exists && companyQuarterReprot.quarter == 4>selected</#if></#if> value="4">第四季度</option>
    </select></td>
 	<td><input type="submit" id="sub" value="查  询" onClick="get('companyQuarterReportForm').submit();" style="input_submit"/></td>
  </tr>
</table>
</form>
<form action="deleteCompanies.xhtml" method="post" name="companyQuarterReportsForm" id="companyQuarterReportsForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th id="th_id" width="4%" style="cursor:hand;" nowrap>序号</th>
    <th id="th_address" width="25%" style="cursor:hand;" nowrap>季报年份</th>
    <th id="th_fdDelegate" width="8%" style="cursor:hand;" nowrap>季度</th>
    <th id="th_companyName" width="28%" style="cursor:hand;" nowrap>上报状态</th>
  </tr>
  <#if companyQuarterReprots?exists>
  	<#list companyQuarterReprots?if_exists as c>
	  <tr>
	    <td width="10%">${c_index+1}</td>
	    <td width="30%"><div align="left">${c.year}年&nbsp;</div></td>
	    <td width="30%">第<#if c.quarter?exists && c.quarter == 1>一</#if><#if c.quarter?exists && c.quarter == 2>二</#if><#if c.quarter?exists && c.quarter == 3>三</#if><#if c.quarter?exists && c.quarter == 4>四</#if>季度&nbsp;</td>
	    <td width="30%"><a href="loadReportByCompany.xhtml?statistic.year=${c.year}&statistic.quarter=${c.quarter}">已上报</a></td>
	  </tr>
	 </#list>
  </#if>
</table>
</form>
</#escape>
<@fkMacros.pageFooter />