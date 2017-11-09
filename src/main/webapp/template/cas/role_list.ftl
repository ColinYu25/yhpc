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
</head>

<body>
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
	<script language="javascript">
	
	var set=function() {
		var csettings=$(":checked[name='settings']");
		if(csettings.size()==0) {
			alert("至少选择一个角色！");
			return;
		}
		
		var datas="";
		var l=csettings.size();
		$.each(csettings,function(i,v){
			datas+="roleIds="+v.value;
			if(i<l-1)
				datas+="&";
		}); 
		$.ajax({
	    	  url:'${contextPath}/cas/role/defaultRoles.xhtml',
	    	  data:datas,
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
		  		  alert("设置成功！");
	  		      window.location.reload();
			  }
		});	
	}
	
	
	</script>
	 <table width="100%" class="windows_list">
        <tr>
          <td width="5%" class="p_btn" ><input type="submit"  id="button" onclick="set();" value="设置" class="ty_btn" /></td>
          <td width="5%" class="p_btn" ><input type="submit"  id="button" onclick="parent.roleManager();" value="角色管理" class="ty_btn" /></td>
          <td width="90%" align="right" class="p_right_5" ><span  style="cursor:hander"><img onclick="window.location.reload();" style="cursor:pointer" src="${contextPath}/resources/cas/img/refresh_icon.png" width="16" height="16" /></span></td>
        </tr>
      </table>
      <table width="100%"  class="windows_list">
        <tr class="title">
          <th width="4%">序号</th>
          <th width="29%">角色名称</th>
          <th width="46%">角色描述</td>    </th>
          <th width="18%" colspan="5">更新日期</th>
        </tr>
         <#if fkRoles??>
		  	<#list fkRoles as m>
		  	 <tr>
			   	<td align="center"><input type="checkbox" value="${m.id}"  name="settings" 
			   		<#if roleIds??>
			   			<#list roleIds as d>
			   				<#if d==m.id>
			   					checked
			   					<#break />
			   				</#if>
						</#list>
			   		</#if>
			   	/>&nbsp;&nbsp;${m_index+1}</td>
				<td align="center" >${m.roleName}</td>
				<td align="center">${m.roleDepic}</td>
				<td align="center">${m.modifyTime}</td>
			  </tr>
	         </#list>
		   </#if>
      
      </table>
   
	
</body>
</html>