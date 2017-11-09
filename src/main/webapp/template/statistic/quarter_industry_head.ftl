			  <!--<input type="button" onclick="changeUrlIndustryOfAj('anjian')" value="安监" class="btn_2k3"/>-->
			  <!--<input type="button" onclick="changeUrlIndustryOfAj('anjian_whp')" value="安监" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('wenguang')" value="文广" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlItemAndIndustry('jiaotong')" value="交通" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('maoyi')" value="商务" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlItemAndIndustry('chengguan')" value="城管" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('haiyang')" value="海洋渔业" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlItemAndIndustry('shuili')" value="水利" class="btn_2k3"/>
			  <input type="button" onclick="changeUrl('lvyou')" value="旅游" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('jiaoyu')" value="教育" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('weisheng')" value="卫生" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('minzong')" value="民宗" class="btn_2k3"/>
              <input type="button" onclick="changeUrl('dianli')" value="电业" class="btn_2k3"/>
              <input type="button" onclick="changeUrlItem('jianwei')" value="建委" class="btn_2k3"/>
              <input type="button" onclick="changeUrlReportOther('xiaofang')" value="消防" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlReportOther('lishe')" value="机场" class="btn_2k3"/>
			  <input type="button" onclick="changeUrlReportOther('renfang')" value="人防" class="btn_2k3"/>
              <input type="button" onclick="changeUrlReportOther('nongji')" value="农机" class="btn_2k3"/>
              <input type="button" onclick="changeUrlReportOther('zhijian')" value="质监" class="btn_2k3"/>-->
              <ul>
				<li class="ssfd1"><a <#if statistic.remark == 'anjian'>class="hover"</#if> href="#" onclick="changeUrlIndustryOfAj('anjian_whp')">安监</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'jingxin'>class="hover"</#if> href="#" onclick="changeUrl('jingxin')">经信</a></li>
                <li class="ssfd1"><a <#if statistic.remark == 'jiaoyu'>class="hover"</#if> href="#" onclick="changeUrl('jiaoyu')">教育</a></li>
                <li class="ssfd1"><a <#if statistic.remark == 'xiaofang'>class="hover"</#if> href="#" onclick="changeUrlReportOther('xiaofang')">消防</a></li>
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
                <li class="ssfd1"><a <#if statistic.remark == 'zhijian'>class="hover"</#if> href="#" onclick="changeUrlReportOther('zhijian')">质监</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'renfang'>class="hover"</#if> href="#" onclick="changeUrlReportOther('renfang')">人防</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'nongji'>class="hover"</#if> href="#" onclick="changeUrlReportOther('nongji')">农机</a></li>
				<li class="ssfd1"><a <#if statistic.remark == 'dianli'>class="hover"</#if> href="#" onclick="changeUrl('dianli')">电业</a></li>
				<!--<li class="ssfd1"><a <#if statistic.remark == 'lishe'>class="hover"</#if> href="#" onclick="changeUrlReportOther('lishe')">机场</a></li>-->
			</ul>
          	<script type="text/javascript">
              function changeUrl(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadQuarterByIndustry.xhtml?statistic.isSonType=1&statistic.remark="+remark;
				}
			}
			function changeUrlIndustryOfAj(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.open("loadQuarterByIndustryOfAj.xhtml?statistic.isSonType=1&statistic.remark="+remark);
				}
			}
			function changeUrlItem(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadQuarterItem.xhtml?statistic.remark="+remark;
				}
			}
			function changeUrlItemAndIndustry(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadQuarterAndItemByIndustry.xhtml?statistic.isSonType=1&statistic.remark="+remark;
				}
			}
			function changeUrlReportOther(remark) {
				if(remark==null || remark=='') {
					return false;
				}else{
					window.location = "loadQuarterOther.xhtml?statistic.remark="+remark;
				}
			}
			</script>