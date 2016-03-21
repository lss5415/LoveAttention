package com.zykj.loveattention.data;

/**
 * @author lss 2015年7月14日 购物车商品信息
 *
 */
public class ChildrenItem {
//	private String cart_id;
//	private String buyer_id;
//	private String store_id;
//	private String store_name;
//	private String goods_id;
//	private String goods_name;
//	private String goods_price;
//	private String goods_num;
//	private String state;
//	private String goods_jingle;
//	private String goods_spec;
//	private String goods_freight;
//	private String goods_storage;
//	private String goods_image_url;
//	private String goods_sum;
//	private String goods_total;
	private String sid;
	private String price;
	private String imgpath;
	private String goodsid;
	private String goodspecif;
	private String quantity;
	private String goodsName;
	private String oldprice;
	
	private boolean isChecked;//是否选中

	public ChildrenItem() {
	}

	
	public ChildrenItem(String sid,String price, String imgpath, String goodsid, String goodspecif, String quantity, String goodsName, String oldprice) {
		this.sid = sid;
		this.price = price;
		this.imgpath = imgpath;
		this.goodsid = goodsid;
		this.goodspecif = goodspecif;
		this.quantity = quantity;
		this.goodsName = goodsName;
		this.oldprice = oldprice;
	}

	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getImgpath() {
		return imgpath;
	}


	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}


	public String getGoodsid() {
		return goodsid;
	}


	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}


	public String getGoodspecif() {
		return goodspecif;
	}


	public void setGoodspecif(String goodspecif) {
		this.goodspecif = goodspecif;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getOldprice() {
		return oldprice;
	}


	public void setOldprice(String oldprice) {
		this.oldprice = oldprice;
	}


	public boolean isChecked() {
		return isChecked;
	}


	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
