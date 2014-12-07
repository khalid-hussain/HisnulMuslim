package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.List;

import classes.Dua;
import classes.TextViewEx;

public class DuaDetailAdapter extends ArrayAdapter<Dua> {
    public DuaDetailAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DuaDetailAdapter(Context context, int resource, List<Dua> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.dua_detail_item_card, null);
        }

        Dua p = getItem(position);

        if (p != null) {
            TextView tvDuaNumber = (TextView) v.findViewById(R.id.txtDuaNumber);
            TextView tvDuaArabic = (TextView) v.findViewById(R.id.txtDuaArabic);
            TextViewEx tvDuaTranslation = (TextViewEx) v.findViewById(R.id.txtDuaTranslation);
            TextView tvDuaReference = (TextView) v.findViewById(R.id.txtDuaReference);

            if (tvDuaNumber != null) {
                tvDuaNumber.setText("" + p.getReference());
            }
            if (tvDuaArabic != null) {
                tvDuaArabic.setText("" + p.getArabic());
            }
            if (tvDuaTranslation != null) {
                tvDuaTranslation.setText("" + p.getTranslation());
            }
            if (tvDuaReference != null) {
                tvDuaReference.setText("" + p.getBook_reference());
            }
        }
        return v;
    }
}