package cn.edu.zju.isst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTJsonRaw {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("body")
    private JSONObject body;

    private CSTJsonRaw() {

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getBody() {
        return body;
    }
}
