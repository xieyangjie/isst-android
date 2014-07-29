package cn.edu.zju.isst.v2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTUser {

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
    private int id;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("password")
    private String pwd;

    @JsonProperty("name")
    private String name;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("grade")
    private int grade;

    @JsonProperty("classId")
    private int clazzId;

    @JsonProperty("className")
    private String clazzName;

    @JsonProperty("major")
    private String majorName;

    @JsonProperty("cityId")
    private int cityId;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phoneNum;

    @JsonProperty("qq")
    private String qqNum;

    @JsonProperty("company")
    private String company;

    @JsonProperty("position")
    private String jobTitle;

    @JsonProperty("signature")
    private String sign;

    @JsonProperty("cityPrincipal")
    private boolean castellan;

    @JsonProperty("privateQQ")
    private boolean pvtQq;

    @JsonProperty("privateEmail")
    private boolean pvtEmail;

    @JsonProperty("privatePhone")
    private boolean pvtPhoneNum;

    @JsonProperty("privateCompany")
    private boolean pvtCompany;

    @JsonProperty("privatePosition")
    private boolean pvtJobTitle;

    private CSTUser() {

    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getGrade() {
        return grade;
    }

    public int getClazzId() {
        return clazzId;
    }

    public String getClazzName() {
        return clazzName;
    }

    public String getMajorName() {
        return majorName;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getQqNum() {
        return qqNum;
    }

    public String getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getSign() {
        return sign;
    }

    public boolean isCastellan() {
        return castellan;
    }

    public boolean isPvtQq() {
        return pvtQq;
    }

    public boolean isPvtEmail() {
        return pvtEmail;
    }

    public boolean isPvtPhoneNum() {
        return pvtPhoneNum;
    }

    public boolean isPvtCompany() {
        return pvtCompany;
    }

    public boolean isPvtJobTitle() {
        return pvtJobTitle;
    }
}
