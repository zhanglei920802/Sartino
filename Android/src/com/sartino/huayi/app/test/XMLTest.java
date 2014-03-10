package com.sartino.huayi.app.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sartino.huayi.app.bean.DetailInfo;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.common.XMLUtil;

import android.os.Environment;
import android.test.AndroidTestCase;

public class XMLTest extends AndroidTestCase {
	public void testXml() {// ��������������ͨ��
		try {
			InputStream ins = new FileInputStream(new File(
					Environment.getExternalStorageDirectory(), "test.xml"));
			List<SimpleInfo> s = XMLUtil.getSimpleInfoInBatch(ins);
			System.out.println(s.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("XMLTest.testXml()" + e.getMessage());
		} catch (IOException e) {
			System.out.println("XMLTest.testXml()" + e.getMessage());
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	//
	public void testDetailXml() {// ����ͨ��
		try {
			InputStream ins = new FileInputStream(new File(
					Environment.getExternalStorageDirectory(), "detail.xml"));
			DetailInfo de = DetailInfo.parse(ins);
			System.out.println(de);
		} catch (FileNotFoundException e) {
				System.out.println("XMLTest.testDetailXml()" + e.getMessage());
		}
	}
}
