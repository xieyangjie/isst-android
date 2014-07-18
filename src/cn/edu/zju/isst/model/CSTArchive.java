package cn.edu.zju.isst.model;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;

import cn.edu.zju.isst.data.CSTJsonParser;

/**
 * Created by i308844 on 7/17/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTArchive {

    public int id;
    public String title;
    public String description;
    public long updateAt;
    public int publisherId;
    public CSTUser publisher;
    public String content;

    private CSTArchive(){

    }

    public static CSTArchive create(byte[] data){
        CSTArchive instance = null;
        try {
            instance = CSTJsonParser.readByte(data, CSTArchive.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instance;
    }

    public static CSTArchive create(JSONObject jsonObject){
        CSTArchive instance = null;
        try {
            instance = CSTJsonParser.readJsonObject(jsonObject, CSTArchive.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
