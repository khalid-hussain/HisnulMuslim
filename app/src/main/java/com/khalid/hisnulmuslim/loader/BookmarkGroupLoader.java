package com.khalid.hisnulmuslim.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khalid.hisnulmuslim.model.Dua;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 01 Улгиг.
 */
public class BookmarkGroupLoader extends AbstractQueryLoader<List<Dua>> {
    public BookmarkGroupLoader(Context context) {
        super(context);
    }

    @Override
    public List<Dua> loadInBackground() {
        List<Dua> results = null;
        Cursor duaGroupCursor = null;
        try {
            final SQLiteDatabase database = mDbHelper.getDb();

            duaGroupCursor = database.rawQuery("SELECT _id, en_title " +
                    "FROM dua_group " +
                    "WHERE _id " +
                    "IN " +
                    "(SELECT group_id " +
                    "FROM dua " +
                    "WHERE fav=?)",new String[]{"1"});

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