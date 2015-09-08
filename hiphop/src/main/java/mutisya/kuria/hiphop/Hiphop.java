/**
 * 
 */
package mutisya.kuria.hiphop;

import mutisya.kuria.hiphop.quiz.GamePlay;
import android.app.Application;

public class Hiphop extends Application {

	private GamePlay currentGame;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	/**
	 * @param currentGame
	 *            the currentGame to set
	 */
	public void setCurrentGame(GamePlay currentGame) {
		this.currentGame = currentGame;
	}

	/**
	 * @return the currentGame
	 */
	public GamePlay getCurrentGame() {
		return currentGame;
	}



	@Override
	public void onTerminate() {
		super.onTerminate();

	}

}
