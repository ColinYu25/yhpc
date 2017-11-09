<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if daNomalDanger.repair?if_exists==0>
	<#assign letter='未治理'>
	<#assign surl='loadNomalDangersUnGorver.xhtml'>
<#elseif  daNomalDanger.repair?if_exists==1>
	<#assign letter='已治理'>
	<#assign surl='loadNomalDangersGorver.xhtml'>
<#elseif  daNomalDanger.repair?if_exists==2>
	<#assign surl='loadNomalDangers.xhtml'>
</#if>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th><#if userDetail.userIndustry!='qiye'><input type="text" id="year" value="${daNomalDanger.nowYear}年" onChange="winLocation();" onfocus="WdatePicker({dateFmt:'yyyy年',vel:'year_2',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="10">
    <input type="hidden" name="daNomalDanger.nowYear" id="year_2" value="${daNomalDanger.nowYear}"/></#if>${company.companyName}一般隐患${letter}列表</th>
  </tr>
</table>
<div class="menu">
  	<ul>
  	<#if otherTradeDanger==0>
  	<li id="img_save"><a href="#" class="b1" onClick="submitNomalDanger('createNomalDangerInit.xhtml');"><b>添加</b></a></li>
  	<!--
  	<li id="img_amend"><a href="#" class="b2" onClick="loadNote('createNomalDangerInit.xhtml?<#if company??>company.id=${company.id}</#if>&daNomalDanger.id');"><b>修改</b></a></li>
  	<li id="img_amend"><a href="#" class="b2" onClick="submitNomalDanger('deleteNomalDanger.xhtml');"><b>删除</b></a></li>-->
  	<#if userDetail.userIndustry!='anwei'>  	
  	<li id="img_004"><a href="#" class="b_004" onClick="submitNomalDanger('${surl}?otherTradeDanger=1&daNomalDanger.nowYear=${daNomalDanger.nowYear}')"><b><#if userDetail.userIndustry=='qiye'>行业部门填报<#else>企业或行业填报</#if></b></a></li>
  	</#if>
  	<#else>
  	<li id="img_zgtzs"><a href="#" class="img_zgtzs" onClick="submitNomalDanger('${surl}?otherTradeDanger=0&daNomalDanger.nowYear=${daNomalDanger.nowYear}')"><b><#if userDetail.userIndustry=='qiye'>企业填报<#else>本行业隐患</#if></b></a></li>
  	</#if>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>

<form name="nomalDangerForm" id="nomalDangerForm" method="post">
<input type="hidden" name="company.id" value="${company.id}" />
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
<tr> 
<#if otherTradeDanger==0>
	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
</#if>
<th style="cursor:pointer;" width="4%">序号</th>
<#if otherTradeDanger==1>
<th style="cursor:pointer;" width="12%">行业部门</th>
</#if>
<th style="cursor:pointer;" width="15%">隐患类别</th>
<th style="cursor:pointer;" width="30%">隐患描述</th>
<th style="cursor:pointer" width="12%">联 系 人</th>
<th style="cursor:pointer" width="18%">完成整改时间</th>
</tr>
<#if daNomalDangers?exists>
<#if (daNomalDangers?size gt 0)>
	<#list daNomalDangers?if_exists as nd>
	<tr>
	<#if otherTradeDanger==0>
		<td><input name="ids" type="checkbox" value="${nd.id}" id="ids${nd.id}" <#if nd.userId!=userDetail.userId>disabled="true"</#if>/></td>
	</#if>
	<td><#if pagination.itemCount?exists>${pagination.itemCount+nd_index+1}<#else>${nd_index+1}</#if></td>
	<#if otherTradeDanger==1>
	<td><#if nd.industryName?exists>${nd.industryName}<#else>企业填报</#if>&nbsp;</td>
	</#if>
	<td><#if nd.danger><#if nd.industry??>${nd.industry.name}<#else>未选择隐患类别</#if><#else>零隐患</#if></td>
	<!--<td><div align="left"><#if otherTradeDanger==0 && userDetail.userIndustry!='anwei' && nd.userId==userDetail.userId><a href="createNomalDangerInit.xhtml?company.id=${company.id}&daNomalDanger.id=${nd.id}">${nd.description}</a><#else>${nd.description}</#if>&nbsp;</div></td>-->
	<td><div align="left"><#if otherTradeDanger==0 && nd.userId==userDetail.userId><a href="createNomalDangerInit.xhtml?company.id=${company.id}&daNomalDanger.id=${nd.id}"><#if nd.danger><#if nd.description?exists>${nd.description}<#else>未填写隐患描述</#if><#else>零隐患</#if></a><#else><#if nd.danger><#if nd.description?exists>${nd.description}<#else>未填写隐患描述</#if><#else>零隐患</#if></#if>&nbsp;</div></td>
	<td>${nd.linkMan}</td>
	<td><#if nd.danger><#if nd.completedDate?exists>${nd.completedDateString}<#else>未整改</#if><#else>&nbsp;</#if></td>
	</tr>
	</#list>
<#else>
	<tr><td colspan=6>暂无数据</td></tr>
</#if>
<#else>
	<tr><td colspan=6>暂无数据</td></tr>
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
function submitNomalDanger(url){
	var nomaldForm=get("nomalDangerForm");
	nomaldForm.action=url;
	nomaldForm.submit();
}
function winLocation(){
	window.location="?otherTradeDanger=${otherTradeDanger}&company.id=${company.id}&daNomalDanger.nowYear="+get("year_2").value.substring(0,4);
}
</script>
</#escape> 
<@fkMacros.pageFooter />