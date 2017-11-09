//定时判断 cookie中的usertype 和 aw 是否有所变更 刷新页面
function getcookie(e){
	var t=document.cookie.split("; ");
	for (var n=0; n<t.length; n++) {
		var r=t[n].split("=");
		if (r[0]==e) {
			return unescape(r[1]);
		}
	}
}
function dog(){
	var e = getcookie("JSESSIONID");
	e!=""&&sid!=e&&(sid!=""&&reloadIgnoreTicket(),sid=e);
}

function reloadIgnoreTicket() {
	window.location = "loadMainWorkSpace.xhtml";
}
var sid="";
self.setInterval("dog()",1e3);
