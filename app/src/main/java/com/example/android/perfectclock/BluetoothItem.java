package com.example.android.perfectclock;

public class BluetoothItem {
    String mName;
    String mMacAddress;
    public BluetoothItem(String name, String macAddress){
        mName = name;
        mMacAddress = macAddress;
    }

    public String getmName() {
        return mName;
    }

    public String getmMacAddress() {
        return mMacAddress;
    }
}
