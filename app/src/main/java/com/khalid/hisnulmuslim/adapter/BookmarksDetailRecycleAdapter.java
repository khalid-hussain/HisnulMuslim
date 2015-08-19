package com.khalid.hisnulmuslim.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.database.ExternalDbOpenHelper;
import com.khalid.hisnulmuslim.database.HisnDatabaseInfo;
import com.khalid.hisnulmuslim.model.Dua;
import com.mikepenz.iconics.view.IconicsButton;

import java.util.List;

/**
 * Created by Khalid on 18 أغسطس.
 */

// Class blueprint taken from: http://hmkcode.com/android-simple-recyclerview-widget-example/
public class BookmarksDetailRecycleAdapter extends RecyclerView.Adapter<BookmarksDetailRecycleAdapter.ViewHolder> {
    private static Typeface sCachedTypeface = null;

    private List<Dua> mDuaData;
    private LayoutInflater mInflater;
    private ViewHolder mHolder;

    private static float prefArabicFontSize;
    private static float prefOtherFontSize;
    private static String prefArabicFontTypeface;

    private static ExternalDbOpenHelper mDbHelper;

    private static String myToolbarTitle;

    public BookmarksDetailRecycleAdapter(Context context, List<Dua> items, String toolbarTitle) {
        mDuaData = items;

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        prefArabicFontTypeface =
                sharedPreferences.getString(
                        context.getResources().getString(R.string.pref_font_arabic_typeface),
                        context.getString(R.string.pref_font_arabic_typeface_default));
        prefArabicFontSize =
                sharedPreferences.getInt(
                        context.getResources().getString(R.string.pref_font_arabic_size),
                        context.getResources().getInteger(R.integer.pref_font_arabic_size_default));
        prefOtherFontSize =
                sharedPreferences.getInt(
                        context.getResources().getString(R.string.pref_font_other_size),
                        context.getResources().getInteger(R.integer.pref_font_other_size_default));

        if (sCachedTypeface == null) {
            sCachedTypeface = Typeface.createFromAsset(
                    context.getAssets(), prefArabicFontTypeface);
        }

        myToolbarTitle = toolbarTitle;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookmarksDetailRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dua_detail_item_card, null);

        // create ViewHolder
        mHolder = new ViewHolder(itemLayoutView);
        return mHolder;
    }

    public void deleteRow(int position){
        mDuaData.remove(position); // this will remove row of data
        notifyItemRemoved(position); // this will do the animation of removal
        /*runOnUiThread(new Runnable() {
            notifyDataSetChanged();
        });*/
        dataSetChanged();
    }

    @UiThread
    protected void dataSetChanged() {
        notifyDataSetChanged();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position){//final RecyclerView rv) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        final int finalPosition = position;

        mHolder.tvDuaNumber.setText(mDuaData.get(position).getReference() + "");
        mHolder.tvDuaArabic.setText(Html.fromHtml(mDuaData.get(position).getArabic()));

        final Spannable translation = new SpannableString(mDuaData.get(position).getTranslation());
        mHolder.tvDuaTranslation.setText(translation);

        if (mDuaData.get(position).getBook_reference() != null)
            mHolder.tvDuaReference.setText(Html.fromHtml(mDuaData.get(position).getBook_reference()));
        else
            mHolder.tvDuaReference.setText("null");

        if (mDuaData.get(position).getFav()) {
            mHolder.favButton.setText("{faw-star}");
        } else {
            mHolder.favButton.setText("{faw-star-o}");
        }

        final ViewHolder finalmHolder = mHolder;

        mHolder.shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View convertView) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        myToolbarTitle + "\n\n" +
                                finalmHolder.tvDuaArabic.getText() + "\n\n" +
                                finalmHolder.tvDuaTranslation.getText() + "\n\n" +
                                finalmHolder.tvDuaReference.getText() + "\n\n" +
                                convertView.getResources().getString(R.string.action_share_credit)
                );
                intent.setType("text/plain");
                convertView.getContext().startActivity(
                        Intent.createChooser(
                                intent,
                                convertView.getResources().getString(R.string.action_share_title)
                        )
                );
            }
        });

        finalmHolder.favButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // boolean isFav = !p.getFav();
                boolean isFav = !mDuaData.get(finalPosition).getFav();

                int sql_position = Integer.parseInt(finalmHolder.tvDuaNumber.getText().toString());
                sql_position -= 1;

                Log.d("KHALID_NUMBER", sql_position + "");
                // Log.d("KHALID_isFav", isFav + "");

                deleteRow(finalPosition);

                Resources resources = v.getResources();
                String snack_begin = resources.getString(R.string.snackbar_text_begin);
                String snack_end = resources.getString(R.string.snackbar_text_end);
                String snack_action = resources.getString(R.string.snackbar_action).toUpperCase();

                // Following snippet taken from:
                // http://developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
                mDbHelper = new ExternalDbOpenHelper(v.getContext().getApplicationContext());

                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(HisnDatabaseInfo.DuaTable.FAV, isFav);

                // Which row to update, based on the ID
                String selection = HisnDatabaseInfo.DuaTable.DUA_ID + " LIKE ?";
                String[] selectionArgs = {String.valueOf(finalmHolder.tvDuaNumber.getText().toString())};

                int count = db.update(
                        HisnDatabaseInfo.DuaTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                if (count == 1) {
                    if (isFav) {
                        finalmHolder.favButton.setText("{faw-star}");
                    } else {
                        finalmHolder.favButton.setText("{faw-star-o}");
                        /*Snackbar.make(rv,
                                // snack_begin + p.getReference() + snack_end,
                                snack_begin + snack_end,
                                Snackbar.LENGTH_LONG)
                                .setAction(snack_action, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Snack bar action and animation
                                        // Toast.makeText(finalConvertView.getContext(), "Testing", Toast.LENGTH_LONG).show();
                                        Toast.makeText(rv.getContext(), "Testing", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .show();*/
                    }
                    // mDuaData.get(finalPosition).setFav(isFav);
                }
                if (getItemCount() == 0) {
                    // I don't even know if this block is needed. Review once you have some sleep.
                }
            }
        });
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDuaNumber;
        public TextView tvDuaArabic;
        public TextView tvDuaTranslation;
        public TextView tvDuaReference;
        public IconicsButton shareButton;
        public IconicsButton favButton;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvDuaNumber = (TextView) itemLayoutView.findViewById(R.id.txtDuaNumber);
            tvDuaArabic = (TextView) itemLayoutView.findViewById(R.id.txtDuaArabic);
            tvDuaTranslation = (TextView) itemLayoutView.findViewById(R.id.txtDuaTranslation);
            tvDuaReference = (TextView) itemLayoutView.findViewById(R.id.txtDuaReference);
            shareButton = (IconicsButton) itemLayoutView.findViewById(R.id.button_share);
            favButton = (IconicsButton) itemLayoutView.findViewById(R.id.button_star);

            tvDuaArabic.setTypeface(sCachedTypeface);
            tvDuaArabic.setTextSize(prefArabicFontSize);
            tvDuaTranslation.setTextSize(prefOtherFontSize);
            tvDuaReference.setTextSize(prefOtherFontSize);
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDuaData == null ? 0 : mDuaData.size();
    }
}