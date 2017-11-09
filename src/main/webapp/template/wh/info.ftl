<script type="text/javascript">
	var info='${info}';
    if(info=='updateUserName'){
      alert("您单位的用户名和统一社会信用代码（工商注册号）不一致，危化企业年度报告录入前请先进入后台管理菜单修改用户名!");
	  window.parent.rightFrame.location.href="../company/updaeUserName.xhtml";
    }else if(info=='updateUserNameSetupNumber'){
      alert("您单位的用户名和组织机构代码不一致，隐患填报前请先进入后台管理菜单修改用户名!");
	  window.parent.rightFrame.location.href="../company/updaeUserName.xhtml";
    }else if(info=='updateCompany'){
       alert("危化企业年度报告录入前请先进入企业管理菜单完善企业基本信息!");
	  window.parent.rightFrame.location.href="../company/loadCompany.xhtml";
    }else if(info=='updatePassword'){
      alert("为了确保信息系统使用安全性，业务操作前，请您设置一个高强度、不易猜测的密码作为您的系统登录密码！");
	  window.parent.rightFrame.location.href="../person/updatePasswordInit.xhtml";
    }
   
</script>
