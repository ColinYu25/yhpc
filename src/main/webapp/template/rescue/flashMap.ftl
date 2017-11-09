<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>map</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.olMap {
	height:630px;
}
#stem {
	position:absolute;
	width:100%;
	height:630px;
	z-index:1;
	text-align:center;
	left: 0px;
	top: 0px;
	background-image:url('${resourcePath}/images/mapBG.jpg');
}
#flash {
	position:absolute;
	width:100%;
	height:630px;
	z-index:999;
	text-align:center;
	left: 0px;
	top: 0px;
}
-->
</style>
<link href="${resourcePath}/css/map.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="mapbody">
</div>
<div id="stem"></div>
<div id="flash"><table width="640" height="600" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="640" height="600">
          <param name="movie" value="${resourcePath}/flash/map${flashCode}.swf" />
          <param name="quality" value="high" />
          <param name="wmode" value="transparent" />
          <embed src="${resourcePath}/flash/map${flashCode}.swf" width="640" height="600" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed>
        </object></td>
      </tr>
    </table></div>
</body>
</html>
