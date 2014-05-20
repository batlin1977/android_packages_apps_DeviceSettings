/*
 * Copyright (C) 2012 The CyanogenMod Project
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

package com.teamcanjica.settings.device.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.teamcanjica.settings.device.DeviceSettings;
import com.teamcanjica.settings.device.MasterListPreference;
import com.teamcanjica.settings.device.R;
import com.teamcanjica.settings.device.Utils;

public class ScreenFragmentActivity extends PreferenceFragment {

	private static final String TAG = "NovaThor_Settings_Screen";

	private static final String FILE_2TAP2WAKE_CODINA = "/sys/kernel/bt404/doubletap2wake";
	private static final String FILE_SWEEP2WAKE_CODINA = "/sys/kernel/bt404/sweep2wake";
	private static final String FILE_SWEEP2WAKE_JANICE = "/sys/kernel/mxt224e/sweep2wake";
	private static final String FILE_EMULATOR = "/sys/kernel/abb-ponkey/emulator";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.screen_preferences);

		getActivity().getActionBar().setTitle(getResources().getString(R.string.screen_name));
		getActivity().getActionBar().setIcon(getResources().getDrawable(R.drawable.screen_icon));

		PreferenceCategory touchscreenCategory = (PreferenceCategory) findPreference(DeviceSettings.KEY_TOUCHSCREEN);
		PreferenceCategory visualCategory = (PreferenceCategory) findPreference(DeviceSettings.KEY_SCREEN_VISUAL);
		// Compatibility check for codina (Panel Gamma & Touchscreen Sensitivity)
		if (Utils.isCodina()) {
			visualCategory.removePreference(getPreferenceScreen().findPreference(DeviceSettings.KEY_PANEL_GAMMA));
			touchscreenCategory.removePreference(getPreferenceScreen().findPreference(DeviceSettings.KEY_TOUCHSCREEN_SENSITIVITY));
		// Compatibility check for janice (2Tap2Wake)
		} else if (Utils.isJanice()) {
			visualCategory.removePreference(getPreferenceScreen().findPreference(DeviceSettings.KEY_MIN_BRIGHTNESS));
			touchscreenCategory.removePreference(getPreferenceScreen().findPreference(DeviceSettings.KEY_USE_2TAP2WAKE));
			touchscreenCategory.removePreference(getPreferenceScreen().findPreference(DeviceSettings.KEY_DT2W_TIMEOUT));
		}

		if (!Utils.fileExists(MasterListPreference.FILE_FSYNC_MODE))
			getPreferenceScreen().removePreference(findPreference(DeviceSettings.KEY_FSYNC_CAT));
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {

		String key = preference.getKey();

		Log.w(TAG, "key: " + key);

		if (key.equals(DeviceSettings.KEY_USE_2TAP2WAKE)) {
			Utils.writeValue(FILE_2TAP2WAKE_CODINA, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
		} else if (key.equals(DeviceSettings.KEY_USE_SWEEP2WAKE)) {
			if (Utils.isJanice()) {
				Utils.writeValue(FILE_SWEEP2WAKE_JANICE, (((CheckBoxPreference) preference).
						isChecked() ? "on" : "off"));
			} else if (Utils.isCodina()) {
				Utils.writeValue(FILE_SWEEP2WAKE_CODINA, (((CheckBoxPreference) preference).
						isChecked() ? "on" : "off"));
			}
		} else if (key.equals(DeviceSettings.KEY_SCREEN_OFF)) {
			// 0 ensures least lag between press and screen off
			Utils.writeValue(FILE_EMULATOR, "0");
		} else if (key.equals(DeviceSettings.KEY_POWER_MENU)) {
			Utils.writeValue(FILE_EMULATOR, "525");
		}

		return true;
	}

	public static void restore(Context context) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		if (Utils.isJanice()) {
			Utils.writeValue(FILE_SWEEP2WAKE_JANICE, sharedPrefs.getBoolean(
					DeviceSettings.KEY_USE_SWEEP2WAKE, false) ? "on" : "off");
		} else if (Utils.isCodina()) {
			Utils.writeValue(FILE_2TAP2WAKE_CODINA, sharedPrefs.getBoolean(
					DeviceSettings.KEY_USE_2TAP2WAKE, false) ? "on" : "off");
			Utils.writeValue(FILE_SWEEP2WAKE_CODINA, sharedPrefs.getBoolean(
					DeviceSettings.KEY_USE_SWEEP2WAKE, false) ? "on" : "off");
		}

	}

}
