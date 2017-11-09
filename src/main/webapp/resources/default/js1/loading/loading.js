//判断页面是否加载完毕，如果加载完毕，就删除加载信息的DIV
function document.onreadystatechange()
{
	try
	{
		if (document.readyState == "complete") 
		{
	     	delNode("loading");
	    }
    }
    catch(e)
    {
    	alert("页面加载失败");
    }
}

//删除指定的DIV
function  delNode(nodeId)
{   
  try
  {   
	  var div =document.getElementById(nodeId);  
	  if(div !==null)
	  {
		  div.parentNode.removeChild(div);   
		  div=null;    
		  CollectGarbage(); 
	  }  
  }
  catch(e)
  {   
  	   alert("删除ID为"+nodeId+"的节点出现异常");
  }   
}