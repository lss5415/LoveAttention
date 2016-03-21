package com.zykj.loveattention.data;

import java.io.Serializable;

/**
 * @author lss
 *{
    "status": {
        "succeed": 1,
        "errcode": "0000",
        "errdesc": "用户详情信息"
    },
    "data": {
        "birthday": "2013-11-23",
        "mail": "j j k k",
        "sex": "男",
        "coin": "0",
        "receiveaddress": "(null)",
        "blog": null,
        "integral": "0",
        "accountinfo": null,
        "sign": "我的个性签名",
        "weixin": "1234567890",
        "invitecode": "000004",
        "address": "山东省临沂市",
        "name": "你好吧台的话，",
        "headportain": "20160310/20160310112544204.jpg",
        "intime": "2015-08-15 11:03:45",
        "qq": "123456789",
        "mobile": "15165519504"
    }
}
 */
public class UserInfo implements Serializable{
	private String birthday; //
	private String mail; //
	private String sex; //
	private String coin; //
	private String receiveaddress; //
	private String blog; //
	private String integral;//
	private String accountinfo;//
	private String sign; //
	private String weixin; //
	private String invitecode; //
	private String address; //
	private String name;//
	private String headportain; //
	private String intime; //
	private String qq; //
	private String mobile;//
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}
	public String getReceiveaddress() {
		return receiveaddress;
	}
	public void setReceiveaddress(String receiveaddress) {
		this.receiveaddress = receiveaddress;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getAccountinfo() {
		return accountinfo;
	}
	public void setAccountinfo(String accountinfo) {
		this.accountinfo = accountinfo;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadportain() {
		return headportain;
	}
	public void setHeadportain(String headportain) {
		this.headportain = headportain;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
