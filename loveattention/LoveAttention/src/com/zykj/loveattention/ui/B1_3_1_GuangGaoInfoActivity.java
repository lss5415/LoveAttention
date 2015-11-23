package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B131GuangGaoList;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.PickDialog;
import com.zykj.loveattention.view.PickDialogListener;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author LSS 2015年9月30日 下午2:47:39
 *
 */
public class B1_3_1_GuangGaoInfoActivity extends BaseActivity {
	private ImageView im_b131_back_btn;//返回
	private String advertid,memberid;
	private RequestQueue mRequestQueue;
	private ImageView im_mainimgpath;//轮播图
	private ImageView im_shangpin1,im_shangpin2,im_shangpin3;//商品图片
	private AutoListView list_shangpin;//商品列表
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private B131GuangGaoList adapter;
	private TextView tv_jianjie,tv_fenxiang,tv_dati;//简介,分享，答题
	private PickDialog pickDialog;//答题弹出框
	private ArrayList<String> listdati=new ArrayList<String>();//答题选项
	private String timu;//题目
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_3_1_guanggaoinfo);
		Intent intent = getIntent();   
		//获取数据   
		advertid = intent.getStringExtra("advertid");  
		memberid = getSharedPreferenceValue("memberid");
		mRequestQueue = Volley.newRequestQueue(this);
		Advertdetail();
		initView();
	} 


	public void initView() {
		im_b131_back_btn = (ImageView) findViewById(R.id.im_b131_back_btn);
		im_mainimgpath = (ImageView) findViewById(R.id.im_mainimgpath);
		im_shangpin1 = (ImageView) findViewById(R.id.im_shangpin1);
		im_shangpin2 = (ImageView) findViewById(R.id.im_shangpin2);
		im_shangpin3 = (ImageView) findViewById(R.id.im_shangpin3);	
		list_shangpin = (AutoListView) findViewById(R.id.list_shangpin);
		tv_jianjie = (TextView) findViewById(R.id.tv_jianjie);
		tv_fenxiang = (TextView) findViewById(R.id.tv_fenxiang);
		tv_dati = (TextView) findViewById(R.id.tv_dati);
		
		setListener(im_b131_back_btn,tv_fenxiang,tv_dati);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b131_back_btn:
			this.finish();
			break;
		case R.id.tv_fenxiang:
			// 首先在您的Activity中添加如下成员变量
			final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
			// 设置分享内容
			mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
			// 设置分享图片, 参数2为图片的url地址
			mController.setShareMedia(new UMImage(B1_3_1_GuangGaoInfoActivity.this,"http://www.umeng.com/images/pic/banner_module_social.png"));
			mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
			mController.openShare(B1_3_1_GuangGaoInfoActivity.this, false);
			break;
		case R.id.tv_dati:
			pickDialog=new PickDialog(B1_3_1_GuangGaoInfoActivity.this, timu, new PickDialogListener() {

				@Override
				public void onRightBtnClick() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onListItemLongClick(int position, String string) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onListItemClick(int position, String string) {
					// TODO Auto-generated method stub
					Toast.makeText(B1_3_1_GuangGaoInfoActivity.this, listdati.get(position), Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onLeftBtnClick() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});
			pickDialog.show();
			
			//for test 延迟三秒加载
			new Handler().postDelayed(new Runnable(){   

			    public void run() {   

			    //execute the task 
			    	pickDialog.initListViewData(listdati);

			    }   

			 }, 3000);  
			break;
//		case R.id.tv_myyaoqingren:
//			Intent itdenglu = new Intent();
//			itdenglu.setClass(B4_5_WoDeYaoQingActivity.this, B4_5_1_MyYaoQingRenActivity.class);
//			startActivity(itdenglu);
//			break;
		default:
			break;
		}
	}

	public void Advertdetail() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", memberid);
		map.put("advertid", advertid);
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_advertdetail(json), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						JSONObject status;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								JSONObject job = response.getJSONObject("data");
								String otherimgpath = job.getString("otherimgpath");
								String[] a = otherimgpath.split(",");
								ImageLoader.getInstance().displayImage(job.getString("mainimgpath"), im_mainimgpath);
								int s = a.length;
								if (a.length>=3) {
									ImageLoader.getInstance().displayImage(a[0], im_shangpin1);
									ImageLoader.getInstance().displayImage(a[1], im_shangpin2);
									ImageLoader.getInstance().displayImage(a[2], im_shangpin3);
								}else if(a.length==2) {
									ImageLoader.getInstance().displayImage(a[0], im_shangpin1);
									ImageLoader.getInstance().displayImage(a[1], im_shangpin2);
								}else if(a.length==1) {
									ImageLoader.getInstance().displayImage(a[0], im_shangpin1);
								}else {
									
								}
								tv_jianjie.setText(job.getString("intruduct"));
								String questionoption = job.getString("questionoption");
								String[] b = questionoption.split("\\|");
								for(int i=0;i<b.length;i++){
									listdati.add(b[i]);
								}
								timu = job.getString("intruduct");
								org.json.JSONArray array1 = job.getJSONArray("list");
								for (int i = 0; i < array1.length(); i++) {
									JSONObject jsonItem1 = array1.getJSONObject(i);
									Map<String, String> map1 = new HashMap<String, String>();
									map1.put("remark", jsonItem1.getString("remark"));
									map1.put("menuid", jsonItem1.getString("menuid"));
									map1.put("shiptype", jsonItem1.getString("shiptype"));
									map1.put("merchantid", jsonItem1.getString("merchantid"));
									map1.put("postage", jsonItem1.getString("postage"));
									map1.put("id", jsonItem1.getString("id"));			
									map1.put("imgpath", jsonItem1.getString("imgpath"));		
									map1.put("price", jsonItem1.getString("price"));		
									map1.put("isfreepost", jsonItem1.getString("isfreepost"));		
									map1.put("stars", jsonItem1.getString("stars"));		
									map1.put("advertid", jsonItem1.getString("advertid"));		
									map1.put("name", jsonItem1.getString("name"));		
									map1.put("paynum", jsonItem1.getString("paynum"));		
									map1.put("intime", jsonItem1.getString("intime"));		
									map1.put("sharenum", jsonItem1.getString("sharenum"));		
									map1.put("visitnum", jsonItem1.getString("visitnum"));		
									map1.put("oldprice", jsonItem1.getString("oldprice"));				
									data.add(map1);
								}
								adapter = new B131GuangGaoList(B1_3_1_GuangGaoInfoActivity.this, data);
								list_shangpin.setAdapter(adapter);
								
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_3_1_GuangGaoInfoActivity.this,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(B1_3_1_GuangGaoInfoActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

}