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

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import java.util.ArrayList;

import adapters.Dua;
import adapters.DuaListAdapter;
import database.ExternalDbOpenHelper;

//import adapters.Dua;

public class DuaList extends ActionBarActivity {
    private static final String DB_NAME = "hisnul.sqlite3";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "dua_group";
    private static final String GROUP_ID = "_id";
    private static final String GROUP_TITLE = "en_title";

    private SQLiteDatabase database;
    private ArrayList duas;

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_list);

        //Our key helper
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();

        //That’s it, the database is open!
        fillDua();
        setUpDuaList();
    }

    public void fillDua(){
        //Extracting Elements from Database
        duas = new ArrayList<Dua>();
        Cursor friendCursor = database.query(TABLE_NAME, new String[] {GROUP_ID,
                GROUP_TITLE}, null, null, null, null, GROUP_ID);
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                String reference = friendCursor.getString(0);
                String name = friendCursor.getString(1);
                duas.add(new Dua(Integer.parseInt(reference), name));
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
    }

    public void setUpDuaList(){
        final ListView listView;
        listView = (ListView) findViewById(R.id.duaListView);

        DuaListAdapter customAdapter;
        customAdapter = new DuaListAdapter(this, duas);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter =
                new SwingBottomInAnimationAdapter(customAdapter);

        // Assign the ListView to the AnimationAdapter and vice versa
        swingBottomInAnimationAdapter.setAbsListView(listView);
        listView.setAdapter(swingBottomInAnimationAdapter);

        //Let’s set a message shown upon tapping an item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Clicked Number " + (position+1) , Toast.LENGTH_SHORT)
                        .show();

                Intent intent = new Intent(getBaseContext(), DuaDetail.class);
                intent.putExtra("dua_id", position+1 +"");
                String dua_title = ((Dua)duas.get(position)).getTitle();
                intent.putExtra("dua_title", dua_title);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        else if (id == R.id.action_bookmarks){
            Toast.makeText(this, "Bookmarks Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}