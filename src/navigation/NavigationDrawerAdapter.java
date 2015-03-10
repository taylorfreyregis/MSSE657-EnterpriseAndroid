package navigation;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.taylorcfrey.msse657enterpriseandroid.R;

import java.util.ArrayList;

import navigation.NavigationItem;

public class NavigationDrawerAdapter extends BaseAdapter {

	private ArrayList<NavigationItem> mNavigationItems;
	private Context mContext;
	private int mResourceId;
	
	public NavigationDrawerAdapter(Context context, int resourceId, ArrayList<NavigationItem> navigationItems) {
		this.mContext = context;
		this.mResourceId = resourceId;
		this.mNavigationItems = navigationItems;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return mNavigationItems.size();
	}

	@Override
	public NavigationItem getItem(int position) {
		return mNavigationItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NavigationItemViewHolder naviationItemViewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            naviationItemViewHolder = new NavigationItemViewHolder();

            view = inflater.inflate(mResourceId, parent, false);
            naviationItemViewHolder.title = (TextView) view
                    .findViewById(R.id.text_view_list_item_navigation_drawer);

            view.setTag(naviationItemViewHolder);

        } else {
            naviationItemViewHolder = (NavigationItemViewHolder) view.getTag();

        }

        NavigationItem navigationItem = (NavigationItem) this.mNavigationItems.get(position);

        naviationItemViewHolder.title.setText(navigationItem.getTitle());

        return view;
	}

	static class NavigationItemViewHolder {
		TextView title;
	}
}
