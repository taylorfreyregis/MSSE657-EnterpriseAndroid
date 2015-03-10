package feedback;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class FeedbackActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		if (savedInstanceState == null) {
			loadFeedbackFragment();
		}
	}

	private void loadFeedbackFragment() {
		FeedbackFragment fragment = new FeedbackFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.activity_feedback_container, fragment,
				"FeedbackFragment");
		fragmentTransaction.commit();
	}
}
