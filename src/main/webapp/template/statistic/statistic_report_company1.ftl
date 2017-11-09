<@fkMacros.pageHeader />
<#escape x as (x)!>
<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="800">
  <TR>
    <td width="10%" rowSpan="2" align="center"><strong>单　位</strong></td>
    <td width="10%" align="center"><strong>地　址</strong></td>
    <TD colSpan="3" width="80%">&nbsp;${statisticForCompany.address}&nbsp;</TD>
  </TR>
  <TR>
    <td align="center"><strong>区　域</strong></td>
    <TD colSpan="5">&nbsp;${statisticForCompany.areaName}&nbsp;</TD>
  </TR>
  <TR>
    <td rowSpan="2" align="center"><strong>作　业<br>场　所</strong></td>
    <td align="center"><strong>地　址</strong></td>
    <TD colSpan="3">&nbsp;</TD>
  </TR>
  <TR>
    <td align="center"><strong>区　域</strong></td>
    <TD colSpan="3">&nbsp;　　　　　　　　　　　　　　　　　　　县（市）区　　　　　　　　　　　　　　　　　乡镇（街道）</TD>
  </TR>
  <TR>
    <td colspan="2" align="center"><strong>联 系 人</strong> </td>
    <TD width="20%">&nbsp;${statisticForCompany.linkMan}</TD>
    <td align="center" width="15%"><strong>联系电话</strong></td>
    <TD>&nbsp;座机：&nbsp;<#if statisticForCompany?if_exists.linkTel?exists>${statisticForCompany.linkTel}
    <#else>　　　　　　</#if>　　　　　　手机： </TD>
  </TR>
</table>
<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="800">
  <TR>
    <td width="50%" colSpan="2" align="center"><strong>一 般 隐 患</strong></td>
    <TD colSpan="2" width="50%" align="center"><strong>重 大 隐 患</strong></TD>
  </TR>
  <TR>
    <td align="center" width="25%"><strong>发现：　　${statisticForCompany.troubNum}　　项</strong></td>
    <td align="center" width="25%"><strong>尚未整改：　　${statisticForCompany.troubNum-statisticForCompany.troubCleanNum}　　项</strong></td>
    <td align="center" width="25%"><strong>发现：　　${statisticForCompany.bigTroubNum}　　项</strong></td>
    <td align="center" width="25%"><strong>尚未整改：　　${statisticForCompany.planBigTroubNum}　　项</strong></td>
  </TR>
</table>
<table cellspacing="0" align="center" cellpadding="0" class="table_input2" width="800">
  <TR>
    <td width="100%" colSpan="6" align="center"><strong>列入治理计划的未整改重大隐患共  　　${statisticForCompany.planBigTroubNum}　　 项，其中</strong></td>
  </TR>
  <TR>
    <td align="center" width="16.65%"><strong>落实治理<br>目标任务</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>经费物资</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>机构人员</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>时　　间</strong></td>
    <td align="center" width="16.65%"><strong>落实安全措施<br>及应急预案</strong></td>
    <td align="center" width="16.65%"><strong>落实治理<br>资金（累计）</strong></td>
  </TR>
  <tr>
  	<td align="center" width="16.65%">${statisticForCompany.target}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.goods}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.resource}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.finishDate}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.safetyMethod}　项</td>
  	<td align="center" width="16.65%">${statisticForCompany.governMoney}　万元</td>
  </tr>
</table>
</#escape>
<@fkMacros.pageFooter />