package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B2_FuJin_Adapter;
import com.zykj.loveattention.adapter.B2_TextSizeAdapter;
import com.zykj.loveattention.adapter.FirstClassAdapter;
import com.zykj.loveattention.adapter.SecondClassAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.FirstClassItem;
import com.zykj.loveattention.data.SecondClassItem;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.ScreenUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月8日	附近－
 *
 */
public class B1_4_TuanGouActivity extends BaseActivity {
	private ImageView im_b14_back_btn;// 返回
	private Context mContext = B1_4_TuanGouActivity.this;
	public TextView tv_shanghuleixing,tv_quancheng,tv_zhinengpaixu,tv_shaixuan;//商户类型，全城，智能排序，筛选
	public ImageView b2_shanghuleixing,b2_quancheng,b2_zhinengpaixu,b2_shaixuan;//底部黄条
	public ImageView arrowdown1,arrowdown2,arrowdown3,arrowdown4;//向下的指示尖头
	public ListView list_fujin;//展示列表
	private B2_FuJin_Adapter fujinAdapter;//适配器
	private RequestQueue mRequestQueue;
	private String json;
	private List<Map<String, String>> fujindata = new ArrayList<Map<String, String>>();
	private String lng="",lat="";
	private List<HashMap<String, String>> zhinengpaixu;//智能排序
	private List<HashMap<String, String>> shaixuan;//筛选
	private List<HashMap<String, String>> shopClass;//商户类型
	private B2_TextSizeAdapter b2tsa;
    private ListView pList;
    private PopupWindow popupWindow,popupWindow1;
	private int pagesize = 5;//每页数量
	private int pagenumber = 1;//当前页
	private String districtid = "0";//地区ID
	private String categoryid = "0";//分类id
	private int orderType = 0;//智能排序  1.人气 2.口碑 3.离我最近 4.人均最高 5.人均最低 
	private int searchType = 0;//筛选  1.卡卷 2.免预约 3.节假日可用
	private String isRebate = "1";//返佣商家 1.全部 2.返佣商家 
    /**左侧和右侧两个ListView*/
    private ListView leftLV, rightLV;
    /**左侧一级分类的数据*/
    private List<FirstClassItem> firstList;
    /**右侧二级分类的数据*/
    private List<SecondClassItem> secondList;
    private TextView tv_title;//表头
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b1_4_tuangou);
		mRequestQueue = Volley.newRequestQueue(this);
		initUI();
        initPop();
        initData();
		setListener(im_b14_back_btn,tv_shanghuleixing,tv_quancheng,tv_zhinengpaixu,tv_shaixuan);//绑定点击事件
	}
	
	/**
	 * 绑定组件
	 */
	private void initUI() {
		im_b14_back_btn = (ImageView) findViewById(R.id.im_b14_back_btn);
		arrowdown1  = (ImageView) findViewById(R.id.arrowdown1);
		arrowdown2  = (ImageView) findViewById(R.id.arrowdown2);
		arrowdown3  = (ImageView) findViewById(R.id.arrowdown3);
		arrowdown4  = (ImageView) findViewById(R.id.arrowdown4);
		b2_shanghuleixing  = (ImageView) findViewById(R.id.b2_shanghuleixing);
		b2_quancheng  = (ImageView) findViewById(R.id.b2_quancheng);
		b2_zhinengpaixu  = (ImageView) findViewById(R.id.b2_zhinengpaixu);
		b2_shaixuan  = (ImageView) findViewById(R.id.b2_shaixuan);
		tv_shanghuleixing = (TextView) findViewById(R.id.tv_shanghuleixing);
		tv_quancheng = (TextView) findViewById(R.id.tv_quancheng);
		tv_zhinengpaixu = (TextView) findViewById(R.id.tv_zhinengpaixu);
		tv_shaixuan = (TextView) findViewById(R.id.tv_shaixuan);
		list_fujin = (ListView) findViewById(R.id.list_fujin);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(getIntent().getStringExtra("title"));
		try {
//			categoryid = getIntent().getStringExtra("categoryid");
		} catch (Exception e) {
			
		}
	}

    /**
     * 管理店铺(分类、排序)
     */
    private void initPop() {
//    	LeiXing();
        View view=LayoutInflater.from(B1_4_TuanGouActivity.this).inflate(R.layout.popu_layout,null);
        pList = (ListView) view.findViewById(R.id.lv_content);
        if(popupWindow == null){
            popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            ColorDrawable cd = new ColorDrawable(-0000);
            popupWindow.setBackgroundDrawable(cd);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
        }
		requestData();
    }

    /**
     * 管理店铺(分类、排序)
     */

    private void initPopup() {
        popupWindow1 = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        leftLV = (ListView) view.findViewById(R.id.pop_listview_left);
        rightLV = (ListView) view.findViewById(R.id.pop_listview_right);

        popupWindow1.setContentView(view);
        popupWindow1.setBackgroundDrawable(new PaintDrawable());
        popupWindow1.setFocusable(true);

        popupWindow1.setHeight(ScreenUtils.getScreenH(this) * 2 / 3);
        popupWindow1.setWidth(ScreenUtils.getScreenW(this));

        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                leftLV.setSelection(0);
                rightLV.setSelection(0);
            }
        });


        //为了方便扩展，这里的两个ListView均使用BaseAdapter.如果分类名称只显示一个字符串，建议改为ArrayAdapter.
        //加载一级分类
        final FirstClassAdapter firstAdapter = new FirstClassAdapter(this, firstList);
        leftLV.setAdapter(firstAdapter);

        //加载左侧第一行对应右侧二级分类
        secondList = new ArrayList<SecondClassItem>();
        secondList.addAll(firstList.get(0).getSecondList());
        final SecondClassAdapter secondAdapter = new SecondClassAdapter(this, secondList);
        rightLV.setAdapter(secondAdapter);

        //左侧ListView点击事件
        leftLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //二级数据
                List<SecondClassItem> list2 = firstList.get(position).getSecondList();
                //如果没有二级类，则直接跳转
                if (list2 == null || list2.size() == 0) {
                    popupWindow1.dismiss();

                    int firstId = firstList.get(position).getId();
                    String selectedName = firstList.get(position).getName();
                    handleResult(firstId, -1, selectedName);
                    return;
                }

                FirstClassAdapter adapter = (FirstClassAdapter) (parent.getAdapter());
                //如果上次点击的就是这一个item，则不进行任何操作
                if (adapter.getSelectedPosition() == position){
                    return;
                }

                //根据左侧一级分类选中情况，更新背景色
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();

                //显示右侧二级分类
                updateSecondListView(list2, secondAdapter);
            }
        });

        //右侧ListView点击事件
        rightLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupWindow，显示用户选择的分类
                popupWindow1.dismiss();

                int firstPosition = firstAdapter.getSelectedPosition();
                int firstId = firstList.get(firstPosition).getId();
                int secondId = firstList.get(firstPosition).getSecondList().get(position).getId();
                String selectedName = firstList.get(firstPosition).getSecondList().get(position)
                        .getName();
                handleResult(firstId, secondId, selectedName);
            }
        });
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b14_back_btn:
			this.finish();
			break;
		case R.id.tv_shanghuleixing://商户类型
			tv_shanghuleixing.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(android.graphics.Color.BLACK);
			arrowdown1.setImageResource(R.drawable.arrowdownyellow);
			arrowdown2.setImageResource(R.drawable.arrowdowngrey);
			arrowdown3.setImageResource(R.drawable.arrowdowngrey);
			arrowdown4.setImageResource(R.drawable.arrowdowngrey);
			b2_shanghuleixing.setVisibility(View.VISIBLE);
			b2_quancheng.setVisibility(View.INVISIBLE);
			b2_zhinengpaixu.setVisibility(View.INVISIBLE);
			b2_shaixuan.setVisibility(View.INVISIBLE);
			tab1OnClick();
			break;
		case R.id.tv_quancheng:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(android.graphics.Color.BLACK);
			arrowdown1.setImageResource(R.drawable.arrowdowngrey);
			arrowdown2.setImageResource(R.drawable.arrowdownyellow);
			arrowdown3.setImageResource(R.drawable.arrowdowngrey);
			arrowdown4.setImageResource(R.drawable.arrowdowngrey);
			b2_shanghuleixing.setVisibility(View.INVISIBLE);
			b2_quancheng.setVisibility(View.VISIBLE);
			b2_zhinengpaixu.setVisibility(View.INVISIBLE);
			b2_shaixuan.setVisibility(View.INVISIBLE);
			break;
		case R.id.tv_zhinengpaixu:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_shaixuan.setTextColor(android.graphics.Color.BLACK);
			arrowdown1.setImageResource(R.drawable.arrowdowngrey);
			arrowdown2.setImageResource(R.drawable.arrowdowngrey);
			arrowdown3.setImageResource(R.drawable.arrowdownyellow);
			arrowdown4.setImageResource(R.drawable.arrowdowngrey);
			b2_shanghuleixing.setVisibility(View.INVISIBLE);
			b2_quancheng.setVisibility(View.INVISIBLE);
			b2_zhinengpaixu.setVisibility(View.VISIBLE);
			b2_shaixuan.setVisibility(View.INVISIBLE);

            //智能排序
			zhinengpaixu = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < 6; i++){
				HashMap<String, String> map = new HashMap<String, String>();
				if (i==0) {
					map.put("sc_name", "全部");
				}else if (i==1) {
					map.put("sc_name", "人气最高");
				}else if (i==2) {
					map.put("sc_name", "口碑最好");
				}else if (i==3) {
					map.put("sc_name", "离我最近");
				}else if (i==4) {
					map.put("sc_name", "人均最高");
				}else if (i==5) {
					map.put("sc_name", "人均最低");
				}
				zhinengpaixu.add(map);
			}
			b2tsa = new B2_TextSizeAdapter(B1_4_TuanGouActivity.this, zhinengpaixu);
			pList.setAdapter(b2tsa);
            pList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					orderType = position;
	                if(popupWindow.isShowing()){
	                    popupWindow.dismiss();
	                }
	    			requestData();
				}
			});
            popupWindow.showAsDropDown(v);
			break;
		case R.id.tv_shaixuan:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(this.getResources().getColor(R.color.yellow));
			arrowdown1.setImageResource(R.drawable.arrowdowngrey);
			arrowdown2.setImageResource(R.drawable.arrowdowngrey);
			arrowdown3.setImageResource(R.drawable.arrowdowngrey);
			arrowdown4.setImageResource(R.drawable.arrowdownyellow);
			b2_shanghuleixing.setVisibility(View.INVISIBLE);
			b2_quancheng.setVisibility(View.INVISIBLE);
			b2_zhinengpaixu.setVisibility(View.INVISIBLE);
			b2_shaixuan.setVisibility(View.VISIBLE);

            //筛选
			shaixuan = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < 4; i++){
				HashMap<String, String> map = new HashMap<String, String>();
				if (i==0) {
					map.put("sc_name", "全部");
				}else if (i==1) {
					map.put("sc_name", "卡卷");
				}else if (i==2) {
					map.put("sc_name", "免预约");
				}else if (i==3) {
					map.put("sc_name", "节假日可用");
				}
				shaixuan.add(map);
			}
			b2tsa = new B2_TextSizeAdapter(B1_4_TuanGouActivity.this, shaixuan);
			pList.setAdapter(b2tsa);
            pList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					searchType = position;
	                if(popupWindow.isShowing()){
	                    popupWindow.dismiss();
	                }
	    			requestData();
				}
			});
            popupWindow.showAsDropDown(v);
			break;
		default:
			break;
		}
	}


	/**
	 * 请求服务器数据----店铺列表
	 */
	private void requestData(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("pagenumber", String.valueOf(pagenumber));
		map.put("pagesize", String.valueOf(pagesize));
		map.put("districtid", districtid);
		map.put("categoryid", categoryid);
		map.put("longitude", "2000");
		map.put("latitude", "3000");
		map.put("distance", "0");
		map.put("orderType", String.valueOf(orderType));
		map.put("searchType", String.valueOf(searchType));
		map.put("isRebate", isRebate);
		json = JsonUtils.toJson(map);
		
		// 发现中活动
		HuoDong();
		fujinAdapter = new B2_FuJin_Adapter(this,fujindata);
		list_fujin.setAdapter(fujinAdapter);
//		RequestDailog.showDialog(this, "正在加载数据，请稍后");
//		HttpUtils.getStoreList(curpage == 1?res_getStoresList:res_getMoreStoreList, HttpUtils.iterateParams(params));
	}
	
	//发现中活动
		public void HuoDong(){
			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_fujin(json), null,
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
									fujindata.clear();
									JSONObject jobs = response.getJSONObject("data");
									org.json.JSONArray array1 = jobs.getJSONArray("resultlist");
									for (int i = 0; i < array1.length(); i++) {
										JSONObject jsonItem1 = array1.getJSONObject(i);
										Map<String, String> map1 = new HashMap<String, String>();
										map1.put("distance", jsonItem1.getString("distance"));
										map1.put("imgpath", jsonItem1.getString("imgpath"));
										map1.put("perperson", jsonItem1.getString("perperson"));
										map1.put("remark", jsonItem1.getString("remark"));
										map1.put("stars", jsonItem1.getString("stars"));
										map1.put("name", jsonItem1.getString("name"));
										map1.put("merchantid", jsonItem1.getString("merchantid"));	
										fujindata.add(map1);
									}
									fujinAdapter = new B2_FuJin_Adapter(B1_4_TuanGouActivity.this,fujindata);
									list_fujin.setAdapter(fujinAdapter);
									
								} else {// 失败,提示失败信息
									String errdesc = status.getString("errdesc");
									Toast.makeText(B1_4_TuanGouActivity.this,errdesc, Toast.LENGTH_LONG).show();
								}
							} catch (org.json.JSONException e) {
								e.printStackTrace();
							}

						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							RequestDailog.closeDialog();
							Tools.Log("ErrorResponse=" + error.getMessage());
							Toast.makeText(B1_4_TuanGouActivity.this, "网络连接失败，请重试",
									Toast.LENGTH_LONG).show();
						}
					});
			mRequestQueue.add(jr);
		}
		
		//商户类型
			public void LeiXing(){
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_leixingfenlei(), null,
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
										fujindata.clear();
										JSONObject jobs = response.getJSONObject("data");
										org.json.JSONArray array1 = jobs.getJSONArray("resultlist");
										for (int i = 0; i < array1.length(); i++) {
											JSONObject jsonItem1 = array1.getJSONObject(i);
											Map<String, String> map1 = new HashMap<String, String>();
											map1.put("distance", jsonItem1.getString("distance"));
											map1.put("imgpath", jsonItem1.getString("imgpath"));
											map1.put("perperson", jsonItem1.getString("perperson"));
											map1.put("remark", jsonItem1.getString("remark"));
											map1.put("stars", jsonItem1.getString("stars"));
											map1.put("name", jsonItem1.getString("name"));
											map1.put("merchantid", jsonItem1.getString("merchantid"));	
											fujindata.add(map1);
										}
										fujinAdapter = new B2_FuJin_Adapter(B1_4_TuanGouActivity.this,fujindata);
										list_fujin.setAdapter(fujinAdapter);
										
									} else {// 失败,提示失败信息
										String errdesc = status.getString("errdesc");
										Toast.makeText(B1_4_TuanGouActivity.this,errdesc, Toast.LENGTH_LONG).show();
									}
								} catch (org.json.JSONException e) {
									e.printStackTrace();
								}

							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								RequestDailog.closeDialog();
								Tools.Log("ErrorResponse=" + error.getMessage());
								Toast.makeText(B1_4_TuanGouActivity.this, "网络连接失败，请重试",
										Toast.LENGTH_LONG).show();
							}
						});
				mRequestQueue.add(jr);
			}


			
			//顶部第一个标签的点击事件
		    private void tab1OnClick() {
		        if (popupWindow1.isShowing()) {
		            popupWindow1.dismiss();
		        } else {
		            popupWindow1.showAsDropDown(findViewById(R.id.main_div_line));
		            popupWindow1.setAnimationStyle(-1);
		        }
		    }
			
		    //刷新右侧ListView
		    private void updateSecondListView(List<SecondClassItem> list2,SecondClassAdapter secondAdapter) {
		        secondList.clear();
		        secondList.addAll(list2);
		        secondAdapter.notifyDataSetChanged();
		    }

		    //处理点击结果
		    private void handleResult(int firstId, int secondId, String selectedName){
		        String text = "first id:" + firstId + ",second id:" + secondId;
				districtid = firstId+"";
				categoryid = secondId+"";
		        requestData();
		    }
		    
		    private void initData() {
		    		RequestDailog.showDialog(mContext, "数据加载中，请稍后...");
		    		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
		    				HttpUtils.url_allcategorynew(), null,
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
		    								org.json.JSONArray arrayq = response.getJSONArray("data");
		    								firstList = new ArrayList<FirstClassItem>();
		    								firstList = JSONArray.parseArray(arrayq.toString(), FirstClassItem.class);
		    								initPopup();
		    								
		    							} else {// 失败,提示失败信息
		    								String errdesc = status.getString("errdesc");
		    								Toast.makeText(mContext,errdesc, Toast.LENGTH_LONG).show();
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
		    						Toast.makeText(mContext, "网络连接失败，请重试",
		    								Toast.LENGTH_LONG).show();
		    					}
		    				});
		    		mRequestQueue.add(jr);
//		        
		    }
			
			
			
}
