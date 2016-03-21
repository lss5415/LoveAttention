package com.zykj.loveattention.data;

import java.util.ArrayList;

/**
 * @author  lc 
 * @date 创建时间：2016-1-9 下午4:45:52 
 * @version 1.0 
 * @Description 搜索结果一级列表
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"火锅"
    },
    "data":[
        {
            "remark":"优惠便利",
            "rebaterate":2.5,
            "mdesc":"商户描述信息2",
            "id":1,
            "needreserve":0,
            "ismail":null,
            "name":"东北麻辣烫",
            "longitude":"118.336462",
            "holidayavailable":0,
            "opentime":"2006-00-18 00:00",
            "sharenum":20,
            "visitnum":20,
            "rstate":0,
            "announcement":null,
            "perperson":20,
            "isrebate":0,
            "label":"高德",
            "categoryid":10,
            "imgpath":"20160108/20160108140650538.jpg",
            "goodList":Array[2],
            "address":"临沂市兰山区通达路",
            "stars":3,
            "latitude":"35.072076",
            "districtid":2,
            "telephone":"010-23456789-2"
        }
    ]
}
 */
public class SearchList {
	private String imgpath;
	private int stars;
	private String name;
	private String remark;
	private int perperson;
	private String latitude;
	private String longitude;
	private double distance;
	private ArrayList<GoodList> goodList;
	private String id;
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getPerperson() {
		return perperson;
	}
	public void setPerperson(int perperson) {
		this.perperson = perperson;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public ArrayList<GoodList> getGoodList() {
		return goodList;
	}
	public void setGoodList(ArrayList<GoodList> goodList) {
		this.goodList = goodList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
