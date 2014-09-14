package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.ArrayList;

//public class DuaListAdapter extends ArrayAdapter<Dua> {
public class DuaListAdapter extends com.nhaarman.listviewanimations.ArrayAdapter<Dua> {
    private Context mContext;

    public DuaListAdapter(Context context, ArrayList<Dua> items) {
        super(items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.dua_list_item_card, null);
        }

        Dua p = getItem(position);

        if (p != null) {
            TextView tvReference = (TextView) v.findViewById(R.id.txtReference);
            TextView tvDuaName = (TextView) v.findViewById(R.id.txtDuaName);

            if (tvReference != null) {
                tvReference.setText("" + p.getReference());
            }
            if (tvDuaName != null) {
                tvDuaName.setText(p.getTitle().toString());
            }
        }
        return v;
    }
}