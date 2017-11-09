<@fkMacros.company_pageheader />
<#escape x as (x)!> 
<@enum.initAreaXML/>
<script type="text/javascript">
var checkExt = function(obj) {
	if (!(/(jpg|png|gif)$/i.test(obj.value))) {
		alert("只允许上传jpg、png、gif的文件");
		obj.select();// 选择后清空文件框
		document.selection.clear();
	}
};

function checkForm() {
	var imagefile = document.getElementById("daImageFile").value;
	if (!imagefile) {
		alert("请选择上传的文件！")
		return false;
	}
	return true;
}
</script>
<style type="text/css">
.noborder {
	border: 0;
}

.border {
	border: solid 1px #7f9db9
}

.onborder {
	background-color: #D8DBFE;
	border-top: solid 1px #D8E3EB;
	border-left: solid 1px #D8E3EB;
	border-bottom: solid 1px #0080C0;
	border-right: solid 1px #0080C0;
}

.input {
	height: 24px;
	BORDER-BOTTOM: #DBDBDB 1px solid;
	BORDER-LEFT: #DBDBDB 1px solid;
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	BACKGROUND: #f6f6f6;
	BORDER-TOP: #DBDBDB 1px solid;
	BORDER-RIGHT: #DBDBDB 1px solid;
	PADDING-TOP: 0px;
	-moz-border-radius: 0px;
	-webkit-border-radius: 0px
}

/*弹出上传*/
.main_up {
	margin-top: 10px;
	background: #F6F8FC
}

.dbsy_1 {
	border: solid 2px #C9D7F1;
}
</style>
<body style="background-color:#fff; overflow:scroll;overflow-y:hidden;overflow-x:hidden;"> 
  <div id="window_about_qy">
	<div class="about_tips"><strong>提示：</strong>如果贵企业信息有变动，请及时修改！</div>
	<div class="about_menu">
	   <ul>
	     <li class="selected">单位基本情况</li>
		 <li>安全生产基本状况</li>
	   </ul>
	</div>
	<div class="about_content">
	 <!--单位基本情况内容 开始-->
	   <div>
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" class="about_qy_tab" align="center" style="cursor:default;">
		<tbody>
			<tr>
				<td class="tit" width="88" >单位名称</td>
				<td title='${company.companyName}' width="180" >
					${company.companyName}</td>
				<td width="91" class="tit" >地&nbsp;址</td>
				<td width="191" >
					${company.regAddress}
				</td>
			</tr>
			<tr>
				<td  width="88" class="tit" >所属区域</td>
				<td  width="180" >
					<#if company.firstArea??><@enum.getSelectArea '${company.firstArea}' />&nbsp;&nbsp;</#if>
					<#if company.secondArea??><@enum.getSelectArea '${company.secondArea}' />&nbsp;&nbsp;</#if>
					<#if company.thirdArea??><@enum.getSelectArea '${company.thirdArea}' />&nbsp;&nbsp;</#if>
				</td>
				<td  width="91" class="tit" >所属行业</td>
				<td  width="191" >
					<#if tradeTypes??><#list tradeTypes as t>${t.daIndustryParameter.name}&nbsp;&nbsp;</#list><#else>&nbsp;</#if>
				</td>
			</tr>
			<tr>
				<td class="tit" width="88" >主要负责人</td>
				<td  width="180" >${company.fddelegate?default('&nbsp;')} </td>
				<td  width="91" class="tit">手机号码</td>
				<td  width="191">
					<input id="phoneCode" name="phoneCode" value="${company.phoneCode}" entity="DaCompany" type="text"/>
				</td>
			</tr>
			<tr>
			  <td class="tit" >单位联系电话</td>
			  <td >
			  		<input id="phoneCode" name="phoneCode" value="${company.phoneCode}" entity="DaCompany" type="text" class="noborder"/>
			  </td>
			  <td class="tit">单位传真</td>
			  <td >
			  	    
			  </td>
		    </tr>
			<tr>
				<td  width="88" class="tit" >统一社会信用代码（工商注册号）</td>
				<td  width="180" >
					<input id="regNum" name="regNum" value="${company.regNum}" entity="DaCompany" type="text" class="noborder"/></td>
				<td  width="91" class="tit">组织机构代码</td>
				<td  width="191" >
					<input id="setupNumber" name="setupNumber" value="${company.setupNumber}" entity="DaCompany" type="text" class="noborder"/>
				</td>
			</tr>
			<tr>
				<td  width="88" class="tit" >规模以上<br /> 工业企业</td>
				<td  width="180" >
					<#if company?if_exists.daCompanyPass?if_exists.enterprise?exists && company.daCompanyPass.enterprise>
				    <input type="radio" name="enterprise" value="true" checked="true" entity="DaCompanyPass"/>是
				    <input type="radio" name="enterprise" value="false" entity="DaCompanyPass"/>否
				    <#else>
				    <input type="radio" name="enterprise" value="true" entity="DaCompanyPass"/>是
				    <input type="radio" name="enterprise" value="false" checked="true" entity="DaCompanyPass"/>否
					</#if>
				</td>
				<td  width="91" class="tit" >从业人员人数</td>
				<td  width="191" >
					<input id="employeeNumber" name="employeeNumber" value="${company.employeeNumber}" entity="DaCompany" type="text" class="noborder"/> 人
				</td>
			</tr>
			<tr>
				<td  width="88" class="tit" >是否重点<br />消防单位</td>
				<td  width="180" >
					<input type="radio" name="focusFireUnits" value="1" <#if company?if_exists.focusFireUnits??&&company.focusFireUnits == 1>checked="true"</#if> entity="DaCompany"/>是
	    			<input type="radio" name="focusFireUnits" value="0" <#if company?if_exists.focusFireUnits??&&company.focusFireUnits != 1>checked="true"</#if> entity="DaCompany"/>否   
				</td>
				<td  width="91" class="tit" >经营范围</td>
				<td  width="191" >
					<input id="businessScope" name="businessScope" value="${company.businessScope}" entity="DaCompany" type="text" class="noborder"/>
				</td>
			</tr>
		</tbody>
		</table>
		 <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td width="280" align="center" valign="middle">
		    	<#if imageOne??>
		    	<a href="../${imageOne.path}" class="thickbox"><img src="../${imageOne.path}" width="260" height="170"/></a><br/>
		    		 <a href="#" onclick="javascript:showImageUpload(2,${imageOne.id})">编辑</a> <a href="#" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageOne.id}&daImageFile.daCompany.id=${company.id}'">删除</a>
		    	<#else>
		    		<img src="${resourcePath}/img/nopicture.jpg" width="260" height="170">
		    		<div id="oneOperate" style="font-size:13px;text-align:center;">
		    		 <a href="#" onclick="javascript:showImageUpload(1,-1)">上传</a>
		    		</div>
		    	</#if>
		    </td>
		    <td width="280" align="center" valign="middle">
			    <#if imageTwo??>
			    	<a href="../${imageTwo.path}" class="thickbox"><img src="../${imageTwo.path}" width="260" height="170"/></a><br/>
		    		 <a href="#" onclick="javascript:showImageUpload(2,${imageTwo.id})">编辑</a> <a href="#" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageTwo.id}&daImageFile.daCompany.id=${company.id}'">删除</a>
				<#else>
					<img src="${resourcePath}/img/nopicture.jpg" width="260" height="170">
					<div id="twoOperate" style="font-size:13px;text-align:center;">
		    		 <a href="#" onclick="javascript:showImageUpload(1,-1)">上传</a>
		    		</div>
				</#if>
		  	</td>
		  </tr>
		</table>
	  </div>  
	 <!--安全生产基本状况 开始-->
	 <div class="hide">
	   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="about_qy_tab">
          <tr>
                 <td width="18%" class="tit_bold">安管机构和人员</td>
                 <td width="82%" class="noborder">
                 	<table width="100%" cellspacing="0" cellpadding="0">
	                   <tr>
	                     <td width="22%" class="tit">安全机构<br />设置</td>
	                     <td width="16%">
	                     	<input name="smSet" size="15" value="${(securityManage.smSet)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/></td>
	                     <td width="22%" class="tit" nowrap>专职人员数</td>
	                     <td colspan="3">
	                     	<input name="fulltimeStaff" size="15" value="${(securityManage.fulltimeStaff)?if_exists}" type="text" entity="DaSecurityManage" class="noborder"/> 人</td>
	                   </tr>
	                   <tr>
	                     <td class="tit" nowrap>主要负责人</td>
	                     <td>
	                     	<input name="mainPrincipal" size="8" value="${(securityManage.mainPrincipal)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/>
	                     </td>
	                     <td class="tit">证书号</td>
	                     <td width="18%">
	                     	<input name="mpCertificate" size="8" value="${(securityManage.mpCertificate)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/>
	                     </td>
	                     <td width="22%" class="tit">有效期</td>
	                     <td width="19%">
	                     	<input name="mpValidity" size="8" value="${(securityManage.mpValidity)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/>
	                     </td>
	                   </tr>
	                   <tr>
	                     <td class="tit">安全负责人</td>
	                     <td>
	                     	<input name="securityPrincipal" size="8" value="${(securityManage.securityPrincipal)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/>
	                     </td>
	                     <td class="tit">证书号</td>
	                     <td>
	                     	<input name="spCertificate" size="8" value="${(securityManage.spCertificate)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/>
	                     </td>
	                     <td class="tit">有效期</td>
	                     <td>
	                     	<input name="spValidity" size="8" value="${(securityManage.spValidity)?if_exists}" type="text" class="noborder" entity="DaSecurityManage"/>
	                     </td>
	                 </tr>
	              </table>
	          </td>
           </tr>
           <tr>
             <td class="tit_bold"><span class="tit">安全生产许可证</span></td>
             <td class="noborder">
	             <table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
				      <td width="94" height="28" align="center" class="tit">种类</td>
				      <td width="67" align="center" class="td3" id="category">&nbsp;</td>
				      <td width="50" align="center" class="tit">证书号</td>
				      <td width="146" align="center" class="td3" id="licence">&nbsp;</td>
				      <td width="44" align="center" class="tit">有效期</td>
				      <td width="70" align="center" id="certEnd">&nbsp;</td>
				    </tr>
	               <tr>
	                 <td class="tit" style="line-height:36px;">许可范围</td>
	                 <td colspan="5" style="position:relative;">
				<textarea id="chinaName" style="height:30px;" cols=62 name="textarea"></textarea>
	                 	<div style="position:absolute; right:15px; top:0;">
					    <ul>
						  <li style="margin-top:3px;" ><img src="${resourcePath}/icon/icon_jia.jpg" class="bigger" style="cursor:pointer;" title="增加高度"></li>
						  <li style="margin-top:3px;" ><img src="${resourcePath}/icon/icon_jian.jpg" class="smaller" style="cursor:pointer;" title="减少高度"></li>
						</ul>
					 </div>
	                 </td>
	               </tr>
	             </table>
	         </td>
          </tr>
          <tr>
                 <td class="tit_bold">重大危险源</td>
                 <td class="noborder">
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                   <tr>
	                     <td width="14%" class="tit">备案情况</td>
	                    <td width="86%" align="center" id="isMajorDangerousSource">未备案&nbsp;</td>
	                   </tr>
	                 </table>
	             </td>
          </tr>
         </table>
         <iframe width="100%" align="center" height="38" id="iframe_standards" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src="../map/loadNullStandard.xhtml"></iframe>
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
		      <td height="1" bgcolor="DBDBDB"></td>
		    </tr>
		  </table>
		  <iframe id="iframe_succor" align="center" width="100%" height="38" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src="http://60.190.2.104:8082/succor/manager/manager_view.xhtml?regNum=${company.regNum}"></iframe>
		  <iframe id="iframe_nbyhpc" align="center" width="100%" height="34" frameborder=0 scrolling=NO src="../danger/loadDangercount.xhtml?company.id=${company.id}"></iframe>
		  <iframe id="iframe_nbxzzf" align="center" width="100%" height="34" frameborder=0 scrolling=NO src="../companyPunishment/loadCompanyPunishmentsinfo.xhtml?company.id=${company.id}"></iframe>
         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="about_qy_tab">
			<tr>
           		 <td width="18%" class="tit_bold">事故情况</td>
                 <td width="82%" class="noborder">
	           		 <iframe id="iframe_nbsg" align="center" width="100%" height="28" marginwidth=0 marginheight=0 frameborder=0 scrolling=NO src=""></iframe>
	             </td>
          </tr>
               <tr>
                 <td class="tit_bold">信用管理</td>
                 <td class="noborder">
	                 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						  <tr>
						    <td width="29%" height="28" align="center"  class="tit">信用等级</td>
						    <td width="21%" align="center">${creditManage.grade}&nbsp;</td>
						    <td width="29%" align="center"  class="tit">承诺书</td>
						    <td width="21%" align="center"><#if creditManage?? && creditManage.promise??><a href="${(creditManage.promise)?if_exists}"><img src="${resourcePath}/images/download.gif" width="16" height="16" style="cursor:pointer;" title="点击下载"/></a><#else></#if></td>
						  </tr>
						  <tr>
						    <td height="28" align="center"  class="tit">公示</td>
						    <td align="center"><#if creditManage?? && creditManage.publicity??><a href="${(creditManage.publicity)?if_exists}"><img src="${resourcePath}/images/download.gif" width="16" height="16" style="cursor:pointer;" title="点击下载"/></a><#else></#if></td>
						    <td align="center"  class="tit">年度报告</td>
						    <td align="center"><#if creditManage?? && creditManage.yearReport??><a href="${(creditManage.yearReport)?if_exists}"><img src="${resourcePath}/images/download.gif" width="16" height="16" style="cursor:pointer;" title="点击下载"/></a><#else></#if></td>
						  </tr>
					</table>
	             </td>
          </tr>
        </table>
	 </div>
	 <!--安全生产基本状况 结束-->
	 <div id="uploadImageDiv" style="position:absolute; left:500px; display:none; left: 150px; top: 300px; font-size:13px;">
		<form method="post" id="imageUploadForm" action="#" enctype="multipart/form-data" onsubmit="return checkForm();">
		<input type="hidden" name="daImageFile.daCompany.id" value="${company.id}" />
		  <table width="280" border="0" cellpadding="0" cellspacing="0" align="center" class="main_up">
			  <tr>
			    <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="dbsy_1" height="96">
			      <tr>
			        <td valign="top" align="center" style="padding:1px 0px"><table width="94%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td height="30" align="left" valign="bottom">图片上传：</td>
			          </tr>
			          <tr>
			            <td height="30" align="left" valign="middle">
			            <input type="file" id="daImageFile" name="daImageFile.file" size="34" contentEditable="false" onchange="checkExt(this)" />
			            </td>
			          </tr>
			          <tr>
			            <td height="30" align="center" valign="middle">
			            	<input value="上传" type="submit" name="btnSubmit" style="background:url('${resourcePath}/img/map/bg1.gif');width:45px;height:20px;padding:0px;margin:0px;border:0px;" />&nbsp;
			      			<input value="取消" type="button" name="btnCancel" style="background:url('${resourcePath}/img/map/bg1.gif');width:45px;height:20px;padding:0px;margin:0px;border:0px;" onclick="javascript:hideImageUpload()" />	
						</td>
			          </tr>
			        </table></td>
			      </tr>
			    </table></td>
			  </tr>
			</table>
		 </form>
	</div>
  </div>
<script language="javascript"> 
$(document).ready(function(){ 
	var inputVal = "";
	$('input:text').focus(function(){ 
		inputVal = $(this).val();
	}); 
	$('input:text').blur(function(){ 
		var entity = $(this).attr("entity");//获取对象的名称
		var name = $(this).attr("name");//获取input的名称
		var id = "";
		if (entity == 'DaCompany') {
			id = "${company.id}"; 
		}else {
			id = "${securityManage.id}";
		}
		if(inputVal != $(this).val()){
			$.ajax({
				type:"POST",
				url:"${base}/ajax/ajax_update.xhtml",
				data:"tableName="+ entity +"&id="+id+"&value="+ $(this).val() + "&fieldName="+name,
				//async:false,
				success:function(msg){
					if(!eval(msg)){
					alert("保存失败！");
					window.location.reload();
				}
				}
			});
		}
	}); 
	$('input:radio').click(function(){ 
		var entity = $(this).attr("entity");//获取对象的名称
		var name = $(this).attr("name");//获取input的名称
		$.ajax({
			type:"POST",
			url:"${base}/ajax/ajax_update.xhtml",
			data:"tableName="+ entity +"&id=${company.id}&value="+ $(this).val() + "&fieldName="+name,
			//async:false,
			success:function(msg){
				if(!eval(msg)){
					alert("保存失败！");
					window.location.reload();
				}
			} 
		});
	}); 

	$('input:text').mouseover(function(){ 
		$(this).addClass('onborder'); //onborder
	}); 

	$('input:text').mouseout(function(){ 
		$(this).removeClass('onborder'); 
	}); 
}); 
</script>
<script type="text/javascript">
	function loadStandardOldAdvance(url,method){
		jQuery.ajax({
		   	url: url,
		   	data: "businessRegNum=${company.regNum}&method=" + method,
		   	type: "POST",
		   	dataType:"json",
		   	success: function(data){ 
		   
		   		if(data.secondForm != null){
		   			$("#category").html('乙证');
		   			$("#licence").html(data.secondForm.licence);
		   			$("#certEnd").html(data.secondForm.certEnd);
		   			if(data.licenceScope != null){
			   			$("#chinaName").val(data.licenceScope.chinaName);
			   		}
			   		return;
		   		}

		   		if(data.companyInfo != null && data.companyInfo.licence != null){
		   			$("#category").html('甲证');
		   			$("#licence").html(data.companyInfo.licence);
		   			$("#certEnd").html(data.companyInfo.certEnd);
		   			$("#chinaName").val(data.companyInfo.chinaName);
		   			return;
		   		}
		   		if(data.companyInfo3 != null && data.companyInfo3.licence != null){
		   			$("#category").html('生产许可证');
		   			$("#licence").html(data.companyInfo3.licence);
		   			$("#certEnd").html(data.companyInfo3.certEnd);
		   			$("#chinaName").val(data.companyInfo3.chinaName);
		   			return;
		   		}
	   		}
		});
	}
//	loadStandardOldAdvance('http://127.0.0.1/nbwhp/second/newsecond.do', 'loadSecondByBgnAjax');
//	loadStandardOldAdvance('http://127.0.0.1/whp/proLicense/proLicense.do', 'loadProLicenseByBgnAjax');
	
	function loadRedirectUrl(id,siteId,url,method) {
	jQuery.ajax({
			   	url: "http://127.0.0.1:8080/nbajj_colligation/siteIface/loadRedirectUrl.xhtml",
			   	data: "siteId=" + siteId,
			   	type: "POST",
			   	dataType:"json",
			   	success: function(data){ 
			   
			   		data = data.replace(/\\/g,""); 
			   		url += "?method=" + method + "$businessRegNum=${company.regNum}";
			   		alert( data + "&url='" + url + "'");
					document.getElementById(id).src = data + "&url='" + url + "'";
		   		}
			});
	}
	//loadRedirectUrl('iframe_nbsg',6,'nbsg/up/sgqk.do','count');
	//loadRedirectUrl('iframe_whp',8,'whp/pla/pla.do','');
	
	//id:iframe的id
	function loadStandardOld(id,url,method){
		jQuery.ajax({
		   	url: url,
		   	data: "businessRegNum=${company.regNum}&method=" + method,
		   	type: "POST",
		   	dataType:"json",
		   	success: function(data){ 
		   	alert(data);
		   		if(data != null){
		   			url += "?businessRegNum=${company.regNum}&method=loadSelfcheckByBgn";
		   			alert(url);
					document.getElementById(id).src =url;
		   		}
	   		}
		});
	}
	//id:iframe的id
	function loadStandardNew(id,url){
		jQuery.ajax({
		   	url: url,
		   	data: "company.businessRegNum=${company.regNum}",
		   	type: "POST",
		   	dataType:"json",
		   	success: function(data){ 
		   		if(data != null){
			   		url = "http://127.0.0.1:8085/standard/company/loadCompanyStandard.xhtml";
	   				url += "?company.businessRegNum=${company.regNum}";
					document.getElementById(id).src =url;
				}
	   		}
		});
	}
	loadStandardOld('iframe_standards','http://127.0.0.1/jxbzh2/nselfcheck/nselfcheck.do', 'loadSelfcheckByBgnAjax');
	//loadStandardOld('iframe_standards','http://http://60.190.2.104:8083/wlevel/nselfcheck/nselfcheck.do', 'loadSelfcheckByBgnAjax');
	//loadStandardNew('iframe_standards','http://127.0.0.1:8085/standard/company/loadCompanyStandardAjax.xhtml');
	function loadZdwxyInfo(companyName){
	$.get("http://127.0.0.1:8080/nbajj_colligation/statistic/isMhStatistic.xhtml",{mc: companyName},  function(result)
			  {
			  	alert("dddd");
			  	var isMajorDangerousSource=document.getElementById("isMajorDangerousSource");
				if (result == true) {
					isMajorDangerousSource.innerHTML="已备案";
				} else {
					isMajorDangerousSource.innerHTML="未备案";
				}
			  });
	}
	//window.onload=loadZdwxyInfo('${company.companyName}');
	//tag:1 上传 2：编辑
	function showImageUpload(tag, id){
		$("#uploadImageDiv").show();
		if(tag == 1){
			$("#imageUploadForm").attr("action", "../imageFile/createFile.xhtml");;
		}else{
			$("#imageUploadForm").attr("action", "../imageFile/updateFile.xhtml?daImageFile.id="+id);
		}
	}
	function hideImageUpload(){
		$("#uploadImageDiv").hide();
	}
	
</script>
<script type="text/javascript">
					$(function(){
						var $comment = $('#chinaName');  //获取许可范围文本框
						$('.bigger').click(function(){ //放大按钮绑定单击事件
						   if(!$comment.is(":animated")){ //判断是否处于动画
							  if( $comment.height() < 200 ){ 
									$comment.animate({ height : "+=30" },400); //重新设置高度，在原有的基础上加50
							  }
							}
						})
						$('.smaller').click(function(){//缩小按钮绑定单击事件
						   if(!$comment.is(":animated")){//判断是否处于动画
								if( $comment.height() > 50 ){
									$comment.animate({ height : "-=30" },400); //重新设置高度，在原有的基础上减50
								}
							}
						});
					});
					</script>

</#escape> 
<@fkMacros.pageFooter />