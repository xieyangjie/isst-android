/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.util.Judgement;

/**
 * @author theasir
 * 
 */
public class CampusActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6366035213405880557L;

	private int id;
	private String title;
	private String picture;
	private String description;
	private String content;
	private String publisherName;
	private long updatedAt;
	private long startTime;
	private long expireTime;
	private String updateTimeString;
	private String startTimeString;
	private String expireTimeString;

	/**
	 * 默认值初始化并更新
	 * 
	 * @param jsonObject
	 *            数据源
	 * @throws JSONException
	 *             未处理异常
	 */
	public CampusActivity(JSONObject jsonObject) throws JSONException {
		id = -1;
		title = "";
		picture = "";
		description = "";
		content = "";
		publisherName = "管理员";
		updatedAt = 0;
		startTime = 0;
		expireTime = 0;
		updateTimeString = "";
		startTimeString = "";
		expireTimeString = "";
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
			if (Judgement.isValidJsonValue("title", jsonObject)) {
				title = jsonObject.getString("title");
			}
			if (Judgement.isValidJsonValue("picture", jsonObject)) {
				picture = jsonObject.getString("picture");
			}
			if (Judgement.isValidJsonValue("description", jsonObject)) {
				description = jsonObject.getString("description");
			}
			if (Judgement.isValidJsonValue("content", jsonObject)) {
				content = jsonObject.getString("content");
			}
			if (Judgement.isValidJsonValue("user", jsonObject)) {
				publisherName = jsonObject.getJSONObject("user").getString(
						"name");
			}
			if (Judgement.isValidJsonValue("updatedAt", jsonObject)) {
				updatedAt = jsonObject.getLong("updatedAt");
				updateTimeString = timeLongToString(updatedAt);
			}
			if (Judgement.isValidJsonValue("startTime", jsonObject)) {
				updatedAt = jsonObject.getLong("startTime");
				startTimeString = timeLongToString(startTime);
			}
			if (Judgement.isValidJsonValue("expireTime", jsonObject)) {
				updatedAt = jsonObject.getLong("expireTime");
				expireTimeString = timeLongToString(expireTime);
			}
		}
	}

	private String timeLongToString(long time) {
		if (time > 0) {
			Date date = new Date(time);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
			return df.format(date);
		}
		return "";
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
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * @return the updatedAt
	 */
	public long getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @return the expireTime
	 */
	public long getExpireTime() {
		return expireTime;
	}

	/**
	 * @return the updateTimeString
	 */
	public String getUpdateTimeString() {
		return updateTimeString;
	}

	/**
	 * @return the startTimeString
	 */
	public String getStartTimeString() {
		return startTimeString;
	}

	/**
	 * @return the expireTimeString
	 */
	public String getExpireTimeString() {
		return expireTimeString;
	}
}
