package com.sartino.huayi.domain;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class SimpleInfo extends Enity {

	private static final long serialVersionUID = 5616351220918363466L;
	public static final int CATEGORY_DISPLAY_SHOW = 0x01;
	public static final int CATEGORY_DESIGN_SHOWCASE = 0x02;
	public static final int CATEGORY_DESIGN_MEETIING = 0x03;
	public static final int CATEGORY_DESIGN_WEDDING = 0x04;
	public static final int CATEGORY_DESIGN_DISPLAY = 0x05;
	public static final int CATEGORY_DESIGN_LANDSCAPE = 0x06;
	public static final int CATEGORY_DESIGN_INNER = 0x07;
	public static final int CATEGORY_HUALI_WESTERN = 0x08;
	public static final int CATEGORY_HUALI_EAST = 0x09;
	public static final int CATEGORY_HUAYI_WESTERN = 0xA;
	public static final int CATEGORY_HUAYI_EAST = 0xB;

	private int category = 0;// 类别
	private String zh_title = null;// 中文标题
	private String en_title = null;// 英文标题
	private int id = 0;// id
	private Image img = null;// 图片信息
	private String detail = null;// 详细内容链接

	public SimpleInfo() {
		super();
	}

	public SimpleInfo(int category, String zh_title, String en_title, int id,
			Image img, String detail) {
		super();
		this.category = category;
		this.zh_title = zh_title;
		this.en_title = en_title;
		this.id = id;
		this.img = img;
		this.detail = detail;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getZh_title() {
		return zh_title;
	}

	public void setZh_title(String zh_title) {
		this.zh_title = zh_title;
	}

	public String getEn_title() {
		return en_title;
	}

	public void setEn_title(String en_title) {
		this.en_title = en_title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "SimpleInfo [category=" + category + ", zh_title=" + zh_title
				+ ", en_title=" + en_title + ", id=" + id + ", img=" + img
				+ ", detail=" + detail + "]";
	}

}
