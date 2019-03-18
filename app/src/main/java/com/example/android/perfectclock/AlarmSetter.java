package com.example.android.perfectclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

/**
 * Created by User on 27-12-2018.
 */

public class AlarmSetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Table table = new Table(context);
        SQLiteDatabase sqLiteDatabase = table.getReadableDatabase();
        String column[] = {Field.Table.COLUMN_HOUR_MINUTE};
        Cursor cursor = sqLiteDatabase.query(Field.Table.TABLE_NAME,null,null,null,null,null,null);
        int count = cursor.getCount();
        sqLiteDatabase.close();
        for(int i=0;i<count;i++){
            Table table1 = new Table(context);
            SQLiteDatabase sqLiteDatabase1 = table1.getReadableDatabase();
            String column1[] = {Field.Table.COLUMN_MILLI};
            Cursor cursor1 = sqLiteDatabase1.query(Field.Table.TABLE_NAME,column1,null,null,null,null,null);
            cursor1.moveToFirst();
            long id = 0;
            if(cursor1 != null){
                cursor1.moveToPosition(i);
                id = cursor1.getLong(cursor1.getColumnIndexOrThrow(Field.Table.COLUMN_MILLI));
            }
            Intent intent1 = new Intent(context,AlarmReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int)id,intent1,0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,id,pendingIntent);
            }else{
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,id,pendingIntent);
            }
            sqLiteDatabase1.close();
        }
    }
}
