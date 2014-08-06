package cn.edu.zju.isst.v2.archive.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.user.data.CSTUser;

/**
 * Created by tan on 2014/8/6.
 */
public class CommonUser extends BasicUser {

    @JsonProperty("gender")
    public CSTUser.Gender gender;

    @JsonProperty("classId")
    public int clazzId;

    @JsonProperty("className")
    public String clazzName;

    @JsonProperty("grade")
    public String grade;

    @JsonProperty("cityId")
    public String cityId;

    @JsonProperty("cityName")
    public String cityName;

    @JsonProperty("major")
    public String major;

    @JsonProperty("company")
    public String company;

    @JsonProperty("position")
    public String jobTitle;

    @JsonProperty("signature")
    public String signature;

    public CommonUser() {
    }
}
