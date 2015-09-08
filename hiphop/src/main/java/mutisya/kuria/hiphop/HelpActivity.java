/**
 * 
 */
package mutisya.kuria.hiphop;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;

public class HelpActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		TextView text = (TextView)findViewById(R.id.helpText);
		String formattedText = getString(R.string.txtHelp);
		Spanned result = Html.fromHtml(formattedText);
		text.setText(result);

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
