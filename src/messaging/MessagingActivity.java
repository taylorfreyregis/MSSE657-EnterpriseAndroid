package messaging;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class MessagingActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private final static String LOGTAG = "MessagingActivity";
	
	private MessagingPagerAdapter mMessagingPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        
        // Set up the tabs
        mActionBar = getActionBar();
        
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Set up the view pager (must be done before adding tabs)
        mMessagingPagerAdapter = new MessagingPagerAdapter(getSupportFragmentManager());
        
        mViewPager = (ViewPager) findViewById(R.id.messaging_view_pager);
        
        mViewPager.setAdapter(mMessagingPagerAdapter);
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
		Tab gcmTab = mActionBar.newTab().setText("Google").setTabListener(this);
        Log.d(LOGTAG, "gcmTab: " + gcmTab);
		Tab csmTab = mActionBar.newTab().setText("Course").setTabListener(this);
        Log.d(LOGTAG, "csmTab: " + csmTab);
        mActionBar.addTab(gcmTab);
        mActionBar.addTab(csmTab);
        
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
