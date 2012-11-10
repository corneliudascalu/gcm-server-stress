package ro.corneliudascalu.gcm;

import ro.corneliudascalu.gcm.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class SamplePushActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView regNumber = (TextView) findViewById(R.id.textView1);
		GCMRegistrar.checkDevice(this);

		// uncomment below lines to unregister the device
//		GCMRegistrar.unregister(this);
//		Log.d("info", "unregistereddd....." + GCMRegistrar.getRegistrationId(this));

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

	}
}