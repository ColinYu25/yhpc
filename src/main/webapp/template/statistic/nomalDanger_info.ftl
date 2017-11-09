<@fkMacros.pageHeader />
<#escape x as (x)!> 
  	<#assign url='createNomalDanger.xhtml'>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("seasonReportForm");
  		  obj.action="${url}";
  		  if(get("moreDanger").rows.length!=0){
  		  	obj.submit();
  		  }else{
  		  	alert('添加一般隐患请先点"添加隐患"按钮，然后录入具体隐患再点"保持"按钮进行保存！\n 如需对修改过的隐患内容进行保存，请点击该条隐患右侧"保存修改"进行保存！')
  		  }
 		  
 	}
}

function opened(id){
	get('description_'+id).disabled=false;
	getName('type_'+id)[0].disabled=false;
	getName('type_'+id)[1].disabled=false;
	getName('type_'+id)[2].disabled=false;
	getName('repaired_'+id)[0].disabled=false;
	getName('repaired_'+id)[1].disabled=false;
}

function closed(id){
	get('description_'+id).disabled=true;
	getName('type_'+id)[0].disabled=true;
	getName('type_'+id)[1].disabled=true;
	getName('type_'+id)[2].disabled=true;
	getName('repaired_'+id)[0].disabled=true;
	getName('repaired_'+id)[1].disabled=true;
	get('description_'+id).value="";
	getName('type_'+id)[0].checked=false;
	getName('type_'+id)[1].checked=false;
	getName('type_'+id)[2].checked=false;
	getName('repaired_'+id)[0].checked=false;
	getName('repaired_'+id)[1].checked=false;
}

function add_row(){
		get("dangerTrId").style.display='';
        if(moreDanger.rows.length>19) return;
        newRow = get("moreDanger").insertRow();
		newcell=newRow.insertCell();
		newcell.style.width="3%";
		newcell.innerHTML=get("moreDanger").rows.length;
		newcell=newRow.insertCell();
		newcell.style.width="5%";
        newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].danger' value='true' >";
        newcell=newRow.insertCell();
        newcell.style.width="5%";
		newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].danger' checked value='false' >";
		newcell=newRow.insertCell();
		newcell.style.width="45%";
		newcell.innerHTML="<div align='left'><textarea name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].description' rows='2' cols='45'></textarea></div>";
		newcell=newRow.insertCell();
		newcell.style.width="6%";
		newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].type' value='1327' >";
		newcell=newRow.insertCell();
		newcell.style.width="6%";
		newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].type' value='1332' >";
		newcell=newRow.insertCell();
		newcell.style.width="6%";
		newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].type' value='1344' >";
		newcell=newRow.insertCell();
		newcell.style.width="6%";
		newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].repaired' value='false'>";
		newcell=newRow.insertCell();
		newcell.style.width="6%";
		newcell.innerHTML="<input type='radio' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].repaired' value='true' >";
        newcell=newRow.insertCell();
        newcell.style.width="10%";
        newcell.innerHTML="<input type='button' onclick='del_row()'  name='subtractOne' class='input_input' value='删除此行'>";
}
function del_row(){   
      document.all.moreDanger.deleteRow(window.event.srcElement.parentElement.parentElement.rowIndex);   
    }
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">一般隐患列表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<!--<li id="img_save"><a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_xcjcjl"><a href="javascript:add_row();" class="b_xcjcjl" ><b>添加隐患</b></a></li>-->
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<@fkMacros.formValidator 'seasonReportForm' />
<form id="seasonReportForm" name="seasonReportForm" action="" method="post">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
<tr>
  <th width="14%">单位名称</th>
  <td colspan="6"><#if company??>${company.companyName}&nbsp;</#if><#if bag??>${bag.name}</#if></td>
  </tr>
  <tr>
  <th>单位地址</th>
  <td colspan="6"><#if company??>${company.regAddress}&nbsp;</#if><#if bag??>${bag.regAddress}</#if></td>
  </tr>
  <tr>
<tr>
	  <th>联 系 人</th>
	  <td width="23%">${daNomalDanger?if_exists.linkMan}&nbsp;</td>
	  <th style="width:12%;">联系电话</th>
	  <td width="20%">
	  ${daNomalDanger?if_exists.linkTel}&nbsp;</td>
	  <th style="width:12%;">手　　机</th>
	  <td width="22%" colspan=2>
	  ${daNomalDanger?if_exists.linkMobile}&nbsp;</td>
	</tr>
   	<#if company??>
	<input type="hidden" name="company.id" value="${company.id}" />
	</#if>

	<input type="hidden" name="daNomalDanger.danger" id="isDangerVal"/>
</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list" >
	<tr>
		<th width="4%" rowspan="2">序号</th>
		<th width="10%" colspan="2">有无隐患</th>
		<th width="45%" rowspan="2">隐患描述</th>
		<th width="18%" colspan="3">隐患类别</th>
		<th width="12%" colspan="2">治理状态</th>
	</tr>
	<tr>
		<th width="5%">有</th>
		<th width="5%">无</th>
		<th width="6%">人</th>
		<th width="6%">物</th>
		<th width="6%">管理</th>
		<th width="6%">未治理</th>
		<th width="6%">已治理</th>
	</tr>
	<tr id="dangerTrId" style="display:none;">
		<td colspan="10">
			<table width="100%" id="moreDanger" border="0" cellpadding="0" cellspacing="0" class="table_list">
			</table>
		</td>
	</tr>
	</form>
	<#if (daNomalDangers?if_exists?size > 0)>
		<#list daNomalDangers?if_exists as nd>
		<tr>
			<td><#if pagination.itemCount?exists>${pagination.itemCount+nd_index+1}<#else>${nd_index+1}</#if></td>
			<td><input type="radio" name="danger_${nd.id}" value="true"  <#if nd?if_exists.danger?exists&&nd.danger>checked</#if>></td>
			<td><input type="radio"  name="danger_${nd.id}" value="false"  <#if !nd?if_exists.danger?exists||!nd.danger>checked</#if>></td>
			<td><div align="left"><#if nd.danger>${nd.description}<#else>无隐患</#if></div>&nbsp;</td>
			<td><input type="radio" <#if !nd.danger>disabled="disabled"</#if> name="type_${nd.id}" value="1327" <#if nd.danger><#if nd.type?exists><#if nd?if_exists.type?if_exists==1327>checked</#if></#if></#if>></td>
			<td><input type="radio" <#if !nd.danger>disabled="disabled"</#if> name="type_${nd.id}" value="1332" <#if nd.danger><#if nd.type?exists><#if nd?if_exists.type?if_exists==1332>checked</#if></#if></#if>></td>
			<td><input type="radio" <#if !nd.danger>disabled="disabled"</#if> name="type_${nd.id}" value="1344" <#if nd.danger><#if nd.type?exists><#if nd?if_exists.type?if_exists==1344>checked</#if></#if></#if>></td>
			<td><input type="radio" <#if !nd.danger>disabled="disabled"</#if> name="repaired_${nd.id}" value="false" <#if nd?if_exists.danger?exists&&nd.danger><#if !nd?if_exists.repaired?exists||!nd.repaired>checked</#if></#if>></td>
			<td><input type="radio" <#if !nd.danger>disabled="disabled"</#if> name="repaired_${nd.id}" value="true" <#if nd?if_exists.danger?exists&&nd.danger><#if nd?if_exists.repaired?exists&&nd.repaired>checked</#if></#if>></td>
			<input type="hidden"  value="${nd.createTime}" />
		</tr>
		</#list>
	<#else>
		<tr>
			<td colspan="10">暂无记录</td>
		</tr>
	</#if>
</table>
<#if (daNomalDangers?if_exists?size > 0)>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</#if>
<form name="updateDanger" id="updateDanger" action="" method="post" >
	<input type="hidden" name="company.id" value="${company.id}" />
	<input type="hidden" name="daNomalDanger.linkMobile"  id="daNomalDanger_linkMobile"/>
	<input type="hidden" name="daNomalDanger.linkTel"  id="daNomalDanger_linkTel"/>
	<input type="hidden" name="daNomalDanger.linkMan"  id="daNomalDanger_linkMan"/>
	<input type="hidden" name="daNomalDanger.id"  id="daNomalDanger_id" />
	<input type="hidden" name="daNomalDanger.description"  id="daNomalDanger_description" />
	<input type="hidden" name="daNomalDanger.danger"  id="daNomalDanger_danger" />
	<input type="hidden" name="daNomalDanger.type"  id="daNomalDanger_type" />
	<input type="hidden" name="daNomalDanger.repaired"  id="daNomalDanger_repaired" />
</form>
<script>
function updateDanger(id){
	var obj=get("updateDanger");
		get("daNomalDanger_linkMobile").value=get("linkMobile").value;
		get("daNomalDanger_linkTel").value=get("linkTel").value;
		get("daNomalDanger_linkMan").value=get("linkMan").value;
		get("daNomalDanger_id").value=id;
		get("daNomalDanger_description").value=get("description_"+id).value;
		get("daNomalDanger_danger").value=GetRadioValue("danger_"+id);
		get("daNomalDanger_type").value=GetRadioValue("type_"+id)!=null?GetRadioValue("type_"+id):'';
		get("daNomalDanger_repaired").value=GetRadioValue("repaired_"+id);
  		obj.action="updateNomalDanger.xhtml";
 		obj.submit();
}

function GetRadioValue(RadioName){
    var obj;    
    obj=document.getElementsByName(RadioName);
    if(obj!=null){
        var i;
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
                return obj[i].value;            
            }
        }
    }
    return null;
}
</script>
</#escape> 
<@fkMacros.pageFooter />