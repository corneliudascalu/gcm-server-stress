package ro.corneliudascalu.gcm.server;

import java.util.ArrayList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

class Notify {
	public static void main(String args[]) {

		try {

			Sender sender = new Sender("AIzaSyCp25M0MKoAO99RZ7Nx9W-686BbI0lokCo");

			ArrayList<String> devicesList = new ArrayList<String>();

			devicesList.add("APA91bF6tGGaXA_4GoP0l-VUR5q96vORnowlgRTZYslQkTWQ7E1wqqkjrD-JdIBXDabF-ajSDhXjsZTjSiyTUGEABwlALw1Ex1bSOiAfSDVv4Tzn6k_yVH0HqggI5WgrtnnfdSB0drI6Gf5VjH554aDKxr48mhI6Bw");

			// Use this line to send message without payload data
			// Message message = new Message.Builder().build();

			// use this line to send message with payload data
			Message message = new Message.Builder().collapseKey("1").timeToLive(3).delayWhileIdle(true).addData("message", "this text will be seen in notification bar!!").build();

			// Use this code to send to a single device
			// Result result = sender
			// .send(message,
			// "APA91bGiRaramjyohc2lKjAgFGpzBwtEmI8tJC30O89C2b3IjP1CuMeU1h9LMjKhmWuZwcXZjy1eqC4cE0tWBNt61Kx_SuMF6awzIt8WNq_4AfwflaVPHQ0wYHG_UX3snjp_U-5kJkmysdRlN6T8xChB1n3DtIq98w",
			// 1);

			// Use this for multicast messages
			for (int i = 0; i < 10; i++) {
				Thread.sleep(500);
				message = new Message.Builder().collapseKey("1").timeToLive(3).delayWhileIdle(true).addData("message", "Message with id " + i).addData("id", "" + i).build();
				MulticastResult result = sender.send(message, devicesList, 1);
				sender.send(message, devicesList, 1);

				System.out.println(result.toString());
				if (result.getResults() != null) {
					int canonicalRegId = result.getCanonicalIds();
					if (canonicalRegId != 0) {
					}
				} else {
					int error = result.getFailure();
					System.out.println(error);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
