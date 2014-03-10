package com.sartino.huayi.service;

import java.sql.Connection;
import java.sql.DriverManager;

import com.sartino.huayi.config.NetConfig;

/**
 * 获取数据库连接
 * 
 * 
 * 
 */
public class DBConnection_jdbc {

	/** Oracle数据库连接URL */
	private final static String DB_URL = "jdbc:mysql://" + NetConfig.DB_HOST
			+ ":3306/v7";
	/** Oracle数据库连接驱动 */

	private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	/** 数据库用户名 */
	private final static String DB_USERNAME = "root";

	/** 数据库密码 */
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
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		/** 声明Connection连接对象 */
		Connection conn = null;
		try {
			/** 使用Class.forName()方法自动创建这个驱动程序的实例且自动调用DriverManager来注册它 */
			Class.forName(DB_DRIVER);
			/** 通过DriverManager的getConnection()方法获取数据库连接 */
			conn = DriverManager
					.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param connect
	 */
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				/** 判断当前连接连接对象如果没有被关闭就调用关闭方法 */
				if (!conn.isClosed()) {
					conn.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}