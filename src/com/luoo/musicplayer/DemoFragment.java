package com.luoo.musicplayer;

import java.io.IOException;


import android.app.Fragment;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DemoFragment extends Fragment implements OnClickListener {

    private TextView mLable;
    private Button mBtnPlay;
    private Button mBtnStop;
    private boolean mPlayed;
    private boolean mInitPlay;
    private static final String AUDIO_LOCAL = "file:///storage/sdcard0/Music/kugo/Britney Spears - Everytime.mp3";
    private static final String AUDIO_REMOTE = "http://luoo.800edu.net/low/luoo/radio492/03.mp3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.demo_fragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        mLable = (TextView) getActivity().findViewById(R.id.text_lable3);
//        mLable.setText("Hello Fragment");
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        mBtnPlay = (Button) view.findViewById(R.id.button_play);
        mBtnStop = (Button) view.findViewById(R.id.button_stop);
        mBtnPlay.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.button_play:
            if (!mPlayed) {
                if (!mInitPlay) {
                    mInitPlay = true;
                    Intent intent = MusicPlayerService.actionPlayMusicIntent(getActivity(), AUDIO_REMOTE);
                    getActivity().startService(intent);
                } else {
                    Intent intent = MusicPlayerService.actionResumeMusicIntent(getActivity());
                    getActivity().startService(intent);
                }
                mPlayed = true;
                mBtnPlay.setText(R.string.pause);
            } else {
                Intent intent = MusicPlayerService.actionPauseMusicIntent(getActivity());
                getActivity().startService(intent);
                mPlayed = false;
                mBtnPlay.setText(R.string.play);
            }
            break;
        case R.id.button_stop:
            Intent intent = MusicPlayerService.actionStopMusicIntent(getActivity());
            getActivity().startService(intent);
            mInitPlay = false;
            mPlayed = false;
            break;
        default:
            break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        getActivity().stopService(MusicPlayerService.actionMusicPlayerIntent(getActivity()));
        super.onDestroyView();
    }
}
