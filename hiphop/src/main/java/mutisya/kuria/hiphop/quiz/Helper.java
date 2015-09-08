/**
 * 
 */
package mutisya.kuria.hiphop.quiz;

public class Helper {

	/**
	 * This method selects a end game response based on the players score
	 * and current difficulty level
	 * 
	 * @param numCorrect - num correct answers
	 * @param numRounds - number of questions
	 * @param diff - the difficulty level
	 * @return String comment
	 */
	public static String getResultComment(int numCorrect, int numRounds, int diff)
	{
		String comm="";
		int percentage = calculatePercentage(numCorrect, numRounds);
			if (percentage > 90){
				comm = "Awesome!";
			}else if (percentage >= 80){
				comm="Jaribu tena!";
			}else if (percentage >= 60){
				comm="Keep on trying..";
			}else if (percentage >= 40){
				comm="ALmost kenyan..";
			}else{
				comm="Wacha mchezo wewe..";
			}		
		
		return comm;
	}
	

	/**
	 * Calculate the percentage result based on the number correct and number of questions
	 * 
	 * @param numCorrect - number of questions right
	 * @param numRounds - total number of questions
	 * @return int percentage correct
	 */
	private static int calculatePercentage(int numCorrect, int numRounds) {
		double frac = (double)numCorrect/(double)numRounds;
		int percentage = (int) (frac*100);
		return percentage;
	}
}
