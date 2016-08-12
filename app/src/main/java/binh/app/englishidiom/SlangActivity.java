package binh.app.englishidiom;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import binh.app.englishidiom.database.DatabaseHelper;
import binh.app.englishidiom.database.ParseAdapter;

public class SlangActivity extends Activity {
	List<String> booklist = new ArrayList<String>();
	ListView listviewbook;
	DatabaseHelper dbhHelper;
	EditText edit_search;
	private boolean check = false;
	static List<Integer> listid;
	private List<String> templist = new ArrayList<String>();
	private List<Integer> templistid = new ArrayList<Integer>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slang_layout);
		listviewbook = (ListView) findViewById(R.id.slang_list);
		edit_search=(EditText)findViewById(R.id.edit_search);
		     dbhHelper = Main.dbHeplper;

		booklist = dbhHelper.getallSlang();
		listid=dbhHelper.getslangId();
		if(booklist!=null)
		{
			ParseAdapter adapter=new ParseAdapter(this,R.layout.slang_text_item,booklist);
			listviewbook.setAdapter(adapter);
			listviewbook.setTextFilterEnabled(true);
			listviewbook.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long arg3) {
					// TODO Auto-generated method stub
						SharedPreferences pref = getSharedPreferences("slang",
								MODE_PRIVATE);
						SharedPreferences.Editor editor = pref.edit();

						if (!check) {
							editor.putString("slangtitle", booklist.get(arg2));
							editor.putInt("slang_index", arg2);
						} else {
							editor.putString("slangtitle", templist.get(arg2));
							editor.putInt("slang_index", templistid.get(arg2));
						}
						editor.commit();
						Intent intent = new Intent(getBaseContext(),
								ContentSlangActivity.class);
						startActivity(intent);
				}
			});
			edit_search.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					check = true;
					templist.clear();
					templistid.clear();
					searchapp(s.toString());
				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});
		}


	}
	void searchapp(String newText) {
		for (int i = 0; i < booklist.size(); i++) {
			if (booklist.get(i).toLowerCase().startsWith(newText)) {
				templist.add(booklist.get(i));
				templistid.add(i);
			}

		}
		ParseAdapter adapter2=new ParseAdapter(this,R.layout.slang_text_item,templist);
		listviewbook.setAdapter(adapter2);
		adapter2.notifyDataSetChanged();

	}
}
