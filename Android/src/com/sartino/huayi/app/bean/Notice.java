package com.sartino.huayi.app.bean;

import java.io.Serializable;

/**
 * 通知信息实体类
 * 
 * @author Administrator
 * 
 */
public class Notice implements Serializable {

	private static final long serialVersionUID = -5278937688552212422L;

	public static final int NOTICE_TYPE_NEWMESSAGE = 0x0;
	private int mNewMessage = 0;

	public int getmNewMessage() {
		return mNewMessage;
	}

	public void setmNewMessage(int mNewMessage) {
		this.mNewMessage = mNewMessage;
	}

	public Notice() {
		super();

	}

	public Notice(int mNewMessage) {
		super();
		this.mNewMessage = mNewMessage;
	}

	@Override
	public String toString() {
		return "Notice [mNewMessage=" + mNewMessage + "]";
	}

}
