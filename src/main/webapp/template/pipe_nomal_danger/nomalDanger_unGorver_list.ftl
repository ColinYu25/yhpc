<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#assign url='loadNomalDangers.xhtml'>
<@enum.initAreaXML/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">一般隐患列表</th>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
	<tr>
	  <th width="14%">管道名称</th>
	  <td colspan="6">${entity.name}&nbsp;</td>
	</tr>
	<tr>
	  <th>企业名称</th>
	  <td colspan="6">${entity.daPipelineCompanyinfo.company.companyName}&nbsp;</td>
	</tr>
	<tr>
	  <th>企业地址</th>
	  <td colspan="6">${entity.daPipelineCompanyinfo.company.regAddress}&nbsp;</td>
	</tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_del"><a class="b_del" href="javaScript:deleteNote(get('pipeNomalDangerForm'), 'deleteNomalDanger.xhtml')"><b>删除</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<form id="pipeNomalDangerForm" name="pipeNomalDangerForm" action="" method="post">
<input type="hidden" name="entityId" value="${entity.id}" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list" id="moreDanger">
	<tr>
		<th width="2%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
		<th width="3%" nowrap>序号</th>
		<!--<th width="5%" nowrap>有无隐患</th>-->
		<th width="6%" nowrap>隐患类别</th>
		<th width="19%" nowrap>隐患描述</th>
		<th width="12%" nowrap>隐患所在区域</th>
		<th width="5%" nowrap>治理状态</th>
		<th width="7%" nowrap>填报人</th>
		<th width="7%" nowrap>联系电话</th>
		<th width="7%" nowrap>联系手机</th>
		<th width="7%" nowrap>填报时间</th>
	</tr>
	<#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <!--<td><#if item.danger>有<#else>无</#if>&nbsp;</td>-->
	    <td>
	    <#if item.type?? && item.type=1010002>违章占压</#if>
		<#if item.type?? && item.type=1010003>检验检测</#if>
		<#if item.type?? && item.type=1010004>建设施工</#if>
		<#if item.type?? && item.type=1010005>标志标识</#if>
		<#if item.type?? && item.type=1010006>设备损坏</#if>
		<#if item.type?? && item.type=1010026>规划许可</#if>
		<#if item.type?? && item.type=1010025>使用登记</#if>
		<#if item.type?? && item.type=1010024>安全间距</#if>
		<#if item.type?? && item.type=1010007>其他</#if>
	    </td>
		<td>${item.description}&nbsp;</td>
		<td><#--if item.firstArea??><@enum.getSelectArea item.firstArea/></#if-->
		<#if item.secondArea??><@enum.getSelectArea item.secondArea/></#if>
		<#if item.thirdArea??><@enum.getSelectArea item.thirdArea/></#if></td>
		<td><#if item.repaired>已治理<#else>未治理</#if>&nbsp;</td>
		<td>${item.linkMan}&nbsp;</td>
		<td>${item.linkTel}&nbsp;</td>
		<td>${item.linkMobile}&nbsp;</td>
		<td>${item.fillDate}&nbsp;</td>
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
	function loadNoteMore() {
		if(chooseCheckBox()) {
			if(arguments[1])
				arguments[0].action=arguments[1];
			if(arguments[0].action != null)
				arguments[0].submit();
		}
	}
	
	function deleteNote() {
		if(chooseCheckBox()) {
			if(window.confirm('确定删除吗？')) {
				if(arguments[1])
					arguments[0].action=arguments[1];
				if(arguments[0].action != null)
					arguments[0].submit();
			}
		}
	}
</script>
</#escape> 
<@fkMacros.pageFooter />