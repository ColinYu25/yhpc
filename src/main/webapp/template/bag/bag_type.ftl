<@fkMacros.pageHeaderAjax />
<#escape x as (x)!> 

<script type="text/javascript">

</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>请选择所属类别</th>
  </tr>
</table>
<form id="bagForm" name="bagForm" method="post" action="">
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	<li id="img_save"><a href="javascript:chooseBagName();" class="b1"><b>完成</b></a></li>
	</ul>
	</div>
</div>
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
            <tr>
              <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
                
				<tr>
				  <th>类     型</th> 
				  <td>
				        <select name="bag.bagType" id="bagType" onChange="loadBagType(this)">
		        			<option value="-1">--请选择--</option>
		        		</select>
				  </td>
				  <th>名     称</th>
				  <td>
				        <select name="bag.name" id="na" onChange="loadBagName(this)">
		        			<option value="-1">--请选择--</option>
		        		</select>
				  </td>
				</tr>
              </table></td>
            </tr>
        </table>
 </form>
 <form id="bagCompanyRelForm" name="bagCompanyRelForm" method="post" action="${contextPath}/bagCompanyRel/createBagCompanyRel.xhtml">
 	<input type="hidden" name="bagId" id="id" value=""/>
 	<input type="hidden" name="companyPassIds" id="id" value="${companyIds}"/>
 </form>
 <script type="text/javascript">
 	var enumObj=new Enum("${enumXmlUrl}");
 	enumObj.initSelect("bagType","bagType");
 	function loadBagType(id){
 		var myAjax = new Ajax.Request("${contextPath}/bag/loadBagsByBagType.xhtml?tp="+id.value,{method: "get",asynchronous:false,onSuccess:function(transport){
				bags= transport.responseText.evalJSON();
				var obj=document.getElementById("na")
				if (obj) {
				for(var i=obj.options.length-1; i>0; i--) {
					   obj.options[i] = null;
				    }
			    }
			    if(obj!=null&&obj!=undefined){
					var opt = new Option("--请选择--","");
					obj.options[0] = opt ;
					opt = null;
					for(var i=0;i<bags.length; i++) {
						var opt = new Option(bags[i].name,bags[i].id) ;
							obj.options[i+1] = opt ;
							opt = null ;
					}
				}
			}
		});
    }
    function loadBagName(name){
    	get("bagId").value=name.value;
    }
    function chooseBagName(){
    	get("bagCompanyRelForm").submit();
    	window.parent.location.reload();   
    	closeWindows('dabao');
	}
 </script>

</#escape>
<@fkMacros.pageFooter />