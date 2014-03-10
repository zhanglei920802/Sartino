package com.sartino.huayi.app.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.sartino.huayi.app.common.MD5Util;

public class TestMd5 {
	private static Map users = new HashMap();

	public static void main(String[] args) {
		String userName = "zyg";
		String password = "123";
		registerUser(userName, password);

		userName = "changong";
		password = "456";
		registerUser(userName, password);

		String loginUserId = "zyg";
		String pwd = "1232";
		try {
			if (loginValid(loginUserId, pwd)) {
				System.out.println("��ӭ��½������");
			} else {
				System.out.println("����������������룡����");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ע���û�
	 * 
	 * @param userName
	 * @param password
	 */
	public static void registerUser(String userName, String password) {
		String encryptedPwd = null;
		try {
			encryptedPwd = MD5Util.getEncryptedPwd(password);

			users.put(userName, encryptedPwd);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��֤��½
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean loginValid(String userName, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String pwdInDb = (String) users.get(userName);
		if (null != pwdInDb) { // ���û�����
			return MD5Util.validPassword(password, pwdInDb);
		} else {
			System.out.println("�����ڸ��û�������");
			return false;
		}
	}
}