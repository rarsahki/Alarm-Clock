package com.example.android.perfectclock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
        if(bluetoothItem.getmName().equalsIgnoreCase(""))
            BlueToothName.setText(bluetoothItem.getmMacAddress());
        else
            BlueToothName.setText(bluetoothItem.getmName());
        for(int i=0;i<count();i++){
            if(bluetoothMacAddress(i).equalsIgnoreCase(bluetoothItem.getmMacAddress())){
                BlueToothName.setTextColor(Color.BLUE);
            }
        }
        return convertView;
    }
    public String bluetoothMacAddress(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.BluetoothTable.COLUMN_MACADDRESS};
        Cursor cursor = sqLiteDatabase.query(Field.BluetoothTable.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.BluetoothTable.COLUMN_MACADDRESS));
        }
        return id;
    }
    public int count(){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.BluetoothTable.COLUMN_NAME};
        Cursor cursor = sqLiteDatabase.query(Field.BluetoothTable.TABLE_NAME,null,null,null,null,null,null);
        int id = cursor.getCount();
        sqLiteDatabase.close();
        return id;
    }
}
