package binh.app.englishidiom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;

import binh.app.englishidiom.database.DatabaseHelper;

public class Main extends Activity {
    public static DatabaseHelper dbHeplper = null;
    TextView txtrate, txtmore;
    ImageView idiom_btn, favor_btn, slang_btn, common_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_main);
        idiom_btn = (ImageView) findViewById(R.id.idiom_btn);
        favor_btn = (ImageView) findViewById(R.id.favor_btn);
        slang_btn = (ImageView) findViewById(R.id.slang);
        common_btn = (ImageView) findViewById(R.id.common_btn);
        txtrate = (TextView) findViewById(R.id.txtrate);
        txtmore = (TextView) findViewById(R.id.txtmore);
        dbHeplper = new DatabaseHelper(this);
        try {
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();

        } catch (IOException e) {
            e.printStackTrace();
        }

        idiom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, ActivityMain.class);

                startActivity(intent);
                overridePendingTransition(R.anim.alpha_anim, R.anim.rotate_anim);


            }
        });
        slang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlangActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.alpha_anim, R.anim.rotate_anim);
            }
        });

        favor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookMarkActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.alpha_anim, R.anim.rotate_anim);
            }
        });
        common_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommonActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.alpha_anim, R.anim.rotate_anim);
            }
        });
        txtmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAboutDialog();

            }
        });
        txtrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="
                                + getApplicationContext()
                                .getPackageName()));
                startActivity(browserIntent);
            }
        });
    }


    private void displayAboutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.about_title));
        builder.setMessage(getString(R.string.about_desc));

        builder.setPositiveButton("More app",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/dev?id=7684745413933237187"));
                        startActivity(browserIntent);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("No Thanks!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }

}
