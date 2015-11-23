package com.zykj.loveattention.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zykj.loveattention.R;

public class GridViewAdapter extends BaseAdapter {
	private LayoutInflater inflater; 
	private Context context; 
	private Map<String, String >map = new HashMap();
	private Iterator iterator;
	
	public GridViewAdapter(Context context,Map<String, String>map){ 
         this.context = context; 
         this.map = map; 
         inflater = LayoutInflater.from(context);
         Set keys = map.keySet( );  
         if(keys != null) {       
        	    
        	    iterator = keys.iterator( );       
        	    
        	}    
     } 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return map.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder; 
		if (convertView == null) 
        { 
            convertView = inflater.inflate(R.layout.gridview_item, null); 
            viewHolder = new ViewHolder(); 
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content); 
            convertView.setTag(viewHolder); 
        } else
        { 
            viewHolder = (ViewHolder) convertView.getTag(); 
        } 
		
 	    if(iterator.hasNext( )) {       
    	    
	        Object key = iterator.next( );       
	    
	        Object value = map.get(key);       
	    
	        viewHolder.tv_content.setText(value+"");
	    }   
		
		return convertView;
	}
	class ViewHolder 
	{ 
	    public TextView tv_content; 
	} 

}
