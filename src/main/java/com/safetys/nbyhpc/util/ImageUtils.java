/**
 * 
 */
package com.safetys.nbyhpc.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * @author liucj
 * @date 2014年5月9日 15:46:11
 * @version https://code.google.com/p/thumbnailator/
 */
public class ImageUtils {
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 800;
	private static final String[] IMAGE_ARRAY = { ".png", ".gif", ".jpg", ".jpeg", ".bmp", ".tiff" };
	private static final String IMAGE_STRING = ".png.gif.jpg.jpeg.bmp";//后缀名为.tiff格式文件压缩的时候工具类存在bug，此处不做压缩处理
	/**
	 * 指定大小进行缩放
	 * @param src 原始图片
	 * @param dst 图片输出位置
	 */
	public static void scaleImage(File src, File dst) {
		try {
			Thumbnails.of(src)
			.size(DEFAULT_WIDTH, DEFAULT_HEIGHT)
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按照比例进行缩放
	 * @param src原始图片
	 * @param dst图片输出位置
	 * @param num图片缩放比例
	 */
	public static void scaleImage(File src, File dst, float num) {
		try {
			Thumbnails.of(src)
			.scale(num)//scale(比例)
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 不按照比例，指定大小进行缩放
	 * @param src原始图片
	 * @param dst图片输出位置
	 */
	public static void keepAspectRatioImage(File src, File dst) {
		try {
			Thumbnails.of(src)
			.size(DEFAULT_WIDTH, DEFAULT_HEIGHT)
			.keepAspectRatio(false)//不等比缩放，强制转化成指定大小
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 旋转
	 * @param src原始图片
	 * @param dst图片输出位置
	 * @param num旋转的度数：90°，180°， -90°，-180°等
	 */
	public static void rotateImage(File src, File dst, int num) {
		try {
			Thumbnails.of(src)
			.size(DEFAULT_WIDTH, DEFAULT_HEIGHT)
			.rotate(num)//rotate(角度),正数：顺时针 负数：逆时针
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 水印
	 * @param src原始图片
	 * @param dst图片输出位置
	 * @param watermark水印图片
	 */
	public static void watermarkImage(File src, File dst, File watermark) {
		try {
			Thumbnails.of(src)
			.size(DEFAULT_WIDTH, DEFAULT_HEIGHT)
			//.size(1280, 1024)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)//watermark(位置，水印图，透明度)
            .outputQuality(0.8f)
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 转化图片格式
	 * @param src原始图片
	 * @param dst图片输出位置
	 * @param format图片格式:png,jpg,gif...
	 */
	public static void formatImage(File src, File dst, String format) {
		try {
			Thumbnails.of(src)
			.size(DEFAULT_WIDTH, DEFAULT_HEIGHT)
			.outputFormat(format)
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 裁剪
	 * @param src原始图片
	 * @param dst图片输出位置
	 */
	public static void sourceRegion(File src, File dst) {
		try {
			Thumbnails.of(src)
			.sourceRegion(Positions.CENTER, 400,400)//图片中心400*400的区域
			//.sourceRegion(Positions.BOTTOM_RIGHT, 400,400)//图片右下400*400的区域
			//.sourceRegion(600, 500, 400, 400)//随意指定坐标下400*400的区域
			.keepAspectRatio(false)
			.size(200, 200)//裁剪图片的大小
			.toFile(dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证上传文件是否图片（简单判断后缀名是否为图片格式，如需要更高要求请扩展）
	 * @param extention 图片后缀名
	 * @return boolean
	 */
	public static boolean isImage(String extention) {
		return IMAGE_STRING.contains(extention.toLowerCase());
	}
	
	public static void main(String[] args) {
		System.out.println(isImage(".pnG"));
	}
}
