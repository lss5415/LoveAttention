package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.ListViewSwipeGesture;

/**
 * @author lss 2015年8月12日	我的消息
 *
 */
public class B4_4_WoDeXiaoXiActivity extends BaseActivity {
	private ImageView im_b44_back_btn;//返回
	private TextView tv_wodexiaoxi,tv_tuisongxiaoxi,tv_xitongxiaoxi;//我的，推送，系统
	private ImageView b3_hongdi_wode,b3_hongdi_tuisong,b3_hongdi_xitong;//红底我的，推送，系统
    private ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String, String>>();
    private ListView lv_gonggao;
    private BaseAdapter baseAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_4_wodexiaoxi);
		initView();
		for (int i=0;i<20;i++){
            HashMap<String,String> itemData=new HashMap<String, String>();
            itemData.put("tv_gonggao","茂业商场大促销仅剩三天了！"+i);
            data.add(itemData);
        }
        baseAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }
            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    LayoutInflater layoutInflater=getLayoutInflater();
                    convertView=layoutInflater.inflate(R.layout.manager_group_list_item_parent,parent,false);
                }
                Map<String,String> itemData=(Map<String,String>)getItem(position);
                TextView tv_gonggao=(TextView)convertView.findViewById(R.id.tv_gonggao);
                tv_gonggao.setText(itemData.get("tv_gonggao").toString());
                return convertView;
            }
        };
        lv_gonggao.setAdapter(baseAdapter);
        final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
                lv_gonggao, swipeListener, this);
        touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //设置两个选项列表项的背景
        lv_gonggao.setOnTouchListener(touchListener);
	}


	public void initView() {
		im_b44_back_btn = (ImageView) findViewById(R.id.im_b44_back_btn);
		tv_wodexiaoxi = (TextView)findViewById(R.id.tv_wodexiaoxi);
		tv_tuisongxiaoxi = (TextView)findViewById(R.id.tv_tuisongxiaoxi);
		tv_xitongxiaoxi = (TextView)findViewById(R.id.tv_xitongxiaoxi);
		b3_hongdi_wode = (ImageView) findViewById(R.id.b3_hongdi_wode);//我的消息--下方黄条
		b3_hongdi_tuisong = (ImageView) findViewById(R.id.b3_hongdi_tuisong);//推送消息 --下方黄条
		b3_hongdi_xitong = (ImageView) findViewById(R.id.b3_hongdi_xitong);//系统推送--下方黄条
        lv_gonggao=(ListView)findViewById(R.id.lv_gonggao);
        
		setListener(im_b44_back_btn,tv_wodexiaoxi,tv_tuisongxiaoxi,tv_xitongxiaoxi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b44_back_btn:
			this.finish();
			break;
        case R.id.tv_wodexiaoxi:
        	setVisible();
        	b3_hongdi_wode.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_wodexiaoxi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	
        	break;
        case R.id.tv_tuisongxiaoxi:
        	setVisible();
        	b3_hongdi_tuisong.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_tuisongxiaoxi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	
        	break;
        case R.id.tv_xitongxiaoxi:
        	setVisible();
        	b3_hongdi_xitong.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_xitongxiaoxi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	
        	break;
        default:
        	break;
        }
        }
	
	//设置所有黄条默认屏蔽
	public void setVisible(){
    	b3_hongdi_wode.setVisibility(View.INVISIBLE);
    	b3_hongdi_tuisong.setVisibility(View.INVISIBLE);
    	b3_hongdi_xitong.setVisibility(View.INVISIBLE);
	}
	
	//设置活动字体颜色为黑色
	public void setTextColor(){
		tv_wodexiaoxi.setTextColor(Color.BLACK);
		tv_tuisongxiaoxi.setTextColor(Color.BLACK);
		tv_xitongxiaoxi.setTextColor(Color.BLACK);
	}

	 ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

	        @Override
	        public void FullSwipeListView(int position) {
	            // TODO Auto-generated method stub
	            Toast.makeText(B4_4_WoDeXiaoXiActivity.this, "Action_2", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void HalfSwipeListView(int position) {
	            // TODO Auto-generated method stub
//	            System.out.println("<<<<<<<" + position);
	            data.remove(position);
	            baseAdapter.notifyDataSetChanged();
	            Toast.makeText(B4_4_WoDeXiaoXiActivity.this,"删除", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void LoadDataForScroll(int count) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
	            // TODO Auto-generated method stub
//	            Toast.makeText(activity,"Delete", Toast.LENGTH_SHORT).show();
//	            for(int i:reverseSortedPositions){
//	                data.remove(i);
//	                new MyAdapter().notifyDataSetChanged();
//	            }
	        }

	        @Override
	        public void OnClickListView(int position) {
	            // TODO Auto-generated method stub

	        }

	    };

}