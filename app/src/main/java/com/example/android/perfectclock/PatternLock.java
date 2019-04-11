package com.example.android.perfectclock;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.perfectclock.AlarmAlertService.volume;
import static com.example.android.perfectclock.MainActivity.mediaPlayer;
import static com.example.android.perfectclock.R.id.pattern;

public class PatternLock extends AppCompatActivity {
    PatternLockView patternLockView;
    BluetoothAdapter bluetoothAdapter;
    public ArrayList<BluetoothItem> devices;
    int REQUEST_BLUETOOTH_ACCESS = 999;
    String pattern1,json;
    ArrayList<Integer> arrayList = new ArrayList<>();
    TextView pattern_key;
    Gson gson;
    Type type;
    ArrayList<BluetoothItem> savedDevices = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String random_txt = "";
    public static boolean pattern_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
        }
        setContentView(R.layout.activity_pattern_lock);
        patternLockView = (PatternLockView) findViewById(pattern);
        patternLockView.setTactileFeedbackEnabled(true);
        pattern_key = (TextView) findViewById(R.id.pattern_key);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/1942.ttf");
        pattern1 = randomPatternGenerator();
        pattern_key.setTypeface(custom_font);
        if(pattern1==null)
            pattern1 = "612457830";
        pattern_key.setText(random_txt);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if(PatternLockUtils.patternToString(patternLockView, pattern).equalsIgnoreCase(pattern1)){
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                        pattern_status = true;
                        Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_LONG).show();
                        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,10,0);
                        SharedPreferences sharedPreferences = getSharedPreferences("BluetoothAlarmStatus",MODE_PRIVATE);
                        if(sharedPreferences.getBoolean("BluetoothAlarmStatus",false)){
                            Intent bluetooth = new Intent(getBaseContext(),BluetoothPairing.class);
                            bluetooth.putExtra("Search","device");
                            startActivity(bluetooth);
                        }else{
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            final AudioManager audioManager1 = (AudioManager) getSystemService(AUDIO_SERVICE);
                            audioManager1.setStreamVolume(AudioManager.STREAM_MUSIC, (int) volume, 0);
                            Intent intent1 = new Intent(getBaseContext(), AlarmAlertService.class);
                            stopService(intent1);
                            Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent2);
                        }
                }else{
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(getBaseContext(), "Incorrect! Draw pattern according to Numbers below", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getBaseContext(),"Sorry You Must Clear This",Toast.LENGTH_SHORT).show();
    }
    public String randomPatternGenerator(){
        long time_lastdigit = (System.currentTimeMillis()%10000)/1000;
        if(time_lastdigit==9){
            time_lastdigit = time_lastdigit-1;
        }
        long time_secondlastdigit = (System.currentTimeMillis()%1000)/100;
        Log.d("Time and Limit",System.currentTimeMillis()+"");
        long time = System.currentTimeMillis()+10;
        Log.d("Time and Limit",System.currentTimeMillis()+" "+time);
        int number = (int) time_lastdigit;
        int chance = (int) time_secondlastdigit;
        arrayList.add(number);
        arrayList = randomGenerator(number,chance,time);
        String random = "";
        for(int i=0;i<arrayList.size();i++){
            random = random + arrayList.get(i).toString();
            if(i==arrayList.size()-1)
                random_txt = random_txt + arrayList.get(i).toString();
            else
                random_txt = random_txt + arrayList.get(i).toString() + "-";
        }
        return random;
    }
    public ArrayList<Integer> randomGenerator(int number, int chance, long time){
        if(number == 0){
            loop:for(int n=chance%8;n<=7;chance++){
                int m = 0;
                n=chance%8;
                switch (n){
                    case 1: m=m+1;break;
                    case 3: m=m+3;break;
                    case 4: m=m+4;break;
                    case 5: m=m+5;break;
                    case 7: m=m+7;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 1){
            loop:for(int n=chance%8;n<=7;chance++){
                int m = 1;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&n==1){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case 2: m=m+2;break;
                    case 3: m=m+3;break;
                    case 4: m=m+4;break;
                    case 5: m=m+5;break;
                    case 7: m=m+7;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 2){
            loop:for(int n=chance%6;n<=5;chance++){
                int m = 2;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&(n==1||n==2)){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case 2: m=m+2;break;
                    case 3: m=m+3;break;
                    case 5: m=m+5;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 3){
            loop:for(int n=chance%6;n<=5;chance++){
                int m = 3;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&(n==1||n==2||n==3)){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case -2: m=m-2;break;
                    case -3: m=m-3;break;
                    case 3: m=m+3;break;
                    case 4: m=m+4;break;
                    case 5: m=m+5;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 4){
            loop:for(int n=chance%4;n<=3;chance++){
                int m = 4;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&(n==1||n==2||n==3)){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case -2: m=m-2;break;
                    case 2: m=m+2;break;
                    case -3: m=m-3;break;
                    case 3: m=m+3;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 5){
            loop:for(int n=chance%5;n<=4;chance++){
                int m = 5;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&(n==1||n==3||n==4)){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case 2: m=m+2;break;
                    case 3: m=m+3;break;
                    case -3: m=m-3;break;
                    case -4: m=m-4;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 6){
            loop:for(int n=chance%6;n<=5;chance++){
                int m = 6;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&(n==1||n==2||n==3||n==5)){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case -2: m=m-2;break;
                    case -3: m=m-3;break;
                    case -5: m=m-5;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 7){
            loop:for(int n=chance%8;n<=7;chance++){
                int m = 7;
                n=chance%8;
                if(SystemClock.uptimeMillis()%2==0&&(n!=1)){
                    n=n*(-1);
                }
                switch (n){
                    case -1: m=m-1;break;
                    case 1: m=m+1;break;
                    case -2: m=m-2;break;
                    case -3: m=m-3;break;
                    case -4: m=m-4;break;
                    case -5: m=m-5;break;
                    case -7: m=m-7;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        else if(number == 8){
            loop:for(int n=chance%8;n<=7;chance++){
                int m = 8;
                n=chance%8;
                n=n*(-1);
                switch (n){
                    case -1: m=m-1;break;
                    case -3: m=m-3;break;
                    case -4: m=m-4;break;
                    case -7: m=m-7;break;
                    default: if(System.currentTimeMillis()<=time){
                        continue;
                    }else{
                        break loop;
                    }
                }
                int n1 = 0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) != m)
                        n1++;
                    else
                        break;
                }
                if(n1 == arrayList.size()){
                    arrayList.add(m);
                    break;
                }
            }
        }
        if(System.currentTimeMillis()<=time&&arrayList.size()<=9){
            for(int i=0;i<arrayList.size();i++)
                Log.d("Numbers",arrayList.get(i).toString()+" ");
            randomGenerator(arrayList.get(arrayList.size()-1),chance,time);
        }
        return arrayList;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
