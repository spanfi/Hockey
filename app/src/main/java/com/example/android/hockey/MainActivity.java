package com.example.android.hockey;

import android.app.Activity;
import android.view.View;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    /*
     * variables for score
     */
    int team_a_score = 0;
    int team_b_score = 0;

    /*
     * variables for countDownTimer
     */

    private String timeLeftText;
    private TextView countdownText;
    private Button countdownButton;

    /*
        * variables for countDownTimerPenalties
        */

    private Button twoMinForAButton;
    private Button fiveMinForAButton;
    private Button twoMinForBButton;
    private Button fiveMinForBButton;
    private TextView countdownFirstMinA;
    private TextView countdownSecondMinA;
    private TextView countdownFirstMinB;
    private TextView countdownSecondMinB;

    private CountDownTimer contDownTimer;
    private long timeLeftInMilliseconds = 600000;   //10 minuts
    private boolean timeRunning;                    //false

    private int penaltiesInMilliseconds;
    private boolean buttonON;

    /*
     * variables for flashing
     */
    private View mainBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        countdownText = findViewById(R.id.countdown_text);                      //find TextView
        countdownButton = findViewById(R.id.countdown_button);                  //find button Start Timer


        twoMinForAButton = findViewById(R.id.twoMinForTeamA_button);
        fiveMinForAButton = findViewById(R.id.fiveMinForTeamA_button);
        twoMinForBButton = findViewById(R.id.twoMinForTeamB_button);
        fiveMinForBButton = findViewById(R.id.fiveMinForTeamB_button);

        countdownFirstMinA = findViewById(R.id.firstPenaltiesForTeamA);         //find TextView
        countdownSecondMinA = findViewById(R.id.secondPenaltiesForTeamA);       //find TextView
        countdownFirstMinB = findViewById(R.id.firstPenaltiesForTeamB);         //find TextView
        countdownSecondMinB = findViewById(R.id.secondPenaltiesForTeamB);       //find TextView


        mainBackground = findViewById(R.id.viewBackground);                     //find background view

        countdownButton.setOnClickListener(new View.OnClickListener() {         //when the button is clicked
            @Override
            public void onClick(View view) {                                    //on click
                startStop();                                                    //go to startStop
            }
        });

        updateTimer();                                                          //refresh view
        penalties();
    }

    /*
     * --------------------------------------------------------------------------------
     * COUNTDOWN TIMER
     * --------------------------------------------------------------------------------
     */

    public void startStop() {
        if (timeRunning) {                                                      //if time is running
            stopTimer();                                                        //stop countdown
        } else {                                                                //if time isn't running
            startTimer();                                                       //start countdown

        }

    }

    public void startTimer() {
        contDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {          //start timer, schedule a countdown with regular intervals
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;                                     //it's a letter not a number, callback fired on regular interval.
                updateTimer();                                                  //refresh view
            }

            @Override
            public void onFinish() {
                timeLeftInMilliseconds = 600000;                               //restart timer
                countdownButton.setText(R.string.countdown_Start_btn);                         //change name of button
                timeRunning = false;
                updateTimer();
                mainBackground.startAnimation(getBlinkAnimation());            //flashing
            }
        }.start();

        countdownButton.setText(R.string.countdown_Pause_btn);                                  //start time and rename button to "pause"
        timeRunning = true;

    }

    public void stopTimer() {                                                   //stop timer
        contDownTimer.cancel();
        countdownButton.setText(R.string.countdown_Start_btn);                                  //stop time and rename button to "start"
        timeRunning = false;
    }

    public void updateTimer() {                                                 //determine how the time is displayed
        int minutes = (int) timeLeftInMilliseconds / 60000;                     //convert milliseconds to minutes
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;              //convert rest of milliseconds to seconds (the rest of the division - modulo - %)

        //String timeLeftText;                                                    //format text of time

        timeLeftText = "" + minutes;
        timeLeftText = timeLeftText + ":";
        if (seconds < 10)
            timeLeftText = timeLeftText + "0";                                  //display "9" seconds as "09"
        timeLeftText = timeLeftText + seconds;

        countdownText.setText(timeLeftText);

        if (buttonON) {
            countdownFirstMinA.setText(timeLeftText);
        } else {
            countdownFirstMinA.setText("nie");

        }
    }


    private void penalties() {
        twoMinForAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonON = true;
                penaltiesInMilliseconds = (int) (timeLeftInMilliseconds - 120000);
                String timeLeftText1 = "" + penaltiesInMilliseconds;
                countdownFirstMinB.setText(timeLeftText1);
            }
        });
        updateTimer();

    }

    /*
     * -----------------------------------------------------------------------------
     * FLASHING
     * -----------------------------------------------------------------------------
     */
    public Animation getBlinkAnimation() {
        Animation animation = new AlphaAnimation(1, 0);                         // Change alpha from fully visible to invisible
        animation.setDuration(300);                                             // duration - half a second
        animation.setInterpolator(new LinearInterpolator());                    // do not alter animation rate
        animation.setRepeatCount(5);                                            // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);                             // Reverse animation at the end so the button will fade back in
        return animation;

    }
    /*
      -----------------------------------------------------------------------------
      SCORE
      -----------------------------------------------------------------------------
     */

    /*
     * This method is called when the goal button for team A is clicked.
     */
    public void addGoalForTeamA(View view) {
        team_a_score = team_a_score + 1;
        displayForTeamA(team_a_score);
    }

    /*
     * This method is called when the +2 button for team A  is clicked.
     */
    public void addTwoForTeamA(View view) {
        team_a_score = team_a_score + 2;
        displayForTeamA(team_a_score);

    }

    /*
     * This method is called when the ThreeThrow button for team A  is clicked.
     */
    public void addOneForTeamA(View view) {
        team_a_score = team_a_score + 1;
        displayForTeamA(team_a_score);

    }

    /*
     * This method is called when the goal button for team B is clicked.
     */
    public void addGoalForTeamB(View view) {
        team_b_score = team_b_score + 1;
        displayForTeamB(team_b_score);

    }

    /*
     * This method is called when the +2 button for team B  is clicked.
     */
    public void addTwoForTeamB(View view) {
        team_b_score = team_b_score + 2;
        displayForTeamB(team_b_score);

    }

    /*
     * This method is called when the ThreeThrow button for team B  is clicked.
     */
    public void addOneForTeamB(View view) {
        team_b_score = team_b_score + 1;
        displayForTeamB(team_b_score);

    }

    /*
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int scoreTeamA) {
        TextView scoreView = findViewById(R.id.text_team_a_score);
        scoreView.setText(String.valueOf(scoreTeamA));
    }

    /*
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int scoreTeamB) {
        TextView scoreView = findViewById(R.id.text_team_b_score);
        scoreView.setText(String.valueOf(scoreTeamB));
    }

    /*
  * --------------------------------------------------------------------------
  * RESET
  * --------------------------------------------------------------------------
  */
    /*
     * This method is called when the Reset button is clicked.
     */
    public void resetScore(View view) {
        /*
         * score reset
         */
        team_a_score = 0;
        team_b_score = 0;

        /*
         * display reseted score
         */
        displayForTeamA(team_a_score);
        displayForTeamB(team_b_score);

    }

}
