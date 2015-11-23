package com.zykj.loveattention.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_2_JiFenAdapter;
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
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	private TextView tv_name;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v=inflater.inflate(R.layout.b4_2_wodejinbi_fragment, null);
		context = getActivity();
		lv_jifen = (XListView) v.findViewById(R.id.lv_jifen);
		tv_name = (TextView) v.findViewById(R.id.tv_name);
		tv_name.setText(getArguments().getString("name"));
		for (int i = 0; i < 20; i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("shijian", "3.15.10:20");
			itemData.put("bianliang", "100");
			itemData.put("miaoshu", "广告赚取");
			itemData.put("yue", "1000");
			data.add(itemData);
		}
		jfadapter = new B4_2_JiFenAdapter(context, data);
		lv_jifen.setAdapter(jfadapter);
		lv_jifen.setPullLoadEnable(true);
		lv_jifen.setPullRefreshEnable(true);
		lv_jifen.setXListViewListener(this);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH:mm:ss     ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		lv_jifen.setRefreshTime(str + "刷新");
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
