package com.sartino.huayi.domain;

public class PhotoInfo {
	private int pic_id;
	private int type_id;
	private String relative_path_of_pic;
	private String relative_path_of_origin;
	private String name_of_pic;
	private String width_of_pic;
	private String height_of_pic;
	private String title_of_pic;
	private String content_of_pic;
	private String overview_of_pic;
	private String en_title_of_pic;
	private short price;
	private short stars;

	public PhotoInfo() {
	}

	public PhotoInfo(int pic_id, int type_id, String relative_path_of_pic,
			String relative_path_of_origin, String name_of_pic,
			String width_of_pic, String height_of_pic, String title_of_pic,
			String content_of_pic, String overview_of_pic,
			String en_title_of_pic, short price, short stars) {

		this.pic_id = pic_id;
		this.type_id = type_id;
		this.relative_path_of_pic = relative_path_of_pic;
		this.relative_path_of_origin = relative_path_of_origin;
		this.name_of_pic = name_of_pic;
		this.width_of_pic = width_of_pic;
		this.height_of_pic = height_of_pic;
		this.title_of_pic = title_of_pic;
		this.content_of_pic = content_of_pic;
		this.overview_of_pic = overview_of_pic;
		this.en_title_of_pic = en_title_of_pic;
		this.price = price;
		this.stars = stars;
	}

	public int getPic_id() {
		return pic_id;
	}

	public void setPic_id(int pic_id) {
		this.pic_id = pic_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getRelative_path_of_pic() {
		return relative_path_of_pic;
	}

	public void setRelative_path_of_pic(String relative_path_of_pic) {
		this.relative_path_of_pic = relative_path_of_pic;
	}

	public String getRelative_path_of_origin() {
		return relative_path_of_origin;
	}

	public void setRelative_path_of_origin(String relative_path_of_origin) {
		this.relative_path_of_origin = relative_path_of_origin;
	}

	public String getName_of_pic() {
		return name_of_pic;
	}

	public void setName_of_pic(String name_of_pic) {
		this.name_of_pic = name_of_pic;
	}

	public String getWidth_of_pic() {
		return width_of_pic;
	}

	public void setWidth_of_pic(String width_of_pic) {
		this.width_of_pic = width_of_pic;
	}

	public String getHeight_of_pic() {
		return height_of_pic;
	}

	public void setHeight_of_pic(String height_of_pic) {
		this.height_of_pic = height_of_pic;
	}

	public String getTitle_of_pic() {
		return title_of_pic;
	}

	public void setTitle_of_pic(String title_of_pic) {
		this.title_of_pic = title_of_pic;
	}

	public String getContent_of_pic() {
		return content_of_pic;
	}

	public void setContent_of_pic(String content_of_pic) {
		this.content_of_pic = content_of_pic;
	}

	public String getOverview_of_pic() {
		return overview_of_pic;
	}

	public void setOverview_of_pic(String overview_of_pic) {
		this.overview_of_pic = overview_of_pic;
	}

	public String getEn_title_of_pic() {
		return en_title_of_pic;
	}

	public void setEn_title_of_pic(String en_title_of_pic) {
		this.en_title_of_pic = en_title_of_pic;
	}

	public short getPrice() {
		return price;
	}

	public void setPrice(short s) {
		this.price = s;
	}

	public short getStars() {
		return stars;
	}

	public void setStars(short stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return "PhotoInfo [pic_id=" + pic_id + ", type_id=" + type_id
				+ ", relative_path_of_pic=" + relative_path_of_pic
				+ ", relative_path_of_origin=" + relative_path_of_origin
				+ ", name_of_pic=" + name_of_pic + ", width_of_pic="
				+ width_of_pic + ", height_of_pic=" + height_of_pic
				+ ", title_of_pic=" + title_of_pic + ", content_of_pic="
				+ content_of_pic + ", overview_of_pic=" + overview_of_pic
				+ ", en_title_of_pic=" + en_title_of_pic + ", price=" + price
				+ ", stars=" + stars + "]";
	}

}
