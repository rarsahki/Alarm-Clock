package com.example.android.perfectclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        BlueToothName.setText(bluetoothItem.getmName());
        return convertView;
    }
}
