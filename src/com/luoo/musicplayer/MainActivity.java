package com.luoo.musicplayer;


import com.luoo.musicplayer.adapter.VolMetaInfo;
import com.luoo.musicplayer.ui.MusicPlayerFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.os.Trace;

public class MainActivity extends Activity {

    private static final String TAG = "TEST";
    public static final String EXTRA_TEXT = "message";
    private Button mButton;
    private EditText mEditable;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Trace.beginSection(TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int version = Build.VERSION.SDK_INT;
        Log.d(TAG, Build.MODEL);
        Fragment fragment = new MusicPlayerFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (transaction != null) {
            transaction.add(R.id.view_container, fragment);
            transaction.commit();
        }
//        Trace.endSection();
    }

    @Override
    protected void onResume() {
//        Trace.beginSection("RESUME");
        super.onResume();
//        Trace.endSection();
    }

    public Intent buildIntent() {
        Intent intent = new Intent(this, DemoActivity.class);
        return intent;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
