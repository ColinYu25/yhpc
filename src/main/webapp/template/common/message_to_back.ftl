<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<link rel="stylesheet" type="text/css" href="${contextPath}/ext/resources/css/ext-all.css" />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/easydialog/easydialog.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/easydialog/easydialog.min.js"></script>
</head>
<body>
<#--进度条-->
<div id="progress" style="display:none;border:1px solid #c0c0c0;width:350px;position:absolute;">
	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="0" bgcolor="#EEEEEE">
		<tr>
			<td colspan="2" style="height:20px;" bgcolor="#A6CFFD" align="center"><span style="color:#F8F2C7;font-weight:bold;font-size:12px">【系统提示】</span></td>
		</tr>
		<tr>
        	<td align="center" height="40" id="progressText" ></td> 
       </tr>
       <tr>
        	<td align="center" height="20">
        		<input name="yuran" type="button" style="cursor: pointer;" value="&nbsp;确&nbsp;&nbsp;认&nbsp;" onclick="rollBack();"/>　　
        	</td> 
       </tr>
	</table>
</div>
<script language="javascript">
	jQuery(document).ready(function(){//加载数据
		progressText("${action.getText(messageKey)}");
	})
	
    function rollBack(){
    	history.back(-2);  
    }
	
	//进度条弹出层
	function progressText(txt){
		$("#progressText").html(txt);
		easyDialog.open({
			container : 'progress',
			drag : false
		});
	};
</script>
 </#escape>
<@fkMacros.pageFooter />