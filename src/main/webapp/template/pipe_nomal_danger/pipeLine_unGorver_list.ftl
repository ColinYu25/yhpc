<@fkMacros.pageHeader />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>一般隐患未治理管道列表</th>
  </tr>
</table>
<form action="" method="post" name="pipeLineForm" id="pipeLineForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	  	<th>管道名称</th>
	    <td width="35%"><input type="text" name="entity.name" id="name" value="${entity.name}" size="45" maxlength="50"></td>
		<#if company?? && company.id??>
	    <th>管道传输介质</th>
	    <td width="35%">
	    	<input type="text" name="entity.medium" value="${entity.medium}" id="medium" maxlength="50"/>
	    </td>
	  	<#else>
	  	<th>管道所属企业</th>
	    <td width="35%"><input type="text" name="entity.daPipelineCompanyinfo.company.companyName" id="companyName" value="${entity.daPipelineCompanyinfo.company.companyName}" size="45" maxlength="50"></td>
	  	</#if>
	  </tr>
	  <tr>
	  	<th>管道种类</th>
	    <td width="35%">
	    	<select name="entity.type">
	    		<option value="-1">-请选择-</option>
	    		<option value="1" <#if entity?? && entity.type?? && entity.type == 1>selected</#if>>长输管道</option> 
	    		<option value="2" <#if entity?? && entity.type?? && entity.type == 2>selected</#if>>城镇燃气管道</option> 
	    		<option value="3" <#if entity?? && entity.type?? && entity.type == 3>selected</#if>>工业管道</option> 
	    		<option value="4" <#if entity?? && entity.type?? && entity.type == 4>selected</#if>>港区内油气管道</option> 
	    		<option value="0" <#if entity?? && entity.type?? && entity.type == 0>selected</#if>>中低压燃气管道</option>  
	    	</select>
	    </td>
	    <th>管道铺设方式</th>
	    <td width="35%">
	    	<select name="entity.buildType">
	    		<option value="">-请选择-</option>
	    		<option value="1" <#if entity?? && entity.buildType?? && entity.buildType == 1>selected</#if>>架空</option> 
	    		<option value="2" <#if entity?? && entity.buildType?? && entity.buildType == 2>selected</#if>>埋地</option> 
	    		<option value="3" <#if entity?? && entity.buildType?? && entity.buildType == 3>selected</#if>>地面</option>
	    	</select>
	    </td>
	  </tr>
	  <!--<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm('pipeLineForm');"/></div></th></tr>-->
	</table>
</form>
<div class="menu">
  	<ul>
	<li id="img_xcjcjl"><a class="b_xcjcjl" href="javaScript:loadNote('loadNomalDangersUnGorver.xhtml?entity.id')"><b>隐患列表</b></a></li>
	<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
  	<li id="img_lookup"><a class="b13" href="javaScript:document.getElementById('pipeLineForm').submit();"><b>查询</b></a></li>
	</ul>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="2%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="12%" nowrap>管道名称</th>
    <th width="4%" nowrap>隐患数</th>
    <#if company?? && company.id??><th width="8%" nowrap>传输介质</th><#else><th width="21%" nowrap>所属企业</th></#if>   
    <th width="10%" nowrap>管道种类</th>
    <th width="12%" nowrap>管道起点</th>    
    <th width="13%" nowrap>管道止点</th>  
    <th width="8%" nowrap>竣工时间</th>                 
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td style="text-align:center"><a href="../pipeline/yq_pipeline_update.xhtml?entity.id=${item.id}">${item.name}</a>&nbsp;</td>
	    <td title="查看管道隐患信息" onClick="checkDangerByPipeId(${item.id});" style="cursor:pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';">${item.unRepairCount}</td>
	    <td>
	    	<#if company?? && company.id??>${item.medium}<#else>${item.daPipelineCompanyinfo.company.companyName}</#if>&nbsp;
	    </td>
	    <td>
		    <#if item?? && item.type?? && item.type == 1>长输管道</#if>
	        <#if item?? && item.type?? && item.type == 2>城镇燃气管道</#if>
	        <#if item?? && item.type?? && item.type == 3>工业管道</#if>
	        <#if item?? && item.type?? && item.type == 4>港区内油气管道</#if>&nbsp;
	    </td>
	    <td>${item.startPoint}&nbsp;</td>
	    <td>${item.endPoint}&nbsp;</td>
	    <td>${item.buildEndDate?string('yyyy-MM-dd')}&nbsp;</td>	    	    	    
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
</#escape>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
	var trMoveColor="#cae5ff"; 
	function checkDangerByPipeId(id){
		if(id && id!="")
			window.location = "loadNomalDangersUnGorver.xhtml?entity.id=" + id;
	}
</script>