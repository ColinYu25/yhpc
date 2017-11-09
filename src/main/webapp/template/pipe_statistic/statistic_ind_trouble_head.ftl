              <ul>
				<li class="ssfd2"><a <#if statistic.type?exists && statistic.type?default(99)==1>class="hover"</#if> href="#" onclick="changeUrl(1)">发改委</a></li>
				<li class="ssfd1"><a <#if statistic.type?exists && statistic.type?default(99)==2>class="hover"</#if> href="#" onclick="changeUrl(2)">城管</a></li>
				<li class="ssfd2"><a <#if statistic.type?exists && statistic.type?default(99)==4>class="hover"</#if> href="#" onclick="changeUrl(4)">交通局</a></li>
				<li class="ssfd1"><a <#if statistic.type?exists && statistic.type?default(99)==3>class="hover"</#if> href="#" onclick="changeUrl(3)">安监</a></li>
			</ul>
          	<script type="text/javascript">
              function changeUrl(type) {
				if(type==null || type=='') {
					return false;
				}else{
					window.location = "loadDanger.xhtml?statistic.type="+type+"&area.areaCode=${area.areaCode}&statistic.year=${statistic.year}&statistic.beg_month=${statistic.beg_month}&statistic.end_month=${statistic.end_month}";
				}
			}
			</script>