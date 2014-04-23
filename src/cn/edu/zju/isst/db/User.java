/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.util.J;

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
	private String major;
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
		major = "";
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
		if (!J.isNullOrEmpty(jsonObject)) {
			if (J.isValidJsonValue("id", jsonObject)) {
				id = jsonObject.getInt("id");
			}
			if (J.isValidJsonValue("username", jsonObject)) {
				username = jsonObject.getString("username");
			}
			if (J.isValidJsonValue("password", jsonObject)) {
				password = jsonObject.getString("password");
			}
			if (J.isValidJsonValue("name", jsonObject)) {
				name = jsonObject.getString("name");
			}
			if (J.isValidJsonValue("gender", jsonObject)) {
				gender = jsonObject.getInt("gender");
			}
			if (J.isValidJsonValue("grade", jsonObject)) {
				grade = jsonObject.getInt("grade");
			}
			if (J.isValidJsonValue("classId", jsonObject)) {
				classId = jsonObject.getInt("classId");
			}
			if (J.isValidJsonValue("major", jsonObject)) {
				major = jsonObject.getString("major");
			}
			if (J.isValidJsonValue("cityId", jsonObject)) {
				cityId = jsonObject.getInt("cityId");
			}
			if (J.isValidJsonValue("email", jsonObject)) {
				email = jsonObject.getString("email");
			}
			if (J.isValidJsonValue("phone", jsonObject)) {
				phone = jsonObject.getString("phone");
			}
			if (J.isValidJsonValue("qq", jsonObject)) {
				qq = jsonObject.getString("qq");
			}
			if (J.isValidJsonValue("company", jsonObject)) {
				company = jsonObject.getString("company");
			}
			if (J.isValidJsonValue("position", jsonObject)) {
				position = jsonObject.getString("position");
			}
			if (J.isValidJsonValue("signature", jsonObject)) {
				signature = jsonObject.getString("signature");
			}
			if (J.isValidJsonValue("cityPrincipal", jsonObject)) {
				cityPrincipal = jsonObject.getBoolean("cityPrincipal");
			}
			if (J.isValidJsonValue("privateQQ", jsonObject)) {
				privateQQ = jsonObject.getBoolean("privateQQ");
			}
			if (J.isValidJsonValue("privateEmail", jsonObject)) {
				privateEmail = jsonObject.getBoolean("privateEmail");
			}
			if (J.isValidJsonValue("privatePhone", jsonObject)) {
				privatePhone = jsonObject.getBoolean("privatePhone");
			}
			if (J.isValidJsonValue("privateCompany", jsonObject)) {
				privateCompany = jsonObject.getBoolean("privateCompany");
			}
			if (J.isValidJsonValue("privatePosition", jsonObject)) {
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
	public String getMajor() {
		return major;
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

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @param privateQQ the privateQQ to set
	 */
	public void setPrivateQQ(boolean privateQQ) {
		this.privateQQ = privateQQ;
	}

	/**
	 * @param privateEmail the privateEmail to set
	 */
	public void setPrivateEmail(boolean privateEmail) {
		this.privateEmail = privateEmail;
	}

	/**
	 * @param privatePhone the privatePhone to set
	 */
	public void setPrivatePhone(boolean privatePhone) {
		this.privatePhone = privatePhone;
	}

	/**
	 * @param privateCompany the privateCompany to set
	 */
	public void setPrivateCompany(boolean privateCompany) {
		this.privateCompany = privateCompany;
	}

	/**
	 * @param privatePosition the privatePosition to set
	 */
	public void setPrivatePosition(boolean privatePosition) {
		this.privatePosition = privatePosition;
	}

}
