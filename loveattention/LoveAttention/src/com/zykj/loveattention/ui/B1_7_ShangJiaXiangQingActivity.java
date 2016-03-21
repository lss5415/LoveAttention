package com.zykj.loveattention.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_7_3_ChanPinAdapter;
import com.zykj.loveattention.adapter.B1_7_3_YouHuiAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.DianPuGoodsInfo;
import com.zykj.loveattention.data.MerchantyhInfo;
import com.zykj.loveattention.data.PingLun;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Share;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.RequestDailog;



public class B1_7_ShangJiaXiangQingActivity extends BaseActivity {
	private ImageView b17suggest_back,im_imgpath,im_headportain,suggest_share,im_shoucang;//返回,商家详情图片,评论图片,分享,收藏
	private RelativeLayout rl_youhuiquan;//我的优惠券
	private TextView tv_fancaidan,suggst_input,tv_tuwen;//翻菜单,输入金额,图文
	private RelativeLayout rl_pingjia;//评价
	private String merchantid;
	private JSONObject merchantdetail;
	private TextView tv_namea,tv_remark,tv_opentime,tv_telephone,tv_address,tv_perperso;//店铺名，备注，营业时间，商家电话，商家地址，人均消费
	private RequestQueue mRequestQueue; 
	private TextView tv_youhuihuodong,tv_chanpinxinxi,tv_17shangjiajieshao,tv_17bendiangonggao,tv_17jieshaoandgonggao;//优惠活动，产品信息,商家介绍，本店公告,介绍和公告的内容
	private AutoListView atlv_youhuihuodong;//优惠活动，产品信息list
	private List<MerchantyhInfo> meinfolist;
	private List<DianPuGoodsInfo> dpgoodsinfolist;
	private ArrayList<PingLun> pinglunlist;
	private String jieshao="",gonggao="";
	private TextView tv_name,tv_content,tv_intime;
	private RatingBar comment_rating_bar;
	private String ShareContent,ShareTitle,ShareUrl;
	private int quanorinfo=0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_shangjiaxiangqing);
		initView();
		initShare();//初始化分享模块
		mRequestQueue =  Volley.newRequestQueue(this);  
		requestData();
	}


	public void initView() {
		b17suggest_back = (ImageView) findViewById(R.id.b17suggest_back);
		rl_youhuiquan = (RelativeLayout) findViewById(R.id.rl_youhuiquan);
		tv_fancaidan = (TextView) findViewById(R.id.tv_fancaidan);
		suggst_input = (TextView) findViewById(R.id.suggst_input);
		rl_pingjia = (RelativeLayout) findViewById(R.id.rl_pingjia);
		tv_tuwen = (TextView) findViewById(R.id.tv_tuwen);
//		merchantid = getIntent().getStringExtra("dpid");
		im_imgpath = (ImageView) findViewById(R.id.im_imgpath);
		tv_namea = (TextView) findViewById(R.id.tv_namea);
		tv_remark = (TextView) findViewById(R.id.tv_remark);
		tv_opentime = (TextView) findViewById(R.id.tv_opentime);
		tv_telephone = (TextView) findViewById(R.id.tv_telephone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_perperso = (TextView) findViewById(R.id.tv_perperso);
		tv_youhuihuodong = (TextView) findViewById(R.id.tv_youhuihuodong);
		tv_chanpinxinxi = (TextView) findViewById(R.id.tv_chanpinxinxi);
		tv_17shangjiajieshao = (TextView) findViewById(R.id.tv_17shangjiajieshao);
		tv_17bendiangonggao = (TextView) findViewById(R.id.tv_17bendiangonggao);
		tv_17jieshaoandgonggao = (TextView) findViewById(R.id.tv_17jieshaoandgonggao);
		merchantid = getIntent().getStringExtra("merchantid");
		atlv_youhuihuodong = (AutoListView) findViewById(R.id.atlv_youhuihuodong);
		im_headportain = (ImageView) findViewById(R.id.im_headportain);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_intime = (TextView) findViewById(R.id.tv_intime);
		comment_rating_bar = (RatingBar) findViewById(R.id.comment_rating_bar);
		suggest_share = (ImageView) findViewById(R.id.suggest_share);
		im_shoucang = (ImageView) findViewById(R.id.im_shoucang);
		atlv_youhuihuodong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (quanorinfo>0) {//加入到购物车的信息
					String goodsid = dpgoodsinfolist.get(arg2).getGoodid();
					Intent yhq = new Intent(B1_7_ShangJiaXiangQingActivity.this, ShangPinInfoActivity.class);
					yhq.putExtra("goodsid", goodsid);
					yhq.putExtra("shangjia", tv_namea.getText().toString());
					startActivity(yhq);
				}else {//优惠券信息
					String couponid = meinfolist.get(arg2).getCouponid();
					KaQuanInfo(couponid);
				}
				
			}
		});
		setListener(b17suggest_back,rl_youhuiquan,tv_fancaidan,suggst_input,rl_pingjia,tv_tuwen,tv_youhuihuodong,tv_chanpinxinxi,tv_17shangjiajieshao,tv_17bendiangonggao,suggest_share,im_shoucang);
	}

	private void requestData() {
		/** 商家详情优惠活动 */
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		map.put("memberid", getSharedPreferenceValue("id"));
		String jsonmerchantid = JsonUtils.toJson(map);
//		HttpUtils.getMerChantDetailInfo(jsonmerchantid);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.getMerChantDetailInfo(jsonmerchantid),null,new Response.Listener<JSONObject>() {
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject jobsj = response.getJSONObject("data");
						String dpgoodsinfo = jobsj.getString("goodsinfoList");
						dpgoodsinfolist = JSONArray.parseArray(dpgoodsinfo,DianPuGoodsInfo.class);
						String merchantyhinfo = jobsj.getString("merchantyhinfoList");
						meinfolist = JSONArray.parseArray(merchantyhinfo,MerchantyhInfo.class);
						merchantdetail = jobsj.getJSONObject("merchantdetail");
						ImageLoader.getInstance().displayImage(AppValue.ImgUrl+merchantdetail.getString("imgpath"), im_imgpath);
						tv_namea.setText(merchantdetail.getString("name"));
						tv_remark.setText("商家标签 ："+merchantdetail.getString("remark"));
						tv_opentime.setText("营业时间："+merchantdetail.getString("opentime"));
						tv_telephone.setText("商家电话："+merchantdetail.getString("telephone"));
						tv_address.setText(merchantdetail.getString("address"));
						tv_perperso.setText("人均消费"+merchantdetail.getString("perperson")+"元");
						try {
							if (merchantdetail.getString("isattention").equals("0")) {
								//未关注
								im_shoucang.setImageResource(R.drawable.shoucangqian);
							}else {
								//已关注
								im_shoucang.setImageResource(R.drawable.shoucanghou);
							}
						} catch (Exception e) {
							
						}
						try {
							org.json.JSONArray jargg = jobsj.getJSONArray("announceList");
							jieshao = jargg.getJSONObject(0).getString("content");
							gonggao = jargg.getJSONObject(1).getString("content");
							tv_17jieshaoandgonggao.setText(jieshao);
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							String commentList = jobsj.getString("commentList");
							pinglunlist = (ArrayList<PingLun>) JSONArray.parseArray(commentList,PingLun.class);
							ImageLoader.getInstance().displayImage(AppValue.ImgUrl+pinglunlist.get(1).getHeadportain(), im_headportain);
							tv_name.setText(pinglunlist.get(1).getName());
							tv_content.setText(pinglunlist.get(1).getContent());
							tv_intime.setText(pinglunlist.get(1).getIntime());
							comment_rating_bar.setRating(Float.parseFloat(pinglunlist.get(1).getStars()));
						} catch (Exception e) {
							
						}
						B1_7_3_YouHuiAdapter goodadapter = new B1_7_3_YouHuiAdapter(
								B1_7_ShangJiaXiangQingActivity.this, meinfolist);
						atlv_youhuihuodong.setAdapter(goodadapter);
						
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.b17suggest_back:
			this.finish();
			break;
		case R.id.rl_youhuiquan:
//			Intent yhq = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_1_BenDianKaQuan.class);
//			startActivity(yhq);
			break;
		case R.id.tv_fancaidan:
			Intent fcd = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_2_FanCaiDanActivity.class);
			fcd.putExtra("merchantid", merchantid);
			startActivity(fcd);
			break;
		case R.id.suggst_input:
			/*CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	        builder.setMessage("商家确认订单后，可返利，24小时候商家未处理，可举报！");  
	        builder.setTitle("输入金额");  
	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                //设置你的操作事项  
	                Toast.makeText(B1_7_ShangJiaXiangQingActivity.this, "shuru", Toast.LENGTH_LONG).show();
	            }  
	        });  
	  
	        builder.setNegativeButton("取消",  
	                new android.content.DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int which) {  
	                        dialog.dismiss();  
	                    }  
	                });  
	  
	        builder.create().show(); */ 
			final EditText texta = new EditText(this);
			int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL;
			texta.setInputType(inputType);
			new AlertDialog.Builder(this).setTitle("请输入").setIcon(
					android.R.drawable.ic_dialog_info).setView(
							texta).setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Intent fcd = new Intent(B1_7_ShangJiaXiangQingActivity.this, XianJinZhiFuActivity.class);
							fcd.putExtra("zhifujine", texta.getText().toString());
							fcd.putExtra("merchantid", merchantid);
							startActivity(fcd);
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.rl_pingjia:
			Intent pingjia = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_3_XiangQingActivity.class);
			pingjia.putExtra("leixing", "用户评价");
//			pingjia.putParcelableArrayListExtra("pinglunlist", pinglunlist);
			pingjia.putExtra("isgoods", "0");
			pingjia.putExtra("merchantid", merchantid);
			startActivity(pingjia);
			break;
		case R.id.tv_tuwen:
			Intent tuwen = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_3_XiangQingActivity.class);
			tuwen.putExtra("leixing", "图文介绍");
			tuwen.putParcelableArrayListExtra("pinglunlist", pinglunlist);
			tuwen.putExtra("merchantid", merchantid);
			startActivity(tuwen);
			break;
		case R.id.tv_youhuihuodong:
			quanorinfo=0;
			tv_youhuihuodong.setTextColor(0xffFF8E0A);
			tv_chanpinxinxi.setTextColor(Color.BLACK);
			B1_7_3_YouHuiAdapter goodadapter = new B1_7_3_YouHuiAdapter(
					B1_7_ShangJiaXiangQingActivity.this, meinfolist);
			atlv_youhuihuodong.setAdapter(goodadapter);
			break;
		case R.id.tv_chanpinxinxi:
			quanorinfo=1;
			tv_youhuihuodong.setTextColor(Color.BLACK);
			tv_chanpinxinxi.setTextColor(0xffFF8E0A);
			B1_7_3_ChanPinAdapter goodadapter1 = new B1_7_3_ChanPinAdapter(
					B1_7_ShangJiaXiangQingActivity.this, dpgoodsinfolist);
			atlv_youhuihuodong.setAdapter(goodadapter1);
			break;
		case R.id.tv_17shangjiajieshao:
			tv_17shangjiajieshao.setTextColor(0xffFF8E0A);
			tv_17bendiangonggao.setTextColor(Color.BLACK);
			tv_17jieshaoandgonggao.setText(jieshao);
			
			break;
		case R.id.tv_17bendiangonggao:
			tv_17shangjiajieshao.setTextColor(Color.BLACK);
			tv_17bendiangonggao.setTextColor(0xffFF8E0A);
			tv_17jieshaoandgonggao.setText(gonggao);
			
			break;
		case R.id.suggest_share:
			Share.invit(this, ShareContent, ShareTitle, ShareUrl);
			break;
		case R.id.im_shoucang:
			if (isLoged()) {
				GuanZhu();
			} else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;

		}
	}
	
	private void initShare() {
		ShareContent = "我的爱关注邀请码是:"+getSharedPreferenceValue("invitecode")+"，一起来爱关注吧，http://www.pgyer.com/ov1S";
		ShareTitle = "我的邀请码："+getSharedPreferenceValue("invitecode");
		ShareUrl = "http://www.pgyer.com/ov1S";
	}
	
	private void GuanZhu(){
		/** 商家详情优惠活动 */
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("objid", merchantid);	
		String jsonmerchantid = JsonUtils.toJson(map);
//		HttpUtils.getMerChantDetailInfo(jsonmerchantid);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_attentionAdd(jsonmerchantid),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject jobsj = response.getJSONObject("status");
						if (jobsj.getString("errdesc").equals("关注成功")) {
							Toast.makeText(B1_7_ShangJiaXiangQingActivity.this, "关注成功", Toast.LENGTH_LONG	).show();
							im_shoucang.setImageResource(R.drawable.shoucanghou);
						}else {
							Toast.makeText(B1_7_ShangJiaXiangQingActivity.this, "已取消关注", Toast.LENGTH_LONG	).show();
							im_shoucang.setImageResource(R.drawable.shoucangqian);
						}
						
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}
	
	public void KaQuanInfo(String couponid){
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("couponid", couponid);
		map1.put("invitecode", "");
		map1.put("longitude", getSharedPreferenceValue("lng"));
		map1.put("latitude", getSharedPreferenceValue("lat"));
		String json = JsonUtils.toJson(map1);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_coupondetail(json), null,
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
								JSONObject jobs = response.getJSONObject("data");
								/*tv_name.setText(jobs.getString("name"));
								tv_juli.setText("距离"+jobs.getString("distance")+"米");
								tv_manjian.setText(jobs.getString("couponname"));
								tv_youxiaoqi.setText("有效期"+jobs.getString("effecttime"));
//								tv_yilingnum.setText(jobs.getString("couponcate"));
								tv_xianjia.setText("￥"+jobs.getString("couponprice"));
								tv_yuanjia.setText("￥"+jobs.getString("couponprice"));
								tv_jieshao.setText(jobs.getString("couponname"));
								tv_shuoming.setText(jobs.getString("couponintroduct"));
								if (jobs.getString("friendnum").equals("-1")) {
									tv_haoyouchiyounum.setText("好友持有0人");
								}else {
									tv_haoyouchiyounum.setText("好友持有"+jobs.getString("friendnum")+"人");
								}
								tv_dianpu.setText(jobs.getString("name"));
								tv_dizhi.setText("地址："+jobs.getString("address"));
								tv_dianhua.setText("电话："+jobs.getString("telephone"));
								rl_bg.setBackgroundColor(android.graphics.Color.parseColor(jobs.getString("couponcolor")));
								tv_lingqu.setBackgroundColor(android.graphics.Color.parseColor(jobs.getString("couponcolor")));*/
								Intent itkaquan1 = new Intent(B1_7_ShangJiaXiangQingActivity.this, B4_3_KaQuanInfoActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("couponname", jobs.getString("couponname"));
								bundle.putString("name", jobs.getString("name"));
								bundle.putString("effecttime", jobs.getString("effecttime"));
								bundle.putString("couponintroduct", jobs.getString("couponintroduct"));
								bundle.putString("state", jobs.getString("state"));
								bundle.putString("overdue", jobs.getString("0"));
								itkaquan1.putExtra("data",bundle);
								startActivity(itkaquan1);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_7_ShangJiaXiangQingActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_7_ShangJiaXiangQingActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
	
	//判断是否登录
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
}