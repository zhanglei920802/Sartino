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
	 * ����ͼƬ������,���Խ�ԭʼ·����url����ͼƬ���ź�ŵ������·����newUrl�������
	 * url��newUrl��ͬ���Ǿ�����ԭʼͼƬ�Ļ��������޸���
	 * 
	 * @author kongqz
	 * 
	 * @param url Ҫ���޸ĵ�ͼƬ������·�������Ǵ���ڷ������ϵľ���·��
	 * 
	 * @param newWidth �µĿ��
	 * 
	 * @param newHeight �µĸ߶�
	 * 
	 * @param newUrl Ҫд���·�������Ǵ���ڷ������ϵľ���·�������Ժ�urlһ�����������Ƕ�ԭʼͼƬ���޸���
	 * 
	 * @param suffix �ļ��ĺ�׺�������硰jpg��
	 */
	public static void scaledImage(String url, int newWidth, int newHeight,
			String newUrl, String suffix) throws Exception {

		// ��ȡͼƬ
		BufferedImage bi = ImageIO.read(new File(url));
		// �ж϶���ͼƬ�Ŀ�͸�
		if (bi.getHeight() > bi.getWidth()) {
			// ����߱ȿ��,�ͽ�����ֵ,ȷ�����ɵ�ͼƬ�ĳ�������һ����Χ��
			int tmp = newWidth;
			newWidth = newHeight;
			newHeight = tmp;
		}
		// ��Image��ķ�����ͼƬ���еȱ�ѹ��,ֻҪ��͸���һֵΪ��,���������Ǹ�ֵΪ���߽��еȱ�ѹ��
		Image image2 = bi.getScaledInstance(newWidth, newHeight,
				Image.SCALE_AREA_AVERAGING);
		// ��ȡѹ����ͼƬ�ĸߺͿ�
		int height = image2.getHeight(null);
		int width = image2.getWidth(null);
		// ���µĸߺͿ���һ���µĻ���ͼƬ
		BufferedImage bi3 = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi3.getGraphics();
		// ���µĻ���ͼƬ�л�ͼ,�ڻ�ͼ��ʱ������趨����ɫ
		g.drawImage(image2, 0, 0, Color.white, null);
		// ����IO��������ļ�
		FileOutputStream fos = new FileOutputStream(new File(newUrl));
		// ��ͼƬд�뵽ָ�����ļ���
		ImageIO.write(bi3, suffix, fos);
		fos.close();
	}

	/**
	 * ��ͼƬ���вü�
	 * 
	 * @author kongqz
	 * @param imgsrc
	 *            ԴͼƬ�ļ�·��
	 * @param suffix
	 *            �ļ��ĺ�׺
	 * @param imgdest
	 *            Ŀ��ͼƬ�ļ�д��·��
	 * @param x
	 *            ͼƬ��x����
	 * @param y
	 *            ͼƬ��y����
	 * @param width
	 *            ��ȡ��Ŀ�
	 * @param height
	 *            ��ȡ��ĸ�
	 * @throws IOException
	 * */
	public static void cutImage(String imgsrc, String imgdest, String suffix,
			int x, int y, int width, int height) throws IOException {
		// ���н�ȡ
		// ��ȡԴͼƬ�ļ�
		BufferedImage sourceImage = ImageIO.read(new File(imgsrc));
		Image croppedImage;
		ImageFilter cropFilter;
		// �ĸ������ֱ�Ϊͼ���������Ϳ�ߣ���CropImageFilter(int x,int y,int width,int
		// height)����ϸ�����ο�API
		// ָ��Ҫ�ü��ĵ��ļ��Ŀ�Ⱥ͸߶ȣ��Լ���ʼ����
		cropFilter = new CropImageFilter(x, y, width, height);
		// ����ͼƬ
		croppedImage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(sourceImage.getSource(), cropFilter));

		// ��ȡ�������ͼƬ�ĸ߶�
		int h1 = croppedImage.getHeight(null);
		int w1 = croppedImage.getWidth(null);

		BufferedImage bi = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);

		Graphics g = bi.getGraphics();
		// �ڻ�ͼ��ʱ��������ñ���ɫ
		g.drawImage(croppedImage, 0, 0, Color.white, null);

		// ����Ҫ�洢ͼƬ���ļ�,����ļ����ھͶ�ȡ
		File file = new File(imgdest);
		// ɾ���ļ�
		file.delete();
		// �����ļ������
		FileOutputStream fos = new FileOutputStream(new File(imgdest));
		// ��������ͼƬд�뵽�����
		ImageIO.write(bi, suffix, fos);
		fos.close();
	}
}
