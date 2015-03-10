package messaging;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class MessagingPagerAdapter extends FragmentPagerAdapter {

	public MessagingPagerAdapter(FragmentManager manager) {
		super(manager);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new GoogleCloudMessagingFragment();
		} else if (position == 1) {
			return new CourseServerMessagingFragment();
		}
		return null;
	}
	
}

