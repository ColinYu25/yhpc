<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<#if statistic.remark=='xiaofang'>
	<#assign typeName='消防安全隐患排查治理情况统计季报表'>
<#elseif statistic.remark=='nongji'>
	<#assign typeName='农机行业安全生产隐患排查治理情况统计季报表'>
<#elseif statistic.remark=='lishe'>
	<#assign typeName='栎社机场安全生产隐患排查治理情况统计季报表'>
<#elseif statistic.remark=='zhijian'>
	<#assign typeName='特种设备安全生产隐患排查治理情况统计季报表'>
<#elseif statistic.remark=='renfang'>
	<#assign typeName='人防安全生产隐患排查治理情况统计季报表'>
</#if>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
          	<table width="100%" cellspacing="1" cellpadding="0" border="0" align="center" >
		   <tr>
			<td align="center">
			<br>
			<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　
			<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　
			<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/>
			<br><br>
			
			<span class="head"><input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
             　            
             　<input name="firstQuarter" type="button"  value="一季度"  onclick="changeQUrl(1);"/>
               &nbsp; &nbsp;<input name="secondQuarter" type="button"  value="二季度"  onclick="changeQUrl(2);"/>
               &nbsp; &nbsp;<input name="thirdQuarter" type="button"  value="三季度"  onclick="changeQUrl(3);"/>
               &nbsp; &nbsp;<input name="fouthQuarter" type="button"  value="四季度"  onclick="changeQUrl(4);"/></span>
		  </td></tr>
		  <tr><TD align="center">
		  <#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
			&nbsp;&nbsp;
		  <#if refresh==1 >
			<a href="loadReportByOther.xhtml?<#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
		  </#if>	
		  </td></tr>
           </table>
           <div id='page1'>
         <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0" >
		  <tr>
		   <tr><td align="center" ><br><strong style="font-size:23px;line-height:30px;">${typeName}</strong></td></tr>
		  </tr>
		  <tr><td align="center" style="font-size:14px;line-height:25px;">填报单位（章）：　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　年　　月　　日</td></tr>
		</table>
		  <table width="90%" cellspacing="1" cellpadding="0" border="0" align="center" class="table_list2" style="font-family: '宋体'; font-size: 12px; color: #4F6B72;">
    <tbody>
        <tr height="20" align="center">
          <td width="14%" rowspan="5" align="center">行业和领域</td>
          <td height="10" width="72">&nbsp;</td>
          <td colspan="3" width="216"><center>一般隐患</center></td>
          <td colspan="10" width="720"><center>重大隐患</center></td>
        </tr>
        <tr height="20"  align="center">
          <td width="4%" rowspan="3"><center>排查<br>单位数</center></td>
          <td width="4%" rowspan="3"><center>隐患数</center></td>
          <td width="4%" rowspan="3"><center>已经<br>整改数</center></td>
          <td width="4%" rowspan="3"><center>整改率</center></td>
          <td height="20" colspan="3"><center>排查治理重大隐患</center></td>
          <td colspan="7"><center>列入治理计划的重大隐患</center></td>
        </tr>
        <tr height="20"  align="center">
          <td width="4%" rowspan="2"><center>重大<br>隐患数</center></td>
          <td width="4%" rowspan="2"><center>已整改<br>销号数</center></td>
          <td width="4%" rowspan="2"><center>整改率</center></td>
          <td width="4%" rowspan="2"><center>重大<br>隐患数</center></td>
          <td height="20" colspan="5"><center>其中</center></td>
          <td width="5%" rowspan="2"><center>累计落实<br>治理资金</center></td>
        </tr>
        <tr height="19"  align="center">
          <td width="5%"><center>落实治理<br>目标任务</center></td>
          <td width="5%"><center>落实治理<br>经费物资</center></td>
          <td width="6%"><center>落实治理<br>机构人员</center></td>
          <td width="5%"><center>落实治理<br>时间要求</center></td>
          <td width="7%"><center>落实安全措<br>施应急预案</center></td>
        </tr>        
        <tr height="20"  align="center">
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
        <#list statistics as s>
        <tr height="20" align="center">
          <#if s_index==0><td>${s.enumName}</td></#if>
          <td>${s.companyNum}</td>
          <td>${s.troubNum}</td>
          <td>${s.troubCleanNum}</td>
          <td><#if s.troubNum==0>/<#else>#{(s.troubCleanNum/s.troubNum*100);M1}%</#if></td>
          <td>${s.bigTroubNum}</td>
          <td>${s.bigTroubCleanNum}</td>
          <td><#if s.bigTroubNum==0>/<#else>#{(s.bigTroubCleanNum/s.bigTroubNum*100);M1}%</#if></td>
          <td>${s.bigTroubNum-s.bigTroubCleanNum}</td>
          <td>${s.target}</td>
          <td>${s.goods}</td>
          <td>${s.resource}</td>
          <td>${s.finishDate}</td>
          <td>${s.safetyMethod}</td>
          <td>${s.governMoney}</td>
        </tr>
        </#list>
        </tbody></table>
       <table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:14px;line-height:25px;">单位负责人（签字）：　　　　　　　　　填表人（签字）：　　　　　　　　　联系电话：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	</table>
          </div>
<script type="text/javascript">
		<#if statistic.remark?if_exists!='jiaotong'>
		printParam(10,0,0,0,2);
		<#else>
		printParam(0,0,0,0,2);
		</#if>
	function changeQUrl() {
		window.location = "?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
	}
</script>

</#escape>
<@fkMacros.pageFooter />