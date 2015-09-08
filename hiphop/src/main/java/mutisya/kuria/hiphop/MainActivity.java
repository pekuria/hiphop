package mutisya.kuria.hiphop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.ads.AdRequest;
import com.amazon.device.ads.*;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.example.games.basegameutils.BaseGameActivity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import mutisya.kuria.hiphop.quiz.Constants;
import mutisya.kuria.hiphop.quiz.GamePlay;
import mutisya.kuria.hiphop.quiz.Question;


public class MainActivity extends BaseGameActivity implements SplashFragment.Listener, ScoresFragment.Listener {


    private SplashFragment splashFragment;
    private Intent i;
    private GamePlay c;
    private List<Question> questions;
    private boolean nxtLevel;
    private InterstitialAd interstitial;
    private EasyTracker easyTracker;
    private URI url;
    private String screenName;
    private static final String LOG_TAG = "InterstitialAdSample";
    private static final String APP_KEY = "518481f6b21f476d92635740805a6ae9";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String g = getIntent().getStringExtra("class");


        AdRegistration.enableLogging(true);
        AdRegistration.enableTesting(true);

        // Create the interstitial.
        this.interstitial = new InterstitialAd(this);

        // Set the listener to use the callbacks below.
        this.interstitial.setListener(new MyCustomAdListener());

        try {
            AdRegistration.setAppKey(APP_KEY);
        } catch (final IllegalArgumentException e) {
            Log.e(LOG_TAG, "IllegalArgumentException thrown: " + e.toString());
            return;
        }


        if(findViewById(R.id.fragment_container) != null) {

            if (g != null && g.equalsIgnoreCase("scores")) {

                // Load the interstitial.
                this.interstitial.loadAd();


                // Create the interstitial.
               // interstitial = new InterstitialAd(this);
                //interstitial.setAdUnitId("ca-app-pub-6751988760811765/9809663932");

                // Create ad request.
               // AdRequest adRequest = new AdRequest.Builder()

                        //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        //.addTestDevice("B0779154C9903DDC8FA354143286ABD9")

               //         .build();

                // Begin loading your interstitial.
               // interstitial.loadAd(adRequest);

                onDisplayAd();

                ScoresFragment scoresFragment = new ScoresFragment();
                scoresFragment.setListener(this);
                switchToFragment(scoresFragment);

               screenName = "Scores Fragment";


            } else {
                


                splashFragment = new SplashFragment();
                splashFragment.setListener(this);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, splashFragment).commit();

                screenName = "Splash Fragment";


            }


        }

        easyTracker = EasyTracker.getInstance(this);

// This screen name value will remain set on the tracker and sent with
// hits until it is set to a new value or to null.
        easyTracker.set(Fields.SCREEN_NAME, screenName);

        easyTracker.send(MapBuilder
                        .createAppView()
                        .build()
        );

    }





    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    // Switch UI to the given fragment
    void switchToFragment(Fragment newFrag) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {

            case R.id.action_settings:
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;

           case R.id.action_share:
               onShare();
               return true;

            case R.id.action_help:
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                return true;

           case R.id.action_credit:
               i = new Intent(this, CreditsActivity.class);
               startActivity(i);
               return true;

            case R.id.action_about:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStartGameRequested(boolean hardMode) {
        GamePlay.setNumRounds(10);
        GamePlay.setMode(0);
        GamePlay.setVibrate(true);
        i = new Intent(this, LevelsActivity.class);
        startActivity(i);

    }

    @Override
    public void onShowAchievementsRequested() {

    }

    public void onShare() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        try {
            url = new URI(
                    "https://play.google.com/store/apps/details?id=mutisya.kuria.hiphop");
        } catch (URISyntaxException e) {

            e.printStackTrace();
        }

        String shareBody = "Check out this game " + url;

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Hip Hop Quiz");

        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onNextLevel() {

        if (GamePlay.getRight() >= 7 && GamePlay.getDifficulty() < 6) {
            c = new GamePlay();
            questions = c.getQuestionSetFromDb(this,
                    GamePlay.getDifficulty() + 1, GamePlay.getNumRounds());
            c.setQuestions(questions);
            ((Hiphop) getApplication()).setCurrentGame(c);
            nxtLevel = true;
        }

        return nxtLevel;
    }

    @Override
    public void onDisplayAd() {

        if(interstitial.isReady())
         {
            interstitial.showAd();

        }

    }

    @Override
    public void onSubmitScore(Long score, Button submit) {
        if (isConnected()){
            if (isSignedIn()) {
                Games.Leaderboards.submitScore(getApiClient(),
                        getString(R.string.leaderboard_high_score), score);
                Toast toast = Toast.makeText(this, "Score submitted to leaderboard", Toast.LENGTH_LONG);
                toast.show();
                submit.setEnabled(false);
                easyTracker.send(MapBuilder
                                .createEvent("ui_action", // Event category (required)
                                        "button_press",  // Event action (required)
                                        "submit score",   // Event label
                                        score)            // Event value
                                .build()
                );

            } else {
                showAlert("Please sign in to submit your score");
                beginUserInitiatedSignIn();

            }
        }
            else{
                showAlert(getResources().getString(com.google.example.games.basegameutils.R.string.gamehelper_sign_in_failed));
            }
        
    }

    @Override
    public void onReplay() {
        if (onNextLevel()) {
            finish();
            GamePlay.setDifficulty(GamePlay.getDifficulty() + 1);
            startActivity(new Intent(this, QuestionActivity.class));
        } else {
            finish();
            c = new GamePlay();
            GamePlay.setDifficulty(GamePlay.getDifficulty());
            questions = c
                    .getQuestionSetFromDb(this, GamePlay.getDifficulty(), GamePlay.getNumRounds());
            c.setQuestions(questions);
            ((Hiphop) getApplication()).setCurrentGame(c);
            startActivity(new Intent(this, QuestionActivity.class));
        }

    }

    @Override
    public void onLoadMenu() {
        splashFragment = new SplashFragment();
        splashFragment.setListener(this);
        switchToFragment(splashFragment);

    }

    @Override
    public void onDisplayAnswers() {
        startActivity(new Intent(this, AnswersActivity.class));
    }

    @Override
    public void onShowLeaderboardsRequested() {
        if (isConnected()){
            if (isSignedIn()) {
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
                        getString(R.string.leaderboard_high_score)), Constants.RC_UNUSED);
            } else {
                showAlert(getString(R.string.leaderboards_not_available));
                beginUserInitiatedSignIn();
            }
        }
        else{
            showAlert(getResources().getString(com.google.example.games.basegameutils.R.string.gamehelper_sign_in_failed));
        }
    }

    @Override
    public void onSignInButtonClicked() {
        if(isConnected()) {
            beginUserInitiatedSignIn();
        }
        else{
            showAlert(getResources().getString(com.google.example.games.basegameutils.R.string.gamehelper_sign_in_failed));

        }
    }

    @Override
    public void onSignOutButtonClicked() {
        signOut();
        displaySignInButton();
    }

    @Override
    public void onSignInFailed() {
        displaySignInButton();
    }

    @Override
    public void onSignInSucceeded() {
       displaySignInButton();
    }

    @Override
    public void onStart(){
        super.onStart();
        getGameHelper().setMaxAutoSignInAttempts(1);
        EasyTracker.getInstance(this).activityStart(this);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }

    public void displaySignInButton(){
        if(isConnected() && isSignedIn()) {
            findViewById(R.id.sign_in_bar).setVisibility(View.GONE);
            findViewById(R.id.sign_out_bar).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.sign_in_bar).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_bar).setVisibility(View.GONE);
        }
    }

    public void onDestroy(){
        super.onDestroy();

    }

    public void onPause(){
        super.onPause();
    }

    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this); // Add this method.

    }


    private class MyCustomAdListener implements AdListener {
        @Override
        public void onAdLoaded(Ad ad, AdProperties adProperties) {
            Log.i(LOG_TAG, adProperties.getAdType().toString() + " ad loaded successfully.");

        }

        @Override
        public void onAdFailedToLoad(Ad ad, AdError error) {
            Log.w(LOG_TAG, "Ad failed to load. Code: " + error.getCode() + ", Message: " + error.getMessage());
        }

        @Override
        public void onAdExpanded(Ad ad) {

        }

        @Override
        public void onAdCollapsed(Ad ad) {

        }

        @Override
        public void onAdDismissed(Ad ad) {
            Log.i(LOG_TAG, "Ad has been dismissed by the user.");

        }
    }
}
