package com.zykj.loveattention.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PingLun implements Parcelable {
	private String commentid; //
	private String content; //
	private String intime; //
	private String stars; //
	private String name; //
	private String headportain; //
	private String objid;//

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
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

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(commentid);
		dest.writeString(content);
		dest.writeString(intime);
		dest.writeString(stars);
		dest.writeString(name);
		dest.writeString(headportain);
		dest.writeString(objid);
	}

	public static final Parcelable.Creator<PingLun> CREATOR = new Parcelable.Creator<PingLun>() {

		@Override
		public PingLun createFromParcel(Parcel source) {
			PingLun p = new PingLun();
			p.commentid = source.readString();
			p.content = source.readString();
			p.intime = source.readString();
			p.stars = source.readString();
			p.name = source.readString();
			p.headportain = source.readString();
			p.objid = source.readString();
			return p;
		}

		@Override
		public PingLun[] newArray(int size) {
			return new PingLun[size];
		}
	};

}
