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
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.teamcanjica.settings.device.DeviceSettings;
import com.teamcanjica.settings.device.R;
import com.teamcanjica.settings.device.Utils;

public class AudioFragmentActivity extends PreferenceFragment {

	private static final String TAG = "NovaThor_Settings_Audio";
	public static final String FILE_ANAGAIN3 = "/sys/kernel/abb-codec/anagain3";
	public static final String FILE_HSLDIGGAIN = "/sys/kernel/abb-codec/hsldiggain";
	public static final String FILE_HSRDIGGAIN = "/sys/kernel/abb-codec/hsrdiggain";
	public static final String FILE_HSLOWPOW = "/sys/kernel/abb-codec/hslowpow";
	public static final String FILE_HSDACLOWPOW = "/sys/kernel/abb-codec/hsdaclowpow";
	public static final String FILE_HSHPEN = "/sys/kernel/abb-codec/hshpen";
	public static final String FILE_CLASSDHPG = "/sys/kernel/abb-codec/classdhpg";
	public static final String FILE_CLASSDWG = "/sys/kernel/abb-codec/classdwg";
	public static final String FILE_ADDIGGAIN2 = "/sys/kernel/abb-codec/addiggain2";
	public static final String FILE_EARDIGGAIN = "/sys/kernel/abb-codec/eardiggain";
	public static final String FILE_LPA_MODE = "/sys/kernel/abb-codec/lpa_mode";
	public static final String FILE_CHARGEPUMP = "/sys/kernel/abb-codec/chargepump";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.audio_preferences);

		getPreferenceScreen().findPreference(DeviceSettings.KEY_ANAGAIN3_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_ANAGAIN3)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_ADDIGGAIN2_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_ADDIGGAIN2)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_EARDIGGAIN_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_EARDIGGAIN)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_HSLDIGGAIN_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_HSLDIGGAIN)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_HSRDIGGAIN_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_HSRDIGGAIN)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_CLASSDHPG_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_CLASSDHPG)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_CLASSDWG_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_CLASSDWG)).isChecked());
		getPreferenceScreen().findPreference(DeviceSettings.KEY_LPA_MODE_CONTROL).setEnabled(
				((CheckBoxPreference) findPreference(DeviceSettings.KEY_ENABLE_LPA_MODE)).isChecked());

		getActivity().getActionBar().setTitle(getResources().getString(R.string.audio_name));
		getActivity().getActionBar().setIcon(getResources().getDrawable(R.drawable.audio_icon));
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {

		String key = preference.getKey();

		Log.w(TAG, "key: " + key);

		if (key.equals(DeviceSettings.KEY_ENABLE_ANAGAIN3)) {
			Utils.writeValue(FILE_ANAGAIN3, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_ANAGAIN3_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_HSLDIGGAIN)) {
			Utils.writeValue(FILE_HSLDIGGAIN, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_HSLDIGGAIN_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_HSRDIGGAIN)) {
			Utils.writeValue(FILE_HSRDIGGAIN, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_HSRDIGGAIN_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_HSLOWPOW)) {
			Utils.writeValue(FILE_HSLOWPOW, "mode=" + (((CheckBoxPreference) preference).
					isChecked() ? "1" : "0"));
			Utils.writeValue(FILE_HSLOWPOW, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
		} else if (key.equals(DeviceSettings.KEY_ENABLE_HSDACLOWPOW)) {
			Utils.writeValue(FILE_HSDACLOWPOW, "mode=" + (((CheckBoxPreference) preference).
					isChecked() ? "1" : "0"));
			Utils.writeValue(FILE_HSDACLOWPOW, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
		} else if (key.equals(DeviceSettings.KEY_ENABLE_HSHPEN)) {
			Utils.writeValue(FILE_HSHPEN, "mode=" + (((CheckBoxPreference) preference).
					isChecked() ? "1" : "0"));
			Utils.writeValue(FILE_HSHPEN, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
		} else if (key.equals(DeviceSettings.KEY_ENABLE_CLASSDHPG)) {
			Utils.writeValue(FILE_CLASSDHPG, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_CLASSDHPG_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_CLASSDWG)) {
			Utils.writeValue(FILE_CLASSDWG, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_CLASSDWG_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_ADDIGGAIN2)) {
			Utils.writeValue(FILE_ADDIGGAIN2, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_ADDIGGAIN2_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_EARDIGGAIN)) {
			Utils.writeValue(FILE_EARDIGGAIN, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_EARDIGGAIN_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_ENABLE_LPA_MODE)) {
			Utils.writeValue(FILE_LPA_MODE, (((CheckBoxPreference) preference).
					isChecked() ? "on" : "off"));
			getPreferenceScreen().findPreference(DeviceSettings.KEY_LPA_MODE_CONTROL).setEnabled(
					((CheckBoxPreference) preference).isChecked());
		} else if (key.equals(DeviceSettings.KEY_USE_CHARGEPUMP)) {
			Utils.writeValue(FILE_CHARGEPUMP, ((CheckBoxPreference) preference).
					isChecked());
		}

		return true;
	}
	
	public static void restore(Context context) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		Utils.writeValue(FILE_ANAGAIN3, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_ANAGAIN3, false) ? "on" : "off");

		Utils.writeValue(FILE_HSLDIGGAIN, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSLDIGGAIN, false) ? "on" : "off");

		Utils.writeValue(FILE_HSRDIGGAIN, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSRDIGGAIN, false) ? "on" : "off");

		Utils.writeValue(FILE_HSLOWPOW, "mode=" + String.valueOf(sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSLOWPOW, false) ? "1" : "0"));

		Utils.writeValue(FILE_HSLOWPOW, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSLOWPOW, false) ? "on" : "off");

		Utils.writeValue(FILE_HSDACLOWPOW, "mode=" + String.valueOf(sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSDACLOWPOW, false) ? "1" : "0"));
		
		Utils.writeValue(FILE_HSDACLOWPOW, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSDACLOWPOW, false) ? "on" : "off");

		Utils.writeValue(FILE_HSHPEN, "mode=" + String.valueOf(sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSHPEN, false) ? "1" : "0"));

		Utils.writeValue(FILE_HSHPEN, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_HSHPEN, false) ? "on" : "off");

		Utils.writeValue(FILE_CLASSDHPG, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_CLASSDHPG, false) ? "on" : "off");

		Utils.writeValue(FILE_CLASSDWG, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_CLASSDWG, false) ? "on" : "off");

		Utils.writeValue(FILE_ADDIGGAIN2, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_ADDIGGAIN2, false) ? "on" : "off");

		Utils.writeValue(FILE_EARDIGGAIN, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_EARDIGGAIN, false) ? "on" : "off");

		Utils.writeValue(FILE_LPA_MODE, sharedPrefs.getBoolean(
				DeviceSettings.KEY_ENABLE_LPA_MODE, false) ? "on" : "off");

		Utils.writeValue(FILE_CHARGEPUMP, sharedPrefs.getBoolean(
				DeviceSettings.KEY_USE_CHARGEPUMP, false) ? "1" : "0");
	}

}
