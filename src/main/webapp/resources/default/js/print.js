function initParam(portrait,top,right,bottom,left){
	     // try{
		  document.all.factory.printing.header = "";
		  document.all.factory.printing.footer = "";
		  document.all.factory.printing.portrait = portrait;//false横true纵
		  document.all.factory.printing.topMargin = top;
		  document.all.factory.printing.rightMargin = right;
		  document.all.factory.printing.bottomMargin = bottom;  
		  document.all.factory.printing.leftMargin = left;
		 // }
		  /*catch(e){
			  alert("你的浏览器不能自动安装打印控件,请查看打印帮助");
			  return;
		  }*/
}
	
function showPrint(){
	      try{
		  document.all.factory.printing.Print(false);
		  
		  }catch(e){
			  alert("你的浏览器不能自动安装打印控件,请查看打印帮助");
			  return;
		  }
}
function printpreview(){ 
          try{
          document.all.factory.printing.Preview();
		  }catch(e){
			  alert("你的浏览器不能自动安装打印控件,请查看打印帮助");
			  return;
		  }
} 
function setPrint(){
	      try{
		  document.all.factory.printing.PageSetup();
		  }catch(e){
			  alert("你的浏览器不能自动安装打印控件,请查看打印帮助");
			  return;
		  }
}

//浏览器自带
/*function setWinPrint(){
		document.all.WebBrowser.ExecWB(8,1);
}
function winPrint(){
		document.all.WebBrowser.ExecWB(6,6);
}
function winPrintPreview(){ 
	    document.all.WebBrowser.ExecWB(7,1); 　 
} */

//jatoolsPrinter控件
//打印文档对象
var myreport ={ 
    				documents: document,    // 打印页面(div)们在本文档中
    				copyrights  :    '杰创软件拥有版权 www.jatools.com'         // 版权声明必须
    			  };
				  
function doPrint(how)
{
	var jatoolsPrinter = navigator.userAgent.indexOf('MSIE')>-1 ? ojatoolsPrinter : ejatoolsPrinter;//判别IE或者非IE	
    if(typeof(jatoolsPrinter.page_div_prefix)=='undefined'){
        alert("请按页顶上的黄色提示下载ActiveX控件.如果没有提示请按以下步骤设置ie.\n 工具-> internet 选项->安全->自定义级别,设置 ‘下载未签名的 ActiveX ’为'启用'状态")
        return ;
    }
    // 调用打印方法
    if(how == 'printPreview'){
    	jatoolsPrinter.printPreview(myreport);   // 打印预览
	}else if(how == 'print'){
   		jatoolsPrinter.print(myreport ,false);   // 打印前弹出打印设置对话框
	}else{
   		jatoolsPrinter.print(myreport ,false);       // 不弹出对话框打印
	}
}

function printParam(topMagin,leftMargin,bottomMargin,rightMagin,orientation){
settings = new Object(); 
settings.topMargin = topMagin; 
settings.leftMargin = leftMargin; 
settings.bottomMargin = bottomMargin; 
settings.rightMargin = rightMagin; 
settings.orientation = orientation;
myreport.print_settings=settings;
}

document.write("<OBJECT ID='ojatoolsPrinter' name='ojatoolsPrinter' CLASSID='CLSID:B43D3361-D975-4BE2-87FE-057188254255' codebase='../resources/default/print/jatoolsPrinter.cab#version=2,1,0,5' width='0' height='0'><embed name='ejatoolsPrinter' type='application/x-vnd.jatoolsPrinter' pluginspage='../resources/default/print/jatools.xpi' width='0' height='0'/></OBJECT>");