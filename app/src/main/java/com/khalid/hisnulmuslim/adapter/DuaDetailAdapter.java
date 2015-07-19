package com.khalid.hisnulmuslim.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.model.Dua;

import java.util.List;

public class DuaDetailAdapter extends BaseAdapter {
    private static Typeface sCachedTypeface = null;

    private List<Dua> mList;
    private LayoutInflater mInflater;

    private final float prefArabicFontSize;
    private final float prefOtherFontSize;
    private final String prefArabicFontTypeface;

    public DuaDetailAdapter(Context context, List<Dua> items) {
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
    }

    public void setData(List<Dua> items) {
        mList = items;
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dua_detail_item_card, parent, false);

            holder = new ViewHolder();
            holder.tvDuaNumber = (TextView) convertView.findViewById(R.id.txtDuaNumber);

            holder.tvDuaArabic = (TextView) convertView.findViewById(R.id.txtDuaArabic);
            holder.tvDuaArabic.setTypeface(sCachedTypeface);
            holder.tvDuaArabic.setTextSize(prefArabicFontSize);

            holder.tvDuaTranslation = (TextView) convertView.findViewById(R.id.txtDuaTranslation);
            holder.tvDuaTranslation.setTextSize(prefOtherFontSize);

            holder.tvDuaReference = (TextView) convertView.findViewById(R.id.txtDuaReference);
            holder.tvDuaReference.setTextSize(prefOtherFontSize);

            holder.shareButton = (ImageButton) convertView.findViewById(R.id.button_share);
            final ViewHolder finalHolder = holder;
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View convertView) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,
                            finalHolder.tvDuaArabic.getText() + "\n\n" +
                                    finalHolder.tvDuaTranslation.getText() + "\n\n" +
                                    finalHolder.tvDuaReference.getText() + "\n\n" +
                                    convertView.getResources().getString(R.string.action_share_credit)
                    );
                    intent.setType("text/plain");
                    convertView.getContext().startActivity(
                            Intent.createChooser(
                                    intent,
                                    convertView.getResources().getString(R.string.action_share_title)
                            )
                    );
                    Toast.makeText(convertView.getContext(), finalHolder.tvDuaTranslation.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        Dua p = getItem(position);
        if (p != null) {
            holder.tvDuaNumber.setText("" + p.getReference());
            holder.tvDuaArabic.setText(Html.fromHtml(p.getArabic()));

            final Spannable translation = new SpannableString(p.getTranslation());
            holder.tvDuaTranslation.setText(translation);

            if (p.getBook_reference() != null)
                holder.tvDuaReference.setText(Html.fromHtml(p.getBook_reference()));
            else
                holder.tvDuaReference.setText("null");
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tvDuaNumber;
        TextView tvDuaArabic;
        TextView tvDuaReference;
        TextView tvDuaTranslation;

        ImageButton shareButton;
        // DocumentView tvDuaTranslation;
    }
}