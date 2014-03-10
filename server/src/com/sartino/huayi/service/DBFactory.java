package com.sartino.huayi.service;
import java.sql.Connection;
/**
 * 数据库连接创建工厂类
 * 
 * 
 *
 */
public class DBFactory {
    
    /** 数据库连接对象*/
    private DBConnection_jdbc dbConn = new DBConnection_jdbc();
    
    /**
     * 获取数据库连接对象实例
     * 
     * @return
     */
    public DBConnection_jdbc getDBConnectionInstance(){
        /** 如果为null就创建一个新的实例化对象且返回*/
        if(dbConn==null){
            dbConn = new DBConnection_jdbc();
            return dbConn;
        }
        /** 如果不为null就直接返回当前的实例化对象*/
        else{
            return dbConn;
        }
    }
    
    /** 关闭数据库连接*/
    public void closeConnection(Connection conn){
        /** 如果为null就创建一个新的实例化对象*/
        if(dbConn==null){
            dbConn = new DBConnection_jdbc();
        }
        dbConn.closeConnection(conn);/** 调用关闭连接的方法*/
    }
    
}