package com.sartino.huayi.app.test;

import com.sartino.huayi.app.common.CyptoUtils;

import android.test.AndroidTestCase;

public class TestCyp extends AndroidTestCase {
	public void testCyp(){
		System.out.println("TestCyp.testCyp()"+CyptoUtils.encode("sartinoapp", "dsfsdfgv"));;
	}
}
