package com.zykj.loveattention.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_2_JiFenAdapter;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.view.CircularImage;
import com.zykj.loveattention.view.XListView;
import com.zykj.loveattention.view.XListView.IXListViewListener;

/**
 * @author lss 2015年8月11日	我的金币Fragement
 *
 */
public class B4_2_WoDeJinBi_Fragment extends Fragment implements
IXListViewListener {
	static Context context;
	private static View v;
	private XListView lv_jifen;
	private B4_2_JiFenAdapter jfadapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private TextView tv_name;
	private JSONArray myCoinDetaildata;
	private JSONObject myCoindata;
	private TextView tv_dangqianjifen1,tv_lishizongjifen1,tv_duihuanjifen1,tv_tixianjifen1;
	private CircularImage im_42touxiang;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v=inflater.inflate(R.layout.b4_2_wodejinbi_fragment, null);
		context = getActivity();
		lv_jifen = (XListView) v.findViewById(R.id.lv_jifen);
		tv_name = (TextView) v.findViewById(R.id.tv_name);
		tv_dangqianjifen1 = (TextView) v.findViewById(R.id.tv_dangqianjifen1);
		tv_lishizongjifen1 = (TextView) v.findViewById(R.id.tv_lishizongjifen1);
		tv_duihuanjifen1 = (TextView) v.findViewById(R.id.tv_duihuanjifen1);
		tv_tixianjifen1 = (TextView) v.findViewById(R.id.tv_tixianjifen1);
		tv_name.setText(getArguments().getString("name"));
		im_42touxiang = (CircularImage) v.findViewById(R.id.im_42touxiang);
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+getArguments().getString("touxianga"), im_42touxiang);
		myCoinDetaildata = JSON.parseArray(getArguments().getString("myCoinDetaildata"));
		myCoindata = JSON.parseObject(getArguments().getString("myCoindata"));
		if (myCoindata.size()<1) {
			tv_dangqianjifen1.setText(myCoindata.getString("curvalue"));
			tv_lishizongjifen1.setText("历史总积分 0");
			tv_duihuanjifen1.setText("兑换积分 0");
			tv_tixianjifen1.setText("已提现积分 0");
		}else {
			tv_dangqianjifen1.setText(myCoindata.getString("curvalue"));
			if (myCoindata.getString("hisvalue")==null) {
				tv_lishizongjifen1.setText("历史总积分0");
			}else {
				tv_lishizongjifen1.setText("历史总积分"+myCoindata.getString("hisvalue"));
			}
			if (myCoindata.getString("exchgvalue")==null) {
				tv_duihuanjifen1.setText("兑换积分0");
			}else {
				tv_duihuanjifen1.setText("兑换积分"+myCoindata.getString("exchgvalue"));
			}
			if (myCoindata.getString("drawvalue")==null) {
				tv_tixianjifen1.setText("已提现积分0");
			}else {
				tv_tixianjifen1.setText("已提现积分"+myCoindata.getString("drawvalue"));
			}
			
		}
		for (int i = 0; i < myCoinDetaildata.size(); i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("shijian", myCoinDetaildata.getJSONObject(i).getString("opsdate"));
			itemData.put("bianliang", myCoinDetaildata.getJSONObject(i).getString("opsnum"));
			itemData.put("miaoshu", myCoinDetaildata.getJSONObject(i).getString("opsdesc"));
			if (myCoinDetaildata.getJSONObject(i).getString("balance").equals("null")) {
				itemData.put("yue", "0.00");
			}else {
				itemData.put("yue", myCoinDetaildata.getJSONObject(i).getString("balance"));
			}
			data.add(itemData);
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
