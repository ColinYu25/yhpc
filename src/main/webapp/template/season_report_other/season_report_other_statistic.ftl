<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">公安（道路交通）、农机、质监、海事等安全生产隐患排查治理情况统计表</th>
  </tr>
</table>
<table width="98%" cellspacing="1" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 12px; color: #4F6B72;; background-color:#C1DAD7">
          <tr><th height="25" bgcolor="#D6EEF0" align="center" colspan="16"><span class="head">${typeName}</span></th></tr>
        <tr height="20" bgcolor="#dcfafa" align="center">
          <td width="9%" rowspan="5" align="center" widtd="72">行业和领域</td>
          <td height="10" widtd="72">&nbsp;</td>
          <td colspan="3" widtd="216"><center>一般隐患</center></td>
          <td colspan="10" widtd="720"><center>重大隐患</center></td>
        </tr>
        <tr height="20" bgcolor="#dcfafa" align="center">
          <td width="4%" rowspan="3"><center>排查<br>单位数</center></td>
          <td width="4%" rowspan="3"><center>隐患数</center></td>
          <td width="4%" rowspan="3"><center>已经<br>整改数</center></td>
          <td width="4%" rowspan="3"><center>整改率</center></td>
          <td height="20" colspan="3"><center>排查治理重大隐患</center></td>
          <td colspan="7"><center>列入治理计划的重大隐患</center></td>
        </tr>
        <tr height="20" bgcolor="#dcfafa" align="center">
          <td width="4%" rowspan="2"><center>重大<br>隐患数</center></td>
          <td width="4%" rowspan="2"><center>已整改<br>销号数</center></td>
          <td width="4%" rowspan="2"><center>整改率</center></td>
          <td width="4%" rowspan="2"><center>重大<br>隐患数</center></td>
          <td height="20" colspan="5"><center>其中</center></td>
          <td width="5%" rowspan="2"><center>累计落实<br>治理资金</center></td>
        </tr>
        <tr height="19" bgcolor="#dcfafa" align="center">
          <td width="5%"><center>落实治理<br>目标任务</center></td>
          <td width="5%"><center>落实治理<br>经费物资</center></td>
          <td width="6%"><center>落实治理<br>机构人员</center></td>
          <td width="5%"><center>落实治理<br>时间要求</center></td>
          <td width="7%"><center>落实安全措<br>施应急预案</center></td>
        </tr>        
        <tr height="20" bgcolor="#dcfafa" align="center">
          <td height="20"><center>(家)</center></td>
          <td><center>(项)</center></td>
          <td><center>项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(万元)</center></td>
        </tr>
		<#if statistics?exists>
		<#list statistics as s>
       <tr height="25" bgcolor="#D6EEF0" align="center">
            <td height="20"><div align="left">&nbsp;<strong>${s.enumName}</strong></div></td>
            <td height="20">${s.companyNum}&nbsp;</td>
            <td height="20">${s.troubNum}&nbsp;</td>
            <td height="20">${s.troubCleanNum}&nbsp;</td>
            <td height="20"><#if s.troubNum==0>/<#else>#{(s.troubCleanNum/s.troubNum*100);M1}%</#if></td>
            <td height="20">${s.bigTroubNum}</td>
            <td height="20">${s.bigTroubCleanNum}</td>
            <td height="20"><#if s.bigTroubNum==0>/<#else>#{(s.bigTroubCleanNum/s.bigTroubNum*100);M1}%</#if></td>
            <td height="20">${s.target+s.goods+s.resource+s.finishDate+s.safetyMethod}</td>
            <td height="20">${s.target}</td>
            <td height="20">${s.goods}</td>
            <td height="20">${s.resource}</td>
            <td height="20">${s.finishDate}</td>
            <td height="20">${s.safetyMethod}</td>
            <td height="20">${s.governMoney}</td>
		</tr>
        </#list>
        </#if>
		
        </table>

 </#escape>
<@fkMacros.pageFooter />
