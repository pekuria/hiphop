package mutisya.kuria.hiphop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 *
 */
public class SplashFragment extends Fragment implements View.OnClickListener {

    public interface Listener {

        public void onStartGameRequested(boolean hardMode);
        public void onShowAchievementsRequested();
        public void onShowLeaderboardsRequested();
        public void onSignInButtonClicked();
        public void onSignOutButtonClicked();
    }

    Listener mListener = null;
    boolean mShowSignIn = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_splash, container, false);

        v.findViewById(R.id.playBtn).setOnClickListener(this);
        v.findViewById(R.id.leaderboardBtn).setOnClickListener(this);
        v.findViewById(R.id.helpBtn).setOnClickListener(this);
        v.findViewById(R.id.sign_in_button).setOnClickListener(this);
        v.findViewById(R.id.sign_out_button).setOnClickListener(this);


        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playBtn:
                mListener.onStartGameRequested(false);
                break;

            case R.id.leaderboardBtn:
                mListener.onShowLeaderboardsRequested();
                break;

            case R.id.helpBtn:
                startActivity(new Intent(getActivity(), HelpActivity.class));
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
