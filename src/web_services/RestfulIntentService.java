package web_services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class RestfulIntentService extends IntentService {
	
	private static final String TAG = "RestfulIntentService";
	private static final String NAME = RestfulIntentService.class.getSimpleName();

	public static final String ACTION_REST_VERB = "com.taylorcfrey.RestVerb";
	public static final String STRING_ENTITY = "com.taylorcfrey.StringEntity";
	public static final String KEY_VALUE_PAIR_LIST_ENTITY = "com.taylorcfrey.KeyValuePairListEntity";
	public static final String ACTION_WEB_SERVICE_RESPONSE = "com.taylorcfrey.ActionWebServiceResponse";
	public static final String EXTRA_DATA = "com.taylorcfrey.ExtraData";
    public static final String EXTRA_URI = "com.taylorcfrey.ExtraUri";
	
	public RestfulIntentService() {
		super(NAME);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Uri uri = intent.getData();
		RestVerb action = RestVerb.valueOf(intent.getAction());
		String stringEntity = intent.getStringExtra(STRING_ENTITY); // JSON String
		List listEntity = (List) intent.getSerializableExtra(KEY_VALUE_PAIR_LIST_ENTITY);
		
		// No destination or action defined, return.
		if (uri == null || action == null) {
			return;
		}
		
		// Create the request
		HttpRequestBase request = null;
		
		switch(action) {
		case GET:
			request = new HttpGet(uri.toString());
			request.setHeader("Accept", "application/json");
			break;
		case PUT:
			// Example of non-json
			request = new HttpPut(uri.toString());
			if (listEntity != null) {
				try {
					((HttpPut)request).setEntity(new UrlEncodedFormEntity(listEntity));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case POST:
			// Example of json
			request = new HttpPost(uri.toString());
			request.setHeader("Content-Type", "application/json");
			if (stringEntity != null) {
				try {
					((HttpPut)request).setEntity(new StringEntity(stringEntity));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case DELETE:
			request = new HttpDelete(uri.toString());
			break;
		}

		String result = "";
		HttpClient client = null;
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		
		// Send request and pass along the response
        // TODO: Instead of passing along the Response via the Intent, parse the information and insert
        // TODO: it into the DB. However, we do not yet have the URLs for such an insert.
        // TODO: It is seen in the ResponseListener example in the SocialAuth in the next slides.
		try {
            Log.d(TAG, "Sending request, passing along response");
			
			// Send request, parse response
			client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			inputStream = response.getEntity().getContent();
			outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[0xFFFF]; // 64KB buffer
			int value = 0;
			while ((value = inputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer);
			}
			
			// Pass along the data
			Intent responseIntent = new Intent(ACTION_WEB_SERVICE_RESPONSE);
            responseIntent.putExtra(EXTRA_URI, uri.toString());
			responseIntent.putExtra(EXTRA_DATA, outputStream.toByteArray());
			this.sendBroadcast(responseIntent); // This will work for the time being, need to look into LocalBroadcastManager
			// LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
			
		} catch (Exception ex) {
			
			// Be sure to close the streams, a bit defensive
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception ignore) {}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception ignore) {}
			}
		}
		
	}

}
