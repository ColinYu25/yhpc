<@fkMacros.pageHeader />
<#escape x as (x)!>
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">行业列表</th>
  </tr>
</table>
<form action="loadTradeTypes.xhtml" method="post" name="tradeTypeForm" id="tradeTypeForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th width="15%">名称：</th>
	    <td width="25%"><input type="text" name="tradeType.name" id="name" size="35" value="${tradeType.name}" maxlength="25"></td>
	    <th width="15%">描述：</th>
	    <td width="35%"><input type="text" name="tradeType.depiction" id="depiction" size="35" value="${tradeType.depiction}" maxlength="25"></td>
	  	<td width="10%"><input type="submit" id="sub" value="查  询" onClick="this.form.submit();" style="input_submit"/></td>
  	  </tr>
	</table>
</form>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="createTrade('createTradeTypeInit.xhtml');"><b>添加</b></a></li>
	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('loadTradeType.xhtml?tradeType.id');"><b>修改</b></a></li>
	<li id="img_del">		<a href="#" class="b3" onClick="deleteNote(get('tradeTypesForm'));"><b>删除</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<form action="deleteTradeType.xhtml" method="post" name="tradeTypesForm" id="tradeTypesForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th width="4%">序号</th>
    <th width="15%">类　　型</th>
    <th width="39%">名　　称</th>
    <th width="10%">编　　码</th>
    <th width="5%">排序</th>
    <th width="15%">说　　明</th>
    <th width="18%">操　　作</th>
  </tr>
  <#if tradeTypes?exists>
  	<#list tradeTypes?if_exists as m>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${m.id}" value="${m.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+m_index+1}<#else>${m_index+1}</#if></td>
	    <td><div align="left"><#if m.type?exists><#if m.type==1>企业行业<#elseif m.type==2>工程项目重大隐患类型<#elseif m.type==3>企业重大隐患类型<#elseif m.type==4>打非类型<#elseif m.type==5>统计上报行业<#elseif m.type==6>企业季报行业<#elseif m.type==7>行业部门季报行业<#elseif m.type==8>企业一般隐患类型<#elseif m.type==9>企业分级分类类型<#elseif m.type==10>安全生产执法行业类型</#if></#if>&nbsp;</div></td>
	    <td><p align="left">&nbsp;&nbsp;<#if m.gradeRate!=0><#list 0..m.gradeRate as i>&nbsp;&nbsp;</#list></#if><#if (m.daIndustryParameters?exists && m.daIndustryParameters?size>0)><strong></#if><a href="loadTradeType.xhtml?tradeType.id=${m.id}">${m.name}</a><#if (m.daIndustryParameters?exists && m.daIndustryParameters?size>0)><strong></#if></p></td>
	    <td>${m.code}&nbsp;</td>
	    <td>${m.sort}&nbsp;</td>
	    <td>${m.depiction}&nbsp;</td>
	    <td><a href="loadTradeType.xhtml?tradeType.id=${m.id}">修改</a><#if m.type==9>、<a href="../pointType/loadPointTypes.xhtml?pointType.daIndustryParameter.id=${m.id}">分级分类分数</a></#if></td>
	  </tr>
	 </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
<span style="color:red">注：添加子行业时，请选中一个对应的父行业，然后点击“添加”按钮。<!--<br/>&nbsp;&nbsp;&nbsp;&nbsp;如果没有选中父行业，则默认为顶级行业。--></span>
</body>
<script type="text/javascript">
	function createTrade(url) {
		var checkboxs = getTag('input');
		var checkSize = 0;
		for(var i=0;i<checkboxs.length; i++) {
			if(checkboxs[i].type=='checkbox'){
				if(checkboxs[i].checked) {
					checkSize ++;				
				}
			}
		}
		if(checkSize <= 1){
			if ("${admin.toString()}"=="false"&&checkSize==0) {
				alert("请选择一个父行业。");
			} else {
				get("tradeTypesForm").action = url;
				get("tradeTypesForm").submit();
			}
		} else {
			alert("请将父行业选择为一个。");
		}
	}
</script>
</#escape>
<@fkMacros.pageFooter />