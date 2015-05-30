package nimbus.keydic;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class BookmarkActivity extends ActionBarActivity {

    ListView listView;
    WordItemAdapter arrayAdapter;
    String[] keyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        SharedPreferences prefs = getSharedPreferences("bookmark", MODE_PRIVATE);
        String result = prefs.getString("bookmark", "0"); //키값, 디폴트값
        keyValue = result.split(",");

        listView = (ListView)findViewById(R.id.lv_bookmark);
        arrayAdapter = new WordItemAdapter();
        listView.setAdapter(arrayAdapter);

        for (String key : keyValue) {
            if (key.equals(""))
                continue;

            Word word = Word.get(Integer.parseInt(key));
            String left = word.getLeft();
            String right = word.getRight();
            String center = word.getCenter();
            String example = word.getExample();
            arrayAdapter.add(left +" " + center +" " + right + "\n" + example);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
