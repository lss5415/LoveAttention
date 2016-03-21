package com.zykj.loveattention.ui;

import java.lang.reflect.Field;

import com.zykj.loveattention.R;
import com.zykj.loveattention.R.layout;
import com.zykj.loveattention.base.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ZoomButtonsController;

public class B5_9_WoShiShangJiaActivity extends BaseActivity {

	private WebView webview;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b5_9__wo_shi_shang_jia);
		
		back = (ImageView) findViewById(R.id.im_b45_back_btn);
		webview = (WebView) findViewById(R.id.b5_6_woshishangjia_webview);
		
		webview.loadUrl("http://www.baidu.com");
		
		webview.getSettings().setSupportZoom(true); 
		// 设置出现缩放工具 
		webview.getSettings().setBuiltInZoomControls(true);
		//自适应屏幕
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);		
		webview.getSettings().setUseWideViewPort(true); 
		webview.getSettings().setLoadWithOverviewMode(true);
		
		 //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webview.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });
		
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);  
		WebSettings webSettings =webview.getSettings();  
		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true);  
		setZoomControlGone(webview);  
		
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				B5_9_WoShiShangJiaActivity.this.finish();
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
