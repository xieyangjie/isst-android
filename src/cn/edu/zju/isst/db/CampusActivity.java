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
	    if (J.isValidJsonValue("title", jsonObject)) {
		title = jsonObject.getString("title");
	    }
	    if (J.isValidJsonValue("picture", jsonObject)) {
		picture = jsonObject.getString("picture");
	    }
	    if (J.isValidJsonValue("description", jsonObject)) {
		description = jsonObject.getString("description");
	    }
	    if (J.isValidJsonValue("content", jsonObject)) {
		content = jsonObject.getString("content");
	    }
	    if (J.isValidJsonValue("user", jsonObject)) {
		publisherName = jsonObject.getJSONObject("user").getString(
			"name");
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
}
