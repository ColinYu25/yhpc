<#--初始化枚举XML-->
<#macro initEnumXML>
	<script type="text/javascript">
		var enumObj=new Enum("${enumXmlUrl}");
	</script>
</#macro>

<#--初始化区域XML-->
<#macro initAreaXML>
	<script type="text/javascript">
		var areaObj=new Area("${areaXmlUrl}");
	</script>
</#macro>

<#--初始化所有区域XML-->
<#macro initAllAreaXML>
	<script type="text/javascript">
		var allAreaObj=new Area("${allAreaXmlUrl}");
	</script>
</#macro>

<#--输出枚举值-->
<#macro getSelectEnum code="">
	<#escape x as (x)!>
		<script>
			var str = enumObj.getSelect('${code?trim}');
			if(str==null || str.length<=0)
				str="&nbsp;";
			document.write(str);
		</script>
	</#escape>
</#macro>

<#--输出区域值-->
<#macro getSelectArea code="">
	<#escape x as (x)!>
		<script>
			var arr = areaObj.getArea('${code?trim}');
			if(arr){
				var value = arr[0];
				if (value && value != 'undefined'){
					document.write(value);
				}else{
					document.write("&nbsp;");
				}
			}
		</script>
	</#escape>
</#macro>

<#--输出区域值-->
<#macro getSelectAreaAll code="">
	<#escape x as (x)!>
		<script>
			var arr = allAreaObj.getArea('${code?trim}');
			if(arr){
				var value = arr[0];
				if (value && value != 'undefined'){
					document.write(value);
				}else{
					document.write("&nbsp;");
				}
			}
		</script>
	</#escape>
</#macro>
