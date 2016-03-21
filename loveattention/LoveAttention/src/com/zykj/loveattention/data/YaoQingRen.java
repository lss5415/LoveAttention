package com.zykj.loveattention.data;

import android.os.Parcel;
import android.os.Parcelable;

public class YaoQingRen implements Parcelable {

	private String memberid;	//
	private String headportain;  //
	private String name;  //
	private String inviteNum;  //
	private String intime;  //
	private String mobile;  //
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getHeadportain() {
		return headportain;
	}
	public void setHeadportain(String headportain) {
		this.headportain = headportain;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInviteNum() {
		return inviteNum;
	}
	public void setInviteNum(String inviteNum) {
		this.inviteNum = inviteNum;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(memberid);
		dest.writeString(headportain);
		dest.writeString(name);
		dest.writeString(inviteNum);
		dest.writeString(intime);
		dest.writeString(mobile);
	}
	public static final Parcelable.Creator<YaoQingRen> CREATOR = new Parcelable.Creator<YaoQingRen>() {

		@Override
		public YaoQingRen createFromParcel(Parcel source) {
			YaoQingRen p = new YaoQingRen();
			p.memberid = source.readString();
			p.headportain = source.readString();
			p.name = source.readString();
			p.inviteNum = source.readString();
			p.intime = source.readString();
			p.mobile = source.readString();
			return p;
		}

		@Override
		public YaoQingRen[] newArray(int size) {
			return new YaoQingRen[size];
		}
	};


}
