package cn.edu.zju.isst.v2.contact.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.data.CommonUser;

/**
 * Created by tan on 2014/8/3.
 */
public class CSTAlumni extends CommonUser {
    @JsonProperty("cityPrincipal")
    public boolean cityPrincipal;

    @JsonProperty("privatePosition")
    public boolean pvtPosition;

    @JsonProperty("privateCompany")
    public boolean pvtCompany;

    @JsonProperty("privateQQ")
    public boolean pvtQQ;

    @JsonProperty("privateEmail")
    public boolean pvtEmail;

    @JsonProperty("privatePhone")
    public boolean pvtPhone;

    public CSTAlumni() {
    }
}
