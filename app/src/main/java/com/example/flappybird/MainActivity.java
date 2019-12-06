package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean GAME_OVER=false;
    Intent tempIntent;
    //handling pause
    private static MusicManager musicManager;
    boolean continueBGMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        continueBGMusic=true;

        setContentView(R.layout.activity_main);
        Score myScore = new Score();
        Intent myint = getIntent();
        String gameOver= myint.getStringExtra("gameOverConf");
        String maxScore = myint.getStringExtra("maxScore");
        TextView playText = findViewById(R.id.playText);
        TextView gameoverTxt = findViewById(R.id.gameOverTxt);
        TextView maxScoretxt = findViewById(R.id.ScoreTxt);
        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        playText.setText("PLAY");

        if((gameOver!=null) && (maxScore!=null)) {
            musicManager.start(this, R.raw.gameover_sound);
            if(Integer.parseInt(maxScore)>myScore.highScore(getBaseContext())){
                myScore.storeHighScore(getBaseContext(),Integer.parseInt(maxScore));
            }
            gameoverTxt.setText(gameOver);
            maxScoretxt.setText("Your Score: " + maxScore);

            playText.setText("PLAY AGAIN");
            GAME_OVER=true;

        }

        highScoreTxt.setText("Highscore: "+myScore.highScore(getBaseContext()));

    }
    public void startGame(View view){
        //musicManager.start(this,R.raw.background_music_takeonme);
        if(GAME_OVER){
            startActivity(tempIntent);
            finish();
        }
        else{
            Intent myIntent = new Intent(this, StartGame.class);
            tempIntent = myIntent;
            startActivity(myIntent);
            finish();
            GAME_OVER=false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!continueBGMusic)
            MusicManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueBGMusic=false;
       /* if(GAME_OVER){
            musicManager.start(this,R.raw.gameover_sound);
        }
        else{
            MusicManager.start(this,R.raw.background_music_takeonme);
        }*/
    }
    public static MusicManager getMusicManager(){return musicManager;}
}
