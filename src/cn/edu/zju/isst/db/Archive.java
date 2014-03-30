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
public class Archive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3086117832107474161L;

	private int id;
	private String title;
	private String description;
	private int updatedAt;
	private int publisherId;
	private Publisher publisher;
	private String content;

	public Archive(JSONObject jsonObject) {
		update(jsonObject);
	}

	public void update(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt("id");
			title = jsonObject.getString("title");
			description = jsonObject.getString("description");
			updatedAt = jsonObject.getInt("updatedAt");
			//publisher = new Publisher(jsonObject.getJSONObject("user"));
			publisher = null;
			if (jsonObject.has("userId")) {//列表请求时附带userId没有content
				publisherId = jsonObject.getInt("userId");
//				content = "";
				content = jsonObject.getString("content");
			} else if (jsonObject.has("content")) {//详情页请求时返回content
				content = jsonObject.getString("content");
				publisherId = publisher.getId();
			}else {//这种情况应该不会发生，是由服务器返回的json数据正确性保证的，参见接口文档
				publisherId = -1;
				content = "";
			}
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the updateAt
	 */
	public int getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @return the publisherId
	 */
	public int getPublisherId() {
		return publisherId;
	}

	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

}
