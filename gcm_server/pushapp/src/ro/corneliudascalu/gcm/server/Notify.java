package ro.corneliudascalu.gcm.server;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

class Notify {
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {

		try {

			Sender sender = new Sender("AIzaSyCp25M0MKoAO99RZ7Nx9W-686BbI0lokCo");

			ArrayList<String> devicesList = new ArrayList<String>();
			String deviceId = "APA91bGBNspjNpAR_ErCZnuCNcy2ch2yj8GRBR5G38KHA9XK7eV47a-Fv_NdZWG2D_2Dc686YO0pqEjUGHP-VI6Yyul3vtZ9a3iZC9BqqRy_EL_R_-cnu0rD0WpKRVvBCrPRI0uOS_vNa3JFdG0X0UEHWpDHRMWW4Q";

			devicesList.add(deviceId);

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
			int i = 0;
			DateFormat df = DateFormat.getDateTimeInstance();
		//	while (true) {
				Thread.sleep(200);
				i++;
				JSONObject data = new JSONObject();
				data.put("id", i);
				data.put("date", df.format(new Date()));
				message = new Message.Builder().collapseKey("1").timeToLive(3).delayWhileIdle(true).addData("message", data.toJSONString()).build();
				MulticastResult result = sender.send(message, devicesList, 1);

				System.out.println(result.toString());
				if (result.getResults() != null) {
					int canonicalRegId = result.getCanonicalIds();
					if (canonicalRegId != 0) {
					}
				} else {
					int error = result.getFailure();
					System.out.println(error);
				}
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
