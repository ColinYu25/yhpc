<%@ page contentType="text/html;charset=gb2312"%>

<HTML>
<HEAD>
<TITLE>eWebEditor �� ��׼����ʾ��</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel='stylesheet' type='text/css' href='example.css'>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></HEAD>
<BODY>
<FORM method="post" name="myform" action="do.jsp" onSubmit="doSubmit()">
<TABLE width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#68E1FF">
<TR>
	<TD colspan="4">
		<INPUT type="hidden" name="content1" value="${item.content}">
	  <IFRAME ID="eWebEditor1" src="../Editor/ewebeditor.htm?id=content&style=coolblue&extcss=http://localhost:8080/cpinfo/TemplateLib/css/css.css" frameborder="0" scrolling="no" width="1000" height="650"></IFRAME></TD>
	</TR>
<TR>
	<TD width="6%">&nbsp;</TD>
    <TD width="22%">��������Ҫ������ļ�����</TD>
    <TD width="54%" align=left><input name="fileName" type="text" size="50" maxlength="50" >
      ����:index.jsp��</TD>
    <TD width="18%" rowspan="2" align=middle><input type="submit" name="Submit" value="�ύ"></TD>
</TR>
<TR>
  <TD>&nbsp;</TD>
  <TD>��������Ҫ�����ҳ��ģ�����ƣ�</TD>
  <TD align=left><input name="fileName2" type="text" size="50" >
    ����:��Ѷ��ҳ��</TD>
  </TR>
</TABLE>
</FORM>
</BODY>
</HTML>
<script language="javascript">
	function doSubmit(){
		
		eWebEditor1.setMode('CODE')
	}
</script>