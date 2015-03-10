package feedback;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by taylorfrey on 12/6/14.
 */
public class SocketAsyncTask extends AsyncTask<String, Void, Void> {

    private static final String TAG = "SocketAsyncTask";

    private Context mContext;
    private String mResponse;

    // make the default private
    private SocketAsyncTask() {
        super();
    }

    public SocketAsyncTask(Context context) {
        mContext = context;

    }

    @Override
    protected Void doInBackground(String... params) {

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String message = params[0];

        try {
            InetAddress inetAddress = InetAddress.getByName("www.regisscis.net");
//            socket = new Socket(inetAddress, 8080);
            socket = new Socket("www.regisscis.net", 8080);

            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            Log.d(TAG, "isConnected: " + socket.isConnected() + " isBound: " + socket.isBound());

            if (message != null && !message.isEmpty()) {
                Log.d(TAG, "Writing message: " + message);
                out.println(message);
                out.flush();
                // TODO: Response from the server never seems to be returned.
                mResponse = in.readLine();
                Log.d(TAG, "Reading response: " + mResponse);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
            // Regardless of what happens, be sure to attempt close streams and release resources
            try {
                socket.close();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            try {
                in.close();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d(TAG, "onpost: " + mResponse);
        if (mContext instanceof Activity) {
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, mResponse, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
