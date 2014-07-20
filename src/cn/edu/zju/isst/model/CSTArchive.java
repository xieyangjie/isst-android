package cn.edu.zju.isst.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by i308844 on 7/17/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTArchive extends CSTJsonBaseObject {

    public int id;
    public String title;
    public String description;
    public long updateAt;
    public int publisherId;
    public CSTUser publisher;
    public String content;

    private CSTArchive() {

    }

}
