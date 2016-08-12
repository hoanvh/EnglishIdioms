package binh.app.englishidiom.database;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import binh.app.englishidiom.R;

public class ParseAdapter extends ArrayAdapter<String> {

	Context context;
	int layoutResourceId;
	List<String> data;

	public ParseAdapter(Context context, int layoutResourceId, List<String> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
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
			holder.text = (TextView) row.findViewById(R.id.text_parse);
			holder.text.setTypeface(type2);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		holder.text.setText(data.get(position));
		return row;
	}

	static class RecordHolder {
		TextView text;
	}
}
