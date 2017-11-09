<@fkMacros.pageHeader />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<#escape x as (x)!> 
<script language="javascript">
$(document).ready(function(){
	 $("#to_save").click(function(){
	      if($("[name='j_proxy_id']:checked").size()<1 ) {
	      	  alert("请选择要切换的用户！");
	      	  return;
	      }
		  $.ajax({
	    	  url:  '${contextPath}/proxy_security_check.xhtml',
	    	  data:$("#form").serialize(),
	    	  type:"POST",
	    	  cache:false,
	    	  async:false,
	    	  dateType:"json",
	    	  error:function(XMLHttpRequest,textStatus) {
	              alert('服务器连接失败，请稍候重试！');
	    	  },
	  		  success: function(response){
	  		  	  
	  		  	   	 alert("切换用户成功！");
					 top.window.location.href="${contextPath}${proxyWorkSpaceUrl}";
	  		  	 
			  }
		 });
	});
});
</script>
<body>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>用户切换</th>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
	  	<#if relationUsers?? && relationUsers?size gt 0>
		  	<form id="form" name="form" method="post" action="">
			  	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="table1" class="table_input margin2">
				   <#list relationUsers as m>  
					   <tr>
							<td>  
				  				<input  name="j_proxy_id" type="radio" value="${m.relationUserId}" />${m.relationUser.userName}(${m.relationUser.fkUserInfo.factName})
				  			</td>
				  	   </tr>
			  	   </#list>
			  	   
			  	   <#if user??>
				  	   <tr>
							<td>  
				  				<span style="color:red" >初次登录账号：</span><input name="j_proxy_id" type="radio" value="${user.id}" />${user.userName}(${user.factName})
				  			</td>
				  	   </tr>
			  	   </#if>
			  	   
			  	   <tr class="btn_bg">
						<td class="btn"style="text-align:center">
							<span><input id="to_save" type="button" value="切 换用户" /></span>
						</td>
					</tr>
							
			  	</table>
			 </form>	
		<#else>
			 <div>没有可切换的用户！</div>
	  	</#if>
	  	 
	</td>
  </tr>
</table>
</body>
</#escape> 
</html>
<@fkMacros.pageFooter />
