package com.sartino.huayi.util;

import java.util.ArrayList;
import java.util.List;

import com.sartino.huayi.domain.SimpleInfo;

public class ListUtil {
	public static List<SimpleInfo> reverse(List<SimpleInfo> datas) {
		List<SimpleInfo> data = new ArrayList<SimpleInfo>();

		for (int i = datas.size() - 1; i >= 0; i--) {
			data.add(datas.get(i));
		}

		return data;
	}
}
