package com.example.android.perfectclock;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    public static MediaPlayer mediaPlayer;
    TextView hour;
    TextView minute;
    TextView second;
    TextView setting;
    TextView date;
    TextView step;
    ArrayList<Integer> arrayList = new ArrayList<>();
    public static ArrayList<AlarmItem> alarmItems;
    public static AlarmListAdapter alarmListAdapterl;
    public static SwipeMenuListView swipeMenuListView;
    FrameLayout addAlarm;
    int PERMISSION_REQUEST_LOCATION = 123;
    Handler handler;
    public String realHour;
    public String real24Hour;
    public String realMinute;
    String realSecond;
    String realMark;
    String realDate;
    Uri ringtone;
    final int REQUEST_PERMISSION_GPS = 1;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getBoolean("Pairing",false)==false){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Pairing",true);
            editor.commit();
            startActivity(new Intent(this,BluetoothPairing.class));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getBaseContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){

                }else{
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_LOCATION);
                }
            }
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitle(null);
        for(int i=0;i<count();i++){
            if(pattern_status(i).equalsIgnoreCase("YES")){
                Intent intent = new Intent(this,PatternLock.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        hour = (TextView)findViewById(R.id.hour_id);
        minute = (TextView)findViewById(R.id.minute_id);
        second = (TextView)findViewById(R.id.second_id);
        setting = (TextView)findViewById(R.id.setting_id);
        date = (TextView)findViewById(R.id.date_id);
        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.alarm_list);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/digital-7.ttf");
        hour.setTypeface(custom_font);
        minute.setTypeface(custom_font);
        second.setTypeface(custom_font);
        setting.setTypeface(custom_font);
        handler = new Handler();
        handler.postDelayed(runnable,0);
        addAlarm = (FrameLayout) findViewById(R.id.add_alarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AlarmTimePickupPopup.class);
                startActivity(intent);
            }
        });
        alarmItems = new ArrayList<AlarmItem>();
        //alarmItems.add(new AlarmItem("05","40","12 December 2018", "AM", "ON"));
        alarmItems.clear();
        for(int i=0;i<count();i++){
            alarmItems.add(new AlarmItem(hour(i),minute(i),HH(i),id(i),setting(i),state(i),millis(i)));
        }
        alarmListAdapterl = new AlarmListAdapter(alarmItems, this.getBaseContext());
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                  //      0xCE)));
                // set item width
                editItem.setWidth(180);
                // set item title
                editItem.setIcon(R.drawable.editiconpopup);
                // set item title fontsize
                // set item title font color
                editItem.setTitleColor(Color.WHITE);
                editItem.setBackground(android.R.color.holo_green_light);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                  //      0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.deleteiconpopup);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setBackground(android.R.color.holo_red_light);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        swipeMenuListView.setMenuCreator(creator);
        swipeMenuListView.setAdapter(alarmListAdapterl);
        swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                View view = getViewByPosition(position,swipeMenuListView);
                TextView textView_hour = view.findViewById(R.id.hour_id);
                TextView textView_setting = view.findViewById(R.id.setting_id);
                String hour1;
                if(textView_setting.getText().toString().equalsIgnoreCase("PM")&&!textView_hour.getText().toString().equalsIgnoreCase("12")){
                    hour1 = (12+Integer.parseInt(textView_hour.getText().toString()))+"";
                }
                else {
                    hour1 = textView_hour.getText().toString();
                }
                TextView textView_minute = view.findViewById(R.id.minute_id);
                switch (index){
                    case 0: //edit an item
                        Intent intent = new Intent(MainActivity.this,AlarmTimePickupPopup.class);
                        intent.putExtra("Time",hour1+textView_minute.getText().toString());
                        intent.putExtra("Hour",hour1);
                        intent.putExtra("Minute",textView_minute.getText().toString());
                        intent.putExtra("Setting",textView_setting.getText().toString());
                        intent.putExtra("Edit","true");
                        intent.putExtra("Ringtone",ringtone);
                        startActivity(intent);
                        break;
                    case 1: //delete an item
                        Table table = new Table(MainActivity.this);
                        SQLiteDatabase sqLiteDatabase = table.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Field.Table.COLUMN_STATE,"false");
                        contentValues.put(Field.Table.COLUMN_PATTERN_STATUS,"false");
                        sqLiteDatabase.update(Field.Table.TABLE_NAME,contentValues,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+textView_minute.getText().toString()+"'",null);
                        alarmItems.clear();
                        for(int i=0;i<count();i++){
                            alarmItems.add(new AlarmItem(hour(i),minute(i),HH(i),id(i),setting(i),state(i),millis(i)));
                        }
                        alarmListAdapterl.notifyDataSetChanged();
                        sqLiteDatabase.delete(Field.Table.TABLE_NAME,Field.Table.COLUMN_HOUR_MINUTE+"="+"'"+hour1+textView_minute.getText().toString()+"'",null);
                        sqLiteDatabase.close();
                        alarmItems.clear();
                        for(int i=0;i<count();i++){
                            alarmItems.add(new AlarmItem(hour(i),minute(i),HH(i),id(i),setting(i),state(i),millis(i)));
                        }
                        alarmListAdapterl.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_LOCATION && grantResults[0] == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"Enable Location to show available bluetooth devices",Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.getItem(1).setIcon(R.drawable.sound).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.getItem(1).setIntent(new Intent(RingtoneManager.ACTION_RINGTONE_PICKER));
        Switch bluetoothswitch = menu.getItem(0).getActionView().findViewById(R.id.switch_bluetooth);
        SharedPreferences sharedPreferences = getSharedPreferences("BluetoothAlarmStatus",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("BluetoothAlarmStatus",false)){
            bluetoothswitch.setChecked(true);
        }else{
            bluetoothswitch.setChecked(false);
        }
        bluetoothswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getSharedPreferences("BluetoothAlarmStatus",MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                if(isChecked){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(getBaseContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){

                            }else{
                                ActivityCompat.requestPermissions(getParent(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_LOCATION);
                            }
                        }
                    }
                    editor.putBoolean("BluetoothAlarmStatus",true);
                    editor.commit();
                    startActivity(new Intent(getBaseContext(),BluetoothPairing.class));
                }else {
                    editor.putBoolean("BluetoothAlarmStatus",false);
                    editor.commit();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.alarm_tone:
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALL);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Ringtone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, Uri.parse("C:\\Users\\User\\Desktop\\PerfectClock\\app\\src\\main\\res\\raw\\alarm.ogg"));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                startActivityForResult(intent,999);
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data.getData()==null && requestCode == 999){
            ringtone = (Uri) data.getExtras().get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            SharedPreferences.Editor sharedPreferences = getSharedPreferences("RINGTONE",MODE_PRIVATE).edit();
            sharedPreferences.putString("ringtone",ringtone.toString());
            sharedPreferences.apply();
        }
        else if(data == null){
            Toast.makeText(this,"Please select a valid Ringtone",Toast.LENGTH_LONG).show();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat hours = new SimpleDateFormat("hh");
            SimpleDateFormat tfhours = new SimpleDateFormat("HH");
            SimpleDateFormat minutes = new SimpleDateFormat("mm");
            SimpleDateFormat seconds = new SimpleDateFormat("ss");
            SimpleDateFormat marker = new SimpleDateFormat("a");
            SimpleDateFormat dates = new SimpleDateFormat("dd MMMM yyyy");
            realHour = hours.format(c.getTime());
            real24Hour = tfhours.format(c.getTime());
            realMinute = minutes.format(c.getTime());
            realSecond = seconds.format(c.getTime());
            realMark = marker.format(c.getTime());
            realDate = dates.format(c.getTime());
            hour.setText(realHour);
            minute.setText(realMinute);
            second.setText(realSecond);
            setting.setText(realMark.toUpperCase());
            date.setText(realDate);
            handler.postDelayed(runnable,0);
        }
    };
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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
    public int count(){
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,null,null,null,null,null,null);
        int id = cursor.getCount();
        sqLiteDatabase.close();
        return id;
    }
    public int id1(int i){
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_ID};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        int id = 0;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getInt(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_ID));
        }
        sqLiteDatabase.close();
        return id;
    }
    public String id(int i){
        Table table = new Table(this);
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
    public String hour(int i){
        Table table = new Table(this);
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
        Table table = new Table(this);
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
        Table table = new Table(this);
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
    public String pattern_status(int i){
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_PATTERN_STATUS};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_PATTERN_STATUS));
        }
        sqLiteDatabase.close();
        return id;
    }

    public String setting(int i){
        Table table = new Table(this);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_SETTING};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,column,null,null,null,null,null);
        cursor.moveToFirst();
        String id = null;
        if(cursor != null){
            cursor.moveToPosition(i);
            id = cursor.getString(cursor.getColumnIndexOrThrow(Field.Table.COLUMN_SETTING));
        }
        sqLiteDatabase.close();
        return id;
    }

}
