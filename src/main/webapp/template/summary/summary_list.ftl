<@fkMacros.pageHeader />
<#escape x as (x)!>
<@fkMacros.initAreaXML />
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
	<tr><th>隐患列表</th></tr>
</table>
<form action="${base}/summary/summary_list.xhtml" method="post" name="form1" id="form1">
	<input type="hidden" name="backNum" value="${backNum}" />
	<input type="hidden" name="hiddenVo.year" value="${hiddenVo.year}" />
	<input type="hidden" name="hiddenVo.hiddenLevel" value="${hiddenVo.hiddenLevel}" />
	<input type="hidden" name="hiddenVo.fromSys" value="${hiddenVo.fromSys}" />
	<#if hiddenVo.completed??>
		<input type="hidden" name="hiddenVo.completed" value="${hiddenVo.completed?string}" />
	</#if>
	<input type="hidden" name="hiddenVo.type1" value="${hiddenVo.type1}" />
	<input type="hidden" name="hiddenVo.secondArea" value="${hiddenVo.secondArea}" />
	<input type="hidden" name="hiddenVo.thirdArea" value="${hiddenVo.thirdArea}" />
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
		<tr>
		    <th>单位名称</th>
		    <td width="35%"><input type="text" name="hiddenVo.companyName" id="companyName" value="${hiddenVo.companyName}" size="39" maxlength="50"></td>
		    <th>隐患描述</th>
		    <td width="38%"><input type="text" name="hiddenVo.description" id="description" value="${hiddenVo.description}" size="35" maxlength="50"></td>
	  	</tr>
	  	<#--<tr>
		    <th>区　　域</th>
		    <td colspan="3"><div id="div-area"></div></td>
	  	</tr>-->
	  	<tr>
			<th colspan="4">
				<div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm();"/></div>
			</th>
		</tr>
	</table>
</form>
<div class="menu">
	<ul>
		<!--<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>-->
		<li id="img_return"><a href="javascript:history.go(-${backNum});" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
	<tr>
	    <th width="4%" nowrap>序号</th>
	    <th width="25%" nowrap>单位名称</th>
	    <th width="20%" nowrap>所在区域</th>
	    <th nowrap>隐患描述</th>
	    <th width="15%" nowrap>隐患分类</th>
	    <th width="10%" nowrap>发现时间</th>
	    <th width="7%" nowrap>是否整改</th>
 	</tr>
  	<#if hiddenSummaryList?exists>
  		<#list hiddenSummaryList?if_exists as c>
	  		<tr>
			    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
			    <td>${c.companyName}</td>
			    <td><@fkMacros.getSelectArea c.secondArea/> <@fkMacros.getSelectArea c.thirdArea/></td>
			    <td><a href="${base}/summary/summary_view.xhtml?entity.id=${c.id}">${c.description}</a></td>
			    <td>${c.type1Text}</td>
			    <td>${c.hiddenDate?string('yyyy-MM-dd')}</td>
			    <td><#if c.completed>是<#else>否</#if></td>
	  		</tr>
	 	</#list>
	</#if>
</table>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
<script type="text/javascript">
function submitForm() {
	var formObj = document.getElementById("form1");
	formObj.submit();
}
</script>
<#--<@fkMacros.searchselectAreas_fun "${hiddenVo?if_exists.firstArea?if_exists}" "${hiddenVo?if_exists.secondArea?if_exists}" "${hiddenVo?if_exists.thirdArea?if_exists}" "" "" "hiddenVo."/>-->
</#escape>
<@fkMacros.pageFooter />