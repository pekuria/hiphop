/**
 * 
 */
package mutisya.kuria.hiphop;

import java.util.HashMap;
import java.util.List;

import mutisya.kuria.hiphop.quiz.Constants;
import mutisya.kuria.hiphop.quiz.GamePlay;
import mutisya.kuria.hiphop.quiz.Question;
import mutisya.kuria.hiphop.util.Utility;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

@SuppressLint("UseSparseArrays")
public class QuestionActivity extends MenuActivity implements OnClickListener {

	private Question currentQ;
	private GamePlay currentGame;
	private TextView timerValue;
	private ProgressBar progressBar;
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	private Handler mHandler = new Handler();
	private Animation anim;
	private Vibrator v;
	private SharedPreferences locked;
	private Button option1;
	private Button option2;
	private Button option3;
	private Button option4;
	private Button b;
	private boolean newQ;
	ImageView score;
	ImageView score1;
	ImageView score2;

	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	private long time = 0L;
	protected String text = null;
	private int state;
	private RelativeLayout lives;
	private HashMap<Integer, Integer> scores;
    private Intent i;


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);

		/***
		 * Configure current game and get question
		 */
		GamePlay.setWrong(0);
		GamePlay.setRight(0);
		currentGame = ((Hiphop) getApplication()).getCurrentGame();
		currentQ = currentGame.getNextQuestion();
		// Look up the Adview as a resource and load a request.
		

		timerValue = (TextView) findViewById(R.id.timer);
		option1 = (Button) findViewById(R.id.Button1);
		option2 = (Button) findViewById(R.id.Button2);
		option3 = (Button) findViewById(R.id.Button3);
		option4 = (Button) findViewById(R.id.Button4);
		option1.setOnClickListener(this);
		option2.setOnClickListener(this);
		option3.setOnClickListener(this);
		option4.setOnClickListener(this);

		score = (ImageView) findViewById(R.id.score);
		score1 = (ImageView) findViewById(R.id.score1);
		score2 = (ImageView) findViewById(R.id.score2);
		lives = (RelativeLayout)findViewById(R.id.lives);
		scores = new HashMap<Integer, Integer>();

		// Blink text animation
		anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(100); // You can manage the time of the blink with this
								// parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setProgress(0);
		progressBar.setMax(GamePlay.getNumRounds());
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		locked = getSharedPreferences(Constants.STATUS, 0);
		
		
		
		if (GamePlay.getMode() == Constants.SIMPLE) {
			lives.setVisibility(View.GONE);
			
		}
		
		/**
		 * Update the question and answer options..
		 */
		setQuestions();

	}

	

	/**
	 * Method to set the text for the question and answers from the current
	 * games current question
	 */
	private void setQuestions() {
		// set the question text from current question
		String question = Utility.capitalise(currentQ.getQuestion()) + "?";
		TextView qText = (TextView) findViewById(R.id.question);
		qText.setText(question);

		// set the available options
		List<String> answers = currentQ.getQuestionOptions();
		// TextView option1 = (TextView) findViewById(R.id.answer1);
		option1.setEnabled(true);
		option1.setTextColor(Color.WHITE);
		option1.setText(Utility.capitalise(answers.get(0)));

		// TextView option2 = (TextView) findViewById(R.id.answer2);
		option2.setEnabled(true);
		option2.setTextColor(Color.WHITE);
		option2.setText(Utility.capitalise(answers.get(1)));

		// TextView option3 = (TextView) findViewById(R.id.answer3);
		option3.setEnabled(true);
		option3.setTextColor(Color.WHITE);
		option3.setText(Utility.capitalise(answers.get(2)));

		// TextView option4 = (TextView) findViewById(R.id.answer4);
		option4.setEnabled(true);
		option4.setTextColor(Color.WHITE);
		option4.setText(Utility.capitalise(answers.get(3)));
		newQ = true;

	}

	@Override
	public void onClick(View v) {
		// Log.d("Questions", "Moving to next question");

		b = (Button) v;
		text = b.getText().toString();
		checkAnswer();

	}

	/**
	 * Check if a checkbox has been selected, and if it has then check if its
	 * correct and update gamescore
	 */
	private boolean checkAnswer() {
		String answer = text;
		// getSelectedAnswer();

		if (answer == null) {
			// Log.d("Questions", "No selection made - returning");
			return false;
		} else {

			if (currentQ.getAnswer().equalsIgnoreCase(answer)) {

				if (newQ) {
					currentGame.incrementRightAnswers();

					newQ = false;
				}

				/**
				 * check if end of game
				 */

				if (currentGame.isGameOver()) {

					// stop the timer and remove handler

					gameOver(MainActivity.class);
				}

				else {
					currentQ = currentGame.getNextQuestion();
					setQuestions();
					text = null;
					mHandler.post(new Runnable() {
						public void run() {
							progressBar.setProgress(currentGame.getRound());
						}

					});

				}

			} else {

				if (newQ) {
					currentGame.incrementWrongAnswers();

					if (GamePlay.getMode() == Constants.ADVANCED) {

						if (GamePlay.getWrong() == 1) {
							score2.setVisibility(View.GONE);
						}
						if (GamePlay.getWrong() == 2) {
							score1.setVisibility(View.GONE);
						}
						if (GamePlay.getWrong() == 3) {
							score.setVisibility(View.GONE);
							Toast toast = Toast.makeText(this, "Game Over",
									Toast.LENGTH_LONG);
							toast.show();
							gameOver(LevelsActivity.class);
						}
					}

					newQ = false;
				}
				b.setTextColor(0xffff0000);
				b.setEnabled(false);

				if(GamePlay.isVibrate()) {

					v.vibrate(400);
				}

			}
			return true;
		}
	}

	private void timer() {

            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
	}

	private Runnable updateTimerThread = new Runnable() {

		public void run() {

			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			time = timeSwapBuff + timeInMilliseconds;

			timerValue.setText("Time:" + Utility.timeToString(time));
			customHandler.postDelayed(this, 0);

			if (time > 600000) {
				finish();

			}
		}

	};
	private Editor editor;
	

	@SuppressLint("UseSparseArrays")
	private void gameOver(Class<?> a) {

		state = Constants.LOCKED;
		//	int k = locked.getInt(GamePlay.getDifficulty(), 0);
			if (a == MainActivity.class) {
				
				int k = locked.getInt("locked" + GamePlay.getDifficulty(), 0);
				
				
				if (GamePlay.getRight() >= 7 || k == 1) {

					state = Constants.UNLOCKED;
					
				}

				
				
				scores.put(GamePlay.getDifficulty(), state);

				editor = locked.edit();
				for (int s  : scores.keySet()) {
					editor.putInt("locked" + s, scores.get(s));
				}
				editor.commit();

			GamePlay.setScores(scores);
			GamePlay.setTime(time);
			timeSwapBuff += timeInMilliseconds;
			customHandler.removeCallbacks(updateTimerThread);
		}

        i = new Intent(this, a);
        i.putExtra("class","scores");
		finish();
		startActivity(i);

	}

	@Override
	public void onResume() {
		super.onResume();
        timer();
		
	}

	@Override
	public void onPause() {
		super.onPause();
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
		
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		super.onDestroy();
		

	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, LevelsActivity.class)
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
