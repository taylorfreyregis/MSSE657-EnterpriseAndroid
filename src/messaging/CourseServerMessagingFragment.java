package messaging;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.tavendo.autobahn.*;

public class CourseServerMessagingFragment extends Fragment {

	private static final String TAG = "CourseServerMessagingFragment";
	
	private RelativeLayout mRelativeLayout;
	private final WebSocketConnection mConnection = new WebSocketConnection();
	private final String mWebServiceUri = "ws://www.regisscis.net:80/WebSocketServer/chat";
	private Button mConnectButton;
	private Button mDisconnectButton;
	private Button mSendButton;
	private EditText mMessageEditText;
	private TextView mReceivedMessageTextView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_csm, container, false);

		mMessageEditText = (EditText) mRelativeLayout.findViewById(R.id.websocket_message_edit_text);
		mReceivedMessageTextView = (TextView) mRelativeLayout.findViewById(R.id.websocket_response_text_view);
		
		mConnectButton = (Button) mRelativeLayout.findViewById(R.id.websocket_connect_button);
		mConnectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				start();
				
			}
		});
		
		mDisconnectButton = (Button) mRelativeLayout.findViewById(R.id.websocket_disconnect_button);
		mDisconnectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mConnection.disconnect();
				
			}
		});
		
		mSendButton = (Button) mRelativeLayout.findViewById(R.id.websocket_send_message_button);
		mSendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mConnection.isConnected()) {
					mConnection.sendTextMessage(mMessageEditText.getText().toString());
					mMessageEditText.setText("");
				} else {
					Toast.makeText(getActivity(), "Please hit \"Connect\"", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
        return mRelativeLayout;
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mConnection.isConnected()) {
			mConnection.disconnect();
		}
	}
	
	private void start() {
		
		try {
			mConnection.connect(mWebServiceUri, new WebSocketHandler() {
				
				@Override
				public void onOpen() {
					Log.d(TAG, "WebSocketConnection opened.");
					Toast.makeText(getActivity(), "Connection Established", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onTextMessage(String payload) {
					Log.d(TAG, "onTextMessage - payload: " + payload);
					messageReceived(payload);
				}
				
				@Override
				public void onClose(int code, String reason) {
					Log.d(TAG, "onClose - int: " + code + " reason: " + reason);
					// TODO: Toast throws exception if not triggered from the disconnect button
//					Toast.makeText(getActivity(), "Successfully Disconnected", Toast.LENGTH_SHORT).show();
				}
			});
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void messageReceived(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		mReceivedMessageTextView.append("\n" + message);
	}
}
