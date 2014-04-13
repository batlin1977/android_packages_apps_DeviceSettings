package com.teamcanjica.settings.device;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

public class MasterListPreference extends ListPreference implements
OnPreferenceChangeListener {
	
	private static final String FILE_MALI_GPU_CLOCK = "/sys/kernel/mali/mali_gpu_clock";
	private static final String FILE_FSYNC_MODE = "/sys/kernel/fsync/mode";
	private static final String FILE_CHARGER_CURR = "/sys/kernel/abb-charger/charger_curr";
	private static final String FILE_DEEPEST_SLEEP_STATE = "/sys/kernel/debug/cpuidle/deepest_state";
	private static final String FILE_MALIL2_MAX_READS = "/sys/module/mali/parameters/mali_l2_max_reads";
	private static final String FILE_MALI_PREALLOC_MEM = "/sys/module/mali/parameters/pre_allocated_memory_size_max";
	private static final String FILE_SCHED_MC = "/sys/devices/system/cpu/sched_mc_power_savings";
	private static final String FILE_TCP_CONTROL = "/proc/sys/net/ipv4/tcp_congestion_control";
	private static final String FILE_PANEL_GAMMA = "/sys/class/lcd/panel/device/gamma_mode";
	private static final String FILE_TOUCHSCREEN_SENSITIVITY = "/sys/kernel/mxt224e/threshold_t48";

	private Context mCtx;

	public MasterListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnPreferenceChangeListener(this);
		mCtx = context;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		
		String key = preference.getKey();
		
		if (key.equals(DeviceSettings.KEY_SET_GPU_CLOCK)) {
			Utils.writeValue(FILE_MALI_GPU_CLOCK, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_FSYNC_MODE)) {
			Utils.writeValue(FILE_FSYNC_MODE, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_CHARGER_CURRENCY)) {
			Utils.writeValue(FILE_CHARGER_CURR, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_DEEPEST_SLEEP_STATE)) {
			Utils.writeValue(FILE_DEEPEST_SLEEP_STATE, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_HSPA)) {
			sendIntent(mCtx, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_MALI_L2MR)) {
			Utils.writeValue(FILE_MALIL2_MAX_READS, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_MALI_PAM)) {
			Utils.writeValue(FILE_MALI_PREALLOC_MEM, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_SCHED_MC)) {
			Utils.writeValue(FILE_SCHED_MC, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_TCP_CONTROL)) {
			Utils.writeValue(FILE_TCP_CONTROL, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_PANEL_GAMMA)) {
			Utils.writeValue(FILE_PANEL_GAMMA, (String) newValue);
		} else if (key.equals(DeviceSettings.KEY_TOUCHSCREEN_SENSITIVITY)) {
			Utils.writeValue(FILE_TOUCHSCREEN_SENSITIVITY, (String) newValue);
		}

		return true;
	}

	public static void restore(Context context) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		Utils.writeValue(FILE_MALI_GPU_CLOCK, sharedPrefs.getString(
				DeviceSettings.KEY_SET_GPU_CLOCK, "499200"));
		
		Utils.writeValue(FILE_FSYNC_MODE, sharedPrefs.getString(
				DeviceSettings.KEY_FSYNC_MODE, "0"));
		
		Utils.writeValue(FILE_CHARGER_CURR, sharedPrefs.getString(
				DeviceSettings.KEY_CHARGER_CURRENCY, "600"));
		
		Utils.writeValue(FILE_DEEPEST_SLEEP_STATE, sharedPrefs.getString(
				DeviceSettings.KEY_DEEPEST_SLEEP_STATE, "3"));
		
		sendIntent(context,
				sharedPrefs.getString(DeviceSettings.KEY_HSPA, "23"));
		
		Utils.writeValue(FILE_MALIL2_MAX_READS, sharedPrefs.getString(
				DeviceSettings.KEY_MALI_L2MR, "48"));
		
		Utils.writeValue(FILE_MALI_PREALLOC_MEM, sharedPrefs.getString(
				DeviceSettings.KEY_MALI_PAM, "50331648"));
		
		Utils.writeValue(FILE_SCHED_MC, sharedPrefs.getString(
				DeviceSettings.KEY_SCHED_MC, "0"));
		
		Utils.writeValue(FILE_TCP_CONTROL, sharedPrefs.getString(
				DeviceSettings.KEY_TCP_CONTROL, "cubic"));

		Utils.writeValue(FILE_PANEL_GAMMA, sharedPrefs.getString(
				DeviceSettings.KEY_PANEL_GAMMA, "0"));

		Utils.writeValue(FILE_TOUCHSCREEN_SENSITIVITY, sharedPrefs.getString(
				DeviceSettings.KEY_TOUCHSCREEN_SENSITIVITY, "val=17"));
	}
	
	private static void sendIntent(Context context, String value) {
		Intent i = new Intent("com.cyanogenmod.SamsungServiceMode.EXECUTE");
		i.putExtra("sub_type", 20); // HSPA Setting
		i.putExtra("data", value);
		context.sendBroadcast(i);
	}

}
