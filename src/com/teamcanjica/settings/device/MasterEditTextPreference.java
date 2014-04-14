package com.teamcanjica.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.AttributeSet;

public class MasterEditTextPreference extends EditTextPreference implements OnPreferenceChangeListener {
	
	private static final String FILE_S2WTHRESH_CODINA = "/sys/kernel/bt404/sweep2wake";
	private static final String FILE_S2WTHRESH_JANICE = "/sys/kernel/mxt224e/sweep2wake";
	private static final String FILE_BOOST_DELAY = "/sys/kernel/mali/mali_boost_delay";
	
	public MasterEditTextPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {

		String key = preference.getKey();
		
		switch (key) {
		
		case DeviceSettings.KEY_X_SWEEP2WAKE:
			if (Utils.isCodina()) {
				Utils.writeValue(FILE_S2WTHRESH_CODINA, "threshold_x=" + newValue);
			} else {
				Utils.writeValue(FILE_S2WTHRESH_JANICE, "threshold_x=" + newValue);
			}
			break;
			
		case DeviceSettings.KEY_Y_SWEEP2WAKE:
			if (Utils.isCodina()) {
				Utils.writeValue(FILE_S2WTHRESH_CODINA, "threshold_y=" + newValue);
			} else {
				Utils.writeValue(FILE_S2WTHRESH_JANICE, "threshold_y=" + newValue);
			}
			break;
			
		case DeviceSettings.KEY_BOOST_DELAY:
			Utils.writeValue(FILE_BOOST_DELAY, (String) newValue);
			break;

		}
		return true;
	}
	
	public static void restore(Context context) {

		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		if (Utils.isCodina()) {
		Utils.writeValue(FILE_S2WTHRESH_CODINA, "threshold_x=" + sharedPrefs.getString(
				DeviceSettings.KEY_X_SWEEP2WAKE, "120"));

		Utils.writeValue(FILE_S2WTHRESH_CODINA, "threshold_y=" + sharedPrefs.getString(
				DeviceSettings.KEY_Y_SWEEP2WAKE, "240"));
		} else {
			Utils.writeValue(FILE_S2WTHRESH_JANICE, "threshold_x=" + sharedPrefs.getString(
					DeviceSettings.KEY_X_SWEEP2WAKE, "120"));

			Utils.writeValue(FILE_S2WTHRESH_JANICE, "threshold_y=" + sharedPrefs.getString(
					DeviceSettings.KEY_Y_SWEEP2WAKE, "240"));
		}

		Utils.writeValue(FILE_BOOST_DELAY, sharedPrefs.getString(
				DeviceSettings.KEY_BOOST_DELAY, "500"));
	}

}
