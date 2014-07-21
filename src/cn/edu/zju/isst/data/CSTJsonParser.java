package cn.edu.zju.isst.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.edu.zju.isst.util.T;

/**
 * Created by i308844 on 7/15/14.
 */
public class CSTJsonParser {

    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JsonOrgModule());
    }

    public static <T> T readByte(byte[] data, Class<T> clazz) throws IOException {
        return mapper.readValue(data, clazz);
    }

    public static <T> T readString(String jsonString, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonString, clazz);
    }

    public static <T> T readJsonObject(JSONObject jsonObject, Class<T> clazz) throws IOException {
        return mapper.readValue(String.valueOf(jsonObject), clazz);
    }

}
