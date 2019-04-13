package com.khalid.hisnulmuslim;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.LoaderManager;
import androidx.core.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.adapter.BookmarksGroupAdapter;
import com.khalid.hisnulmuslim.loader.BookmarkGroupLoader;
import com.khalid.hisnulmuslim.model.Dua;

import java.util.List;


public class BookmarksGroupActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Dua>> {
    private BookmarksGroupAdapter mAdapter;
    private ListView mListView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_group);

        toolbar = (Toolbar) findViewById(R.id.my_action_bar);
        View mToolbarShadow = findViewById(R.id.view_toolbar_shadow);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.bookmarksDuaListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getBaseContext(),
                        BookmarksDetailActivity.class);

                Dua SelectedDua = (Dua) parent.getAdapter().getItem(position);
                int dua_id = SelectedDua.getReference();
                String dua_title = SelectedDua.getTitle();

                intent.putExtra("dua_id", dua_id);
                intent.putExtra("dua_title", dua_title);

                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.my_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= 21) {
            mToolbarShadow.setVisibility(View.GONE);
        }

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmarks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Dua>> onCreateLoader(int id, Bundle args) {
        return new BookmarkGroupLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Dua>> loader, List<Dua> data) {
        if (mAdapter == null) {
            mAdapter = new BookmarksGroupAdapter(this, data);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dua>> loader) {
        mAdapter.setData(null);
    }
}