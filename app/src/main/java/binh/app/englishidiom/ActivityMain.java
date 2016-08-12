package binh.app.englishidiom;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import binh.app.englishidiom.database.GridTextAdapter;

public class ActivityMain extends Activity {

    ListView listtitle;
    String[] title = {"*", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);

        listtitle = (ListView) findViewById(R.id.title_list);
        GridTextAdapter adapter = new GridTextAdapter(getApplicationContext(),
                R.layout.grid_text, title);
        listtitle.setAdapter(adapter);
        listtitle.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                SharedPreferences pref = getSharedPreferences("binh",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("tieude", title[arg2]);
                editor.putInt("index", arg2);
                editor.commit();
                Intent intent=new Intent(ActivityMain.this,ParseActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_anim,R.anim.rotate_anim);
            }
        });

    }




}
