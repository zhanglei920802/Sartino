package com.sartino.huayi.domain;

public class TitleTypeInfo {
	private int type_id;
	private int navigation_id;
	private String en_name_of_title_type;
	private String zh_name_of_title_type;

	public TitleTypeInfo() {
		// super();
	}

	public TitleTypeInfo(int type_id, int navigation_id,
			String en_name_of_title_type, String zh_name_of_title_type) {
		// super();
		this.type_id = type_id;
		this.navigation_id = navigation_id;
		this.en_name_of_title_type = en_name_of_title_type;
		this.zh_name_of_title_type = zh_name_of_title_type;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public int getNavigation_id() {
		return navigation_id;
	}

	public void setNavigation_id(int navigation_id) {
		this.navigation_id = navigation_id;
	}

	public String getEn_name_of_title_type() {
		return en_name_of_title_type;
	}

	public void setEn_name_of_title_type(String en_name_of_title_type) {
		this.en_name_of_title_type = en_name_of_title_type;
	}

	public String getZh_name_of_title_type() {
		return zh_name_of_title_type;
	}

	public void setZh_name_of_title_type(String zh_name_of_title_type) {
		this.zh_name_of_title_type = zh_name_of_title_type;
	}

	@Override
	public String toString() {
		return "TitleTypeInfo [type_id=" + type_id + ", navigation_id="
				+ navigation_id + ", en_name_of_title_type="
				+ en_name_of_title_type + ", zh_name_of_title_type="
				+ zh_name_of_title_type + "]";
	}

}
