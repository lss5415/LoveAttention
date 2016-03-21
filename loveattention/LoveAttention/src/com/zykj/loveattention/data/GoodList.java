package com.zykj.loveattention.data;
/**
 * @author  lc 
 * @date 创建时间：2016-1-9 下午4:50:13 
 * @version 1.0 
 * @Description 搜索结果的二级列表
 * "goodList":[
                {
                    "remark":"商品描述信息",
                    "menuid":1,
                    "shiptype":0,
                    "merchantid":1,
                    "postage":0,
                    "id":4,
                    "goodstock":1,
                    "imgpath":"20151205/20151205094121288.png",
                    "price":0.01,
                    "isfreepost":1,
                    "stars":4,
                    "advertid":2,
                    "name":"彤德莱火锅",
                    "paynum":15,
                    "intime":"2015-09-14 15:58:30",
                    "sharenum":10,
                    "specifdetail":null,
                    "visitnum":25,
                    "oldprice":20
                },
                {
                    "remark":"商品描述信息",
                    "menuid":1,
                    "shiptype":0,
                    "merchantid":1,
                    "postage":0,
                    "id":5,
                    "goodstock":1,
                    "imgpath":"20151205/20151205094113675.jpg",
                    "price":0.01,
                    "isfreepost":1,
                    "stars":3,
                    "advertid":2,
                    "name":"蒙山生态火锅",
                    "paynum":15,
                    "intime":"2015-09-14 15:58:30",
                    "sharenum":10,
                    "specifdetail":null,
                    "visitnum":25,
                    "oldprice":20
                }
            ],
 */
public class GoodList {
	private double price;
	private int paynum;
	private String name;
	private String id;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getPaynum() {
		return paynum;
	}
	public void setPaynum(int paynum) {
		this.paynum = paynum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
