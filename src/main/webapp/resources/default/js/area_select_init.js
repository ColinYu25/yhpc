function initXMLHttpClient() {
	 var xmlhttp;
	 try {
		// Mozilla / Safari / IE7
		xmlhttp = new XMLHttpRequest();
	 } catch (e) {
		// IE
			var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
									'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
									'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
			var success = false;
			for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
				try {
				   xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
				   success = true;
	            } catch (e) {
					//empty body
				}
			}
			if (!success) {
				throw new Error('Unable to create XMLHttpRequest.');
			 }
	  }
	  return xmlhttp;
	}
	
	var xmlobject=initXMLHttpClient();
	xmlobject.onreadystatechange = function() {
		if (xmlobject.readyState == 4) {
			if (xmlobject.status == 200) {
				var rootAreas = xmlobject.responseXML.getElementsByTagName('root-area');//only one
				var firstResult="";
				var secondResult="";
				var thirdResult="";
				var fouthResult="";
				var fifthResult="";
				var nextElement=rootAreas[0].firstChild;
				if(nextElement!=null){
						firstResult='<select id="first-area" name="fkUserInfo.firstArea" onchange="initNextAreaSelect(this.value,\'second-area\',\'first-area\',\'second-area\');">';
						firstResult+='<option value="0">--请选择--</option>';
						nextElement=nextElement.firstChild;
						if(nextElement!=null){
								secondResult='<select id="second-area" name="fkUserInfo.secondArea" onchange="initNextAreaSelect(this.value,\'third-area\',\'second-area\',\'third-area\');">';
								secondResult+='<option value="0">--请选择--</option>';
								nextElement=nextElement.firstChild;
								if(nextElement!=null){
										thirdResult='<select id="third-area" name="fkUserInfo.thirdArea" onchange="initNextAreaSelect(this.value,\'fouth-area\',\'third-area\',\'fouth-area\');">';
										thirdResult+='<option value="0">--请选择--</option>';
										nextElement=nextElement.firstChild;
										if(nextElement!=null){
												fouthResult='<select id="fouth-area" name="fkUserInfo.fouthArea" onchange="initNextAreaSelect(this.value,\'fifth-area\',\'fouth-area\',\'fifth-area\');">';
												fouthResult+='<option value="0">--请选择--</option>';
												nextElement=nextElement.firstChild;
												if(nextElement!=null){
														fifthResult='<select id="fifth-area" name="fkUserInfo.fifthArea" onchange="initNextAreaSelect(this.value,\'sixth-area\',\'fifth-area\',\'sixth-area\');">';
														fifthResult+='<option value="0">--请选择--</option>';											
												}						
										}						
								}						
						}
				}

				if(rootAreas.length>0){
					var firstAreas = rootAreas[0].getElementsByTagName('first-area');
					for(var i=1;i<=firstAreas.length;i++){
						//first-area IF BEGIN	
						if("${selectedFirstAreaCode}"!=""&&firstAreas[i-1].getAttribute("code")=="${selectedFirstAreaCode}"){			
							firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'" selected="selected">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
							var secondAreas = firstAreas[i-1].getElementsByTagName('second-area');
							for(var j=1;j<=secondAreas.length;j++){	
								//second-area IF BEGIN	
								if("${selectedSecondAreaCode}"!=""&&secondAreas[j-1].getAttribute("code")=="${selectedSecondAreaCode}"){			
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'" selected="selected">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
									var thirdAreas = secondAreas[j-1].getElementsByTagName('third-area');
									for(var k=1;k<=thirdAreas.length;k++){	
										//third-area IF BEGIN
										if("${selectedThirdAreaCode}"!=""&&thirdAreas[k-1].getAttribute("code")=="${selectedThirdAreaCode}"){			
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'" selected="selected">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
											var fouthAreas = thirdAreas[k-1].getElementsByTagName('fouth-area');
											for(var m=1;m<=fouthAreas.length;m++){	
												//fouth-area IF BEGIN
												if("${selectedFouthAreaCode}"!=""&&fouthAreas[m-1].getAttribute("code")=="${selectedFouthAreaCode}"){			
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'" selected="selected">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;					
													var fifthAreas = fouthAreas[m-1].getElementsByTagName('fifth-area');
													for(var n=1;n<=fifthAreas.length;n++){	
														//fifth-area IF BEGIN
														if("${selectedFifthAreaCode}"!=""&&fifthAreas[n-1].getAttribute("code")=="${selectedFifthAreaCode}"){			
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'" selected="selected">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;					
														}else{
															fifthResult+='<option value="'+fifthAreas[n-1].getAttribute("code")+'">'+fifthAreas[n-1].getAttribute("name")+'</option>' ;
														}
														//fifth-area IF END
													}
												}else{
													fouthResult+='<option value="'+fouthAreas[m-1].getAttribute("code")+'">'+fouthAreas[m-1].getAttribute("name")+'</option>' ;
												}
												//fouth-area IF END
											}
										}else{
											thirdResult+='<option value="'+thirdAreas[k-1].getAttribute("code")+'">'+thirdAreas[k-1].getAttribute("name")+'</option>' ;
										}
										//third-area IF END
									}
								}else{
									secondResult+='<option value="'+secondAreas[j-1].getAttribute("code")+'">'+secondAreas[j-1].getAttribute("name")+'</option>' ;
								}
								//second-area IF END	
							}
						}else{
							firstResult+='<option value="'+firstAreas[i-1].getAttribute("code")+'">'+firstAreas[i-1].getAttribute("name")+'</option>' ;
						}
						//first-area IF END
					}
					var divArea = document.getElementById("div-area");
					var totalResult="";
					if(firstResult!=""){
						totalResult+=firstResult+"</select>&nbsp;&nbsp;";
					}
					if(secondResult!=""){
						totalResult+=secondResult+"</select>&nbsp;&nbsp;";
					}
					if(thirdResult!=""){
						totalResult+=thirdResult+"</select>&nbsp;&nbsp;";
					}
					if(fouthResult!=""){
						totalResult+=fouthResult+"</select>&nbsp;&nbsp;";
					}
					if(fifthResult!=""){
						totalResult+=fifthResult+"</select>";
					}
					divArea.innerHTML=totalResult;
				}
			} else {
					alert('Loading Areas Error: ['+xmlobject.status+'] '+xmlobject.statusText);
			}
		}
	}
	xmlobject.open('GET','${resourcePath}/js/area.xml',true);
	xmlobject.send(null);
	function initNextAreaSelect(thisSelectValue,nextSelectId,thisTagName,nextTagName){
			var nextObj=document.getElementById(nextSelectId);
			if(nextObj!=null&&nextObj!=undefined){
				for(var k=1;k<=nextObj.options.length;k++){
					nextObj.options[k]=null;
				}
				var thisAreas = xmlobject.responseXML.getElementsByTagName(thisTagName);
				for(var i=0;i<thisAreas.length;i++){
					if(thisAreas[i].getAttribute("code")==thisSelectValue){
						var nextAreas = thisAreas[i].getElementsByTagName(nextTagName);
						for(var j=1;j<=nextAreas.length;j++){
								var opt = new Option(nextAreas[j-1].getAttribute("name"),nextAreas[j-1].getAttribute("code")) ;
								nextObj.options[j] = opt ;
								opt = null ;
						}
						break;
					}
				}
			}
				
	}