package cn.edu.zju.isst.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONArray;

/**
 * Created by i308844 on 7/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTRawParsedJsonArray {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("jsonArrayBody")
    private JSONArray jsonArrayBody;

    private CSTRawParsedJsonArray() {

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public JSONArray getJsonArrayBody() {
        return jsonArrayBody;
    }
}
