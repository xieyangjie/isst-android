package cn.edu.zju.isst.v2.archive.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.model.CSTDataItem;
import cn.edu.zju.isst.v2.user.data.CSTUser;

/**
 * Created by tan on 2014/7/30.
 */
public class CSTCity extends CSTDataItem<CSTCity> {

    @JsonProperty("id")
    public int id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("cityMaster")
    public CSTUser cityMaster;

}
