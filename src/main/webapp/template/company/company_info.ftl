<@fkMacros.pageHeader />
<#escape x as (x)!> 
  <#if company?exists>
  	<#assign url='updateCompany.xhtml'>
  <#else>
  	<#assign url='createCompany.xhtml'>
  </#if>
<script type="text/javascript">
var  flag_setupNumber=0;
var  flag_regNum=0;
if(parent){
	parent.company = "${company.companyName}";
}

function submitUpdate(){
 	if(formValidator.validate()){
  		  var obj=get("companyForm");
  		  obj.action="createCompanyAcount.xhtml";
 		  obj.submit();
 	}
}
function submitCreate(){

	//添加flag字段来标识，是否保存完整，0表示未保存完整，1表示保存完整。默认为“1”
	var flag="1";
	//添加null_field来保存未保存的字段名称
	var null_field="";
	var num=1;
	
	var id=document.getElementById("id").value;
    if (flag_setupNumber==1){  //组织机构代码验证
	     alert("已存在相同的组织机构代码,请更正!");
		 return;
	}
	
	if (flag_regNum==1){  //工商注册号验证
	     alert("已存在相同的统一社会信用代码（工商注册号）,请更正!");
		 return;
	}

	<#if (tradeTypesAllow?size>=1)>   
	var str=document.getElementsByName("company.tradeTypes");
	var objarray=str.length;
	var first_name;
	var fstval;
	var a=-1;
	
	for (i=0;i<objarray;i++)
	{
	  if(str[i].checked == true)
	  {
	   a=i;
	   first_name="sel_sec_"+str[i].value;
	    
	     if(str[i].value!=471780){
	        fstval=document.getElementById(first_name).value;
	        if(    fstval==-1){   //除了人防(其下无行业)外,其他未选一级行业的给予提示  必选
	        
	            //新增的话，一定要选则行业
	            //if(id!=null&&id>0){
	                
	            //}else{
		            alert("请选择一级行业!"); 
			   		return;
	           // }
		   		
		   		
	   		}
	     
	     }  
	      
	  }
	}
	
	if (a==-1){
		
		
		 //新增的话，一定要选则行业
	     //if(id!=null&&id>0){
	     //   flag="0";
		 //	null_field+=num+"、行业分类；\n";
		 //num=parseInt(num)+1; 
	     //}else{
		      alert("请选择行业!");
		      return;
	     //}
	            
	            
		
	}
	</#if>

    if (document.getElementById("second-area").value==0){
	 	//alert("请选择二级区域!");
	 	//return;	 
	 	
	 	flag="0";
	    null_field+=num+"、二级区域；\n";
	    num=parseInt(num)+1;
	
		
 	}else if (document.getElementById("third-area").value==0  &&  document.getElementById("second-area").value!=330218000000  &&  document.getElementById("second-area").value!=330219000000 &&  document.getElementById("second-area").value!=330215000000 ){
 		//alert("请选择三级区域!");
	 	//return;
	    flag="0";
	    null_field+=num+"、三级区域；\n";
	    num=parseInt(num)+1;
 	}
 	
 	
 	
 	  //高风险作业判断
 	  var isHighRiskWork;
 	       
 	  var temp1 = document.getElementsByName("company.isHighRiskWork");  
 	  for(var i=0;i<temp1.length;i++)  {    
	 	   if(temp1[i].checked)            
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
	 	  if(flag=='0'){
	 	     alert("请选择具体的高风险行业！");
	 	     return false;
	 	  }
	 }else{
	  document.getElementById("company.highRiskWork").value="";   
	 }
	 	 
	 	 
	 	 
 	//if(formValidator.validate()){
 	
 	      var haveRegNum;
 	       
 	      var temp = document.getElementsByName("company.haveRegNum");  
 	      for(var i=0;i<temp.length;i++)  {    
	 	       if(temp[i].checked)            
	 	       haveRegNum = temp[i].value; 
 	      }
 	     
 	      if(haveRegNum=='1'){
 	         
 	         var regNum = document.getElementById("regNum").value;
 	         if(regNum==''){
 	           var regNumTips = document.getElementById("regNumTips").value;
 	           //alert(regNumTips);
	 	       //return;
	 	       
	 	        flag="0";
	            null_field+=num+"、"+regNumTips+"；\n";
	            num=parseInt(num)+1;
 	         }
 	        
 	      }
 	     
 	      
 		  var haveSetupNumber;
 	       
 	      var temp1 = document.getElementsByName("haveSetupNumber");  
 	      for(var i=0;i<temp1.length;i++)  {    
	 	       if(temp1[i].checked)            
	 	       haveSetupNumber = temp1[i].value; 
 	      }
	 	 if(haveSetupNumber=='1'){
	 	 
	 	 if (document.getElementById("setupNumber").value==""){
		 	  //alert("组织机构代码不为空!");
		 	  //return;
		 	  
		 	   flag="0";
	           null_field+=num+"、组织机构代码；\n";
	           num=parseInt(num)+1;
		 	  }
	 	 }
 	
 	
 	      
 	     //添加经济类型子类判断不能为空
 	     var economicType1=document.getElementById("economicType1").value;
 	     if(economicType1==''){
 	           flag="0";
	           null_field+=num+"、经济类型；\n";
	           num=parseInt(num)+1;
 	     }
 	     
 	     var economicType2=document.getElementById("economicType2").value;
		//如果经济类型子类为空的话，还要判断是否就不存在经济类型子类。存在的话，要求必填。
		if(economicType2==''){
				var eId;
		         <#if (economyKindEnums?if_exists?size > 0)>
					<#list economyKindEnums?if_exists as economyKindEnum>
					              var eCode='${economyKindEnum.enumCode}';
					            
							      if(eCode==economicType1){
							        eId=${economyKindEnum.id};
							      }
				    </#list>
			    </#if>
		        
		       
		        var economicType2Request='0';
		        <#if (childEconomyKindEnums?if_exists?size > 0)>
		           
				    <#list childEconomyKindEnums?if_exists as economicType>
				               var fatherId=${economicType.fatherId};
				               if(eId==fatherId){
				                 economicType2Request='1';
				               }
					</#list>
				</#if>
				
				
				if(economicType2Request=='1'){
				 //alert("请选择经济类型子类！");
				 //return;
				 
				    flag="0";
	                null_field+=num+"、经济类型子类；\n";
	                num=parseInt(num)+1;
				}
		  }
		
		
		 //添加国民经济分类子类判断不能为空
 	     var naEcoType1=document.getElementById("naEcoType1").value;
 	     
 	     if(naEcoType1==''){
 	           flag="0";
	           null_field+=num+"、国民经济分类；\n";
	           num=parseInt(num)+1;
 	     }
 	     
 	     var naEcoType2=document.getElementById("naEcoType2").value;
		//如果国民经济分类子类为空的话，还要判断是否就不存在国民经济分类子类。存在的话，要求必填。
		if(naEcoType2==''){
				 var naEcoTypeId;
		         <#if (nationalEconomicEnums?if_exists?size > 0)>
					<#list nationalEconomicEnums?if_exists as naEcoType>
							      var eCode='${naEcoType.enumCode}';
							      if(eCode==naEcoType1){
							        naEcoTypeId=${naEcoType.id};
							      }
				    </#list>
			    </#if>
		       
		       
		        var naEcoType2Request='0';
		        <#if (childNationalEconomicEnums?if_exists?size > 0)>
				    <#list childNationalEconomicEnums?if_exists as naEco>
				               var fatherId=${naEco.fatherId};
				               if(naEcoTypeId==fatherId){
				                 naEcoType2Request='1';
				               }
					</#list>
				</#if>
				
				
				
				if(naEcoType2Request=='1'){
				// alert("请选择国民经济分类子类！");
				 //return;
				 
				    flag="0";
	                null_field+=num+"、国民经济分类子类；\n";
	                num=parseInt(num)+1;
				}
		  }
		  
 	      
 	      
 	      
 	      
 	      //添加企业规模判断不能为空
 	      var productionScale=document.getElementById("productionScale").value;
 	      if(productionScale==''){
 	           flag="0";
	           null_field+=num+"、企业规模；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加单位名称判断不能为空
 	      var companyName=document.getElementById("companyName").value;
 	      
 	       if(companyName==''){
		 	      //新增的话，一定要填写企业名称
			      if(id!=null&&id>0){  
		 	           flag="0";
			           null_field+=num+"、单位名称；\n";
			           num=parseInt(num)+1;
			      }else{
				        alert("请填写企业名称!"); 
					    return;
			      }
 	       }	
 	     
 	      //添加注册地址判断不能为空
 	      var regAddress=document.getElementById("regAddress").value;
 	      if(regAddress==''){
 	           flag="0";
	           null_field+=num+"、注册地址；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加所在地址判断不能为空
 	      var address=document.getElementById("address").value;
 	      if(address==''){
 	           flag="0";
	           null_field+=num+"、所在地址；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加所属行业主管部门判断不能为空
 	      var govAdminOrg=document.getElementById("govAdminOrg").value;
 	      if(govAdminOrg==''){
 	           flag="0";
	           null_field+=num+"、所属行业主管部门；\n";
	           num=parseInt(num)+1;
 	      }
 	      
 	      //添加成立日期判断不能为空
 	      var establishmentDay=document.getElementById("establishmentDay").value;
 	      if(establishmentDay==''){
 	           flag="0";
	           null_field+=num+"、成立日期；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加法定代表人或主要负责人判断不能为空
 	      var fddelegate=document.getElementById("fddelegate").value;
 	      if(fddelegate==''){
 	           flag="0";
	           null_field+=num+"、法定代表人或主要负责人；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加联系电话判断不能为空
 	      var phoneCode=document.getElementById("phoneCode").value;
 	      if(phoneCode==''){
 	           flag="0";
	           null_field+=num+"、联系电话；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加安管负责人判断不能为空
 	      var safetyMngPerson=document.getElementById("safetyMngPerson").value;
 	      if(safetyMngPerson==''){
 	           flag="0";
	           null_field+=num+"、安管负责人；\n";
	           num=parseInt(num)+1;
 	      }
 	      //添加安管负责人联系电话判断不能为空
 	      var safetyMngPersonPhone=document.getElementById("safetyMngPersonPhone").value;
 	      if(safetyMngPersonPhone==''){
 	           flag="0";
	           null_field+=num+"、安管负责人联系电话；\n";
	           num=parseInt(num)+1;
 	      }
 	      
 	     
	 	 
 	      //添加经营范围判断不能为空
 	      var businessScope=document.getElementById("businessScope").value;
 	      if(businessScope==''){
 	           flag="0";
	           null_field+=num+"、经营范围；\n";
	           num=parseInt(num)+1;
 	      }
 	      
 		  document.getElementById("img_xcjcjl").disabled=true;
  		  var obj=get("companyForm");
  		  obj.action="${url}";
  		  for(var i=0;i<getName("company.tradeTypes").length;i++) {
  		  	getName("company.tradeTypes")[i].disabled = false;
  		  }
  		  for(var i=0;i<getTag("input").length;i++) {
  		  	if(getTag("input")[i].type=="checkbox" && getTag("input")[i].id.indexOf("fir_")>-1) {
  		  		if(!getTag("input")[i].checked) {
  		  			if(get("sp_sec_"+getTag("input")[i].id.split("_")[1]))
  		  				get("sp_sec_"+getTag("input")[i].id.split("_")[1]).innerHTML = "";
  		  			if(get("sp_thi_"+getTag("input")[i].id.split("_")[1]))
  		  				get("sp_thi_"+getTag("input")[i].id.split("_")[1]).innerHTML = "";
  		  			if(get("sp_for_"+getTag("input")[i].id.split("_")[1]))
  		  				get("sp_for_"+getTag("input")[i].id.split("_")[1]).innerHTML = "";
  		  		}
  		  	}
  		  }
  		  
  		  
  		  //根据flag字段来设置isModify的值
  		  if(flag=='0'){
  		    num=parseInt(num)-1;
  		    document.getElementById("isModify").value="0";
  		    alert("您此次修改有"+num+"项必填项没有填写，这些必填项为：\n"+null_field);
  		  }else{
  		    document.getElementById("isModify").value="1";
  		  }
  	
 		  obj.submit();
 	//}
 	
 	
}
function showSecondTrade(firstTrade) {
	if (firstTrade.checked) {
		if(get("sp_sec_"+firstTrade.id.split("_")[1])) {
			get("sp_sec_"+firstTrade.id.split("_")[1]).style.display = "";
			get("sel_sec_"+firstTrade.id.split("_")[1]).options[0].selected = true;
		}
	} else{
		if(get("sp_sec_"+firstTrade.id.split("_")[1])) {
		    get("sel_sec_"+firstTrade.id.split("_")[1]).options[0].selected = true;
			get("sp_sec_"+firstTrade.id.split("_")[1]).style.display = "none";
		}
		if(get("sp_thi_"+firstTrade.id.split("_")[1])) {
		    get("sel_thi_"+firstTrade.id.split("_")[1]).options[0].selected = true;
			get("sp_thi_"+firstTrade.id.split("_")[1]).style.display = "none";
		}
		if(get("sp_for_"+firstTrade.id.split("_")[1])) {
		    get("sel_for_"+firstTrade.id.split("_")[1]).options[0].selected = true;
			get("sp_for_"+firstTrade.id.split("_")[1]).style.display = "none";
		}
	}
}
function showThirdTrade(secondTrade) {
	var fir_id = secondTrade.id.split("_")[secondTrade.id.split("_").length-1];
	var sec_id = secondTrade.value;
	var is_done = false;
	<#list tradeTypes?if_exists as fir>
		if(fir_id == "${fir.id}") {
			<#list fir.daIndustryParameters?if_exists as sec>
				if(sec_id == "${sec.id}" && "${sec.type}" == 1) {
					<#if sec.daIndustryParameters?exists&&sec.daIndustryParameters?size!=0>
						get("sp_thi_${fir.id}").style.display = "";
						get("sel_thi_${fir.id}").style.display = "";
						for(var i=get("sel_thi_${fir.id}").options.length;i>=1;i--) {
							get("sel_thi_${fir.id}").options[i] = null;
						}
						<#list sec.daIndustryParameters?if_exists as thi>
							if("${thi.type}" == 1) {
								var opt = new Option("${thi.name}","${thi.id}");
								get("sel_thi_${fir.id}").options[get("sel_thi_${fir.id}").length] = opt;
								opt = null;
								is_done = true;
							}
						</#list>
					<#else>
						if(get("sp_thi_${fir.id}")) {
						    get("sel_thi_${fir.id}").options[0].selected = true;
							get("sel_thi_${fir.id}").style.display = "none";
						}
						if(get("sp_for_${fir.id}")) {
						    get("sel_for_${fir.id}").options[0].selected = true;
							get("sel_for_${fir.id}").style.display = "none";
						}
					</#if>
				}
			</#list>
		}
	</#list>
	if(!is_done) {
		if(get("sel_thi_"+fir_id)) {
		    get("sel_thi_"+fir_id).options[0].selected = true;
			get("sel_thi_"+fir_id).style.display = "none";
		}
		if(get("sel_for_"+fir_id)) {
		    get("sel_for_"+fir_id).options[0].selected = true;
			get("sel_for_"+fir_id).style.display = "none";
		}
	}
}
function showForthTrade(ThirdTrade) {
	var fir_id = ThirdTrade.id.split("_")[ThirdTrade.id.split("_").length-1];
	var sec_id = get("sel_sec_"+fir_id).value;
	var thi_id = ThirdTrade.value;
	var is_done = false;
	<#list tradeTypes?if_exists as fir>
		if(fir_id == "${fir.id}") {
			<#list fir.daIndustryParameters?if_exists as sec>
				if(sec_id == "${sec.id}" && "${sec.type}" == 1) {
					<#if sec.daIndustryParameters?exists&&sec.daIndustryParameters?size!=0>
						<#list sec.daIndustryParameters?if_exists as thi>
							if(thi_id == "${thi.id}" && "${thi.type}" == 1) {
								<#if thi.daIndustryParameters?exists&&thi.daIndustryParameters?size!=0>
									get("sp_for_${fir.id}").style.display = "";
									get("sel_for_${fir.id}").style.display = "";
									for(var i=get("sel_for_${fir.id}").options.length;i>=1;i--) {
										get("sel_for_${fir.id}").options[i] = null;
									}
									<#list thi.daIndustryParameters?if_exists as for>
										if("${for.type}" == 1) {
											var opt = new Option("${for.name}","${for.id}");
											get("sel_for_${fir.id}").options[get("sel_for_${fir.id}").length] = opt;
											opt = null;
											is_done = true;
										}
									</#list>
								<#else>
									if(get("sp_for_${fir.id}")) {
									    get("sel_for_${fir.id}").options[0].selected = true;
										get("sel_for_${fir.id}").style.display = "none";
									}
								</#if>
							}
						</#list>
					</#if>
				}
			</#list>
		}
	</#list>
	if(!is_done) {
		if(get("sel_for_"+fir_id)) {
		    get("sel_for_"+fir_id).options[0].selected = true;
			get("sel_for_"+fir_id).style.display = "none";
		}
	}
}
function showSecOptionByCoreCompany() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if tradetypeId2?exists>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${tradetypeId2}") {
					arguments[0].options[i].selected = true;
					if(get("sel_sec_"+fir_id)) {
						showThirdTrade(get("sel_sec_"+fir_id));
					}
					if(get("sel_thi_"+fir_id)) {
						showThiOptionByCoreCompany(get("sel_thi_"+fir_id));
					}
				}
			}
	</#if>
}

function showSecOption() {

	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if company?exists>
		<#list company.hzTradeTypes?if_exists as ht>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${ht.id}") {
					arguments[0].options[i].selected = true;
					if(get("sel_sec_"+fir_id)) {
						showThirdTrade(get("sel_sec_"+fir_id));
					}
					if(get("sel_thi_"+fir_id)) {
						showThiOption(get("sel_thi_"+fir_id));
					}
				}
			}
		</#list>
	</#if>
}

function showThiOptionByCoreCompany() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if tradetypeId3?exists>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${tradetypeId3}") {
					arguments[0].options[i].selected = true;
					if(get("sel_thi_"+fir_id)) {
						showForthTrade(get("sel_thi_"+fir_id));
					}
					if(get("sel_for_"+fir_id)) {
						showForOption(get("sel_for_"+fir_id));
					}
				}
			}
	</#if>
}


function showThiOption() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if company?exists>
		<#list company.hzTradeTypes?if_exists as ht>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${ht.id}") {
					arguments[0].options[i].selected = true;
					if(get("sel_thi_"+fir_id)) {
						showForthTrade(get("sel_thi_"+fir_id));
					}
					if(get("sel_for_"+fir_id)) {
						showForOption(get("sel_for_"+fir_id));
					}
				}
			}
		</#list>
	</#if>
}
function showForOption() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if company?exists>
		<#list company.hzTradeTypes?if_exists as ht>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${ht.id}") {
					arguments[0].options[i].selected = true;
				}
			}
		</#list>
	</#if>
}
function checkTradeType() {
    if(chooseCheckBox1(1)){
         submitCreate();
    }
}
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th><#if company?exists>修改<#else>添加</#if>基本信息</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if !viewRole?? || !viewRole>
		<#if userDetail.userIndustry!='admin'>
		<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="checkTradeType();"><b>保存/确认</b></a></li>
		</#if>
	</#if>
	<!-- <li id="img_amend"><a href="#" class="b2" onClick="addComapnyPass(${company.id})"><b>确认</b></a></li>
	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="addComapnyLogout1()"><b>注销或暂定经营</b></a></li>
	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="addComapnyLogout2(${company.id})"><b>无经营场所</b></a></li>-->
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'companyForm' />
<form id="companyForm" name="companyForm" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th colspan="2"><#if userDetail.userIndustry=='minzong'>场所名称<#else>单位名称</#if></th>
        <td><input name="company.companyName" type="text" class="input" id="companyName" value="${company.companyName}" size="40" maxlength="50">&nbsp;
        <span style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        <#-- <ui:v for="companyName"  rule="require" empty="单位名称不允许为空" pass="&nbsp;" warn="&nbsp;"/>-->
        </td>
      
        <th>注册地址</th>
        <td> 
        <#-- <#if corecompany?exists&&corecompany.regAddress?exists&&corecompany.regAddress!=''>
         <input name="company.regAddress" type="text" class="input" id="regAddress" value="${corecompany.regAddress}" size="25" maxlength="25"><span style=color:red>*</span>
        <#else>-->
          <input name="company.regAddress" type="text" class="input" id="regAddress" value="${company.regAddress}" size="25" maxlength="25">
          <span style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        <#--  </#if>-->
        
          <#-- <ui:v for="regAddress"  rule="require" empty="企业注册地址不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
        </td>
      </tr>
      <tr>
       	<th colspan="2">所在地址</th>
        <td><input name="company.address" type="text" class="input" id="address" value="${company.address}" size="40" maxlength="50">&nbsp;
         <span style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
         <#-- <ui:v for="address"  rule="require" empty="企业所在地址不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
         
         </td>
        <th>组织机构代码</th>
        <td>
          <!--<input name="company.setupNumber" type="text" class="input" id="setupNumber" value="${company.setupNumber}" size="25" maxlength="25"><span style=color:red>*</span>
        
         <ui:v for="setupNumber"  rule="require" empty="组织机构代码不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
        <#if company?exists&&company.setupNumber?exists&&company.setupNumber!=''&&company.setupNumber!='null'>
	             <input type="radio"  name="haveSetupNumber" value="1" checked onClick="setSetupNumberBlock(1);">有 
                 <input type="radio"  name="haveSetupNumber" value="0" onClick="setSetupNumberBlock(0);">无
                  &nbsp;&nbsp;<input name="company.setupNumber" type="text" class="input" id="setupNumber" value="${company.setupNumber}" style="width:150px;" size="15" maxlength="25" onblur="checkSetupNumber(this.value)" >
                  <span  id="setupNumberSpenId"  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        <#else>
          <input type="radio"  name="haveSetupNumber" value="1"  onClick="setSetupNumberBlock(1);">有 
          <input type="radio"  name="haveSetupNumber" value="0" checked onClick="setSetupNumberBlock(0);">无
           &nbsp;&nbsp;<input name="company.setupNumber" type="text" class="input" style="display:none;width:150px;" id="setupNumber" value="${company.setupNumber}" size="15" maxlength="25"  onblur="checkSetupNumber(this.value)">
           <span  id="setupNumberSpenId"  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        </#if> 
         
         
         
        </td>
      	</tr>
      <tr>
        <th colspan="2">所在区域</th>
        <td nowrap="nowrap" style="word-break:keep-all;"><div id="div-area" style="display:inline"></div><div style="display:inline"><span style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span></div>
        
        </td>
        
        <th><#if userDetail.userIndustry=='minzong'>登记证号<#else>统一社会信用代码<br/>（工商注册号）</#if></th>
        <td>
        
         <#if company?exists&&company.haveRegNum?exists&&company.haveRegNum!=''>
	         <#if company.haveRegNum=='0'>
	              <input type="radio"  name="company.haveRegNum" value="1"  onClick="setRegNumBlock(1);">有 
                  <input type="radio"  name="company.haveRegNum" value="0" checked onClick="setRegNumBlock(0);">无
                   &nbsp;&nbsp;<input name="company.regNum" type="text" class="input" id="regNum" value="${company.regNum}" style="display:none;width:150px;" onblur="checkRegNum(this.value)" size="15" maxlength="25">
                   <span id="regNumSpenId" style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
	         <#else>
	             <input type="radio"  name="company.haveRegNum" value="1" checked onClick="setRegNumBlock(1);">有 
                 <input type="radio"  name="company.haveRegNum" value="0" onClick="setRegNumBlock(0);">无
                  &nbsp;&nbsp;<input name="company.regNum" type="text" class="input" id="regNum" value="${company.regNum}" size="15" style="width:150px;" onblur="checkRegNum(this.value)" maxlength="25">
                  <span id="regNumSpenId" style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
	         </#if>
        <#else>
          <input type="radio"  name="company.haveRegNum" value="1" checked onClick="setRegNumBlock(1);">有 
          <input type="radio"  name="company.haveRegNum" value="0" onClick="setRegNumBlock(0);">无
           &nbsp;&nbsp;<input name="company.regNum" type="text" class="input" id="regNum" value="${company.regNum}" size="15" onblur="checkRegNum(this.value)" maxlength="25">
           <span id="regNumSpenId" style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        </#if>
        
        <#--<div style="display:inline">-->
        <#--<input type="radio"  name="company.haveRegNum" value="1" checked onClick="setRegNumBlock(1);">有 -->
        <#--<input type="radio"  name="company.haveRegNum" value="0" onClick="setRegNumBlock(0);">无-->
        <#--</div>-->
        
        <#--<#if corecompany?exists&&corecompany.businessRegNum?exists&&corecompany.businessRegNum!=''> 
          <input name="company.regNum" type="text" class="input" id="regNum" value="${corecompany.businessRegNum}" size="25" maxlength="25"><span style=color:red>*</span>
        <#else>-->
          <#--<div style="display:inline" id=regNumDiv>-->
          <#--&nbsp;&nbsp;<input name="company.regNum" type="text" class="input" id="regNum" value="${company.regNum}" size="15" maxlength="25"><span id="regNumSpenId" style="color:red">*</span>-->
          <#--<span style=color:red>*</span>-->
          <#--</div>-->
         <#-- </#if>-->
       
        
        <#-- <#if userDetail.userIndustry=='minzong'>
        <ui:v for="regNum"  rule="require" empty="企业登记证号不允许为空" pass="&nbsp" warn="&nbsp;"/>
        <#else>
        <ui:v for="regNum"  rule="require" empty="企业工商注册号不允许为空" pass="&nbsp" warn="&nbsp;"/>
        </#if>-->
        
         <#--
         <#if userDetail.userIndustry=='minzong'>
            <input type="hidden" name="regNumTips" id="regNumTips" value="企业登记证号不允许为空">
         <#else>
            <input type="hidden" name="regNumTips" id="regNumTips" value="企业工商注册号不允许为空">
         </#if>
         -->
         
         
         <#if userDetail.userIndustry=='minzong'>
            <input type="hidden" name="regNumTips" id="regNumTips" value="企业登记证号">
         <#else>
            <input type="hidden" name="regNumTips" id="regNumTips" value="企业工商注册号">
         </#if>
        </td>
      </tr>
      <tr>
      	<th colspan="2">所属行业主管部门</th>
        <td>
         <#if company?exists&&company.govAdminOrg?exists&&company.govAdminOrg!=''>
            <input name="company.govAdminOrg" type="text" class="input" id="govAdminOrg" value="${company.govAdminOrg}" size="25" maxlength="20">
            
         <#else>
            <input name="company.govAdminOrg" type="text" class="input" id="govAdminOrg" value="${departmentName}" size="25" maxlength="20">
         </#if>
        
        
        &nbsp;<span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
       <#-- <ui:v for="govAdminOrg" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/>-->
       
       </td>
        <th>企业内部编码</th>
        <td><input name="company.companyCode" type="text" class="input" id="companyCode" value="${company.companyCode}" size="25" maxlength="13" style="ime-mode:disabled;">
        </td>
       </tr>
      
      <tr>
        <th colspan="2">经济类型</th>
        <td>
        
  	          <select name="company.economicType1" id="economicType1" onChange="addEconomicTypeOption(this.value);">
	              <option value="">--请选择--</option>
	              <#--定义一级经济类型id-->
	              <#assign fId=0>
	              <#if (economyKindEnums?if_exists?size > 0)>
	                     <#--定义一类经济类型变量，如果中心库Tcompany中的economicType1不为空的话，就赋值给变量。如果为空的话，则取daCompany表中的economicType1值-->
	                     <#assign companyEconomicType=''>
	                     <#--<#if corecompany?exists&&corecompany.economicType1?exists&&corecompany.economicType1!=''>
                                    <#assign companyEconomicType='${corecompany.economicType1}'/>
                         <#else>-->
                                    <#if company?exists&&company.economicType1?exists>
                                        <#assign companyEconomicType='${company.economicType1}'/>
                                    </#if>
                          <#--</#if>-->
                                
                                
				      <#list economyKindEnums?if_exists as economyKindEnum>
				                <#--定义变量selected是否选中-->
				                <#assign selected=''>
				                
				                <#--用企业一类经济类型变量companyEconomicType和循环的emnu值进行比较，相等的话，则让变量selected设置为selected。并赋值fId，为二级经济类型默认选中值做条件。-->
				                <#if companyEconomicType==economyKindEnum.enumCode>
                                    <#assign selected='selected'>
                                    <#assign fId='${economyKindEnum.id}'/>
                                </#if>
					            <option value="${economyKindEnum.enumCode}" ${selected}>${economyKindEnum.enumName}</option>
					  </#list>
				  </#if>
			  </select>
  	      <select name="company.economicType2" id="economicType2">
	              <option value=''>--请选择--</option>
	              
	               <#if (childEconomyKindEnums?if_exists?size > 0)>
	                    
	                  <#--定义二类经济类型变量，如果中心库Tcompany中的economicType2不为空的话，就赋值给变量。如果为空的话，则取daCompany表中的economicType2值-->
	                  <#assign companyEconomicType2=''>
	                 <#-- <#if corecompany?exists&&corecompany.economicType2?exists&&corecompany.economicType2!=''>
	                    <#assign companyEconomicType2='${corecompany.economicType2}'>
	                  <#else>-->
                                   <#if company?exists&&company.economicType2?exists>
                                        <#assign companyEconomicType2='${company.economicType2}'/>
                                     </#if>
	                  <#--</#if>-->
	               
				      <#list childEconomyKindEnums?if_exists as economyKind>
					      <#if economyKind.fatherId==fId?number>
					         <option value="${economyKind.enumCode}"  <#if companyEconomicType2==economyKind.enumCode>selected</#if>>${economyKind.enumName}</option>
					      </#if>   
					  </#list>
				  </#if>
		  </select>&nbsp;<span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
		  <#--<ui:v for="economicType1"  rule="require" empty="经济类型不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
        </td>
        
        <th>成立日期</th>
        <td>
         <#-- <#if corecompany?exists&&corecompany.establishmentDay?exists> 
          <input name="company.establishmentDay" type="text" size="12" id="establishmentDay"  class="Wdate" value="${corecompany.establishmentDay?date}" maxlength="10" onfocus="WdatePicker();" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;">
         <#else>-->
          <input name="company.establishmentDay" type="text" size="12" id="establishmentDay"  class="Wdate" value="${company.establishmentDay?date}" maxlength="10" onfocus="WdatePicker();" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;">
         <#--</#if>-->
       <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
       <#-- <ui:v for="establishmentDay"  rule="require" empty="成立日期不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
       </td>
        </td>
      </tr>
      <tr>
        <th colspan="2">国民经济分类</th>
        <td colspan="3">
        
  	          <select name="company.naEcoType1" id="naEcoType1" onChange="addNaEcoTypeOption(this.value);">
	              <option value="">--请选择--</option>
	              <#assign fNationalEconomicId=0>
	              <#if (nationalEconomicEnums?if_exists?size > 0)>
	              
	                     <#--定义一类国民经济分类变量，如果中心库Tcompany中的nationalEconomicType1不为空的话，就赋值给变量。如果为空的话，则取daCompany表中的naEcoType1值-->
	                     <#assign companynaEcoType1=''>
	                      <#--<#if corecompany?exists&&corecompany.nationalEconomicType1?exists&&corecompany.nationalEconomicType1!=''>
                                    <#assign companynaEcoType1='${corecompany.nationalEconomicType1}'/>
                         <#else>-->
                                    <#if company?exists&&company.naEcoType1?exists>
                                        <#assign companynaEcoType1='${company.naEcoType1}'/>
                                    </#if>
                        <#--  </#if>-->
                         
                         
				      <#list nationalEconomicEnums?if_exists as nationalEconomicEnum>
				                <#assign selected=''>
				      
				                <#if companynaEcoType1==nationalEconomicEnum.enumCode>
                                    <#assign selected='selected'>
                                    <#assign fNationalEconomicId='${nationalEconomicEnum.id}'/>
                                </#if>
                                
                                
					            <option value="${nationalEconomicEnum.enumCode}" ${selected}>${nationalEconomicEnum.enumName}</option>
					  </#list>
				  </#if>
			  </select>
		  
		
  	      <select name="company.naEcoType2" id="naEcoType2">
	               <option value=''>--请选择--</option>
	              
	               <#if (childNationalEconomicEnums?if_exists?size > 0)>
	               
	                  <#--定义二类国民经济分类变量，如果中心库Tcompany中的nationalEconomicType2不为空的话，就赋值给变量。如果为空的话，则取daCompany表中的naEcoType2值-->
	                  <#assign companynaEcoType2=''>
	                  <#--<#if corecompany?exists&&corecompany.nationalEconomicType2?exists&&corecompany.nationalEconomicType2!=''>
	                    <#assign companynaEcoType2='${corecompany.nationalEconomicType2}'>
	                  <#else>-->
                                    <#if company?exists&&company.naEcoType2?exists>
                                        <#assign companynaEcoType2='${company.naEcoType2}'/>
                                    </#if>
	                <#-- </#if>-->
	                  
	                  
				      <#list childNationalEconomicEnums?if_exists as naEcoType>
					      <#if naEcoType.fatherId==fNationalEconomicId?number>
					         <option value="${naEcoType.enumCode}" <#if companynaEcoType2==naEcoType.enumCode>selected</#if>>${naEcoType.enumName}</option>
					      </#if>   
					  </#list>
				  </#if>
		  </select>&nbsp;<span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
		<#-- <ui:v for="naEcoType1"  rule="require" empty="国民经济分类不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
        </td>
        
        
      </tr>
      
      <tr>
      	<th colspan="2">法定代表人<br />或主要负责人</th>
        <td>
         <#--<#if corecompany?exists&&corecompany.legalPerson?exists&&corecompany.legalPerson!=''>
           <input name="company.fddelegate" type="text" class="input" id="fddelegate" value="${corecompany.legalPerson}" size="25" maxlength="20">
         <#else>-->
           <input name="company.fddelegate" type="text" class="input" id="fddelegate" value="${company.fddelegate}" size="25" maxlength="20">
         <#--</#if>-->
         
         
        &nbsp;<span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        <#--<ui:v for="fddelegate" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/>-->
        
        </td>
        <th>联系电话</th>
        <td><input name="company.phoneCode" type="text" class="input" id="phoneCode" value="${company.phoneCode}" size="25" maxlength="13" style="ime-mode:disabled;">
        <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        <ui:v for="phoneCode" rule="phone_mobile" require="false" warn="电话正确格式如0574-87364008。" pass="&nbsp;"/>
       <#-- <ui:v for="phoneCode"  rule="require" empty="联系电话不允许为空" pass="&nbsp" warn="&nbsp;"/>-->
        </td>
      </tr>
      
      
      <tr>
      	<th colspan="2">安管负责人</th>
        <td><input name="company.safetyMngPerson" type="text" class="input" id="safetyMngPerson" value="${company.safetyMngPerson}" size="25" maxlength="20">&nbsp;
        <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        	<#--<ui:v for="safetyMngPerson" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/>-->
        </td>
        <th>安管负责人联系电话</th>
        <td><input name="company.safetyMngPersonPhone" type="text" class="input" id="safetyMngPersonPhone" value="${company.safetyMngPersonPhone}" size="25" maxlength="13" style="ime-mode:disabled;">&nbsp;
        <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
                <ui:v for="safetyMngPersonPhone" rule="phone_mobile" require="false" warn="电话正确格式如0574-87364008。" pass="&nbsp;"/>
        </td>
      </tr>
      
 
       <tr >
        <th colspan="2">企业规模</th>
        <td >
	        <select name="company.productionScale" id ="productionScale">
	         	<option value="">--请选择--</option>
	         	
	         	
	         	  
				 <#list companyScaleEnums?if_exists as companyScale>
					   

					         <option value="${companyScale.enumCode}" <#if company?exists&&company.productionScale?exists&&company.productionScale==companyScale.enumCode>selected</#if>>${companyScale.enumName}</option>
					    
			     </#list>
	        </select>
	       &nbsp;<a  href="../scaleBz.html"  style="color:red"  target="_blank">划分标准</a>&nbsp;
	       
	       <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
	       <#--<ui:v for="productionScale" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/>-->
	       
        </td>
         <th >规模以上企业</th>
        <td>
        	<input type="radio" name="company.daCompanyPass.enterprise" value="true" <#if company?if_exists.daCompanyPass?if_exists.enterprise?exists && company.daCompanyPass.enterprise>checked</#if>/>是
        	&nbsp;&nbsp;<input type="radio" name="company.daCompanyPass.enterprise" value="false" <#if !company?if_exists.daCompanyPass?if_exists.enterprise?exists || !company.daCompanyPass.enterprise>checked</#if>/>否 
        	<span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        </td>
      </tr>
         
     <tr>
      	<th colspan="2">高风险作业</th>
        <td colspan="3" nowrap="nowrap">
        
         <#if company?exists&&company.isHighRiskWork?exists&&company.isHighRiskWork!=''&&company.isHighRiskWork!='null'>
	              <span style="float:left">
	              <input type="radio"  name="company.isHighRiskWork" value="1" <#if company.isHighRiskWork=='1'>checked</#if>  onClick="javascript:setHighRiskWorkBlock(1);">有 
		          <input type="radio"  name="company.isHighRiskWork" value="0" <#if company.isHighRiskWork=='0'>checked</#if>  onClick="javascript:setHighRiskWorkBlock(0);">无
                  </span>
                  <span  style="float:left" id="highRiskWork_span" <#if company.isHighRiskWork=='1'> style="display:block"<#else>style="display:none"</#if>>
                  &nbsp;&nbsp;&nbsp;
                  <#if (highRiskWorkEnums?if_exists?size > 0)>
				      <#list highRiskWorkEnums?if_exists as highRiskWork>  
					     <input type="checkbox" name="highRiskWorkCode"  <#if (company.isHighRiskWork=='1'&&company.highRiskWork?exists&&company.highRiskWork?index_of(highRiskWork.enumCode) > -1)>checked </#if>  value="${highRiskWork.enumCode}">${highRiskWork.enumName} 	
					  </#list>
				  </#if>
				  <input type="hidden" name="company.highRiskWork" id="company.highRiskWork" value="${company.highRiskWork}">
				  <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
				  </span>
				
        <#else>
                  <span style="float:left">
		          <input type="radio"  name="company.isHighRiskWork" value="1" onClick="javascript:setHighRiskWorkBlock(1);">有 
		          <input type="radio"  name="company.isHighRiskWork" value="0" onClick="javascript:setHighRiskWorkBlock(0);">无
                   </span>
                  <span  style="float:left" id="highRiskWork_span" style="display:none">
                  &nbsp;&nbsp;&nbsp;
                  <#if (highRiskWorkEnums?if_exists?size > 0)>
				      <#list highRiskWorkEnums?if_exists as highRiskWork>
					     <input type="checkbox" name="highRiskWorkCode" value="${highRiskWork.enumCode}">${highRiskWork.enumName}
					  </#list>
				  </#if>
				 <input type="hidden" name="company.highRiskWork" id="company.highRiskWork" value="${company.highRiskWork}"> 
				 <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
				 </span>
        </#if> 
        </td>
      </tr>
      
     <tr>
      	<th colspan="2">经营范围</th>
        <td colspan="3">
        <#--
         <#if corecompany?exists&&corecompany.businessScope?exists&&corecompany.businessScope!=''>
            <textarea width="80%" rows="4" cols="80%" name="company.businessScope" id="businessScope">${corecompany.businessScope}</textarea>
         <#else>-->
            <textarea width="80%" rows="4" cols="80%" name="company.businessScope" id="businessScope">${company.businessScope}</textarea>
        <#-- </#if>-->
         <#-- <ui:v for="businessScope" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/>-->
           <span  style=color:red><b><font size="5" style="vertical-align:middle;">*</font></b></span>
        </td>
        
      </tr>
      
      
      <tr  style="display:none">
        
        <th>社会团体</th>
        <td>
        	<input type="radio" name="company.daCompanyPass.orga" value="true" <#if company?if_exists.daCompanyPass?if_exists.orga?exists && company.daCompanyPass.orga>checked</#if>/>是
        	&nbsp;&nbsp;<input type="radio" name="company.daCompanyPass.orga" value="false" <#if !company?if_exists.daCompanyPass?if_exists.orga?exists || !company.daCompanyPass.orga>checked</#if>/>否
        </td>
      </tr>
      
      <tr id="tr_trade" name="tr_trade">
        <td id="td_trade" width="8%" style="background:#F0F0F0;color:#333;"><p align="center"><strong>行业分类</strong></p></td>
        <#list tradeTypes?if_exists as t>
        	<#if t_index!=0>
        	   <tr id="tr_trade" name="tr_trade" style="display:none;">
        	</#if>
	        <td width="9%" style="background:#F0F0F0;color:#333;display:none;" id="td_name" name="td_name">
	               <p align="right">
		           <#if userDetail.secondArea!=0 && t.name='贸易'>
		           <p align="right"><strong>商务</strong></p>
		           <#else>
					<p align="right"><strong>${t.name}</strong></p>
		           </#if>
                   </p>			
	        </td>
	        <td colspan="3" id="td_trade_${t.id}" style="display:none;">
	        	<input type="checkbox" name="company.tradeTypes" id="fir_${t.id}" value="${t.id}" onClick="showSecondTrade(this);" disabled="true" />&nbsp;&nbsp;
        		<#assign hasChild=false>
        		<#list t.daIndustryParameters?if_exists as t_dip><#if t_dip.type==1><#assign hasChild=true></#if></#list>
        		<#if t.daIndustryParameters?exists&&t.daIndustryParameters?size!=0&&hasChild>
		        	<span id="sp_sec_${t.id}" style="display:none;">
		        		<select name="company.tradeTypes" id="sel_sec_${t.id}" onChange="showThirdTrade(this)" disabled="true">
		        			<option value="-1">--请选择--</option>
		        			<#list t.daIndustryParameters?if_exists as sec_T>
		        				<#if sec_T.type==1>
		        					<option value="${sec_T.id}">${sec_T.name}</option>
		        				</#if>
		        			</#list>
		        		</select>
		        	</span>
		        	&nbsp;
		        	<span id="sp_thi_${t.id}" style="display:none">
		        		<select name="company.tradeTypes" id="sel_thi_${t.id}" disabled="true" onChange="showForthTrade(this);">
		        			<option value="-1">--请选择--</option>
		        		</select>
		        	</span>
		        	&nbsp;
		        	<span id="sp_for_${t.id}" style="display:none">
		        		<select name="company.tradeTypes" id="sel_for_${t.id}" disabled="true">
		        			<option value="-1">--请选择--</option>
		        		</select>
		        	</span>
        		</#if>
	        </td>
	        </tr>
        </#list>
    </table></td>
  </tr>
  
</table>
<input type="hidden" name="company.uuid" id="uuid" value="${company.uuid}"/>
<input type="hidden" name="company.id" id="id" value="${company.id}"/>
<input type="hidden" name="corecompany.id"  value="${corecompany.id}"/>
<input type="hidden" name="company.industryId" id="industryId" value="${company.industryId}"/>
<input type="hidden" name="company.employeeNumber" id="employeeNumber" value="${company.employeeNumber}"/>
<#--<input type="hidden" name="company.businessScope" id="businessScope" value="${company.businessScope}"/>-->
<input type="hidden" name="company.focusFireUnits" id="focusFireUnits" value="${company.focusFireUnits}"/>
<input type="hidden" name="company.daCompanyPass.id" id="id" value="${company.daCompanyPass.id}"/>
<input type="hidden" name="company.daCompanyPass.comUserId" id="id" value="${company.daCompanyPass.comUserId}"/>

<input type="hidden" name="company.isModify" id="isModify" value="${company.isModify}"/>
</form>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
<form action="../companyLogout/createCompanyLogout.xhtml" method="post" name="companyLogoutForm" id="companyLogoutForm">
	<input type="hidden" name="daCompanyLogout.daCompany.id" id="daCompanyLogoutId" value=""/>
	<input type="hidden" name="daCompanyLogout.type" id="type" value=""/>
</form>
<form action="../company/createCompanyPass.xhtml" method="post" name="companyPassForm" id="companyPassForm">
	<input type="hidden" id="companyIds" name="companyIds" value=""/>
</form>
<script type="text/javascript">

	function addComapnyPass(id){
		get("companyIds").value=id;
		get("companyPassForm").submit();
	}
	function addComapnyLogout1(id){
		get("daCompanyLogoutId").value=id;
		get("type").value=1;
		get("companyLogoutForm").submit();
	}
	function addComapnyLogout2(id){
		get("type").value=2;
		get("daCompanyLogoutId").value=id;
		get("companyLogoutForm").submit();
	}

<#list tradeTypesAllow?if_exists as ta>
	get("fir_${ta.id}").disabled = false;
	<#if userDetail.userIndustry!='anwei' && userDetail.userIndustry!='qiye'>
		get("fir_${ta.id}").checked = true;
		showSecondTrade(get("fir_${ta.id}"));
	</#if>
	
	
	if(get("sel_sec_${ta.id}")) {
		get("sel_sec_${ta.id}").disabled = false;
	}	
	if(get("sel_thi_${ta.id}")) {
		get("sel_thi_${ta.id}").disabled = false;
	}	
	if(get("sel_for_${ta.id}")) {
		get("sel_for_${ta.id}").disabled = false;
	}
	
	
	
</#list>

<#--首先根据中心库企业表的信息来初始化行业类别-->
<#if tradetypeId1?exists>
    <#--先判断中心库企业表部门code有没有，没的话，再用本系统的企业信息初始化。有的情况-->
    var flag='0';<#--用flag来判断中心库企业表部门code是否有和本系统的code相等的情况，有的话，初始化，没的话，用本系统的企业信息初始化-->
	  <#list tradeTypes?if_exists as ht>
	   <#if ht.id==tradetypeId1>
	      if(get("fir_${ht.id}")){
						get("fir_${ht.id}").checked = true;
						showSecondTrade(get("fir_${ht.id}"));
						if(get("sel_sec_${ht.id}"))
							showSecOption(get("sel_sec_${ht.id}"));
					}
			flag='1';
	   </#if>
	</#list>
	
	<#--中心库企业表部门code在本系统中不存在，用本系统的企业信息初始化行业信息-->
	if(flag=='0'){
	
	   initFirstTradeTypes();
	}

<#else>
<#--中心库企业表部门code没有的情况-->
	 initFirstTradeTypes();


</#if>




<#if (tradeTypesAllow?size>1)>
	get("td_trade").colSpan = "1";
	get("td_trade").rowSpan = "${tradeTypes.size()}";
	if (getName("tr_trade").length) {
		for (var i=1; i<getName("tr_trade").length; i++) {
			getName("tr_trade")[i].style.display = "";
		}
	}
	if (getName("td_name").length) {
		for (var i=0; i<getName("td_name").length; i++) {
			getName("td_name")[i].style.display = "";
		}
	}
	for (var i=0; i<getName("tr_trade").length; i++) {
		for (var j=0; j<getName("tr_trade")[i].children.length; j++) {
			if (getName("tr_trade")[i].children[j].id &&
				getName("tr_trade")[i].children[j].id.indexOf("td_trade_") > -1) {
				getName("tr_trade")[i].children[j].style.display = "";
			}
		}
	}
<#else>
	<#list tradeTypesAllow?if_exists as ta>
		if (getName("tr_trade")[0].children[2] != get("td_trade_${ta.id}")) {
			getName("tr_trade")[0].children[2].innerHTML = get("td_trade_${ta.id}").innerHTML;
			get("td_trade_${ta.id}").innerHTML = "";
		}
		getName("tr_trade")[0].children[2].style.display = "";
		getName("tr_trade")[0].children[0].colSpan="1";		
		getName("td_name")[0].style.display = "";
		
		getName("td_name")[0].innerHTML = "<p align='right'><strong><#if userDetail.secondArea!=0 && ta.name='贸易'>商务 <#else>${ta.name} </#if></strong></p>";
		<#if userDetail.userIndustry!='maoyi'>
		if(getName("company.tradeTypes")[0].checked){
			getName("company.tradeTypes")[0].onclick = function (){return false;};
		}
		</#if>
	</#list>
</#if>
//temp_img_save();




function initFirstTradeTypes(){
        <#if company?exists>
		if ("${company.userId}"!="${userDetail.userId}" && "${admin?string}"=="false") {
			//get("img_save").style.display = "none";
		}
		<#list company.hzTradeTypes?if_exists as ht>
			<#list tradeTypes?if_exists as fir>
				<#if ht.id==fir.id>
					if(get("fir_${fir.id}")){
						get("fir_${fir.id}").checked = true;
						showSecondTrade(get("fir_${fir.id}"));
						if(get("sel_sec_${fir.id}"))
							showSecOption(get("sel_sec_${fir.id}"));
					}
				</#if>
			</#list>
		</#list>
	</#if>
}
function addEconomicTypeOption(code){
         var eId;
         <#if (economyKindEnums?if_exists?size > 0)>
			<#list economyKindEnums?if_exists as economyKindEnum>
			              var eCode='${economyKindEnum.enumCode}';
			            
					      if(eCode==code){
					        eId=${economyKindEnum.id};
					      }
		    </#list>
	    </#if>
        
        var economicTypeSelect=get('economicType2');

        //先清除所有的选项
        economicTypeSelect.options.length=0
        //再添加相应的选项
        <#if (childEconomyKindEnums?if_exists?size > 0)>
            economicTypeSelect.options.add(new Option('--请选择--','')); 
		    <#list childEconomyKindEnums?if_exists as economicType>
		               var fatherId=${economicType.fatherId};
		               if(eId==fatherId){
		                  economicTypeSelect.options.add(new Option('${economicType.enumName}','${economicType.enumCode}')); 
		               }
			</#list>
		</#if>
}



function addNaEcoTypeOption(code){
         var eId;
         <#if (nationalEconomicEnums?if_exists?size > 0)>
			<#list nationalEconomicEnums?if_exists as naEcoType>
					      var eCode='${naEcoType.enumCode}';
					      if(eCode==code){
					        eId=${naEcoType.id};
					      }
		    </#list>
	    </#if>
       
        var naEcoTypeSelect=get('naEcoType2');
        //先清除所有的选项
        naEcoTypeSelect.options.length=0
        //再添加相应的选项
        <#if (childNationalEconomicEnums?if_exists?size > 0)>
           naEcoTypeSelect.options.add(new Option('--请选择--','')); 
		    <#list childNationalEconomicEnums?if_exists as naEco>
		               var fatherId=${naEco.fatherId};
		               if(eId==fatherId){
		                  naEcoTypeSelect.options.add(new Option('${naEco.enumName}','${naEco.enumCode}')); 
		               }
			</#list>
		</#if>
}


function setSetupNumberBlock(v){

	if(v==0){
	   get("setupNumber").style.display = "none";
	   get("setupNumberSpenId").style.display = "none";
	   get("setupNumber").value="";
	    flag_setupNumber=0;
	}else{
	   get("setupNumber").style.display = "";
	   get("setupNumberSpenId").style.display = "";
	}
}

function setHighRiskWorkBlock(v){
	if(v==0){
	   get("highRiskWork_span").style.display = "none";
	   var highRiskWorkCodes=document.getElementsByName("highRiskWorkCode");
	   for(var i=0;i<highRiskWorkCodes.length;i++){
	       highRiskWorkCodes[i].checked="";
	   }
	}else{
	   get("highRiskWork_span").style.display = "";
	}
}

function setRegNumBlock(v){

	if(v==0){
	   
	   get("regNum").style.display = "none";
	   get("regNumSpenId").style.display = "none";
	   flag_regNum=0;
	}else{
	   get("regNum").style.display = "";
	   get("regNumSpenId").style.display = "";
	}
}

window.onerror=function(){return true;} 
//alert('${userDetail.userIndustry}');

setTimeout('check()',500) ;

function check(){
if ('${userDetail.userIndustry}'!='qiye'){

	if(${userDetail.thirdArea}!=null && ${userDetail.thirdArea}!="" && ${userDetail.thirdArea}!="0"){
			document.getElementById("first-area").disabled=true;
			document.getElementById("second-area").disabled=true;
			document.getElementById("third-area").disabled=true;
			
	}else if(${userDetail.secondArea}!=null && ${userDetail.secondArea}!="" && ${userDetail.secondArea}!="0"){
			document.getElementById("first-area").disabled=true;
			document.getElementById("second-area").disabled=true;
	}
	}
}

function  checkSetupNumber(value){

    document.getElementById("img_xcjcjl").disabled=true;
	var url = "checkSetupNumber.xhtml?company.id=${company.id}&company.setupNumber="+value;
			jQuery.ajax({
			   type: "POST",
			   url: url,
			   dataType:"text",
			   data: "",
			   async:false,
			   success: function(msg){
					 if(msg=='success'){
			       		 //alert("已存在相同的组织机构代码!");
			       		 flag_setupNumber=1;
			         }else{
			          	 flag_setupNumber=0;
			         }
			         
			          document.getElementById("img_xcjcjl").disabled=false;
			   }
			  
	});	
}


function  checkRegNum(value){

    document.getElementById("img_xcjcjl").disabled=true;
	var url = "checkRegNum.xhtml?company.id=${company.id}&company.RegNum="+value;
			jQuery.ajax({
			   type: "POST",
			   url: url,
			   async:false,
			   dataType:"text",
			   data: "",
			   success: function(msg){
					 if(msg=='success'){
			       		 //alert("已存在相同的工商注册号!");
			       		 flag_regNum=1;
			         }else{
			         
			          flag_regNum=0;
			         }
			         
			         document.getElementById("img_xcjcjl").disabled=false;
			   }
			  
	});	
}
</script>
</#escape>
<@fkMacros.pageFooter />