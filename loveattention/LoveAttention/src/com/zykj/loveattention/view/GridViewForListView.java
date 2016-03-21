package com.zykj.loveattention.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

/**
 * @author  lc 
 * @date 创建时间：2016-1-12 上午10:06:52 
 * @version 1.0 
 * @Description
 */
public class GridViewForListView extends GridView {
    public GridViewForListView(Context context) {
        super(context);
    }

    public GridViewForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
