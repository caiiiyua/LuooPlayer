package com.luoo.musicplayer.ui;

import java.io.IOException;

import com.luoo.musicplayer.R;
import com.luoo.musicplayer.R.id;
import com.luoo.musicplayer.R.layout;
import com.luoo.musicplayer.R.string;
import com.luoo.musicplayer.service.MusicPlayerService;


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
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;

public class MusicPlayerFragment extends Fragment implements OnClickListener {

    private TextView mLable;
    private ImageButton mBtnPlay;
    private ImageButton mBtnPrevious;
    private ImageButton mBtnNext;
    private boolean mPlayed;
    private boolean mInitPlay;
    private static final String AUDIO_LOCAL = "file:///storage/sdcard0/Music/kugo/Britney Spears - Everytime.mp3";
    private static final String AUDIO_REMOTE = "http://luoo.800edu.net/low/luoo/radio492/03.mp3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.music_player, null);
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
        mBtnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        mBtnNext = (ImageButton) view.findViewById(R.id.btnNext);
        mBtnPrevious = (ImageButton) view.findViewById(R.id.btnPrevious);
        mBtnPlay.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.btnPlay:
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
                mBtnPlay.setImageResource(R.drawable.btn_pause);
            } else {
                Intent intent = MusicPlayerService.actionPauseMusicIntent(getActivity());
                getActivity().startService(intent);
                mPlayed = false;
                mBtnPlay.setImageResource(R.drawable.btn_play);
            }
            break;
        case R.id.btnNext:
            Intent next = MusicPlayerService.actionNextMusicIntent(getActivity());
            getActivity().startService(next);
            mInitPlay = true;
            mPlayed = true;
            mBtnPlay.setImageResource(R.drawable.btn_pause);
            break;
        case R.id.btnPrevious:
            Intent previous = MusicPlayerService.actionPreviousMusicIntent(getActivity());
            getActivity().startService(previous);
            mInitPlay = true;
            mPlayed = true;
            mBtnPlay.setImageResource(R.drawable.btn_pause);
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
