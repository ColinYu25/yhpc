<@fkMacros.pageHeader_pipe_statistic />
<body>&nbsp;
<div class="main">
	<div class="box">
		<div class="box-top">
			<div class="box-nav">
				<ul class="<#if statistic?? && statistic.areaType?? && statistic.areaType=='third_area'>fnlist_3<#else>fnlist3</#if>">
					<#if statistic?? && statistic.areaType?? && statistic.areaType=='third_area'><li><a href="loadQuarter.xhtml?statistic.areaCode=330200000000&statistic.remark=${statistic.remark}&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}">返回市级统计</a></li></#if>
					<li><a <#if !(statistic.remark?? && statistic.remark != '')>class="hover"</#if> href="loadQuarter.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}">各地管道季报情况</a></li>
					<li><a <#if statistic.remark?? && statistic.remark != ''>class="hover"</#if> href="loadQuarter.xhtml?statistic.areaCode=${statistic.areaCode}&statistic.remark=anjian&statistic.year=${statistic.year}&statistic.quarter=${statistic.quarter}">各部门管道季报情况</a></li>
				</ul>
			</div>
		</div>
		<#if !(statistic.remark?? && statistic.remark != '')>
		<div class="box-con">
			<div class="box-title5">
				<div class="left"></div>
				<div class="center">
					<div class="fn2">
						<div class="left">
							<input type="text" id="year" value="${statistic.year}" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="4" maxlength="4" onchange="showYearData(this.value);">年
							<b><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if>各地季报进度</b>
						</div>
						<div class="right">
							<ul class="fnlist">
								<li><a <#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeUrl(0);">全 年</a></li>
								<li><a <#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeUrl(1);">第一季度</a></li>
								<li><a <#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeUrl(2);">第二季度</a></li>
								<li><a <#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeUrl(3);">第三季度</a></li>
								<li><a <#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeUrl(4);">第四季度</a></li>
							</ul>
							<ul class="fnlist2">
							</ul>
						</div>
					</div>							
				</div>
				<div class="right"></div>
			</div>
		   <#else>
			<div class="box-con">
				<div class="box-title7">
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
						<div class="fn2">
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
						            ${title}季报进度</b>
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
					</div>
					<div class="right"></div>
				</div>
				</#if>