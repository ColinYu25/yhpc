<%@ page contentType="text/html; charset=utf-8" %>
<html>
<body>
<form action="/nbyhpc/company/duanxin_send.xhtml" method="post">
	手机号：<input name="phone"><br/>
	消息内容：<input name="message"><br/>
	<input type="submit" value="发送">
</form>
<form action="/nbyhpc/company/duanxin_sendMonth.xhtml" method="post">
<input type="submit" value="发送月度未完成（慎点）">
</form>
<form action="/nbyhpc/company/duanxin_sendQuarter.xhtml" method="post">
<input type="submit" value="发送上季度未完成季报（慎点）">
</form>
<div>${result}</div>
<div style="color: red">
01：短消息发送内容为空；<br/>
02: 接收号码为空；<br/>
03：用户单位短消息系统的发送人帐号为空；<br/>
04：短信平台接入代码不存在；<br/>
05：客户端IP不符合；<br/>
06：接入密码错误；<br/>
07：短消息使用单位已被禁止；<br/>
08：短消息使用单位下发送用户已被禁止；<br/>
（不禁用，除本地用户禁用外，外部连接用户不予禁用，仅记录）统计各部门用户及用户的短信收发数<br/>
09：发送量过多已被禁止。
</div>
</body>
</html>