package com.sartino.huayi.app.bean;

import java.util.List;

public class SimpleDatas extends Enity {

	private static final long serialVersionUID = -6067014478340575010L;
	private List<SimpleInfo> datas = null;

	public SimpleDatas() {
		super();

	}

	public SimpleDatas(List<SimpleInfo> datas) {
		super();
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "SimpleDatas [datas=" + datas + "]";
	}

	public List<SimpleInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<SimpleInfo> datas) {
		this.datas = datas;
	}

}
