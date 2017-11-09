package com.safetys.nbyhpc.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.safetys.nbyhpc.domain.model.CommonFile;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @() FileUpload.java
 * @date 2008-12-26
 * @author lihu
 * @copyright (c) 2008 Safetys.cn All rights reserved.
 * 
 */
public class FileUpload {

	public final static String UPLOAD_PATH_EMERGENCY = "/upload/file";// 应急预案保存文件目录

	public static final String UPLOAD_BAK = "/upload/uploadBak";// 删除文件的备份文件夹
	
	public static final String UPLOAD_CLIENT = "/android"; //

	private static int FILE_MAX_SIZE = 5242880;// 设置上传文件最大值

	private static String REAL_PATH = null;// 文件绝对路径
	
	public static String DANGER_IMAGE_PATH = "/upload/danger";

	private static String[] NOT_ALLOWTYPES = { "exe", "jsp", "htm", "html",
			"vbs", "ftl", "asp", "bat", "sh", "shtml", "xhtml", "php"};// 默认不允许上传的文件类型

	public static final String[] ALLOW_TYPE_WORD = { "doc", "pdf", "rar", "zip" };//文档格式或压缩文件
	
	public static final String[] ALLOW_TYPE_CAD  = { "dwg", "jpg"};//CAD图片允许上传文件格式

	public static final String[] ALLOW_TYPE_JPG = { "jpg", "gif", "png", "jpeg" };//图片格式
	
	public static final String[] ALLOW_TYPE_JPG_AND_WORD = { "doc", "pdf", "rar", "zip", "jpg", "xls" };

	private static int OUTPUTWIDTH = 400; // 默认输出图片宽

	private static int OUTPUTHEIGHT = 400; // 默认输出图片高
	
	public final static String UPLOAD_PATH_FILE = "/upload/image";

	/**
	 * 取得文件扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * @return String
	 */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos < 0) {
			return null;
		}
		return fileName.substring(pos);
	}

	/**
	 * 将上传的文件写入到本地文件中
	 * 
	 * @param src
	 *            读取的文件
	 * @param dst
	 *            将要写入的文件
	 */
	private static void writeToLocaleFile(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[FILE_MAX_SIZE];
		try {
			try {
				in = new BufferedInputStream(new FileInputStream(src));
				out = new BufferedOutputStream(new FileOutputStream(dst));
				int length = -1;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 等比例压缩图片
	 * 
	 * @param src
	 *            源文件对象
	 * @param dst
	 * 			  目标文件对象
	 */
	public final static void compressPic(File src, File dst) {
		int newWidth = 0;
		int newHeight = 0;
		FileOutputStream out = null;
		try {
			try {
				Image img = ImageIO.read(src);
//				ImageObserver observer = 
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null))
						/ (double) OUTPUTWIDTH + 0.1;
				double rate2 = ((double) img.getHeight(null))
						/ (double) OUTPUTHEIGHT + 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				out = new FileOutputStream(dst);
				// JPEGImageEncoder可适用于其他图片类型的转换
//				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//				encoder.encode(tag);
			} finally {
				if(out != null) 
					out.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 上传文件并自动压缩成小图片
	 * 
	 * @param file
	 * @param fileName
	 * @param savePath
	 * @param allowTypes
	 * @return
	 */
	public final static Map UploadFileAndZip(File file, String fileName,
			String savePath, String[] allowTypes) {
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		String error = "";
		if (file == null) {
			error = "file.upload.noFile";
			map.put("error", error);
			return map;
		} else if (file.length() > FILE_MAX_SIZE) {
			error = "file.upload.too.large";
			map.put("error", error);
			return map;
		}
		String extention = getExtention(fileName).toLowerCase(); // 文件扩展名
		if (extention == null) {
			map.put("error", getError(allowTypes));
			return map;
		}
		if (allowTypes != null) {
			boolean allow = false;
			for (int i = 0; i < allowTypes.length; i++) {
				if (extention.trim().substring(1).toUpperCase().equals(
						allowTypes[i])
						|| extention.trim().substring(1).toLowerCase().equals(
								allowTypes[i])) {
					allow = true;
				}
			}
			if (!allow) {
				error = getError(allowTypes);
				map.put("error", error);
				return map;
			}
		}
		String storedFileName = ""; // 文件存放的名称
		String uploadFilePath = ""; // 文件存放的路径
		File newFile = new File(getREAL_PATH() + savePath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		try {
			storedFileName = (new Date()).getTime() + extention;
			uploadFilePath = ServletActionContext.getServletContext()
					.getRealPath(savePath)
					+ "/";
			File localFile = new File(uploadFilePath + storedFileName);
			compressPic(file, localFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fileType", extention.substring(1));
		map.put("filePath", (savePath + "/" + storedFileName));
		map.put("fileSize", file.length());
		map.put("error", error);
		return map;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param fileName
	 * @param savePath
	 * @param allowTypes
	 * @return
	 */
	public final static Map UploadFile(File file, String fileName,
			String savePath, String[] allowTypes, boolean isUserFileName) {
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		String error = "";
		if (file == null) {
			error = "file.upload.noFile";
			map.put("error", error);
			return map;
		} else if (file.length() > FILE_MAX_SIZE) {
			error = "file.upload.too.large";
			map.put("error", error);
			return map;
		}
		String extention = getExtention(fileName).toLowerCase(); // 文件扩展名
		if (extention == null) {
			map.put("error", getError(allowTypes));
			return map;
		}
		if (allowTypes != null) {
			boolean allow = false;
			for (int i = 0; i < allowTypes.length; i++) {
				if (extention.trim().substring(1).toUpperCase().equals(
						allowTypes[i])
						|| extention.trim().substring(1).toLowerCase().equals(
								allowTypes[i])) {
					allow = true;
				}
			}
			if (!allow) {
				error = getError(allowTypes);
				map.put("error", error);
				return map;
			}
		}
		String storedFileName = ""; // 文件存放的名称
		String uploadFilePath = ""; // 文件存放的路径
		File newFile = new File(getREAL_PATH() + savePath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		try {
			if (isUserFileName) {
				storedFileName = fileName;
			} else {
				storedFileName = (new Date()).getTime() + extention;
			}
			uploadFilePath = ServletActionContext.getServletContext()
					.getRealPath(savePath)
					+ "/";
			File localFile = new File(uploadFilePath + storedFileName);
			writeToLocaleFile(file, localFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fileType", extention.substring(1));
		map.put("filePath", (savePath + "/" + storedFileName));
		map.put("fileSize", file.length());
		map.put("error", error);
		return map;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param fileName
	 * @param savePath
	 * @param allowTypes
	 * @return
	 */
	public final static Map UploadImage(File file, String fileName, String savePath) {
		String[] allowTypes = ALLOW_TYPE_JPG;
		Map<String, String> map = new HashMap<String, String>();
		String error = "";
		if (file == null) {
			error = "file.upload.noFile";
			map.put("error", error);
			return map;
		} else if (file.length() > FILE_MAX_SIZE) {
			error = "file.upload.too.large";
			map.put("error", error);
			return map;
		}
		String extention = getExtention(fileName).toLowerCase(); // 文件扩展名
		if (extention == null) {
			map.put("error", getError(allowTypes));
			return map;
		}
		if (allowTypes != null) {
			boolean allow = false;
			for (int i = 0; i < allowTypes.length; i++) {
				if (extention.trim().substring(1).toUpperCase().equals(allowTypes[i])
						|| extention.trim().substring(1).toLowerCase().equals(allowTypes[i])) {
					allow = true;
				}
			}
			if (!allow) {
				error = getError(allowTypes);
				map.put("error", error);
				return map;
			}
		}
		String storedFileName = ""; // 文件存放的名称
		String uploadFilePath = ""; // 文件存放的路径
		File newFile = new File(getREAL_PATH() + savePath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		try {
			storedFileName = (new Date()).getTime() + extention;
			uploadFilePath = ServletActionContext.getServletContext() .getRealPath(savePath) + "/";
			File localFile = new File(uploadFilePath + storedFileName);
			ImageUtils.scaleImage(file, localFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("filePath", (savePath + "/" + storedFileName));
		map.put("error", error);
		return map;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param fileName
	 * @param savePath
	 * @return
	 */
	public final static Map UploadFile(File file, String fileName,
			String savePath) {
		String extention = getExtention(fileName).toLowerCase(); // 文件扩展名
		boolean allow = true;
		if (NOT_ALLOWTYPES != null) {
			for (int i = 0; i < NOT_ALLOWTYPES.length; i++) {
				if (extention.trim().equals(NOT_ALLOWTYPES[i].toLowerCase())
						|| extention.trim().equals(
								NOT_ALLOWTYPES[i].toUpperCase())) {
					allow = false;
				}
			}
		}
		if (allow) {
			return UploadFile(file, fileName, savePath, null, false);
		} else {
			Map<String, Comparable> map = new HashMap<String, Comparable>();
			map.put("error", "file.upload.notAllowedFileType");
			return map;
		}
	}

	/**
	 * 验证上传文件是否符合规范
	 * @param files 上传的文件集合
	 * @param allowTypes 指定上传文件类型
	 * @return String
	 */
	public static String ValidateFile(List<CommonFile> files , String[] allowTypes){
		String error = "";
		for(CommonFile file : files ){
			if(file != null && file.getFile() != null){
				String extention = getExtention(file.getFileFileName()).toLowerCase(); // 文件扩展名
				if(file.getFile().length() == 0){
					error = "文件内容为空，上传失败！";
					return error;
				}
				if(file.getFile().length() > FILE_MAX_SIZE){
					error = "文件大小不能超过5M!";
					return error;
				}
				if(extention == null) {
					error = getError(allowTypes);
					if(StringUtils.isNotEmpty(error)){
						error = "文件格式错误，请按提示上传文件！";
					}
					return error;
				}
				if (allowTypes != null) {
					boolean allow = false;
					for (int i = 0; i < allowTypes.length; i++) {
						if (extention.trim().substring(1).toUpperCase().equals(allowTypes[i]) || 
							extention.trim().substring(1).toLowerCase().equals(allowTypes[i])) {
							allow = true;
						}
					}
					if (!allow) {
						error = getError(allowTypes);
						if(StringUtils.isNotEmpty(error)){
							error = "文件格式错误，请按提示上传文件！";
						}
						return error;
					}
				}
			}
		}
		return error;
	}
	
	private static String getError(String[] allowTypes) {
		if (allowTypes.equals(ALLOW_TYPE_JPG)) {
			return "file.upload.notAllowedFileType.jpg";
		} else if (allowTypes.equals(ALLOW_TYPE_WORD)) {
			return "file.upload.notAllowedFileType.word";
		} else if (allowTypes.equals(ALLOW_TYPE_CAD)) {
			return "file.upload.notAllowedFileType.cad";
		}
		return "";
	}

	/**
	 * 删除文件，节约文件上传的空间
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		if (null == filePath || "".equals(filePath)) {
			return;
		}
		File file = new File(REAL_PATH + filePath);
		if (!file.exists()){
			return;
		}
		UploadFile(file, filePath.split("/")[filePath.split("/").length - 1],
				UPLOAD_BAK);
		file.delete();
		cleanUpBakFile();
	}
	
	public static void deleteFile2(String filePath){
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		File file = new File(REAL_PATH + filePath);
		if (!file.exists()){
			return;
		}
		file.delete();
	}

	/**
	 * 清理bak文件夹
	 * 
	 */
	private static void cleanUpBakFile() {
		Calendar cal = Calendar.getInstance();
		GregorianCalendar gc1 = new GregorianCalendar(2009, Calendar.JANUARY, 1);
		GregorianCalendar gc2 = new GregorianCalendar(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		int alternation = 100;
		int compareDay = getDays(gc1, gc2);
		if (compareDay % alternation == 0) {// 自2009年1月1日起，在操作文件上传功能时，如果间隔xx天，则自动清理备份文件夹内容
			delFolder(REAL_PATH + UPLOAD_BAK);
		}
	}

	/**
	 * 获取时间差
	 * 
	 * @param g1
	 * @param g2
	 * @return
	 */
	public static int getDays(GregorianCalendar g1, GregorianCalendar g2) {
		int elapsed = 0;
		GregorianCalendar gc1, gc2;
		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		} else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}
		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		while (gc1.before(gc2)) {
			gc1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed;
	}

	/**
	 * 获取差之后的日期
	 * 
	 * @param g1
	 * @param timeOut
	 * @return
	 */
	public static Date getTimeOutDate(GregorianCalendar g1, int timeOut) {
		GregorianCalendar gc1;
		gc1 = (GregorianCalendar) g1.clone();
		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		if (timeOut > 0) {
			for (int i = 0; i < timeOut; i++) {
				gc1.add(Calendar.DATE, 1);
			}
		} else {
			for (int i = timeOut; i < 0; i++) {
				gc1.add(Calendar.DATE, 1);
			}
		}
		return gc1.getTime();
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 */
	private static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹中的文件
	 * 
	 * @param path
	 * @return
	 */
	private static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 设置文件保存的绝对目录，一般为项目目录，且系统启动后仅设置一次
	 * 
	 * @return
	 */
	public static String getREAL_PATH() {
		if (REAL_PATH == null || REAL_PATH.length() == 0) {
			String appPath = ((ServletContext) ActionContext.getContext().get(
					ServletActionContext.SERVLET_CONTEXT)).getRealPath("");
			setREAL_PATH(appPath);
		}
		return REAL_PATH;
	}

	public static void setREAL_PATH(String real_path) {
		REAL_PATH = real_path;
		cleanUpBakFile();
	}
	
	/**
	 * 
	 * @param entity 当前实体对象
	 * @param path 文件保存路径  默认路径/upload/book
	 * @param allowTypes 文件上传类型定义
	 * @return
	 */
	public static void uploadByObjs(DaBaseModel entity, String path){
		if (entity.getListFile() != null && !entity.getListFile().isEmpty()) {
			if (StringUtils.isEmpty(path)) {
				path = DANGER_IMAGE_PATH;
			}
			List<DaDangerImage> list = new ArrayList<DaDangerImage>();
			for (CommonFile file : entity.getListFile()) {
				if (file != null) {
					if (file.getFile() != null) {
						DaDangerImage info = new DaDangerImage();
						Map map = UploadImage(file.getFile(), file.getFileFileName(), path);
						String filePath = (String) map.get("filePath");
						info.setName(file.getFileFileName());
						info.setPath(filePath);
						list.add(info);
					}
				}
			}
			entity.setDaDangerImageList(list);
		}
	}
}
