<@fkMacros.pageHeaderAjax />
<#escape x as (x)!> 
  <#if company?exists>
  	<#assign url='updateCompany.xhtml'>
  <#else>
  	<#assign url='createCompany.xhtml'>
  </#if>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>重新绑定企业信息</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="loadTcompany();"><b>loading</b></a></li>
	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="bindingTcompany();"><b>binding</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'companyForm' />
<form id="companyForm" name="companyForm" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th>企业名称</th>
        <td nowrap>${company.companyName}</td>
      
        <th>新企业名称</th>
        <td> 
          <input name="tcompany.companyName" type="text" class="input" id="tcompany.companyName" value="" size="35" maxlength="40">
        </td>
      </tr>
      <tr>
        <th>统一社会信用代码（工商注册号）</th>
        <td nowrap>${company.regNum}</td>
        <th>新统一社会信用代码（新工商注册号）</th>
        <td> 
          <input name="tcompany.businessRegNum" type="text" class="input" id="tcompany.businessRegNum" value="" size="35" maxlength="40">
        </td>
      </tr>
     <tr>
        <th>组织机构编码</th>
        <td nowrap>${company.setupNumber}</td>
        <th>新组织机构编码</th>
        <td> 
          <input name="tcompany.orgCode" type="text" class="input" id="tcompany.orgCode" value="" size="35" maxlength="40">
        </td>
      </tr>
      <tr>
        <th>UUID</th>
        <td nowrap>${company.uuid}</td>
        <th>新UUID</th>
        <td> 
          <input name="tcompany.uuid" type="text" class="input" id="tcompany.uuid" value="" size="35" maxlength="40">
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
  <tr>
    <td>
   </br></br>
   </td>
  </tr>
  <tr>
    <td>
    <div><font color="red" size="4">操作说明：1、在右侧输入框中填入要绑定的企业信息，点击“loading”按钮加载出要绑定的企业信息。确定无误后，点击“binding”按钮，进行邦定操作。</font></br>
    <font color="red" size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、绑定成功后，跳转到企业列表页面，默认查询出绑定后的企业信息，可以通过列表显示的企业信息，查看企业是否绑定成功。</font>
    </div>
   </td>
  </tr>
  <tr>
    <td>
   </br></br>
   </td>
  </tr>
  <tr>
    <td>
    <div><font color="red" size="4">注意事项：1、点击“loading”按钮,弹出“根据条件没有查询到符合的企业信息，请修改条件重新查询！”，说明输入的查询信息有误，必须重新输入正确的查询条件。</font></br>
    <font color="red" size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、点击“loading”按钮,弹出“根据条件查询到多条企业信息，请完善查询条件以确保只查询到唯一的一条企业信息！”，说明根据输入的条件查询到中心库存在多条符合要求的记录，此时应该添加查询条件，继续过滤企业，直到只查询出一条符合要求的企业。</font></br>
    <font color="red" size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、点击“loading”按钮,弹出“根据条件查询要绑定的中心库企业已经存在绑定的隐患企业，请重新核对要绑定的企业信息！”，说明根据输入条件查询到的中心库企业在隐患系统中已存在与之绑定的有效隐患企业，此时就需要重新考虑这家要绑定的企业信息。</font>
    </div>
   </td>
  </tr>
</table>
<input type="hidden" name="company.uuid" id="company.uuid" value="${company.uuid}"/>
<input type="hidden" name="company.id" id="company.id" value="${company.id}"/>
<input type="hidden" name="tcompany.id" id="tcompany.id"/>
</form>
<script type="text/javascript">
function loadTcompany() {
         //加载企业信息之前先报tCompanyId清空
         document.getElementById("tcompany.id").value='';
		 //获得企业名称
 	     var companyName=document.getElementById("tcompany.companyName").value;
 	     //获得工商注册号
 	     var businessRegNum=document.getElementById("tcompany.businessRegNum").value;
 	     //获得组织机构编码
 	     var orgCode=document.getElementById("tcompany.orgCode").value;
 	     //获得uuid
 	     var uuid=document.getElementById("tcompany.uuid").value;
 	     if(companyName==''&&businessRegNum==''&&orgCode==''&&uuid==''){
 	       alert("请输入新企业的信息！");
 	       return false;
 	     }
 	     new Ajax.Request("${contextPath}/ajax/loadTCompany.xhtml?tcompany.companyName="+companyName+"&tcompany.businessRegNum="+businessRegNum+"&tcompany.orgCode="+orgCode+"&tcompany.uuid="+uuid ,{method: "post",onSuccess:function(transport){
					var response =transport.responseText.evalJSON();
					if(response.msg=='0'){
					   alert("根据条件没有查询到符合的企业信息，请修改条件重新查询！");
					   return false;
					}else if(response.msg=='2'){
					   alert("根据条件查询到多条企业信息，请完善查询条件以确保只查询到唯一的一条企业信息！");
					   return false;
					}else if(response.msg=='1'){
					     var t_companyName=response.companyVo.companyName;
					     var t_businessRegNum=response.companyVo.businessRegNum;
					     var t_orgCode=response.companyVo.orgCode;
					     var t_uuid=response.companyVo.uuid;
					     var t_id=response.companyVo.id;
					     
					     //判断要绑定的企业信息是否已经在有效的企业中存在，存在的话给出提醒，不存在的话，可以重新绑定
						 new Ajax.Request("${contextPath}/ajax/checkTCompany.xhtml?tcompany.uuid="+t_uuid,{method: "post",onSuccess:function(transport){
									var response1 =transport.responseText.evalJSON();
									if(response1.msg=='0'){
									   document.getElementById("tcompany.companyName").value=t_companyName;
									   document.getElementById("tcompany.businessRegNum").value=t_businessRegNum;
									   document.getElementById("tcompany.orgCode").value=t_orgCode;
									   document.getElementById("tcompany.uuid").value=t_uuid;
									   document.getElementById("tcompany.id").value=t_id;
									}else{
									   alert("根据条件查询要绑定的中心库企业已经存在绑定的隐患企业，请重新核对要绑定的企业信息！");
									   return false;
									}
									
								}
						});
						
					}else{
					
					}
					
				}
		});
 	     
};
function bindingTcompany() {
		 //根据tcompany.id是否存在判断是否可以提交
 	     var tId=document.getElementById("tcompany.id").value;
 	     if(tId!=null&&tId!=""&&tId!='0'){
 	       var obj=get("companyForm");
  		   obj.action="updateCompanyForBinding.xhtml";
 		   obj.submit();
 	     }else{
 	       alert("请查询正确的企业绑定！");
 	       return false;
 	     }
 	     
}

</script>
</#escape>
<@fkMacros.pageFooter />