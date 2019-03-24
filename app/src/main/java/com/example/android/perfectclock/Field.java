package com.example.android.perfectclock;

import android.provider.BaseColumns;

/**
 * Created by User on 06-12-2018.
 */

public class Field {
    public class Table implements BaseColumns {
        public static final String TABLE_NAME = "Alarms";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_24HOUR = "fullhour";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_SETTING = "setting";
        public static final String COLUMN_HOUR_MINUTE = "hm";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_MILLI = "milli";
        public static final String COLUMN_RINGTONE = "ringtone";
        public static final String COLUMN_PATTERN_STATUS = "pattern";
        public static final String COLUMN_SUNDAY = "Sunday";
        public static final String COLUMN_MONDAY = "Monday";
        public static final String COLUMN_TUESDAY = "Tuesday";
        public static final String COLUMN_WEDNESDAY = "Wednesday";
        public static final String COLUMN_THURSDAY = "Thursday";
        public static final String COLUMN_FRIDAY = "Friday";
        public static final String COLUMN_SATURDAY = "Saturday";
    }
    public class BluetoothTable implements BaseColumns{
        public static final String TABLE_NAME = "BluetoothDevices";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MACADDRESS = "macaddress";
    }
}
