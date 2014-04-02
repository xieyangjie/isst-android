/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.util.Judgement;

/**
 * 用户解析类
 * 
 * @author theasir
 * 
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512168798814691417L;

	/**
	 * 以下字段详见服务器接口文档
	 */
	private int id;
	private String username;
	private String password;
	private String name;
	private int gender;
	private int grade;
	private int classId;
	private int majorId;
	private int cityId;
	private String email;
	private String phone;
	private String qq;
	private String company;
	private String position;
	private String signature;
	private boolean cityPrincipal;
	private boolean privateQQ;
	private boolean privateEmail;
	private boolean privatePhone;
	private boolean privateCompany;
	private boolean privatePosition;

	/**
	 * 默认值初始化并更新
	 * 
	 * @param jsonObject
	 *            数据源
	 * @throws JSONException
	 *             未处理异常
	 */
	public User(JSONObject jsonObject) throws JSONException {
		id = -1;
		username = "";
		password = "";
		name = "";
		gender = -1;
		grade = -1;
		classId = -1;
		majorId = -1;
		cityId = -1;
		email = "";
		phone = "";
		qq = "";
		company = "";
		position = "";
		signature = "";
		cityPrincipal = false;
		privateQQ = false;
		privateEmail = false;
		privatePhone = false;
		privateCompany = false;
		privatePosition = false;
		update(jsonObject);
	}

	/**
	 * 更新数据，强制判断设计
	 * 
	 * @param jsonObject
	 *            数据源
	 * @throws JSONException
	 *             未处理异常
	 */
	public void update(JSONObject jsonObject) throws JSONException {
		if (!Judgement.isNullOrEmpty(jsonObject)) {
			if (jsonObject.has("id")
					&& !Judgement.isNullOrEmpty(jsonObject.get("id"))) {
				id = jsonObject.getInt("id");
			}
			if (jsonObject.has("username")
					&& !Judgement.isNullOrEmpty(jsonObject.get("id"))) {
				username = jsonObject.getString("username");
			}
			if (jsonObject.has("password")
					&& !Judgement.isNullOrEmpty(jsonObject.get("password"))) {
				password = jsonObject.getString("password");
			}
			if (jsonObject.has("name")
					&& !Judgement.isNullOrEmpty(jsonObject.get("name"))) {
				name = jsonObject.getString("name");
			}
			if (jsonObject.has("gender")
					&& !Judgement.isNullOrEmpty(jsonObject.get("gender"))) {
				gender = jsonObject.getInt("gender");
			}
			if (jsonObject.has("grade")
					&& !Judgement.isNullOrEmpty(jsonObject.get("grade"))) {
				grade = jsonObject.getInt("grade");
			}
			if (jsonObject.has("classId")
					&& !Judgement.isNullOrEmpty(jsonObject.get("classId"))) {
				classId = jsonObject.getInt("classId");
			}
			if (jsonObject.has("majorId")
					&& !Judgement.isNullOrEmpty(jsonObject.get("majorId"))) {
				majorId = jsonObject.getInt("majorId");
			}
			if (jsonObject.has("cityId")
					&& !Judgement.isNullOrEmpty(jsonObject.get("cityId"))) {
				cityId = jsonObject.getInt("cityId");
			}
			if (jsonObject.has("email")
					&& !Judgement.isNullOrEmpty(jsonObject.get("email"))) {
				email = jsonObject.getString("email");
			}
			if (jsonObject.has("phone")
					&& !Judgement.isNullOrEmpty(jsonObject.get("phone"))) {
				phone = jsonObject.getString("phone");
			}
			if (jsonObject.has("qq")
					&& !Judgement.isNullOrEmpty(jsonObject.get("qq"))) {
				qq = jsonObject.getString("qq");
			}
			if (jsonObject.has("company")
					&& !Judgement.isNullOrEmpty(jsonObject.get("company"))) {
				company = jsonObject.getString("company");
			}
			if (jsonObject.has("position")
					&& !Judgement.isNullOrEmpty(jsonObject.get("position"))) {
				position = jsonObject.getString("position");
			}
			if (jsonObject.has("signature")
					&& !Judgement.isNullOrEmpty(jsonObject.get("signature"))) {
				signature = jsonObject.getString("signature");
			}
			if (jsonObject.has("cityPrincipal")
					&& !Judgement
							.isNullOrEmpty(jsonObject.get("cityPrincipal"))) {
				cityPrincipal = jsonObject.getBoolean("cityPrincipal");
			}
			if (jsonObject.has("privateQQ")
					&& !Judgement.isNullOrEmpty(jsonObject.get("privateQQ"))) {
				privateQQ = jsonObject.getBoolean("privateQQ");
			}
			if (jsonObject.has("privateEmail")
					&& !Judgement.isNullOrEmpty(jsonObject.get("privateEmail"))) {
				privateEmail = jsonObject.getBoolean("privateEmail");
			}
			if (jsonObject.has("privatePhone")
					&& !Judgement.isNullOrEmpty(jsonObject.get("privatePhone"))) {
				privatePhone = jsonObject.getBoolean("privatePhone");
			}
			if (jsonObject.has("privateCompany")
					&& !Judgement.isNullOrEmpty(jsonObject
							.get("privateCompany"))) {
				privateCompany = jsonObject.getBoolean("privateCompany");
			}
			if (jsonObject.has("privatePosition")
					&& !Judgement.isNullOrEmpty(jsonObject
							.get("privatePosition"))) {
				privatePosition = jsonObject.getBoolean("privatePosition");
			}
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @return the classId
	 */
	public int getClassId() {
		return classId;
	}

	/**
	 * @return the majotId
	 */
	public int getMajotId() {
		return majorId;
	}

	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @return the cityPrincipal
	 */
	public boolean isCityPrincipal() {
		return cityPrincipal;
	}

	/**
	 * @return the privateQQ
	 */
	public boolean isPrivateQQ() {
		return privateQQ;
	}

	/**
	 * @return the privateEmail
	 */
	public boolean isPrivateEmail() {
		return privateEmail;
	}

	/**
	 * @return the privatePhone
	 */
	public boolean isPrivatePhone() {
		return privatePhone;
	}

	/**
	 * @return the privateCompany
	 */
	public boolean isPrivateCompany() {
		return privateCompany;
	}

	/**
	 * @return the privatePosition
	 */
	public boolean isPrivatePosition() {
		return privatePosition;
	}

}
