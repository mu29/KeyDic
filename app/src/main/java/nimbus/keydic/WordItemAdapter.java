package nimbus.keydic;

import android.content.Context;
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

    private ArrayList<String> m_List;

    public WordItemAdapter() {
        m_List = new ArrayList<String>();
    }
    @Override
    public int getCount() {
       return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();
        if ( view == null ) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.word_item, viewGroup, false);

            TextView text = (TextView) view.findViewById(R.id.tv_item);
            text.setText(m_List.get(position));

            Button btn = (Button) view.findViewById(R.id.bt_delete);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(context, m_List.get(pos) +"\n"+"Delete", Toast.LENGTH_SHORT).show();
                     m_List.remove(pos);
                }
            });


            // 리스트 아이템을 터치 했을 때 이벤트 발생
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "리스트 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                }
            });

            // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
            view.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "리스트 롱 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        return view;
    }
    public void add(String _msg) {
        m_List.add(_msg);
    }
    public void remove(int _position) {
        m_List.remove(_position);
    }
}
