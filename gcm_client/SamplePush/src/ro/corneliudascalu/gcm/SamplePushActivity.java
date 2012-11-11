package ro.corneliudascalu.gcm;

import org.json.JSONException;
import org.json.JSONObject;

import ro.corneliudascalu.gcm.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class SamplePushActivity extends Activity {
	private BroadcastReceiver messageReceiver;
	private TextView regNumber;
	long maxDelay = 0;
	long minDelay = Long.MAX_VALUE;
	int count = 0;
	long aggregatedDelay = 0;
	long avgDelay = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		regNumber = (TextView) findViewById(R.id.textView1);
		GCMRegistrar.checkDevice(this);

		// uncomment below lines to unregister the device
		// GCMRegistrar.unregister(this);
		// Log.d("info", "unregistereddd....." +
		// GCMRegistrar.getRegistrationId(this));

		GCMRegistrar.checkManifest(this);
		if (GCMRegistrar.isRegistered(this)) {
			Log.d("info", GCMRegistrar.getRegistrationId(this));
		}
		final String regId = GCMRegistrar.getRegistrationId(this);
		regNumber.setText(regId);
		if (regId.equals("")) {
			// replace this with the project ID
			GCMRegistrar.register(this, "1053520176880");
			Log.d("info", GCMRegistrar.getRegistrationId(this));
			regNumber.setText(regId);
		} else {
			Log.d("info", "already registered as" + regId);
		}

		messageReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				regNumber.setText("Message: " + intent.getStringExtra(GCMIntentService.GCM_DATA));
				try {
					countMessage(new JSONObject(intent.getStringExtra(GCMIntentService.GCM_DATA)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

	}

	protected void countMessage(JSONObject jsonObject) {
		try {
			long now = System.currentTimeMillis();
			long time = jsonObject.getLong("date");
			long delay = now - time;
			aggregatedDelay += delay;
			count++;
			avgDelay = aggregatedDelay / count;
			if (delay > maxDelay) {
				maxDelay = delay;
			}
			if (delay < minDelay) {
				minDelay = delay;
			}

			regNumber.append("\n MaxDelay: " + maxDelay + "\n MinDelay: " + minDelay + "\n AvgDelay: " + avgDelay);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void registerClicked(View view) {
		GCMRegistrar.register(this, "1053520176880");
		String regId = GCMRegistrar.getRegistrationId(this);
		Log.d("info", regId);
		regNumber.setText(regId);
	}

	public void unregisterClicked(View view) {
		GCMRegistrar.unregister(this);
		Log.d("info", "unregistereddd....." + GCMRegistrar.getRegistrationId(this));
	}

	@Override
	protected void onStart() {
		super.onStart();
		registerReceiver(messageReceiver, new IntentFilter(GCMIntentService.MESSAGE_RECEIVED_ACTION));
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(messageReceiver);
	}
}