package com.example.anitagarcia.myfirstapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MyActivity extends AppCompatActivity {
    final private int WINNING_SCORE = 20;
    private int userOverAllScore = 0;
    private int userTurnScore = 0;
    private int compOverallScore = 0;
    private int compTurnScore = 0;
	Animation shk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // On click listeners for buttons
		 shk=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        Button roll_button = (Button) findViewById(R.id.roll_button);
        final Button hold_button = (Button) findViewById(R.id.hold_button);
        Button reset_button = (Button) findViewById(R.id.reset_button);

        // Roll Button
        roll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int face = roll();
                    playUser(face);

            }
        });
        // Hold Button
        if (hold_button != null) {
            hold_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userOverAllScore += userTurnScore;//make sure it has only been clicked oncee
                    userTurnScore = 0;
                    updateOverallUser(userOverAllScore);
                    updateUserScoreText(0);
                    if(checkIfGameIsOver()){
                        gameOver();

                    }else
                    computerTurn();
                }
            });
        }
        //Reset Button
        reset_button.setOnClickListener(new View.OnClickListener(){
            @Override
        public  void onClick(View v){
                reset();
            }
        });

    }
    public void playUser(int face){
        if(face != 1 && face !=0){
            userTurnScore += face;
            updateUserScoreText(userTurnScore);
        }else if(face == 1){
            userTurnScore = 0;
            updateUserScoreText(userTurnScore);
            computerTurn();
        }
    }
    public void playComp(int face){

        if(face != 1 && face !=0){
            compTurnScore += face;
            updateComputerScoreText(compTurnScore);
            Log.d("(playComp", "" + compTurnScore);
        }else if(face == 1){
            compTurnScore = 0;
            updateComputerScoreText(compTurnScore);
            // stop

            Log.d("(playComp", "" + compTurnScore);
        }
    }
    public void computerTurn(){
      //  int value;
        final Button roll_button = (Button) findViewById(R.id.roll_button);
        final Button hold_button = (Button) findViewById(R.id.hold_button);
        roll_button.setEnabled(false);
        hold_button.setEnabled(false);
        final Handler delay = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                int value = roll();
                if(value != 1){
                    playComp(value);
                    if(!checkIfGameIsOver() && compTurnScore < 20){
                        computerTurn();
                    }else{
                        compOverallScore += compTurnScore;//make sure it has only been clicked once
                        gameOver();
                       /* compTurnScore = 0;
                        updateOverallComp(compOverallScore);
                        updateComputerScoreText(0);
                        roll_button.setEnabled(true);
                        hold_button.setEnabled(true);*/

                    }
                }else {
                    roll_button.setEnabled(true);
                    if (hold_button != null) {
                        hold_button.setEnabled(true);
                    }
                    compTurnScore = 0;
                    updateComputerScoreText(0);
                }

            }
        };
        delay.postDelayed(run, 1000);

    }
    public void updateUserScoreText(int score){
        TextView scoreText = (TextView) findViewById(R.id.your_score);
        scoreText.setText("Your Score:" + score);
    }
    public void updateComputerScoreText(int score){
        TextView scoreText = (TextView) findViewById(R.id.computer_score);
        scoreText.setText("Computer Score:" + score);
    }
    public void updateOverallComp(int score){
        TextView scoreText = (TextView) findViewById(R.id.overall_score_comp);
        scoreText.setText("Your Score:" + score);
    }
    public void updateOverallUser(int score) {
        TextView scoreText = (TextView) findViewById(R.id.overall_score_user);
        scoreText.setText("Your Score:" + score);
    }
    public int roll(){
        //Random randomGenerator = new Random();
        //int randomNumber = randomGenerator.nextInt(6);
		 int max=6;
        int min=1;
        int randomNumber=min+(int)(Math.random()*((max-min)+1));
        ImageView iv = (ImageView) findViewById(R.id.imageView);
		iv.startAnimation(shk);
        Drawable diceImage;
        switch(randomNumber){
            case 1: diceImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dice1, null);
                iv.setImageDrawable(diceImage);
                break;
            case 2: diceImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dice2, null);
                iv.setImageDrawable(diceImage);
                break;
            case 3: diceImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dice3, null);
                iv.setImageDrawable(diceImage);
                break;
            case 4: diceImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dice4, null);
                iv.setImageDrawable(diceImage);
                break;
            case 5: diceImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dice5, null);
                iv.setImageDrawable(diceImage);
                break;
            case 6: diceImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dice6, null);
                iv.setImageDrawable(diceImage);
                break;
        }

        return randomNumber;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    }public void reset(){
        userOverAllScore = 0;
        userTurnScore = 0;
        compOverallScore = 0;
        compTurnScore = 0;
        updateUserScoreText(0);
        updateComputerScoreText(0);
        updateOverallUser(0);
        updateOverallComp(0);
    }
    public void gameOver(){
        //reset();
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Drawable gameOver = ResourcesCompat.getDrawable(getResources(), R.drawable.gameover, null);
        iv.setImageDrawable(gameOver);

        View b = findViewById(R.id.roll_button);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.hold_button);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.reset_button);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.play_again_button);
        b.setVisibility(View.VISIBLE);
    }
    public boolean checkIfGameIsOver(){

        return userOverAllScore >= WINNING_SCORE || compOverallScore >= WINNING_SCORE;

    }
    public void playAgain(View view){
        reset();
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Drawable gameOver = ResourcesCompat.getDrawable(getResources(), R.drawable.dice1, null);
        iv.setImageDrawable(gameOver);

        View b = findViewById(R.id.roll_button);
        b.setVisibility(View.VISIBLE);

        b = findViewById(R.id.hold_button);
        b.setVisibility(View.VISIBLE);

        b = findViewById(R.id.reset_button);
        b.setVisibility(View.VISIBLE);

        b = findViewById(R.id.play_again_button);
        b.setVisibility(View.GONE);
    }

}
