package binh.app.englishidiom;

import java.util.ArrayList;
import java.util.List;

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
import binh.app.englishidiom.database.DatabaseHelper;
import binh.app.englishidiom.database.ParseAdapter;

public class ParseActivity extends Activity {
	DatabaseHelper dbHeplper;
	ParseAdapter adapter;
	ListView idiomtitle;
	static List<String> listUsers;
	static List<Integer> listid;
	private List<String> templist = new ArrayList<String>();
	private List<Integer> templistid = new ArrayList<Integer>();

	private boolean check = false;
	EditText edit_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parse_activity);
		dbHeplper = Main.dbHeplper;
		idiomtitle = (ListView) findViewById(R.id.listparse);

		edit_search=(EditText)findViewById(R.id.edit_search);
		listUsers = dbHeplper.getAllUsers();
		listid = dbHeplper.getId();
		if (listUsers != null) {
			adapter = new ParseAdapter(this,R.layout.parse_text_item,listUsers);
			idiomtitle.setAdapter(adapter);
			idiomtitle.setTextFilterEnabled(true);
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
			idiomtitle.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					SharedPreferences pre = getSharedPreferences("idiom",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = pre.edit();
					if (!check) {
						editor.putString("chiso", listUsers.get(arg2));
						editor.putInt("chiso2", listid.get(arg2));
					} else {
						editor.putString("chiso", templist.get(arg2));
						editor.putInt("chiso2", templistid.get(arg2));
					}
					editor.commit();
					Intent intent = new Intent(getBaseContext(),
							ContentActivity.class);
					startActivity(intent);

				}
			});
		}

	}



	void searchapp(String newText) {
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).toLowerCase().startsWith(newText)) {
				templist.add(listUsers.get(i));
				templistid.add(listid.get(i));
			}

		}
		ParseAdapter adapter2=new ParseAdapter(this,R.layout.parse_text_item,templist);
		idiomtitle.setAdapter(adapter2);
		adapter2.notifyDataSetChanged();

	}
}
