package mutisya.kuria.hiphop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mutisya.kuria.hiphop.quiz.GamePlay;
import mutisya.kuria.hiphop.util.AppRater;
import mutisya.kuria.hiphop.util.Utility;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 */
public class ScoresFragment extends Fragment implements View.OnClickListener {


    private Intent i;
    private TextView results;
    private String result;
    private int mode;
    private double time;
    private double time2;
    private long ans;
    private double score;
    private Button replay;


    public interface Listener{

        public boolean onNextLevel();
        public void onDisplayAd();
        public void onSubmitScore(Long scoreResult, Button n);
        public void onReplay();
        public void onLoadMenu();
        public void onDisplayAnswers();
        public void onShowLeaderboardsRequested();
        public void onSignInButtonClicked();
        public void onSignOutButtonClicked();


    }

    private Listener mListener;
    private Button submit;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_scores, container, false);

        submit = (Button)v.findViewById(R.id.submitBtn);
        submit.setOnClickListener(this);
        replay = (Button)v.findViewById(R.id.replayBtn);
        replay.setOnClickListener(this);
        v.findViewById(R.id.answerBtn).setOnClickListener(this);
        v.findViewById(R.id.menuBtn).setOnClickListener(this);
        v.findViewById(R.id.leaderboardBtn).setOnClickListener(this);
        v.findViewById(R.id.sign_in_button).setOnClickListener(this);
        v.findViewById(R.id.sign_out_button).setOnClickListener(this);

        AppRater.app_launched(getActivity());

        if(mListener.onNextLevel()){

            replay.setText("Next Level");
        }


        mode = GamePlay.getDifficulty() * 100;

        time = GamePlay.getTime();

        ans = GamePlay.getRight() * 10;

        time2 = time/10000;


        score = (double)(mode  + ans) - time2;

        result = "Level: " + GamePlay.getDifficulty() +
         " Score: " + GamePlay.getRight() + "/" + GamePlay.getNumRounds() + "\n" +
         " Time: " + Utility.timeToString((long)time) +
          " Total: " + score;

        results = (TextView)v.findViewById(R.id.high_scores_list);
        results.setText(result);

        //mListener.onSubmitScore((long)(score * 100) , submit);

        return v;
    }



    public void setListener(Listener l) {
        mListener = l;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.submitBtn:
                mListener.onSubmitScore((long)(score * 100) , submit);
                break;

            case R.id.answerBtn:
               mListener.onDisplayAnswers();
               break;

            case R.id.replayBtn:
                 mListener.onReplay();
                 mListener.onDisplayAd();
                break;

            case R.id.menuBtn:
                getActivity().finish();
                mListener.onLoadMenu();
                break;

            case R.id.leaderboardBtn:
                mListener.onShowLeaderboardsRequested();
             break;

            case R.id.sign_in_button:
                mListener.onSignInButtonClicked();
                break;


            case R.id.sign_out_button:
                mListener.onSignOutButtonClicked();
                break;


        }
    }


    }




