<#macro showContentType ctype="text/html; charset=utf-8">
    <meta http-equiv="Content-Type" content="${ctype}" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
</#macro>

<#macro pageHeader>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产事故隐患排查治理信息系统</title>
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/new_css.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/mootools.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/full-validator.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
	<script>jQuery.noConflict();</script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.checkboxes.js"></script>
	</head>
	<!--<body oncontextmenu=self.event.returnValue=false>-->
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#macro pageHeader_pipe_statistic>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产事故隐患排查治理信息系统</title>
	<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.7.1.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.jtemplates.js"></script>
	<script>jQuery.noConflict();</script>
	</head>
	<body oncontextmenu=self.event.returnValue=false>
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]>
	<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
</#macro>

<#macro pageHeaderCas>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产综合监管平台</title>
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/new_css.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/mootools.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/full-validator.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
	<script>jQuery.noConflict();</script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.checkboxes.js"></script>
	</head>
	<!--<body oncontextmenu=self.event.returnValue=false>-->
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#macro company_pageheader>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-y:auto;overflow-x:auto;"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>宁波市安全生产综合监管服务平台</title>
</head>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
<script language="JavaScript" type=text/javascript src="${resourcePath}/js/function_all.js"></script>
<script language="JavaScript" type=text/javascript src="${resourcePath}/js/png.js"></script>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/thickbox-compressed.js"></script>
<link href="${resourcePath}/css/thickbox.css" rel="stylesheet" type="text/css" />
<link href="${resourcePath}/css/public.css" rel="stylesheet" type="text/css" />
<link href="${resourcePath}/css/other.css" rel="stylesheet" type="text/css" />
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
</#macro>

<#macro login_company_pageheader>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-y:auto;overflow-x:auto;"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>宁波市安全生产综合监管服务平台</title>
</head>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
<script language="JavaScript" type=text/javascript src="${resourcePath}/js/function_all.js"></script>
<script language="JavaScript" type=text/javascript src="${resourcePath}/js/png.js"></script>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script language="JavaScript"type="text/javascript" src="${resourcePath}/js/thickbox-compressed.js"></script>
<link href="${resourcePath}/css/thickbox.css" rel="stylesheet" type="text/css" />
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
</#macro>

<#macro pageHeaderStatistic>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.jtemplates.js"></script>
	<script>jQuery.noConflict();</script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.checkboxes.js"></script>
	</head>
	<!--<body oncontextmenu=self.event.returnValue=false>-->
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#macro pageHeaderPrint>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产隐患排查治理信息平台</title>
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" src="${resourcePath}/js/print.js"></script>
	</head>
	<!--<body oncontextmenu=self.event.returnValue=false>-->
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#macro pageHeaderPrintNew>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产隐患排查治理信息平台</title>
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" src="${resourcePath}/js/print_new.js"></script>
	</head>
	<!--<body oncontextmenu=self.event.returnValue=false>-->
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#macro pageHeaderAjax>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产隐患排查治理信息平台</title>
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${contextPath}/ext/adapter/prototype/prototype.js"></script>
	<script type="text/javascript" src="${contextPath}/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${contextPath}/ext/ext-all.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/mootools.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/full-validator.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
	</head>
	<body oncontextmenu=self.event.returnValue=false>
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#macro winLIKE>
	<script type="text/javascript">
		  WinLIKEerrorpage='${contextPath}/winlike/winlike/winman/hlp-error.html';
		  WinLIKEskinpath = "${contextPath}/winlike/skins/";
		  WinLIKEfilepath = "${contextPath}/winlike/winlike/";
	</script>
		<script src="${contextPath}/winlike/winlike/winman/wininit.js" type="text/javascript"></script>
		<script src="${contextPath}/winlike/winlike/winman/winman.js" type="text/javascript"></script>
		<!--IMG ID=ih_ SRC="${contextPath}/winlike/skins/trans.gif" style="z-Index:4000;position:absolute;left:0;top:0;width:100%;height:100%"-->
	<script type="text/javascript">
	window.onResize=WinLIKE.resizewindows;
	window.onload=WinLIKE.init;
	</script>
</#macro>

<#macro winlikeHeader>
	<script language="javascript">
	  WinLIKEerrorpage='${contextPath}/winlike/winlike/winman/hlp-error.html';
	  WinLIKEskinpath = "${contextPath}/winlike/skins/";
	  WinLIKEfilepath = "${contextPath}/winlike/winlike/";
	</script>
	<SCRIPT SRC="${contextPath}/winlike/winlike/winman/wininit.js"></SCRIPT>
	<SCRIPT SRC="${contextPath}/winlike/winlike/winman/winman.js"></SCRIPT>
	<!--IMG ID=ih_ SRC="${contextPath}/winlike/skins/trans.gif" style="z-Index:4000;position:absolute;left:0;top:0;width:100%;height:100%"-->
	<!--IMG ID=ig_ SRC="${resourcePath}/winlike/winlike/winman/load.gif" STYLE="position:absolute;left:35%;top:40%;z-Index:4001"-->
	<script type="text/javascript">
	window.onResize=WinLIKE.resizewindows;
	window.onload=WinLIKE.init;
	</script>	
</#macro>

<#macro loading>
<link rel="stylesheet" type="text/css" href="${resourcePath}/loading/style.css" />
<script src="${resourcePath}/loading/loading.js"></script>
<div id="loading">
	<div class="loading-indicator">
		地图正在加载中...
	</div>
</div>
</#macro>

<#macro pageFooter>
	</body>
	</html>
</#macro>

<!--You can only append a type to ValidatorType.when developing, don't delete any one. 
    summary: '<div id="summary" class="summary"></div>'
-->
<#macro formValidator formId  validateType=6>
	<script language="javascript">
		var formValidator=Validator.setup({   
		    form : '${formId}',
		    configs : 'attribute,tag',
		    <#switch validateType>
			  <#case 1>
			     warns : 'color,class,tips',
			     <#break>
			  <#case 2>
			     warns : 'color,alert',
			     <#break>
			  <#case 3>
			     warns : 'follow,color,class,tips',
			     <#break>
			  <#case 4>
			     warns : 'summary,color',
			     summary : { id : 'summary'},
			     <#break>
			  <#case 5>
			     warns : 'summary,follow,color',
			     summary : { id : 'summary'},
			     <#break>			     
			  <#default>
			     warns : 'follow,color,class',
			</#switch>
			color : {warn :'black', pass:'black'}
		});  
	</script>
</#macro>
 

<#macro muilt_select_js>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/muilt_select.js"></script>
</#macro>

<!--
动态生成多级联动区域下拉框(最大支持五级）搜索时
-->
<#macro searchselectAreas_fun selectedFirstAreaCode="" selectedSecondAreaCode="" selectedThirdAreaCode="" selectedFouthAreaCode="" selectedFifthAreaCode="" entityName="fkUserInfo.">
<script language="javascript">
		function initNextAreaSelect(thisSelectValue,nextSelectId,thisTagName,nextTagName){
				var nextObj=document.getElementById(nextSelectId);
				if(nextObj!=null&&nextObj!=undefined){
					nextObj.options.length=1;//只留<请选择选项>
					var thisAreas = xmlobject.responseXML.getElementsByTagName(thisTagName);
					for(var i=0;i<thisAreas.length;i++){
						if(thisAreas[i].getAttribute("code")==thisSelectValue){
	
							var nextAreas = thisAreas[i].getElementsByTagName(nextTagName);
							for(var j=1;j<=nextAreas.length;j++){
									var opt = new Option(nextAreas[j-1].getAttribute("name"),nextAreas[j-1].getAttribute("code")) ;
									nextObj.options[j] = opt ;
									opt = null ;
							}
							break;
						}
					}
				}
					
	  }
		
	  function initXMLHttpClient() {
		 var xmlhttp;//只留<请选择选项111>
		 try {
			// Mozilla / Safari / IE7
			xmlhttp = new XMLHttpRequest();
		 } catch (e) {
			// IE
				var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
										'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
										'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
				var success = false;
				for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
					try {
					   xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
					   success = true;
		            } catch (e) {
						//empty body
					}
				}
				if (!success) {
					throw new Error('Unable to create XMLHttpRequest.');
				 }
		  }
		  return xmlhttp;
		}
		
		var xmlobject=initXMLHttpClient();
		xmlobject.onreadystatechange = function() {//只留<请选择选项1212>
			if (xmlobject.readyState == 4) {
				if (xmlobject.status == 200) {
					var firstAreas = xmlobject.responseXML.getElementsByTagName('first-area');//only one
					var firstResult="";
					var secondResult="";
					var thirdResult="";
					var fouthResult="";
					var fifthResult="";
					var nextElement=firstAreas[0];
					//alert("是搜索"+firstAreas);
					if(nextElement!=null){
							firstResult='<select id="first-area" name="${entityName}firstArea">';
						//firstResult+='<option value="0">--请选择--</option>';//改为默认
						
						//经测试发现nextElement.firstChild不起作用，故直接用xmlobject.responseXML.getElementsByTagName('second-area')[0]来判断这个区域对象是否为空
						var secondAreas=xmlobject.responseXML.getElementsByTagName('second-area')[0];
						var thirdAreas = xmlobject.responseXML.getElementsByTagName('third-area')[0];
						var fouthAreas = xmlobject.responseXML.getElementsByTagName('fouth-area')[0];
						var fifthAreas = xmlobject.responseXML.getElementsByTagName('fifth-area')[0];
						
						
						//nextElement=nextElement.firstChild;
						if(secondAreas!=null){
								secondResult='<select id="second-area" name="${entityName}secondArea"  onchange="initNextAreaSelect(this.value,\'third-area\',\'second-area\',\'third-area\');">';
								secondResult+='<option value="0">--请选择--</option>';
								//nextElement=nextElement.firstChild;
							
								if(thirdAreas!=null){
										thirdResult='<select id="third-area" name="${entityName}thirdArea"  onchange="initNextAreaSelect(this.value,\'fouth-area\',\'third-area\',\'fouth-area\');">';
										thirdResult+='<option value="0">--请选择--</option>';
										//nextElement=nextElement.firstChild;
										
										if(fouthAreas!=null){
												fouthResult='<select id="fouth-area" name="${entityName}fouthArea" onchange="initNextAreaSelect(this.value,\'fifth-area\',\'fouth-area\',\'fifth-area\');">';
												fouthResult+='<option value="0">--请选择--</option>';
												//nextElement=nextElement.firstChild;
												if(fifthAreas!=null){
														fifthResult='<select id="fifth-area" name="${entityName}fifthArea"  onchange="initNextAreaSelect(this.value,\'sixth-area\',\'fifth-area\',\'sixth-area\');">';
														fifthResult+='<option value="0">--请选择--</option>';											
												}						
										}						
								}						
						}
				}
	
				if(firstAreas.length>0){//只留<请选择选项>1313
					for(var i=1;i<=firstAreas.length;i++){
						//first-area IF BEGIN	
						//if("${selectedFirstAreaCode}"!=""&&firstAreas[i-1].getAttribute("code")=="${selectedFirstAreaCode}"){			
							firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'" selected="selected">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
							var secondAreas = firstAreas[i-1].getElementsByTagName('second-area');
							for(var j=1;j<=secondAreas.length;j++){	
								//second-area IF BEGIN	
								if("${selectedSecondAreaCode}"!=""&&secondAreas[j-1].getAttribute("code")=="${selectedSecondAreaCode}"){			
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'" selected="selected">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
									var thirdAreas = secondAreas[j-1].getElementsByTagName('third-area');
									for(var k=1;k<=thirdAreas.length;k++){	
										//third-area IF BEGIN
										if("${selectedThirdAreaCode}"!=""&&thirdAreas[k-1].getAttribute("code")=="${selectedThirdAreaCode}"){			
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'" selected="selected">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
											var fouthAreas = thirdAreas[k-1].getElementsByTagName('fouth-area');
											for(var m=1;m<=fouthAreas.length;m++){	
												//fouth-area IF BEGIN
												if("${selectedFouthAreaCode}"!=""&&fouthAreas[m-1].getAttribute("code")=="${selectedFouthAreaCode}"){			
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'" selected="selected">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;					
													var fifthAreas = fouthAreas[m-1].getElementsByTagName('fifth-area');
													for(var n=1;n<=fifthAreas.length;n++){	
														//fifth-area IF BEGIN
														if("${selectedFifthAreaCode}"!=""&&fifthAreas[n-1].getAttribute("code")=="${selectedFifthAreaCode}"){			
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'" selected="selected">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;					
														}else{
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;
														}
														//fifth-area IF END
													}
												}else{
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;
												}
												//fouth-area IF END
											}
										}else{
										
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
										}
										//third-area IF END
									}
								}else{
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
								}
								//second-area IF END	
							}
						//}else{
						//	firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
						//}
						//first-area IF END   默认
					}
					var divArea = document.getElementById("div-area");
					var totalResult="";
					if(firstResult!=""){
						totalResult+=firstResult+"</select>&nbsp;&nbsp;";
					}	
					if(secondResult!=""){
						totalResult+=secondResult+"</select>&nbsp;&nbsp;";
					}
					if(thirdResult!=""){
						totalResult+=thirdResult+"</select>&nbsp;&nbsp;";
					}
					if(fouthResult!=""){
						totalResult+=fouthResult+"</select>&nbsp;&nbsp;";
					}
					if(fifthResult!=""){
						totalResult+=fifthResult+"</select>";
					}
					divArea.innerHTML=totalResult;
	
				}
			} else {
					alert('Loading Areas Error: ['+xmlobject.status+'] '+xmlobject.statusText);
			}
		}
	}
	xmlobject.open('GET','${allAreaXmlUrl}',true);
	xmlobject.send(null);

</script>
</#macro>


<!--
动态生成多级联动区域下拉框(最大支持五级）不是搜索
-->
<#macro selectAreas_fun selectedFirstAreaCode="" selectedSecondAreaCode="" selectedThirdAreaCode="" selectedFouthAreaCode="" selectedFifthAreaCode="" entityName="fkUserInfo.">
<script language="javascript">
		function initNextAreaSelect(thisSelectValue,nextSelectId,thisTagName,nextTagName){
				var nextObj=document.getElementById(nextSelectId);
				if(nextObj!=null&&nextObj!=undefined){
					nextObj.options.length=1;//只留<请选择选项>
					var thisAreas = xmlobject.responseXML.getElementsByTagName(thisTagName);
					for(var i=0;i<thisAreas.length;i++){
						if(thisAreas[i].getAttribute("code")==thisSelectValue){
	
							var nextAreas = thisAreas[i].getElementsByTagName(nextTagName);
							for(var j=1;j<=nextAreas.length;j++){
									var opt = new Option(nextAreas[j-1].getAttribute("name"),nextAreas[j-1].getAttribute("code")) ;
									nextObj.options[j] = opt ;
									opt = null ;
							}
							break;
						}
					}
				}
					
	  }
		
	  function initXMLHttpClient() {
		 var xmlhttp;//只留<请选择选项111>
		 try {
			// Mozilla / Safari / IE7
			xmlhttp = new XMLHttpRequest();
		 } catch (e) {
			// IE
				var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
										'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
										'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
				var success = false;
				for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
					try {
					   xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
					   success = true;
		            } catch (e) {
						//empty body
					}
				}
				if (!success) {
					throw new Error('Unable to create XMLHttpRequest.');
				 }
		  }
		  return xmlhttp;
		}
		
		var xmlobject=initXMLHttpClient();
		xmlobject.onreadystatechange = function() {//只留<请选择选项1212>
			if (xmlobject.readyState == 4) {
				if (xmlobject.status == 200) {
					var firstAreas = xmlobject.responseXML.getElementsByTagName('first-area');//only one
					var firstResult="";
					var secondResult="";
					var thirdResult="";
					var fouthResult="";
					var fifthResult="";
					
					var nextElement=firstAreas[0];
					if(nextElement!=null){
							firstResult='<select id="first-area" name="${entityName}firstArea">';
						//firstResult+='<option value="0">--请选择--</option>';//改为默认
						
					    //经测试发现nextElement.firstChild不起作用，故直接用xmlobject.responseXML.getElementsByTagName('second-area')[0]来判断这个区域对象是否为空
						var secondAreas=xmlobject.responseXML.getElementsByTagName('second-area')[0];
						var thirdAreas = xmlobject.responseXML.getElementsByTagName('third-area')[0];
						var fouthAreas = xmlobject.responseXML.getElementsByTagName('fouth-area')[0];
						var fifthAreas = xmlobject.responseXML.getElementsByTagName('fifth-area')[0];
					
						//nextElement=nextElement.firstChild;
						if(secondAreas!=null){
								secondResult='<select id="second-area" name="${entityName}secondArea"  onchange="initNextAreaSelect(this.value,\'third-area\',\'second-area\',\'third-area\');">';
								secondResult+='<option value="0">--请选择--</option>';
								//nextElement=nextElement.firstChild;
								if(thirdAreas!=null){
										thirdResult='<select id="third-area" name="${entityName}thirdArea"  onchange="initNextAreaSelect(this.value,\'fouth-area\',\'third-area\',\'fouth-area\');">';
										thirdResult+='<option value="0">--请选择--</option>';
										//nextElement=nextElement.firstChild;
										if(fouthAreas!=null){
												fouthResult='<select id="fouth-area" name="${entityName}fouthArea" onchange="initNextAreaSelect(this.value,\'fifth-area\',\'fouth-area\',\'fifth-area\');">';
												fouthResult+='<option value="0">--请选择--</option>';
												//nextElement=nextElement.firstChild;
												if(fifthAreas!=null){
														fifthResult='<select id="fifth-area" name="${entityName}fifthArea"  onchange="initNextAreaSelect(this.value,\'sixth-area\',\'fifth-area\',\'sixth-area\');">';
														fifthResult+='<option value="0">--请选择--</option>';											
												}						
										}						
								}						
						}
				}
	
				if(firstAreas.length>0){//只留<请选择选项>1313
					for(var i=1;i<=firstAreas.length;i++){
						//first-area IF BEGIN	
						//if("${selectedFirstAreaCode}"!=""&&firstAreas[i-1].getAttribute("code")=="${selectedFirstAreaCode}"){			
							firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'" selected="selected">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
							var secondAreas = firstAreas[i-1].getElementsByTagName('second-area');
							for(var j=1;j<=secondAreas.length;j++){	
								//second-area IF BEGIN	
								if("${selectedSecondAreaCode}"!=""&&secondAreas[j-1].getAttribute("code")=="${selectedSecondAreaCode}"){			
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'" selected="selected">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
									var thirdAreas = secondAreas[j-1].getElementsByTagName('third-area');
									for(var k=1;k<=thirdAreas.length;k++){	
										//third-area IF BEGIN
										if("${selectedThirdAreaCode}"!=""&&thirdAreas[k-1].getAttribute("code")=="${selectedThirdAreaCode}"){			
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'" selected="selected">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
											var fouthAreas = thirdAreas[k-1].getElementsByTagName('fouth-area');
											for(var m=1;m<=fouthAreas.length;m++){	
												//fouth-area IF BEGIN
												if("${selectedFouthAreaCode}"!=""&&fouthAreas[m-1].getAttribute("code")=="${selectedFouthAreaCode}"){			
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'" selected="selected">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;					
													var fifthAreas = fouthAreas[m-1].getElementsByTagName('fifth-area');
													for(var n=1;n<=fifthAreas.length;n++){	
														//fifth-area IF BEGIN
														if("${selectedFifthAreaCode}"!=""&&fifthAreas[n-1].getAttribute("code")=="${selectedFifthAreaCode}"){			
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'" selected="selected">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;					
														}else{
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;
														}
														//fifth-area IF END
													}
												}else{
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;
												}
												//fouth-area IF END
											}
										}else{
										
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
										}
										//third-area IF END
									}
								}else{
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
								}
								//second-area IF END	
							}
						//}else{
						//	firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
						//}
						//first-area IF END   默认
					}
					var divArea = document.getElementById("div-area");
					var totalResult="";
					if(firstResult!=""){
						totalResult+=firstResult+"</select>&nbsp;&nbsp;";
					}	
					if(secondResult!=""){
						totalResult+=secondResult+"</select>&nbsp;&nbsp;";
					}
					
					if(thirdResult!=""){
						totalResult+=thirdResult+"</select>&nbsp;&nbsp;";
					}
					if(fouthResult!=""){
						totalResult+=fouthResult+"</select>&nbsp;&nbsp;";
					}
					if(fifthResult!=""){
						totalResult+=fifthResult+"</select>";
					}
					divArea.innerHTML=totalResult;
	
				}
			} else {
					alert('Loading Areas Error: ['+xmlobject.status+'] '+xmlobject.statusText);
			}
		}
	}
	xmlobject.open('GET','${areaXmlUrl}',true);
	xmlobject.send(null);

</script>
</#macro>

<!--
动态生成多级联动区域下拉框(最大支持五级）---全部可以访问-无权限控制 ----管道隐患时用
-->
<#macro selectAreas_fun_pipe selectedFirstAreaCode="" selectedSecondAreaCode="" selectedThirdAreaCode="" selectedFouthAreaCode="" selectedFifthAreaCode="" entityName="fkUserInfo.">
<script language="javascript">

		function initNextAreaSelect(thisSelectValue,nextSelectId,thisTagName,nextTagName){//只留<请选择选项>14
		  
				var nextObj=document.getElementById(nextSelectId);
				if(nextObj!=null&&nextObj!=undefined){
					nextObj.options.length=1;//只留<请选择选项>
					var thisAreas = xmlobject.responseXML.getElementsByTagName(thisTagName);
					for(var i=0;i<thisAreas.length;i++){
						if(thisAreas[i].getAttribute("code")==thisSelectValue){
	
							var nextAreas = thisAreas[i].getElementsByTagName(nextTagName);
							for(var j=1;j<=nextAreas.length;j++){
									var opt = new Option(nextAreas[j-1].getAttribute("name"),nextAreas[j-1].getAttribute("code")) ;
									nextObj.options[j] = opt ;
									opt = null ;
							}
							break;
						}
					}
				}
					
	  }
		
	  function initXMLHttpClient() {//只留<请选择选项>15
		 var xmlhttp;
		 try {
			// Mozilla / Safari / IE7
			xmlhttp = new XMLHttpRequest();
		 } catch (e) {
			// IE
				var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
										'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
										'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
				var success = false;
				for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
					try {
					   xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
					   success = true;
		            } catch (e) {
						//empty body
					}
				}
				if (!success) {
					throw new Error('Unable to create XMLHttpRequest.');
				 }
		  }
		  return xmlhttp;
		}
		
		var xmlobject=initXMLHttpClient();//只留<请选择选项>16
		xmlobject.onreadystatechange = function() {
			if (xmlobject.readyState == 4) {
				if (xmlobject.status == 200) {
					var firstAreas = xmlobject.responseXML.getElementsByTagName('first-area');//only one
					var firstResult="";
					var secondResult="";
					var thirdResult="";
					var fouthResult="";
					var fifthResult="";
					var nextElement=firstAreas[0];
					if(nextElement!=null){
							firstResult='<select id="first-area" name="${entityName}firstArea">';
						//firstResult+='<option value="0">--请选择--</option>';//改为默认
						//nextElement=nextElement.firstChild;
						
						//经测试发现nextElement.firstChild不起作用，故直接用xmlobject.responseXML.getElementsByTagName('second-area')[0]来判断这个区域对象是否为空
						var secondAreas=xmlobject.responseXML.getElementsByTagName('second-area')[0];
						var thirdAreas = xmlobject.responseXML.getElementsByTagName('third-area')[0];
						var fouthAreas = xmlobject.responseXML.getElementsByTagName('fouth-area')[0];
						var fifthAreas = xmlobject.responseXML.getElementsByTagName('fifth-area')[0];
						
						
						if(secondAreas!=null){
								secondResult='<select id="second-area" name="${entityName}secondArea"  onchange="initNextAreaSelect(this.value,\'third-area\',\'second-area\',\'third-area\');">';
								secondResult+='<option value="0">--请选择--</option>';
								//nextElement=nextElement.firstChild;
								if(thirdAreas!=null){
										thirdResult='<select id="third-area" name="${entityName}thirdArea"  onchange="initNextAreaSelect(this.value,\'fouth-area\',\'third-area\',\'fouth-area\');">';
										thirdResult+='<option value="0">--请选择--</option>';
										//nextElement=nextElement.firstChild;
										if(fouthAreas!=null){
												fouthResult='<select id="fouth-area" name="${entityName}fouthArea" onchange="initNextAreaSelect(this.value,\'fifth-area\',\'fouth-area\',\'fifth-area\');">';
												fouthResult+='<option value="0">--请选择--</option>';
												//nextElement=nextElement.firstChild;
												if(fifthAreas!=null){
														fifthResult='<select id="fifth-area" name="${entityName}fifthArea"  onchange="initNextAreaSelect(this.value,\'sixth-area\',\'fifth-area\',\'sixth-area\');">';
														fifthResult+='<option value="0">--请选择--</option>';											
												}						
										}						
								}						
						}
				}
	
				if(firstAreas.length>0){//只留<请选择选项>17
					for(var i=1;i<=firstAreas.length;i++){
						//first-area IF BEGIN	
						if("${selectedFirstAreaCode}"!=""&&firstAreas[i-1].getAttribute("code")=="${selectedFirstAreaCode}"){			
							firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'" selected="selected">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
							var secondAreas = firstAreas[i-1].getElementsByTagName('second-area');
							for(var j=1;j<=secondAreas.length;j++){	
								//second-area IF BEGIN	
								if("${selectedSecondAreaCode}"!=""&&secondAreas[j-1].getAttribute("code")=="${selectedSecondAreaCode}"){			
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'" selected="selected">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
									var thirdAreas = secondAreas[j-1].getElementsByTagName('third-area');
									for(var k=1;k<=thirdAreas.length;k++){	
										//third-area IF BEGIN
										if("${selectedThirdAreaCode}"!=""&&thirdAreas[k-1].getAttribute("code")=="${selectedThirdAreaCode}"){			
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'" selected="selected">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
											var fouthAreas = thirdAreas[k-1].getElementsByTagName('fouth-area');
											for(var m=1;m<=fouthAreas.length;m++){	
												//fouth-area IF BEGIN
												if("${selectedFouthAreaCode}"!=""&&fouthAreas[m-1].getAttribute("code")=="${selectedFouthAreaCode}"){			
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'" selected="selected">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;					
													var fifthAreas = fouthAreas[m-1].getElementsByTagName('fifth-area');
													for(var n=1;n<=fifthAreas.length;n++){	
														//fifth-area IF BEGIN
														if("${selectedFifthAreaCode}"!=""&&fifthAreas[n-1].getAttribute("code")=="${selectedFifthAreaCode}"){			
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'" selected="selected">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;					
														}else{
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;
														}
														//fifth-area IF END
													}
												}else{
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;
												}
												//fouth-area IF END
											}
										}else{
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
										}
										//third-area IF END
									}
								}else{
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
								}
								//second-area IF END	
							}
						//}else{
						//	firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
						//}
						//first-area IF END   默认
					}
					var divArea = document.getElementById("div-area");
					var totalResult="";
					if(firstResult!=""){
						totalResult+=firstResult+"</select>&nbsp;&nbsp;";
					}
					if(secondResult!=""){
						totalResult+=secondResult+"</select>&nbsp;&nbsp;";
					}
					if(thirdResult!=""){
						totalResult+=thirdResult+"</select>&nbsp;&nbsp;";
					}
					if(fouthResult!=""){
						totalResult+=fouthResult+"</select>&nbsp;&nbsp;";
					}
					if(fifthResult!=""){
						totalResult+=fifthResult+"</select>";
					}
					divArea.innerHTML=totalResult;
	
				}
			} else {
					alert('Loading Areas Error: ['+xmlobject.status+'] '+xmlobject.statusText);
			}
		}
	}
	xmlobject.open('GET','${areaXmlUrl}',true);
	xmlobject.send(null);

</script>
</#macro>

<#--直接生成3.4级-->
<#macro selectAreas_fun2 selectedThirdAreaCode="" selectedFouthAreaCode="" entityName="fkUserInfo.">
<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<script language="javascript">
var thirdValue = '${selectedThirdAreaCode}';
var fouthValue = '${selectedFouthAreaCode}';
var entityName = '${entityName}';
var beforeInitArea = null;
var q = jQuery.noConflict();
	q.get("${areaXmlUrl}", function(data){
		var doc = q(data);
		var code = doc.find("third-area").attr("code");
		var html = "";
		html += "<select id='thirdArea' name='" + entityName + "thirdArea' >";
		doc.find("third-area").each(function(){
			var name = q(this).attr("name");
			var value = q(this).attr("code");
			var selected = "";
			if (value == thirdValue){
				selected = "selected";
			}
			html += "<option value='"+ value +"' "+selected+">"+ name +"</option>"
		});
		html += "</select>"
		q("#div-area").html(html);

		q("#thirdArea").change(function(){
			showChildrenByCode(this);
		}).change();
		
		if(beforeInitArea!=null){
			beforeInitArea();
		}
	});
	
	function showChildrenByCode(obj){
		var tagName = obj.getAttribute("id");
		if (tagName == "thirdArea"){
			tagName = "fouthArea";
		}else{
			return;
		} 

		q.get("${areaXmlUrl}", function(data){
			var doc = q(data);
			var html = "";
			doc.find("*[code='" + obj.value + "']").children().each(function(){
				var name = q(this).attr("name");
				var value = q(this).attr("code");
				var selected = "";
				if (value == thirdValue || value == fouthValue){
					selected = "selected";
				}
				html += "<option value='"+ value +"' "+ selected +">"+ name +"</option>"
			});
			
			if (html.length == 0){//如果没有数据，返回空，不显示select
				return;
			}
			html = "<option value=''>--请选择--</option>" + html;
			if (q("#" + tagName).length == 0){
				var html = "<select id='"+ tagName +"' name='" + entityName + tagName +"' >" + html + "</select>";
				
				q("#div-area select:last").after("&nbsp;&nbsp;" + html);		
					q("#"+ tagName).change(function(){
					showChildrenByCode(this);
				}).change();
			}else{
				q("#" + tagName).html("");
				q("#" + tagName).html(html);
			}
			
		});
		
	}
</script>
</#macro>


<#macro selectAreas_fun1 selectedFirstAreaCode="" selectedSecondAreaCode="" selectedThirdAreaCode="" selectedFouthAreaCode="" selectedFifthAreaCode="" entityName="fkUserInfo.">
<script language="javascript">

		function initNextAreaSelect(thisSelectValue,nextSelectId,thisTagName,nextTagName){		//只留<请选择选项>23
		   
				var nextObj=document.getElementById(nextSelectId);
				if(nextObj!=null&&nextObj!=undefined){
					nextObj.options.length=1;//只留<请选择选项>
					var thisAreas = xmlobject.responseXML.getElementsByTagName(thisTagName);
					for(var i=0;i<thisAreas.length;i++){
						if(thisAreas[i].getAttribute("code")==thisSelectValue){
	
							var nextAreas = thisAreas[i].getElementsByTagName(nextTagName);
							for(var j=1;j<=nextAreas.length;j++){
									var opt = new Option(nextAreas[j-1].getAttribute("name"),nextAreas[j-1].getAttribute("code")) ;
									nextObj.options[j] = opt ;
									opt = null ;
							}
							break;
						}
					}
				}
					
	  }
		
	  function initXMLHttpClient() {//只留<请选择选项>24
		 var xmlhttp;
		 try {
			// Mozilla / Safari / IE7
			xmlhttp = new XMLHttpRequest();
		 } catch (e) {
			// IE
				var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
										'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
										'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
				var success = false;
				for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
					try {
					   xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
					   success = true;
		            } catch (e) {
						//empty body
					}
				}
				if (!success) {
					throw new Error('Unable to create XMLHttpRequest.');
				 }
		  }
		  return xmlhttp;
		}
		
		var xmlobject=initXMLHttpClient();//只留<请选择选项>25
		xmlobject.onreadystatechange = function() {
			if (xmlobject.readyState == 4) {
				if (xmlobject.status == 200) {
					var firstAreas = xmlobject.responseXML.getElementsByTagName('first-area');//only one
					var firstResult="";
					var secondResult="";
					var thirdResult="";
					var fouthResult="";
					var fifthResult="";
					var nextElement=firstAreas[0];
					if(nextElement!=null){
					firstResult="";
							//firstResult='<select id="first-area" name="${entityName}firstArea">';
						//firstResult+='<option value="0">--请选择--</option>';//改为默认
						//nextElement=nextElement.firstChild;
						
						//经测试发现nextElement.firstChild不起作用，故直接用xmlobject.responseXML.getElementsByTagName('second-area')[0]来判断这个区域对象是否为空
						var secondAreas=xmlobject.responseXML.getElementsByTagName('second-area')[0];
						var thirdAreas = xmlobject.responseXML.getElementsByTagName('third-area')[0];
						var fouthAreas = xmlobject.responseXML.getElementsByTagName('fouth-area')[0];
						var fifthAreas = xmlobject.responseXML.getElementsByTagName('fifth-area')[0];
						
						if(secondAreas!=null){
								secondResult='<select id="second-area" name="${entityName}secondArea" style="width:80px;" onchange="initNextAreaSelect(this.value,\'third-area\',\'second-area\',\'third-area\');">';
								secondResult+='<option value="0">--请选择--</option>';
								//nextElement=nextElement.firstChild;
								if(thirdAreas!=null){
										thirdResult='<select id="third-area" name="${entityName}thirdArea" style="width:80px;" onchange="initNextAreaSelect(this.value,\'fouth-area\',\'third-area\',\'fouth-area\');">';
										thirdResult+='<option value="0">--请选择--</option>';
										//nextElement=nextElement.firstChild;
										if(fouthAreas!=null){
												fouthResult='<select id="fouth-area" name="${entityName}fouthArea" onchange="initNextAreaSelect(this.value,\'fifth-area\',\'fouth-area\',\'fifth-area\');">';
												fouthResult+='<option value="0">--请选择--</option>';
												//nextElement=nextElement.firstChild;
												if(fifthAreas!=null){
														fifthResult='<select id="fifth-area" name="${entityName}fifthArea"  onchange="initNextAreaSelect(this.value,\'sixth-area\',\'fifth-area\',\'sixth-area\');">';
														fifthResult+='<option value="0">--请选择--</option>';											
												}						
										}						
								}						
						}
				}
	
				if(firstAreas.length>0){//只留<请选择选项>26
					for(var i=1;i<=firstAreas.length;i++){
						//first-area IF BEGIN	
						//if("${selectedFirstAreaCode}"!=""&&firstAreas[i-1].getAttribute("code")=="${selectedFirstAreaCode}"){			
							//firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'" selected="selected">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
							firstResult+="";
							var secondAreas = firstAreas[i-1].getElementsByTagName('second-area');
							for(var j=1;j<=secondAreas.length;j++){	
								//second-area IF BEGIN	
								if("${selectedSecondAreaCode}"!=""&&secondAreas[j-1].getAttribute("code")=="${selectedSecondAreaCode}"){			
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'" selected="selected">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
									var thirdAreas = secondAreas[j-1].getElementsByTagName('third-area');
									for(var k=1;k<=thirdAreas.length;k++){	
										//third-area IF BEGIN
										if("${selectedThirdAreaCode}"!=""&&thirdAreas[k-1].getAttribute("code")=="${selectedThirdAreaCode}"){			
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'" selected="selected">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
											var fouthAreas = thirdAreas[k-1].getElementsByTagName('fouth-area');
											for(var m=1;m<=fouthAreas.length;m++){	
												//fouth-area IF BEGIN
												if("${selectedFouthAreaCode}"!=""&&fouthAreas[m-1].getAttribute("code")=="${selectedFouthAreaCode}"){			
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'" selected="selected">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;					
													var fifthAreas = fouthAreas[m-1].getElementsByTagName('fifth-area');
													for(var n=1;n<=fifthAreas.length;n++){	
														//fifth-area IF BEGIN
														if("${selectedFifthAreaCode}"!=""&&fifthAreas[n-1].getAttribute("code")=="${selectedFifthAreaCode}"){			
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'" selected="selected">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;					
														}else{
														    <#if dataAccess.fifthAreaAccess>
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;
														    </#if>
														}
														//fifth-area IF END
													}
												}else{
												   <#if dataAccess.fouthAreaAccess>
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;
												   </#if>
												}
												//fouth-area IF END
											}
										}else{
											<#if dataAccess.thirdAreaAccess>
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
										    </#if>
										}
										//third-area IF END
									}
								}else{
								   <#if dataAccess.secondAreaAccess>
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
								   </#if>
								}
								//second-area IF END	
							}
						//}else{
						//	firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
						//}
						//first-area IF END   默认
					}
					var divArea = document.getElementById("div-area");
					var totalResult="";
					if(firstResult!=""){
						totalResult+=firstResult+"</select>&nbsp;&nbsp;";
					}
					if(secondResult!=""){
						totalResult+=secondResult+"</select>&nbsp;&nbsp;";
					}
					if(thirdResult!=""){
						totalResult+=thirdResult+"</select>&nbsp;&nbsp;";
					}
					if(fouthResult!=""){
						totalResult+=fouthResult+"</select>&nbsp;&nbsp;";
					}
					if(fifthResult!=""){
						totalResult+=fifthResult+"</select>";
					}
					divArea.innerHTML=totalResult;
	
				}
			} else {
					alert('Loading Areas Error: ['+xmlobject.status+'] '+xmlobject.statusText);
			}
		}
	}
	xmlobject.open('GET','${areaXmlUrl}',true);
	xmlobject.send(null);

</script>
</#macro>

<#--输出区域值-->
<#macro getSelectArea code="">
<#escape x as (x)!><#if code??>
<script>
     
	var arr = areaObj.getArea('${code?trim}');
	
	if(arr){
		var value = arr[0];
		if (value && value != 'undefined'){
			document.write(value);
		}
	}
	</script>
	
<#else>
&nbsp;
	
</#if></#escape>
</#macro>

<#--初始化区域XML-->
<#macro initAreaXML>
<script type="text/javascript">
function get(id){return document.getElementById(id)};
var areaObj=new Area("${areaXmlUrl}");
</script>
</#macro>

<#macro pageMapHeader>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产综合监管服务平台</title>
	<link href="${resourcePath}/css/public.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/map_style.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/wbox/wbox.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/mootools.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.7.1.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.checkboxes.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/map.js"></script>
	<script type="text/javascript" src="${resourcePath}/js/wbox-min.js"></script> 
	<script>jQuery.noConflict();</script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.3&services=true"></script>
	</head>
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
</#macro>

<#macro pageEnterpriseMapHeader>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@showContentType />
	<title>宁波市安全生产综合监管服务平台</title>
	<link href="${resourcePath}/css/public.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/map_style.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/mootools.js"></script>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/validator/js/full-validator.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/public.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="${resourcePath}/js/global.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/map_qy.js"></script>
	<script>jQuery.noConflict();</script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery.checkboxes.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.3&services=true"></script>
	</head>
	<!--<body oncontextmenu=self.event.returnValue=false>-->
	<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
	<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]>
	<!--#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]-->
	<!--#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]-->
</#macro>

<#--2013.12.10-->
<#-- 用户代理公共资源文件  -->
<#macro proxyUserCommonResource>
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/default/js/extjs3.4/css/ext-all.css" />
<script type="text/javascript" src="${contextPath}/resources/default/js/extjs3.4/ext-base.js"></script>
<script type="text/javascript" src="${contextPath}/resources/default/js/extjs3.4/ext-all.js"></script>
<script type="text/javascript" src="${contextPath}/resources/default/js/extjs3.4/ext-lang-zh_CN.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/default/js/extjs3.4/ui/ext-ui.js"></script>
</#macro>

<#-- 用户代理列表相关文件  -->
<#macro proxyUserListView>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/lhgdialog4.2/lhgdialog.js"></script>
<script type="text/javascript" src="${contextPath}/resources/default/js/user/user.js"></script>
</#macro>

<#-- 用户代理设置相关文件  -->
<#macro proxyUserSetView>
<script type="text/javascript" src="${contextPath}/resources/default/js/user/userproxy.js"></script>
</#macro>

<#-- 列表页备注文字 -->
<#macro remark>
<font color=red><strong>注: 不若列表页的记录总数与统计表上的不一致,则刷新统计表以获取实时数据。</strong></font>
</#macro>

