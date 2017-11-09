<%@ page language="java" pageEncoding="GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.21cp.net/fn" prefix="cpinfofn" %>
<html>
<head>	
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>广告模块</title>
<script language="javascript">

function ok(){
	if(checkForm())
	{
		window.returnValue = null;
		window.close();
	}
}
</script>
</head>
<BODY>

  <table width="375" border=0 cellpadding=0 cellspacing=0 id=tabDialogSize>
    <tr>
      <td width="20"><p>&nbsp;</p>
        <p>        </td>
      <td width="277">&nbsp;      </td>
      <td width="80" rowspan="2" valign="bottom"><p>
        &nbsp;&nbsp;<input name="button" type=submit  style=" width:50px;" onClick="ok()" value='确定'>
 </p>
        <p>       
          &nbsp;&nbsp;<input name="button" type=button style=" width:50px;" onClick="window.close();" value='取消'>
      </p></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td valign="top">
      <p>广告位：
        <select name="local" id="local">
          <c:forEach items="${cpinfofn:loadAllAdverLocation()}" var="item" >
            <option value="${item.id}">${item.name}</option>
          </c:forEach>
        </select>
      </p></td>
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

	return true;

}

function getHtml(){
var local=document.getElementById("local").value;

var html="{广告盒子:";
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