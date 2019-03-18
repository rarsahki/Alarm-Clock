package com.example.android.perfectclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by User on 04-12-2018.
 */

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat tfhours = new SimpleDateFormat("HHmm");
        String time = tfhours.format(calendar.getTime());
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        loop:for(int i=1;i<=7;i++){
            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat day = new SimpleDateFormat("EEEE");
            calendar1.add(Calendar.DATE,i);
            String dayOfWeek = day.format(calendar1.getTime());
            if(dayOfWeek.equalsIgnoreCase("Monday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_MONDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_MONDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }
            }
            else if(dayOfWeek.equalsIgnoreCase("Tuesday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_TUESDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_TUESDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }
            }
            else if(dayOfWeek.equalsIgnoreCase("Wednesday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_WEDNESDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_WEDNESDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }

            }
            else if(dayOfWeek.equalsIgnoreCase("Thursday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_THURSDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_THURSDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }
            }
            else if(dayOfWeek.equalsIgnoreCase("Friday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_FRIDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_FRIDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }
            }
            else if(dayOfWeek.equalsIgnoreCase("Saturday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_SATURDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_SATURDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }
            }
            else if(dayOfWeek.equalsIgnoreCase("Sunday")){
                Table table1 = new Table(context);
                SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
                String column[] = {Field.Table.COLUMN_SUNDAY};
                Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null,null,null,null);
                cursor.moveToFirst();
                String id = null;
                if(cursor != null){
                    id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_SUNDAY));
                }
                sqLiteDatabase1.close();
                if(id.equalsIgnoreCase("true")){
                    calendar.add(Calendar.DATE,i);
                    contentValues.put(Field.Table.COLUMN_STATE,"true");
                    break loop;
                }else{
                    contentValues.put(Field.Table.COLUMN_STATE,"false");
                }
            }
        }
        contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
        contentValues.put(Field.Table.COLUMN_MILLI,calendar.getTimeInMillis());
        sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+time+"'",null);
        sqLiteDatabase.close();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent_setAlarm = new Intent(context,AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int)calendar.getTimeInMillis(),intent_setAlarm,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(context,AlarmAlertService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(intent1);
                }
                else
                    context.startService(intent1);
            }
        }).start();
        Intent intent1 = new Intent(context,PatternLock.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);
    }
}
