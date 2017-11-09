<@fkMacros.pageHeader />
<#escape x as (x)!>
<script>
function createClient() {
	if (formValidator.validate()) {
		if (confirm("请确认上传的apk对应的用户类型是否正确？")) {
			var type = document.getElementById("type").value;
			var maxVersionNum = type == "ES" ? ${maxVersionNumES} : ${maxVersionNumGOV};
			var versionNum = document.getElementById("versionNum").value;
			if (versionNum <= maxVersionNum) {
				alert("版本序号不能比历史版本小");
				return;
			}
			document.getElementById("form1").submit();
		}
	}
}
</script>
<table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
	<tr>
    	<td align="center"><strong>修改密码</strong></td>
	</tr>
</table>
<@fkMacros.formValidator 'form1' />
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
	<form id="form1" name="form1" method="post" action="${base}/clientlog/create.xhtml" enctype="multipart/form-data">
	    <tr>
	    	<th width="15%">安装包英文名（原名）</th>
	      	<td width="85%"><input type="text" id="packageEnName" name="entity.packageEnName" class="input" rule="require" empty="请输入安装包英文名（原名）" pass="&nbsp;"/></td>
	    </tr>
	    <tr>
	      	<th>安装包中文名</th>
	      	<td><input id="packageZhName" name="entity.packageZhName" type="text" class="input" rule="require" empty="请输入安装包中文名" pass="&nbsp;"/></td>
	    </tr>
	    <tr>
	      	<th>版本号</th>
	      	<td><input type="text" class="input" id="versionCode" name="entity.versionCode" rule="require" empty="请输入版本号" pass="&nbsp;"/></td>
	    </tr>
	    <tr>
	    	<th>版本序号</th>
	      	<td><input type="text" class="input" id="versionNum" name="entity.versionNum" rule="require" empty="请输入版本序号" pass="&nbsp;"/></td>
	    </tr>
	    <tr>
	      	<th>支持平台</th>
	      	<td>
	      		<select id="os" name="entity.os" rule="require" empty="请选择支持平台" pass="&nbsp;">
	      			<option value="">请选择</option>
	      			<#list osTypeList as item>
						<option value="${item.code}">${item.text}</option>
					</#list>
	      		</select>
	      	</td>
	    </tr>
	    <tr>
	      	<th>用户类型</th>
	      	<td>
	      		<select id="type" name="entity.type" rule="require" empty="请选择用户类型" pass="&nbsp;">
	      			<option value="">请选择</option>
					<#list osUserTypeList as item>
						<option value="${item.code}">${item.text}</option>
					</#list>
	      		</select>
	      	</td>
	    </tr>
	    <tr>
	      	<th>发布日志</th>
	      	<td><textarea id="changeLog" name="entity.changeLog" type="text" class="input"></textarea></td>
	    </tr>
	    <tr>
	    	<th>客户端</th>
	      	<td>
	      		<input type="file" class="input" id="file" name="file" rule="require" empty="请上传客户端" pass="&nbsp;"/>
	      		注：上传的客户端，如果是android客户端上传后，企业版apk名字yhpc_es.apk 政府版yhpc_gov.apk
	      	</td>
	    </tr>
	    <tr>
	      	<th colspan="2">
	      		<div align="center"><input name="to_save" id="to_save" type="button" class="btn_save" value="创建" onclick="javascript:createClient();" /></div>
	      	</th>
	    </tr>
	</form>
</table>
</#escape> 
<@fkMacros.pageFooter />