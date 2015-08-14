package com.khalid.hisnulmuslim.database;

import android.provider.BaseColumns;

public class HisnDatabaseInfo {
    public static final String DB_NAME = "hisnul.sqlite3";
    public static final int DB_VERSION = 1;

    public static final class DuaGroupTable implements BaseColumns {
        public static final String TABLE_NAME = "dua_group";
        public static final String ARABIC_TITLE = "ar_title";
        public static final String ENGLISH_TITLE = "en_title";
        public static final String FRENCH_TITLE = "fr_title";
    }

    public static final class DuaTable implements BaseColumns {
        public static final String TABLE_NAME = "dua";
        public static final String DUA_ID = "_id";
        public static final String GROUP_ID = "group_id";
        public static final String FAV = "fav";
        public static final String ARABIC_DUA = "ar_dua";
        public static final String ARABIC_REFERENCE = "ar_reference";
        public static final String ENGLISH_TRANSLATION = "en_translation";
        public static final String ENGLISH_REFERENCE = "en_reference";
    }
}