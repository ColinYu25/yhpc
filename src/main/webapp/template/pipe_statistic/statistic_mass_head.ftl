<@fkMacros.pageHeader_pipe_statistic />
<body>&nbsp;
<div class="main">
	<div class="box">
		<div class="box-top">
			<div class="box-nav">
				<ul class="<#if statistic?? && statistic.areaType?? && statistic.areaType=='third_area'>fnlist_3<#else>fnlist3</#if>">
					<#if statistic?? && statistic.areaType?? && statistic.areaType=='third_area'><li><a href="loadMass.xhtml?statistic.areaCode=330200000000&statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}">返回市级统计</a></li></#if>
					<li><a <#if !(statistic.remark?? && statistic.remark != '')>class="hover"</#if> href="loadMass.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}">各地管道报送情况</a></li>
					<li><a <#if statistic.remark?? && statistic.remark != ''>class="hover"</#if> href="loadMass.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=anjian&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}">各部门管道报送情况</a></li>
				</ul>
			</div>
		</div>
		<#assign menuName = "季度"/>
		<#assign menuName2 = "季度"/>
		<#assign topName = "季报"/>
		<#if statistic.month?? && statistic.month != 0>
			<#assign menuName = "月"/>
			<#assign menuName2 = "月"/>
			<#assign topName = "月报"/>
		<#else>
			<#if statistic.quarter??  && statistic.quarter != 0>
				<#assign menuName = "季度"/>
				<#assign topName = "季报"/>
				<#assign menuName2 = "季度"/>
			<#else>
				<#assign menuName = "年"/>
				<#assign menuName2 = "年"/>
				<#assign topName = "年报"/>
			</#if>
		</#if>
		<#if !(statistic.remark?? && statistic.remark != '')>
		<div class="box-con">
			<div class="box-title2">
				<div class="left"></div>
				<div class="center">
				<div class="fn2">
					<div class="left">
						<input type="text" id="year" value="${statistic.year}" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="4" maxlength="4" onchange="showYearData(this.value);">年
						<b><#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>各地报送情况</b>
					</div>
					<div class="right">
						<ul class="fnlist">
							<li><a <#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeUrl(0);">全 年</a></li>
							<li><a <#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeUrl(1);">第一季度</a></li>
							<li><a <#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeUrl(2);">第二季度</a></li>
							<li><a <#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeUrl(3);">第三季度</a></li>
							<li><a <#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeUrl(4);">第四季度</a></li>
						</ul>
					</div>
				</div>
				<div class="yf">
					<table border="0" cellspacing="0" cellpadding="0" >
				  		<tr>
						 <#assign beginMonth = "1"/>
			              <#assign endMonth = "12"/> 
			              <#if statistic.quarter == 0>
				              <#assign beginMonth = 1/>
			    	          <#assign endMonth = 12/>               
			              <#else >
				              <#assign beginMonth = (statistic.quarter * 3) -2/>
			    	          <#assign endMonth = (statistic.quarter * 3)/>                             
			              </#if>
			              <#list beginMonth..endMonth as item>
			             	<td style="text-align:right;" class="shun"><a <#if statistic.month == item>class="hover"</#if> href="#" name="month" onclick="showMonthData(${item});">${item}月份</a></td><!--<#if item ==statistic.month>disabled</#if>-->
			              </#list>
					  	</tr>	
					 </table>	
				</div>
			</div>
			<div class="right"></div>
		</div>
		<#else>
		<div class="box-con">
			<div class="box-title">
				<div class="left"></div>
				<div class="center">
					<div class="mlist">
						<ul>
							<li class="ssfd2"><a <#if statistic.remark == 'fagai'>class="hover"</#if> href="#" onclick="changeUrl2('fagai')">发改委</a></li>
							<li class="ssfd1"><a <#if statistic.remark == 'chengguan'>class="hover"</#if> href="#" onclick="changeUrl2('chengguan')">城管</a></li>
							<li class="ssfd2"><a <#if statistic.remark == 'jiaotong'>class="hover"</#if> href="#" onclick="changeUrl2('jiaotong')">交通局</a></li>
							<li class="ssfd1"><a <#if statistic.remark == 'anjian'>class="hover"</#if> href="#" onclick="changeUrl2('anjian')">安监</a></li>
						</ul>
					</div>
					<div class="fn">
						<div class="left">
							<input type="text" id="year" value="${statistic.year}" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="4" maxlength="4" onchange="showYearData(this.value);">年
							<b> <#assign title=""/>
					             <#if statistic.month gt 0>
					             <#assign title=statistic.month +"月份"/>
					             <#else>
						             <#if statistic.quarter==1>
							             <#assign title="第一季度"/>	             
						             <#elseif statistic.quarter==2>
						             	<#assign title="第二季度"/>	 
						             <#elseif statistic.quarter==3>
							             <#assign title="第三季度"/>
						             <#elseif statistic.quarter==4>
							             <#assign title="第四季度"/>             
						             <#else>
						                 <#assign title="全年"/>             
						             </#if>
					             </#if>
					            ${title}月报情况</b>
						</div><#--${s.enumName}-->
						<div class="right">
							<ul class="fnlist">
								<li><a <#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeUrl(0);">全 年</a></li>
								<li><a <#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeUrl(1);">第一季度</a></li>
								<li><a <#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeUrl(2);">第二季度</a></li>
								<li><a <#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeUrl(3);">第三季度</a></li>
								<li><a <#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeUrl(4);">第四季度</a></li>
							</ul>
						</div>
					</div>
					<div class="yf">
						<table  border="0" cellspacing="0" cellpadding="0" >
					  		<tr>
							 <#assign beginMonth = "1"/>
				              <#assign endMonth = "12"/> 
				              <#if statistic.quarter == 0>
					              <#assign beginMonth = 1/>
				    	          <#assign endMonth = 12/>               
				              <#else >
					              <#assign beginMonth = (statistic.quarter * 3) -2/>
				    	          <#assign endMonth = (statistic.quarter * 3)/>                             
				              </#if>
				              <#list beginMonth..endMonth as item>
				             	<td style="text-align:right;" class="shun"><a <#if statistic.month == item>class="hover"</#if> href="#" name="month" onclick="showMonthData(${item});">${item}月份</a></td><!--<#if item ==statistic.month>disabled</#if>-->
				              </#list>
						  	</tr>	
						 </table>
					</div>				
				</div>
				<div class="right"></div>
			</div>	
		</#if>