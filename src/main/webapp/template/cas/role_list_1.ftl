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
	<script language="javascript">
	$(document).ready(function(){
	        $("#tableList tr").mouseover(function(){$(this).addClass("selectTr");}).mouseout(function(){$(this).removeClass("selectTr");});
	        $('#tableList').fixedHeaderTable({themeClass: 'table_list', width: '100%',height: '400px',footer: true, cloneHeadToFoot: false, autoShow: true, fixedColumn: true });
	     
	     
	});
	
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
<#escape x as (x)!> 

	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="content">
	  <tr>
	    <td valign="top">
			<div class="con_box">
				<div class="operate">
					<div class="operate_left">
						<ul>
							<li><a  onclick="set();" href="javascript:;"><span>设置</span></a></li>
						</ul>
					</div>
					<div class="operate_right"><a href="javascript:;" onclick="window.location.reload();"><img src="${contextPath}/resources/default/themes/default/framework/images/reload.gif"  alt="刷新"/></a></div>
				</div>
				
				<table id="tableList" border="0" cellspacing="0" cellpadding="0">
				 <thead>
				  <tr>
				   	<th width="5%">序号</th>
					<th>角色名称</th>
				    <th>角色描述</th>
				    <th>更新日期</th>
				  </tr>
				 </thead>
				 <tbody>
				  <#if fkRoles??>
				  	<#list fkRoles as m>
					   	<td><input type="checkbox" value="${m.id}"  name="settings" 
					   		<#if roleIds??>
					   			<#list roleIds as d>
					   				<#if d==m.id>
					   					checked
					   					<#break />
					   				</#if>
								</#list>
					   		</#if>
					   	/>&nbsp;&nbsp;${m_index+1}</td>
						<td class="align-left" width="26%" >${m.roleName}</td>
						<td class="align-left" width="17%">${m.roleDepic}</td>
						<td class="align-center" width="14%">${m.modifyTime}</td>
					  </tr>
			         </#list>
			    </#if>
			    </tbody>
				</table>
			</div>
		</td>
	  </tr>
	</table>
</body>
</#escape> 
</body>
</html>

