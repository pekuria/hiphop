/**
 * 
 */
package mutisya.kuria.hiphop.quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mutisya.kuria.hiphop.db.DBHelper;
import android.app.Activity;
import android.database.SQLException;

/**
 * 
 * This class represents the current game being played tracks the score and
 * player details
 * 
 */
public class GamePlay {

	private static int numRounds;
	private static int difficulty;
	private static int right;
	private static int wrong;
	private static boolean mute;
	private static boolean vibrate;
	private int round;
	private static long time;
	private static int mode;
	private static HashMap<?, ?> scores;

	public static HashMap<?, ?> getScores() {
		return scores;
	}

	public static void setScores(HashMap<?, ?> scores) {
		GamePlay.scores = scores;
	}

	public static int getMode() {
		return mode;
	}

	public static void setMode(int mode) {
		GamePlay.mode = mode;
	}

	public static long getTime() {
		return time;
	}

	public static void setTime(long time) {
		GamePlay.time = time;
	}

	private List<Question> questions = new ArrayList<Question>();

	public List<Question> getQuestionSetFromDb(Activity ctx, int level,
			int numQ) throws Error {
		// diff = getDifficultySettings();

		DBHelper myDbHelper = new DBHelper(ctx);
		
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		
		List<Question> questions = myDbHelper.getQuestionSet(level, numQ);
		myDbHelper.close();
		return questions;
	}

	/**
	 * @return the right
	 */
	public static int getRight() {
		return right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public static void setRight(int right) {
		GamePlay.right = right;
	}

	/**
	 * @return the wrong
	 */
	public static int getWrong() {
		return wrong;
	}

	/**
	 * @param wrong
	 *            the wrong to set
	 */
	public static void setWrong(int wrong) {
		GamePlay.wrong = wrong;
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * @param round
	 *            the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public static void setDifficulty(int difficulty) {
		GamePlay.difficulty = difficulty;
	}

	/**
	 * @return the difficulty
	 */
	public static int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @param q
	 *            the question to add
	 */
	public void addQuestions(Question q) {
		this.questions.add(q);
	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	public Question getNextQuestion() {

		// get the question
		Question next = questions.get(this.getRound());
		// update the round number to the next round
		this.setRound(this.getRound() + 1);
		return next;
	}

	/**
	 * method to increment the number of correct answers this game
	 */
	public void incrementRightAnswers() {
		right++;
	}

	/**
	 * method to increment the number of incorrect answers this game
	 */
	public void incrementWrongAnswers() {
		wrong++;
	}

	/**
	 * @param numRounds
	 *            the numRounds to set
	 */
	public static void setNumRounds(int numRounds) {
		GamePlay.numRounds = numRounds;
	}

	/**
	 * @return the numRounds
	 */
	public static int getNumRounds() {
		return numRounds;
	}

	/**
	 * method that checks if the game is over
	 * 
	 * @return boolean
	 */
	public boolean isGameOver() {

		return (getRound() >= getNumRounds());
	}

	public static boolean isMute() {
		return mute;
	}

	public static void setMute(boolean mute) {
		GamePlay.mute = mute;
	}

	public static boolean isVibrate() {
		return vibrate;
	}

	public static void setVibrate(boolean vibrate) {
		GamePlay.vibrate = vibrate;
	}
	
	

}
