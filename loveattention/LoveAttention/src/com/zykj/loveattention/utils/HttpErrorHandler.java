package com.zykj.loveattention.utils;

import org.apache.http.Header;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zykj.loveattention.view.MyRequestDailog;

/**
 * Created by ss on 15-4-14.
 */
public abstract class HttpErrorHandler extends AbstractHttpHandler {
    private static final String TAG="httpResponse";
    @Override
    public void onJsonSuccess(JSONObject json) {
       JSONObject status = json.getJSONObject("status");
       String succeed= status.getString("succeed");
        if(TextUtils.isEmpty(succeed) || !succeed.equals("1")){
            onRecevieFailed(succeed,json);
    		MyRequestDailog.closeDialog();
        }else{
         	onRecevieSuccess(json);
        }
    }

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        MyRequestDailog.closeDialog();
	}

    public abstract void onRecevieSuccess(JSONObject json);

    public void onRecevieFailed(String status,JSONObject json){};
}
