package com.khalid.hisnulmuslim;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

public class AboutActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.my_action_bar);
        View mToolbarShadow = findViewById(R.id.view_toolbar_shadow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvAboutUs = (TextView) findViewById(R.id.tvAboutUs);
        // DocumentView tvAboutUs = (DocumentView) findViewById(R.id.tvAboutUs);
        tvAboutUs.setVerticalScrollBarEnabled(true);
        tvAboutUs.setText(Html.fromHtml(getString(R.string.about_us)));
        tvAboutUs.setMovementMethod(LinkMovementMethod.getInstance());

        if (Build.VERSION.SDK_INT >= 21) {
            mToolbarShadow.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}