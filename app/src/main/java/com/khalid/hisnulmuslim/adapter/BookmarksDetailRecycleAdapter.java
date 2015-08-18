package com.khalid.hisnulmuslim.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.database.ExternalDbOpenHelper;
import com.khalid.hisnulmuslim.model.Dua;
import com.mikepenz.iconics.view.IconicsButton;

import java.util.List;

/**
 * Created by Khalid on 18 أغسطس.
 */
public class BookmarksDetailRecycleAdapter extends RecyclerView.Adapter<BookmarksDetailRecycleAdapter.ViewHolder> {
    private static Typeface sCachedTypeface = null;

    private List<Dua> mDuaData;
    private LayoutInflater mInflater;

    private static float prefArabicFontSize;
    private static float prefOtherFontSize;
    private static String prefArabicFontTypeface;

    ExternalDbOpenHelper mDbHelper;

    private String myToolbarTitle;

    public BookmarksDetailRecycleAdapter(Context context, List<Dua> items) {
        mDuaData = items;
        // notifyDataSetChanged();

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
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookmarksDetailRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dua_detail_item_card, null);

        // create ViewHolder
        ViewHolder mHolder = new ViewHolder(itemLayoutView);
        return mHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        mHolder.tvDuaNumber.setText(mDuaData.get(position).getReference() + "");
        mHolder.tvDuaArabic.setText(Html.fromHtml(mDuaData.get(position).getArabic()));

        final Spannable translation = new SpannableString(mDuaData.get(position).getTranslation());
        mHolder.tvDuaTranslation.setText(translation);

        if (mDuaData.get(position).getBook_reference() != null)
            mHolder.tvDuaReference.setText(Html.fromHtml(mDuaData.get(position).getBook_reference()));
        else
            mHolder.tvDuaReference.setText("null");
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
            tvDuaArabic=(TextView) itemLayoutView.findViewById(R.id.txtDuaArabic);
            tvDuaTranslation=(TextView)itemLayoutView.findViewById(R.id.txtDuaTranslation);
            tvDuaReference=(TextView)itemLayoutView.findViewById(R.id.txtDuaReference);
            shareButton=(IconicsButton)itemLayoutView.findViewById(R.id.button_share);
            favButton=(IconicsButton)itemLayoutView.findViewById(R.id.button_star);

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