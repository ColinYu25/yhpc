<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<script type="text/javascript"
	src="resources/default/js/extjs3.4/ext-base.js"></script>
<script type="text/javascript"
	src="resources/default/js/extjs3.4/ext-all.js"></script>
<script type="text/javascript"
	src="resources/default/wbox1.0/jquery1.4.2.js"></script>
<script type="text/javascript" src="resources/default/wbox1.0/wbox.js"></script>
<script type="text/javascript" src="resources/default/js/des.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/default/wbox1.0/wbox/wbox.css" />

<script type="text/javascript" src="ext/adapter/prototype/prototype.js"></script>
<link href="resources/default/css/style.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="resources/default/js/extjs3.4/css/ext-all.css" />
</head>


<body>
<table>
	<tr>
		<td>手动调用da_company同步t_company方法:<input type="button"
			onClick="loadSyn('initErrorCompanyInfo');"
			name="initErrorCompanyInfo" value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用t_company同步da_company方法:<input type="button"
			onClick="loadSyn('loadSynDaCompany');" name="loadSynDaCompany"
			value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用企业基本信息同步到中心平台方法:<input type="button"
			onClick="loadSyn('swapCompanyService');" name="swapCompanyService"
			value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用一般隐患信息同步到中心平台方法:<input type="button"
			onClick="loadSyn('swapNormalDangerService');"
			name="swapNormalDangerService" value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用重大隐患信息同步到中心平台方法:<input type="button"
			onClick="loadSyn('swapDangerService');" name="swapDangerService"
			value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用挂牌隐患信息同步到中心平台方法:<input type="button"
			onClick="loadSyn('swapGpDangerService');" name="swapGpDangerService"
			value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用隐患季报单向同步到中心平台方法:<input type="button"
			onClick="loadSyn('swapReportService');" name="swapReportService"
			value="执行"></input></td>
	</tr>
	<tr>
		<td>手动调用隐患危化单向同步到中心平台方法:<input type="button"
			onClick="loadSyn('swapWhService');" name="swapWhService" id="swapWhService" value="执行"></input>
		</td>
	</tr>
	
	<tr>
		<td>
		<br/>
		<br/>
		<br/>
		</td>
	</tr>
	<tr>
		<td><font color="red">手动调用统计报表发送到省局(谨慎操作，只有当统计报表数据发送到省局失败的时候，才需要重新发送)<font/>
			<input type="button" onClick="loadSyn('sendData');" name="sendData" id="sendData" value="执行1"></input>
			<input type="button" onClick="loadSyn('sendData2');" name="sendData2" id="sendData2" value="执行2"/>
		</td>
	</tr>
</table>
</body>
</html>
<script language="javascript">
function loadSyn(fv){

	var url="";
	if(fv=='initErrorCompanyInfo'){
		url="ajax/nosecuritycheck/syn_initErrorCompanyInfo.xhtml";
	}else if(fv=='loadSynDaCompany'){
		url="ajax/nosecuritycheck/syn_loadSynDaCompany.xhtml";
	}else if(fv=='swapCompanyService'){
		url="ajax/nosecuritycheck/syn_swapCompanyService.xhtml";
	}else if(fv=='swapNormalDangerService'){
		url="ajax/nosecuritycheck/syn_swapNormalDangerService.xhtml";
	}else if(fv=='swapDangerService'){
		url="ajax/nosecuritycheck/syn_swapDangerService.xhtml";
	}else if(fv=='swapGpDangerService'){
		url="ajax/nosecuritycheck/syn_swapGpDangerService.xhtml";
	}else if(fv=='swapReportService'){
		url="ajax/nosecuritycheck/syn_swapReportService.xhtml";
	}else if(fv=='swapWhService'){
		url="ajax/nosecuritycheck/syn_swapWhService.xhtml";
	}else if(fv=='sendData'){
		//点过一次让按钮变的不可以用，防止重复提交
		document.getElementById("sendData").style.display="none";
		url="ajax/nosecuritycheck/syn_sendData.xhtml";
	}else if(fv=='sendData2'){
		//点过一次让按钮变的不可以用，防止重复提交
		document.getElementById("sendData2").style.display="none";
		url="ajax/nosecuritycheck/syn_sendData2.xhtml";
	}
   
    new Ajax.Request(url,{method: "post",onSuccess:function(transport){
		var response =transport.responseText.evalJSON();
	    //判断企业是否删除了所有的行业
		if (response.success) {
			Ext.Msg.alert('系统消息','执行成功！');
		}else{
			Ext.Msg.alert('系统消息','执行失败！');
		}
	  }
	});
}
</script>

