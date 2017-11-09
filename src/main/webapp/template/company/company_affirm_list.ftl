<@fkMacros.pageHeader />
<#escape x as (x)!>
<script>
	function modifyPass(userId){
			window.location="updateCompanyPasswordInit.xhtml?fkUser.id="+userId;
	}
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业已审核列表</th>
  </tr>
</table>
  <form action="loadCompaniesAffirm.xhtml" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th width="5%" nowrap="nowrap">单位名称</th>
	    <td width="20%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="20" maxlength="50"></td>
	    
	    <th width="20%" nowrap="nowrap">行　　业</th>
	    <td id="td_trade"  >
	    <select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);">
	    <option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" 
	    <#if userDetail.userIndustry!='anwei'>selected</#if>><#if userDetail.secondArea!=0 && t.name='贸易'>商务<#else>${t.name} </#if></option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);">
	    <option value="">--请选择--</option></select>&nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange="">
	    <option value="">--请选择--</option></select></td>
	    
	  </tr>
	  <tr>
	    <th>地　　址</th>
	    <td><input type="text" name="company.regAddress" id="address" value="${company.regAddress}"  size="20" maxlength="50"></td>
	    <th>区　　域</th>
	    <td><div id="div-area"></div></td>
	 </tr>
	    <input type="hidden" id="orderProperty" name="company.orderProperty" value="createTime"/>
		<input type="hidden" id="orderType" name="company.orderType"/>
	<tr>
	    <th>企业规模</th>
	    <td>
	        <select name="company.productionScale" id ="productionScale">
	         	<option value="0" selected >--请选择--</option>
	         	  
				 <#list companyScaleEnums?if_exists as companyScale>
					   
					         <option value="${companyScale.enumCode}" <#if company?exists&&company.productionScale?exists&&company.productionScale==companyScale.enumCode>selected</#if>>${companyScale.enumName}</option>
					    
			     </#list>
		      
	        </select>
	      
	    </td>
	     <th nowrap="nowrap">
		      <input name="company.type"   value="1"  type="radio"  checked="checked" />  统一社会信用代码（工商注册号） 
		      <input type="radio" name="company.type"   value="2"   <#if company?exists && company.type?? && company.type==2 >   checked="checked"  </#if> />  组织机构代码
		 </th> 
		 <td >    
		      <input type="text" id="regNum" name="company.regNum"  value=${company.regNum} >
	     </td>
	    
	 </tr>
	 <tr>
	      
	      <th>高风险作业</th>
	    <td  nowrap="nowrap"  colspan=3> 
	      <span style="float:left">
	      <select name="company.isHighRiskWork" id ="isHighRiskWork" onChange="javascript:setHighRiskWorkBlock(this.value);">
	         	<option value="" selected >--请选择--</option>
	         	<option value="1"  <#if company?exists&&company.isHighRiskWork?exists&&company.isHighRiskWork=='1'>selected</#if> >有</option>
	         	<option value="0"  <#if company?exists&&company.isHighRiskWork?exists&&company.isHighRiskWork=='0'>selected</#if> >无</option>
	      </select>
	       </span>
          <span style="float:left" id="highRiskWork_span" <#if company?exists&&company.isHighRiskWork?exists&&company.isHighRiskWork=='1'> style="display:block"<#else>style="display:none"</#if>>
	      <#if (highRiskWorkEnums?if_exists?size > 0)>
				      <#list highRiskWorkEnums?if_exists as highRiskWork>  
					     <input type="checkbox" name="highRiskWorkCode"  <#if (company?exists&&company.highRiskWork?exists&&company.highRiskWork?index_of(highRiskWork.enumCode) > -1)>checked </#if>  value="${highRiskWork.enumCode}">${highRiskWork.enumName} 	
					  </#list>
		 </#if>
		 <input type="hidden" name="company.highRiskWork" id="company.highRiskWork">
		 </span>
	    </td>
	    </tr>
	  <tr>
	    <th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="submitForm('companyForm');" /></div></th>
	  </tr>
	</table>
	</form>
<div class="menu">
  	<ul>
	<li id="img_save"><a href="createCompanyInit.xhtml" class="b1"><b>添加</b></a></li>
	<li id="img_amend"><a href="#" class="b2" onClick="loadNote('loadCompany.xhtml?company.id');"><b>修改</b></a></li>
	<#if isAnweiUser>
	   <li id="img_del"><a href="#" class="b3" onClick="loadNote('delCompanyHy.xhtml?company.id');"><b>删除</b></a></li>
	</#if>
	<!--<li id="img_amend"><a href="#" class="b2" onClick="loadNote('portrayalCompanyInit.xhtml?company.id');"><b>注销</b></a></li>-->
	<li id="img_004"><a href="#" class="b_004" onClick="loadNoteForUser('loadCompanyForUser.xhtml?company.id');"><b>添加企业用户名</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:get('companyForm').submit();" class="b6"><b>查询</b></a></li>
	</ul>
</div>
<form action="" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th id="th_id" width="4%"  nowrap>序号</th>
    <th id="th_companyName" width="25%" style="cursor:pointer;" onClick="orderProperty('companyName');" nowrap><b>单位名称 &nbsp;<img src='${contextPath}/resources/default/img/arrow_for_two.gif' border='0'  width='6' height='18'/></b></th>
    <th id="th_regAddress" width="20%" style="cursor:pointer;" onClick="orderProperty('regAddress');" nowrap><b>地　　址 &nbsp;<img src='${contextPath}/resources/default/img/arrow_for_two.gif' border='0'  width='6' height='18'/></b></th>
    <th id="th_fddelegate" width="8%"   nowrap>法人代表</th>
    <th id="th_phoneCode" width="8%"  nowrap>联系电话</th>
    <th id="th_createTime" width="8%" style="cursor:pointer;" onClick="orderProperty('createTime');" nowrap><b>创建时间 &nbsp;<img src='${contextPath}/resources/default/img/arrow_for_two.gif' border='0'  width='6' height='18'/></b></th>
    <!--<th width="12%" nowrap>是否已添加用户名</th>-->
    <th width="12%" nowrap>企业用户名</th>
   <th width="12%" nowrap>修改密码</th>
    <th width="12%" nowrap>重置密码</th>
  </tr>
  <#if companies?exists>
  	<#list companies?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="loadCompany.xhtml?company.id=${c.id}">${c.companyName}</a></td>
	    <td><div align="left">${c.regAddress}&nbsp;</div></td>
	    <td>${c.fddelegate}&nbsp;</td>
	    <td>${c.phoneCode}&nbsp;</td>
	    <td>${c.createTime?date}&nbsp;</td>
	    <!--
	    <td><#if c.daCompanyPass?if_exists.comUserId?exists><#if c.daCompanyPass?if_exists.comUserId?if_exists!=0>是<#else>否</#if><#else>否</#if></td>
	  	-->
	  	<td><#if c.userName?if_exists!=''>${c.userName}<#else>&nbsp;</#if></td>
	  	<input type="hidden" id="is_user_${c.id}" name="fkUser.id" value="${c.daCompanyPass.comUserId}"/>
	  <td>
	  	<#if c.daCompanyPass?if_exists.comUserId?exists>
	  		<#if c.daCompanyPass?if_exists.comUserId?if_exists!=0>
	  			<a href="javascript:modifyPass(${c.daCompanyPass.comUserId});">修改密码</a><#else>&nbsp;</#if>
	  	<#else>&nbsp;</#if>
	  	</td>
	  	<td>
	  	<a  href="javascript:if(confirm('确定要重置成Abc123456吗?')){reset(${c.daCompanyPass.comUserId},'${c.userName}');}" ><font color=red>重置</font></a>
	  	</td>
	  </tr>
	 </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
<script type="text/javascript">
	<#if userDetail.userIndustry!='anwei'>
		changeTrade(get("tradeTypes1").value,1);
	</#if>
	function loadNoteForUser(url) {
		if(chooseCheckBox(1)){
			if(get("is_user_"+getCheckBoxValue()).value!=null&&get("is_user_"+getCheckBoxValue()).value!=0){
				alert("该企业已添加用户名，无需再添加！");
			}else{
				window.location = url+'='+getCheckBoxValue();				
			}
		}
	}  
	function orderProperty(orderProperty) {
		var orderType = true;
		<#if company?if_exists.orderType?exists>
			orderType = "${company.orderType?string}"=="true"?false:true;
		</#if>
		get("orderType").value = orderType;
		get("orderProperty").value=orderProperty;		
		submitSerach();
	}
function submitForm(formName){
   
   
      //高风险作业查询条件
 	  var isHighRiskWork;  
 	  var temp1 = document.getElementById("isHighRiskWork");  
 	  for(var i=0;i<temp1.length;i++)  {    
	 	   if(temp1[i].selected)            
	 	    isHighRiskWork = temp1[i].value; 
 	  }
	  if(isHighRiskWork=='1'){
	       var flag="0";
	       var highRiskWork="";
		   var highRiskWorkCodes=document.getElementsByName("highRiskWorkCode");
		   for(var i=0;i<highRiskWorkCodes.length;i++){
		       if(highRiskWorkCodes[i].checked){
		          flag="1";
		          highRiskWork+=highRiskWorkCodes[i].value+";";
		       }
		   }
	      document.getElementById("company.highRiskWork").value=highRiskWork;
	 }else{
	  document.getElementById("company.highRiskWork").value="";   
	 }
	 	 
	 	 
	var formObj=get(formName);
	formObj.submit();
}
	function submitSerach(){
			get('companyForm').submit();
	}
	<#if company?exists>
		<#if company.orderProperty?exists>
			get("th_${company.orderProperty}").innerHTML = "<div align='left'><img src='${contextPath}/resources/default/img/"+(("${company.orderType?string}"=="true")?2:1)+".gif' border='0'/></div>"+get("th_${company.orderProperty}").innerHTML;
		</#if>
	</#if>
function changeTrade(tradeId, type) {
	var length = 1;
	if(type == 1) {
		cleanSelect(get("tradeTypes2"));
		cleanSelect(get("tradeTypes3"));
	} else if (type == 2){
		cleanSelect(get("tradeTypes3"));
	}
	<#list tradeTypes?if_exists as t>
		<#list t.daIndustryParameters?if_exists as st>
			if (type == 1 && tradeId == "${t.id}" && "${st.type}" == 1) {
				var opt = new Option("${st.name}", "${st.id}");
				get("tradeTypes2").options[length] = opt;
				opt = null;
				length ++;
			}
			<#list st.daIndustryParameters?if_exists as sst>
				if (type == 2 && tradeId == "${st.id}" && "${sst.type}" == 1) {
					var opt = new Option("${sst.name}", "${sst.id}");
					get("tradeTypes3").options[length] = opt;
					opt = null;
					length ++;
				}
			</#list>
		</#list>
	</#list>
}
<#if company?exists>
	<#if company.tradeTypes?exists>
		var trades = "${company.tradeTypes}";
		getName("company.tradeTypes")[getName("company.tradeTypes").length-1].value = trades;
		for (var i=0;i<trades.split(",").length;i++) {
			if (trim(trades.split(",")[i]) != "") {
				get("tradeTypes"+(i+1)).value = trim(trades.split(",")[i]);
				changeTrade(trim(trades.split(",")[i]), (i+1));
			}
		}
	</#if>
</#if>

function reset(id,name){
	var url = "resetCompanyPassword.xhtml?fkUser.id="+id+"&fkUser.userName="+encodeURI(name);
			jQuery.ajax({
			   type: "POST",
			   url: url,
			   dataType:"text",
			   data: "",
			   success: function(msg){
					 if(msg=='success'){
			       		 alert("重置成功");
			         }else{
			          	 alert("重置失败");
			         }
			   }
			  
	});	
	
}

function setHighRiskWorkBlock(v){
	if(v==0||v==''){
	   get("highRiskWork_span").style.display = "none";
	   var highRiskWorkCodes=document.getElementsByName("highRiskWorkCode");
	   for(var i=0;i<highRiskWorkCodes.length;i++){
	       highRiskWorkCodes[i].checked="";
	   }
	}else{
	   get("highRiskWork_span").style.display = "";
	}
}

</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.searchselectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />