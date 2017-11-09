
var myreport = {
		/*
        要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
        作为首页打印id 为'page2'的作为第二页打印            */
    documents: document,
    copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
}; 

function printParam(topMagin,leftMargin,bottomMargin,rightMagin,orientation){
	settings = new Object(); 
	settings.topMargin = topMagin; 
	settings.leftMargin = leftMargin; 
	settings.bottomMargin = bottomMargin; 
	settings.rightMargin = rightMagin; 
	settings.orientation = orientation;
	myreport.print_settings=settings;
}

function doPrint(how) { 
    var jatoolsPrinter = document.getElementById("jatoolsPrinter");
    if (how == 'printPreview') {
    	jatoolsPrinter.printPreview(myreport); // 打印预览
    } else if (how == 'print') {
    	jatoolsPrinter.print(myreport, true); // 打印前弹出打印设置对话框
    } else {
    	jatoolsPrinter.print(myreport, false); // 直接打印，不弹出打印机设置对话框
    }
}

document.write("<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='../resources/default/jatools/jatoolsPrinter.cab#version=8,6,0,0' width='0' height='0'></OBJECT>");