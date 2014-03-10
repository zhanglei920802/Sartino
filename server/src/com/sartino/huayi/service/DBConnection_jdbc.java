package com.sartino.huayi.service;

import java.sql.Connection;
import java.sql.DriverManager;

import com.sartino.huayi.config.NetConfig;

/**
 * ��ȡ���ݿ�����
 * 
 * 
 * 
 */
public class DBConnection_jdbc {

	/** Oracle���ݿ�����URL */
	private final static String DB_URL = "jdbc:mysql://" + NetConfig.DB_HOST
			+ ":3306/v7";
	/** Oracle���ݿ��������� */

	private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	/** ���ݿ��û��� */
	private final static String DB_USERNAME = "root";

	/** ���ݿ����� */
	private final static String DB_PASSWORD = "pkx.cdut";

	public static String getDbUrl() {
		return DB_URL;
	}

	public static String getDbDriver() {
		return DB_DRIVER;
	}

	public static String getDbUsername() {
		return DB_USERNAME;
	}

	public static String getDbPassword() {
		return DB_PASSWORD;
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 */
	public Connection getConnection() {
		/** ����Connection���Ӷ��� */
		Connection conn = null;
		try {
			/** ʹ��Class.forName()�����Զ�����������������ʵ�����Զ�����DriverManager��ע���� */
			Class.forName(DB_DRIVER);
			/** ͨ��DriverManager��getConnection()������ȡ���ݿ����� */
			conn = DriverManager
					.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param connect
	 */
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				/** �жϵ�ǰ�������Ӷ������û�б��رվ͵��ùرշ��� */
				if (!conn.isClosed()) {
					conn.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}