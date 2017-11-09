package com.safetys.nbyhpc.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

@SuppressWarnings("serial")
public class ExcelNotMonthReportAction extends ActionSupport {
	private String fileName = "";
	// 是否是二级统计
	private String secondStatistic = "1";
	private StatisticFacadeIface statisticFacadeIface;
	private Statistic statistic;
	private List<Statistic> statistics;
	private FkArea area;// 区域对象
	private DaCompany company;// 企业单位
	private int current_quarter;// 当前季度
	private Pagination pagination;// 分页对象

	public InputStream getExcelFile() {
		
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(1000);
		}
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}
		
		SecurityContext context;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailWrapper userDetail = (UserDetailWrapper) authentication.getPrincipal();
		try {
			area = new FkArea();

			if (company != null) {

				if (company.getStaticDate() != null) {

					company.setYear(Integer.parseInt(company.getStaticDate().split("-")[0]));
					company.setMonth(Integer.parseInt(company.getStaticDate().split("-")[1]));

					if (company.getMonth() == 1 || company.getMonth() == 2 || company.getMonth() == 3) {
						company.setQuarter(1);
					} else if (company.getMonth() == 4 || company.getMonth() == 5 || company.getMonth() == 6) {
						company.setQuarter(2);
					} else if (company.getMonth() == 7 || company.getMonth() == 8 || company.getMonth() == 9) {
						company.setQuarter(3);
					} else if (company.getMonth() == 10 || company.getMonth() == 11 || company.getMonth() == 12) {
						company.setQuarter(4);
					}

					if (userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0) {
						area.setAreaCode(userDetail.getThirdArea());
						company.setThirdArea(userDetail.getThirdArea());
						company.setSecondArea(userDetail.getSecondArea());
					} else if (userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0) {
						area.setAreaCode(userDetail.getSecondArea());
						company.setSecondArea(userDetail.getSecondArea());
					}

					if (company.getFouthArea() != null && company.getFouthArea() != 0) {
						area.setAreaCode(company.getFouthArea());
					} else if (company.getThirdArea() != null && company.getThirdArea() != 0) {
						area.setAreaCode(company.getThirdArea());
					} else if (company.getSecondArea() != null && company.getSecondArea() != 0) {
						area.setAreaCode(company.getSecondArea());
					} else {
						area.setAreaCode(Nbyhpc.AREA_CODE); // 宁波市
					}
				}

			} else {

				company = new DaCompany();
				if (userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0) {
					area.setAreaCode(userDetail.getThirdArea());
				} else if (userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0) {
					area.setAreaCode(userDetail.getSecondArea());
				} else {
					area.setAreaCode(Nbyhpc.AREA_CODE); // 宁波市
				}
				company.setYear(year);
				company.setMonth(month);
				company.setStaticDate("" + year + "-" + month + "");
				company.setSecondArea(userDetail.getSecondArea());
				company.setThirdArea(userDetail.getThirdArea());
				company.setQuarter(current_quarter);
			}
			company.setCompanyTrade("c");
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				// liulj 加上历史数据判断
				if (statistic == null) {
					statistic = new Statistic();
				}

				statistic.setYear(company.getYear());
				statistic.setQuarter(company.getQuarter());

				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}

			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 try {
			 statistics=statisticFacadeIface.loadPaiChaOfCompanyList(company, pagination, area, current_quarter, secondStatistic, userDetail);
		} catch (ApplicationAccessException e1) {
			e1.printStackTrace();
		}
		
		
		
		try {
			this.fileName = new String("企业月报未上报情况汇总表".getBytes("gb2312"), "iso8859-1")+".xls";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 10000);
		{
			HSSFCellStyle headFontStyle = workbook.createCellStyle();

			headFontStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
			headFontStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
			headFontStyle.setWrapText(true);// 指定单元格自动换行
			
			HSSFCellStyle style1 = workbook.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
			
			HSSFCellStyle style2 = workbook.createCellStyle();

			style2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 指定单元格居中对齐
			
			// 设置单元格字体
			HSSFFont headFont = workbook.createFont();
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headFont.setFontName("宋体");
			headFont.setFontHeight((short) 200);
			headFontStyle.setFont(headFont);
			
			// 创建表头
			HSSFRow row = sheet.createRow(0);
			// 设置行高
			row.setHeight((short) 400);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("序号");
			cell.setCellStyle(headFontStyle);
			cell = row.createCell((short) 1);
			cell.setCellStyle(headFontStyle);
			cell.setCellValue("单位名称");
			cell = row.createCell((short) 2);
			cell.setCellStyle(headFontStyle);
			cell.setCellValue("联系人");
			cell = row.createCell((short) 3);
			cell.setCellStyle(headFontStyle);
			cell.setCellValue("联系电话");
			cell = row.createCell((short) 4);
			cell.setCellStyle(headFontStyle);
			cell.setCellValue("地址");
			// 第一行
			int i=1;
			for (Statistic s:statistics){
				row = sheet.createRow(i);
				cell = row.createCell((short) 0);
				cell.setCellStyle(style1);
				cell.setCellValue(i);
				

				cell = row.createCell((short) 1);
				cell.setCellStyle(style2);
				cell.setCellValue(s.getCompanyName());
				
				cell = row.createCell((short) 2);
				cell.setCellStyle(style1);
				cell.setCellValue(s.getSafetyMngPerson());
				cell = row.createCell((short) 3);
				cell.setCellStyle(style1);
				cell.setCellValue(s.getSafetyMngPersonPhone());
				cell = row.createCell((short) 4);
				cell.setCellStyle(style2);
				cell.setCellValue(s.getAddress());
				i++;
			}

		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook.write(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] ba = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		return bais;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSecondStatistic() {
		return secondStatistic;
	}

	public void setSecondStatistic(String secondStatistic) {
		this.secondStatistic = secondStatistic;
	}

	public StatisticFacadeIface getStatisticFacadeIface() {
		return statisticFacadeIface;
	}

	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
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

	public FkArea getArea() {
		return area;
	}

	public void setArea(FkArea area) {
		this.area = area;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public int getCurrent_quarter() {
		return current_quarter;
	}

	public void setCurrent_quarter(int current_quarter) {
		this.current_quarter = current_quarter;
	}

	
	
	
}