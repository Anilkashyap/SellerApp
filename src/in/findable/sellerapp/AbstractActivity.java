package in.findable.sellerapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class AbstractActivity extends Activity {

	/** Show Toast messages */
	protected void displayMessage(String alert_msg) {
		Toast.makeText(this, alert_msg, Toast.LENGTH_SHORT).show();
	}
	/** Show Toast messages */
	protected void displayMessage(int alert_msg) {
		Toast.makeText(this, getResources().getString(alert_msg), Toast.LENGTH_SHORT).show();
	}

	/** Check phone is online */
	public boolean isOnline() {
		try {
			ConnectivityManager cm =
			        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			 
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null &&
			                      activeNetwork.isConnectedOrConnecting();
			return isConnected;
		} catch (Exception e) {
			Log.v("connectivity", e.toString());
		}
		return false;
	}

}
