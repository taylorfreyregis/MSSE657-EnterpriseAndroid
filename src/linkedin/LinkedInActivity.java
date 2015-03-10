package linkedin;

import messaging.MessagingPagerAdapter;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class LinkedInActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private final static String LOGTAG = "MessagingActivity";
	
	private LinkedInPagerAdapter mLinkedInPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedin);
        
        // Set up the tabs
        mActionBar = getActionBar();
        
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Set up the view pager (must be done before adding tabs)
        mLinkedInPagerAdapter = new LinkedInPagerAdapter(getSupportFragmentManager());
        
        mViewPager = (ViewPager) findViewById(R.id.linkedin_view_pager);
        
        mViewPager.setAdapter(mLinkedInPagerAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        
        
		// Add 2 tabs
		Tab postLi = mActionBar.newTab().setText("Post").setTabListener(this);
		Tab profileLi = mActionBar.newTab().setText("Profile").setTabListener(this);
		Tab contactsLi = mActionBar.newTab().setText("Contacts").setTabListener(this);
        mActionBar.addTab(postLi);
        mActionBar.addTab(profileLi);
        mActionBar.addTab(contactsLi);
        
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// SHOW
        mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// HIDE
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// refresh maybe? probably ignore
		
	}
}
