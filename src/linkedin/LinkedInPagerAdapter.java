package linkedin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class LinkedInPagerAdapter extends FragmentPagerAdapter {

	public LinkedInPagerAdapter(FragmentManager manager) {
		super(manager);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new LinkedInPostFragment();
		} else if (position == 1) {
			return new LinkedInProfileFragment();
		} else if (position == 2) {
			return new LinkedInContactsFragment();
		}
		return null;
	}
	
}

