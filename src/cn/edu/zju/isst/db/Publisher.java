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
public class Publisher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2853718318441053288L;

	private int id;
	private String name;
	private String phone;
	private String qq;
	private String email;
	
	public Publisher(JSONObject jsonObject){
		try {
			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			phone = jsonObject.getString("phone");
			qq = jsonObject.getString("qq");
			email = jsonObject.getString("qq");
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
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

}
