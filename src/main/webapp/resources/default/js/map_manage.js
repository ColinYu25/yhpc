//更新安管机构和人员
function updateSm(){
	var id = $('*[name="smId"]').val();
	var cid = $('*[name="cId"]').val();
	var createTime = $('*[name="createTime"]').val();
	var smSet = $('*[name="smSet"]').val();
	var fulltimeStaff = $("*[name='fulltimeStaff']").val();
	var mainPrincipal = $("*[name='mainPrincipal']").val();
	var mpCertificate = $("*[name='mpCertificate']").val();
	var mpValidity = $("*[name='mpValidity']").val();
	var securityPrincipal = $("*[name='securityPrincipal']").val();
	var spCertificate = $("*[name='spCertificate']").val();
	var spValidity = $("*[name='spValidity']").val();
	$.ajax({
		type : "post",
		method : "post",
		dataType : "json",
		data:{
			"id":id,
			"cid":cid, 
			"createTime":createTime, 
			"smSet":smSet, 
			"fulltimeStaff":fulltimeStaff,
			"mainPrincipal":mainPrincipal,
			"mpCertificate":mpCertificate,
			"mpValidity":mpValidity,
			"securityPrincipal":securityPrincipal,
			"spCertificate":spCertificate,
			"spValidity":spValidity
		},
		url : "../securityManage/updateSecurityManage.xhtml",
		success : function(data) {
		}
	});
}
//更新工商管理
function updateIm(){
	var id = $('*[name="imId"]').val();
	var cid = $('*[name="cId"]').val();
	var createTime = $('*[name="createTime2"]').val();
	var insuredNum = $('*[name="insuredNum"]').val();
	var injuryNum = $("*[name='injuryNum']").val();
	$.ajax({
		type : "post",
		method : "post",
		dataType : "json",
		data:{
			"id":id,
			"cid":cid, 
			"createTime":createTime, 
			"insuredNum":insuredNum, 
			"injuryNum":injuryNum
		},
		url : "../injuryManage/updateInjuryManage.xhtml",
		success : function(data) {
		}
	});
}