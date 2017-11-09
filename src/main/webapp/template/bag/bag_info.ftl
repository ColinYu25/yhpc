<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if bag?exists>
  	<#assign url='updateBag.xhtml'>
  <#else>
  	<#assign url='createBag.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
 		  if(get("bagType").options[get("bagType").options.selectedIndex].value==''){
 		  		alert("请选择一个类型！");
 		  		return;
 		  }
  		  var obj=get("bagForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
	
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>包基本信息</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'bagForm' />
<form id="bagForm" name="bagForm" method="post" action="">
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
            <tr>
              <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
                <tr>
                  
				  <th>名　　称</th>
                  <td colspan="4">
				  <input name="bag.name"  type="text"  id="name" size="73" maxlength="100" value="${bag.name}"/>
                    &nbsp;<span style=color:red>*</span><ui:v for="name"  rule="require" empty="名称不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
                </tr>
				<tr>
                  <th>地　　址</th>
                  <td colspan="4">
				  <input name="bag.regAddress"  type="text"  id="regAddress"  size="73" maxlength="100" value="${bag.regAddress}"/>
                    &nbsp;<span style=color:red>*</span><ui:v for="regAddress"  rule="require" empty="地址不允许为空" pass="&nbsp;" tips="&nbsp;"/></td></td>
                </tr>
				<tr>
                  <th >区　　域</th>
                  <td colspan="4"><div id="div-area"></div></td> 
                </tr>
				<tr>
				  <th>物业名称</th>
				  <td><input name="bag.propertyname" id="propertyname"  type="text" class="input" size="30" maxlength="10" value="${bag.propertyname}"/></td>
				  <th>类     型</th> 
				  <td>
				        <select name="bag.bagType" id="bagType">
		        			<option value="-1">--请选择--</option>
		        		</select>
				  </td>
				</tr>
				  <th>法人或主要负责人</th>
				  <td><input name="bag.fddelegate" id="fddelegate"  type="text" class="input" size="30" maxlength="10" value="${bag.fddelegate}"/></td>
				  <th>联系电话</th>
				  <td align="left" colspan="2">
				  <input name="bag.phoneCode" id="phoneCode" type="text" class="input" maxlength="13" size="30" value="${bag.phoneCode}" />　<ui:v for="phoneCode" rule="phone_mobile" require="true" empty="电话正确格式如0574-87364008。" pass="&nbsp;"/></td>
				</tr>
              </table></td>
            </tr>
        </table>
        <input type="hidden" name="bag.id" id="id" value="${bag.id}"/>
 </form>
 <script type="text/javascript">
 var enumObj=new Enum("${enumXmlUrl}");
 enumObj.initSelect("bagType","bagType");
 <#if bag?if_exists.bagType?exists>
	get("bagType").value = "${bag.bagType}";
</#if>
 </script>
<@fkMacros.muilt_select_js />
<#if bag?has_content>
<@fkMacros.selectAreas_fun "${bag?if_exists.firstArea?if_exists}" "${bag?if_exists.secondArea?if_exists}" "${bag?if_exists.thirdArea?if_exists}" "${bag?if_exists.fouthArea?if_exists}" "${bag?if_exists.fifthArea?if_exists}" "bag."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "bag."/>
</#if>
</#escape>
<@fkMacros.pageFooter />