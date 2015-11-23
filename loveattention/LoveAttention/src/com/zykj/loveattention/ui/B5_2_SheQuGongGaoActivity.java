package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.ListViewSwipeGesture;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月10日 社区公告
 *
 */
public class B5_2_SheQuGongGaoActivity extends BaseActivity {
    private ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String, String>>();
    private ListView lv_gonggao;
    private BaseAdapter baseAdapter;
    private ImageView im_b5_2_back_btn;//返回
	private RequestQueue mRequestQueue;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b5_2_shequgonggao);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		GongGaoList();
        AdapterInfo();
    }
    
    /*
       初始化View
     */
    private void initView(){
        lv_gonggao=(ListView)findViewById(R.id.lv_gonggao);
        im_b5_2_back_btn = (ImageView)findViewById(R.id.im_b5_2_back_btn);
        
        setListener(im_b5_2_back_btn);
    }
    
    public void onClick(View v) {
    	super.onClick(v);
    	switch (v.getId()) {
		case R.id.im_b5_2_back_btn:
			this.finish();
			break;

		default:
			break;
		}
    };

    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

        @Override
        public void FullSwipeListView(int position) {
            // TODO Auto-generated method stub
            Toast.makeText(B5_2_SheQuGongGaoActivity.this, "Action_2", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void HalfSwipeListView(int position) {
            // TODO Auto-generated method stub
//            System.out.println("<<<<<<<" + position);
            data.remove(position);
            baseAdapter.notifyDataSetChanged();
            Toast.makeText(B5_2_SheQuGongGaoActivity.this,"删除", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void LoadDataForScroll(int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            // TODO Auto-generated method stub
//            Toast.makeText(activity,"Delete", Toast.LENGTH_SHORT).show();
//            for(int i:reverseSortedPositions){
//                data.remove(i);
//                new MyAdapter().notifyDataSetChanged();
//            }
        }

        @Override
        public void OnClickListView(int position) {
            // TODO Auto-generated method stub

        }


    };

    //公告列表数据
	public void GongGaoList() {
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_gonggaolist(), null,
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
								org.json.JSONArray array1 = response.getJSONArray("data");
								for (int i = 0; i < array1.length(); i++) {
									JSONObject job = array1.getJSONObject(i);
									HashMap<String,String> map = new HashMap<String, String>();
									map.put("content", job.getString("content"));
									map.put("objid", job.getString("objid"));
									map.put("objtype", job.getString("objtype"));
									map.put("aid", job.getString("aid"));
									data.add(map);
								}
								
								
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B5_2_SheQuGongGaoActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B5_2_SheQuGongGaoActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
	
	//公告列表的Adapter
	public void AdapterInfo(){
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
                HashMap<String, String> itemData = data.get(position);
//                Map<String,String> itemData= (Map<String,String>)getItem(position);
                TextView tv_gonggao=(TextView)convertView.findViewById(R.id.tv_gonggao);
                tv_gonggao.setText(itemData.get("content").toString());
                return convertView;
            }
        };
        lv_gonggao.setAdapter(baseAdapter);
        final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
                lv_gonggao, swipeListener, this);
        touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //设置两个选项列表项的背景
        lv_gonggao.setOnTouchListener(touchListener);
	}
}

