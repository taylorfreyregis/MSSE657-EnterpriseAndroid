package social_media;

import java.io.File;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SocialMediaActivity extends FragmentActivity {
	
	private final static String LOGTAG = "MessagingActivity";

	Button mShareButton;
	SocialAuthAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        
        // Welcome Message
        TextView textview = (TextView)findViewById(R.id.text);
        textview.setText("Welcome to SocialAuth Demo. We are sharing text SocialAuth Android by share button.");
        
        //Create Your Own Share Button
        mShareButton = (Button) findViewById(R.id.sharebutton);
        mShareButton.setText("Share");
        mShareButton.setTextColor(Color.WHITE);
        mShareButton.setBackgroundResource(R.drawable.button_gradient);
                        
		// Add it to Library
        mAdapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
        mAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
//        mAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);
        mAdapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
//        mAdapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
//        mAdapter.addProvider(Provider.YAMMER, R.drawable.yammer);
//        mAdapter.addProvider(Provider.EMAIL, R.drawable.email);
//        mAdapter.addProvider(Provider.MMS, R.drawable.mms);

		// Providers require setting user call Back url
//        mAdapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
//        mAdapter.addCallBack(Provider.YAMMER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");

		// Enable Provider
        mAdapter.enable(mShareButton);

	}

	/**
	 * Listens Response from Library
	 * 
	 */
	private final class ResponseListener implements DialogListener {
		
		Button mUpdate;
		EditText mEditText;
		
		@Override
		public void onComplete(Bundle values) {

			Log.d("ShareButton", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(SocialMediaActivity.this, providerName + " connected", Toast.LENGTH_LONG).show();

			mUpdate = (Button) findViewById(R.id.update);
			mEditText = (EditText) findViewById(R.id.editTxt);

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.

			mUpdate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mAdapter.updateStatus(mEditText.getText().toString(), new MessageListener(), false);
				}
			});

			// Share via Email Intent
			if (providerName.equalsIgnoreCase("share_mail")) {
				// Use your own code here
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
						"vineet.aggarwal@3pillarglobal.com", null));
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test");
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
						"image5964402.png");
				Uri uri = Uri.fromFile(file);
				emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(emailIntent, "Test"));
			}

			// Share via mms intent
			if (providerName.equalsIgnoreCase("share_mms")) {

				// Use your own code here
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
						"image5964402.png");
				Uri uri = Uri.fromFile(file);

				Intent mmsIntent = new Intent(Intent.ACTION_SEND, uri);
				mmsIntent.putExtra("sms_body", "Test");
				mmsIntent.putExtra(Intent.EXTRA_STREAM, uri);
				mmsIntent.setType("image/png");
				startActivity(mmsIntent);
			}

		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}

	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(SocialMediaActivity.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(SocialMediaActivity.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}
}
