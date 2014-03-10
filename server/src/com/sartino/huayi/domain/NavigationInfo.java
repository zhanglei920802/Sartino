package com.sartino.huayi.domain;

public class NavigationInfo {
	private int id;
	private int navigation_id;
	private String en_name_of_navigation;
	private String zh_name_of_navigation;
	private String zh_introduce;
	private String display_pic;
	private String en_introduce;

	public NavigationInfo() {
		// super();
	}

	public NavigationInfo(int id, int navigation_id,
			String en_name_of_navigation, String zh_name_of_navigation,
			String zh_introduce, String display_pic, String en_introduce) {
		super();
		this.id = id;
		this.navigation_id = navigation_id;
		this.en_name_of_navigation = en_name_of_navigation;
		this.zh_name_of_navigation = zh_name_of_navigation;
		this.zh_introduce = zh_introduce;
		this.display_pic = display_pic;
		this.en_introduce = en_introduce;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNavigation_id() {
		return navigation_id;
	}

	public void setNavigation_id(int navigation_id) {
		this.navigation_id = navigation_id;
	}

	public String getEn_name_of_navigation() {
		return en_name_of_navigation;
	}

	public void setEn_name_of_navigation(String en_name_of_navigation) {
		this.en_name_of_navigation = en_name_of_navigation;
	}

	public String getZh_name_of_navigation() {
		return zh_name_of_navigation;
	}

	public void setZh_name_of_navigation(String zh_name_of_navigation) {
		this.zh_name_of_navigation = zh_name_of_navigation;
	}

	public String getZh_introduce() {
		return zh_introduce;
	}

	public void setZh_introduce(String zh_introduce) {
		this.zh_introduce = zh_introduce;
	}

	public String getDisplay_pic() {
		return display_pic;
	}

	public void setDisplay_pic(String display_pic) {
		this.display_pic = display_pic;
	}

	public String getEn_introduce() {
		return en_introduce;
	}

	public void setEn_introduce(String en_introduce) {
		this.en_introduce = en_introduce;
	}

	@Override
	public String toString() {
		return "NavigationInfo [id=" + id + ", navigation_id=" + navigation_id
				+ ", en_name_of_navigation=" + en_name_of_navigation
				+ ", zh_name_of_navigation=" + zh_name_of_navigation
				+ ", zh_introduce=" + zh_introduce + ", display_pic="
				+ display_pic + ", en_introduce=" + en_introduce + "]";
	}

}
