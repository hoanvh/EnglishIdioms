package binh.app.englishidiom;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

import binh.app.englishidiom.database.DatabaseHelper;

public class ContentSlangActivity extends Activity {
    DatabaseHelper dbhelper;
    static int tg;
    TextView texttile, define, example;
    static boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_slang);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(
                "C8833E8436E08A3863F71E2442FF2B96").build();
        adView.loadAd(adRequest);
        example = (TextView) findViewById(R.id.example);
        texttile = (TextView) findViewById(R.id.texttitle);
        define = (TextView) findViewById(R.id.define);
        dbhelper = new DatabaseHelper(getApplicationContext());
        try {
            dbhelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences pref = getSharedPreferences("slang", MODE_PRIVATE);
        tg = pref.getInt("slang_index", 0);
        String st=pref.getString("slangtitle","");
        texttile.setText(st);
        define.setText(dbhelper.getdefWithID());
        example.setText(dbhelper.getlangWithID());
    }

}
