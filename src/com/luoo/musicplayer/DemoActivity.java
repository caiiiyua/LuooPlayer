package com.luoo.musicplayer;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DemoActivity extends Activity {

    private MyActionBarView mActionBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String textString = bundle.getString(MainActivity.EXTRA_TEXT);
        TextView view = (TextView)findViewById(R.id.text_lable2);
        view.setText(textString);
        
        final ActionBar actionBar = getActionBar();
        setupActionBarView(actionBar);
        if (actionBar != null && mActionBarView != null) {
            actionBar.setCustomView(mActionBarView, new ActionBar.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            // Show a custom view and home icon, keep the title and subttitle
            final int mask = ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_USE_LOGO;
            setBackButton(actionBar, mask);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setupActionBarView(ActionBar actionBar) {
        final LayoutInflater inflater = LayoutInflater.from(actionBar.getThemedContext());
        mActionBarView = (MyActionBarView) inflater.inflate(R.layout.actionbar_view, null);
        mActionBarView.initilize(this, actionBar);
    }

    public void setBackButton(ActionBar actionBar, int mask) {
        // Show home as up, and show an icon.
        mask = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | mask;
        actionBar.setDisplayOptions(mask, mask);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }
 
}
