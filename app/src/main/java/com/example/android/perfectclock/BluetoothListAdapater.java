package com.example.android.perfectclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BluetoothListAdapater extends ArrayAdapter<BluetoothItem> {
    ArrayList<BluetoothItem> bluetoothItems;
    Context context;
    public  BluetoothListAdapater(ArrayList<BluetoothItem> mBlueToothItems, Context mContext){
        super(mContext,-1,mBlueToothItems);
        context = mContext;
        bluetoothItems = mBlueToothItems;
    }
    @Override
    public int getCount() {
        return bluetoothItems.size();
    }

    @Override
    public BluetoothItem getItem(int position) {
        return bluetoothItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothItem bluetoothItem;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bluetoothitem, parent, false);
        }
        bluetoothItem = (BluetoothItem) getItem(position);
        TextView BlueToothName = convertView.findViewById(R.id.BlueToothName);
        if(getArrayList("registeredDevices")!=null){
            for(int i=0;i<getArrayList("registeredDevices").size();i++){
                if(bluetoothItem.getmName().equalsIgnoreCase(getArrayList("registeredDevices").get(i).getmName())&&
                        bluetoothItem.getmMacAddress().equalsIgnoreCase(getArrayList("registeredDevices").get(i).getmMacAddress())){
                    BlueToothName.setTextColor(Color.BLUE);
                }
            }
        }
        if(bluetoothItem.getmName().equalsIgnoreCase(""))
            BlueToothName.setText(bluetoothItem.getmMacAddress());
        else
            BlueToothName.setText(bluetoothItem.getmName());
        return convertView;
    }
    public void saveArrayList(ArrayList<BluetoothItem> list, String key){
        SharedPreferences prefs = context.getSharedPreferences("savedDevices",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
        editor.commit();
    }
    public ArrayList<BluetoothItem> getArrayList(String key){
        SharedPreferences prefs = context.getSharedPreferences("savedDevices",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<BluetoothItem>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
