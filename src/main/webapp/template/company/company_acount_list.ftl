<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>异地经营台帐</th>
  </tr>
</table>
<script type="text/javascript">
	var areaObj = new Area("${AreaXmlUrl}");
</script>
<div class="menu">
  	<ul>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteCompanies.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th id="th_id" width="4%" style="cursor:hand;" nowrap>序号</th>
    <th id="th_companyName" width="28%" style="cursor:hand;" nowrap>原来区域</th>
    <th id="th_address" width="25%" style="cursor:hand;" nowrap>现在区域</th>
    <th id="th_fdDelegate" width="8%" style="cursor:hand;" nowrap>法人代表</th>
    <th id="th_phone" width="12%" style="cursor:hand;" nowrap>联系电话</th>
    <th id="th_phone" width="12%" style="cursor:hand;" nowrap>是否宁波</th>
    <!--<th width="18%">所属行业</th>-->
  </tr>
  <#if companyAcounts?exists>
  	<#list companyAcounts?if_exists as c>
	  <tr>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div id="oldArea${c.id}" align="left"></div></td>
	    <td><div id="area${c.id}" align="left"></div></td>
	    <td>${c.fddelegate}&nbsp;</td>
	    <td>${c.tel}&nbsp;</td>
	    <td>
	    <#if c.isningbo>
	    	是
	    <#else>
	    	否
	    </#if>
	    </td>
	    <!--<td><#list c.hzTradeTypes?if_exists as ct><#list tradeTypes?if_exists as t><#if t.id==ct.id>${ct.tradeName} </#if></#list></#list>&nbsp;</td>-->
	    <!--<td><#list c.hzTradeTypes?if_exists as ct>${ct.tradeName} </#list>&nbsp;</td>-->
	  <!--  <td><#list tradeTypes?if_exists as t><#list c.hzTradeTypes?if_exists as ct><#if t==ct><!-- ${ct.tradeName}--><!--</#if></#list><#list t.hzTradeTypes?if_exists as st><#list c.hzTradeTypes?if_exists as ct><#if st==ct> ${ct.tradeName}</#if></#list><#list st.hzTradeTypes?if_exists as sst><#list c.hzTradeTypes?if_exists as ct><#if sst==ct> ${ct.tradeName}</#if></#list></#list></#list><#list c.hzTradeTypes?if_exists as ct><#if (t==ct&&c.hzTradeTypes?if_exists?size>1)>;</#if></#list></#list>&nbsp;</td> -->
	  </tr>
	 </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>

<script type="text/javascript">
	var firstArea="";
	var secondArea="";
	var thirdArea="";
	var oldfirstArea="";
	var oldsecondArea="";
	var oldthirdArea="";
   <#if companyAcounts?exists>
  	<#list companyAcounts?if_exists as c>
  		<#if c.oldfirstArea != 0>
			oldfirstArea=areaObj.getArea("${c.oldfirstArea}")[0];
		</#if>
		<#if c.oldsecondArea != 0>
			oldsecondArea=areaObj.getArea("${c.oldsecondArea}")[0];
		</#if>
		<#if c.oldthirdArea != 0>
			oldthirdArea=areaObj.getArea("${c.oldthirdArea}")[0];
		</#if>
			get("oldArea${c.id}").innerHTML=oldfirstArea+" "+oldsecondArea+" "+oldthirdArea;
 		<#if c.firstArea != 0>
			firstArea=areaObj.getArea("${c.firstArea}")[0];
		</#if>
		<#if c.secondArea != 0>
			secondArea=areaObj.getArea("${c.secondArea}")[0];
		</#if>
		<#if c.thirdArea != 0>
			thirdArea=areaObj.getArea("${c.thirdArea}")[0];
		</#if>
		get("area${c.id}").innerHTML=firstArea+" "+secondArea+" "+thirdArea;
	</#list>
  </#if>
	
</script>

</#escape>
<@fkMacros.pageFooter />