package linkedin;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthListener;

import social_media.SocialMediaActivity;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LinkedInPostFragment extends Fragment {

	RelativeLayout mRelativeLayout;
	ImageButton mImageButton;
	EditText mPostEditText;
	SocialAuthAdapter mAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_linkedin_post, container, false);

		mImageButton = (ImageButton) mRelativeLayout.findViewById(R.id.image_button_linkedin);
		mPostEditText = (EditText) mRelativeLayout.findViewById(R.id.edit_text_post);
		
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
			mAdapter.updateStatus(mPostEditText.getText().toString(), new MessageListener(), false);
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
		private final class MessageListener implements SocialAuthListener<Integer> {
			@Override
			public void onExecute(String provider, Integer t) {
				Integer status = t;
				if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
					Toast.makeText(getActivity(), "Message posted on " + provider, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getActivity(), "Message not posted on " + provider, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(SocialAuthError e) {

			}
		}
}
