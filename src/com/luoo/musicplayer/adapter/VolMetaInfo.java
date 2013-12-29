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
            mUrlString = buildVolUrl(mVolId);
            mMusicUriBase = buildMusicUriBase(mVolId);
            mCoverUri = Uri.parse(mMusicParser.getVolCover());
            mMusicList = mMusicParser.getMusicList();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return true;
    }

    private String buildMusicUriBase(long volId) {
        String urlString = LuooConstantUtils.LUOO_URL_MUSIC_BASE;
        return String.format(urlString, volId);
    }

    private String buildVolUrl(long volId) {
        String urlString = LuooConstantUtils.LUOO_URL_BASE;
        
        return String.format("%s%d", urlString, volId);
    }

    public String toString() {
        return "[ " + mVolId + ", "
                + mTopic + ", " + mDescription + ", " + mUrlString + ", "
                + mCoverUri + ", " + mMusicUriBase + " ]";
    }
}
