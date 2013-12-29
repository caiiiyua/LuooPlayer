package com.luoo.musicplayer.service;

import java.io.IOException;

import com.luoo.musicplayer.adapter.VolMetaInfo;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MusicPlayerService extends Service implements OnPreparedListener, OnErrorListener,
        OnAudioFocusChangeListener, OnCompletionListener {

    private static final String TAG = "MusicPlayerSerivce";
    private static final String ACTION_PLAY = "com.music.action.PLAY";
    private static final String ACTION_STOP = "com.music.action.STOP";
    private static final String ACTION_PAUSE = "com.music.action.PAUSE";
    private static final String ACTION_SEEKTO = "com.music.action.SEEKTO";
    private static final String ACTION_RESUME = "com.music.action.RESUME";
    private static final String ACTION_NEXT = "com.music.action.NEXT";
    private static final String ACTION_PREVIOUS = "com.music.action.PREVIOUS";

    private MediaPlayer mPlayer = null;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private VolMetaInfo mVolInfo;
    private int mTrackId;

    public MusicPlayerService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
        Log.d(TAG, "Service onStartCommand with intent: " + intent + " and startId: " + startId);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service onCreate");
        // initialize musicplayer here
        init();

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();

        mServiceLooper = handlerThread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    private void init() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnErrorListener(this);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Log.d(TAG, "Init MusicPlayer: " + mPlayer);
        }
    }

    private void play(String uri) {
        if (mPlayer == null) {
            init();
        }

        try {
            Log.d(TAG, "Play with MusicPlayer: " + mPlayer + " uri: " + uri);
            mPlayer.setDataSource(uri);
            mPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public VolMetaInfo testInfo() {
        final String URL_TEST = "http://www.luoo.net/";
        VolMetaInfo volMetaInfo = new VolMetaInfo(URL_TEST);
        volMetaInfo.initializeMetaInfo();
        Log.d(TAG, "Luoo: " + volMetaInfo);
        return volMetaInfo;
    }

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: " + msg);
            if (msg == null) return;
            Intent intent = (Intent) msg.obj;
            if (intent == null) return;

            if (mVolInfo == null) {
                mVolInfo = testInfo();
                mTrackId = 1;
            }
            String action = intent.getAction();
            String uri = intent.getDataString();
            Log.d(TAG, "Handle action: " + action + " with URI: " + uri);
            if (ACTION_PLAY.equals(action)) {
                uri = mVolInfo.getTrackUri(mTrackId).toString();
                play(uri);
            } else if (ACTION_PAUSE.equals(action)) {
                pause();
            } else if (ACTION_STOP.equals(action)) {
                stop();
            } else if (ACTION_SEEKTO.equals(action)) {
                
            } else if (ACTION_RESUME.endsWith(action)) {
                resume();
            } else if (ACTION_NEXT.endsWith(action)) {
//                stop();
                playNext();
            } else if (ACTION_PREVIOUS.endsWith(action)) {
//                stop();
                playPrevious();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.d(TAG, "Start with MusicPlayer: " + mediaPlayer);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mp != null) {
            if (mp.isPlaying()) {
                stop();
            }
            mp.reset();
        }
        return true;
    }

    public static Intent actionPlayMusicIntent(Context context, String uri) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_PLAY);
        intent.setData(Uri.parse(uri));
        return intent;
    }

    public static Intent actionNextMusicIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_NEXT);
        return intent;
    }

    public static Intent actionPreviousMusicIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_PREVIOUS);
        return intent;
    }

    public static Intent actionResumeMusicIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_RESUME);
        return intent;
    }

    public static Intent actionPauseMusicIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_PAUSE);
        return intent;
    }

    public static Intent actionStopMusicIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_STOP);
        return intent;
    }

    public static Intent actionMusicPlayerIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        return intent;
    }

    private void resume() {
        // TODO Auto-generated method stub
        if (mPlayer == null) {
            init();
        }
        mPlayer.start();
        Log.d(TAG, "Resume with MusicPlayer: " + mPlayer);
    }

    private void stop() {
        if (mPlayer == null) {
            init();
        }
        pause();
        mPlayer.stop();
        Log.d(TAG, "Stop with MusicPlayer: " + mPlayer);
    }

    private void pause() {
        if (mPlayer == null) {
            init();
        }
        mPlayer.pause();
        Log.d(TAG, "Pause with MusicPlayer: " + mPlayer);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "MusicPlayerService onDestroy with " + mPlayer);
        deinit();
        super.onDestroy();
    }

    private void deinit() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                stop();
            }
            Log.d(TAG, "Deinit with MusicPlayer: " + mPlayer);
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
        case AudioManager.AUDIOFOCUS_GAIN:
            // resume playback
            init();
            if (!mPlayer.isPlaying()) {
                resume();
                mPlayer.setVolume(1.0f, 1.0f);
            }
            break;

        case AudioManager.AUDIOFOCUS_LOSS:
            // Lost focus for an unbounded amount of time: stop playback and
            // release media player
            if (mPlayer.isPlaying()) {
                stop();
            }
            break;

        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            // Lost focus for a short time, but we have to stop
            // playback. We don't release the media player because playback
            // is likely to resume
            if (mPlayer.isPlaying()) {
                pause();
            }
            break;

        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
            // Lost focus for a short time, but it's ok to keep playing
            // at an attenuated level
            if (mPlayer.isPlaying()) {
                mPlayer.setVolume(1.0f, 1.0f);
            }
            break;
        }
    }

    public void playNext() {
        mPlayer.reset();
        long trackId = mTrackId + 1;
        String uri = mVolInfo.getTrackUri(trackId).toString();
        play(uri);
        mTrackId = (int) trackId;
    }

    public void playPrevious() {
        mPlayer.reset();
        long trackId = mTrackId - 1;
        if (trackId < 1) {
            trackId = 1;
        }
        String uri = mVolInfo.getTrackUri(trackId).toString();
        play(uri);
        mTrackId = (int) trackId;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext();
    }
}
