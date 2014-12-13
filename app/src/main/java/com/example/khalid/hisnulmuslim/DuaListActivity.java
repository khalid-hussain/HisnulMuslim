package com.example.khalid.hisnulmuslim;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import classes.Dua;
import adapters.DuaGroupAdapter;
import database.ExternalDbOpenHelper;
import database.mySqliteDatabase;

public class DuaListActivity extends ActionBarActivity {
    private SQLiteDatabase database;
    private ArrayList ArrayListDuas = new ArrayList<Dua>();

    private mySqliteDatabase myDB = new mySqliteDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_list);

        ExternalDbOpenHelper dbOpenHelper;
        dbOpenHelper = new ExternalDbOpenHelper(this, myDB.DB_NAME);
        database = dbOpenHelper.openDataBase();

        fromDBtoArrayList(ArrayListDuas);
        fromArrayListToListView();
    }

    public void fromDBtoArrayList(ArrayList<Dua> ArrayListDuas) {
        Cursor duaGroupCursor;
        duaGroupCursor = database.query(myDB.TABLE_DUA_GROUP,
                new String[]{myDB.TABLE_DUA_GROUP_ID,
                        myDB.TABLE_DUA_GROUP_TITLE},
                null,
                null,
                null,
                null,
                myDB.TABLE_DUA_GROUP_ID);

        duaGroupCursor.moveToFirst();

        if (!duaGroupCursor.isAfterLast()) {
            do {
                String dua_group_id = duaGroupCursor.getString(0);
                String dua_group_title = duaGroupCursor.getString(1);
                ArrayListDuas.add(new Dua(Integer.parseInt(dua_group_id), dua_group_title));
            } while (duaGroupCursor.moveToNext());
        }
        duaGroupCursor.close();
    }

    public void fromArrayListToListView() {
        final ListView listView;
        listView = (ListView) findViewById(R.id.duaListView);

        DuaGroupAdapter mAdapter;
        mAdapter = new DuaGroupAdapter(this,
                R.layout.dua_list_item_card,
                ArrayListDuas);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getBaseContext(),
                        DuaDetailActivity.class);

                String dua_id = String.valueOf(position + 1);
                String dua_title = ((Dua) ArrayListDuas.get(position)).getTitle();

                intent.putExtra("dua_id", dua_id);
                intent.putExtra("dua_title", dua_title);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_dualist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_bookmarks) {
            Toast.makeText(this, "Bookmarks Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}