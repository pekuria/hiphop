package mutisya.kuria.hiphop;

import mutisya.kuria.hiphop.quiz.GamePlay;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.google.analytics.tracking.android.EasyTracker;

public class SettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	public static String KEY_PREF_SYNC_VIBRATE = "pref_sync_vibrate";
	public static String KEY_PREF_SYNC_MODE = "pref_sync_mode";

	private boolean val;
	private String mode;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		// show the current value in the settings screen
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		if (key.equals(KEY_PREF_SYNC_VIBRATE)) {
			Preference connectionPref = findPreference(key);
			// Set summary to be the user-description for the selected value
			val = connectionPref.getSharedPreferences().getBoolean(
					KEY_PREF_SYNC_VIBRATE, false);
			GamePlay.setVibrate(val);

		}

		if (key.equals(KEY_PREF_SYNC_MODE)) {
			Preference connectionPref = findPreference(key);
			// Set summary to be the user-description for the selected value
			mode = connectionPref.getSharedPreferences().getString(
					KEY_PREF_SYNC_MODE, "1");
			GamePlay.setMode(Integer.parseInt(mode));

		}

	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.

	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);

	}

}
