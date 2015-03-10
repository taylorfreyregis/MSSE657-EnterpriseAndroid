package main;

import com.taylorcfrey.msse657enterpriseandroid.R;

import scis.ScisProgramActivity;
import feedback.FeedbackActivity;
import linkedin.LinkedInActivity;
import messaging.MessagingActivity;
import social_media.SocialMediaActivity;
import navigation.NavigationDrawerFragment;
import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	// Positions
	private static final int SCIS_POSITION = 0;
	private static final int SOCIAL_MEDIA = 1;
	private static final int MESSAGING = 2;
	private static final int POST_FEEDBACK = 3;
	
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	
    	switch (position) {
    		case SCIS_POSITION:
        		Intent scisIntent = new Intent(this, ScisProgramActivity.class);
        		this.startActivity(scisIntent);
    			break;
    		case SOCIAL_MEDIA:
    			Intent socialMediaIntent = new Intent(this, SocialMediaActivity.class);
    			this.startActivity(socialMediaIntent);
    			break;
    		case MESSAGING:
    			Intent messagingIntent = new Intent(this, MessagingActivity.class);
    			this.startActivity(messagingIntent);
    			break;
    		case POST_FEEDBACK:
    			Intent feedbackIntent = new Intent(this, FeedbackActivity.class);
    			this.startActivity(feedbackIntent);
    			break;
    		case 4:
    			Intent linkedInIntent = new Intent(this, LinkedInActivity.class);
    			this.startActivity(linkedInIntent);
    			break;
    	}
    }

    public void restoreActionBar() { 
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
