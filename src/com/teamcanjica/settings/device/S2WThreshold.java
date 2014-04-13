/*
 * Copyright (C) 2014 TeamCanjica https://github.com/TeamCanjica
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teamcanjica.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

public class S2WThreshold extends EditTextPreference implements OnPreferenceChangeListener {

	public S2WThreshold(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnPreferenceChangeListener(this);
	}
	
	private static final String FILE = "/sys/kernel/bt404/sweep2wake";
	
	public static boolean isSupported() {
		return Utils.fileExists(FILE);
	}
	
	/**
	 * Restore threshold value from SharedPreferences.
	 * 
	 * @param context
	 *            The context to read the SharedPreferences from
	 */
	public static void restore(Context context) {
		if (!isSupported()) {
			return;
		}

		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		
		Utils.writeValue(FILE, "threshold_x=" + sharedPrefs.getString(
				DeviceSettings.KEY_X_SWEEP2WAKE, "120"));

		Utils.writeValue(FILE, "threshold_y=" + sharedPrefs.getString(
				DeviceSettings.KEY_Y_SWEEP2WAKE, "240"));
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		
		if (key.equals(DeviceSettings.KEY_X_SWEEP2WAKE)) {
		Utils.writeValue(FILE, "threshold_x=" + newValue);
		}
		
		if (key.equals(DeviceSettings.KEY_Y_SWEEP2WAKE)) {
			Utils.writeValue(FILE, "threshold_y=" + newValue);
		}
		return true;
	}

}
