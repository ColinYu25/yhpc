<@fkMacros.pageHeader />
<#escape x as (x)!>
<script src="${resourcePath}/js/lhgdialog.4.2.0/lhgdialog.min.js?self=false&skin=chrome" type="text/javascript"></script>
<#assign url='createNomalDanger.xhtml'>
<script type="text/javascript">
if ( ${company.secondArea}==0){
	alert("请先完善企业基本信息！");
	location.href='../company/loadCompany.xhtml';
}

function  moneyts(){
	alert("请注意: 落实治理经费单位为万元!");
}


function  limit(a){
 	if (a>100){
		alert("个数要求控制在100以下!");
		document.getElementById("yhcount_0").value=0;
	}
}


function submitCreate(){

 var obj=get("seasonReportForm");
 obj.action="${url}";

 var  chk;
 var chkObjs = document.getElementsByName("daNomalDanger.inputType");
                for(var i=0;i<chkObjs.length;i++){
                    if(chkObjs[i].checked){
                        chk = chkObjs[i].value;
                        break;
                    }
                }

 if (chk==1){   //按明细

 	//if(formValidator.validate()){
  		 
  		  if(get("moreDanger").rows.length!=0){
  		    //对隐患描述，隐患类别进行非空判断
  		    var len=parseInt(get("moreDanger").rows.length);
  		    for(var i=0;i<len;i++){
  		      var num=parseInt(i)+1;
  	
  		      var description_index="daNomalDangers["+i+"].description";
  		      if(get(description_index).value.trim()==''){
  		         alert("第"+num+"项一般隐患的隐患描述不能为空！");
  		         return;
  		      }
  	
  		      var secondType_index="daNomalDangers["+i+"].secondType";
  		      if(get(secondType_index).value==''){
  		         alert("第"+num+"项一般隐患的隐患类型不能为空！");
  		         return;
  		      }
  		      var governMoney_index="daNomalDangers["+i+"].governMoney";
  		      if(get(governMoney_index).value==''){
  		         alert("第"+num+"项一般隐患的落实治理经费不能为空！");
  		         return;
  		      }
  		      if(isNaN(get(governMoney_index).value)){
  		        alert("第"+num+"项一般隐患的落实治理经费为数字类型，请重新输入！");
  		        return;
  		      }
  		      if(get(governMoney_index).value>500){
					alert("落实治理经费不应大于500万元！");
				    return;
			  }
  		    }
  		     
  		  	obj.submit();
  		  }else{
  		  	alert('添加一般隐患请先点"添加隐患"按钮，然后录入具体隐患再点"保存"按钮进行保存！\n 如需对修改过的隐患内容进行保存，请点击该条隐患右侧"保存修改"进行保存！')
  		  }
 	//}
 	
 }else{  //按个数
	 if (get("dangerTrId1").style.display=='none'){
		 alert('请先点"添加隐患"按钮，然后录入隐患个数再点"保存"按钮进行保存！')
	 }else if (document.getElementById("yhcount_0").value==0){
		 alert("隐患个数必须大于0");
	 }else{
	 	 obj.submit();
	 }
 }	
 	
}

function opened1(id){
	get('daNomalDangers['+id+'].description').disabled=false;
	getName('daNomalDangers['+id+'].type')[0].disabled=false;
	getName('daNomalDangers['+id+'].type')[1].disabled=false;
	getName('daNomalDangers['+id+'].type')[2].disabled=false;
	getName('daNomalDangers['+id+'].repaired')[0].disabled=false;
	getName('daNomalDangers['+id+'].repaired')[1].disabled=false;
	
	getName('daNomalDangers['+id+'].repaired')[0].checked=false;
	getName('daNomalDangers['+id+'].repaired')[1].checked=true;
}

function closed1(id){
	get('daNomalDangers['+id+'].description').disabled=true;
	getName('daNomalDangers['+id+'].type')[0].disabled=true;
	getName('daNomalDangers['+id+'].type')[1].disabled=true;
	getName('daNomalDangers['+id+'].type')[2].disabled=true;
	getName('daNomalDangers['+id+'].repaired')[0].disabled=true;
	getName('daNomalDangers['+id+'].repaired')[1].disabled=true;
	get('daNomalDangers['+id+'].description').value="";
	getName('daNomalDangers['+id+'].type')[0].checked=false;
	getName('daNomalDangers['+id+'].type')[1].checked=false;
	getName('daNomalDangers['+id+'].type')[2].checked=false;
	getName('daNomalDangers['+id+'].repaired')[0].checked=false;
	getName('daNomalDangers['+id+'].repaired')[1].checked=false;
}


function opened(id){

	get('description_'+id).disabled=false;
	getName('type_'+id)[0].disabled=false;
	getName('type_'+id)[1].disabled=false;
	getName('type_'+id)[2].disabled=false;
	getName('repaired_'+id)[0].disabled=false;
	getName('repaired_'+id)[1].disabled=false;
	
	getName('repaired_'+id)[0].checked=false;
	getName('repaired_'+id)[1].checked=true;
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
 var  chk;
 var chkObjs = document.getElementsByName("daNomalDanger.inputType");
                for(var i=0;i<chkObjs.length;i++){
                    if(chkObjs[i].checked){
                        chk = chkObjs[i].value;
                        break;
                    }
                }


 if (chk==1){   //按明细

		get("dangerTrId").style.display='';
        if(moreDanger.rows.length>19) return;
        newRow = get("moreDanger").insertRow(moreDanger.rows.length);
        
		newcell=newRow.insertCell(0);
		newcell.style.width="4%";
		newcell.innerHTML=get("moreDanger").rows.length;
		
		newcell=newRow.insertCell(1);
		newcell.style.width="12%";
		var hazardSource="<div align='left'><select style='width:85px' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].hazardSourceCode'>";
		<#if (hazardSourceEnums?if_exists?size > 0)>
		    <#list hazardSourceEnums?if_exists as fkEnum>
					  hazardSource+="<option value='${fkEnum.enumCode}' title='${fkEnum.enumName}'>${fkEnum.enumName}</option>";
			</#list>
		</#if>
		hazardSource+="</select></div>";
	    newcell.innerHTML=hazardSource;
		
		newcell=newRow.insertCell(2);
		newcell.style.width="23%";
		newcell.innerHTML="<div align='left'><textarea name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].description' id='daNomalDangers["+(get("moreDanger").rows.length-1)+"].description' rows='4' cols='19'></textarea></div>";
			
		newcell=newRow.insertCell(3);
		newcell.style.width="21%";
		var daNomalDangers="<div align='left'><select name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].type'  style='width:52px' onChange='addSecondTypeOption(0,this.value,"+(get("moreDanger").rows.length-1)+");'><option value=''>-请选择-</option>";
		  
				<#if (parentDaIndustryParameters?if_exists?size > 0)>
					<#list parentDaIndustryParameters?if_exists as DaIndustryParameter>
					   daNomalDangers+="<option value='${DaIndustryParameter.id}' title='${DaIndustryParameter.name}'>${DaIndustryParameter.name}</option>";
					</#list>
				</#if>
		
		daNomalDangers+="</select>";
		daNomalDangers+="&nbsp;<select  name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].secondType'  style='width:95px' id='daNomalDangers["+(get("moreDanger").rows.length-1)+"].secondType'><option value=''>-请选择-</option></select></div>";
		newcell.innerHTML=daNomalDangers;
			
		newcell=newRow.insertCell(4);
		newcell.style.width="5%";
		newcell.innerHTML="<input type='radio'  name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].repaired' checked='checked' value='true' >";
		
		newcell=newRow.insertCell(5);
		newcell.style.width="5%";
		newcell.innerHTML="<input type='radio'  onClick='info();'   name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].repaired' value='false'>";
		
	    newcell=newRow.insertCell(6);
        newcell.style.width="10%";
        newcell.innerHTML="<input type='text' name='daNomalDangers["+(get("moreDanger").rows.length-1)+"].governMoney' id='daNomalDangers["+(get("moreDanger").rows.length-1)+"].governMoney'   style='width:46px' maxlength='6'  onBlur='moneyts();'    value='0'>";
		
		newcell=newRow.insertCell(7);
        newcell.style.width="8%";
        newcell.innerHTML=" ";
        
        newcell=newRow.insertCell(8);
        newcell.style.width="8%";
        newcell.innerHTML=" ";
        
        newcell=newRow.insertCell(9);
        newcell.style.width="5%";
        newcell.innerHTML="<input type='button' onclick='del_row()'  name='subtractOne' class='input_input' value='删除' style='width:32px'>";
 }else{  //按个数
 
	 if (get("dangerTrId1").style.display==''){
	 	alert("一次性不能操作多条,请先保存后再添加  !");
	 }else{
	 
	 
	 	    get("dangerTrId1").style.display='';
	        if(moreDanger1.rows.length>10) return;
	        newRow = get("moreDanger1").insertRow();
	        
			newcell=newRow.insertCell(0);
			newcell.style.width="5%";
			newcell.innerHTML=get("moreDanger1").rows.length;
			
	 	    newcell=newRow.insertCell(1);
	        newcell.style.width="20%";
	       // alert((get("moreDanger").rows.length));
	        newcell.innerHTML="<input type='text' id='yhcount_"+(get("moreDanger").rows.length)+"' name='daNomalDangers["+(get("moreDanger").rows.length)+"].yhcount' id='daNomalDangers["+(get("moreDanger").rows.length)+"].yhcount'   style='width:25px' maxlength='3'  onBlur='limit(this.value);'    value='0'>";
			
			newcell=newRow.insertCell(2);
	        newcell.style.width="30%";
	        newcell.innerHTML=" ";
	        
	        newcell=newRow.insertCell(3);
	        newcell.style.width="30%";
	        newcell.innerHTML=" ";
        
	        newcell=newRow.insertCell(4);
	        newcell.style.width="15%";
	        newcell.innerHTML="<input type='button' onclick='del_row1()'  name='subtractOne' class='input_input' value='删除' style='width:32px'>";
	 }
 }
        
}
function del_row(){   
      document.all.moreDanger.deleteRow(window.event.srcElement.parentElement.parentElement.rowIndex);   
    }

function del_row1(){   
	  get("dangerTrId1").style.display='none';
      document.all.moreDanger1.deleteRow(window.event.srcElement.parentElement.parentElement.rowIndex);   
      
    }


function info(){
	alert("一般事故隐患是指危害和整改难度较小，\n发现后能够立即整改排除的隐患");
}


function setTextReadOnly(f,id){
	var governMoney_=document.getElementById("governMoney_"+id);
	governMoney_.value=0;
	if(f==0){
	   governMoney_.readOnly="readOnly";
	  
	}else{
	   governMoney_.readOnly="";
	}

}

function setTextReadOnly1(id){
	alert(id);
}

/*
add by chy 2010-12-13
用来控制删除操作和单选框的显示和隐藏，页面上获取到服务器当前的时间和记录的创建时间
如果服务器当前时间的年份、季度和记录的创建时间的年份、季度都相同时显示删除
操作，否则不就不显示
*/
function control(ids){

 var  chk;
 var chkObjs = document.getElementsByName("daNomalDanger.inputType");
                for(var i=0;i<chkObjs.length;i++){
                    if(chkObjs[i].checked){
                        chk = chkObjs[i].value;
                        break;
                    }
                }


 if (chk==1){   //按明细

	//try{
		var ndCreateTime = document.getElementsByName('ndCreateTime_' + ids);
		var ndcurrentTime = document.getElementsByName('ndcurrentTime_' + ids);
		var deleteSpan = document.getElementsByName('deleteSpan_' + ids);
		var repaired = document.getElementsByName("repaired_" + ids);
		var array1 = ndCreateTime[0].value.split("-");
		var y1 = array1[0];
		var m1 = array1[1];
		var quarter1 = Math.ceil(m1 / 3);
		
		var array2 = ndcurrentTime[0].value.split("-");
		var y2 = array2[0];
		var m2 = array2[1];
		var quarter2 = Math.ceil(m2 / 3);
	//	alert("y1: "+y1+" y2: "+y2 );
	//	alert("m1: "+m1+" m2: "+m2 );
	//	alert("quarter1: "+quarter1+" quarter2: "+quarter2 );
		if(y1 != y2 || m1 != m2){
			if(deleteSpan.length > 0){
				deleteSpan[0].innerHTML = "";
			}
		
		}
		if(y1 != y2 || quarter1 != quarter2){
			if(deleteSpan.length > 0){
				deleteSpan[0].innerHTML = "";
			}
			get('danger_'+ids)[0].disabled=true;
			get('danger_'+ids)[1].disabled=true;
			get('description_'+ids).disabled=true;
			getName('type_'+ids)[0].disabled=true;
			getName('type_'+ids)[1].disabled=true;
			getName('type_'+ids)[2].disabled=true;
			getName('repaired_'+ids)[0].disabled=true;			
		}
	//}catch(err){
	//	alert(err.description);
	//}
	
	}
}


function addSecondTypeOption(dotype,typeId,ids){
        //dotype  0表示添加，1表示修改
        var secondTypeSelect;
        if(dotype==0){
           secondTypeSelect=get('daNomalDangers['+ids+'].secondType');
        }else{
           secondTypeSelect=get('secondType_'+ids);
        }
        //先清除所有的选项
        secondTypeSelect.options.length=0
        //再添加相应的选项
        <#if (childDaIndustryParameters?if_exists?size > 0)>
            secondTypeSelect.options.add(new Option('-请选择-','')); 
		    <#list childDaIndustryParameters?if_exists as daIndustryParameter>
		               var parentId=${daIndustryParameter.daIndustryParameter.id};
		               if(parentId==typeId){
		                  var option=new Option('${daIndustryParameter.name}','${daIndustryParameter.id}');
		                  option.title='${daIndustryParameter.name}';
		                  secondTypeSelect.options.add(option); 
		               }
			</#list>
		</#if>
}


//if (${type}==2){  //按条数页面

   //document.getElementById("ipt1").style.display="block";
  // document.getElementById("hh").style.display="none";
//}


var lhgDg=null;
function showImageWin(dangerId) {
	lhgDg = jQuery.dialog({
  		autoPos:true, //自动居中
        iconTitle:false,
	    id:'showImageWin',
	    width:580,
	    height:350,
	    lock : true,
	    max :false,
	    cache : false,
	    //ok : function(){ lhgDg.content.uploadImage(); return false;},
	    //cancel : true,
	    //cancelVal : "关闭",
	    //okVal : "上传",
	    title:"一般隐患图片",
	    cover:true,
	    resize:false,//允许拖动改变窗口大小
	    btnBar:false,
	    content: 'url:${base}/danger/image_showImageWin.xhtml?nomalDanger.id=' + dangerId
	}).focus();
}

</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">一般隐患</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if !viewRole?? || !viewRole>
	<li id="img_save"><a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_xcjcjl"><a href="javascript:add_row();" class="b_xcjcjl" ><b>添加隐患</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<@fkMacros.formValidator 'seasonReportForm' />
<form id="seasonReportForm" name="seasonReportForm" action="" method="post">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
<tr>
	  <th width="15%">单位名称</th>
	  <td width="35%"><#if company??>${company.companyName}</#if><#if bag??>${bag.name}</#if></td>
	  
	  <th width="15%">单位地址</th>
	  <td width="35%"><#if company??>${company.address}</#if><#if bag??>${bag.regAddress}</#if></td>
</tr>
<tr>
	  <th width="15%">联 系 人</th>
	  <td>
	     <#if company?exists&&company.safetyMngPerson?exists&&company.safetyMngPerson!=''>
	      ${company.safetyMngPerson}
		  <input id="linkMan" name="daNomalDanger.linkMan" type="hidden" value="${company.safetyMngPerson}" size="16" maxlength="50" />
	     <#else>
	       /
	     </#if> 
	  </td>
	  <th style="width:15%;">联系电话</th>
	  <td>
	   <#if company?exists&&company.safetyMngPersonPhone?exists&&company.safetyMngPersonPhone!=''>
	  	  ${company.safetyMngPersonPhone}
          <input id="linkTel" name="daNomalDanger.linkTel" type="hidden" size="13" value="${company.safetyMngPersonPhone}" maxlength="13" style="ime-mode:disabled"/>
	   <#else>
	       /
	   </#if> 
	   
	      	  <#if company??>
			  <input type="hidden" name="company.id" value="${company.id}" />
			  </#if>
			  <input type="hidden" name="daNomalDanger.danger" id="isDangerVal"/>
	 </td>
	 

</tr>

<tr>
	  <th   id="hh">隐患录入方式</th>
	  <td colspan="3">
	  <input type="radio" name="daNomalDanger.inputType" value="1"  id="ipt1"  checked onclick="javascript: document.getElementById('table1').style.display='';document.getElementById('table2').style.display='none';" /> 按明细 
	  <input type="radio" name="daNomalDanger.inputType" value="2"  id="ipt2"  disabled  onclick="javascript: document.getElementById('table2').style.display='block';document.getElementById('table1').style.display='none';" />按个数
	  (注:只有当月已录入条数达10条以上且企业规模为大型企业时才可用!)
	  </td>
</tr>	     
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list"  id="table2"  style="display:none">
  	<tr>
		<td colspan="19">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				<tr>
			     	<th width="5%" >序号</th>
					<th width="20%">隐患个数</th>
					<th width="30%">录入时间</th>
					<th width="30%">整改完成日期</th>
					<th width="15%">操作</th>
				</tr>
				<tr id="dangerTrId1" style="display:none;">
					<td colspan="5"  width="100%">
						<table width="100%" id="moreDanger1" border="0" cellpadding="0" cellspacing="0" class="table_list">
						</table>
					</td>
				</tr>
				<#if (daNomalDangers1?if_exists?size > 0)>
					<#list daNomalDangers1?if_exists as nd>
						<tr>
					     	<td width="5%" >${nd_index+1}</td>
							<td width="20%">${nd.yhcount} </td>
							<td width="30%">${nd.createTime} </td>
							<td width="30%">${nd.completedDate} </td>
							<td width="15%">
							   <#if nd.sysFlag?exists && nd.sysFlag='1'  ><a href="deleteNomalDanger1.xhtml?daNomalDanger.flag=${nd.flag}">删除</a></#if>
							</td>
						</tr>
					</#list>
				</#if>
			</table>
		</td>
	</tr>	
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list"  id="table1"  >
   <tr>
		<td colspan="19">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th width="4%"  rowspan="2">序号</th>
					<th width="12%" rowspan="2">隐患来源</th>
					<th width="23%" rowspan="2">隐患描述</th>
					<th width="20%" colspan="3" rowspan="2">隐患类别</th>
					<th width="10%" colspan="2">整改<br>情况</th>
					<th width="10%" rowspan="2">落实治<br>理经费<br><font color=red ><strong>（万元）</strong></font></th>
					<th width="8%" rowspan="2">录入<br>时间</th>
					<th width="8%" rowspan="2">整改<br>完成<br>日期</th>
					<th width="5%" rowspan="2">操作</th>
				</tr>
				<tr>
				    <th width="5%">已<br>整<br>改</th>
					<th width="5%">未<br>整<br>改</th>
				</tr>
		</table>
		</td>
	</tr>
	<tr id="dangerTrId" style="display:none;">
		<td colspan="19">
			<table width="100%" id="moreDanger" border="0" cellpadding="0" cellspacing="0" class="table_list">
			</table>
		</td>
	</tr>
	
    <tr>
		<td colspan="19">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				<#if (daNomalDangers?if_exists?size > 0)>
					<#list daNomalDangers?if_exists as nd>
					<input type="hidden" name="sysFlag_${nd.id}" id="sysFlag_${nd.id}"  value="${nd.sysFlag}" />
					<tr>
					
						<td width="4%"><#if pagination.itemCount?exists>${pagination.itemCount+nd_index+1}<#else>${nd_index+1}</#if></td>
						
						<td align="left" width="12%">
						<div align="left">
						<select name="hazardSourceCode_${nd.id}" id="hazardSourceCode_${nd.id}"  style="width:85px" <#if nd.userId!=userDetail.userId>disabled="disabled"</#if>  <#if nd.sysFlag?exists && nd.sysFlag!=""  >disabled="disabled"</#if>  >
							<#if (hazardSourceEnums?if_exists?size > 0)>
								<#list hazardSourceEnums?if_exists as fkEnum>
								   <option value="${fkEnum.enumCode}"  title="${fkEnum.enumName}"
								   <#if nd.hazardSourceCode?exists><#if nd.hazardSourceCode==fkEnum.enumCode>selected</#if> </#if>>${fkEnum.enumName}</option>
								</#list>
							</#if>
						</select>
						</div>
						</td>
						
						<td width="23%"><div align="left"><#if nd.userId!=userDetail.userId>
						<#if nd.danger>${nd.description}<#else>无隐患</#if>
						<#else>
						<textarea <#if nd.sysFlag?exists && nd.sysFlag!=""  >disabled="disabled"</#if>   <#if !nd.danger>disabled="disabled"</#if> id="description_${nd.id}" name="description_${nd.id}" rows="4" cols="19" ><#if nd.danger>${nd.description}<#else>无隐患</#if></textarea></#if>
						</div>
						</td>
						
						<td  colspan="3" nowrap="nowrap" align="left"  width="20%">
						<div align="left">
						<select name="type_${nd.id}" id="type_${nd.id}"  style="width:52px"  onChange="addSecondTypeOption(1,this.value,${nd.id});" <#if nd.userId!=userDetail.userId>disabled="disabled"</#if> <#if nd.sysFlag?exists && nd.sysFlag!=""  >disabled="disabled"</#if>>
						    <option value="">-请选择-</option>
							<#if (parentDaIndustryParameters?if_exists?size > 0)>
								<#list parentDaIndustryParameters?if_exists as daIndustryParameter>
								   <option value="${daIndustryParameter.id}" <#if nd.type==daIndustryParameter.id>selected</#if> title="${daIndustryParameter.name}">${daIndustryParameter.name}</option>
								</#list>
							</#if>
						</select>
						
						
						<select name="secondType_${nd.id}" id="secondType_${nd.id}"  style="width:95px"  <#if nd.userId!=userDetail.userId>disabled="disabled"</#if> <#if nd.sysFlag?exists && nd.sysFlag!=""  >disabled="disabled"</#if>>>
						<option value="">-请选择-</option>
						<#if (childDaIndustryParameters?if_exists?size > 0)>
					      <#list childDaIndustryParameters?if_exists as daIndustryParameter>
					              <#if nd.type==daIndustryParameter.daIndustryParameter.id>
						            <option value="${daIndustryParameter.id}"  title="${daIndustryParameter.name}" size="2" <#if nd.secondType?exists><#if nd.secondType==daIndustryParameter.id>selected</#if></#if>>${daIndustryParameter.name}</option>
					              </#if>
						  </#list>
					    </#if>
					    </select>
					    </div>
						</td>
						<td width="5%"><input type="radio"   <#if nd.sysFlag?exists && nd.sysFlag!="" && nd.repaired?exists && nd.repaired >disabled="disabled"</#if>    <#if !nd.danger || nd.userId!=userDetail.userId>disabled="disabled"</#if> name="repaired_${nd.id}" value="true" <#if nd?if_exists.danger?exists&&nd.danger><#if nd?if_exists.repaired?exists&&nd.repaired>checked</#if></#if>></td>
						<td width="5%"><input type="radio"  onClick="info();"  <#if nd.sysFlag?exists && nd.sysFlag!="" && nd.repaired?exists && nd.repaired >disabled="disabled"</#if> <#if !nd.danger || nd.userId!=userDetail.userId>disabled="disabled"</#if> name="repaired_${nd.id}" value="false" <#if nd?if_exists.danger?exists&&nd.danger><#if !nd?if_exists.repaired?exists||!nd.repaired>checked</#if></#if>></td>
						<td  width="10%"><input type="text" id="governMoney_${nd.id}" <#if nd.sysFlag?exists && nd.sysFlag!=""  >disabled="disabled"</#if>  <#if nd.userId!=userDetail.userId>disabled="disabled"</#if>  name="governMoney_${nd.id}" id="governMoney_${nd.id}"  value="${nd.sGovernMoney}"  maxlength='6'  style="width:26px"></td>
						<td  width="8%">${nd.createTime.toString().substring(0,10)}</td>
						<td  width="8%">${nd.completedDate.toString().substring(0,10)}</td>	
						<td  width="5%" nowrap="nowrap" >
							<#if nd.userId==userDetail.userId>
								<#if nd.repaired?exists && nd.repaired >  <#else>
								<a href="javascript:updateDanger(${nd.id});">保存</a>
								<br/>
								</#if>
								<span id="deleteSpan_${nd.id}" name="deleteSpan_${nd.id}">
									<#if nd.sysFlag?exists && nd.sysFlag!=""  >  <#else>
									<a href="deleteNomalDanger.xhtml?nomalDangerIds=${nd.id}&company.id=${company.id}">删除</a>
									<br/>
									</#if>
									<a href="javascript:void(0);" onclick="showImageWin('${nd.id}');">图片</a>
								</span>
							<#else>&nbsp;</#if>
						</td>
						<input type="hidden" name="ids" id="ids" value="${nd.id}" />
						<input name="ndCreateTime_${nd.id}" type="hidden"  value="${nd.createTime}" />
						<input name="ndcurrentTime_${nd.id}" type="hidden" value="${daNomalDanger.currentTime}" />
						<input name="danger_${nd.id}" type="hidden" value="true" />
						<script>
							var ids = document.getElementsByName('ids');
							control(ids[ids.length-1].value);
						</script>
					</tr>
					
					    
					</#list>
		
			</table>
		</td>
	</tr>
	<#else>
		<tr>
			<td colspan="19">暂无记录</td>
		</tr>
	</#if>
</table>


</form>
<#if (daNomalDangers?if_exists?size > 0)>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</#if>



<script type="text/javascript">



if (${company.productionScale}='NP_01'   &&  ${nowYhCount} >= 10  ){  //大型企业 且已录入条数为10条
	document.getElementById("ipt2").disabled=false;
}


if(${type}==2){
	document.getElementById("ipt2").checked=true;
	document.getElementById("table1").style.display="none";
	document.getElementById("table2").style.display="";
}
	
</script>


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
	<input type="hidden" name="daNomalDanger.sysFlag"  id="daNomalDanger_sysFlag" />
	<input type="hidden" name="daNomalDanger.hazardSourceCode"  id="daNomalDanger_hazardSourceCode" />
	<input type="hidden" name="daNomalDanger.secondType"  id="daNomalDanger_secondType" />
	<input type="hidden" name="daNomalDanger.governMoney"  id="daNomalDanger_governMoney" />
</form>
<script>
function updateDanger(id){
	var obj=get("updateDanger");
	
		if(get("hazardSourceCode_"+id).value==''){
		    alert("隐患来源不能为空！");
		    return; 
		}  
		if(get("description_"+id).value.trim()==''){
		    alert("隐患描述不能为空！");
		    return; 
		}
		if(get("type_"+id).value==''){
		    alert("隐患类别不能为空！");
		    return;
		}
		if(get("secondType_"+id).value==''){
		    alert("隐患类别不能为空！");
		    return;
		}
		if(get("governMoney_"+id).value>500){
			alert("落实治理经费不应大于500万元！");
		    return;
		}
		
		//get("daNomalDanger_linkMobile").value=get("linkMobile").value;
		get("daNomalDanger_linkTel").value=get("linkTel").value;
		get("daNomalDanger_linkMan").value=get("linkMan").value;
		get("daNomalDanger_id").value=id;
		get("daNomalDanger_description").value=get("description_"+id).value;
		get("daNomalDanger_danger").value=GetRadioValue("danger_"+id);
		get("daNomalDanger_repaired").value=GetRadioValue("repaired_"+id);
		get("daNomalDanger_sysFlag").value=get("sysFlag_"+id).value;
		
		
		get("daNomalDanger_hazardSourceCode").value=get("hazardSourceCode_"+id).value;
		get("daNomalDanger_secondType").value=get("secondType_"+id).value;
		get("daNomalDanger_type").value=get("type_"+id).value;
		get("daNomalDanger_governMoney").value=get("governMoney_"+id).value;

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