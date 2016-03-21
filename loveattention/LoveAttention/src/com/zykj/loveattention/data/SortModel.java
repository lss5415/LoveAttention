package com.zykj.loveattention.data;

public class SortModel {

	private String cityname;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	private String id;  //城市id
	private String citynum;
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCitynum() {
		return citynum;
	}
	public void setCitynum(String citynum) {
		this.citynum = citynum;
	}
	
}
