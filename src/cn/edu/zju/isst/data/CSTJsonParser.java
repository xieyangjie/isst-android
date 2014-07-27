package cn.edu.zju.isst.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.fasterxml.jackson.databind.JavaType;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public static <T> List<T> getCollection(String jsonStr, Class <? extends List> collectionClazz,
              Class <T> elementClazz) throws IOException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClazz, elementClazz);
        List <T> listResult  = mapper.readValue(jsonStr, javaType);
        return listResult;
    }

    public static <K, V> Map<K, V> getMap(String jsonStr, Class <? extends Map> mapClazz ,Class<K> keyClazz,
              Class<V> valueClazz) throws IOException {
        JavaType javaType = mapper.getTypeFactory().constructMapType(mapClazz, keyClazz, valueClazz);
        Map <K,V> mapResult = mapper.readValue(jsonStr, javaType);
        return mapResult;
    }
}
