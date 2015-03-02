package com.khalid.hisnulmuslim.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import classes.Dua;
import com.khalid.hisnulmuslim.database.HisnDatabaseInfo;

public class DuaGroupLoader extends AbstractQueryLoader<List<Dua>> {
    public DuaGroupLoader(Context context) {
        super(context);
    }

    @Override
    public List<Dua> loadInBackground() {
        List<Dua> results = null;
        Cursor duaGroupCursor = null;
        try {
            final SQLiteDatabase database = mDbHelper.getDb();
            duaGroupCursor = database.query(HisnDatabaseInfo.DuaGroupTable.TABLE_NAME,
                    new String[]{HisnDatabaseInfo.DuaGroupTable._ID,
                            HisnDatabaseInfo.DuaGroupTable.ENGLISH_TITLE},
                    null,
                    null,
                    null,
                    null,
                    HisnDatabaseInfo.DuaGroupTable._ID);

            if (duaGroupCursor != null && duaGroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int dua_group_id = duaGroupCursor.getInt(0);
                    String dua_group_title = duaGroupCursor.getString(1);
                    results.add(new Dua(dua_group_id, dua_group_title));
                } while (duaGroupCursor.moveToNext());
            }
        } finally {
            if (duaGroupCursor != null) {
                duaGroupCursor.close();
            }
        }

        return results;
    }
}
