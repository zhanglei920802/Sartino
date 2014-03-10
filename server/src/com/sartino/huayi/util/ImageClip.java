package com.sartino.huayi.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageClip {

	/*
	 * 
	 * 进行图片的缩放,可以将原始路径（url）的图片缩放后放到另外的路径（newUrl）。如果
	 * url和newUrl相同，那就是在原始图片的基础上做修改了
	 * 
	 * @author kongqz
	 * 
	 * @param url 要做修改的图片的物理路径，就是存放在服务器上的绝对路径
	 * 
	 * @param newWidth 新的宽度
	 * 
	 * @param newHeight 新的高度
	 * 
	 * @param newUrl 要写入的路径，就是存放在服务器上的绝对路径，可以和url一样，这样就是对原始图片的修改了
	 * 
	 * @param suffix 文件的后缀名，例如“jpg”
	 */
	public static void scaledImage(String url, int newWidth, int newHeight,
			String newUrl, String suffix) throws Exception {

		// 读取图片
		BufferedImage bi = ImageIO.read(new File(url));
		// 判断读入图片的宽和高
		if (bi.getHeight() > bi.getWidth()) {
			// 如果高比宽大,就交换两值,确保生成的图片的长个宽都在一个范围内
			int tmp = newWidth;
			newWidth = newHeight;
			newHeight = tmp;
		}
		// 用Image里的方法对图片进行等比压缩,只要宽和高其一值为负,则以正的那个值为最大边进行等比压缩
		Image image2 = bi.getScaledInstance(newWidth, newHeight,
				Image.SCALE_AREA_AVERAGING);
		// 获取压缩后图片的高和宽
		int height = image2.getHeight(null);
		int width = image2.getWidth(null);
		// 以新的高和宽构造一个新的缓存图片
		BufferedImage bi3 = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi3.getGraphics();
		// 在新的缓存图片中画图,在画图的时候可以设定背景色
		g.drawImage(image2, 0, 0, Color.white, null);
		// 构造IO流输出到文件
		FileOutputStream fos = new FileOutputStream(new File(newUrl));
		// 将图片写入到指定的文件中
		ImageIO.write(bi3, suffix, fos);
		fos.close();
	}

	/**
	 * 将图片进行裁剪
	 * 
	 * @author kongqz
	 * @param imgsrc
	 *            源图片文件路径
	 * @param suffix
	 *            文件的后缀
	 * @param imgdest
	 *            目标图片文件写入路径
	 * @param x
	 *            图片的x坐标
	 * @param y
	 *            图片的y坐标
	 * @param width
	 *            截取框的宽
	 * @param height
	 *            截取框的高
	 * @throws IOException
	 * */
	public static void cutImage(String imgsrc, String imgdest, String suffix,
			int x, int y, int width, int height) throws IOException {
		// 进行截取
		// 读取源图片文件
		BufferedImage sourceImage = ImageIO.read(new File(imgsrc));
		Image croppedImage;
		ImageFilter cropFilter;
		// 四个参数分别为图像起点坐标和宽高，即CropImageFilter(int x,int y,int width,int
		// height)，详细情况请参考API
		// 指定要裁剪的的文件的宽度和高度，以及起始坐标
		cropFilter = new CropImageFilter(x, y, width, height);
		// 生成图片
		croppedImage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(sourceImage.getSource(), cropFilter));

		// 获取创建后的图片的高度
		int h1 = croppedImage.getHeight(null);
		int w1 = croppedImage.getWidth(null);

		BufferedImage bi = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);

		Graphics g = bi.getGraphics();
		// 在画图的时候可以设置背景色
		g.drawImage(croppedImage, 0, 0, Color.white, null);

		// 创建要存储图片的文件,如果文件存在就读取
		File file = new File(imgdest);
		// 删除文件
		file.delete();
		// 创建文件输出流
		FileOutputStream fos = new FileOutputStream(new File(imgdest));
		// 将创建的图片写入到输出流
		ImageIO.write(bi, suffix, fos);
		fos.close();
	}
}
