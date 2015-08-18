package com.khalid.hisnulmuslim.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    // Taken from the BaseAdapter version
    private static Typeface sCachedTypeface = null;

    private List<Dua> mList;
    private LayoutInflater mInflater;

    /*private final float prefArabicFontSize;
    private final float prefOtherFontSize;
    private final String prefArabicFontTypeface;*/

    ExternalDbOpenHelper mDbHelper;

    private String myToolbarTitle;
    // End

    // private ItemData[] itemsData;

    public BookmarksDetailRecycleAdapter(List<Dua> items) {
        mList = items;
        // notifyDataSetChanged();
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

        // mHolder.txtViewTitle.setText(itemsData[position].getTitle());
        // mHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());

        mHolder.tvDuaNumber.setText(mList.get(position).getReference() + "");
        mHolder.tvDuaArabic.setText(Html.fromHtml(mList.get(position).getArabic()));

        final Spannable translation = new SpannableString(mList.get(position).getTranslation());
        mHolder.tvDuaTranslation.setText(translation);

        if (mList.get(position).getBook_reference() != null)
            mHolder.tvDuaReference.setText(Html.fromHtml(mList.get(position).getBook_reference()));
        else
            mHolder.tvDuaReference.setText("null");
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public TextView tvDuaNumber;
        public TextView tvDuaArabic;
        public TextView tvDuaTranslation;
        public TextView tvDuaReference;
        public IconicsButton shareButton;
        public IconicsButton favButton;

        // tvDuaArabic.setTypeface(sCachedTypeface);
        // tvDuaArabic.setTextSize(prefArabicFontSize);
        // tvDuaTranslation.setTextSize(prefOtherFontSize);
        // tvDuaReference.setTextSize(prefOtherFontSize);

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvDuaNumber = (TextView) itemLayoutView.findViewById(R.id.txtDuaNumber);
            tvDuaArabic=(TextView) itemLayoutView.findViewById(R.id.txtDuaArabic);
            tvDuaTranslation=(TextView)itemLayoutView.findViewById(R.id.txtDuaTranslation);
            tvDuaReference=(TextView)itemLayoutView.findViewById(R.id.txtDuaReference);
            shareButton=(IconicsButton)itemLayoutView.findViewById(R.id.button_share);
            favButton=(IconicsButton)itemLayoutView.findViewById(R.id.button_star);
            /*txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);*/
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}