package com.example.uniqueid;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView uniqueIdTextView = (TextView) findViewById(R.id.unique_id_text_view);

		String uniqueId = getAndroidUniqueDeviceId();

		uniqueIdTextView.setText(uniqueId);

	}

	public String getAndroidUniqueDeviceId() {

		StringBuffer idView = new StringBuffer();

		String imeistring = null;
		String imsistring = null;

		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * getDeviceId() function Returns the unique device ID. for example,the
		 * IMEI for GSM and the MEID or ESN for CDMA phones.
		 */
		imeistring = telephonyManager.getDeviceId();
		idView.append("IMEI No : " + imeistring + "\n");

		/*
		 * getSubscriberId() function Returns the unique subscriber ID, for
		 * example, the IMSI for a GSM phone.
		 */
		imsistring = telephonyManager.getSubscriberId();
		idView.append("IMSI No : " + imsistring + "\n");

		/*
		 * System Property ro.serialno returns the serial number as unique
		 * number Works for Android 2.3 and above
		 */

		String serialnum = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			serialnum = (String) (get.invoke(c, "ro.serialno", "unknown"));
			idView.append("serial : " + serialnum + "\n");
		} catch (Exception ignored) {
		}
		String serialnum2 = null;
		try {
			Class myclass = Class.forName("android.os.SystemProperties");
			Method[] methods = myclass.getMethods();
			Object[] params = new Object[] { new String("ro.serialno"),
					new String("Unknown") };
			serialnum2 = (String) (methods[2].invoke(myclass, params));
			idView.append("serial2 : " + serialnum2 + "\n");
		} catch (Exception ignored) {
		}
		/*
		 * Settings.Secure.ANDROID_ID returns the unique DeviceID Works for
		 * Android 2.2 and above
		 */
		String androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		idView.append("AndroidID : " + androidId + "\n");

		return idView.toString();
	}

}
