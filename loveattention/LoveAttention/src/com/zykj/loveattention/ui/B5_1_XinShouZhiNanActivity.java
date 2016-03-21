package com.zykj.loveattention.ui;

import java.lang.reflect.Field;

import com.zykj.loveattention.R;
import com.zykj.loveattention.R.layout;
import com.zykj.loveattention.R.menu;
import com.zykj.loveattention.base.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ZoomButtonsController;

/**
 * 
 * @author lc
 * @date 创建时间：2016-1-16 上午9:30:29
 * @version 1.0 
 * @Description 新手指南
 */
public class B5_1_XinShouZhiNanActivity extends BaseActivity {
	
	private WebView webview;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b5_1__xin_shou_zhi_nan);
		
		back = (ImageView) findViewById(R.id.im_b45_back_btn);
		webview = (WebView) findViewById(R.id.b5_1_xinshouzhinan_webview);
		
		webview.loadUrl("http://115.28.67.86:8080/aigz/data/guide");
		
		webview.getSettings().setSupportZoom(true); 
		// 设置出现缩放工具 
		webview.getSettings().setBuiltInZoomControls(true);
		//自适应屏幕
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);		
		webview.getSettings().setUseWideViewPort(true); 
		webview.getSettings().setLoadWithOverviewMode(true);
		
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);  
		WebSettings webSettings =webview.getSettings();  
		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true);  
		setZoomControlGone(webview);  
		
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				B5_1_XinShouZhiNanActivity.this.finish();
			}
		});
	}

	//实现放大缩小控件隐藏  
	public void setZoomControlGone(View view) {  
	    Class classType;  
	    Field field;  
	    try {  
	        classType = WebView.class;  
	        field = classType.getDeclaredField("mZoomButtonsController");  
	        field.setAccessible(true);  
	        ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);  
	        mZoomButtonsController.getZoomControls().setVisibility(View.GONE);  
	        try {  
	            field.set(view, mZoomButtonsController);  
	        } catch (IllegalArgumentException e) {  
	            e.printStackTrace();  
	        } catch (IllegalAccessException e) {  
	            e.printStackTrace();  
	        }  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (NoSuchFieldException e) {  
	        e.printStackTrace();  
	    }  
	}  
	
}
