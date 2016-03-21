package com.zykj.loveattention.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_2_JiFenAdapter;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.ui.B4_2_DuiHuanActivity;
import com.zykj.loveattention.view.CircularImage;
import com.zykj.loveattention.view.XListView;
import com.zykj.loveattention.view.XListView.IXListViewListener;

/**
 * @author lss 2015年8月11日 我的积分Fragment
 * 
 */
public class B4_2_WoDeJiFen_Fragment extends Fragment implements IXListViewListener {
	static Context context;
	private static View v;
	private XListView lv_jifen;
	private B4_2_JiFenAdapter jfadapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private TextView tv_duihuanjinbi,tv_jifentixian,tv_nickname;
	private JSONArray myIntegralDetaildata;
	private JSONObject myIntegraldata;
	private TextView tv_dangqianjifen,tv_lishizongjifen,tv_duihuanjifen,tv_tixianjifen;
	private CircularImage im_b42touxiang;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.b4_2_wodejifen_fragment, null);
		context = getActivity();
		lv_jifen = (XListView) v.findViewById(R.id.lv_jifen);
		tv_duihuanjinbi = (TextView) v.findViewById(R.id.tv_duihuanjinbi);
		tv_jifentixian = (TextView) v.findViewById(R.id.tv_jifentixian);
		tv_nickname = (TextView) v.findViewById(R.id.tv_nickname);
		tv_dangqianjifen = (TextView) v.findViewById(R.id.tv_dangqianjifen);
		tv_lishizongjifen = (TextView) v.findViewById(R.id.tv_lishizongjifen);
		tv_duihuanjifen = (TextView) v.findViewById(R.id.tv_duihuanjifen);
		tv_tixianjifen = (TextView) v.findViewById(R.id.tv_tixianjifen);
		im_b42touxiang = (CircularImage) v.findViewById(R.id.im_b42touxiang);
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+getArguments().getString("touxianga"), im_b42touxiang);
		tv_nickname.setText(getArguments().getString("name"));//获取传递过来的昵称
		myIntegralDetaildata = JSON.parseArray(getArguments().getString("myIntegralDetaildata"));
		myIntegraldata = JSON.parseObject(getArguments().getString("myIntegraldata"));
		if (myIntegraldata.size()<1) {
			tv_dangqianjifen.setText(myIntegraldata.getString("curvalue"));
			tv_lishizongjifen.setText("历史总积分 0");
			tv_duihuanjifen.setText("兑换积分 0");
			tv_tixianjifen.setText("已提现积分 0");
		}else {
			tv_dangqianjifen.setText(myIntegraldata.getString("curvalue"));
			if (myIntegraldata.getString("hisvalue")==null) {
				tv_lishizongjifen.setText("历史总积分0");
			}else {
				tv_lishizongjifen.setText("历史总积分"+myIntegraldata.getString("hisvalue"));
			}
			if (myIntegraldata.getString("exchgvalue")==null) {
				tv_duihuanjifen.setText("兑换积分0");
			}else {
				tv_duihuanjifen.setText("兑换积分"+myIntegraldata.getString("exchgvalue"));
			}
			if (myIntegraldata.getString("drawvalue")==null) {
				tv_tixianjifen.setText("已提现积分0");
			}else{
				tv_tixianjifen.setText("已提现积分"+myIntegraldata.getString("drawvalue"));
			}
		}
		
		tv_duihuanjinbi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent itduihuan = new Intent();
				itduihuan.setClass(getActivity(), B4_2_DuiHuanActivity.class);
				startActivity(itduihuan);
			}
		});
		tv_jifentixian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context, "暂时不支持提现！", Toast.LENGTH_LONG).show();
			}
		});

		for (int i = 0; i < myIntegralDetaildata.size(); i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			try {
				itemData.put("shijian", myIntegralDetaildata.getJSONObject(i).getString("opsdate"));
				itemData.put("bianliang", myIntegralDetaildata.getJSONObject(i).getString("opsnum"));
				itemData.put("miaoshu", myIntegralDetaildata.getJSONObject(i).getString("opsdesc"));
				if (myIntegralDetaildata.getJSONObject(i).getString("balance").equals("null")) {
					itemData.put("yue", "0.00");
				}else {
					itemData.put("yue", myIntegralDetaildata.getJSONObject(i).getString("balance"));
				}
				data.add(itemData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		jfadapter = new B4_2_JiFenAdapter(context, data);
		lv_jifen.setAdapter(jfadapter);
		lv_jifen.setPullLoadEnable(true);
		lv_jifen.setPullRefreshEnable(true);
		lv_jifen.setXListViewListener(this);
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy年MM月dd日   HH:mm:ss     ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String str = formatter.format(curDate);
//		lv_jifen.setRefreshTime(str + "刷新");
		return v;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

}
