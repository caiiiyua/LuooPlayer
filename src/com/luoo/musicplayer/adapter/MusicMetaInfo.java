package com.luoo.musicplayer.adapter;

import android.net.Uri;

public class MusicMetaInfo {

    private long mId;
    private long mTrackId;
    private String mName;
    private String mAlbum;
    private String mArtist;
    private long mDuration;
    private int mSize;
    private long mVolId;
    private String mDescription;
    private Uri mCoverUri;
    private String mLrc;
    private Uri mTrackUri;

    private static final String TAG = "MusicMetaInfo";

    public MusicMetaInfo() {
        
    }

    public MusicMetaInfo(long volId) {
        mVolId = volId;
    }

    public long getTrackId() {
        return mTrackId;
    }

    public void setTrackId(long trackId) {
        mTrackId = trackId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        this.mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        this.mSize = size;
    }

    public long getVolId() {
        return mVolId;
    }

    public void setVolId(long volId) {
        this.mVolId = volId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Uri getCoverUri() {
        return mCoverUri;
    }

    public void setCoverUri(Uri coverUri) {
        this.mCoverUri = coverUri;
    }

    public Uri getTrackUri() {
        return mTrackUri;
    }

    public void setTrackUri(Uri trackUri) {
        this.mTrackUri = trackUri;
    }

    public String getLrc() {
        return mLrc;
    }

    public void setLrc(String lrc) {
        this.mLrc = lrc;
    }

    @Override
    public String toString() {
        return "[ " + mTrackId + ", " + mName + ", " + mAlbum + ", " + mCoverUri +
                ", " + mTrackUri + " ]";
    }
}
