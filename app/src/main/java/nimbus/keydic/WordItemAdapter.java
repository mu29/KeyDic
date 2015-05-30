package nimbus.keydic;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by ezbrother on 15. 5. 30..
 */
public class WordItemAdapter extends BaseAdapter {

    private ArrayList<String> list;

    public WordItemAdapter() {
        list = new ArrayList<String>();
    }

    @Override
    public int getCount() {
       return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();
        CustomHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.word_item, viewGroup, false);
            holder = new CustomHolder();
            holder.mTextView = (TextView) view.findViewById(R.id.tv_item);
            holder.mButton = (Button) view.findViewById(R.id.btn_delete);
            view.setTag(holder);
        }
        else {
            holder = (CustomHolder) view.getTag();
        }

        holder.mTextView.setText(list.get(position));
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = context.getSharedPreferences("bookmark", Context.MODE_PRIVATE);
                String bookmark = pref.getString("bookmark", "");
                String[] temp = bookmark.split(",");
                String delete_result = "";

                for (int i = 0; i < temp.length - 1; i++) {
                    if (i == pos) continue;
                    delete_result += temp[i] + ",";
                }
                if (pos != temp.length - 1)
                    delete_result += temp[temp.length - 1];

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("bookmark", delete_result);
                editor.apply();

                list.remove(pos);
                notifyDataSetChanged();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "리스트 클릭 : " + list.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "리스트 롱 클릭 : "+list.get(pos), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }

    public void add(String _msg) {
        list.add(_msg);
    }

    public void remove(int _position) {
        list.remove(_position);
    }

    private class CustomHolder {
        TextView mTextView;
        Button mButton;
    }
}
