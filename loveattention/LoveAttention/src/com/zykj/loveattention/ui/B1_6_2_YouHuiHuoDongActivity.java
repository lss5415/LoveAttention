package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.view.XListView;

public class B1_6_2_YouHuiHuoDongActivity extends BaseActivity {
	private ImageView im_back;
	private XListView list_jinri;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_2_youhuihuodong);
		initView();
	}
	
	public void initView(){
		im_back = (ImageView)findViewById(R.id.im_back);
		list_jinri = (XListView)findViewById(R.id.list_jinri);
	/*	list_jinri.setDividerHeight(0);
		list_jinri.setPullLoadEnable(true);
		list_jinri.setXListViewListener(this);
		list_jinri.setOnItemClickListener(this);
		list_jinri.setAdapter(adapter);*/
		
		setListener(im_back);
	}
	
	@Override
	public void  onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back:
			this.finish();
			break;
//        case R.id.tv_remen:
//        	setVisible();
//        	b3_hongdi_remen.setVisibility(View.VISIBLE);
//        	setTextColor();
//        	tv_remen.setTextColor(getResources().getColor(R.color.all_huang_color));
//        	break;
        default:
        	break;
        }
   }
}