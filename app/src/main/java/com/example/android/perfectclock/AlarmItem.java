package com.example.android.perfectclock;

/**
 * Created by User on 02-12-2018.
 */

public class AlarmItem {
    private String mHour;
    private String mMinute;
    private String mHHmm;
    private String mHH;
    private String mSetting;
    private String mState;
    private long mMillis;

    public AlarmItem(String Hour,String Minute,String HH, String HHmm, String Setting, String State, long Millis){
        mHH = HH;
        mHour = Hour;
        mMinute = Minute;
        mHHmm = HHmm;
        mSetting = Setting;
        mState = State;
        mMillis = Millis;
    }

    public String getmHH() { return mHH; }
    public String getmHour(){
        return mHour;
    }
    public String getmMinute(){
        return mMinute;
    }
    public String getmHHmm(){ return mHHmm; }
    public String getmSetting(){
        return mSetting;
    }
    public String getmState(){
        return mState;
    }
    public long getmMillis() { return mMillis; }
}
