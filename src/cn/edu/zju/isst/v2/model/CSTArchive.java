package cn.edu.zju.isst.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by i308844 on 7/17/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTArchive {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("updatedAt")
    private long updateTime;

    @JsonProperty("userId")
    private int publisherId;

    @JsonProperty("user")
    private CSTUser publisher;

    @JsonProperty("content")
    private String content;

    private CSTArchive() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public CSTUser getPublisher() {
        return publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
