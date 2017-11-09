package com.safetys.nbyhpc.web.axis2.client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.ZjReportParam;
import com.safetys.nbyhpc.domain.model.ZjSend;
import com.safetys.nbyhpc.facade.iface.Axis2FacadeIface;
import com.safetys.nbyhpc.util.ConnectionFactory;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
import com.safetys.nbyhpc.web.axis2.model.Danger;
import com.safetys.nbyhpc.web.axis2.model.NomalDanger;
import com.safetys.nbyhpc.web.axis2.model.Param;
import com.safetys.nbyhpc.web.axis2.model.ThreeIllegal;
import com.safetys.nbyhpc.web.axis2.model.ThreeParam;

/**
 * 季报统计和数据交换
 */
public class Axis2ClientAction extends DaAppAction { 
	
	private static final long serialVersionUID = 4153724799048091974L;

	private Axis2FacadeIface axis2FacadeIface;

	private List<Danger> dangers;// 重大隐患集合  

	private List<NomalDanger> nomalDangers;// 一般隐患集合

	private List<Param> params;// 参数集合

	private List<FkArea> areas;// 区域集合

	private List<Statistic> statistics;
	
	private List<ZjReportParam> reportParams;
	
	private List<ThreeIllegal> threeIllegals;
	
	private List<ThreeParam> threeParams;

	private Danger danger;// 重大隐患对象

	private NomalDanger nomalDanger;// 一般隐患对象

	private Param param;// 参数对象

	private FkArea area;// 区域对象

	private Statistic statistic;
	
	private ZjReportParam reportParam;
	
	private ThreeIllegal threeIllegal;
	
	private ThreeParam threeParam;
	
	private ZjSend send;
	
	Map<String, Object> map;
	
	private int refresh; // 是否有刷新按钮

	private String refreshDate; // 刷新时间
	
	private static ConnectionFactory cFactory = new ConnectionFactory();
	
	public String loadReportParam(){
		try {
			areas = axis2FacadeIface.loadAreasForReport();
			reportParams = axis2FacadeIface.loadReportParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String createReportParam(){
		try {
			axis2FacadeIface.createReportParam(reportParams,getUserId());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String updateReportParam(){
		try {
			axis2FacadeIface.updateReportParam(reportParams,getUserId());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 打非统计
	 * 
	 * @return
	 */
	public String loadThreeIllegal() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYear() == 0){
			Calendar cal = Calendar.getInstance();
			statistic.setYear(cal.get(Calendar.YEAR));
		}
		statistic.setAreaCode(getUserDetail().getSecondArea());
		try {
			statistics = axis2FacadeIface.loadThreeIllegal(statistic, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticMineByParam() {
		Calendar cal = Calendar.getInstance();
		int nowY = cal.get(Calendar.YEAR);
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYear() == 0){
			statistic.setYear(cal.get(Calendar.YEAR));
			statistic.setMonth(cal.get(Calendar.MONTH) + 1);
		}
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			map = axis2FacadeIface.loadStatisticMineByParam2(statistic);
			
			if (statistic.getYear() == nowY) { // 是否最新年份
				refresh = 1;

				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_AxisMine_"  + "0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			
			
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticMineByParam2() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYear() == 0){
			Calendar cal = Calendar.getInstance();
			statistic.setYear(cal.get(Calendar.YEAR));
		}
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			statistics = axis2FacadeIface.loadStatisticMineByParam(statistic);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticOtherByParam() {
		Calendar cal = Calendar.getInstance();
		int nowY = cal.get(Calendar.YEAR);
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYear() == 0){
			statistic.setYear(cal.get(Calendar.YEAR));
			statistic.setMonth(cal.get(Calendar.MONTH) + 1);
		}
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			map = axis2FacadeIface.loadStatisticOtherByParam2(statistic);
			
			if (statistic.getYear() == nowY ) { // 是否最新年份
				refresh = 1;

				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_AxisOther_0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticOtherByParam2() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYear() == 0){
			Calendar cal = Calendar.getInstance();
			statistic.setYear(cal.get(Calendar.YEAR));
		}
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			statistics = axis2FacadeIface.loadStatisticOtherByParam(statistic);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticForAxis2MineByParam() {
		Calendar cal = Calendar.getInstance();
		int nowY = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}else{
			statistic.setYear(Integer.parseInt(statistic.getYearMonth().split("-")[0]));
			statistic.setMonth(Integer.parseInt(statistic.getYearMonth().split("-")[1]));
			String yearMonth = statistic.getYear() + "-" + ((statistic.getMonth() < 10) ? "0" : "") + statistic.getMonth();
			statistic.setYearMonth(yearMonth);
		}
		statistic.setType(1);
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			send =axis2FacadeIface.loadSend(statistic);
			map = axis2FacadeIface.loadStatisticForAxis2MineByParam2(statistic);
			

			if (statistic.getYear() == nowY && statistic.getMonth() == month) { // 是否最新年份
				if (send == null || (send != null && Integer.valueOf(0).equals(send.getIsSend()))) {
					refresh = 1;
					
					try {
						String nowMonth = statistic.getYearMonth();
						Integer cYear = Integer.parseInt(nowMonth.split("-")[0]);
						Integer cMonth = Integer.parseInt(nowMonth.split("-")[1]);
						String cacheName = "nbyhpc_Axis2Mine_" + cYear + cMonth;
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='" + cacheName + "'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticForAxis2MineByParam2() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(1);
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			send = axis2FacadeIface.loadSend(statistic);
			statistics = axis2FacadeIface.loadStatisticForAxis2MineByParam(statistic);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticForAxis2OtherByParam() {
		Calendar cal = Calendar.getInstance();
		int nowY = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}else{
			statistic.setYear(Integer.parseInt(statistic.getYearMonth().split("-")[0]));
			statistic.setMonth(Integer.parseInt(statistic.getYearMonth().split("-")[1]));
			String yearMonth = statistic.getYear() + "-" + ((statistic.getMonth() < 10) ? "0" : "") + statistic.getMonth();
			statistic.setYearMonth(yearMonth);
		}
		statistic.setType(2);
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			send = axis2FacadeIface.loadSend(statistic);
			map = axis2FacadeIface.loadStatisticForAxis2OtherByParam2(statistic);
			
			if (statistic.getYear() == nowY && statistic.getMonth() == month) { // 是否最新年份
				if (send == null || (send != null && Integer.valueOf(0).equals(send.getIsSend()))) {
					refresh = 1;
					
					try {
						String nowMonth = statistic.getYearMonth();
						Integer cYear = Integer.parseInt(nowMonth.split("-")[0]);
						Integer cMonth = Integer.parseInt(nowMonth.split("-")[1]);
						String cacheName = "nbyhpc_Axis2Other_" + cYear + cMonth;
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='" + cacheName + "'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticForAxis2OtherByParam2() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(2);
		statistic.setAreaCode(getUserDetail().getSecondArea());
		statistic.setThirdCode(getUserDetail().getThirdArea());
		try {
			send = axis2FacadeIface.loadSend(statistic);
			statistics = axis2FacadeIface.loadStatisticForAxis2OtherByParam(statistic);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadStatisticForAxis2ThreeIllegal() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(3);
		statistic.setAreaCode(getUserDetail().getSecondArea());
		try {
			send = axis2FacadeIface.loadSend(statistic);
			statistics = axis2FacadeIface.loadStatisticForAxis2ThreeIllegal(statistic);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public FkArea getArea() {
		return area;
	}

	public void setArea(FkArea area) {
		this.area = area;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

	public Danger getDanger() {
		return danger;
	}

	public void setDanger(Danger danger) {
		this.danger = danger;
	}

	public List<Danger> getDangers() {
		return dangers;
	}

	public void setDangers(List<Danger> dangers) {
		this.dangers = dangers;
	}

	public NomalDanger getNomalDanger() {
		return nomalDanger;
	}

	public void setNomalDanger(NomalDanger nomalDanger) {
		this.nomalDanger = nomalDanger;
	}

	public List<NomalDanger> getNomalDangers() {
		return nomalDangers;
	}

	public void setNomalDangers(List<NomalDanger> nomalDangers) {
		this.nomalDangers = nomalDangers;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	
	/**
	 * @Description:发送省局
	 * @author:liulj
	 * @time:2012-1-14
	 */
	public String sendDataOfMineByOMElement() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(1);
		try {
			if(axis2FacadeIface.sendDataOfMineOrOtherByOMElement(statistic,getUserId())){
				setMessageKey("发送数据成功！");
				setUrl("loadStatisticForAxis2MineByParam.xhtml?statistic.yearMonth=" + statistic.getYearMonth());
				return MESSAGE_TO_REDIRECT;
			} else {
				setMessageKey("发送数据失败，请重新发送！");
				return MESSAGE_TO_BACK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String sendDataOfOtherByOMElement() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(2);
		try {
			if(axis2FacadeIface.sendDataOfMineOrOtherByOMElement(statistic,getUserId())){
				setMessageKey("发送数据成功！");
				setUrl("loadStatisticForAxis2OtherByParam.xhtml?statistic.yearMonth=" + statistic.getYearMonth());
				return MESSAGE_TO_REDIRECT;
			} else {
				setMessageKey("发送数据失败，请重新发送！");
				return MESSAGE_TO_BACK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	public String sendDataOfIllegalByOMElement() {
		if (statistic == null) {
			statistic = new Statistic();
		}
		if(statistic.getYearMonth() == null || "".equals(statistic.getYearMonth())){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			statistic.setYearMonth(formater.format(cal.getTime()));
		}
		statistic.setType(3);
		try {
			axis2FacadeIface.sendDataOfIllegalByOMElement(statistic,getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 保存设置的发送时间
	 * @return
	 */
	public String saveSendTime()  {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			
			axis2FacadeIface.saveSendTime(statistic);
			
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return NONE;
	}


	public void setAxis2FacadeIface(Axis2FacadeIface axis2FacadeIface) {
		this.axis2FacadeIface = axis2FacadeIface;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	public ZjReportParam getReportParam() {
		return reportParam;
	}

	public void setReportParam(ZjReportParam reportParam) {
		this.reportParam = reportParam;
	}

	public List<ZjReportParam> getReportParams() {
		return reportParams;
	}

	public void setReportParams(List<ZjReportParam> reportParams) {
		this.reportParams = reportParams;
	}

	public ThreeIllegal getThreeIllegal() {
		return threeIllegal;
	}

	public void setThreeIllegal(ThreeIllegal threeIllegal) {
		this.threeIllegal = threeIllegal;
	}

	public List<ThreeIllegal> getThreeIllegals() {
		return threeIllegals;
	}

	public void setThreeIllegals(List<ThreeIllegal> threeIllegals) {
		this.threeIllegals = threeIllegals;
	}

	public ThreeParam getThreeParam() {
		return threeParam;
	}

	public void setThreeParam(ThreeParam threeParam) {
		this.threeParam = threeParam;
	}

	public List<ThreeParam> getThreeParams() {
		return threeParams;
	}

	public void setThreeParams(List<ThreeParam> threeParams) {
		this.threeParams = threeParams;
	}

	public ZjSend getSend() {
		return send;
	}

	public void setSend(ZjSend send) {
		this.send = send;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public String getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(String refreshDate) {
		this.refreshDate = refreshDate;
	}
	
	
}
