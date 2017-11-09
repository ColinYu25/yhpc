			  <!--<input type="button" onclick="changeUrlIndustryOfAj('anjian')" value="安监" class="btn_2k3"/>-->
			  <!--<input type="button" onclick="changeUrl('anjian')" value="安监" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlReportOther('xiaofang')" value="消防" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlReportOther('lishe')" value="机场" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('wenguang')" value="文广" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('jiaotong')" value="交通" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('maoyi')" value="商务" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlItem('jianwei')" value="建委" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('chengguan')" value="城管" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('haiyang')" value="海洋渔业" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('shuili')" value="水利" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('lvyou')" value="旅游" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('jiaoyu')" value="教育" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('weisheng')" value="卫生" class="btn_2k3"/>
              <input type="button" onclick="changeUrlReportOther('renfang')" value="人防" class="btn_2k3"/>
              <input type="button" onclick="changeUrlReportOther('nongji')" value="农机" class="btn_2k3"/>
              <input type="button" onclick="changeUrlReportOther('zhijian')" value="质监" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('minzong')" value="民宗" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('dianli')" value="电业" class="btn_2k3"/>-->
              <ul>




				
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'anjian'>class="hover"</#if> href="#" onclick="changeUrl('anjian')">安监</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'jingxin'>class="hover"</#if> href="#" onclick="changeUrl('jingxin')">经信</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'jiaoyu'>class="hover"</#if> href="#" onclick="changeUrl('jiaoyu')">教育</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'minzong'>class="hover"</#if> href="#" onclick="changeUrl('minzong')">民宗</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'jianwei'>class="hover"</#if> href="#" onclick="changeUrlItem('jianwei')">建委</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'chengguan'>class="hover"</#if> href="#" onclick="changeUrl('chengguan')">城管</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'jiaotong'>class="hover"</#if> href="#" onclick="changeUrl('jiaotong')">交通</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'shuili'>class="hover"</#if> href="#" onclick="changeUrl('shuili')">水利</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'maoyi'>class="hover"</#if> href="#" onclick="changeUrl('maoyi')">商务</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'wenguang'>class="hover"</#if> href="#" onclick="changeUrl('wenguang')">文广</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'weisheng'>class="hover"</#if> href="#" onclick="changeUrl('weisheng')">卫生</a></li>
				<li class="ssfd2"><a <#if statistic.remark?? && statistic.remark == 'haiyang'>class="hover"</#if> href="#" onclick="changeUrl('haiyang')">海洋渔业</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'lvyou'>class="hover"</#if> href="#" onclick="changeUrl('lvyou')">旅游</a></li>
				<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'dianli'>class="hover"</#if> href="#" onclick="changeUrl('dianli')">电业</a></li>
				<#--<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'xiaofang'>class="hover"</#if> href="#" onclick="changeUrlReportOther('xiaofang')">消防</a></li>-->
				<#--<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'lishe'>class="hover"</#if> href="#" onclick="changeUrlReportOther('lishe')">机场</a></li>-->
				<#--<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'renfang'>class="hover"</#if> href="#" onclick="changeUrlReportOther('renfang')">人防</a></li>-->
				<#--<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'nongji'>class="hover"</#if> href="#" onclick="changeUrlReportOther('nongji')">农机</a></li>-->
				<#--<li class="ssfd1"><a <#if statistic.remark?? && statistic.remark == 'zhijian'>class="hover"</#if> href="#" onclick="changeUrlReportOther('zhijian')">质监</a></li>-->
				
			</ul>
          	<script type="text/javascript">
              function changeUrl(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadTroubleByNomalAndHiddenAndRollcall.xhtml?statistic.isSonType=1&statistic.remark="+remark;
				}
			}
			function changeUrlIndustryOfAj(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadCompanyByIndustryOfAj.xhtml?statistic.remark="+remark;
				}
			}
			function changeUrlItem(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadItemByIndustry.xhtml?statistic.isStType=1&statistic.remark="+remark;
				}
			}
			function changeUrlItemAndIndustry(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadCompanyAndItemByIndustry.xhtml?statistic.remark="+remark;
				}
			}
			function changeUrlReportOther(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadStatisticsOfSeasonReportOther.xhtml?statistic.isStType=1&statistic.remark="+remark;
				}
			}
			</script>