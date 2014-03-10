package com.sartino.huayi.domain;

import java.util.ArrayList;
import java.util.List;

public class SimpleInfos extends Base {

	private static final long serialVersionUID = 28493810949152458L;
	private List<SimpleInfo> datas = new ArrayList<SimpleInfo>();
	private int size = 0;
	public List<SimpleInfo> getDatas() {
		return datas;
	}
	
	public SimpleInfos() {
		super();
		
	}

	public SimpleInfos(List<SimpleInfo> datas, int size) {
		super();
		this.datas = datas;
		this.size = size;
	}

	public void setDatas(List<SimpleInfo> datas) {
		this.datas = datas;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "SimpleInfos [datas=" + datas + ", size=" + size + "]";
	}
	
	

}
