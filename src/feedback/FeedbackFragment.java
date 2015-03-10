package feedback;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class FeedbackFragment extends Fragment {

    private static final String TAG = "FeedbackFragment";

	private RelativeLayout mRelativeLayout;
    private EditText mFeedbackMessageEditText;
    private Button mSendFeedbackButton;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_feedback, container, false);

        mFeedbackMessageEditText = (EditText) mRelativeLayout.findViewById(R.id.edit_text_feedback);
        mSendFeedbackButton = (Button) mRelativeLayout.findViewById(R.id.button_send_feedback);
        mSendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Send onClick");

                new SocketAsyncTask(getActivity()).execute(mFeedbackMessageEditText.getText().toString());
            }
        });

        return mRelativeLayout;
    }
}
