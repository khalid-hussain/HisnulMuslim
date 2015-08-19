package com.khalid.hisnulmuslim.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khalid.hisnulmuslim.database.HisnDatabaseInfo;
import com.khalid.hisnulmuslim.model.Dua;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 01 أغسطس.
 */
public class BookmarkDetailsLoader extends AbstractQueryLoader<List<Dua>> {
    private int mGroup;

    public BookmarkDetailsLoader(Context context, int group) {
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
                            HisnDatabaseInfo.DuaTable.FAV,
                            HisnDatabaseInfo.DuaTable.ARABIC_DUA,
                            HisnDatabaseInfo.DuaTable.ENGLISH_TRANSLATION,
                            HisnDatabaseInfo.DuaTable.ENGLISH_REFERENCE},
                    HisnDatabaseInfo.DuaTable.GROUP_ID + "=" + mGroup
                    + " AND " + HisnDatabaseInfo.DuaTable.FAV + "= 1"
                    ,
                    null, null, null, null);
            results = new ArrayList<>();
            if (duaDetailCursor != null && duaDetailCursor.moveToFirst()) {
                do {
                    int reference = Integer.parseInt(duaDetailCursor.getString(0));
                    boolean fav = duaDetailCursor.getInt(1) == 1;
                    String arabic = duaDetailCursor.getString(2);
                    String translation = duaDetailCursor.getString(3);
                    String book_reference = duaDetailCursor.getString(4);
                    results.add(new Dua(reference, fav, arabic, translation, book_reference));
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