package cn.edu.zju.isst.v2.user.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import cn.edu.zju.isst.v2.archive.data.CSTConsumer;
import cn.edu.zju.isst.v2.archive.data.CommonUser;
import cn.edu.zju.isst.v2.model.CSTDataItem;

/**
 * Created by i308844 on 7/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSTUser extends CommonUser {

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
