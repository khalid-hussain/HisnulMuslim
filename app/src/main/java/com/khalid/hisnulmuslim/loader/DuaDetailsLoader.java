package com.khalid.hisnulmuslim.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import classes.Dua;
import com.khalid.hisnulmuslim.database.HisnDatabaseInfo;

public class DuaDetailsLoader extends AbstractQueryLoader<List<Dua>> {
    private int mGroup;

    public DuaDetailsLoader(Context context, int group) {
        super(context);
        mGroup = group;
    }

    @Override
    public List<Dua> loadInBackground() {
        List<Dua> results = null;
        Cursor duaDetailCursor = null;
        try {
            final SQLiteDatabase database = mDbHelper.getDb();
            duaDetailCursor = database.query(HisnDatabaseInfo.DuaTable.TABLE_NAME,
                    new String[]{HisnDatabaseInfo.DuaTable._ID,
                            HisnDatabaseInfo.DuaTable.ARABIC_DUA,
                            HisnDatabaseInfo.DuaTable.ENGLISH_TRANSLATION,
                            HisnDatabaseInfo.DuaTable.ENGLISH_REFERENCE},
                    HisnDatabaseInfo.DuaTable.GROUP_ID + "=" + mGroup,
                    null, null, null, null);
            results = new ArrayList<>();
            if (duaDetailCursor != null && duaDetailCursor.moveToFirst()) {
                do {
                    int reference = Integer.parseInt(duaDetailCursor.getString(0));
                    String arabic = duaDetailCursor.getString(1);
                    String translation = duaDetailCursor.getString(2);
                    String book_reference = duaDetailCursor.getString(3);
                    results.add(new Dua(reference, arabic, translation, book_reference));
                } while (duaDetailCursor.moveToNext());
            }
        } finally {
            if (duaDetailCursor != null) {
                duaDetailCursor.close();
            }
        }
        return results;
    }
}
