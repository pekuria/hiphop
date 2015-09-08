package mutisya.kuria.hiphop;

import mutisya.kuria.hiphop.quiz.GamePlay;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;


public class MenuActivity extends ActionBarActivity {

	
	private Intent i;
	private SharedPreferences sharedPref;
	private boolean vibrateOn;
	private int mode;

	public static String KEY_PREF_SYNC_MODE = "pref_sync_mode";
	// protected IBinder service;
	static boolean musicOn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		vibrateOn = sharedPref.getBoolean("pref_sync_vibrate", false);
		mode = Integer.parseInt(sharedPref.getString("pref_sync_mode", "1"));

		GamePlay.setMode(mode);
		GamePlay.setVibrate(vibrateOn);

	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		menu.removeItem(R.id.action_share);
		return true;
	}

	@SuppressLint("NewApi")
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_settings:
			i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;

		case R.id.action_help:
			i = new Intent(this, HelpActivity.class);
			startActivity(i);
			return true;

		case R.id.action_about:
			i = new Intent(this, AboutActivity.class);
			startActivity(i);
			return true;

            case R.id.action_credit:
                i = new Intent(this, CreditsActivity.class);
                startActivity(i);
                return true;

		}
		return super.onOptionsItemSelected(item);
	}

	

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.

	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);

	}

	@Override
	protected void onPause() {
		super.onPause();

		

	}

	@Override
	protected void onResume() {
		super.onResume();

		
	}

	
	

}
