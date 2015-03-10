package scis;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.taylorcfrey.msse657enterpriseandroid.R;

import java.util.List;

public class ScisProgramAdapter extends BaseAdapter {

	private List<ScisProgram> mPrograms;
	private Context mContext;
	private int mResourceId;
	
	public ScisProgramAdapter(Context context, int resourceId, List<ScisProgram> scisPrograms) {
		this.mContext = context;
		this.mResourceId = resourceId;
		this.mPrograms = scisPrograms;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return mPrograms.size();
	}

	@Override
	public ScisProgram getItem(int position) {
		return mPrograms.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ScisProgramViewHolder scisProgramViewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            scisProgramViewHolder = new ScisProgramViewHolder();

            view = inflater.inflate(mResourceId, parent, false);
            scisProgramViewHolder.title = (TextView) view
                    .findViewById(R.id.text_view_list_item_navigation_drawer);

            view.setTag(scisProgramViewHolder);

        } else {
        	scisProgramViewHolder = (ScisProgramViewHolder) view.getTag();

        }

        ScisProgram scisProgram = (ScisProgram) this.mPrograms.get(position);

        scisProgramViewHolder.title.setText(scisProgram.getTitle());

        return view;
	}

	static class ScisProgramViewHolder {
		TextView title;
	}
}
