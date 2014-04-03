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
			if (Judgement.isValidJsonValue("id", jsonObject)) {
				id = jsonObject.getInt("id");
			}
			if (Judgement.isValidJsonValue("username", jsonObject)) {
				username = jsonObject.getString("username");
			}
			if (Judgement.isValidJsonValue("password", jsonObject)) {
				password = jsonObject.getString("password");
			}
			if (Judgement.isValidJsonValue("name", jsonObject)) {
				name = jsonObject.getString("name");
			}
			if (Judgement.isValidJsonValue("gender", jsonObject)) {
				gender = jsonObject.getInt("gender");
			}
			if (Judgement.isValidJsonValue("grade", jsonObject)) {
				grade = jsonObject.getInt("grade");
			}
			if (Judgement.isValidJsonValue("classId", jsonObject)) {
				classId = jsonObject.getInt("classId");
			}
			if (Judgement.isValidJsonValue("majorId", jsonObject)) {
				majorId = jsonObject.getInt("majorId");
			}
			if (Judgement.isValidJsonValue("cityId", jsonObject)) {
				cityId = jsonObject.getInt("cityId");
			}
			if (Judgement.isValidJsonValue("email", jsonObject)) {
				email = jsonObject.getString("email");
			}
			if (Judgement.isValidJsonValue("phone", jsonObject)) {
				phone = jsonObject.getString("phone");
			}
			if (Judgement.isValidJsonValue("qq", jsonObject)) {
				qq = jsonObject.getString("qq");
			}
			if (Judgement.isValidJsonValue("company", jsonObject)) {
				company = jsonObject.getString("company");
			}
			if (Judgement.isValidJsonValue("position", jsonObject)) {
				position = jsonObject.getString("position");
			}
			if (Judgement.isValidJsonValue("signature", jsonObject)) {
				signature = jsonObject.getString("signature");
			}
			if (Judgement.isValidJsonValue("cityPrincipal", jsonObject)) {
				cityPrincipal = jsonObject.getBoolean("cityPrincipal");
			}
			if (Judgement.isValidJsonValue("privateQQ", jsonObject)) {
				privateQQ = jsonObject.getBoolean("privateQQ");
			}
			if (Judgement.isValidJsonValue("privateEmail", jsonObject)) {
				privateEmail = jsonObject.getBoolean("privateEmail");
			}
			if (Judgement.isValidJsonValue("privatePhone", jsonObject)) {
				privatePhone = jsonObject.getBoolean("privatePhone");
			}
			if (Judgement.isValidJsonValue("privateCompany", jsonObject)) {
				privateCompany = jsonObject.getBoolean("privateCompany");
			}
			if (Judgement.isValidJsonValue("privatePosition", jsonObject)) {
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
