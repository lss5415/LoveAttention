package com.zykj.loveattention.utils;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by ss on 15-4-14.
 */
public abstract class AbstractHttpHandler extends AsyncHttpResponseHandler{

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {
            String responseString=new String(responseBody, HTTP.UTF_8);
            JSONObject json = (JSONObject) JSON.parse(responseString);
            onJsonSuccess(json);
        } catch (Exception e) {
        	Log.e("JSON--parse", e.toString());
            onJsonSuccess(JSONObject.parseObject("{\"code\":400,\"message\":\"请求失败\",\"datas\":null}"));
        } 
    }
    
	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
		// TODO Auto-generated method stub
		String a = "";
	}

	public abstract void onJsonSuccess(JSONObject json);

}
