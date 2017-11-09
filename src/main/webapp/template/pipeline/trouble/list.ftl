<@fkMacros.pageHeader />
<@fkMacros.initAreaXML />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<div id="main">
	<div id="contents">
		<div class="menu">
		  	<ul>
			<li id="img_save"><a href="JavaScript:create();" class="b1"><b>添加</b></a></li>
			<li id="img_amend"><a class="b2" href="javaScript:loadNote('pipeTrouble_update.xhtml?entity.id');"><b>修改</b></a></li>
			<li id="img_del"><a class="b3" href="javaScript:del();"><b>删除</b></a></li>  	
			<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
			<li id="img_educe"><a class="b11" href="javaScript:setParentTrouble();"><b>完成</b></a></li>		
			</ul>
		</div>
		<form action="trouble_list.xhtml" method="post" name="companiesForm" id="companiesForm">
		<input type="hidden" name="entity.daPipelineInfo.id" value="${entity.daPipelineInfo.id}" id="pipeLineId" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
			  <tr>
			  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
			    <th width="4%" nowrap>序号</th>
			    <th width="25%" nowrap>区域</th>   
			    <th width="10%" nowrap>隐患类型</th>
			    <th width="25%" nowrap>隐患内容</th>
			    <th width="25%" nowrap>治理状态</th>        
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
				    <#if item.type?? && item.type == 1010002>
				    	违章占压
				    <#elseif item.type?? && item.type == 1010003>
				    	检验检测
				    <#elseif item.type?? && item.type == 1010004>
				    	建设施工  
				    <#elseif item.type?? && item.type == 1010005>
				   		 标志标识
				    <#elseif item.type?? && item.type == 1010006>
				    	设备损坏
				    <#elseif item.type?? && item.type == 1010007>
				    	其他
					</#if>	    	    
				    </td>
				    <td>
				    <#if item?? && item.content?? && item.content?length gt 30>
					    <span title="${item.content}">${item.content?substring(0, 30)}</span>
				    <#else>
					    ${item.content}	    
				    </#if>
			
				    </td>   	    
				    <td>
				    <#if item.status?? && item.status == 1>
					    已治理 
				    <#else>
					    未治理
				    </#if>
				    </td>   	  	    
				  </tr>
				 </#list>
			  </#if>
			</table>
		</form>
		<table width="100%" cellpadding="0" cellspacing="1" >
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
			window.location.href = "pipeTrouble_create.xhtml?entity.daPipelineInfo.id=" + pipeLineId;	
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
				window.location.href = "pipeTrouble_delete.xhtml?entity.type=3&entity.id=" + ids[i].value + "&entity.daPipelineInfo.id=" + pipeLineId;
				return;
			}
		}
		alert("请选择要删除的数据");
	}
	
	function setParentTrouble(){
		window.parent.setTroubleValue(${pagination.totalCount});
		closeWindows("companies");
	}
</script>