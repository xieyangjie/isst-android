package cn.edu.zju.isst.v2.campusevent.data;

import cn.edu.zju.isst.v2.model.CSTDataItem;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tan on 2014/7/30.
 */
public class CSTCampusEvent extends CSTDataItem<CSTCampusEvent> {

    @JsonProperty("id")
    public int id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("picture")
    public String picture;

    @JsonProperty("description")
    public String description;

    @JsonProperty("content")
    public String content;

    @JsonProperty("publisherName")
    public String pubName;

    @JsonProperty("updatedAt")
    public long updatedAt;

    @JsonProperty("startTime")
    public long startTime;

    @JsonProperty("expireTime")
    public long expireTime;

    public CSTCampusEvent() {
    }
}
