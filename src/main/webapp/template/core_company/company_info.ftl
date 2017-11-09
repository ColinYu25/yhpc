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
<script type="text/javascript">
var enumObj=new Enum("${resourcePath}/js/qyda_enum.xml");//企业档案枚举
var xzxkRow = 0;//行政许可行
var bzhRow = 0;//标准化行
$(document).ready(function(){ 

	var inputVal = "";
	$('input:text').focus(function(){
		inputVal = $(this).val();
	}); 

	//不需要变换就更新的ID控件
	var noUp=new Array();
	noUp.push("xkType");
	noUp.push("licence");
	noUp.push("validityEnd");
	noUp.push("permitScope");
	noUp.push("bzhType");
	noUp.push("bzh_licence");
	noUp.push("bzh_validityEnd");
	noUp.push("bzhGrade");
	function isNoUp(id){
		for(var i = 0 ; i < noUp.length;i++){
			if(id==noUp[i]){
				return true;
			}
		}
		return false;
	}
	
	var handler = function() {
		var elementId = $(this).attr("id");
		if(isNoUp(elementId)){
			return false;
		}
		//更新区域
		if (elementId == 'second-area' || elementId == 'third-area') {
			if (elementId == 'second-area') {
				loadThirdAreas($(this),'');
			} else if(elementId == 'third-area'){
				loadFouthAreas($(this),'');
			}
		}
		
		var entity = $(this).attr("entity");//获取对象的名称
		var id = (entity == 't_core_company')?'${map["company_id"]}':'${map["sem_id"]}';
		var fieldName = $(this).attr("name");//获取input的名称
		var value = $(this).val();
		$.ajax({type : "post", method : "post", dataType : "json",
			data:{"map['entity']": entity, "map['fieldName']": fieldName, "map['id']": id, "map['value']": value},
			url : "updateSqlVal.xhtml",
			success : function(data) {//异步获取的数据 
				if(!(data && data.result)){
					alert("保存失败,请确认您输入的信息是否正确！");
				}
			}
		});	
	}
	//文本框
	$('input:text').focusout(handler);
	
	//单选按钮
	$('input:radio').click(handler); 
	
	//下拉单
	$('select').focusout(handler);
	
	$('input:text').mouseover(function(){ 
		$(this).addClass('onborder'); //onborder
	}); 

	$('input:text').mouseout(function(){ 
		$(this).removeClass('onborder'); 
	}); 
	//添加许可
	$("#addXzxk").click(function(){
		var company_id = '${map["company_id"]}';
		var xkType = $("#xkType").val();
		var licence = $("#licence").val();
		var validityEnd = $("#validityEnd").val();
		var permitScope = $("#permitScope").val();
		if(xkType==''||xkType=='0'){
			alert("请选择许可证种类");
			return false;
		}
		if(licence==''){
			alert("请填写许可证号");
			return false;
		}
		if(validityEnd==''){
			alert("请填写有效期");
			return false;
		}else if(!isDate(validityEnd)){
			return false;
		}
		if(permitScope==''){
			alert("请填写经营范围");
			return false;
		}
		$.ajax({
			type:"post",
			method:"post",
			dataType:"json",
			data:{"map['company_id']":company_id,"map['xkType']":xkType,"map['licence']":licence,"map['validityEnd']":validityEnd,"map['permitScope']":permitScope},
			url:"ajaxAddXzxk.xhtml",
			success:function(data){
				if(data && data.result){
					alert("保存成功！");
					$("#xkType").val("0");
					$("#licence").val("");
					$("#validityEnd").val("");
					$("#permitScope").val("");
					addXzxktable(data.id,xkType,licence,validityEnd,permitScope);
				}else{
					alert("保存失败！");
				}
			}
		});
	});
	
	//许可添加成功后表格添加一行
	function addXzxktable(id,xkType,licence,validityEnd,permitScope){
		var xzxkTable = $("#xzxkTable");
		xzxkTable.append("<tr><td width=\"8%\" class=\"center\">"+(xzxkRow+1)+"</td><td width=\"25%\" class=\"center\">"+enumObj.getSelect(xkType)+"</td>"
					+"<td width=\"20%\" class=\"center\">"
					+"<div title=\""+licence+"\" style=\"width:80px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;\">"+licence+"<\div>"
					+"</td><td width=\"17%\" class=\"center\">"+checkDate(validityEnd)+"</td>"
           			+"<td width=\"20%\" class=\"center\">"
           			+"<div title=\""+permitScope+"\" style=\"width:80px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;\">"+permitScope+"<\div>"
           			+"</td><td width=\"10%\" class=\"center\" >"
           			+"<img src=\"${resourcePath}/img/del_qyhkb_icon.jpg\" width=\"16\" height=\"16\" onClick=\"delXzxkRow(this,"+id+");\" style=\"cursor:pointer;\"/></td></tr>");
           			
        /*   			
        var xzxkDiv = $("#xzxkDiv");
        xzxkDiv.append("<table width=\"100%\" border=\"0\"><tr><td rowspan=\"2\" width=\"4%\" nowrap>"+(xzxkRow+1)+"</td>"
        				+"<td width=\"8%\" class=\"tit\" nowrap>种类</td><td width=\"24%\">"+enumObj.getSelect(xkType)+"</td>"
                         +"<td width=\"10%\" class=\"tit\" nowrap>证书号</td><td width=\"25%\"><div title=\""+licence+"\" style=\"width:100px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;\">"+licence+"<div></td>"
                         +"<td width=\"10%\" class=\"tit\" nowrap>有效期</td><td width=\"17%\">"+checkDate(validityEnd)+"</td></tr>"
                       +"<tr><td class=\"tit\">范围</td><td colspan=\"4\"><div title=\""+permitScope+"\" style=\"width:235px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;\">"+permitScope+"<div></td>"
	                   +"<td class=\"center\"><img title=\"删除\" src=\"${resourcePath}/img/qyda_close.jpg\" width=\"16\" height=\"16\" onClick=\"delXzxkRow(this,"+id+");\" style=\"cursor:pointer;\"/></td></tr></table>");
	   	$("#add_xkz_form").slideUp(500);
	   	*/
       	xzxkRow++;
	}
	
	//添加标准化
	$("#addBzh").click(function(){
		var company_id = '${map["company_id"]}';
		var bzhType = $("#bzhType").val();
		var licence = $("#bzh_licence").val();
		var validityEnd = $("#bzh_validityEnd").val();
		var bzhGrade = $("#bzhGrade").val();
		if(bzhType==''||bzhType=='0'){
			alert("请选择标准化种类");
			return false;
		}
		if(licence==''){
			alert("请填写证书号");
			return false;
		}
		if(validityEnd==''){
			alert("请填写有效期");
			return false;
		}else if(!isDate(validityEnd)){
			return false;
		}
		if(bzhGrade==''){
			alert("请选择等级");
			return false;
		}
		$.ajax({
			type:"post",
			method:"post",
			dataType:"json",
			data:{"map['company_id']":company_id,"map['bzhType']":bzhType,"map['licence']":licence,"map['validityEnd']":validityEnd,"map['bzhGrade']":bzhGrade},
			url:"ajaxAddBzh.xhtml",
			success:function(data){
				if(data && data.result){
					alert("保存成功！");
					$("#bzhType").val("0");
					$("#bzh_licence").val("");
					$("#bzh_validityEnd").val("");
					$("#bzhGrade").val("0");
					addBzhtable(data.id,bzhType,licence,validityEnd,bzhGrade);
				}else{
					alert("保存失败！");
				}
			}
		});
	});
	
	//标准化添加成功后表格添加一行
	function addBzhtable(id,bzhType,licence,validityEnd,bzhGrade){
		var bzhTable = $("#bzhTable");
		bzhTable.append("<tr><td width=\"8%\" class=\"center\">"+(bzhRow+1)+"</td><td width=\"27%\" class=\"center\">"+enumObj.getSelect(bzhType)+"</td>"
					+"<td width=\"20%\" class=\"center\">"
					+"<div title=\""+licence+"\" style=\"width:80px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;\">"+licence+"<\div>"
					+"</td><td width=\"15%\" class=\"center\" >"+enumObj.getSelect(bzhGrade)+"</td>"
           			+"<td width=\"18%\" class=\"center\">"+checkDate(validityEnd)+"</td><td width=\"12%\" class=\"center\" >"
           			+"<img src=\"${resourcePath}/img/del_qyhkb_icon.jpg\" width=\"16\" height=\"16\" onClick=\"delBzhRow(this,"+id+");\" style=\"cursor:pointer;\"/></td></tr>");
       	bzhRow++;
        $("#add_bzh_form").slideUp(500);
	}
	
}); 

	//删除许可
	function delXzxkRow(obj,id){
		if(confirm("确定删除！")){
			$.ajax({
				type:"post",
				method:"post",
				dataType:"json",
				data:{"map['id']":id},
				url:"ajaxDelXzxk.xhtml",
				success:function(data){
					if(data && data.result){
						alert("删除成功!")
						//$(obj).parent().parent().parent().remove();
						$(obj).parent().parent().remove();
					}else{
						alert("删除失败!")
					}
				}
			});
		}
	}
	
	//删除标准化
	function delBzhRow(obj,id){
		if(confirm("确定删除！")){
			$.ajax({
				type:"post",
				method:"post",
				dataType:"json",
				data:{"map['id']":id},
				url:"ajaxDelBzh.xhtml",
				success:function(data){
					if(data && data.result){
						alert("删除成功!")
						$(obj).parent().parent().remove();
					}else{
						alert("删除失败!")
					}
				}
			});
		}
	}
	
	//级联  子下拉框  v为父的值，id为子的控件id
	function nextSelect(v,id){
		if(v&&v!=''&&v!='0'){
			enumObj.initSelect(v,id);
		}
	}
	
	//判断是否是时间格式
	function isDate(v){
		if(v=="")return true;
	  	var r=v.match(/^\d{4}-\d{2}-\d{2}$/); 
	    if(r==null){
	   		alert("请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：1900-01-01\n\r");
	  		return false;
	  	}
	  	return true;
	}
	
	//是否过期
	function checkDate(certDate){
		var now = new Date();
		var ds= certDate.replace(/-/g, "/"); 
		var dt = new Date(ds);
		if(now.getTime()>dt.getTime()){
			return "<font color=\"red\">"+certDate+"</font>";
		}else{
			return certDate;
		}
	}
</script>
<style type="text/css">
<!--
.red {
	color: #F00;
}
-->
</style>
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
<body style="background-color:#fff;>
   <div id="window_about_qy"  >

	<div class="about_tips"><strong>提示：</strong>页面无保存按钮,修改内容后鼠标移开即保存！带&nbsp;<span class="red">*</span>&nbsp;的为必填项</div>
	<div class="about_menu">
	   <ul>
	     <li class="selected">单位基本情况</li>
		 <li>安全生产基本状况</li>
	   </ul>
	</div>
	<div class="about_content">
	
	 <!--单位基本情况内容 开始-->
	   <div>
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" class="about_qy_tab"align="center" style="cursor:default;">
		<tbody>
			<tr>
				<td  class="tit" width="98" ><span class="red">*</span>单位名称</td>
				<td colspan="2"  title='${map["company_name"]}' ><input name="company_name"
				class="noborder" id="company_name" value="${map["company_name"]}" size="32" maxlength="100" entity="t_core_company" /></td>
				<td    width="102" class="tit" ><span class="red">*</span>注册地址</td>
				<td colspan="2"  title='${map["reg_address"]}' ><input name="reg_address"
				class="noborder" id="reg_address" value="${map["reg_address"]}" size="18" maxlength="100" title="许可类企业注册地址不可编辑" entity="t_core_company" />
				</td>
			</tr>
			<tr>
				<td  width="98" class="tit" title="主要经营场所所在区域"><span class="red">*</span>经营场所<br />区域</td>
				<td  width="114" >
                	<select id="second-area" name="second_area" entity="t_core_company" style="width:100px;">
			        	<option value="0">--请选择--</option>
			        	<#if childAreas??>
			        		<#list childAreas as s>
				    			<option value="${s.areaCode}">${s.shortName?default(s.areaName)}</option>
				    		</#list>
				    	</#if>
			        </select></td>
				<td  width="102" >
					<select name="third_area" entity="t_core_company" id="third-area" style="width:100px;">
			    		<option value="0">－请选择－</option>
			    	</select>
				</td>
				<td>
					<select name="fouth_area" entity="t_core_company" id="fouth-area" style="width:100px;">
			    		<option value="0">－请选择－</option>
			    	</select>
				</td>
				<td  width="60" >邮编</td>
				<td  width="174" ><input name="zip_code"
				class="noborder" id="zip_code" value="${map["zip_code"]}" size="8" maxlength="6" entity="t_core_company" /></td>
			</tr>
			<tr>
				
				<td width="98" rowspan="2" class="tit" ><span class="red">*</span>行业划分</td>
				<td >国民经济分类一级</td>
				<td ><select style="width:100px;" name="na_eco_level1" id="na_eco_level1" entity="t_core_company" onChange="nextSelect(this.value,'na_eco_level2');">
					<option value="0">--请选择--</option>
				  <option>制造业</option>
                </select></td>
				<td class="tit">二级</td>
				<td colspan="2" ><select style="width:120px;" name="na_eco_level2" id="na_eco_level2" entity="t_core_company">
					<option value="0">--请选择--</option>
			    </select></td>
			</tr>
			<tr>
			  <td >管理分类一级</td>
			  <td ><select style="width:100px;" name="management_level1" id="management_level1" entity="t_core_company" onChange="nextSelect(this.value,'management_level2');">
			  	<option value="0">--请选择--</option>
              </select></td>
			  <td class="tit">二级</td>
			  <td colspan="2" ><select style="width:120px;" name="management_level2" id="management_level2" entity="t_core_company">
			  	<option value="0">--请选择--</option>
              </select></td>
          </tr>
		 <tr>
			  <td class="tit"><span class="red">*</span>经济类型</td>
			  <td ><select style="width:100px;" name="economic_type1" id="economic_type1" entity="t_core_company" onChange="nextSelect(this.value,'economic_type2');">
			  	<option value="0">--请选择--</option>
              </select></td>
			  <td ><select style="width:100px;" name="economic_type2" id="economic_type2" entity="t_core_company" style="width:100px;">
			  	<option value="0">--请选择--</option>
              </select></td>
			  <td class="tit"><span class="red">*</span>成立时间</td>
			  <td colspan="2"><input id="establishment_day" class="noborder"  value="${map["establishment_day"]?date}" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});" size="15" name="establishment_day" entity="t_core_company" 
			  style="ime-mode:disabled"  maxlength="10" title="格式如：1900-01-01"/></td>
		    </tr>
			<tr>
				<td  width="98" class="tit" nowrap><span class="red">*</span>统一社会信用代码（工商注册号）</td>
				<td colspan="2" title='${map["business_reg_num"]}'><input id="business_reg_num" class="noborder" value="${map["business_reg_num"]}" name="business_reg_num" size="15" maxlength="25" entity="t_core_company" /></td>
				<td class="tit" ><span class="red">*</span>组织机构代码</td>
				<td colspan="2" title='${map["org_code"]}'><input id="org_code" 
				class="noborder" name="org_code" value="${map["org_code"]}" size="15" maxlength="25" entity="t_core_company" /></td>
			</tr>
			<tr>
			  <td class="tit" ><span class="red">*</span>注册资金</td>
			  <td colspan="2" title='${map["reg_capital"]}'><input id="reg_capital" 
				class="noborder" name="reg_capital" value="<#if map["reg_capital"]??&&map["reg_capital"]!=0>#{map["reg_capital"];m1M1}</#if>" size="15" maxlength="8" style="ime-mode:disabled;" entity="t_core_company" />万</td>
			  <td class="tit" ><span class="red">*</span>年销售额</td>
			  <td colspan="2"><input id="year_sales"
				class="noborder" name="year_sales" value="<#if map["year_sales"]??&&map["year_sales"]!=0>#{map["year_sales"];m1M1}</#if>" size="15" maxlength="8" style="ime-mode:disabled;" entity="t_core_company" />万</td>
		  </tr>
			<tr>
				<td  width="98" class="tit" ><span class="red">*</span>法定代表人</td>
				<td colspan="2" title='${map["legal_person"]}'><input id="legal_person" 
				class="noborder" name="legal_person" value="${map["legal_person"]}" size="15" maxlength="50" entity="t_core_company" /></td>
				<td class="tit" >占地面积</td>
				<td colspan="2" title='${map["floor_area"]}'><input id="floor_area" 
				class="noborder" name="floor_area" value="<#if map["floor_area"]??&&map["floor_area"]!=0>#{map["floor_area"];m1M1}</#if>" size="15" maxlength="8" style="ime-mode:disabled;" entity="t_core_company" />㎡</td>
			</tr>
			<tr>
				<td  width="98" rowspan="2" class="tit" ><span class="red">*</span>主要负责人</td>
				<td class="tit" >姓名</td>
			  <td title='${map["principal_person"]}'><input id="principal_person" 
				class="noborder" name="principal_person" value="${map["principal_person"]}" size="15" maxlength="50" entity="t_core_company" /></td>
				<td class="tit" >手机号码</td>
				<td colspan="2" title='${map["principal_mobile"]}'><input id="principal_mobile" 
				class="noborder" name="principal_mobile" value="${map["principal_mobile"]}" style="ime-mode:disabled;" size="15" maxlength="11" entity="t_core_company" /></td>
			</tr>
			<tr>
			  <td class="tit" >联系电话</td>
			  <td colspan="4" title='${map["principal_telephone"]}'><input id="principal_telephone" 
				class="noborder" name="principal_telephone" value="${map["principal_telephone"]}" style="ime-mode:disabled;" size="15" maxlength="13" entity="t_core_company" /></td>
		  </tr>
			<tr>
			  <td class="tit" >单位传真</td>
			  <td colspan="2" title='${map["fax"]}'><input id="fax" 
				class="noborder" name="fax" value="${map["fax"]}" style="ime-mode:disabled;" size="15" maxlength="13" entity="t_core_company" /></td>
			  <td class="tit" nowrap><span class="red">*</span>规模以上<br />工业企业</td>
			  <td colspan="2" ><input type="radio" name="is_enterprise" value="true" 
<#if map["is_enterprise"]?? && map["is_enterprise"]>checked="true"</#if> entity="t_core_company"/> 是　
					    <input type="radio" name="is_enterprise" value="false"
<#if map["is_enterprise"]?? && !map["is_enterprise"]>checked="true"</#if> entity="t_core_company"/> 否</td>
		  </tr>
			<tr>
			  <td class="tit" ><span class="red">*</span>重点消防<br />单位</td>
			  <td colspan="2" ><input type="radio" name="is_focus_fire_units" value="true" 
<#if map["is_focus_fire_units"]?? && map["is_focus_fire_units"]>checked="true"</#if> entity="t_core_company" />
			    是　<input type="radio" name="is_focus_fire_units" value="false" 
<#if map["is_focus_fire_units"]?? && !map["is_focus_fire_units"]>checked="checked"</#if> entity="t_core_company" />
              否</td>
			  <td class="tit" >乡镇(街道)<br />
		      重点监管单位</td>
			  <td colspan="2" ><input type="radio" name="is_focus_town_units" value="true" 
<#if map["is_focus_town_units"]?? && map["is_focus_town_units"]>checked="true"</#if> entity="t_core_company" />
是　
  <input type="radio" name="is_focus_town_units" value="false"
<#if map["is_focus_town_units"]?? && !map["is_focus_town_units"]>checked="true"</#if>  entity="t_core_company" />
否</td>
		  </tr>
			<tr>
			  <td class="tit" >从业人员</td>
			  <td colspan="2" ><input id="employee_num" 
				class="noborder" name="employee_num" value="${map["employee_num"]}" style="ime-mode:disabled;" size="15" maxlength="7" entity="t_core_company" />人</td>
			  <td class="tit" ><span class="red">*</span>工伤保险<br />参保人数</td>
			  <td colspan="2" ><input id="insured_num" 
				class="noborder" name="insured_num" value="${map["insured_num"]}" style="ime-mode:disabled;" size="15" maxlength="7" entity="t_core_company" />人</td>
		  </tr>
			<tr>
			  <td class="tit" ><span class="red">*</span>经营范围</td>
			  <td colspan="5" ><input id="business_scope" 
				class="noborder" name="business_scope" value="${map["business_scope"]}" size="80" maxlength="500" entity="t_core_company" /></td>
		  </tr>
		</tbody>
		</table>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td height="24" align="center"><span class="red">*</span>厂区大门<br /></td>
			  <td>&nbsp;</td>
			  <td height="24" align="center"><span class="red">*</span>主要作业场所</td>
			</tr>
			<tr>
			  <td align="center">
			  <#if imageOne??>
			  	<a href="../${imageOne.path}" class="thickbox"><img src="${contextPath}${imageOne.path}" width="260" height="195"/></a>
			  <#else>
			  	<img src="${resourcePath}/img/nopicture.jpg" width="260" height="195"/>
			  </#if></td>
			  <td align="center">&nbsp;</td>
			  <td align="center"><#if imageTwo??>
			  <a href="../${imageTwo.path}" class="thickbox"><img src="${contextPath}${imageTwo.path}" width="260" height="195"/></a>
			  <#else>
			  <img src="${resourcePath}/img/nopicture.jpg" width="260" height="195"/>
			  </#if></td>
			</tr>
			<tr>
			  <td height="30" align="center">
				  <#if imageOne??>
					  <input name="edit" type="button" class="btn" value="编 辑" onclick="javascript:showImageUpload(2,${imageOne.id},1)"/>
		              <input name="delete" type="button" class="btn" value="删 除" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageOne.id}&daImageFile.daCompany.id=${coreCompany.id}'"/>
	              <#else>
	              	<input name="up" type="button" class="btn" value="上 传" onclick="javascript:showImageUpload(1,-1,1)"/>
	              </#if>
              </td>
			  <td>&nbsp;</td>
			  <td align="center">
			  	  <#if imageTwo??>
					  <input name="edit" type="button" class="btn" value="编 辑" onclick="javascript:showImageUpload(2,${imageTwo.id},2)"/>
		              <input name="delete" type="button" class="btn" value="删 除" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageTwo.id}&daImageFile.daCompany.id=${coreCompany.id}'"/>
	              <#else>
	              	<input name="up" type="button" class="btn" value="上 传" onclick="javascript:showImageUpload(1,-1,2)"/>
	              </#if>
			  </td>
		    </tr>
	     </table>
	  </div>  
	 <!--单位基本情况内容 结束-->
	 <!--安全生产基本状况 开始-->
	 <div class="hide">
               
<script type="text/javascript">
   $(function(){
		  //点击标题单元格，右侧内容展开与收起
		  $(".tit_bold").toggle(function () {
		  var $this = $(this)
		  var $div = $(this).next().children("div")
		  $div.attr("name", $div.height()).slideUp(500, function () { if ($this.next().children(".msg").show().length == 0) { $this.next().append("<span class='msg'>再次点击展开</span>"); } });
		  
		  }, function () {
		  var $this = $(this)
		  var $next = $this.next();
		  $this.next().children(".msg").hide();
		  $next.children("div").height($next.children().attr("name")).slideDown(500, function () { $next.children().css("height", "auto") });
		  });
 
            
		  //鼠标经过变色
		  $(".tit_bold").hover(
		  function () {
		    $(this).addClass("tit_bold_hover");
		  },
		  function () {
		    $(this).removeClass("tit_bold_hover");
		  });
	
		  //许可证-新增显示
		  $("#add_xkz_btn").click(function(){
		    $("#add_xkz_form").slideDown(500);
		  });
		  //许可证-关闭消失
		  $("#close_xkz_btn").click(function(){
		    $("#add_xkz_form").slideUp(500);
		  })	 
		  
		  //标准化-新增显示
		  $("#add_bzh_btn").click(function(){
		     $("#add_bzh_form").slideDown(500);
		  });
		  
		  //标准化-关闭消失
		  $("#close_bzh__btn").click(function(){
		   $("#add_bzh_form").slideUp(500);
		  })
  });
       </script>
 
	   <table width="100%" cellspacing="0" cellpadding="0" class="about_qy_tab">
          <tr>
            <td class="tit_bold"><span class="red">*</span>安管机构</td>
            <td class="noborder">
              <div class="content_table">
               <input type="radio" name="have_security_org" value="true" 
<#if map["have_security_org"]?? && map["have_security_org"]>checked="true"</#if> entity="t_core_company"/>有　
               <input type="radio" name="have_security_org" value="false"
<#if map["have_security_org"]?? && !map["have_security_org"]>checked="true"</#if> entity="t_core_company" />无
              </div>
            </td>
          </tr>
          <tr>
                 <td width="18%" class="tit_bold">安管人员</td>
                 <td width="82%" class="noborder"><div class="content_table"><input id="security_person" 
				class="noborder" name="security_person" value="${map["security_person"]}" size="8" maxlength="5" entity="t_core_company" />人</div></td>
          </tr>
          <tr>
            <td class="tit_bold">特种设备</td>
            <td class="noborder"><div class="content_table"><table width="100%" border="0">
              <tr>
                <td width="17%" class="tit">锅炉</td>
                <td width="17%"><input id="special_boiler" 
				class="noborder" name="special_boiler" value="${map["special_boiler"]}" style="ime-mode:disabled;" size="4" maxlength="6" entity="t_core_company" />台</td>
                <td width="17%" class="tit">压力容器</td>
                <td width="21%"><input id="special_pressure_vessel" 
				class="noborder" name="special_pressure_vessel" value="${map["special_pressure_vessel"]}" size="4" style="ime-mode:disabled;" maxlength="6" entity="t_core_company" />台</td>
                <td width="9%" class="tit">电梯</td>
                <td width="19%"><input id="special_lift" 
				class="noborder" name="special_lift" value="${map["special_lift"]}" style="ime-mode:disabled;" size="4" maxlength="6" entity="t_core_company" />台</td>
              </tr>
              <tr>
                <td class="tit">起重机械</td>
                <td><input id="special_lifting_machine" 
				class="noborder" name="special_lifting_machine" value="${map["special_lifting_machine"]}" style="ime-mode:disabled;" size="4" maxlength="6" entity="t_core_company" />台</td>
                <td class="tit">场内机动车</td>
                <td colspan="3"><input id="special_motor_vehicle" 
				class="noborder" name="special_motor_vehicle" value="${map["special_motor_vehicle"]}" style="ime-mode:disabled;" size="4" maxlength="6" entity="t_core_company" />台</td>
              </tr>
            </table></div></td>
          </tr>
               <tr>
                 <td class="tit_bold tit">许可证</td>
                 <td class="noborder"><div class="content_table">
                   <table width="100%" border="0">
                     <tr class="tit">
                       <td width="8%" class="center">序号</td>
                       <td width="25%" class="center">种类</td>
                       <td width="20%" class="center">证书号</td>
                       <td width="17%" class="center">有效期</td>
                       <td width="20%" class="center">范围</td>
                       <td width="10%" class="center"><img src="${resourcePath}/img/add_qyhkb_icon.jpg" width="38" height="22" id="add_xkz_btn" style="cursor:pointer;" /></td>
                     </tr>
                     
                   </table>
                       <table width="100%" border="0" id="xzxkTable">
                       <#list map["xzxks"] as xzxk>
	                       <tr>
	                           <td width="8%" class="center">${xzxk_index+1}</td>
	                           <td width="25%" class="center">
		                           <#if xzxk.xkType?exists>
		                           	<script type="text/javascript">document.write(enumObj.getSelect("${xzxk.xkType}"));</script>
		                           </#if>
	                           </td>
	                           <td width="20%" class="center" title="${xzxk.licence}">
	                           <div title="${xzxk.licence}" style="width:80px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">${xzxk.licence}<div>
	                           </td>
	                           <td width="17%" class="center">
	                           <script type="text/javascript">document.write(checkDate("${xzxk.validityEnd?string("yyyy-MM-dd")}"));</script>
	                           </td>
	                           <td width="20%" class="center" title="${xzxk.permitScope}">
	                           <div title="${xzxk.permitScope}" style="width:80px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">${xzxk.permitScope}<div>
	                           </td>
	                           <td width="10%" class="center"><img src="${resourcePath}/img/del_qyhkb_icon.jpg" width="16" height="16" onClick="delXzxkRow(this,${xzxk.id});" style="cursor:pointer;"/></td>
	                         </tr>
	                         <script type="text/javascript">
	                         	xzxkRow++;
	                         </script>
                       </#list>
                       </table>
                       <!--
                       <div id="xzxkDiv">
                       <#list map["xzxks"] as xzxk>
                       <table width="100%" border="0" id="xzxkTable">
	                     <tr>
                         <td rowspan="2" width="4%" nowrap>${xzxk_index+1}</td>
                         <td width="8%" class="tit" nowrap>种类</td>
                         <td width="25%"><font>
                         	<#if xzxk.xkType?exists>
	                           	<script type="text/javascript">document.write(enumObj.getSelect("${xzxk.xkType}"));</script>
	                           </#if></font>
                         </td>
                         <td width="10%" class="tit" nowrap>证书号</td>
                         <td width="24%">
                          <div title="${xzxk.licence}" style="width:100px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">${xzxk.licence}<div>
                         </td>
                         <td width="10%" class="tit" nowrap>有效期</td>
                         <td width="17%">
                         <script type="text/javascript">document.write(checkDate("${xzxk.validityEnd?string("yyyy-MM-dd")}"));</script>
                         </td>
                       </tr>
                       <tr>
                       <td class="tit">范围</td><td colspan="4">
                       <div title="${xzxk.permitScope}" style="width:235px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">${xzxk.permitScope}<div>
                       </td>
	                   <td class="center">
	                     <img title="新增" src="${resourcePath}/img/qyda_add.jpg" width="16" height="16" onClick="showXzk();" style="cursor:pointer;"/>
	                     <img title="删除" src="${resourcePath}/img/qyda_close.jpg" width="16" height="16" onClick="delXzxkRow(this,${xzxk.id});" style="cursor:pointer;"/>
	                   </td>
                       </tr>
	                         <script type="text/javascript">
	                         	xzxkRow++;
	                         </script>
                       </table>
                       </#list>
                       </div>-->
                        <!--许可证新增---表单 开始-->
                   <div id="add_xkz_form" style="display:none; ">
                       <table width="100%" border="0" >
                       <tr>
                       	<td rowspan="2" width="4%" nowrap></td>
                         <td width="8%"  class="tit">种类</td>
                         <td width="24%">
                         <select style="width:100%;" name="xkType" id="xkType">
                           <option value="0">--请选择--</option>
                         </select></td>
                         <td width="10%" nowrap class="tit">证书号</td>
                         <td width="25%"><input type="text" name="licence" id="licence" style="width:100%" maxlength="50"/></td>
                         <td width="10%" nowrap class="tit">有效期</td>
                         <td width="17%">
                         <input type="text"  name="validityEnd" id="validityEnd"  onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});" maxlength="10" style="width:100%" title="格式如：1900-01-01">
                         </td>
                       </tr>
                       <tr>
                       <td class="tit">范围</td><td colspan="4"><input type="text" name="permitScope" id="permitScope" style="width:100%" maxlength="500"></td>
                         <td class="center">
                         	<img src="${resourcePath}/img/qyda_ok.jpg" width="16" height="16" id="addXzxk" style="cursor:pointer;" title="确定"/>
                          <img src="${resourcePath}/img/qyda_cancel.jpg" width="16" height="16" id="close_xkz_btn" style="cursor:pointer;" title="关闭"/> 
                          </td>
                       </tr>
                     </table>
                   </div>
                   <!--许可证新增---表单 结束-->
                 </div></td>
          </tr>
               <tr>
                 <td class="tit_bold">安评报告</td>
                 <td class="noborder"><div class="content_table"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="17%" class="tit">备案日期</td>
                     <td width="16%"><input id="anp_records_date" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="anp_records_date" value="${map["anp_records_date"]?date}" size="10" entity="t_core_company" style="ime-mode:disabled"  maxlength="10" title="格式如：1900-01-01" /></td>
                     <td width="15%" class="tit">有效期</td>
                     <td width="51%"><input id="anp_validity_end" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="anp_validity_end" value="${map["anp_validity_end"]?date}" size="10" entity="t_core_company" style="ime-mode:disabled"  maxlength="10" title="格式如：1900-01-01" /></td>
                   </tr>
                 </table></div></td>
          </tr>
               <tr>
                 <td class="tit_bold">重大危险源</td>
                 <td class="noborder"><div class="content_table"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="17%" class="tit">等级</td>
                     <td width="16%">
                     <select name="major_grade" id="major_grade" style="width:80px;" entity="t_core_company">
				  		<option value="0">--请选择--</option>
		        	 </select></td>
                     <td width="15%" class="tit">备案日期</td>
                     <td width="51%" colspan="2"><input id="major_records_date" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="major_records_date" value="${map["major_records_date"]?date}" size="10" entity="t_core_company" style="ime-mode:disabled"  maxlength="10" title="格式如：1900-01-01" /></td>
                   </tr>
                 </table></div></td>
          </tr>
               <tr>
                 <td class="tit_bold">应急管理</td>
                 <td class="noborder"><div class="content_table"><table width="100%" border="0">
                   <tr>
                     <td width="18%" class="tit">预案备案日期</td>
                     <td width="82%"><input id="emergency_records_date" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="emergency_records_date" value="${map["emergency_records_date"]?date}" size="10" entity="t_core_company" style="ime-mode:disabled"  maxlength="10" title="填写最近预案备案日期，格式如：1900-01-01" /></td>
                   </tr>
                   <tr>
                     <td class="tit">演练情况</td>
                     <td title="当年应急演练情况">
		        	 <input type="radio" name="emergency_drill" value="true" 
					<#if map["emergency_drill"]?? && map["emergency_drill"]>checked="true"</#if> entity="t_core_company"/>有　
               		<input type="radio" name="emergency_drill" value="false"
					<#if map["emergency_drill"]?? && !map["emergency_drill"]>checked="true"</#if> entity="t_core_company" />无
		        	 </td>
                   </tr>
                 </table></div></td>
          </tr>
               <tr>
                 <td class="tit_bold">标准化</td>
                 <td align="right" class="noborder"><div class="content_table">
                   <table width="100%" border="0">
                     <tr class="tit">
                       <td width="8%" class="center">序号</td>
                       <td width="27%" class="center">种类</td>
                       <td width="20%" class="center">证书号</td>
                       <td width="15%" class="center">等级</td>
                       <td width="18%" class="center">有效期</td>
                       <td width="12%" class="center"><img src="${resourcePath}/img/add_qyhkb_icon.jpg" alt="" width="38" height="22" id="add_bzh_btn" style="cursor:pointer;" /></td>
                     </tr>
                   </table>
                   <table width="100%" border="0" id="bzhTable">
	                   <#list map["bzhs"] as bzh>
	                   	<tr>
	                       <td width="8%" class="center">${bzh_index+1}</td>
	                       <td width="27%" class="center">
		                       <#if bzh.bzhType?exists>
	                           	<script type="text/javascript">document.write(enumObj.getSelect("${bzh.bzhType}"));</script>
	                           </#if>
	                       </td>
	                       <td width="20%" class="center" >
	                       <div title="${bzh.licence}" style="width:80px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">${bzh.licence}<div></td>
	                       <td width="15%" class="center">
	                        <#if bzh.bzhGrade?exists>
	                           	<script type="text/javascript">document.write(enumObj.getSelect("${bzh.bzhGrade}"));</script>
	                           </#if>
	                       </td>
	                       <td width="18%" class="center">
	                       <script type="text/javascript">document.write(checkDate("${bzh.validityEnd?string("yyyy-MM-dd")}"));</script>
	                       </td>
	                       <td width="12%" class="center">
	                       <img src="${resourcePath}/img/qyda_close.jpg" width="16" height="16" onClick="delBzhRow(this,${bzh.id});" style="cursor:pointer;" title="删除"/>
	                       </td>
	                     </tr>
	                     <script type="text/javascript">
	                     	bzhRow++;
	                     </script>
	                   </#list>
                   </table>
                   <div id="add_bzh_form" style="display:none;">
                     <table width="100%" >
                     <tr>
	                       <td width="8%" >&nbsp;</td>
	                       <td width="27%" >
		                       <select style="width:90px;" name="bzhType" id="bzhType">
                           <option value="0">--种类选择--</option>
                         </select>
	                       </td>
	                       <td width="20%"><input type="text" name="bzh_licence" id="bzh_licence" style="width:100%" maxlength="50"/></td>
	                       <td width="15%">
	                       <select  name="bzhGrade" id="bzhGrade">
                           <option value="0">请选择</option>
                           <option value="bzhGrade_1">一级</option>
                           <option value="bzhGrade_2">二级</option>
                           <option value="bzhGrade_3">三级</option>
                       </select>
	                       </td>
	                       <td width="18%">
	                       <input type="text" name="bzh_validityEnd" id="bzh_validityEnd"  onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});" style="width:100%" maxlength="10" title="格式如：1900-01-01">
	                       </td>
	                       <td width="12%" class="center">
	                       <img src="${resourcePath}/img/qyda_ok.jpg" width="16" height="16" id="addBzh" style="cursor:pointer;" title="确定"/>
	                       <img src="${resourcePath}/img/qyda_cancel.jpg" width="16" height="16" id="close_bzh__btn" style="cursor:pointer;" title="关闭"/>
	                       </td>
	                     </tr>
                     </table>
                   </div>
                 </div></td>
          </tr>
               <tr>
                 <td class="tit_bold">隐患排查治理</td>
                 <td class="noborder"><div class="content_table"><table width="100%" border="0">
                   <tr>
                     <td width="15%" class="tit">季报日期</td>
                     <td width="20%"><input id="yh_quarter_reports_date" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="yh_quarter_reports_date" value="${map["yh_quarter_reports_date"]?date}" size="10" entity="t_core_company" style="ime-mode:disabled"  maxlength="10" title="格式如：1900-01-01" /></td>
                     <td width="16%" class="tit">未治理<br />重大隐患数</td>
                     <td width="15%"><input id="yh_no_manage_num" 
				class="noborder" name="yh_no_manage_num" value="${map["yh_no_manage_num"]}" style="ime-mode:disabled;" size="2" maxlength="6" entity="t_core_company" />个</td>
                     <td width="18%" class="tit">挂牌督办隐患</td>
                     <td width="16%"><input id="yh_hidden_danger_licenses" 
				class="noborder" name="yh_hidden_danger_licenses" value="${map["yh_hidden_danger_licenses"]}" style="ime-mode:disabled;" size="2" maxlength="6" entity="t_core_company" />个</td>
                   </tr>
                 </table></div></td>
               </tr>
               <tr>
                 <td class="tit_bold">监督检查</td>
            <td class="noborder"><div class="content_table"><table width="100%" border="0">
              <tr>
                <td width="12%" class="tit">执法</td>
                <td width="8%" rowspan="2">次数</td>
                <td width="20%"><input id="check_justice_num" 
				class="noborder" name="check_justice_num" value="${map["check_justice_num"]}" style="ime-mode:disabled;" size="2" maxlength="6" entity="t_core_company" title="当年检查次数"/>次</td>
                <td width="15%" class="tit">执法文书</td>
                <td width="50%"><input id="check_document_num" 
				class="noborder" name="check_document_num" value="${map["check_document_num"]}" style="ime-mode:disabled;" size="2" maxlength="6" entity="t_core_company" />份</td>
              </tr>
              <tr>
                <td class="tit">日常</td>
                <td><input id="check_usual_num" 
				class="noborder" name="check_usual_num" value="${map["check_usual_num"]}" style="ime-mode:disabled;" size="2" maxlength="6" entity="t_core_company" title="当年检查次数"/>次</td>
                <td class="tit">发现隐患</td>
                <td><input type="radio" name="check_find_trouble" value="true" 
					<#if map["check_find_trouble"]?? && map["check_find_trouble"]>checked="true"</#if> entity="t_core_company"/>有　
               		<input type="radio" name="check_find_trouble" value="false"
					<#if map["check_find_trouble"]?? && !map["check_find_trouble"]>checked="true"</#if> entity="t_core_company" />无
		        </td>
              </tr>
            </table></div></td>
         </tr>
               <tr>
                 <td class="tit_bold">事故情况</td>
                 <td class="noborder"><div class="content_table"><table width="100%" border="0">
                   <tr>
                     <td width="11%" rowspan="2" class="tit center">非火灾</td>
                     <td width="8%">死亡</td>
                     <td width="12%"><input id="acc_non_fire_death_num" 
				class="noborder" name="acc_non_fire_death_num" value="${map["acc_non_fire_death_num"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" title="当年非火灾死亡事故起数"/>起</td>
                     <td width="13%">死</td>
                     <td width="12%"><input id="acc_non_fire_deaths" 
				class="noborder" name="acc_non_fire_deaths" value="${map["acc_non_fire_deaths"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />人</td>
                     <td width="7%" rowspan="3" class="tit center">伤</td>
                     <td width="12%"><input id="acc_non_fire_casualties" 
				class="noborder" name="acc_non_fire_casualties" value="${map["acc_non_fire_casualties"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />人</td>
                     <td width="8%" rowspan="3" class="tit center">直<br />接<br />经<br />济<br />损<br />失</td>
                     <td width="13%"><input id="acc_non_fire_death_losses" 
				class="noborder" name="acc_non_fire_death_losses" value="<#if map["acc_non_fire_death_losses"]??>#{map["acc_non_fire_death_losses"];m1M1}</#if>" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />万元</td>
                   </tr>
                   <tr>
                     <td>工伤</td>
                     <td><input id="acc_non_fire_injury_num" 
				class="noborder" name="acc_non_fire_injury_num" value="${map["acc_non_fire_injury_num"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" title="当年非火灾工伤事故起数"/>起</td>
                     <td>上年千人<br />事故率(‰)</td>
                     <td><input id="acc_non_last_accident_rate" 
				class="noborder" name="acc_non_last_accident_rate" value="<#if map["acc_non_last_accident_rate"]??&&map["acc_non_last_accident_rate"]!=0>#{map["acc_non_last_accident_rate"];m1M1}</#if>" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" /></td>
                     <td><input id="acc_non_fire_injuries" 
				class="noborder" name="acc_non_fire_injuries" value="${map["acc_non_fire_injuries"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />人</td>
                     <td><input id="acc_non_fire_injurie_losses" 
				class="noborder" name="acc_non_fire_injurie_losses" value="<#if map["acc_non_fire_injurie_losses"]??>#{map["acc_non_fire_injurie_losses"];m1M1}</#if>" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />万元</td>
                   </tr>
                   <tr>
                     <td class="tit center">火灾</td>
                     <td colspan="2"><input id="acc_fire_num" 
				class="noborder" name="acc_fire_num" value="${map["acc_fire_num"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" title="当年火灾事故起数"/>起</td>
                     <td>死</td>
                     <td><input id="acc_fire_deaths" 
				class="noborder" name="acc_fire_deaths" value="${map["acc_fire_deaths"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />人</td>
                     <td><input id="acc_fire_injuries" 
				class="noborder" name="acc_fire_injuries" value="${map["acc_fire_injuries"]}" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />人</td>
                     <td><input id="acc_fire_losses" 
				class="noborder" name="acc_fire_losses" value="<#if map["acc_fire_losses"]??>#{map["acc_fire_losses"];m1M1}</#if>" style="ime-mode:disabled;width:30px" size="2" maxlength="6" entity="t_core_company" />万元</td>
                   </tr>
                 </table></div></td>
               </tr>
               <tr>
                 <td class="tit_bold">信用管理</td>
                 <td class="noborder"><div class="content_table"><table width="100%" border="0">
                   <tr>
                     <td width="13%" class="tit">信用等级</td>
                     <td width="16%"><select style="width:60px;" name="credit_grade" id="credit_grade" style="width:80px" entity="t_core_company">
				  		<option value="0">请选择</option>
		        	 </select></td>
                     <td width="13%" class="tit">承诺日期</td>
                     <td width="20%"><input id="credit_promise_date" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="credit_promise_date" value="${map["credit_promise_date"]?date}" size="10" entity="t_core_company" style="ime-mode:disabled"  maxlength="10" title="格式如：1900-01-01" /></td>
                     <td width="19%" class="tit">履诺报告日期</td>
                     <td width="19%"><input id="credit_perform_date" onClick="WdatePicker({onpicked:function(){this.focus();this.blur();}});"
				class="noborder" name="credit_perform_date" value="${map["credit_perform_date"]?date}" style="ime-mode:disabled;" size="10" maxlength="10" entity="t_core_company" style="ime-mode:disabled" maxlength="10" title="格式如：1900-01-01" /></td>
                   </tr>
                 </table></div></td>
               </tr>    
        </table>
		
	 </div>
	 <!--安全生产基本状况 结束-->
		 <div id="uploadImageDiv" style="position:absolute; left:500px; display:none; left: 150px; top: 300px; font-size:13px;">
			<form method="post" id="imageUploadForm" action="#" enctype="multipart/form-data" onsubmit="return checkForm();">
			<input type="hidden" name="daImageFile.daCompany.id" value="${coreCompany.id}" />
			<input type="hidden" name="daImageFile.fileDescription" id="fileDescription" />
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
  </div>
<script type="text/javascript">
$(function(){
	var $comment = $('#xk_contentg'); 
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

	//tag:1 上传 2：编辑
	function showImageUpload(tag, id,des){
		$("#uploadImageDiv").show();
		$("#fileDescription").val(des)
		if(tag == 1){
			$("#imageUploadForm").attr("action", "../imageFile/createFile.xhtml");;
		}else{
			$("#imageUploadForm").attr("action", "../imageFile/updateFile.xhtml?daImageFile.id="+id);
		}
	}
	function hideImageUpload(){
		$("#uploadImageDiv").hide();
	}
	enumObj.initSelect("xkType","xkType");//行政许可--下拉菜单填充
	enumObj.initSelect("bzhType","bzhType");//标准版--下拉菜单填充
	//enumObj.initSelect("bzhGrade","bzhGrade");//标准化等级--下拉菜单填充
	enumObj.initSelect("na_eco_level1","na_eco_level1");//国民经济分类一级--下拉菜单填充
	enumObj.initSelect("management_level1","management_level1");//管理分类一级--下拉菜单填充
	enumObj.initSelect("major_grade","major_grade");//重大危险源等级--下拉菜单填充
	enumObj.initSelect("credit_grade","credit_grade");//信用等级--下拉菜单填充
	enumObj.initSelect("economic_type1","economic_type1");//经济类型--下拉菜单填充
	//区域下拉单赋值
	<#if map['second_area']??>
		jQuery("#second-area").val("${map['second_area']}");
		loadThirdAreas('',"${map['second_area']}","${map['third_area']}");
		<#if map['third_area']??>
			loadFouthAreas('',"${map['third_area']}","${map['fouth_area']}");
		</#if>
	</#if>
	
	function loadThirdAreas(obj,areaCode,third_area){
	    if (areaCode == 'undefined' || areaCode == '') {
	    	areaCode = jQuery(obj).val();
	    } 
		jQuery("#third-area option").remove();
		jQuery("#fouth-area option").remove();
		jQuery.ajax({
	        url : 'ajaxThirdArea.xhtml',
	        type : 'post',
	        dataType : 'html',
	        data : 'areaCode=' + areaCode +'&third_area='+third_area+'&s='+ Math.round(Math.random() * 10000),
	        success : function(html) {
	            if (html != null && html != '') {
	                jQuery("#third-area").append(html);
	            }
	        }
	    });
	}
	
	function loadFouthAreas(obj,areaCode,fouth_area){
	    if (areaCode == 'undefined' || areaCode == '') {
	    	areaCode = jQuery(obj).val();
	    } 
		jQuery("#fouth-area option").remove();
		jQuery.ajax({
	        url : 'ajaxFouthArea.xhtml',
	        type : 'post',
	        dataType : 'html',
	        data : 'areaCode=' + areaCode +'&fouth_area='+fouth_area+'&s='+ Math.round(Math.random() * 10000),
	        success : function(html) {
	            if (html != null && html != '') {
	                jQuery("#fouth-area").append(html);
	            }
	        }
	    });
	}
	//经济类型
	<#if map['economic_type1']??>
		jQuery("#economic_type1").val("${map['economic_type1']}");
		nextSelect("${map['economic_type1']}","economic_type2");
		<#if map['economic_type2']??>
			jQuery("#economic_type2").val("${map['economic_type2']}");
		</#if>
	</#if>
	//国民经济分类
	<#if map['na_eco_level1']??>
		jQuery("#na_eco_level1").val("${map['na_eco_level1']}");
		nextSelect("${map['na_eco_level1']}","na_eco_level2");
		<#if map['na_eco_level2']??>
			jQuery("#na_eco_level2").val("${map['na_eco_level2']}");
		</#if>
	</#if>
	//管理分类
	<#if map['management_level1']??>
		jQuery("#management_level1").val("${map['management_level1']}");
		nextSelect("${map['management_level1']}","management_level2");
		<#if map['management_level2']??>
			jQuery("#management_level2").val("${map['management_level2']}");
		</#if>
	</#if>
	//重大危险源等级
	<#if map['major_grade']??>
		jQuery("#major_grade").val("${map['major_grade']}");
	</#if>
	
	//信用等级
	<#if map['credit_grade']??>
		jQuery("#credit_grade").val("${map['credit_grade']}");
	</#if>
	function showXzk(){
		  	$("#add_xkz_form").slideDown(500);
		  }
</script>
</#escape> 
<@fkMacros.pageFooter />