/**
 * 
 */
package mutisya.kuria.hiphop;

import mutisya.kuria.hiphop.quiz.GamePlay;
import mutisya.kuria.hiphop.util.Utility;
import android.os.Bundle;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;


public class AnswersActivity extends MenuActivity  {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answers);
		GamePlay currentGame = ((Hiphop)getApplication()).getCurrentGame();
		
		TextView results = (TextView)findViewById(R.id.answers);
		String answers = Utility.getAnswers(currentGame.getQuestions());
		results.setText(answers);

		
	}

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);

    }



	
	 

}
