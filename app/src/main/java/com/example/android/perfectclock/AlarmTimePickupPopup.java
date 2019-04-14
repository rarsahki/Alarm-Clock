package com.example.android.perfectclock;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.android.perfectclock.MainActivity.alarmItems;
import static com.example.android.perfectclock.MainActivity.alarmListAdapterl;
import static com.example.android.perfectclock.MainActivity.swipeMenuListView;

public class AlarmTimePickupPopup extends Activity {
    public int diff_hour;
    public int diff_minute;
    public Calendar calendar;
    TimePicker timePicker;
    ArrayList<String> repeat_days;
    String hour_alarm;
    int hour;
    int minute;
    int realhour;
    int realMinute;
    Handler handler;
    String minute_alarm;
    Button ok_alarm;
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    CheckBox sunday;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_time_pickup_popup);
        calendar = Calendar.getInstance();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getWindow().setLayout((int) (displayMetrics.widthPixels * .8), (int) (displayMetrics.heightPixels * .8));
        WindowManager.LayoutParams wLayoutParams = getWindow().getAttributes();
        wLayoutParams.gravity = Gravity.CENTER;
        wLayoutParams.x = 0;
        wLayoutParams.y = -20;
        getWindow().setAttributes(wLayoutParams);
        handler = new Handler();
        handler.postDelayed(runnable, 0);
        timePicker = findViewById(R.id.timePicker);
        repeat_days = new ArrayList<>();
        ok_alarm = findViewById(R.id.ok_button);
        monday =  findViewById(R.id.monday_check);
        tuesday =  findViewById(R.id.tuesday_check);
        wednesday =  findViewById(R.id.wednesday_check);
        thursday =  findViewById(R.id.thursday_check);
        friday =  findViewById(R.id.friday_check);
        saturday =  findViewById(R.id.saturday_check);
        sunday =  findViewById(R.id.sunday_check);
        if (getIntent().getStringExtra("Hour") != null) {
            String hour_intent = getIntent().getStringExtra("Hour");
            String minute_intent = getIntent().getStringExtra("Minute");
            Table table1 = new Table(this);
            SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
            String column[] = {Field.Table.COLUMN_SUNDAY};
            Cursor cursor = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor.moveToFirst();
            String id = null;
            if(cursor != null){
                id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_SUNDAY));
            }
            if(id.equalsIgnoreCase("true"))
                sunday.setChecked(true);
            String column1[] = {Field.Table.COLUMN_MONDAY};
            Cursor cursor1 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column1,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor1.moveToFirst();
            String id1 = null;
            if(cursor1 != null){
                id1 = cursor1.getString(cursor1.getColumnIndexOrThrow(Field.Table.COLUMN_MONDAY));
            }
            if(id1.equalsIgnoreCase("true"))
                monday.setChecked(true);
            String column2[] = {Field.Table.COLUMN_TUESDAY};
            Cursor cursor2 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column2,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor2.moveToFirst();
            String id2 = null;
            if(cursor2 != null){
                id2 = cursor2.getString(cursor2.getColumnIndexOrThrow(Field.Table.COLUMN_TUESDAY));
            }
            if(id2.equalsIgnoreCase("true"))
                tuesday.setChecked(true);
            String column3[] = {Field.Table.COLUMN_WEDNESDAY};
            Cursor cursor3 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column3,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor3.moveToFirst();
            String id3 = null;
            if(cursor3 != null){
                id3 = cursor3.getString(cursor3.getColumnIndexOrThrow(Field.Table.COLUMN_WEDNESDAY));
            }
            if(id3.equalsIgnoreCase("true"))
                wednesday.setChecked(true);
            String column4[] = {Field.Table.COLUMN_THURSDAY};
            Cursor cursor4 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column4,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor4.moveToFirst();
            String id4 = null;
            if(cursor4 != null){
                id4 = cursor4.getString(cursor4.getColumnIndexOrThrow(Field.Table.COLUMN_THURSDAY));
            }
            if(id4.equalsIgnoreCase("true"))
                thursday.setChecked(true);
            String column5[] = {Field.Table.COLUMN_FRIDAY};
            Cursor cursor5 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column5,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor5.moveToFirst();
            String id5 = null;
            if(cursor5 != null){
                id5 = cursor5.getString(cursor5.getColumnIndexOrThrow(Field.Table.COLUMN_FRIDAY));
            }
            if(id5.equalsIgnoreCase("true"))
                friday.setChecked(true);
            String column6[] = {Field.Table.COLUMN_SATURDAY};
            Cursor cursor6 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column6,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+getIntent().getStringExtra("Time")+"'",null,null,null,null);
            cursor6.moveToFirst();
            String id6 = null;
            if(cursor6 != null){
                id6 = cursor6.getString(cursor6.getColumnIndexOrThrow(Field.Table.COLUMN_SATURDAY));
            }
            if(id6.equalsIgnoreCase("true"))
                saturday.setChecked(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(Integer.parseInt(hour_intent));
                timePicker.setMinute(Integer.parseInt(minute_intent));
            }
            timePicker.setIs24HourView(false);
        }
        ok_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour_alarm = timePicker.getHour() + "";
                    hour = timePicker.getHour();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    minute_alarm = timePicker.getMinute() + "";
                    minute = timePicker.getMinute();
                }
                if (hour > realhour) {
                    if (hour - realhour == 1) {
                        diff_hour = 0;
                        diff_minute = (60 - realMinute) + minute;
                        if (diff_minute >= 60) {
                            diff_hour++;
                            diff_minute = diff_minute - 60;
                        }
                    } else {
                        diff_hour = hour - realhour;
                        if (realMinute == minute)
                            diff_minute = 0;
                        else {
                            if (realMinute > minute) {
                                diff_minute = (60 - realMinute) + minute;
                                diff_hour--;
                            } else {
                                diff_minute = Math.abs(realMinute - minute);
                            }
                        }
                    }
                } else if (hour == realhour && realMinute < minute) {
                    diff_hour = 0;
                    diff_minute = Math.abs(realMinute - minute);
                } else {
                    diff_hour = 24 - (realhour - hour);
                    if (realMinute == minute)
                        diff_minute = 0;
                    else {
                        if (realMinute > minute) {
                            diff_minute = (60 - realMinute) + minute;
                            diff_hour--;
                        } else {
                            diff_minute = Math.abs(realMinute - minute);
                        }
                    }
                }
                Table table = new Table(getBaseContext());
                SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                String shour;
                String shour1;
                String sminute;
                if (hour < 10) {
                    shour = "0" + hour;
                    shour1 = "0" + hour;
                } else {
                    if (hour > 12) {
                        if (hour - 12 < 10) {
                            shour = "0" + (hour - 12);
                        } else {
                            shour = "" + (hour - 12);
                        }
                    } else {
                        shour = hour + "";
                    }
                    shour1 = hour + "";

                }
                if (minute < 10) {
                    sminute = "0" + minute;
                } else
                    sminute = minute + "";

                if (monday.isChecked())
                    contentValues.put(Field.Table.COLUMN_MONDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_MONDAY,"false");

                if (tuesday.isChecked())
                    contentValues.put(Field.Table.COLUMN_TUESDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_TUESDAY,"false");

                if (wednesday.isChecked())
                    contentValues.put(Field.Table.COLUMN_WEDNESDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_WEDNESDAY,"false");

                if (thursday.isChecked())
                    contentValues.put(Field.Table.COLUMN_THURSDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_THURSDAY,"false");

                if (friday.isChecked())
                    contentValues.put(Field.Table.COLUMN_FRIDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_FRIDAY,"false");

                if (saturday.isChecked())
                    contentValues.put(Field.Table.COLUMN_SATURDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_SATURDAY,"false");

                if (sunday.isChecked())
                    contentValues.put(Field.Table.COLUMN_SUNDAY,"true");
                else
                    contentValues.put(Field.Table.COLUMN_SUNDAY,"false");

                contentValues.put(Field.Table.COLUMN_24HOUR, shour1);
                contentValues.put(Field.Table.COLUMN_HOUR, shour);
                contentValues.put(Field.Table.COLUMN_MINUTE, sminute);
                contentValues.put(Field.Table.COLUMN_HOUR_MINUTE, shour1 + sminute);
                contentValues.put(Field.Table.COLUMN_STATE, "true");
                contentValues.put(Field.Table.COLUMN_PATTERN_STATUS, "false");
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                String setting;
                if (hour >= 12) {
                    setting = "PM";
                } else {
                    setting = "AM";
                }
                contentValues.put(Field.Table.COLUMN_MILLI, calendar.getTimeInMillis());
                contentValues.put(Field.Table.COLUMN_SETTING, setting);
                if (getIntent().getStringExtra("Time") != null) {
                    Table table1 = new Table(getBaseContext());
                    SQLiteDatabase sqLiteDatabase1 = table1.getWritableDatabase();
                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put(Field.Table.COLUMN_STATE,"false");
                    sqLiteDatabase1.update(Field.Table.TABLE_NAME,contentValues1,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+ getIntent().getStringExtra("Time") +"'",null);
                    sqLiteDatabase1.close();
                    alarmItems.clear();
                    for(int i=0;i<count();i++){
                        alarmItems.add(new AlarmItem(hour(i),minute(i),HH(i),id(i),setting(i),state(i),millis(i)));
                    }
                    alarmListAdapterl.notifyDataSetChanged();
                    sqLiteDatabase.update(Field.Table.TABLE_NAME, contentValues, Field.Table.COLUMN_HOUR_MINUTE + "=" + "'" + getIntent().getStringExtra("Time") + "'", null);
                    alarmItems.clear();
                    for(int i=0;i<count();i++){
                        alarmItems.add(new AlarmItem(hour(i),minute(i),HH(i),id(i),setting(i),state(i),millis(i)));
                    }
                    alarmListAdapterl.notifyDataSetChanged();
                    sqLiteDatabase.close();
                    int a = 0;
                    for (int i = 0; i < count(); i++) {
                        if (id(i).equalsIgnoreCase(shour1 + sminute)) {
                            break;
                        } else
                            a++;
                    }
                    if (a == count()|| getIntent().getStringExtra("Edit").equalsIgnoreCase("true")) {
                        if (diff_hour > 1 && diff_minute > 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hours and " + diff_minute + " minutes to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour == 0 && diff_minute > 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_minute + " minutes to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour == 0 && diff_minute > 0 && diff_minute <= 1)
                            Toast.makeText(AlarmTimePickupPopup.this, "less than a minute to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 0 && diff_hour <= 1 && diff_minute > 0 && diff_minute <= 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hour and " + diff_minute + " minute to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 1 && diff_minute > 0 && diff_minute <= 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hours and " + diff_minute + " minute to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 0 && diff_hour <= 1 && diff_minute > 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hour and " + diff_minute + " minutes to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour >= 1 && diff_minute == 0)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hours to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 0 && diff_hour <= 1 && diff_minute == 0)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hour to go", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(AlarmTimePickupPopup.this, "Alarm already exists", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    int a = 0;
                    for (int i = 0; i < count(); i++) {
                        if (id(i).equalsIgnoreCase(shour1 + sminute)) {
                            break;
                        } else
                            a++;
                    }
                    if (a == count()) {
                        sqLiteDatabase.insert(Field.Table.TABLE_NAME, null, contentValues);
                        sqLiteDatabase.close();
                        if (diff_hour > 1 && diff_minute > 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hours and " + diff_minute + " minutes to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour == 0 && diff_minute > 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_minute + " minutes to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour == 0 && diff_minute > 0 && diff_minute <= 1)
                            Toast.makeText(AlarmTimePickupPopup.this, "less than a minute to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 0 && diff_hour <= 1 && diff_minute > 0 && diff_minute <= 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hour and " + diff_minute + " minute to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 1 && diff_minute > 0 && diff_minute <= 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hours and " + diff_minute + " minute to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 0 && diff_hour <= 1 && diff_minute > 1)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hour and " + diff_minute + " minutes to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour >= 1 && diff_minute == 0)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hours to go", Toast.LENGTH_LONG).show();
                        else if (diff_hour > 0 && diff_hour <= 1 && diff_minute == 0)
                            Toast.makeText(AlarmTimePickupPopup.this, diff_hour + " hour to go", Toast.LENGTH_LONG).show();
                        alarmItems.clear();
                        for(int i=0;i<count();i++){
                            alarmItems.add(new AlarmItem(hour(i),minute(i),HH(i),id(i),setting(i),state(i),millis(i)));
                        }
                        alarmListAdapterl = new AlarmListAdapter(alarmItems, getApplicationContext());
                        swipeMenuListView.setAdapter(alarmListAdapterl);
                        finish();
                    } else {
                        Toast.makeText(AlarmTimePickupPopup.this, "Alarm already exists", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      //  client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat tfhours = new SimpleDateFormat("HH");
            SimpleDateFormat minutes = new SimpleDateFormat("mm");
            realhour = Integer.parseInt(tfhours.format(c.getTime()));
            realMinute = Integer.parseInt(minutes.format(c.getTime()));
            handler.postDelayed(runnable, 0);
        }
    };

    public int count() {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, null, null, null, null, null, null);
        int id = cursor.getCount();
        sqLiteDatabase.close();
        return id;
    }

    public String id(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_HOUR_MINUTE));
        }
        sqLiteDatabase.close();
        return id;
    }

    public int id1(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_ID};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        int id = 0;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getInt(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_ID));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String hour(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_HOUR));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String minute(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_MINUTE));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String state(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_STATE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_STATE));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String pattern_status(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_PATTERN_STATUS};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_PATTERN_STATUS));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String setting(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_SETTING};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_SETTING));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String HH(int i){
        Table table = new Table(this);
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

    public long millis(int i){
        Table table = new Table(this);
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

    public String ringtone(int i) {
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_RINGTONE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME, column, null, null, null, null, null);
        cursor.moveToFirst();
        String id = null;
        if (cursor != null) {
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_RINGTONE));
        }
        sqLiteDatabase.close();
        return id;
    }
}
