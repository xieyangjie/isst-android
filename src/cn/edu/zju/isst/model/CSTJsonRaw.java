package cn.edu.zju.isst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTJsonRaw extends CSTJsonBaseObject {

    public int status;

    public String message;

    public Object body;

    private CSTJsonRaw() {

    }

}
