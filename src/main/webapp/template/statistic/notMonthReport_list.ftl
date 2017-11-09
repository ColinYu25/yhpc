<@fkMacros.pageHeader />
<#escape x as (x)!>
<script language="javascript">
	function showDaNomalDangers(com_id,companyName) {
		window.location = "../statistic/nosecuritycheck/loadNomalDangers.xhtml?company.id="+com_id+"&company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}&company.companyName="+companyName+"";
	}
	
	function showDaDangers(com_id,companyName) {
		window.location = "../statistic/nosecuritycheck/loadDangers.xhtml?company.id="+com_id+"&company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}&company.companyName="+companyName+"";
	}
	
	function showDangersAndNomalDangers(com_id,companyName) {
		window.location = "../statistic/nosecuritycheck/loadDangersAndNomalDangers.xhtml?company.id="+com_id+"&company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}&company.companyName="+companyName+"";
	}
</script>

<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业月报未上报情况汇总表</th>
  </tr>
</table>
<form  action="excel.xhtml?company.year=${company.year}&company.month=${company.month}&area.areaCode=${area.areaCode}<#if company.industryId?exists && company.industryId!=0>&company.industryId=${company.industryId}</#if>&company.companyTrade=${company.companyTrade}" method="post" name="exportForm" id="exportForm">
		<input type="hidden" id="orderProperty_export" name="company.orderProperty" value="id"/>
		<input type="hidden" id="orderType_export" name="company.orderType"/>
		<input type="hidden" id="staticDate" name="company.staticDate"  value="${company.staticDate}"/>
		<input type="hidden" id="firstArea" name="company.firstArea"  value="${company.firstArea}" />
		<input type="hidden" id="secondArea" name="company.secondArea" value="${company.secondArea}"/>
		<input type="hidden" id="thirdArea" name="company.thirdArea" value="${company.thirdArea}"/>
		<input type="hidden" id="fouthArea" name="company.fouthArea" value="${company.fouthArea}"/>
		<input type="hidden" id="pageSize" name="pagination.pageSize"   value="1000"/>
		<input type="hidden" id="companyName" name="company.companyName"   value="${company.companyName}"/>
		<input type="hidden" id="tradeTypes" name="company.tradeTypes"   value="${company.tradeTypes}"/>
</form>

<form  action="?company.year=${company.year}&company.month=${company.month}&area.areaCode=${area.areaCode}<#if company.industryId?exists && company.industryId!=0>&company.industryId=${company.industryId}</#if>&company.companyTrade=${company.companyTrade}" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
		<input type="hidden" id="orderProperty" name="company.orderProperty" value="id"/>
		<input type="hidden" id="orderType" name="company.orderType"/>
		<input type="hidden" id="pageSize" name="pagination.pageSize"   value="${pagination.pageSize}"/>
	  <tr>
	    <th>日     期</th>
	    <td width="35%"><input type="text" id="company.staticDate" name="company.staticDate"  value="${company.staticDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2010-01',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="9" maxlength="8"></td>
	    <th>区     域</th>
	    <td width="38%"><div id="div-area"></div></td>
	  </tr>
	  <tr>
	    <th >企业名称</th>
	    <td  id="td_trade" style="width:300px;" nowrap><input name="company.companyName" id="company.companyName" value="<#if company.companyName?exists>${company.companyName}</#if>" type="text" class="input" size="50"></td>
		<th>行业部门</th>
		    <td id="td_trade" style="width:300px;" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail.userIndustry!='anwei'>selected</#if>><#if userDetail.secondArea!=0 && t.name="贸易局" >商务局 <#else>${t.name} </#if></option></#if></#list></select>
		    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
		    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
		</tr>
	  <tr>
	<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but"  onClick="get('companyForm').submit();" /></div></th></tr>
	</table>
</form>
<div class="menu">
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="mexport();"><b>导出</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>

<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
	<tr>
	    <th  width="5%" style="cursor:hand;"   nowrap>序号</th>
	    <th  width="20%" style="cursor:hand;"  nowrap>单位名称</th>
	    <th  width="7%" style="cursor:hand;"  nowrap>联系人</th>
	    <th  width="10%" style="cursor:hand;"  nowrap>联系电话</th>
	    <th  width="20%"  id="th_address"  style="cursor:hand;" onClick="orderProperty('address');" nowrap><strong>地址&nbsp;<img src='${contextPath}/resources/default/img/arrow_for_two.gif' border='0'  width='7' height='20'/></strong></th>
	    <th  width="8%" style="cursor:hand;"  nowrap>发现一般事故<br>隐患数</th>
	    <th  width="8%" style="cursor:hand;"  nowrap>发现重大事故<br>隐患数</th>
	    <th  width="8%" style="cursor:hand;"  nowrap>季度统计报表<br>报送情况</th>
	    <th  width="8%" style="cursor:hand;"  nowrap>监管部门检查<br>发现隐患数</th>    
	</tr>
	<#if statistics?exists && statistics?size gt 0>
		<#list statistics?if_exists as s>
				<tr class="table03" <#if s_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
					<td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
					<td nowrap><div align="left"><#if s.companyName?exists>${s.companyName}<#else>/</#if></div></td>
					<td ><div align="center"><#if s.safetyMngPerson?exists>${s.safetyMngPerson}<#else>/</#if></div></td>
					<td ><div align="center"><#if s.safetyMngPersonPhone?exists>${s.safetyMngPersonPhone}<#else>/</#if></div></td>
					<td ><div align="left"><#if s.address?exists>${s.address}<#else>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</#if></div></td>
					<td title="点击查看企业一般隐患情况列表" nowrap onClick="showDaNomalDangers(${s.companyId},'${s.companyName}');" style="cursor:pointer;">${s.anum}</td>
					<td title="点击查看企业重大隐患情况列表" nowrap onClick="showDaDangers(${s.companyId},'${s.companyName}');" style="cursor:pointer;">${s.bnum}</td>	  
					<td nowrap><#if s.cnum==0>ㄨ<#else>√</#if></td>
					<td title="点击查看企业监管部门检查发现隐患情况列表" nowrap onClick="showDangersAndNomalDangers(${s.companyId},'${s.companyName}');" style="cursor:pointer;">${s.dnum}</td>	
				</tr>
		</#list>
	    <#else>
				<tr class="table03" bgcolor="#ffffff">
					<td colspan="10" align="center">暂无记录</td>
				</tr>
	    </#if>
				<tr class="table03" nowrap="nowrap" >
					<td  colspan="9">
						<@p.navigation pagination=pagination/>
					</td>
				</tr>
</table>
				<div style="text-align:left"  width="100%">
					<table  width="100%"  cellpadding="0" cellspacing="5">
						<tr>
						<td >
							<table width="98%" border="0" cellpadding="0" cellspacing="0" style="border: #CCCCCC solid 1px; border-collapse: collapse; background: #f6f6f6; font-size: 12px;line-height:200%;">
								<tr>
								<th width="15%" style="border: #CCCCCC solid 1px; color: #5494af; line-height: 30px; text-align: center;">
									说
									<br />
									明
								</th>
								<td width="85%" style="border: #CCCCCC solid 1px; padding-left: 10px; color: #878383; " style="text-align:left">
									<p>
										季度统计报表报送情况："√"代表本季度季报已上报，"ㄨ"代表本季度季报未上报。
									</p>
								</td>
							</tr>
						</table>
						</td>
						</tr>
					</table>
			
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.searchselectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</body>
<script type="text/javascript">

setTimeout('check()',500) ;

<#if company?exists>
		<#if company.orderProperty?exists && company.orderProperty=='address' >
			get("th_${company.orderProperty}").innerHTML = "<div align='left'><img src='${contextPath}/resources/default/img/"+(("${company.orderType?string}"=="true")?2:1)+".gif' border='0'/></div>"+get("th_${company.orderProperty}").innerHTML;
		</#if>
</#if>



function mexport(){
	if (${pagination.totalCount}>1000){
		alert("条数大于1000，请联系安生科技导出！");
	}else{
		get('exportForm').submit();
	}
}

function orderProperty(orderProperty) {
		var orderType = true;
		<#if company?if_exists.orderType?exists>
			orderType = "${company.orderType?string}"=="true"?false:true;
		</#if>
		get("orderType").value = orderType;
		get("orderProperty").value=orderProperty;
		
		get("orderType_export").value = orderType;
		get("orderProperty_export").value=orderProperty;		
		get('companyForm').submit();
}

function check(){
	if(${userDetail.thirdArea}!=null && ${userDetail.thirdArea}!="" && ${userDetail.thirdArea}!="0"){
			document.getElementById("first-area").disabled=true;
			document.getElementById("second-area").disabled=true;
			document.getElementById("third-area").disabled=true;
			
	}else if(${userDetail.secondArea}!=null && ${userDetail.secondArea}!="" && ${userDetail.secondArea}!="0"){
			document.getElementById("first-area").disabled=true;
			document.getElementById("second-area").disabled=true;
	}
}

<#if userDetail.userIndustry!='anwei'>
	changeTrade(get("tradeTypes1").value,1);
</#if>

function changeTrade(tradeId, type) {
	var length = 1;
	if(type == 1) {
		cleanSelect(get("tradeTypes2"));
		cleanSelect(get("tradeTypes3"));
	} else if (type == 2){
		cleanSelect(get("tradeTypes3"));
	}
	<#list tradeTypes?if_exists as t>
		<#list t.daIndustryParameters?if_exists as st>
			if (type == 1 && tradeId == "${t.id}" && "${st.type}" == 1) {
				var opt = new Option("${st.name}", "${st.id}");
				get("tradeTypes2").options[length] = opt;
				opt = null;
				length ++;
			}
			<#list st.daIndustryParameters?if_exists as sst>
				if (type == 2 && tradeId == "${st.id}" && "${sst.type}" == 1) {
					var opt = new Option("${sst.name}", "${sst.id}");
					get("tradeTypes3").options[length] = opt;
					opt = null;
					length ++;
				}
			</#list>
		</#list>
	</#list>
}
<#if company?exists>
	<#if company.tradeTypes?exists>
		var trades = "${company.tradeTypes}";
		getName("company.tradeTypes")[getName("company.tradeTypes").length-1].value = trades;
		for (var i=0;i<trades.split(",").length;i++) {
			if (trim(trades.split(",")[i]) != "") {
				get("tradeTypes"+(i+1)).value = trim(trades.split(",")[i]);
				changeTrade(trim(trades.split(",")[i]), (i+1));
			}
		}
	</#if>
</#if>


</script>
</#escape>
	<#include "statistic_foot.ftl">