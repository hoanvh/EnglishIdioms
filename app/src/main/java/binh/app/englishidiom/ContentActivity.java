package binh.app.englishidiom;

import java.io.IOException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import binh.app.englishidiom.database.DatabaseHelper;

public class ContentActivity extends Activity {
	DatabaseHelper dbhelper;
	int currentapiVersion;
	String text;
	static String tg;
	TextView texttile, bookmark,idiom_content;
	TextView txtcopy,txtshare;
	boolean ischecked = false;
	private int test = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		currentapiVersion = Build.VERSION.SDK_INT;
		setContentView(R.layout.content_layout);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				"C8833E8436E08A3863F71E2442FF2B96").build();
		adView.loadAd(adRequest);
		idiom_content=(TextView)findViewById(R.id.idiom_content);
		texttile = (TextView) findViewById(R.id.texttitle);
		bookmark = (TextView) findViewById(R.id.txtstar);
		txtcopy=(TextView)findViewById(R.id.txtcopy);
		txtshare=(TextView)findViewById(R.id.txtshare);
		dbhelper = new DatabaseHelper(getApplicationContext());
		try {
			dbhelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SharedPreferences pref = getSharedPreferences("idiom", MODE_PRIVATE);
		tg = pref.getString("chiso", "");
		SharedPreferences pref2 = getSharedPreferences("idiom", MODE_PRIVATE);
		test = pref2.getInt("chiso2", 0);
		text = dbhelper.getAllContent();

		ischecked = dbhelper.check(test);
		if (!ischecked) {
			bookmark.setBackgroundResource(R.drawable.fa_off);

		} else {
			bookmark.setBackgroundResource(R.drawable.fa_on);
		}
		texttile.setText(tg);

		idiom_content.setText(Html.fromHtml(text));
		
		bookmark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!ischecked) {
					Toast.makeText(getBaseContext(), "Added to bookmark",
							Toast.LENGTH_SHORT).show();
					bookmark.setBackgroundResource(R.drawable.fa_on);
					ischecked = true;
					dbhelper.addbookmark(test);
				} else {
					Toast.makeText(getBaseContext(), "Removed from bookmark",
							Toast.LENGTH_SHORT).show();
					bookmark.setBackgroundResource(R.drawable.fa_off);

					ischecked = false;
					dbhelper.removebookmark(test);

				}

			}

		});
		txtcopy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				setClipboard(getApplicationContext(),tg);
				Toast.makeText(getApplicationContext(),"Copied to clipboard",Toast.LENGTH_SHORT).show();
			}
		});
		txtshare.setOnClickListener(new OnClickListener() {
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (!ischecked) {
			bookmark.setBackgroundResource(R.drawable.fa_off);

		} else {
			bookmark.setBackgroundResource(R.drawable.fa_on);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (!ischecked) {
			bookmark.setBackgroundResource(R.drawable.fa_off);

		} else {
			bookmark.setBackgroundResource(R.drawable.fa_on);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!ischecked) {
			bookmark.setBackgroundResource(R.drawable.fa_off);

		} else {
			bookmark.setBackgroundResource(R.drawable.fa_on);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}
	private void setClipboard(Context context,String text) {
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
