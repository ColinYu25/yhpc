<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if daActualTableNotice?exists>
  		<#assign url='updateNotice.xhtml'>
<#else>
  		<#assign url='createNotice.xhtml'>
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
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th><#if daActualTableNotice?exists>修改<#else>添加</#if>通知通告</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="#" class="b_xcjcjl" onClick="submitCreate();"><b>保存</b></a></li>
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
        <th colspan="2">标题</th>
        <td><input name="daActualTableNotice.title" type="text" class="input" id="title" value="${daActualTableNotice.title}" size="40" maxlength="50">&nbsp;<span style=color:red>*</span></td>
        <th>起草人</th>
        <td><input name="daActualTableNotice.author" type="text" class="input" id="author" value="${daActualTableNotice.author}" size="25" maxlength="25">&nbsp;<span style=color:red>*</span></td>
      	</tr>
      </tr>
      <tr>
       	<th colspan="2">起草时间</th>
        <td><input name="daActualTableNotice.createTimeBegin" type="text" class="input" id="createTimeBegin" value="${daActualTableNotice.createTimeBegin}"  style="ime-mode:disabled"  maxlength="10"size="14" maxlength="10" onfocus="WdatePicker();" class="Wdate" ><span style=color:red>*</span> </td>
        <th>起草部门</th>
        <td><input name="daActualTableNotice.dept" type="text" class="input" id="dept" value="${daActualTableNotice.dept}" size="25" maxlength="25">&nbsp;<span style=color:red>*</span></td>
      	</tr>
      <tr>
      <th colspan="2">内容</th>
      	<td colspan="3"><textarea name="daActualTableNotice.content" id="content" style="display:none" >${daActualTableNotice.content}</textarea>
             <iframe ID="editor" src="../editor/ewebeditor.htm?id=content" frameborder=1 scrolling=no width="98%" height="405"></iframe></td>
      	</td>
      </tr>
      <input type="hidden" name="daActualTableNotice.id" value="${daActualTableNotice.id}">
      <input type="hidden" name="daActualTableNotice.type" value="${daActualTableNotice.type}">
      <input type="hidden" name="daActualTableNotice.auditing" value="${daActualTableNotice.auditing?string}">
      <input type="hidden" name="daActualTableNotice.createTime" value="${daActualTableNotice.createTime?date}">
</table>
</form>

</#escape>
<@fkMacros.pageFooter />