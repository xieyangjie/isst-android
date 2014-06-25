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
public class PushMessage implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2915741690852964093L;

    public int id;
    public String title;
    public String content;
    public long createdTime;
    
    public PushMessage(JSONObject jsonObject) throws JSONException{
	id = -1;
	title = "";
	content = "";
	createdTime = 0;
	update(jsonObject);
    }
    
    public void update(JSONObject jsonObject) throws JSONException{
	if (!J.isNullOrEmpty(jsonObject)) {
	    if (J.isValidJsonValue("id", jsonObject)) {
		id = jsonObject.getInt("id");
	    }
	    if (J.isValidJsonValue("title", jsonObject)) {
		title = jsonObject.getString("title");
	    }
	    if (J.isValidJsonValue("createdAt", jsonObject)) {
		createdTime = jsonObject.getLong("createdAt");
	    }
	    if (J.isValidJsonValue("content", jsonObject)) {
		content = jsonObject.getString("content");
	    }
	}
    }
}
