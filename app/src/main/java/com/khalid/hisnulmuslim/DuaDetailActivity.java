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
import android.widget.ListView;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.adapter.DuaDetailAdapter;
import com.khalid.hisnulmuslim.loader.DuaDetailsLoader;
import com.khalid.hisnulmuslim.model.Dua;

import java.util.List;

import me.grantland.widget.AutofitTextView;


public class DuaDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dua>> {
    private int duaIdFromDuaListActivity;
    private String duaTitleFromDuaListActivity;
    private DuaDetailAdapter adapter;
    private ListView listView;

    private Toolbar toolbar;
    private TextView my_toolbar_duaGroup_number;
    private AutofitTextView my_autofit_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_detail);

        toolbar = (Toolbar) findViewById(R.id.my_detail_action_bar);
        my_toolbar_duaGroup_number = (TextView) findViewById(R.id.txtReference_duaDetail);
        my_autofit_toolbar_title = (AutofitTextView) findViewById(R.id.dua_detail_autofit_actionbar_title);
        View mToolbarShadow = findViewById(R.id.view_toolbar_shadow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.listView = (ListView) findViewById(R.id.duaDetailListView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        duaIdFromDuaListActivity = bundle.getInt("dua_id");
        duaTitleFromDuaListActivity = bundle.getString("dua_title");

        my_toolbar_duaGroup_number.setText(Integer.toString(duaIdFromDuaListActivity));
        my_autofit_toolbar_title.setText(duaTitleFromDuaListActivity);
        setTitle("");

        if (Build.VERSION.SDK_INT >= 21) {
            mToolbarShadow.setVisibility(View.GONE);
        }

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dua_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Dua>> onCreateLoader(int id, Bundle args) {
        return new DuaDetailsLoader(DuaDetailActivity.this, duaIdFromDuaListActivity);
    }

    @Override
    public void onLoadFinished(Loader<List<Dua>> loader, List<Dua> data) {
        if (adapter == null) {
            adapter = new DuaDetailAdapter(this, data, duaTitleFromDuaListActivity);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dua>> loader) {
        if (adapter != null) {
            adapter.setData(null);
        }
    }
}