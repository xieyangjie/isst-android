package cn.edu.zju.isst.v2.campusactivity.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.model.CSTDataItem;

/**
 * Created by lqynydyxf on 2014/8/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTCampusActivity extends CSTDataItem<CSTCampusActivity> {
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
    public String publishername;
    @JsonProperty("updataAt")
    public long updatedAt;
    @JsonProperty("startTime")
    public long startTime;
    @JsonProperty("expireTime")
    public long expireTime;
    public CSTCampusActivity(){

    }
}