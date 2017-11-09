<@fkMacros.pageHeader />
<#escape x as (x)!>
<script type="text/javascript">
	function createTrade(url) {
		var checkboxs = getTag('input');
		var checkSize = 0;
		for(var i=0;i<checkboxs.length; i++) {
			if(checkboxs[i].type=='checkbox'){
				if(checkboxs[i].checked) {
					checkSize ++;				
				}
			}
		}
		if(checkSize <= 1){
			if ("${admin.toString()}"=="false"&&checkSize==0) {
				alert("请选择一个父行业。");
			} else {
				get("tradeTypesForm").action = url;
				get("tradeTypesForm").submit();
			}
		} else {
			alert("请将父行业选择为一个。");
		}
	}
</script>
<body>
<#--if sysComit!=true><div style="background:url(../../resources/default/img/bg.jpg);width:98%;height:202px;"></div></#if-->
<div  style="width:100%;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">实施方案列表</th>
  </tr>
</table>
<form action="loadActualizeProject.xhtml?sysComit=${sysComit?string}" method="post" name="tradeTypeForm" id="tradeTypeForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th width="15%">标题：</th>
	    <td width="25%"><input type="text" name="daActualTableNotice.title" id="title" size="35" value="${daActualTableNotice.title}" maxlength="25"></td>
	    <th width="15%">发布时间：</th>
	    <td width="35%"><input type="text" name="daActualTableNotice.createTimeBegin" id="createTimeBegin" size="35" value="${daActualTableNotice.createTimeBegin?date}" style="ime-mode:disabled"  maxlength="10"size="14" maxlength="10" onfocus="WdatePicker();" class="Wdate"></td>
	  	<td width="10%"><input type="submit" id="sub" value="查  询" onClick="this.form.submit();" style="input_submit"/></td>
  	  </tr>
	</table>
</form>
</div>
<div class="menu" style="width:100%;">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if sysComit><li id="img_save">		<a href="#" class="b1" onClick="createTrade('createActualizeProjectInit.xhtml?sysComit=${sysComit?string}');"><b>添加</b></a></li>
	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('createActualizeProjectInit.xhtml?sysComit=${sysComit?string}&daActualTableNotice.id');"><b>修改</b></a></li>
	<li id="img_del">		<a href="#" class="b3" onClick="loadNote('deleteActualizeProject.xhtml?sysComit=${sysComit?string}&daActualTableNotice.id');"><b>删除</b></a></li></#if>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>

<form action="deleteActualizeProject.xhtml" method="post" name="tradeTypesForm" id="tradeTypesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%">序号</th>
    <th width="40%">标题</th>
    <th width="20%">发布时间</th>
    <!--<#if sysComit><th width="25%">是否审核</th></#if>-->
  </tr>
  <#if actualizeTableNoticeList?exists>
  	<#list actualizeTableNoticeList as actualizeTableNotice>
  		<tr>
  			<td><input id="id" name="ids" type="checkbox" value="${actualizeTableNotice.id}"/></td>
	    	<td><#if pagination.itemCount?exists>${pagination.itemCount+actualizeTableNotice_index+1}<#else>${actualizeTableNotice_index+1}</#if></td>
  			<td><div align="left"><a href="createActualizeProjectInit.xhtml?daActualTableNotice.id=${actualizeTableNotice.id}&sysComit=${sysComit?string}">${actualizeTableNotice.title}&nbsp;</a></div></td>
  			<td>&nbsp;${actualizeTableNotice.createTimeBegin}</td>
 			<!--<#if sysComit><td><#if actualizeTableNotice.auditing><a href="updateActualizeProject.xhtml?daActualTableNotice.id=${actualizeTableNotice.id}&state=false&sysComit=${sysComit?string}">取消审核</a><#else><a href="updateActualizeProject.xhtml?daActualTableNotice.id=${actualizeTableNotice.id}&state=true&sysComit=${sysComit?string}">审核</a></#if></td></#if>-->
  		</tr>
  	</#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (actualizeTableNoticeList?if_exists?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
</body>
<#--if sysComit!=true><div style="background:url(../../resources/default/img/footerbg.jpg) no-repeat;width:98%;height:73px;text-align:center;font-size:14px;line-height:20px;">
		<p>宁波市安全生产监督管理局</p><span>技术支持：</span><a href="http://www.safetys.cn/"> 安生科技</a><span id="lxdh">联系电话：0574-87364008</span>
	</div></#if-->
</#escape>
<@fkMacros.pageFooter />