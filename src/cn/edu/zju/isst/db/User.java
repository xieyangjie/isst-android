/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author theasir
 * 
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512168798814691417L;

	private int id;
	private String username;
	private String password;
	private String name;
	private int gender;
	private int grade;
	private int classId;
	private int majotId;
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

	public User(JSONObject jsonObject) {
		upadte(jsonObject);
	}

	public void upadte(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt("id");
			username = jsonObject.getString("username");
			password = jsonObject.getString("password");
			name = jsonObject.getString("name");
			gender = jsonObject.getInt("gender");
			grade = jsonObject.getInt("grade");
			classId = jsonObject.getInt("classId");
			majotId = jsonObject.getInt("majorId");
			cityId = jsonObject.getInt("cityId");
			email = jsonObject.getString("email");
			phone = jsonObject.getString("phone");
			qq = jsonObject.getString("qq");
			company = jsonObject.getString("company");
			position = jsonObject.getString("position");
			signature = jsonObject.getString("signature");
			cityPrincipal = jsonObject.getBoolean("cityPrincipal");
			privateQQ = jsonObject.getBoolean("privateQQ");
			privateEmail = jsonObject.getBoolean("privateEmail");
			privatePhone = jsonObject.getBoolean("privatePhone");
			privateCompany = jsonObject.getBoolean("privateCompany");
			privatePosition = jsonObject.getBoolean("privatePosition");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return majotId;
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
