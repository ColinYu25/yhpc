<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#assign url='createNomalDanger.xhtml'>
<@enum.initAreaXML/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">一般隐患添加</th>
  </tr>
</table>
<div class="menu">
  	<ul>
	<li id="img_save"><a class="b1" href="javaScript:submitCreate();"><b>保存</b></a></li>
	<li id="img_xcjcjl"><a href="javascript:add_row();" class="b_xcjcjl" ><b>添加隐患</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form id="pipeNomalDangerForm" name="pipeNomalDangerForm" action="" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
	<tr>
	  <th width="14%">管道名称</th>
	  <td colspan="6">${entity.name}&nbsp;</td>
	</tr>
	<tr>
	  <th>企业名称</th>
	  <td colspan="6">${entity.daPipelineCompanyinfo.company.companyName}&nbsp;</td>
	</tr>
	<tr>
	  <th>企业地址</th>
	  <td colspan="6">${entity.daPipelineCompanyinfo.company.regAddress}&nbsp;</td>
	</tr>
	<tr>
	  <th>联 系 人</th>
	  <td width="23%"><input id="linkMan" name="nomalDanger.linkMan" type="text" value="${entity.daPipelineCompanyinfo.company?if_exists.fddelegate}" size="16" maxlength="50" /> 
	  <span class="red_point">*</span><ui:v for="linkMan" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
	  <th style="width:12%;">联系电话</th>
	  <td width="20%">
	  <input id="linkTel" name="nomalDanger.linkTel" type="text" size="13" value="${entity.daPipelineCompanyinfo.company?if_exists.phoneCode}" maxlength="13" style="ime-mode:disabled"/>
	  <span class="red_point">*</span><ui:v for="linkTel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
	  <th style="width:12%;">手　　机</th>
	  <td width="22%" colspan=2>
	  <input id="linkMobile" name="nomalDanger.linkMobile" type="text" value="${nomalDanger?if_exists.linkMobile}"  size="11" maxlength="13" style="ime-mode:disabled"/>
	  <ui:v for="linkMobile" rule="mobile" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
	</tr>
	<input type="hidden" name="entity.id" value="${entity.id}" />
	<input type="hidden" name="nomalDanger.danger" id="isDangerVal"/>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list" id="moreDanger">
	<tr>
		<th width="4%" rowspan="2">序号</th>
		<!--<th width="10%" colspan="2">有无隐患</th>-->
		<th width="9%" rowspan="2">隐患类别</th>
		<th width="35%" rowspan="2">隐患描述</th>
		<th width="15%" rowspan="2">隐患所在区域<br>（双击选择区域）</th>
		<th width="12%" colspan="2">治理状态</th>
		<th width="7%" rowspan="2">操作</th>
	</tr>
	<tr>
		<!--<th width="5%">有</th>
		<th width="5%">无</th>-->
		<th width="5%">未治理</th>
		<th width="5%">已治理</th>
	</tr>
	<#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<#if !item.disable>
			<td><input type="hidden" id="id_${item_index}" name="nomalDangers[${item_index}].id" value="${item.id}">${item_index+1}</td>
			<!--<td><input type="radio" id="danger_${item_index}" name="nomalDangers[${item_index}].danger" <#if item.danger>checked</#if> value="true" onClick="insertInfo(false, ${item_index});"></td>
			<td><input type="radio" id="undanger_${item_index}" name="nomalDangers[${item_index}].danger" <#if !item.danger>checked</#if> value="false" onClick="insertInfo(true, ${item_index});"></td>-->
			<td>
				<select id="type_${item_index}" name="nomalDangers[${item_index}].type" <#if !item.danger>disabled="disabled"</#if>>
					<option value="0">-请选择-</option>
					<option value="1010003" <#if item.type?? && item.type=1010003>selected</#if>>检验检测</option>
					<option value="1010002" <#if item.type?? && item.type=1010002>selected</#if>>违章占压</option>
					<option value="1010004" <#if item.type?? && item.type=1010004>selected</#if>>建设施工</option>
					<option value="1010005" <#if item.type?? && item.type=1010005>selected</#if>>标志标识</option>
					<option value="1010006" <#if item.type?? && item.type=1010006>selected</#if>>设备损坏</option>
					<option value="1010026" <#if item.type?? && item.type=1010026>selected</#if>>规划许可</option>
					<option value="1010025" <#if item.type?? && item.type=1010025>selected</#if>>使用登记</option>
					<option value="1010024" <#if item.type?? && item.type=1010024>selected</#if>>安全间距</option>
					<option value="1010007" <#if item.type?? && item.type=1010007>selected</#if>>其他</option>
				</select>
			</td>
			<td><textarea id="description_${item_index}" name="nomalDangers[${item_index}].description" rows="2" cols="50" <#if !item.danger>disabled="disabled"</#if>>${item.description}</textarea></td>
			<td title="双击选择区域" id="area_${item_index}" ondblclick="showSelectAreaBOX(${item_index});" <#if !item.danger>disabled="disabled"</#if>>
				<input type="hidden" id="firstArea_${item_index}" name="nomalDangers[${item_index}].firstArea" value="${item.firstArea}">
				<input type="hidden" id="secondArea_${item_index}" name="nomalDangers[${item_index}].secondArea" value="${item.secondArea}">
				<input type="hidden" id="thirdArea_${item_index}" name="nomalDangers[${item_index}].thirdArea" value="${item.thirdArea}">
				<span id="areaText_${item_index}" style="display:-moz-inline-box; cursor: pointer; display:inline-block;width:80%;border:#12B81B solid 1px; padding-left:5px; padding-right:5px; color:#FF6600; padding-top:1px">
				<#--if item.firstArea??><@enum.getSelectArea item.firstArea/></#if-->
				<#if item.secondArea??><@enum.getSelectArea item.secondArea/></#if><#if item.thirdArea??><@enum.getSelectArea item.thirdArea/></#if>
				</span>
			</td>
			<td><input type="radio" id="unrepaired_${item_index}" name="nomalDangers[${item_index}].repaired" <#if !item.repaired>checked</#if> value="false" <#if !item.danger>disabled="disabled"</#if>></td>
			<td><input type="radio" id="repaired_${item_index}" name="nomalDangers[${item_index}].repaired" <#if item.repaired>checked</#if> value="true" <#if !item.danger>disabled="disabled"</#if>></td>
			<td><input type="button" id="opeate_${item_index}" onclick="del_row()" name="subtractOne" value="删 除"></td>
		<#else>
			<td>${item_index+1}</td>
			<td>
				<select>
					<option value="0">-请选择-</option>
					<option value="1010003" <#if item.type?? && item.type=1010003>selected</#if>>检验检测</option>
					<option value="1010002" <#if item.type?? && item.type=1010002>selected</#if>>违章占压</option>
					<option value="1010004" <#if item.type?? && item.type=1010004>selected</#if>>建设施工</option>
					<option value="1010005" <#if item.type?? && item.type=1010005>selected</#if>>标志标识</option>
					<option value="1010006" <#if item.type?? && item.type=1010006>selected</#if>>设备损坏</option>
					<option value="1010026" <#if item.type?? && item.type=1010026>selected</#if>>规划许可</option>
					<option value="1010025" <#if item.type?? && item.type=1010025>selected</#if>>使用登记</option>
					<option value="1010024" <#if item.type?? && item.type=1010024>selected</#if>>安全间距</option>
					<option value="1010007" <#if item.type?? && item.type=1010007>selected</#if>>其他</option>
				</select>
			</td>
			<td>${item.description}</td>
			<td>
			<span id="areaText_${item_index}" style="display:-moz-inline-box; display:inline-block;width:80%;border:#12B81B solid 1px; padding-left:5px; padding-right:5px; color:#FF6600; padding-top:1px">
				<#--if item.firstArea??><@enum.getSelectArea item.firstArea/></#if-->
				<#if item.secondArea??><@enum.getSelectArea item.secondArea/></#if>
				<#if item.thirdArea??><@enum.getSelectArea item.thirdArea/></#if>
			</span>
			</td>
			<td><input type="radio" <#if !item.repaired>checked</#if> value="false"></td>
			<td><input type="radio" <#if item.repaired>checked</#if> value="true"></td>
			<td>&nbsp;</td>
		</#if>
		</tr>
	</#list>
  	</#if>
</table>
<#--<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input" style="margin-top: 4px;">
  <tr>
    <th colspan="2">单位负责人<BR>
    </p></th>
    <td><input id="chargePerson" maxLength="50" size="14" name="nomalDanger.chargePerson" value="${nomalDanger.chargePerson}"> 
    <span class="red_point">*</span><ui:v for="chargePerson" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
    <th style="width:12%;">填报日期</th>
    <td><input id="fillDate" maxLength="10" size="12" name="nomalDanger.fillDate" class="Wdate" value="${nomalDanger.fillDate?date}" maxlength="10" onfocus="WdatePicker();" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"> 
    <span class="red_point">*</span><ui:v for="fillDate" rule="date" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
  </tr>
</table>-->
</form>
<#--一般隐患隐患区域选择弹出窗口-->
<div id="selectAreaBox" style="display:none;border:1px solid #c0c0c0;width:470px;">
	<input type="hidden" id="areaID" name="" value="" />
	<table width="100%" class="table_list1" border="0" align="center" cellpadding="1" cellspacing="0" bgcolor="#EEEEEE">
		<tr>
			<td colspan="5" width="100%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td height="20px" width="100%" align="center" bgcolor="#A6CFFD">
							<span style="color:#F8F2C7;font-weight:bold;font-size:12px">【隐患区域选择】</span>
						</td>
					</tr>
				</table>			
			</td>
	   </tr>
	    <tr>
			<th height="35" valign="bottom" width="20%">所在地区</th>
      		<td colspan="4" valign="bottom" align="left" width="80%"><div id="div-area"></div></td>
		</tr>
		<tr>
			<th height="40" colspan="5">
				<center>
					<input type="button" class="sub1" value="确认" onClick="selectedArea();"/>
					<input type="button" class="sub1" value="关闭" onClick="closeBox();"/>
				</center>
			</th>
		</tr>
	</table>
</div>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/easydialog/easydialog.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/easydialog/easydialog.min.js"></script>
<script type="text/javascript">
	jQuery.noConflict();
	
	//添加一般隐患
	var row_num = <#if result?? && result?size gt 0>${result?size }<#else>0</#if>;
	
	function submitCreate(){
		var n = 0;
		var linkMan = jQuery("#linkMan").val()
		if(jQuery.trim(linkMan)==""){	
	       alert("联系人项不能为空！");
	       n=0;
		}else{
			n++;
		}
		var linkTel = jQuery("#linkTel").val();
		if(n!=0&&jQuery.trim(linkTel)==""){	
	       alert("联系电话项不能为空！");
	       n=0;
		}else{
			n++;
		}
		<#--var chargePerson = jQuery("#chargePerson").val()
		if(jQuery.trim(chargePerson)==""){	
	       alert("单位负责人项不能为空！");
		   return false;
		}
		var fillDate = jQuery("#fillDate").val()
		if(jQuery.trim(fillDate)==""){	
	       alert("填报日期项不能为空！");
		   return false;
		}
		if(n!=0){
			for(var num=0; num<=row_num; num ++ ){
				if(get('id_' + num)){
					if(get('danger_' + num).checked==true){  
						var type = jQuery("#type_" + num).val();
						var description = jQuery("#description_" + num).val();
						var secondArea = jQuery("#secondArea_" + num).val();
						if(jQuery.trim(type)=="0"){
							 alert("隐患类型不能为空！");
							 n=0;
							 break;
						}
						if(jQuery.trim(description)==""){
							 alert("隐患描述不能为空！");
							 n=0;
							 break;
						}
						if(jQuery.trim(secondArea)==""){
							 alert("隐患所在区域不能为空！");
							 n=0;
							 break;
						}
					}
				}
				n++;
			}
		}-->
	    if(n!=0&&true){//formValidator.validate()
  		  var obj=get("pipeNomalDangerForm");
  		  obj.action="${url}";
  		  if(get("moreDanger").rows.length!=0){
  		  	obj.submit();
  		  }else{
  		  	alert('添加一般隐患请先点"添加隐患"按钮，然后录入具体隐患再点"保持"按钮进行保存！\n 如需对修改过的隐患内容进行保存，请点击该条隐患右侧"保存修改"进行保存！')
  		  }
	   }
	}
	
	function add_row(){
		jQuery("#moreDanger").append( 
		'<tr>'
		+ '<td><input id="id_'+row_num +'" type="hidden" name="nomalDangers['+row_num +'].id" value="">'+(row_num+1) +'</td>'
		//+ '<td><input id="danger_'+row_num +'" type="radio" name="nomalDangers['+row_num +'].danger" value="true" onClick="insertInfo(false, '+row_num +');"></td>'
		//+ '<td><input id="undanger_'+row_num +'" type="radio" name="nomalDangers['+row_num +'].danger" checked value="false" onClick="insertInfo(true, '+row_num +');"></td>'
		+ '<td><select id="type_'+row_num +'" name="nomalDangers['+row_num +'].type">'
		+ '<option value="0">-请选择-</option><option value="1010003">检验检测</option><option value="1010002">违章占压</option>'
		+ '<option value="1010004">建设施工</option><option value="1010005">标志标识</option><option value="1010006">设备损坏</option>'
		+ '<option value="1010006">设备损坏</option><option value="1010026">规划许可</option><option value="1010025">使用登记</option>'
		+ '<option value="1010024">安全间距</option><option value="1010007">其他</option></select></td>'
		+ '<td><textarea id="description_'+row_num +'" name="nomalDangers['+row_num +'].description" rows="2" cols="50" ></textarea></td>'
		+ '<td title="双击选择区域" id="area_'+row_num +'" ondblclick="showSelectAreaBOX('+row_num +');" >'
		+ '<input type="hidden" id="firstArea_'+row_num +'" name="nomalDangers['+row_num +'].firstArea" value="330200000000">'
		+ '<input type="hidden" id="secondArea_'+row_num +'" name="nomalDangers['+row_num +'].secondArea" value="">'
		+ '<input type="hidden" id="thirdArea_'+row_num +'" name="nomalDangers['+row_num +'].thirdArea" value="">'
		+ '<span id="areaText_'+row_num +'" style="display:-moz-inline-box; cursor: pointer; display:inline-block;width:80%;border:#12B81B solid 1px; padding-left:5px; padding-right:5px; color:#FF6600; padding-top:1px">&nbsp;</span></td>'
		+ '<td><input type="radio" id="unrepaired_'+row_num +'" name="nomalDangers['+row_num +'].repaired" value="false" ></td>'
		+ '<td><input type="radio" id="repaired_'+row_num +'" name="nomalDangers['+row_num +'].repaired" checked value="true" ></td>'
		+ '<td><input type="button" id="opeate_'+row_num +'" onclick="del_row()" name="subtractOne" value="删 除"></td>'
		+ '</tr>');
		row_num ++ ;
	}
	
	//有无隐患控制
	function insertInfo(disValue, id) {
		get('type_' + id).disabled=disValue;
		get('description_' + id).disabled=disValue;
		get('area_' + id).disabled=disValue;
		get('unrepaired_' + id).disabled=disValue;
		get('repaired_' + id).disabled=disValue;
		if(disValue){//有隐患设置“未治理”，无隐患设置“已治理”
			get('repaired_' + id).checked=true;
		}else{
			get('unrepaired_' + id).checked=true;
		}
	}

	//删除
	function del_row(){   
	  document.all.moreDanger.deleteRow(window.event.srcElement.parentElement.parentElement.rowIndex);   
	}
	
	//跳出一般隐患区域选择框弹出层
	function showSelectAreaBOX(areaID) {
		document.getElementById("areaID").value = areaID;
		var firstArea = jQuery("#firstArea_" + areaID).val();
		if(firstArea!=""){	
            jQuery("#first-area").attr("value", firstArea);
		}
		var secondArea = jQuery("#secondArea_" + areaID).val();
		if(secondArea!=""){	
			jQuery("#second-area").attr("value", secondArea);
			initNextAreaSelect(secondArea,'third-area','second-area','third-area');
		}
		var thirdArea = jQuery("#thirdArea_" + areaID).val();
		if(thirdArea!=""){	
			jQuery("#third-area").attr("value", thirdArea);
		}
		easyDialog.open({
			container : 'selectAreaBox',
			drag : false
		});
	}
	
	//确认选的区域
	function selectedArea() {
		var areaID = jQuery("#areaID").val();
		jQuery("#firstArea_" + areaID).attr("value", jQuery("#first-area").val());
		jQuery("#secondArea_" + areaID).attr("value", jQuery("#second-area").val());
		jQuery("#thirdArea_" + areaID).attr("value", jQuery("#third-area").val());
		var firstArea = jQuery("#first-area").val();
		var areaText = "";
		if(firstArea!="0"){	
			//areaText = jQuery("#first-area").find("option:selected").text();
		}
		var secondArea = jQuery("#secondArea_" + areaID).val()
		if(secondArea!="0"){	
			areaText = areaText + " " + jQuery("#second-area").find("option:selected").text();
		}
		var thirdArea = jQuery("#thirdArea_" + areaID).val()
		if(thirdArea!="0"){	
			areaText = areaText + " "  + jQuery("#third-area").find("option:selected").text();
		}
		jQuery("#areaText_" + areaID).html(areaText);
		closeBox();
	}
	
	//关闭窗口
	function closeBox() {
		document.getElementById("areaID").value = areaID;
		jQuery("#first-area").val(330200000000);
		jQuery("#second-area").val(0);
		jQuery("#third-area").val(0);
		easyDialog.close();
	}
</script>
</#escape> 
<@fkMacros.pageFooter />