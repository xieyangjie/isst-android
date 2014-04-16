/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.util.J;

/**
 * 发布者解析类
 * 
 * @author theasir
 * 
 */
public class Publisher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2853718318441053288L;

	/**
	 * 以下字段详见服务器接口文档
	 */
	private int id;
	private String name;
	private String phone;
	private String qq;
	private String email;

	/**
	 * 默认值初始化并更新
	 * 
	 * @param jsonObject
	 *            数据源
	 * @throws JSONException
	 *             未处理异常
	 */
	public Publisher(JSONObject jsonObject) throws JSONException {
		id = 0;
		name = "管理员";// TODO raw string should not appear in code
		phone = "";
		qq = "";
		email = "";
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
			if (J.isValidJsonValue("name", jsonObject)) {
				name = jsonObject.getString("name");
			}
			if (J.isValidJsonValue("phone", jsonObject)) {
				phone = jsonObject.getString("phone");
			}
			if (J.isValidJsonValue("qq", jsonObject)) {
				qq = jsonObject.getString("qq");
			}
			if (J.isValidJsonValue("email", jsonObject)) {
				email = jsonObject.getString("email");
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
