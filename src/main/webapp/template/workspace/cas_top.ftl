<div class="top_shadow"></div>
<#if casUser??>
	<div class="smart_logo"><img src="${resourcePath}/images/top_smart_logo_01.jpg" id="logo_temp">
        <div class="smart_sub">
          <ul style="float:left;">
            <li><a href="#" style="color:#000;text-decoration: none;"><b>${casUser.name?default("")}</b></a><a href="${base}/logout.xhtml?logoutSuccessUrl=${sso_logout?default('')}" style="color:#000;text-decoration: none;" target="_top"> [退出]</a></li>
          </ul>
          <img src="${resourcePath}/images/top_smart_logo_02.jpg" id="logo_img">
          <div id="sort" style="float:right;">
            <div id="btn_asc"></div>
            <div id="btn_desc"></div>
          </div>
          <div id="div_menu">
              <ul id="ul_menu">
              	<#if (realIcons?if_exists?size > 0)>
	              	<#list realIcons as icon>
	              		<#if !icon.enabled>
							<li><a href="javascript:alert('正在建设中...');">${icon.title}</a></li>
	              		<#else>
	              			<li><a href="${icon.appURL}" target="app${icon.id}">${icon.title}</a></li>
	              		</#if>
	              		<#if icon_has_next>
                		<li>|</li>
                		</#if>
	              	</#list>
	            </#if>
              </ul>
          </div>
        </div>
    </div>
</#if>