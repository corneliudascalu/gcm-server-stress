/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.corneliudascalu.gcm;

import org.json.JSONException;
import org.json.JSONObject;

import ro.corneliudascalu.gcm.R;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * {@link IntentService} responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	public static final String GCM_DATA = "gcmData";
	@SuppressWarnings("hiding")
	private static final String TAG = "GCMIntentService";
	public static final String MESSAGE_RECEIVED_ACTION = "messageReceived";

	public GCMIntentService() {
		super("1053520176880");
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {
		// long when = System.currentTimeMillis();
		// NotificationManager notificationManager = (NotificationManager)
		// context.getSystemService(Context.NOTIFICATION_SERVICE);
		// Notification notification = new Notification(R.drawable.ic_launcher,
		// message, when);
		// String title = context.getString(R.string.app_name);
		// Intent notificationIntent = new Intent(context,
		// SamplePushActivity.class);
		// // set intent so it does not start a new activity
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// PendingIntent intent = PendingIntent.getActivity(context, 0,
		// notificationIntent, 0);
		// notification.setLatestEventInfo(context, title, message, intent);
		// notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// notificationManager.notify(0, notification);

		Log.d(TAG, "Received: " + message);

		Intent intent2 = new Intent(MESSAGE_RECEIVED_ACTION);
		intent2.putExtra(GCM_DATA, message);

		context.sendBroadcast(intent2);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {

		Log.d("GCM", "RECIEVED A MESSAGE");
		// Get the data from intent and send to notificaion bar
		generateNotification(arg0, arg1.getStringExtra("message"));
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}
