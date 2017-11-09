<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />   
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<title></title>
	<link href="${contextPath}/resources/default/css/css.css" rel="stylesheet" type="text/css" />

	<script language="JavaScript" type="text/javascript" src="${contextPath}/resources/default/js/jquery-1.4.2.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/resources/default/js/jquery.validate.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/resources/default/js/public.js"></script>
	
	<script language="JavaScript" type="text/javascript" src="${contextPath}/resources/default/js/jquery.checkboxes.js"></script>
		
	<script type="text/javascript" src="${contextPath}/resources/default/plugins/jquery.fixedheadertable/jquery.fixedheadertable.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/default/plugins/jquery.area/jquery.area.js"></script>
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/default/plugins/jquery.fixedheadertable/css/defaultTheme.css"/>
</head>
<body>
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
	<#escape x as (x)!> 
	<script language="javascript">
	$(document).ready(function(){
	        $("#tableList tr").mouseover(function(){$(this).addClass("selectTr");}).mouseout(function(){$(this).removeClass("selectTr");});
	        $('#tableList').fixedHeaderTable({themeClass: 'table_list', width: '100%',height: '400px',footer: true, cloneHeadToFoot: false, autoShow: true, fixedColumn: true });
	        var xPath='first-area[code="${userDetail.firstArea}"]';
	        <@authz.authorize ifAnyGranted="ROLE_ADMINISTRATOR">
	        xPath="first-area";
	        </@authz.authorize>
	        $.areaInit('${contextPath}/resources/default/release/area.xml');
	        $('#root').areaSelect(xPath,'${fkUserInfo.firstArea}');
	        $('#parent').areaCascade('root','${fkUserInfo.secondArea}');
	        $('#child').areaCascade('parent','${fkUserInfo.thirdArea}');
	        $('#root').change();
	        $('#parent').change();
	        
	        $(".on_off").click(function(){
	        	$("#tagContent").toggle();
	        	if($("#tagContent").is(":hidden")){
	            	$(this).html('<img src="${contextPath}/resources/default/themes/default/framework/images/block.gif" />');
	            }else{
	            	$(this).html('<img src="${contextPath}/resources/default/themes/default/framework/images/unblock.gif" />');
	            }
	        });
	});
	function del(id){
		if(confirm(' 确认要删除吗？')){
			$.ajax({
		    	  url:'${contextPath}/user/deleteUser.xhtml',
		    	  data:"fkUser.id="+id,
		    	  type:"GET",
		    	  cache:false,
		    	  async:false,
		    	  dateType:"json",
		    	  error:function(XMLHttpRequest,textStatus) {
		              alert('服务器连接失败，请稍候重试！');
		    	  },
		  		  success: function(response){
		  		  	  if(!response.success){
		  		  	  		alert(response.msg);
		  		  	  		return;
		  		  	  }
		  		      window.location.reload();
				  }
			});	
		}
	}
	
	var synchro=function() {
		var p="";
		if($.trim($("#userName").val())=="") {
			alert("请输入账号");
			return;
		}	
		p="userName="+$.trim($("#userName").val());
		if($("#forceUpdate").attr("checked")) {
			p+="&forceUpdate=true";
		}
		p+="&localUserName="+$.trim($("#localUserName").val());;
		$.ajax({
		    	  url:'${contextPath}/cas/user/synchro.xhtml',
		    	  data:p,
		    	  type:"post",
		    	  cache:false,
		    	  async:false,
		    	  dateType:"json",
		    	  error:function(XMLHttpRequest,textStatus) {
		              alert('服务器连接失败，请稍候重试！');
		    	  },
		  		  success: function(response){
		  		  	  if(!response.success){
		  		  	  	alert(response.msg);
		  		  	  	return;
		  		  	  }
		  		      window.location.reload();
				  }
		});	
		
	}
	</script>
	<body>
		<table width="99%" border="0" cellspacing="0" cellpadding="0" id="content">
		  <tr>
		    <td valign="top">
				<div class="con_box">
					<div id="tags_con" class="margin2">
						<div id="tagContent">
						  <form name="searchForm" action="#" method="post">
							<div class="selectTag" id="tagContent0">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
								  <tr>
									<td width="15%" class="align-right">所在地区：</td>
									<td nowrap="nowrap" width="37%"><select id="root" name="fkUserInfo.firstArea"></select><select id="parent" name="fkUserInfo.secondArea"></select><select id="child" name="fkUserInfo.thirdArea"></select></td>
									<td width="15%" class="align-right">单位名称：</td>
									<td width="23%"><input name="fkUserInfo.userCompany" value="${fkUserInfo.userCompany}" type="text" class="text_large" /></td>
									<td width="10%"  class="align-center btn" rowspan="2"><input  type="submit" value="搜  索"></td>
								  </tr>
								  <tr>
									<td class="align-right">用户名：</td>
									<td><input name="fkUser.userName" value="${fkUser.userName}" type="text" class="text_large" /></td>
									<td class="align-right">真实姓名：</td>
									<td><input name="fkUserInfo.factName" value="${fkUserInfo.factName}" type="text" class="text_large" /></td>
								  </tr>
								</table>
							</div>
						  </form>
						</div>
						<a href="javascript:;"><div class="on_off"><img src="${contextPath}/resources/default/themes/default/framework/images/unblock.gif" /></div></a>
					</div>
					<div class="operate">
						<div class="operate_left" style="width:55%">
							<ul>
								<li>中心账号<input type="text"  value="" id="userName" name="userName" style="width:50px"/><li>
								<li>系统账号<input type="text"  value="" id="localUserName" name="localUserName" style="width:50px" /></li>
								<li><input type="checkbox"  value="true" id="forceUpdate" name="forceUpdate" alter="如果存在账号会强制同步！" />替换<li>
								<li><input type="checkbox"  value="true" id="rePassword" name="rePassword" alter="如果存在账号替换密码！" />密码<li>
								<li><a onclick="synchro()" href="javascript:;"><span>同步</span></a></li>
							</ul>
						</div>
						<div class="operate_right" style="width:40%"><a href="javascript:;" onclick="window.location.reload();"><img src="${contextPath}/resources/default/themes/default/framework/images/reload.gif"  alt="刷新"/></a></div>
					</div>
					
					<table id="tableList" border="0" cellspacing="0" cellpadding="0">
					 <thead>
					  <tr>
					    <th width="5%">序号</th>
					    <th width="10%">用户名</th>
					    <th width="10%">真实姓名</th>
					    <th width="28%">所属单位</th>
					    <th width="10%">联系手机</th>
					    <th width="15%">注册日期</th>
					    <th width="22%">操作</th>
					  </tr>
					 </thead>
					  <tbody>
						  <#list fkUsers?if_exists as m>    
						    <tr>
						      <td><input type="checkbox" value="${m.id}"  name="bindings" />&nbsp;&nbsp;${pagination.itemCount+m_index+1}</td>
						      <td>${m.userName}</td>
						      <td>${m.fkUserInfo.factName}</td>
						      <td>${m.fkUserInfo.userCompany}</td>
						      <td>${m.fkUserInfo.userMobile}</td>
						      <td>${m.createTime}</td>
						      <td>
						      	【<a href="${contextPath}/user/editUserInit.xhtml?fkUser.id=${m.id}">修改信息</a>】
						     	 <!--【<a href="editPasswordInit.xhtml?fkUser.id=${m.id}">修改密码</a>】 -->
						      	【<a onclick="del(${m.id})" href="javascript:;">删除</a>】
						      </td>
						    </tr>
						  </#list>
	
				      </tbody>
				      <tfoot>
				         <#if fkUsers?size==0>
				    	 <tr>
				    	    <td colspan="7" class="align-center">此列表没有任何数据!</td>
				    	 </tr>
				         </#if>
				      	<tr>
					    	 <td colspan="7" class="page"><@p.navigation pagination=pagination/></td>
					    </tr>
				      </tfoot>
					</table>
				</div>
			</td>
		  </tr>
		</table>
	</body>
	</#escape> 
</body>
</html>