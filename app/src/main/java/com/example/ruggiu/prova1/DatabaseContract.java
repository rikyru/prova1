package com.example.ruggiu.prova1;

import android.provider.BaseColumns;

public final class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME_USERS = "users";
        public static final String TABLE_NAME_MEASURES = "measures";
        public static final String TABLE_NAME_MEASURE_TYPES= "measure_types";
        public static final String COLUMN_NAME_ID_USER = "id_user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_NAME_DR_EMAIL = "dr_email";
        public static final String COLUMN_NAME_ID_MEASURE = "id_measure";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_ID_TYPE_MEASURE = "id_type_measure";
        public static final String COLUMN_NAME_MEASURE_NAME = "measures_name";
        public static final String COLUMN_NAME_TMSTMP = "unixtimestamp";
        public static final String COLUMN_NAME_TIME = "time"; //ridondante
    }
}
