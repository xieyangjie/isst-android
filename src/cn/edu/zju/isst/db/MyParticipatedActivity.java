/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.util.J;

/**
 * @author theasir
 * 
 */
public class MyParticipatedActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 873372366398839689L;
	public int id;
	public String title;
	public String imgUrl;
	public int cityId;
	public String location;
	public long startTime;
	public long expireTime;
	public long updatedAt;
	public String content;
	public Publisher publisher;

	public MyParticipatedActivity(JSONObject jsonObject) throws JSONException {
		id = -1;
		title = "";
		imgUrl = "";
		cityId = -1;
		location = "";
		startTime = 0;
		expireTime = 0;
		updatedAt = 0;
		content = "";
		publisher = new Publisher(null);
		update(jsonObject);
	}

	public void update(JSONObject jsonObject) throws JSONException {
		if (!J.isNullOrEmpty(jsonObject)) {
			if (J.isValidJsonValue("id", jsonObject)) {
				id = jsonObject.getInt("id");
			}
			if (J.isValidJsonValue("title", jsonObject)) {
				title = jsonObject.getString("title");
			}
			if (J.isValidJsonValue("picture", jsonObject)) {
				imgUrl = jsonObject.getString("picture");
			}
			if (J.isValidJsonValue("cityId", jsonObject)) {
				cityId = jsonObject.getInt("cityId");
			}
			if (J.isValidJsonValue("location", jsonObject)) {
				location = jsonObject.getString("location");
			}
			if (J.isValidJsonValue("user", jsonObject)) {
				publisher.update(jsonObject.getJSONObject("user"));
			}
			if (J.isValidJsonValue("updatedAt", jsonObject)) {
				updatedAt = jsonObject.getLong("updatedAt");
			}
			if (J.isValidJsonValue("startTime", jsonObject)) {
				updatedAt = jsonObject.getLong("startTime");
			}
			if (J.isValidJsonValue("expireTime", jsonObject)) {
				updatedAt = jsonObject.getLong("expireTime");
			}
			if (J.isValidJsonValue("content", jsonObject)) {
				content = jsonObject.getString("content");
			}
		}
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public int getCityId() {
		return cityId;
	}

	public String getLocation() {
		return location;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public String getContent() {
		return content;
	}

	public Publisher getPublisher() {
		return publisher;
	}
}
