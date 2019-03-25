package com.example.ruggiu.prova1;

import android.provider.BaseColumns;

public final class PressureReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PressureReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "measures";
        public static final String COLUMN_NAME_SYS = "systolic";
        public static final String COLUMN_NAME_DIA = "diastolic";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
