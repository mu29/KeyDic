package nimbus.keydic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Button btnLeft;
    private Button btnRight;
    private Button btnCenter;
    private Button btnBookMark;
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
        btnBookMark = (Button) findViewById(R.id.btn_bookmark);
        txtCombine = (TextView) findViewById(R.id.tv_combine);
        txtExample = (TextView) findViewById(R.id.tv_example);
    }

    private void setAllListeners() {
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 뷰 가져오기
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.controls, null);
                final AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.search);
                textView.setVisibility(View.VISIBLE);
                // 자동 완성 텍스트 리스트
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line, Word.getAllWords());
                textView.setAdapter(adapter);

                // 다이얼로그 설정
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordCenter = textView.getText().toString().trim();
                        btnCenter.setText(wordCenter);
                        wordLeft = "";
                        btnLeft.setText(wordLeft);
                        wordRight = "";
                        btnRight.setText(wordRight);
                        search();
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.setView(v);

                // 다이얼로그 생성
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCenterEmpty())
                    return;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 뷰 가져오기
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.controls, null);
                final NumberPicker picker = (NumberPicker) v.findViewById(R.id.picker);
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

                // 다이얼로그 설정
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordLeft = picker.getDisplayedValues()[picker.getValue()];
                        btnLeft.setText(wordLeft);
                        search();
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.setView(v);

                // 다이얼로그 생성
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCenterEmpty())
                    return;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 뷰 가져오기
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.controls, null);
                final NumberPicker picker = (NumberPicker) v.findViewById(R.id.picker);
                // 피커 클리어
                picker.setMaxValue(0);
                picker.setDisplayedValues(new String[] { " " });
                // 피커 값을 세팅
                String[] values = getPickerValue(1);
                picker.setDisplayedValues(values);
                picker.setMaxValue(values.length - 1);
                // 피커를 리프레쉬
                picker.setValue(0);
                picker.requestLayout();
                picker.clearFocus();
                picker.setVisibility(View.VISIBLE);

                // 다이얼로그 설정
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordRight = picker.getDisplayedValues()[picker.getValue()];
                        btnRight.setText(wordRight);
                        search();
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.setView(v);

                // 다이얼로그 생성
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        btnBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("bookmark", MODE_PRIVATE);
                String bookmark = pref.getString("bookmark", "");
                int no = Word.getNo(wordLeft, wordCenter, wordRight);
                if (no != -1) bookmark += (bookmark.equals("") ? "" : ",") + no;
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("bookmark", bookmark);
                editor.apply();

                Toast.makeText(getApplicationContext(), "북마크에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isCenterEmpty() {
        if (wordCenter.equals("")) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("중앙 텍스트를 설정해주세요.");
            dialog.setNegativeButton("확인", null);
            dialog.show();
            return true;
        }

        return false;
    }

    private void search() {
        Word result = Word.search(wordLeft, wordCenter, wordRight);
        if (result != null) {
            txtCombine.setText(wordLeft + " " + wordCenter + " " + wordRight);
            txtExample.setText(result.getExample());
        }
    }

    private String[] getPickerValue(int _direction) {
        ArrayList<Word> words = Word.findWords(_direction == 0 ? "" : wordLeft, wordCenter, _direction == 0 ? wordRight : "");
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
        if (id == R.id.action_bookmark) {
            Intent intent = new Intent(getApplicationContext(), BookmarkActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
