<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if daActualTableNotice?exists>
  		<#assign url='updateActualizeProject.xhtml?sysComit=true'>
<#else>
  		<#assign url='createActualizeProject.xhtml?sysComit=true'>
</#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("daActualTableNoticeForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
</script>
<body>
<#--if sysComit!=true><div style="background:url(../../resources/default/img/bg.jpg);width:98%;height:202px;"></div></#if-->
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th><#if sysComit><#if daActualTableNotice?exists>修改<#else>添加</#if></#if>实施方案</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if sysComit><li id="img_save"><a href="#" class="b_xcjcjl" onClick="submitCreate();"><b>保存</b></a></li></#if>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'daActualTableNoticeForm' />
<form id="daActualTableNoticeForm" name="daActualTableNoticeForm" method="post" action="">
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th >标  题</th>
        <td><#if sysComit><input name="daActualTableNotice.title" type="text" class="input" id="title" value="${daActualTableNotice.title}" size="40" maxlength="50">&nbsp;
        <span style=color:red>*</span><ui:v for="title" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.title}</#if></td>
        <th >起草时间</th>
        <td><#if sysComit><input name="daActualTableNotice.createTimeBegin" type="text"  id="createTimeBegin" value="${daActualTableNotice.createTimeBegin}"  style="ime-mode:disabled"  maxlength="10"size="25" onfocus="WdatePicker();" class="Wdate" >&nbsp;
        <span style=color:red>*</span><ui:v for="createTimeBegin" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.createTimeBegin}</#if></td>
      	</tr>
      </tr>
      <tr>
       	<th>起草人</th>
        <td><#if sysComit><input name="daActualTableNotice.author" type="text" class="input" id="author" value="${daActualTableNotice.author}" size="40" maxlength="25">&nbsp;
        <span style=color:red>*</span><ui:v for="author" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.author}</#if></td>
        <th>起草部门</th>
        <td><#if sysComit><input name="daActualTableNotice.dept" type="text" class="input" id="dept" value="${daActualTableNotice.dept}" size="25" maxlength="25">&nbsp;
        <span style=color:red>*</span><ui:v for="dept" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.dept}</#if></td>
      	</tr>
      <tr>
      <th >内容</th>
      	<td colspan="3"><#if sysComit><textarea name="daActualTableNotice.content" id="content" style="display:none" >${daActualTableNotice.content}</textarea>
             <iframe ID="editor" src="../editor/ewebeditor.htm?id=content" frameborder=1 scrolling=no width="98%" height="405"></iframe><#else>${daActualTableNotice.content}</#if></td>
      	</td>
      </tr>
      <input type="hidden" name="daActualTableNotice.id" value="${daActualTableNotice.id}">
      <input type="hidden" name="daActualTableNotice.type" value="${daActualTableNotice.type}">
      <input type="hidden" name="daActualTableNotice.auditing" value="${daActualTableNotice.auditing?string}">
      <input type="hidden" name="daActualTableNotice.createTime" value="${daActualTableNotice.createTime?date}">
</table>
</form>
<#--if sysComit!=true><br><div style="background:url(../../resources/default/img/footerbg.jpg);width:100%;height:73px;text-align:center;font-size:14px;line-height:20px;">
		<p>宁波市安全生产监督管理局</p><span>技术支持：</span><a href="http://www.safetys.cn/"> 安生科技</a><span id="lxdh">联系电话：0574-87364008</span>
	</div></#if-->
</#escape>
<@fkMacros.pageFooter />