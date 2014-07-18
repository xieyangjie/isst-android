package cn.edu.zju.isst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import cn.edu.zju.isst.data.CSTJsonParser;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTJsonRaw {
    public int status;
    public String message;
    public Object body;

    private CSTJsonRaw(){

    }

    public static CSTJsonRaw create(byte[] data){
        CSTJsonRaw instance = null;
        try {
            instance = CSTJsonParser.readByte(data, CSTJsonRaw.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
