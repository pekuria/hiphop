/**
 * 
 */
package mutisya.kuria.hiphop;



import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;



public class AboutActivity extends Activity {
	
	private TextView version;
	private String verName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        version = (TextView) findViewById(R.id.version);

        try {

            verName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {

            verName = "";
            //e.printStackTrace();
        }

        version.setText(verName);


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
	
	
	
	 
}
