<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="${contextPath}/resources/cas/css/public.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/cas/css/other.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${contextPath}/resources/cas/js/jquery-1.7.1.min.js"></script>
<script language="javascript" src="${contextPath}/resources/cas/js/right.js"></script>
<script type="text/javascript" src="${contextPath}/resources/cas/js/calendar.js"></script>
<script type="text/javascript" src="${contextPath}/resources/cas/js/png.js"></script>
<script type="text/javascript" src="${contextPath}/resources/default/plugins/jquery.area/jquery.area.js"></script>
</head>


<body>
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
	<#escape x as (x)!> 
	<script language="javascript">
	$(document).ready(function(){
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
<form name="searchForm" action="#" method="post">	
	<table width="100%" class="windows_list">
	  <tr>
	      <td width="6%" align="center">所在区域</td>
	      <td width="20%" >
	        <select id="root" name="fkUserInfo.firstArea"></select><select id="parent" name="fkUserInfo.secondArea"></select><select id="child" name="fkUserInfo.thirdArea"></select>
	      </td>
	      <td width="6%" align="center">单位名称</td>
	      <td width="13%">
	        <input name="fkUserInfo.userCompany" value="${fkUserInfo.userCompany}" type="text" class="text_large" />
	      </td>
	      <td width="6%" align="center">用户名</td>
	      <td width="16%">
	       <input name="fkUser.userName" value="${fkUser.userName}" type="text" class="text_large" />
	      </td>
	      <td width="6%">真实姓名</td>
	      <td width="9%">
	       <input name="fkUserInfo.factName" value="${fkUserInfo.factName}" type="text" class="text_large" />
	      </td>
	      <td width="7%" align="center">
	        <input type="submit"  id="button2" value="搜索" class="ty_btn" />
	      </td>
	      <td width="18%">&nbsp;</td>
	  </tr>
	 </table>
 </form>
 
 <table width="100%" class="windows_list">
  <tr>
      <td width="6%" align="center">中心账号</td>
      <td width="6%"><input type="text"  value="" id="userName" name="userName" style="width:100px"/></td>
      <td width="6%" align="center">系统账号</td>
      <td width="7%" align="center"><input type="text"  value="" id="localUserName" name="localUserName" style="width:100px" /></td>
      <td width="7%" align="center"><input type="checkbox"  value="true" id="forceUpdate" name="forceUpdate" alter="如果存在账号会强制同步！" />强制更新</td>
 	  <td width="6%"><input type="checkbox"  value="true" id="rePassword" name="rePassword" alter="如果存在账号替换密码！" />替换密码</td>
      <td><input type="button"  id="button3" value="同步"  onclick="synchro()" class="ty_btn" /></td>
      <td align="right" class="p_right_5"><span  style="cursor:pointer"><img src="${contextPath}/resources/cas/img/refresh_icon.png"  onclick="window.location.reload();" width="16" height="16" /></span></td>
  </tr>
 </table>
 <table width="100%"  class="windows_list">
    <thead>
        <tr class="title">
          <th width="4%">序号</th>
          <th width="12%">用户名</th>
          <th width="9%">真实姓名</th>
          <th width="29%">用户单位</th>
          <th width="16%">联系手机</th>
          <th width="15%">注册日期</td>
          <th width="12%" colspan="5">操作</th>
        </tr>
    </thead>
    
    <tbody>
       <#list fkUsers?if_exists as m>    
		    <tr>
		      <td align="center"><input type="checkbox" value="${m.id}"  name="bindings" />&nbsp;&nbsp;${pagination.itemCount+m_index+1}</td>
		      <td align="center">${m.userName}</td>
		      <td align="center">${m.fkUserInfo.factName}</td>
		      <td align="center">${m.fkUserInfo.userCompany}</td>
		      <td align="center">${m.fkUserInfo.userMobile}</td>
		      <td align="center">${m.createTime}</td>
		      <td colspan="5" align="center">
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
	    	    <td colspan="11" align="center">此列表没有任何数据!</td>
	    	 </tr>
        </#if>
      	<tr>
	    	 <td colspan="11" class="page" align="center"><@p.navigation pagination=pagination/></td>
	    </tr>
   </tfoot>
</table>
</#escape> 	
</body>
</html>