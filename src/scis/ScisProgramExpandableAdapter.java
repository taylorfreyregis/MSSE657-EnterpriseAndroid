package scis;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.taylorcfrey.msse657enterpriseandroid.R;

import java.util.List;

public class ScisProgramExpandableAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private List<ScisProgram> mScisPrograms;
    private int mGroupResourceId;
    private int mChildResourceId;

    public ScisProgramExpandableAdapter(Context context, List<ScisProgram> scisPrograms,
                                        int groupResourceId, int childResourceId) {
        this.mContext = context;
        this.mScisPrograms = scisPrograms;
        this.mGroupResourceId = groupResourceId;
        this.mChildResourceId = childResourceId;
    }

    @Override
    public int getGroupCount() {
        return mScisPrograms.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mScisPrograms.get(groupPosition).getScisCourses().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mScisPrograms.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mScisPrograms.get(groupPosition).getScisCourses().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ScisProgramViewHolder scisProgramViewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            scisProgramViewHolder = new ScisProgramViewHolder();

            view = inflater.inflate(mGroupResourceId, parent, false);
            scisProgramViewHolder.title = (TextView) view
                    .findViewById(R.id.text_view_list_group_scis_program);

            view.setTag(scisProgramViewHolder);

        } else {
        	scisProgramViewHolder = (ScisProgramViewHolder) view.getTag();

        }

        ScisProgram scisProgram = (ScisProgram) this.getGroup(groupPosition);

        scisProgramViewHolder.title.setText(scisProgram.getTitle());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {
        ScisCourseViewHolder scisCourseViewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            scisCourseViewHolder = new ScisCourseViewHolder();

            view = inflater.inflate(mChildResourceId, parent, false);
            scisCourseViewHolder.title = (TextView) view
                    .findViewById(R.id.text_view_list_item_scis_course);

            view.setTag(scisCourseViewHolder);

        } else {
            scisCourseViewHolder = (ScisCourseViewHolder) view.getTag();

        }

        ScisCourse scisCourse = (ScisCourse) this.getChild(groupPosition, childPosition);

        scisCourseViewHolder.title.setText(scisCourse.getTitle());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ScisProgramViewHolder {
		TextView title;
	}

    static class ScisCourseViewHolder {
		TextView title;
	}

}
