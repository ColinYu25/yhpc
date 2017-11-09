function get(str) {
	if (document.all(str)) {
		return document.all(str);
	} else {
		return document.getElementById(str);
	}
}
function getTag() {
	return document.getElementsByTagName(arguments[0]);
}
function loadWindows(hre,w,h,l,t,win_n,win_t){
		var default_wn = 'win_default';
		var default_w = 450;
		var default_h = 220;
		var default_l = 0;
		var default_t = 0;
		var default_wt = (win_t)?'<font size="2">'+win_t+'</font>':'<font size="2">新增页</font>';
		var j=new WinLIKE.window(default_wt,(l&&l!=0)?l:default_l,(t&&t!=0)?t:default_t,(w&&w!=0)?w:default_w,(h&&h!=0)?h:default_h);
		j.Min=true;
		j.Siz=false;
		j.Ski='round';
		j.Adr=hre; 
		j.LD=false;
		j.SD=false;
		j.Nam=(win_n)?win_n:default_wn;
		WinLIKE.createwindow(j,true);
}
function closeWindows()  {
	for(var i=0;i<window.parent.WinLIKE.windows.length;i++){if(i!=0)window.parent.WinLIKE.windows[i].close();}
}
function openSonTree() {
	if(!get(arguments[0]))
		return false;
	var tree = get("rightFrameTree");
	if (!tree && getTag('table') && getTag('table').length == 1)
	{
		tree = getTag('table')[0];
	}
	if(tree && tree.rows)
		for(var i=0; i<tree.rows.length; i++)
			if (tree.rows[i].id!=""
				&&tree.rows[i].id.substring(0,2)=="tr"
				&&tree.rows[i].style.display!="none"
				&&tree.rows[i].id!=arguments[0]){
				if(arguments[1]){
					for (var j=1; j<arguments.length; j++) {
						if(arguments[j]!=tree.rows[i].id
							&& j== arguments.length-1)
							tree.rows[i].style.display = "none";
					}
				} else {
					tree.rows[i].style.display = "none";
				}
			}
	if(get(arguments[0]).length) {
		if(get(arguments[0])[0].style.display=='none'){
			for(var i=0;i<get(arguments[0]).length;i++){get(arguments[0])[i].style.display='';}
		} 
		else{
			for(var i=0;i<get(arguments[0]).length;i++){get(arguments[0])[i].style.display='none';}
		}
	} else {
		if(get(arguments[0]).style.display=='none')
			get(arguments[0]).style.display = '';
		else
			get(arguments[0]).style.display = 'none';
	}
	
}

function guidang () {
	if(!chooseCheckBox())
		return false;
	var now = (new Date()).getYear() + '-' + ((((new Date()).getMonth()+1)<=9)?('0'+((new Date()).getMonth()+1)):((new Date()).getMonth()+1))+'-'+(((new Date()).getDate()<=9)?('0'+(new Date()).getDate()):(new Date()).getDate())+' '+(((new Date()).getHours()<=9)?('0'+(new Date()).getHours()):(new Date()).getHours())+':'+(((new Date()).getMinutes()<=9)?('0'+(new Date()).getMinutes()):(new Date()).getMinutes());
	var checkboxs = getTag('input');
	var j=0;
	for(var i=0;i<checkboxs.length; i++) {
		if(checkboxs[i].type=='checkbox'){
			j ++;
			if(checkboxs[i].checked) {
				if(get('td_s_'+j))
					get('td_s_'+j).innerHTML = '已归档';
				if(get('td_d_'+j))
					get('td_d_'+j).innerHTML = now;	
				checkboxs[i].checked = !checkboxs[i].checked;
			}
		}
	}
	alert('归档成功！');
}

function chooseCheckBox() {
	var checkboxs = getTag('input');
	var noChoose = true;
	for(var i=0;i<checkboxs.length; i++) {
		if(checkboxs[i].type=='checkbox'){
			if(checkboxs[i].checked) {
				noChoose = false;
			}
		}
	}
	if(noChoose) {
		alert('请选择对应操作的复选框！');
		return false;
	}
	return true;
}

function checkThenForward() {
	if(chooseCheckBox()) {
		if(arguments[0] && arguments[0] != undefined){
			window.location = arguments[0];
		}
	}
}

function getNowDate() {
	var param = 'dd';
	if (arguments[0] && arguments[0] != '')
		param = arguments[0];
	var now = ((arguments[0]=='yy'||arguments[0]=='MM'||arguments[0]=='dd'||arguments[0]=='hh'||arguments[0]=='mm'||arguments[0]=='ss')?(new Date()).getYear()+((arguments[0]=='MM'||arguments[0]=='dd'||arguments[0]=='hh'||arguments[0]=='mm'||arguments[0]=='ss')?'-'+((((new Date()).getMonth()+1)<=9)?('0'+((new Date()).getMonth()+1)):((new Date()).getMonth()+1))+((arguments[0]=='dd'||arguments[0]=='hh'||arguments[0]=='mm'||arguments[0]=='ss')?'-'+(((new Date()).getDate()<=9)?('0'+(new Date()).getDate()):(new Date()).getDate())+((arguments[0]=='hh'||arguments[0]=='mm'||arguments[0]=='ss')?' '+(((new Date()).getHours()<=9)?('0'+(new Date()).getHours()):(new Date()).getHours())+((arguments[0]=='mm'||arguments[0]=='ss')?':'+(((new Date()).getMinutes()<=9)?('0'+(new Date()).getMinutes()):(new Date()).getMinutes())+((arguments[0]=='ss')?(':'+((new Date().getSeconds()<=9)?('0'+new Date().getSeconds()):new Date().getSeconds())):''):''):''):''):''):(new Date()));
	
	return now;
}