package com.example.android.perfectclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 02-12-2018.
 */

public class AlarmListAdapter extends BaseAdapter {
    AlarmTimePickupPopup alarmTimePickupPopup;
    private Context context;
    private ArrayList<AlarmItem> alarmItems;

    public AlarmListAdapter(ArrayList<AlarmItem> mAlarmItems, Context mContext){
        context = mContext;
        alarmItems = mAlarmItems;
    }
    @Override
    public int getCount() {
        return alarmItems.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Log.d("Position",position+"");
        final AlarmItem alarmItem;
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.alarm_list_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            alarmItem = (AlarmItem) getItem(position);
            convertView.setTag(viewHolder);
        }else {
            alarmItem = (AlarmItem) getItem(position);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.hour.setText(alarmItem.getmHour());
        viewHolder.minute.setText(alarmItem.getmMinute());
        viewHolder.setting.setText(alarmItem.getmSetting());
        viewHolder.millis.setText(alarmItem.getmMillis()+"");
        Boolean state_boolean = Boolean.valueOf(state(position));
        viewHolder.state.setChecked(state_boolean);
        final String hour1;
        if(viewHolder.setting.getText().toString().equalsIgnoreCase("PM")&&!viewHolder.hour.getText().toString().equalsIgnoreCase("12")){
            hour1 = (12+Integer.parseInt(viewHolder.hour.getText().toString()))+"";
        }
        else {
            hour1 = viewHolder.hour.getText().toString();
        }
        // initialising the switch status
        if(viewHolder.state.isChecked()){
            Calendar calender = Calendar.getInstance();
            if((Long.parseLong(viewHolder.millis.getText().toString())>System.currentTimeMillis())){
                AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(parent.getContext(),AlarmReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)Long.parseLong(viewHolder.millis.getText().toString()),intent,0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,Long.parseLong(viewHolder.millis.getText().toString()),pendingIntent);
                }
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,Long.parseLong(viewHolder.millis.getText().toString()),pendingIntent);
            }
            else{
                Date date = new Date();
                calender.setTime(date);
                calender.add(Calendar.DATE,1);
                calender.set(Calendar.HOUR_OF_DAY,Integer.parseInt(viewHolder.hour.getText().toString()));
                calender.set(Calendar.MINUTE,Integer.parseInt(viewHolder.minute.getText().toString()));
                calender.set(Calendar.SECOND,0);
                calender.set(Calendar.MILLISECOND,0);
                Table table = new Table(context);
                SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(Field.Table.COLUMN_MILLI,calender.getTimeInMillis());
                sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                sqLiteDatabase.close();
                AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(parent.getContext(),AlarmReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)calender.getTimeInMillis(),intent,0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingIntent);
                }
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingIntent);
            }
        }
        else{
            AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)Long.parseLong(viewHolder.millis.getText().toString()),new Intent(parent.getContext(),AlarmReciever.class),0);
            alarmManager.cancel(pendingIntent);
        }
        // onClickListener for switch status
        viewHolder.state.setOnTouchListener(new View.OnTouchListener() {
            float initialX,initialY,finalX,finalY;
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getRawX();
                        initialY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        finalX = event.getRawX();
                        finalY = event.getRawY();
                        if (initialX < finalX) {
                            Calendar calender = Calendar.getInstance();
                            if(Long.parseLong(viewHolder.millis.getText().toString())>System.currentTimeMillis()){
                                Table table = new Table(context);
                                SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(Field.Table.COLUMN_STATE,"true");
                                contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                                sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                                sqLiteDatabase.close();
                                Toast.makeText(parent.getContext(),"Alarm Enabled",Toast.LENGTH_SHORT).show();
                                AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                                Intent intent = new Intent(parent.getContext(),AlarmReciever.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)Long.parseLong(viewHolder.millis.getText().toString()),intent,0);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,Long.parseLong(viewHolder.millis.getText().toString()),pendingIntent);
                                }
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,Long.parseLong(viewHolder.millis.getText().toString()),pendingIntent);
                            }
                            else{
                                Table table = new Table(context);
                                SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                                ContentValues contentValues = new ContentValues();
                                Date date = new Date();
                                calender.setTime(date);
                                calender.add(Calendar.DATE,1);
                                calender.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour1));
                                calender.set(Calendar.MINUTE,Integer.parseInt(viewHolder.minute.getText().toString()));
                                calender.set(Calendar.SECOND,0);
                                calender.set(Calendar.MILLISECOND,0);
                                Toast.makeText(parent.getContext(),"Alarm Enabled",Toast.LENGTH_SHORT).show();
                                contentValues.put(Field.Table.COLUMN_STATE,"true");
                                contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                                contentValues.put(Field.Table.COLUMN_MILLI,calender.getTimeInMillis());
                                sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                                sqLiteDatabase.close();
                                AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                                Intent intent = new Intent(parent.getContext(),AlarmReciever.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)calender.getTimeInMillis(),intent,0);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingIntent);
                                }
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingIntent);
                            }
                        }

                        if (initialX > finalX) {
                            Table table = new Table(context);
                            SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Field.Table.COLUMN_STATE,"false");
                            contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                            sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                            sqLiteDatabase.close();
                            Toast.makeText(parent.getContext(),"Alarm Disabled",Toast.LENGTH_SHORT).show();
                            AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)Long.parseLong(viewHolder.millis.getText().toString()),new Intent(parent.getContext(),AlarmReciever.class),0);
                            alarmManager.cancel(pendingIntent);
                        }
                        break;
                }
                return false;
            }
        });
        viewHolder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.state.isChecked()){
                    Calendar calender = Calendar.getInstance();
                    if(Long.parseLong(viewHolder.millis.getText().toString())>System.currentTimeMillis()){
                        Table table = new Table(context);
                        SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Field.Table.COLUMN_STATE,"true");
                        contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                        sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                        sqLiteDatabase.close();
                        Toast.makeText(parent.getContext(),"Alarm Enabled",Toast.LENGTH_SHORT).show();
                        AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(parent.getContext(),AlarmReciever.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)Long.parseLong(viewHolder.millis.getText().toString()),intent,0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,Long.parseLong(viewHolder.millis.getText().toString()),pendingIntent);
                        }
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,Long.parseLong(viewHolder.millis.getText().toString()),pendingIntent);
                    }
                    else{
                        Table table = new Table(context);
                        SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        Date date = new Date();
                        calender.setTime(date);
                        calender.add(Calendar.DATE,1);
                        calender.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour1));
                        calender.set(Calendar.MINUTE,Integer.parseInt(viewHolder.minute.getText().toString()));
                        calender.set(Calendar.SECOND,0);
                        calender.set(Calendar.MILLISECOND,0);
                        Toast.makeText(parent.getContext(),"Alarm Enabled",Toast.LENGTH_SHORT).show();
                        contentValues.put(Field.Table.COLUMN_STATE,"true");
                        contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                        contentValues.put(Field.Table.COLUMN_MILLI,calender.getTimeInMillis());
                        sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                        sqLiteDatabase.close();
                        AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(parent.getContext(),AlarmReciever.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)calender.getTimeInMillis(),intent,0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingIntent);
                        }
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingIntent);
                    }
                }
                else{
                    Table table = new Table(context);
                    SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                    contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                    sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+viewHolder.minute.getText().toString()+"'",null);
                    sqLiteDatabase.close();
                    Toast.makeText(parent.getContext(),"Alarm Disabled",Toast.LENGTH_SHORT).show();
                    AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(),(int)Long.parseLong(viewHolder.millis.getText().toString()),new Intent(parent.getContext(),AlarmReciever.class),0);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        notifyDataSetChanged();
        return convertView;
    }
    @Override
    public int getViewTypeCount() {
        if(getCount()>1)
            return getCount();
        else
            return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
    private class ViewHolder {
        TextView hour;
        TextView minute;
        TextView setting;
        TextView millis;
        Switch state;

        public ViewHolder(View view) {
            hour = (TextView)view.findViewById(R.id.hour_id);
            minute = (TextView) view.findViewById(R.id.minute_id);
            setting = (TextView) view.findViewById(R.id.setting_id);
            state = (Switch) view.findViewById(R.id.switch_state);
            millis = (TextView) view.findViewById(R.id.millis);
        }
    }
    // Helper methods to get the item details from the database
    public String id(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_HOUR_MINUTE));
        }
        sqLiteDatabase.close();
        return id;
    }
    public String HH(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_24HOUR};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_24HOUR));
        }
        sqLiteDatabase.close();
        return id;
    }
    public String hour(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_HOUR));
        }
        sqLiteDatabase.close();
        return id;
    }
    public String minute(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_MINUTE));
        }
        sqLiteDatabase.close();
        return id;
    }
    public String state(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_STATE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_STATE));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String setting(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_SETTING};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_SETTING));
        }
        return id;
    }

    public long millis(int i){
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_MILLI};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        long id = 0;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getLong(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_MILLI));
        }
        sqLiteDatabase.close();
        return id;
    }
}
