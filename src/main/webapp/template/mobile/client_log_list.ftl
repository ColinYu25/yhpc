<@fkMacros.pageHeader />
<script src="${resourcePath}/js/lhgdialog.4.2.0/lhgdialog.min.js?self=false&skin=chrome" type="text/javascript"></script>
<#escape x as (x)!>
<script language="javascript">
var lhgDg=null;
function showBarWin() {
	alert("jdk1.6生成二维码有问题，固定使用草料二维码， apk名字企业是yhpc_es.apk, 政府是yhpc_gov.apk");
	return;
	/*lhgDg = jQuery.dialog({
  		autoPos:true, //自动居中
        iconTitle:false,
	    id:'showBarWin',
	    width:200,
	    height:100,
	    lock : true,
	    max :false,
	    cache : false,
	    ok : function(){var type = document.getElementById("type").value; generateBar(type); return false;},
	    cancel : true,
	    cancelVal : "关闭",
	    okVal : "生成",
	    title:"选择APP用户类型",
	    cover:true,
	    resize:false,//允许拖动改变窗口大小
	    btnBar:false,
	    content: '用户类型 <select id="type"><#list osUserTypeList as item><option value="${item.code}">${item.text}</option></#list></select>'
	}).focus()*/
}

function generateBar(type) {
	jQuery.ajax({
		url:'${contextPath}/clientlog/generateBarCode.xhtml',
		type:"post",
		data:{"type":type},
	  	error:function(XMLHttpRequest,textStatus) {
        	alert('服务器连接失败，请稍候重试！');
	  	},
	  	success: function(data) {
	  		if (data == "true") {
	  			alert("发布成功");
	  		} else {
	  			alert("发布失败");
	  		}
	  	}
	});
}

function checkbox_onclick(obj) {
	jQuery("input[type='checkbox'][name='ids']").removeAttr("checked");
	obj.checked = true;
}

function submitForm(){
	var formObj = document.getElementById("searchForm");
	formObj.submit();
}

</script>
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_title">
	<tr>
    	<td height="27" class="text_blue_12"><a href="${contextPath}/clientlog/show.xhtml" class="blue_12">发布</a> | <a href="javascript:showBarWin();" class="blue_12">生成二维码</a></td>
  	</tr>
</table>
<form name="searchForm" action="${base}/clientlog/list.xhtml" method="post" id="searchForm">
	<table id="searchTable"  width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
    	<tr>
      		<th>用户类型</th>
      		<td colspan="3">
		      	<select id="type" name="entity.type">
					<option value="">请选择</option>
					<#list osUserTypeList as item>
						<option value="${item.code}">${item.text}</option>
					</#list>
				</select>
      		</td>
    	</tr>
    	<tr>
      		<th colspan="4"><div align="center"><input type="button" value="查　询" class="confirm_but" style="height:25px; width:80px" onClick="javascript:submitForm();"/></div></th>
   		</tr>
  	</table>
</form>
<table width="99%" cellpadding="0" cellspacing="1" class="table_list">
  	<tr>
	    <th width="4%"><input type="checkbox" onclick="javascript:selectAllOrNo(this,this.form.elements['ids']);" /></th>
	    <th>序号</th>
	    <th>原名</th>
	    <th>别名</th>
	    <th>版本号</th>
	    <th>版本序号</th>
	    <th>支持平台</th>
	    <th>用户类型</th>
	    <th>发布时间</th>
  	</tr>
	<#if clientChangeLogList?exists>
		<#list clientChangeLogList?if_exists as m>    
		    <tr>
		      	<td align="center"><input type="checkbox" id="ids${m.id}" value="${m.id}" name="ids" onclick="checkbox_onclick(this);"/></td>
		      	<td>${pagination.itemCount+m_index+1}</td>
		      	<td>${m.packageEnName}</td>
		      	<td>${m.packageZhName}</td>
		      	<td>${m.versionCode}</td>
		      	<td>${m.versionNum}</td>
		      	<td>${m.osTypeText}</td>
		      	<td>${m.typeText}</td>
		      	<td>${m.publishDate?string('yyyy-MM-dd')}</td>
		    </tr>
	  	</#list>
	</#if>
</table>
<table width="99%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center"><@p.navigation pagination=pagination/></td>
	</tr>
</table>
</#escape> 
<@fkMacros.pageFooter />