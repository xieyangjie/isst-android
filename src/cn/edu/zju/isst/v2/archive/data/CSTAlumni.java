package cn.edu.zju.isst.v2.archive.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.model.CSTDataItem;
import cn.edu.zju.isst.v2.user.data.CSTUser;

/**
 * Created by tan on 2014/8/3.
 */
public class CSTAlumni extends CSTDataItem<CSTAlumni> {

    @JsonProperty("id")
    public int id;

    @JsonProperty("username")
    public String userName;

    @JsonProperty("name")
    public String name;

    @JsonProperty("gender")
    public CSTUser.Gender gender;

    @JsonProperty("grade")
    public int grade;

    @JsonProperty("classId")
    public int clazzId;

    @JsonProperty("classname")
    public String clazzName;

    @JsonProperty("major")
    public String major;

    @JsonProperty("email")
    public String email;

    @JsonProperty("phone")
    public String phoneNum;

    @JsonProperty("qq")
    public String qqNum;

    @JsonProperty("company")
    public String company;

    @JsonProperty("position")
    public String jobTitle;

    @JsonProperty("signature")
    public String signature;

    @JsonProperty("cityId")
    public int cityId;

    @JsonProperty("cityName")
    public String cityName;

    public CSTAlumni() {
    }
}
