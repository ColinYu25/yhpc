var g_object;
var inover=false;

var values="";
function checkAll(e, itemName)
{
  var bb;
  var aa = document.getElementsByName(itemName);  
  for (var i=0; i<aa.length; i++)
  {
	aa[i].checked = e.checked;	
  }
  if (!e.checked)
  {
	g_object.value="";
  }
  else
  {
    g_object.value="All";
  }
}

function checkItem(e, allName)
{
  var all = document.getElementsByName(allName)[0];
  if(!e.checked) 
  {all.checked = false;
   showItem("b1");
  }
  else
  {
	showItem("b1");
    var aa = document.getElementsByName(e.name);
    for (var i=0; i<aa.length; i++)  
		if(!aa[i].checked) return;	 
		all.checked = true;
		g_object.value="All";
	  
  }
  
}

function showItem(itemName)
{
var aa = document.getElementsByName(itemName);
for (var i=0;i<aa.length ;i++)
{
	if (aa[i].checked)
	{
		if (values=="")
		{
			values = aa[i].value;
		}
		else
		{
			values = values + "," + aa[i].value;
		}
	}
}
g_object.value=values;
values="";
}

function init()
{	 
     document.writeln("<div name=\"down_checkbox\" id=\"down_checkbox\"  style=\"display:none\" style=\"LEFT: 69px; POSITION: absolute; TOP: 159px;Z-INDEX:99\" onBlur=\"hilayer()\"  onMouseout=\"lostlayerfocus()\"></div>");
}

function set_down_checkbox(tempArr)
{
	var temp_str;
	var temp_Arr;	
	
	 if (tempArr.indexOf(",")==-1)
	{
		temp_Arr=tempArr;
		alert(tempArr);
	 }
	 else
	{		
		temp_Arr=tempArr.split(",");		
	}
	window.down_checkbox.innerHTML="";
	temp_str="<table border=\"0\" bgcolor=\"#FFFFFF\" bordercolor=\"white\"><tr><td>";
	temp_str+="<tr><td align=\"left\"><input name=\"b1All\" id=\"b1All\" type=\"checkbox\" size=\"2\" language=\"javascript\" onmouseover=\"overcolor(this);\" onMouseout=\"hilayer()\" onClick=\"checkAll(this,'b1');\">全选</td></tr>"
	for (var i=0;i<temp_Arr.length;i++)
	{	
	temp_str+="<tr><td align=\"left\"><input name=\"b1\" id=\"b1\" type=\"checkbox\" size=\"2\" language=\"javascript\" onmouseover=\"overcolor(this);\" onMouseout=\"hilayer()\" onClick=\"checkItem(this,'b1All');\" value="+temp_Arr[i]+">"+temp_Arr[i]+"</td></tr>"
	}
	temp_str+="</table>";
	window.down_checkbox.innerHTML=temp_str;
}

//t_object：点击的对象；
function show_down_checkbox(eP,t_object,strArr)
{
window.down_checkbox.style.display="";
window.down_checkbox.style.zIndex=99
var s,cur_d
var eT = eP.offsetTop;  
var eH = eP.offsetHeight+eT;  
var dH = window.down_checkbox.style.pixelHeight;  
var sT = document.body.scrollTop; 
var sL = document.body.scrollLeft; 
event.cancelBubble=true;
window.down_checkbox.style.posLeft = event.clientX-event.offsetX+sL-5;  
window.down_checkbox.style.posTop = event.clientY-event.offsetY+eH+sT-5;
if (window.down_checkbox.style.posLeft+window.down_checkbox.clientWidth>document.body.clientWidth) window.down_checkbox.style.posLeft+=eP.offsetWidth-window.down_checkbox.clientWidth;
g_object=t_object;
set_down_checkbox(strArr);
window.down_checkbox.style.display="block";
window.down_checkbox.focus();
}


function overcolor(obj)
{
  if (obj.style.cursor=="hand") obj.style.color = "#DDDDDD";

  inover=true;
  window.down_checkbox.focus();

}

function outcolor(obj)
{
	obj.style.color = "#000000";
	inover=false;

}

function hilayer()
{
	if (inover==false)
	{
		var lay=document.all.down_checkbox;
		lay.style.display="none";
	}
}
function getlayerfocus()
{
	inover=true;
}
function lostlayerfocus()
{
	inover=false;	
}
init();  
  