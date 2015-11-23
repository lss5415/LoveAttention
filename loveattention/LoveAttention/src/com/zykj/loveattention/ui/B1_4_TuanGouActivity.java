package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B2_FuJin_Adapter;
import com.zykj.loveattention.adapter.B2_TextSizeAdapter;
import com.zykj.loveattention.adapter.B2_and_B3_Adapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.JsonUtils;

/**
 * @author LSS 2015年8月24日 下午4:38:32 团购
 * 
 */
public class B1_4_TuanGouActivity extends BaseActivity {
	private ImageView im_b14_back_btn;// 返回
	public TextView tv_shanghuleixing, tv_quancheng, tv_zhinengpaixu,
			tv_shaixuan;// 商户类型，全城，智能排序，筛选
	public LinearLayout ll_shanghuleixing, ll_quancheng, ll_zhinengpaixu,
			ll_shaixuan;// 商户类型，全城，智能排序，筛选
	public ImageView b2_shanghuleixing, b2_quancheng, b2_zhinengpaixu,
			b2_shaixuan;// 底部黄条
	public ImageView arrowdown1, arrowdown2, arrowdown3, arrowdown4;// 向下的指示尖头
	public ListView list_fujin;// 展示列表
	private B2_and_B3_Adapter fujinAdapter;// 适配器
	private TextView tv_title;//表头
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private List<HashMap<String, String>> zhinengpaixu;//智能排序
	private List<HashMap<String, String>> shaixuan;//筛选
	private B2_TextSizeAdapter b2tsa;
    private ListView pList;
    private PopupWindow popupWindow;
	private int pagesize = 5;//每页数量
	private int pagenumber = 1;//当前页
	private String districtid = "0";//地区ID
	private String categoryid = "0";//分类id
	private int orderType = 0;//智能排序  1.人气 2.口碑 3.离我最近 4.人均最高 5.人均最低 
	private int searchType = 0;//筛选  1.卡卷 2.免预约 3.节假日可用
	private String isRebate = "1";//返佣商家 1.全部 2.返佣商家 
	private String json;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_4_tuangou);
		initView();
        initPop();
//		fujinAdapter = new B2_and_B3_Adapter(this,data);
//		list_fujin.setAdapter(fujinAdapter);
	}

	public void initView() {
		im_b14_back_btn = (ImageView) findViewById(R.id.im_b14_back_btn);
		arrowdown1 = (ImageView) findViewById(R.id.arrowdown1);
		arrowdown2 = (ImageView) findViewById(R.id.arrowdown2);
		arrowdown3 = (ImageView) findViewById(R.id.arrowdown3);
		arrowdown4 = (ImageView) findViewById(R.id.arrowdown4);
		b2_shanghuleixing = (ImageView) findViewById(R.id.b2_shanghuleixing);
		b2_quancheng = (ImageView) findViewById(R.id.b2_quancheng);
		b2_zhinengpaixu = (ImageView) findViewById(R.id.b2_zhinengpaixu);
		b2_shaixuan = (ImageView) findViewById(R.id.b2_shaixuan);
		tv_shanghuleixing = (TextView) findViewById(R.id.tv_shanghuleixing);
		tv_quancheng = (TextView) findViewById(R.id.tv_quancheng);
		tv_zhinengpaixu = (TextView) findViewById(R.id.tv_zhinengpaixu);
		tv_shaixuan = (TextView) findViewById(R.id.tv_shaixuan);
		ll_shanghuleixing = (LinearLayout) findViewById(R.id.ll_shanghuleixing);
		ll_quancheng = (LinearLayout) findViewById(R.id.ll_quancheng);
		ll_zhinengpaixu = (LinearLayout) findViewById(R.id.ll_zhinengpaixu);
		ll_shaixuan = (LinearLayout) findViewById(R.id.ll_shaixuan);
		list_fujin = (ListView) findViewById(R.id.list_fujin);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(getIntent().getStringExtra("title"));
		list_fujin.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent tuangou = new Intent(B1_4_TuanGouActivity.this,B1_7_ShangJiaXiangQingActivity.class);
				startActivity(tuangou);
			}
		});

		setListener(im_b14_back_btn, ll_shanghuleixing, ll_quancheng,
				ll_zhinengpaixu, ll_shaixuan);// 绑定点击事件
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

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b14_back_btn:
			this.finish();
			break;
		case R.id.ll_shanghuleixing:// 商户类型
			tv_shanghuleixing.setTextColor(this.getResources().getColor(
					R.color.yellow));
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

			break;
		case R.id.ll_quancheng:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(this.getResources().getColor(
					R.color.yellow));
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
		case R.id.ll_zhinengpaixu:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(this.getResources().getColor(
					R.color.yellow));
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
		case R.id.ll_shaixuan:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(this.getResources().getColor(
					R.color.yellow));
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
		map.put("distance", "1");
		map.put("orderType", String.valueOf(orderType));
		map.put("searchType", String.valueOf(searchType));
		map.put("isRebate", isRebate);
		json = JsonUtils.toJson(map);

		
		// 发现中活动
//		HuoDong();
//		fujinAdapter = new B2_FuJin_Adapter(this,fujindata);
//		list_fujin.setAdapter(fujinAdapter);
//		RequestDailog.showDialog(this, "正在加载数据，请稍后");
//		HttpUtils.getStoreList(curpage == 1?res_getStoresList:res_getMoreStoreList, HttpUtils.iterateParams(params));
	}
}