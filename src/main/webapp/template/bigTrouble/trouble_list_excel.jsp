<%@ page contentType="application/msexcel; charset=utf-8" %>
<!-- 以上这行设定本网页为excel格式的网页 -->
<%
   String name="重大隐患.xls";
   name = new  String(name.getBytes("GBK"),"iso8859-1");   //中文转换成ISO8859-1不然会乱码
   response.setHeader("Content-disposition","attachment; filename="+name);
   //以上这行设定传送到前端浏览器时的文件名称
   //就是靠这一行，让前端浏览器以为接收到一个excel文件
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<title>重大隐患</title>
<body>	
<table width="98%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="7" align="center" style="font-size:18px"><strong>
		重大隐患列表
		</strong>
    </td>
  </tr>
</table>
<table width="98%" border="1" cellpadding="0" cellspacing="0">
  <tr>
	<th width="4%">序号</th>
	<th width="4%">地区</th>
	<th width="4%">行业与领域</th>
	<th width="15%">单位名称</th>
	<th width="15%">地址</th>
	<th width="40%">隐患内容</th>
	<th width="40%">落实治理目标任务</th>
	<th width="40%">落实治理经费物资</th>
	<th width="40%">落实治理机构人员</th>
	<th width="40%">落实治理时间要求</th>
	<th width="40%">落实安全措施应急预案</th>
	<th width="9%">时间</th>
	</tr>
	<c:forEach var="list" items="${bigTroubles}" varStatus="status">
	<tr>
	<td  align="center">${status.count}&nbsp;</td>
	<td><p>${list.chargeMan}&nbsp;</p></td>
	<td><p><c:if test="${list.tradeType==1}">金属非金属矿山企业</c:if>
		   <c:if test="${list.tradeType==2}">尾矿库</c:if>
		   <c:if test="${list.tradeType==3}">冶金企业</c:if>
		   <c:if test="${list.tradeType==4}">有色企业</c:if>
		   <c:if test="${list.tradeType==5}">石油天然气开采企业</c:if>
		   <c:if test="${list.tradeType==6}">其中重大基础设施</c:if>
	       <c:if test="${list.tradeType==7}">生产企业</c:if>
           <c:if test="${list.tradeType==8}">经营企业和单位</c:if>
           <c:if test="${list.tradeType==9}">存储企业和单位(仓储业)</c:if>
		   <c:if test="${list.tradeType==10}">使用危化品的化工企业</c:if>
		   <c:if test="${list.tradeType==11}">使用危化品的医药生产企业</c:if>
		   <c:if test="${list.tradeType==12}">使用危化品的其他企业或单位</c:if>
		   <c:if test="${list.tradeType==13}">道路运输企业和单位</c:if>
		   <c:if test="${list.tradeType==14}">生产企业</c:if>
		   <c:if test="${list.tradeType==15}">经营(批发)企业</c:if>
		   <c:if test="${list.tradeType==16}">经营(零售)企</c:if>
		   <c:if test="${list.tradeType==17}">其他企业</c:if>
		   <c:if test="${list.tradeType==18}">道路运输企业</c:if>
		   <c:if test="${list.tradeType==19}">公路养护施工企业</c:if>
		   <c:if test="${list.tradeType==20}">水上运输企业</c:if>
		   <c:if test="${list.tradeType==21}">铁路运输企业</c:if>
		   <c:if test="${list.tradeType==22}">航空公司</c:if>
		   <c:if test="${list.tradeType==23}">机场和油料企业</c:if>
		   <c:if test="${list.tradeType==24}">渔业企业</c:if>
		   <c:if test="${list.tradeType==25}">农机行业</c:if>
		   <c:if test="${list.tradeType==26}">水利工程</c:if>
		   <c:if test="${list.tradeType==27}">其中：水库</c:if>
		   <c:if test="${list.tradeType==28}">学校</c:if>
		   <c:if test="${list.tradeType==29}">商场、市场等人员密集场所</c:if>
		   <c:if test="${list.tradeType==30}">建筑施工企业</c:if>
		   <c:if test="${list.tradeType==31}">民爆器材生产企业</c:if>
		   <c:if test="${list.tradeType==32}">电力企业</c:if>
		   <c:if test="${list.tradeType==33}">机械制造企业</c:if>
		   <c:if test="${list.tradeType==34}">其他企业和单位</c:if>
		   <c:if test="${list.tradeType==35}">道路交通事故多发点段</c:if>
		   <c:if test="${list.tradeType==36}">道路交通安全设施</c:if>
		   <c:if test="${list.tradeType==37}">临水临崖危险路段</c:if>
		   <c:if test="${list.tradeType==38}">城市公共交通</c:if>
		   <c:if test="${list.tradeType==39}">燃气</c:if>
		   <c:if test="${list.tradeType==40}">旅游行业</c:if>
		   <c:if test="${list.tradeType==41}">铁路</c:if>
		   <c:if test="${list.tradeType==42}">医院</c:if>
		   <c:if test="${list.tradeType==43}">"三合一"场所</c:if>
		   <c:if test="${list.tradeType==44}">出租房</c:if>
	</td>
	<td><p>${list.companyName}&nbsp;</p></td>
	<td><p>${list.address}&nbsp;</p></td>
	<td><p>${list.content}&nbsp;</p></td>
	<td>${list.targetTask==0?'否':'是'}&nbsp;</td>
	<td>${list.goods==0?'否':'是'}&nbsp;</td>
	<td>${list.worker==0?'否':'是'}&nbsp;</td>
	<td>${list.governDate==0?'否':'是'}&nbsp;</td>
	<td>${list.safetyMethod==0?'否':'是'}&nbsp;</td>
	<td>${list.reportMonth}&nbsp;</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>