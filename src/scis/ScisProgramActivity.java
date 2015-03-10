package scis;

import com.taylorcfrey.msse657enterpriseandroid.R;

import feedback.FeedbackFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ScisProgramActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scis_program);
        
        if (savedInstanceState == null) {
        	loadScisProgramFragment();
        }
    }
    
    private void loadScisProgramFragment() {
    	ScisProgramFragment fragment = new ScisProgramFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_scis_container, fragment, "ScisProgramFragment");
        fragmentTransaction.commit();
    }
}
