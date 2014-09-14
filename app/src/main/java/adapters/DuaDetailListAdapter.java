/*package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.List;

public class DuaDetailListAdapter extends ArrayAdapter<DuaDetail> {
    private Context mContext;

    public DuaDetailListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DuaDetailListAdapter(Context context, int resource, List<DuaDetail> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.dua_detail_list_item_card, null);
        }

        DuaDetail p = getItem(position);

        if (p != null) {
            TextView tvTxtDuaDetail = (TextView) v.findViewById(R.id.txtDuaDetail);

            if (tvTxtDuaDetail != null) {
                tvTxtDuaDetail.setText("" + p.getReference());
            }
        }
        return v;
    }
}
*/