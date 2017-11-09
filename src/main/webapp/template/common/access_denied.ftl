<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="utf-8" />
<title>安生公共服务平台</title>
<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />
<script type="text/javascript" src="../ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="../ext/ext-all.js"></script>
</head>
<body>
<script type="text/javascript">
Ext.onReady(function(){
    Ext.MessageBox.show({
       title: '403(禁止)错误',
       msg: '你没有对此资源的访问权限!',
       buttons: Ext.MessageBox.OK,
       fn: rollBack,
       icon: Ext.MessageBox.WARNING
    });

    function rollBack(){
    	self.location="../index.jsp";  
    }
});
</script>
</body>
</html>