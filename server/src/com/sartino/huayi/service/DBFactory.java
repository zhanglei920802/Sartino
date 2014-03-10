package com.sartino.huayi.service;
import java.sql.Connection;
/**
 * ���ݿ����Ӵ���������
 * 
 * 
 *
 */
public class DBFactory {
    
    /** ���ݿ����Ӷ���*/
    private DBConnection_jdbc dbConn = new DBConnection_jdbc();
    
    /**
     * ��ȡ���ݿ����Ӷ���ʵ��
     * 
     * @return
     */
    public DBConnection_jdbc getDBConnectionInstance(){
        /** ���Ϊnull�ʹ���һ���µ�ʵ���������ҷ���*/
        if(dbConn==null){
            dbConn = new DBConnection_jdbc();
            return dbConn;
        }
        /** �����Ϊnull��ֱ�ӷ��ص�ǰ��ʵ��������*/
        else{
            return dbConn;
        }
    }
    
    /** �ر����ݿ�����*/
    public void closeConnection(Connection conn){
        /** ���Ϊnull�ʹ���һ���µ�ʵ��������*/
        if(dbConn==null){
            dbConn = new DBConnection_jdbc();
        }
        dbConn.closeConnection(conn);/** ���ùر����ӵķ���*/
    }
    
}