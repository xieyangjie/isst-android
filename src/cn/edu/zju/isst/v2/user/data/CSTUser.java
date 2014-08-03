package cn.edu.zju.isst.v2.user.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import cn.edu.zju.isst.v2.model.CSTDataItem;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTUser extends CSTDataItem<CSTUser>{

    public enum Gender {
        MALE(1), FEMALE(2);

        private final int value;

        private String typeName;

        Gender(int value) {
            this.value = value;
            this.typeName = value == 1 ? "男" : value == 2 ? "女" : null;
        }

        public String getTypeName() {
            return typeName;
        }

        @JsonValue
        public int getValue() {
            return this.value;
        }

        @JsonCreator
        public static Gender fromValue(int typeCode) {
            for (Gender g : Gender.values()) {
                if (g.value == typeCode) {
                    return g;
                }
            }
            throw new IllegalArgumentException("Invalid Gender type code: " + typeCode);
        }


        @Override
        public String toString() {
            return this.typeName;
        }
    }

    @JsonProperty("id")
    public int id;

    @JsonProperty("username")
    public String userName;

    @JsonProperty("password")
    public String pwd;

    @JsonProperty("name")
    public String name;

    @JsonProperty("gender")
    public Gender gender;

    @JsonProperty("grade")
    public int grade;

    @JsonProperty("classId")
    public int clazzId;

    @JsonProperty("className")
    public String clazzName;

    @JsonProperty("major")
    public String majorName;

    @JsonProperty("cityId")
    public int cityId;

    @JsonProperty("cityName")
    public String cityName;

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
    public String sign;

    @JsonProperty("cityPrincipal")
    public boolean castellan;

    @JsonProperty("privateQQ")
    public boolean pvtQq;

    @JsonProperty("privateEmail")
    public boolean pvtEmail;

    @JsonProperty("privatePhone")
    public boolean pvtPhoneNum;

    @JsonProperty("privateCompany")
    public boolean pvtCompany;

    @JsonProperty("privatePosition")
    public boolean pvtJobTitle;

    public CSTUser() {

    }

}
