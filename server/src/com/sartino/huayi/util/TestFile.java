package com.sartino.huayi.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class TestFile {

	@Test
	public void test() {
		
		//fail("Not yet implemented");
		List<String> datas = FileUtil.getFileListName(new File("D:/website/v7/images/huayi/origin/12/1_20130105120122_wqosr"));
		System.out.println(datas.toString());
	}
}
