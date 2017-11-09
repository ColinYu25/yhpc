<@fkMacros.pageHeader />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
	
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>管道信息列表</th>
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
	    		<option value="1" <#if entity?? && entity.type?? && entity.type == 1>selected</#if>>石油天然气管道</option> 
	    		<option value="2" <#if entity?? && entity.type?? && entity.type == 2>selected</#if>>城镇燃气管道</option> 
	    		<option value="3" <#if entity?? && entity.type?? && entity.type == 3>selected</#if>>危险化学品管道</option> 
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
	<ul><li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
  	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
  	<li id="img_lookup"><a class="b13" href="javaScript:document.getElementById('pipeLineForm').submit();"><b>查询</b></a></li>
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<input type="hidden" name="entity.daPipelineCompanyinfo.id" value="${entity.daPipelineCompanyinfo.id}" id="companyId" />
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%" nowrap>序号</th>
    <th width="20%" nowrap>使用单位</th>
    <th width="20%" nowrap>管道名称</th>
    <th width="10%" nowrap>管道长度</th>    
    <th width="10%" nowrap>铺设方式</th>
    <th width="10%" nowrap>管道起点</th>        
    <th width="10%" nowrap>管道止点</th>  
    <th width="10%" nowrap>竣工时间</th>                  
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td><a href="yq_pipeline_update.xhtml?entity.id=${item.id}">${item.daPipelineCompanyinfo.company.companyName}</a>&nbsp;</td>	    
	    <td><a href="yq_pipeline_update.xhtml?entity.id=${item.id}">${item.name}</a>&nbsp;</td>
	    <td><a href="yq_pipeline_update.xhtml?entity.id=${item.id}">${item.length?string("####.####")} Km</a>&nbsp;</td>
	    <td>
	    <#if item.buildType?? && item.buildType ==1>
	    	架空	    
	    <#elseif item.buildType?? && item.buildType ==2>
	    	埋地
	    <#else>
	    	地面
	    </#if>&nbsp;
	    </td>
	    <td>
	    ${item.startPoint}&nbsp;
	    </td>
	    <td>
	    ${item.endPoint}&nbsp;
	    </td>
	    <td>
	    ${item.buildEndDate?string('yyyy-MM-dd')}&nbsp;
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

</#escape>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
jQuery.noConflict();
</script>