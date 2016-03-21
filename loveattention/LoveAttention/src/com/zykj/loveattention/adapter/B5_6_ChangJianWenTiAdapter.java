package com.zykj.loveattention.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.data.B5_6_ChangJianWenTi;

/**
 * @author  lc 
 * @date 创建时间：2016-1-16 上午10:53:34 
 * @version 1.0 
 * @Description 常见问题Adapter
 */
public class B5_6_ChangJianWenTiAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<B5_6_ChangJianWenTi> list;
	
	public B5_6_ChangJianWenTiAdapter(Context mContext,
			ArrayList<B5_6_ChangJianWenTi> list) {
		super();
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(list.size()>0){
			count = list.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView!=null){
			view = convertView;
		}else{
			view = LayoutInflater.from(mContext).inflate(R.layout.b5_6_changjianwenti_listview_item, parent,false);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder();
			
			holder.txt_answer = (TextView) view.findViewById(R.id.b5_6_changjianwenti_listview_item_txt_answer);
			holder.txt_question = (TextView) view.findViewById(R.id.b5_6_changjianwenti_listview_item_txt_question);
			holder.view = view.findViewById(R.id.b5_6_changjianwenti_listview_item_view);
			
			view.setTag(holder);
		}
		
		B5_6_ChangJianWenTi wenti = list.get(position);
		holder.txt_question.setText(position+1+"、"+wenti.getQuestion());
		holder.txt_answer.setText(wenti.getAnswer());
		if(position == list.size()-1){
			holder.view.setVisibility(View.GONE);
		}
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_question,txt_answer;
		View view;
	}

}
