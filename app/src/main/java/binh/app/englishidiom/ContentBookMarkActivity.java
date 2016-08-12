package binh.app.englishidiom;


import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import binh.app.englishidiom.database.DatabaseHelper;

public class ContentBookMarkActivity extends Activity {
	DatabaseHelper dbhelper;
	String text;
	static String tg;
	TextView texttile, bookmark;
	TextView txtcopy,txtshare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_bookmark);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				"C8833E8436E08A3863F71E2442FF2B96").build();
		adView.loadAd(adRequest);
		bookmark = (TextView) findViewById(R.id.webbookmark);
		texttile = (TextView) findViewById(R.id.texttitle);
		txtcopy=(TextView)findViewById(R.id.txtcopy);
		txtshare=(TextView)findViewById(R.id.txtshare);
		dbhelper =Main.dbHeplper;

		SharedPreferences pref = getSharedPreferences("bookmark", MODE_PRIVATE);
		tg = pref.getString("booktitle", "");
		text = dbhelper.getAllbookmarkContent();
		texttile.setText(tg);
		bookmark.setText(Html.fromHtml(text));;

		txtcopy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setClipboard(getApplicationContext(),tg);
				Toast.makeText(getApplicationContext(),"Copied to clipboard",Toast.LENGTH_SHORT).show();
			}
		});
		txtshare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/*");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tg);
				startActivity(Intent.createChooser(sharingIntent, "share text to:"));
			}
		});
	}
	private void setClipboard(Context context, String text) {
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
			clipboard.setPrimaryClip(clip);
		}
	}
}
