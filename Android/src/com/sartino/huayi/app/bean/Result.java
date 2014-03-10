package com.sartino.huayi.app.bean;

/**
 * �û���Ϣ
 * 
 * @author Administrator
 * 
 */
public class Result extends Base {

	private static final long serialVersionUID = -842170887290041637L;
	private int errorCode;
	private String errorMessage;

	/**
	 * ��¼�ɹ�
	 * 
	 * @return
	 */
	public boolean OK() {
		return errorCode == 1;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public boolean REG_OK() {
		return 0x08 == errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "Result [errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + "]";
	}

}
