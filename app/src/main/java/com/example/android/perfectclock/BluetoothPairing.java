package com.example.android.perfectclock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.android.perfectclock.AlarmAlertService.volume;
import static com.example.android.perfectclock.MainActivity.mediaPlayer;

public class BluetoothPairing extends Activity {
    int REQUEST_BLUETOOTH_ACCESS = 999;
    public ArrayList<BluetoothItem> devices;
    ArrayList<BluetoothItem> registeredDevices;
    ListView BlueToothList;
    FloatingActionButton refresh;
    FloatingActionButton ok;
    BluetoothAdapter bluetoothAdapter;
    BluetoothListAdapater bluetoothListAdapater;
    SharedPreferences sharedPreferences;
    ArrayList<BluetoothItem> savedDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_pairing);
        if(getIntent().getStringExtra("Search")!=null){
            RelativeLayout bluetoothlayout = findViewById(R.id.bluetoothlayout);
            ok = findViewById(R.id.ok);
            bluetoothlayout.removeView(ok);
            refresh = findViewById(R.id.refresh);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0,0,0,16);
            refresh.setLayoutParams(layoutParams);
            savedDevices = new ArrayList<>();
            savedDevices = getArrayList("registeredDevices");
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getWindow().setLayout((int) (displayMetrics.widthPixels * .8), (int) (displayMetrics.heightPixels * .8));
        WindowManager.LayoutParams wLayoutParams = getWindow().getAttributes();
        wLayoutParams.gravity = Gravity.CENTER;
        wLayoutParams.x = 0;
        wLayoutParams.y = -20;
        getWindow().setAttributes(wLayoutParams);
        devices = new ArrayList<>();
        registeredDevices = new ArrayList<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth,REQUEST_BLUETOOTH_ACCESS);
        }
        Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1200);
        startActivity(discoverable);
        IntentFilter listOfDevices = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver,listOfDevices);
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothListAdapater.notifyDataSetChanged();
                devices.clear();
                IntentFilter listOfDevices = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver,listOfDevices);
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                if(getIntent().getStringExtra("Search")==null)
                    Toast.makeText(getBaseContext(),"Long Click To Register/Deregister",Toast.LENGTH_LONG).show();
            }
        });
        bluetoothListAdapater = new BluetoothListAdapater(devices,getBaseContext());
        BlueToothList = findViewById(R.id.BlueToothList);
        BlueToothList.setAdapter(bluetoothListAdapater);
        ok = findViewById(R.id.ok);
        if(ok != null){
            BlueToothList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    View linearLayout = parent.getChildAt(position);
                    BluetoothItem bluetoothItem = (BluetoothItem) parent.getItemAtPosition(position);
                    TextView textView = (TextView) view.findViewById(R.id.BlueToothName);
                    if(textView.getCurrentTextColor()== Color.BLUE){
                        textView.setTextColor(Color.BLACK);
                        registeredDevices.remove(bluetoothItem);
                    }else{
                        textView.setTextColor(Color.BLUE);
                        registeredDevices.add(bluetoothItem);
                    }
                    Toast.makeText(getBaseContext(),registeredDevices.size()+"",Toast.LENGTH_SHORT).show();
                    saveArrayList(registeredDevices,"registeredDevices");
                    Toast.makeText(getBaseContext(),getArrayList("registeredDevices").size()+"",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_BLUETOOTH_ACCESS && resultCode == RESULT_OK){
            IntentFilter listOfDevices = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver,listOfDevices);
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();
        }else{
            Toast.makeText(this,"Please Enable Bluetooth", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = bluetoothDevice.getName();
                String macAddress = bluetoothDevice.getAddress();
                if(name == null){
                    devices.add(new BluetoothItem("",macAddress+""));
                }else{
                    devices.add(new BluetoothItem(name,macAddress+""));
                }
                bluetoothListAdapater.notifyDataSetChanged();
                if(getIntent().getStringExtra("Search")!=null){
                    for(int i=0;i<savedDevices.size();i++) {
                        if (savedDevices.get(i).getmName().equalsIgnoreCase(name) ||
                                savedDevices.get(i).getmMacAddress().equalsIgnoreCase(macAddress)) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) volume, 0);
                            Intent intent1 = new Intent(getBaseContext(), AlarmAlertService.class);
                            stopService(intent1);
                            Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent2);
                        }
                    }
                }
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                if(devices.size()==0){
                    Toast.makeText(getBaseContext(),"No devices found",Toast.LENGTH_LONG).show();
                    bluetoothAdapter.cancelDiscovery();
                }
                else{
                    Toast.makeText(getBaseContext(),"Disovery Done! Press refresh to discover again",Toast.LENGTH_LONG).show();
                    bluetoothAdapter.cancelDiscovery();
                }
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN){
            Toast.makeText(this,"Please Pass First",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN){
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    public void saveArrayList(ArrayList<BluetoothItem> list, String key){
        SharedPreferences prefs = getSharedPreferences("savedDevices",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
        editor.commit();
    }

    public ArrayList<BluetoothItem> getArrayList(String key){
        SharedPreferences prefs = getSharedPreferences("savedDevices",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<BluetoothItem>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
