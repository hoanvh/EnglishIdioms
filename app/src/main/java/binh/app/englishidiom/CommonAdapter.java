package binh.app.englishidiom;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommonAdapter extends ArrayAdapter<String> {

	Context context;
	int layoutResourceId;
	String[] data;
	String [] content;

	public CommonAdapter(Context context, int layoutResourceId, String[] data,String[] content) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.content=content;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			Typeface type2 = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
			holder.text = (TextView) row.findViewById(R.id.cm_title);
			holder.text_content=(TextView)row.findViewById(R.id.cm_content);
			holder.text.setTypeface(type2);
			holder.text_content.setTypeface(type2);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		holder.text.setText(data[position]);
		holder.text_content.setText(content[position]);
		return row;
	}

	static class RecordHolder {
		TextView text;
		TextView text_content;
	}
}
