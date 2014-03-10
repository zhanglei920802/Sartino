package com.sartino.huayi.app.test;

import com.sartino.huayi.app.AppContext;


import android.test.AndroidTestCase;


public class Test extends AndroidTestCase {
	public void testCreateCache() {
		AppContext ac = AppContext.getInstance();
		ac.creareCache();
	}
}
