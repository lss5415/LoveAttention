package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.qr_codescan.MipcaActivityCapture;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_ShouYeAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.ImageOptions;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.utils.isTopURL;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月8日 首页
 * 
 */
public class B1_ShouYeActivity extends BaseActivity {

	private ImageView iv_erwei;// 扫描二维码
	private ViewPager viewPager1;// 横向滚动的16个分类
	private ArrayList<View> pageview;
	private AutoListView lv_shouyelist;
	private B1_ShouYeAdapter syadapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	
	private ImageView dotA,dotB;
	
	private LinearLayout ll_meishi;//美食
	private LinearLayout ll_gouwu;//购物
	private LinearLayout ll_jiudian;//酒店
	private LinearLayout ll_liren;//丽人
	
	private LinearLayout ll_qinzi;//亲子
	private LinearLayout ll_xiuxianyule;//休闲娱乐
	private LinearLayout ll_shouyirenshangmen;//手艺人上门
	private LinearLayout ll_waimaiwaisong;//外卖外送
	
	
	private LinearLayout ll_shenghuofuwu;//生活服务
	private LinearLayout ll_xiedianping;//写点评
	private LinearLayout ll_wodedingdan;//我的订单
	private LinearLayout ll_wodekaquan;//我的卡券
	
	private LinearLayout ll_wodeqianbao;//我的钱包
	private LinearLayout ll_wodeyaoqing;//我的邀请
	private LinearLayout ll_woshishanghu;//我是商户
	private LinearLayout ll_quanbufenlei;//全部分类
	
	private TextView tv_myDNA;//我的dna
	
	private RelativeLayout net_lay1;//广告定制
	private RelativeLayout mobileshop_lay2;//团购
	private RelativeLayout net_lay2;//今日新单
	private RelativeLayout mobileshop_lay3;//金币商城
	
	private RelativeLayout rl_sousuokuang;//搜索框
	private EditText et_sousuo;//搜索框
	
	private final static int SCANNIN_GREQUEST_CODE = 101;

	private RelativeLayout rl_ditu;// 地图
	private TextView tv_cityname;// 城市名称
	private String cityname;
	private RequestQueue mRequestQueue;
	//广告定制
	private TextView net_title1;
	private TextView net_subtitle1;
	private ImageView net_img1;
	//团购
	private TextView mobileshop_title2;
	private TextView mobileshop_subtitle2;
	private ImageView mobileshop_img2;
	//今日新单
	private TextView net_title2;
	private TextView net_subtitle2;
	private ImageView net_img2;
	//金币商城
	private TextView mobileshop_title3;
	private TextView mobileshop_subtitle3;
	private ImageView mobileshop_img3;
	private TextView tv_cainixihuan,tv_tuijianshanghu;
	List<Map<String, String>> cnxhdata = new ArrayList<Map<String, String>>();
//	private Adapter 

	public String ErweimaUrl = null;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_shouye);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		tv_cityname = (TextView) findViewById(R.id.tv_cityname);
		if (getIntent().getStringExtra("cityname") == null) {
			putSharedPreferenceValue("lng", "118.338501");
			putSharedPreferenceValue("lat", "35.063786");
			putSharedPreferenceValue("cityid", "235");
			// HttpUtils.getFirstList(res_getSyList, "235", "118.338501",
			// "35.063786");
		} else {
			cityname = getIntent().getStringExtra("cityname");
			tv_cityname.setText(cityname);
			String cityid = getIntent().getStringExtra("cityid");
			String lng = getIntent().getStringExtra("lng");
			String lat = getIntent().getStringExtra("lat");
			putSharedPreferenceValue("lng", lng);
			putSharedPreferenceValue("lat", lat);
			putSharedPreferenceValue("cityid", cityid);
			// HttpUtils.getFirstList(res_getSyList, cityid, lng, lat);
		}
		setAdapter();
		// 首页-广告 自定义广告和轮播广告
		GuangGao();
	}

	public void initView() {
		rl_sousuokuang = (RelativeLayout) findViewById(R.id.rl_sousuokuang);
		et_sousuo = (EditText) findViewById(R.id.et_sousuo);
		iv_erwei = (ImageView) findViewById(R.id.iv_erwei);
		
		dotA = (ImageView) findViewById(R.id.dotA);
		dotB = (ImageView) findViewById(R.id.dotB);

		viewPager1 = (ViewPager) findViewById(R.id.viewPager);
		lv_shouyelist = (AutoListView) findViewById(R.id.lv_shouyelist);

		tv_myDNA = (TextView) findViewById(R.id.tv_myDNA);

		net_lay1 = (RelativeLayout) findViewById(R.id.net_lay1);
		mobileshop_lay2 = (RelativeLayout) findViewById(R.id.mobileshop_lay2);
		net_lay2 = (RelativeLayout) findViewById(R.id.net_lay2);
		mobileshop_lay3 = (RelativeLayout) findViewById(R.id.mobileshop_lay3);
		// 查找布局文件用LayoutInflater.inflate
		LayoutInflater inflater = getLayoutInflater();
		View view1 = inflater.inflate(R.layout.ui_b1_firstpage, null);
		View view2 = inflater.inflate(R.layout.ui_b1_secondpage, null);

		ll_meishi = (LinearLayout) view1.findViewById(R.id.ll_meishi);
		ll_gouwu = (LinearLayout) view1.findViewById(R.id.ll_gouwu);
		ll_jiudian = (LinearLayout) view1.findViewById(R.id.ll_jiudian);
		ll_liren = (LinearLayout) view1.findViewById(R.id.ll_liren);

		ll_qinzi = (LinearLayout) view1.findViewById(R.id.ll_qinzi);
		ll_xiuxianyule = (LinearLayout) view1.findViewById(R.id.ll_xiuxianyule);
		ll_shouyirenshangmen = (LinearLayout) view1
				.findViewById(R.id.ll_shouyirenshangmen);
		ll_waimaiwaisong = (LinearLayout) view1
				.findViewById(R.id.ll_waimaiwaisong);

		ll_shenghuofuwu = (LinearLayout) view2
				.findViewById(R.id.ll_shenghuofuwu);
		ll_xiedianping = (LinearLayout) view2.findViewById(R.id.ll_xiedianping);
		ll_wodedingdan = (LinearLayout) view2.findViewById(R.id.ll_wodedingdan);
		ll_wodekaquan = (LinearLayout) view2.findViewById(R.id.ll_wodekaquan);

		ll_wodeqianbao = (LinearLayout) view2.findViewById(R.id.ll_wodeqianbao);
		ll_wodeyaoqing = (LinearLayout) view2.findViewById(R.id.ll_wodeyaoqing);
		ll_woshishanghu = (LinearLayout) view2
				.findViewById(R.id.ll_woshishanghu);
		ll_quanbufenlei = (LinearLayout) view2
				.findViewById(R.id.ll_quanbufenlei);

		rl_ditu = (RelativeLayout) findViewById(R.id.rl_ditu);
		
		net_title1 = (TextView) findViewById(R.id.net_title1);
		net_subtitle1 = (TextView) findViewById(R.id.net_subtitle1);
		net_img1 = (ImageView) findViewById(R.id.net_img1);

		mobileshop_title2 = (TextView) findViewById(R.id.mobileshop_title2);
		mobileshop_subtitle2 = (TextView) findViewById(R.id.mobileshop_subtitle2);
		mobileshop_img2 = (ImageView) findViewById(R.id.mobileshop_img2);

		net_title2 = (TextView) findViewById(R.id.net_title2);
		net_subtitle2 = (TextView) findViewById(R.id.net_subtitle2);
		net_img2 = (ImageView) findViewById(R.id.net_img2);

		mobileshop_title3 = (TextView) findViewById(R.id.mobileshop_title3);
		mobileshop_subtitle3 = (TextView) findViewById(R.id.mobileshop_subtitle3);
		mobileshop_img3 = (ImageView) findViewById(R.id.mobileshop_img3);
		
		tv_cainixihuan = (TextView) findViewById(R.id.tv_cainixihuan);
		tv_tuijianshanghu = (TextView) findViewById(R.id.tv_tuijianshanghu);

		// 将view装入数组
		pageview = new ArrayList<View>();
		pageview.add(view1);
		pageview.add(view2);

		// 数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			// 获取当前窗体界面数
			public int getCount() {
				// TODO Auto-generated method stub
				return pageview.size();
			}

			@Override
			// 断是否由对象生成界面
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			// 是从ViewGroup中移出当前View
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(pageview.get(arg1));
			}

			// 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
			public Object instantiateItem(View arg0, int arg1) {
				((ViewPager) arg0).addView(pageview.get(arg1));
				return pageview.get(arg1);
			}

		};

		// 绑定适配器
		viewPager1.setAdapter(mPagerAdapter);
		viewPager1
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:
							dotA.setImageResource(R.drawable.dot1);
							dotB.setImageResource(R.drawable.dot0);
							break;
						case 1:
							dotA.setImageResource(R.drawable.dot0);
							dotB.setImageResource(R.drawable.dot1);
							break;

						default:
							break;
						}

					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});
		/*
		 * setListener(iv_erwei, et_sousuo, ll_meishi, ll_gouwu, ll_jiudian,
		 * ll_liren, ll_qinzi, ll_xiuxianyule, ll_shouyirenshangmen,
		 * ll_waimaiwaisong, ll_shenghuofuwu, ll_xiedianping, ll_wodedingdan,
		 * ll_wodekaquan, ll_wodeqianbao, ll_wodeyaoqing, ll_woshishanghu,
		 * ll_quanbufenlei, tv_myDNA, net_lay1, mobileshop_lay2, net_lay2,
		 * mobileshop_lay3, rl_ditu,tv_cainixihuan,tv_tuijianshanghu);
		 */
		viewPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					dotA.setImageResource(R.drawable.dot1);
					dotB.setImageResource(R.drawable.dot0);
					break;
				case 1:
					dotA.setImageResource(R.drawable.dot0);
					dotB.setImageResource(R.drawable.dot1);
					break;

				default:
					break;
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		setListener(iv_erwei, et_sousuo,rl_sousuokuang,
				 ll_meishi, ll_gouwu, ll_jiudian,ll_liren,
	 				ll_qinzi, ll_xiuxianyule, ll_shouyirenshangmen,
					ll_waimaiwaisong, ll_shenghuofuwu, ll_xiedianping,
					ll_wodedingdan, ll_wodekaquan, 
					ll_wodeqianbao, ll_wodeyaoqing,
					ll_woshishanghu, ll_quanbufenlei, tv_myDNA, net_lay1,
					mobileshop_lay2, net_lay2, mobileshop_lay3, rl_ditu,tv_cainixihuan,tv_tuijianshanghu);
	}

	public void setAdapter() {
		GuessInfo();
		lv_shouyelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent tuangou = new Intent(B1_ShouYeActivity.this,
						B1_7_ShangJiaXiangQingActivity.class);
				tuangou.putExtra("dpid", cnxhdata.get(arg2).get("merchantid"));
				startActivity(tuangou);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_erwei:
			Intent intent = new Intent();
			intent.setClass(B1_ShouYeActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.rl_sousuokuang:
		case R.id.et_sousuo:
			Intent intent_sousuo = new Intent();
			intent_sousuo.setClass(B1_ShouYeActivity.this, B1_sousuo.class);
			startActivity(intent_sousuo);
			break;
		case R.id.ll_meishi:
			// Toast.makeText(this, "美食", Toast.LENGTH_LONG).show();
			Intent meishi = new Intent(this, B1_4_TuanGouActivity.class);
			meishi.putExtra("title", "美食");
			startActivity(meishi);
			break;
		case R.id.ll_gouwu:
			// Toast.makeText(this, "购物", Toast.LENGTH_LONG).show();
			Intent gouwu = new Intent(this, B1_4_TuanGouActivity.class);
			gouwu.putExtra("title", "购物");
			startActivity(gouwu);
			break;
		case R.id.ll_jiudian:
			// Toast.makeText(this, "酒店", Toast.LENGTH_LONG).show();
			Intent jiudian = new Intent(this, B1_4_TuanGouActivity.class);
			jiudian.putExtra("title", "酒店");
			startActivity(jiudian);
			break;
		case R.id.ll_liren:
			// Toast.makeText(this, "丽人", Toast.LENGTH_LONG).show();
			Intent liren = new Intent(this, B1_4_TuanGouActivity.class);
			liren.putExtra("title", "丽人");
			startActivity(liren);
			break;
		case R.id.ll_qinzi:
			// Toast.makeText(this, "亲子", Toast.LENGTH_LONG).show();
			Intent qinzi = new Intent(this, B1_4_TuanGouActivity.class);
			qinzi.putExtra("title", "亲子");
			startActivity(qinzi);
			break;
		case R.id.ll_xiuxianyule:
			// Toast.makeText(this, "休闲娱乐", Toast.LENGTH_LONG).show();
			Intent xiuxianyule = new Intent(this, B1_4_TuanGouActivity.class);
			xiuxianyule.putExtra("title", "休闲娱乐");
			startActivity(xiuxianyule);
			break;
		case R.id.ll_shouyirenshangmen:
			// Toast.makeText(this, "手艺人上门", Toast.LENGTH_LONG).show();
			Intent shouyirenshangmen = new Intent(this,
					B1_4_TuanGouActivity.class);
			shouyirenshangmen.putExtra("title", "手艺人上门");
			startActivity(shouyirenshangmen);
			break;
		case R.id.ll_waimaiwaisong:
			// Toast.makeText(this, "外卖外送", Toast.LENGTH_LONG).show();
			Intent waimaiwaisong = new Intent(this, B1_4_TuanGouActivity.class);
			waimaiwaisong.putExtra("title", "外卖外送");
			startActivity(waimaiwaisong);
			break;

		case R.id.ll_shenghuofuwu:
			// Toast.makeText(this, "生活服务", Toast.LENGTH_LONG).show();
			Intent shenghuofuwu = new Intent(this, B1_4_TuanGouActivity.class);
			shenghuofuwu.putExtra("title", "生活服务");
			startActivity(shenghuofuwu);
			break;
		case R.id.ll_xiedianping:
			Toast.makeText(this, "写点评", Toast.LENGTH_LONG).show();
			break;
		case R.id.ll_wodedingdan:
			// Toast.makeText(this, "我的订单", Toast.LENGTH_LONG).show();
			if (isLoged()) {
				Intent itdingdan = new Intent();
				itdingdan.setClass(B1_ShouYeActivity.this,
						B4_7_WoDeDingdan.class);
				startActivity(itdingdan);
			} else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ll_wodekaquan:
			// Toast.makeText(this, "我的卡券", Toast.LENGTH_LONG).show();
			if (isLoged()) {
				Intent itkaquan = new Intent();
				itkaquan.setClass(B1_ShouYeActivity.this,
						B4_3_WoDeKaQuanActivity.class);
				startActivity(itkaquan);
			} else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.ll_wodeqianbao:
			// Toast.makeText(this, "钱包", Toast.LENGTH_LONG).show();
			if (isLoged()) {
				Intent itqianbao = new Intent();
				itqianbao.setClass(B1_ShouYeActivity.this,
						B4_2_WoDeQianBaoActivity.class);
				itqianbao.putExtra("id", getSharedPreferenceValue("id"));
				startActivity(itqianbao);
			} else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ll_wodeyaoqing:
			// Toast.makeText(this, "我的邀请", Toast.LENGTH_LONG).show();
			if (isLoged()) {
				Intent ityaoqing = new Intent();
				ityaoqing.setClass(B1_ShouYeActivity.this,
						B4_5_WoDeYaoQingActivity.class);
				startActivity(ityaoqing);
			} else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ll_woshishanghu:
			// Toast.makeText(this, "我的订单", Toast.LENGTH_LONG).show();

			break;
		case R.id.ll_quanbufenlei:
			Toast.makeText(this, "全部分类", Toast.LENGTH_LONG).show();
			Intent it4allclassification = new Intent(this,B1_1_allclassification.class);
			startActivity(it4allclassification);
			break;
		case R.id.tv_myDNA:
			// Toast.makeText(this, "我的dna", Toast.LENGTH_LONG).show();
			Intent intent2 = new Intent(this, B1_1_MyDNA.class);
			startActivity(intent2);
			break;
		case R.id.net_lay1:
			// Toast.makeText(this, "广告定制", Toast.LENGTH_LONG).show();
			Intent guanggao = new Intent(this,
					B1_3_GuangGaoDingZhiActivity.class);
			startActivity(guanggao);
			break;
		case R.id.mobileshop_lay2:
			// Toast.makeText(this, "团购", Toast.LENGTH_LONG).show();
			Intent tuangou = new Intent(this, B1_4_TuanGouActivity.class);
			tuangou.putExtra("title", "团购");
			startActivity(tuangou);
			break;
		case R.id.net_lay2:
			// Toast.makeText(this, "今日新单", Toast.LENGTH_LONG).show();
			Intent xindan = new Intent(this, B1_5_JinRiXinDanActivity.class);
			startActivity(xindan);
			break;
		case R.id.mobileshop_lay3:
			// Toast.makeText(this, "金币商城", Toast.LENGTH_LONG).show();
			Intent jinbi = new Intent(this, B1_6_JinBiShangChengActivity.class);
			startActivity(jinbi);
			break;
		// 城市选择
		case R.id.rl_ditu:
			Intent itmap = new Intent();
			itmap.setClass(this, B1_01_MapActivity.class);
			startActivity(itmap);
			break;
		case R.id.tv_cainixihuan:
			tv_cainixihuan.setTextColor(Color.rgb(255, 0, 0));
			tv_tuijianshanghu.setTextColor(Color.BLACK);
			GuessInfo();
			break;
		case R.id.tv_tuijianshanghu:
			tv_tuijianshanghu.setTextColor(Color.rgb(255, 0, 0));
			tv_cainixihuan.setTextColor(Color.BLACK);
			HotmerChantInfo();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String result = bundle.getString("result");
				if (isTopURL.isURL(result)) {
					ErweimaUrl = bundle.getString("result");
					Tools.Notic(B1_ShouYeActivity.this, ErweimaUrl,
							new OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									Uri content_url = Uri.parse(ErweimaUrl);
									intent.setData(content_url);
									startActivity(intent);
								}
							});
				} else {
					ErweimaUrl = bundle.getString("result");
					Tools.Notic(B1_ShouYeActivity.this, ErweimaUrl, null);
				}
				// Toast.makeText(this, "result="+bundle.getString("result"),
				// Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

	public boolean isLoged() {
		String isLoged = null;
		if (getSharedPreferenceValue("isLoged") != null) {
			isLoged = getSharedPreferenceValue("isLoged");
			if (isLoged.equals("1")) {
				return true;
			} else {
				return false;
			}
		} else {
			putSharedPreferenceValue("isLoged", "0");
			return false;
		}
	}

	public void GuangGao() {
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_shouyeguanggao(), null,
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
								org.json.JSONArray array1 = job.getJSONArray("advertList");
								net_title1.setText(array1.getJSONObject(0).getString("aname"));
								net_subtitle1.setText(array1.getJSONObject(0).getString("adesc"));
//								ImageLoader.getInstance().displayImage(array1.getJSONObject(0).getString("imgpath"), net_img1);
								ImageLoader.getInstance().displayImage(array1.getJSONObject(0).getString("imgpath"), net_img1, ImageOptions.getOpstion(), animateFirstListener);
								
								mobileshop_title2.setText(array1.getJSONObject(1).getString("aname"));
								mobileshop_subtitle2.setText(array1.getJSONObject(1).getString("adesc"));
								ImageLoader.getInstance().displayImage(array1.getJSONObject(1).getString("imgpath"), mobileshop_img2, ImageOptions.getOpstion(), animateFirstListener);
								
								net_title2.setText(array1.getJSONObject(2).getString("aname"));
								net_subtitle2.setText(array1.getJSONObject(2).getString("adesc"));
								ImageLoader.getInstance().displayImage(array1.getJSONObject(2).getString("imgpath"), net_img2, ImageOptions.getOpstion(), animateFirstListener);
								
								mobileshop_title3.setText(array1.getJSONObject(3).getString("aname"));
								mobileshop_subtitle3.setText(array1.getJSONObject(3).getString("adesc"));
								ImageLoader.getInstance().displayImage(array1.getJSONObject(3).getString("imgpath"), mobileshop_img3, ImageOptions.getOpstion(), animateFirstListener);
								
								
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_ShouYeActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_ShouYeActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

	public void HotmerChantInfo() {
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_hotmerchantinfo(), null,
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
								cnxhdata.clear();
								org.json.JSONArray array1 = response.getJSONArray("data");
								for (int i = 0; i < array1.length(); i++) {
									JSONObject jsonItem1 = array1.getJSONObject(i);
									Map<String, String> map1 = new HashMap<String, String>();
									map1.put("imgpath", jsonItem1.getString("imgpath"));
									map1.put("perperson", jsonItem1.getString("perperson"));
									map1.put("remark", jsonItem1.getString("remark"));
									map1.put("stars", jsonItem1.getString("stars"));
									map1.put("name", jsonItem1.getString("name"));
									map1.put("merchantid", jsonItem1.getString("merchantid"));					
									cnxhdata.add(map1);
								}
								syadapter = new B1_ShouYeAdapter(B1_ShouYeActivity.this, cnxhdata);
								lv_shouyelist.setAdapter(syadapter);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_ShouYeActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_ShouYeActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

	public void GuessInfo() {
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_guessinfo(), null,
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
								cnxhdata.clear();
								org.json.JSONArray array1 = response.getJSONArray("data");
								for (int i = 0; i < array1.length(); i++) {
									JSONObject jsonItem1 = array1.getJSONObject(i);
									Map<String, String> map1 = new HashMap<String, String>();
									map1.put("imgpath", jsonItem1.getString("imgpath"));
									map1.put("perperson", jsonItem1.getString("perperson"));
									map1.put("remark", jsonItem1.getString("remark"));
									map1.put("stars", jsonItem1.getString("stars"));
									map1.put("name", jsonItem1.getString("name"));
									map1.put("merchantid", jsonItem1.getString("merchantid"));					
									cnxhdata.add(map1);
								}
								syadapter = new B1_ShouYeAdapter(B1_ShouYeActivity.this, cnxhdata);
								lv_shouyelist.setAdapter(syadapter);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_ShouYeActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_ShouYeActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

}
