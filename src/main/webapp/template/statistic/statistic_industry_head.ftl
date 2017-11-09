			  <!--<input type="button" onclick="changeUrlIndustryOfAj('anjian')" value="安监" class="btn_2k3"/>-->
			 <ul>
				<li class="ssfd1"><a <#if statistic.remark == 'anjian'>class="hover"</#if> href="#" onclick="changeUrlIndustryOfAj('anjian_whp')">安监</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'jingxin'>class="hover"</#if> href="#" onclick="changeUrl('jingxin')">经信</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'jiaoyu'>class="hover"</#if> href="#" onclick="changeUrl('jiaoyu')">教育</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'minzong'>class="hover"</#if> href="#" onclick="changeUrl('minzong')">民宗</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'jianwei'>class="hover"</#if> href="#" onclick="changeUrlItem('jianwei')">住建</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'chengguan'>class="hover"</#if> href="#" onclick="changeUrlItemAndIndustry('chengguan')">城管</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'jiaotong'>class="hover"</#if> href="#" onclick="changeUrlItemAndIndustry('jiaotong')">交通</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'shuili'>class="hover"</#if> href="#" onclick="changeUrlItemAndIndustry('shuili')">水利</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'maoyi'>class="hover"</#if> href="#" onclick="changeUrl('maoyi')">商务</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'wenguang'>class="hover"</#if> href="#" onclick="changeUrl('wenguang')">文广</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'weisheng'>class="hover"</#if> href="#" onclick="changeUrl('weisheng')">卫生</a></li>
				<li class="ssfd2"><a <#if statistic.remark == 'haiyang'>class="hover"</#if> href="#" onclick="changeUrl('haiyang')">海洋渔业</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'lvyou'>class="hover"</#if> href="#" onclick="changeUrl('lvyou')">旅游</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'dianli'>class="hover"</#if> href="#" onclick="changeUrl('dianli')">电业</a></li>
				<#--<li class="ssfd1"><a <#if statistic.remark == 'xiaofang'>class="hover"</#if> href="#" onclick="changeUrlReportOther('xiaofang')">消防</a></li-->
				<#--<li class="ssfd1"><a <#if statistic.remark == 'lishe'>class="hover"</#if> href="#" onclick="changeUrlReportOther('lishe')">机场</a></li>-->
				<#--<li class="ssfd1"><a <#if statistic.remark == 'renfang'>class="hover"</#if> href="#" onclick="changeUrlReportOther('renfang')">人防</a></li-->
				<#--<li class="ssfd1"><a <#if statistic.remark == 'nongji'>class="hover"</#if> href="#" onclick="changeUrlReportOther('nongji')">农机</a></li-->
				<#--<li class="ssfd1"><a <#if statistic.remark == 'zhijian'>class="hover"</#if> href="#" onclick="changeUrlReportOther('zhijian')">质监</a></li-->
				
			</ul>
          	<script type="text/javascript">
              function changeUrl(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadCompanyByIndustry.xhtml?statistic.isSonType=1&statistic.remark="+remark;
				}
			}
			function changeUrlIndustryOfAj(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.open("loadCompanyByIndustryOfAj.xhtml?statistic.isSonType=1&statistic.remark="+remark);
				}
			}
			function changeUrlItem(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadItemByIndustry.xhtml?statistic.remark="+remark;
				}
			}
			function changeUrlItemAndIndustry(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadCompanyAndItemByIndustry.xhtml?statistic.isSonType=1&statistic.remark="+remark;
				}
			}
			function changeUrlReportOther(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadStatisticsOfSeasonReportOther.xhtml?statistic.remark="+remark;
				}
			}
			</script>