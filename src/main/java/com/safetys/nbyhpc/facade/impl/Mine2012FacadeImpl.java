package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.model.ZjStatistic;
import com.safetys.nbyhpc.facade.iface.BigTroubleFacadeIface;
import com.safetys.nbyhpc.facade.iface.Mine2012FacadeIface;
import com.safetys.nbyhpc.facade.iface.MineDetailFacadeIface;
import com.safetys.nbyhpc.util.SafetyTrouble;
import com.safetys.nbyhpc.util.State;

public class Mine2012FacadeImpl extends MineFacadeImpl implements Mine2012FacadeIface {

	private MineDetailFacadeIface mineDetailFacadeIface;// 矿山详细接口

	private BigTroubleFacadeIface bigTroubleFacadeIface;// 重大隐患接口

	public List<ZjMineReportDetail> loadMineReportDetailsByMine(ZjMineReport mineReport, Pagination pagination)
			throws ApplicationAccessException {
		return switchDetails(mineReport, mineDetailFacadeIface.loadMineReportDetailsByMine(mineReport.getId(),
				pagination));
	}

	public List<ZjMineReportDetail> loadMineReportDetailsForProvince(ZjMineReport mineReport)
			throws ApplicationAccessException {
		return switchDetails(mineReport, mineDetailFacadeIface.loadMineReportDetailsForProvince(mineReport));
	}

	public List<ZjMineReportDetail> loadMineReportDetailsByUser(ZjMineReport mineReport)
			throws ApplicationAccessException {
		return switchDetails(mineReport, mineDetailFacadeIface.loadMineReportDetailsByUser(mineReport));
	}

	public List<ZjStatistic> loadStatisticForMine(ZjMineReport mineReport) throws ApplicationAccessException {
		return switchBigTroubles(mineReport, bigTroubleFacadeIface.loadStatisticForMine(mineReport));
	}

	private List<ZjMineReportDetail> switchDetails(ZjMineReport mineReport, List<ZjMineReportDetail> mineReportDetails)
			throws ApplicationAccessException {
		List<ZjMineReportDetail> details = new ArrayList<ZjMineReportDetail>();
		List<State> types =
				mineReport.getType().intValue() == SafetyTrouble.MINE_TYPE ? getMineTypeMap() : getOtherTypeMap();
		for (State type : types) {
			for (ZjMineReportDetail detail : mineReportDetails) {
				if (type.getIntKey() >= 100) {// 特殊类型
					Integer[] ts = SafetyTrouble.getTradeTypesByFatherCode(type.getIntKey());
					ZjMineReportDetail tmp = new ZjMineReportDetail();
					tmp.initMineDetail();
					tmp.setType(type.getIntKey());
					if (ts != null && ts.length > 0) {
						for (int i : ts) {
							for (ZjMineReportDetail d : mineReportDetails) {
								if (d.getType().intValue() == i) {
									tmp.addMineDetail(d);
								}
							}
						}
					}
					if (109 == type.getIntKey()) {// 模拟查询矿山表数据,将其中的危化品--道路运输企业加到道路交通行业中
						ZjMineReport m = new ZjMineReport();
						m.setUserId(mineReport.getUserId());
						m.setQueryReportMonth(true);
						m.setType(1);
						m.setReportMonth(mineReport.getReportMonth());
						List<ZjMineReportDetail> ds =
								mineDetailFacadeIface.loadMineReportDetailsForProvince(m);
						for (ZjMineReportDetail d : ds) {
							if (d.getType().intValue() == 13) {
								tmp.addMineDetail(d);
							}
						}
					}
					details.add(tmp);
				} else if (detail.getType().intValue() == type.getIntKey()) {
					details.add(detail);
				} else {
					continue;
				}
				break;
			}
		}
		return details;
	}

	private List<ZjStatistic> switchBigTroubles(ZjMineReport mineReport, List<ZjStatistic> statistics)
			throws ApplicationAccessException {
		List<ZjStatistic> sta = new ArrayList<ZjStatistic>();
		List<State> types =
				mineReport.getType().intValue() == SafetyTrouble.MINE_TYPE ? getMineTypeMap() : getOtherTypeMap();
		for (State type : types) {
			for (ZjStatistic s : statistics) {
				if (type.getIntKey() >= 100) {// 特殊类型
					Integer[] ts = SafetyTrouble.getTradeTypesByFatherCode(type.getIntKey());
					ZjStatistic tmp = new ZjStatistic();
					tmp.initStatistic();
					if (ts != null && ts.length > 0) {
						for (int i : ts) {
							for (ZjStatistic s1 : statistics) {
								if (s1.getType().intValue() == i) {
									tmp.addStatistic(s1);
								}
							}
						}
					}
					if (109 == type.getIntKey()) {// 模拟查询矿山表数据,将其中的危化品--道路运输企业加到道路交通行业中
						ZjMineReport m = new ZjMineReport();
						m.setUserId(mineReport.getUserId());
						m.setQueryReportMonth(true);
						m.setType(1);
						m.setReportMonth(mineReport.getReportMonth());
						List<ZjStatistic> ss = bigTroubleFacadeIface.loadStatisticForMine(m);
						for (ZjStatistic s1 : ss) {
							if (s1.getType().intValue() == 13) {
								tmp.addStatistic(s1);
							}
						}
					}
					sta.add(tmp);
				} else if (s.getType().intValue() == type.getIntKey()) {
					sta.add(s);
				} else {
					continue;
				}
				break;
			}
		}
		return sta;
	}

	public List<State> getMineTypeMap() {
		List<State> types = new ArrayList<State>();
		types.add(new State(100, "1.煤矿企业"));// 原表格中不含有
		types.add(new State(101, "2.金属非金属矿山企业"));// 包含金属非金属(type=1)、尾矿库(type=2)
		types.add(new State(102, "3.石油天然气开采企业"));// 原表格中不含有
		types.add(new State(103, "4.危险化学品企业"));// 排除危险化学品--道路运输企业与单位(type=13)，type为7、8、9、10、11、12的总和
		types.add(new State(104, "  (1)生产、储存和其他化工企业"));// 包含7、9、10、11、12
		types.add(new State(8, "  (2)经营企业和单位"));
		types.add(new State(105, "5.烟花爆竹企业"));// 包含14、15、16
		types.add(new State(106, "6.冶金、有色企业"));// 包含3、4
		types.add(new State(107, "7.其他企业"));// 包含5、6、17
		return types;
	}

	public List<State> getOtherTypeMap() {
		List<State> types = new ArrayList<State>();
		types.add(new State(109, "1.道路运输企业"));// 包含交通运输行业中的“1.道路运输企业”(type=18)
		// 以及 矿山行业中的“6.危险化学品企业”--“(5).道路运输企业和单位”(type=13)
		types.add(new State(19, "2.公路养护施工企业"));
		types.add(new State(20, "3.水上运输企业"));
		types.add(new State(21, "4.铁路运输企业"));
		types.add(new State(22, "5.航空公司"));
		types.add(new State(23, "6.机场和油料企业"));
		types.add(new State(30, "7.建筑施工企业"));
		types.add(new State(28, "8.学校"));
		types.add(new State(29, "9.商场、市场等人员密集场所"));
		types.add(new State(26, "10.水库"));
		types.add(new State(32, "11.电力企业"));
		types.add(new State(25, "12.农机行业"));
		types.add(new State(24, "13.渔业企业"));
		types.add(new State(31, "14.民爆器材生产企业"));
		types.add(new State(110, "15.其他企业单位"));// 包含交通运输行业中的“15.机械制造行业”(type=33)、16.地铁施工（按项目部统计）(type=27)
		//20.城市公共交通(type=38)、21.燃气(type=39)、		22.旅游行业(type=40)、		23.铁路(type=41)、		24.医院(type=42)、
		// “27.其他单位”(type=34)
		return types;
	}

	public void setMineDetailFacadeIface(MineDetailFacadeIface mineDetailFacadeIface) {
		this.mineDetailFacadeIface = mineDetailFacadeIface;
	}

	public void setBigTroubleFacadeIface(BigTroubleFacadeIface bigTroubleFacadeIface) {
		this.bigTroubleFacadeIface = bigTroubleFacadeIface;
	}

}
