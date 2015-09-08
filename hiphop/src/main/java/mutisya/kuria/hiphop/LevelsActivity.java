package mutisya.kuria.hiphop;

import java.util.HashMap;
import java.util.List;
import mutisya.kuria.hiphop.quiz.Constants;
import mutisya.kuria.hiphop.quiz.GamePlay;
import mutisya.kuria.hiphop.quiz.Question;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

public class LevelsActivity extends MenuActivity implements OnClickListener {

	// private TextView level1;

	private Button btnLevel1;
	private Button btnLevel2;
	private Button btnLevel3;
	private Button btnLevel4;
	private Button btnLevel5;
	private Button btnLevel6;
	private Intent i;
	private GamePlay c;
	private List<Question> questions;
	private boolean level2_unlocked;
	private boolean level3_unlocked;
	private boolean level4_unlocked;
	private boolean level5_unlocked;
	private boolean level6_unlocked;
	private SharedPreferences locked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);

		btnLevel1 = (Button) findViewById(R.id.btnStart1);
		btnLevel2 = (Button) findViewById(R.id.btnStart2);
		btnLevel3 = (Button) findViewById(R.id.btnStart3);
		btnLevel4 = (Button) findViewById(R.id.btnStart4);
		btnLevel5 = (Button) findViewById(R.id.btnStart5);
		btnLevel6 = (Button) findViewById(R.id.btnStart6);
		btnLevel1.setOnClickListener(this);
		btnLevel2.setOnClickListener(this);
		btnLevel3.setOnClickListener(this);
		btnLevel4.setOnClickListener(this);
		btnLevel5.setOnClickListener(this);
		btnLevel6.setOnClickListener(this);
		locked = getSharedPreferences(Constants.STATUS, 0);

		unlock();

	}

	@SuppressLint("NewApi")
	public void unlock() {

		@SuppressWarnings("unchecked")
		HashMap<String, Integer> map = (HashMap<String, Integer>) locked
				.getAll();
		for (String s : map.keySet()) {
			Integer value = map.get(s);

			if (value == 1) {

				if (s.equalsIgnoreCase("locked1")) {
					
					btnLevel2.setBackground(getResources().getDrawable(
							R.drawable.button_selector));
                    btnLevel2.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null );
					level2_unlocked = true;

				}
				if (s.equalsIgnoreCase("locked2")) {

					btnLevel3.setBackground(getResources().getDrawable(
							R.drawable.button_selector));
                    btnLevel3.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null );
					level3_unlocked = true;

				}
				if (s.equalsIgnoreCase("locked3")) {

					btnLevel4.setBackground(getResources().getDrawable(
							R.drawable.button_selector));
                    btnLevel4.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null );
					level4_unlocked = true;
				}
				if (s.equalsIgnoreCase("locked4")) {

					btnLevel5.setBackground(getResources().getDrawable(
							R.drawable.button_selector));
                    btnLevel5.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null );
					level5_unlocked = true;
				}

				if (s.equalsIgnoreCase("locked5")) {

					btnLevel6.setBackground(getResources().getDrawable(
							R.drawable.button_selector));
                    btnLevel6.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null );
					level6_unlocked = true;
				}

			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnStart1:
			startGame(1);
			break;

		case R.id.btnStart2:
			if (level2_unlocked) {
				startGame(2);
			} else {
				Toast toast = Toast.makeText(this, "level is locked",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			break;

		case R.id.btnStart3:
			if (level3_unlocked) {
				startGame(3);
			} else {
				Toast toast = Toast.makeText(this, "level is locked",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			break;
		case R.id.btnStart4:
			if (level4_unlocked) {
				startGame(4);
			} else {
				Toast toast = Toast.makeText(this, "level is locked",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			break;

		case R.id.btnStart5:
			if (level5_unlocked) {
				startGame(5);
			} else {
				Toast toast = Toast.makeText(this, "level is locked",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			break;

		case R.id.btnStart6:
			if (level6_unlocked) {
				startGame(6);
			} else {
				Toast toast = Toast.makeText(this, "level is locked",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			break;
		}

	}

	public void startGame(int level) {
		c = new GamePlay();
		questions = c
				.getQuestionSetFromDb(this, level, GamePlay.getNumRounds());
		c.setQuestions(questions);
		((Hiphop) getApplication()).setCurrentGame(c);
		GamePlay.setDifficulty(level);

		i = new Intent(this, QuestionActivity.class);
		finish();
		startActivity(i);
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// Destroy the AdView.

	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MainActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		return;
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
