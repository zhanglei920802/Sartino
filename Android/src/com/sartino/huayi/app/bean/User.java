package com.sartino.huayi.app.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User extends Base {
	private static final long serialVersionUID = -7714383603428388775L;

	private String mUserName = null;
	private String mPwd = null;
	private String lastLogin = null;
	private String introduce = null;
	private int id = 0;
	private int gid = 0;
	private Result validate;// 用户是否合法

	public String getmUserName() {
		return mUserName;
	}

	public User() {
		super();
	}

	public User(String mUserName, String mPwd, String lastLogin) {
		super();
		this.mUserName = mUserName;
		this.mPwd = mPwd;
		this.lastLogin = lastLogin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [mUserName=" + mUserName + ", mPwd=" + mPwd
				+ ", lastLogin=" + lastLogin + ", introduce=" + introduce
				+ ", id=" + id + ", gid=" + gid + ", validate=" + validate
				+ "]";
	}

	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getPwd() {
		return mPwd;
	}

	public void setPwd(String mPwd) {
		this.mPwd = mPwd;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Result getValidate() {
		return validate;
	}

	public void setValidate(Result validate) {
		this.validate = validate;
	}

	public String getmPwd() {
		return mPwd;
	}

	public void setmPwd(String mPwd) {
		this.mPwd = mPwd;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	// [{"code":0,"msg":{"uid":1,"gid":3,"introduce":""}}]
	// [{"code":0,"msg":"sddd"]
	public static User parseJson(JSONArray data) {
		if (data == null || data.equals("")) {
			System.out.println("User.parseJson()" + "数据为空");
			return null;
		}
		User user = null;
		try {
			JSONObject jsonArray = data.getJSONObject(0);
			user = new User();
			Result rs = new Result();
			user.setValidate(rs);
			// code
			rs.setErrorCode(jsonArray.getInt("code"));
			// 额外信息
			JSONObject msg = null;

			try {// 假设登录成功
				msg = jsonArray.getJSONObject("msg");
				user.setId(msg.getInt("uid"));
				user.setIntroduce(msg.getString("introduce"));
				user.setGid(msg.getInt("gid"));

			} catch (Exception e) {
				msg = null;
				rs.setErrorMessage(jsonArray.getString("msg"));
			}
		} catch (JSONException e) {

			System.out.println("User.parseJson()" + "数据转换出错了");
		}

		return user;

	}
}
