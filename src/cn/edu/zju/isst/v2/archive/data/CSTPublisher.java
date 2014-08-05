package cn.edu.zju.isst.v2.archive.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.model.CSTDataItem;

/**
 * Created by tan on 2014/8/3.
 */
public class CSTPublisher extends CSTDataItem<CSTPublisher> {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phoneNum;

    @JsonProperty("qq")
    private String qqNum;

    @JsonProperty("email")
    private String email;

    public CSTPublisher() {
    }
}
