package com.luoo.musicplayer.adapter;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Element;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class VolMetaInfo {

    private long mId;
    private long mVolId;
    private String mTopic;
    private String mDescription;
    private ArrayList<MusicMetaInfo> mMusicList;
    private Uri mCoverUri;
    private String mUrlString;
    private String mMusicUriBase;
    private MusicHtmlParser mMusicParser;

    public VolMetaInfo() {
        // TODO Auto-generated constructor stub
    }

    public VolMetaInfo(String url) {
        mUrlString = url;
        mMusicParser = new MusicHtmlParser(mUrlString);
    }

    public VolMetaInfo(long volId, String topic, String description, String url) {
        mVolId = volId;
        mTopic = topic;
        mDescription = description;
        mUrlString = url;
        mMusicParser = new MusicHtmlParser(mUrlString);
    }

    public boolean initializeMetaInfo() {
        if (TextUtils.isEmpty(mUrlString)) {
            return false;
        }
        if (mMusicParser == null) {
            mMusicParser = new MusicHtmlParser(mUrlString);
        }
        
        try {
            mTopic = mMusicParser.getVolTopic();
            mDescription = mMusicParser.getVolDescription();
            mVolId = mMusicParser.getVolId();
            mUrlString = LuooConstantUtils.buildVolUrl(mVolId);
            mMusicUriBase = LuooConstantUtils.buildMusicUriBase(mVolId);
            mCoverUri = Uri.parse(mMusicParser.getVolCover());
            mMusicList = mMusicParser.getMusicList(mVolId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return true;
    }

    public Uri getTrackUri(long trackId) {
        Log.d(LuooConstantUtils.TAG, "getTrackUri with id:" + trackId);
        if (mMusicList == null) return null;
        for (MusicMetaInfo metaInfo : mMusicList) {
            Log.d(LuooConstantUtils.TAG, "getTrackUri with metainfo:" + metaInfo);
            if (metaInfo.getTrackId() == trackId) {
                return metaInfo.getTrackUri();
            }
        }
        return null;
    }

    public String toString() {
        return "[ " + mVolId + ", "
                + mTopic + ", " + mDescription + ", " + mUrlString + ", "
                + mCoverUri + ", " + mMusicUriBase + " ]";
    }
}
