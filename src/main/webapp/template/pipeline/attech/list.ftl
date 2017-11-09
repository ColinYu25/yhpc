<@fkMacros.pageHeader />
<@fkMacros.initAreaXML />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<div id="main">
	<div id="contents">
		<div class="menu">
		  	<ul>
			<li id="img_save"><a href="JavaScript:create();" class="b1"><b>添加</b></a></li>
			<li id="img_amend"><a class="b2" href="javaScript:loadNote('attech_update.xhtml?entity.id');"><b>修改</b></a></li>
			<li id="img_del"><a class="b3" href="javaScript:del();"><b>删除</b></a></li>  	
			<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
			<li id="img_educe"><a class="b11" href="javaScript:setParentAttech();"><b>完成</b></a></li>
			</ul>
		</div>
		<form action="cyrymjcs_list.xhtml" method="post" name="companiesForm" id="companiesForm">
		<input type="hidden" name="entity.daPipelineInfo.id" value="${entity.daPipelineInfo.id}" id="pipeLineId" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
			  <tr>
			  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
			    <th width="4%" nowrap>序号</th>
			    <th width="46%" nowrap>区域</th>     
			    <th width="46%" nowrap>名称</th>
			  </tr>
			  <#if result?exists>
			  	<#list result?if_exists as item>
				  <tr>
				  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
				    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
				    <td>
				    <#if item.firstArea??>
				    <@fkMacros.getSelectArea item.firstArea/>	    
				    </#if>
					<#if item.secondArea??>
				    <@fkMacros.getSelectArea item.secondArea/>	    
				    </#if>
				    <#if item.thirdArea??>
				    <@fkMacros.getSelectArea item.thirdArea/>	    
				    </#if>
				    &nbsp;
				    </td>	    
				    <td>
				    ${item.title}
				    </td>  	    
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
	</div>
</div>
</#escape>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
	jQuery.noConflict();
	
	function create(){
		var pipeLineId = jQuery("#pipeLineId").val();
		if (pipeLineId != ""){
			window.location.href = "attech_create.xhtml?entity.type=${entity.type}&entity.daPipelineInfo.id=" + pipeLineId;	
		}
	}
	
	function del(){
		var ids = document.getElementsByName("ids");
		if (ids == null || ids.length == 0){
			alert("没有找到要删除的数据");
			return;
		}
		
		if (!window.confirm("确实要删除吗？")){
			return;
		}
		
		var pipeLineId = jQuery("#pipeLineId").val();
		for(var i=0; i<ids.length; i++){
			if (ids[i].checked){
				window.location.href = "attech_delete.xhtml?entity.type=${entity.type}&entity.id=" + ids[i].value + "&entity.daPipelineInfo.id=" + pipeLineId;
				return;
			}
		}
		alert("请选择要删除的数据");
	}

	function setParentAttech(){
		window.parent.setAttechValue(${entity.type},${pagination.totalCount});
		closeWindows("companies");
	}
</script>