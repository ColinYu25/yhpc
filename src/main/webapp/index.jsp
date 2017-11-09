<%@ page contentType="text/html; charset=utf-8" %>
<script>
if(top!=self){
	if(top==self.parent){
		self.parent.location="<%=request.getContextPath()%>/login.jsp";  
	}else if(top==self.parent.parent){
		self.parent.parent.location="<%=request.getContextPath()%>/login.jsp";  
	}
}else{  
	self.location="<%=request.getContextPath()%>/login.jsp";  
}
</script>