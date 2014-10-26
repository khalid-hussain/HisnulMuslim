package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;
import com.example.khalid.hisnulmuslim.TextViewEx;

import java.util.ArrayList;

//public class DuaListAdapter extends ArrayAdapter<Dua> {
public class DuaDetailAdapter extends com.nhaarman.listviewanimations.ArrayAdapter<Dua> {
    private Context mContext;

    public DuaDetailAdapter(Context context, ArrayList<Dua> items) {
        super(items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.dua_detail_item_card, null);
        }

        Dua p = getItem(position);

        if (p != null) {
            TextView tvDuaNumber = (TextView) v.findViewById(R.id.txtDuaNumber);
            TextView tvDuaArabic = (TextView) v.findViewById(R.id.txtDuaArabic);
            TextViewEx tvDuaTranslation = (TextViewEx) v.findViewById(R.id.txtDuaTranslation);
            TextView tvDuaReference = (TextView) v.findViewById(R.id.txtDuaReference);

            /*Typeface face;
            face = Typeface.createFromAsset(getContext().getAssets(), "DroidNaskh-Regular.ttf");
            tvDuaArabic.setTypeface(face);*/

            if (tvDuaNumber != null) {
                tvDuaNumber.setText("" + p.getReference());
            }
            if (tvDuaArabic != null) {
                tvDuaArabic.setText("" + p.getArabic());
            }
            if (tvDuaTranslation != null) {
                tvDuaTranslation.setText(p.getTranslation().toString(), true);
            }
            if (tvDuaReference != null) {
                tvDuaReference.setText(p.getBook_reference().toString());
            }
        }
        return v;
    }
}