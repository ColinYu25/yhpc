<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<HTML>
<HEAD>
<style type="text/css">.cacher {behavior:url(#default#userdata);}</style>
<script type="text/javascript" src="${resourcePath}/js/common.js"></script>
<script language="JavaScript">
<!--

function SymError()
{
  return true;
}

window.onerror = SymError;

var SymRealWinOpen = window.open;

function SymWinOpen(url, name, attributes)
{
  return (new Object());
}

window.open = SymWinOpen;

//--> 
</script>

<script language="javascript">
var COOKIE_NAME = "x";

function setCookie(name, value, expiry, path, domain, secure){
	var nameString = name + "=" + value;
	var expiryString = (expiry == null) ? "" : " ;expires = "+ expiry.toGMTString();
	var pathString = (path == null) ? "" : " ;path = "+ path;
	var domainString = (path == null) ? "" : " ;domain = "+ domain;
	var secureString = (secure) ?";secure" :"";
	document.cookie = nameString + expiryString + pathString + domainString + secureString;
}

function getCookie(sName) {
	var aCookie = document.cookie.split("; ");
	for (var i=0; i < aCookie.length; i++) {
		var aCrumb = aCookie[i].split("=");
		if (sName == aCrumb[0]) 
		return unescape(aCrumb[1]);
	}
	return "";
}

function remainLocale() {
	var layoutObj = new Array();
	var i = 0;
	while (i < top.frames.length) {
		var frame_obj = eval("top.frames[" + i + "]");
		layoutObj[i] = frame_obj.name + "^" +  frame_obj.location;
		i++;
	}
	saveDataToCache(layoutObj.valueOf());
	setCookie(COOKIE_NAME, 'y');
}

var CACHE_DATA_KEY = "cData";
var CACHE_DATA_NAME = "cDataName";

function setExpire(obj) {	
	var oTimeNow = new Date();
	oTimeNow.setMinutes(oTimeNow.getMinutes() + 5);
	var sExpirationDate = oTimeNow.toUTCString();
	obj.expires = sExpirationDate;
}

function saveDataToCache(v) {
	var cacheData = get("cacheData");
	//setExpire(cacheData);
	cacheData.setAttribute(CACHE_DATA_NAME, v);
	cacheData.save(CACHE_DATA_KEY);	
}

function loadDataFromCache() {	
	var cacheData = get("cacheData");
	cacheData.load(CACHE_DATA_KEY);
	return cacheData.getAttribute(CACHE_DATA_NAME);
}

function removeDataFromCache() {
	try {
		var cacheData = get("cacheData");
		cacheData.removeAttribute(CACHE_DATA_NAME);		
		cacheData.save(CACHE_DATA_KEY);
	} catch(e) {};
}

function restoreLocale() {
	var cv = getCookie(COOKIE_NAME);
	var str = loadDataFromCache();
	if (cv && str && str.length>0) {
		var frame_obj = str.split(",");

		var layoutObj = new Array();
		for (i=0; i<frame_obj.length; i++) {
			var piece = frame_obj[i].split("^");
			if (piece[0]==window.name) continue;
			eval("top." + piece[0] + ".location = '" + piece[1] + "'");
		}
	}
	removeDataFromCache();
}

var flag = false;
function shift_status()
{
	if(flag)
	{
		if(screen.width>1024)
			parent.main.cols = "178,16,*";
		else if(screen.width>800)	
			parent.main.cols = "178,16,*";
		else
			parent.main.cols = "178,16,*";
		document.all.menuSwitch1.src='${resourcePath}/img/13x79.gif';
		document.all.menuSwitch1.title='隐藏';
	}
	else
	{
		parent.main.cols = "0,16,*";
		document.all.menuSwitch1.src='${resourcePath}/img/13x79_2.gif';
		document.all.menuSwitch1.title='显示';
	}

	flag = !flag;
}
</script>
</HEAD>

<BODY onclick="shift_status()" leftmargin=0 topmargin=0 onLoad="restoreLocale()" oncontextmenu=self.event.returnValue=false>
<span class="cacher" id="cacheData" name="cacheData"></span>
<table width="13" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="157"><img src="${resourcePath}/img/13x157.gif" width="13" height="157"></td>
  </tr>
  <tr>
    <td height="110" background="${resourcePath}/img/13x1.gif"></td>
  </tr>
  <tr>
    <td height="79" id=menuSwitch style="cursor:hand"><img src="${resourcePath}/img/13x79.gif" width="13" height="79" id=menuSwitch1></td>
  </tr>
  <tr>
    <td background="${resourcePath}/img/13x1.gif"></td>
  </tr>
</table>
</BODY>
</HTML>

<script language="JavaScript">
<!--
var SymRealOnLoad;
var SymRealOnUnload;

function SymOnUnload()
{
  window.open = SymWinOpen;
  if(SymRealOnUnload != null)
     SymRealOnUnload();
}

function SymOnLoad()
{
  if(SymRealOnLoad != null)
     SymRealOnLoad();
  window.open = SymRealWinOpen;
  SymRealOnUnload = window.onunload;
  window.onunload = SymOnUnload;
}

SymRealOnLoad = window.onload;
window.onload = SymOnLoad;

//-->
</script>
