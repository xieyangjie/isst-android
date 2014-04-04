/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.util.Judgement;

/**
 * 通讯录解析类
 * 
 * @author xyj
 * 
 */
public class Alumni implements Serializable {

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
	private int grade;
	private int classId;
	private int majorId;
	private int cityId;
	private String company;
	

	/**
	 * 默认值初始化并更新
	 * 
	 * @param jsonObject
	 *            数据源
	 * @throws JSONException
	 *             未处理异常
	 */
	public Alumni(JSONObject jsonObject) throws JSONException {
		id = 0;
		name = "";// TODO raw string should not appear in code
		phone = "";
		grade = 0;
		classId = 0;
		majorId = 0;
		cityId = 0;
		company = "";
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
			if (Judgement.isValidJsonValue("name", jsonObject)) {
				name = jsonObject.getString("name");
			}
			if (Judgement.isValidJsonValue("phone", jsonObject)) {
				phone = jsonObject.getString("phone");
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
			if (Judgement.isValidJsonValue("company", jsonObject)) {
				company = jsonObject.getString("company");
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
	 * @return the majorId
	 */
	public int getMajorId() {
		return majorId;
	}
	
	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}
	
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

}
