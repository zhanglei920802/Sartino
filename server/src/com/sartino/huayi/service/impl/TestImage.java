package com.sartino.huayi.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sartino.huayi.domain.DetailInfo;
import com.sartino.huayi.service.ImageService;

public class TestImage {

	@Test
	public void test() {
		// fail("Not yet implemented");
		ImageService imgsrv = new ImageServiceBean();
		DetailInfo de= imgsrv.getDetialImageInfo(168);
		System.out.println(de);
	}

}
