package com.zykj.loveattention.data;

import java.util.List;

/**
 * @author lss 2015年7月14日 购物车店铺信息
 *
 */
public class GroupItem {

	private String merchantid;
	private String name;
	private String ismail;
	private String postage;
	private List<ChildrenItem> goodList;

	private boolean isChecked;//是否选中
	private boolean isSelected;//是否编辑
	
	public GroupItem() {
	}
	
	public GroupItem(String merchantid,String name,String ismail,String postage,List<ChildrenItem> childrenItems) {
		this.merchantid = merchantid;
		this.name = name;
		this.ismail = ismail;
		this.postage = postage;
		this.goodList = childrenItems;
	}
	
	public List<ChildrenItem> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<ChildrenItem> goodList) {
		this.goodList = goodList;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsmail() {
		return ismail;
	}

	public void setIsmail(String ismail) {
		this.ismail = ismail;
	}

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
