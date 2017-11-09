<@fkMacros.pageHeader />
<#escape x as (x)!>
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">分数列表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="createPointTypeInit.xhtml?pointType.daIndustryParameter.id=${pointType.daIndustryParameter.id}" class="b1"><b>添加</b></a></li>
	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('loadPointType.xhtml?pointType.id');"><b>修改</b></a></li>
	<li id="img_del">		<a href="#" class="b3" onClick="deleteNote(get('pointTypesForm'));"><b>删除</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<form action="deletePointType.xhtml" method="post" name="pointTypesForm" id="pointTypesForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%">序号</th>
    <th width="40%">名　称</th>
    <th width="20%">分　数</th>
    <th width="10%">修改时间</th>
    <th width="10%">创建时间</th>
  </tr>
  <#if pointTypes?exists>
  	<#list pointTypes?if_exists as m>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${m.id}" value="${m.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+m_index+1}<#else>${m_index+1}</#if></td>
	    <td><p align="left"><a href="loadPointType.xhtml?pointType.id=${m.id}">${m.name}</a>&nbsp;</p></td>
	    <td>${m.point}&nbsp;</td>
	    <td>${m.modifyTime.toString().substring(0,10)}&nbsp;</td>
	    <td>${m.createTime.toString().substring(0,10)}&nbsp;</td>
	  </tr>
	 </#list>
  </#if>
</table>
<input type="hidden" name="pointType.daIndustryParameter.id" id="typeId" value="${pointType.daIndustryParameter.id}"/>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</body>
</#escape>
<@fkMacros.pageFooter />