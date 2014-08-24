package cn.edu.zju.isst.v2.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.zju.isst.v2.model.CSTDataItem;

/**
 * Created by tan on 2014/8/3.
 */
public class CSTRestaurant extends CSTDataItem<CSTRestaurant> {

    @JsonProperty("id")
    public int id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("picture")
    public String picture;

    @JsonProperty("address")
    public String address;

    @JsonProperty("hotline")
    public String hotLine;

    @JsonProperty("businessHours")
    public String businessHours;

    @JsonProperty("content")
    public String content;

    @JsonProperty("restaurantMenu")
    public CSTRestaurantMenu restaurantMenu;

    public CSTRestaurant() {
    }
}
