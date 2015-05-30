package nimbus.keydic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Button btnLeft;
    private Button btnRight;
    private Button btnCenter;
    private TextView txtCombine;
    private TextView txtExample;

    private String wordLeft = "";
    private String wordRight = "";
    private String wordCenter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        setAllListeners();
    }

    private void findAllViews() {
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnCenter = (Button) findViewById(R.id.btn_center);
        txtCombine = (TextView) findViewById(R.id.txt_combine);
        txtExample = (TextView) findViewById(R.id.txt_example);
    }

    DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    };

    DialogInterface.OnClickListener insertCenter = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };

    private void setAllListeners() {
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.controls, null);
                final AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.search);
                textView.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, Word.getAllWords());
                textView.setAdapter(adapter);

                builder.setCancelable(true);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordCenter = textView.getText().toString().trim();
                        btnCenter.setText(wordCenter);
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.setView(v);

                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.controls, null);
                NumberPicker picker = (NumberPicker) v.findViewById(R.id.picker);
                // 피커 클리어
                picker.setMaxValue(0);
                picker.setDisplayedValues(new String[] { " " });
                // 피커 값을 세팅
                String[] values = getPickerValue(0);
                picker.setDisplayedValues(values);
                picker.setMaxValue(values.length - 1);
                // 피커를 리프레쉬
                picker.setValue(0);
                picker.requestLayout();
                picker.clearFocus();
                picker.setVisibility(View.VISIBLE);

                builder.setCancelable(true);
                builder.setPositiveButton(R.string.confirm, click);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setView(v);

                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private String[] getPickerValue(int _direction) {
        ArrayList<Word> words = Word.findWords(wordLeft, wordCenter, wordRight);
        String[] values = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            values[i] = _direction == 0 ? words.get(i).getLeft() : words.get(i).getRight();
        }

        return values;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
