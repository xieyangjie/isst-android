package cn.edu.zju.isst.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by i308844 on 7/15/14.
 */
public class CSTJsonParser {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readByte(byte[] data, Class<T> clazz) throws IOException {
        T instance = mapper.readValue(data, clazz);

        return instance;
    }

    public static <T> T readJsonObject(JSONObject jsonObject, Class<T> clazz) throws IOException {
        T instance = mapper.readValue(jsonObject.toString(), clazz);

        return instance;
    }
}
