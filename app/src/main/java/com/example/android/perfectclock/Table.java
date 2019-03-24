package com.example.android.perfectclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 06-12-2018.
 */

public class Table extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ALARM";

    public Table(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    public static final String CREATE_TABLE_ALARM = "CREATE TABLE " + Field.Table.TABLE_NAME + "( " + Field.Table.COLUMN_HOUR_MINUTE +
            " TEXT" + " PRIMARY KEY" + ", " + Field.Table.COLUMN_HOUR +" TEXT" + " NOT NULL"+ ", " +
            Field.Table.COLUMN_MINUTE + " TEXT" + " NOT NULL"+ ", "  + Field.Table.COLUMN_STATE + " TEXT" + " NOT NULL"+ ", "
            + Field.Table.COLUMN_PATTERN_STATUS + " TEXT" + " NOT NULL"+ ", " + Field.Table.COLUMN_MILLI + " INTEGER" +
            " NOT NULL" + ", " + Field.Table.COLUMN_SETTING + " TEXT" +
            " NOT NULL" + ", " + Field.Table.COLUMN_24HOUR +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_SUNDAY +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_MONDAY +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_TUESDAY +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_WEDNESDAY +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_THURSDAY +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_FRIDAY +" TEXT" + " NOT NULL" +
            ", " + Field.Table.COLUMN_SATURDAY +" TEXT" + " NOT NULL" + ")";
    public static final String CREATE_TABLE_BLUETOOTH = "CREATE TABLE " + Field.BluetoothTable.TABLE_NAME + "( " + Field.BluetoothTable.COLUMN_MACADDRESS + " TEXT" + " NOT NULL" + ", "
            + Field.BluetoothTable.COLUMN_NAME + " TEXT" + " NOT NULL" + ")";
    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_ALARM);
            db.execSQL(CREATE_TABLE_BLUETOOTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
