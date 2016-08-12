package binh.app.englishidiom;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;



public class CommonActivity extends Activity {
	String [] common_title,common_detail;
	ListView cm_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common);
		common_title=getResources().getStringArray(R.array.subject);
		common_detail=getResources().getStringArray(R.array.content);
		cm_list=(ListView)findViewById(R.id.list_common);
		CommonAdapter adapter=new CommonAdapter(this,R.layout.common_text_item,common_title,common_detail);

		cm_list.setAdapter(adapter);

	}
}
