/**
 * 
 */
package mutisya.kuria.hiphop.util;

import android.annotation.SuppressLint;
import java.util.List;
import mutisya.kuria.hiphop.quiz.Question;

public class Utility {
	
	/**
	 * Method to capitalise the first letter of a given string
	 * 
	 * @param s
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String capitalise(String s){
		if (s==null || s.length()==0) return s;
		
		String s1 = s.substring(0, 1).toUpperCase() + s.substring(1);
		return s1;
	}

	/**
	 * Method to get set of answers for a list of questions
	 * @param questions
	 * @return
	 */
	public static String getAnswers(List<Question> questions) {
		int question = 1;
		StringBuffer sb = new StringBuffer();
		
		for (Question q : questions){
			sb.append("Q").append(question).append(") ").append(q.getQuestion()).append("? \n");
			sb.append("Answer: ").append(q.getAnswer()).append("\n\n");
			question ++;
		}
		
		return sb.toString();
	}
	
	public static String timeToString(long updateTime){
		
		int secs = (int)(updateTime/1000);
		int mins = secs/60;
		secs = secs % 60;
		
		String text = "" + mins + ":"
				+ String.format("%02d", secs) + " Secs";
		
		return text;
		
	}

}
