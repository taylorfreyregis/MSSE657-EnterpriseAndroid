package web_services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class WebServiceRequestTask extends AsyncTask<String, String, String> {

	private WebServiceResponseListener mWebServiceResponseListener;
	
	public WebServiceRequestTask(WebServiceResponseListener listener) {
		this.mWebServiceResponseListener = listener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		String result = "";
		
		HttpClient client = new DefaultHttpClient();
		String url = "http://regisscis.net/Regis2/webresources/regis2.program";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		try {
			HttpResponse response = client.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String temp = "";
			while((temp = reader.readLine()) != null) {
				result += temp;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		try {
			mWebServiceResponseListener.onWebServiceResponse(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mWebServiceResponseListener = null;
	}

}
