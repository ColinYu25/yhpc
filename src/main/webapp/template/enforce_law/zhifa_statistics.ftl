<@fkMacros.pageHeader />
<#escape x as (x)!> 
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0" id="tb">
   <tr>
      <td>
    <table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="loadStatistics.xhtml" method="post" name="enforceLawForm" id="enforceLawForm">
	  <tr>
	    <th>区　　域</th>
	    <td width="20%">
		    <select name="daZhifaReport.areaCode">
		    <option value="0">-----请选择-----</option>
		    	<#list areas?if_exists as area>
		    		<option value="${area.areaCode}">${area.areaName}</option>
		    	</#list>
		    </select>
	    </td>
	    <th>填报月份</th>
	    <td width="20%"><input type="text" value="${daZhifaReport.writtenMonth}" id="writtenMonth" onfocus="WdatePicker({dateFmt:'yyyy-MM'});" name="daZhifaReport.writtenMonth" class="Wdate" size="10"/></td>
	    <td width="20%"><input type="submit" id="sub" value="搜　索" /></td>
	  </tr>
		</form>
	</table>
	</td>
	</tr>
     <tr>
      <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10"></td>
        </tr>
		 <tr>
          <th colspan="3" style="font-size:16px" class="head" align="center">安全生产执法行动情况统计表</tH>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left">
		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_list">
            <tr>
              <td width="10%" rowspan="2">行业（领域）</td>
              <td rowspan="2">开展执法行动情况合计 （起、处）</td>
              <td>无证或证照不全从事建设、生产、经营的（起）</td>
              <td>关闭取缔后又擅自建设、生产、经营的（起）</td>
              <td>小煤矿应关未关的（处 ）</td>
              <td>私采滥挖、超层越界开采、尾矿库违规排放的（起）</td>
              <td>违反建设项目安全设施“三同时”规定的（起）</td>
              <td>谎报、瞒报事故的（起）</td>
              <td>重大隐患隐瞒不报或不按规定期限予以整治的（起）</td>
              <td>不按规定进行安全培训或无证上岗的（起）</td>
              <td>拒不执行安全监管监察指令、抗拒安全执法的（起）</td>
              <td>其他非法违法建设、生产、经营的（起）</td>
              </tr>
            <tr>
              <td width="8%">1</td>
              <td width="8%">2</td>
              <td width="8%">3</td>
              <td width="8%">4</td>
              <td width="8%">5</td>
              <td width="8%">6</td>
              <td width="8%">7</td>
              <td width="8%">8</td>
              <td width="8%">9</td>
              <td width="8%">10</td>
            </tr>
			<tbody id="tf">
			<#assign totalSum=0>
			<#assign data1Sum=0>
			<#assign data2Sum=0>
			<#assign data3Sum=0>
			<#assign data4Sum=0>
			<#assign data5Sum=0>
			<#assign data6Sum=0>
			<#assign data7Sum=0>
			<#assign data8Sum=0>
			<#assign data9Sum=0>
			<#assign data10Sum=0>
			<#list daZhifaReportDetails?if_exists as dzrd>
			<#assign totalSum=totalSum+dzrd.total>
			<#assign data1Sum=data1Sum+dzrd.data1>
			<#assign data2Sum=data2Sum+dzrd.data2>
			<#assign data3Sum=data3Sum+dzrd.data3>
			<#assign data4Sum=data4Sum+dzrd.data4>
			<#assign data5Sum=data5Sum+dzrd.data5>
			<#assign data6Sum=data6Sum+dzrd.data6>
			<#assign data7Sum=data7Sum+dzrd.data7>
			<#assign data8Sum=data8Sum+dzrd.data8>
			<#assign data9Sum=data9Sum+dzrd.data9>
			<#assign data10Sum=data10Sum+dzrd.data10>
				<tr>
				  <td style="text-align:left" id="td_type" nowrap="nowrap">${dzrd.name}</td>
				  <td align="center" >${dzrd.total}</td>
	              <td align="center" >${dzrd.data1}</td>
	              <td align="center" >${dzrd.data2}</td>
	              <td align="center" >${dzrd.data3}</td>
				  <td align="center" >${dzrd.data4}</td>
	              <td align="center" >${dzrd.data5}</td>
	              <td align="center" >${dzrd.data6}</td>
	              <td align="center" >${dzrd.data7}</td>
	              <td align="center" >${dzrd.data8}</td>
	              <td align="center" >${dzrd.data9}</td>
	              <td align="center" >${dzrd.data10}</td>
	            </tr>
			</#list>
			<tr>
				  <td style="text-align:left" id="td_type" nowrap="nowrap">总计</td>
				  <td align="center" >${totalSum}</td>
	              <td align="center" >${data1Sum}</td>
	              <td align="center" >${data2Sum}</td>
	              <td align="center" >${data3Sum}</td>
				  <td align="center" >${data4Sum}</td>
	              <td align="center" >${data5Sum}</td>
	              <td align="center" >${data6Sum}</td>
	              <td align="center" >${data7Sum}</td>
	              <td align="center" >${data8Sum}</td>
	              <td align="center" >${data9Sum}</td>
	              <td align="center" >${data10Sum}</td>
		   </tbody>
	
          </table></td>
        </tr>
		
      </table> 
</#escape>
<@fkMacros.pageFooter />