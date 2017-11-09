<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<!--<th width="98%" height="22" >重大隐患信息表</th>-->
	<th width="98%" height="22" >重大事故隐患情况表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<table width="98%" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
	<th style="width:14%;" align="right" nowrap>单位名称</th>
	<td colspan="2">&nbsp;${company.companyName}</td>
	<th width="11%" align="right" nowrap>隐患编号</th>
	<td colspan="2">&nbsp;${danger.dangerNo}</td>
  </tr>
  <tr>
    <th style="width:12%;" align="right" nowrap>单位地址</th>
    <td colspan="2">&nbsp;${company.regAddress}</td>
    <th width="11%" align="right" nowrap>市级以上重点工程</th>
	<td colspan="2"> <input type="radio" name="danger.emphasisProject" value="true" <#if danger?if_exists.emphasisProject?exists&&danger.emphasisProject>checked</#if>>
      是    
      <input type="radio" name="danger.emphasisProject" value="false" <#if !danger?if_exists.emphasisProject?exists||!danger.emphasisProject>checked</#if>>
    否</td>
  </tr>
  <tr>
    <th style="width:12%;" align="right" nowrap>隐患地址</th>
    <td colspan="5">${danger.dangerAdd}&nbsp;</td>
  </tr>
  <tr>
    <th style="width:12%;" align="right" nowrap>隐患区域</th>
    <td colspan="5"><div id="div-area"></div></td>
  </tr>
  <tr>
    <th style="width:12%;" align="right" nowrap>联 系 人</th>
    <td width="20%">${danger.linkMan}&nbsp;</td>
    <th style="width:12%;" align="right" nowrap>联系电话</th>
    <td width="20%">${danger.linkTel}&nbsp;</td>
    <th style="width:12%;" align="right" nowrap>手　　机</th>
    <td width="28%">${danger.linkMobile}&nbsp;</td>
  </tr>
  <tr>
    <th style="width:12%;" align="right" nowrap>隐患基本情况<br>
    （简述）</th>
    <td colspan="5">${danger.description}&nbsp;</td>
  </tr>
  <tr>
    <th style="width:12%;" align="right" nowrap>隐患类别 <span class="red_point">*</span></th>
    <td colspan="5">
    <#if industryParameters?exists>
  	<#list industryParameters?if_exists as i>
  		<#assign checked=''>
  			<#if danger?exists>
  				<#list danger.daIndustryParameters?if_exists as ddi>
  					<#if i.id=ddi.id>
  						<#assign checked='checked="checked"'>
  					</#if>
  				</#list>
  			</#if>
  		<input type="checkbox" ${checked} name="danger.industryParameters" value="${i.id}" />&nbsp;${i.name}<#if (i_index+1)%3!=0><br></#if>
  	</#list>
  	</#if>
  	</td>
  </tr>
 </table>
 <table width="98%" cellpadding="0" cellspacing="0" class="table_input">
 <tr>
    <th colspan="6"><p align="center">整治说明</th>
  </tr>
  </tr>
  <tr>
    <th style="width:12%;" nowrap>是否需政府协调</th>
    <td width="20%" align="center" nowrap>
      <input type="radio" name="danger.govCoordination" value="true" <#if danger?if_exists.govCoordination?exists&&danger.govCoordination>checked</#if>>
      是    
      <input type="radio" name="danger.govCoordination" value="false" <#if !danger?if_exists.govCoordination?exists||!danger.govCoordination>checked</#if>>
    否 </td>
    <th style="width:12%;" nowrap>是否需局部停产停业</th>
    <td width="20%" align="center" nowrap>
      <input type="radio" name="danger.partStopProduct" value="true" <#if danger?if_exists.partStopProduct?exists&&danger.partStopProduct>checked</#if>>
      是    
      <input type="radio" name="danger.partStopProduct" value="false" <#if !danger?if_exists.partStopProduct?exists||!danger.partStopProduct>checked</#if>>
    否 </td>
    <th style="width:12%;" nowrap>是否需全部停产停业</th>
    <td width="20%" align="center" nowrap>    
      <input type="radio" name="danger.fullStopProduct" value="true" <#if danger?if_exists.fullStopProduct?exists&&danger.fullStopProduct>checked</#if>>
      是    
      <input type="radio" name="danger.fullStopProduct" value="false" <#if !danger?if_exists.fullStopProduct?exists||!danger.fullStopProduct>checked</#if>>
    否 </td>
  </tr>
  <tr>
    <th colspan="6"><p align="center">隐患治理落实措施情况</p></th>
  </tr>
  <tr>
    <th style="width:12%;" nowrap>落实治理目标</th>
    <td align="center" nowrap>
      <input type="radio" name="danger.target" value="true" <#if danger?if_exists.target?exists&&danger.target>checked</#if>>
    是    
    <input type="radio" name="danger.target" value="false" <#if !danger?if_exists.target?exists||!danger.target>checked</#if>>
    否 </td>
    <th style="width:12%;">落实治理机构人员</th>
    <td align="center" nowrap>
      <input type="radio" name="danger.resource" value="true" <#if danger?if_exists.resource?exists&&danger.resource>checked</#if>>
    是    
    <input type="radio" name="danger.resource" value="false" <#if !danger?if_exists.resource?exists||!danger.resource>checked</#if>>
    否 </td>
    <th style="width:12%;" nowrap>落实安全措施<br>及应急预案</th>
    <td align="center" nowrap>
      <input type="radio" name="danger.safetyMethod" value="true" <#if danger?if_exists.safetyMethod?exists&&danger.safetyMethod>checked</#if>>
    是    
    <input type="radio" name="danger.safetyMethod" value="false" <#if !danger?if_exists.safetyMethod?exists||!danger.safetyMethod>checked</#if>>
    否 </td>
  </tr>
  <tr>
    <th style="width:12%;" nowrap>落实治理经费物资</th>
    <td align="center" nowrap>
      <input type="radio" name="danger.goods" value="true" <#if danger?if_exists.goods?exists&&danger.goods>checked</#if>>
    是    
    <input type="radio" name="danger.goods" value="false" <#if !danger?if_exists.goods?exists||!danger.goods>checked</#if>>
    否 </td>
    <th style="width:12%;">计划完成治理时间</th>
    <td>${danger.finishDate?date}&nbsp;</td>
    <th style="width:12%;">落实治理经费</th>
    <td>${danger.governMoney.toString()} 万元</td>
  </tr>
  <tr>
    <th style="width:12%;" nowrap>单位负责人</th>
    <td>
    ${danger.chargePerson}&nbsp;</td>
    <th style="width:12%;">填报时间</th>
    <td>${danger.fillDate?date}&nbsp;</td>
    <th style="width:12%;">填 报 人</th>
    <td>${danger.fillMan}&nbsp;</td>
  </tr>
</table>
</form>
<@fkMacros.muilt_select_js />
<#if danger?has_content>
	<#if danger?if_exists.id?if_exists!=-1>
		<@fkMacros.selectAreas_fun "${danger?if_exists.firstArea?if_exists}" "${danger?if_exists.secondArea?if_exists}" "${danger?if_exists.thirdArea?if_exists}" "${danger?if_exists.fouthArea?if_exists}" "${danger?if_exists.fifthArea?if_exists}" "danger."/>
	<#else>
		<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "danger."/>
	</#if>
<#else>
	<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "danger."/>
</#if>
</#escape>
<@fkMacros.pageFooter />