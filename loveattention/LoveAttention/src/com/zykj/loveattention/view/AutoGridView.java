package com.zykj.loveattention.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author lss
 *
 */
public class AutoGridView extends GridView{  
    public AutoGridView(Context context, AttributeSet attrs) {   
        super(context, attrs);   
    }   
   
    public AutoGridView(Context context) {   
        super(context);   
    }   
   
    public AutoGridView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }   
   
    @Override   
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
   
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,   
                MeasureSpec.AT_MOST);   
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }   
}  