package cn.edu.zju.isst.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONObject;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTRawParsedJsonObject {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("jsonObjectBody")
    private JSONObject jsonObjectBody;

    private CSTRawParsedJsonObject() {

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getJsonObjectBody() {
        return jsonObjectBody;
    }
}
