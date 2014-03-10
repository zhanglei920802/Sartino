package com.sartino.huayi.domain;

/**
 * Õº∆¨ µÃÂ¿‡
 */
public class Image extends Enity {

	private static final long serialVersionUID = 454448337596051443L;
	private String url = null;
	private int height = 0;
	private int width = 0;
	private String name = null;
	private String thumbUrl = null;

	public Image(String url, int height, int width, String name, String thumbUrl) {
		super();
		this.url = url;
		this.height = height;
		this.width = width;
		this.thumbUrl = thumbUrl;
		this.name = name;
	}

	public Image() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	@Override
	public String toString() {
		return "Image [url=" + url + ", height=" + height + ", width=" + width
				+ ", name=" + name + ", thumbUrl=" + thumbUrl + "]";
	}

}
