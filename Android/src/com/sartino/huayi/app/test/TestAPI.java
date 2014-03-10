package com.sartino.huayi.app.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppException;
import com.sartino.huayi.app.api.APIClient;
import com.sartino.huayi.app.bean.DetailInfo;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.common.XMLUtil;

import android.test.AndroidTestCase;

public class TestAPI extends AndroidTestCase {
	// 测试成功
	public void testHttpget() {// 访问简要信息
		String url = "http://202.115.133.241:8080/Server/servlet/ListServlet?format=xml&type=simpleinfo&category=12&pageindex=0&pagesize=20&action=1";
		AppContext app = AppContext.getInstance();
		try {
			InputStream ins = APIClient.http_get(app, url);
			List<SimpleInfo> datas = XMLUtil.getSimpleInfoInBatch(ins);
			System.out.println(datas);
		} catch (AppException e) {
			System.out.println("TestAPI.testHttpget()" + e.getMessage());

		} catch (IOException e) {
			System.out.println("TestAPI.testHttpget()" + e.getMessage());

		}

	}

	// 测试成功
	public void testHttp_get_detail() {
		String url = "http://www.sartino.com:8080/Server/servlet/ListServlet?format=xml&id=168&type=detailinfo";
		AppContext app = AppContext.getInstance();
		try {
			InputStream ins = APIClient.http_get(app, url);
			DetailInfo datas = DetailInfo.parse(ins);
			System.out.println(datas);
		} catch (AppException e) {
			System.out.println("TestAPI.testHttpget()" + e.getMessage());

		}

	}

	public void testLogin() {
		APIClient.userLogin("admin", "B036970A33B65511");
	}

	public void testReg() {
		APIClient.userRegister("zhanglei19920802", "B036970A33B65511");
	}
}
