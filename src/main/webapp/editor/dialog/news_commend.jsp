<%@ page language="java" pageEncoding="GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.21cp.net/fn" prefix="cpinfofn" %>
<html>
<head>	
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����Ƽ�ģ��</title>
<script language=javascript src="dialog.js"></script>
<script language="javascript">
document.write ("<link href='../language/" + AvailableLangs["Active"] + ".css' type='text/css' rel='stylesheet'>");
document.write ("<link href='dialog.css' type='text/css' rel='stylesheet'>");

var sAction = "INSERT";
var sTitle = lang["DlgComInsert"];
function InitDocument(){
	AvailableLangs.TranslatePage(document);
	adjustDialog();
}

function ok(){
	if(checkForm())
	{
		dialogArguments.insertHTML(getHtml());
		window.returnValue = null;
		window.close();
	}
}
</script>
</head>
<BODY onLoad="InitDocument()">

  <table width="375" border=0 cellpadding=0 cellspacing=0 id=tabDialogSize>
    <tr>
      <td width="20"><p>&nbsp;</p>
        <p>        </td>
      <td width="277">&nbsp;�����Ƽ�λ�ã�
        <select name="local" id="local">
			<c:forEach items="${cpinfofn:loadAllNewsCommendLocation()}" var="item" >
				<option value="${item.id}">${item.name}</option>
			</c:forEach>
        </select></td>
      <td width="80" rowspan="2" valign="bottom"><p>
        &nbsp;&nbsp;<input name="button" type=submit  style=" width:50px;" onClick="ok()" value='ȷ��'>
 </p>
        <p>       
          &nbsp;&nbsp;<input name="button" type=button style=" width:50px;" onClick="window.close();" value='ȡ��'>
      </p></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><fieldset>
      <legend>����</legend>
      <p>��ȣ�
        &nbsp;<input name="col" type="text" id="col">
      </p>
      <p>������
        &nbsp;<input name="row" type="text" id="row">
      </p>
      <p>�Ƿ���ʵʱ�䣺
        <input name="showTime" type="checkbox" id="showTime" value="true">
      &nbsp;</p>	  
      <p>icon��
        &nbsp;<input name="ico" id="ico" type="text">
      </p>		    	  
      <p>&nbsp;</p>
      </fieldset></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><div id="warn"></div></td>
      <td valign="bottom">&nbsp;</td>
    </tr>
  </table>
  <p>&nbsp;</p>

<p>&nbsp;</p>
</body>
</html>
<script language="javascript">
function checkForm(){
var col=document.getElementById("col").value;
var row=document.getElementById("row").value;
var str="";
if(row==""){
	str="����������ģ����Ҫ��ʾ��������";
}
if(col=="" && !issafe(col))
{
	str="����������ģ����Ҫ��ʾ�Ŀ�ȣ�";
}
	if(str.length>0){
	document.getElementById("warn").innerText=str;
	return false;
}
else
{
	return true;
}
}

function getHtml(){
var local=document.getElementById("local").value;
var col=document.getElementById("col").value;
var row=document.getElementById("row").value;
var showTime=document.getElementById("showTime");
var ico=document.getElementById("ico").value;
var html="{�����Ƽ�����:";
html+=" col=\""+col+"\"";
html+=" row=\""+row+"\"";
html+=" ico=\""+ico+"\"";
if(showTime.checked){
	html+=" showTime=\"true\"";
}
html+=" local=\""+local+"\"}";
//alert(html)
return html;

}

function issafe(str){
    for (var i=0;i<str.length;i++) {
	     var substr = str.charAt(i);
		 alert(substr);
		 var reg = /[0-9]/;
		 alert(reg.test(substr)); 
		 }
}

function test(){
alert(getHtml());

}

</script>