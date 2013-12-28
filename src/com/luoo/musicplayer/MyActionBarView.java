package com.luoo.musicplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MyActionBarView extends LinearLayout implements OnClickListener {

    private Activity mActivity;
    private ActionBar mActionBar;

    public MyActionBarView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyActionBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        mActivity.finish();
    }

    public void initilize(Activity activity, ActionBar actionBar) {
        mActivity = activity;
        mActionBar = actionBar;
        setOnClickListener(this);
    }
}
