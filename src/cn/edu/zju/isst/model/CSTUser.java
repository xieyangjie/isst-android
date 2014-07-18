package cn.edu.zju.isst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;

import cn.edu.zju.isst.data.CSTJsonParser;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTUser {

    private CSTUser(){

    }

    public static CSTUser create(byte[] data){
        CSTUser instance = null;
        try {
            instance = CSTJsonParser.readByte(data, CSTUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
