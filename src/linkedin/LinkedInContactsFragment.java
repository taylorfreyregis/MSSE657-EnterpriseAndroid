package linkedin;

import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthListener;

import social_media.SocialMediaActivity;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LinkedInContactsFragment extends Fragment {

	private static final String TAG = "LinkedInContactsFragment";
	
	RelativeLayout mRelativeLayout;
	ImageButton mImageButton;
	SocialAuthAdapter mAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_linkedin_contacts, container, false);

		mImageButton = (ImageButton) mRelativeLayout.findViewById(R.id.image_button_linkedin);
		
		mAdapter = new SocialAuthAdapter(new ResponseListener());
		
		mImageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mAdapter.authorize(getActivity(), Provider.LINKEDIN);
			}
		});
		
        return mRelativeLayout;
    }
	
	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			mAdapter.getUserProfileAsync(new ProfileDataListener());
		}

		@Override
		public void onBack() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(SocialAuthError arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	// To get status of message after authentication
		private final class ProfileDataListener implements SocialAuthListener {

			@Override
			public void onError(SocialAuthError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onExecute(String arg0, Object arg1) {
				List<Contact> contacts = null;
				try {
					contacts = (List<Contact>) arg1;
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
				if (contacts != null && contacts.size() > 0) {
					for (Contact contact : contacts) {
						Log.d(TAG, "Contact: " + contact);
					}
				}
				
			}

		}
}
