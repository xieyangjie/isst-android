package cn.edu.zju.isst.v2.city.data;

import cn.edu.zju.isst.v2.model.CSTDataItem;
import cn.edu.zju.isst.v2.user.data.CSTUser;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public CSTCity(){

    }
}
