package cn.edu.zju.isst.v2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zju.isst.v2.net.CSTStatusInfo;

/**
 * Created by i308844 on 7/31/14.
 */
public abstract class CSTDataItem<T> implements Serializable{

    private CSTStatusInfo statusInfo;

    public List<T> itemList = new ArrayList<>();

    public CSTDataItem<T> setStatusInfo(CSTStatusInfo statusInfo){
        this.statusInfo = statusInfo;
        return this;
    }

    public CSTStatusInfo getStatusInfo(){
        return statusInfo;
    }

}
