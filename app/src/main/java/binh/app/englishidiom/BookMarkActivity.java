package binh.app.englishidiom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import binh.app.englishidiom.database.DatabaseHelper;
import binh.app.englishidiom.database.ParseAdapter;

public class BookMarkActivity extends Activity {
	List<String> booklist = new ArrayList<String>();
	List<Integer> bookindex = new ArrayList<Integer>();
	ListView listviewbook;
	DatabaseHelper dbhHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bookmark_layout);
		listviewbook = (ListView) findViewById(R.id.listbookmark);
		dbhHelper = new DatabaseHelper(getApplicationContext());
		try {
			dbhHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		booklist = dbhHelper.getAllbookmark();
		bookindex = dbhHelper.getIdbook();
		ParseAdapter adapter=new ParseAdapter(this,R.layout.bookmark_text_item,booklist);
		listviewbook.setAdapter(adapter);
		listviewbook.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				SharedPreferences pref = getSharedPreferences("bookmark",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putString("booktitle", booklist.get(arg2));
				editor.putInt("bookindex", bookindex.get(arg2));
				editor.commit();
				Intent intent = new Intent(getBaseContext(),
						ContentBookMarkActivity.class);
				startActivity(intent);

			}
		});

	}
}
