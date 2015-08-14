package com.khalid.hisnulmuslim.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.database.ExternalDbOpenHelper;
import com.khalid.hisnulmuslim.model.Dua;
import com.mikepenz.iconics.view.IconicsButton;

import java.util.List;

/**
 * Created by Khalid on 31 íæáíæ.
 */
public class BookmarksDetailAdapter extends BaseAdapter {
    private static Typeface sCachedTypeface = null;

    private List<Dua> mList;
    private LayoutInflater mInflater;

    private final float prefArabicFontSize;
    private final float prefOtherFontSize;
    private final String prefArabicFontTypeface;

    ExternalDbOpenHelper mDbHelper;

    private String myToolbarTitle;

    public BookmarksDetailAdapter(Context context, List<Dua> items, String toolbarTitle) {
        mInflater = LayoutInflater.from(context);
        mList = items;

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

    public void setData(List<Dua> items) {
        mList = items;
        notifyDataSetChanged();
    }

    public void updateData(List<Dua> items) {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Dua getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder mHolder;
        final Dua p = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dua_detail_item_card, parent, false);

            mHolder = new ViewHolder();
            mHolder.tvDuaNumber = (TextView) convertView.findViewById(R.id.txtDuaNumber);

            mHolder.tvDuaArabic = (TextView) convertView.findViewById(R.id.txtDuaArabic);
            mHolder.tvDuaArabic.setTypeface(sCachedTypeface);
            mHolder.tvDuaArabic.setTextSize(prefArabicFontSize);

            mHolder.tvDuaTranslation = (TextView) convertView.findViewById(R.id.txtDuaTranslation);
            mHolder.tvDuaTranslation.setTextSize(prefOtherFontSize);

            mHolder.tvDuaReference = (TextView) convertView.findViewById(R.id.txtDuaReference);
            mHolder.tvDuaReference.setTextSize(prefOtherFontSize);

            mHolder.shareButton = (IconicsButton) convertView.findViewById(R.id.button_share);
            mHolder.favButton = (IconicsButton) convertView.findViewById(R.id.button_star);


            mHolder.shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View convertView) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,
                            myToolbarTitle + "\n\n" +
                                    mHolder.tvDuaArabic.getText() + "\n\n" +
                                    mHolder.tvDuaTranslation.getText() + "\n\n" +
                                    mHolder.tvDuaReference.getText() + "\n\n" +
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

            final View finalConvertView = convertView;
            mHolder.favButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View ConvertView) {

                    Snackbar.make(finalConvertView,
                            "This doesn't work here yet.",
                            Snackbar.LENGTH_SHORT)
                            .show();

                    /* Work in Progress
                    boolean isFav = !p.getFav();

                    int position = Integer.parseInt(mHolder.tvDuaNumber.getText().toString());
                    position = -1;

                    mList.remove(position);
                    Log.d("KHALID_NUMBER", position + "");
                    Log.d("KHALID_MList", mList.get(position).getTranslation());
                    notifyDataSetChanged();

                    Resources resources = ConvertView.getResources();
                    String snack_begin = resources.getString(R.string.snackbar_text_begin);
                    String snack_end = resources.getString(R.string.snackbar_text_end);
                    String snack_action = resources.getString(R.string.snackbar_action).toUpperCase();

                    // Following snippet taken from:
                    // http://developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
                    mDbHelper = new ExternalDbOpenHelper(finalConvertView.getContext().getApplicationContext());

                    SQLiteDatabase db = mDbHelper.getReadableDatabase();

                    // New value for one column
                    ContentValues values = new ContentValues();
                    values.put(HisnDatabaseInfo.DuaTable.FAV, isFav);

                    // Which row to update, based on the ID
                    String selection = HisnDatabaseInfo.DuaTable.DUA_ID + " LIKE ?";
                    String[] selectionArgs = {String.valueOf(mHolder.tvDuaNumber.getText().toString())};

                    int count = db.update(
                            HisnDatabaseInfo.DuaTable.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);

                    if (count == 1) {
                        if (isFav) {
                            mHolder.favButton.setText("{faw-star}");
                        } else {
                            mHolder.favButton.setText("{faw-star-o}");
                            Snackbar.make(finalConvertView,
                                    snack_begin + p.getReference() + snack_end,
                                    Snackbar.LENGTH_LONG)
                                    .setAction(snack_action, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Snack bar action and animation
                                            Toast.makeText(finalConvertView.getContext(), "Testing", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .show();
                        }
                        p.setFav(isFav);
                    }*/
                }
            });
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        if (p != null) {
            mHolder.tvDuaNumber.setText("" + p.getReference());
            mHolder.tvDuaArabic.setText(Html.fromHtml(p.getArabic()));

            final Spannable translation = new SpannableString(p.getTranslation());
            mHolder.tvDuaTranslation.setText(translation);

            if (p.getBook_reference() != null)
                mHolder.tvDuaReference.setText(Html.fromHtml(p.getBook_reference()));
            else
                mHolder.tvDuaReference.setText("null");

            if (p.getFav()) {
                mHolder.favButton.setText("{faw-star}");
            } else {
                mHolder.favButton.setText("{faw-star-o}");
            }
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView tvDuaNumber;
        TextView tvDuaArabic;
        TextView tvDuaReference;
        TextView tvDuaTranslation;
        IconicsButton shareButton;
        IconicsButton favButton;
    }
}