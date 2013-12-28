package com.luoo.musicplayer;


import android.os.Build;
import android.os.Bundle;
//import android.os.Trace;
import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        Fragment fragment = new DemoFragment();
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
