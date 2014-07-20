package cn.edu.zju.isst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by i308844 on 7/19/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTJsonBaseObject {

    public CSTJsonBaseObject create() {
        CSTJsonBaseObject instance = new CSTJsonBaseObject();

        return instance;
    }
}
